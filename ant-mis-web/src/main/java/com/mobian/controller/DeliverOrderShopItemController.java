package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DeliverOrderShopItem;
import com.bx.ant.service.DeliverOrderShopItemServiceI;
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
 * DeliverOrderShopItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/deliverOrderShopItemController")
public class DeliverOrderShopItemController extends BaseController {

	@Resource
	private DeliverOrderShopItemServiceI deliverOrderShopItemService;


	/**
	 * 跳转到DeliverOrderShopItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/deliverordershopitem/deliverOrderShopItem";
	}

	/**
	 * 获取DeliverOrderShopItem数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DeliverOrderShopItem deliverOrderShopItem, PageHelper ph) {
		return deliverOrderShopItemService.dataGridWithName(deliverOrderShopItem, ph);
	}
	/**
	 * 获取DeliverOrderShopItem数据表格excel
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
	public void download(DeliverOrderShopItem deliverOrderShopItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(deliverOrderShopItem,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DeliverOrderShopItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DeliverOrderShopItem deliverOrderShopItem = new DeliverOrderShopItem();
		return "/deliverordershopitem/deliverOrderShopItemAdd";
	}

	/**
	 * 添加DeliverOrderShopItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DeliverOrderShopItem deliverOrderShopItem) {
		Json j = new Json();		
		deliverOrderShopItemService.add(deliverOrderShopItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DeliverOrderShopItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DeliverOrderShopItem deliverOrderShopItem = deliverOrderShopItemService.get(id);
		request.setAttribute("deliverOrderShopItem", deliverOrderShopItem);
		return "/deliverordershopitem/deliverOrderShopItemView";
	}

	/**
	 * 跳转到DeliverOrderShopItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DeliverOrderShopItem deliverOrderShopItem = deliverOrderShopItemService.get(id);
		request.setAttribute("deliverOrderShopItem", deliverOrderShopItem);
		return "/deliverordershopitem/deliverOrderShopItemEdit";
	}

	/**
	 * 修改DeliverOrderShopItem
	 * 
	 * @param deliverOrderShopItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DeliverOrderShopItem deliverOrderShopItem) {
		Json j = new Json();		
		deliverOrderShopItemService.edit(deliverOrderShopItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DeliverOrderShopItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Long id) {
		Json j = new Json();
		deliverOrderShopItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
