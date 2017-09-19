package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbShopContactDaoI;
import com.mobian.model.TmbShopContact;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShopContact;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbShopContactServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbShopContactServiceImpl extends BaseServiceImpl<MbShopContact> implements MbShopContactServiceI {

	@Autowired
	private MbShopContactDaoI mbShopContactDao;

	@Override
	public DataGrid dataGrid(MbShopContact mbShopContact, PageHelper ph) {
		List<MbShopContact> ol = new ArrayList<MbShopContact>();
		String hql = " from TmbShopContact t ";
		DataGrid dg = dataGridQuery(hql, ph, mbShopContact, mbShopContactDao);
		@SuppressWarnings("unchecked")
		List<TmbShopContact> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbShopContact t : l) {
				MbShopContact o = new MbShopContact();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbShopContact mbShopContact, Map<String, Object> params) {
		String whereHql = "";	
		if (mbShopContact != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbShopContact.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbShopContact.getTenantId());
			}		
			if (!F.empty(mbShopContact.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbShopContact.getIsdeleted());
			}		
			if (!F.empty(mbShopContact.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", mbShopContact.getShopId());
			}		
			if (!F.empty(mbShopContact.getContactName())) {
				whereHql += " and t.contactName = :contactName";
				params.put("contactName", mbShopContact.getContactName());
			}		
			if (!F.empty(mbShopContact.getTelNumber())) {
				whereHql += " and t.telNumber = :telNumber";
				params.put("telNumber", mbShopContact.getTelNumber());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbShopContact mbShopContact) {
		TmbShopContact t = new TmbShopContact();
		BeanUtils.copyProperties(mbShopContact, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbShopContactDao.save(t);
	}

	@Override
	public MbShopContact get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbShopContact t = mbShopContactDao.get("from TmbShopContact t  where t.id = :id", params);
		MbShopContact o = new MbShopContact();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbShopContact mbShopContact) {
		TmbShopContact t = mbShopContactDao.get(TmbShopContact.class, mbShopContact.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbShopContact, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbShopContactDao.executeHql("update TmbShopContact t set t.isdeleted = 1 where t.id = :id",params);
		//mbShopContactDao.delete(mbShopContactDao.get(TmbShopContact.class, id));
	}

}
