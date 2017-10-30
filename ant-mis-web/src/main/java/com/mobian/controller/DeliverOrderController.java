package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.DeliverOrderItemServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.SupplierItemRelationServiceI;
import com.bx.ant.service.SupplierServiceI;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.util.ConfigUtil;
import com.mobian.util.ImportExcelUtil;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		return deliverOrderService.dataGridWithName(deliverOrderQuery, ph);
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

	@RequestMapping("/addOrderBill")
	@ResponseBody
	public Json addOrderBill(Integer supplierId, String unpayDeliverOrders, Date startTime, Date endTime) {
		Json j = new Json();
		JSONArray jsonArray = JSONArray.fromObject(unpayDeliverOrders);
	   	List<DeliverOrder> list = (List<DeliverOrder>) jsonArray.toCollection(jsonArray,DeliverOrder.class);
	   	deliverOrderService.addOrderBill(list,supplierId,startTime,endTime);
	   	for(DeliverOrder d : list) {
	   		d.setPayStatus("DPS03");//结算中
			deliverOrderService.edit(d);
		}
	   	j.setMsg("生成账单成功");
	   	j.setSuccess(true);
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
}
