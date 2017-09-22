package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.MbCouponsItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbCouponsItemServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbCouponsItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbCouponsItemController")
public class MbCouponsItemController extends BaseController {

	@Autowired
	private MbCouponsItemServiceI mbCouponsItemService;


	/**
	 * 跳转到MbCouponsItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbcouponsitem/mbCouponsItem";
	}

	/**
	 * 获取MbCouponsItem数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbCouponsItem mbCouponsItem, PageHelper ph) {
		return mbCouponsItemService.dataGrid(mbCouponsItem, ph);
	}
	/**
	 * 获取MbCouponsItem数据表格excel
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
	public void download(MbCouponsItem mbCouponsItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbCouponsItem,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbCouponsItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbCouponsItem mbCouponsItem = new MbCouponsItem();
		return "/mbcouponsitem/mbCouponsItemAdd";
	}

	/**
	 * 添加MbCouponsItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbCouponsItem mbCouponsItem) {
		Json j = new Json();		
		mbCouponsItemService.add(mbCouponsItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbCouponsItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbCouponsItem mbCouponsItem = mbCouponsItemService.get(id);
		request.setAttribute("mbCouponsItem", mbCouponsItem);
		return "/mbcouponsitem/mbCouponsItemView";
	}

	/**
	 * 跳转到MbCouponsItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbCouponsItem mbCouponsItem = mbCouponsItemService.get(id);
		request.setAttribute("mbCouponsItem", mbCouponsItem);
		return "/mbcouponsitem/mbCouponsItemEdit";
	}

	/**
	 * 修改MbCouponsItem
	 * 
	 * @param mbCouponsItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbCouponsItem mbCouponsItem) {
		Json j = new Json();		
		mbCouponsItemService.edit(mbCouponsItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbCouponsItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbCouponsItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
