package com.camel.front.controller;

import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.MbUserInvoiceServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guxin on 2017/5/7.
 *
 * 用户发票
 */
@Controller
@RequestMapping("/api/apiUserInvoiceController")
public class ApiUserInvoiceController extends BaseController {

    @Autowired
    private MbUserInvoiceServiceI mbUserInvoiceService;
    @Autowired
    private TokenManage tokenManage;

    /**
     * 获取发票接口
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(MbUserInvoice mbUserInvoice, PageHelper ph, HttpServletRequest request) {
        Json j = new Json();
        try{
            String uid = tokenManage.getUid(request);
            mbUserInvoice.setUserId(Integer.valueOf(uid));
            if(ph.getRows() == 0 || ph.getRows() > 150) {
                ph.setRows(10);
            }
            DataGrid dg = mbUserInvoiceService.dataGrid(mbUserInvoice, ph);
            j.setSuccess(true);
            j.setMsg("获取发票成功！");
            j.setObj(dg);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取发票接口异常", e);
        }

        return j;
    }

    /**
     * 添加发票接口
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbUserInvoice mbUserInvoice, HttpServletRequest request) {
        Json j = new Json();
        try{
            String uid = tokenManage.getUid(request);
            mbUserInvoice.setUserId(Integer.parseInt(uid));
            mbUserInvoiceService.add(mbUserInvoice);
            j.setSuccess(true);
            j.setMsg("添加发票成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("添加发票接口异常", e);
        }

        return j;
    }

    /**
     * 修改发票接口
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbUserInvoice mbUserInvoice) {
        Json j = new Json();
        try{
            if(F.empty(mbUserInvoice.getId())) {
                j.setMsg("发票id不能为空！");
                return j;
            }
            mbUserInvoiceService.edit(mbUserInvoice);
            j.setSuccess(true);
            j.setMsg("修改发票成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("修改发票接口异常", e);
        }

        return j;
    }

    /**
     * 删除发票接口
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(MbUserInvoice mbUserInvoice) {
        Json j = new Json();
        try{
            if(F.empty(mbUserInvoice.getId())) {
                j.setMsg("发票id不能为空！");
                return j;
            }
            mbUserInvoiceService.delete(mbUserInvoice.getId());
            j.setSuccess(true);
            j.setMsg("删除发票成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("删除发票接口异常", e);
        }

        return j;
    }

}
