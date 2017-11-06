package com.bx.ant.controller;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by  wanxp 2017/9/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/deliverOrder")
public class ApiDriverOrderShopController extends BaseController {
    public static final String ORDER_COMPLETE = "orderComplete";


    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DriverOrderShopServiceI driverOrderShopService;

    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(DriverOrderShop driverOrderShop, HttpServletRequest request, PageHelper pageHelper) {
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        String accountId = token.getUid();
        driverOrderShop.setDriverAccountId(Integer.parseInt(accountId));

        if (F.empty(pageHelper.getSort())) {
            pageHelper.setSort("updatetime");
            pageHelper.setOrder("desc");
        }

        json.setMsg("u know");
        json.setObj(driverOrderShopService.dataGridView(driverOrderShop, pageHelper));
        json.setSuccess(true);
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
        driverOrderShop.setStatus(DriverOrderShopServiceI.STATUS_ACCEPTED);

        driverOrderShopService.transform(driverOrderShop);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }


    /**
     * 运单发货
     * @param request
     * @param id
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
    public Json completeOrder(HttpServletRequest request, Long id ){
        Json json = new Json();

        TokenWrap token = getTokenWrap(request);
        Integer accountId  = Integer.parseInt(token.getUid());
        DriverOrderShop driverOrderShop = new DriverOrderShop();
        driverOrderShop.setId(id);
        driverOrderShop.setDriverAccountId(accountId);
        driverOrderShop.setStatus(DriverOrderShopServiceI.STATUS_DELIVERED);

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
//
//    /**
//     * 新订单数量
//     * @param request
//     * @return
//     */
//    @RequestMapping("/countNewAllocationOrder")
//    @ResponseBody
//    public  Json countNewAllocationOrder(HttpServletRequest request){
//        Json json = new Json();
//
//        TokenWrap token = getTokenWrap(request);
//        Integer accountId  = Integer.parseInt(token.getUid());
//
//        json.setObj(deliverOrderService.clearAllocationOrderRedis(accountId));
//        json.setMsg("u know");
//        json.setSuccess(true);
//        return json;
//    }
}
