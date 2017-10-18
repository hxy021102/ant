package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.bx.ant.pageModel.DeliverOrderShopPay;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderShopPayServiceI;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * DeliverOrderShopPay管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/deliverOrderShopPayController")
public class DeliverOrderShopPayController extends BaseController {

	@Resource
	private DeliverOrderShopPayServiceI deliverOrderShopPayService;


	/**
	 * 跳转到DeliverOrderShopPay管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/deliverordershoppay/deliverOrderShopPay";
	}

	/**
	 * 获取DeliverOrderShopPay数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DeliverOrderShopPay deliverOrderShopPay, PageHelper ph) {
		return deliverOrderShopPayService.dataWithNameGrid(deliverOrderShopPay, ph);
	}
	/**
	 * 获取DeliverOrderShopPay数据表格excel
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
	public void download(DeliverOrderShopPay deliverOrderShopPay, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(deliverOrderShopPay,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DeliverOrderShopPay页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DeliverOrderShopPay deliverOrderShopPay = new DeliverOrderShopPay();
		return "/deliverordershoppay/deliverOrderShopPayAdd";
	}

	/**
	 * 添加DeliverOrderShopPay
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DeliverOrderShopPay deliverOrderShopPay) {
		Json j = new Json();		
		deliverOrderShopPayService.add(deliverOrderShopPay);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DeliverOrderShopPay查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Long id) {
		DeliverOrderShopPay deliverOrderShopPay = deliverOrderShopPayService.get(id);
		request.setAttribute("deliverOrderShopPay", deliverOrderShopPay);
		return "/deliverordershoppay/deliverOrderShopPayView";
	}

	/**
	 * 跳转到DeliverOrderShopPay修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		DeliverOrderShopPay deliverOrderShopPay = deliverOrderShopPayService.get(id);
		request.setAttribute("deliverOrderShopPay", deliverOrderShopPay);
		return "/deliverordershoppay/deliverOrderShopPayEdit";
	}

	/**
	 * 修改DeliverOrderShopPay
	 * 
	 * @param deliverOrderShopPay
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DeliverOrderShopPay deliverOrderShopPay) {
		Json j = new Json();		
		deliverOrderShopPayService.edit(deliverOrderShopPay);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DeliverOrderShopPay
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Long id) {
		Json j = new Json();
		deliverOrderShopPayService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
