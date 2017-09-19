package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbItemStockLogServiceI;
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
 * MbItemStockLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbItemStockLogController")
public class MbItemStockLogController extends BaseController {

	@Autowired
	private MbItemStockLogServiceI mbItemStockLogService;


	/**
	 * 跳转到MbItemStockLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbitemstocklog/mbItemStockLog";
	}

	/**
	 * 获取MbItemStockLog数据表格
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbItemStockLog mbItemStockLog, PageHelper ph) {
		return mbItemStockLogService.dataGrid(mbItemStockLog, ph);
	}
	/**
	 * 获取MbItemStockLog数据表格excel
	 */
	@RequestMapping("/download")
	public void download(MbItemStockLog mbItemStockLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbItemStockLog,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbItemStockLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbItemStockLog mbItemStockLog = new MbItemStockLog();
		return "/mbitemstocklog/mbItemStockLogAdd";
	}

	/**
	 * 添加MbItemStockLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbItemStockLog mbItemStockLog) {
		Json j = new Json();		
		mbItemStockLogService.add(mbItemStockLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbItemStockLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		request.setAttribute("itemStockId", id);
		return "/mbitemstocklog/mbItemStockLogView";
	}

	/**
	 * 跳转到MbItemStockLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbItemStockLog mbItemStockLog = mbItemStockLogService.get(id);
		request.setAttribute("mbItemStockLog", mbItemStockLog);
		return "/mbitemstocklog/mbItemStockLogEdit";
	}

	/**
	 * 修改MbItemStockLog
	 * 
	 * @param mbItemStockLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbItemStockLog mbItemStockLog) {
		Json j = new Json();		
		mbItemStockLogService.edit(mbItemStockLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbItemStockLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbItemStockLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 跳转到MbItemStockLog变更管理页面
	 *
	 * @return
	 */
	@RequestMapping("/managerChange")
	public String managerChange(HttpServletRequest request) {
		return "/mbitemstocklog/mbItemStockLogChange";
	}

	/**
	 * 获取MbItemStockLog变更数据表格
	 */
	@RequestMapping("/dataGridChange")
	@ResponseBody
	public DataGrid dataGridChange(MbItemStockLog mbItemStockLog, PageHelper ph) {
		if (mbItemStockLog.getStockLogStartTime()==null||mbItemStockLog.getStockLogEndTime()==null) {
			return new DataGrid();
		}
		mbItemStockLog.setLogType("SL04");
		mbItemStockLog.setQuantity(0);
		return mbItemStockLogService.dataGridStockInLog(mbItemStockLog, ph);
	}

	/**
	 * 获取MbItemStockLog数据表格excel
	 */
	@RequestMapping("/downloadChange")
	public void downloadChange(MbItemStockLog mbItemStockLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		mbItemStockLog.setLogType("SL04");
		mbItemStockLog.setQuantity(0);
		DataGrid dg = dataGrid(mbItemStockLog,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}

}
