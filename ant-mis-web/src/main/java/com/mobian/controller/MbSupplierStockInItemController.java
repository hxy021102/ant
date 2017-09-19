package com.mobian.controller;

import com.mobian.absx.F;
import com.mobian.concurrent.ThreadCache;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * MbSupplierStockInItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbSupplierStockInItemController")
public class MbSupplierStockInItemController extends BaseController {

	@Autowired
	private MbSupplierStockInItemServiceI mbSupplierStockInItemService;
	@Autowired
	private MbSupplierStockInServiceI mbSupplierStockInService;

	@Autowired
	private MbItemServiceI mbItemService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private MbWarehouseServiceI mbWarehouseService;

	@Autowired
	private MbSupplierOrderServiceI mbSupplierOrderService;

	@Autowired
	private MbSupplierContractServiceI mbSupplierContractService;

	@Autowired
	private MbSupplierServiceI mbSupplierService;

	@Autowired
	private MbSupplierFinanceLogServiceI mbSupplierFinanceLogService;

	/**
	 * 跳转到MbSupplierStockInItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbsupplierstockinitem/mbSupplierStockInItem";
	}

	/**
	 * 获取MbSupplierStockInItem数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbSupplierStockInItem mbSupplierStockInItem, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<MbSupplierStockInItem> list = mbSupplierStockInItemService.query(mbSupplierStockInItem);
		dg.setRows(list);
		return dg;
	}
	/**
	 * 获取MbSupplierStockInItem数据表格excel
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
	public void download(MbSupplierStockInItem mbSupplierStockInItem,String downloadFields,HttpServletResponse response,Integer id) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		PageHelper ph = new PageHelper();
		ph.setRows(5000);
		ph.setHiddenTotal(true);
		DataGrid dg = mbSupplierStockInItemService.dataGrid(mbSupplierStockInItem, ph);
		List<MbSupplierStockInItem> mbSupplierStockInItems = dg.getRows();
		ThreadCache mbItemCache = new ThreadCache(MbItem.class) {
			@Override
			protected Object handle(Object key) {
				return mbItemService.getFromCache((Integer) key);
			}
		};
		ThreadCache mbStockInCache = new ThreadCache(MbSupplierStockIn.class) {
			@Override
			protected Object handle(Object key) {
				return mbSupplierStockInService.get((Integer) key);
			}
		};

		ThreadCache mbSupplierOrderCache = new ThreadCache(MbSupplierOrder.class) {
			@Override
			protected Object handle(Object key) {
				return mbSupplierOrderService.get((Integer) key);
			}
		};

		ThreadCache mbSupplierContractCache = new ThreadCache(MbSupplierContract.class) {
			@Override
			protected Object handle(Object key) {
				return mbSupplierContractService.get((Integer) key);
			}
		};
		ThreadCache mbSupplierCache = new ThreadCache(MbSupplier.class) {
			@Override
			protected Object handle(Object key) {
				return mbSupplierService.get((Integer) key);
			}
		};

		ThreadCache financeCache = new ThreadCache(MbSupplierFinanceLog.class) {
			@Override
			protected Object handle(Object key) {
				MbSupplierFinanceLog request = new MbSupplierFinanceLog();
				request.setSupplierStockInId((Integer) key);
				List<MbSupplierFinanceLog> mbSupplierFinanceLogs = mbSupplierFinanceLogService.query(request);
				return mbSupplierFinanceLogs != null && mbSupplierFinanceLogs.size() > 0 ? mbSupplierFinanceLogs.get(0) : null;
			}
		};


		ThreadCache userCache = new ThreadCache(User.class) {
			@Override
			protected Object handle(Object key) {
				return userService.get((String) key);
			}
		};
		List<MbSupplierStockInItemExport> mbSupplierStockInItemExports = new ArrayList<MbSupplierStockInItemExport>();
		try {
			for (MbSupplierStockInItem itemExport : mbSupplierStockInItems) {
				MbSupplierStockIn mbSupplierStockIn = mbStockInCache.getValue(itemExport.getSupplierStockInId());
				if (mbSupplierStockIn == null || mbSupplierStockIn.getId() == null) continue;
				MbSupplierStockInItemExport mbOrderItemExport = new MbSupplierStockInItemExport();
				BeanUtils.copyProperties(itemExport, mbOrderItemExport);
				mbSupplierStockInItemExports.add(mbOrderItemExport);
				MbWarehouse mbWarehouse = mbWarehouseService.getFromCache(mbSupplierStockIn.getWarehouseId());
				if (mbWarehouse != null)
					mbOrderItemExport.setWarehouseName(mbWarehouse.getName());

				if (!F.empty(mbSupplierStockIn.getDriverLoginId())) {
					mbSupplierStockIn.setDriverLoginId(mbSupplierStockIn.getDriverLoginId());
					User user = userCache.getValue(mbSupplierStockIn.getDriverLoginId());
					if (user != null)
						mbOrderItemExport.setDriverName(user.getNickname());
				}

				MbItem mbItem = mbItemCache.getValue(itemExport.getItemId());
				if (mbItem != null) {
					mbOrderItemExport.setItemCode(mbItem.getCode());
					mbOrderItemExport.setItemName(mbItem.getName());
				}

				mbOrderItemExport.setPayStatusName(Application.getString(mbSupplierStockIn.getPayStatus()));
				mbOrderItemExport.setInvoiceStatusName(Application.getString(mbSupplierStockIn.getInvoiceStatus()));

				MbSupplierOrder mbSupplierOrder = mbSupplierOrderCache.getValue(mbSupplierStockIn.getSupplierOrderId());
				mbOrderItemExport.setOrderId(mbSupplierStockIn.getSupplierOrderId()+"");
				if (mbSupplierOrder != null) {
					MbSupplierContract mbSupplierContract = mbSupplierContractCache.getValue(mbSupplierOrder.getSupplierContractId());
					MbSupplier mbSupplier = mbSupplierCache.getValue(mbSupplierOrder.getSupplierId());
					if (mbSupplierContract != null) {
						mbOrderItemExport.setContractCode(mbSupplierContract.getCode());
						mbOrderItemExport.setRate(mbSupplierContract.getRate());
					}
					if (mbSupplier != null) {
						mbOrderItemExport.setSupplierName(mbSupplier.getName());
					}
				}

				MbSupplierFinanceLog mbSupplierFinanceLog = financeCache.getValue(mbSupplierStockIn.getId());
				if(mbSupplierFinanceLog!=null){
					//invoiceNo
				}

			}
		} finally {
			ThreadCache.clear();
		}
		dg.setRows(mbSupplierStockInItemExports);
		List<Colum> colums = new ArrayList<Colum>();
		Colum colum = new Colum();
		colum.setField("supplierName");
		colum.setTitle("供应商名称");
		colums.add(colum);
		colum = new Colum();
		colum.setField("orderId");
		colum.setTitle("采购订单ID");
		colums.add(colum);
		colum = new Colum();
		colum.setField("updatetime");
		colum.setTitle("入库时间");
		colums.add(colum);
		colum = new Colum();
		colum.setField("itemId");
		colum.setTitle("商品Id");
		colums.add(colum);
		colum = new Colum();
		colum.setField("itemCode");
		colum.setTitle("商品编码");
		colums.add(colum);
		colum = new Colum();
		colum.setField("itemName");
		colum.setTitle("商品名称");
		colums.add(colum);
		colum = new Colum();
		colum.setField("price");
		colum.setTitle("价格");
		colums.add(colum);
		colum = new Colum();
		colum.setField("quantity");
		colum.setTitle("入库数量");
		colums.add(colum);
		colum = new Colum();
		colum.setField("driverLoginId");
		colum.setTitle("司机编码");
		colums.add(colum);
		colum = new Colum();
		colum.setField("driverName");
		colum.setTitle("司机名称");
		colums.add(colum);
		colum = new Colum();
		colum.setField("payStatusName");
		colum.setTitle("付款状态");
		colums.add(colum);
		colum = new Colum();
		colum.setField("invoiceStatusName");
		colum.setTitle("开票状态");
		colums.add(colum);

		colum = new Colum();
		colum.setField("warehouseName");
		colum.setTitle("仓库名称");
		colums.add(colum);

		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbSupplierStockInItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbSupplierStockInItem mbSupplierStockInItem = new MbSupplierStockInItem();
		return "/mbsupplierstockinitem/mbSupplierStockInItemAdd";
	}

	/**
	 * 添加MbSupplierStockInItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbSupplierStockInItem mbSupplierStockInItem) {
		Json j = new Json();
		mbSupplierStockInItemService.add(mbSupplierStockInItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbSupplierStockInItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbSupplierStockInItem mbSupplierStockInItem = mbSupplierStockInItemService.get(id);
		request.setAttribute("mbSupplierStockInItem", mbSupplierStockInItem);
		return "/mbsupplierstockinitem/mbSupplierStockInItemView";
	}

	/**
	 * 跳转到MbSupplierStockInItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbSupplierStockInItem mbSupplierStockInItem = mbSupplierStockInItemService.get(id);
		request.setAttribute("mbSupplierStockInItem", mbSupplierStockInItem);
		return "/mbsupplierstockinitem/mbSupplierStockInItemEdit";
	}

	/**
	 * 修改MbSupplierStockInItem
	 * 
	 * @param mbSupplierStockInItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbSupplierStockInItem mbSupplierStockInItem) {
		Json j = new Json();
		mbSupplierStockInItemService.edit(mbSupplierStockInItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbSupplierStockInItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbSupplierStockInItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
