package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.FmOptions;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.FmOptionsServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * FmOptions管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/fmOptionsController")
public class FmOptionsController extends BaseController {

	@Autowired
	private FmOptionsServiceI fmOptionsService;


	/**
	 * 跳转到FmOptions管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/fmoptions/fmOptions";
	}

	/**
	 * 获取FmOptions数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(FmOptions fmOptions, PageHelper ph) {
		return fmOptionsService.dataGrid(fmOptions, ph);
	}
	/**
	 * 获取FmOptions数据表格excel
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
	public void download(FmOptions fmOptions, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(fmOptions,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加FmOptions页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		FmOptions fmOptions = new FmOptions();
		fmOptions.setId(UUID.randomUUID().toString());
		return "/fmoptions/fmOptionsAdd";
	}

	/**
	 * 添加FmOptions
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(FmOptions fmOptions) {
		Json j = new Json();		
		fmOptionsService.add(fmOptions);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到FmOptions查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		FmOptions fmOptions = fmOptionsService.get(id);
		request.setAttribute("fmOptions", fmOptions);
		return "/fmoptions/fmOptionsView";
	}

	/**
	 * 跳转到FmOptions修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		FmOptions fmOptions = fmOptionsService.get(id);
		request.setAttribute("fmOptions", fmOptions);
		return "/fmoptions/fmOptionsEdit";
	}

	/**
	 * 修改FmOptions
	 * 
	 * @param fmOptions
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(FmOptions fmOptions) {
		Json j = new Json();		
		fmOptionsService.edit(fmOptions);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除FmOptions
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		fmOptionsService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
