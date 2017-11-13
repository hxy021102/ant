package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bx.ant.pageModel.*;
import com.bx.ant.service.DriverAccountServiceI;
import com.bx.ant.service.DriverOrderShopBillServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.util.ConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * DriverOrderShopBill管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/driverOrderShopBillController")
public class DriverOrderShopBillController extends BaseController {

	@Resource
	private DriverOrderShopBillServiceI driverOrderShopBillService;
	@Resource
	private DriverAccountServiceI driverAccountService;

	/**
	 * 跳转到DriverOrderShopBill管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/driverordershopbill/driverOrderShopBill";
	}

	/**
	 * 获取DriverOrderShopBill数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DriverOrderShopBill driverOrderShopBill, PageHelper ph) {
		return driverOrderShopBillService.dataGridView(driverOrderShopBill, ph);
	}
	/**
	 * 获取DriverOrderShopBill数据表格excel
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
	public void download(DriverOrderShopBill driverOrderShopBill, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(driverOrderShopBill,ph);
		DataGrid dataGrid = new DataGrid();
		List<DriverOrderShopBill> driverOrderShopBills =dg.getRows();
		if(CollectionUtils.isNotEmpty(driverOrderShopBills)){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<DriverOrderShopBillExt> driverOrderShopBillExtList =new ArrayList<DriverOrderShopBillExt>();
			for (DriverOrderShopBill  orderShopBill : driverOrderShopBills) {
				DriverOrderShopBillExt driverOrderShopBillExt=new DriverOrderShopBillExt();
				BeanUtils.copyProperties(orderShopBill,driverOrderShopBillExt);
				String addDateStr= formatter.format(orderShopBill.getAddtime());
				driverOrderShopBillExt.setAddTimeString(addDateStr);
				String startDateStr= formatter.format(orderShopBill.getStartDate());
				driverOrderShopBillExt.setStartTimeString(startDateStr);
				String endDateStr= formatter.format(orderShopBill.getEndDate());
				driverOrderShopBillExt.setEndTimeString(endDateStr);
				driverOrderShopBillExt.setAmountElement(orderShopBill.getAmount()/100.0);
				driverOrderShopBillExtList.add(driverOrderShopBillExt);
			}
			dataGrid.setRows(driverOrderShopBillExtList);
		}
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		if (CollectionUtils.isNotEmpty(colums)) {
			for (Colum colum : colums) {
				switch (colum.getField()) {
					case "addtime":
						colum.setField("addTimeString");
						break;
					case "startDate":
						colum.setField("startTimeString");
						break;
					case "endDate":
						colum.setField("endTimeString");
						break;
					case "amount":
						colum.setField("amountElement");
						break;

				}
			}
		}
		downloadTable(colums, dataGrid, response);
	}
	/**
	 * 跳转到添加DriverOrderShopBill页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DriverOrderShopBill driverOrderShopBill = new DriverOrderShopBill();
		return "/driverordershopbill/driverOrderShopBillAdd";
	}

	/**
	 * 添加DriverOrderShopBill
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DriverOrderShopBill driverOrderShopBill) {
		Json j = new Json();		
		driverOrderShopBillService.add(driverOrderShopBill);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DriverOrderShopBill查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Long id) {
		DriverOrderShopBill driverOrderShopBill = driverOrderShopBillService.getView(id);
		request.setAttribute("driverOrderShopBill", driverOrderShopBill);
		return "/driverordershopbill/driverOrderShopBillView";
	}

	/**
	 * 跳转到DriverOrderShopBill修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		DriverOrderShopBill driverOrderShopBill = driverOrderShopBillService.get(id);
		request.setAttribute("driverOrderShopBill", driverOrderShopBill);
		return "/driverordershopbill/driverOrderShopBillEdit";
	}

	/**
	 * 修改DriverOrderShopBill
	 * 
	 * @param driverOrderShopBill
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DriverOrderShopBill driverOrderShopBill) {
		Json j = new Json();		
		driverOrderShopBillService.edit(driverOrderShopBill);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DriverOrderShopBill
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Long id) {
		Json j = new Json();
		driverOrderShopBillService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 创建骑手结算账单
	 * @param driverOrderShopBillView
	 * @return
	 */
	@RequestMapping(value="/addDriverOrderBill", method = RequestMethod.POST)
	@ResponseBody
	public Json addDriverOrderBill(@RequestBody DriverOrderShopBillView driverOrderShopBillView) {
		Json j = new Json();
		String result=driverOrderShopBillService.addDriverOrderShopBillandPay(driverOrderShopBillView);
		if(F.empty(result)) {
			j.setSuccess(true);
			j.setMsg("创建骑手账单成功！");
		}else {
			j.setSuccess(false);
			j.setMsg("失败,"+result+" 已经被创建！");
		}
		return j;
	}

	/**
	 * 跳转到骑手账单审核并支付页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/examinePage")
	public String examinePage(HttpServletRequest request, Long id) {
		DriverOrderShopBill driverOrderShopBill = driverOrderShopBillService.get(id);
		if(driverOrderShopBill!=null){
			DriverAccount driverAccount =driverAccountService.get(driverOrderShopBill.getDriverAccountId());
			DriverOrderShopBillView driverOrderShopBillView = new DriverOrderShopBillView();
			BeanUtils.copyProperties(driverOrderShopBill,driverOrderShopBillView);
			driverOrderShopBillView.setUserName(driverAccount.getUserName());
			request.setAttribute("driverOrderShopBill", driverOrderShopBillView);
		}
		return "driverordershopbill/driverOrderBillExamine";
	}



	@RequestMapping("/editState")
	@ResponseBody
	public Json editState(DriverOrderShopBill driverOrderShopBill, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		driverOrderShopBill.setHandleLoginId(sessionInfo.getId());
		driverOrderShopBillService.editDriverShopBillAndOrderPay(driverOrderShopBill);

		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

}
