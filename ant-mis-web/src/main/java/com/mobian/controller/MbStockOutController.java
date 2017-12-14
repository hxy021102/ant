package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbStockOutOrderServiceI;
import com.mobian.service.MbStockOutServiceI;

import com.mobian.service.MbWarehouseServiceI;
import com.mobian.service.UserServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbStockOut管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbStockOutController")
public class MbStockOutController extends BaseController {

	@Autowired
	private MbStockOutServiceI mbStockOutService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private MbWarehouseServiceI mbWarehouseService;
	@Autowired
	private MbStockOutOrderServiceI mbStockOutOrderService;


	/**
	 * 跳转到MbStockOut管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbstockout/mbStockOut";
	}

	/**
	 * 获取MbStockOut数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbStockOut mbStockOut, PageHelper ph) {
		return mbStockOutService.dataGrid(mbStockOut, ph);
	}
	/**
	 * 获取MbStockOut数据表格excel
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
	public void download(MbStockOut mbStockOut, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbStockOut,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbStockOut页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbStockOut mbStockOut = new MbStockOut();
		return "/mbstockout/mbStockOutAdd";
	}

	/**
	 * 添加MbStockOut
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbStockOut mbStockOut,String dataGrid,String deliverOrderIds) {
		Json j = new Json();		
		if(F.empty(deliverOrderIds)) mbStockOutService.addStockOut(mbStockOut,dataGrid);
		else mbStockOutService.addStockOut(mbStockOut, dataGrid, deliverOrderIds);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbStockOut查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbStockOut mbStockOut = mbStockOutService.get(id);
		if (mbStockOut.getStockOutPeopleId() != null) {
			User user = userService.get(mbStockOut.getStockOutPeopleId());
			mbStockOut.setStockOutPeopleName(user.getNickname());
		}
		if (mbStockOut.getLoginId() != null) {
			User user = userService.get(mbStockOut.getLoginId());
			if (user != null) {
				mbStockOut.setLoginName(user.getName());
			}
		}
		if (mbStockOut.getWarehouseId() != null) {
			MbWarehouse mbWarehouse = mbWarehouseService.get(mbStockOut.getWarehouseId());
			mbStockOut.setWarehouseName(mbWarehouse.getName());
		}
		request.setAttribute("mbStockOut", mbStockOut);
		return "/mbstockout/mbStockOutView";
	}

	/**
	 * 跳转到MbStockOut修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbStockOut mbStockOut = mbStockOutService.get(id);
		request.setAttribute("mbStockOut", mbStockOut);
		return "/mbstockout/mbStockOutEdit";
	}

	/**
	 * 修改MbStockOut
	 * 
	 * @param mbStockOut
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbStockOut mbStockOut) {
		Json j = new Json();		
		mbStockOutService.edit(mbStockOut);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbStockOut
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id,PageHelper ph) {
		Json j = new Json();
		mbStockOutService.delete(id);
		mbStockOutService.deleteStockOutItem(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/viewDetails")
	public String viewDetails(HttpServletRequest request, Integer id) {
		MbStockOut mbStockOut = mbStockOutService.get(id);
		if (mbStockOut.getStockOutPeopleId() != null) {
			User user = userService.get(mbStockOut.getStockOutPeopleId());
			mbStockOut.setStockOutPeopleName(user.getNickname());
		}
		if (mbStockOut.getLoginId() != null) {
			User user = userService.get(mbStockOut.getLoginId());
			if (user != null) {
				mbStockOut.setLoginName(user.getName());
			}
		}
		if (mbStockOut.getWarehouseId() != null) {
			MbWarehouse mbWarehouse = mbWarehouseService.get(mbStockOut.getWarehouseId());
			mbStockOut.setWarehouseName(mbWarehouse.getName());
		}
		MbStockOutOrder mbStockOutOrder = new MbStockOutOrder();
		mbStockOutOrder.setMbStockOutId(id);
		List<MbStockOutOrder> mbStockOutOrders = mbStockOutOrderService.query(mbStockOutOrder);
		if (CollectionUtils.isNotEmpty(mbStockOutOrders)) {
			mbStockOutOrder = mbStockOutOrders.get(0);
		}
		mbStockOut.setDeliverOrderId(mbStockOutOrder.getDeliverOrderId());
		request.setAttribute("mbStockOut", mbStockOut);
		return "/mbstockout/mbStockOutDetails";
	}

}
