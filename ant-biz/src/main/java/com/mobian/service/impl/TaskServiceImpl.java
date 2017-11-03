package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.concurrent.ThreadCache;
import com.mobian.model.TmbContract;
import com.mobian.model.TmbContractItem;
import com.mobian.model.TmbOrder;
import com.mobian.model.TmbOrderItem;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.Constants;
import com.mobian.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by john on 16/10/16.
 */
@Service
public class TaskServiceImpl implements TaskServiceI {

    @Autowired
    private MbContractServiceI mbContractService;
    @Autowired
    private MbContractItemServiceI mbContractItemService;
    @Autowired
    private RedisUserServiceImpl redisUserService;
    @Autowired
    private MbOrderServiceI mbOrderService;
    @Autowired
    private MbOrderItemServiceI mbOrderItemService;
    @Autowired
    private MbStockOutServiceI mbStockOutService;
    @Autowired
    private MbStockOutItemServiceI mbStockOutItemService;
    @Autowired
    private MbItemStockServiceI mbItemStockService;
    @Autowired
    private MbItemStockLogServiceI mbItemStockLogService;
    @Autowired
    private UserServiceI userService;
    @Autowired
    private MbShopServiceI mbShopService;
    @Autowired
    private MbSupplierStockInItemServiceI mbSupplierStockInItemService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HibernateTransactionManager transactionManager;

    @Override
    public void setContractPrice() {
        try {
            List<TmbContract> l = mbContractService.queryAllMbContract();
            if(l != null && l.size() > 0) {
                for(TmbContract tmbContract : l) {
                    List<TmbContractItem> lc = mbContractItemService.queryMbContractItemByShopId(tmbContract.getId());
                    if(lc != null && lc.size() > 0) {
                        for(TmbContractItem tmbContractItem : lc) {
                            redisUserService.setContractPrice(tmbContract.getShopId(), tmbContractItem.getItemId(), tmbContractItem.getPrice());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOrderQuantity() {

        List<TmbOrder> l = mbOrderService.queryOrderListByStatus();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (l != null && l.size() > 0) {
            for (TmbOrder tmbOrder : l) {
                List<TmbOrderItem> itemList = mbOrderItemService.queryMbOrderItemByOrderId(tmbOrder.getId());
                if (itemList != null && itemList.size() > 0) {
                    for (TmbOrderItem tmbOrderItem : itemList) {
                        if (tmbOrder.getDeliveryWarehouseId() == null) continue;
                        String key = tmbOrder.getDeliveryWarehouseId() + ":" + tmbOrderItem.getItemId();
                        Integer itemIdValue = map.get(key);
                        if (itemIdValue == null) {
                            map.put(key, tmbOrderItem.getQuantity());
                        } else {
                            map.put(key, itemIdValue += tmbOrderItem.getQuantity());
                        }

                        String keyStatus = tmbOrder.getDeliveryWarehouseId() + ":" + tmbOrderItem.getItemId() + ":" + tmbOrder.getStatus();
                        Integer value = map.get(keyStatus);
                        if (value == null) {
                            map.put(keyStatus, tmbOrderItem.getQuantity());
                        } else {
                            map.put(keyStatus, value += tmbOrderItem.getQuantity());
                        }
                    }
                }
            }
        }

        for (String key : map.keySet()) {
            Integer orderQuantity = map.get(key);
            redisUserService.setOrderQuantity(key, orderQuantity);
        }

    }
    @Override
    public void deleteUnPayOrder(){
        mbOrderService.deleteUnPayOrderByTime();
    }

    @Override
    public void updateBatchShopLocation() {
        List<MbShop> mbShopList = mbShopService.getNullLocation();
        for (MbShop mbShop : mbShopList) {
            mbShopService.setShopLocation(mbShop);
            mbShopService.edit(mbShop);
        }
    }

    @Override
    public void updateCostPrice() {
        try {
            ThreadCache mbOrderCache = new ThreadCache(MbOrder.class) {
                @Override
                protected Object handle(Object key) {
                    return mbOrderService.get((Integer) key);
                }
            };
            ThreadCache mbStockOrderCache = new ThreadCache(MbStockOut.class) {
                @Override
                protected Object handle(Object key) {
                    return mbStockOutService.get((Integer) key);
                }
            };

            ThreadCache mbItemStock = new ThreadCache(MbItemStock.class) {
                @Override
                protected Object handle(Object key) {
                    String[] keys = key.toString().split("[|]");
                    return mbItemStockService.getByWareHouseIdAndItemId(Integer.parseInt(keys[0]), Integer.parseInt(keys[1]));
                }
            };
            List<MbStockOutItem> mbStockOutItemList = mbStockOutItemService.queryStockOutItemWithoutCostPrice();
            for (MbStockOutItem mbStockOutItem : mbStockOutItemList) {
                MbStockOut mbStockOut = mbStockOrderCache.getValue(mbStockOutItem.getMbStockOutId());
                if (mbStockOut != null && !F.empty(mbStockOut.getWarehouseId()) && !F.empty(mbStockOutItem.getItemId())) {

                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();

                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务

                    TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

                    try{
                        MbItemStock itemStock = mbItemStock.getValue(mbStockOut.getWarehouseId() + "|" + mbStockOutItem.getItemId());
                        mbStockOutItem.setCostPrice(itemStock.getAveragePrice());
                        mbStockOutItemService.edit(mbStockOutItem);
                        transactionManager.commit(status);
                    }catch(Exception e){
                        transactionManager.rollback(status);
                        e.printStackTrace();
                    }

                }
            }
            List<MbOrderItem> mbOrderItemList = mbOrderItemService.queryListByWithoutCostPrice();
            for (MbOrderItem mbOrderItem : mbOrderItemList) {
                MbOrder mbOrder = mbOrderCache.getValue(mbOrderItem.getOrderId());
                if (mbOrder != null && !F.empty(mbOrder.getDeliveryWarehouseId()) && !F.empty(mbOrderItem.getItemId())) {
                    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
                    TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
                    try {
                        MbItemStock itemStock = mbItemStock.getValue(mbOrder.getDeliveryWarehouseId() + "|" + mbOrderItem.getItemId());
                        mbOrderItem.setCostPrice(itemStock.getAveragePrice());
                        mbOrderItemService.edit(mbOrderItem);
                        transactionManager.commit(status);
                    } catch (Exception e) {
                        transactionManager.rollback(status);
                        e.printStackTrace();
                    }
                }

            }

            ThreadCache mbItemStock2 = new ThreadCache(MbItemStock.class) {
                @Override
                protected Object handle(Object key) {
                    return mbItemStockService.get((Integer) key);
                }
            };
            List<MbItemStockLog> mbItemStockLogList = mbItemStockLogService.queryListByWithoutCostPrice();
            for (MbItemStockLog mbItemStockLog : mbItemStockLogList) {
                MbItemStock stock = mbItemStock2.getValue(mbItemStockLog.getItemStockId());
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
                TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
                if(F.empty(mbItemStockLog.getCostPrice())) {
                    mbItemStockLog.setCostPrice(stock.getAveragePrice() == null ? -1 : stock.getAveragePrice());
                }
                try {
                    //入库单；取入库价格
                    if (!F.empty(mbItemStockLog.getReason())) {
                        Pattern p = Pattern.compile("([\\s\\S]*)ID[:：](\\d+)[\\s\\S]*库存[:：](\\d+)");
                        Matcher m = p.matcher(mbItemStockLog.getReason());
                        String[] strs = new String[3];
                        boolean isMatch = m.find();
                        int i = 0, j = m.groupCount();
                        while (isMatch && i < j) {
                            strs[i] = m.group(i + 1);
                            i++;
                        }
                        if (isMatch) {
                            if (strs[0].indexOf("入库") > -1) {
                                MbSupplierStockInItem request = new MbSupplierStockInItem();
                                request.setSupplierStockInId(Integer.parseInt(strs[1]));
                                request.setQuantity(mbItemStockLog.getQuantity());
                                request.setItemId(stock.getItemId());
                                List<MbSupplierStockInItem> list = mbSupplierStockInItemService.query(request);
                                if (!CollectionUtils.isEmpty(list)) {
                                    mbItemStockLog.setInPrice(list.get(0).getPrice());
                                }
                            }
                            if (F.empty(mbItemStockLog.getEndQuantity())) {
                                String endQuantity = strs[2];
                                if (!F.empty(endQuantity)) {
                                    mbItemStockLog.setEndQuantity(Integer.parseInt(endQuantity));
                                }
                            }
                        }
                    }

                    mbItemStockLogService.edit(mbItemStockLog);
                    transactionManager.commit(status);
                } catch (Exception e) {
                    transactionManager.rollback(status);
                    e.printStackTrace();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ThreadCache.clear();
        }
    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void remindDrivers() {
        List<MbOrder> list = mbOrderService.queryRemindOrder();
        if (!CollectionUtils.isEmpty(list)) {
            for (MbOrder t : list) {
                //把每个订单的订单号，配送司机，配送时间 当做key（保证唯一性）
                String key = t.getId() + ":" + t.getDeliveryDriver() + ":" + DateUtil.format(t.getDeliveryTime(), Constants.DATE_FORMAT_YMD);
                // 查询redis里面是否有过发送记录，没有就发送一条信息，有就不重复发送
                if (t.getDeliveryDriver() != null && redisUtil.getString(Key.build(Namespace.REMIND_DRIVERS, key)) == null) {
                    User user = userService.getFromCache(t.getDeliveryDriver());
                    String phone = user.getPhone();
                    if (!F.empty(phone)) {
                        MbShop mbShop = mbShopService.getFromCache(t.getShopId());
                        String shopName = mbShop.getName();
                        MNSTemplate template = new MNSTemplate();
                        //设置短信模板
                        template.setTemplateCode("SMS_91795050");
                        Map<String, String> params = new HashMap<String, String>();
                        //设置模板里需要的参数
                        params.put("shopName", shopName);
                        params.put("orderId", t.getId() + "");
                        params.put("deliveryTime", DateUtil.format(t.getDeliveryTime(), Constants.DATE_FORMAT));
                        template.setParams(params);
                        MNSUtil.sendMns(phone, template);
                        redisUtil.set(Key.build(Namespace.REMIND_DRIVERS, key), "已发",24*60*60, TimeUnit.SECONDS);
                    }
                }
            }
        }

    }

}
