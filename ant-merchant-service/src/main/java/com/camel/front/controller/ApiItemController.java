package com.camel.front.controller;

import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.listener.Application;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.MbItem;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbItemServiceI;
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
 * 商品接口
 */
@Controller
@RequestMapping("/api/apiItemController")
public class ApiItemController extends BaseController {

    @Autowired
    private MbItemServiceI mbItemService;
    @Autowired
    private TokenManage tokenManage;
    @Autowired
    private RedisUserServiceImpl redisUserService;

    /**
     * 获取商品列表接口
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(MbItem mbItem, PageHelper ph, Integer shopId, HttpServletRequest request) {
        Json j = new Json();
        try{
            if(ph.getRows() == 0 || ph.getRows() > 50) {
                ph.setRows(10);
            }
            if(F.empty(ph.getSort())) {
                ph.setSort("seq");
                ph.setOrder("asc");
            }
            //库存必须大于0
            mbItem.setQuantity(0);
            mbItem.setIsShelves(true);
            DataGrid dg = mbItemService.dataGrid(mbItem, ph);
            String uid = tokenManage.getUid(request);
            if(!F.empty(uid)) {
                List<MbItem> ol = dg.getRows();
                if(ol != null && ol.size() > 0) {
                    if(F.empty(shopId)) shopId = tokenManage.getShopId(request);
                    for (MbItem o : ol) {
                        Integer contractPrice = redisUserService.getContractPrice(shopId, o.getId());
                        o.setContractPrice(contractPrice);
                    }
                }
            }
            j.setSuccess(true);
            j.setMsg("获取商品列表成功！");
            j.setObj(dg);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取商品列表接口异常", e);
        }

        return j;
    }

    /**
     * 获取商品列表接口
     */
    @RequestMapping("/getList")
    @ResponseBody
    public Json getList(MbItem mbItem, HttpServletRequest request) {
        Json j = new Json();
        try{
            //库存必须大于0
            mbItem.setQuantity(0);
            mbItem.setIsShelves(true);
            List<MbItem> items = mbItemService.query(mbItem);
//            String uid = tokenManage.getUid(request);
//            if(!F.empty(uid)) {
//                if(items != null && items.size() > 0) {
//                    Integer shopId = tokenManage.getShopId(request);
//                    for (MbItem o : items) {
//                        Integer contractPrice = redisUserService.getContractPrice(shopId, o.getId());
//                        o.setContractPrice(contractPrice);
//                    }
//                }
//            }
            j.setSuccess(true);
            j.setMsg("获取商品列表成功！");
            j.setObj(items);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取商品列表接口异常", e);
        }

        return j;
    }

    /**
     * 获取商品详情接口
     */
    @RequestMapping("/get")
    @ResponseBody
    public Json get(MbItem mbItem, Integer shopId, HttpServletRequest request) {
        Json j = new Json();
        try{
            Integer id = mbItem.getId();
            if(F.empty(id)) {
                j.setMsg("商品id不能为空！");
                return j;
            }
            MbItem o = mbItemService.get(id);
            String uid = tokenManage.getUid(request);
            if(!F.empty(uid)) {
                if(F.empty(shopId)) shopId = tokenManage.getShopId(request);
                Integer contractPrice = redisUserService.getContractPrice(shopId, o.getId());
                o.setContractPrice(contractPrice);
            }
            j.setSuccess(true);
            j.setMsg("取商品详情成功！");
            j.setObj(o);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("取商品详情接口异常", e);
        }

        return j;
    }

}
