package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierOrderDaoI;
import com.mobian.model.TmbSupplierOrder;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.ConfigUtil;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbSupplierOrderServiceImpl extends BaseServiceImpl<MbSupplierOrder> implements MbSupplierOrderServiceI {

    @Autowired
    private MbSupplierOrderDaoI mbSupplierOrderDao;
    @Autowired
    private MbSupplierServiceI mbSupplierService;
    @Autowired
    private UserServiceI userService;
    @Autowired
    MbWarehouseServiceI mbWarehouseService;
    @Autowired
    private MbSupplierOrderItemServiceI mbSupplierOrderItemService;
    @Autowired
    private MbSupplierContractServiceI mbSupplierContractService;

    @Override
    public DataGrid dataGrid(MbSupplierOrder mbSupplierOrder, PageHelper ph) {
        List<MbSupplierOrder> ol = new ArrayList<MbSupplierOrder>();
        String hql = " from TmbSupplierOrder t ";
        DataGrid dg = dataGridQuery(hql, ph, mbSupplierOrder, mbSupplierOrderDao);
        @SuppressWarnings("unchecked")
        List<TmbSupplierOrder> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbSupplierOrder t : l) {
                MbSupplierOrder o = new MbSupplierOrder();
                BeanUtils.copyProperties(t, o);
                if (o.getSupplierId() != null) {
                    MbSupplier supplier = mbSupplierService.getFromCache(o.getSupplierId());
                    if (supplier != null) {
                        o.setSupplierName(supplier.getName());
                    }
                }
                if (o.getSupplierPeopleId() != null) {
                    User user = userService.getFromCache(o.getSupplierPeopleId());
                    if (user != null) {
                        o.setSupplierPeopleName(user.getName());
                    }
                }

                if (o.getReviewerId() != null) {
                    User user = userService.getFromCache(o.getReviewerId());
                    if (user != null) {
                        o.setReviewerName(user.getName());
                    }
                }
                if (o.getWarehouseId() != null) {
                    MbWarehouse mbWarehouse = mbWarehouseService.getFromCache(o.getWarehouseId());
                    if (mbWarehouse != null) {
                        o.setWarehouseName(mbWarehouse.getName());
                    }
                }
                if (o.getSupplierContractId() != null) {
                    MbSupplierContract mbSupplierContract = mbSupplierContractService.get(o.getSupplierContractId());
                    o.setCode(mbSupplierContract.getCode());

                }
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }

    public Boolean addMbSupplierOrder(MbSupplierOrder mbSupplierOrder, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        int totalPrice = 0;
        Boolean t = true;
        List<Integer> list = new ArrayList<Integer>();
        List<MbSupplierOrderItem> mbSupplierOrderItems= mbSupplierOrder.getMbSupplierOrderItemList();
        List<Integer> itemIds = new ArrayList<Integer>();
        for(MbSupplierOrderItem mbSupplierOrderItem:mbSupplierOrderItems){
            if (mbSupplierOrderItem.getPrice() != 0) {
                if (list.contains(mbSupplierOrderItem.getItemId())) {
                    t = false;
                    break;
                } else {
                    list.add(mbSupplierOrderItem.getItemId());
                }
            }
            if (F.empty(mbSupplierOrderItem.getPrice())) {
                if (itemIds.contains(mbSupplierOrderItem.getItemId())) {
                    t = false;
                    break;
                } else {
                    itemIds.add(mbSupplierOrderItem.getItemId());
                }
            }
        }
        if (t) {
            for (MbSupplierOrderItem mbSupplierOrderItem : mbSupplierOrder.getMbSupplierOrderItemList()) {
                totalPrice += mbSupplierOrderItem.getPrice() * mbSupplierOrderItem.getQuantity();
            }
            mbSupplierOrder.setTotalPrice(totalPrice);
            mbSupplierOrder.setStatus("SS04");
            mbSupplierOrder.setSupplierPeopleId(sessionInfo.getId());
            add(mbSupplierOrder);
            for (MbSupplierOrderItem mbSupplierOrderItem : mbSupplierOrder.getMbSupplierOrderItemList()) {
                mbSupplierOrderItem.setSupplierOrderId(mbSupplierOrder.getId());
                mbSupplierOrderItemService.add(mbSupplierOrderItem);
            }
            return true;
        }
        return false;

    }
    public MbSupplierOrder showMbSupplierOrder(Integer id){
        MbSupplierOrder mbSupplierOrder =get(id);
        MbSupplierContract mbSupplierContract = mbSupplierContractService.get(mbSupplierOrder.getSupplierContractId());
        if (mbSupplierContract.getCode() != null) {
            mbSupplierOrder.setCode(mbSupplierContract.getCode());
        }
        if (mbSupplierOrder.getSupplierId() != null) {
            MbSupplier supplier = mbSupplierService.getFromCache(mbSupplierOrder.getSupplierId());
            if (supplier != null) {
                mbSupplierOrder.setSupplierName(supplier.getName());
            }
        }
        if (mbSupplierOrder.getSupplierPeopleId() != null) {
            User user = userService.getFromCache(mbSupplierOrder.getSupplierPeopleId());
            if (user != null) {
                mbSupplierOrder.setSupplierPeopleName(user.getName());
            }
        }
        if (mbSupplierOrder.getReviewerId() != null) {
            User user = userService.getFromCache(mbSupplierOrder.getReviewerId());
            if (user != null) {
                mbSupplierOrder.setReviewerName(user.getName());
            }
        }
        if (mbSupplierOrder.getWarehouseId() != null) {
            MbWarehouse mbWarehouse = mbWarehouseService.getFromCache(mbSupplierOrder.getWarehouseId());
            if (mbWarehouse != null) {
                mbSupplierOrder.setWarehouseName(mbWarehouse.getName());
            }
        }
        return mbSupplierOrder;
    }
    public Boolean editMbSupplierOrder(MbSupplierOrder mbSupplierOrder) {
        int totalPrice = 0;
        Boolean t = true;
        List<Integer> list = new ArrayList<Integer>();
        for (MbSupplierOrderItem mbSupplierOrderItem : mbSupplierOrder.getMbSupplierOrderItemList()) {
            if (list.contains(mbSupplierOrderItem.getItemId())) {
                t = false;
                break;
            } else {
                list.add(mbSupplierOrderItem.getItemId());
            }
        }
        if (t) {
            List<MbSupplierOrderItem> orderItemsList = mbSupplierOrderItemService.mbSupplierOrderItemByOrderId(mbSupplierOrder.getId());
            for (MbSupplierOrderItem mItem : orderItemsList) {
                mbSupplierOrderItemService.delete(mItem.getId());
            }
            for (MbSupplierOrderItem mbSupplierOrderItem : mbSupplierOrder.getMbSupplierOrderItemList()) {
                totalPrice += mbSupplierOrderItem.getPrice() * mbSupplierOrderItem.getQuantity();
            }
            mbSupplierOrder.setTotalPrice(totalPrice);
            edit(mbSupplierOrder);
            for (MbSupplierOrderItem mbSupplierOrderItem : mbSupplierOrder.getMbSupplierOrderItemList()) {
                mbSupplierOrderItem.setSupplierOrderId(mbSupplierOrder.getId());
                mbSupplierOrderItemService.add(mbSupplierOrderItem);
            }
            return true;
        }
        return false;
    }

    protected String whereHql(MbSupplierOrder mbSupplierOrder, Map<String, Object> params) {
        String whereHql = "";
        if (mbSupplierOrder != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbSupplierOrder.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbSupplierOrder.getTenantId());
            }
            if (!F.empty(mbSupplierOrder.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbSupplierOrder.getIsdeleted());
            }
            if (!F.empty(mbSupplierOrder.getSupplierId())) {
                whereHql += " and t.supplierId = :supplierId";
                params.put("supplierId", mbSupplierOrder.getSupplierId());
            }
            if (!F.empty(mbSupplierOrder.getTotalPrice())) {
                whereHql += " and t.totalPrice = :totalPrice";
                params.put("totalPrice", mbSupplierOrder.getTotalPrice());
            }
            if (!F.empty(mbSupplierOrder.getStatus())) {
                whereHql += " and t.status in (:status)";
                params.put("status", mbSupplierOrder.getStatus().split(","));
            }
            if (!F.empty(mbSupplierOrder.getSupplierPeopleId())) {
                whereHql += " and t.supplierPeopleId = :supplierPeopleId";
                params.put("supplierPeopleId", mbSupplierOrder.getSupplierPeopleId());
            }
            if (!F.empty(mbSupplierOrder.getReviewerId())) {
                whereHql += " and t.reviewerId = :reviewerId";
                params.put("reviewerId", mbSupplierOrder.getReviewerId());
            }
            if (!F.empty(mbSupplierOrder.getReviewComment())) {
                whereHql += " and t.reviewComment = :reviewComment";
                params.put("reviewComment", mbSupplierOrder.getReviewComment());
            }
            if (!F.empty(mbSupplierOrder.getWarehouseId())) {
                whereHql += " and t.warehouseId = :warehouseId";
                params.put("warehouseId", mbSupplierOrder.getWarehouseId());
            }
            if (!F.empty(mbSupplierOrder.getRemark())) {
                whereHql += " and t.remark = :remark";
                params.put("remark", mbSupplierOrder.getRemark());
            }
            if(mbSupplierOrder.getAddtimeStart() != null) {
                whereHql += " and t.addtime >= :addtimeStart";
                params.put("addtimeStart", mbSupplierOrder.getAddtimeStart());
            }
            if(mbSupplierOrder.getAddtimeEnd() != null) {
                whereHql += " and t.addtime <= :addtimeEnd";
                params.put("addtimeEnd", mbSupplierOrder.getAddtimeEnd());
            }
        }
        return whereHql;
    }

    public void deleteSupplierOrderAndItem(Integer id) {
        List<MbSupplierOrderItem> orderItemsList = mbSupplierOrderItemService.mbSupplierOrderItemByOrderId(id);
        for (MbSupplierOrderItem m : orderItemsList) {
            mbSupplierOrderItemService.delete(m.getId());
        }
        delete(id);
    }

    @Override
    public List<MbSupplierOrder> query(MbSupplierOrder mbSupplierOrder) {
        List<MbSupplierOrder> ol = new ArrayList<MbSupplierOrder>();
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = " from TmbSupplierOrder t ";
        List<TmbSupplierOrder> l = mbSupplierOrderDao.find(hql + whereHql(mbSupplierOrder, params), params);
        if (l != null && l.size() > 0) {
            for (TmbSupplierOrder t : l) {
                MbSupplierOrder o = new MbSupplierOrder();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        return ol;
    }

    @Override
    public void add(MbSupplierOrder mbSupplierOrder) {
        TmbSupplierOrder t = new TmbSupplierOrder();
        BeanUtils.copyProperties(mbSupplierOrder, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbSupplierOrderDao.save(t);
        mbSupplierOrder.setId(t.getId());
    }

    @Override
    public MbSupplierOrder get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbSupplierOrder t = mbSupplierOrderDao.get("from TmbSupplierOrder t  where t.id = :id", params);
        MbSupplierOrder o = new MbSupplierOrder();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(MbSupplierOrder mbSupplierOrder) {
        TmbSupplierOrder t = mbSupplierOrderDao.get(TmbSupplierOrder.class, mbSupplierOrder.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbSupplierOrder, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbSupplierOrderDao.executeHql("update TmbSupplierOrder t set t.isdeleted = 1 where t.id = :id", params);
        //mbSupplierOrderDao.delete(mbSupplierOrderDao.get(TmbSupplierOrder.class, id));
    }

}
