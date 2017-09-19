package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbProblemTrackItemServiceI;
import com.mobian.service.UserServiceI;
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
 * MbProblemTrackItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbProblemTrackItemController")
public class MbProblemTrackItemController extends BaseController {

    public static final String MB_PROBLEM="mbproblemtrackItem";
	//图标最大上传数据尺寸200kB
	public static final Integer ICON_FILE_MAX_SIZE = 204800;

	//图标格式要求
	public String[] ICON_TYPE = {"png","PNG","jgp","JPG","jpeg","JPEG","gif","GIF"};

	@Autowired
	private MbProblemTrackItemServiceI mbProblemTrackItemService;
	@Autowired
	private UserServiceI userService;

	/**
	 * 跳转到MbProblemTrackItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbproblemtrackitem/mbProblemTrackItem";
	}

	/**
	 * 获取MbProblemTrackItem数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbProblemTrackItem mbProblemTrackItem, PageHelper ph,Integer problemOrderId) {
		mbProblemTrackItem.setProblemOrderId(problemOrderId);
		return mbProblemTrackItemService.dataGridWithSetName(mbProblemTrackItem,ph);
	}
	/**
	 * 获取MbProblemTrackItem数据表格excel
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
	public void download(MbProblemTrackItem mbProblemTrackItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = mbProblemTrackItemService.dataGrid(mbProblemTrackItem,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbProblemTrackItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,Integer problemOrderId,String ownerId) {
		MbProblemTrackItem mbProblemTrackItem = new MbProblemTrackItem();
		request.setAttribute("problemOrderId",problemOrderId);
		request.setAttribute(" ownerId", ownerId);
		return "/mbproblemtrackitem/mbProblemTrackItemAdd";
	}

	/**
	 * 添加MbProblemTrackItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbProblemTrackItem mbProblemTrackItem,@RequestParam MultipartFile fileName) {
		Json j = new Json();
		System.out.println("文件信息为");
		boolean  checkResult = checkIconImage(fileName,j);
		if (checkResult){
		  mbProblemTrackItem.setFile(uploadFile( MB_PROBLEM,fileName));
		}
		mbProblemTrackItemService.addProblemTrackAndUpdateStatus(mbProblemTrackItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbProblemTrackItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbProblemTrackItem mbProblemTrackItem = mbProblemTrackItemService.get(id);
		if (mbProblemTrackItem.getOwnerId() != null) {
			User user = userService.getFromCache(mbProblemTrackItem.getOwnerId());
			if (user != null) {
				mbProblemTrackItem.setOwnerId(user.getName());
			}
		}
		if (mbProblemTrackItem.getLastOwnerId() != null) {
			User user = userService.getFromCache(mbProblemTrackItem.getLastOwnerId());
			if (user != null) {
				mbProblemTrackItem.setLastOwnerId(user.getName());
			}
		}
		request.setAttribute("mbProblemTrackItem", mbProblemTrackItem);
		return "/mbproblemtrackitem/mbProblemTrackItemView";
	}

	/**
	 * 跳转到MbProblemTrackItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbProblemTrackItem mbProblemTrackItem = mbProblemTrackItemService.get(id);
		request.setAttribute("mbProblemTrackItem", mbProblemTrackItem);
		return "/mbproblemtrackitem/mbProblemTrackItemEdit";
	}

	/**
	 * 修改MbProblemTrackItem
	 * 
	 * @param mbProblemTrackItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbProblemTrackItem mbProblemTrackItem) {
		Json j = new Json();		
		mbProblemTrackItemService.edit(mbProblemTrackItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbProblemTrackItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbProblemTrackItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
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
}
