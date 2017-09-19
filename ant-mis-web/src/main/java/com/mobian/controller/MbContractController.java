package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.model.TmbContractItem;
import com.mobian.pageModel.*;
import com.mobian.service.MbContractItemServiceI;
import com.mobian.service.MbContractServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MbContract管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/mbContractController")
public class MbContractController extends BaseController {

	@Autowired
	private MbContractServiceI mbContractService;

	@Autowired
	private MbContractItemServiceI mbContractItemService;

	public static final String MB_CONTRACT = "mbContract";

	/**
	 * 跳转到MbContract管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/mbcontract/mbContract";
	}

	/**
	 * 获取MbContract数据表格
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MbContract mbContract, PageHelper ph) {
		return mbContractService.dataGrid(mbContract, ph);
	}
	/**
	 * 获取MbContract数据表格excel
	 */
	@RequestMapping("/download")
	public void download(MbContract mbContract, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(mbContract,ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}

	@RequestMapping("/downloadAttachment")
	public void downloadAttachment(HttpServletResponse response, Integer id) {
		MbContract contract = mbContractService.get(id);
		String attachmentUrl = contract.getAttachment();
		URL url;
		try {
			url = new URL(attachmentUrl);
			URLConnection connection = url.openConnection();
			InputStream in = connection.getInputStream();

			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			in.close();

			response.reset();
			String suffix = attachmentUrl.substring(attachmentUrl.lastIndexOf('.'));
			String filename = contract.getName() + suffix;
			response.addHeader("Content-Disposition", "attachment;filename=" + filename);
			response.setContentType("application/octet-stream");

			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(buffer);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 跳转到添加MbContract页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		MbContract mbContract = new MbContract();
		return "/mbcontract/mbContractAdd";
	}

	/**
	 * 添加MbContract
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(MbContract mbContract, @RequestParam MultipartFile file) {
		Json j = new Json();

		if (mbContractService.isContractExists(mbContract)) {
			j.setSuccess(false);
			j.setMsg("该合同代码已存在!");
		} else {
			String attachmentUrl = uploadFile(MB_CONTRACT, file);
			mbContract.setAttachment(attachmentUrl);
			mbContractService.add(mbContract);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		}

		return j;
	}

	/**
	 * 跳转到MbContract查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		MbContract mbContract = mbContractService.get(id);
		request.setAttribute("mbContract", mbContract);
		return "/mbcontract/mbContractView";
	}

	/**
	 * 跳转到MbContract修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		MbContract mbContract = mbContractService.get(id);
		request.setAttribute("mbContract", mbContract);
		return "/mbcontract/mbContractEdit";
	}

	/**
	 * 修改MbContract
	 * 
	 * @param mbContract
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(MbContract mbContract, @RequestParam MultipartFile file) {
		Json j = new Json();

		if (mbContractService.isContractExists(mbContract)
				&& doesContractCodeChanged(mbContract)) {
			j.setSuccess(false);
			j.setMsg("该合同代码已存在!");
		} else {
			String attachmentUrl = uploadFile(MB_CONTRACT, file);
			mbContract.setAttachment(attachmentUrl);
			mbContractService.edit(mbContract);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		}
		return j;
	}

	private boolean doesContractCodeChanged(MbContract mbContract) {
		MbContract dbItem = mbContractService.get(mbContract.getId());
		if (dbItem.getCode().equals(mbContract.getCode())) {
			return false;
		}
		return true;
	}
	@RequestMapping("/getMbContractItemMap")
	@ResponseBody
	private Json getMbContractItemMap(Integer shopId){
		Json json = new Json();
		Map<String,TmbContractItem> map = new HashMap<String,TmbContractItem>();

		MbContract mbContract = mbContractService.getNewMbContract(shopId);
		if (mbContract != null) {
			List<TmbContractItem> tmbContractItemList = mbContractItemService.queryMbContractItemByShopId(mbContract.getId());
			if (CollectionUtils.isNotEmpty(tmbContractItemList)) {
				for (TmbContractItem tmbContractItem : tmbContractItemList) {
					map.put(tmbContractItem.getItemId()+"", tmbContractItem);
				}
			}
		}
		json.setObj(map);
		json.success();
		return json;
	}

	/**
	 * 删除MbContract
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		mbContractService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
