package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbShopInvoiceDaoI;
import com.mobian.model.TmbShopInvoice;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShopInvoice;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbShopInvoiceServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbShopInvoiceServiceImpl extends BaseServiceImpl<MbShopInvoice> implements MbShopInvoiceServiceI {

    @Autowired
    private MbShopInvoiceDaoI mbShopInvoiceDao;


    @Override
    public DataGrid dataGrid(MbShopInvoice mbShopInvoice, PageHelper ph) {
        DataGrid dg = new DataGrid();
        List<MbShopInvoice> ol = new ArrayList<MbShopInvoice>();
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = " from TmbShopInvoice t ";
        String where = whereHql(mbShopInvoice, params);
        List<TmbShopInvoice> l = mbShopInvoiceDao.find(hql + where, params);
        if (l != null && l.size() > 0) {
            for (TmbShopInvoice t : l) {
                MbShopInvoice o = new MbShopInvoice();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }


    protected String whereHql(MbShopInvoice mbShopInvoice, Map<String, Object> params) {
        String whereHql = "";
        if (mbShopInvoice != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbShopInvoice.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbShopInvoice.getTenantId());
            }
            if (!F.empty(mbShopInvoice.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbShopInvoice.getIsdeleted());
            }
            if (!F.empty(mbShopInvoice.getShopId())) {
                whereHql += " and t.shopId = :shopId";
                params.put("shopId", mbShopInvoice.getShopId());
            }
            if (!F.empty(mbShopInvoice.getCompanyName())) {
                whereHql += " and t.companyName = :companyName";
                params.put("companyName", mbShopInvoice.getCompanyName());
            }
            if (!F.empty(mbShopInvoice.getCompanyTfn())) {
                whereHql += " and t.companyTfn = :companyTfn";
                params.put("companyTfn", mbShopInvoice.getCompanyTfn());
            }
            if (!F.empty(mbShopInvoice.getRegisterAddress())) {
                whereHql += " and t.registerAddress = :registerAddress";
                params.put("registerAddress", mbShopInvoice.getRegisterAddress());
            }
            if (!F.empty(mbShopInvoice.getRegisterPhone())) {
                whereHql += " and t.registerPhone = :registerPhone";
                params.put("registerPhone", mbShopInvoice.getRegisterPhone());
            }
            if (!F.empty(mbShopInvoice.getBankName())) {
                whereHql += " and t.bankName = :bankName";
                params.put("bankName", mbShopInvoice.getBankName());
            }
            if (!F.empty(mbShopInvoice.getBankNumber())) {
                whereHql += " and t.bankNumber = :bankNumber";
                params.put("bankNumber", mbShopInvoice.getBankNumber());
            }
            if (!F.empty(mbShopInvoice.getInvoiceUse())) {
                whereHql += " and t.invoiceUse = :invoiceUse";
                params.put("invoiceUse", mbShopInvoice.getInvoiceUse());
            }
            if (!F.empty(mbShopInvoice.getInvoiceType())) {
                whereHql += " and t.invoiceType = :invoiceType";
                params.put("invoiceType", mbShopInvoice.getInvoiceType());
            }
            if (!F.empty(mbShopInvoice.getInvoiceDefault())) {
                whereHql += " and t.invoiceDefault=:invoiceDefault";
                params.put("invoiceDefault", mbShopInvoice.getInvoiceDefault());

            }
        }
        return whereHql;
    }

    @Override
    public void add(MbShopInvoice mbShopInvoice) {
        TmbShopInvoice t = new TmbShopInvoice();
        BeanUtils.copyProperties(mbShopInvoice, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbShopInvoiceDao.save(t);
    }

    @Override
    public MbShopInvoice get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbShopInvoice t = mbShopInvoiceDao.get("from TmbShopInvoice t  where t.id = :id", params);
        MbShopInvoice o = new MbShopInvoice();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(MbShopInvoice mbShopInvoice) {
        TmbShopInvoice t = mbShopInvoiceDao.get(TmbShopInvoice.class, mbShopInvoice.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbShopInvoice, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbShopInvoiceDao.executeHql("update TmbShopInvoice t set t.isdeleted = 1 where t.id = :id", params);
        //mbShopInvoiceDao.delete(mbShopInvoiceDao.get(TmbShopInvoice.class, id));
    }

    @Override
    public List<MbShopInvoice> query(MbShopInvoice mbShopInvoice) {
        List<MbShopInvoice> ol = new ArrayList<MbShopInvoice>();
        String hql = " from TmbShopInvoice t ";
        Map<String, Object> params = new HashMap<String, Object>();
        String where = whereHql(mbShopInvoice, params);
        List<TmbShopInvoice> l = mbShopInvoiceDao.find(hql + where, params);
        if (CollectionUtils.isNotEmpty(l)) {
            for (TmbShopInvoice t : l) {
                MbShopInvoice o = new MbShopInvoice();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        return ol;
    }
}
