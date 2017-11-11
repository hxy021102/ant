package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bx.ant.pageModel.DriverFreightRule;
import com.bx.ant.pageModel.DriverFreightRuleVO;
import com.bx.ant.service.DriverFreightRuleServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.*;

import com.mobian.service.DiveRegionServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.ConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * DriverFreightRule管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/driverFreightRuleController")
public class DriverFreightRuleController extends BaseController {

	@Resource
	private DriverFreightRuleServiceI driverFreightRuleService;

	@Resource
	private DiveRegionServiceI diveRegionService;

	@Resource
	private UserServiceI userService;


	/**
	 * 跳转到DriverFreightRule管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/driverfreightrule/driverFreightRule";
	}

	/**
	 * 获取DriverFreightRule数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DriverFreightRule driverFreightRule, PageHelper ph) {
		DataGrid dataGrid = new DataGrid();
		List<DriverFreightRule> l = driverFreightRuleService.dataGrid(driverFreightRule, ph).getRows();
		List<DriverFreightRuleVO> ol = new ArrayList<DriverFreightRuleVO>();
		if (CollectionUtils.isNotEmpty(l)) {
			for(DriverFreightRule t : l) {
				DriverFreightRuleVO o = new DriverFreightRuleVO();
				BeanUtils.copyProperties(t, o);
				if (!F.empty(t.getRegionId())) {
					o.setRegionPath(diveRegionService.getRegionPath(t.getRegionId() +""));
				}
				ol.add(o);
			}
		}
		dataGrid.setRows(ol);
		return dataGrid;
	}
	/**
	 * 获取DriverFreightRule数据表格excel
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
	public void download(DriverFreightRule driverFreightRule, PageHelper ph,String downloadFields,
						 HttpServletResponse response) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(driverFreightRule,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DriverFreightRule页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		DriverFreightRule driverFreightRule = new DriverFreightRule();
		return "/driverfreightrule/driverFreightRuleAdd";
	}

	/**
	 * 添加DriverFreightRule
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DriverFreightRule driverFreightRule, HttpSession session) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		String loginId = sessionInfo.getId();
		driverFreightRule.setLoginId(loginId);
		driverFreightRuleService.add(driverFreightRule);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DriverFreightRule查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DriverFreightRuleVO driverFreightRuleVO = new DriverFreightRuleVO();
		DriverFreightRule driverFreightRule = driverFreightRuleService.get(id);
		if (driverFreightRule != null) {

			BeanUtils.copyProperties(driverFreightRule, driverFreightRuleVO);
			if (!F.empty(driverFreightRule.getRegionId())) {
				driverFreightRuleVO.setRegionPath(diveRegionService.getRegionPath(driverFreightRule.getRegionId() + ""));
			}
			if (!F.empty(driverFreightRule.getLoginId())){
				User user = userService.getFromCache(driverFreightRule.getLoginId());
				if (user != null) {
					driverFreightRuleVO.setLoginName(user.getName());
				}
			}
		}
		request.setAttribute("driverFreightRule", driverFreightRuleVO);
		return "/driverfreightrule/driverFreightRuleView";
	}

	/**
	 * 跳转到DriverFreightRule修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DriverFreightRuleVO driverFreightRuleVO = new DriverFreightRuleVO();
		DriverFreightRule driverFreightRule = driverFreightRuleService.get(id);
		if (driverFreightRule != null) {

			BeanUtils.copyProperties(driverFreightRule, driverFreightRuleVO);
			if (!F.empty(driverFreightRule.getRegionId())) {
				driverFreightRuleVO.setRegionPath(diveRegionService.getRegionPath(driverFreightRule.getRegionId() + ""));
			}
		}
		request.setAttribute("driverFreightRule", driverFreightRuleVO);
		return "/driverfreightrule/driverFreightRuleEdit";
	}

	/**
	 * 修改DriverFreightRule
	 * 
	 * @param driverFreightRule
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DriverFreightRule driverFreightRule) {
		Json j = new Json();		
		driverFreightRuleService.edit(driverFreightRule);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DriverFreightRule
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		driverFreightRuleService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
