package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierStockInDaoI;
import com.mobian.model.TmbSupplierStockIn;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.MyBeanUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbSupplierStockInServiceImpl extends BaseServiceImpl<MbSupplierStockIn> implements MbSupplierStockInServiceI {

    @Autowired
    private MbSupplierStockInDaoI mbSupplierStockInDao;
    @Autowired
    private MbSupplierOrderServiceI mbSupplierOrderService;
    @Autowired
    private MbSupplierStockInItemServiceI mbSupplierStockInItemService;
    @Autowired
    private MbItemStockServiceI mbItemStockService;
    @Autowired
    private UserServiceI userService;
    @Autowired
    private MbSupplierFinanceLogServiceI mbSupplierFinanceLogService;
    @Autowired
    private MbWarehouseServiceImpl mbWarehouseService;
    @Autowired
    private MbSupplierServiceI mbSupplierService;
    @Autowired
    private MbSupplierContractServiceI mbSupplierContractService;


    @Override
    public DataGrid dataGrid(MbSupplierStockIn mbSupplierStockIn, PageHelper ph) {
        List<MbSupplierStockIn> ol = new ArrayList<MbSupplierStockIn>();
        String hql = " from TmbSupplierStockIn t ";
        DataGrid dg = dataGridQuery(hql, ph, mbSupplierStockIn, mbSupplierStockInDao);
        @SuppressWarnings("unchecked")
        List<TmbSupplierStockIn> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbSupplierStockIn t : l) {
                MbSupplierStockIn o = new MbSupplierStockIn();
                BeanUtils.copyProperties(t, o);
                if (o.getLoginId() != null) {
                    User user = userService.getFromCache(o.getLoginId());
                    if (user != null) {
                        o.setLoginName(user.getName());
                    }
                }
                if (o.getSignPeopleId() != null) {
                    User user = userService.getFromCache(o.getSignPeopleId());
                    if (user != null) {
                        o.setSignPeopleName(user.getNickname());
                    }
                }
                if (o.getWarehouseId() != null) {
                    MbWarehouse mbWarehouse = mbWarehouseService.get(o.getWarehouseId());
                    o.setWarehouseName(mbWarehouse.getName());
                }
                if (o.getSupplierOrderId() != null) {
                    MbSupplierOrder mbSupplierOrder = mbSupplierOrderService.get(o.getSupplierOrderId());
                    MbSupplier mbSupplier = mbSupplierService.get(mbSupplierOrder.getSupplierId());
                    o.setSupplierName(mbSupplier.getName());
                }
                MbSupplierOrder mbSupplierOrder = mbSupplierOrderService.get(o.getSupplierOrderId());
                MbSupplierContract mbSupplierContract = mbSupplierContractService.get(mbSupplierOrder.getSupplierContractId());
                o.setPaymentDays(mbSupplierContract.getPaymentDays());
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }

    @Override
    public void addSupplierStockIn(MbSupplierStockIn mbSupplierStockIn, String dataGrid) {
        mbSupplierStockIn.setPayStatus("FS01");
        mbSupplierStockIn.setInvoiceStatus("IS01");
        add(mbSupplierStockIn);
        addStockInItem(dataGrid, mbSupplierStockIn);
    }

    //解析传进来的dataGrid对象并且把里面的每个元素保存到入库详情表。
    @Override
    public void addStockInItem(String dataGrid, MbSupplierStockIn mbSupplierStockIn) {
        net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(dataGrid);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            MbSupplierStockInItem mbSupplierStockInItem = (MbSupplierStockInItem) JSONObject.toBean(jsonObject, MbSupplierStockInItem.class);
            mbSupplierStockInItem.setSupplierStockInId(mbSupplierStockIn.getId());
            mbSupplierStockInItemService.add(mbSupplierStockInItem);
            Integer id = mbSupplierStockIn.getWarehouseId();
            Integer itemid = mbSupplierStockInItem.getItemId();
            //添加入库项改变仓库库存
            MbItemStock mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(id, itemid);
            Integer totalPrice, totalQuantity, averagePrice;
            totalQuantity = mbItemStockShop.getQuantity() + mbSupplierStockInItem.getQuantity();
            if (F.empty(mbItemStockShop.getAveragePrice())) {
                averagePrice =mbSupplierStockInItem.getPrice();
            } else {
                if (mbItemStockShop.getQuantity() < 0) {
                    mbItemStockShop.setQuantity(mbItemStockShop.getQuantity() * (-1));
                }
                totalPrice = mbItemStockShop.getAveragePrice() * mbItemStockShop.getQuantity() + mbSupplierStockInItem.getPrice() * mbSupplierStockInItem.getQuantity();
                averagePrice = totalPrice / (mbItemStockShop.getQuantity() + mbSupplierStockInItem.getQuantity());
            }
            MbItemStock changeShop = new MbItemStock();
            changeShop.setId(mbItemStockShop.getId());
            changeShop.setAdjustment(mbSupplierStockInItem.getQuantity() == null ? 0 : mbSupplierStockInItem.getQuantity());
            changeShop.setAveragePrice(averagePrice);
            changeShop.setLogType("SL02");
            changeShop.setReason(String.format("入库ID：%s发货入库，库存:%s", mbSupplierStockIn.getId(),totalQuantity));
            mbItemStockService.editAndInsertLog(changeShop, mbSupplierStockIn.getLoginId());
            mbItemStockService.editItemStockAveragePrice(changeShop);

        }

    }

    @Override
    public void addPay(Integer id, String remark, String loginId) {
        MbSupplierStockIn mbSupplierStockIn = new MbSupplierStockIn();
        mbSupplierStockIn.setId(id);
        mbSupplierStockIn.setPayStatus("FS02");
        edit(mbSupplierStockIn);
        MbSupplierFinanceLog mbSupplierFinanceLog = new MbSupplierFinanceLog();
        mbSupplierFinanceLog.setPayStatus(mbSupplierStockIn.getPayStatus());
        mbSupplierFinanceLog.setPayLoginId(loginId);
        mbSupplierFinanceLog.setSupplierStockInId(mbSupplierStockIn.getId());
        mbSupplierFinanceLog.setPayRemark(remark);
        mbSupplierFinanceLog.setInvoiceStatus(mbSupplierStockIn.getInvoiceStatus());
        mbSupplierFinanceLogService.add(mbSupplierFinanceLog);

    }

    @Override
    public void addInvoice(Integer id, String remark, String loginId,String invoiceNo) {
        MbSupplierStockIn mbSupplierStockIn = new MbSupplierStockIn();
        mbSupplierStockIn.setId(id);
        mbSupplierStockIn.setInvoiceStatus("IS02");
        edit(mbSupplierStockIn);
        MbSupplierFinanceLog mbSupplierFinanceLog = new MbSupplierFinanceLog();
        mbSupplierFinanceLog.setSupplierStockInId(mbSupplierStockIn.getId());
        List<MbSupplierFinanceLog> list = mbSupplierFinanceLogService.query(mbSupplierFinanceLog);
        MbSupplierFinanceLog mbSupplierFinanceLog2 = list.get(0);
        mbSupplierFinanceLog2.setInvoiceStatus(mbSupplierStockIn.getInvoiceStatus());
        mbSupplierFinanceLog2.setInvoiceLoginId(loginId);
        mbSupplierFinanceLog2.setInvoiceRemark(remark);
        mbSupplierFinanceLog2.setInvoiceNo(invoiceNo);
        mbSupplierFinanceLogService.edit(mbSupplierFinanceLog2);
    }

    protected String whereHql(MbSupplierStockIn mbSupplierStockIn, Map<String, Object> params) {
        String whereHql = "";
        if (mbSupplierStockIn != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbSupplierStockIn.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbSupplierStockIn.getTenantId());
            }
            if (!F.empty(mbSupplierStockIn.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbSupplierStockIn.getIsdeleted());
            }
            if (!F.empty(mbSupplierStockIn.getSupplierOrderId())) {
                whereHql += " and t.supplierOrderId = :supplierOrderId";
                params.put("supplierOrderId", mbSupplierStockIn.getSupplierOrderId());
            }
            if (!F.empty(mbSupplierStockIn.getStatus())) {
                whereHql += " and t.status = :status";
                params.put("status", mbSupplierStockIn.getStatus());
            }
            if (!F.empty(mbSupplierStockIn.getSignPeopleId())) {
                whereHql += " and t.signPeopleId = :signPeopleId";
                params.put("signPeopleId", mbSupplierStockIn.getSignPeopleId());
            }
            if (!F.empty(mbSupplierStockIn.getReceiveUrl())) {
                whereHql += " and t.receiveUrl = :receiveUrl";
                params.put("receiveUrl", mbSupplierStockIn.getReceiveUrl());
            }
            if (!F.empty(mbSupplierStockIn.getLoginId())) {
                whereHql += " and t.loginId = :loginId";
                params.put("loginId", mbSupplierStockIn.getLoginId());
            }
            if (!F.empty(mbSupplierStockIn.getPayStatus())) {
                whereHql += " and t.payStatus = :payStatus";
                params.put("payStatus", mbSupplierStockIn.getPayStatus());
            }
            if (!F.empty(mbSupplierStockIn.getInvoiceStatus())) {
                whereHql += " and t.invoiceStatus = :invoiceStatus";
                params.put("invoiceStatus", mbSupplierStockIn.getInvoiceStatus());
            }
            if (!F.empty(mbSupplierStockIn.getWarehouseId())) {
                whereHql += " and t.warehouseId = :warehouseId";
                params.put("warehouseId", mbSupplierStockIn.getWarehouseId());
            }
            if (mbSupplierStockIn.getStockinTimeBegin() != null) {
                whereHql += " and t.addtime >= :stockinTimeBegin";
                params.put("stockinTimeBegin", mbSupplierStockIn.getStockinTimeBegin());
            }
            if (mbSupplierStockIn.getStockinTimeEnd() != null) {
                whereHql += " and t.addtime <= :stockinTimeEnd";
                params.put("stockinTimeEnd", mbSupplierStockIn.getStockinTimeEnd());
            }
        }
        return whereHql;
    }

    @Override
    public void add(MbSupplierStockIn mbSupplierStockIn) {
        TmbSupplierStockIn t = new TmbSupplierStockIn();
        BeanUtils.copyProperties(mbSupplierStockIn, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbSupplierStockInDao.save(t);
        mbSupplierStockIn.setId(t.getId());

    }

    @Override
    public MbSupplierStockIn get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbSupplierStockIn t = mbSupplierStockInDao.get("from TmbSupplierStockIn t  where t.id = :id", params);
        MbSupplierStockIn o = new MbSupplierStockIn();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public MbSupplierStockIn getByOrderId(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbSupplierStockIn t = mbSupplierStockInDao.get("from TmbSupplierStockIn t  where t.supplierOrderId = :id", params);
        MbSupplierStockIn o = new MbSupplierStockIn();
        if(t!=null) {
            BeanUtils.copyProperties(t, o);
        }
        return o;
    }

    @Override
    public void edit(MbSupplierStockIn mbSupplierStockIn) {
        TmbSupplierStockIn t = mbSupplierStockInDao.get(TmbSupplierStockIn.class, mbSupplierStockIn.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbSupplierStockIn, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbSupplierStockInDao.executeHql("update TmbSupplierStockIn t set t.isdeleted = 1 where t.id = :id", params);
        //mbSupplierStockInDao.delete(mbSupplierStockInDao.get(TmbSupplierStockIn.class, id));
    }

    @Override
    public List<MbSupplierStockIn> getMbSupplierStockInListByOrderId(Integer orderId) {
        return getListByOrderIds(new Integer[]{orderId});
    }

    @Override
    public List<MbSupplierStockIn> getListByOrderIds(Integer[] orderIds) {
        List<MbSupplierStockIn> ol = new ArrayList<MbSupplierStockIn>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("alist", orderIds);
        List<TmbSupplierStockIn> l = mbSupplierStockInDao.find("from TmbSupplierStockIn t where t.isdeleted = 0 and t.supplierOrderId in (:alist)", params);
        if (!CollectionUtils.isEmpty(l)) {
            for (TmbSupplierStockIn stockIn : l) {
                MbSupplierStockIn o = new MbSupplierStockIn();
                BeanUtils.copyProperties(stockIn, o);
                ol.add(o);
            }
        }
        return ol;
    }

    @Override
    public List<MbSupplierStockIn> query(MbSupplierStockIn stockIn) {
        List<MbSupplierStockIn> ol = new ArrayList<MbSupplierStockIn>();
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = " from TmbSupplierStockIn t ";
        List<TmbSupplierStockIn> l = mbSupplierStockInDao.find(hql + whereHql(stockIn, params), params);
        if (l != null && l.size() > 0) {
            for (TmbSupplierStockIn t : l) {
                MbSupplierStockIn o = new MbSupplierStockIn();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        return ol;
    }
}
