package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mobian.pageModel.*;
import com.mobian.service.*;


import com.mobian.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbOrderCallbackItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbOrderCallbackItemController")
public class MbOrderCallbackItemController extends BaseController {

	@Autowired
	private MbOrderCallbackItemServiceI mbOrderCallbackItemService;

	@Autowired
	private MbBalanceLogServiceI mbBalanceLogService;
	@Autowired
	private MbBalanceServiceI mbBalanceService;
	@Autowired
	private MbItemServiceI mbItemService;



	/**
	 * 跳转到MbOrderCallbackItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbordercallbackitem/mbOrderCallbackItem";
	}

	/**
	 * 获取MbOrderCallbackItem数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbOrderCallbackItem mbOrderCallbackItem, PageHelper ph) {
		return mbOrderCallbackItemService.dataGrid(mbOrderCallbackItem, ph);
	}
	/**
	 * 获取MbOrderCallbackItem数据表格excel
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
	public void download(MbOrderCallbackItem mbOrderCallbackItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbOrderCallbackItem,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbOrderCallbackItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,Integer id,Integer shopId ) {
		request.setAttribute("orderId",id);
		request.setAttribute("shopId",shopId);
		return "/mbordercallbackitem/mbOrderCallbackItemAddPage";
	}

	/**
	 * 添加MbOrderCallbackItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbOrderCallbackItem mbOrderCallbackItem, HttpSession session,Integer packId,Integer shopId,Integer orderId) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		mbOrderCallbackItem.setItemId(packId);
		mbOrderCallbackItem.setLoginId(sessionInfo.getId());
		mbOrderCallbackItemService.add(mbOrderCallbackItem);
		//与桶账挂钩
		MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceCash(shopId);
		MbItem packItem = mbItemService.getFromCache(packId);
		MbBalanceLog mbBalanceLog = new MbBalanceLog();
		mbBalanceLog.setAmount(packItem.getMarketPrice() * mbOrderCallbackItem.getQuantity());
		mbBalanceLog.setRefId(orderId + "");
		mbBalanceLog.setRefType("BT018");
		mbBalanceLog.setBalanceId(mbBalance.getId());
		mbBalanceLog.setReason(String.format("订单ID：%s回桶出库 商品[%s],数量[%s]", orderId, packItem.getName(), mbOrderCallbackItem.getQuantity()));
		mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}


	/**
	 * 跳转到MbOrderCallbackItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbOrderCallbackItem mbOrderCallbackItem = mbOrderCallbackItemService.get(id);
		request.setAttribute("mbOrderCallbackItem", mbOrderCallbackItem);
		return "/mbordercallbackitem/mbOrderCallbackItemView";
	}

	/**
	 * 跳转到MbOrderCallbackItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbOrderCallbackItem mbOrderCallbackItem = mbOrderCallbackItemService.get(id);
		request.setAttribute("mbOrderCallbackItem", mbOrderCallbackItem);
		return "/mbordercallbackitem/mbOrderCallbackItemEdit";
	}


	/**
	 * 修改MbOrderCallbackItem
	 * 
	 * @param mbOrderCallbackItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbOrderCallbackItem mbOrderCallbackItem) {
		Json j = new Json();		
		mbOrderCallbackItemService.edit(mbOrderCallbackItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbOrderCallbackItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbOrderCallbackItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
