package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.MbContractItemServiceI;
import com.mobian.service.MbItemServiceI;
import com.mobian.util.ImportExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * MbContractItem管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbContractItemController")
public class MbContractItemController extends BaseController {

    @Autowired
    private MbContractItemServiceI mbContractItemService;

    @Autowired
    private MbItemServiceI mbItemService;


    /**
     * 跳转到MbContractItem管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbcontractitem/mbContractItem";
    }

    @RequestMapping("/updateContractPriceManage")
    public String updateContractPrice(HttpServletRequest request) {
        return "/mbcontractitem/updateContractPriceManage";
    }

    @RequestMapping("/editContractPricePage")
    public String editContractPricePage(HttpServletRequest request, Integer id) {
        return "/mbcontractitem/editContractPrice";
    }

    @RequestMapping(value = "/editContractPrice", method = RequestMethod.POST)
    @ResponseBody
    public Json editContractPrice(String mbContractItemList, HttpSession session, Integer newPrice) {
        mbContractItemService.updateBatchContractPrice(mbContractItemList, newPrice);
        Json j = new Json();
        j.setSuccess(true);
        j.setMsg("修改成功！");
        return j;
    }

    /**
     * 获取MbContractItem数据表格
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbContractItem mbContractItem, PageHelper ph) {
        return mbContractItemService.dataGrid(mbContractItem, ph);
    }

    /**
     * 通过商品id查询合同商品信息
     */
    @RequestMapping("/queryContractItem")
    @ResponseBody
    public DataGrid priceDataGrid(MbContractItem mbContractItem, PageHelper ph) {
        return mbContractItemService.queryContractItem(mbContractItem, ph);
    }

    /**
     * 获取MbContractItem数据表格excel
     */
    @RequestMapping("/download")
    public void download(MbContractItem mbContractItem, PageHelper ph, String downloadFields, HttpServletResponse response, HttpServletRequest request) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {

        DataGrid dg = dataGrid(mbContractItem, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbContractItem页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, Integer contractId) {
        request.setAttribute("contractId", contractId);
        MbContractItem mbContractItem = new MbContractItem();
        return "/mbcontractitem/mbContractItemAdd";
    }

    /**
     * 添加MbContractItem
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbContractItem mbContractItem) {
        Json j = new Json();
        mbContractItemService.add(mbContractItem);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 获取MbContractItem数据表格excel
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Json upload(MbContractItem mbContractItem, @RequestParam MultipartFile file) throws Exception {
        Json json = new Json();
        try {
            if (file.isEmpty()) {
                throw new ServiceException("请上传文件！！");
            }
            InputStream in = file.getInputStream();
            List<List<Object>> listOb = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
            in.close();
            List<MbContractItem> mbContractItemList = new ArrayList<MbContractItem>();
            for (int i = 0; i < listOb.size(); i++) {
                List<Object> lo = listOb.get(i);
                MbContractItem item = new MbContractItem();

                MbItem request = new MbItem();
                String code = lo.get(0).toString();
                request.setCode(code);
                List<MbItem> list = mbItemService.query(request);
                if (CollectionUtils.isEmpty(list)) {
                    throw new ServiceException(String.format("%s商品不存在", code));
                } else {
                    MbItem mbItem = list.get(0);
                    item.setItemId(mbItem.getId());
                }
                item.setContractId(mbContractItem.getContractId());
                item.setPrice(Integer.parseInt(lo.get(1).toString()));
                mbContractItemList.add(item);
            }
            mbContractItemService.addBatchAndOverride(mbContractItemList);
            json.success();
        } catch (ServiceException e) {
            json.setMsg(e.getMsg());
        }
        return json;
    }

    /**
     * 跳转到添加MbContractItem页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/uploadPage")
    public String uploadPage(HttpServletRequest request, Integer contractId) {
        request.setAttribute("contractId", contractId);
        return "/mbcontractitem/mbContractItemUpload";
    }

    /**
     * 跳转到MbContractItem查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbContractItem mbContractItem = mbContractItemService.get(id);
        request.setAttribute("mbContractItem", mbContractItem);
        return "/mbcontractitem/mbContractItemView";
    }

    /**
     * 跳转到MbContractItem修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbContractItem mbContractItem = mbContractItemService.get(id);
        request.setAttribute("mbContractItem", mbContractItem);
        return "/mbcontractitem/mbContractItemEdit";
    }

    /**
     * 修改MbContractItem
     *
     * @param mbContractItem
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbContractItem mbContractItem) {
        Json j = new Json();
        mbContractItemService.editAndHistory(mbContractItem);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbContractItem
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbContractItemService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

}
