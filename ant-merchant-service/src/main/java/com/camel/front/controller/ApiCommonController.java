package com.camel.front.controller;

import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.listener.Application;
import com.mobian.pageModel.BaseData;
import com.mobian.pageModel.Bug;
import com.mobian.pageModel.Json;
import com.mobian.service.BugServiceI;
import com.mobian.thirdpart.wx.SignUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.util.ConvertNameUtil;
import com.mobian.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公共模块接口
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiCommon")
public class ApiCommonController extends BaseController {
	
	
	@Autowired
	private TokenManage tokenManage;
		

	@Autowired
	private BugServiceI bugService;
	
	/**
	 * 生成html
	 * @return
	 */
	@RequestMapping("/html")
	public void html(String type,String id,HttpServletResponse response) {
		PrintWriter out = null;
		String content = "";
		try{
			response.setContentType("text/html");  
			response.setCharacterEncoding("UTF-8");

			out = response.getWriter();
			out.write("<html><head>");
			out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\">");
			out.write("<style type=\"text/css\">");
			out.write("body {font-family:\"微软雅黑\";font-size:12px; background-color:#f8f7f5;}");
			out.write("ul,ol,li{padding:0; margin:0;}");
			out.write("img{border:0; line-height:0; width: 100%;}");
			out.write("ol,ul {list-style:none;}");
			out.write("a { color: #000; text-decoration: none; outline: none;}");
			out.write("a img { border: none; }");
			out.write("</style></head><body>");
			out.write(content);
			out.write("</body></html>");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}
		}	
	}	
	
	/**
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/error")
	public Json error() {
		Json j = new Json();
		j.setObj("token_expire");
		j.setSuccess(false);
		j.setMsg("token过期，请重新登录！");
		return j;
	}

	/**
	 * 微信JS-SDK权限签名
	 * @param signUrl
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/wxJSSign")
	public Json wxJSSign(String signUrl) {
		Json j = new Json();
		try {
			Map<String, String> signMap = SignUtil.sign(signUrl.split("#")[0]);
			signMap.put("appId", ConvertNameUtil.getString(WeixinUtil.APPID));
			j.success();
			j.setObj(signMap);
			j.setMsg("微信JS-SDK权限签名成功！");
		}catch(Exception e){
			j.setMsg(ConvertNameUtil.getString(EX_0001));
			logger.error("微信JS-SDK权限签名接口异常", e);
		}

		return j;
	}

	@ResponseBody
	@RequestMapping("/validToken")
	public Json validToken() {
		Json j = new Json();
		j.success();
		j.setMsg("token有效！");
		return j;
	}
	
	/**
	 * 分享统一入口
	 * @param
	 * @param
	 * @return
	 * eg: http://www.e-diving.com.cn/api/apiCommon/share?businessId=4fa21103-c7cb-495c-a35d-691c73d32f37&businessType=BT06
	 */
	@RequestMapping("/share")
	public String share(String businessId,String businessType,HttpServletRequest request) {

		return "/diveshare/diveshare";
	}
	
	/**
	 * app错误日志上传
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload_errorlog")
	public Json uploadErrorlog(Bug bug, @RequestParam MultipartFile logFile, HttpServletRequest request) {
		Json j = new Json();
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar calendar = Calendar.getInstance();  
			String dirName = "errorlog/" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
			bug.setFilePath(uploadFile(request, dirName, logFile, format.format(calendar.getTime())));
			bug.setTypeId("0"); // 错误
			bug.setId(UUID.randomUUID().toString());
			bugService.add(bug);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}
	
	/**
	 * 检查更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkUpdate")
	public Json checkUpdate(String versionNo) {
		Json j = new Json();
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("andriod_mark", false);
			result.put("ios_mark", false);
			
			// 检查android版本
			BaseData android_version = Application.get("VM01");
			if(F.empty(versionNo) || (android_version != null && !versionNo.equals(android_version.getName()))) {
				result.put("andriod_mark", true);
				result.put("android_filePath", android_version.getIcon());
				result.put("android_update_log", android_version.getDescription());
			} 
			
			// 检查ios版本
			BaseData ios_version = Application.get("VM02");
			if(F.empty(versionNo) || (ios_version != null && !versionNo.equals(ios_version.getName()))) {
				result.put("ios_mark", true);
				result.put("ios_downloadUrl", ios_version.getDescription());
			} 
			
			j.setObj(result);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}
	
	
	@ResponseBody
	@RequestMapping("/getIQiYiMP4Url")
	public Json getIQiYiMP4Url(String src, HttpServletRequest request) {
		Json j = new Json();
		try{
			j.setObj(HttpUtil.httpRequest(src, "GET", null));
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}

	@ResponseBody
	@RequestMapping("/upload")
	public Json upload(String bizType, @RequestParam(required = false) MultipartFile[] iconFiles, HttpServletRequest request) {
		Json j = new Json();
		try {
			List<String> iconList = new ArrayList<String>();
			for (MultipartFile iconFile : iconFiles) {
				String icon = uploadFile(request, bizType, iconFile);
				iconList.add(icon);
			}
			j.setSuccess(true);
			j.setObj(iconList);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
}
