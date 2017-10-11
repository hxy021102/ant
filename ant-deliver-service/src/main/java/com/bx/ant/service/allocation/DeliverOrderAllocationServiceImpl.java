package com.bx.ant.service.allocation;

import com.bx.ant.service.DeliverOrderAllocationServiceI;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.service.MbShopServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by john on 17/10/11.
 */
@Service
public class DeliverOrderAllocationServiceImpl implements DeliverOrderAllocationServiceI {

    @Resource
    private MbShopServiceI mbShopService;

    @Autowired
    private ShopDeliverApplyServiceI shopDeliverApplyService;

    @Override
    public void updateOrderOwnerShopId() {
        //1、查开通了派单功能，且状态开启配送的门店List
        //2、获取待分配的订单
        //3、计算待分配订单的数字地址
        //4、计算最近距离点
        //5、计算分单价格
    }
}
