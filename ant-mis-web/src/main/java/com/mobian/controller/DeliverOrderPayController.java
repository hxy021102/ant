package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bx.ant.pageModel.Supplier;
import com.bx.ant.pageModel.DeliverOrderPayExt;
import com.bx.ant.service.SupplierServiceI;
import com.mobian.pageModel.Colum;
import com.bx.ant.pageModel.DeliverOrderPay;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.DeliverOrderPayServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * DeliverOrderPay管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/deliverOrderPayController")
public class DeliverOrderPayController extends BaseController {
    @Resource
	private DeliverOrderPayServiceI deliverOrderPayService;
    @Resource
	private SupplierServiceI supplierService;


	/**
	 * 跳转到DeliverOrderPay管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/deliverorderpay/deliverOrderPay";
	}

	/**
	 * 获取DeliverOrderPay数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DeliverOrderPay deliverOrderPay, PageHelper ph) {
		DataGrid dataGrid = new DataGrid();
		List<DeliverOrderPayExt> l = new ArrayList<DeliverOrderPayExt>();
		DataGrid dg = deliverOrderPayService.dataGrid(deliverOrderPay, ph);
	 	List<DeliverOrderPay> list = dg.getRows();
	 	for(DeliverOrderPay d : list) {
	 		DeliverOrderPayExt o = new DeliverOrderPayExt();
			BeanUtils.copyProperties(d,o);
	 		if(o.getSupplierId() !=null) {
	 		Supplier s = supplierService.get(d.getSupplierId());
	 			o.setSupplierName(s.getName());
			}
			l.add(o);
		}

	 	dataGrid.setRows(l);
	 	dataGrid.setTotal(dg.getTotal());
		return  dataGrid;
	}
	/**
	 * 获取DeliverOrderPay数据表格excel
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
	public void download(DeliverOrderPay deliverOrderPay, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(deliverOrderPay,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DeliverOrderPay页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DeliverOrderPay deliverOrderPay = new DeliverOrderPay();
		return "/deliverorderpay/deliverOrderPayAdd";
	}

	/**
	 * 添加DeliverOrderPay
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DeliverOrderPay deliverOrderPay) {
		Json j = new Json();		
		deliverOrderPayService.add(deliverOrderPay);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DeliverOrderPay查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DeliverOrderPay deliverOrderPay = deliverOrderPayService.get(id);
		request.setAttribute("deliverOrderPay", deliverOrderPay);
		return "/deliverorderpay/deliverOrderPayView";
	}

	/**
	 * 跳转到DeliverOrderPay修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DeliverOrderPay deliverOrderPay = deliverOrderPayService.get(id);
		request.setAttribute("deliverOrderPay", deliverOrderPay);
		return "/deliverorderpay/deliverOrderPayEdit";
	}

	/**
	 * 修改DeliverOrderPay
	 * 
	 * @param deliverOrderPay
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DeliverOrderPay deliverOrderPay) {
		Json j = new Json();		
		deliverOrderPayService.edit(deliverOrderPay);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DeliverOrderPay
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		deliverOrderPayService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
