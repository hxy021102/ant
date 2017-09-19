package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.ConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * MbSupplierOrder管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbSupplierOrderController")
public class MbSupplierOrderController extends BaseController {

    @Autowired
    private MbSupplierOrderServiceI mbSupplierOrderService;
    @Autowired
    private MbSupplierOrderItemServiceI mbSupplierOrderItemService;
    @Autowired
    private MbSupplierServiceI mbSupplierService;
    @Autowired
    private UserServiceI userService;
    @Autowired
    private MbWarehouseServiceI mbWarehouseService;
    @Autowired
    private MbSupplierContractItemServiceI mbSupplierContractItemService;
    @Autowired
    private MbSupplierContractServiceI mbSupplierContractService;
    @Autowired
    private MbItemServiceI mbItemService;


    /**
     * 跳转到MbSupplierOrder管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbsupplierorder/mbSupplierOrder";
    }

    /**
     * 获取MbSupplierOrder数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbSupplierOrder mbSupplierOrder, PageHelper ph) {
        return mbSupplierOrderService.dataGrid(mbSupplierOrder, ph);
    }

    /**
     * 获取MbSupplierOrder数据表格excel
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
    public void download(MbSupplierOrder mbSupplierOrder, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbSupplierOrder, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbSupplierOrder页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, Integer id) {
        MbSupplierContractItem mbSupplierContractItem = mbSupplierContractItemService.get(id);
        MbSupplierContract mbSupplierContract = mbSupplierContractService.get(mbSupplierContractItem.getSupplierContractId());
        MbSupplierOrder mbSupplierOrder = new MbSupplierOrder();
        mbSupplierOrder.setSupplierId(mbSupplierContract.getSupplierId());
        mbSupplierOrder.setCode(mbSupplierContract.getCode());
        mbSupplierOrder.setSupplierContractId(mbSupplierContract.getId());     //获取供应商合同表的id作为订单表的合同id
        MbSupplier mbSupplier = mbSupplierService.get(mbSupplierContract.getSupplierId());
        mbSupplierOrder.setWarehouseId(mbSupplier.getWarehouseId());
        mbSupplierOrder.setSupplierName(mbSupplier.getName());
        request.setAttribute("mbSupplierOrder", mbSupplierOrder);
        return "/mbsupplierorder/mbSupplierOrderAdd";
    }

    @RequestMapping("/selectQuery")
    @ResponseBody
    public List<MbSupplierContractItem> query(Integer supplierContractId) {
        MbSupplierContractItem mbSupplierContractItem = new MbSupplierContractItem();
        mbSupplierContractItem.setSupplierContractId(supplierContractId);
        PageHelper ph = new PageHelper();
        ph.setHiddenTotal(true);
        ph.setPage(100);
        DataGrid contractItemList = mbSupplierContractItemService.dataGrid(mbSupplierContractItem, ph);
        List<MbSupplierContractItem> rows = contractItemList.getRows();
        if (!CollectionUtils.isEmpty(rows)) {
            for (MbSupplierContractItem d : rows) {
                if (d.getItemId() != null) {
                    MbItem item = mbItemService.getFromCache(d.getItemId());
                    if (item != null) {
                        d.setItemName(item.getName());
                    }
                }
                d.setId(d.getItemId());

            }
        }
        return rows;
    }

    /**
     * 添加MbSupplierOrder
     *
     * @return MbSupplierOrder
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Json add(@RequestBody MbSupplierOrder mbSupplierOrder, HttpSession session) {
        Json j = new Json();
        if (mbSupplierOrder != null) {
            if (mbSupplierOrderService.addMbSupplierOrder(mbSupplierOrder, session)) {
                j.setSuccess(true);
                j.setMsg("添加成功！");
                return j;
            }
        }
        j.setSuccess(false);
        j.setMsg("同种商品不能重复添加，添加失败！");
        return j;

    }

    /**
     * 跳转到MbSupplierOrder查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbSupplierOrder mbSupplierOrder =mbSupplierOrderService.showMbSupplierOrder(id);
        request.setAttribute("mbSupplierOrder", mbSupplierOrder);
        return "/mbsupplierorder/mbSupplierOrderView";
    }

    /**
     * 跳转到MbSupplierOrder修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbSupplierOrder mbSupplierOrder = mbSupplierOrderService.get(id);
        MbSupplierContract mbSupplierContract = mbSupplierContractService.get(mbSupplierOrder.getSupplierContractId());
        MbSupplier mbSupplier = mbSupplierService.get(mbSupplierContract.getSupplierId());
        mbSupplierOrder.setSupplierName(mbSupplier.getName());
        if (mbSupplierContract != null) {
            mbSupplierOrder.setCode(mbSupplierContract.getCode());
        }
        request.setAttribute("mbSupplierOrder", mbSupplierOrder);
        return "/mbsupplierorder/mbSupplierOrderEdit";
    }

    /**
     * 跳转到MbSupplierOrder审核页面
     *
     * @return
     */
    @RequestMapping("/examinePage")
    public String examinePage(HttpServletRequest request, Integer id) {
        MbSupplierOrder mbSupplierOrder = mbSupplierOrderService.get(id);
        request.setAttribute("mbSupplierOrder", mbSupplierOrder);
        return "/mbsupplierorder/mbSupplierOrderExamine";
    }

    /**
     * 修改MbSupplierOrder
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Json edit(@RequestBody MbSupplierOrder mbSupplierOrder, HttpSession session) {
        Json j = new Json();
        if (mbSupplierOrderService.editMbSupplierOrder(mbSupplierOrder)) {
            j.setSuccess(true);
            j.setMsg("编辑成功！");
            return j;
        }
        j.setSuccess(false);
        j.setMsg("同种商品不能重复添加，添加失败！");
        return j;
    }

    /**
     * 修改MbSupplierOrder
     *
     * @param
     * @return
     */
    @RequestMapping("/editState")
    @ResponseBody
    public Json editState(MbSupplierOrder mbSupplierOrder, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        Json j = new Json();
        if (sessionInfo.getId() != null) {
            mbSupplierOrder.setReviewerId(sessionInfo.getId());
        }
        mbSupplierOrder.setReviewDate(new Date());
        mbSupplierOrderService.edit(mbSupplierOrder);

        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbSupplierOrder
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbSupplierOrderService.deleteSupplierOrderAndItem(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/deleteOrder")
    @ResponseBody
    public Json deleteOrder(Integer id) {
        Json j = new Json();
        mbSupplierContractService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }
    @RequestMapping("/updateSupplierOrderStatus")
    @ResponseBody
    public Json updateSupplierOrderStatus(Integer id){
        Json j=new Json();
        MbSupplierOrder mbSupplierOrder=new MbSupplierOrder();
        mbSupplierOrder.setId(id);
        mbSupplierOrder.setStatus("SS03");
        mbSupplierOrderService.edit(mbSupplierOrder);
        j.setMsg("确认入库完成");
        j.setSuccess(true);
        return  j;
    }

}
