package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbItemCategoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * MbItemCategory管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbItemCategoryController")
public class MbItemCategoryController extends BaseController {

	public static final String ITEM_CATEGORY = "itemCategory";
	@Autowired
	private MbItemCategoryServiceI mbItemCategoryService;


	/**
	 * 跳转到MbItemCategory管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbitemcategory/mbItemCategory";
	}

	/**
	 * 获取MbItemCategory数据表格
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbItemCategory mbItemCategory, PageHelper ph) {
		return mbItemCategoryService.dataGrid(mbItemCategory, ph);
	}
	/**
	 * 获取MbItemCategory数据表格excel
	 */
	@RequestMapping("/download")
	public void download(MbItemCategory mbItemCategory, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbItemCategory,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbItemCategory页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbItemCategory mbItemCategory = new MbItemCategory();
		return "/mbitemcategory/mbItemCategoryAdd";
	}

	/**
	 * 添加MbItemCategory
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbItemCategory mbItemCategory,@RequestParam MultipartFile equipIconFile) {
		Json j = new Json();

		if (mbItemCategoryService.isItemCategoryExists(mbItemCategory)) {
			j.setSuccess(false);
			j.setMsg("该分类代码已存在！");
		} else {
			mbItemCategory.setUrl(uploadFile(ITEM_CATEGORY, equipIconFile));
			mbItemCategoryService.add(mbItemCategory);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		}

		return j;
	}

	/**
	 * 跳转到MbItemCategory查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbItemCategory mbItemCategory = mbItemCategoryService.get(id);
		request.setAttribute("mbItemCategory", mbItemCategory);
		return "/mbitemcategory/mbItemCategoryView";
	}

	/**
	 * 跳转到MbItemCategory修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbItemCategory mbItemCategory = mbItemCategoryService.get(id);
		request.setAttribute("mbItemCategory", mbItemCategory);
		return "/mbitemcategory/mbItemCategoryEdit";
	}

	/**
	 * 修改MbItemCategory
	 * 
	 * @param mbItemCategory
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbItemCategory mbItemCategory,@RequestParam MultipartFile equipIconFile) {
		Json j = new Json();

		if (mbItemCategoryService.isItemCategoryExists(mbItemCategory)
				&& doesItemCategoryCodeChanged(mbItemCategory)) {
			j.setSuccess(false);
			j.setMsg("该分类代码已存在！");
		} else {
			mbItemCategory.setUrl(uploadFile(ITEM_CATEGORY, equipIconFile));
			mbItemCategoryService.edit(mbItemCategory);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		}

		return j;
	}

	private boolean doesItemCategoryCodeChanged(MbItemCategory mbItemCategory) {
		MbItemCategory dbItemCategory = mbItemCategoryService.get(mbItemCategory.getId());
		if (dbItemCategory.getCode().equals(mbItemCategory.getCode())) {
			return false;
		}
		return true;
	}

	/**
	 * 删除MbItemCategory
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbItemCategoryService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
