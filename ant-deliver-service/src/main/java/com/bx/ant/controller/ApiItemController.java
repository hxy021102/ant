package com.bx.ant.controller;

import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.ShopItemServiceI;
import com.bx.ant.service.session.TokenServiceI;
import com.mobian.pageModel.*;
import com.mobian.pageModel.ShopItem;
import com.mobian.service.MbItemServiceI;
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
    public Json getAllItemList(MbItem mbItem, PageHelper ph) {
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
    public Json getShopItemOnline(HttpServletRequest request, ShopItem shopItem, PageHelper ph) {
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
    public Json getShopItemOffline(HttpServletRequest request, ShopItem shopItem, PageHelper ph) {
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
     * 批量商品上架(大仓商品)
     *
     * @param itemIds
     * @return
     */
    @RequestMapping("/addBatchItemOnline")
    @ResponseBody
    public Json addBatchItemOnline(HttpServletRequest request, String itemIds) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItemService.addBatchItemOnline(itemIds, token.getShopId());
        j.setSuccess(true);
        j.setMsg("门店新增商品批量上架成功！！");
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
        shopItemService.updateBatchItemOnline(itemIds, token.getShopId());
        j.setSuccess(true);
        j.setMsg("批量门店商品上架成功！！");
        return j;
    }

    /**
     * 某种商品上架(大仓商品)
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/addItemOnline")
    @ResponseBody
    public Json addItemOnline(HttpServletRequest request, Integer itemId) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItemService.addItemOnline(itemId, token.getShopId());
        j.setSuccess(true);
        j.setMsg("上架成功！！");
        return j;
    }

    /**
     * 修改门店某种商品状态为上架
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
    @RequestMapping("/updateBatchShopItemOffline")
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
     * @param itemIds
     * @return
     */
    @RequestMapping("/deleteBatchShopItem")
    @ResponseBody
    public Json deleteBatchShopItem(HttpServletRequest request, String itemIds) {
        Json j = new Json();
        TokenWrap token = tokenService.getToken(request);
        shopItemService.deleteBatchShopItem(itemIds, token.getShopId());
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
        shopItemService.delete(shopItemId);
        j.setSuccess(true);
        j.setMsg("删除成功！！");
        return j;
    }

    /**
     * 获取门店商品库存量
     *
     * @param request
     * @param shopItemId
     * @return
     */
    @RequestMapping("/getShopItemQuantity")
    @ResponseBody
    public Json getShopItemQuantity(HttpServletRequest request, Integer shopItemId) {
        Json j = new Json();
        ShopItem shopItem = shopItemService.get(shopItemId);
        j.setObj(shopItem);
        j.setSuccess(true);
        return j;
    }

    /**
     * 修改门店商品库存量
     *
     * @param request
     * @param shopItemId
     * @return
     */
    @RequestMapping("/updateShopItemQuantity")
    @ResponseBody
    public Json updateShopItemQuantity(HttpServletRequest request, Integer shopItemId, Integer quantity) {
        Json j = new Json();
        shopItemService.updateShopItemQuantity(shopItemId, quantity);
        j.setSuccess(true);
        return j;
    }
}
