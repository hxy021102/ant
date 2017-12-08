package com.bx.ant.service.impl.driverodershopstate;

import com.bx.ant.pageModel.DeliverOrderShop;
import com.bx.ant.pageModel.DriverOrderShop;
import com.bx.ant.service.DeliverOrderShopServiceI;
import com.bx.ant.service.DriverFreightRuleServiceI;
import com.bx.ant.service.DriverOrderShopServiceI;
import com.bx.ant.service.DriverOrderShopState;
import com.mobian.absx.F;
import com.mobian.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  添加
 * Created by w9777 on 2017/11/6.
 */
@Service(value = "driverOrderShop01StateImpl")
public class DriverOrderShop01StateImpl implements DriverOrderShopState {
    @Resource(name = "driverOrderShop50StateImpl")
    private DriverOrderShopState driverOrderShopState50;

    @Resource(name = "driverOrderShop03StateImpl")
    private DriverOrderShopState driverOrderShopState03;

    @Resource
    private DriverOrderShopServiceI driverOrderShopSerivce;

    @Resource
    private DeliverOrderShopServiceI deliverOrderShopService;

    @Resource
    private DriverFreightRuleServiceI driverFreightRuleService;


    @Override
    public String getStateName() {
        return "01";
    }

    @Override
    public void handle(DriverOrderShop driverOrderShop) {
        //判断是否有充足的参数
        if (F.empty(driverOrderShop.getShopId()) || F.empty(driverOrderShop.getDeliverOrderShopId())) {
            throw new ServiceException("DriverOrderShop01StateImpl参数不足");
        }
        //状态和支付状态
        driverOrderShop.setStatus(prefix + getStateName());
        driverOrderShop.setPayStatus(DriverOrderShopServiceI.PAY_STATUS_NOT_PAY);

        //计算金额
        if (!F.empty(driverOrderShop.getDeliverOrderShopId())) {
            DeliverOrderShop orderShop = deliverOrderShopService.get(driverOrderShop.getDeliverOrderShopId());
            if (orderShop != null) {
                driverOrderShop.setAmount(driverFreightRuleService.getOrderFreight(orderShop, DriverFreightRuleServiceI.TYPE_CONTRACT));
            }
        }

        driverOrderShopSerivce.update(driverOrderShop);
    }

    @Override
    public DriverOrderShopState next(DriverOrderShop driverOrderShop) {
        if ( (prefix  + "03").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState03;
        }
        if ((prefix + "50").equals(driverOrderShop.getStatus())) {
            return driverOrderShopState50;
        }
        return null;
    }
}
