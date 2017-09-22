package com.mobian.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.BasetypeServiceI;
import com.mobian.service.BasedataServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 基础数据管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/basedataController")
public class BasedataController extends BaseController {

	@Autowired
	private BasedataServiceI basedataService;

	@Autowired
	private BasetypeServiceI basetypeService;

	/**
	 * 跳转到基础数据管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		//request.setAttribute("bugTypeList", bugTypeService.getBugTypeList());
		return "/admin/base";
	}

	/**
	 * 获取基础数据数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/basedataDataGrid")
	@ResponseBody
	public DataGrid dataGrid(BaseData bd, PageHelper ph) {
		return basedataService.dataGrid(bd, ph);
	}

	/**
	 * 跳转到添加基础数据页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/basedataAddPage")
	public String basedataAddPage(HttpServletRequest request,BaseType baseType) {
		BaseData b = new BaseData();
		b.setBasetypeCode(baseType.getCode());
		b.setCodeName(baseType.getName());
		request.setAttribute("basedata", b);
		//request.setAttribute("bugTypeList", bugTypeService.getBugTypeList());
		return "/admin/basedataAdd";
	}
	/**
	 * 跳转到添加基础类型页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/basetypeAddPage")
	public String basetypeAddPage(HttpServletRequest request) {
		return "/admin/basetypeAdd";
	}
	
	/**
	 * 获得基础类型列表
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<Tree> treeGrid() {
		List<Tree> tree = new ArrayList<Tree>();
		List<BaseType> baseTypes = basetypeService.treeGrid();
		Tree root = new Tree();
		String rootID = "root";
		root.setId(rootID);
		root.setState("open");
		root.setText("枚举");
		tree.add(root);
		if (CollectionUtils.isNotEmpty(baseTypes)) {
			Collections.sort(baseTypes, new Comparator<BaseType>() {
				@Override
				public int compare(BaseType o1, BaseType o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			for (BaseType t : baseTypes) {
				Tree node = new Tree();
				node.setId(t.getCode());
				node.setText(t.getName());
				node.setAttributes(t);
				node.setPid(rootID);
				if (t.getType() == 0) {
					node.setIconCls("image");
				} else {
					//node.setIconCls(iconCls);
				}

				tree.add(node);
			}
		}
		return tree;
	}
	/**
	 * 添加basedata
	 * 
	 * @return
	 */
	@RequestMapping("/basedataAdd")
	@ResponseBody
	public Json basedataAdd(BaseData basedata, @RequestParam MultipartFile iconFile, HttpServletRequest request) {
		Json j = new Json();
		try {
//			String fileName = "VM".equals(basedata.getBasetypeCode()) ? null : basedata.getId();
//			basedata.setIcon(uploadFile(request, "basedata/" + basedata.getBasetypeCode(), iconFile, fileName));
			basedata.setIcon(uploadFile("basedata", iconFile));
			basedataService.add(basedata);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
	/**
	 * 添加basetype
	 * 
	 * @return
	 */
	@RequestMapping("/basetypeAdd")
	@ResponseBody
	public Json basetypeAdd(BaseType basetype) {
		Json j = new Json();
		try {
			basetypeService.add(basetype);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	

	/**
	 * 跳转到BUG修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/basedataEditPage")
	public String basedataEditPage(HttpServletRequest request, String id) {
		BaseData bd = basedataService.get(id);		
		request.setAttribute("basedata", bd);
		return "/admin/basedataEdit";
	}

	/**
	 * 修改基础数据
	 * 
	 * @param basedata
	 * @return
	 */
	@RequestMapping("/basedataEdit")
	@ResponseBody
	public Json basedataEdit(BaseData basedata, @RequestParam MultipartFile iconFile, HttpServletRequest request) {
		Json j = new Json();
		try {
//			String fileName = "VM".equals(basedata.getBasetypeCode()) ? null : basedata.getId();
//			basedata.setIcon(uploadFile(request, "basedata/" + basedata.getBasetypeCode(), iconFile, fileName));
			basedata.setIcon(uploadFile("basedata", iconFile));
			basedataService.edit(basedata);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 修改基础数据
	 * 
	 * @param basetype
	 * @return
	 */
	@RequestMapping("/basetypeEdit")
	@ResponseBody
	public Json basetypeEdit(BaseType basetype) {
		Json j = new Json();
		try {
			basetypeService.edit(basetype);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}


	/**
	 * 删除基础数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/basedatadelete")
	@ResponseBody
	public Json basedataDelete(String id) {
		Json j = new Json();
		basedataService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 删除基础类型
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/basetypedelete")
	@ResponseBody
	public Json basetypeDelete(String id) {
		Json j = new Json();
		basetypeService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}


	@RequestMapping("/goodsTree")
	@ResponseBody
	public List<Tree> tree(String q, String id) {
		BaseData baseData = new BaseData();
		if(!F.empty(q)){
			baseData.setName(q);
		}
		if(F.empty(id))
			baseData.setPid("GN");
		else
			baseData.setPid(id);
		baseData.setBasetypeCode("GN");
		List<Tree> lt = new ArrayList<Tree>();
		List<BaseData> baseDataList = basedataService.getBaseDatas(baseData);
		if(!CollectionUtils.isEmpty(baseDataList)){
			Map<String, Object> idsMap = basedataService.queryHaveChildsByPid(baseData.getPid());
			for (BaseData data : baseDataList) {
				Tree tree = new Tree();
				tree.setId(data.getId());
				tree.setPid(data.getPid());
				tree.setText(data.getName());
				tree.setIconCls(data.getIcon());
				if(idsMap.containsKey(data.getId()))
					tree.setState("closed");

				lt.add(tree);
			}
		}
		return lt;
	}

	@RequestMapping("/goodsQuery")
	@ResponseBody
	public List<Tree> query(String q) {
		BaseData baseData = new BaseData();

		baseData.setBasetypeCode("GN");
		List<Tree> lt = new ArrayList<Tree>();
		if(!F.empty(q)){
			baseData.setName(q);
		}else{
			return lt;
		}
		List<BaseData> baseDataList = basedataService.getBaseDatas(baseData);
		if(!CollectionUtils.isEmpty(baseDataList)){
			for (BaseData data : baseDataList) {
				Tree tree = new Tree();
				tree.setId(data.getId());
				tree.setPid(data.getPid());
				tree.setText(data.getName());
				tree.setIconCls(data.getIcon());
				lt.add(tree);
			}
		}
		return lt;
	}
}
