package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.FmProperties;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.FmPropertiesServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * FmProperties管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/fmPropertiesController")
public class FmPropertiesController extends BaseController {

	@Autowired
	private FmPropertiesServiceI fmPropertiesService;


	/**
	 * 跳转到FmProperties管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/fmproperties/fmProperties";
	}

	/**
	 * 获取FmProperties数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(FmProperties fmProperties, PageHelper ph) {
		return fmPropertiesService.dataGrid(fmProperties, ph);
	}
	/**
	 * 获取FmProperties数据表格excel
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
	public void download(FmProperties fmProperties, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(fmProperties,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加FmProperties页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,String goodName) {
		FmProperties fmProperties = new FmProperties();
		fmProperties.setId(UUID.randomUUID().toString());
		fmProperties.setGoodName(goodName);
		request.setAttribute("fmProperties", fmProperties);
		return "/fmproperties/fmPropertiesAdd";
	}

	/**
	 * 添加FmProperties
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(FmProperties fmProperties) {
		Json j = new Json();		
		fmPropertiesService.add(fmProperties);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到FmProperties查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		FmProperties fmProperties = fmPropertiesService.get(id);
		request.setAttribute("fmProperties", fmProperties);
		return "/fmproperties/fmPropertiesView";
	}

	/**
	 * 跳转到FmProperties修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		FmProperties fmProperties = fmPropertiesService.get(id);
		request.setAttribute("fmProperties", fmProperties);
		return "/fmproperties/fmPropertiesEdit";
	}

	/**
	 * 修改FmProperties
	 * 
	 * @param fmProperties
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(FmProperties fmProperties) {
		Json j = new Json();		
		fmPropertiesService.edit(fmProperties);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除FmProperties
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		fmPropertiesService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
