package com.camel.front.controller;

import com.mobian.controller.BaseController;
import com.mobian.listener.Application;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.MbItemCategory;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbItemCategoryServiceI;
import com.mobian.util.ConvertNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by guxin on 2017/5/3.
 *
 * 商品分类
 */
@Controller
@RequestMapping("/api/apiItemCategoryController")
public class ApiItemCategoryController extends BaseController {

    @Autowired
    private MbItemCategoryServiceI mbItemCategoryService;

    /**
     * 获取商品分类接口
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(MbItemCategory mbItemCategory, PageHelper ph) {
        Json j = new Json();
        try{
            if(ph.getRows() == 0 || ph.getRows() > 50) {
                ph.setRows(10);
            }
            DataGrid dg = mbItemCategoryService.dataGrid(mbItemCategory, ph);
            j.setSuccess(true);
            j.setMsg("获取商品分类成功！");
            j.setObj(dg);
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取商品分类接口异常", e);
        }

        return j;
    }

}
