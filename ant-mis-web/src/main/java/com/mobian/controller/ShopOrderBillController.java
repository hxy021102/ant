package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bx.ant.pageModel.ShopOrderBillQuery;
import com.bx.ant.service.ShopOrderBillServiceI;
import com.mobian.pageModel.*;
import com.bx.ant.pageModel.ShopOrderBill;

import com.mobian.service.MbShopServiceI;
import com.mobian.util.ConfigUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ShopOrderBill管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/shopOrderBillController")
public class ShopOrderBillController extends BaseController {

	@Resource
	private ShopOrderBillServiceI shopOrderBillService;
	@Resource
	private MbShopServiceI mbShopService;


	/**
	 * 跳转到ShopOrderBill管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/shoporderbill/shopOrderBill";
	}

	/**
	 * 获取ShopOrderBill数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ShopOrderBill shopOrderBill, PageHelper ph) {
		return shopOrderBillService.dataGrid(shopOrderBill, ph);
	}
	/**
	 * 获取ShopOrderBill数据表格excel
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
	public void download(ShopOrderBill shopOrderBill, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(shopOrderBill,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ShopOrderBill页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ShopOrderBill shopOrderBill = new ShopOrderBill();
		return "/shoporderbill/shopOrderBillAdd";
	}

	/**
	 * 添加ShopOrderBill
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ShopOrderBill shopOrderBill) {
		Json j = new Json();		
		shopOrderBillService.add(shopOrderBill);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到ShopOrderBill查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Long id) {
		ShopOrderBill shopOrderBill = shopOrderBillService.get(id);
		request.setAttribute("shopOrderBill", shopOrderBill);
		return "/shoporderbill/shopOrderBillView";
	}

	/**
	 * 跳转到ShopOrderBill修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		ShopOrderBill shopOrderBill = shopOrderBillService.get(id);
		request.setAttribute("shopOrderBill", shopOrderBill);
		return "/shoporderbill/shopOrderBillEdit";
	}

	/**
	 * 修改ShopOrderBill
	 * 
	 * @param shopOrderBill
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ShopOrderBill shopOrderBill) {
		Json j = new Json();		
		shopOrderBillService.edit(shopOrderBill);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ShopOrderBill
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		shopOrderBillService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 *跳转到门店账单审核页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/examinePage")
	public String examinePage(HttpServletRequest request, Long id) {
		ShopOrderBill shopOrderBill = shopOrderBillService.get(id);
		if(shopOrderBill!=null){
			MbShop shop =mbShopService.getFromCache(shopOrderBill.getShopId());
			ShopOrderBillQuery shopOrderBillQuery = new ShopOrderBillQuery();
			BeanUtils.copyProperties(shopOrderBill,shopOrderBillQuery);
			shopOrderBillQuery.setShopName(shop.getName());
			request.setAttribute("shopOrderBill", shopOrderBillQuery);
		}
		request.setAttribute("id", id);
		return "shopartificialpay/shopOrderBillExamine";
	}

	/**
	 * 编辑审核状态（若审核通过则进行支付，否则不做支付操作）
	 * @param shopOrderBill
	 * @param session
	 * @return
	 */
	@RequestMapping("/editState")
	@ResponseBody
	public Json editState(ShopOrderBill shopOrderBill, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		shopOrderBill.setReviewerId(sessionInfo.getId());
		shopOrderBill.setPayWay("DPW01");
		shopOrderBillService.editBillStatusAndPayStatus(shopOrderBill);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 跳转到门店账单支付页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/payShopBillPage")
	public String payShopBillPage(HttpServletRequest request, Long id) {
		ShopOrderBill shopOrderBill = shopOrderBillService.get(id);
		if(shopOrderBill!=null){
 			MbShop shop =mbShopService.getFromCache(shopOrderBill.getShopId());
			ShopOrderBillQuery shopOrderBillQuery = new ShopOrderBillQuery();
			BeanUtils.copyProperties(shopOrderBill,shopOrderBillQuery);
			shopOrderBillQuery.setShopName(shop.getName());
			request.setAttribute("shopOrderBill", shopOrderBillQuery);
		}
		return "/shoporderbill/shopOrderBillPay";
	}

	/**
	 * 支付门店账单
	 * @param shopOrderBill
	 * @return
	 */
	@RequestMapping("/payShopBill")
	@ResponseBody
	public Json payShopBill(ShopOrderBill shopOrderBill) {
		Json j = new Json();
		shopOrderBillService.editDeliverOrderStatusAndShopBalance(shopOrderBill);
		j.setSuccess(true);
		j.setMsg("支付成功！");
		return j;
	}


}
