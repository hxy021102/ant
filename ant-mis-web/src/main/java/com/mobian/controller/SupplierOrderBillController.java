package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.bx.ant.pageModel.Supplier;
import com.bx.ant.service.SupplierServiceI;
import com.mobian.pageModel.*;
import com.bx.ant.pageModel.SupplierOrderBill;
import com.bx.ant.service.SupplierOrderBillServiceI;

import com.mobian.service.UserServiceI;
import com.mobian.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * SupplierOrderBill管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/supplierOrderBillController")
public class SupplierOrderBillController extends BaseController {

	@Resource
	private SupplierOrderBillServiceI supplierOrderBillService;
	@Resource
	private SupplierServiceI supplierService;
    @Autowired
	private UserServiceI userService;


	/**
	 * 跳转到SupplierOrderBill管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/supplierorderbill/supplierOrderBill";
	}

	/**
	 * 获取SupplierOrderBill数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(SupplierOrderBill supplierOrderBill, PageHelper ph,HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		User user = userService.get(sessionInfo.getId());
		if ("URT02".equals(user.getRefType())) {
			supplierOrderBill.setSupplierId(Integer.parseInt(user.getRefId()));
		}
		return supplierOrderBillService.dataGrid(supplierOrderBill, ph);
	}
	/**
	 * 获取SupplierOrderBill数据表格excel
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
	public void download(SupplierOrderBill supplierOrderBill, PageHelper ph,String downloadFields,HttpServletResponse response,HttpSession session) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(supplierOrderBill,ph,session);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加SupplierOrderBill页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		SupplierOrderBill supplierOrderBill = new SupplierOrderBill();
		return "/supplierorderbill/supplierOrderBillAdd";
	}

	/**
	 * 添加SupplierOrderBill
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(SupplierOrderBill supplierOrderBill) {
		Json j = new Json();		
		supplierOrderBillService.add(supplierOrderBill);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到SupplierOrderBill查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Long id, Integer isView) {
		SupplierOrderBill supplierOrderBill = supplierOrderBillService.get(id);
		if(supplierOrderBill.getSupplierId() !=null) {
			Supplier supplier = supplierService.get(supplierOrderBill.getSupplierId());
			supplierOrderBill.setSupplierName(supplier.getName());
		}
		request.setAttribute("isView",isView);
		request.setAttribute("supplierOrderBill", supplierOrderBill);
		return "/supplierorderbill/supplierOrderBillView";
	}

	/**
	 * 跳转到SupplierOrderBill修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		SupplierOrderBill supplierOrderBill = supplierOrderBillService.get(id);
		request.setAttribute("supplierOrderBill", supplierOrderBill);
		return "/supplierorderbill/supplierOrderBillEdit";
	}

	/**
	 * 修改SupplierOrderBill
	 * 
	 * @param supplierOrderBill
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(SupplierOrderBill supplierOrderBill) {
		Json j = new Json();		
		supplierOrderBillService.edit(supplierOrderBill);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除SupplierOrderBill
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Long id) {
		Json j = new Json();
		supplierOrderBillService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}
	//结算审核
	@RequestMapping("/editStatus")
	@ResponseBody
	public Json editStatus(SupplierOrderBill supplierOrderBill, Boolean isAgree) {
		Json j = new Json();
		try {
		Integer result = supplierOrderBillService.editBillStatus(supplierOrderBill,isAgree);
		 if (result > 0) {
			 j.setSuccess(true);
			 j.setMsg("审核完成!");
		 }
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("重复审核!");
			e.printStackTrace();
		}
		return  j;
	}

}
