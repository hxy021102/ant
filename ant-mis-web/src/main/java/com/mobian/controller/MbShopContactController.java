package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbShopContactServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * MbShopContact管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbShopContactController")
public class MbShopContactController extends BaseController {

	@Autowired
	private MbShopContactServiceI mbShopContactService;


	/**
	 * 跳转到MbShopContact管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request,Integer shopId) {
		request.setAttribute("shopId", shopId);
		return "/mbshopcontact/mbShopContact";
	}

	/**
	 * 获取MbShopContact数据表格
	 * 
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbShopContact mbShopContact, PageHelper ph) {
		return mbShopContactService.dataGrid(mbShopContact, ph);
	}
	/**
	 * 获取MbShopContact数据表格excel
	 * 
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(MbShopContact mbShopContact, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbShopContact,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbShopContact页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,Integer shopId) {
		request.setAttribute("shopId", shopId);

		return "/mbshopcontact/mbShopContactAdd";
	}

	/**
	 * 添加MbShopContact
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbShopContact mbShopContact) {
		Json j = new Json();
		mbShopContactService.add(mbShopContact);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbShopContact查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbShopContact mbShopContact = mbShopContactService.get(id);
		request.setAttribute("mbShopContact", mbShopContact);
		return "/mbshopcontact/mbShopContactView";
	}

	/**
	 * 跳转到MbShopContact修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbShopContact mbShopContact = mbShopContactService.get(id);
		request.setAttribute("mbShopContact", mbShopContact);
		return "/mbshopcontact/mbShopContactEdit";
	}

	/**
	 * 修改MbShopContact
	 * 
	 * @param mbShopContact
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbShopContact mbShopContact) {
		Json j = new Json();
		mbShopContactService.edit(mbShopContact);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbShopContact
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbShopContactService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
