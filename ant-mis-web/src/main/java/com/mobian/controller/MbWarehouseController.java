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
import com.mobian.service.MbWarehouseServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * MbWarehouse管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbWarehouseController")
public class MbWarehouseController extends BaseController {

	@Autowired
	private MbWarehouseServiceI mbWarehouseService;


	/**
	 * 跳转到MbWarehouse管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbwarehouse/mbWarehouse";
	}

	/**
	 * 获取MbWarehouse数据表格

	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbWarehouse mbWarehouse, PageHelper ph) {
		return mbWarehouseService.dataGrid(mbWarehouse, ph);
	}
	/**
	 * 获取MbWarehouse数据表格excel
	 */
	@RequestMapping("/download")
	public void download(MbWarehouse mbWarehouse, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbWarehouse,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbWarehouse页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbWarehouse mbWarehouse = new MbWarehouse();
		return "/mbwarehouse/mbWarehouseAdd";
	}

	/**
	 * 添加MbWarehouse
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbWarehouse mbWarehouse) {
		Json j = new Json();

		if (mbWarehouseService.isWarehouseExists(mbWarehouse)) {
		    j.setSuccess(false);
		    j.setMsg("该仓库代码已存在！");
        } else {
            mbWarehouseService.add(mbWarehouse);
            j.setSuccess(true);
            j.setMsg("添加成功！");
        }

		return j;
	}

	/**
	 * 跳转到MbWarehouse查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbWarehouse mbWarehouse = mbWarehouseService.get(id);
		request.setAttribute("mbWarehouse", mbWarehouse);
		return "/mbwarehouse/mbWarehouseView";
	}

	/**
	 * 跳转到MbWarehouse修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbWarehouse mbWarehouse = mbWarehouseService.get(id);
		request.setAttribute("mbWarehouse", mbWarehouse);
		return "/mbwarehouse/mbWarehouseEdit";
	}

	/**
	 * 修改MbWarehouse
	 * 
	 * @param mbWarehouse
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbWarehouse mbWarehouse) {
		Json j = new Json();

		if (mbWarehouseService.isWarehouseExists(mbWarehouse)
                && doesWarehouseCodeChanged(mbWarehouse)) {
		    j.setSuccess(false);
		    j.setMsg("该仓库代码已存在！");
        } else {
            mbWarehouseService.edit(mbWarehouse);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
        }

		return j;
	}

	private boolean doesWarehouseCodeChanged(MbWarehouse mbWarehouse) {
	    MbWarehouse dbWarehouse = mbWarehouseService.get(mbWarehouse.getId());
	    if (dbWarehouse.getCode().equals(mbWarehouse.getCode())) {
	        return false;
        }
        return true;
    }

	/**
	 * 删除MbWarehouse
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbWarehouseService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/selectQuery")
	@ResponseBody
	public List<Tree> query(String q) {
		MbWarehouse mbWarehouse = new MbWarehouse();
		List<Tree> lt = new ArrayList<Tree>();
		if (!F.empty(q)) {
			mbWarehouse.setName(q);
		} else {
			return lt;
		}
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		ph.setPage(100);
		DataGrid diveRegionList = mbWarehouseService.dataGrid(mbWarehouse, ph);
		List<MbWarehouse> rows = diveRegionList.getRows();
		if (!CollectionUtils.isEmpty(rows)) {
			for (MbWarehouse d : rows) {
				Tree tree = new Tree();
				tree.setId(d.getId() + "");
				tree.setPid(d.getRegionId() + "");
				tree.setText(d.getName());
				tree.setParentName(d.getRegionPath());
				lt.add(tree);
			}
		}
		return lt;
	}

	/**
	 * 跳转到仓库地图页面并传递地图数据
	 * @param request
	 * @param mbWarehouse
	 * @return
	 */
	@RequestMapping("/getWarehouseMap")
	public String getWarehouseMap(HttpServletRequest request,MbWarehouse mbWarehouse) {
		List<MbWarehouse> mbWarehouses=mbWarehouseService.getWarehouseMapData(mbWarehouse);
		request.setAttribute("mbWarehouseData",JSON.toJSONString(mbWarehouses));
		return "/mbwarehouse/mbWarehouseMap";
	}

}
