package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbSupplierContractItemServiceI;
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
 * MbSupplierContractItem管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbSupplierContractItemController")
public class MbSupplierContractItemController extends BaseController {

    @Autowired
    private MbSupplierContractItemServiceI mbSupplierContractItemService;


    /**
     * 跳转到MbSupplierContractItem管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbsuppliercontractitem/mbSupplierContractItem";
    }

    /**
     * 获取MbSupplierContractItem数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbSupplierContractItem mbSupplierContractItem, PageHelper ph) {
        return mbSupplierContractItemService.dataGrid(mbSupplierContractItem, ph);
    }

    /**
     * 获取MbSupplierContractItem数据表格excel
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
    public void download(MbSupplierContractItem mbSupplierContractItem, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbSupplierContractItem, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbSupplierContractItem页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, Integer supplierContractId) {
        request.setAttribute("supplierContractId", supplierContractId);
        return "/mbsuppliercontractitem/mbSupplierContractItemAdd";
    }

    /**
     * 添加MbSupplierContractItem
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbSupplierContractItem mbSupplierContractItem) {
        Json j = new Json();
        mbSupplierContractItemService.add(mbSupplierContractItem);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到MbSupplierContractItem查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbSupplierContractItem mbSupplierContractItem = mbSupplierContractItemService.get(id);
        request.setAttribute("mbSupplierContractItem", mbSupplierContractItem);
        return "/mbsuppliercontractitem/mbSupplierContractItemView";
    }

    /**
     * 跳转到MbSupplierContractItem修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbSupplierContractItem mbSupplierContractItem = mbSupplierContractItemService.get(id);
        request.setAttribute("mbSupplierContractItem", mbSupplierContractItem);
        return "/mbsuppliercontractitem/mbSupplierContractItemEdit";
    }

    /**
     * 修改MbSupplierContractItem的信息
     *
     * @param mbSupplierContractItem
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbSupplierContractItem mbSupplierContractItem) {
        Json j = new Json();
        if (mbSupplierContractItem != null) {
            mbSupplierContractItemService.editAndHistory(mbSupplierContractItem);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
        } else {
            j.setSuccess(false);
            j.setMsg("编辑失败！");
        }
        return j;
    }

    /**
     * 删除MbSupplierContractItem
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbSupplierContractItemService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

}
