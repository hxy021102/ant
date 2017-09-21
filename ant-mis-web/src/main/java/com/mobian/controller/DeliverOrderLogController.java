package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.DeliverOrderLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderLogServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * DeliverOrderLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/deliverOrderLogController")
public class DeliverOrderLogController extends BaseController {

	private DeliverOrderLogServiceI deliverOrderLogService;


	/**
	 * 跳转到DeliverOrderLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/deliverorderlog/deliverOrderLog";
	}

	/**
	 * 获取DeliverOrderLog数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DeliverOrderLog deliverOrderLog, PageHelper ph) {
		return deliverOrderLogService.dataGrid(deliverOrderLog, ph);
	}
	/**
	 * 获取DeliverOrderLog数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(DeliverOrderLog deliverOrderLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(deliverOrderLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DeliverOrderLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DeliverOrderLog deliverOrderLog = new DeliverOrderLog();
		return "/deliverorderlog/deliverOrderLogAdd";
	}

	/**
	 * 添加DeliverOrderLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DeliverOrderLog deliverOrderLog) {
		Json j = new Json();		
		deliverOrderLogService.add(deliverOrderLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DeliverOrderLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DeliverOrderLog deliverOrderLog = deliverOrderLogService.get(id);
		request.setAttribute("deliverOrderLog", deliverOrderLog);
		return "/deliverorderlog/deliverOrderLogView";
	}

	/**
	 * 跳转到DeliverOrderLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DeliverOrderLog deliverOrderLog = deliverOrderLogService.get(id);
		request.setAttribute("deliverOrderLog", deliverOrderLog);
		return "/deliverorderlog/deliverOrderLogEdit";
	}

	/**
	 * 修改DeliverOrderLog
	 * 
	 * @param deliverOrderLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DeliverOrderLog deliverOrderLog) {
		Json j = new Json();		
		deliverOrderLogService.edit(deliverOrderLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DeliverOrderLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		deliverOrderLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
