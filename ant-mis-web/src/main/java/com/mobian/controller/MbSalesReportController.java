package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbOrderItemServiceI;
import org.apache.commons.collections.CollectionUtils;
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
 * 采购报表管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbSalesReportController")
public class MbSalesReportController extends BaseController {

    @Autowired
    private MbOrderItemServiceI mbOrderItemService;


    /**
     * 跳转到MbSupplierOrderItem管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbsalesreport/mbSalesReport";
    }

    /**
     * 获取MbSupplierOrderItem数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbSalesReport salesReport, PageHelper ph) {
        if(salesReport.getStartDate() == null && salesReport.getEndDate() == null)
            return new DataGrid();

        return mbOrderItemService.dataGridSalesReport(salesReport);
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
    public void download(MbSalesReport mbSalesReport, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbSalesReport, ph);
        List<MbSalesReport> mbSalesReportList=dg.getRows();
        if (CollectionUtils.isNotEmpty(mbSalesReportList)) {
            for (MbSalesReport salesReport : mbSalesReportList) {
                salesReport.setTotalPriceElement(salesReport.getTotalPrice() / 100.0);
                salesReport.setAvgPriceElement(salesReport.getAvgPrice() / 100.0);
                salesReport.setTotalCostElement(salesReport.getTotalCost() / 100.0);
                salesReport.setAvgCostElement(salesReport.getAvgCost() / 100.0);
                salesReport.setProfitElement(salesReport.getProfit() / 100.0);
            }
            MbSalesReport footer = (MbSalesReport) dg.getFooter().get(0);
            footer.setTotalPriceElement(footer.getTotalPrice() / 100.0);
            footer.setAvgPriceElement(footer.getAvgPrice() / 100.0);
            footer.setTotalCostElement(footer.getTotalCost() / 100.0);
            footer.setAvgCostElement(footer.getAvgCost() / 100.0);
            footer.setProfitElement(footer.getProfit() / 100.0);
        }
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        if (CollectionUtils.isNotEmpty(colums)) {
            for (Colum colum : colums) {
                switch (colum.getField()) {
                    case "totalPrice":
                        colum.setField("totalPriceElement");
                        break;
                    case "avgPrice":
                        colum.setField("avgPriceElement");
                        break;
                    case "totalCost":
                        colum.setField("totalCostElement");
                        break;
                    case "avgCost":
                        colum.setField("avgCostElement");
                        break;
                    case "profit":
                        colum.setField("profitElement");
                        break;
                }
            }
        }
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到MbSupplierOrderItem管理页面
     *
     * @return
     */
    @RequestMapping("/viewChart")
    public String viewChart(MbSalesReport mbSalesReport, HttpServletRequest request) {
        List<MbSalesReport> list = mbOrderItemService.dataGridSalesReport(mbSalesReport).getRows();
        request.setAttribute("chartData", JSON.toJSONString(list));
        return "/mbsalesreport/mbSalesReportChart";
    }

}
