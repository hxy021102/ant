package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.alibaba.fastjson.JSONObject;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.DeliverOrderItemServiceI;
import com.mobian.pageModel.*;
import com.bx.ant.service.DeliverOrderServiceI;


import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * DeliverOrder管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/deliverOrderController")
public class DeliverOrderController extends BaseController {

	@Autowired
	private DeliverOrderServiceI deliverOrderService;

	@Autowired
	private DeliverOrderItemServiceI deliverOrderItemService;


	/**
	 * 跳转到DeliverOrder管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/deliverorder/deliverOrder";
	}

	/**
	 * 获取DeliverOrder数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DeliverOrder deliverOrder, PageHelper ph) {
		return deliverOrderService.dataGridWithName(deliverOrder, ph);
	}
	/**
	 * 获取DeliverOrder数据表格excel
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
	public void download(DeliverOrder deliverOrder, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(deliverOrder,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DeliverOrder页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request, Integer supplierId) {
		request.setAttribute("supplierId", supplierId);
		return "/deliverorder/deliverOrderAdd";
	}

	/**
	 * 添加DeliverOrder
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DeliverOrder deliverOrder,String itemListStr) {
		Json j = new Json();
		List<SupplierItemRelationView> items = JSONObject.parseArray(itemListStr, SupplierItemRelationView.class);
		if (CollectionUtils.isNotEmpty(items)) {
			deliverOrderService.add(deliverOrder);
			for (SupplierItemRelationView item : items) {
				DeliverOrderItem orderItem = new DeliverOrderItem();
				orderItem.setItemId(item.getItemId());
				orderItem.setQuantity(item.getQuantity());
				orderItem.setDeliverOrderId(deliverOrder.getId());
				deliverOrderItemService.addAndFill(orderItem,deliverOrder);
			}
		}
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DeliverOrder查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Long id) {
		DeliverOrderQuery deliverOrderQuery = deliverOrderService.getDeliverOrderView(id);
		request.setAttribute("deliverOrder", deliverOrderQuery);
		return "deliverorder/deliverOrderView";
	}

	/**
	 * 跳转到DeliverOrder修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		DeliverOrder deliverOrder = deliverOrderService.get(id);
		request.setAttribute("deliverOrder", deliverOrder);
		return "/deliverorder/deliverOrderEdit";
	}

	/**
	 * 修改DeliverOrder
	 * 
	 * @param deliverOrder
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DeliverOrder deliverOrder) {
		Json j = new Json();		
		deliverOrderService.edit(deliverOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DeliverOrder
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Long id) {
		Json j = new Json();
		deliverOrderService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}
	/**
	 * 删除DeliverOrder
	 *
	 * @param deliverOrder
	 * @return
	 */
	@RequestMapping("/editStatus")
	@ResponseBody
	public Json editStatus(DeliverOrder deliverOrder) {
		Json j = new Json();
		deliverOrderService.transform(deliverOrder);
		j.setMsg("编辑成功");
		j.setSuccess(true);
		return j;
	}
}
