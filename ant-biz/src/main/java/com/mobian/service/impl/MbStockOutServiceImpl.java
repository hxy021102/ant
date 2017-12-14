package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbStockOutDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbStockOut;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.MyBeanUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbStockOutServiceImpl extends BaseServiceImpl<MbStockOut> implements MbStockOutServiceI {

    @Autowired
    private MbStockOutDaoI mbStockOutDao;
    @Autowired
    private MbStockOutItemServiceI mbStockOutItemService;
    @Autowired
    private MbStockOutOrderServiceI mbStockOutOrderService;
    @Autowired
    private UserServiceI userService;
    @Autowired
    private MbItemStockServiceI mbItemStockService;
    @Autowired
    private MbWarehouseServiceI mbWarehouseService;
    @Autowired
    private MbItemServiceI mbItemService;

    @Override
    public DataGrid dataGrid(MbStockOut mbStockOut, PageHelper ph) {
        List<MbStockOut> ol = new ArrayList<MbStockOut>();
        String hql = " from TmbStockOut t ";
        DataGrid dg = dataGridQuery(hql, ph, mbStockOut, mbStockOutDao);
        @SuppressWarnings("unchecked")
        List<TmbStockOut> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbStockOut t : l) {
                MbStockOut o = new MbStockOut();
                BeanUtils.copyProperties(t, o);
                if (o.getStockOutPeopleId() != null) {
                    User user = userService.getFromCache(o.getStockOutPeopleId());
                    if (user != null) {
                        o.setStockOutPeopleName(user.getNickname());
                    }
                }
                if (o.getLoginId() != null){
                    User user = userService.getFromCache(o.getLoginId());
                    if (user != null) {
                        o.setLoginName(user.getName());
                    }
                }
                if (o.getWarehouseId() != null) {
                    MbWarehouse mbWarehouse = mbWarehouseService.get(o.getWarehouseId());
                    o.setWarehouseName(mbWarehouse.getName());
                }

                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }


    protected String whereHql(MbStockOut mbStockOut, Map<String, Object> params) {
        String whereHql = "";
        if (mbStockOut != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbStockOut.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbStockOut.getTenantId());
            }
            if (!F.empty(mbStockOut.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbStockOut.getIsdeleted());
            }
            if (!F.empty(mbStockOut.getStockOutPeopleId())) {
                whereHql += " and t.stockOutPeopleId = :stockOutPeopleId";
                params.put("stockOutPeopleId", mbStockOut.getStockOutPeopleId());
            }
            if (!F.empty(mbStockOut.getRemark())) {
                whereHql += " and t.remark = :remark";
                params.put("remark", mbStockOut.getRemark());
            }
            if (mbStockOut.getStockOutTimeBegin() != null) {
                whereHql += " and t.addtime >= :stockOutTimeBegin";
                params.put("stockOutTimeBegin", mbStockOut.getStockOutTimeBegin());
            }
            if (mbStockOut.getStockOutTimeEnd() != null) {
                whereHql += " and t.addtime <= :stockOutTimeEnd";
                params.put("stockOutTimeEnd", mbStockOut.getStockOutTimeEnd());
            }
        }
        return whereHql;
    }

    @Override
    public void add(MbStockOut mbStockOut) {
        TmbStockOut t = new TmbStockOut();
        BeanUtils.copyProperties(mbStockOut, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbStockOutDao.save(t);
        mbStockOut.setId(t.getId());
    }

    @Override
    public void addStockOut(MbStockOut mbStockOut, String dataGrid) {
        add(mbStockOut);
        addStockOutItem(mbStockOut, dataGrid);
    }

    @Override
    public void addStockOut(MbStockOut mbStockOut, String dataGrid, String deliverOrderIds) {
        add(mbStockOut);
        addStockOutOrder(mbStockOut, dataGrid, deliverOrderIds);
    }

    private void addStockOutOrder(MbStockOut mbStockOut, String dataGrid, String deliverOrderIds) {
        net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(dataGrid);
        String[] deliverOrderIdArr = deliverOrderIds.split(",");
        for (int i = 0; i < deliverOrderIdArr.length; i++) {
            if(F.empty(deliverOrderIdArr[i])) continue;

            MbStockOutOrder mbStockOutOrder = new MbStockOutOrder();
            mbStockOutOrder.setMbStockOutId(mbStockOut.getId());
            mbStockOutOrder.setDeliverOrderId(Integer.valueOf(deliverOrderIdArr[i]));
            mbStockOutOrderService.add(mbStockOutOrder);
        }

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            MbStockOutItem mbStockOutItem = (MbStockOutItem) JSONObject.toBean(jsonObject, MbStockOutItem.class);

            Integer id = mbStockOut.getWarehouseId();
            Integer itemId = mbStockOutItem.getItemId();
            MbItem mbItem = mbItemService.getFromCache(itemId);
            if(mbItem == null) throw new ServiceException(String.format("商品不存在:%s",itemId));
            //添加出库项改变仓库库存
            MbItemStock mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(id, itemId);
            MbItemStock changeShop = new MbItemStock();
            changeShop.setId(mbItemStockShop.getId());
            changeShop.setAdjustment(mbStockOutItem.getQuantity()*(-1));
            changeShop.setLogType("SL03");
            changeShop.setReason(String.format("出库ID：%s出库，库存：%s", mbStockOut.getId(),mbItemStockShop.getQuantity()-mbStockOutItem.getQuantity()));
            mbItemStockService.editAndInsertLog(changeShop, mbStockOut.getLoginId());
        }

    }

    @Override
    public void addStockOutItem(MbStockOut mbStockOut, String dataGrid) {
        net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(dataGrid);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            MbStockOutItem mbStockOutItem = (MbStockOutItem) JSONObject.toBean(jsonObject, MbStockOutItem.class);
            mbStockOutItem.setMbStockOutId(mbStockOut.getId());
            mbStockOutItemService.add(mbStockOutItem);
            Integer id = mbStockOut.getWarehouseId();
            Integer itemId = mbStockOutItem.getItemId();
            MbItem mbItem = mbItemService.getFromCache(itemId);
            if(mbItem == null) throw new ServiceException(String.format("商品不存在:%s",itemId));
            //添加出库项改变仓库库存
            MbItemStock mbItemStockShop = mbItemStockService.getByWareHouseIdAndItemId(id, itemId);
            MbItemStock changeShop = new MbItemStock();
            changeShop.setId(mbItemStockShop.getId());
            changeShop.setAdjustment(mbStockOutItem.getQuantity()*(-1));
            changeShop.setLogType("SL03");
            changeShop.setReason(String.format("出库ID：%s出库，库存：%s", mbStockOut.getId(),mbItemStockShop.getQuantity()-mbStockOutItem.getQuantity()));
            mbItemStockService.editAndInsertLog(changeShop, mbStockOut.getLoginId());
        }

    }

    @Override
    public void deleteStockOutItem(Integer id) {
        MbStockOutItem mbStockOutItem = new MbStockOutItem();
        mbStockOutItem.setMbStockOutId(id);
        List<MbStockOutItem> list = mbStockOutItemService.queryStockOutItem(mbStockOutItem);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                mbStockOutItemService.delete(list.get(i).getId());
            }
        }

    }


    @Override
    public MbStockOut get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbStockOut t = mbStockOutDao.get("from TmbStockOut t  where t.id = :id", params);
        MbStockOut o = new MbStockOut();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(MbStockOut mbStockOut) {
        TmbStockOut t = mbStockOutDao.get(TmbStockOut.class, mbStockOut.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbStockOut, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbStockOutDao.executeHql("update TmbStockOut t set t.isdeleted = 1 where t.id = :id", params);
        //mbStockOutDao.delete(mbStockOutDao.get(TmbStockOut.class, id));
    }

}
