package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.Supplier;
import com.bx.ant.pageModel.SupplierOrderBill;
import com.bx.ant.service.SupplierOrderBillServiceI;
import com.bx.ant.service.SupplierServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.Colum;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Supplier管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/receivablesController")
public class ReceivablesController extends BaseController {

    @Resource
    private SupplierOrderBillServiceI supplierOrderBillService;
    @Resource
    private SupplierServiceI supplierService;

    /**
     * 跳转到应收汇款管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager() {
        return "/receivables/receivables";
    }

    @RequestMapping("/queryUnReceivableBill")
    @ResponseBody
    public DataGrid queryUnReceivableBill(SupplierOrderBill supplierOrderBill, PageHelper ph) {
        if (!F.empty(supplierOrderBill.getSupplierName())) {
            List<Supplier> suppliers = supplierService.getSupplierListByName(supplierOrderBill.getSupplierName());
            if (CollectionUtils.isNotEmpty(suppliers)) {
                Integer[] supplierIds = new Integer[suppliers.size()];
                int i = 0;
                for (Supplier supplier : suppliers) {
                    supplierIds[i++] = supplier.getId();
                }
                supplierOrderBill.setSupplierIds(supplierIds);
            }
        }
        supplierOrderBill.setStatus("BAS01");
        DataGrid dataGrid = supplierOrderBillService.dataGrid(supplierOrderBill, ph);
        List<SupplierOrderBill> supplierOrderBills = dataGrid.getRows();
        if (CollectionUtils.isNotEmpty(supplierOrderBills)) {
            SupplierOrderBill foot = new SupplierOrderBill();
            foot.setAmount(0);
            for (SupplierOrderBill orderBill : supplierOrderBills) {
                foot.setAmount(foot.getAmount() + orderBill.getAmount());
            }
            dataGrid.setFooter(Arrays.asList(foot));
        }
        return dataGrid;
    }

    /**
     * 导出应收汇总报表
     *
     * @param supplierOrderBill
     * @param ph
     * @param ph
     * @param downloadFields
     * @param response
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(SupplierOrderBill supplierOrderBill, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = queryUnReceivableBill(supplierOrderBill, ph);
        List<SupplierOrderBill> supplierOrderBills = dg.getRows();
        if (CollectionUtils.isNotEmpty(supplierOrderBills)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (SupplierOrderBill orderBill : supplierOrderBills) {
                String createTimeName = formatter.format(orderBill.getAddtime());
                orderBill.setCreateTimeName(createTimeName);
                if (!F.empty(orderBill.getAmount())) {
                    orderBill.setAmountElement(orderBill.getAmount() / 100.0);
                }
            }
        }
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        if (CollectionUtils.isNotEmpty(colums)) {
            for (Colum colum : colums) {
                switch (colum.getField()) {
                    case "addtime":
                        colum.setField("createTimeName");
                        break;
                    case "amount":
                        colum.setField("amountElement");
                        break;
                }
            }
        }
        downloadTable(colums, dg, response);
    }
}
