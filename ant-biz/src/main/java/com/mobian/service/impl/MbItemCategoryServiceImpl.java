package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbItemCategoryDaoI;
import com.mobian.model.TmbItemCategory;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbItemCategory;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbItemCategoryServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbItemCategoryServiceImpl extends BaseServiceImpl<MbItemCategory> implements MbItemCategoryServiceI {

	@Autowired
	private MbItemCategoryDaoI mbItemCategoryDao;

	@Override
	public DataGrid dataGrid(MbItemCategory mbItemCategory, PageHelper ph) {
		List<MbItemCategory> ol = new ArrayList<MbItemCategory>();
		String hql = " from TmbItemCategory t ";
		DataGrid dg = dataGridQuery(hql, ph, mbItemCategory, mbItemCategoryDao);
		@SuppressWarnings("unchecked")
		List<TmbItemCategory> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbItemCategory t : l) {
				MbItemCategory o = new MbItemCategory();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbItemCategory mbItemCategory, Map<String, Object> params) {
		String whereHql = "";	
		if (mbItemCategory != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbItemCategory.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbItemCategory.getTenantId());
			}		
			if (!F.empty(mbItemCategory.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbItemCategory.getIsdeleted());
			}		
			if (!F.empty(mbItemCategory.getCode())) {
				whereHql += " and t.code = :code";
				params.put("code", mbItemCategory.getCode());
			}		
			if (!F.empty(mbItemCategory.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", mbItemCategory.getName());
			}		
			if (!F.empty(mbItemCategory.getParentId())) {
				whereHql += " and t.parentId = :parentId";
				params.put("parentId", mbItemCategory.getParentId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbItemCategory mbItemCategory) {
		TmbItemCategory t = new TmbItemCategory();
		BeanUtils.copyProperties(mbItemCategory, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbItemCategoryDao.save(t);
	}

	@Override
	public MbItemCategory get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbItemCategory t = mbItemCategoryDao.get("from TmbItemCategory t  where t.id = :id", params);
		MbItemCategory o = new MbItemCategory();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public MbItemCategory getFromCache(Integer id) {
		TmbItemCategory t = mbItemCategoryDao.getById(id);
		MbItemCategory o = new MbItemCategory();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbItemCategory mbItemCategory) {
		TmbItemCategory t = mbItemCategoryDao.get(TmbItemCategory.class, mbItemCategory.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbItemCategory, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbItemCategoryDao.executeHql("update TmbItemCategory t set t.isdeleted = 1 where t.id = :id",params);
		//mbItemCategoryDao.delete(mbItemCategoryDao.get(TmbItemCategory.class, id));
	}

	@Override
	public boolean isItemCategoryExists(MbItemCategory mbItemCategory) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", mbItemCategory.getCode());
		List<TmbItemCategory> itemCategoryList = mbItemCategoryDao.find("from TmbItemCategory t where t.code = :code", params);
		if (itemCategoryList.size() == 0) {
			return false;
		}
		return true;
	}

}
