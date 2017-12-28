package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bx.ant.pageModel.SupplierView;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.bx.ant.service.SupplierServiceI;

import com.bx.ant.pageModel.Supplier;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.ConfigUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import org.springframework.web.multipart.MultipartFile;

/**
 * Supplier管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/supplierController")
public class SupplierController extends BaseController {
	public static final String SUPPLIER = "supplier";
	//图标最大上传数据尺寸200kB
	public static final Integer ICON_FILE_MAX_SIZE = 204800;
	//图标格式要求
	public String[] ICON_TYPE = {"png","PNG","jgp","JPG","jpeg","JPEG","gif","GIF"};

    @Resource
	private SupplierServiceI supplierService;
    @Autowired
	private UserServiceI userService;
    @Autowired
	private MbBalanceServiceI mbBalanceService;


	/**
	 * 跳转到Supplier管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/supplier/supplier";
	}

	/**
	 * 获取Supplier数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(HttpSession session,Supplier supplier, PageHelper ph) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		User user = userService.get(sessionInfo.getId());
		if ("URT02".equals(user.getRefType())) {
			supplier.setId(Integer.parseInt(user.getRefId()));
		}
		return supplierService.dataGrid(supplier, ph);
	}
	/**
	 * 获取Supplier数据表格excel
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
	public void download(Supplier supplier, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = supplierService.dataGrid(supplier,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加Supplier页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Supplier supplier = new Supplier();
		String loginId = sessionInfo.getId();
		supplier.setLoginId(loginId);
		request.setAttribute("supplier",supplier);
		return "/supplier/supplierAdd";
	}

	/**
	 * 添加Supplier
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Supplier supplier,@RequestParam MultipartFile equipIconFile) {
		Json j = new Json();
		supplier.setAppKey(UUID.randomUUID().toString());
		supplier.setAppSecret(UUID.randomUUID().toString());
		boolean checkResult = checkIconImage(equipIconFile,j);
		if (checkResult){
			supplier.setCharterUrl(uploadFile(SUPPLIER, equipIconFile));
			supplierService.add(supplier);
		}

		return j;
	}
	/**
	 * 对上传的图片格式进行验证,是否为ICON_TYPE数组内的格式之一
	 * 对上传的图片的大小进行验证,是否为小于ICON_FILE_MAX_SIZE
	 * @param equipIconFile
	 * @param j
	 * @return boolean
	 */
	private boolean checkIconImage(MultipartFile equipIconFile,Json j){
		boolean checkResult = false;
		String iconType ;
		String iconTypes = "";
		int i = 0 ;
		if(equipIconFile == null) return checkResult;
		if(equipIconFile.getSize()==0){
			j.setSuccess(true);
			j.setMsg("图片未更改！");
			checkResult = true ;
			return checkResult;
		}
		else {
			do{
				iconType = "image/" + ICON_TYPE[i];
				if(iconType.equals(equipIconFile.getContentType())){
					j.setSuccess(true);
					j.setMsg("编辑成功！");
					checkResult = true ;
					break;
				}else{
					iconTypes += ICON_TYPE[i]+",";
				}
				i++;
			}while (i<ICON_TYPE.length);
			if(i==ICON_TYPE.length) {
				j.setSuccess(false);
				j.setMsg("基本信息栏-上传图片格式要求:" + iconTypes.substring(0,iconTypes.length()-1));
			}
		}
		return checkResult;
	}
	/**
	 * 跳转到Supplier查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		Supplier supplier = supplierService.get(id);
		User user = userService.get(supplier.getLoginId());
		SupplierView supplierView=new SupplierView();
		BeanUtils.copyProperties(supplier,supplierView);
		MbBalance mbBalance = mbBalanceService.addOrGetAccessSupplierBalance(id);
		MbBalance mbBalanceBond = mbBalanceService.addOrGetAccessSupplierBond(id);
		MbBalance mbBalanceCredit = mbBalanceService.addOrGetAccessSupplierCredit(id);
		supplierView.setBalance(mbBalance.getAmount());
		supplierView.setBond(mbBalanceBond.getAmount());
		supplierView.setCredit(mbBalanceCredit.getAmount());
		supplier.setLoginName(user.getNickname());
		request.setAttribute("supplier", supplierView);
		return "/supplier/supplierView";
	}

	/**
	 * 跳转到Supplier修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		Supplier supplier = supplierService.get(id);
		request.setAttribute("supplier", supplier);
		return "/supplier/supplierEdit";
	}

	/**
	 * 修改Supplier
	 * 
	 * @param supplier
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Supplier supplier,@RequestParam MultipartFile equipIconFile) {
		Json j = new Json();
		boolean checkResult = checkIconImage(equipIconFile,j);
		if (checkResult){
			supplier.setCharterUrl(uploadFile(SUPPLIER, equipIconFile));
			supplierService.edit(supplier);
		}
		return j;
	}

	/**
	 * 删除Supplier
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		supplierService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/selectQuery")
	@ResponseBody
	public List<Tree> query(String q) {
		Supplier supplier = new Supplier();
		List<Tree> lt = new ArrayList<Tree>();
		if (!F.empty(q)) {
			supplier.setName(q);
		} else {
			return lt;
		}
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		ph.setPage(100);
		DataGrid suppliers = supplierService.dataGrid(supplier, ph);
		List<Supplier> rows = suppliers.getRows();
		if (!CollectionUtils.isEmpty(rows)) {

			for (Supplier d : rows) {
				Tree tree = new Tree();
				tree.setId(d.getId() + "");
				tree.setText(d.getName());
				tree.setParentName(d.getContacter());
				lt.add(tree);
			}
		}
		return lt;
	}

}
