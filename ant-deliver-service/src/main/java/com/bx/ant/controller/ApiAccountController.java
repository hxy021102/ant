package com.bx.ant.controller;

import com.mobian.pageModel.Json;
import com.mobian.service.MbShopServiceI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by guxin on 2017/4/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/accountController")
public class ApiAccountController extends BaseController {

    @Resource
    private MbShopServiceI mbShopService;

    @RequestMapping("/test")
    public Json getShop(){
        Json json = new Json();
        json.setObj(mbShopService.getFromCache(35));
        return json;
    }

}
