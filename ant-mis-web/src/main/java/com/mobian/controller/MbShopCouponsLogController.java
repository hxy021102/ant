package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbShopCouponsLogServiceI;
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
 * MbShopCouponsLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbShopCouponsLogController")
public class MbShopCouponsLogController extends BaseController {

	@Autowired
	private MbShopCouponsLogServiceI mbShopCouponsLogService;


	/**
	 * 跳转到MbShopCouponsLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbshopcouponslog/mbShopCouponsLog";
	}

	/**
	 * 获取MbShopCouponsLog数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbShopCouponsLog mbShopCouponsLog, PageHelper ph) {
		return mbShopCouponsLogService.dataGrid(mbShopCouponsLog, ph);
	}
	/**
	 * 获取MbShopCouponsLogView数据表格
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGridMbShopCouponsLogView")
	@ResponseBody
	public DataGrid dataGridMbShopCouponsLogView(MbShopCouponsLog mbShopCouponsLog) {
		return mbShopCouponsLogService.dataGridShopCouponsLogView(mbShopCouponsLog);
	}
	/**
	 * 获取MbShopCouponsLog数据表格excel
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
	public void download(MbShopCouponsLog mbShopCouponsLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbShopCouponsLog,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbShopCouponsLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbShopCouponsLog mbShopCouponsLog = new MbShopCouponsLog();
		return "/mbshopcouponslog/mbShopCouponsLogAdd";
	}

	/**
	 * 添加MbShopCouponsLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbShopCouponsLog mbShopCouponsLog) {
		Json j = new Json();
		mbShopCouponsLogService.add(mbShopCouponsLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbShopCouponsLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbShopCouponsLog mbShopCouponsLog = mbShopCouponsLogService.get(id);
		request.setAttribute("mbShopCouponsLog", mbShopCouponsLog);
		return "/mbshopcouponslog/mbShopCouponsLogView";
	}

	/**
	 * 跳转到MbShopCouponsLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbShopCouponsLog mbShopCouponsLog = mbShopCouponsLogService.get(id);
		request.setAttribute("mbShopCouponsLog", mbShopCouponsLog);
		return "/mbshopcouponslog/mbShopCouponsLogEdit";
	}

	/**
	 * 修改MbShopCouponsLog
	 * 
	 * @param mbShopCouponsLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbShopCouponsLog mbShopCouponsLog) {
		Json j = new Json();
		mbShopCouponsLogService.edit(mbShopCouponsLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbShopCouponsLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbShopCouponsLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
