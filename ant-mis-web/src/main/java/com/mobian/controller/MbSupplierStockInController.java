package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.service.impl.MbWarehouseServiceImpl;
import com.mobian.util.ConfigUtil;
import com.mobian.util.Constants;
import com.mobian.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * MbSupplierStockIn管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbSupplierStockInController")
public class MbSupplierStockInController extends BaseController {


    @Autowired
    private MbWarehouseServiceImpl mbWarehouseService;

    @Autowired
    private UserServiceI userService;
    @Autowired
    private MbSupplierStockInServiceI mbSupplierStockInService;
    @Autowired
    private MbSupplierStockInItemServiceI mbSupplierStockInItemService;
    @Autowired
    private MbSupplierFinanceLogServiceI mbSupplierFinanceLogService;
    @Autowired
    private MbSupplierOrderServiceI mbSupplierOrderService;
    @Autowired
    private MbSupplierServiceI mbSupplierService;
    @Autowired
    private MbItemServiceI mbItemService;
    @Autowired
    private MbItemCategoryServiceI mbItemCategoryService;
    @Autowired
    private MbSupplierContractServiceI mbSupplierContractService;

    /**
     * 跳转到MbSupplierStockIn管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request,Integer supplierId,String payStatus) {
        request.setAttribute("supplierId",supplierId);
        request.setAttribute("payStatus",payStatus);
        return "/mbsupplierstockin/mbSupplierStockIn";
    }

    /**
     * 获取MbSupplierStockIn数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbSupplierStockIn mbSupplierStockIn,Integer supplierId, PageHelper ph) {
        if(!F.empty(supplierId)) {
            MbSupplierOrder mbSupplierOrder = new MbSupplierOrder();
            mbSupplierOrder.setSupplierId(supplierId);
            List<MbSupplierOrder> mbSupplierOrderList = mbSupplierOrderService.query(mbSupplierOrder);
            Integer[] orderIds = new Integer[mbSupplierOrderList.size()];
            for (int i = 0; i < mbSupplierOrderList.size(); i++) {
                orderIds[i] = mbSupplierOrderList.get(i).getId();
            }
            mbSupplierStockIn.setSupplierOrderIdList(orderIds);
        }
        DataGrid dataGrid = mbSupplierStockInService.dataGrid(mbSupplierStockIn, ph);
        List<MbSupplierStockIn> mbSupplierStockInList = dataGrid.getRows();
        MbSupplierStockInView footer = new MbSupplierStockInView();
        footer.setTotalAmount(0);
        footer.setSupplierName("合计");
        if (CollectionUtils.isNotEmpty(mbSupplierStockInList)) {
            Integer[] mbSupplierStockIns = new Integer[mbSupplierStockInList.size()];
            int i = 0;
            List<MbSupplierStockInView> mbSupplierStockInViewList = new ArrayList<MbSupplierStockInView>();
            for (MbSupplierStockIn supplierStockIn : mbSupplierStockInList) {
                mbSupplierStockIns[i++] = supplierStockIn.getId();
                MbSupplierStockInView mbSupplierStockInView = new MbSupplierStockInView();
                BeanUtils.copyProperties(supplierStockIn, mbSupplierStockInView);
                mbSupplierStockInViewList.add(mbSupplierStockInView);
            }
            MbSupplierStockInItem mbSupplierStockInItem = new MbSupplierStockInItem();
            mbSupplierStockInItem.setSupplierStockInIdArray(mbSupplierStockIns);
            List<MbSupplierStockInItem> mbSupplierStockInItemList = mbSupplierStockInItemService.query(mbSupplierStockInItem);
            if (CollectionUtils.isNotEmpty(mbSupplierStockInItemList)) {
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                for (MbSupplierStockInItem supplierStockInItem : mbSupplierStockInItemList) {
                    Integer price = supplierStockInItem.getQuantity() * supplierStockInItem.getPrice();
                    footer.setTotalAmount(footer.getTotalAmount() + price);
                    Integer key = supplierStockInItem.getSupplierStockInId();
                    Integer totalPrice = map.get(key);
                    if (totalPrice == null) {
                        map.put(key, price);
                    } else {
                        map.put(key, totalPrice += price);
                    }
                }
                for (MbSupplierStockInView mbSupplierStockInView : mbSupplierStockInViewList) {
                    mbSupplierStockInView.setTotalAmount(map.get(mbSupplierStockInView.getId()));
                }
                dataGrid.setRows(mbSupplierStockInViewList);
            }
        }
        dataGrid.setFooter(Arrays.asList(footer));
        return dataGrid;

    }

    /**
     * 获取MbSupplierStockIn数据表格excel
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
    public void download(MbSupplierStockIn mbSupplierStockIn, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbSupplierStockIn,null, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }


    /**
     * 跳转到添加MbSupplierStockIn页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, Integer id) {
        MbSupplierOrder mbSupplierOrder = mbSupplierOrderService.get(id);
        request.setAttribute("mbSupplierOrder", mbSupplierOrder);
        request.setAttribute("orderid", id);
        return "/mbsupplierstockin/mbSupplierStockInAdd";
    }

    /**
     * 添加MbSupplierStockIn
     *
     * @return
     */


    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbSupplierStockIn mbSupplierStockIn, String dataGrid, HttpServletRequest request) {
        mbSupplierStockInService.addSupplierStockIn(mbSupplierStockIn, dataGrid);
        Json j = new Json();
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }


    /**
     * 跳转到MbSupplierStockIn查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbSupplierStockIn mbSupplierStockIn = mbSupplierStockInService.get(id);
        MbWarehouse mbWarehouse = mbWarehouseService.get(mbSupplierStockIn.getWarehouseId());
        mbSupplierStockIn.setWarehouseName(mbWarehouse.getName());
        User user = userService.get(mbSupplierStockIn.getSignPeopleId());
        mbSupplierStockIn.setSignPeopleName(user.getNickname());
        MbSupplierFinanceLog mbSupplierFinanceLog = new MbSupplierFinanceLog();
        mbSupplierFinanceLog.setSupplierStockInId(mbSupplierStockIn.getId());
        MbSupplierOrder mbSupplierOrder = mbSupplierOrderService.get(mbSupplierStockIn.getSupplierOrderId());
        MbSupplier mbSupplier = mbSupplierService.get(mbSupplierOrder.getSupplierId());
        mbSupplierStockIn.setSupplierName(mbSupplier.getName());
        List<MbSupplierFinanceLog> list = mbSupplierFinanceLogService.query(mbSupplierFinanceLog);
        if (list != null && list.size() > 0) {
            MbSupplierFinanceLog SupplierFinanceLog = list.get(0);
            user = userService.get(SupplierFinanceLog.getPayLoginId());
            String payLoginName = user.getName();
            String payRemark = SupplierFinanceLog.getPayRemark();
            String invoiceLoginId = SupplierFinanceLog.getInvoiceLoginId();
            if (invoiceLoginId != null) {
                user = userService.get(invoiceLoginId);
                String invoiceLoginName = user.getName();
                String invoiceRemark = SupplierFinanceLog.getInvoiceRemark();
                request.setAttribute("invoiceLoginName", invoiceLoginName);
                request.setAttribute("invoiceRemark", invoiceRemark);
            }
            request.setAttribute("payLoginName", payLoginName);
            request.setAttribute("payRemark", payRemark);
        }
        user = userService.get(mbSupplierStockIn.getDriverLoginId());
        if (user != null) {
            mbSupplierStockIn.setDriverName(user.getNickname());
        }
        request.setAttribute("mbSupplierStockIn", mbSupplierStockIn);
        return "/mbsupplierstockin/mbSupplierStockInView";
    }

    /**
     * 跳转到MbSupplierStockIn修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbSupplierStockIn mbSupplierStockIn = mbSupplierStockInService.get(id);
        request.setAttribute("mbSupplierStockIn", mbSupplierStockIn);
        return "/mbsupplierstockin/mbSupplierStockInEdit";
    }

    /**
     * 修改MbSupplierStockIn
     *
     * @param mbSupplierStockIn
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbSupplierStockIn mbSupplierStockIn) {
        Json j = new Json();
        mbSupplierStockInService.edit(mbSupplierStockIn);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }
    /**
     * 打印入库单
     */
    @RequestMapping("/printStockIn")
    public  String printStockIn(HttpServletRequest request,Integer id){
        MbSupplierStockIn mbSupplierStockIn = mbSupplierStockInService.get(id);
        User user = userService.get(mbSupplierStockIn.getSignPeopleId());
        mbSupplierStockIn.setSignPeopleName(user.getNickname());
        MbSupplierOrder mbSupplierOrder = mbSupplierOrderService.get(mbSupplierStockIn.getSupplierOrderId());
        MbSupplier mbSupplier = mbSupplierService.get(mbSupplierOrder.getSupplierId());
        mbSupplierStockIn.setSupplierName(mbSupplier.getName());
        user = userService.get(mbSupplierOrder.getSupplierPeopleId());
        mbSupplierOrder.setSupplierName(mbSupplier.getName());
        mbSupplierOrder.setSupplierPeopleName(user.getNickname());
        MbSupplierStockInItem mbSupplierStockInItem = new MbSupplierStockInItem();
        mbSupplierStockInItem.setSupplierStockInId(id);
        List<MbSupplierStockInItem> mbSupplierStockInItemList = mbSupplierStockInItemService.query(mbSupplierStockInItem);
        for (MbSupplierStockInItem m : mbSupplierStockInItemList) {
            MbItem mbItem = mbItemService.getFromCache(m.getItemId());
            m.setCode(mbItem.getCode());
            m.setQuantityUnitName(mbItem.getQuantityUnitName());
            MbItemCategory mbItemCategory = mbItemCategoryService.getFromCache(mbItem.getCategoryId());
            m.setCategoryName(mbItemCategory.getName());
            m.setProductName(mbItem.getName());
        }
        MbSupplierFinanceLog m = new MbSupplierFinanceLog();
        m.setSupplierStockInId(mbSupplierStockIn.getId());
        List<MbSupplierFinanceLog> list = mbSupplierFinanceLogService.query(m);
        if (list != null && list.size() > 0) {
            MbSupplierFinanceLog mbSupplierFinanceLog = list.get(0);
            user = userService.get(mbSupplierFinanceLog.getPayLoginId());
            mbSupplierFinanceLog.setPayLoginName(user.getNickname());
            request.setAttribute("mbSupplierFinanceLog", mbSupplierFinanceLog);
        }
        request.setAttribute("mbSupplierStockIn", mbSupplierStockIn);
        request.setAttribute("mbSupplierOrder", mbSupplierOrder);
        request.setAttribute("mbSupplierStockInItemList", mbSupplierStockInItemList);

        return "/mbsupplierstockin/mbSupplierStockInPrint";
    }
    /**
     * 删除MbSupplierStockIn
     *
     * @param id
     * @return
     */
    @RequestMapping("/invoicePage")
    public String Page(HttpServletRequest request, Integer id) {
        MbSupplierStockIn mbSupplierStockIn = mbSupplierStockInService.get(id);
        User user = userService.get(mbSupplierStockIn.getLoginId());
        mbSupplierStockIn.setLoginName(user.getName());
        request.setAttribute("mbSupplierStockIn", mbSupplierStockIn);
        return "/mbsupplierstockin/mbSupplierStockIninvoice";
    }

    @RequestMapping("/editInvoiceStatus")
    @ResponseBody
    public Json invoice(Integer id, String remark, String invoiceNo, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        Json j = new Json();
        mbSupplierStockInService.addInvoice(id, remark, sessionInfo.getId(), invoiceNo);
        j.setMsg("更新开票状态成功！");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/payPage")
    public String payPage(HttpServletRequest request, Integer id) {
        MbSupplierStockIn mbSupplierStockIn = mbSupplierStockInService.get(id);
        User user = userService.get(mbSupplierStockIn.getLoginId());
        mbSupplierStockIn.setLoginName(user.getName());
        request.setAttribute("mbSupplierStockIn", mbSupplierStockIn);
        return "/mbsupplierstockin/mbSupplierStockInpay";
    }

    @RequestMapping("/editPayStatus")
    @ResponseBody
    public Json pay(Integer id, String remark, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        Json j = new Json();
        mbSupplierStockInService.addPay(id, remark, sessionInfo.getId());
        j.setMsg("更新付款状态成功！");
        j.setSuccess(true);
        return j;
    }
    @RequestMapping("/checkPayTimeOut")
    @ResponseBody
    public Json checkPayTimeOut(Integer id){
        Json j = new Json();
        MbSupplierStockIn mbSupplierStockIn = mbSupplierStockInService.get(id);
        MbSupplierOrder mbSupplierOrder = mbSupplierOrderService.get(mbSupplierStockIn.getSupplierOrderId());
        MbSupplierContract mbSupplierContract = mbSupplierContractService.get(mbSupplierOrder.getSupplierContractId());
        Integer paymentDays = mbSupplierContract.getPaymentDays();
        // 获取付款日 账期不为空才会计算
        if (paymentDays != null){
            Date payDate = DateUtil.addDayToDate(mbSupplierStockIn.getAddtime(),paymentDays);
            // 判断当前时间是否超过付款日
            if (new Date().getTime() < payDate.getTime()) {
                j.setSuccess(true);
                //计算距离还款日还有多少天；
                long diff = (payDate.getTime() - new Date().getTime()) / 1000 / 60 / 60 / 24;
                j.setMsg("账期为："+paymentDays+"天,  付款日为："+ DateUtil.format(payDate, Constants.DATE_FORMAT_YMD)+"</br>距离账期还有"+diff+"天,您确认付款吗？");
            }
        }



        return  j;
    }

}
