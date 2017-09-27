package com.mobian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 黄晓渝 on 2017/9/26.
 */
@Controller
@RequestMapping("/mbShopMapController")
public class MbShopMapController  extends BaseController {


    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbshopmap/mbShopMap";
    }
}
