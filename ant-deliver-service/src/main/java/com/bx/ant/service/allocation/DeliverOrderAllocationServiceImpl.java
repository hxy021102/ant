package com.bx.ant.service.allocation;

import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbShopServiceI;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.GeoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 17/10/11.
 */
@Service
public class DeliverOrderAllocationServiceImpl implements DeliverOrderAllocationServiceI {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private MbShopServiceI mbShopService;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private ShopDeliverApplyServiceI shopDeliverApplyService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Autowired
    private DeliverOrderShopItemServiceI deliverOrderShopItemService;

    @Autowired
    private DeliverOrderItemServiceI deliverOrderItemService;

    @Autowired
    private HibernateTransactionManager transactionManager;


    @Override
    public void orderAllocation() {

        //1、获取待分配的订单
        DeliverOrderExt request = new DeliverOrderExt();
        PageHelper ph = new PageHelper();
        ph.setHiddenTotal(true);
        request.setStatusList(new String[]{DeliverOrderServiceI.STATUS_NOT_ALLOCATION, DeliverOrderServiceI.STATUS_SHOP_REFUSE});
        DataGrid dataGrid = deliverOrderService.dataGrid(request, ph);
        List<DeliverOrder> deliverOrderList = dataGrid.getRows();

        for (DeliverOrder deliverOrder : deliverOrderList) {

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();

            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务

            TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

            try{
                allocationOrderOwnerShopId(deliverOrder);
                transactionManager.commit(status);
            }catch(Exception e){
                transactionManager.rollback(status);
                e.printStackTrace();
                logger.error("分单失败", e);
            }
        }
    }
    //PROPAGATION_REQUIRES_NEW
    public void allocationOrderOwnerShopId(DeliverOrder deliverOrder) {
        //2、查开通了派单功能，且状态开启配送的门店List
        List<ShopDeliverApply> shopDeliverApplyList = shopDeliverApplyService.getAvailableAndWorkShop();

        //3、计算待分配订单的数字地址
        if ((deliverOrder.getLongitude() == null || deliverOrder.getLatitude() == null)
                && !F.empty(deliverOrder.getDeliveryAddress())) {
            BigDecimal[] point = GeoUtil.getPosition(deliverOrder.getDeliveryAddress());
            if(point != null) {
                deliverOrder.setLongitude(point[0]);
                deliverOrder.setLatitude(point[1]);
            }

        }
        //4、计算最近距离点
        MbShop minMbShop = null;
        double minDistance = 0;
        //拒接的状态下，查询拒接过的门店
        List<Integer> excludeShop = new ArrayList<Integer>();
        if (DeliverOrderServiceI.STATUS_SHOP_REFUSE.equals(deliverOrder.getStatus())) {
            DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
            deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_REFUSED);
            List<DeliverOrderShop> deliverOrderShopList = deliverOrderShopService.query(deliverOrderShop);
            for (DeliverOrderShop orderShop : deliverOrderShopList) {
                excludeShop.add(orderShop.getShopId());
            }
        }
        for (ShopDeliverApply shopDeliverApply : shopDeliverApplyList) {
            MbShop mbShop = shopDeliverApply.getMbShop();
            if (excludeShop.contains(mbShop.getId())) continue;
            double distance = GeoUtil.getDistance(deliverOrder.getLongitude().doubleValue(), deliverOrder.getLatitude().doubleValue(), mbShop.getLongitude().doubleValue(), mbShop.getLatitude().doubleValue());
            if (distance < minDistance || minDistance == 0) {
                minMbShop = mbShop;
                minDistance = distance;
            }
        }
        //5、计算分单价格
        if (minMbShop != null && !F.empty(minMbShop.getId())) {
            DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
            deliverOrderShop.setAmount(deliverOrder.getAmount());
            deliverOrderShop.setDeliverOrderId(deliverOrder.getId());
            deliverOrderShop.setShopId(minMbShop.getId());
            deliverOrderShop.setStatus(DeliverOrderShopServiceI.STATUS_AUDITING);
            deliverOrderShop.setDistance(new BigDecimal(minDistance));
            deliverOrderShopService.add(deliverOrderShop);
            List<DeliverOrderItem> deliverOrderItemList = deliverOrderItemService.getDeliverOrderItemList(deliverOrder.getId());
            deliverOrderShopItemService.addByDeliverOrderItemList(deliverOrderItemList, deliverOrderShop);
            deliverOrder.setShopId(minMbShop.getId());
            deliverOrder.setStatus(DeliverOrderServiceI.STATUS_SHOP_ALLOCATION);
            deliverOrderService.edit(deliverOrder);

            // 发送短信通知
            if(!F.empty(minMbShop.getContactPeople())) {
                MNSTemplate template = new MNSTemplate();
                template.setTemplateCode("SMS_105685061");
                Map<String, String> params = new HashMap<String, String>();
                params.put("orderId", deliverOrder.getId().toString());
                params.put("address", deliverOrder.getDeliveryAddress());
                params.put("time", ConvertNameUtil.getString("DSV100") + "分钟");
                template.setParams(params);
                MNSUtil.sendMns(minMbShop.getContactPeople(), template);
            }

        }

    }
}
