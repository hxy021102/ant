package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.bx.ant.service.SupplierServiceI;

import com.bx.ant.pageModel.Supplier;
import com.mobian.util.ConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * Supplier管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/supplierController")
public class SupplierController extends BaseController {
    @Resource
	private SupplierServiceI supplierService;


	/**
	 * 跳转到Supplier管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/supplier/supplier";
	}

	/**
	 * 获取Supplier数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(Supplier supplier, PageHelper ph) {
		return supplierService.dataGrid(supplier, ph);
	}
	/**
	 * 获取Supplier数据表格excel
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
	public void download(Supplier supplier, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(supplier,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加Supplier页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Supplier supplier = new Supplier();
		String loginId = sessionInfo.getId();
		supplier.setLoginId(loginId);
		request.setAttribute("supplier",supplier);
		return "/supplier/supplierAdd";
	}

	/**
	 * 添加Supplier
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Supplier supplier) {
		Json j = new Json();		
		supplierService.add(supplier);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到Supplier查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		Supplier supplier = supplierService.get(id);
		request.setAttribute("supplier", supplier);
		return "/supplier/supplierView";
	}

	/**
	 * 跳转到Supplier修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		Supplier supplier = supplierService.get(id);
		request.setAttribute("supplier", supplier);
		return "/supplier/supplierEdit";
	}

	/**
	 * 修改Supplier
	 * 
	 * @param supplier
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Supplier supplier) {
		Json j = new Json();		
		supplierService.edit(supplier);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除Supplier
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		supplierService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/selectQuery")
	@ResponseBody
	public List<Tree> query(String q) {
		Supplier supplier = new Supplier();
		List<Tree> lt = new ArrayList<Tree>();
		if (!F.empty(q)) {
			supplier.setName(q);
		} else {
			return lt;
		}
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		ph.setPage(100);
		DataGrid suppliers = supplierService.dataGrid(supplier, ph);
		List<Supplier> rows = suppliers.getRows();
		if (!CollectionUtils.isEmpty(rows)) {

			for (Supplier d : rows) {
				Tree tree = new Tree();
				tree.setId(d.getId() + "");
				tree.setText(d.getName());
				tree.setParentName(d.getContacter());
				lt.add(tree);
			}
		}
		return lt;
	}

}
