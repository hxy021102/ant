package com.mobian.service.impl;

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

}
