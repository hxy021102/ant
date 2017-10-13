package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.bx.ant.pageModel.ShopItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.ShopItemServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ShopItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/shopItemController")
public class ShopItemController extends BaseController {

	private ShopItemServiceI shopItemService;


	/**
	 * 跳转到ShopItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/shopitem/shopItem";
	}

	/**
	 * 获取ShopItem数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ShopItem shopItem, PageHelper ph) {
		return shopItemService.dataGrid(shopItem, ph);
	}
	/**
	 * 获取ShopItem数据表格excel
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
	public void download(ShopItem shopItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(shopItem,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ShopItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ShopItem shopItem = new ShopItem();
		return "/shopitem/shopItemAdd";
	}

	/**
	 * 添加ShopItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ShopItem shopItem) {
		Json j = new Json();		
		shopItemService.add(shopItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ShopItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		ShopItem shopItem = shopItemService.get(id);
		request.setAttribute("shopItem", shopItem);
		return "/shopitem/shopItemView";
	}

	/**
	 * 跳转到ShopItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		ShopItem shopItem = shopItemService.get(id);
		request.setAttribute("shopItem", shopItem);
		return "/shopitem/shopItemEdit";
	}

	/**
	 * 修改ShopItem
	 * 
	 * @param shopItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ShopItem shopItem) {
		Json j = new Json();		
		shopItemService.edit(shopItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ShopItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		shopItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
