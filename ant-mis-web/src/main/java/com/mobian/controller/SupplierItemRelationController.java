package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.SupplierItemRelation;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.SupplierItemRelationServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * SupplierItemRelation管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/supplierItemRelationController")
public class SupplierItemRelationController extends BaseController {

	private SupplierItemRelationServiceI supplierItemRelationService;


	/**
	 * 跳转到SupplierItemRelation管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/supplieritemrelation/supplierItemRelation";
	}

	/**
	 * 获取SupplierItemRelation数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(SupplierItemRelation supplierItemRelation, PageHelper ph) {
		return supplierItemRelationService.dataGrid(supplierItemRelation, ph);
	}
	/**
	 * 获取SupplierItemRelation数据表格excel
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
	public void download(SupplierItemRelation supplierItemRelation, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(supplierItemRelation,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加SupplierItemRelation页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		SupplierItemRelation supplierItemRelation = new SupplierItemRelation();
		return "/supplieritemrelation/supplierItemRelationAdd";
	}

	/**
	 * 添加SupplierItemRelation
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(SupplierItemRelation supplierItemRelation) {
		Json j = new Json();		
		supplierItemRelationService.add(supplierItemRelation);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到SupplierItemRelation查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		SupplierItemRelation supplierItemRelation = supplierItemRelationService.get(id);
		request.setAttribute("supplierItemRelation", supplierItemRelation);
		return "/supplieritemrelation/supplierItemRelationView";
	}

	/**
	 * 跳转到SupplierItemRelation修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		SupplierItemRelation supplierItemRelation = supplierItemRelationService.get(id);
		request.setAttribute("supplierItemRelation", supplierItemRelation);
		return "/supplieritemrelation/supplierItemRelationEdit";
	}

	/**
	 * 修改SupplierItemRelation
	 * 
	 * @param supplierItemRelation
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(SupplierItemRelation supplierItemRelation) {
		Json j = new Json();		
		supplierItemRelationService.edit(supplierItemRelation);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除SupplierItemRelation
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		supplierItemRelationService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
