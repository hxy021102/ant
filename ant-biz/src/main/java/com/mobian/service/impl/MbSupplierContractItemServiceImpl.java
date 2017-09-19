package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierContractItemDaoI;
import com.mobian.model.TmbSupplierContractItem;
import com.mobian.pageModel.*;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbSupplierContractItemServiceI;
import com.mobian.service.MbSupplierContractServiceI;
import com.mobian.service.MbSupplierServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbSupplierContractItemServiceImpl extends BaseServiceImpl<MbSupplierContractItem> implements MbSupplierContractItemServiceI {

    @Autowired
    private MbSupplierContractItemDaoI mbSupplierContractItemDao;
    @Autowired
    private MbSupplierContractServiceI mbSupplierContractService;
    @Autowired
    private MbSupplierServiceI mbSupplierService;
    @Autowired
    private MbItemServiceI mbItemService;

    @Override
    public DataGrid dataGrid(MbSupplierContractItem mbSupplierContractItem, PageHelper ph) {
        List<MbSupplierContractItem> ol = new ArrayList<MbSupplierContractItem>();
        String hql = " from TmbSupplierContractItem t ";
        DataGrid dg = dataGridQuery(hql, ph, mbSupplierContractItem, mbSupplierContractItemDao);
        @SuppressWarnings("unchecked")
        List<TmbSupplierContractItem> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbSupplierContractItem t : l) {
                MbSupplierContractItem o = new MbSupplierContractItem();
                BeanUtils.copyProperties(t, o);
                if (o.getSupplierContractId() != null) {
                    MbSupplierContract mbSupplierContract = mbSupplierContractService.get(o.getSupplierContractId());
                    if (mbSupplierContract.getValid() == false) {
                        continue;
                    }
                    if (mbSupplierContract.getSupplierId() != null) {
                        MbSupplier supplier = mbSupplierService.getFromCache(mbSupplierContract.getSupplierId());
                        o.setExpiryDateStart(mbSupplierContract.getExpiryDateStart());
                        o.setExpiryDateEnd(mbSupplierContract.getExpiryDateEnd());
                        o.setCode(mbSupplierContract.getCode());
                        o.setSupplierName(supplier.getName());
                    }
                }
                if (o.getItemId() != null) {
                    MbItem item = mbItemService.getFromCache(o.getItemId());
                    if (item != null) {
                        o.setItemName(item.getName());
                    }
                }
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }

    public void editAndHistory(MbSupplierContractItem mbSupplierContractItem) {
        TmbSupplierContractItem t = mbSupplierContractItemDao.get(TmbSupplierContractItem.class, mbSupplierContractItem.getId());
        TmbSupplierContractItem old = new TmbSupplierContractItem();
        BeanUtils.copyProperties(t, old, new String[]{"id", "addtime", "isdeleted", "updatetime"});
        old.setIsdeleted(true);
        mbSupplierContractItemDao.save(old);
        if (t != null) {
            MyBeanUtils.copyProperties(mbSupplierContractItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    protected String whereHql(MbSupplierContractItem mbSupplierContractItem, Map<String, Object> params) {
        String whereHql = "";
        if (mbSupplierContractItem != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbSupplierContractItem.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbSupplierContractItem.getTenantId());
            }
            if (!F.empty(mbSupplierContractItem.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbSupplierContractItem.getIsdeleted());
            }
            if (!F.empty(mbSupplierContractItem.getSupplierContractId())) {
                whereHql += " and t.supplierContractId = :supplierContractId";
                params.put("supplierContractId", mbSupplierContractItem.getSupplierContractId());
            }
            if (!F.empty(mbSupplierContractItem.getItemId())) {
                whereHql += " and t.itemId = :itemId";
                params.put("itemId", mbSupplierContractItem.getItemId());
            }
            if (!F.empty(mbSupplierContractItem.getPrice())) {
                whereHql += " and t.price = :price";
                params.put("price", mbSupplierContractItem.getPrice());
            }
        }
        return whereHql;
    }

    @Override
    public void add(MbSupplierContractItem mbSupplierContractItem) {
        TmbSupplierContractItem t = new TmbSupplierContractItem();
        BeanUtils.copyProperties(mbSupplierContractItem, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbSupplierContractItemDao.save(t);
    }

    @Override
    public MbSupplierContractItem get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbSupplierContractItem t = mbSupplierContractItemDao.get("from TmbSupplierContractItem t  where t.id = :id", params);
        MbSupplierContractItem o = new MbSupplierContractItem();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(MbSupplierContractItem mbSupplierContractItem) {
        TmbSupplierContractItem t = mbSupplierContractItemDao.get(TmbSupplierContractItem.class, mbSupplierContractItem.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbSupplierContractItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbSupplierContractItemDao.executeHql("update TmbSupplierContractItem t set t.isdeleted = 1 where t.id = :id", params);
        //mbSupplierContractItemDao.delete(mbSupplierContractItemDao.get(TmbSupplierContractItem.class, id));
    }

}
