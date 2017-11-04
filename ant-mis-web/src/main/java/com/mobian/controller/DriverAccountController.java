package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.bx.ant.pageModel.DriverAccount;
import com.bx.ant.pageModel.DriverAccountView;
import com.bx.ant.service.DriverAccountServiceI;
import com.mobian.controller.BaseController;
import com.mobian.pageModel.Colum;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * DriverAccount管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/driverAccountController")
public class DriverAccountController extends BaseController {

	@Autowired
	private DriverAccountServiceI driverAccountService;


	/**
	 * 跳转到DriverAccount管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/driveraccount/driverAccount";
	}

	/**
	 * 获取DriverAccount数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DriverAccount driverAccount, PageHelper ph) {
		return driverAccountService.dataGridView(driverAccount, ph);
	}
	/**
	 * 获取DriverAccount数据表格excel
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
	public void download(DriverAccount driverAccount, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(driverAccount,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DriverAccount页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DriverAccount driverAccount = new DriverAccount();
		return "/driveraccount/driverAccountAdd";
	}

	/**
	 * 添加DriverAccount
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DriverAccount driverAccount) {
		Json j = new Json();		
		driverAccountService.add(driverAccount);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DriverAccount查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DriverAccountView driverAccountView = driverAccountService.getView(id);
		request.setAttribute("driverAccount", driverAccountView);
		return "/driveraccount/driverAccountView";
	}

	/**
	 * 跳转到DriverAccount修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DriverAccount driverAccount = driverAccountService.get(id);
		request.setAttribute("driverAccount", driverAccount);
		return "/driveraccount/driverAccountEdit";
	}

	/**
	 * 修改DriverAccount
	 * 
	 * @param driverAccount
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DriverAccount driverAccount) {
		Json j = new Json();		
		driverAccountService.edit(driverAccount);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DriverAccount
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		driverAccountService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
