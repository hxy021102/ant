package com.bx.ant.controller;

import com.bx.ant.pageModel.session.TokenWrap;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbShopServiceI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by  wanxp 2017/9/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/shop")
public class ApiShopController extends BaseController {

    @Resource
    private MbShopServiceI mbShopService;

    /**
     * 获取shopDataGrid
    * @param shop
     * @param pageHelper
     * @return
     */
    @RequestMapping("dataGrid")
    @ResponseBody
    public  Json dataGrid(MbShop shop, PageHelper pageHelper, HttpServletRequest request) {
        Json j = new Json();
        TokenWrap token = getTokenWrap(request);
        shop.setContactPhone(token.getName());
        j.setSuccess(true);
        j.setMsg("u know");
        j.setObj(mbShopService.dataGrid(shop, pageHelper));
        return j;
    }
}
