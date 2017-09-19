package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbCouponsItemDaoI;
import com.mobian.model.TmbCouponsItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbCouponsItem;
import com.mobian.pageModel.MbItem;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbCouponsItemServiceI;
import com.mobian.service.MbItemServiceI;
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
public class MbCouponsItemServiceImpl extends BaseServiceImpl<MbCouponsItem> implements MbCouponsItemServiceI {

	@Autowired
	private MbCouponsItemDaoI mbCouponsItemDao;
	@Autowired
	private MbItemServiceI mbItemService;

	@Override
	public DataGrid dataGrid(MbCouponsItem mbCouponsItem, PageHelper ph) {
		List<MbCouponsItem> ol = new ArrayList<MbCouponsItem>();
		String hql = " from TmbCouponsItem t ";
		DataGrid dg = dataGridQuery(hql, ph, mbCouponsItem, mbCouponsItemDao);
		@SuppressWarnings("unchecked")
		List<TmbCouponsItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbCouponsItem t : l) {
				MbCouponsItem o = new MbCouponsItem();
				BeanUtils.copyProperties(t, o);
				fillCouponsItemInfo(o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	public void fillCouponsItemInfo(MbCouponsItem m) {
		fillItemInfo(m);
	}
	public void fillItemInfo(MbCouponsItem m) {
		MbItem item = mbItemService.get(m.getItemId());
		if (item != null) {
			m.setItemCode(item.getCode());
			m.setItemName(item.getName());
			m.setItemMarketPrice(item.getMarketPrice());
		}
	}

	protected String whereHql(MbCouponsItem mbCouponsItem, Map<String, Object> params) {
		String whereHql = "";	
		if (mbCouponsItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbCouponsItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbCouponsItem.getTenantId());
			}		
			if (!F.empty(mbCouponsItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbCouponsItem.getIsdeleted());
			}		
			if (!F.empty(mbCouponsItem.getCouponsId())) {
				whereHql += " and t.couponsId = :couponsId";
				params.put("couponsId", mbCouponsItem.getCouponsId());
			}		
			if (!F.empty(mbCouponsItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", mbCouponsItem.getItemId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbCouponsItem mbCouponsItem) {
		TmbCouponsItem t = new TmbCouponsItem();
		BeanUtils.copyProperties(mbCouponsItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbCouponsItemDao.save(t);
	}

	@Override
	public MbCouponsItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbCouponsItem t = mbCouponsItemDao.get("from TmbCouponsItem t  where t.id = :id", params);
		MbCouponsItem o = new MbCouponsItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbCouponsItem mbCouponsItem) {
		TmbCouponsItem t = mbCouponsItemDao.get(TmbCouponsItem.class, mbCouponsItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbCouponsItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbCouponsItemDao.executeHql("update TmbCouponsItem t set t.isdeleted = 1 where t.id = :id",params);
		//mbCouponsItemDao.delete(mbCouponsItemDao.get(TmbCouponsItem.class, id));
	}
	@Override
	public MbCouponsItem getByCouponsIdAndItemId(Integer couponsId, Integer itemId) {
		MbCouponsItem mbCouponsItem = new MbCouponsItem();
		mbCouponsItem.setCouponsId(couponsId);
		mbCouponsItem.setItemId(itemId);
		return listCouponsItem(mbCouponsItem).get(0);
	}
	@Override
	public List<MbCouponsItem> listCouponsItem(MbCouponsItem m) {
		return listCouponsItem(m, 100);
	}
	@Override
	public List<MbCouponsItem> listCouponsItem(MbCouponsItem m, Integer rows) {
		return listCouponsItem(m, 0, rows);
	}
	@Override
	public List<MbCouponsItem> listCouponsItem(MbCouponsItem m, Integer page, Integer rows) {
		String hql = " from TmbCouponsItem t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(m, params);
		List<MbCouponsItem> ol = new ArrayList<MbCouponsItem>();
		List<TmbCouponsItem> l = mbCouponsItemDao.find(hql + whereHql, params, page, rows);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbCouponsItem t : l
				 ) {
				MbCouponsItem o = new MbCouponsItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
