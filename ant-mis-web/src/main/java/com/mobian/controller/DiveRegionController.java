package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.DiveRegionServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * DiveRegion管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/diveRegionController")
public class DiveRegionController extends BaseController {

	@Autowired
	private DiveRegionServiceI diveRegionService;


	/**
	 * 跳转到DiveRegion管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/diveregion/diveRegion";
	}

	/**
	 * 获取DiveRegion数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(DiveRegion diveRegion, PageHelper ph) {
		return diveRegionService.dataGrid(diveRegion, ph);
	}
	/**
	 * 获取DiveRegion数据表格excel
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
	public void download(DiveRegion diveRegion, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(diveRegion,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加DiveRegion页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/diveregion/diveRegionAdd";
	}

	/**
	 * 添加DiveRegion
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(DiveRegion diveRegion) {
		Json j = new Json();
		diveRegionService.add(diveRegion);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到DiveRegion查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		DiveRegion diveRegion = diveRegionService.get(id);
		request.setAttribute("diveRegion", diveRegion);
		return "/diveregion/diveRegionView";
	}

	/**
	 * 跳转到DiveRegion修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		DiveRegion diveRegion = diveRegionService.get(id);
		request.setAttribute("diveRegion", diveRegion);
		return "/diveregion/diveRegionEdit";
	}

	/**
	 * 修改DiveRegion
	 * 
	 * @param diveRegion
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(DiveRegion diveRegion) {
		Json j = new Json();
		diveRegionService.edit(diveRegion);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除DiveRegion
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		diveRegionService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 根据父编码获取列表
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/getByParentRegionId")
	@ResponseBody
	public Json getByParentRegionId(DiveRegion diveRegion) {
		Json j = new Json();
		try{
			j.setObj(diveRegionService.findAllByParams(diveRegion));
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}

	@RequestMapping("/selectQuery")
	@ResponseBody
	public List<Tree> query(String q) {
		DiveRegion diveRegion = new DiveRegion();
		List<Tree> lt = new ArrayList<Tree>();
		if(!F.empty(q)){
			diveRegion.setRegionLevel(3);
			diveRegion.setRegionNameZh(q);
		}else{
			return lt;
		}
		List<DiveRegion> diveRegionList = diveRegionService.findAllByParams(diveRegion);
		if(!CollectionUtils.isEmpty(diveRegionList)){
			for (DiveRegion d : diveRegionList) {
				Tree tree = new Tree();
				tree.setId(d.getRegionId());
				tree.setPid(d.getRegionParentId());
				tree.setText(d.getRegionNameZh());
				//tree.setIconCls(data.getIcon());
				if(!F.empty(tree.getPid())){
					DiveRegion diveRegion1 = diveRegionService.getFromCache(tree.getPid());
					if(diveRegion1!=null){
						tree.setParentName(diveRegion1.getRegionNameZh());
					}
				}
				lt.add(tree);
			}
		}
		return lt;
	}
}
