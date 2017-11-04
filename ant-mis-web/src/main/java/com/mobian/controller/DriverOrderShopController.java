package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.pageModel.DriverOrderShopView;
import com.bx.ant.service.DriverOrderShopServiceI;
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
 * DriverOrderShop管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/driverOrderShopController")
public class DriverOrderShopController extends BaseController {

	@Autowired
	private DriverOrderShopServiceI driverOrderShopService;


	/**
	 * 跳转到DriverOrderShop管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/driverordershop/driverOrderShop";
	}

	/**
	 * 获取DriverOrderShop数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DriverOrderShop driverOrderShop, PageHelper ph) {
		return driverOrderShopService.dataGridView(driverOrderShop, ph);
	}
	/**
	 * 获取DriverOrderShop数据表格excel
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
	public void download(DriverOrderShop driverOrderShop, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(driverOrderShop,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DriverOrderShop页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DriverOrderShop driverOrderShop = new DriverOrderShop();
		return "/driverordershop/driverOrderShopAdd";
	}

	/**
	 * 添加DriverOrderShop
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DriverOrderShop driverOrderShop) {
		Json j = new Json();		
		driverOrderShopService.add(driverOrderShop);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DriverOrderShop查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Long id) {
		DriverOrderShopView driverOrderShopView = driverOrderShopService.getView(id);
		request.setAttribute("driverOrderShop", driverOrderShopView);
		return "/driverordershop/driverOrderShopView";
	}

	/**
	 * 跳转到DriverOrderShop修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		DriverOrderShop driverOrderShop = driverOrderShopService.get(id);
		request.setAttribute("driverOrderShop", driverOrderShop);
		return "/driverordershop/driverOrderShopEdit";
	}

	/**
	 * 修改DriverOrderShop
	 * 
	 * @param driverOrderShop
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DriverOrderShop driverOrderShop) {
		Json j = new Json();		
		driverOrderShopService.edit(driverOrderShop);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DriverOrderShop
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Long id) {
		Json j = new Json();
		driverOrderShopService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
