package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbLogRecordServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * MbLogRecord管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbLogRecordController")
public class MbLogRecordController extends BaseController {

	@Autowired
	private MbLogRecordServiceI mbLogRecordService;


	/**
	 * 跳转到MbLogRecord管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mblogrecord/mbLogRecord";
	}

	/**
	 * 获取MbLogRecord数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbLogRecord mbLogRecord, PageHelper ph) {
		return mbLogRecordService.dataGrid(mbLogRecord, ph);
	}
	/**
	 * 获取MbLogRecord数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(MbLogRecord mbLogRecord, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbLogRecord,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbLogRecord页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbLogRecord mbLogRecord = new MbLogRecord();
		return "/mblogrecord/mbLogRecordAdd";
	}

	/**
	 * 添加MbLogRecord
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbLogRecord mbLogRecord) {
		Json j = new Json();		
		mbLogRecordService.add(mbLogRecord);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbLogRecord查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbLogRecord mbLogRecord = mbLogRecordService.get(id);
		request.setAttribute("mbLogRecord", mbLogRecord);
		return "/mblogrecord/mbLogRecordView";
	}

	/**
	 * 跳转到MbLogRecord修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbLogRecord mbLogRecord = mbLogRecordService.get(id);
		request.setAttribute("mbLogRecord", mbLogRecord);
		return "/mblogrecord/mbLogRecordEdit";
	}

	/**
	 * 修改MbLogRecord
	 * 
	 * @param mbLogRecord
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbLogRecord mbLogRecord) {
		Json j = new Json();		
		mbLogRecordService.edit(mbLogRecord);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbLogRecord
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbLogRecordService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
