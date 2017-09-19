package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbUserAddressDaoI;
import com.mobian.model.TmbUserAddress;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbUserAddress;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbUserAddressServiceI;
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
public class MbUserAddressServiceImpl extends BaseServiceImpl<MbUserAddress> implements MbUserAddressServiceI {

	@Autowired
	private MbUserAddressDaoI mbUserAddressDao;

	@Override
	public DataGrid dataGrid(MbUserAddress mbUserAddress, PageHelper ph) {
		List<MbUserAddress> ol = new ArrayList<MbUserAddress>();
		String hql = " from TmbUserAddress t ";
		DataGrid dg = dataGridQuery(hql, ph, mbUserAddress, mbUserAddressDao);
		@SuppressWarnings("unchecked")
		List<TmbUserAddress> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbUserAddress t : l) {
				MbUserAddress o = new MbUserAddress();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbUserAddress mbUserAddress, Map<String, Object> params) {
		String whereHql = "";	
		if (mbUserAddress != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbUserAddress.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbUserAddress.getTenantId());
			}		
			if (!F.empty(mbUserAddress.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbUserAddress.getIsdeleted());
			}		
			if (!F.empty(mbUserAddress.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", mbUserAddress.getUserId());
			}		
			if (!F.empty(mbUserAddress.getRegionId())) {
				whereHql += " and t.regionId = :regionId";
				params.put("regionId", mbUserAddress.getRegionId());
			}
			if (!F.empty(mbUserAddress.getProvinceName())) {
				whereHql += " and t.provinceName = :provinceName";
				params.put("provinceName", mbUserAddress.getProvinceName());
			}
			if (!F.empty(mbUserAddress.getCityName())) {
				whereHql += " and t.cityName = :cityName";
				params.put("cityName", mbUserAddress.getCityName());
			}
			if (!F.empty(mbUserAddress.getCountyName())) {
				whereHql += " and t.countyName = :countyName";
				params.put("countyName", mbUserAddress.getCountyName());
			}
			if (!F.empty(mbUserAddress.getDetailInfo())) {
				whereHql += " and t.detailInfo = :detailInfo";
				params.put("detailInfo", mbUserAddress.getDetailInfo());
			}
			if (!F.empty(mbUserAddress.getUserName())) {
				whereHql += " and t.userName = :userName";
				params.put("userName", mbUserAddress.getUserName());
			}
			if (!F.empty(mbUserAddress.getTelNumber())) {
				whereHql += " and t.telNumber = :telNumber";
				params.put("telNumber", mbUserAddress.getTelNumber());
			}

		}	
		return whereHql;
	}

	@Override
	public void add(MbUserAddress mbUserAddress) {
		TmbUserAddress t = new TmbUserAddress();
		BeanUtils.copyProperties(mbUserAddress, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbUserAddressDao.save(t);
		mbUserAddress.setId(t.getId());
	}

	@Override
	public MbUserAddress get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbUserAddress t = mbUserAddressDao.get("from TmbUserAddress t  where t.id = :id", params);
		MbUserAddress o = new MbUserAddress();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public MbUserAddress getFromCache(Integer id) {
		TmbUserAddress source = mbUserAddressDao.getById(id);
		MbUserAddress target = new MbUserAddress();
		BeanUtils.copyProperties(source, target);
		return target;
	}

	@Override
	public void edit(MbUserAddress mbUserAddress) {
		TmbUserAddress t = mbUserAddressDao.get(TmbUserAddress.class, mbUserAddress.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbUserAddress, t, new String[]{"id", "addtime", "isdeleted", "updatetime", "userId"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbUserAddressDao.executeHql("update TmbUserAddress t set t.isdeleted = 1 where t.id = :id",params);
		//mbUserAddressDao.delete(mbUserAddressDao.get(TmbUserAddress.class, id));
	}

	@Override
	public MbUserAddress getDefaultAddress(Integer userId) {
		List<TmbUserAddress> l = mbUserAddressDao.find("from TmbUserAddress t where t.isdeleted = 0 and defaultAddress = 1 and userId=" + userId, 1, 1);
		if(l != null && l.size() > 0) {
			MbUserAddress o = new MbUserAddress();
			BeanUtils.copyProperties(l.get(0), o);
			return o;
		}
		return null;
	}

	@Override
	public void setDefaultAddress(MbUserAddress mbUserAddress) {
		mbUserAddressDao.executeHql("update TmbUserAddress t set t.defaultAddress = 0 where t.userId = " + mbUserAddress.getUserId());
		mbUserAddressDao.executeHql("update TmbUserAddress t set t.defaultAddress = 1 where t.id = " + mbUserAddress.getId());
	}

	@Override
	public MbUserAddress get(MbUserAddress mbUserAddress) {
		String hql = " from TmbUserAddress t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(mbUserAddress, params);
		List<TmbUserAddress> l = mbUserAddressDao.find(hql  + where, params);
		MbUserAddress o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new MbUserAddress();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

}
