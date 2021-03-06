package com.bx.ant.controller;

import com.aliyun.mns.model.TopicMessage;
import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DeliverOrderShopPay;
import com.bx.ant.pageModel.ShopDeliverAccount;
import com.bx.ant.pageModel.ShopDeliverApply;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopPayServiceI;
import com.bx.ant.service.ShopDeliverAccountServiceI;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
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
@RequestMapping("/api/deliver/deliverBalance")
public class ApiDeliverBalanceController extends BaseController {
    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Resource
    private MbBalanceServiceI mbBalanceService;

    @Resource
    private MbBalanceLogServiceI mbBalanceLogService;

    @Autowired
    private DeliverOrderShopPayServiceI deliverOrderPayShopService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ShopDeliverAccountServiceI shopDeliverAccountService;

    @Resource
    private ShopDeliverApplyServiceI shopDeliverApplyService;

    @Resource
    private MbWithdrawLogServiceI mbWithdrawLogService;

    /**
     * 查看余额列表
     * @param mbBalanceLog
     * @return
     */
    @RequestMapping("/viewDeliverBalanceLogList")
    @ResponseBody
    public Json viewDeliverBalanceLogList(MbBalanceLog mbBalanceLog){
        Json json = new Json();
        json.setMsg("u know");
        json.setObj(mbBalanceLogService.list(mbBalanceLog));
        json.setSuccess(true);
        return json;
    }

    /**
     * 获取门店派单账号流水明细
     * mbBalanceLog
     * @param  balanceLog
     * @return
     */
    @RequestMapping("/viewDeliverBalanceLogDetail")
    @ResponseBody
    public Json viewDeliverBalanceLogDetail(MbBalanceLog balanceLog) {
        Json json = new Json();
        if ("BT060".equals(balanceLog.getRefType()) ||"BT061".equals(balanceLog.getRefType()) ) {
            DeliverOrderShopPay deliverOrderShopPay = deliverOrderPayShopService.get(Long.parseLong(balanceLog.getRefId()));
            DeliverOrderShop deliverOrderShop = new DeliverOrderShop();
            deliverOrderShop.setDeliverOrderId(deliverOrderShopPay.getDeliverOrderId());
            deliverOrderShop.setId(deliverOrderShopPay.getDeliverOrderShopId());
            deliverOrderShop.setStatus("DSS04");
            json.setObj(deliverOrderService.getBalanceLogDetail(deliverOrderShop));
        }
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 获取门店派单账号流水
     * @param date
     * @param pageHelper
     * @return
     */
    @RequestMapping("/viewDeliverBalanceLogDataGrid")
    @ResponseBody
    public Json viewDeliverBalanceLogDataGrid(String date,PageHelper pageHelper, HttpServletRequest request) {
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
        Integer shopId = token.getShopId();
        mbBalanceLog.setShopId(shopId);
        dataGrid = mbBalanceLogService.updateDeliveryBalanceLogDataGrid(mbBalanceLog, pageHelper);
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
        Integer shopId = tokenWrap.getShopId();

        Map<String, Object> objectMap = new HashMap<String, Object>();
        if (!F.empty(shopId)) {
            objectMap.put("deliverBalance", mbBalanceService.addOrGetMbBalanceDelivery(shopId));
            objectMap.put("balance",mbBalanceService.addOrGetMbBalance(shopId) );
            j.setObj(objectMap);
            j.setMsg("u know");
            j.setSuccess(true);
        } else {
            j.setMsg("shopId不能为空");
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

    /**
     * 派单账户余额转出采购账户余额
     * @param request
     * @return
     */
    @RequestMapping("/updateAmountDeliverToBalance")
    @ResponseBody
    public Json updateAmountDeliverToBalance(HttpServletRequest request, Integer amount, String vcode) {
        Json j = new Json();
        TokenWrap tokenWrap = getTokenWrap(request);
        Integer shopId = tokenWrap.getShopId();

        String oldCode = (String) redisUtil.getString(Key.build(Namespace.SHOP_BALANCE_ROLL_VALIDATE_CODE, tokenWrap.getName()));
        if(F.empty(oldCode)) {
            j.setMsg("验证码已过期！");
            return j;
        }
        if(!oldCode.equals(vcode)) {
            j.setMsg("验证码错误！");
            return j;
        }

        mbBalanceService.transform(shopId, amount, 10, 1, 0);
        j.setMsg("u know");
        j.setSuccess(true);
        return j;
    }


    /**
     * 采购账户余额转入派单账户余额
     * @param request
     * @return
     */
    @RequestMapping("/updateAmountBalanceToDeliver")
    @ResponseBody
    public Json updateAmountBalanceToDeliver(HttpServletRequest request, Integer amount, String vcode) {
        Json j = new Json();
        TokenWrap tokenWrap = getTokenWrap(request);
        Integer shopId = tokenWrap.getShopId();

        String oldCode = (String) redisUtil.getString(Key.build(Namespace.SHOP_BALANCE_ROLL_VALIDATE_CODE, tokenWrap.getName()));
        if(F.empty(oldCode)) {
            j.setMsg("验证码已过期！");
            return j;
        }
        if(!oldCode.equals(vcode)) {
            j.setMsg("验证码错误！");
            return j;
        }

        mbBalanceService.transform(shopId, amount, 1, 10, 0);
        j.setMsg("u know");
        j.setSuccess(true);
        return j;
    }


    @ResponseBody
    @RequestMapping("/getVCode")
    public Json getVCode(HttpServletRequest request) {
        Json j = new Json();
        try {
            TokenWrap tokenWrap = getTokenWrap(request);
            String phone = tokenWrap.getName();
            if(!F.empty(phone)) {
                String oldCode = (String) redisUtil.getString(Key.build(Namespace.SHOP_BALANCE_ROLL_VALIDATE_CODE, phone));
                if(!F.empty(oldCode)) {
                    j.setMsg("访问过于频繁，请秒后重试！");
                    return j;
                }

                String code = Util.CreateNonceNumstr(6); //生成短信验证码
                MNSTemplate template = new MNSTemplate();
                template.setTemplateCode("SMS_105720074");
                Map<String, String> params = new HashMap<String, String>();
                params.put("code", code);
                template.setParams(params);
                TopicMessage topicMessage = MNSUtil.sendMns(phone, template);
                if(topicMessage != null) {
                    redisUtil.set(Key.build(Namespace.SHOP_BALANCE_ROLL_VALIDATE_CODE, phone), code, 60, TimeUnit.SECONDS);
                    j.setSuccess(true);
                    j.setMsg("获取短信验证码成功！");
                    j.setObj(params);
                    return j;
                }
                j.setMsg("获取短信验证码失败！");
            }
        } catch (Exception e) {
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取短信验证码接口异常", e);
        }
        return j;
    }

    /**
     * 提现
     * @param request
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateWithdraw")
    public Json updateWithdraw(HttpServletRequest request, MbWithdrawLog withdrawLog, String vcode){
        Json json = new Json();

        //1. 单次限额1W
        if (F.empty(withdrawLog.getAmount()) || withdrawLog.getAmount() > 10000 * 100) {
            json.setSuccess(false);
            json.setMsg("超过单次额度");
            return json;
        }

        //2. 获取账户
        TokenWrap tokenWrap = getTokenWrap(request);
        Integer shopId = tokenWrap.getShopId();

        // 短信验证
        String oldCode = (String) redisUtil.getString(Key.build(Namespace.SHOP_BALANCE_ROLL_VALIDATE_CODE, tokenWrap.getName()));
        if (F.empty(oldCode)) {
            json.setMsg("验证码已过期！");
            return json;
        }
        if (!oldCode.equals(vcode)) {
            json.setMsg("验证码错误！");
            return json;
        }

        MbBalance balance = mbBalanceService.addOrGetMbBalanceDelivery(shopId);

        //1. 判断余额
        if (F.empty(withdrawLog.getAmount()) || withdrawLog.getAmount() > balance.getAmount()) {
            //throw new ServiceException("转账金额为空或余额不足");
            json.setSuccess(false);
            json.setMsg("转账金额为空或余额不足");
            return json;
        }

        //2. 填充数据
        ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
        shopDeliverApply.setShopId(shopId);
        shopDeliverApply.setStatus("DAS02");
        List<ShopDeliverApply> shopDeliverApplies = shopDeliverApplyService.query(shopDeliverApply);
        if (CollectionUtils.isNotEmpty(shopDeliverApplies)) {
            shopDeliverApply = shopDeliverApplies.get(0);
            ShopDeliverAccount shopDeliverAccount = shopDeliverAccountService.get(shopDeliverApply.getAccountId());
            withdrawLog.setBalanceId(balance.getId());
            withdrawLog.setApplyLoginId(shopDeliverAccount.getId() +"");
            withdrawLog.setReceiver(shopDeliverAccount.getNickName());
            withdrawLog.setReceiverAccount(shopDeliverAccount.getRefId());
            withdrawLog.setHandleStatus("HS01");
            withdrawLog.setRefType("BT101");
            withdrawLog.setApplyLoginIP(HttpUtil.getIpAddress(request));

            mbWithdrawLogService.add(withdrawLog);
        }

        json.setMsg("申请成功");
        json.setSuccess(true);
        return json;
    }

    /**
     *
     * @param request
     * @param pageHelper
     * @param date
     * @return
     */
    @RequestMapping("/getWithdrawDataGrid")
    @ResponseBody
    public Json getWithdrawDataGrid(HttpServletRequest request, PageHelper pageHelper, String date) {
        Json json = new Json();
        DataGrid dataGrid = new DataGrid();

        //通过门店ID找到申请者账户
        TokenWrap tokenWrap = getTokenWrap(request);
        Integer shopId = tokenWrap.getShopId();

        MbWithdrawLogView withdrawLogView = new MbWithdrawLogView();

        //默认时间为当月
        if (date == null) {
            Calendar now = Calendar.getInstance();
            date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) ;
        }

        //设定搜索时间为月初00:00:00至下月初00:00:00
        try {
            Date timeStart  = new SimpleDateFormat("yyyy-MM").parse(date);
            withdrawLogView.setAddtimeBegin(timeStart);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(timeStart);
            calendar.add(Calendar.MONTH,1);
            withdrawLogView.setAddtimeEnd(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
        shopDeliverApply.setShopId(shopId);
        shopDeliverApply.setStatus("DAS02");
        List<ShopDeliverApply> shopDeliverApplies = shopDeliverApplyService.query(shopDeliverApply);
        if (CollectionUtils.isNotEmpty(shopDeliverApplies)) {
            shopDeliverApply = shopDeliverApplies.get(0);
            withdrawLogView.setApplyLoginId(shopDeliverApply.getAccountId() + "");
            withdrawLogView.setRefType("BT101");
            //未指定排序则默认为修改时间降序
            if (F.empty(pageHelper.getSort())) {
                pageHelper.setSort("updatetime");
                pageHelper.setOrder("desc");
            }
            //获取数据
            dataGrid = mbWithdrawLogService.dataGridView(withdrawLogView, pageHelper);
        }
        json.setObj(dataGrid);
        json.setSuccess(true);
        json.setMsg("查询数据成功");
        return json;

    }

    /**
     * 按月统计收入支出
     * @param request
     * @return
     */
    @RequestMapping("/getTotalBalanceByMonth")
    @ResponseBody
    public Json getTotalBalanceByMonth(HttpServletRequest request, String date) {

        Json j = new Json();
        try{
            TokenWrap token = getTokenWrap(request);
            if(!F.empty(token.getUid())) {
                MbBalanceLog mbBalanceLog = new MbBalanceLog();
                mbBalanceLog.setShopId(token.getShopId());
                //默认时间为当月
                if (date == null) {
                    Calendar now = Calendar.getInstance();
                    date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) ;
                }

                //设定搜索时间为月初00:00:00至下月初00:00:00

                Date timeStart = new SimpleDateFormat("yyyy-MM").parse(date);
                mbBalanceLog.setUpdatetimeBegin(timeStart);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(timeStart);
                calendar.add(Calendar.MONTH,1);
                mbBalanceLog.setUpdatetimeEnd(calendar.getTime());
                Map<String, Integer> totalBalance = mbBalanceLogService.totalBalanceByMonth(mbBalanceLog);

                j.setSuccess(true);
                j.setMsg("获取成功！");
                j.setObj(totalBalance);
            }

        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("按月统计收入支出接口异常", e);
        }

        return j;
    }
}
