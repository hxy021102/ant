package com.bx.ant.controller;

import com.bx.ant.pageModel.DeliverOrderShopQuery;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.mobian.pageModel.*;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by  wanxp 2017/9/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/shop")
public class ApiShopController extends BaseController {

    @Resource
    private MbShopServiceI mbShopService;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;


    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;

    /**
     * 门店管理界面
     * @param request
     * @return
     */
    @RequestMapping("/manager")
    @ResponseBody
    public Json manager( HttpServletRequest request) {
        Json json = new Json();
        Map<String, Object> data = new HashMap<String, Object>();
        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();

        //获取门店信息
        data.put("shop", mbShopService.get(shopId));
        //获取当天结束与开始
        Calendar todayC = Calendar.getInstance();
        todayC.set(Calendar.HOUR_OF_DAY,0);
        todayC.set(Calendar.MINUTE,0);
        todayC.set(Calendar.SECOND,0);
        Date todayStart = todayC.getTime();
        todayC.set(Calendar.HOUR_OF_DAY,23);
        todayC.set(Calendar.MINUTE,59);
        todayC.set(Calendar.SECOND,59);
        Date todayEnd = todayC.getTime();

        //获取余额流水
        MbBalanceLog mbBalanceLog = new MbBalanceLog();
        mbBalanceLog.setUpdatetimeBegin(todayStart);
        mbBalanceLog.setUpdatetimeEnd(todayEnd);
        mbBalanceLog.setShopId(shopId);
        DataGrid dataGrid = mbBalanceLogService.updateDeliveryBalanceLogDataGrid(mbBalanceLog,new PageHelper());
        data.put("balanceLogDataGrid", dataGrid);
       //获取有效订单数量
        Integer todayQuantity = new Integer(0);
        DeliverOrderShopQuery deliverOrderShop = new DeliverOrderShopQuery();
        deliverOrderShop.setShopId(shopId);
        String[] statusList = {DeliverOrderShopServiceI.STATUS_ACCEPTED,DeliverOrderShopServiceI.STATUS_COMPLETE};
        deliverOrderShop.setStatusList(statusList);
        deliverOrderShop.setUpdatetimeBegin(todayStart);
        deliverOrderShop.setUpdatetimeEnd(todayEnd);
        List<DeliverOrderShop> deliverOrderShopList = deliverOrderShopService.dataGrid(deliverOrderShop, new PageHelper()).getRows();
        if (CollectionUtils.isNotEmpty(deliverOrderShopList)) data.put("deliverOrderShopQuantity", deliverOrderShopList.size());

        json.setMsg("u know");
        json.setObj(data);
        json.setSuccess(true);
        return json;
    }

    /**
     * 获取shopDataGrid
    * @param shop
     * @param pageHelper
     * @return
     */
    @RequestMapping("dataGrid")
    @ResponseBody
    public  Json dataGrid(MbShop shop, PageHelper pageHelper, HttpServletRequest request) {
        Json j = new Json();
        TokenWrap token = getTokenWrap(request);
        shop.setContactPhone(token.getName());
        j.setSuccess(true);
        j.setMsg("u know");
        j.setObj(mbShopService.dataGrid(shop, pageHelper));
        return j;
    }
}
