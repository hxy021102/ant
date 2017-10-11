package com.bx.ant.controller;

import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopPayServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.pageModel.DeliverOrderShopPay;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by wanxp 2017/9/22
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
    private MbBalanceServiceI mbBalanceService;

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

    /**
     * 获取门店运单账号流水明细
     * refId为mbBalanceLog.refId即
     * @param refId
     * @return
     */
    @RequestMapping("/viewDeliverBanlanceLogDetial")
    @ResponseBody
    public Json viewBanlanceLogDetial(Long refId) {
        Json json = new Json();
        DeliverOrderShopPay deliverOrderShopPay = deliverOrderPayShopService.get(refId);
        json.setObj(deliverOrderService.getDeliverOrderExt(deliverOrderShopPay.getDeliverOrderId()));
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 获取门店运单账号流水
     * @param date
     * @param pageHelper
     * @return
     */
    @RequestMapping("/viewDeliverBanlanceLogDataGrid")
    @ResponseBody
    public Json viewDeliverBanlanceLogDataGrid(String date,PageHelper pageHelper) {
        Json j = new Json();
        DataGrid dataGrid;
        MbBalanceLog mbBalanceLog = new MbBalanceLog();

        //默认时间为当月
        if (date == null) {
             Calendar now = Calendar.getInstance();
             date = now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) ;
        }

        //设定搜索时间为月初00:00:00至下月初00:00:00
        try {
            Date timeStart  = new SimpleDateFormat("yyyy-MM").parse(date);
            mbBalanceLog.setUpdatetimeBegin(timeStart);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(timeStart);
            calendar.add(Calendar.MONTH,1);
            mbBalanceLog.setUpdatetimeEnd(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //TODO 测试时设置shop ID值,若真正使用从token中获取
//        TokenWrap token = getTokenWrap(request);
//        Integer shopId = token.getShopId();
        Integer shopId = 1332;
        mbBalanceLog.setShopId(shopId);
        dataGrid = mbBalanceLogService.getDeliveryBalanceLogDataGrid(mbBalanceLog, pageHelper);
        j.setObj(dataGrid);
        j.setMsg("u know");
        j.setSuccess(true);
        return j;
    }

    /**
     * 获取门店运单账户余额
     * @param request
     * @return
     */
    @RequestMapping("/viewDeliverBalance")
    @ResponseBody
    public Json viewBalance(HttpServletRequest request) {
        Json j = new Json();
//        TokenWrap tokenWrap = getTokenWrap(request);
//        Integer shopId = tokenWrap.getShopId();
       Integer shopId = 1332;
       if (!F.empty(shopId)) {
           j.setObj(mbBalanceService.addOrGetMbBalanceDelivery(shopId));
           j.setMsg("u know");
           j.setSuccess(true);
       } else {
           j.setMsg("shopId不能为空");
           j.setSuccess(false);
       }
       return j;
    }
    /**
     * 转出
     * @param request
     * @return
     */
    @RequestMapping("/transformAmountDeliverToBalance")
    @ResponseBody
    public Json transformAmountDeliverToBalance(HttpServletRequest request, Integer amount) {
        Json j = new Json();
//        TokenWrap tokenWrap = getTokenWrap(request);
//        Integer shopId = tokenWrap.getShopId();
        Integer shopId = 1332;
        mbBalanceService.transform(shopId, amount, 10, 1, 0);
        j.setMsg("u know");
        j.setSuccess(true);
        return j;
    }

    /**
     * 转出
     * @param request
     * @return
     */
    @RequestMapping("/transformAmountBalanceToDeliver")
    @ResponseBody
    public Json transformAmountBalanceToDeliver(HttpServletRequest request, Integer amount) {
        Json j = new Json();
//        TokenWrap tokenWrap = getTokenWrap(request);
//        Integer shopId = tokenWrap.getShopId();
        Integer shopId = 1332;
        mbBalanceService.transform(shopId, amount, 1, 10, 0);
        j.setMsg("u know");
        j.setSuccess(true);
        return j;
    }
}
