package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbOrderLogServiceI;
import com.mobian.service.MbOrderServiceI;
import com.mobian.util.ConfigUtil;
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
 * MbOrderLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbOrderLogController")
public class MbOrderLogController extends BaseController {

	@Autowired
	private MbOrderLogServiceI mbOrderLogService;

	@Autowired
	private MbOrderServiceI mbOrderService;

	/**
	 * 跳转到MbOrderLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mborderlog/mbOrderLog";
	}

	/**
	 * 获取MbOrderLog数据表格
	 * 
	 * @param mbOrderLog,ph
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbOrderLog mbOrderLog, PageHelper ph) {
		return mbOrderLogService.dataGrid(mbOrderLog, ph);
	}
	/**
	 * 获取MbOrderLog数据表格excel
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
	public void download(MbOrderLog mbOrderLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbOrderLog,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbOrderLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbOrderLog mbOrderLog = new MbOrderLog();
		return "/mborderlog/mbOrderLogAdd";
	}

	/**
	 * 添加MbOrderLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbOrderLog mbOrderLog) {
		Json j = new Json();
		mbOrderLogService.add(mbOrderLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbOrderLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbOrderLog mbOrderLog = mbOrderLogService.get(id);
		request.setAttribute("mbOrderLog", mbOrderLog);
		return "/mborderlog/mbOrderLogView";
	}

	/**
	 * 跳转到MbOrderLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbOrderLog mbOrderLog = mbOrderLogService.get(id);
		request.setAttribute("mbOrderLog", mbOrderLog);
		return "/mborderlog/mbOrderLogEdit";
	}

	/**
	 * 修改MbOrderLog
	 * 
	 * @param mbOrderLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbOrderLog mbOrderLog) {
		Json j = new Json();
		mbOrderLogService.edit(mbOrderLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 *
	 * @return
	 */
	@RequestMapping("/addCompleteDeliveryRemarkLog")
	@ResponseBody
	public Json addCompleteDeliveryRemarkLog(MbOrder mbOrder, HttpSession session){
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		mbOrder.setLoginId(sessionInfo.getId());
		mbOrderService.transform(mbOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功");
		return j;
	}
	/**
	 * 删除MbOrderLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbOrderLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
