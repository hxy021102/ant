package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbPaymentServiceI;
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
 * MbPayment管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbPaymentController")
public class MbPaymentController extends BaseController {

	@Autowired
	private MbPaymentServiceI mbPaymentService;


	/**
	 * 跳转到MbPayment管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbpayment/mbPayment";
	}

	/**
	 * 获取MbPayment数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbPayment mbPayment, PageHelper ph) {
		return mbPaymentService.dataGrid(mbPayment, ph);
	}
	/**
	 * 获取MbPayment数据表格excel
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
	public void download(MbPayment mbPayment, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbPayment,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbPayment页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbPayment mbPayment = new MbPayment();
		return "/mbpayment/mbPaymentAdd";
	}

	/**
	 * 添加MbPayment
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbPayment mbPayment) {
		Json j = new Json();
		mbPaymentService.add(mbPayment);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbPayment查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbPayment mbPayment = mbPaymentService.get(id);
		request.setAttribute("mbPayment", mbPayment);
		return "/mbpayment/mbPaymentView";
	}

	/**
	 * 跳转到MbPayment修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbPayment mbPayment = mbPaymentService.get(id);
		request.setAttribute("mbPayment", mbPayment);
		return "/mbpayment/mbPaymentEdit";
	}

	/**
	 * 修改MbPayment
	 * 
	 * @param mbPayment
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbPayment mbPayment) {
		Json j = new Json();
		mbPaymentService.edit(mbPayment);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbPayment
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbPaymentService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
