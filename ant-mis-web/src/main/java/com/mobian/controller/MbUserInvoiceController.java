package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbUserInvoiceServiceI;
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
 * MbUserInvoice管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbUserInvoiceController")
public class MbUserInvoiceController extends BaseController {

	@Autowired
	private MbUserInvoiceServiceI mbUserInvoiceService;


	/**
	 * 跳转到MbUserInvoice管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbuserinvoice/mbUserInvoice";
	}

	/**
	 * 获取MbUserInvoice数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbUserInvoice mbUserInvoice, PageHelper ph) {
		return mbUserInvoiceService.dataGrid(mbUserInvoice, ph);
	}
	/**
	 * 获取MbUserInvoice数据表格excel
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
	public void download(MbUserInvoice mbUserInvoice, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbUserInvoice,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbUserInvoice页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbUserInvoice mbUserInvoice = new MbUserInvoice();
		return "/mbuserinvoice/mbUserInvoiceAdd";
	}

	/**
	 * 添加MbUserInvoice
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbUserInvoice mbUserInvoice) {
		Json j = new Json();
		mbUserInvoiceService.add(mbUserInvoice);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbUserInvoice查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbUserInvoice mbUserInvoice = mbUserInvoiceService.get(id);
		request.setAttribute("mbUserInvoice", mbUserInvoice);
		return "/mbuserinvoice/mbUserInvoiceView";
	}

	/**
	 * 跳转到MbUserInvoice修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbUserInvoice mbUserInvoice = mbUserInvoiceService.get(id);
		request.setAttribute("mbUserInvoice", mbUserInvoice);
		return "/mbuserinvoice/mbUserInvoiceEdit";
	}

	/**
	 * 修改MbUserInvoice
	 * 
	 * @param mbUserInvoice
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbUserInvoice mbUserInvoice) {
		Json j = new Json();
		mbUserInvoiceService.edit(mbUserInvoice);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbUserInvoice
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbUserInvoiceService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
