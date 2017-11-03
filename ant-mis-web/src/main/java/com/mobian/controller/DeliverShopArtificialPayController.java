package com.mobian.controller;

import com.bx.ant.pageModel.DeliverOrderQuery;
import com.bx.ant.pageModel.ShopOrderBill;
import com.bx.ant.pageModel.ShopOrderBillQuery;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.ShopOrderBillServiceI;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
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
	public DataGrid dataGrid(DeliverOrderQuery deliverOrderQuery, PageHelper ph) {
		//已配送完成,等待用户确认状态
		deliverOrderQuery.setStatus("DOS30");
		return deliverOrderService.dataGridShopArtificialPay(deliverOrderQuery, ph);
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
		shopOrderBillService.addShopOrderBillAndShopPay(shopOrderBillQuery);
		j.setSuccess(true);
		j.setMsg("创建门店账单成功！");
		j.setObj(shopOrderBillQuery);
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
		request.setAttribute("shopOrderBill", shopOrderBill);
		return "/shopartificialpay/shopOrderBillView";
	}

}