package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbShopCouponsServiceI;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.ArrayList;
import java.util.List;

/**
 * MbItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbItemController")
public class MbItemController extends BaseController {

	public static final String MB_ITEM = "mbItem";

	//图标最大上传数据尺寸200kB
	public static final Integer ICON_FILE_MAX_SIZE = 204800;

	//图标格式要求
	public String[] ICON_TYPE = {"png","PNG","jgp","JPG","jpeg","JPEG","gif","GIF"};

	@Autowired
	private MbItemServiceI mbItemService;

	@Autowired
	private MbShopCouponsServiceI mbShopCouponsService;

	/**
	 * 跳转到MbItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbitem/mbItem";
	}

	/**
	 * 获取MbItem数据表格
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbItem mbItem, PageHelper ph) {
		return mbItemService.dataGrid(mbItem, ph);
	}
	/**
	 * 获取MbItem数据表格excel
	 */
	@RequestMapping("/download")
	public void download(MbItem mbItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbItem,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbItem mbItem = new MbItem();
		return "/mbitem/mbItemAdd";
	}

	/**
	 * 添加MbItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbItem mbItem,@RequestParam MultipartFile equipIconFile) {
		Json j = new Json();

		if (mbItemService.isItemExists(mbItem)) {
			j.setSuccess(false);
			j.setMsg("该商品代码已存在！");
		} else {
			boolean checkResult = checkIconImage(equipIconFile,j);
			if (checkResult){
				mbItem.setUrl(uploadFile(MB_ITEM, equipIconFile));
			    mbItemService.add(mbItem);
			}
		}
		return j;
	}

	/**
	 * 跳转到MbItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbItem mbItem = mbItemService.get(id);
		request.setAttribute("mbItem", mbItem);
		return "/mbitem/mbItemView";
	}

	/**
	 * 跳转到MbItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbItem mbItem = mbItemService.get(id);
		request.setAttribute("mbItem", mbItem);
		return "/mbitem/mbItemEdit";
	}

	/**
	 * 修改MbItem
	 * 
	 * @param mbItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbItem mbItem,@RequestParam MultipartFile equipIconFile) {
		Json j = new Json();
		if (mbItemService.isItemExists(mbItem)
				&& doesItemCodeChanged(mbItem)) {
			j.setSuccess(false);
			j.setMsg("该商品代码已存在！");
		}
		else{
			boolean checkResult = checkIconImage(equipIconFile,j);
			if (checkResult){
				mbItem.setUrl(uploadFile(MB_ITEM, equipIconFile));
			    mbItemService.edit(mbItem);
			}
		}
		return j;
	}

	private boolean doesItemCodeChanged(MbItem mbItem) {
		MbItem dbItem = mbItemService.get(mbItem.getId());
		if (dbItem.getCode().equals(mbItem.getCode())) {
			return false;
		}
		return true;
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
		else if(equipIconFile.getSize()>=ICON_FILE_MAX_SIZE){
			j.setSuccess(false);
			j.setMsg("基本信息栏-上传图片大小不能超过" + (ICON_FILE_MAX_SIZE/1024) +"kB");
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
	 * 删除MbItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/editPropertiesPage")
	public String editPropertiesPage(HttpServletRequest request, Integer id) {
		request.setAttribute("id", id);
		return "/fmproperties/fmIndex";
	}


	@RequestMapping("/selectQuery")
	@ResponseBody
	public List<ItemTree> query(String q,String shopId) {
		MbItem mbItem = new MbItem();
		List<ItemTree> lt = new ArrayList<ItemTree>();
		if (!F.empty(q)) {
			mbItem.setKeyword(q);
		} else {
			return lt;
		}
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		ph.setPage(100);
		DataGrid diveRegionList = mbItemService.dataGrid(mbItem, ph);
		List<MbItem> rows = diveRegionList.getRows();
		if (!CollectionUtils.isEmpty(rows)) {
			for (MbItem d : rows) {
				ItemTree tree = new ItemTree();
				tree.setId(d.getId() + "");
				tree.setPid(d.getCategoryId() + "");
				tree.setText(d.getName());
				tree.setParentName(d.getCategoryName());
				tree.setCode(d.getCode());
				tree.setMarketPrice(d.getMarketPrice());
				lt.add(tree);
			}
		}
		return lt;
	}

	/**
	 * 获取需要补充商品的DeliverOrderShop商品列表
	 * @param mbItemQuery
	 * @param ph
	 * @return
	 */
	@RequestMapping("/dataGridWidthDeliverOrderShop")
	@ResponseBody
	public DataGrid dataGridWidthDeliverOrderShop(MbItemQuery mbItemQuery, PageHelper ph) {
		if (mbItemQuery.getDeliverOrderShopIds() != null && !F.empty(mbItemQuery.getShopId()))
			return mbItemService.dataGridWidthDeliverOrderShop(mbItemQuery, ph);
		return new DataGrid();
	}
}
