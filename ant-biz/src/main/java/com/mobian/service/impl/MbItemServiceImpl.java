package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbItemDaoI;
import com.mobian.model.TmbItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbItem;
import com.mobian.pageModel.MbItemCategory;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbItemCategoryServiceI;
import com.mobian.service.MbItemServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbItemServiceImpl extends BaseServiceImpl<MbItem> implements MbItemServiceI {

	@Autowired
	private MbItemDaoI mbItemDao;

	@Resource
	private MbItemCategoryServiceI categoryService;

	@Override
	public DataGrid dataGrid(MbItem mbItem, PageHelper ph) {
		List<MbItem> ol = new ArrayList<MbItem>();
		String hql = " from TmbItem t ";
		DataGrid dg = dataGridQuery(hql, ph, mbItem, mbItemDao);
		@SuppressWarnings("unchecked")
		List<TmbItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbItem t : l) {
				MbItem o = new MbItem();
				BeanUtils.copyProperties(t, o);

				if (t.getCategoryId() != null) {
					MbItemCategory category = categoryService.getFromCache(t.getCategoryId());
					if (category != null) {
						o.setCategoryName(category.getName());
					}
				}

				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbItem mbItem, Map<String, Object> params) {
		String whereHql = "";	
		if (mbItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbItem.getTenantId());
			}		
			if (!F.empty(mbItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbItem.getIsdeleted());
			}		
			if (!F.empty(mbItem.getCode())) {
				whereHql += " and t.code = :code";
				params.put("code", mbItem.getCode());
			}		
			if (!F.empty(mbItem.getName())) {
				whereHql += " and t.name LIKE :name";
				params.put("name", "%" + mbItem.getName() + "%");
			}		
			if (!F.empty(mbItem.getCategoryId())) {
				whereHql += " and t.categoryId = :categoryId";
				params.put("categoryId", mbItem.getCategoryId());
			}		

			if (!F.empty(mbItem.getMarketPrice())) {
				whereHql += " and t.marketPrice = :marketPrice";
				params.put("marketPrice", mbItem.getMarketPrice());
			}
			if (mbItem.getQuantity() != null) {
				whereHql += " and t.quantity > :quantity";
				params.put("quantity", mbItem.getQuantity());
			}

			if(!F.empty(mbItem.getKeyword())){
				whereHql += " and (t.name LIKE :name or code like:code)";
				params.put("name", "%" + mbItem.getKeyword() + "%");
				params.put("code",mbItem.getKeyword() + "%");
			}
			if (!F.empty(mbItem.getIsShelves())) {
				whereHql += " and t.isShelves = :isShelves";
				params.put("isShelves", mbItem.getIsShelves());
			}
			if (!F.empty(mbItem.getIspack())) {
				whereHql += " and t.ispack = :ispack";
				params.put("ispack", mbItem.getIspack());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbItem mbItem) {
		TmbItem t = new TmbItem();
		BeanUtils.copyProperties(mbItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbItemDao.save(t);
	}

	@Override
	public MbItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbItem t = mbItemDao.get("from TmbItem t  where t.id = :id", params);
		MbItem o = new MbItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public MbItem getFromCache(Integer id) {
		TmbItem source = mbItemDao.getById(id);
		MbItem target = new MbItem();
		BeanUtils.copyProperties(source, target);
		return target;
	}

	@Override
	public void edit(MbItem mbItem) {
		TmbItem t = mbItemDao.get(TmbItem.class, mbItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbItemDao.executeHql("update TmbItem t set t.isdeleted = 1 where t.id = :id",params);
		//mbItemDao.delete(mbItemDao.get(TmbItem.class, id));
	}

	@Override
	public boolean isItemExists(MbItem mbItem) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", mbItem.getCode());
		List<TmbItem> itemList = mbItemDao.find("from TmbItem t where t.code = :code", params);
		if (itemList.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public int reduceItemCount(Integer id, Integer count) {
		return mbItemDao.executeHql("update TmbItem t set t.quantity = t.quantity-" + count + " where t.id = " + id + " and t.quantity >= " + count + " for update");
	}

	@Override
	public List<MbItem> query(MbItem mbItem) {
		List<MbItem> ol = new ArrayList<MbItem>();
		String hql = " from TmbItem t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(mbItem, params);
		List<TmbItem> l = mbItemDao.find(hql  + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbItem t : l) {
				MbItem o = new MbItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
