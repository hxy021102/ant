package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbItemStockServiceI;
import com.mobian.util.ConfigUtil;
import com.mobian.util.ImportExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * MbItemStock管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbItemStockController")
public class MbItemStockController extends BaseController {

    @Autowired
    private MbItemStockServiceI mbItemStockService;
    @Autowired
    private MbItemServiceI mbItemService;


    /**
     * 跳转到MbItemStock管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbitemstock/mbItemStock";
    }

    /**
     * 获取MbItemStock数据表格
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbItemStock mbItemStock, PageHelper ph) {
        return mbItemStockService.dataGridWithOrderSum(mbItemStock, ph);
    }

    /**
     * 获取MbItemStock数据表格excel
     */
    @RequestMapping("/download")
    public void download(MbItemStock mbItemStock, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbItemStock, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbItemStock页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        MbItemStock mbItemStock = new MbItemStock();
        return "/mbitemstock/mbItemStockAdd";
    }

    /**
     * 添加MbItemStock
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbItemStock mbItemStock, HttpSession session) {
        Json j = new Json();

        if (!mbItemStockService.isWarehouseItemPairExist(mbItemStock.getWarehouseId(), mbItemStock.getItemId())) {
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
            mbItemStockService.addAndInsertLog(mbItemStock, sessionInfo.getId());
            j.setSuccess(true);
            j.setMsg("添加成功！");
        } else {
            j.setSuccess(false);
            j.setMsg("该仓库中该商品的库存已存在！");
        }

        return j;
    }

    /**
     * 跳转到MbItemStock查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbItemStock mbItemStock = mbItemStockService.get(id);
        request.setAttribute("mbItemStock", mbItemStock);
        return "/mbitemstock/mbItemStockView";
    }

    /**
     * 跳转到MbItemStock修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbItemStock mbItemStock = mbItemStockService.get(id);
        mbItemStockService.addWarehouseAndItemInfo(mbItemStock);
        request.setAttribute("mbItemStock", mbItemStock);
        return "/mbitemstock/mbItemStockEdit";
    }

    /**
     * 跳转到MbItemStock修改页面
     *
     * @return
     */
    @RequestMapping("/editSafePage")
    public String editSafePage(HttpServletRequest request, Integer id) {
        MbItemStock mbItemStock = mbItemStockService.get(id);
        request.setAttribute("mbItemStock", mbItemStock);
        return "/mbitemstock/mbItemStockSafeEdit";
    }

    /**
     * 修改安全库存
     * @param mbItemStock
     * @param session
     * @return
     */
    @RequestMapping("/editSafe")
    @ResponseBody
    public Json editSafe(MbItemStock mbItemStock, HttpSession session) {
        Json j = new Json();
        mbItemStockService.edit(mbItemStock);
        j.success();
        return j;
    }

    /**
     * 修改MbItemStock
     *
     * @param mbItemStock
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbItemStock mbItemStock, HttpSession session) {
        Json j = new Json();

        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());

        if (mbItemStock.getAdjustment() != null && mbItemStock.getAdjustment() != 0) {
            boolean flag = mbItemStockService.editAndInsertLog(mbItemStock, sessionInfo.getId()) != null;
            j.setSuccess(flag);
            if (flag)
                j.setMsg("编辑成功！");
            else
                j.setMsg("调整失败！");
        } else {
            j.setSuccess(false);
            j.setMsg("调整量不能为空！！");
        }

        return j;
    }


    /**
     * 删除MbItemStock
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbItemStockService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

    /**
     * 跳转到添加MbItemStock页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/uploadPage")
    public String uploadPage(HttpServletRequest request) {
        return "/mbitemstock/mbitemstockUpload";
    }
    /**
     * 获取MbItemStock数据表格excel
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Json upload(MbItemStock mbItemStock, @RequestParam MultipartFile file,HttpSession session) throws Exception {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        Json json = new Json();
         try {
            if (file.isEmpty()) {
                throw new ServiceException("请上传文件！！");
            }
            InputStream in = file.getInputStream();
            List<List<Object>> listOb = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
            in.close();
            List<MbItemStock> mbItemStockList = new ArrayList<MbItemStock>();
            for (int i = 0; i < listOb.size(); i++) {
                List<Object> lo = listOb.get(i);
                MbItemStock stock = new MbItemStock();

                MbItem request = new MbItem();
                String code = lo.get(0).toString();
                request.setCode(code);
                List<MbItem> list = mbItemService.query(request);
                if (CollectionUtils.isEmpty(list)) {
                    throw new ServiceException(String.format("%s商品不存在", code));
                } else {
                    MbItem mbItem = list.get(0);
                    stock.setItemId(mbItem.getId());
                }
                stock.setWarehouseId(mbItemStock.getWarehouseId());
                stock.setQuantity(Integer.parseInt(lo.get(1).toString()));
                mbItemStockList.add(stock);
            }
             mbItemStockService.addBatchAndUpdateItemStock(mbItemStockList,sessionInfo.getId());
            json.setSuccess(true);
        } catch (ServiceException e) {
            json.setMsg(e.getMsg());
        }
        return json;
    }

    /**
     * 跳转到MbItemStock修改页面
     *
     * @return
     */
    @RequestMapping("/editAveragePricePage")
    public String editAveragePricePage(HttpServletRequest request, Integer id) {
        MbItemStock mbItemStock = mbItemStockService.get(id);
        request.setAttribute("mbItemStock", mbItemStock);
        return "/mbitemstock/mbItemStockAveragePriceEdit";
    }
    /**
     * 修改库存商品平均价格
     * @param mbItemStock
     * @param session
     * @return
     */
    @RequestMapping("/editAveragePrice")
    @ResponseBody
    public Json editAveragePrice(MbItemStock mbItemStock, HttpSession session) {
        Json j = new Json();
        mbItemStockService.edit(mbItemStock);
        j.success();
        return j;
    }

    /**
     * 跳转到MbItemStock管理页面
     *
     * @return
     */
    @RequestMapping("/managerReport")
    public String managerReport(HttpServletRequest request) {
        return "/mbitemstock/mbItemStockReport";
    }

    /**
     * 获取MbItemStock数据表格
     */
    @RequestMapping("/dataGridReport")
    @ResponseBody
    public DataGrid dataGridReport(MbItemStock mbItemStock, PageHelper ph) {
        if (F.empty(mbItemStock.getItemId()) && F.empty(mbItemStock.getWarehouseId())) {
            return new DataGrid();
        }
        return mbItemStockService.dataGridReport(mbItemStock, ph);
    }
    /**
     * 跳转到报表页面
     */
    @RequestMapping("/viewChart")
    public String viewChart(MbItemStock mbItemStock, PageHelper ph, HttpServletRequest request) {
        DataGrid dataGrid = mbItemStockService.dataGridReport(mbItemStock, ph);
        List<MbItemStock> mbItemStocks = dataGrid.getRows();
        request.setAttribute("chartData", JSON.toJSONString(mbItemStocks));
        return "/mbitemstock/mbItemStockReportChart";
    }

    /**
     * 获取MbItemStock数据表格excel
     */
    @RequestMapping("/downloadWithTotalPrice")
    public void downloadWithTotalPrice(MbItemStock mbItemStock, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        ph.setPage(0);
        ph.setRows(0);
        DataGrid dg = mbItemStockService.dataGridReport(mbItemStock, ph);
        downloadTable(colums, dg, response);
    }
    /**
     * 获取MbItemStock带有空桶的数据表格
     */
    @RequestMapping("/emptyBucketDataGrid")
    @ResponseBody
    public DataGrid emptyBucketDataGrid(Integer warehouseId, PageHelper ph) {
        if (!F.empty(warehouseId)) {
            MbItemStock mbItemStock = new MbItemStock();
            mbItemStock.setWarehouseId(warehouseId);
            DataGrid dataGrid = mbItemStockService.dataGridEmptyBucket(mbItemStock, ph);
            return dataGrid;
        }
        return new DataGrid();
    }

    /**
     * 修改门店所对应的库存
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/editStockPage")
    public String  editStockPage(Integer id,Integer shopId, HttpServletRequest request) {
        MbItemStock mbItemStock = mbItemStockService.get(id);
        mbItemStockService.addWarehouseAndItemInfo(mbItemStock);
        mbItemStock.setShopId(shopId);
        request.setAttribute("mbItemStock", mbItemStock);
        return "/mbitemstock/mbItemStockShopEdit";
    }

    @RequestMapping("/editStock")
    @ResponseBody
    public Json editStock(MbItemStock mbItemStock, HttpSession session) {
        Json j = new Json();
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        if (mbItemStock.getAdjustment() != null && mbItemStock.getAdjustment() != 0) {
            mbItemStockService.editStockAndBalance(mbItemStock, sessionInfo.getId());
            j.setSuccess(true);
            j.setMsg("编辑成功！");
        } else {
            j.setSuccess(false);
            j.setMsg("调整量不能为空！！");
        }
        return j;
    }

    @RequestMapping("/editStockQuantityPage")
    public String editStockQuantity(HttpServletRequest request, Integer id) {
        MbItemStock mbItemStock = mbItemStockService.get(id);
        mbItemStockService.addWarehouseAndItemInfo(mbItemStock);
        request.setAttribute("mbItemStock", mbItemStock);
        return "/mbitemstock/mbItemStockEditQuantity";
    }
    /**
     * 对库存大盘的数量进行修改
     * @param mbItemStock
     * @param session
     * @return
     */
    @RequestMapping("/editStockQuantity")
    @ResponseBody
    public Json editStockQuantity(MbItemStock mbItemStock, HttpSession session) {
        Json j = edit(mbItemStock,session);
        return j;
    }
}
