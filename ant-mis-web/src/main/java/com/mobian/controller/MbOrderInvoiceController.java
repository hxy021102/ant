package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbOrderInvoiceServiceI;
import com.mobian.service.MbShopInvoiceServiceI;
import com.mobian.service.UserServiceI;
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
 * MbOrderInvoice管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbOrderInvoiceController")
public class MbOrderInvoiceController extends BaseController {

    @Autowired
    private MbOrderInvoiceServiceI mbOrderInvoiceService;
    @Autowired
    private MbShopInvoiceServiceI shopInvoiceService;
    @Autowired
    private UserServiceI userService;


    /**
     * 跳转到MbOrderInvoice管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mborderinvoice/mbOrderInvoice";
    }

    /**
     * 跳转到搜索订单页面
     */
    @RequestMapping("/queryOrderManager")
    public String queryOrderManager(HttpServletRequest request) {
        return "/mborderinvoice/mbOrderInvoice";
    }

    /**
     * 获取MbOrderInvoice数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbOrderInvoice mbOrderInvoice, PageHelper ph) {
        return mbOrderInvoiceService.dataGrid(mbOrderInvoice, ph);
    }

    /**
     * 获取MbOrderInvoice数据表格excel
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
    public void download(MbOrderInvoice mbOrderInvoice, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbOrderInvoice, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbOrderInvoice页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        MbOrderInvoice mbOrderInvoice = new MbOrderInvoice();
        return "/mborderinvoice/mbOrderInvoiceAdd";
    }

    /**
     * 添加MbOrderInvoice
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbOrderInvoice mbOrderInvoice) {
        Json j = new Json();
        mbOrderInvoiceService.add(mbOrderInvoice);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到MbOrderInvoice查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbOrderInvoice mbOrderInvoice = mbOrderInvoiceService.get(id);
        User user = userService.get(mbOrderInvoice.getLoginId());
        mbOrderInvoice.setLoginName(user.getName());
        request.setAttribute("mbOrderInvoice", mbOrderInvoice);
        return "/mborderinvoice/mbOrderInvoiceView";
    }

    /**
     * 跳转到MbOrderInvoice修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbOrderInvoice mbOrderInvoice = mbOrderInvoiceService.get(id);
        request.setAttribute("mbOrderInvoice", mbOrderInvoice);
        return "/mborderinvoice/mbOrderInvoiceEdit";
    }
    /**
     *  查询该门店有没有模板
     *
     */
    @RequestMapping("/queryShopInvoice")
    @ResponseBody
    public Json queryShopInvoice(Integer shopId){
        Json j =new Json();
        MbShopInvoice mbShopInvoice = new MbShopInvoice();
        mbShopInvoice.setShopId(shopId);
        List<MbShopInvoice> list = shopInvoiceService.query(mbShopInvoice);
        if(list.size()==0){
            j.setSuccess(true);//没有模板，前台页面就要弹窗提醒。
        }
        return  j;
    }
    /**
     * 跳转到批量开票的页面
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping("/editOrderInvoicePage")
    public String editOrderInvoice(HttpServletRequest request, Integer shopId) {
        MbShopInvoice mbShopInvoice = new MbShopInvoice();
        mbShopInvoice.setShopId(shopId);
        List<MbShopInvoice> list = shopInvoiceService.query(mbShopInvoice);
        if (list != null && list.size() > 0) {
            mbShopInvoice = list.get(0);
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getInvoiceDefault()==true) {
                    mbShopInvoice=list.get(i);
                    break;
                }
            }
        }
        request.setAttribute("mbShopInvoice", mbShopInvoice);
        return "mborderinvoice/mbEditOrderInvoice";
    }


    /**
     *
     */
    @RequestMapping("/editOrderInvoice")
    @ResponseBody
    public Json editOrderInvoice(MbOrderInvoice mbOrderInvoice, String mbOrderInvoiceList) {
        Json j = new Json();
        mbOrderInvoiceService.addOrderInvoice(mbOrderInvoice, mbOrderInvoiceList);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }


    /**
     * 修改MbOrderInvoice
     *
     * @param mbOrderInvoice
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbOrderInvoice mbOrderInvoice) {
        Json j = new Json();
        mbOrderInvoiceService.edit(mbOrderInvoice);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbOrderInvoice
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbOrderInvoiceService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

}
