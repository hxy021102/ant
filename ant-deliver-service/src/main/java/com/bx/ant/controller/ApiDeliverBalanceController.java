package com.bx.ant.controller;

import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopPayServiceI;
import com.mobian.pageModel.*;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.DeliverOrderShopPay;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbShopServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by guxin on 2017/4/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/deliverBalance")
public class ApiDeliverBalanceController extends BaseController {

    @Resource
    private MbShopServiceI mbShopService;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;

    @Autowired
    private DeliverOrderShopPayServiceI deliverOrderPayShopService;


    @RequestMapping("/viewDeliverBanlanceLogList")
    @ResponseBody
    public Json viewBanlanceLogList(MbBalanceLog mbBalanceLog){
        Json json = new Json();
        json.setMsg("u know");
        json.setObj(mbBalanceLogService.list(mbBalanceLog));
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/viewDeliverBanlanceLogDetial")
    public Json viewBanlanceLogDetial(Integer refId) {
        Json json = new Json();
        DeliverOrderShopPay deliverOrderShopPay = deliverOrderPayShopService.get(refId);
        json.setObj(deliverOrderService.getDeliverOrderExt(deliverOrderShopPay.getDeliverOrderId()));
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }
}
