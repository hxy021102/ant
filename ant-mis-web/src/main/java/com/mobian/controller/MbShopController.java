package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.bx.ant.pageModel.ShopDeliverApply;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.ConfigUtil;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * MbShop管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbShopController")
public class MbShopController extends BaseController {

    @Autowired
    private MbShopServiceI mbShopService;
    @Autowired
    private MbBalanceServiceI mbBalanceService;
    @Autowired
    private MbUserServiceI mbUserService;
    @Autowired
    private MbOrderServiceI mbOrderService;
    @Autowired
    private DiveRegionServiceI diveRegionService;
    @Resource
    private ShopDeliverApplyServiceI shopDeliverApplyService;
    @Autowired
    private UserServiceI userService;


    /**
     * 跳转到MbShop管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request, String auditStatus,Integer id) {
        request.setAttribute("auditStatus", auditStatus);
        if (id != null)
            request.setAttribute("id", id);
        return "/mbshop/mbShop";
    }
    /**
     * 跳转到MbShop分配销售管理页面
     *
     * @return
     */
    @RequestMapping("/distributionSalesManager")
    public String distributionSalesManager(HttpServletRequest request) {
        return "mbshop/mbShopDistributionSales";
    }
    /**
     * 获取MbShop数据表格
     *
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbShop mbShop, PageHelper ph) {
        DataGrid dataGrid = mbShopService.dataGrid(mbShop, ph);
        List<MbShop> mbShopList = dataGrid.getRows();
        List<MbShopExt> mbShopExtList = new ArrayList<>();
        for (MbShop shop : mbShopList) {
            MbShopExt mbShopExt = new MbShopExt();
            BeanUtils.copyProperties(shop, mbShopExt);
            MbBalance mbBalance = mbBalanceService.queryByShopId(shop.getId());
            mbShopExtList.add(mbShopExt);
            if (mbBalance != null) {
                mbShopExt.setBalanceAmount(mbBalance.getAmount());
                mbShopExt.setBalanceId(mbBalance.getId());
            }

            //通过的才有桶账
            if("AS02".equals(mbShopExt.getAuditStatus())) {
                mbBalance = mbBalanceService.addOrGetMbBalanceCash(shop.getId());
                if (mbBalance != null) {
                    mbShopExt.setCashBalanceId(mbBalance.getId());
                    mbShopExt.setCashBalanceAmount(mbBalance.getAmount());
                }
            }
        }
        dataGrid.setRows(mbShopExtList);
        return dataGrid;
    }

    @RequestMapping("/dataGridKeyword")
    @ResponseBody
    public DataGrid dataGridKeyword(MbShop mbShop) {
        if (F.empty(mbShop.getContactPhone()) && F.empty(mbShop.getName()) && F.empty(mbShop.getRegionId())
                && F.empty(mbShop.getParentId()) && F.empty(mbShop.getAddress())) {
            return new DataGrid();
        }
        return mbShopService.dataGridKeyword(mbShop);
    }

    /**
     * 获取MbShop数据表格excel
     *
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(MbShop mbShop, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbShop, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbShop页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        MbShop mbShop = new MbShop();
        return "/mbshop/mbShopAdd";
    }

    /**
     * 添加MbShop
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbShop mbShop) {
        Json j = new Json();
        //mbShopService.setShopLocation(mbShop);
        mbShopService.add(mbShop);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到MbShop查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbShop mbShop = mbShopService.getFromCache(id);
        MbShopExt mbShopExt = new MbShopExt();
        BeanUtils.copyProperties(mbShop, mbShopExt);
        MbBalance mbBalance = mbBalanceService.queryByShopId(mbShop.getId());
        if (mbBalance != null) {
            mbShopExt.setBalanceAmount(mbBalance.getAmount());
            mbShopExt.setBalanceId(mbBalance.getId());
        }
        if ("AS02".equals(mbShopExt.getAuditStatus())) {
            mbBalance = mbBalanceService.addOrGetMbBalanceCash(mbShop.getId());
            if (mbBalance != null) {
                mbShopExt.setCashBalanceId(mbBalance.getId());
                mbShopExt.setCashBalanceAmount(mbBalance.getAmount());
            }
        }
        if(!F.empty(mbShopExt.getSalesLoginId())) {
            User user = userService.getFromCache(mbShopExt.getSalesLoginId());
            mbShopExt.setSalesLoginName(user.getNickname());
        }
        Integer debt = mbOrderService.getOrderDebtMoney(id);
        debt = debt == null ? 0 : debt;
        ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
        shopDeliverApply.setShopId(id);
        shopDeliverApply.setStatus("DAS02");
        List<ShopDeliverApply> list = shopDeliverApplyService.query(shopDeliverApply);
        Integer accountId = null;
        if(!CollectionUtils.isEmpty(list)) {
            mbShopExt.setDeliver(1);
            accountId = list.get(0).getAccountId();
        }else {
            mbShopExt.setDeliver(0);
        }
        MbBalance balance = mbBalanceService.addOrGetMbBalanceDelivery(id);
        request.setAttribute("mbShopExt", mbShopExt);
        request.setAttribute("debt", debt);
        request.setAttribute("accountId",accountId);
        request.setAttribute("money",balance.getAmount());
        return "/mbshop/mbShopView";
    }

    @RequestMapping("/getPropertyGrid")
    @ResponseBody
    public Map<String, Object> getPropertyGrid(Integer shopId) {
        MbShop mbShop = mbShopService.getFromCache(shopId);
        MbBalance mbBalance = mbBalanceService.addOrGetMbBalance(mbShop.getId());
        Integer sumDebtMoney=mbOrderService.getOrderDebtMoney(mbShop.getId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("balance", mbBalance.getAmount());
        map.put("debtMoney",sumDebtMoney);
        return map;
    }

    /**
     * 跳转到MbShop修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbShop mbShop = mbShopService.get(id);
        request.setAttribute("mbShop", mbShop);
        return "/mbshop/mbShopEdit";
    }

    /**
     * 跳转到MbShop审核页面
     *
     * @return
     */
    @RequestMapping("/editAuditPage")
    public String editAuditPage(HttpServletRequest request, Integer id) {
        MbShop mbShop = mbShopService.get(id);
        request.setAttribute("mbShop", mbShop);
        return "/mbshop/mbShopEditAudit";
    }

    /**
     * 审核
     *
     * @param mbShop
     * @return
     */
    @RequestMapping("/editAudit")
    @ResponseBody
    public Json editAudit(MbShop mbShop, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        Json j = new Json();
        mbShop.setAuditLoginId(sessionInfo.getId());
        mbShopService.editAudit(mbShop);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 修改MbShop
     *
     * @param mbShop
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbShop mbShop) {
        Json j = new Json();
        mbShopService.setShopLocation(mbShop);
        mbShopService.edit(mbShop);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 获得门店负责人更改页面
     */
    @RequestMapping("/editShopInChargeUserPage")
    public String editShopInChargeUserView(Integer id, HttpServletRequest request) {
        MbShop mbShop = mbShopService.get(id);
        MbUser mbUser = null;
        if (!F.empty(mbShop.getUserId())) {
            mbUser = mbUserService.get(mbShop.getUserId());
        }
        request.setAttribute("shopId", id);
        request.setAttribute("mbUser", mbUser);
        return "/mbshop/mbShopInChargeUserEdit";
    }

    /**
     * 修改门店负责人
     *
     * @return
     */
    @RequestMapping("/editShopInChargeUser")
    @ResponseBody
    public Json editShopInChargeUser(MbShop mbShop, Integer oldUserId) {
        Json j = new Json();
        //若前端传递过来的oldUserId不为null且mbShop对象的userId为null则说明用户要解绑
        if (!F.empty(oldUserId) && F.empty(mbShop.getUserId())) {
            MbUser mbUser = new MbUser();
            mbUser.setId(oldUserId);
            mbShopService.unboundUser(mbShop, mbUser);
        }
        //若前端传递过来的oldUserId为null且mbShop对象的userId不为null则进行绑定
        if (F.empty(oldUserId) && !F.empty(mbShop.getUserId())) {
            MbUser mbUser = new MbUser();
            mbUser.setId(mbShop.getUserId());
            mbUser.setShopId(mbShop.getId());
            mbShopService.boundUser(mbShop, mbUser);
        }
        j.setSuccess(true);
        j.setMsg("完成");
        return j;
    }

    /**
     * 跳转绑定主门店页面
     */
    @RequestMapping("/editMainShopPage")
    public String editMainShopPage(Integer id, HttpServletRequest request) {
        MbShop mbShop = mbShopService.get(id);
        int balanceAmount = 0, cashBalanceAmount = 0;

        // 尚未绑定主门店时查询余额
        if(F.empty(mbShop.getParentId()) || mbShop.getParentId().equals(id)) {
            MbBalance mbBalance = mbBalanceService.queryByShopId(id);
            if(mbBalance != null) {
                balanceAmount = mbBalance.getAmount();
            }

            mbBalance = mbBalanceService.getCashByShopId(id);
            if(mbBalance != null) {
                cashBalanceAmount = mbBalance.getAmount();
            }
        }

        request.setAttribute("balanceAmount", balanceAmount);
        request.setAttribute("cashBalanceAmount", cashBalanceAmount);
        request.setAttribute("shopId", id);
        return "/mbshop/mbMainShopEdit";
    }

    /**
     * 绑定主门店
     *
     * @return
     */
    @RequestMapping("/editMainShop")
    @ResponseBody
    public Json editMainShop(MbShop mbShop, int balanceAmount, int cashBalanceAmount) {
        Json j = new Json();
        mbShopService.editMainShop(mbShop, balanceAmount, cashBalanceAmount);
        j.setSuccess(true);
        j.setMsg("绑定成功");
        return j;
    }

    /**
     * 删除MbShop
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbShopService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

    @RequestMapping("/selectQuery")
    @ResponseBody
    public List<Tree> query(String q, HttpServletRequest request) {
        String paramsJson = request.getParameter("params");
        MbShop mbShop = new MbShop();
        List<Tree> lt = new ArrayList<Tree>();
        if (!F.empty(q)) {
            if(!F.empty(paramsJson)) {
                mbShop = JSON.parseObject(paramsJson, MbShop.class);
            }
            mbShop.setName(q);
        } else {
            return lt;
        }

        PageHelper ph = new PageHelper();
        ph.setHiddenTotal(true);
        ph.setPage(100);
        mbShop.setAuditStatus("AS02");
        DataGrid mbShopList = mbShopService.dataGrid(mbShop, ph);
        List<MbShop> rows = mbShopList.getRows();
        if (!CollectionUtils.isEmpty(rows)) {
            for (MbShop d : rows) {
                Tree tree = new Tree();
                tree.setId(d.getId() + "");
                tree.setText(d.getName());
                tree.setParentName(d.getRegionPath());
                tree.setPid(d.getParentName());
                lt.add(tree);
            }
        }
        return lt;
    }
    /**
     * 跳转到门店欠款页面
     *
     * @return
     */
    @RequestMapping("/managerShopDebt")
    public String managerShopDebt(HttpServletRequest request, String auditStatus,Integer id) {
        return "/mbshop/mbShopDebtMessage";
    }

    /**
     *  获取门店余额欠款列表
     * @param mbShop
     * @param ph
     * @return
     */
    @RequestMapping("/dataGridShopArrears")
    @ResponseBody
    public DataGrid dataGridShopArrears(MbShop mbShop,PageHelper ph) {
        DataGrid dataGrid = mbShopService.dataGridShopArrears(mbShop, ph);

        List<MbShopExt> rows = dataGrid.getRows();
        MbShopExt footer = new MbShopExt();
        footer.setBalanceAmount(0);
        footer.setDebt(0);
        footer.setTotalDebt(0);
        for (MbShopExt row : rows) {
            footer.setBalanceAmount(footer.getBalanceAmount() + row.getBalanceAmount());
            footer.setDebt(footer.getDebt() + row.getDebt());
            footer.setTotalDebt(footer.getTotalDebt()+row.getTotalDebt());
        }
        dataGrid.setFooter(Arrays.asList(footer));
        return dataGrid;
    }
    /**
     *  获取门店余额欠款列表
     * @param mbShop
     * @param ph
     * @return
     */
    @RequestMapping("/dataGridShopBarrel")
    @ResponseBody
    public DataGrid dataGridShopBarrel(MbShop mbShop,PageHelper ph) {
        DataGrid dataGrid = mbShopService.dataGridShopBarrel(mbShop,ph);
        List<MbShopExt> rows = dataGrid.getRows();
        MbShopExt footer = new MbShopExt();
        footer.setBalanceAmount(0);
        footer.setCashBalanceAmount(0);
        for (MbShopExt row : rows) {
            footer.setBalanceAmount(footer.getBalanceAmount() + row.getBalanceAmount());
            //footer.setDebt(footer.getDebt() + row.getDebt());
            footer.setCashBalanceAmount(footer.getCashBalanceAmount()+row.getCashBalanceAmount());
        }
        dataGrid.setFooter(Arrays.asList(footer));
        return dataGrid;
    }

    @RequestMapping("/getAllShopLocation")
    @ResponseBody
    public Json getAllShopLocation() {
        Json j = new Json();
        List<MbShop> mbShops = mbShopService.getNullLocation();
        for (MbShop m : mbShops) {
           mbShopService.setShopLocation(m);
           mbShopService.edit(m);
        }
        j.setSuccess(true);
        return j;
    }


    @RequestMapping("/getShopMap")
    @ResponseBody
    public Json getShopMap(MbShop mbShop) {
        Json j = new Json();
        List<MbShopMap> mbShopMaps = mbShopService.getShopMapData(mbShop);
        j.setSuccess(true);
        j.setObj(mbShopMaps);
        return j;
    }
    /**
     * 分配门店销售人员
     */
    @RequestMapping("/addShopSalesPage")
    public String addShopSalesPage() {
        MbShop mbShop = new MbShop();
        return "/mbshop/mbShopAddSales";
    }

    @RequestMapping("/addShopSales")
    @ResponseBody
    public Json addShopSales(String salesLoginId, String mbShopList) {
        Json j = new Json();
        JSONArray jsonArray = JSONArray.fromObject(mbShopList);
        List<MbShop> list =(List<MbShop>)jsonArray.toCollection(jsonArray,MbShop.class);
        for(MbShop m : list) {
            m.setSalesLoginId(salesLoginId);
            mbShopService.edit(m);
        }
        j.setSuccess(true);
        j.setMsg("分配完成！");
        return  j;
    }

}
