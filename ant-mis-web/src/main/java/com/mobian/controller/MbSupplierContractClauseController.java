package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.MbSupplierContractClause;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbSupplierContractClauseServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbSupplierContractClause管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbSupplierContractClauseController")
public class MbSupplierContractClauseController extends BaseController {

	@Autowired
	private MbSupplierContractClauseServiceI mbSupplierContractClauseService;


	/**
	 * 跳转到MbSupplierContractClause管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbsuppliercontractclause/mbSupplierContractClause";
	}

	/**
	 * 获取MbSupplierContractClause数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbSupplierContractClause mbSupplierContractClause, PageHelper ph) {
		return mbSupplierContractClauseService.dataGrid(mbSupplierContractClause, ph);
	}
	/**
	 * 获取MbSupplierContractClause数据表格excel
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
	public void download(MbSupplierContractClause mbSupplierContractClause, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbSupplierContractClause,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbSupplierContractClause页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbSupplierContractClause mbSupplierContractClause = new MbSupplierContractClause();
		return "/mbsuppliercontractclause/mbSupplierContractClauseAdd";
	}

	/**
	 * 添加MbSupplierContractClause
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbSupplierContractClause mbSupplierContractClause) {
		Json j = new Json();		
		mbSupplierContractClauseService.add(mbSupplierContractClause);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbSupplierContractClause查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbSupplierContractClause mbSupplierContractClause = mbSupplierContractClauseService.get(id);
		request.setAttribute("mbSupplierContractClause", mbSupplierContractClause);
		return "/mbsuppliercontractclause/mbSupplierContractClauseView";
	}

	/**
	 * 跳转到MbSupplierContractClause修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbSupplierContractClause mbSupplierContractClause = mbSupplierContractClauseService.get(id);
		request.setAttribute("mbSupplierContractClause", mbSupplierContractClause);
		return "/mbsuppliercontractclause/mbSupplierContractClauseEdit";
	}

	/**
	 * 修改MbSupplierContractClause
	 * 
	 * @param mbSupplierContractClause
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbSupplierContractClause mbSupplierContractClause) {
		Json j = new Json();		
		mbSupplierContractClauseService.edit(mbSupplierContractClause);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbSupplierContractClause
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbSupplierContractClauseService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
