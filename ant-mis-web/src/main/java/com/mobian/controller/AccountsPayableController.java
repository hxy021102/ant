package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.DriverAccountServiceI;
import com.bx.ant.service.DriverOrderShopBillServiceI;
import com.bx.ant.service.ShopOrderBillServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.Colum;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbShopServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Supplier管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/accountsPayableController")
public class AccountsPayableController extends BaseController {

    @Resource
    private ShopOrderBillServiceI shopOrderBillService;
    @Resource
    private DriverOrderShopBillServiceI driverOrderShopBillService;
    @Resource
    private MbShopServiceI mbShopService;
    @Resource
    private DriverAccountServiceI driverAccountService;

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
    public DataGrid queryUnShopBillOrUnDriverBill(String payer, Long id, String name, PageHelper ph) throws UnsupportedEncodingException {
        if ("shop".equals(payer)) {
            ShopOrderBillQuery shopOrderBillQuery = new ShopOrderBillQuery();
            if (!F.empty(id)) {
                shopOrderBillQuery.setId(id);
            }
            if (!F.empty(name)) {
                name = new String(name.getBytes("iso-8859-1"), "utf-8");
                List<MbShop> mbShopList = mbShopService.getMbshopListByName(name);
                if (CollectionUtils.isNotEmpty(mbShopList)) {
                    Integer[] shopIds = new Integer[mbShopList.size()];
                    int i = 0;
                    for (MbShop mbShop : mbShopList) {
                        shopIds[i++] = mbShop.getId();
                    }
                    shopOrderBillQuery.setShopIds(shopIds);
                }
            }
            shopOrderBillQuery.setStatus("BAS01");
            DataGrid dataGrid = shopOrderBillService.dataGridWithName(shopOrderBillQuery, ph);
            List<ShopOrderBillQuery> shopOrderBills = dataGrid.getRows();
            if (CollectionUtils.isNotEmpty(shopOrderBills)) {
                ShopOrderBill foot = new ShopOrderBill();
                foot.setAmount(0);
                for (ShopOrderBillQuery orderBill : shopOrderBills) {
                    foot.setAmount(foot.getAmount() + orderBill.getAmount());
                }
                dataGrid.setFooter(Arrays.asList(foot));
            }
            return dataGrid;
        } else if ("rider".equals(payer)) {
            DriverOrderShopBillView driverOrderShopBillView = new DriverOrderShopBillView();
            driverOrderShopBillView.setHandleStatus("DHS01");
            if (!F.empty(id)) {
                driverOrderShopBillView.setId(id);
            }
            if (!F.empty(name)) {
                List<DriverAccount> driverAccountList = driverAccountService.getDriverAccountListByName(name);
                if (CollectionUtils.isNotEmpty(driverAccountList)) {
                    Integer[] accountIds = new Integer[driverAccountList.size()];
                    int j = 0;
                    for (DriverAccount driverAccount : driverAccountList) {
                        accountIds[j++] = driverAccount.getId();
                    }
                    driverOrderShopBillView.setAccountIds(accountIds);
                }
            }
            DataGrid dataGrid = driverOrderShopBillService.dataGridView(driverOrderShopBillView, ph);
            List<DriverOrderShopBillView> driverOrderShopBills = dataGrid.getRows();
            if (CollectionUtils.isNotEmpty(driverOrderShopBills)) {
                DriverOrderShopBill foot = new DriverOrderShopBill();
                foot.setAmount(0);
                for (DriverOrderShopBillView orderShopBill : driverOrderShopBills) {
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
     *
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
    public void download(String payer, Long id, String name, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        payer = payer.substring(0, payer.length() - 1);
        if (!F.empty(payer)) {
            DataGrid dg = queryUnShopBillOrUnDriverBill(payer, id, name, ph);
            if ("shop".equals(payer)) {
                List<ShopOrderBillQuery> shopOrderBillQueries = dg.getRows();
                if (CollectionUtils.isNotEmpty(shopOrderBillQueries)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (ShopOrderBillQuery shopOrderBillQuery : shopOrderBillQueries) {
                        String createTimeName = formatter.format(shopOrderBillQuery.getAddtime());
                        shopOrderBillQuery.setCreateTimeName(createTimeName);
                        if (!F.empty(shopOrderBillQuery.getAmount())) {
                            shopOrderBillQuery.setAmountElement(shopOrderBillQuery.getAmount() / 100.0);
                        }
                    }

                }
            } else if ("rider".equals(payer)) {
                List<DriverOrderShopBillView> driverOrderShopBillViews = dg.getRows();
                if (CollectionUtils.isNotEmpty(driverOrderShopBillViews)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (DriverOrderShopBillView orderShopBillView : driverOrderShopBillViews) {
                        String createTimeName = formatter.format(orderShopBillView.getAddtime());
                        orderShopBillView.setCreateTimeName(createTimeName);
                        if (!F.empty(orderShopBillView.getAmount())) {
                            orderShopBillView.setAmountElement(orderShopBillView.getAmount() / 100.0);
                        }
                    }
                }
            }
            downloadFields = downloadFields.replace("&quot;", "\"");
            downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
            List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
            if (CollectionUtils.isNotEmpty(colums)) {
                for (Colum colum : colums) {
                    switch (colum.getField()) {
                        case "addtime":
                            colum.setField("createTimeName");
                            break;
                        case "amount":
                            colum.setField("amountElement");
                            break;
                    }
                }
            }
            downloadTable(colums, dg, response);
        }
    }
}
