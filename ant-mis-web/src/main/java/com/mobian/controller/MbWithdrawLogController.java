package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbWithdrawLogServiceI;
import com.mobian.util.BeanUtil;
import com.mobian.util.ConfigUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * MbWithdrawLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbWithdrawLogController")
public class MbWithdrawLogController extends BaseController {

	@Autowired
	private MbWithdrawLogServiceI mbWithdrawLogService;


	/**
	 * 跳转到MbWithdrawLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbwithdrawlog/mbWithdrawLog";
	}

	/**
	 * 获取MbWithdrawLog数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbWithdrawLog mbWithdrawLog, PageHelper ph) {
		return mbWithdrawLogService.dataGrid(mbWithdrawLog, ph);
	}
	/**
	 * 获取MbWithdrawLog数据表格excel
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
	public void download(MbWithdrawLog mbWithdrawLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbWithdrawLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbWithdrawLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbWithdrawLog mbWithdrawLog = new MbWithdrawLog();
		return "/mbwithdrawlog/mbWithdrawLogAdd";
	}

	/**
	 * 添加MbWithdrawLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbWithdrawLog mbWithdrawLog) {
		Json j = new Json();		
		mbWithdrawLogService.add(mbWithdrawLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbWithdrawLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbWithdrawLogView mbWithdrawLogView = mbWithdrawLogService.getView(id);

		request.setAttribute("mbWithdrawLog", mbWithdrawLogView);
		return "/mbwithdrawlog/mbWithdrawLogView";
	}

	/**
	 * 跳转到MbWithdrawLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbWithdrawLog mbWithdrawLog = mbWithdrawLogService.get(id);
		request.setAttribute("mbWithdrawLog", mbWithdrawLog);
		return "/mbwithdrawlog/mbWithdrawLogEdit";
	}

	/**
	 * 修改MbWithdrawLog
	 * 
	 * @param mbWithdrawLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbWithdrawLog mbWithdrawLog) {
		Json j = new Json();		
		mbWithdrawLogService.edit(mbWithdrawLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbWithdrawLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbWithdrawLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 转发至审核页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/editAuditPage")
	public String editAuditPage(HttpServletRequest request, Integer id) {
		request.setAttribute("id", id);
		return "/mbwithdrawlog/mbWithDrawLogAudit";
	}

	@RequestMapping("/editAudit")
	@ResponseBody
	public Json editAudit(MbWithdrawLog mbWithdrawLog, HttpSession session, HttpServletRequest request) {
		Json json = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		String loginId = sessionInfo.getId();

		mbWithdrawLogService.editAudit(mbWithdrawLog, loginId, request);



		return null;


	}

	@RequestMapping("/dataGridView")
	@ResponseBody
	public DataGrid dataGridView(MbWithdrawLogView mbWithdrawLogView, PageHelper pageHelper) {
		return mbWithdrawLogService.dataGridView(mbWithdrawLogView, pageHelper);
	}


}
