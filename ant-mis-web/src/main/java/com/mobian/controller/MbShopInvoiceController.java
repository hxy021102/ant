package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbShopInvoiceServiceI;
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
 * MbShopInvoice管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbShopInvoiceController")
public class MbShopInvoiceController extends BaseController {

    @Autowired
    private MbShopInvoiceServiceI mbShopInvoiceService;


    /**
     * 跳转到MbShopInvoice管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbshopinvoice/mbShopInvoice";
    }

    /**
     * 获取MbShopInvoice数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbShopInvoice mbShopInvoice, PageHelper ph) {
        return mbShopInvoiceService.dataGrid(mbShopInvoice, ph);
    }

    /**
     * 获取MbShopInvoice数据表格excel
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
    public void download(MbShopInvoice mbShopInvoice, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbShopInvoice, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbShopInvoice页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, Integer shopId) {
        MbShopInvoice mbShopInvoice = new MbShopInvoice();
        mbShopInvoice.setShopId(shopId);
        request.setAttribute("mbShopInvoice", mbShopInvoice);
        return "/mbshopinvoice/mbShopInvoiceAdd";
    }

    /**
     * 添加MbShopInvoice
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbShopInvoice mbShopInvoice) {
        Json j = new Json();
        mbShopInvoiceService.add(mbShopInvoice);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到MbShopInvoice查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbShopInvoice mbShopInvoice = mbShopInvoiceService.get(id);
        request.setAttribute("mbShopInvoice", mbShopInvoice);
        return "/mbshopinvoice/mbShopInvoiceView";
    }

    /**
     * 下拉动态选择开票模板
     */
    @RequestMapping("/queryShopInvoice")
    @ResponseBody
    public Json queryShopInvoice(HttpServletRequest request, Integer id) {
        Json j = new Json();
        MbShopInvoice mbShopInvoice = mbShopInvoiceService.get(id);
        j.setObj(mbShopInvoice);
        j.setSuccess(true);
        return j;
    }

    /**
     * 跳转到MbShopInvoice修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbShopInvoice mbShopInvoice = mbShopInvoiceService.get(id);
        request.setAttribute("mbShopInvoice", mbShopInvoice);
        return "/mbshopinvoice/mbShopInvoiceEdit";
    }

    /**
     * 修改MbShopInvoice
     *
     * @param mbShopInvoice
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbShopInvoice mbShopInvoice) {
        Json j = new Json();
        mbShopInvoiceService.edit(mbShopInvoice);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbShopInvoice
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbShopInvoiceService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/setInvoiceDefault")
    @ResponseBody
    public Json setInvoiceDefault(Integer id, Integer shopId) {
        Json j = new Json();
        MbShopInvoice mbShopInvoice = new MbShopInvoice();
        mbShopInvoice.setShopId(shopId);
        List<MbShopInvoice> list = mbShopInvoiceService.query(mbShopInvoice);
        for (MbShopInvoice m : list) {
            if (m.getId() == id) {
                m.setInvoiceDefault(true);
                mbShopInvoiceService.edit(m);
            } else {
                m.setInvoiceDefault(false);
                mbShopInvoiceService.edit(m);
            }
        }
        j.setMsg("设置成功！");
        j.setSuccess(true);
        return j;
    }
}
