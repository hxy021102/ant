package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.MbSupplierBankAccount;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbSupplierBankAccountServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbSupplierBankAccount管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbSupplierBankAccountController")
public class MbSupplierBankAccountController extends BaseController {

	@Autowired
	private MbSupplierBankAccountServiceI mbSupplierBankAccountService;


	/**
	 * 跳转到MbSupplierBankAccount管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbsupplierbankaccount/mbSupplierBankAccount";
	}

	/**
	 * 获取MbSupplierBankAccount数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbSupplierBankAccount mbSupplierBankAccount, PageHelper ph) {
		return mbSupplierBankAccountService.dataGrid(mbSupplierBankAccount, ph);
	}
	/**
	 * 获取MbSupplierBankAccount数据表格excel
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
	public void download(MbSupplierBankAccount mbSupplierBankAccount, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbSupplierBankAccount,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbSupplierBankAccount页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request, Integer supplierId) {
		request.setAttribute("supplierId",supplierId);
		MbSupplierBankAccount mbSupplierBankAccount = new MbSupplierBankAccount();
		return "/mbsupplierbankaccount/mbSupplierBankAccountAdd";
	}

	/**
	 * 添加MbSupplierBankAccount
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbSupplierBankAccount mbSupplierBankAccount) {
		Json j = new Json();		
		mbSupplierBankAccountService.add(mbSupplierBankAccount);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbSupplierBankAccount查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbSupplierBankAccount mbSupplierBankAccount = mbSupplierBankAccountService.get(id);
		request.setAttribute("mbSupplierBankAccount", mbSupplierBankAccount);
		return "/mbsupplierbankaccount/mbSupplierBankAccountView";
	}

	/**
	 * 跳转到MbSupplierBankAccount修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbSupplierBankAccount mbSupplierBankAccount = mbSupplierBankAccountService.get(id);
		request.setAttribute("mbSupplierBankAccount", mbSupplierBankAccount);
		return "/mbsupplierbankaccount/mbSupplierBankAccountEdit";
	}

	/**
	 * 修改MbSupplierBankAccount
	 * 
	 * @param mbSupplierBankAccount
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbSupplierBankAccount mbSupplierBankAccount) {
		Json j = new Json();		
		mbSupplierBankAccountService.edit(mbSupplierBankAccount);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbSupplierBankAccount
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbSupplierBankAccountService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
