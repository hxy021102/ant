package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.MbActivity;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbActivityServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbActivity管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbActivityController")
public class MbActivityController extends BaseController {

	@Autowired
	private MbActivityServiceI mbActivityService;


	/**
	 * 跳转到MbActivity管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbactivity/mbActivity";
	}

	/**
	 * 获取MbActivity数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbActivity mbActivity, PageHelper ph) {
		return mbActivityService.dataGrid(mbActivity, ph);
	}
	/**
	 * 获取MbActivity数据表格excel
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
	public void download(MbActivity mbActivity, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbActivity,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbActivity页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbActivity mbActivity = new MbActivity();
		return "/mbactivity/mbActivityAdd";
	}

	/**
	 * 添加MbActivity
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbActivity mbActivity) {
		Json j = new Json();		
		mbActivityService.addActivityAndRuleSet(mbActivity);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbActivity查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbActivity mbActivity = mbActivityService.get(id);
		if(mbActivity.getValid()==true){
			mbActivity.setValidName("是");
		}else {
			mbActivity.setValidName("否");
		}
		request.setAttribute("mbActivity", mbActivity);
		return "/mbactivity/mbActivityView";
	}

	/**
	 * 跳转到MbActivity修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbActivity mbActivity = mbActivityService.get(id);
		request.setAttribute("mbActivity", mbActivity);
		return "/mbactivity/mbActivityEdit";
	}

	/**
	 * 修改MbActivity
	 * 
	 * @param mbActivity
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbActivity mbActivity) {
		Json j = new Json();		
		mbActivityService.editActivityAndRuleSet(mbActivity);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbActivity
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbActivityService.deleteActivityAndRuleSet(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
