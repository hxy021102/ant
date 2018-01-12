package com.bx.ant.controller;

import com.alibaba.fastjson.JSONObject;
import com.bx.ant.pageModel.DeliverOrderExt;
import com.bx.ant.pageModel.DeliverOrderShopItem;
import com.bx.ant.pageModel.DeliverOrderShopItemExt;
import com.bx.ant.pageModel.ShopDeliverApply;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.mobian.thirdpart.yilianyun.Methods;
import com.mobian.util.Constants;
import com.mobian.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by john on 17/11/27.
 */
@Controller
@RequestMapping("/api/deliver/print")
public class ApiPrintController extends BaseController {

    @Resource
    private ShopDeliverApplyServiceI shopDeliverApplyService;

    @Autowired
    private DeliverOrderServiceI deliverOrderService;

    /**
     * 手动添加终端授权
     * @param machineCode 易联云打印机终端号
     * @param msign 易联云终端密钥
     * @param request
     * @return
     */
    @RequestMapping("addPrinter")
    @ResponseBody
    public Json addPrinter(String machineCode, String msign, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(machineCode) || F.empty(msign)) {
                j.setMsg("终端号或密钥不能为空");
                return j;
            }

            TokenWrap token = getTokenWrap(request);
            ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));
            if(machineCode.equals(shopDeliverApply.getMachineCode())) {
                j.setMsg("终端号已绑定");
                return j;
            }

            // TODO 删除旧的终端授权
            if(!F.empty(shopDeliverApply.getMachineCode())) {
                // Methods.getInstance().init().deletePrinter(shopDeliverApply.getMachineCode());
            }

            String result = Methods.getInstance().init().addPrinter(machineCode, msign);
            JSONObject json = JSONObject.parseObject(result);
            if(json.getInteger("error") != 0) {
                j.setMsg("绑定失败");
                return j;
            }

            ShopDeliverApply apply = new ShopDeliverApply();
            apply.setId(shopDeliverApply.getId());
            apply.setMachineCode(machineCode);
            shopDeliverApplyService.edit(apply);

            j.success();
            j.setMsg("绑定成功");
        } catch (Exception e) {
            logger.error("手动添加终端授权接口异常", e);
        }

        return j;
    }

    /**
     * 扫码极速授权
     * @param machineCode 易联云打印机终端号
     * @param qrKey 特殊密钥(有效期为300秒)
     * @param request
     * @return
     */
    @RequestMapping("speedAu")
    @ResponseBody
    public Json speedAu(String machineCode, String qrKey, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(machineCode) || F.empty(qrKey)) {
                j.setMsg("终端号或密钥不能为空");
                return j;
            }

            TokenWrap token = getTokenWrap(request);
            ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));
            if(machineCode.equals(shopDeliverApply.getMachineCode())) {
                j.setMsg("终端号已绑定");
                return j;
            }

            // TODO 删除旧的终端授权
            if(!F.empty(shopDeliverApply.getMachineCode())) {
                // Methods.getInstance().init().deletePrinter(shopDeliverApply.getMachineCode());
            }

            String result = Methods.getInstance().init().speedAu(machineCode, qrKey);
            JSONObject json = JSONObject.parseObject(result);
            if(json.getInteger("error") != 0) {
                j.setMsg("绑定失败");
                return j;
            }

            ShopDeliverApply apply = new ShopDeliverApply();
            apply.setId(shopDeliverApply.getId());
            apply.setMachineCode(machineCode);
            shopDeliverApplyService.edit(apply);

            j.success();
            j.setMsg("绑定成功");
        } catch (Exception e) {
            logger.error("扫码极速授权接口异常", e);
        }

        return j;
    }

    /**
     * 根据订单号打印小票
     * @param orderId
     * @param request
     * @return
     */
    @RequestMapping("print")
    @ResponseBody
    public Json print(Long orderId, HttpServletRequest request) {
        Json j = new Json();
        try {

            TokenWrap token = getTokenWrap(request);
            if(token.getShopId().equals(orderId)) {
                j.setMsg("订单与您身份信息不匹配！");
                return j;
            }
            ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));
            if(F.empty(shopDeliverApply.getMachineCode())) {
                j.setMsg("未添加终端设备");
                return j;
            }

            DeliverOrderExt order = deliverOrderService.getDetail(orderId);
            StringBuffer sb = new StringBuffer();
            sb.append("<FH2><FS><FW2> ** 仓蚁管家 **</FW2></FS></FH2>\r\r");
            sb.append("******************************\r");
            sb.append("<FH>");
            sb.append("收货人：" + order.getContactPeople() + "\r");
            sb.append("收货电话：" + order.getContactPhone() + "\r");
            sb.append("收货地址：" + order.getDeliveryAddress() + "\r");
            sb.append("下单时间：" + DateUtil.format(order.getAddtime(), Constants.DATE_FORMAT) + "\r");
            sb.append("订单号：" + order.getId() + "\r");
            if(!F.empty(order.getOriginalOrderId())) {
                sb.append("原订单号：" + order.getOriginalOrderId() + "\r");
                sb.append("店铺：" + order.getOriginalShop() + "\r");
            }
            sb.append("</FH>");
            sb.append("**************商品*************\r");
            sb.append("<FH>");
            sb.append("<table><tr><td>品名</td><td>数量</td><td>单价</td></tr>");
            if(CollectionUtils.isNotEmpty(order.getDeliverOrderShopItemList())) {
                for(DeliverOrderShopItem item : order.getDeliverOrderShopItemList()) {
                    DeliverOrderShopItemExt itemExt = (DeliverOrderShopItemExt) item;
                    sb.append("<tr>");
                    sb.append("<td>"+itemExt.getItemName()+"</td>");
                    sb.append("<td>x"+ itemExt.getQuantity()+"</td>");
                    sb.append("<td>"+ BigDecimal.valueOf(itemExt.getPrice()).divide(new BigDecimal(100)).floatValue()+"</td>");
                    sb.append("</tr>");
                }
            }
            sb.append("</table></FH>");
            sb.append("******************************\r");
            sb.append("<FH><right>订单总价：￥"+ BigDecimal.valueOf(order.getAmount()).divide(new BigDecimal(100)).floatValue()+"</right></FH>\r\r");

            sb.append("<FH2><FS><FW2>    ** 完 **</FW2></FS></FH2>");

            String result = Methods.getInstance().init().print(shopDeliverApply.getMachineCode(), sb.toString(), orderId + "");
            JSONObject json = JSONObject.parseObject(result);
            if(json.getInteger("error") != 0) {
                j.setMsg("打印失败");
                return j;
            }

            j.success();
            j.setMsg("打印成功");
        } catch (Exception e) {
            logger.error("根据订单号打印小票接口异常", e);
        }

        return j;
    }


    /**
     * 取消所有未打印订单
     * @param request
     * @return
     */
    @RequestMapping("cancelall")
    @ResponseBody
    public Json cancelall(HttpServletRequest request) {
        Json j = new Json();
        try {

            TokenWrap token = getTokenWrap(request);
            ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));

            String result = Methods.getInstance().init().cancelAll(shopDeliverApply.getMachineCode());
            JSONObject json = JSONObject.parseObject(result);
            if(json.getInteger("error") != 0) {
                j.setMsg("取消失败");
                return j;
            }

            j.success();
            j.setMsg("取消成功");
        } catch (Exception e) {
            logger.error("扫码极速授权接口异常", e);
        }

        return j;
    }
}
