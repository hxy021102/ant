package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bx.ant.service.ShopDeliverAccountServiceI;
import com.mobian.pageModel.Colum;
import com.mobian.pageModel.ShopDeliverAccount;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ShopDeliverAccount管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/shopDeliverAccountController")
public class ShopDeliverAccountController extends BaseController {
	@Resource
    private ShopDeliverAccountServiceI shopDeliverAccountService;


	/**
	 * 跳转到ShopDeliverAccount管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/shopdeliveraccount/shopDeliverAccount";
	}

	/**
	 * 获取ShopDeliverAccount数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ShopDeliverAccount shopDeliverAccount, PageHelper ph) {
		return shopDeliverAccountService.dataGrid(shopDeliverAccount, ph);
	}
	/**
	 * 获取ShopDeliverAccount数据表格excel
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
	public void download(ShopDeliverAccount shopDeliverAccount, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(shopDeliverAccount,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ShopDeliverAccount页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ShopDeliverAccount shopDeliverAccount = new ShopDeliverAccount();
		return "/shopdeliveraccount/shopDeliverAccountAdd";
	}

	/**
	 * 添加ShopDeliverAccount
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ShopDeliverAccount shopDeliverAccount) {
		Json j = new Json();		
		shopDeliverAccountService.add(shopDeliverAccount);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ShopDeliverAccount查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		ShopDeliverAccount shopDeliverAccount = shopDeliverAccountService.get(id);
		request.setAttribute("shopDeliverAccount", shopDeliverAccount);
		return "/shopdeliveraccount/shopDeliverAccountView";
	}

	/**
	 * 跳转到ShopDeliverAccount修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		ShopDeliverAccount shopDeliverAccount = shopDeliverAccountService.get(id);
		request.setAttribute("shopDeliverAccount", shopDeliverAccount);
		return "/shopdeliveraccount/shopDeliverAccountEdit";
	}

	/**
	 * 修改ShopDeliverAccount
	 * 
	 * @param shopDeliverAccount
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ShopDeliverAccount shopDeliverAccount) {
		Json j = new Json();		
		shopDeliverAccountService.edit(shopDeliverAccount);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ShopDeliverAccount
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		shopDeliverAccountService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
