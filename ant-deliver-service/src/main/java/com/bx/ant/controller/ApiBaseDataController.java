package com.bx.ant.controller;

import com.mobian.pageModel.BaseData;
import com.mobian.pageModel.Json;
import com.mobian.service.BasedataServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础数据
 *
 * @author John
 *
 */
@Controller
@RequestMapping("/api/deliver/basedata")
public class ApiBaseDataController extends BaseController {

	@Autowired
	private BasedataServiceI basedataService;


	/**
	 * 获取基础数据
	 *
	 * @return
	 */
	@RequestMapping("/basedata")
	@ResponseBody
	public Json basedata(String dataType, String pid) {
		Json j = new Json();
		try{
			BaseData baseData = new BaseData();
			baseData.setBasetypeCode(dataType);
			baseData.setPid(pid);
			j.setObj(basedataService.getBaseDatas(baseData));
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 获取基础数据
	 *
	 * @return
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Json get(String key) {
		Json j = new Json();
		try {
			j.setObj(basedataService.get(key));
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}
}