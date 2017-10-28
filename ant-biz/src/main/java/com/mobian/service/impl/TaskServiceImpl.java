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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private UserServiceI userService;
    @Autowired
    private MbShopServiceI mbShopService;
    @Autowired
    private RedisUtil redisUtil;

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
                    MbItemStock itemStock = mbItemStock.getValue(mbStockOut.getWarehouseId() + "|" + mbStockOutItem.getItemId());
                    mbStockOutItem.setCostPrice(itemStock.getAveragePrice());
                    mbStockOutItemService.edit(mbStockOutItem);
                }
            }
            List<MbOrderItem> mbOrderItemList = mbOrderItemService.queryListByWithoutCostPrice();
            for (MbOrderItem mbOrderItem : mbOrderItemList) {
                MbOrder mbOrder = mbOrderCache.getValue(mbOrderItem.getOrderId());
                if (mbOrder != null && !F.empty(mbOrder.getDeliveryWarehouseId()) && !F.empty(mbOrderItem.getItemId())) {
                    MbItemStock itemStock = mbItemStock.getValue(mbOrder.getDeliveryWarehouseId() + "|" + mbOrderItem.getItemId());
                    mbOrderItem.setCostPrice(itemStock.getAveragePrice());
                    mbOrderItemService.edit(mbOrderItem);
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
