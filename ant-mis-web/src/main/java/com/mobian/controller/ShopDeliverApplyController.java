package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DistributeRangeMap;
import com.bx.ant.pageModel.ShopDeliverApplyQuery;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.pageModel.*;
import com.bx.ant.pageModel.ShopDeliverApply;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.ConfigUtil;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * ShopDeliverApply管理控制器
 *
 * @author John
 *
 */
@Controller
@RequestMapping("/shopDeliverApplyController")
public class ShopDeliverApplyController extends BaseController {

	@Resource
	private ShopDeliverApplyServiceI shopDeliverApplyService;
   @Resource
   private MbShopServiceI mbShopService;

	/**
	 * 跳转到ShopDeliverApply管理页面
	 *
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/shopdeliverapply/shopDeliverApply";
	}

	/**
	 * 获取ShopDeliverApply数据表格
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(ShopDeliverApply shopDeliverApply, PageHelper ph) {
		return shopDeliverApplyService.dataGridWithName(shopDeliverApply, ph);
	}
	/**
	 * 获取ShopDeliverApply数据表格excel
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
	public void download(ShopDeliverApply shopDeliverApply, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(shopDeliverApply,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加ShopDeliverApply页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,Integer shopId) {
		ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
		request.setAttribute("shopId",shopId);
		return "/shopdeliverapply/shopDeliverApplyAdd";
	}

	/**
	 * 添加ShopDeliverApply
	 *
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ShopDeliverApply shopDeliverApply) {
		Json j = new Json();
		ShopDeliverApply deliverApply = new ShopDeliverApply();
		deliverApply.setStatus("DAS02");
		deliverApply.setAccountId(shopDeliverApply.getAccountId());
		List<ShopDeliverApply> shopDeliverApplies =	shopDeliverApplyService.query(deliverApply);
		if(shopDeliverApplies != null && shopDeliverApplies.size() > 0) {
			j.setSuccess(false);
			j.setMsg("该账号已经开通过，请更换账号！");
		}else {
			shopDeliverApply.setResult("后台开通");
			shopDeliverApply.setStatus("DAS02");
			shopDeliverApplyService.add(shopDeliverApply);
			j.setSuccess(true);
			j.setMsg("开通成功！");
		}
		return j;
	}

	/**
	 * 跳转到ShopDeliverApply查看页面
	 *
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		ShopDeliverApplyQuery shopDeliverApplyQuery = shopDeliverApplyService.getViewMessage(id);
		request.setAttribute("shopDeliverApplyQuery", shopDeliverApplyQuery);
		return "/shopdeliverapply/shopDeliverApplyView";
	}

	/**
	 * 跳转到ShopDeliverApply修改页面
	 *
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		ShopDeliverApply shopDeliverApply = shopDeliverApplyService.get(id);
		request.setAttribute("shopDeliverApply", shopDeliverApply);
		return "/shopdeliverapply/shopDeliverApplyEditDeliverDistance";
	}

	/**
	 * 修改ShopDeliverApply
	 *
	 * @param shopDeliverApply
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ShopDeliverApply shopDeliverApply) {
		Json j = new Json();
		shopDeliverApplyService.edit(shopDeliverApply);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 删除ShopDeliverApply
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		shopDeliverApplyService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

    /**
     * 跳转到ShopDeliverApply审核页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/examinePage")
    public String examinePage(HttpServletRequest request, Integer id) {
        ShopDeliverApply shopDeliverApply = shopDeliverApplyService.get(id);
        request.setAttribute("shopDeliverApply", shopDeliverApply);
        return "/shopdeliverapply/shopDeliverApplyExamine";
    }

    /**
     * 编辑审核状态
     * @param shopDeliverApply
     * @param session
     * @return
     */
    @RequestMapping("/editState")
    @ResponseBody
    public Json editState(ShopDeliverApply shopDeliverApply, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        Json j = new Json();
        shopDeliverApplyService.edit(shopDeliverApply);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

	/**
	 * 修改接入方门店配送范围
	 * @param id
	 * @param distributeRange
	 * @return
	 */
	@RequestMapping(value = "/updateDistributeRange", method = RequestMethod.POST)
	@ResponseBody
	public Json updateDistributeRange(Integer id, @RequestBody String distributeRange) {
		Json j = new Json();
		ShopDeliverApply shopDeliverApply = shopDeliverApplyService.get(id);
		shopDeliverApply.setDistributeRange(distributeRange);
		shopDeliverApplyService.edit(shopDeliverApply);
		MbShopMap mbShopMap = mbShopService.getShopApplyMapData(1207,id);
		 JSONArray json = JSONArray.fromObject(distributeRange);
		//把json字符串转换成对象
		List<DistributeRangeMap> distributeRangeMaps = (List<DistributeRangeMap>) JSONArray.toCollection(json, DistributeRangeMap.class);
		DistributeRangeMap distributeRangeMap = new DistributeRangeMap();
		distributeRangeMap.setLng(mbShopMap.getLongitude().doubleValue());
		distributeRangeMap.setLat(mbShopMap.getLatitude().doubleValue());
		Boolean result = shopDeliverApplyService.chechPointInPolygon(distributeRangeMap, distributeRangeMaps);
		j.setSuccess(true);
		j.setMsg("修改接入方门店配范围成功！"+result);
		return j;
	}
}
