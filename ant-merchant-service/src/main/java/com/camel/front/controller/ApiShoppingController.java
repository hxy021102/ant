package com.camel.front.controller;

import com.mobian.absx.F;
import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.Task;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbShoppingServiceI;
import com.mobian.service.impl.CompletionFactory;
import com.mobian.service.impl.RedisUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by guxin on 2017/5/3.
 *
 * 购物车
 */
@Controller
@RequestMapping("/api/apiShoppingController")
public class ApiShoppingController extends BaseController {

    @Autowired
    private MbShoppingServiceI mbShoppingService;
    @Autowired
    private MbItemServiceI mbItemService;
    @Autowired
    private RedisUserServiceImpl redisUserService;
    @Autowired
    private TokenManage tokenManage;

    /**
     * 获取购物车列表接口
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(MbShopping mbShopping, PageHelper ph, Integer shopId, HttpServletRequest request) {
        Json j = new Json();
        try{
            String uid = tokenManage.getUid(request);
            mbShopping.setUserId(Integer.valueOf(uid));
            if(F.empty(shopId)) shopId = tokenManage.getShopId(request);
            final Integer fShopId = shopId;
            if(ph.getRows() == 0 || ph.getRows() > 50) {
                ph.setRows(10);
            }
            DataGrid dg = mbShoppingService.dataGrid(mbShopping, ph);
            List<MbShopping> ol = dg.getRows();
            if(ol != null && ol.size() > 0) {
                final CompletionService completionService = CompletionFactory.initCompletion();
                for (MbShopping ms : ol) {
                    //查询商品详情和合同价
                    completionService.submit(new Task<MbShopping, MbItem>(ms) {
                        @Override
                        public MbItem call() throws Exception {
                            MbItem o = mbItemService.get(getD().getItemId());
                            Integer contractPrice = redisUserService.getContractPrice(fShopId, getD().getItemId());
                            o.setContractPrice(contractPrice);
                            return o;
                        }

                        protected void set(MbShopping d, MbItem mbItem) {
                            d.setMbItem(mbItem);
                        }
                    });
                }
                completionService.sync();
            }
            j.setSuccess(true);
            j.setMsg("获取购物车列表成功！");
            j.setObj(dg);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取购物车列表接口异常", e);
        }

        return j;
    }

    /**
     * 添加购物车商品接口
     *
     * 若购物车已存在此商品(不管是否处于删除状态)，则直接增加购买数量，并激活
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbShopping mbShopping, HttpServletRequest request) {
        Json j = new Json();
        try{
            String uid = tokenManage.getUid(request);
            mbShopping.setUserId(Integer.parseInt(uid));
            if(F.empty(mbShopping.getItemId()) || F.empty(mbShopping.getQuantity()) || mbShopping.getQuantity() < 1) {
                j.setMsg("参数错误！");
                return j;
            }
            MbShopping o = mbShoppingService.get(mbShopping);
            if(o != null) {
                Integer quantity = o.getQuantity() + mbShopping.getQuantity();
                o.setIsdeleted(false);
                o.setQuantity(quantity);
                mbShoppingService.editMbShopping(o);
                j.setSuccess(true);
                j.setMsg("添加购物车商品成功！");
                return j;
            }
            mbShoppingService.add(mbShopping);
            j.setSuccess(true);
            j.setMsg("添加购物车商品成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("添加购物车商品接口异常", e);
        }

        return j;
    }

    /**
     * 修改购物车商品数量接口
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbShopping mbShopping) {
        Json j = new Json();
        try{
            if(F.empty(mbShopping.getId()) || F.empty(mbShopping.getQuantity()) || mbShopping.getQuantity() < 1) {
                j.setMsg("参数错误！");
                return j;
            }
            mbShopping.setItemId(null);
            mbShopping.setUserId(null);
            mbShoppingService.editMbShopping(mbShopping);
            j.setSuccess(true);
            j.setMsg("修改购物车商品数量成功！");
            return j;
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("修改购物车商品数量接口异常", e);
        }

        return j;
    }

    /**
     * 删除购物车商品接口
     *
     * 变更状态，数量清0
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        try{
            if(F.empty(id)) {
                j.setMsg("购物车id不能为空！");
                return j;
            }
            mbShoppingService.deleteMbShopping(id);
            j.setSuccess(true);
            j.setMsg("删除购物车商品成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("删除购物车商品接口异常", e);
        }

        return j;
    }

    /**
     * 批量删除购物车商品接口
     *
     * 变更状态，数量清0
     */
    @RequestMapping("/batchDelete")
    @ResponseBody
    public Json batchDelete(String ids) {
        Json j = new Json();
        try{
            if(F.empty(ids)) {
                j.setMsg("购物车id不能为空！");
                return j;
            }
            for (String id : ids.split(",")) {
                if (!F.empty(id)) {
                    this.delete(Integer.valueOf(id));
                }
            }
            j.setSuccess(true);
            j.setMsg("批量删除购物车商品成功！");
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("批量删除购物车商品接口异常", e);
        }

        return j;
    }

    /**
     * 获取购物车商品数量接口
     */
    @RequestMapping("/count")
    @ResponseBody
    public Json count(HttpServletRequest request) {
        Json j = new Json();
        try{
            String uid = tokenManage.getUid(request);
            Long num = mbShoppingService.count(Integer.parseInt(uid));
            j.setSuccess(true);
            j.setMsg("获取购物车商品数量成功！");
            j.setObj(num);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取购物车商品数量接口异常", e);
        }

        return j;
    }

}
