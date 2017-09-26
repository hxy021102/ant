package com.camel.front.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mobian.absx.F;
import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.Task;
import com.mobian.controller.BaseController;
import com.mobian.exception.ServiceException;
import com.mobian.interceptors.TokenManage;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.service.impl.CompletionFactory;
import com.mobian.service.impl.RedisUserServiceImpl;
import com.mobian.util.ConvertNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by guxin on 2017/5/7.
 *
 * 订单
 */
@Controller
@RequestMapping("/api/apiOrderController")
public class ApiOrderController extends BaseController {

    @Autowired
    private MbOrderServiceI mbOrderService;
    @Autowired
    private MbOrderItemServiceI mbOrderItemService;
    @Autowired
    private MbOrderInvoiceServiceI mbOrderInvoiceService;
    @Autowired
    private MbItemServiceI mbItemService;
    @Autowired
    private TokenManage tokenManage;
    @Autowired
    private RedisUserServiceImpl redisUserService;
    @Autowired
    private BasedataServiceI basedataService;

    /**
     * 获取订单列表接口
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public Json dataGrid(MbOrder mbOrder, PageHelper ph, HttpServletRequest request) {
        Json j = new Json();

        try{
            String uid = tokenManage.getUid(request);
            mbOrder.setUserId(Integer.valueOf(uid));
            if(ph.getRows() == 0 || ph.getRows() > 50) {
                ph.setRows(10);
            }
            DataGrid dg = mbOrderService.dataGrid(mbOrder, ph);
            List<MbOrder> ol = dg.getRows();
            if(ol != null && ol.size() > 0) {
                final CompletionService completionService = CompletionFactory.initCompletion();
                for (MbOrder o : ol) {
                    //获取订单商品信息
                    completionService.submit(new Task<MbOrder, List<MbOrderItem>>(o) {
                        @Override
                        public List<MbOrderItem> call() throws Exception {
                            return mbOrderItemService.getMbOrderItemList(getD().getId());
                        }

                        protected void set(MbOrder d, List<MbOrderItem> mbOrderItemList) {
                            d.setMbOrderItemList(mbOrderItemList);
                        }
                    });
                }
                completionService.sync();
            }
            j.setSuccess(true);
            j.setMsg("获取订单列表成功！");
            j.setObj(dg);
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取订单列表接口异常", e);
        }

        return j;
    }

    /**
     * 获取订单详情接口
     */
    @RequestMapping("/get")
    @ResponseBody
    public Json get(MbOrder mbOrder) {
        Json j = new Json();
        try{
            if(F.empty(mbOrder.getId())) {
                j.setMsg("订单id不能为空！");
                return j;
            }
            MbOrder o = mbOrderService.get(mbOrder.getId());
            if(o != null) {
                final CompletionService completionService = CompletionFactory.initCompletion();
                //获取订单商品信息
                completionService.submit(new Task<MbOrder, List<MbOrderItem>>(o) {
                    @Override
                    public List<MbOrderItem> call() throws Exception {
                        return mbOrderItemService.getMbOrderItemList(getD().getId());
                    }

                    protected void set(MbOrder d, List<MbOrderItem> mbOrderItemList) {
                        d.setMbOrderItemList(mbOrderItemList);
                    }
                });
                //获取订单发票信息
                completionService.submit(new Task<MbOrder, MbOrderInvoice>(o) {
                    @Override
                    public MbOrderInvoice call() throws Exception {
                        return mbOrderInvoiceService.getByOrderId(getD().getId());
                    }

                    protected void set(MbOrder d, MbOrderInvoice mbOrderInvoice) {
                        d.setMbOrderInvoice(mbOrderInvoice);
                    }
                });
                completionService.sync();
            }
            j.setSuccess(true);
            j.setMsg("获取订单详情成功！");
            j.setObj(o);
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("获取订单详情接口异常", e);
        }

        return j;
    }

    /**
     * 取消订单接口
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(MbOrder mbOrder) {
        Json j = new Json();
        try{
            if(F.empty(mbOrder.getId())) {
                j.setMsg("订单id不能为空！");
                return j;
            }
            mbOrderService.delete(mbOrder.getId());
            j.setSuccess(true);
            j.setMsg("取消订单成功！");
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("取消订单接口异常", e);
        }

        return j;
    }

    /**
     * 确认收货接口
     */
    @RequestMapping("/receipt")
    @ResponseBody
    public Json receipt(MbOrder mbOrder) {
        Json j = new Json();
        try{
            mbOrder.setStatus("OD30");
            mbOrderService.transform(mbOrder);
            j.setSuccess(true);
            j.setMsg("确认收货成功！");
        }catch(Exception e){
            j.setMsg(ConvertNameUtil.getString(EX_0001));
            logger.error("确认收货接口异常", e);
        }

        return j;
    }

    /**
     * 下单接口
     *
     * 还需要修改的问题：
     * 1、是否默认各种状态，如支付状态等
     * 2、支付时间是否有过期，过期需设置支付失败
     * 3、支付成功后回调，状态更新等
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(HttpServletRequest request) {
        Json j = new Json();
        try{
            String orderParam = request.getParameter("orderParam");
            if(F.empty(orderParam)) {
                j.setMsg("下单参数为不能为空！");
                return j;
            }
            MbOrder mbOrder = JSON.parseObject(orderParam, new TypeReference<MbOrder>(){});
            if(mbOrder == null) {
                j.setMsg("下单参数为不能为空！");
                return j;
            }
            //验证订单参数
            Integer totalPrice = mbOrder.getTotalPrice();
            if(totalPrice == null || totalPrice < 0) {
                j.setMsg("总金额必需不小于0！");
                return j;
            }
            //验证订单商品信息
            List<MbOrderItem> mbOrderItemList = mbOrder.getMbOrderItemList();
            if(mbOrderItemList == null || mbOrderItemList.size() == 0) {
                j.setMsg("商品信息不能为空！");
                return j;
            }
            // Integer shopId = tokenManage.getShopId(request);
            int realPrice = 0;
            for (MbOrderItem mbOrderItem : mbOrderItemList) {
                //验证商品的id，商品数量，商品购买价格
                Integer itemId = mbOrderItem.getItemId();
                if(F.empty(itemId) || F.empty(mbOrderItem.getQuantity()) || mbOrderItem.getQuantity() < 1
                        || F.empty(mbOrderItem.getBuyPrice())) {
                    j.setMsg("商品基本信息错误！" + "商品id：" + itemId);
                    return j;
                }
                //验证商品是否存在
                MbItem mbItem = mbItemService.get(itemId);
                if(mbItem == null || mbItem.getIsdeleted()) {
                    j.setMsg("商品不存在！" + "商品id：" + itemId);
                    return j;
                }
                //验证商品库存是否足够
                if(mbItem.getQuantity() < mbOrderItem.getQuantity()) {
                    j.setMsg("商品库存不足！" + "商品id：" + itemId);
                    return j;
                }
                //验证购买价格
                int price = mbItem.getMarketPrice();
                Integer contractPrice = redisUserService.getContractPrice(mbOrder.getShopId(), itemId);
                if(contractPrice != null) {
                    price = contractPrice;
                }
                if(mbOrderItem.getBuyPrice() != price) {
                    j.setMsg("商品的购买价格取值错误！" + "商品id：" + itemId);
                    return j;
                }
                realPrice = realPrice + price*mbOrderItem.getQuantity();
            }
            // 运费
            if("DW02".equals(mbOrder.getDeliveryWay())) {
                BaseData bd = basedataService.get("DW02");
                if(!F.empty(bd.getDescription())) {
                    realPrice += Double.parseDouble(bd.getDescription())*100;
                }
            }
            if(totalPrice != realPrice) {
                j.setMsg("订单金额计算错误！");
                return j;
            }
            String uid = tokenManage.getUid(request);
            mbOrder.setUserId(Integer.parseInt(uid));

            mbOrderService.transform(mbOrder);
            j.setSuccess(true);
            j.setObj(mbOrder.getId());
            j.setMsg("下单成功！");
        }catch(Exception e){
            if(e instanceof ServiceException){
                j.setMsg(((ServiceException) e).getMsg());
            }else {
                j.setMsg(ConvertNameUtil.getString(EX_0001));
            }
            logger.error("下单接口异常", e);
        }

        return j;
    }

}
