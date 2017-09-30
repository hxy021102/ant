package com.bx.ant.controller;

import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.Json;
import com.mobian.service.MbShopServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by  wanxp 2017/9/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/deliverOrder")
public class ApiDeliverOrderController extends BaseController {

    @Resource
    private MbShopServiceI mbShopService;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;


    @RequestMapping("/viewAuditList")
    @ResponseBody
    public Json viewAuditList(Integer shopId){
        Json json = new Json();
        json.setMsg("u know");
        json.setObj(deliverOrderService.listOrderByOrderShopIdAndShopStatus(shopId,deliverOrderShopService.STATUS_AUDITING));
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/editOrderTransformStatus")
    public Json transform(DeliverOrder deliverOrder) {
        Json json = new Json();
        deliverOrderService.transform(deliverOrder);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/viewOrderList")
    @ResponseBody
    public Json viewOrderList(Integer shopId,String status){
        Json json = new Json();
        json.setMsg("u know");
        json.setObj(deliverOrderService.listOrderByShopIdAndOrderStatus(shopId,status));
        json.setSuccess(true);
        return json;
    }
}
