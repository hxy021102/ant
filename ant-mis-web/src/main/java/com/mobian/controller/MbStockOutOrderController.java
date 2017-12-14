package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.MbStockOutOrder;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbStockOutOrderServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbStockOutOrder管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbStockOutOrderController")
public class MbStockOutOrderController extends BaseController {

	@Autowired
	private MbStockOutOrderServiceI mbStockOutOrderService;


	/**
	 * 跳转到MbStockOutOrder管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbstockoutorder/mbStockOutOrder";
	}

	/**
	 * 获取MbStockOutOrder数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbStockOutOrder mbStockOutOrder, PageHelper ph) {
		return mbStockOutOrderService.dataGrid(mbStockOutOrder, ph);
	}
	/**
	 * 获取MbStockOutOrder数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws java.io.IOException
	 */
	@RequestMapping("/download")
	public void download(MbStockOutOrder mbStockOutOrder, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbStockOutOrder,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbStockOutOrder页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbStockOutOrder mbStockOutOrder = new MbStockOutOrder();
		return "/mbstockoutorder/mbStockOutOrderAdd";
	}

	/**
	 * 添加MbStockOutOrder
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbStockOutOrder mbStockOutOrder) {
		Json j = new Json();		
		mbStockOutOrderService.add(mbStockOutOrder);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbStockOutOrder查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbStockOutOrder mbStockOutOrder = mbStockOutOrderService.get(id);
		request.setAttribute("mbStockOutOrder", mbStockOutOrder);
		return "/mbstockoutorder/mbStockOutOrderView";
	}

	/**
	 * 跳转到MbStockOutOrder修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbStockOutOrder mbStockOutOrder = mbStockOutOrderService.get(id);
		request.setAttribute("mbStockOutOrder", mbStockOutOrder);
		return "/mbstockoutorder/mbStockOutOrderEdit";
	}

	/**
	 * 修改MbStockOutOrder
	 * 
	 * @param mbStockOutOrder
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbStockOutOrder mbStockOutOrder) {
		Json j = new Json();		
		mbStockOutOrderService.edit(mbStockOutOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbStockOutOrder
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbStockOutOrderService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 跳转到添加MbStockOutOrder页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/addStockOutPage")
	public String addStockOutPage(String deliverOrderIds, HttpServletRequest request) {
		request.setAttribute("deliverOrderIds", deliverOrderIds);
		return "/deliverorder/createStockOut";
	}

	/**
	 * 获取出库单商品数据
	 *
	 * @return
	 */
	@RequestMapping("/orderItemDataGrid")
	@ResponseBody
	public DataGrid orderItemDataGrid(MbStockOutOrder mbStockOutOrder, PageHelper ph) {
		return mbStockOutOrderService.dataGrid(mbStockOutOrder, ph);
	}
}
