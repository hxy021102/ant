package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bx.ant.pageModel.DriverOrderShopBill;
import com.bx.ant.service.DriverOrderShopBillServiceI;
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
 * DriverOrderShopBill管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/driverOrderShopBillController")
public class DriverOrderShopBillController extends BaseController {

	@Autowired
	private DriverOrderShopBillServiceI driverOrderShopBillService;


	/**
	 * 跳转到DriverOrderShopBill管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/driverordershopbill/driverOrderShopBill";
	}

	/**
	 * 获取DriverOrderShopBill数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DriverOrderShopBill driverOrderShopBill, PageHelper ph) {
		return driverOrderShopBillService.dataGridView(driverOrderShopBill, ph);
	}
	/**
	 * 获取DriverOrderShopBill数据表格excel
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
	public void download(DriverOrderShopBill driverOrderShopBill, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(driverOrderShopBill,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DriverOrderShopBill页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DriverOrderShopBill driverOrderShopBill = new DriverOrderShopBill();
		return "/driverordershopbill/driverOrderShopBillAdd";
	}

	/**
	 * 添加DriverOrderShopBill
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DriverOrderShopBill driverOrderShopBill) {
		Json j = new Json();		
		driverOrderShopBillService.add(driverOrderShopBill);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DriverOrderShopBill查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DriverOrderShopBill driverOrderShopBill = driverOrderShopBillService.getView(id);
		request.setAttribute("driverOrderShopBill", driverOrderShopBill);
		return "/driverordershopbill/driverOrderShopBillView";
	}

	/**
	 * 跳转到DriverOrderShopBill修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DriverOrderShopBill driverOrderShopBill = driverOrderShopBillService.get(id);
		request.setAttribute("driverOrderShopBill", driverOrderShopBill);
		return "/driverordershopbill/driverOrderShopBillEdit";
	}

	/**
	 * 修改DriverOrderShopBill
	 * 
	 * @param driverOrderShopBill
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DriverOrderShopBill driverOrderShopBill) {
		Json j = new Json();		
		driverOrderShopBillService.edit(driverOrderShopBill);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DriverOrderShopBill
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		driverOrderShopBillService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
