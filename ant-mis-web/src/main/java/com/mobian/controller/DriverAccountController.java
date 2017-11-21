package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.bx.ant.pageModel.*;
import com.bx.ant.service.DriverAccountServiceI;
import com.mobian.pageModel.*;
import com.mobian.util.ConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * DriverAccount管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/driverAccountController")
public class DriverAccountController extends BaseController {

	@javax.annotation.Resource
	private DriverAccountServiceI driverAccountService;


	/**
	 * 跳转到DriverAccount管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/driveraccount/driverAccount";
	}

	/**
	 * 获取DriverAccount数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DriverAccountQuery driverAccount, PageHelper ph) {
		return driverAccountService.dataGridView(driverAccount, ph);
	}
	/**
	 * 获取DriverAccount数据表格excel
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
	public void download(DriverAccountQuery driverAccount, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(driverAccount,ph);
		List<DriverAccountView> driverAccountViews = dg.getRows();
		if (CollectionUtils.isNotEmpty(driverAccountViews)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (DriverAccountView driverAccountView : driverAccountViews) {
				String addDateStr= formatter.format(driverAccountView.getAddtime());
				driverAccountView.setCreateDate(addDateStr);
				driverAccountView.setOnlineName(driverAccountView.getOnline()==true?"是":"否");
			}
		}
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		if (CollectionUtils.isNotEmpty(colums)) {
			for (Colum colum : colums) {
				switch (colum.getField()) {
					case "addtime":
						colum.setField("createDate");
						break;
					case "online":
						colum.setField("onlineName");
						break;
				}
			}
		}
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DriverAccount页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DriverAccount driverAccount = new DriverAccount();
		return "/driveraccount/driverAccountAdd";
	}

	/**
	 * 添加DriverAccount
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DriverAccount driverAccount) {
		Json j = new Json();		
		driverAccountService.add(driverAccount);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DriverAccount查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DriverAccountView driverAccountView = driverAccountService.getView(id);
		request.setAttribute("driverAccount", driverAccountView);
		return "/driveraccount/driverAccountView";
	}

	/**
	 * 跳转到DriverAccount修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DriverAccount driverAccount = driverAccountService.get(id);
		DriverAccountView driverAccountView =new DriverAccountView();
		BeanUtils.copyProperties(driverAccount,driverAccountView);
		request.setAttribute("driverAccount", driverAccountView);
		return "/driveraccount/driverAccountEdit";
	}

	/**
	 * 修改DriverAccount
	 * 
	 * @param driverAccount
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DriverAccount driverAccount) {
		Json j = new Json();		
		driverAccountService.edit(driverAccount);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DriverAccount
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		driverAccountService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 跳转到DriverAccount审核页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/examinePage")
	public String examinePage(HttpServletRequest request, Integer id) {
		DriverAccount driverAccount = driverAccountService.get(id);
		request.setAttribute("driverAccount", driverAccount);
		return "/driveraccount/driverAccountExamine";
	}

	/**
	 * 编辑审核状态
	 * @param driverAccount
	 * @param session
	 * @return
	 */
	@RequestMapping("/editState")
	@ResponseBody
	public Json editState(DriverAccount driverAccount, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		driverAccount.setHandleLoginId(sessionInfo.getId());
		driverAccountService.edit(driverAccount);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

}
