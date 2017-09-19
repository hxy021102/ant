package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbProblemTrackItemDaoI;
import com.mobian.model.TmbProblemTrackItem;
import com.mobian.pageModel.*;
import com.mobian.service.MbProblemTrackItemServiceI;
import com.mobian.service.MbProblemTrackServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbProblemTrackItemServiceImpl extends BaseServiceImpl<MbProblemTrackItem> implements MbProblemTrackItemServiceI {

    @Autowired
    private MbProblemTrackItemDaoI mbProblemTrackItemDao;
    @Autowired
    private MbProblemTrackServiceI mbProblemTrackService;
    @Autowired
    private UserServiceI userService;


    @Override
    public DataGrid dataGrid(MbProblemTrackItem mbProblemTrackItem, PageHelper ph) {
        List<MbProblemTrackItem> ol = new ArrayList<MbProblemTrackItem>();
        String hql = " from TmbProblemTrackItem t ";
        DataGrid dg = dataGridQuery(hql, ph, mbProblemTrackItem, mbProblemTrackItemDao);
        @SuppressWarnings("unchecked")
        List<TmbProblemTrackItem> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbProblemTrackItem t : l) {
                MbProblemTrackItem o = new MbProblemTrackItem();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }

    public DataGrid dataGridWithSetName(MbProblemTrackItem mbProblemTrackItem, PageHelper ph) {
        DataGrid grid = dataGrid(mbProblemTrackItem, ph);
        List<MbProblemTrackItem> mbProblemTrackItemList = grid.getRows();
        for (MbProblemTrackItem item : mbProblemTrackItemList) {
            if (item.getOwnerId() != null) {
                User user = userService.getFromCache(item.getOwnerId());
                if (user != null) {
                    item.setOwnerId(user.getName());
                }
            }
            if (item.getLastOwnerId() != null) {
                User user = userService.getFromCache(item.getLastOwnerId());
                if (user != null) {
                    item.setLastOwnerId(user.getName());
                }
            }
            if(item.getContent()!=null&&item.getContent().length()>=5){
                String content=item.getContent().substring(0,4);
                item.setContent(content+"......");
            }
        }
        return grid;
    }

    public void addProblemTrackAndUpdateStatus(MbProblemTrackItem mbProblemTrackItem) {
        if (mbProblemTrackItem.getProblemOrderId() != null) {
            MbProblemTrack mbProblemTrack = mbProblemTrackService.get(mbProblemTrackItem.getProblemOrderId());
            if (mbProblemTrackItem.getOwnerId() != "" || mbProblemTrackItem.getOwnerId() != null) {
                mbProblemTrack.setOwnerId(mbProblemTrackItem.getOwnerId());
                mbProblemTrackService.edit(mbProblemTrack);
            }
            if ("KK02".equals(mbProblemTrackItem.getStatus())) {
                if (mbProblemTrackItem.getLastOwnerId() != null) {
                    mbProblemTrack.setOwnerId(mbProblemTrackItem.getLastOwnerId());
                }
                mbProblemTrack.setStatus(mbProblemTrackItem.getStatus());
                mbProblemTrackService.edit(mbProblemTrack);
            }
        }
        add(mbProblemTrackItem);
    }

    protected String whereHql(MbProblemTrackItem mbProblemTrackItem, Map<String, Object> params) {
        String whereHql = "";
        if (mbProblemTrackItem != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbProblemTrackItem.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbProblemTrackItem.getTenantId());
            }
            if (!F.empty(mbProblemTrackItem.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbProblemTrackItem.getIsdeleted());
            }
            if (!F.empty(mbProblemTrackItem.getContent())) {
                whereHql += " and t.content = :content";
                params.put("content", mbProblemTrackItem.getContent());
            }
            if (!F.empty(mbProblemTrackItem.getFile())) {
                whereHql += " and t.file = :file";
                params.put("file", mbProblemTrackItem.getFile());
            }
            if (!F.empty(mbProblemTrackItem.getOwnerId())) {
                whereHql += " and t.ownerId = :ownerId";
                params.put("ownerId", mbProblemTrackItem.getOwnerId());
            }
            if (!F.empty(mbProblemTrackItem.getLastOwnerId())) {
                whereHql += " and t.lastOwnerId = :lastOwnerId";
                params.put("lastOwnerId", mbProblemTrackItem.getLastOwnerId());
            }
            if (!F.empty(mbProblemTrackItem.getProblemOrderId())) {
                whereHql += " and t.problemOrderId = :problemOrderId";
                params.put("problemOrderId", mbProblemTrackItem.getProblemOrderId());
            }
        }
        return whereHql;
    }

    @Override
    public void add(MbProblemTrackItem mbProblemTrackItem) {
        TmbProblemTrackItem t = new TmbProblemTrackItem();
        BeanUtils.copyProperties(mbProblemTrackItem, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbProblemTrackItemDao.save(t);
    }

    @Override
    public MbProblemTrackItem get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbProblemTrackItem t = mbProblemTrackItemDao.get("from TmbProblemTrackItem t  where t.id = :id", params);
        MbProblemTrackItem o = new MbProblemTrackItem();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(MbProblemTrackItem mbProblemTrackItem) {
        TmbProblemTrackItem t = mbProblemTrackItemDao.get(TmbProblemTrackItem.class, mbProblemTrackItem.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbProblemTrackItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbProblemTrackItemDao.executeHql("update TmbProblemTrackItem t set t.isdeleted = 1 where t.id = :id", params);
        //mbProblemTrackItemDao.delete(mbProblemTrackItemDao.get(TmbProblemTrackItem.class, id));
    }

}
