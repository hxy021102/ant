package com.bx.ant.controller;

import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.session.TokenServiceI;
import com.mobian.absx.Objectx;
import com.mobian.pageModel.SessionInfo;
import com.mobian.thirdpart.oss.OSSUtil;
import com.mobian.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 基础控制器
 * 
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/baseController")
public class BaseController extends Objectx {

	protected static final String EX_0001 = "EX0001";
	protected static final String SUCCESS_MESSAGE = "操作成功";
	private String _publishSettingVal = "2"; //生产环境

	@Autowired
	private TokenServiceI tokenService;
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

		/**
		 * 防止XSS攻击
		 */
		//binder.registerCustomEditor(String.class, new StringEscapeEditor(true, true));
	}

	/**
	 * 用户跳转JSP页面
	 * 
	 * 此方法不考虑权限控制
	 * 
	 * @param folder
	 *            路径
	 * @param jspName
	 *            JSP名称(不加后缀)
	 * @return 指定JSP页面
	 */
	@RequestMapping("/{folder}/{jspName}")
	public String redirectJsp(@PathVariable String folder, @PathVariable String jspName) {
		return "/" + folder + "/" + jspName;
	}

	protected TokenWrap getTokenWrap(HttpServletRequest request){
		String tokenId = request.getParameter(TokenServiceI.TOKEN_FIELD);
		TokenWrap tokenWrap = tokenService.getToken(tokenId);
		// TODO 上线去除
//		if(tokenWrap == null) {
//			tokenWrap = new TokenWrap();
//			tokenWrap.setShopId(1332);
//		}

		return tokenWrap;
	}

	public String uploadFile(String fileType,MultipartFile file){
		if(file==null||file.isEmpty())
			return null;
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileName = getYmdPath(fileType,suffix);
		try {
			return OSSUtil.putInputStream(OSSUtil.bucketName, file.getInputStream(), fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected String getYmdPath(String fileType,String fileName){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String path = fileType+"/"+calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+ Util.CreateNoncestr(32)+fileName;
		return path;
	}
}
 