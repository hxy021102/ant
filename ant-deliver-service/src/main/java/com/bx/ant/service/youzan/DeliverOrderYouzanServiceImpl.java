package com.bx.ant.service.youzan;

import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.DataGrid;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.Supplier;
import com.bx.ant.pageModel.SupplierItemRelation;
import com.bx.ant.pageModel.SupplierItemRelationView;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderYouzanServiceI;
import com.bx.ant.service.SupplierItemRelationServiceI;
import com.bx.ant.service.SupplierServiceI;
import com.mobian.pageModel.PageHelper;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.thirdpart.youzan.YouzanUtil;
import com.mobian.util.ConvertNameUtil;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanLogisticsOnlineConfirm;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradeSelffetchcodeGet;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradesSoldGet;
import com.youzan.open.sdk.gen.v3_0_0.model.*;
import net.sf.json.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 17/10/11.
 */
@Service
public class DeliverOrderYouzanServiceImpl implements DeliverOrderYouzanServiceI {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DeliverOrderServiceI deliverOrderService;

    @Resource
    private SupplierServiceI supplierService;

    @Resource
    private SupplierItemRelationServiceI supplierItemRelationService;

    @Resource
    private SupplierOrderBillServiceI supplierOrderBillService;
    @Resource
    private DeliverOrderPayServiceI deliverOrderPayService;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private HibernateTransactionManager transactionManager;

    @Override
    public void youzanOrders() {
        String accessToken = (String)redisUtil.get(Key.build(Namespace.YOUZAN_CONFIG, "youzan_access_token"));
        YZClient client = new DefaultYZClient(new Token(accessToken)); //new Sign(appKey, appSecret)
        YouzanTradesSoldGetParams youzanTradesSoldGetParams = new YouzanTradesSoldGetParams();

        youzanTradesSoldGetParams.setStatus(WAIT_SELLER_SEND_GOODS);

        YouzanTradesSoldGet youzanTradesSoldGet = new YouzanTradesSoldGet();
        youzanTradesSoldGet.setAPIParams(youzanTradesSoldGetParams);
        YouzanTradesSoldGetResult result = client.invoke(youzanTradesSoldGet);
        YouzanTradesSoldGetResult.TradeDetailV2[] trades = result.getTrades();
        if(trades != null && trades.length > 0) {
            // 查询对应供应商
            Supplier supplier = new Supplier();
            supplier.setAppKey(ConvertNameUtil.getString(YouzanUtil.APPKEY));
            List<Supplier> suppliers = supplierService.query(supplier);
            if(CollectionUtils.isNotEmpty(suppliers)) {
                supplier = suppliers.get(0);

                for(YouzanTradesSoldGetResult.TradeDetailV2 trade : trades) {
                    // 过滤到店自提订单
                    if("fetch".equals(trade.getShippingType())) continue;

                    DeliverOrder exist = deliverOrderService.getBySupplierOrderId(trade.getTid());
                    if(exist != null) {
                        // 非待分配，修改有赞订单状态
                        if(!DeliverOrderServiceI.STATUS_NOT_ALLOCATION.equals(exist.getStatus())) {
                            youzanOrderConfirm(trade.getTid());
                        }
                        continue;
                    }

                    //填充订单信息
                    DeliverOrder order = new DeliverOrder();
                    order.setSupplierId(supplier.getId());
                    order.setSupplierOrderId(trade.getTid());
                    order.setContactPeople(trade.getReceiverName());
                    order.setContactPhone(trade.getReceiverMobile());
                    order.setDeliveryAddress(trade.getReceiverState() + trade.getReceiverCity() + trade.getReceiverDistrict() + trade.getReceiverAddress());
                    order.setRemark(trade.getBuyerMessage());

                    List<SupplierItemRelationView> supplierItemRelations = new ArrayList<SupplierItemRelationView>();
                    YouzanTradesSoldGetResult.TradeOrderV2[] orders = trade.getOrders();
                    boolean valid = true;
                    for(YouzanTradesSoldGetResult.TradeOrderV2 item : orders) {
                        SupplierItemRelationView itemRelation = new SupplierItemRelationView();
                        itemRelation.setSupplierId(supplier.getId());
                        // TODO 是否更换外部商品编码item.getOuterItemId()
                        itemRelation.setSupplierItemCode(item.getItemId() + "");
                        itemRelation.setOnline(true);
                        List<SupplierItemRelation> itemRelations = supplierItemRelationService.dataGrid(itemRelation, new PageHelper()).getRows();
                        // 无法找到接入方对应商品
                        if (CollectionUtils.isEmpty(itemRelations)) {
                            valid = false;
                            break;
                        }

                        itemRelation.setItemId(itemRelations.get(0).getItemId());
                        itemRelation.setPrice(itemRelations.get(0).getPrice());
                        itemRelation.setQuantity(item.getNum().intValue());

                        supplierItemRelations.add(itemRelation);
                    }

                    // 添加订单和订单明细
                    if(valid) {
                        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
                        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
                        try {
                            deliverOrderService.addAndItems(order, supplierItemRelations);

                            transactionManager.commit(status);
                        }catch(Exception e){
                            transactionManager.rollback(status);
                            logger.error("有赞订单添加失败", e);
                        }

                    }
                }
            }
        }
    }

    @Override
    public void youzanOrderConfirm(String tid) {
        String accessToken = (String)redisUtil.get(Key.build(Namespace.YOUZAN_CONFIG, "youzan_access_token"));
        YZClient client = new DefaultYZClient(new Token(accessToken));
        YouzanLogisticsOnlineConfirmParams youzanLogisticsOnlineConfirmParams = new YouzanLogisticsOnlineConfirmParams();

        youzanLogisticsOnlineConfirmParams.setTid(tid);
        youzanLogisticsOnlineConfirmParams.setIsNoExpress(1L);

        YouzanLogisticsOnlineConfirm youzanLogisticsOnlineConfirm = new YouzanLogisticsOnlineConfirm();
        youzanLogisticsOnlineConfirm.setAPIParams(youzanLogisticsOnlineConfirmParams);
        YouzanLogisticsOnlineConfirmResult result = client.invoke(youzanLogisticsOnlineConfirm);
        if(!result.getIsSuccess()) {
            logger.error("youzan.logistics.online.confirm接口调用失败");
        }
    }

    @Override
    public void settleYouzanBill() {
        String accessToken = (String)redisUtil.get(Key.build(Namespace.YOUZAN_CONFIG, "youzan_access_token"));
        YZClient client = new DefaultYZClient(new Token(accessToken));
        YouzanTradesSoldGetParams youzanTradesSoldGetParams = new YouzanTradesSoldGetParams();

        youzanTradesSoldGetParams.setStatus(WAIT_SELLER_SEND_GOODS);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -Integer.valueOf(ConvertNameUtil.getString(YouzanUtil.SETTLE_TERM, "7")));
        youzanTradesSoldGetParams.setEndUpdate(c.getTime());
        c.add(Calendar.MONTH, -3);
        youzanTradesSoldGetParams.setStartUpdate(c.getTime());

        YouzanTradesSoldGet youzanTradesSoldGet = new YouzanTradesSoldGet();
        youzanTradesSoldGet.setAPIParams(youzanTradesSoldGetParams);
        YouzanTradesSoldGetResult result = client.invoke(youzanTradesSoldGet);
        YouzanTradesSoldGetResult.TradeDetailV2[] trades = result.getTrades();
        if(trades != null && trades.length > 0) {
            String tids = "";
            // 根据供应商订单ID集合查询未结算的订单
            for(YouzanTradesSoldGetResult.TradeDetailV2 trade : trades) {
                if(!F.empty(tids)) tids += ",";
                tids += trade.getTid();
            }
            DeliverOrder order = new DeliverOrder();
            order.setSupplierOrderId(tids);
            PageHelper ph = new PageHelper();
            ph.setHiddenTotal(true);
            List<DeliverOrder> list = deliverOrderService.unPayOrderDataGrid(order, ph).getRows();

            Integer amount = 0;
            for (DeliverOrder d : list) {
                if(d.getAmount() != null) {
                    amount += d.getAmount();
                }
            }
            SupplierOrderBill supplierOrderBill = new SupplierOrderBill();
            supplierOrderBill.setSupplierId(list.get(0).getSupplierId());
            supplierOrderBill.setStatus("BAS04");//自动结算
            supplierOrderBill.setAmount(amount);
            supplierOrderBill.setPayWay(list.get(0).getPayWay());
            supplierOrderBillService.add(supplierOrderBill); // 创建账单
            for (DeliverOrder d : list) {

                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
                TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

                try {
                    d.setPayStatus("DPS02");// 已结算
                    deliverOrderService.editOrderStatus(d);

                    DeliverOrderPay deliverOrderPay = new DeliverOrderPay();
                    deliverOrderPay.setDeliverOrderId(d.getId());
                    deliverOrderPay.setSupplierOrderBillId(supplierOrderBill.getId().intValue());
                    deliverOrderPay.setSupplierId(list.get(0).getSupplierId());
                    deliverOrderPay.setAmount(d.getAmount());
                    deliverOrderPay.setStatus("DPS02");// 已结算
                    deliverOrderPayService.add(deliverOrderPay);

                    transactionManager.commit(status);
                }catch(Exception e){
                    transactionManager.rollback(status);
                    logger.error("自动结算失败", e);
                }

            }
        }
    }

    @Override
    public Long getOrderByCode(String code) {
        String accessToken = (String)redisUtil.get(Key.build(Namespace.YOUZAN_CONFIG, "youzan_access_token"));
        YZClient client = new DefaultYZClient(new Token(accessToken));
        YouzanTradeSelffetchcodeGetParams youzanTradeSelffetchcodeGetParams = new YouzanTradeSelffetchcodeGetParams();

        youzanTradeSelffetchcodeGetParams.setCode(code);
        youzanTradeSelffetchcodeGetParams.setFields("tid");

        YouzanTradeSelffetchcodeGet youzanTradeSelffetchcodeGet = new YouzanTradeSelffetchcodeGet();
        youzanTradeSelffetchcodeGet.setAPIParams(youzanTradeSelffetchcodeGetParams);
        YouzanTradeSelffetchcodeGetResult result = client.invoke(youzanTradeSelffetchcodeGet);
        String  tid = result.getTid();
        DeliverOrder o = deliverOrderService.getOrderByYouZanTid(tid);
        Long orderId = o.getId();
        return orderId;
    }
}
