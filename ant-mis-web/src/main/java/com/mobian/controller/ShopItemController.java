package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bx.ant.pageModel.ShopItemQuery;
import com.mobian.pageModel.*;
import com.bx.ant.pageModel.ShopItem;
import com.bx.ant.service.ShopItemServiceI;

import com.mobian.service.MbItemServiceI;
import com.mobian.util.ConfigUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * ShopItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/shopItemController")
public class ShopItemController extends BaseController {

	@Resource
	private ShopItemServiceI shopItemService;
    @Resource
	private MbItemServiceI mbItemService;

	/**
	 * 跳转到ShopItem管理页面
	 *
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/shopitem/shopItem";
	}

	/**
	 * 获取ShopItem数据表格
	 *
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ShopItem shopItem, PageHelper ph) {
		return shopItemService.dataGridWithItemName(shopItem, ph);
	}
	/**
	 * 获取ShopItem数据表格excel
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
	public void download(ShopItem shopItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(shopItem,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ShopItem页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		ShopItem shopItem = new ShopItem();
		return "/shopitem/shopItemAdd";
	}

	/**
	 * 添加ShopItem
	 *
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ShopItem shopItem) {
		Json j = new Json();
		shopItemService.add(shopItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");
		return j;
	}

	/**
	 * 跳转到ShopItem查看页面
	 *
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		ShopItem shopItem = shopItemService.get(id);
		request.setAttribute("shopItem", shopItem);
		return "/delivershopitem/mbItemEdit";
	}

	/**
	 * 跳转到ShopItem修改页面
	 *
	 * @return
	 */
	@RequestMapping("/editPricePage")
	public String editPage(HttpServletRequest request, Integer id) {
		ShopItem shopItem = shopItemService.get(id);
		ShopItemQuery shopItemQuery= new ShopItemQuery();
		BeanUtils.copyProperties(shopItem,shopItemQuery);
		MbItem mbItem= mbItemService.getFromCache(shopItem.getItemId());
		shopItemQuery.setStatusName(shopItem.getStatus());
		shopItemQuery.setName(mbItem.getName());
		request.setAttribute("shopItem", shopItemQuery);
		return "delivershopitem/shopItemEdit";
	}

	/**
	 * 修改ShopItem
	 *
	 * @param shopItem
	 * @return
	 */
	@RequestMapping("/editPrice")
	@ResponseBody
	public Json edit(ShopItem shopItem) {
		Json j = new Json();
		shopItemService.edit(shopItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 删除ShopItem
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		shopItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 跳转到门店商品审核页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/examinePage")
	public String examinePage(HttpServletRequest request, Long id) {
		request.setAttribute("id", id);
		return "delivershopitem/shopItemEditAudit";
	}

	/**
	 * 编辑审核状态
	 * @param shopItem
	 * @param session
	 * @return
	 */
	@RequestMapping("/editAuditState")
	@ResponseBody
	public Json editState(ShopItem shopItem, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json j = new Json();
		shopItem.setReviewerId(sessionInfo.getId());
		shopItemService.edit(shopItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}
}
