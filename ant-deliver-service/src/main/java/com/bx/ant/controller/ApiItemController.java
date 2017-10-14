package com.bx.ant.controller;

import com.bx.ant.pageModel.ShopItem;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.ShopItemServiceI;
import com.bx.ant.service.session.TokenServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 黄晓渝 on 2017/9/30.
 * 商品管理相关接口
 */
@Controller
@RequestMapping("/api/deliver/item")
public class ApiItemController extends BaseController {

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
    public Json getAllItemList(HttpServletRequest request,MbItem mbItem, PageHelper ph) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if(!F.empty(token.getShopId())) {
            DataGrid dataGridItem = shopItemService.dataGridWithQuantity(mbItem, ph, token.getShopId());
            j.setObj(dataGridItem);
            j.setSuccess(true);
            j.setMsg("获取成功！");
        }else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
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
    public Json getShopItemOnline(HttpServletRequest request, ShopItem shopItem, PageHelper ph) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if(!F.empty(token.getShopId())) {
            shopItem.setShopId(token.getShopId());
            shopItem.setOnline(true);
            DataGrid dataGridShopItem = shopItemService.dataGridWithItemName(shopItem, ph);
            j.setObj(dataGridShopItem);
            j.setSuccess(true);
            j.setMsg("获取成功！");
        } else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
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
    public Json getShopItemOffline(HttpServletRequest request, ShopItem shopItem, PageHelper ph) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if(!F.empty(token.getShopId())) {
            shopItem.setShopId(token.getShopId());
            shopItem.setOnline(false);
            DataGrid dataGridShopItem = shopItemService.dataGridWithItemName(shopItem, ph);
            j.setObj(dataGridShopItem);
            j.setSuccess(true);
            j.setMsg("获取成功！");
        } else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
        return j;
    }

    /**
     * 批量修改门店商品上架
     *
     * @param itemIds
     * @return
     */
    @RequestMapping("/updateBatchItemOnline")
    @ResponseBody
    public Json updateBatchItemOnline(HttpServletRequest request, String itemIds) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if(!F.empty(token.getShopId())) {
            shopItemService.updateBatchItemOnline(itemIds, token.getShopId());
            j.setSuccess(true);
            j.setMsg("批量门店商品上架成功！！");
        } else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
        return j;
    }

    /**
     * 修改门店某种商品状态为上架
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/updateItemOnline")
    @ResponseBody
    public Json updateItemOnline(HttpServletRequest request, Integer itemId) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if(!F.empty(token.getShopId())) {
            shopItemService.updateItemOnline(itemId, token.getShopId());
            j.setSuccess(true);
            j.setMsg("上架成功！！");
        } else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
        return j;
    }

    /**
     * 批量修改门店商品下架
     *
     * @param itemIds
     * @return
     */
    @RequestMapping("/updateBatchShopItemOffline")
    @ResponseBody
    public Json updateBatchShopItemOffline(HttpServletRequest request, String itemIds) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if(!F.empty(token.getShopId())) {
            shopItemService.updateBatchShopItemOffline(itemIds, token.getShopId());
            j.setSuccess(true);
            j.setMsg("批量下架成功！！");
        } else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
        return j;
    }

    /**
     * 修改某种商品下架
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/updateItemOffline")
    @ResponseBody
    public Json updateShopItemOffline(HttpServletRequest request, Integer itemId) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if(!F.empty(token.getShopId())) {
            shopItemService.updateShopItemOffline(itemId,token.getShopId());
            j.setSuccess(true);
            j.setMsg("下架成功！！");
        }else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
        return j;
    }

    /**
     * 批量删除门店商品
     *
     * @param itemIds
     * @return
     */
    @RequestMapping("/deleteBatchShopItem")
    @ResponseBody
    public Json deleteBatchShopItem(HttpServletRequest request, String itemIds) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if (!F.empty(token.getShopId())) {
            shopItemService.deleteBatchShopItem(itemIds, token.getShopId());
            j.setSuccess(true);
            j.setMsg("批量删除成功！！");
        }else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
        return j;
    }

    /**
     * 删除门店某种商品
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/deleteShopItem")
    @ResponseBody
    public Json deleteShopItem(HttpServletRequest request, Integer itemId) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if(!F.empty(token.getShopId())) {
            shopItemService.deleteShopItem(itemId,token.getShopId());
            j.setSuccess(true);
            j.setMsg("删除成功！！");
        }else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
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
        if(!F.empty(token.getShopId())) {
            ShopItem shopItem = shopItemService.getByShopIdAndItemId(token.getShopId(),itemId);
            j.setObj(shopItem);
            j.setSuccess(true);
        }else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
        return j;
    }

    /**
     * 修改门店商品库存量
     *
     * @param request
     * @param itemId
     * @return
     */
    @RequestMapping("/updateShopItemQuantity")
    @ResponseBody
    public Json updateShopItemQuantity(HttpServletRequest request, Integer itemId, Integer quantity) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        if(!F.empty(token.getShopId())) {
            shopItemService.updateShopItemQuantity(itemId,token.getShopId(), quantity);
            j.setSuccess(true);
            j.setMsg("修改信息成功");
        }else {
            j.setSuccess(false);
            j.setMsg("获取门店信息失败！");
        }
        return j;
    }
}
