package com.bx.ant.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mobian.pageModel.Json;
import com.mobian.service.MbShopServiceI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by guxin on 2017/4/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/accountController")
public class ApiAccountController extends BaseController {

    @Reference
    private MbShopServiceI mbShopService;

    @RequestMapping("/test")
    public Json getShop(){
        Json json = new Json();
        json.setObj(mbShopService.getFromCache(1363));
        return json;
    }

}
