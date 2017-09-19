package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierDaoI;
import com.mobian.model.TmbSupplier;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplier;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.DiveRegionServiceI;
import com.mobian.service.MbSupplierServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbSupplierServiceImpl extends BaseServiceImpl<MbSupplier> implements MbSupplierServiceI {

	@Autowired
	private MbSupplierDaoI mbSupplierDao;

	@Autowired
	private DiveRegionServiceI diveRegionService;
	@Override
	public DataGrid dataGrid(MbSupplier mbSupplier, PageHelper ph) {
		List<MbSupplier> ol = new ArrayList<MbSupplier>();
		String hql = " from TmbSupplier t ";
		DataGrid dg = dataGridQuery(hql, ph, mbSupplier, mbSupplierDao);
		@SuppressWarnings("unchecked")
		List<TmbSupplier> l = dg.getRows();

		if (l != null && l.size() > 0) {
			for (TmbSupplier t : l) {
				MbSupplier o = new MbSupplier();
				BeanUtils.copyProperties(t, o);
				o.setRegionPath(diveRegionService.getRegionPath(o.getRegionId()+""));
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbSupplier mbSupplier, Map<String, Object> params) {
		String whereHql = "";	
		if (mbSupplier != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbSupplier.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbSupplier.getTenantId());
			}		
			if (!F.empty(mbSupplier.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbSupplier.getIsdeleted());
			}		
			if (!F.empty(mbSupplier.getName())) {
				whereHql += " and t.name LIKE :name";
				params.put("name", "%" + mbSupplier.getName()+"%");
			}		
			if (!F.empty(mbSupplier.getRegionId())) {
				whereHql += " and t.regionId = :regionId";
				params.put("regionId", mbSupplier.getRegionId());
			}		
			if (!F.empty(mbSupplier.getAddress())) {
				whereHql += " and t.address = :address";
				params.put("address", mbSupplier.getAddress());
			}		
			if (!F.empty(mbSupplier.getContactPhone())) {
				whereHql += " and t.contactPhone = :contactPhone";
				params.put("contactPhone", mbSupplier.getContactPhone());
			}		
			if (!F.empty(mbSupplier.getContactPeople())) {
				whereHql += " and t.contactPeople = :contactPeople";
				params.put("contactPeople", mbSupplier.getContactPeople());
			}		
			if (!F.empty(mbSupplier.getCertificateList())) {
				whereHql += " and t.certificateList = :certificateList";
				params.put("certificateList", mbSupplier.getCertificateList());
			}		
			if (!F.empty(mbSupplier.getWarehouseId())) {
				whereHql += " and t.warehouseId = :warehouseId";
				params.put("warehouseId", mbSupplier.getWarehouseId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbSupplier mbSupplier) {
		TmbSupplier t = new TmbSupplier();
		BeanUtils.copyProperties(mbSupplier, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbSupplierDao.save(t);
	}

	 public MbSupplier getFromCache(Integer id) {
		 TmbSupplier source = mbSupplierDao.getById(id);
		 if (source != null) {
			 MbSupplier target = new MbSupplier();
			 BeanUtils.copyProperties(source, target);
			 return target;
		 } else {
			 return null;
		 }
	 }

	public MbSupplier get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbSupplier t = mbSupplierDao.get("from TmbSupplier t  where t.id = :id", params);
		MbSupplier o = new MbSupplier();
		BeanUtils.copyProperties(t, o);
		return o;
	}


	public void edit(MbSupplier mbSupplier) {
		TmbSupplier t = mbSupplierDao.get(TmbSupplier.class, mbSupplier.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbSupplier, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbSupplierDao.executeHql("update TmbSupplier t set t.isdeleted = 1 where t.id = :id",params);
		//mbSupplierDao.delete(mbSupplierDao.get(TmbSupplier.class, id));
	}


}
