package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbSupplierContractServiceI;
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
 * MbSupplierContract管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbSupplierContractController")
public class MbSupplierContractController extends BaseController {

    @Autowired
    private MbSupplierContractServiceI mbSupplierContractService;

    public static final String MB_SUPPCONTRACT = "mbSupplierContract";

    /**
     * 跳转到MbSupplierContract管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbsuppliercontract/mbSupplierContract";
    }

    @RequestMapping("/searchPrice")
    public String searchPrice(HttpServletRequest request) {
        return "/mbsuppliercontract/priceMessage";
    }

    /**
     * 获取MbSupplierContract数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbSupplierContract mbSupplierContract, PageHelper ph) {
        return mbSupplierContractService.dataGrid(mbSupplierContract, ph);
    }

    /**
     * 获取MbSupplierContract数据表格excel
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
    public void download(MbSupplierContract mbSupplierContract, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbSupplierContract, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbSupplierContract页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        MbSupplierContract mbSupplierContract = new MbSupplierContract();
        return "/mbsuppliercontract/mbSupplierContractAdd";
    }

    /**
     * 添加MbSupplierContract
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbSupplierContract mbSupplierContract) {
        Json j = new Json();
        if (mbSupplierContractService.isContractExists(mbSupplierContract)) {
            j.setSuccess(false);
            j.setMsg("该供应商合同已经存在！");
        } else if (mbSupplierContractService.isSupplierExists(mbSupplierContract)) {
            j.setSuccess(false);
            j.setMsg("有相同的供应商合同存在，请将之前合同的有效性改为：否！");
        } else {
            mbSupplierContractService.add(mbSupplierContract);
            j.setSuccess(true);
            j.setMsg("添加成功！");
        }

        return j;
    }

    /**
     * 跳转到MbSupplierContract查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbSupplierContract mbSupplierContract = mbSupplierContractService.get(id);
        request.setAttribute("mbSupplierContract", mbSupplierContract);
        return "/mbsuppliercontract/mbSupplierContractView";
    }

    /**
     * 跳转到MbSupplierContract修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbSupplierContract mbSupplierContract = mbSupplierContractService.get(id);
        request.setAttribute("mbSupplierContract", mbSupplierContract);
        return "/mbsuppliercontract/mbSupplierContractEdit";
    }

    /**
     * 修改MbSupplierContract
     *
     * @param mbSupplierContract
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbSupplierContract mbSupplierContract) {
        Json j =mbSupplierContractService.editMbSupplierContract(mbSupplierContract);
        return j;
    }

    /**
     * 删除MbSupplierContract
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbSupplierContractService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }


}
