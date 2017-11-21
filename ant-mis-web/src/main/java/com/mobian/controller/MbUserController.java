package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.service.MbUserServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * MbUser管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbUserController")
public class MbUserController extends BaseController {

	@Autowired
	private MbUserServiceI mbUserService;

	@Autowired
	private MbShopServiceI mbShopService;

	@Autowired
	private MbBalanceServiceI mbBalanceService;

	/**
	 * 跳转到MbUser管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbuser/mbUser";
	}

	/**
	 * 获取MbUser数据表格
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbUser mbUser, PageHelper ph) {
		return mbUserService.dataGrid(mbUser, ph);
	}
	/**
	 * 获取MbUser数据表格excel
	 */
	@RequestMapping("/download")
	public void download(MbUser mbUser, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbUser,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加MbUser页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbUser mbUser = new MbUser();
		return "/mbuser/mbUserAdd";
	}

	/**
	 * 添加MbUser
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbUser mbUser) {
		Json j = new Json();		
		mbUserService.add(mbUser);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到MbUser查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbUser mbUser = mbUserService.get(id);
		if (mbUser.getShopId() != null) {
            MbShop mbShop = mbShopService.getFromCache(mbUser.getShopId());
            mbUser.setShopName(mbShop.getName());
        } else {
		    mbUser.setShopName("无");
        }
		request.setAttribute("mbUser", mbUser);
		return "/mbuser/mbUserView";
	}

	@RequestMapping("/viewBalance")
    public String viewBalance(HttpServletRequest request,Integer id,Integer shopId,Integer realShopId,Integer driverAccountId, Integer balanceId) {
		MbBalance mbBalance = null;
		if(!F.empty(id)) {
			MbUser mbUser = mbUserService.get(id);
			mbBalance = mbBalanceService.queryByShopId(mbUser.getShopId());
		}else if(!F.empty(realShopId)){
			mbBalance = mbBalanceService.queryByRealShopId(realShopId);
			request.setAttribute("readOnly", true);
		} else if(!F.empty(driverAccountId)){
			mbBalance =mbBalanceService.addOrGetMbBalance(driverAccountId,50,0);
			request.setAttribute("readOnly", true);
		} else if (!F.empty(balanceId)) {
			mbBalance = mbBalanceService.get(balanceId);
		} else{
			mbBalance = mbBalanceService.queryByShopId(shopId);
		}
	    request.setAttribute("mbBalance", mbBalance);
	    return "/mbuser/mbBalanceView";
    }

	/**
	 * 跳转到MbUser修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbUser mbUser = mbUserService.get(id);
		request.setAttribute("mbUser", mbUser);
		return "/mbuser/mbUserEdit";
	}

	/**
	 * 修改MbUser
	 * 
	 * @param mbUser
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbUser mbUser) {
		Json j = new Json();		
		mbUserService.edit(mbUser);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除MbUser
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbUserService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 根据userName查询mbUser返回List Tree
	 * @param q
	 * @return
	 */
	@RequestMapping("/selectQuery")
	@ResponseBody
	public List<Tree> selectQuery(String q){
		MbUser mbUser = new MbUser();
		List<Tree> lt =new ArrayList<Tree>();
		if(!F.empty(q)){
			mbUser.setUserName(q);
		}else {
			return lt;
		}
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
		ph.setPage(100);
		DataGrid mbUserList = mbUserService.dataGrid(mbUser,ph);
		List<MbUser> rows = mbUserList.getRows();
		if(!CollectionUtils.isEmpty(rows)){
			for(MbUser d : rows){
				Tree tree = new Tree();
				tree.setId(d.getId()+"");
				tree.setText(d.getUserName());
				tree.setParentName(d.getNickName());
				lt.add(tree);
			}
		}
		return lt;
	}
	@RequestMapping("/query")
	@ResponseBody
	public Json query(Integer id){
		Json j = new Json();
		MbUser mbUser = new MbUser();
		MbUser mbUser1 = mbUserService.getFromCache(id);
		mbUser.setUserName(mbUser1.getUserName());
		mbUser.setNickName(mbUser1.getNickName());
		mbUser.setIcon(mbUser1.getIcon());
		mbUser.setSex(mbUser1.getSex());
		mbUser.setPhone(mbUser1.getPhone());
		j.setObj(mbUser);
		j.setSuccess(true);
		j.setMsg("成功查询");
		return j;
	}

}
