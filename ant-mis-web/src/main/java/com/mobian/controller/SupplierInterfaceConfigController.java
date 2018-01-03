package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.SupplierInterfaceConfig;
import com.bx.ant.service.SupplierInterfaceConfigServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * SupplierInterfaceConfig管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/supplierInterfaceConfigController")
public class SupplierInterfaceConfigController extends BaseController {

	@javax.annotation.Resource
	private SupplierInterfaceConfigServiceI supplierInterfaceConfigService;


	/**
	 * 跳转到SupplierInterfaceConfig管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/supplierinterfaceconfig/supplierInterfaceConfig";
	}

	/**
	 * 获取SupplierInterfaceConfig数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(SupplierInterfaceConfig supplierInterfaceConfig, PageHelper ph) {
		return supplierInterfaceConfigService.dataGrid(supplierInterfaceConfig, ph);
	}
	/**
	 * 获取SupplierInterfaceConfig数据表格excel
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
	public void download(SupplierInterfaceConfig supplierInterfaceConfig, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(supplierInterfaceConfig,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加SupplierInterfaceConfig页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,String supplierId) {
		if (!F.empty(supplierId)) {
			request.setAttribute("supplierId", supplierId);
		}
		return "/supplierinterfaceconfig/supplierInterfaceConfigAdd";
	}

	/**
	 * 添加SupplierInterfaceConfig
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(SupplierInterfaceConfig supplierInterfaceConfig) {
		Json j = new Json();		
		supplierInterfaceConfigService.add(supplierInterfaceConfig);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到SupplierInterfaceConfig查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		SupplierInterfaceConfig supplierInterfaceConfig = supplierInterfaceConfigService.get(id);
		request.setAttribute("supplierInterfaceConfig", supplierInterfaceConfig);
		return "/supplierinterfaceconfig/supplierInterfaceConfigView";
	}

	/**
	 * 跳转到SupplierInterfaceConfig修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		SupplierInterfaceConfig supplierInterfaceConfig = supplierInterfaceConfigService.get(id);
		request.setAttribute("supplierInterfaceConfig", supplierInterfaceConfig);
		return "/supplierinterfaceconfig/supplierInterfaceConfigEdit";
	}

	/**
	 * 修 改SupplierInterfaceConfig
	 * 
	 * @param supplierInterfaceConfig
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(SupplierInterfaceConfig supplierInterfaceConfig) {
		Json j = new Json();		
		supplierInterfaceConfigService.edit(supplierInterfaceConfig);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除SupplierInterfaceConfig
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		supplierInterfaceConfigService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
