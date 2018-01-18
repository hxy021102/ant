package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.*;
import com.bx.ant.service.*;
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
import java.util.ArrayList;
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
    private MbShopServiceI mbShopService;
    @Resource
    private DriverAccountServiceI driverAccountService;
    @Resource
    private DriverOrderShopServiceI driverOrderShopService;
    @Resource
    private DeliverOrderShopServiceI deliverOrderShopService;
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
    public DataGrid queryUnShopBillOrUnDriverBill(String payer, String name, PageHelper ph)  {
        if ("shop".equals(payer)) {
            DeliverOrderShop shopOrderBillQuery = new DeliverOrderShop();

            if (!F.empty(name)) {
                List<MbShop> mbShopList = mbShopService.getMbshopListByName(name);
                if (CollectionUtils.isNotEmpty(mbShopList)) {

                    shopOrderBillQuery.setShopId(mbShopList.get(0).getId());
                }
            }
            DataGrid dataGrid = deliverOrderShopService.queryUnPayForCount(shopOrderBillQuery);
            List<DeliverOrderShopQuery> shopOrderBills = dataGrid.getRows();
            if (CollectionUtils.isNotEmpty(shopOrderBills)) {
                ShopOrderBill foot = new ShopOrderBill();
                foot.setAmount(0);
                for (DeliverOrderShopQuery orderBill : shopOrderBills) {
                    orderBill.setId(new Long(orderBill.getShopId()));
                    foot.setAmount(foot.getAmount() + orderBill.getAmount());
                }
                dataGrid.setFooter(Arrays.asList(foot));
            }
            return dataGrid;
        } else if ("driver".equals(payer)) {
            DriverOrderShop driverOrderShop = new DriverOrderShop();
            if (!F.empty(name)) {
                List<DriverAccount> driverAccountList = driverAccountService.getDriverAccountListByName(name);
                if (CollectionUtils.isNotEmpty(driverAccountList)) {
                    driverOrderShop.setDriverAccountId(driverAccountList.get(0).getId());
                }
            }
            DataGrid dataGrid = driverOrderShopService.queryUnPayForCount(driverOrderShop);
            List<DriverOrderShop> driverOrderShops = dataGrid.getRows();
            List<DriverOrderShopView> driverOrderShopViewList = new ArrayList<DriverOrderShopView>();
            dataGrid.setRows(driverOrderShopViewList);
            if (CollectionUtils.isNotEmpty(driverOrderShops)) {
                DriverOrderShop foot = new DriverOrderShop();
                foot.setAmount(0);
                for (DriverOrderShop dOrder : driverOrderShops) {
                    DriverOrderShopView view = new DriverOrderShopView();
                    view.setId(new Long(dOrder.getDriverAccountId()));
                    foot.setAmount(foot.getAmount() + dOrder.getAmount());
                    DriverAccount driverAccount = driverAccountService.getFromCache(dOrder.getDriverAccountId());
                    if (driverAccount != null) {
                        view.setUserName(driverAccount.getUserName());
                    }
                    view.setAmount(dOrder.getAmount());
                    driverOrderShopViewList.add(view);
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
            DataGrid dg = queryUnShopBillOrUnDriverBill(payer, name, ph);
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
            } else if ("driver".equals(payer)) {
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
