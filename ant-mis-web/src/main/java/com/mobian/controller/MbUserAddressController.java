package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbUserAddressServiceI;
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
 * MbUserAddress管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbUserAddressController")
public class MbUserAddressController extends BaseController {

	@Autowired
	private MbUserAddressServiceI mbUserAddressService;


	/**
	 * 跳转到MbUserAddress管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbuseraddress/mbUserAddress";
	}

	/**
	 * 获取MbUserAddress数据表格
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbUserAddress mbUserAddress, PageHelper ph) {
		return mbUserAddressService.dataGrid(mbUserAddress, ph);
	}
	/**
	 * 获取MbUserAddress数据表格excel
	 */
	@RequestMapping("/download")
	public void download(MbUserAddress mbUserAddress, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbUserAddress,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbUserAddress页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbUserAddress mbUserAddress = new MbUserAddress();
		return "/mbuseraddress/mbUserAddressAdd";
	}

	/**
	 * 添加MbUserAddress
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbUserAddress mbUserAddress) {
		Json j = new Json();
		mbUserAddressService.add(mbUserAddress);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbUserAddress查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbUserAddress mbUserAddress = mbUserAddressService.get(id);
		request.setAttribute("mbUserAddress", mbUserAddress);
		return "/mbuseraddress/mbUserAddressView";
	}

	/**
	 * 跳转到MbUserAddress修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbUserAddress mbUserAddress = mbUserAddressService.get(id);
		request.setAttribute("mbUserAddress", mbUserAddress);
		return "/mbuseraddress/mbUserAddressEdit";
	}

	/**
	 * 修改MbUserAddress
	 * 
	 * @param mbUserAddress
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbUserAddress mbUserAddress) {
		Json j = new Json();
		mbUserAddressService.edit(mbUserAddress);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbUserAddress
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbUserAddressService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
