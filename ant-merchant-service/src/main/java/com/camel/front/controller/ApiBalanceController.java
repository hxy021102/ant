package com.camel.front.controller;

import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbRechargeLogServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.Constants;
import com.mobian.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xuwm on 2017/5/17.
 *
 * 钱包
 */
@Controller
@RequestMapping("/api/apiBalanceController")
public class ApiBalanceController extends BaseController {

    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;

    @Autowired
    private MbRechargeLogServiceI mbRechargeLogService;

    @Autowired
    private MbShopServiceI mbShopService;

    /**
     * 获取钱包余额
     */
    @RequestMapping("/getBalance")
    @ResponseBody
    public Json getBalance(Integer type, HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);
            if(s.getShopId() != null) {
                MbShop shop = mbShopService.get(s.getShopId());
                if(!"AS02".equals(shop.getAuditStatus())) {
                    j.setObj(-1);
                    return j;
                }
                if(F.empty(type)) type = 1; // 默认余额
                MbBalance balance = null;
                if(type == 1)
                    balance = mbBalanceService.addOrGetMbBalance(s.getShopId());
                else
                    balance = mbBalanceService.addOrGetMbBalanceCash(s.getShopId());
                j.setSuccess(true);
                j.setMsg("获取余额成功");
                j.setObj(balance);
            } else {
                j.setObj(-1);
            }
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取商品分类接口异常", e);
        }

        return j;
    }

    /**
     * 获取钱包明细接口
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(MbBalanceLog balanceLog, PageHelper ph) {
        Json j = new Json();
        try{
            if(ph.getRows() == 0 || ph.getRows() > 50) {
                ph.setRows(10);
            }
            if(F.empty(ph.getSort())) {
                ph.setSort("addtime");
            }
            if(F.empty(ph.getOrder())) {
                ph.setOrder("desc");
            }
            balanceLog.setIsShow(true);
            DataGrid dg = mbBalanceLogService.dataGrid(balanceLog, ph);
            j.setSuccess(true);
            j.setMsg("获取钱包明细成功！");
            j.setObj(dg);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取钱包明细接口异常", e);
        }

        return j;
    }


    /**
     * 钱包余额提交转账汇款
     */
    @RequestMapping("/balanceRemit")
    @ResponseBody
    public Json balanceRemit(MbRechargeLog rechargeLog, String remitterTimeStr, HttpServletRequest request) {
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);
            if(s.getShopId() == null){
                j.setMsg("请认证");
                return  j;
            }
            MbBalance balance = mbBalanceService.addOrGetMbBalance(s.getShopId());
            rechargeLog.setBalanceId(balance.getId());
            rechargeLog.setRefType("BT003"); // 转账汇款充值
            rechargeLog.setApplyLoginId(s.getId());
            if(!F.empty(remitterTimeStr)) rechargeLog.setRemitterTime(DateUtil.parse(remitterTimeStr, Constants.DATE_FORMAT_YMDHM));
            mbRechargeLogService.add(rechargeLog);

            j.setSuccess(true);
            j.setMsg("提交余额转账成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("提交余额转账接口异常", e);
        }

        return j;
    }
}
