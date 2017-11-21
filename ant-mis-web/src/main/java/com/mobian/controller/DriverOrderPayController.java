package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DriverOrderPay;
import com.bx.ant.service.DriverOrderPayServiceI;
import com.mobian.pageModel.Colum;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * DriverOrderPay管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/driverOrderPayController")
public class DriverOrderPayController extends BaseController {

	@Resource
	private DriverOrderPayServiceI driverOrderPayService;


	/**
	 * 跳转到DriverOrderPay管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/driverorderpay/driverOrderPay";
	}

	/**
	 * 获取DriverOrderPay数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DriverOrderPay driverOrderPay, PageHelper ph) {
		return driverOrderPayService.dataGridView(driverOrderPay, ph);
	}
	/**
	 * 获取DriverOrderPay数据表格excel
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
	public void download(DriverOrderPay driverOrderPay, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(driverOrderPay,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DriverOrderPay页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DriverOrderPay driverOrderPay = new DriverOrderPay();
		return "/driverorderpay/driverOrderPayAdd";
	}

	/**
	 * 添加DriverOrderPay
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DriverOrderPay driverOrderPay) {
		Json j = new Json();		
		driverOrderPayService.add(driverOrderPay);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DriverOrderPay查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DriverOrderPay driverOrderPay = driverOrderPayService.get(id);
		request.setAttribute("driverOrderPay", driverOrderPay);
		return "/driverorderpay/driverOrderPayView";
	}

	/**
	 * 跳转到DriverOrderPay修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DriverOrderPay driverOrderPay = driverOrderPayService.get(id);
		request.setAttribute("driverOrderPay", driverOrderPay);
		return "/driverorderpay/driverOrderPayEdit";
	}

	/**
	 * 修改DriverOrderPay
	 * 
	 * @param driverOrderPay
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DriverOrderPay driverOrderPay) {
		Json j = new Json();		
		driverOrderPayService.edit(driverOrderPay);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DriverOrderPay
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		driverOrderPayService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
