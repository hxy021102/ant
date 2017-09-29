package com.bx.ant.controller;

import com.bx.ant.service.DeliverOrderServiceI;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.MbBalanceLog;
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
    public Json viewBanlanceLogDetial(Long deliverOrderId) {
        Json json = new Json();
        json.setObj(deliverOrderService.getDeliverOrderExt(deliverOrderId));
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }
}
