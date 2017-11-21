package com.bx.ant.controller;

import com.mobian.pageModel.Json;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by guxin on 2017/4/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/apiCommon")
public class ApiCommonController extends BaseController {

    /**
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/error")
    public Json error() {
        Json j = new Json();
        j.setObj("token_expire");
        j.setSuccess(false);
        j.setMsg("token过期，请重新登录！");
        return j;
    }

}
