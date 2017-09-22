package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.*;
import com.mobian.service.MbPaymentItemServiceI;

import com.mobian.service.MbPaymentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbPaymentItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbPaymentItemController")
public class MbPaymentItemController extends BaseController {

	@Autowired
	private MbPaymentItemServiceI mbPaymentItemService;
	@Autowired
	private MbPaymentServiceI mbPaymentService;


	/**
	 * 跳转到MbPaymentItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbpaymentitem/mbPaymentItem";
	}

	/**
	 * 获取MbPaymentItem数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbPaymentItem mbPaymentItem, PageHelper ph) {
		return mbPaymentItemService.dataGrid(mbPaymentItem, ph);
	}

	@RequestMapping("/dataGridByOrderId")
	@ResponseBody
	public DataGrid dataGridByOrderId(Integer orderId) {
		MbPayment mbPayment = mbPaymentService.getByOrderId(orderId);
		if(mbPayment == null){
			return new DataGrid();
		}
		MbPaymentItem mbPaymentItem = new MbPaymentItem();
		mbPaymentItem.setPaymentId(mbPayment.getId());
		PageHelper ph = new PageHelper();
		return mbPaymentItemService.dataGrid(mbPaymentItem, ph);
	}
	/**
	 * 获取MbPaymentItem数据表格excel
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
	public void download(MbPaymentItem mbPaymentItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbPaymentItem,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbPaymentItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbPaymentItem mbPaymentItem = new MbPaymentItem();
		return "/mbpaymentitem/mbPaymentItemAdd";
	}

	/**
	 * 添加MbPaymentItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbPaymentItem mbPaymentItem) {
		Json j = new Json();		
		mbPaymentItemService.add(mbPaymentItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbPaymentItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbPaymentItem mbPaymentItem = mbPaymentItemService.get(id);
		request.setAttribute("mbPaymentItem", mbPaymentItem);
		return "/mbpaymentitem/mbPaymentItemView";
	}

	/**
	 * 跳转到MbPaymentItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbPaymentItem mbPaymentItem = mbPaymentItemService.get(id);
		request.setAttribute("mbPaymentItem", mbPaymentItem);
		return "/mbpaymentitem/mbPaymentItemEdit";
	}

	/**
	 * 修改MbPaymentItem
	 * 
	 * @param mbPaymentItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbPaymentItem mbPaymentItem) {
		Json j = new Json();		
		mbPaymentItemService.edit(mbPaymentItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbPaymentItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbPaymentItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
