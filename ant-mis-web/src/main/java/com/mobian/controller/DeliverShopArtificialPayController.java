package com.mobian.controller;

import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.ShopOrderBill;
import com.bx.ant.pageModel.ShopOrderBillQuery;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.bx.ant.service.ShopOrderBillServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * DeliverShopArtificialPayController管理控制器
 * 
 * @author 黄晓渝
 * 
 */
@Controller
@RequestMapping("/deliverShopArtificialPayController")
public class DeliverShopArtificialPayController extends BaseController {

	@Resource
	private DeliverOrderServiceI deliverOrderService;

	@Resource
	private ShopOrderBillServiceI shopOrderBillService;
	@Resource
	private DeliverOrderShopServiceI deliverOrderShopService;

	/**
	 * 跳转到DeliverOrder管理页面
	 *
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "shopartificialpay/shopArtificialPay";
	}

	/**
	 * 获取DeliverOrderShop数据表格
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DeliverOrderShop deliverOrderShop, PageHelper ph) {
		//已配送完成,等待用户确认状态
		deliverOrderShop.setStatus("DSS06");
		deliverOrderShop.setShopPayStatus("SPS01");
		return deliverOrderShopService.dataGridShopArtificialPay(deliverOrderShop, ph);
	}

	/**
	 * 创建门店订单账单
	 * @param shopOrderBillQuery
	 * @return
	 */
	@RequestMapping(value="/addShopOrderBill", method = RequestMethod.POST)
	@ResponseBody
	public Json addShopOrderBill(@RequestBody ShopOrderBillQuery shopOrderBillQuery) {
		Json j = new Json();
		String result=shopOrderBillService.addShopOrderBillAndShopPay(shopOrderBillQuery);
		if(F.empty(result)) {
			j.setSuccess(true);
			j.setMsg("创建门店账单成功！");
		}else {
			j.setSuccess(false);
			j.setMsg("失败,"+result+" 已经被创建！");
		}
		return j;
	}

	/**
	 * 跳转到人工门店订单账单管理
	 * @param request
	 * @return
	 */
	@RequestMapping("/managerBill")
	public String managerBill(HttpServletRequest request) {
		return "/shopartificialpay/shopOrderBill";
	}
	/**
	 * 门店人工结算订单账单列表
	 * @param shopOrderBill
	 * @param ph
	 * @return
	 */
	@RequestMapping("/dataGridOrderBill")
	@ResponseBody
	public DataGrid dataGridOrderBill(ShopOrderBill shopOrderBill, PageHelper ph) {
		return shopOrderBillService.dataGridWithName(shopOrderBill, ph);
	}

	/**
	 * 跳转到ShopOrderBill详情页面
	 *
	 * @return
	 */
	@RequestMapping("/viewBill")
	public String viewBill(HttpServletRequest request,Long id) {
		ShopOrderBill shopOrderBill = shopOrderBillService.getViewShopOrderBill(id);
		ShopOrderBillQuery shopOrderBillQuery =new ShopOrderBillQuery();
		BeanUtils.copyProperties(shopOrderBill,shopOrderBillQuery);
		shopOrderBillQuery.setStatusName(shopOrderBill.getStatus());
		shopOrderBillQuery.setPayWayName(shopOrderBill.getPayWay());
		request.setAttribute("shopOrderBill", shopOrderBillQuery);
		return "/shopartificialpay/shopOrderBillView";
	}

}