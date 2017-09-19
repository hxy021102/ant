package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbSupplierFinanceLogServiceI;
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
 * MbSupplierFinanceLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbSupplierFinanceLogController")
public class MbSupplierFinanceLogController extends BaseController {

	@Autowired
	private MbSupplierFinanceLogServiceI mbSupplierFinanceLogService;


	/**
	 * 跳转到MbSupplierFinanceLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbsupplierfinancelog/mbSupplierFinanceLog";
	}

	/**
	 * 获取MbSupplierFinanceLog数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbSupplierFinanceLog mbSupplierFinanceLog, PageHelper ph) {
		return mbSupplierFinanceLogService.dataGrid(mbSupplierFinanceLog, ph);
	}
	/**
	 * 获取MbSupplierFinanceLog数据表格excel
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
	public void download(MbSupplierFinanceLog mbSupplierFinanceLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbSupplierFinanceLog,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbSupplierFinanceLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbSupplierFinanceLog mbSupplierFinanceLog = new MbSupplierFinanceLog();
		return "/mbsupplierfinancelog/mbSupplierFinanceLogAdd";
	}

	/**
	 * 添加MbSupplierFinanceLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbSupplierFinanceLog mbSupplierFinanceLog) {
		Json j = new Json();
		mbSupplierFinanceLogService.add(mbSupplierFinanceLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbSupplierFinanceLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbSupplierFinanceLog mbSupplierFinanceLog = mbSupplierFinanceLogService.get(id);
		request.setAttribute("mbSupplierFinanceLog", mbSupplierFinanceLog);
		return "/mbsupplierfinancelog/mbSupplierFinanceLogView";
	}

	/**
	 * 跳转到MbSupplierFinanceLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbSupplierFinanceLog mbSupplierFinanceLog = mbSupplierFinanceLogService.get(id);
		request.setAttribute("mbSupplierFinanceLog", mbSupplierFinanceLog);
		return "/mbsupplierfinancelog/mbSupplierFinanceLogEdit";
	}

	/**
	 * 修改MbSupplierFinanceLog
	 * 
	 * @param mbSupplierFinanceLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbSupplierFinanceLog mbSupplierFinanceLog) {
		Json j = new Json();
		mbSupplierFinanceLogService.edit(mbSupplierFinanceLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbSupplierFinanceLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbSupplierFinanceLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
