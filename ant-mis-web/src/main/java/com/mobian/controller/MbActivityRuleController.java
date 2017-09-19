package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbActivityActionServiceI;
import com.mobian.service.MbActivityRuleServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * MbActivityRule管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbActivityRuleController")
public class MbActivityRuleController extends BaseController {

    @Autowired
    private MbActivityRuleServiceI mbActivityRuleService;
    @Autowired
    private MbActivityActionServiceI mbActivityActionService;


    /**
     * 跳转到MbActivityRule管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbactivityrule/mbActivityRule";
    }

    /**
     * 获取MbActivityRule数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbActivityRule mbActivityRule, PageHelper ph) {
        return mbActivityRuleService.dataGrid(mbActivityRule, ph);
    }

    /**
     * 获取MbActivityRule数据表格excel
     *
     * @param
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(MbActivityRule mbActivityRule, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbActivityRule, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbActivityRule页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        MbActivityRule mbActivityRule = new MbActivityRule();
        return "/mbactivityrule/mbActivityRuleAdd";
    }

    /**
     * 添加MbActivityRule
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbActivityRule mbActivityRule) {
        Json j = new Json();
        mbActivityRuleService.add(mbActivityRule);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到MbActivityRule查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbActivityRule mbActivityRule = mbActivityRuleService.get(id);
        request.setAttribute("mbActivityRule", mbActivityRule);
        return "/mbactivityrule/mbActivityRuleView";
    }

    /**
     * 跳转到MbActivityRule修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbActivityRule mbActivityRule = mbActivityRuleService.get(id);
        request.setAttribute("mbActivityRule", mbActivityRule);
        return "/mbactivityrule/mbActivityRuleEdit";
    }

    /**
     * 修改MbActivityRule
     *
     * @param mbActivityRule
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbActivityRule mbActivityRule) {
        Json j = new Json();
        mbActivityRuleService.edit(mbActivityRule);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbActivityRule
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbActivityRuleService.deleteRule(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

}
