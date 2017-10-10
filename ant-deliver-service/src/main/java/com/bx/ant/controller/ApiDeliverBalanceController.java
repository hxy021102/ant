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
        Calendar now = Calendar.getInstance();

        if (date == null) {
             date = now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) ;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            mbBalanceLog.setUpdatetimeBegin(dateFormat.parse(date
                    + "-" + now.getActualMinimum(Calendar.DAY_OF_MONTH) + " 00:00:00"));
            mbBalanceLog.setUpdatetimeEnd(dateFormat.parse(date
                    +  "-" + now.getActualMaximum(Calendar.DAY_OF_MONTH) + " 00:00:00"));
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
}
