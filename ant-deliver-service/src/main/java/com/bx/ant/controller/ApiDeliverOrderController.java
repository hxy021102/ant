package com.bx.ant.controller;

import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.bx.ant.pageModel.DeliverOrder;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbShopServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by  wanxp 2017/9/22.
 *
 * 用户相关接口
 */
@Controller
@RequestMapping("/api/deliver/deliverOrder")
public class ApiDeliverOrderController extends BaseController {

    @Resource
    private MbShopServiceI mbShopService;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    @Autowired
    private DeliverOrderShopServiceI deliverOrderShopService;

    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(DeliverOrder deliverOrder, HttpServletRequest request, PageHelper pageHelper) {
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();
        deliverOrder.setShopId(shopId);
        json.setMsg("u know");
        json.setObj(deliverOrderService.dataGridExt(deliverOrder, pageHelper));
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Json add()
    {
        return null;
    }
//    /**
//     * 获取门店新订单审核列表DataGrid
//     * @param request
//     * @param pageHelper
//     * @return
//     */
//    @RequestMapping("/viewAuditDataGrid")
//    @ResponseBody
//    public Json viewAuditList(HttpServletRequest request, PageHelper pageHelper){
//        Json json = new Json();
//
//        //获取shopId
//        //TODO 测试时设置shop ID值,若真正使用从token中获取
////        TokenWrap token = getTokenWrap(request);
////        Integer shopId = token.getShopId();
//        Integer shopId = 1332;
//        DeliverOrder deliverOrder =  new DeliverOrder();
//        deliverOrder.setShopId(shopId);
//        deliverOrder.setStatus(deliverOrderService.STATUS_PAY_SUCCESS);
//        json.setMsg("u know");
//        json.setObj(deliverOrderService.dataGridExt(deliverOrder, pageHelper));
//        json.setSuccess(true);
//        return json;
//    }

    /**
     *  查看已接单
      * @param request
     * @param pageHelper
     * @return
     */
    @RequestMapping("/viewAcceptedDataGrid")
    @ResponseBody
    public Json viewAcceptedDataGrid(HttpServletRequest request, PageHelper pageHelper){
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();
        DeliverOrder deliverOrder =  new DeliverOrder();
        deliverOrder.setShopId(shopId);
        deliverOrder.setStatus(deliverOrderService.STATUS_SHOP_ACCEPT);
        json.setMsg("u know");
        json.setObj(deliverOrderService.dataGridExt(deliverOrder, pageHelper));
        json.setSuccess(true);
        return json;
    }

    /**
     *  查看
     * @param request
     * @param pageHelper
     * @return
     */
    @RequestMapping("/viewRefusedDataGrid")
    @ResponseBody
    public Json viewRefusedDataGrid(HttpServletRequest request, PageHelper pageHelper){
        Json json = new Json();
        DataGrid dataGrid = new DataGrid();
        List<DeliverOrder> ol = new ArrayList<DeliverOrder>();
        //获取shopId
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();
        //获取拒绝的deliverOrderShop
        DeliverOrderShop orderShop = new DeliverOrderShop();
        orderShop.setStatus(DeliverOrderShopServiceI.STATUS_REFUSED);
        orderShop.setShopId(shopId);
        List<DeliverOrderShop> orderShops = deliverOrderShopService.query(orderShop);
        Collections.sort(orderShops, new Comparator<DeliverOrderShop>() {
            @Override
            public int compare(DeliverOrderShop d1, DeliverOrderShop d2) {
                return  (int)(d1.getUpdatetime().getTime() - d2.getUpdatetime().getTime());
            }
        });
        if (CollectionUtils.isNotEmpty(orderShops)) {
            //通过deliverOrderShop获取deliverOrder
            for (DeliverOrderShop deliverOrderShop : orderShops) {
                if (!F.empty(deliverOrderShop.getDeliverOrderId())) {
                    ol.add(deliverOrderService.getDeliverOrderExt(deliverOrderShop.getDeliverOrderId()));
                }
            }
            dataGrid.setRows(ol);
            json.setMsg("u know");
            json.setObj(dataGrid);
        }
        json.setSuccess(true);
        return json;
    }

    /**
     * 查看已发货订单
     * @param request
     * @param pageHelper
     * @return
     */
    @RequestMapping("/viewDeliveringDataGrid")
    @ResponseBody
    public Json viewDeliveringDataGrid(HttpServletRequest request, PageHelper pageHelper){
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();
        DeliverOrder deliverOrder =  new DeliverOrder();
        deliverOrder.setShopId(shopId);
        deliverOrder.setStatus(deliverOrderService.STATUS_DELIVERING);
        json.setMsg("u know");
        json.setObj(deliverOrderService.dataGridExt(deliverOrder, pageHelper));
        json.setSuccess(true);
        return json;
    }

    /**
     * 查看完成配送订单
     * @param request
     * @param pageHelper
     * @return
     */
    @RequestMapping("/viewDeliveredDataGrid")
    @ResponseBody
    public Json viewDeliveredDataGrid(HttpServletRequest request, PageHelper pageHelper){
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();
        DeliverOrder deliverOrder =  new DeliverOrder();
        deliverOrder.setShopId(shopId);
        deliverOrder.setStatus(deliverOrderService.STATUS_DELIVERY_COMPLETE);
        json.setMsg("u know");
        json.setObj(deliverOrderService.dataGridExt(deliverOrder, pageHelper));
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/viewCompletedDataGrid")
    @ResponseBody
    public Json viewCompletedDataGrid(HttpServletRequest request, PageHelper pageHelper){
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();
        DeliverOrder deliverOrder =  new DeliverOrder();
        deliverOrder.setShopId(shopId);
        deliverOrder.setStatus(deliverOrderService.STATUS_CLOSED);
        json.setMsg("u know");
        json.setObj(deliverOrderService.dataGridExt(deliverOrder, pageHelper));
        json.setSuccess(true);
        return json;
    }

    /**
     * 门店接受新运单
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editOrderAccept")
    @ResponseBody
    public Json takeOrder(HttpServletRequest request, Long id){
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();

        deliverOrderService.transformByShopIdAndStatus(id,shopId,deliverOrderService.STATUS_SHOP_ACCEPT);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }
    /**
     * 门店拒绝新运单
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editOrderRefuse")
    @ResponseBody
    public Json refuseOrder(HttpServletRequest request, Long id, String remark){
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();

        //配置deliverOrder
        DeliverOrder deliverOrder =  new DeliverOrder();
        deliverOrder.setId(id);
        deliverOrder.setShopId(shopId);
        deliverOrder.setRemark(remark);
        deliverOrder.setStatus(deliverOrderService.STATUS_SHOP_REFUSE);

        //状态翻转
        deliverOrderService.transform(deliverOrder);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 门店运单发货
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editOrderSendOut")
    @ResponseBody
    public Json sendOutOrder(HttpServletRequest request, Long id){
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();

        deliverOrderService.transformByShopIdAndStatus(id,shopId,deliverOrderService.STATUS_DELIVERING);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 门店运单发货完成
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editOrderComplete")
    @ResponseBody
    public Json completeOrder(HttpServletRequest request, Long id){
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();

        deliverOrderService.transformByShopIdAndStatus(id,shopId,deliverOrderService.STATUS_DELIVERY_COMPLETE);

        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    /**
     * 门店接受新运单
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editOrderTakeByUser")
    @ResponseBody
    public Json takeOrderUserByUser(HttpServletRequest request, Long id){
        Json json = new Json();

        //获取shopId
        //TODO 测试时设置shop ID值,若真正使用从token中获取
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();

        deliverOrderService.transformByShopIdAndStatus(id,shopId,deliverOrderService.STATUS_CLOSED);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/editOrderTransformStatus")
    public Json transform(DeliverOrder deliverOrder) {
        Json json = new Json();
        deliverOrderService.transform(deliverOrder);
        json.setMsg("u know");
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/viewOrderList")
    @ResponseBody
    public Json viewOrderList(Integer shopId,String status){
        Json json = new Json();
        json.setMsg("u know");
        json.setObj(deliverOrderService.listOrderByShopIdAndOrderStatus(shopId,status));
        json.setSuccess(true);
        return json;
    }

    @RequestMapping("/countNewAllocationOrder")
    @ResponseBody
    public  Json countNewAllocationOrder(HttpServletRequest request){
        Json json = new Json();

        //获取shopId
        TokenWrap token = getTokenWrap(request);
        Integer shopId = token.getShopId();

        json.setSuccess(true);
        json.setMsg("u know");
        json.setObj(deliverOrderService.clearAllocationOrderRedis(shopId));
        return json;
    }
}
