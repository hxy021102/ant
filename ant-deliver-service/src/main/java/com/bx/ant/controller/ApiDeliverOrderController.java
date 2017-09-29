package com.bx.ant.controller;

import com.bx.ant.pageModel.DeliverOrderExt;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.mobian.pageModel.DeliverOrder;
import com.mobian.pageModel.DeliverOrderShop;
import com.mobian.pageModel.Json;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.BeanUtil;
import org.apache.http.HttpRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guxin on 2017/4/22.
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


    @RequestMapping("/viewAuditList")
    @ResponseBody
    public Json viewAuditList(Integer shopId){
        Json json = new Json();
        json.setMsg("u know");
        json.setObj(deliverOrderService.listAuditOrder(shopId));
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
}
