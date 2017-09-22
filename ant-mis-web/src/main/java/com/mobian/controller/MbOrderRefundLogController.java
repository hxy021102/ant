package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.MbOrderRefundLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbOrderRefundLogServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbOrderRefundLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbOrderRefundLogController")
public class MbOrderRefundLogController extends BaseController {

	@Autowired
	private MbOrderRefundLogServiceI mbOrderRefundLogService;


	/**
	 * 跳转到MbOrderRefundLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mborderrefundlog/mbOrderRefundLog";
	}

	/**
	 * 获取MbOrderRefundLog数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbOrderRefundLog mbOrderRefundLog, PageHelper ph) {
		return mbOrderRefundLogService.dataGrid(mbOrderRefundLog, ph);
	}
	/**
	 * 获取MbOrderRefundLog数据表格excel
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
	public void download(MbOrderRefundLog mbOrderRefundLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbOrderRefundLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbOrderRefundLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbOrderRefundLog mbOrderRefundLog = new MbOrderRefundLog();
		return "/mborderrefundlog/mbOrderRefundLogAdd";
	}

	/**
	 * 添加MbOrderRefundLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbOrderRefundLog mbOrderRefundLog) {
		Json j = new Json();		
		mbOrderRefundLogService.add(mbOrderRefundLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbOrderRefundLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbOrderRefundLog mbOrderRefundLog = mbOrderRefundLogService.get(id);
		request.setAttribute("mbOrderRefundLog", mbOrderRefundLog);
		return "/mborderrefundlog/mbOrderRefundLogView";
	}

	/**
	 * 跳转到MbOrderRefundLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbOrderRefundLog mbOrderRefundLog = mbOrderRefundLogService.get(id);
		request.setAttribute("mbOrderRefundLog", mbOrderRefundLog);
		return "/mborderrefundlog/mbOrderRefundLogEdit";
	}

	/**
	 * 修改MbOrderRefundLog
	 * 
	 * @param mbOrderRefundLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbOrderRefundLog mbOrderRefundLog) {
		Json j = new Json();		
		mbOrderRefundLogService.edit(mbOrderRefundLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbOrderRefundLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbOrderRefundLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}


}
