package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbOrderInvoiceDaoI;
import com.mobian.model.TmbOrderInvoice;
import com.mobian.pageModel.*;
import com.mobian.service.MbOrderInvoiceServiceI;
import com.mobian.service.MbOrderServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.MyBeanUtils;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbOrderInvoiceServiceImpl extends BaseServiceImpl<MbOrderInvoice> implements MbOrderInvoiceServiceI {




    @Autowired
    private MbOrderInvoiceDaoI mbOrderInvoiceDao;
    @Autowired
    private MbOrderServiceI mbOrderService;
    @Autowired
    private MbShopServiceI mbShopService;
    @Autowired
    private UserServiceI userService;

    @Override
    public DataGrid dataGrid(MbOrderInvoice mbOrderInvoice, PageHelper ph) {
        DataGrid dg = new DataGrid();
        List<MbOrderInvoice> ol = new ArrayList<MbOrderInvoice>();
        String hql = " from TmbOrderInvoice t ";
        Map<String, Object> params = new HashMap<String, Object>();
        List<TmbOrderInvoice> l = mbOrderInvoiceDao.find(hql + whereHql(mbOrderInvoice, params)/* + orderHql(ph)*/, params, ph.getPage(), ph.getRows());
        if (l != null && l.size() > 0) {
            for (TmbOrderInvoice t : l) {
                MbOrderInvoice o = new MbOrderInvoice();
                BeanUtils.copyProperties(t, o);
                if (o.getLoginId() != null) {
                  User user = userService.get(o.getLoginId());
                  String name=user.getName();
                  o.setLoginName(name);
                }
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }


    protected String whereHql(MbOrderInvoice mbOrderInvoice, Map<String, Object> params) {
        String whereHql = "";
        if (mbOrderInvoice != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbOrderInvoice.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbOrderInvoice.getTenantId());
            }
            if (!F.empty(mbOrderInvoice.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbOrderInvoice.getIsdeleted());
            }
            if (!F.empty(mbOrderInvoice.getOrderId())) {
                whereHql += " and t.orderId = :orderId";
                params.put("orderId", mbOrderInvoice.getOrderId());
            }
            if (!F.empty(mbOrderInvoice.getInvoiceStatus())) {
                whereHql += " and t.invoiceStatus = :invoiceStatus";
                params.put("invoiceStatus", mbOrderInvoice.getInvoiceStatus());
            }
            if (!F.empty(mbOrderInvoice.getCompanyName())) {
                whereHql += " and t.companyName = :companyName";
                params.put("companyName", mbOrderInvoice.getCompanyName());
            }
            if (!F.empty(mbOrderInvoice.getCompanyTfn())) {
                whereHql += " and t.companyTfn = :companyTfn";
                params.put("companyTfn", mbOrderInvoice.getCompanyTfn());
            }
            if (!F.empty(mbOrderInvoice.getRegisterAddress())) {
                whereHql += " and t.registerAddress = :registerAddress";
                params.put("registerAddress", mbOrderInvoice.getRegisterAddress());
            }
            if (!F.empty(mbOrderInvoice.getRegisterPhone())) {
                whereHql += " and t.registerPhone = :registerPhone";
                params.put("registerPhone", mbOrderInvoice.getRegisterPhone());
            }
            if (!F.empty(mbOrderInvoice.getBankName())) {
                whereHql += " and t.bankName = :bankName";
                params.put("bankName", mbOrderInvoice.getBankName());
            }
            if (!F.empty(mbOrderInvoice.getBankNumber())) {
                whereHql += " and t.bankNumber = :bankNumber";
                params.put("bankNumber", mbOrderInvoice.getBankNumber());
            }
            if (!F.empty(mbOrderInvoice.getInvoiceUse())) {
                whereHql += " and t.invoiceUse = :invoiceUse";
                params.put("invoiceUse", mbOrderInvoice.getInvoiceUse());
            }
            if (!F.empty(mbOrderInvoice.getLoginId())) {
                whereHql += " and t.loginId = :loginId";
                params.put("loginId", mbOrderInvoice.getLoginId());
            }
            if (!F.empty(mbOrderInvoice.getRemark())) {
                whereHql += " and t.remark = :remark";
                params.put("remark", mbOrderInvoice.getRemark());
            }
            if (!F.empty(mbOrderInvoice.getShopId())) {
                whereHql += " and t.shopId = :shopId";
                params.put("shopId", mbOrderInvoice.getShopId());
            }
        }
        return whereHql;
    }

    @Override
    public void add(MbOrderInvoice mbOrderInvoice) {
        TmbOrderInvoice t = new TmbOrderInvoice();
        BeanUtils.copyProperties(mbOrderInvoice, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbOrderInvoiceDao.save(t);
    }

    @Override
    public MbOrderInvoice get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbOrderInvoice t = mbOrderInvoiceDao.get("from TmbOrderInvoice t  where t.id = :id", params);
        MbOrderInvoice o = new MbOrderInvoice();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(MbOrderInvoice mbOrderInvoice) {
        TmbOrderInvoice t = mbOrderInvoiceDao.get(TmbOrderInvoice.class, mbOrderInvoice.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbOrderInvoice, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbOrderInvoiceDao.executeHql("update TmbOrderInvoice t set t.isdeleted = 1 where t.id = :id", params);
        //mbOrderInvoiceDao.delete(mbOrderInvoiceDao.get(TmbOrderInvoice.class, id));
    }

    @Override
    @Cacheable(value = "orderInvoiceGetByOrderIdCache", key = "#orderId")
    public MbOrderInvoice getByOrderId(Integer orderId) {
        TmbOrderInvoice tmbOrderInvoice = mbOrderInvoiceDao.get("from TmbOrderInvoice t where t.orderId = " + orderId);
        if (tmbOrderInvoice != null) {
            MbOrderInvoice mbOrderInvoice = new MbOrderInvoice();
            BeanUtils.copyProperties(tmbOrderInvoice, mbOrderInvoice);
            return mbOrderInvoice;
        }
        return null;
    }
    @Override
    public MbOrderInvoice getWithOrderId(Integer orderId) {
        TmbOrderInvoice tmbOrderInvoice = mbOrderInvoiceDao.get("from TmbOrderInvoice t where t.orderId = " + orderId);
        if (tmbOrderInvoice != null) {
            MbOrderInvoice mbOrderInvoice = new MbOrderInvoice();
            BeanUtils.copyProperties(tmbOrderInvoice, mbOrderInvoice);
            return mbOrderInvoice;
        }
        return null;
    }
    @Override
    public void addOrderInvoice(MbOrderInvoice mbOrderInvoice, String mbOrderInvoiceList) {
        JSONArray jsonArray = JSONArray.fromObject(mbOrderInvoiceList);
        List<MbOrder> list = (List<MbOrder>) jsonArray.toCollection(jsonArray, MbOrder.class);
            for (int i = 0; i < list.size(); i++) {
            mbOrderInvoice.setOrderId(list.get(i).getId());
            mbOrderInvoice.setInvoiceStatus("IS02");
            add(mbOrderInvoice);
        }
    }
}
