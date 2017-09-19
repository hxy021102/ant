package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierOrderItemDaoI;
import com.mobian.model.TmbSupplierOrderItem;
import com.mobian.model.TmbSupplierStockInItem;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MbSupplierOrderItemServiceImpl extends BaseServiceImpl<MbSupplierOrderItem> implements MbSupplierOrderItemServiceI {

    @Autowired
    private MbSupplierOrderItemDaoI mbSupplierOrderItemDao;
    @Autowired
    private MbSupplierOrderServiceI mbSupplierOrderService;
    @Autowired
    private MbItemServiceI mbItemService;
    @Autowired
    private MbSupplierStockInServiceI mbSupplierStockInService;
    @Autowired
    private MbSupplierStockInItemServiceI mbSupplierStockInItemService;

    @Override
    public DataGrid dataGrid(MbSupplierOrderItem mbSupplierOrderItem, PageHelper ph) {
        List<MbSupplierOrderItem> ol = new ArrayList<MbSupplierOrderItem>();
        String hql = " from TmbSupplierOrderItem t ";
        DataGrid dg = dataGridQuery(hql, ph, mbSupplierOrderItem, mbSupplierOrderItemDao);
        @SuppressWarnings("unchecked")
        List<TmbSupplierOrderItem> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbSupplierOrderItem t : l) {
                MbSupplierOrderItem o = new MbSupplierOrderItem();
                BeanUtils.copyProperties(t, o);
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


    protected String whereHql(MbSupplierOrderItem mbSupplierOrderItem, Map<String, Object> params) {
        String whereHql = "";
        if (mbSupplierOrderItem != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbSupplierOrderItem.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbSupplierOrderItem.getTenantId());
            }
            if (!F.empty(mbSupplierOrderItem.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbSupplierOrderItem.getIsdeleted());
            }
            if (!F.empty(mbSupplierOrderItem.getSupplierOrderId())) {
                whereHql += " and t.supplierOrderId = :supplierOrderId";
                params.put("supplierOrderId", mbSupplierOrderItem.getSupplierOrderId());
            }
            if (!F.empty(mbSupplierOrderItem.getItemId())) {
                whereHql += " and t.itemId = :itemId";
                params.put("itemId", mbSupplierOrderItem.getItemId());
            }
            if (!F.empty(mbSupplierOrderItem.getQuantity())) {
                whereHql += " and t.quantity = :quantity";
                params.put("quantity", mbSupplierOrderItem.getQuantity());
            }
            if (!F.empty(mbSupplierOrderItem.getPrice())) {
                whereHql += " and t.price = :price";
                params.put("price", mbSupplierOrderItem.getPrice());
            }
        }
        return whereHql;
    }

    public Integer getTotalPrice(Integer Id) {
        List<TmbSupplierOrderItem> orderItemList = mbSupplierOrderItemDao.find("from TmbSupplierOrderItem where supplierOrderId = " + Id);
        Integer totalPrice = 0;
        for (TmbSupplierOrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getPrice() * orderItem.getQuantity() * 100;
        }
        return totalPrice;
    }

    public List<MbSupplierOrderItem> mbSupplierOrderItemByOrderId(Integer id) {
        List<MbSupplierOrderItem> ol = new ArrayList<MbSupplierOrderItem>();
        List<TmbSupplierOrderItem> orderItemList = mbSupplierOrderItemDao.find("from TmbSupplierOrderItem where supplierOrderId = " + id);
        for (TmbSupplierOrderItem t : orderItemList) {
            MbSupplierOrderItem o = new MbSupplierOrderItem();
            BeanUtils.copyProperties(t, o);
            ol.add(o);
        }
        return ol;
    }

    @Override
    public void add(MbSupplierOrderItem mbSupplierOrderItem) {
        TmbSupplierOrderItem t = new TmbSupplierOrderItem();
        BeanUtils.copyProperties(mbSupplierOrderItem, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbSupplierOrderItemDao.save(t);
    }

    @Override
    public MbSupplierOrderItem get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbSupplierOrderItem t = mbSupplierOrderItemDao.get("from TmbSupplierOrderItem t  where t.id = :id", params);
        MbSupplierOrderItem o = new MbSupplierOrderItem();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(MbSupplierOrderItem mbSupplierOrderItem) {
        TmbSupplierOrderItem t = mbSupplierOrderItemDao.get(TmbSupplierOrderItem.class, mbSupplierOrderItem.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbSupplierOrderItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbSupplierOrderItemDao.executeHql("update TmbSupplierOrderItem t set t.isdeleted = 1 where t.id = :id", params);
        //mbSupplierOrderItemDao.delete(mbSupplierOrderItemDao.get(TmbSupplierOrderItem.class, id));
    }

    /**
     * 转换名字
     */
    public String conversion(MbSupplierOrderItem mbSupplierOrderItem) {
        Integer id = mbSupplierOrderItem.getItemId();
        MbItem mbItem = mbItemService.get(id);
        return mbItem.getName();

    }

    public List<MbSupplierOrderItem> query(MbSupplierOrderItem mbSupplierOrderItem) {
        List<MbSupplierOrderItem> ol = new ArrayList<MbSupplierOrderItem>();
        String hql = " from TmbSupplierOrderItem t ";
        Map<String, Object> params = new HashMap<String, Object>();
        String where = whereHql(mbSupplierOrderItem, params);
        List<TmbSupplierOrderItem> l = mbSupplierOrderItemDao.find(hql + where, params);
        if (CollectionUtils.isNotEmpty(l)) {
            for (TmbSupplierOrderItem t : l) {
                MbSupplierOrderItem o = new MbSupplierOrderItem();
                BeanUtils.copyProperties(t, o);
                Integer id = o.getItemId();
                MbItem mbItem = mbItemService.get(id);
                String name = mbItem.getName();
                o.setItemName(name);
                ol.add(o);
            }
        }
        return ol;
    }

    public DataGrid dataGridSupplierOrderItemWithStockQuantity(MbSupplierOrderItem mbSupplierOrderItem, PageHelper ph) {
        DataGrid dataGrid = dataGrid(mbSupplierOrderItem, ph);
        List<MbSupplierOrderItem> mbSupplierOrderItemList = dataGrid.getRows();
        Integer supplierOrderId = mbSupplierOrderItem.getSupplierOrderId();
        if (supplierOrderId != null) {
            List<MbSupplierStockIn> mbSupplierStockIns=mbSupplierStockInService.getMbSupplierStockInListByOrderId(supplierOrderId);
            if(!CollectionUtils.isEmpty(mbSupplierStockIns)){
               List<TmbSupplierStockInItem> stockInItemList=new ArrayList<>();
               for(MbSupplierStockIn stockIn : mbSupplierStockIns){
                   List<TmbSupplierStockInItem> tmbSupplierStockInItemList = mbSupplierStockInItemService.getStockInItemListByStockInId(stockIn.getId());
                   if(!CollectionUtils.isEmpty(tmbSupplierStockInItemList)) {
                       for (TmbSupplierStockInItem stockInItem : tmbSupplierStockInItemList) {
                           stockInItemList.add(stockInItem);
                       }
                   }
               }
               Map<Integer,Integer> map=new HashMap<Integer, Integer>();
                for(TmbSupplierStockInItem item :stockInItemList){
                    Integer itemValue=map.get(item.getItemId());
                    if(itemValue==null){
                        map.put(item.getItemId(),item.getQuantity());
                    }else{
                        map.put(item.getItemId(),itemValue +=item.getQuantity());
                    }
                }
                for(Integer itemId :map.keySet()){
                    for (MbSupplierOrderItem order : mbSupplierOrderItemList) {
                        if(itemId.equals(order.getItemId())){
                            order.setWarehouseQuantity(map.get(itemId));
                        }
                    }
                }
            }
            for (MbSupplierOrderItem order : mbSupplierOrderItemList){
                order.setSumPrice(order.getQuantity() * order.getPrice());
            }
        }
        return dataGrid;
    }

    @Override
    public DataGrid dataGridReport(MbSupplierOrderReport supplierOrderReport) {

        DataGrid dataGrid = new DataGrid();
        List<MbSupplierOrderReport> ol = new ArrayList<MbSupplierOrderReport>();

        MbSupplierStockIn stockIn = new MbSupplierStockIn();
        stockIn.setStockinTimeBegin(supplierOrderReport.getStartDate());
        stockIn.setStockinTimeEnd(supplierOrderReport.getEndDate());
        List<MbSupplierStockIn> stockIns = mbSupplierStockInService.query(stockIn);
        if(CollectionUtils.isNotEmpty(stockIns)) {
            List<Integer> itemIds = new ArrayList<Integer>(); // 商品查询条件
            if(!F.empty(supplierOrderReport.getItemIds())) {
                String[] itemIdList = supplierOrderReport.getItemIds().split(",");
                for(int i=0; i<itemIdList.length; i++) {
                    if(!F.empty(itemIdList[i]))
                        itemIds.add(Integer.valueOf(itemIdList[i]));
                }
            }

            Integer[] supplierOrderIds = new Integer[stockIns.size()];
            for(int i=0; i<stockIns.size(); i++) {
                supplierOrderIds[i] = stockIns.get(i).getSupplierOrderId();
            }

            List<MbSupplierOrderItem> orderItems = queryListByOrderIds(supplierOrderIds);

            Map<Integer, MbSupplierOrderReport> map = new HashMap<Integer, MbSupplierOrderReport>();
            int totalQuantity = 0;
            for(MbSupplierOrderItem item : orderItems){
                if(CollectionUtils.isNotEmpty(itemIds) && !itemIds.contains(item.getItemId())) continue;
                MbSupplierOrderReport r = map.get(item.getItemId());
                if(r == null) {
                    r = new MbSupplierOrderReport();
                    r.setItemId(item.getItemId());
                    r.setItemName(item.getItemName());
                    r.setItemCode(item.getItemCode());
                    r.setQuantity(item.getQuantity());
                } else {
                    r.setQuantity(r.getQuantity() + item.getQuantity());
                }
                map.put(item.getItemId(), r);

                totalQuantity += item.getQuantity();
            }

            Integer[] stockInIds = new Integer[stockIns.size()];
            for(int i=0; i<stockIns.size(); i++) {
                stockInIds[i] = stockIns.get(i).getId();
            }

            List<MbSupplierStockInItem> stockInItems = mbSupplierStockInItemService.getListByStockInIds(stockInIds);
            int totalStockInQuantity = 0, totalPrice = 0;
            for(MbSupplierStockInItem stockInItem : stockInItems) {
                if(CollectionUtils.isNotEmpty(itemIds) && !itemIds.contains(stockInItem.getItemId())) continue;
                MbSupplierOrderReport r = map.get(stockInItem.getItemId());
                int quantity = stockInItem.getQuantity() == null ? 0 : stockInItem.getQuantity();
                if(r != null) {
                    if(r.getStockInQuantity() == null) {
                        r.setStockInQuantity(quantity);
                        r.setTotalPrice(quantity*stockInItem.getPrice());
                    } else {
                        r.setStockInQuantity(r.getStockInQuantity() + quantity);
                        r.setTotalPrice(r.getTotalPrice() + quantity*stockInItem.getPrice());
                    }
                }
                totalStockInQuantity += quantity;
                totalPrice += quantity*stockInItem.getPrice();
            }

            for(Integer itemId : map.keySet()) {
                ol.add(map.get(itemId));
            }

            Collections.sort(ol, new Comparator<MbSupplierOrderReport>() {
                public int compare(MbSupplierOrderReport arg0, MbSupplierOrderReport arg1) {
                    return arg1.getQuantity().compareTo(arg0.getQuantity());
                }
            });

            // 合计
            if(CollectionUtils.isNotEmpty(ol)) {
                MbSupplierOrderReport totalRow = new MbSupplierOrderReport();
                totalRow.setItemCode("合计");
                totalRow.setQuantity(totalQuantity);
                totalRow.setStockInQuantity(totalStockInQuantity);
                totalRow.setTotalPrice(totalPrice);
                ol.add(totalRow);
            }
        }

        dataGrid.setRows(ol);

        return dataGrid;
    }

    public List<MbSupplierOrderItem> queryListByOrderIds(Integer[] orderIds) {
        List<MbSupplierOrderItem> ol = new ArrayList<MbSupplierOrderItem>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("alist", orderIds);
        List<TmbSupplierOrderItem> l = mbSupplierOrderItemDao.find("from TmbSupplierOrderItem t where t.isdeleted = 0 and t.supplierOrderId in (:alist)", params);

        if (CollectionUtils.isNotEmpty(l)) {
            for (TmbSupplierOrderItem t : l) {
                MbSupplierOrderItem o = new MbSupplierOrderItem();
                BeanUtils.copyProperties(t, o);
                Integer id = o.getItemId();
                MbItem mbItem = mbItemService.getFromCache(id);
                o.setItemName(mbItem.getName());
                o.setItemCode(mbItem.getCode());
                ol.add(o);
            }
        }
        return ol;
    }
}
