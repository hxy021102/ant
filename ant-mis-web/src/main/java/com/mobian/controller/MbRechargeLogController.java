package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.*;
import com.mobian.service.BasedataServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbRechargeLogServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.ConfigUtil;
import com.mobian.util.ImportExcelUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MbRechargeLog管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbRechargeLogController")
public class MbRechargeLogController extends BaseController {

    @Autowired
    private MbRechargeLogServiceI mbRechargeLogService;
    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbShopServiceI mbShopService;
    @Autowired
    private BasedataServiceI basedataService;


    /**
     * 跳转到MbRechargeLog管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbrechargelog/mbRechargeLog";
    }

    /**
     * 获取MbRechargeLog数据表格
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbRechargeLog mbRechargeLog, PageHelper ph) {
        if (mbRechargeLog.getShopId() != null) {
            MbBalance mbBalance = mbBalanceService.queryByShopId(mbRechargeLog.getShopId());
            if (mbBalance != null) {
                mbRechargeLog.setBalanceId(mbBalance.getId());
            } else {
                return new DataGrid();
            }
        }
        DataGrid dataGrid = mbRechargeLogService.dataGrid(mbRechargeLog, ph);
        List<MbRechargeLog> mbRechargeLogList = dataGrid.getRows();
        for (MbRechargeLog rechargeLog : mbRechargeLogList) {
            MbBalance mbBalance = mbBalanceService.get(rechargeLog.getBalanceId());
            if (mbBalance != null) {
                rechargeLog.setShopId(mbBalance.getRefId());
                MbShop mbShop = mbShopService.getFromCache(rechargeLog.getShopId());
                if (mbShop != null) {
                    rechargeLog.setShopName(mbShop.getName());
                }
            }
        }
        return dataGrid;
    }

    /**
     * 获取MbRechargeLog数据表格excel
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
    public void download(MbRechargeLog mbRechargeLog, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbRechargeLog, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbRechargeLog页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        MbRechargeLog mbRechargeLog = new MbRechargeLog();
        return "/mbrechargelog/mbRechargeLogAdd";
    }

    @RequestMapping("/addShopMoneyPage")
    public String rechargeAddPage(HttpServletRequest request, Integer shopId) {
        MbRechargeLog mbRechargeLog = new MbRechargeLog();
        request.setAttribute(" shopId", shopId);
        return "mbuser/addShopMoney";
    }

    /**
     * 跳转到现金充值页面
     *
     * @param request
     * @param shopId
     * @return
     */
    @RequestMapping("/addShopCashChargePage")
    public String addShopCashChargePage(HttpServletRequest request, Integer shopId) {
        MbRechargeLog mbRechargeLog = new MbRechargeLog();
        request.setAttribute(" shopId", shopId);
        return "mbuser/addShopCashCharge";
    }

    /**
     * 添加MbRechargeLog
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(HttpSession session, MbRechargeLog mbRechargeLog) {
        Json j = new Json();
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        MbBalance mbBalance = mbBalanceService.addOrGetMbBalance(mbRechargeLog.getShopId());
        mbRechargeLog.setBalanceId(mbBalance.getId());
        mbRechargeLog.setApplyLoginId(sessionInfo.getId());
        mbRechargeLogService.addAndUpdateBalance(mbRechargeLog);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 现金充值
     *
     * @param session
     * @param mbRechargeLog
     * @return
     */
    @RequestMapping("/addCashCharge")
    @ResponseBody
    public Json addCashCharge(HttpSession session, MbRechargeLog mbRechargeLog) {
        Json j = new Json();
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        MbBalance mbBalance = mbBalanceService.addOrGetMbBalance(mbRechargeLog.getShopId());
        mbRechargeLog.setBalanceId(mbBalance.getId());
        mbRechargeLog.setApplyLoginId(sessionInfo.getId());
        mbRechargeLogService.addAndUpdateBalance(mbRechargeLog);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到MbRechargeLog查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbRechargeLog mbRechargeLog = mbRechargeLogService.get(id);
        request.setAttribute("mbRechargeLog", mbRechargeLog);
        return "/mbrechargelog/mbRechargeLogView";
    }

    /**
     * 跳转到MbRechargeLog修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbRechargeLog mbRechargeLog = mbRechargeLogService.get(id);
        request.setAttribute("mbRechargeLog", mbRechargeLog);
        return "/mbrechargelog/mbRechargeLogEdit";
    }

    /**
     * 修改MbRechargeLog
     *
     * @param mbRechargeLog
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbRechargeLog mbRechargeLog) {
        Json j = new Json();
        mbRechargeLogService.edit(mbRechargeLog);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbRechargeLog
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbRechargeLogService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

    /**
     * 跳转到汇款审核页面
     *
     * @return
     */
    @RequestMapping("/editAuditPage")
    public String editAuditPage(HttpServletRequest request, Integer id) {
        MbRechargeLog mbRechargeLog = mbRechargeLogService.get(id);
        request.setAttribute("id", id);
        request.setAttribute("mbRechargeLog", mbRechargeLog);
        return "/mbrechargelog/mbRechargeLogAudit";
    }

    /**
     * 审核
     *
     * @param mbRechargeLog
     * @return
     */
    @RequestMapping("/editAudit")
    @ResponseBody
    public Json editAudit(MbRechargeLog mbRechargeLog, HttpSession session) {
        Json j = new Json();
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        if (F.empty(mbRechargeLog.getHandleRemark())&&F.empty(mbRechargeLog.getShopId())) {
            j.setSuccess(false);
            j.setMsg("请选择门店和填写备注！");

        } else if (F.empty(mbRechargeLog.getPayCode()) && "HS02".equals(mbRechargeLog.getHandleStatus())) {
            j.setSuccess(false);
            j.setMsg("请输入银行汇款单号");
        } else {
            MbRechargeLog rechargeLog = new MbRechargeLog();
            rechargeLog.setPayCode(mbRechargeLog.getPayCode());
            rechargeLog.setHandleStatus("HS02");
            if(mbRechargeLogService.checkRechargeLogPayCode(rechargeLog)==null) {
                mbRechargeLog.setHandleLoginId(sessionInfo.getId());
                mbRechargeLog.setHandleTime(new Date());
                try {
                    mbRechargeLogService.editAudit(mbRechargeLog);
                    j.setSuccess(true);
                    j.setMsg("编辑成功!");
                } catch (ServiceException e) {
                    j.setSuccess(false);
                    j.setMsg(e.getMsg());
                }
            }else{
                j.setSuccess(false);
                j.setMsg("银行汇款单号已存在,请重新确认");
            }
        }
        return j;
    }

    /**
     * 跳转到订单金额冲正页面
     *
     * @param request
     * @param shopId
     * @return
     */
    @RequestMapping("/addOrderRechargePage")
    public String addOrderRechargePage(HttpServletRequest request, Integer shopId, Integer orderId) {
        MbRechargeLog mbRechargeLog = new MbRechargeLog();
        request.setAttribute(" shopId", shopId);
        request.setAttribute(" orderId", orderId);
        return "mborder/mbOrderRechargeAdd";
    }

    @RequestMapping("/addOrderRecharge")
    @ResponseBody
    public Json addOrderRecharge(HttpSession session, MbRechargeLog mbRechargeLog) {
        Json j = new Json();
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        MbBalance mbBalance = mbBalanceService.addOrGetMbBalance(mbRechargeLog.getShopId());
        mbRechargeLog.setBalanceId(mbBalance.getId());
        mbRechargeLog.setApplyLoginId(sessionInfo.getId());
        mbRechargeLogService.add(mbRechargeLog);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到添加MbRechargeLog导入页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/uploadPage")
    public String uploadPage(HttpServletRequest request) {
        return "/mbrechargelog/mbrechargeogUpload";
    }

    /**
     * 批量导入银行收款信息
     *
     * @param file
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Json upload(@RequestParam MultipartFile file, HttpSession session) throws Exception {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        Json json = new Json();
        try {
            if (file.isEmpty()) {
                throw new ServiceException("请上传文件！！");
            }
            InputStream in = file.getInputStream();
            List<List<Object>> listOb = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
            in.close();
            List<MbRechargeLog> mbRechargeLogList = new ArrayList<MbRechargeLog>();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < listOb.size(); i++) {
                List<Object> lo = listOb.get(i);
                MbRechargeLog mbRechargeLog = new MbRechargeLog();
                BaseData baseData = new BaseData();
                List<BaseData> baseDataList = basedataService.getBaseDatas(baseData);
                if (CollectionUtils.isNotEmpty(baseDataList)) {
                    for (BaseData data : baseDataList) {
                        JSONObject jsonObject = JSONObject.fromObject(data.getDescription());
                        String bankCard = (String) jsonObject.get("bank_card");
                        if (bankCard.trim().equals(lo.get(6).toString().trim())) {
                            mbRechargeLog.setBankCode(data.getId());
                            mbRechargeLog.setRefType((String) jsonObject.get("ref_type"));
                            //默认设置余额id值为：-1
                            mbRechargeLog.setBalanceId(-1);
                        }
                    }
                    if (F.empty(mbRechargeLog.getBankCode())) {
                        throw new ServiceException(String.format("%s银行卡号不存在", lo.get(6).toString()));
                    }
                } else {
                    throw new ServiceException("获取基础数据失败！");
                }
                String dateStr = lo.get(0).toString();
                Date date = sdf.parse(dateStr);
                mbRechargeLog.setRemitterTime(date);
                mbRechargeLog.setAmount(Integer.parseInt(lo.get(1).toString())*100);
                mbRechargeLog.setContent(lo.get(2).toString());
                mbRechargeLog.setPayerBankCode(lo.get(3).toString());
                mbRechargeLog.setRemitter(lo.get(4).toString());
                if (F.empty(lo.get(5).toString())) {
                    mbRechargeLog.setPayCode(mbRechargeLog.getRemitter() + mbRechargeLog.getPayerBankCode()
                            + mbRechargeLog.getRemitterTime() + mbRechargeLog.getAmount());
                } else {
                    mbRechargeLog.setPayCode(lo.get(7).toString());
                }
                mbRechargeLogList.add(mbRechargeLog);
            }
            mbRechargeLogService.addBatchMbRechargeLog(mbRechargeLogList, sessionInfo.getId());
            json.setSuccess(true);
        } catch (ServiceException e) {
            json.setMsg(e.getMsg());
        }
        return json;
    }
}
