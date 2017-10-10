package com.bx.ant.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.model.TopicMessage;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.ShopDeliverAccountServiceI;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.bx.ant.service.ShopItemServiceI;
import com.bx.ant.service.session.TokenServiceI;
import com.mobian.absx.F;
import com.mobian.absx.UUID;
import com.mobian.pageModel.*;
import com.mobian.pageModel.ShopDeliverAccount;
import com.mobian.pageModel.ShopDeliverApply;
import com.mobian.pageModel.ShopItem;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.thirdpart.mns.MNSTemplate;
import com.mobian.thirdpart.mns.MNSUtil;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.org.mozilla.javascript.internal.Token;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by 黄晓渝 on 2017/9/30.
 * 商品管理相关接口
 */
@Controller
@RequestMapping("/api/deliver/item")
public class ApiItemController extends BaseController {

    @Resource
    private MbItemServiceI mbItemService;
    @Resource
    private ShopItemServiceI shopItemService;
    @Resource
    private TokenServiceI tokenService;

    /**
     * 获取所有商品信息
     *
     * @param mbItem
     * @return
     */
    @RequestMapping("/getAllItemList")
    @ResponseBody
   public Json getAllItemList(MbItem mbItem,PageHelper ph) {
        Json j = new Json();
        DataGrid dataGridItem = mbItemService.dataGrid(mbItem, ph);
        j.setObj(dataGridItem);
        j.setSuccess(true);
        j.setMsg("获取成功！");
        return j;
    }

    /**
     * 获取门店上架商品
     *
     * @param request
     * @param shopItem
     * @param ph
     * @return
     */
    @RequestMapping("/getShopItemOnline")
    @ResponseBody
    public Json getShopItemOnline(HttpServletRequest request,ShopItem shopItem, PageHelper ph) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItem.setShopId(token.getShopId());
        shopItem.setOnline(true);
        DataGrid dataGridShopItem = shopItemService.dataGridWithItemName(shopItem, ph);
        j.setObj(dataGridShopItem);
        j.setSuccess(true);
        j.setMsg("获取成功！");
        return j;
    }

    /**
     * 获取门店下架商品
     *
     * @param shopItem
     * @param ph
     * @return
     */
    @RequestMapping("/getShopItemOffline")
    @ResponseBody
    public Json getShopItemOffline(HttpServletRequest request,ShopItem shopItem, PageHelper ph) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItem.setShopId(token.getShopId());
        shopItem.setOnline(false);
        DataGrid dataGridShopItem = shopItemService.dataGridWithItemName(shopItem, ph);
        j.setObj(dataGridShopItem);
        j.setSuccess(true);
        j.setMsg("获取成功！");
        return j;
    }

    /**
     * 批量修改商品上架
     *
     * @param itemIds
     * @return
     */
    @RequestMapping(value = "/updateBatchItemOnline", method = RequestMethod.POST)
    @ResponseBody
    public Json updateBatchItemOnline(HttpServletRequest request, String itemIds) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItemService.updateBatchItemOnline(itemIds, token.getShopId());
        j.setSuccess(true);
        j.setMsg("批量上架成功！！");
        return j;
    }

    /**
     * 某种商品上架
     *
     * @param shopItemId
     * @return
     */
    @RequestMapping("/updateItemOnline")
    @ResponseBody
    public Json updateItemOnline(HttpServletRequest request, Integer shopItemId) {
        Json j = new Json();
        shopItemService.updateItemOnline(shopItemId);
        j.setSuccess(true);
        j.setMsg("上架成功！！");
        return j;
    }

    /**
     * 批量修改门店商品下架
     *
     * @param itemIds
     * @return
     */
    @RequestMapping(value = "/updateBatchShopItemOffline", method = RequestMethod.POST)
    @ResponseBody
    public Json updateBatchShopItemOffline(HttpServletRequest request, String itemIds) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItemService.updateBatchShopItemOffline(itemIds, token.getShopId());
        j.setSuccess(true);
        j.setMsg("批量下架成功！！");
        return j;
    }

    /**
     * 修改某种商品下架
     *
     * @param shopItemId
     * @return
     */
    @RequestMapping("/updateItemOffline")
    @ResponseBody
    public Json updateShopItemOffline(HttpServletRequest request, Integer shopItemId) {
        Json j = new Json();
        shopItemService.updateShopItemOffline(shopItemId);
        j.setSuccess(true);
        j.setMsg("下架成功！！");
        return j;
    }

    /**
     * 批量删除门店商品
     *
     * @param itemList
     * @return
     */
    @RequestMapping(value = "/deleteBatchShopItem", method = RequestMethod.POST)
    @ResponseBody
    public Json deleteBatchShopItem(HttpServletRequest request, String itemList) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItemService.deleteBatchShopItem(itemList, token.getShopId());
        j.setSuccess(true);
        j.setMsg("批量删除成功！！");
        return j;
    }

    /**
     * 删除门店某种商品
     *
     * @param shopItemId
     * @return
     */
    @RequestMapping("/deleteShopItem")
    @ResponseBody
    public Json deleteShopItem(HttpServletRequest request, Integer shopItemId) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItemService.delete(shopItemId);
        j.setSuccess(true);
        j.setMsg("删除成功！！");
        return j;
    }
    /**
     * 获取门店商品库存量
     *
     * @param request
     * @param itemId
     * @return
     */
    @RequestMapping("/getShopItemQuantity")
    @ResponseBody
    public Json getShopItemQuantity(HttpServletRequest request, Integer itemId) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        ShopItem shopItem=shopItemService.getByShopIdAndItemId(token.getShopId(),itemId);
        j.setObj(shopItem);
        j.setSuccess(true);
        return j;
    }

    /**
     * 修改门店商品库存量
     *
     * @param request
     * @param itemId
     * @return
     */
    @RequestMapping("/getShopItemQuantity")
    @ResponseBody
    public Json updateShopItemQuantity(HttpServletRequest request, Integer itemId,Integer quantity) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItemService.updateShopItemQuantity(token.getShopId(),itemId,quantity);
        j.setSuccess(true);
        return j;
    }
}
