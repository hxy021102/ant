package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.MbShopCouponsLogServiceI;
import com.mobian.service.MbShopCouponsServiceI;
import com.mobian.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * MbShopCoupons管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbShopCouponsController")
public class MbShopCouponsController extends BaseController {

	@Autowired
	private MbShopCouponsServiceI mbShopCouponsService;
	@Autowired
	private MbShopCouponsLogServiceI mbShopCouponsLogService;


	/**
	 * 跳转到MbShopCoupons管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbshopcoupons/mbShopCoupons";
	}

	/**
	 * 获取MbShopCoupons数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbShopCoupons mbShopCoupons, PageHelper ph) {
		return mbShopCouponsService.dataGrid(mbShopCoupons, ph);
	}
	/**
	 * 获取MbShopCoupons数据表格excel
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
	public void download(MbShopCoupons mbShopCoupons, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbShopCoupons,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbShopCoupons页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request, Integer shopId) {
	    request.setAttribute("shopId",shopId);
		return "/mbshopcoupons/mbShopCouponsAdd";
	}

	/**
	 * 添加MbShopCoupons
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbShopCoupons mbShopCoupons,HttpSession session) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		try {
			mbShopCoupons.setLoginId(sessionInfo.getId());
			mbShopCoupons.setQuantityUsed(0);
			mbShopCouponsService.addShopCouponsAndEditBalance(mbShopCoupons);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		}catch (ServiceException se) {
			j.setSuccess(false);
			j.setMsg(se.getMsg());
		}finally {
			return j;
		}

	}

	/**
	 * 跳转到MbShopCoupons查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbShopCouponsView mbShopCoupons = mbShopCouponsService.getShopCouponsView(id);
		request.setAttribute("mbShopCoupons", mbShopCoupons);
		return "/mbshopcoupons/mbShopCouponsView";
	}

	/**
	 * 跳转到MbShopCoupons修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbShopCoupons mbShopCoupons = mbShopCouponsService.get(id);
		request.setAttribute("mbShopCoupons", mbShopCoupons);
		return "/mbshopcoupons/mbShopCouponsEdit";
	}

	/**
	 * 修改MbShopCoupons
	 * 
	 * @param mbShopCoupons
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbShopCoupons mbShopCoupons,HttpSession session) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		MbShopCouponsLog mbShopCouponsLog = new MbShopCouponsLog();
		mbShopCouponsLog.setShopCouponsId(mbShopCoupons.getId());
		mbShopCouponsLog.setShopCouponsStatus(mbShopCoupons.getStatus());
		mbShopCouponsLog.setLoginId(sessionInfo.getId());
		mbShopCouponsLog.setRemark(mbShopCoupons.getRemark());
		mbShopCouponsLogService.addLogAndUpdateShopCoupons(mbShopCouponsLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbShopCoupons
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbShopCouponsService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 删除MbShopCoupons
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/shopCouponsDeletePage")
	public String shopCouponsDeletePage(HttpServletRequest request, Integer id) {
		request.setAttribute("id", id);
		return "/mbshopcoupons/shopCouponsDeletePage";
	}
	/**
	 * 删除MbShopCoupons
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/shopCouponsDelete")
	@ResponseBody
	public Json shopCouponsDelete(MbShopCoupons mbShopCoupons, HttpSession session) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		mbShopCoupons.setLoginId(sessionInfo.getId());
		mbShopCouponsService.deleteAndRefund(mbShopCoupons);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}
	/**
	 * 查询门店拥有的对应的券票
	 */
	@RequestMapping("/shopCouponsQuantityQuery")
	@ResponseBody
	public Json shopCouponsQuery(Integer shopId) {
		Json j = new Json();
		Map<String, MbShopCoupons> shopCouponsMap = mbShopCouponsService.getShopCouponsMapByShopId(shopId);
		j.setSuccess(true);
		j.setMsg("ok");
		j.setObj(shopCouponsMap);
		return j;
	}

}
