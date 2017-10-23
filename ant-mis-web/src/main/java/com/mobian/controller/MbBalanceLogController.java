package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.concurrent.ThreadCache;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.ConvertNameUtil;
import org.apache.commons.collections.CollectionUtils;
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
 * MbBalanceLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbBalanceLogController")
public class MbBalanceLogController extends BaseController {

	@Autowired
	private MbBalanceLogServiceI mbBalanceLogService;

	@Autowired
	private MbShopServiceI mbShopService;

	@Autowired
	private MbBalanceServiceI mbBalanceService;


	/**
	 * 跳转到MbBalanceLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbbalancelog/mbBalanceLog";
	}

	/**
	 * 获取MbBalanceLog数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbBalanceLog mbBalanceLog, PageHelper ph) {
		return mbBalanceLogService.dataGrid(mbBalanceLog,ph);
	}
	/**
	 * 获取MbBalanceLog数据表格excel
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
	public void download(MbBalanceLog mbBalanceLog,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
		PageHelper ph = new PageHelper();
		ph.setRows(5000);
		ph.setHiddenTotal(true);
		DataGrid dg = dataGrid(mbBalanceLog, ph);
		List<MbBalanceLogExport> mbBalanceLogExports = new ArrayList<MbBalanceLogExport>();
		List<MbBalanceLog> mbBalanceLogs = dg.getRows();
		ThreadCache mbShopCache = new ThreadCache(MbShop.class) {
			@Override
			protected Object handle(Object key) {
				return mbShopService.getFromCache((Integer) key);
			}
		};
		ThreadCache mbBalanceCache = new ThreadCache(MbBalance.class) {
			@Override
			protected Object handle(Object key) {
				return mbBalanceService.get((Integer) key);
			}
		};
		try {
			for (MbBalanceLog balanceLog : mbBalanceLogs) {
				MbBalanceLogExport export = new MbBalanceLogExport();
				BeanUtils.copyProperties(balanceLog, export);
				mbBalanceLogExports.add(export);
				export.setRefTypeName(ConvertNameUtil.getString(balanceLog.getRefType()));

				MbBalance mbBalance = mbBalanceCache.getValue(balanceLog.getBalanceId());
				if (mbBalance != null) {
					MbShop mbShop = mbShopCache.getValue(mbBalance.getRefId());
					if (mbShop != null) {
						export.setShopId(mbShop.getId());
						export.setShopName(mbShop.getName());
					}
				}
				//mbBalanceLogExports.add(export);
			}
		}finally {
			ThreadCache.clear();
		}
		dg.setRows(mbBalanceLogExports);
		List<Colum> colums = new ArrayList<Colum>();
		Colum colum = new Colum();
		colum.setField("shopId");
		colum.setTitle("门店ID");
		colums.add(colum);
		colum = new Colum();
		colum.setField("shopName");
		colum.setTitle("门店名称");
		colums.add(colum);
		colum = new Colum();
		colum.setField("refTypeName");
		colum.setTitle("类型");
		colums.add(colum);
		colum = new Colum();
		colum.setField("updatetime");
		colum.setTitle("交易时间");
		colums.add(colum);
		colum = new Colum();
		colum.setField("amount");
		colum.setTitle("金额");
		colums.add(colum);
		colum = new Colum();
		colum.setField("reason");
		colum.setTitle("原因");
		colums.add(colum);
		colum = new Colum();
		colum.setField("remark");
		colum.setTitle("备注");
		colums.add(colum);
		colum = new Colum();
		colum.setField("refId");
		colum.setTitle("refId");
		colums.add(colum);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbBalanceLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbBalanceLog mbBalanceLog = new MbBalanceLog();
		return "/mbbalancelog/mbBalanceLogAdd";
	}

	/**
	 * 添加MbBalanceLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbBalanceLog mbBalanceLog) {
		Json j = new Json();		
		mbBalanceLogService.add(mbBalanceLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	@RequestMapping("/addCashPage")
	public String addCashPage(HttpServletRequest request,Integer balanceId) {
		//MbBalanceLog mbBalanceLog = new MbBalanceLog();
		request.setAttribute("balanceId", balanceId);
		return "/mbbalancelog/addShopCash";
	}

	/**
	 * 添加MbBalanceLog 押金
	 *
	 * @return
	 */
	@RequestMapping("/addCash")
	@ResponseBody
	public Json addCash(MbBalanceLog mbBalanceLog) {
		Json j = new Json();
		mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");
		return j;
	}

	/**
	 * 跳转到MbBalanceLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbBalanceLog mbBalanceLog = mbBalanceLogService.get(id);
		request.setAttribute("mbBalanceLog", mbBalanceLog);
		return "/mbbalancelog/mbBalanceLogView";
	}

	/**
	 * 跳转到MbBalanceLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbBalanceLog mbBalanceLog = mbBalanceLogService.get(id);
		request.setAttribute("mbBalanceLog", mbBalanceLog);
		return "/mbbalancelog/mbBalanceLogEdit";
	}

	/**
	 * 修改MbBalanceLog
	 * 
	 * @param mbBalanceLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbBalanceLog mbBalanceLog) {
		Json j = new Json();		
		mbBalanceLogService.edit(mbBalanceLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbBalanceLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbBalanceLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 *导出mbBalanceLog数据表格
	 * @param mbBalanceLog
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
	@RequestMapping("/downloadBalanceLog")
	public void downloadBalanceLog(MbBalanceLog mbBalanceLog, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
		ph.setRows(0);
		DataGrid dg = mbBalanceLogService.dataGridBalanceLogDownload(mbBalanceLog, ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		if (CollectionUtils.isNotEmpty(colums)) {
			for (Colum colum : colums) {
				if ("amount".equals(colum.getField())) {
					colum.setField("amountElement");
				}
			}
		}
		downloadTable(colums, dg, response);
	}

	/**
	 * 获取mbBalanceLog数据和名店名称
	 * @param mbBalanceLog
	 * @param ph
	 * @return
	 */
	@RequestMapping("/dataGridFlow")
	@ResponseBody
	public DataGrid dataGridFlow(MbBalanceLog mbBalanceLog, PageHelper ph) {
		if (mbBalanceLog.getUpdatetimeBegin() == null || mbBalanceLog.getUpdatetimeEnd() == null)
			return new DataGrid();
		if("".equals(mbBalanceLog.getRefTypes())){
			mbBalanceLog.setRefTypes(null);
		}
		return mbBalanceLogService.dataGridWithShopName(mbBalanceLog, ph);
	}

}
