package com.bx.ant.service.impl;

import com.bx.ant.pageModel.DriverAccount;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.pageModel.DriverOrderShopView;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbShopServiceI;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.GeoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by w9777 on 2017/11/6.
 */
@Service
public class DriverOrderShopAllocationServiceImpl implements DriverOrderShopAllocationServiceI {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DriverOrderShopServiceI driverOrderShopService;

    @Autowired
    private DriverAccountServiceI driverAccountService;

    @Resource
    private MbShopServiceI mbShopService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void orderAllocation() {
        //1. 获取待分配的订单
        DriverOrderShop request = new DriverOrderShop();
        PageHelper ph = new PageHelper();
        String status = DriverOrderShopServiceI.STATUS_STANDBY;
        request.setStatus(status);
        List<DriverOrderShopView> driverOrderShops = driverOrderShopService.dataGridView(request, ph).getRows();
        int size = driverOrderShops.size();
        for (int i = 0;i < size;i++) {
            try{
                allocationDriverOrder(driverOrderShops.get(0));
            }catch(Exception e){
                logger.error("分单失败", e);
            }
        }
    }

    protected void allocationDriverOrder(DriverOrderShopView driverOrderShop) {
        //2. 获取骑手
        List<DriverAccount> driverAccounts = driverAccountService.getAvilableAndWorkDriver();

        //3. 门店数字地址
        MbShop shop = mbShopService.getFromCache(driverOrderShop.getDeliverOrderShop().getShopId());
        if (shop == null) throw new ServiceException("信息不全driverOrderShop.getDeliverOrderShop().getShopId()无法获取门店");
        if ((shop.getLongitude() == null || shop.getLatitude() == null)
                && !F.empty(shop.getAddress())) {
            BigDecimal[] point = GeoUtil.getPosition(shop.getAddress());
            if (point != null) {
                shop.setLongitude(point[0]);
                shop.setLatitude(point[1]);
            }
        }
        //4. 距离
        double maxDistance = Double.valueOf(ConvertNameUtil.getString("DDSV001", "5000"));;

        //5. 筛选出符合条件的骑手
        for (DriverAccount account : driverAccounts) {
            String titude = (String) redisUtil.getString(Key.build(Namespace.DRIVER_REALTIME_LOCATION, account.getId().toString()));

            //无坐标信息则不分单
            if (F.empty(titude)) continue;

            Double driverLongtitude = Double.parseDouble(titude.split(",")[0]);
            Double driverLatitude = Double.parseDouble(titude.split(",")[1]);

            double distance = GeoUtil.getDistance(driverLongtitude, driverLatitude,
                    shop.getLongitude().doubleValue(), shop.getLatitude().doubleValue());
            //6. 分配订单
            if (distance < maxDistance) {
                Calendar today = Calendar.getInstance();
                String todayStr = today.get(Calendar.YEAR) + "-" + today.get(Calendar.MONTH)
                        + "-" + today.get(Calendar.DAY_OF_MONTH);
                redisUtil.setList(Key.build(Namespace.DRIVER_ORDER_SHOP_CACHE, account.getId().toString() + ":" + todayStr),
                        driverOrderShop.getId().toString());
                redisUtil.expire(Key.build(Namespace.DRIVER_ORDER_SHOP_CACHE, account.getId().toString() + ":" + todayStr),
                        Integer.parseInt(ConvertNameUtil.getString("DDSV101","100")), TimeUnit.SECONDS);
            }
        }


    }
}
