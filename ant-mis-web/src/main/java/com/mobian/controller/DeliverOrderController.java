package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
import com.mobian.absx.F;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderPay;
import com.bx.ant.pageModel.DeliverOrderQuery;
import com.bx.ant.pageModel.Supplier;
import com.bx.ant.service.DeliverOrderItemServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.SupplierItemRelationServiceI;
import com.bx.ant.service.SupplierServiceI;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.BasedataServiceI;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.service.MbStockOutOrderServiceI;
import com.mobian.util.ConfigUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.DateUtil;
import com.mobian.util.ImportExcelUtil;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DeliverOrder管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/deliverOrderController")
public class DeliverOrderController extends BaseController {

	@Resource
	private DeliverOrderServiceI deliverOrderService;
	@Resource
	private SupplierServiceI supplierService;
	@Resource
	private DeliverOrderItemServiceI deliverOrderItemService;

	@Resource
	private SupplierItemRelationServiceI supplierItemRelationService;
    @Autowired
	private BasedataServiceI basedataService;
    @Resource
	private DeliverOrderShopServiceI deliverOrderShopService;

	@Resource
	private MbShopServiceI mbShopService;

	@Autowired
	private MbStockOutOrderServiceI mbStockOutOrderService;

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
	 * 跳转到DeliverOrder管理页面
	 *
	 * @return
	 */
	@RequestMapping("/unPayOrderManager")
	public String unPayOrdermanager(HttpServletRequest request) {
		return "/deliverorder/unPayDeliverOrder";
	}

	/**
	 * 获取DeliverOrder数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DeliverOrderQuery deliverOrderQuery, PageHelper ph) {
		if(!F.empty(ph.getSort()) && "shopName".equals(ph.getSort())) ph.setSort("shopId");

        if(deliverOrderQuery.getTime()!=null&&deliverOrderQuery.getTime()!=0){
			deliverOrderQuery.setTime(Integer.valueOf(ConvertNameUtil.getString("DSV500", "30")));
			return deliverOrderService.dataGridOutTimeDeliverOrder(deliverOrderQuery, ph);
		}else if("notDriver,".equals(deliverOrderQuery.getStatus())){
        	return deliverOrderService.dataGridNotDriverDeliverOrder(deliverOrderQuery,ph);
		}else {
			DataGrid dg = deliverOrderService.dataGridWithName(deliverOrderQuery, ph);

			// 统计运单创建出库单次数
			if(DeliverOrderServiceI.AGENT_STATUS_DTS02.equals(deliverOrderQuery.getAgentStatus())) {
				List<DeliverOrderQuery> list = dg.getRows();
				Integer[] ids = new Integer[list.size()];
				for (int i = 0; i < list.size(); i++) {
					ids[i] = list.get(i).getId().intValue();
				}
				MbStockOutOrder mbStockOutOrder = new MbStockOutOrder();
				mbStockOutOrder.setDeliverOrderIds(ids);
				List<MbStockOutOrder> stockOutOrders = mbStockOutOrderService.query(mbStockOutOrder);
				if(CollectionUtils.isNotEmpty(stockOutOrders)) {
					Map<Long, Integer> numMap = new HashMap<Long, Integer>();
					for(MbStockOutOrder stockOutOrder : stockOutOrders) {
						if(numMap.containsKey(stockOutOrder.getDeliverOrderId().longValue())) {
							numMap.put(stockOutOrder.getDeliverOrderId().longValue(), numMap.get(stockOutOrder.getDeliverOrderId().longValue()) + 1);
						} else {
							numMap.put(stockOutOrder.getDeliverOrderId().longValue(), 1);
						}
					}
					for(DeliverOrderQuery order : list) {
						order.setStockOutNum(numMap.get(order.getId()));
					}
				}

			}
			return dg;
		}


	}
	@RequestMapping("/unPayOrderDataGrid")
	@ResponseBody
	public DataGrid unPayOrderDataGrid(DeliverOrder deliverOrder, PageHelper ph) {
		DataGrid g = deliverOrderService.unPayOrderDataGrid(deliverOrder, ph);
		List<DeliverOrder> list = g.getRows();
		List<DeliverOrderQuery>  deliverOrderQueries = new ArrayList<DeliverOrderQuery>();
		Integer amount = 0;
		for(DeliverOrder d : list) {
			if(d.getAmount() != null) {
				amount += d.getAmount();
			}
			DeliverOrderQuery deliverOrderQuery  = new DeliverOrderQuery();
			BeanUtils.copyProperties(d,deliverOrderQuery);
			if(d.getSupplierId() != null) {
				Supplier s = supplierService.get(d.getSupplierId());
				deliverOrderQuery.setSupplierName(s.getName());
			}
			if(d.getStatus() != null) {
				deliverOrderQuery.setStatusName(d.getStatus());
			}
			if(d.getDeliveryStatus() != null) {
				deliverOrderQuery.setDeliveryStatusName(d.getDeliveryStatus());
			}
			if(d.getPayStatus() != null) {
				deliverOrderQuery.setPayStatusName(d.getPayStatus());
			}
			deliverOrderQueries.add(deliverOrderQuery);
		}
		DataGrid dg = new DataGrid();
		dg.setTotal(g.getTotal());
		dg.setRows(deliverOrderQueries);
		List<DeliverOrderQuery> footer = new ArrayList<DeliverOrderQuery>();
		DeliverOrderQuery totalRow = new DeliverOrderQuery();
		totalRow.setId(null);
		totalRow.setSupplierName("总计");
		totalRow.setAmount(amount);
		footer.add(totalRow);
		dg.setFooter(footer);
		return  dg;
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
	public void download(DeliverOrderQuery deliverOrderQuery, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
		DataGrid dg = dataGrid(deliverOrderQuery, ph);
		List<DeliverOrderQuery> deliverOrderQueries = dg.getRows();
		if (CollectionUtils.isNotEmpty(deliverOrderQueries)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (DeliverOrderQuery orderQuery : deliverOrderQueries) {
				String addDateStr= formatter.format(orderQuery.getAddtime());
				String requiredDateStr = formatter.format(orderQuery.getDeliveryRequireTime());
				orderQuery.setCreateDate(addDateStr);
				orderQuery.setRequiredDate(requiredDateStr);
				orderQuery.setAmountElement(orderQuery.getAmount() / 100.0);
			}
		}
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		if (CollectionUtils.isNotEmpty(colums)) {
			for (Colum colum : colums) {
				switch (colum.getField()) {
					case "amount":
						colum.setField("amountElement");
						break;
					case "addtime":
						colum.setField("createDate");
						break;
					case "deliveryRequireTime":
						colum.setField("requiredDate");
						break;
				}
			}
		}
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
	public Json add(DeliverOrder deliverOrder, String itemListStr, HttpSession  session) {
		Json j = new Json();
		if (!"[{\"status\":\"P\"}]".equals(itemListStr)) {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName()) ;
			String loginId = sessionInfo.getId();
			deliverOrderService.addAndItems(deliverOrder, itemListStr);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} else {
			j.setSuccess(false);
			j.setMsg("请确认已经选中商品列表中的商品");
		}
		return j;
	}

	/**
	 * 跳转到DeliverOrder查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Long id,Long deliverOrderShopId) {
		if (!F.empty(deliverOrderShopId)) {
			DeliverOrderShop deliverOrderShop = deliverOrderShopService.get(deliverOrderShopId);
			if (deliverOrderShop != null) {
				id = deliverOrderShop.getDeliverOrderId();
			}
		}
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

	@RequestMapping("/addOrderBill")
	@ResponseBody
	public Json addOrderBill(Integer supplierId, String unpayDeliverOrders, Date startTime, Date endTime) {
		Json j = new Json();
		JSONArray jsonArray = JSONArray.fromObject(unpayDeliverOrders);
	   	List<DeliverOrder> list = (List<DeliverOrder>) jsonArray.toCollection(jsonArray,DeliverOrder.class);
	   	List<DeliverOrderPay> d = deliverOrderService.addOrderBill(list,supplierId,startTime,endTime);
	   	if (CollectionUtils.isEmpty(d)) {
			j.setMsg("创建账单成功！");
			j.setSuccess(true);
		}else {
	   		String msg = "";
	   		for (DeliverOrderPay pay : d) {
	   			msg += pay.getDeliverOrderId().toString()+",";
			}
			j.setMsg("运单号" + msg +"已创建过账单！请勿重复创建！");
		}

	   	return  j;
	}

	@RequestMapping("/uploadPage")
	public String uploadPage(){
		return "/deliverorder/deliverOrderUpload";
	}

	@RequestMapping("/upload")
	@ResponseBody
	public Json upload(@RequestParam MultipartFile file, Integer supplierId) throws Exception {
		Json json = new Json();
		try {
			if (file.isEmpty()) {
				json.setMsg("未选中文件");
				json.setSuccess(false);
				return json;
			}
			InputStream in = file.getInputStream();
			List<List<Object>> listOb = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
			in.close();
			List<DeliverOrder> deliverOrderList = new ArrayList<>();
			Iterator<List<Object>> listIterator = listOb.iterator();
//			listIterator.next();
			while (listIterator.hasNext()) {
				List<Object> lo = listIterator.next();

				if(lo.size() < 6) throw new ServiceException("数据不完整,请确认除备注外是否有空数据");
				deliverOrderService.addByTableList(lo, supplierId);
				json.setSuccess(true);
				json.setMsg("添加完成");
				return json;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		json.setMsg("error");
		json.setSuccess(false);
		return json;
	}

	/**
	 * 跳转到指派运单给门店页
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/assignOrderShopPage")
	public String assignOrderShopPage(HttpServletRequest request, Long id,Long orderShopId) {
		DeliverOrder deliverOrder = deliverOrderService.get(id);
	 	request.setAttribute("deliverOrder", JSON.toJSONString(deliverOrder));
		request.setAttribute("id", id);
		request.setAttribute("orderShopId", orderShopId);
		return "/deliverorder/assignOrderShop";
	}

	/**
	 * 指派运单给门店
	 * @param deliverOrder
	 * @return
	 */
	@RequestMapping("/assignOrderShop")
	@ResponseBody
	public Json assignOrderShop(DeliverOrder deliverOrder) {
		Json j = new Json();
	 	Boolean result=deliverOrderService.handleAssignDeliverOrder(deliverOrder);
	 	if(result) {
			j.setSuccess(true);
			j.setMsg("指派成功！");
		}else{
			j.setSuccess(false);
			j.setMsg("指派失败，门店商品不足！");
		}
		return j;
	}

	/**
	 * 获取打印数据
	 * @return
	 */
	@RequestMapping("/printView")
	public String printView(HttpServletRequest request,String deliverOrderIds) {
		String[] ids = deliverOrderIds.split("[,]");
		Long[] idLong = new Long[ids.length];
		for (int i = 0; i < ids.length; i++) {
			idLong[i] = new Long(ids[i]);
		}
		DeliverOrderQuery query = new DeliverOrderQuery();
		query.setIds(idLong);
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		DataGrid dataGrid = deliverOrderService.dataGridExt(query, ph);
		List<DeliverOrderExt> deliverOrderExtList = dataGrid.getRows();
		for (DeliverOrderExt deliverOrderExt : deliverOrderExtList) {
			MbShop mbShop = mbShopService.getFromCache(deliverOrderExt.getShopId());
			deliverOrderExt.setMbShop(mbShop);
		}
		request.setAttribute("printTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

		request.setAttribute("deliverOrderExtList", deliverOrderExtList);
		return "/deliverorder/deliverOrderPrint";
	}

	/**
	 * 扫码打单页面
	 * @return
	 */
	@RequestMapping("/managerOrderSan")
	public String updateOrderSanPage() {
		return "/deliverorder/updateOrderSanPage";
	}

	/**
	 * 扫码打单，修改代送单状态为已打单
	 * @param session
	 * @param deliverOrderId
	 * @return
	 */
	@RequestMapping("/updateOrderSan")
	@ResponseBody
	public Json updateOrderSan( HttpSession session, Long deliverOrderId) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		DeliverOrder deliverOrder = deliverOrderService.get(deliverOrderId);
		if (deliverOrder != null) {
			if ("DTS01".equals(deliverOrder.getAgentStatus())&& "DAW04".equals(deliverOrder.getDeliveryWay())) {
				deliverOrder.setAgentStatus("DTS02");
				deliverOrderService.editAndAddLog(deliverOrder, DeliverOrderLogServiceI.TYPE_DLT15, "扫码打单成功", sessionInfo.getId());
				j.setMsg("单号："+deliverOrderId+",扫码打单成功！");
				j.setSuccess(true);
				return j;
			}else {
				j.setMsg("单号："+deliverOrderId+",已扫码打单，不能重复执行！");
			}
		} else {
			j.setMsg("单号："+deliverOrderId+",不存在，请确认订单是否正确！");
		}
		j.setSuccess(false);
		return j;
	}

	/**
	 * 扫码订单发货页面
	 * @return
	 */
	@RequestMapping("/managerOrderDeliverGoodsPage")
	public String updateOrderDeliverGoodsPage() {
		return "/deliverorder/updateOrderDeliverGoodsPage";
	}

	/**
	 * 修改订单为已发货
	 * @param session
	 * @param deliverOrderId
	 * @return
	 */
	@RequestMapping("/updateOrderDeliverGoods")
	@ResponseBody
	public Json updateOrderDeliverGoods(HttpSession session, Long deliverOrderId) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		DeliverOrder deliverOrder = deliverOrderService.get(deliverOrderId);
		if (deliverOrder != null) {
			if ("DTS02".equals(deliverOrder.getAgentStatus())&& "DAW04".equals(deliverOrder.getDeliveryWay())) {
				deliverOrder.setAgentStatus("DTS03");
				deliverOrderService.editAndAddLog(deliverOrder, DeliverOrderLogServiceI.TYPE_DLT14, "扫码发货成功", sessionInfo.getId());
				j.setMsg("单号："+deliverOrderId+",扫码发货成功！");
				j.setSuccess(true);
				return j;
			}else {
				j.setMsg("单号："+deliverOrderId+",已扫码发货，不能重复执行！");
			}
		} else {
			j.setMsg("单号："+deliverOrderId+",不存在，请确认订单是否正确！");
		}
		j.setSuccess(false);
		return j;
	}

	/**
	 * 批量修改订单为已发货
	 * @return
	 */
	@RequestMapping("/batchUpdateOrderDeliver")
	@ResponseBody
	public Json batchUpdateOrderDeliver(HttpSession session, String deliverOrderIds) {
		Json j = new Json();
		if(!F.empty(deliverOrderIds)) {
			for (String id : deliverOrderIds.split(",")) {
				if (!F.empty(id)) {
					updateOrderDeliverGoods(session, Long.valueOf(id));
				}
			}
		}
		j.setMsg("批量发货成功！");
		j.setSuccess(true);
		return j;
	}
}
