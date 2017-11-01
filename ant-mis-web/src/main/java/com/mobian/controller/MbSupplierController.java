package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbSupplierServiceI;

import com.mobian.service.UserServiceI;
import com.mobian.service.impl.DiveRegionServiceImpl;
import com.mobian.service.impl.MbItemStockServiceImpl;
import com.mobian.service.impl.MbWarehouseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbSupplier管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbSupplierController")
public class MbSupplierController extends BaseController {

    @Autowired
    private MbSupplierServiceI mbSupplierService;
    @Autowired
    private DiveRegionServiceImpl diveRegionService;
    @Autowired
    private MbWarehouseServiceImpl mbWarehouseService;
    @Autowired
    private UserServiceI userService;

    /**
     * 跳转到MbSupplier管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbsupplier/mbSupplier";
    }

    /**
     * 获取MbSupplier数据表格
     *
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbSupplier mbSupplier, PageHelper ph) {
        DataGrid g = mbSupplierService.dataGrid(mbSupplier, ph);
        List<MbSupplier> l = g.getRows();
        for (MbSupplier m : l) {
            Integer id = m.getRegionId();
            if (!F.empty(id)) {
                String regionId = id + "";
                DiveRegion d = diveRegionService.getFromCache(regionId);
                String name = d.getRegionNameZh();
                m.setRegionName(name);
            }
        }
        return g;
    }

    /**
     * 获取MbSupplier数据表格excel
     *
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(MbSupplier mbSupplier, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbSupplier, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbSupplier页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        MbSupplier mbSupplier = new MbSupplier();
        return "/mbsupplier/mbSupplierAdd";
    }

    /**
     * 添加MbSupplier
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbSupplier mbSupplier) {
        Json j = new Json();
        if(mbSupplier.getSupplierCode() !=null) {
            MbSupplier supplier = mbSupplierService.getBySupplierCode(mbSupplier.getSupplierCode());
            if(supplier != null) {
                j.setSuccess(false);
                j.setMsg("已存在相同供应商代码！请重新添加！");
            }else {
                mbSupplierService.add(mbSupplier);
                j.setSuccess(true);
                j.setMsg("添加成功！");

            }
        }
        return j;
    }

    /**
     * 跳转到MbSupplier查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbSupplier mbSupplier = mbSupplierService.get(id);
        if (mbSupplier.getRegionId() != null) {
            DiveRegion diveRegion = diveRegionService.getFromCache(mbSupplier.getRegionId() + "");
            mbSupplier.setRegionName(diveRegion.getRegionNameZh());
        }
        if (mbSupplier.getWarehouseId() != null) {
            MbWarehouse mbWarehouse = mbWarehouseService.get(mbSupplier.getWarehouseId());
            mbSupplier.setWarehouseName(mbWarehouse.getName());
        }
        request.setAttribute("mbSupplier", mbSupplier);
        return "/mbsupplier/mbSupplierView";
    }

    /**
     * 跳转到MbSupplier修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbSupplier mbSupplier = mbSupplierService.get(id);
        request.setAttribute("mbSupplier", mbSupplier);
        return "/mbsupplier/mbSupplierEdit";
    }

    /**
     * 修改MbSupplier
     *
     * @param mbSupplier
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbSupplier mbSupplier) {
        Json j = new Json();
        mbSupplierService.edit(mbSupplier);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbSupplier
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbSupplierService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }


    @RequestMapping("/selectQuery")
    @ResponseBody
    public List<Tree> query(String q) {

        MbSupplier mbSupplier = new MbSupplier();
        List<Tree> lt = new ArrayList<Tree>();
        if (!F.empty(q)) {
            mbSupplier.setName(q);
        } else {
            return lt;
        }
        PageHelper ph = new PageHelper();
        ph.setHiddenTotal(true);
        ph.setPage(100);
        DataGrid mbSupplierList = mbSupplierService.dataGrid(mbSupplier, ph);
        List<MbSupplier> rows = mbSupplierList.getRows();
        if (!CollectionUtils.isEmpty(rows)) {

            for (MbSupplier d : rows) {
                Tree tree = new Tree();
                tree.setId(d.getId() + "");
                tree.setPid(d.getRegionId() + "");
                tree.setText(d.getName());
                tree.setParentName(d.getRegionPath());
                lt.add(tree);
            }
        }
        return lt;
    }

}
