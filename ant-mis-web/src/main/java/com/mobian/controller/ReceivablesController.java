package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderQuery;
import com.bx.ant.pageModel.Supplier;
import com.bx.ant.pageModel.SupplierOrderBill;
import com.bx.ant.service.DeliverOrderServiceI;
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
import java.util.ArrayList;
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
    private DeliverOrderServiceI deliverOrderService;

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
    public DataGrid queryUnReceivableBill(DeliverOrder deliverOrder) {
        DataGrid dg = deliverOrderService.queryUnPayForCount(deliverOrder);
        List<DeliverOrder> rows = dg.getRows();
        List<DeliverOrderQuery> deliverOrderQueries = new ArrayList<DeliverOrderQuery>();
        DeliverOrderQuery footer = new DeliverOrderQuery();
        footer.setAmount(0);
        dg.setFooter(Arrays.asList(footer));
        for (DeliverOrder row : rows) {
            DeliverOrderQuery deliverOrderQuery = new DeliverOrderQuery();
            Supplier supplier = supplierService.get(row.getSupplierId());
            if(supplier!=null)
            deliverOrderQuery.setSupplierName(supplier.getName());
            deliverOrderQuery.setAmount(row.getAmount());
            deliverOrderQuery.setSupplierId(row.getSupplierId());
            deliverOrderQueries.add(deliverOrderQuery);
            footer.setAmount(deliverOrderQuery.getAmount()+footer.getAmount());
        }
        dg.setRows(deliverOrderQueries);
        return dg;
    }

    /**
     * 导出应收汇总报表
     *
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
    public void download(DeliverOrder deliverOrder, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = queryUnReceivableBill(deliverOrder);
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
