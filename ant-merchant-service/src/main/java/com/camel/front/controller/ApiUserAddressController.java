package com.camel.front.controller;

import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.MbUserAddressServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guxin on 2017/5/7.
 *
 * 用户地址
 */
@Controller
@RequestMapping("/api/apiUserAddressController")
public class ApiUserAddressController extends BaseController {

    @Autowired
    private MbUserAddressServiceI mbUserAddressService;
    @Autowired
    private TokenManage tokenManage;

    /**
     * 获取收货地址接口
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(MbUserAddress mbUserAddress, PageHelper ph, HttpServletRequest request) {
        Json j = new Json();
        try{
            String uid = tokenManage.getUid(request);
            mbUserAddress.setUserId(Integer.valueOf(uid));
            if(ph.getRows() == 0 || ph.getRows() > 50) {
                ph.setRows(10);
            }
            if(F.empty(ph.getSort())) {
                ph.setSort("defaultAddress");
            }
            if(F.empty(ph.getOrder())) {
                ph.setOrder("desc");
            }
            DataGrid dg = mbUserAddressService.dataGrid(mbUserAddress, ph);
            j.setSuccess(true);
            j.setMsg("获取收货地址成功！");
            j.setObj(dg);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取收货地址接口异常", e);
        }

        return j;
    }

    /**
     * 添加收货地址接口
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbUserAddress mbUserAddress, HttpServletRequest request) {
        Json j = new Json();
        try{
            String uid = tokenManage.getUid(request);
            mbUserAddress.setUserId(Integer.parseInt(uid));
            // 查询地址信息是否存在
            MbUserAddress exist = mbUserAddressService.get(mbUserAddress);
            if(exist == null) {
                mbUserAddressService.add(mbUserAddress);
                // 设置默认地址
                mbUserAddressService.setDefaultAddress(mbUserAddress);
            }

            j.setSuccess(true);
            j.setMsg("添加收货地址成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("添加收货地址接口异常", e);
        }

        return j;
    }

    /**
     * 修改收货地址接口
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbUserAddress mbUserAddress) {
        Json j = new Json();
        try{
            if(F.empty(mbUserAddress.getId())) {
                j.setMsg("收货地址id不能为空！");
                return j;
            }
            mbUserAddressService.edit(mbUserAddress);
            j.setSuccess(true);
            j.setMsg("修改收货地址成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("修改收货地址接口异常", e);
        }

        return j;
    }

    /**
     * 删除收货地址接口
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(MbUserAddress mbUserAddress) {
        Json j = new Json();
        try{
            if(F.empty(mbUserAddress.getId())) {
                j.setMsg("收货地址id不能为空！");
                return j;
            }
            mbUserAddressService.delete(mbUserAddress.getId());
            j.setSuccess(true);
            j.setMsg("删除收货地址成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("删除收货地址接口异常", e);
        }

        return j;
    }

    /**
     * 获取默认收货地址接口
     */
    @RequestMapping("/getDefaultAddress")
    @ResponseBody
    public Json getDefaultAddress(HttpServletRequest request) {
        Json j = new Json();
        try{
            String uid = tokenManage.getUid(request);
            MbUserAddress o = mbUserAddressService.getDefaultAddress(Integer.parseInt(uid));
            j.setSuccess(true);
            j.setMsg("获取默认收货地址成功！");
            j.setObj(o);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取默认收货地址接口异常", e);
        }

        return j;
    }

    /**
     * 设为默认收货地址接口
     */
    @RequestMapping("/setDefaultAddress")
    @ResponseBody
    public Json setDefaultAddress(MbUserAddress mbUserAddress, HttpServletRequest request) {
        Json j = new Json();
        try{
            if(F.empty(mbUserAddress.getId())) {
                j.setMsg("收货地址id不能为空！");
                return j;
            }
            String uid = tokenManage.getUid(request);
            mbUserAddress.setUserId(Integer.parseInt(uid));
            mbUserAddressService.setDefaultAddress(mbUserAddress);
            j.setSuccess(true);
            j.setMsg("设为默认收货地址成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("设为默认收货地址接口异常", e);
        }

        return j;
    }

}
