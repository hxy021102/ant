package com.bx.ant.controller;

import com.alibaba.fastjson.JSONObject;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.ShopDeliverApply;
import com.bx.ant.pageModel.session.TokenWrap;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.Json;
import com.mobian.thirdpart.yilianyun.Methods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 易联云打印接口
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
            DeliverOrder order = deliverOrderService.get(orderId);
            if(order == null) {
                j.setMsg("订单号【"+orderId+"】不存在");
                return j;
            }
            if(!token.getShopId().equals(order.getShopId())) {
                j.setMsg("订单与您身份信息不匹配！");
                return j;
            }
            ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));
            if(F.empty(shopDeliverApply.getMachineCode())) {
                j.setMsg("未绑定终端设备");
                return j;
            }

            boolean flag = deliverOrderService.printOrder(orderId, shopDeliverApply.getMachineCode());
            if(!flag) {
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

            if(F.empty(shopDeliverApply.getMachineCode())) {
                j.setMsg("未绑定终端设备");
                return j;
            }

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

    /**
     * 打印机关机重启
     * @param request
     * @return
     */
    @RequestMapping("shutdownrestart")
    @ResponseBody
    public Json shutdownrestart(String responseType, HttpServletRequest request) {
        Json j = new Json();
        try {

            TokenWrap token = getTokenWrap(request);
            ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));

            if(F.empty(shopDeliverApply.getMachineCode())) {
                j.setMsg("未绑定终端设备");
                return j;
            }

            String result = Methods.getInstance().init().shutDownRestart(shopDeliverApply.getMachineCode(), responseType);
            JSONObject json = JSONObject.parseObject(result);
            if(json.getInteger("error") != 0) {
                j.setMsg("关机失败");
                return j;
            }

            j.success();
            j.setMsg("关机成功");
        } catch (Exception e) {
            logger.error("打印机关机重启接口异常", e);
        }

        return j;
    }

    /**
     * 更新是否自动打印
     * @param request
     * @return
     */
    @RequestMapping("updateAutoPrint")
    @ResponseBody
    public Json updateAutoPrint(Boolean autoPrint, HttpServletRequest request) {
        Json j = new Json();
        try {

            TokenWrap token = getTokenWrap(request);
            if(!F.empty(token.getUid()) && autoPrint != null) {
                ShopDeliverApply shopDeliverApply = shopDeliverApplyService.getByAccountId(Integer.valueOf(token.getUid()));
                if(shopDeliverApply != null) {
                    ShopDeliverApply apply = new ShopDeliverApply();
                    apply.setId(shopDeliverApply.getId());
                    apply.setAutoPrint(autoPrint);
                    shopDeliverApplyService.edit(apply);
                    j.setSuccess(true);
                    j.setMsg("更新成功！");
                }
            }
        } catch (Exception e) {
            logger.error("打印机关机重启接口异常", e);
        }

        return j;
    }
}
