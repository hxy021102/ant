package com.camel.front.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.mobian.controller.BaseController;
import com.mobian.pageModel.BaseData;
import com.mobian.pageModel.DiveRegion;
import com.mobian.pageModel.FmProperties;
import com.mobian.pageModel.Json;
import com.mobian.service.BasedataServiceI;
import com.mobian.service.DiveRegionServiceI;
import com.mobian.service.FmOptionsServiceI;
import com.mobian.service.FmPropertiesServiceI;
import com.mobian.thirdpart.oss.OSSUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 基础数据
 *
 * @author John
 *
 */
@Controller
@RequestMapping("/api/apiBaseDataController")
public class ApiBaseDataController extends BaseController {

	@Autowired
	private BasedataServiceI basedataService;

	@Autowired
	private DiveRegionServiceI diveRegionService;

	@Autowired
	private FmPropertiesServiceI fmPropertiesService;

	@Autowired
	private FmOptionsServiceI fmOptionsService;

	/**
	 * 获取基础数据
	 *
	 * @return
	 */
	@RequestMapping("/basedata")
	@ResponseBody
	public Json basedata(String dataType,String pid) {
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
	@RequestMapping("/goodsType")
	@ResponseBody
	public Json baseDataWithGoodsType() {
		Json j = new Json();
		try{
			BaseData baseData = new BaseData();
			baseData.setBasetypeCode("GN");
			List<BaseData> baseDataList = basedataService.getBaseDatas(baseData);
			if(!CollectionUtils.isEmpty(baseDataList)){
				Iterator<BaseData> iterator = baseDataList.iterator();
				while (iterator.hasNext()){
					BaseData bd = iterator.next();
					if(StringUtils.isNotEmpty(bd.getPid())){
						for (BaseData data : baseDataList) {
							if(data.getId().equals(bd.getPid())){
								List<BaseData> children = data.getChildren();
								if(children == null){
									children = new ArrayList<BaseData>();
									data.setChildren(children);
								}
								children.add(bd);
								iterator.remove();
								break;
							}
						}
					}
				}
			}
			j.setObj(baseDataList);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 获取行政区域列表
	 *
	 * @return
	 */
	@RequestMapping("/region")
	@ResponseBody
	public Json region(DiveRegion diveRegion) {
		Json j = new Json();
		try{
			j.setObj(diveRegionService.findAllByParams(diveRegion));
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 获取规则配置
	 *
	 * @return
	 */
	@RequestMapping("/extFields")
	@ResponseBody
	public Json getExtFields(String goodsId) {
		Json j = new Json();
		try{
			List<FmProperties> fmPropertiesList = fmPropertiesService.getByGoodsId(goodsId);
			j.setObj(fmPropertiesList);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping("/uploadSign")
	@ResponseBody
	public Json getUploadSign(HttpServletRequest request) {
		Json j = new Json();
		String endpoint = OSSUtil.endpoint;
		String endpointUrl = OSSUtil.endpointUrl;
		String accessId = OSSUtil.accessKeyId;
		String accessKey = OSSUtil.accessKeySecret;
		String bucket = OSSUtil.bucketName;
		String dir = getYmdPath("mbItem");
		String host = request.getScheme()+"://" + bucket +  endpointUrl;
		OSSClient client = new OSSClient(endpoint, accessId, accessKey);
		try {
			long expireTime = 600;
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			Date expiration = new Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

			String postPolicy = client.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = client.calculatePostSignature(postPolicy);

			Map<String, String> respMap = new LinkedHashMap<String, String>();
			respMap.put("accessid", accessId);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			//respMap.put("expire", formatISO8601Date(expiration));
			respMap.put("dir", dir);
			respMap.put("host", host);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));
			j.setObj(respMap);
			j.success();
		} catch (Exception e) {
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	protected String getYmdPath(String fileType){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String path = fileType+"/"+calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
		return path;
	}
}