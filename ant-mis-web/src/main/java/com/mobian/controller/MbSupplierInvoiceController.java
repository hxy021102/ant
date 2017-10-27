package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.*;
import com.mobian.service.MbSupplierInvoiceServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbSupplierInvoice管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbSupplierInvoiceController")
public class MbSupplierInvoiceController extends BaseController {

	@Autowired
	private MbSupplierInvoiceServiceI mbSupplierInvoiceService;


	/**
	 * 跳转到MbSupplierInvoice管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbsupplierinvoice/mbSupplierInvoice";
	}

	/**
	 * 获取MbSupplierInvoice数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbSupplierInvoice mbSupplierInvoice, PageHelper ph) {
		return mbSupplierInvoiceService.dataGrid(mbSupplierInvoice, ph);
	}
	/**
	 * 获取MbSupplierInvoice数据表格excel
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
	public void download(MbSupplierInvoice mbSupplierInvoice, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbSupplierInvoice,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbSupplierInvoice页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,Integer supplierId) {
		request.setAttribute("supplierId",supplierId);
		MbSupplierInvoice mbSupplierInvoice = new MbSupplierInvoice();
		return "/mbsupplierinvoice/mbSupplierInvoiceAdd";
	}

	/**
	 * 添加MbSupplierInvoice
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbSupplierInvoice mbSupplierInvoice) {
		Json j = new Json();		
		mbSupplierInvoiceService.add(mbSupplierInvoice);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbSupplierInvoice查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbSupplierInvoice mbSupplierInvoice = mbSupplierInvoiceService.get(id);
		MbSupplierInvoiceExt mbSupplierInvoiceExt = new MbSupplierInvoiceExt();
		BeanUtils.copyProperties(mbSupplierInvoice,mbSupplierInvoiceExt);
		mbSupplierInvoiceExt.setInvoiceUseName(mbSupplierInvoice.getInvoiceUse());
		mbSupplierInvoiceExt.setInvoiceTypeName(mbSupplierInvoice.getInvoiceType());
		request.setAttribute("mbSupplierInvoice", mbSupplierInvoiceExt);
		return "/mbsupplierinvoice/mbSupplierInvoiceView";
	}

	/**
	 * 跳转到MbSupplierInvoice修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbSupplierInvoice mbSupplierInvoice = mbSupplierInvoiceService.get(id);
		request.setAttribute("mbSupplierInvoice", mbSupplierInvoice);
		return "/mbsupplierinvoice/mbSupplierInvoiceEdit";
	}

	/**
	 * 修改MbSupplierInvoice
	 * 
	 * @param mbSupplierInvoice
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbSupplierInvoice mbSupplierInvoice) {
		Json j = new Json();		
		mbSupplierInvoiceService.edit(mbSupplierInvoice);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbSupplierInvoice
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbSupplierInvoiceService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/setInvoiceDefault")
	@ResponseBody
	public Json setInvoiceDefault(Integer id, Integer supplierId) {
		Json j = new Json();
		MbSupplierInvoice mbSupplierInvoice = new MbSupplierInvoice();
		mbSupplierInvoice.setSupplierId(supplierId);
		List<MbSupplierInvoice> list = mbSupplierInvoiceService.query(mbSupplierInvoice);
		for (MbSupplierInvoice m : list) {
			if (m.getId() == id) {
				m.setInvoiceDefault(true);
				mbSupplierInvoiceService.edit(m);
			} else {
				m.setInvoiceDefault(false);
				mbSupplierInvoiceService.edit(m);
			}
		}
		j.setMsg("设置成功！");
		j.setSuccess(true);
		return j;
	}
}
