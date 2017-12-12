package com.mobian.service.impl;

import com.bx.ant.pageModel.DeliverOrderShopItem;
import com.bx.ant.service.DeliverOrderShopItemServiceI;
import com.mobian.absx.F;
import com.mobian.dao.MbContractItemDaoI;
import com.mobian.model.TmbContractItem;
import com.mobian.pageModel.*;
import com.mobian.service.MbContractItemServiceI;
import com.mobian.service.MbContractServiceI;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.MyBeanUtils;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MbContractItemServiceImpl extends BaseServiceImpl<MbContractItem> implements MbContractItemServiceI {

    @Autowired
    private MbContractItemDaoI mbContractItemDao;

    @Autowired
    private MbItemServiceI mbItemService;
    @Autowired
    private MbContractServiceI mbContractService;
    @Autowired
    private MbShopServiceI mbShopService;
    @javax.annotation.Resource
    private DeliverOrderShopItemServiceI deliverOrderShopItemService;

    @Override
    public DataGrid dataGrid(MbContractItem mbContractItem, PageHelper ph) {
        List<MbContractItem> ol = new ArrayList<MbContractItem>();
        String hql = " from TmbContractItem t ";
        DataGrid dg = dataGridQuery(hql, ph, mbContractItem, mbContractItemDao);
        @SuppressWarnings("unchecked")
        List<TmbContractItem> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbContractItem t : l) {
                MbContractItem o = new MbContractItem();
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

    public void updateBatchContractPrice(String mbContractItemList, Integer newPrice) {
        JSONArray json = JSONArray.fromObject(mbContractItemList);
        if (!F.empty(mbContractItemList)) {
            //把json字符串转换成对象
            List<MbContractItem> itemList = (List<MbContractItem>) JSONArray.toCollection(json, MbContractItem.class);
            for (MbContractItem item : itemList) {
                item.setPrice(newPrice);
                editAndHistory(item);
            }
        }
    }

    protected String whereHql(MbContractItem mbContractItem, Map<String, Object> params) {
        String whereHql = "";
        if (mbContractItem != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbContractItem.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbContractItem.getTenantId());
            }
            if (!F.empty(mbContractItem.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbContractItem.getIsdeleted());
            }
            if (!F.empty(mbContractItem.getContractId())) {
                whereHql += " and t.contractId = :contractId";
                params.put("contractId", mbContractItem.getContractId());
            }
            if (!F.empty(mbContractItem.getItemId())) {
                whereHql += " and t.itemId = :itemId";
                params.put("itemId", mbContractItem.getItemId());
            }
            if (!F.empty(mbContractItem.getPrice())) {
                whereHql += " and t.price = :price";
                params.put("price", mbContractItem.getPrice());
            }
            if (mbContractItem.getContractIds() != null && mbContractItem.getContractIds().length > 0) {
                whereHql += " and t.contractId in(:contractIds)";
                params.put("contractIds", mbContractItem.getContractIds());
            }
            if (mbContractItem instanceof MbContractItemQuery) {
                MbContractItemQuery mbContractItemQuery = (MbContractItemQuery) mbContractItem;
                if (mbContractItemQuery.getItemIds() != null && mbContractItemQuery.getItemIds().length > 0) {
                    whereHql += " and t.itemId in(:itemIds)";
                    params.put("itemIds", mbContractItemQuery.getItemIds());
                }
            }
        }
        return whereHql;
    }

    @Override
    public void add(MbContractItem mbContractItem) {
        TmbContractItem t = new TmbContractItem();
        BeanUtils.copyProperties(mbContractItem, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbContractItemDao.save(t);
    }

    @Override
    public MbContractItem get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbContractItem t = mbContractItemDao.get("from TmbContractItem t  where t.id = :id", params);
        MbContractItem o = new MbContractItem();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    public DataGrid queryContractItem(MbContractItem mbContractItem, PageHelper ph) {
        List<MbContractItem> ol = new ArrayList<MbContractItem>();
        String hql = " from TmbContractItem t ";
        if ("shopName".equals(ph.getSort())) {
            ph.setSort(null);
        }
        DataGrid dg = dataGridQuery(hql, ph, mbContractItem, mbContractItemDao);
        @SuppressWarnings("unchecked")
        List<TmbContractItem> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbContractItem t : l) {
                MbContractItem o = new MbContractItem();
                BeanUtils.copyProperties(t, o);
                if (o.getContractId() != null) {
                    MbContract mbContract = mbContractService.getFromCache(o.getContractId());
                    if (mbContract.getValid() == true&&mbContract.getIsdeleted()==false) {
                        o.setCode(mbContract.getCode());
                        o.setExpiryDateStart(mbContract.getExpiryDateStart());
                        o.setExpiryDateEnd(mbContract.getExpiryDateEnd());
                        if (mbContract.getShopId() != null) {
                            MbShop shop = mbShopService.getFromCache(mbContract.getShopId());
                            if (shop != null) {
                                o.setShopName(shop.getName());
                            }
                        }
                    } else {
                        continue;
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
        if (!"price".equals(ph.getSort())) {
            Collections.sort(ol, new Comparator<MbContractItem>() {
                @Override
                public int compare(MbContractItem o1, MbContractItem o2) {
                    return o1.getShopName().compareTo(o2.getShopName());
                }
            });
        }
        dg.setRows(ol);
        return dg;
    }

    @Override
    public void edit(MbContractItem mbContractItem) {
        TmbContractItem t = mbContractItemDao.get(TmbContractItem.class, mbContractItem.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbContractItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void editAndHistory(MbContractItem mbContractItem) {
        TmbContractItem t = mbContractItemDao.get(TmbContractItem.class, mbContractItem.getId());
        TmbContractItem old = new TmbContractItem();
        BeanUtils.copyProperties(t, old, new String[]{"id", "addtime", "isdeleted", "updatetime"});
        old.setIsdeleted(true);
        mbContractItemDao.save(old);
        if (t != null) {
            MyBeanUtils.copyProperties(mbContractItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void addBatchAndOverride(List<MbContractItem> mbContractItemList) {
        for (MbContractItem mbContractItem : mbContractItemList) {
            MbContractItem request = new MbContractItem();
            request.setContractId(mbContractItem.getContractId());
            request.setItemId(mbContractItem.getItemId());
            List<TmbContractItem> list = query("from TmbContractItem t ", request, mbContractItemDao);
            if (CollectionUtils.isNotEmpty(list)) {
                TmbContractItem oldMbContractItem = list.get(0);
                mbContractItem.setId(oldMbContractItem.getId());
                editAndHistory(mbContractItem);
            } else {
                add(mbContractItem);
            }
        }

    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbContractItemDao.executeHql("update TmbContractItem t set t.isdeleted = 1 where t.id = :id", params);
        //mbContractItemDao.delete(mbContractItemDao.get(TmbContractItem.class, id));
    }

    @Override
    public List<TmbContractItem> queryMbContractItemByShopId(Integer contractId) {
        return mbContractItemDao.find("from TmbContractItem t where t.isdeleted = 0 and t.contractId =" + contractId);
    }

    @Override
    public List<MbContractItem> query(MbContractItem mbContractItem) {
        List<MbContractItem> ol = new ArrayList<MbContractItem>();
        String hql = " from TmbContractItem t ";
        Map<String, Object> params = new HashMap<String, Object>();
        String where = whereHql(mbContractItem, params);
        List<TmbContractItem> l = mbContractItemDao.find(hql + where, params);
        if (CollectionUtils.isNotEmpty(l)) {
            for (TmbContractItem t : l) {
                MbContractItem o = new MbContractItem();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        return ol;
    }
    @Override
    public List<MbContractItem> query(MbContractItem mbContractItem) {
        List<MbContractItem> ol = new ArrayList<MbContractItem>();
        String hql = " from TmbContractItem t ";
        Map<String, Object> params = new HashMap<String, Object>();
        String where = whereHql(mbContractItem, params);
        List<TmbContractItem> l = mbContractItemDao.find(hql + where, params);
        if (CollectionUtils.isNotEmpty(l)) {
            for (TmbContractItem t : l) {
                MbContractItem o = new MbContractItem();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        return ol;
    }

    @Override
    public List<MbItemView> getItemListWidthPriceAndQuantity(String DeliverOrderShopIds, Integer shopId) {
        Map<Integer, DeliverOrderShopItem> deliverOrderShopItemMap = deliverOrderShopItemService.queryOrderShopItem(DeliverOrderShopIds);
        if (CollectionUtils.isNotEmpty(deliverOrderShopItemMap.values())) {
            Integer[] itemIds = new Integer[new ArrayList<DeliverOrderShopItem>(deliverOrderShopItemMap.values()).size()];
            int i = 0;
            for (DeliverOrderShopItem deliverOrderShopItem : new ArrayList<DeliverOrderShopItem>(deliverOrderShopItemMap.values())) {
                itemIds[i++] = deliverOrderShopItem.getItemId();
            }
            MbItemQuery mbItemQuery = new MbItemQuery();
            mbItemQuery.setItemIds(itemIds);
            //获取所对应的商品列表
            List<MbItem> mbItemList = mbItemService.query(mbItemQuery);
            if (CollectionUtils.isNotEmpty(mbItemList)) {
                Map<Integer, MbContractItem> map = new HashMap<Integer, MbContractItem>();
                MbContract mbContract = mbContractService.getNewMbContract(shopId);
                if (mbContract != null) {
                    MbContractItemQuery mbContractItemQuery = new MbContractItemQuery();
                    mbContractItemQuery.setItemIds(itemIds);
                    mbContractItemQuery.setContractId(mbContract.getId());
                    if (mbContract != null) {
                        List<MbContractItem> mbContractItemList = query(mbContractItemQuery);
                        if (CollectionUtils.isNotEmpty(mbContractItemList)) {
                            for (MbContractItem mbContractItem : mbContractItemList) {
                                map.put(mbContractItem.getItemId(), mbContractItem);
                            }
                        }
                    }
                }
                //如果门店跟公司签订了合同价，怎按合同价格设置购买价格，否则设置为市场价格
                List<MbItemView> mbItemViewList = new ArrayList<MbItemView>();
                for (MbItem mbItem : mbItemList) {
                    MbItemView mbItemView = new MbItemView();
                    BeanUtils.copyProperties(mbItem, mbItemView);
                    if (map.get(mbItemView.getId()) != null) {
                        mbItemView.setBuyPrice(map.get(mbItem.getId()).getPrice());
                    } else {
                        mbItemView.setBuyPrice(mbItem.getMarketPrice());
                    }
                    mbItemViewList.add(mbItemView);
                }
                //设置购买数量
                if (CollectionUtils.isNotEmpty(mbItemViewList)) {
                    for (MbItemView mbItem : mbItemViewList) {
                        mbItem.setQuantity(deliverOrderShopItemMap.get(mbItem.getId()).getQuantity());
                        mbItem.setItemId(mbItem.getId());
                        mbItem.setId(null);
                    }
                    return mbItemViewList;
                }
            }
        }
        return null;
    }
}
