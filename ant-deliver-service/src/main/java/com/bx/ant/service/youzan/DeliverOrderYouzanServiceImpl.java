package com.bx.ant.service.youzan;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.Supplier;
import com.bx.ant.pageModel.SupplierItemRelation;
import com.bx.ant.pageModel.SupplierItemRelationView;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderYouzanServiceI;
import com.bx.ant.service.SupplierItemRelationServiceI;
import com.bx.ant.service.SupplierServiceI;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.PageHelper;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.thirdpart.youzan.YouzanUtil;
import com.mobian.util.ConvertNameUtil;
import com.youzan.open.sdk.client.auth.Sign;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanLogisticsOnlineConfirm;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradesSoldGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanLogisticsOnlineConfirmParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanLogisticsOnlineConfirmResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private RedisUtil redisUtil;

    @Override
    public void youzanOrders() {
        String accessToken = (String)redisUtil.get(Key.build(Namespace.YOUZAN_CONFIG, "youzan_access_token"));
        YZClient client = new DefaultYZClient(new Token(accessToken)); //new Sign(appKey, appSecret)
        YouzanTradesSoldGetParams youzanTradesSoldGetParams = new YouzanTradesSoldGetParams();

        youzanTradesSoldGetParams.setStatus(WAIT_SELLER_SEND_GOODS);
//        youzanTradesSoldGetParams.setStatus(WAIT_BUYER_CONFIRM_GOODS);

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
                    if(valid)
                        deliverOrderService.addAndItems(order, supplierItemRelations);

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
}
