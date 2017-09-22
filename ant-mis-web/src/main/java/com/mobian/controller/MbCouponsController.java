package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbCouponsItemServiceI;
import com.mobian.service.MbCouponsServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbCoupons管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbCouponsController")
public class MbCouponsController extends BaseController {
	static final String COUPONS_TYPE_VOUCHER_1 = "CT001";
	static final String COUPONS_ERROR_MESSAGE_NO_ITEMS = "未添加券对应商品,请重新选择";

	@Autowired
	private MbCouponsServiceI mbCouponsService;
	@Autowired
	private MbCouponsItemServiceI mbCouponsItemService;


	/**
	 * 跳转到MbCoupons管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbcoupons/mbCoupons";
	}

	/**
	 * 获取MbCoupons数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbCoupons mbCoupons, PageHelper ph) {
		return mbCouponsService.dataGrid(mbCoupons, ph);
	}
	/**
	 * 获取MbCoupons数据表格excel
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
	public void download(MbCoupons mbCoupons, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbCoupons,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbCoupons页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/mbcoupons/mbCouponsAdd";
	}

	/**
	 * 添加MbCoupons
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbCoupons mbCoupons,String couponsItems) {
		Json j = new Json();
		if (F.empty(couponsItems)) {
			j.setSuccess(false);
			j.setMsg(COUPONS_ERROR_MESSAGE_NO_ITEMS);
			return j;
		}
			List<MbCouponsItem> couponsItemList = JSON.parseArray(couponsItems,MbCouponsItem.class);
		if (CollectionUtils.isEmpty(couponsItemList)) {
			j.setSuccess(false);
			j.setMsg(COUPONS_ERROR_MESSAGE_NO_ITEMS);
			return j;
		}
		if(COUPONS_TYPE_VOUCHER_1.equals(mbCoupons.getType())) {
			mbCouponsService.addCouponsAndCouponsItem(mbCoupons,couponsItemList.get(0));
		}
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbCoupons查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbCoupons mbCoupons = mbCouponsService.get(id);
		request.setAttribute("mbCoupons", mbCoupons);
		return "/mbcoupons/mbCouponsView";
	}

	/**
	 * 跳转到MbCoupons修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbCoupons mbCoupons = mbCouponsService.get(id);
		request.setAttribute("mbCoupons", mbCoupons);
		return "/mbcoupons/mbCouponsEdit";
	}

	/**
	 * 修改MbCoupons
	 * 
	 * @param mbCoupons
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbCoupons mbCoupons,String couponsItems) {
		Json j = new Json();
		if (F.empty(couponsItems)) {
			mbCouponsService.edit(mbCoupons);
		}else {
			List<MbCouponsItem> couponsItemList = JSON.parseArray(couponsItems,MbCouponsItem.class);
			if (CollectionUtils.isEmpty(couponsItemList)) {
				j.setSuccess(false);
				j.setMsg(COUPONS_ERROR_MESSAGE_NO_ITEMS);
				return j;
			}
			if(COUPONS_TYPE_VOUCHER_1.equals(mbCoupons.getType())) {
				mbCouponsService.editCouponsAndCouponsItem(mbCoupons,couponsItemList.get(0));
			}
		}
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbCoupons
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbCouponsService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}
	@RequestMapping("/selectQuery")
	@ResponseBody
	public List<Tree> selectQuery(String q) {
		MbCoupons m = new MbCoupons();
		List<Tree> lt = new ArrayList<Tree>();
		if (!F.empty(q)) {
			m.setKeyword(q);
			//保证券是有效的才能够被添加至门店
			m.setStatus("NS001");
		} else {
			return lt;
		}
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		ph.setPage(100);
		DataGrid list = mbCouponsService.dataGrid(m, ph);
		List<MbCoupons> rows = list.getRows();
		if (!CollectionUtils.isEmpty(rows)) {

			for (MbCoupons d : rows) {
				Tree tree = new Tree();
				tree.setId(d.getId() + "");
				tree.setText(d.getName());
				tree.setParentName(d.getCode());
				tree.setPid(d.getPrice().toString());
				lt.add(tree);
			}
		}
		return lt;
	}

}
