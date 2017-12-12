package com.bx.ant.controller;

import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.pageModel.DriverOrderShopView;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.DriverAccountServiceI;
import com.bx.ant.service.DriverOrderShopAllocationServiceI;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.util.ConvertNameUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by  wanxp 2017/9/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/driver/orderShop")
public class ApiDriverOrderShopController extends BaseController {
    public static final String ORDER_COMPLETE = "orderComplete";

    @Autowired
    private DriverOrderShopServiceI driverOrderShopService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DriverAccountServiceI driverAccountService;

    @Autowired
    private DriverOrderShopAllocationServiceI driverOrderShopAllocationService;


    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(DriverOrderShop driverOrderShop, HttpServletRequest request, PageHelper pageHelper) {
        Json json = new Json();

        //获取accountId
        TokenWrap token = getTokenWrap(request);
        Integer accountId  = Integer.parseInt(token.getUid());
        driverOrderShop.setDriverAccountId(accountId);

        if (F.empty(pageHelper.getSort())) {
            pageHelper.setSort("updatetime");
            pageHelper.setOrder("desc");
        }

        json.setMsg("u know");
        json.setObj(driverOrderShopService.dataGridView(driverOrderShop, pageHelper));
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/viewAuditOrder")
    @ResponseBody
    public Json getAuditOrder(HttpServletRequest request) {
        Json json = new Json();
        DataGrid dataGrid = new DataGrid();
        List<DriverOrderShop> ol = new ArrayList<DriverOrderShop>();

        //获取accountId
        TokenWrap token = getTokenWrap(request);
        String accountId = token.getUid();

        DriverOrderShop driverOrderShop = new DriverOrderShop();
        driverOrderShop.setDriverAccountId(Integer.parseInt(accountId));
        driverOrderShop.setStatus(DriverOrderShopServiceI.STATUS_ACCEPTED  + ","
                + DriverOrderShopServiceI.STATUS_DELVIERING
        );
        PageHelper ph = new PageHelper();
        ph.setHiddenTotal(true);
        DataGrid dg = driverOrderShopService.dataGrid(driverOrderShop, ph);
        if (CollectionUtils.isEmpty(dataGrid.getRows())) {
            Calendar today = Calendar.getInstance();
            String todayStr = today.get(Calendar.YEAR) + "-" + today.get(Calendar.MONTH) + "-"
                    + today.get(Calendar.DAY_OF_MONTH);

            Set<String> orderIdSet = redisUtil.getAllSet(Key.build(Namespace.DRIVER_ORDER_SHOP_CACHE,
                    accountId + ":" + todayStr));

            if (CollectionUtils.isNotEmpty(orderIdSet)) {
                for (String orderId : orderIdSet) {
                    try {
                        DriverOrderShopView o = driverOrderShopService.getView(Long.parseLong(orderId));
                        if (o != null && DriverOrderShopServiceI.STATUS_ALLOCATION.equals(o.getStatus())) {
                            ol.add(o);
                        }
                    } catch (ServiceException e){
                        driverOrderShopAllocationService.editClearOrderAllocation(Long.parseLong(orderId));
                        continue;
                    }


                }
                dataGrid.setRows(ol);
            }
        }

        json.setSuccess(true);
        json.setMsg("OK");
        json.setObj(dataGrid);
        return json;
    }

    /**
     * 接受新运单
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editOrderAccept")
    @ResponseBody
    public Json takeOrder(HttpServletRequest request, Long id){
        Json json = new Json();
        TokenWrap token = getTokenWrap(request);
       Integer accountId  = Integer.parseInt(token.getUid());

        DriverOrderShop driverOrderShop = new DriverOrderShop();
        driverOrderShop.setId(id);
        driverOrderShop.setDriverAccountId(accountId);
        Boolean b;
        if (b = driverOrderShopService.editOrderAccept(driverOrderShop)) {
            json.setMsg("u know");
        } else {
            //json.setMsg("订单已失效,请刷新页面");
            json.setMsg("失败，你有订单未配送完成，请配送完再接单！");
        }
        json.setSuccess(b);
        return json;
    }

    /**
     * 拒绝运单
     * @param request
     * @return
     */
    @RequestMapping("/editOrderRefuse")
    @ResponseBody
    public Json editOrderRefuse(HttpServletRequest request, Long id ){
        Json json = new Json();

        TokenWrap token = getTokenWrap(request);
        Integer accountId  = Integer.parseInt(token.getUid());

        DriverOrderShop driverOrderShop = new DriverOrderShop();
        driverOrderShop.setId(id);
        driverOrderShop.setDriverAccountId(accountId);
        driverOrderShopService.refuseOrder(driverOrderShop);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 运单发货
     * @param request
     * @param
     * @return
     */
    @RequestMapping("/editOrderSendOut")
    @ResponseBody
    public Json sendOutOrder(HttpServletRequest request, Long id){
        Json json = new Json();

        TokenWrap token = getTokenWrap(request);
        Integer accountId  = Integer.parseInt(token.getUid());
        DriverOrderShop driverOrderShop = new DriverOrderShop();
        driverOrderShop.setId(id);
        driverOrderShop.setDriverAccountId(accountId);
        driverOrderShop.setStatus(DriverOrderShopServiceI.STATUS_DELVIERING);

        driverOrderShopService.transform(driverOrderShop);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 运单发货完成
     * @param request
     * @return
     */
    @RequestMapping("/editOrderComplete")
    @ResponseBody
    public Json completeOrder(HttpServletRequest request, DriverOrderShop driverOrderShop ){
        Json json = new Json();

        TokenWrap token = getTokenWrap(request);
        Integer accountId  = Integer.parseInt(token.getUid());
        driverOrderShop.setDriverAccountId(accountId);
        driverOrderShop.setStatus(DriverOrderShopServiceI.STATUS_DELIVERED_AUDIT);

        driverOrderShopService.transform(driverOrderShop);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 图片上传
     * @return
     */
    @RequestMapping("/uploadImage")
    @ResponseBody
    public Json uploadImage(@RequestParam(required = false) MultipartFile imageFile){
        Json json = new Json();

        String path = uploadFile(ORDER_COMPLETE, imageFile);

        json.setMsg("u know");
        json.setSuccess(true);
        json.setObj(path);
        return json;
    }

    /**
     * 新订单数量
     * @param request
     * @return
     */
    @RequestMapping("/updateCountNewAllocationOrder")
    @ResponseBody
    public  Json countNewAllocationOrder(HttpServletRequest request){
        Json json = new Json();

        TokenWrap token = getTokenWrap(request);
        Integer accountId  = Integer.parseInt(token.getUid());

        json.setObj(driverOrderShopService.clearAllocationOrderRedis(accountId));
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 今日有效订单
     * @param request
     * @return
     */
    @RequestMapping("/getTodayOrders")
    @ResponseBody
    public Json getTodayOrders(HttpServletRequest request) {
        Json json = new Json();
        //获取accountId
        TokenWrap token = getTokenWrap(request);
        Integer accountId = Integer.parseInt(token.getUid());
        DataGrid dataGrid = driverOrderShopService.listTodayOrderByAccountId(accountId);
        json.setMsg("u know");
        json.setObj(dataGrid);
        json.setSuccess(true);
        return json;
    }

    /**
     * 删除某账户上的redis
     * @param
     * @return
     */
    @RequestMapping("/deleteRedisErrorOrder")
    @ResponseBody
    public Json getTodayOrders(Integer accountId, String driverOrderShopld) {
        Json json = new Json();

        redisUtil.removeSet(driverAccountService.buildAllocationOrderKey(accountId),driverOrderShopld);

        json.setMsg(String.format("accountId:%1s成功删除driverOrderShopld:%2s", accountId, driverOrderShopld) );
        json.setSuccess(true);
        return json;
    }

    /**
     *获取新订单数量(test)
     * @param
     * @return
     */
    @RequestMapping("/getRedisNewOrder")
    @ResponseBody
    public Json getTodayOrders(String accountId) {
        Json json = new Json();
        String key = Key.build(Namespace.DRIVER_ORDER_SHOP_NEW_ASSIGNMENT_COUNT, accountId + "");
        String s = (String) redisUtil.getString(key);

        json.setMsg(String.format("accountId:%1新订单数量:%2s",accountId, s));
        json.setSuccess(true);
        return json;
    }


    @RequestMapping("/getDetail")
    @ResponseBody
    public Json getDetail(Long id) {
        Json j = new Json();
        try {
            j.setMsg("u know");
            j.setObj(driverOrderShopService.getView(id));
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取订单详情接口异常", e);
        }
        return j;
    }

    /**
     * 今日收益详情
     * @param request
     * @return
     */
    @RequestMapping("/getTodayProfitOrders")
    @ResponseBody
    public Json getTodayProfitOrders(HttpServletRequest request) {
        Json json = new Json();
        //获取accountId
        TokenWrap token = getTokenWrap(request);
        Integer accountId = Integer.parseInt(token.getUid());
        // String status = DriverOrderShopServiceI.STATUS_DELIVERED + "," + DriverOrderShopServiceI.STATUS_SETTLEED + ",";
        DataGrid dataGrid = driverOrderShopService.listTodayOrderByAccountId(accountId);
        json.setMsg("u know");
        json.setObj(dataGrid);
        json.setSuccess(true);
        return json;
    }
}
