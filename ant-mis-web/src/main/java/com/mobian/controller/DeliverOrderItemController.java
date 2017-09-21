package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.DeliverOrderItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderItemServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * DeliverOrderItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/deliverOrderItemController")
public class DeliverOrderItemController extends BaseController {

	private DeliverOrderItemServiceI deliverOrderItemService;


	/**
	 * 跳转到DeliverOrderItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/deliverorderitem/deliverOrderItem";
	}

	/**
	 * 获取DeliverOrderItem数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DeliverOrderItem deliverOrderItem, PageHelper ph) {
		return deliverOrderItemService.dataGrid(deliverOrderItem, ph);
	}
	/**
	 * 获取DeliverOrderItem数据表格excel
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
	public void download(DeliverOrderItem deliverOrderItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(deliverOrderItem,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DeliverOrderItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DeliverOrderItem deliverOrderItem = new DeliverOrderItem();
		return "/deliverorderitem/deliverOrderItemAdd";
	}

	/**
	 * 添加DeliverOrderItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DeliverOrderItem deliverOrderItem) {
		Json j = new Json();		
		deliverOrderItemService.add(deliverOrderItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DeliverOrderItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DeliverOrderItem deliverOrderItem = deliverOrderItemService.get(id);
		request.setAttribute("deliverOrderItem", deliverOrderItem);
		return "/deliverorderitem/deliverOrderItemView";
	}

	/**
	 * 跳转到DeliverOrderItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DeliverOrderItem deliverOrderItem = deliverOrderItemService.get(id);
		request.setAttribute("deliverOrderItem", deliverOrderItem);
		return "/deliverorderitem/deliverOrderItemEdit";
	}

	/**
	 * 修改DeliverOrderItem
	 * 
	 * @param deliverOrderItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DeliverOrderItem deliverOrderItem) {
		Json j = new Json();		
		deliverOrderItemService.edit(deliverOrderItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DeliverOrderItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		deliverOrderItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
