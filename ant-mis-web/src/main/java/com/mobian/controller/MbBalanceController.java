package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.MbBalance;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbBalanceServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbBalance管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbBalanceController")
public class MbBalanceController extends BaseController {

	@Autowired
	private MbBalanceServiceI mbBalanceService;


	/**
	 * 跳转到MbBalance管理页面
	 *
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbbalance/mbBalance";
	}

	/**
	 * 获取MbBalance数据表格
	 * 
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbBalance mbBalance, PageHelper ph) {
		return mbBalanceService.dataGrid(mbBalance, ph);
	}
	/**
	 * 获取MbBalance数据表格excel
	 * 
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(MbBalance mbBalance, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbBalance,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbBalance页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbBalance mbBalance = new MbBalance();
		return "/mbbalance/mbBalanceAdd";
	}

	/**
	 * 添加MbBalance
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbBalance mbBalance) {
		Json j = new Json();		
		mbBalanceService.add(mbBalance);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbBalance查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbBalance mbBalance = mbBalanceService.get(id);
		request.setAttribute("mbBalance", mbBalance);
		return "/mbbalance/mbBalanceView";
	}

	/**
	 * crashView 押金
	 *
	 * @return
	 */
	@RequestMapping("/viewCash")
	public String cashView(HttpServletRequest request, Integer id, Integer shopId) {
//		MbBalance mbBalance = mbBalanceService.get(id);
		MbBalance mbBalance = mbBalanceService.getCashByShopId(shopId);
		request.setAttribute("mbBalance", mbBalance);
		return "/mbbalance/mbBalanceCashView";
	}

	/**
	 * 跳转到MbBalance修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbBalance mbBalance = mbBalanceService.get(id);
		request.setAttribute("mbBalance", mbBalance);
		return "/mbbalance/mbBalanceEdit";
	}

	/**
	 * 修改MbBalance
	 * 
	 * @param mbBalance
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbBalance mbBalance) {
		Json j = new Json();		
		mbBalanceService.edit(mbBalance);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbBalance
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbBalanceService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
