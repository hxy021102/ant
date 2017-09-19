package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbOrderItemServiceI;
import com.mobian.service.MbOrderRefundItemServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * MbOrderRefundItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbOrderRefundItemController")
public class MbOrderRefundItemController extends BaseController {

	@Autowired
	private MbOrderRefundItemServiceI mbOrderRefundItemService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private MbItemServiceI mbItemService;

	@Autowired
	private MbOrderItemServiceI mbOrderItemService;


	/**
	 * 跳转到MbOrderRefundItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mborderrefunditem/mbOrderRefundItem";
	}

	/**
	 * 获取MbOrderRefundItem数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbOrderRefundItem mbOrderRefundItem, PageHelper ph) {
		DataGrid dataGrid = mbOrderRefundItemService.dataGrid(mbOrderRefundItem, ph);
		List<MbOrderRefundItem> mbOrderRefundItems = dataGrid.getRows();
		for (MbOrderRefundItem o:mbOrderRefundItems
			 ) {
			if(o.getLoginId() != null){
				User user = userService.get(o.getLoginId());
				if(user != null){
					o.setLoginName(user.getName());
				}
			}
			if(o.getItemId() != null){
			    MbItem mbItem = mbItemService.get(o.getItemId());
				if(mbItem != null){
				    o.setItemName(mbItem.getName());
				}
			}
		}
		dataGrid.setRows(mbOrderRefundItems);
		return dataGrid;
	}
	/**
	 * 获取MbOrderRefundItem数据表格excel
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
	public void download(MbOrderRefundItem mbOrderRefundItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbOrderRefundItem,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbOrderRefundItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,Integer id) {
		request.setAttribute("orderId",id);
		return "/mborderrefunditem/mbOrderRefundItemAddPage";
	}

	/**
	 * 添加MbOrderRefundItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbOrderRefundItem mbOrderRefundItem, HttpSession session) {
		Json j = new Json();


		MbOrderItem mbOrderItem = new MbOrderItem();
		mbOrderItem.setOrderId(mbOrderRefundItem.getOrderId());
		mbOrderItem.setItemId(mbOrderRefundItem.getItemId());
		List<MbOrderItem> mbOrderItemList = mbOrderItemService.query(mbOrderItem);
		Integer mbOrderItemQuantity = 0;
		for (MbOrderItem orderItem : mbOrderItemList) {
			mbOrderItemQuantity += orderItem.getQuantity();
		}

		//初始化退回商品总数
		Integer mbOrderRefundItemQuantity ;
		if((mbOrderRefundItemQuantity = mbOrderRefundItem.getQuantity()) == null)	{
			mbOrderRefundItemQuantity=0;
		}
		//根据MborderRefundItem中的orderId和ItemId搜索数据库并遍历
		MbOrderRefundItem mbOrderRefundItemA = new MbOrderRefundItem();
		mbOrderRefundItemA.setItemId(mbOrderRefundItem.getItemId());
		mbOrderRefundItemA.setOrderId(mbOrderRefundItem.getOrderId());
		List<MbOrderRefundItem> mbOrderRefundItems =	mbOrderRefundItemService.query(mbOrderRefundItemA);
		//遍历从数据库中获取到的退回商品序列,累加不同类型的数量和判断是否存在已经添加的同类型商品
		for (MbOrderRefundItem m : mbOrderRefundItems) {
			//累加订单中已经添加的退回商品的数量
			if (m.getQuantity() != null) {
				mbOrderRefundItemQuantity += m.getQuantity();
			}
		}
		//判断退货商品数量是否超过发货数量
		if(mbOrderItemQuantity == null   || mbOrderItemQuantity < mbOrderRefundItemQuantity){
			j.setSuccess(false);
			j.setMsg("添加的退货商品数量不能超过该商品的发货量");
			return j;
		}

		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		mbOrderRefundItem.setLoginId(sessionInfo.getId());
		mbOrderRefundItemService.add(mbOrderRefundItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");

		return j;
	}

	/**
	 * 跳转到MbOrderRefundItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbOrderRefundItem mbOrderRefundItem = mbOrderRefundItemService.get(id);
		request.setAttribute("mbOrderRefundItem", mbOrderRefundItem);
		return "/mborderrefunditem/mbOrderRefundItemView";
	}

	/**
	 * 跳转到MbOrderRefundItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbOrderRefundItem mbOrderRefundItem = mbOrderRefundItemService.get(id);
		request.setAttribute("mbOrderRefundItem", mbOrderRefundItem);
		return "/mborderrefunditem/mbOrderRefundItemEdit";
	}

	/**
	 * 修改MbOrderRefundItem
	 * 
	 * @param mbOrderRefundItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbOrderRefundItem mbOrderRefundItem) {
		Json j = new Json();
		mbOrderRefundItemService.edit(mbOrderRefundItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbOrderRefundItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbOrderRefundItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
