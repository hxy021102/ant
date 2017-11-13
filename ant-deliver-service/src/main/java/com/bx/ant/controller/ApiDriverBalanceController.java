package com.bx.ant.controller;

import com.aliyun.mns.model.TopicMessage;
import com.bx.ant.pageModel.*;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.service.MbWithdrawLogServiceI;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.HttpUtil;
import com.mobian.util.Util;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanxp 2017/9/22
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/driver/driverBalance")
public class ApiDriverBalanceController extends BaseController {

    @Resource
    private MbBalanceServiceI mbBalanceService;

    @Resource
    private MbBalanceLogServiceI mbBalanceLogService;

    @Resource
    private DriverOrderShopServiceI driverOrderPShopService;

    /**
     * 派单账号流水明细
     * mbBalanceLog
     * @param  balanceLog
     * @return
     */
    @RequestMapping("/viewDriverBanlanceLogDetial")
    @ResponseBody
    public Json viewBanlanceLogDetial(MbBalanceLog balanceLog) {


        Json json = new Json();
        if ("BT150".equals(balanceLog.getRefType()) ||"BT151".equals(balanceLog.getRefType()) ) {
            DriverOrderShop driverOrderShop= driverOrderPShopService.getView(Long.parseLong(balanceLog.getRefId()));
            json.setObj(driverOrderShop);
        }
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 获取派单账号流水
     * @param date
     * @param pageHelper
     * @return
     */
    @RequestMapping("/viewDriverBanlanceLogDataGrid")
    @ResponseBody
    public Json viewDriverBanlanceLogDataGrid(String date,PageHelper pageHelper, HttpServletRequest request) {
        Json j = new Json();
        DataGrid dataGrid;
        MbBalanceLog mbBalanceLog = new MbBalanceLog();
        if(F.empty(pageHelper.getRows()  )) {
            pageHelper.setRows(50);
        }
        if(F.empty(pageHelper.getSort())) {
            pageHelper.setSort("addtime");
        }
        if(F.empty(pageHelper.getOrder())) {
            pageHelper.setOrder("desc");
        }
        //默认时间为当月
        if (date == null) {
             Calendar now = Calendar.getInstance();
             date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) ;
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
        TokenWrap token = getTokenWrap(request);
        Integer accountId = Integer.parseInt(token.getUid());
        mbBalanceLog.setShopId(accountId);
        dataGrid = mbBalanceLogService.updateDriverBalanceLogDataGrid(mbBalanceLog, pageHelper);
        j.setObj(dataGrid);
        j.setMsg("u know");
        j.setSuccess(true);
        return j;
    }

    /**
     * 获取门店派单账户余额
     * @param request
     * @return
     */
    @RequestMapping("/viewBalance")
    @ResponseBody
    public Json viewBalance(HttpServletRequest request) {
        Json j = new Json();
        TokenWrap tokenWrap = getTokenWrap(request);
        Integer accountId = Integer.parseInt(tokenWrap.getUid());

        Map<String, Object> objectMap = new HashMap<String, Object>();
        if (!F.empty(accountId)) {
            objectMap.put("driverBalance", mbBalanceService.addOrGetDriverBalance(accountId));
            j.setObj(objectMap);
            j.setMsg("u know");
            j.setSuccess(true);
        } else {
            j.setMsg("uId不能为空");
            j.setSuccess(false);
        }
        return j;
    }

//    /**
//     * 获取门店采购账户余额
//     * @param request
//     * @return
//     */
//    @RequestMapping("/viewBalance")
//    @ResponseBody
//    public Json viewBalance(HttpServletRequest request) {
//        Json j = new Json();
////        TokenWrap tokenWrap = getTokenWrap(request);
////        Integer shopId = tokenWrap.getShopId();
//        Integer shopId = 1332;
//        if (!F.empty(shopId)) {
//            j.setObj(mbBalanceService.addOrGetMbBalance(shopId));
//            j.setMsg("u know");
//            j.setSuccess(true);
//        } else {
//            j.setMsg("shopId不能为空");
//            j.setSuccess(false);
//        }
//        return j;
//    }



//    /**
//     * 提现
//     * @param request
//     * @param
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("/withdraw")
//    public Json test(HttpServletRequest request, MbWithdrawLog withdrawLog, String vcode){
//        Json json = new Json();
//
//        //1. 单次限额1W
//        if (F.empty(withdrawLog.getAmount()) || withdrawLog.getAmount() > 10000 * 100) {
//            json.setSuccess(false);
//            json.setMsg("超过单次额度");
//            return json;
//        }
//
//        //2. 获取账户
//        TokenWrap tokenWrap = getTokenWrap(request);
//        Integer shopId = tokenWrap.getShopId();
//
//        // 短信验证
//        String oldCode = (String) redisUtil.getString(Key.build(Namespace.SHOP_BALANCE_ROLL_VALIDATE_CODE, tokenWrap.getName()));
//        if(F.empty(oldCode)) {
//            json.setMsg("验证码已过期！");
//            return json;
//        }
//        if(!oldCode.equals(vcode)) {
//            json.setMsg("验证码错误！");
//            return json;
//        }
//
//        MbBalance balance = mbBalanceService.addOrGetMbBalanceDelivery(shopId);
//
//        //1. 判断余额
//        if(F.empty(withdrawLog.getAmount()) || withdrawLog.getAmount() > balance.getAmount()) {
//            //throw new ServiceException("转账金额为空或余额不足");
//            json.setSuccess(false);
//            json.setMsg("转账金额为空或余额不足");
//            return json;
//        }
//
//        //2. 填充数据
//        ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
//        shopDeliverApply.setShopId(shopId);
//        shopDeliverApply.setStatus("DAS02");
//        List<ShopDeliverApply> shopDeliverApplies = shopDeliverApplyService.query(shopDeliverApply);
//        if (CollectionUtils.isNotEmpty(shopDeliverApplies)) {
//            shopDeliverApply = shopDeliverApplies.get(0);
//            ShopDeliverAccount shopDeliverAccount = shopDeliverAccountService.get(shopDeliverApply.getAccountId());
//            withdrawLog.setBalanceId(balance.getId());
//            withdrawLog.setApplyLoginId(shopDeliverAccount.getId() +"");
//            withdrawLog.setReceiver(shopDeliverAccount.getNickName());
//            withdrawLog.setReceiverAccount(shopDeliverAccount.getRefId());
//            withdrawLog.setHandleStatus("HS01");
//            withdrawLog.setRefType("BT101");
//            withdrawLog.setApplyLoginIP(HttpUtil.getIpAddress(request));
//
//            mbWithdrawLogService.add(withdrawLog);
//        }
//
//        json.setMsg("申请成功");
//        json.setSuccess(true);
//        return json;
//    }
//
//
//    @RequestMapping("/withdrawDataGrid")
//    @ResponseBody
//    public Json dataGridWithdraw(HttpServletRequest request, PageHelper pageHelper, String date) {
//        Json json = new Json();
//        DataGrid dataGrid = new DataGrid();
//
//        //通过门店ID找到申请者账户
//       TokenWrap tokenWrap = getTokenWrap(request);
//       Integer shopId = tokenWrap.getShopId();
//
//        MbWithdrawLogView withdrawLogView = new MbWithdrawLogView();
//
//        //默认时间为当月
//        if (date == null) {
//            Calendar now = Calendar.getInstance();
//            date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) ;
//        }
//
//        //设定搜索时间为月初00:00:00至下月初00:00:00
//        try {
//            Date timeStart  = new SimpleDateFormat("yyyy-MM").parse(date);
//            withdrawLogView.setAddtimeBegin(timeStart);
//            Calendar calendar = new GregorianCalendar();
//            calendar.setTime(timeStart);
//            calendar.add(Calendar.MONTH,1);
//            withdrawLogView.setAddtimeEnd(calendar.getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
//        shopDeliverApply.setShopId(shopId);
//        shopDeliverApply.setStatus("DAS02");
//        List<ShopDeliverApply> shopDeliverApplies = shopDeliverApplyService.query(shopDeliverApply);
//        if (CollectionUtils.isNotEmpty(shopDeliverApplies)) {
//            shopDeliverApply = shopDeliverApplies.get(0);
//            withdrawLogView.setApplyLoginId(shopDeliverApply.getAccountId() + "");
//            //未指定排序则默认为修改时间降序
//            if (F.empty(pageHelper.getSort())) {
//                pageHelper.setSort("updatetime");
//                pageHelper.setOrder("desc");
//            }
//            //获取数据
//            dataGrid = mbWithdrawLogService.dataGridView(withdrawLogView, pageHelper);
//        }
//        json.setObj(dataGrid);
//        json.setSuccess(true);
//        json.setMsg("查询数据成功");
//        return json;
//    }
}
