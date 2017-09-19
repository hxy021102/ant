package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbActivityActionServiceI;
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
 * MbActivityAction管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbActivityActionController")
public class MbActivityActionController extends BaseController {

	@Autowired
	private MbActivityActionServiceI mbActivityActionService;


	/**
	 * 跳转到MbActivityAction管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbactivityaction/mbActivityAction";
	}

	/**
	 * 获取MbActivityAction数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbActivityAction mbActivityAction, PageHelper ph) {
		return mbActivityActionService.dataGrid(mbActivityAction, ph);
	}
	/**
	 * 获取MbActivityAction数据表格excel
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
	public void download(MbActivityAction mbActivityAction, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbActivityAction,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbActivityAction页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbActivityAction mbActivityAction = new MbActivityAction();
		return "/mbactivityaction/mbActivityActionAdd";
	}

	/**
	 * 添加MbActivityAction
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbActivityAction mbActivityAction) {
		Json j = new Json();
		mbActivityActionService.add(mbActivityAction);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbActivityAction查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbActivityAction mbActivityAction = mbActivityActionService.get(id);
		request.setAttribute("mbActivityAction", mbActivityAction);
		return "/mbactivityaction/mbActivityActionView";
	}

	/**
	 * 跳转到MbActivityAction修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbActivityAction mbActivityAction = mbActivityActionService.get(id);
		request.setAttribute("mbActivityAction", mbActivityAction);
		return "/mbactivityaction/mbActivityActionEdit";
	}

	/**
	 * 修改MbActivityAction
	 * 
	 * @param mbActivityAction
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbActivityAction mbActivityAction) {
		Json j = new Json();
		mbActivityActionService.edit(mbActivityAction);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbActivityAction
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbActivityActionService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
