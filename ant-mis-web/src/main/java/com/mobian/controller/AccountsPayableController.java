package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.DriverOrderShopBill;
import com.bx.ant.pageModel.ShopOrderBill;
import com.bx.ant.service.DriverOrderShopBillServiceI;
import com.bx.ant.service.ShopOrderBillServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.Colum;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * Supplier管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/accountsPayableController")
public class AccountsPayableController extends BaseController {

	@Resource
	private ShopOrderBillServiceI shopOrderBillService;
	@Resource
	private DriverOrderShopBillServiceI driverOrderShopBillService;
	/**
	 * 跳转到应付汇款管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "accountspay/accountsPayable";
	}

	@RequestMapping("/queryUnShopBillOrUnDriverBill")
	@ResponseBody
	public DataGrid queryUnShopBillOrUnDriverBill(String payer,Long id, PageHelper ph) {
		if ("shop".equals(payer)) {
			ShopOrderBill shopOrderBill = new ShopOrderBill();
			if (!F.empty(id)) {
				shopOrderBill.setId(id);
			}
			//shopOrderBill.setStatus("BAS01");
			DataGrid dataGrid = shopOrderBillService.dataGridWithName(shopOrderBill, ph);
			List<ShopOrderBill> shopOrderBills = dataGrid.getRows();
			if (CollectionUtils.isNotEmpty(shopOrderBills)) {
				ShopOrderBill foot = new ShopOrderBill();
				foot.setAmount(0);
				for (ShopOrderBill orderBill : shopOrderBills) {
					foot.setAmount(foot.getAmount() + orderBill.getAmount());
				}
				dataGrid.setFooter(Arrays.asList(foot));
			}
			return dataGrid;
		} else if ("rider".equals(payer)) {
			DriverOrderShopBill driverOrderShopBill = new DriverOrderShopBill();
			//driverOrderShopBill.setHandleStatus("DHS01");
			if (!F.empty(id)) {
				driverOrderShopBill.setId(id);
			}
			DataGrid dataGrid = driverOrderShopBillService.dataGridView(driverOrderShopBill, ph);
			List<DriverOrderShopBill> driverOrderShopBills = dataGrid.getRows();
			if (CollectionUtils.isNotEmpty(driverOrderShopBills)) {
				DriverOrderShopBill foot = new DriverOrderShopBill();
				foot.setAmount(0);
				for (DriverOrderShopBill orderShopBill : driverOrderShopBills) {
					foot.setAmount(foot.getAmount() + orderShopBill.getAmount());
				}
				dataGrid.setFooter(Arrays.asList(foot));
			}
			return dataGrid;
		} else
			return new DataGrid();
	}

	/**
	 * 导出应付汇总报表
	 * @param payer
	 * @param id
	 * @param ph
	 * @param downloadFields
	 * @param response
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	@RequestMapping("/download")
	public void download(String payer, Long id, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
		DataGrid dg = queryUnShopBillOrUnDriverBill(payer, id, ph);
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
}
