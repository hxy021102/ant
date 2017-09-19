package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbShoppingServiceI;
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
 * MbShopping管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbShoppingController")
public class MbShoppingController extends BaseController {

	@Autowired
	private MbShoppingServiceI mbShoppingService;


	/**
	 * 跳转到MbShopping管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbshopping/mbShopping";
	}

	/**
	 * 获取MbShopping数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbShopping mbShopping, PageHelper ph) {
		return mbShoppingService.dataGrid(mbShopping, ph);
	}
	/**
	 * 获取MbShopping数据表格excel
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
	public void download(MbShopping mbShopping, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbShopping,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbShopping页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbShopping mbShopping = new MbShopping();
		return "/mbshopping/mbShoppingAdd";
	}

	/**
	 * 添加MbShopping
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbShopping mbShopping) {
		Json j = new Json();
		mbShoppingService.add(mbShopping);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbShopping查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbShopping mbShopping = mbShoppingService.get(id);
		request.setAttribute("mbShopping", mbShopping);
		return "/mbshopping/mbShoppingView";
	}

	/**
	 * 跳转到MbShopping修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbShopping mbShopping = mbShoppingService.get(id);
		request.setAttribute("mbShopping", mbShopping);
		return "/mbshopping/mbShoppingEdit";
	}

	/**
	 * 修改MbShopping
	 * 
	 * @param mbShopping
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbShopping mbShopping) {
		Json j = new Json();
		mbShoppingService.edit(mbShopping);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbShopping
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbShoppingService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
