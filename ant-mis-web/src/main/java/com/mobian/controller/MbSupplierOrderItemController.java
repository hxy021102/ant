package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbSupplierOrderItemServiceI;
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
 * MbSupplierOrderItem管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbSupplierOrderItemController")
public class MbSupplierOrderItemController extends BaseController {

    @Autowired
    private MbSupplierOrderItemServiceI mbSupplierOrderItemService;


    /**
     * 跳转到MbSupplierOrderItem管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbsupplierorderitem/mbSupplierOrderItem";
    }

    /**
     * 获取MbSupplierOrderItem数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbSupplierOrderItem mbSupplierOrderItem, PageHelper ph) {
        return mbSupplierOrderItemService.dataGridSupplierOrderItemWithStockQuantity(mbSupplierOrderItem,ph);
    }

    @RequestMapping("/mbSupplierOrderItem")
    @ResponseBody
    public DataGrid getMbSupplierOrderItem(Integer id) {
        DataGrid dg = new DataGrid();
        MbSupplierOrderItem item = new MbSupplierOrderItem();
        item.setSupplierOrderId(id);
        List<MbSupplierOrderItem> list = mbSupplierOrderItemService.query(item);
        dg.setRows(list);
        return dg;
    }

    /**
     * 获取MbSupplierOrderItem数据表格excel
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
    public void download(MbSupplierOrderItem mbSupplierOrderItem, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbSupplierOrderItem, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbSupplierOrderItem页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, Integer supplierOrderId) {
        //MbSupplierOrderItem mbSupplierOrderItem = new MbSupplierOrderItem();
        request.setAttribute("supplierOrderId", supplierOrderId);
        return "/mbsupplierorderitem/mbSupplierOrderItemAdd";
    }

    /**
     * 添加MbSupplierOrderItem
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbSupplierOrderItem mbSupplierOrderItem) {
        Json j = new Json();
        mbSupplierOrderItemService.add(mbSupplierOrderItem);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到MbSupplierOrderItem查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbSupplierOrderItem mbSupplierOrderItem = mbSupplierOrderItemService.get(id);
        request.setAttribute("mbSupplierOrderItem", mbSupplierOrderItem);
        return "/mbsupplierorderitem/mbSupplierOrderItemView";
    }

    /**
     * 跳转到MbSupplierOrderItem修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbSupplierOrderItem mbSupplierOrderItem = mbSupplierOrderItemService.get(id);
        request.setAttribute("mbSupplierOrderItem", mbSupplierOrderItem);
        return "/mbsupplierorderitem/mbSupplierOrderItemEdit";
    }

    /**
     * 修改MbSupplierOrderItem
     *
     * @param mbSupplierOrderItem
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbSupplierOrderItem mbSupplierOrderItem) {
        Json j = new Json();
        mbSupplierOrderItemService.edit(mbSupplierOrderItem);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbSupplierOrderItem
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbSupplierOrderItemService.delete(id);
        System.out.println(id + "删除是否成功");
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

}
