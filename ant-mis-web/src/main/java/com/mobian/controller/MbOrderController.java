package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.concurrent.CacheKey;
import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.Task;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.service.impl.CompletionFactory;
import com.mobian.service.impl.DiveRegionServiceImpl;
import com.mobian.service.impl.order.OrderState;
import com.mobian.util.ConfigUtil;
import com.mobian.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * MbOrder管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbOrderController")
public class MbOrderController extends BaseController {

	@Autowired
	private MbOrderServiceI mbOrderService;

	@Autowired
	private MbUserServiceI mbUserService;

	@Autowired
    private MbShopServiceI mbShopService;

	@Autowired
	private MbOrderItemServiceI mbOrderItemService;

	@Autowired
	private MbPaymentServiceI mbPaymentService;

	@Autowired
	private MbOrderInvoiceServiceI mbOrderInvoiceService;

	@Autowired
	private DiveRegionServiceImpl diveRegionService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private MbBalanceServiceI mbBalanceService;
	@Autowired
	private MbWarehouseServiceI mbWarehouseService;
	@Autowired
	private MbOrderRefundLogServiceI mbOrderRefundLogService;


	@Resource(name = "order02StateImpl")
	private OrderState orderService02;

	/**
	 * 跳转到MbOrder管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request,String status) {
		request.setAttribute("status", status);
		return "/mborder/mbOrder";
	}

	@RequestMapping("/managerPrintList")
	public String managerPrintList(HttpServletRequest request) {
		request.setAttribute("status", "OD12");
		return "/mborder/mbOrderPrintList";
	}

	@RequestMapping("/managerWorkbench")
	public String managerWorkbench(HttpServletRequest request) {
		return "/mborder/mbOrderWorkbench";
	}

	/**
	 * 获取MbOrder数据表格
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbOrder mbOrder, PageHelper ph) {
		if (mbOrder.getShopId() != null) {
			MbShop mbShop = new MbShop();
			mbShop.setParentId(mbOrder.getShopId());
			mbShop.setOnlyBranch(true);
			List<MbShop> list = mbShopService.query(mbShop);
			Integer[] shopIds = new Integer[list.size() + 1];
			shopIds[0] = mbOrder.getShopId();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					shopIds[i + 1] = list.get(i).getId();
				}
			}
			mbOrder.setShopIds(shopIds);
		}
		DataGrid dg = mbOrderService.dataGrid(mbOrder, ph);
		getDriver(dg);
		return dg;
	}


     protected  void  getDriver(DataGrid dg){
		 List<MbOrder> ol = dg.getRows();
		 if (CollectionUtils.isNotEmpty(ol)) {
			 CompletionService completionService = CompletionFactory.initCompletion();
			 for (MbOrder o : ol) {
				 // 获取司机
				 if (!F.empty(o.getDeliveryDriver()))
					 completionService.submit(new Task<MbOrder, User>(new CacheKey("user", o.getDeliveryDriver()), o) {
						 @Override
						 public User call() throws Exception {
							 return userService.get(getD().getDeliveryDriver());
						 }

						 protected void set(MbOrder d, User v) {
							 if (v != null)
								 d.setDeliveryDriverName(v.getNickname());
						 }
					 });
			 }
			 completionService.sync();
		 }
	 }
	@RequestMapping("/queryOrderDataGrid")
	@ResponseBody
	public DataGrid queryOrderDataGrid(MbOrder mbOrder,PageHelper ph){
		return  mbOrderService.queryOrderDataGrid(mbOrder,ph);

	}


	/**
	 * 获取MbOrder数据表格
	 */
	@RequestMapping("/dataGridPrintList")
	@ResponseBody
	public DataGrid dataGridPrintList(MbOrder mbOrder, PageHelper ph) {
		ph.setSort("deliveryRequireTime asc,t.addtime");
		return mbOrderService.dataGrid(mbOrder, ph);
	}

	/**
	 * 获取用户最近订单信息
	 */
	@RequestMapping("/listOrderItem")
	@ResponseBody
	public DataGrid listOrderItem(MbOrder mbOrder, PageHelper ph) {
		return mbOrderService.listUserOrderItem(mbOrder, ph);
	}

	@RequestMapping("/deliveryDataGrid")
	@ResponseBody
	public DataGrid deliveryDataGrid(MbOrder mbOrder, PageHelper ph) {
		ph.setHiddenTotal(true);
		DataGrid deliveryDataGrid = mbOrderService.dataGrid(mbOrder, ph);
		MbOrder order = (MbOrder) deliveryDataGrid.getRows().get(0);

		if (!F.empty(order.getDeliveryDriver())) {
			User user = userService.get(order.getDeliveryDriver());
			if (user != null)
				order.setDeliveryDriverName(user.getNickname());
		}
		if (order.getDeliveryPrice() == null) {
			deliveryDataGrid.setRows(new ArrayList());
			deliveryDataGrid.setTotal((long) 0);
		}
		return deliveryDataGrid;
	}

	/**
	 * 获取MbOrder数据表格excel
	 */
	@RequestMapping("/download")
	public void download(MbOrder mbOrder, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbOrder,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbOrder页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbOrder mbOrder = new MbOrder();
		return "/mborder/mbOrderAdd";
	}

	/**
	 * 添加MbOrder
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbOrder mbOrder) {
		Json j = new Json();		
		mbOrderService.add(mbOrder);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 添加MbOrder页面
	 *
	 * @return
	 */
	@RequestMapping("/addByCallPage")
	public String addByCallPage() {
		return "/mborder/mbOrderAddByCall";
	}

	/**
	 * 添加MbOrder
	 *
	 * @return
	 */
	@RequestMapping(value = "/addByCall",method = RequestMethod.POST)
	@ResponseBody
	public Json addByCall(@RequestBody MbOrder mbOrder,HttpSession session,String isCheckedPayByVoucher) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());

		Json j = new Json();
		//mbOrderService.add(mbOrder);
		int orderPrice = 0;
		int totalPrice = 0;
		int payQuantity;
		Map<Integer, MbOrderItem> orderItemMap = new HashMap<>();
		for (MbOrderItem orderItem : mbOrder.getMbOrderItemList()) {
			if (orderItemMap.get(orderItem.getItemId()) == null) {
				orderItemMap.put(orderItem.getItemId(),orderItem);
			} else {
				MbOrderItem mbOrderItem = orderItemMap.get(orderItem.getItemId());
				mbOrderItem.setQuantity(mbOrderItem.getQuantity() + orderItem.getQuantity());
				orderItemMap.put(orderItem.getItemId(), mbOrderItem);
			}
		}
		mbOrder.setUsedByCoupons("checked".equals(isCheckedPayByVoucher) ? true : false);
		mbOrder.setMbOrderItemList(new ArrayList<MbOrderItem>(orderItemMap.values()));
		for (MbOrderItem mbOrderItem : mbOrder.getMbOrderItemList()) {
			//orderPrice count
			orderPrice += mbOrderItem.getBuyPrice()*mbOrderItem.getQuantity();
			//totalPrice count
			if (mbOrder.getUsedByCoupons()) {
				mbOrderItem.setVoucherQuantityTotal(mbOrderItem.getVoucherQuantityTotal() == null ? 0 : mbOrderItem.getVoucherQuantityTotal());
				payQuantity = mbOrderItem.getQuantity() - mbOrderItem.getVoucherQuantityTotal();
				payQuantity = payQuantity < 0 ? 0 : payQuantity;
				mbOrderItem.setVoucherQuantityUsed(mbOrderItem.getQuantity() - payQuantity);
				totalPrice += mbOrderItem.getBuyPrice() * payQuantity;
			}else {
				totalPrice = orderPrice ;
			}
		}
		mbOrder.setOrderPrice(orderPrice);
		mbOrder.setTotalPrice(totalPrice);


		//补齐门店数据
		MbShop mbShop = mbShopService.getFromCache(mbOrder.getShopId());
		mbOrder.setContactPhone(mbShop.getContactPhone());
		mbOrder.setContactPeople(mbShop.getContactPeople());
		if(F.empty(mbOrder.getDeliveryAddress()))
		mbOrder.setDeliveryAddress(mbShop.getAddress());

		//费用
		MbBalance mbBalance = mbBalanceService.addOrGetMbBalance(mbShop.getId());
		if (mbBalance.getAmount() >= totalPrice) {
			//支付相关数据
			mbOrder.setPayWay("PW01");
			//自动支付
			mbOrder.setStatus("OD10");
		}else{
			mbOrder.setStatus("OD09");
		}
		mbOrder.setDeliveryPrice(0);
		mbOrder.setAddLoginId(sessionInfo.getId());

		//TODO 找到他的用户ID
		mbOrder.setUserId(mbShop.getUserId());
		orderService02.handle(mbOrder);
		j.setObj(mbOrder.getId());
		j.setSuccess(true);
		j.setMsg("添加成功！");
		return j;
	}

	@RequestMapping(value = "/updateAddPayment",method = RequestMethod.POST)
	@ResponseBody
	public Json updateAddPayment(Integer id,String remark,HttpSession session) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		MbOrder mbOrder = new MbOrder();
		mbOrder.setId(id);
		mbOrder.setLoginId(sessionInfo.getId());
		mbOrder.setRemark(remark);
		mbOrder.setStatus("OD45");
		try {
			mbOrderService.transform(mbOrder);
			j.success();
		}catch (ServiceException e){
			j.setMsg(e.getMsg());
			j.fail();
		}
		return j;
	}

	@RequestMapping("/updateAddPaymentPage")
	public String updateAddPaymentPage(HttpServletRequest request, Integer id) {
		MbOrder mbOrder = new MbOrder();
		mbOrder.setId(id);
		request.setAttribute("mbOrder", mbOrder);
		return "/mborder/mbOrderPay";
	}

	/**
	 * 跳转到MbOrder查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id,String type) {
		if("BT005".equals(type) || "BT006".equals(type)) {
			MbOrderRefundLog mbOrderRefundLog = mbOrderRefundLogService.get(id);
			id = mbOrderRefundLog.getOrderId();
		}
		if("BT002".equals(type)) {
			MbPayment mbPayment = mbPaymentService.get(id);
			id = mbPayment.getOrderId();
		}
		MbOrder mbOrder = mbOrderService.get(id);
        Integer shopId = mbOrder.getShopId();
        if (mbOrder.getUserId() != null) {
            MbUser mbUser = mbUserService.getFromCache(mbOrder.getUserId());
            if (mbUser != null) {
                mbOrder.setUserNickName(mbUser.getNickName());
				if(shopId == null){
					shopId = mbUser.getShopId();
				}
            }
        }

		if (shopId != null) {
			MbShop mbShop = mbShopService.getFromCache(shopId);
			if (mbShop != null) {
				mbOrder.setShopId(mbShop.getId());
				mbOrder.setShopName(mbShop.getName());
			}
		}

        Integer deliveryPrice = mbOrderItemService.getDeliveryPrice(mbOrder.getId());
        mbOrder.setDeliveryPrice(deliveryPrice);

        Integer totalPrice = mbOrderItemService.getTotalPrice(mbOrder.getId());
        mbOrder.setTotalPrice(totalPrice);

        Integer orderPrice = totalPrice - deliveryPrice;
        mbOrder.setOrderPrice(orderPrice);

        MbOrderInvoice mbOrderInvoice = mbOrderInvoiceService.getByOrderId(mbOrder.getId());
        if (mbOrderInvoice != null) {
            mbOrder.setInvoiceStatus(mbOrderInvoice.getInvoiceStatus());
        }
		Integer warehouseId = mbOrder.getDeliveryWarehouseId();
		if (warehouseId != null) {
			MbWarehouse mbWarehouse = mbWarehouseService.getFromCache(warehouseId);
			if (mbWarehouse != null) {
				request.setAttribute("warehouseName", mbWarehouse.getName());
			}
		}
		if (mbOrder.getAddLoginId() != null) {
			User user = userService.getFromCache(mbOrder.getAddLoginId());
			if(user != null){
				request.setAttribute("addLoginName", user.getNickname());
			}
		}

		request.setAttribute("mbOrder", mbOrder);
		request.setAttribute("cancelStatusList", Arrays.asList(new String[]{"OD20","OD30","OD35","OD12","OD15"}));
		return "/mborder/mbOrderView";
	}

	/**
	 * 跳转到MbOrder修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbOrder mbOrder = mbOrderService.get(id);
		request.setAttribute("mbOrder", mbOrder);
		return "/mborder/mbOrderEdit";
	}

	@RequestMapping("/deliveryItemPage")
	public String deliveryItemPage(HttpServletRequest request, Integer id) {
		request.setAttribute("orderId", id);
		MbOrder mbOrder = mbOrderService.get(id);
		request.setAttribute("mbOrder", mbOrder);
		return "/mborder/mbOrderDelivery";
	}

	@RequestMapping("/deliveryItem")
	@ResponseBody
	public Json deliveryItem(Integer id,String deliveryDriver, String deliveryWay,Integer warehouseId, String remark, HttpSession session) {
		Json json = new Json();

		if(F.empty(warehouseId)){
			json.setMsg("发送仓库不能为空");
			return json;
		}
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());

		MbOrder mbOrder = new MbOrder();
		mbOrder.setId(id);
		mbOrder.setDeliveryWay(deliveryWay);
		mbOrder.setUserRemark(remark);
		mbOrder.setLoginId(sessionInfo.getId());
		mbOrder.setDeliveryWarehouseId(warehouseId);
		mbOrder.setDeliveryDriver(deliveryDriver);
		mbOrder.setStatus("OD20");
		mbOrderService.transform(mbOrder);

		json.setSuccess(true);
		json.setMsg("发货成功！");

		return json;
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping("/completeDelivery")
	public String completeDelivery(HttpServletRequest request,Integer id){
		request.setAttribute("orderId", id);
		return "mborder/mbOrderCompleteDeliveryLogEdit";

	}

	@RequestMapping("/confirmMbOrderCallbackItemView")
	public String confirmMbOrderCallbackItemPage(HttpServletRequest request,Integer id) {
		request.setAttribute("orderId", id);
		return "/mborder/confirmMbOrderCallbackItemPage";
	}
	@RequestMapping("/confirmMbOrderCallbackItem")
	@ResponseBody
	public Json confirm(MbOrder mbOrder, HttpSession session){
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		mbOrder.setLoginId(sessionInfo.getId());
		mbOrderService.transform(mbOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功");
		return j;
	}

	/**
	 * 跳转到汇款审核页面
	 *
	 * @return
	 */
	@RequestMapping("/updateAuditPage")
	public String editAuditPage(HttpServletRequest request, Integer id) {
		request.setAttribute("orderId", id);
		return "/mborder/mbOrderAudit";
	}

	/**
	 * 审核
	 * @param mbOrder
	 * @param session
	 * @return
	 */
	@RequestMapping("/editAudit")
	@ResponseBody
	public Json editAudit(MbOrder mbOrder,HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		mbOrder.setLoginId(sessionInfo.getId());
		if(F.empty(mbOrder.getRemark())){
			j.setSuccess(false);
			j.setMsg("请输入原因");
		}
		else if(F.empty(mbOrder.getPayCode()) && "OD10".equals(mbOrder.getStatus())){
			j.setSuccess(false);
			j.setMsg("请输入银行汇款单号");
		}
		else{
			try {
				mbOrderService.transform(mbOrder);
				j.setSuccess(true);
				j.setMsg("编辑成功！");
			}
			catch (ServiceException se){
				j.setSuccess(false);
				j.setMsg(se.getMsg());
			}
		}
		return j;
	}

	/**
	 * 修改MbOrder
	 * 
	 * @param mbOrder
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbOrder mbOrder) {
		Json j = new Json();		
		mbOrderService.edit(mbOrder);

//		try {
//			ruleEngineTest.executeTest2(mbOrder);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbOrder
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbOrderService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 跳转到开票页面
	 *
	 * @return
	 */
	@RequestMapping("/editInvoicePage")
	public String editInvoicePage(HttpServletRequest request, Integer id){
		MbOrder mbOrder = mbOrderService.get(id);
		request.setAttribute("mbOrder", mbOrder);
		return "/mborder/mbOrderInvoiceEdit";
	}

	/**
	 * 开票
	 * @param id
	 * @param remark
	 * @return
	 */
	@RequestMapping("/editInvoice")
	@ResponseBody
	public Json editInvoice(Integer id,String remark, HttpSession session) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		mbOrderService.changeInvoiceStatus(id, remark, sessionInfo.getId());
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}
	/**
	 * 跳转到订单审核页面
	 *
	 * @return
	 */
	@RequestMapping("/editOrderAcceptAuditPage")
	public String editOrderAcceptAuditPage(HttpServletRequest request, Integer id){
		MbOrder mbOrder = mbOrderService.get(id);
		if(mbOrder.getDeliveryRequireTime()== null){
			//加一天
			mbOrder.setDeliveryRequireTime(DateUtil.addDayToDate(mbOrder.getUpdatetime(),1));
		}
		request.setAttribute("mbOrder", mbOrder);
		return "/mborder/mbOrderAcceptAudit";
	}

	/**
	 * 是否接收订单
	 * @param id
	 * @return
	 */
	@RequestMapping("/editOrderAcceptAudit")
	@ResponseBody
	public Json editOrderAcceptAudit(Integer id,String status,String reason,Date deliveryRequireTime,Integer warehouseId,HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		MbOrder mbOrder = new MbOrder();
		mbOrder.setStatus(status);
		mbOrder.setUserRemark(reason);
		mbOrder.setId(id);
		mbOrder.setDeliveryRequireTime(deliveryRequireTime);
		mbOrder.setDeliveryWarehouseId(warehouseId);
		mbOrder.setLoginId(sessionInfo.getId());
		mbOrderService.transform(mbOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}
	@RequestMapping("/editOrderPrint")
	@ResponseBody
	public Json editOrderPrint(Integer id,HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		MbOrder exist = mbOrderService.get(id);
		if(exist == null) {
			j.fail();
			j.setMsg("订单号不存在");
			return j;
		} else if(!"OD12".equals(exist.getStatus())) {
			j.fail();
			j.setMsg("订单号【"+id+"】重复确认打印，请认真核对信息！");
			return j;
		}

		MbOrder mbOrder = new MbOrder();
		mbOrder.setStatus("OD15");
		mbOrder.setId(id);
		mbOrder.setLoginId(sessionInfo.getId());
		mbOrderService.transform(mbOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}
	/**
	 * 获取打印数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/printOrderView")
	public String printOrderView(HttpServletRequest request,Integer id){
		MbOrder mbOrder = mbOrderService.get(id);
		List<MbOrderItem> mbOrderItemList = mbOrderItemService.getMbOrderItemList(mbOrder.getId());
		MbPayment mbPayment = mbPaymentService.getByOrderId(id);
		if(!F.empty(mbOrder.getShopId())) {
			MbShop mbShop = mbShopService.getFromCache(mbOrder.getShopId());
			if(!F.empty(mbShop.getRegionId())){
				mbShop.setRegionPath(diveRegionService.getRegionPath(mbShop.getRegionId()+""));
			}
			request.setAttribute("mbShop", mbShop);
			MbBalance mbBalance = mbBalanceService.addOrGetMbBalance(mbOrder.getShopId());
			Integer amount = mbOrderService.getOrderDebtMoney(mbShop.getId());
			amount = amount == null ? 0 : amount;
			mbBalance.setAmount(mbBalance.getAmount() - amount);
			request.setAttribute("mbBalance", mbBalance);
		}

		request.setAttribute("mbOrder", mbOrder);
		request.setAttribute("mbOrderItemList", mbOrderItemList);
		request.setAttribute("mbPayment", mbPayment);
		request.setAttribute("printTime",DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));

		return "/mborder/mbOrderPrint";
	}

	@RequestMapping("/updateCancelOrderPage")
	public String updateCancelOrderPage(HttpServletRequest request, Integer id) {
		request.setAttribute("orderId", id);
		return "/mborder/mbOrderCancelOrder";
	}

	@RequestMapping("/updateCancelOrder")
	@ResponseBody
	public Json updateCancelOrder(Integer id, String remark, HttpSession session) {
		Json json = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		MbOrder mbOrder = new MbOrder();
		mbOrder.setId(id);
		mbOrder.setRemark(remark);
		mbOrder.setStatus("OD32");
		mbOrder.setLoginId(sessionInfo.getId());
		mbOrderService.transform(mbOrder);
		json.setSuccess(true);
		json.setMsg("取消订单成功！");
		return json;
	}

	@RequestMapping("/updateDeliveryDriverPage")
	public String updateDeliveryDriverPage(HttpServletRequest request, Integer id) {
		MbOrder mbOrder = mbOrderService.get(id);
		request.setAttribute("mbOrder", mbOrder);
		return "/mborder/mbOrderDeliveryDriver";
	}

	@RequestMapping("/updateDeliveryDriver")
	@ResponseBody
	public Json updateDeliveryDriver(Integer id, String deliveryDriver, String remark, HttpSession session) {
		Json json = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		mbOrderService.editOrderDeliveryDriver(id, deliveryDriver, remark, sessionInfo.getId());
		json.setSuccess(true);
		json.setMsg("分配司机成功！");
		return json;
	}

	/**
	 * 跳转到MbOrder管理页面
	 *
	 * @return
	 */
	@RequestMapping("/managerDistribution")
	public String managerDistribution() {
		return "/mborder/MbOrderDistribution";
	}

	/**
	 * 显示订单分布图
	 * @param mbOrder
	 * @return
	 */
	@RequestMapping("/viewChart")
	@ResponseBody
	public List<MbOrderDistribution> viewChart(@RequestBody MbOrder mbOrder, HttpServletRequest request) {
		if (mbOrder.getOrderTimeBegin() != null && mbOrder.getOrderTimeEnd() != null) {
			List<MbOrderDistribution> list = mbOrderService.getOrderDistributionData(mbOrder);
			return list;
		}
		return null;
	}
}
