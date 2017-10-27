package com.bx.ant.service.impl;

import com.bx.ant.pageModel.SupplierQuery;
import com.mobian.absx.F;
import com.bx.ant.dao.SupplierDaoI;
import com.bx.ant.model.Tsupplier;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.pageModel.Supplier;
import com.bx.ant.service.SupplierServiceI;
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
public class SupplierServiceImpl extends BaseServiceImpl<Supplier> implements SupplierServiceI {

	@Autowired
	private SupplierDaoI supplierDao;

	@Override
	public DataGrid dataGrid(Supplier supplier, PageHelper ph) {
		List<Supplier> ol = new ArrayList<Supplier>();
		String hql = " from Tsupplier t ";
		DataGrid dg = dataGridQuery(hql, ph, supplier, supplierDao);
		@SuppressWarnings("unchecked")
		List<Tsupplier> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (Tsupplier t : l) {
				Supplier o = new Supplier();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(Supplier supplier, Map<String, Object> params) {
		String whereHql = "";	
		if (supplier != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(supplier.getId())) {
				whereHql += " and t.id = :id";
				params.put("id", supplier.getId());
			}
			if (!F.empty(supplier.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", supplier.getTenantId());
			}
			if (!F.empty(supplier.getAppKey())) {
				whereHql += " and t.appKey = :appKey";
				params.put("appKey", supplier.getAppKey());
			}		
			if (!F.empty(supplier.getAppSecret())) {
				whereHql += " and t.appSecret = :appSecret";
				params.put("appSecret", supplier.getAppSecret());
			}		
			if (!F.empty(supplier.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", supplier.getStatus());
			}		
			if (!F.empty(supplier.getName())) {
				whereHql += " and t.name LIKE :name";
				params.put("name", "%" + supplier.getName() + "%");
			}		
			if (!F.empty(supplier.getAddress())) {
				whereHql += " and t.address = :address";
				params.put("address", supplier.getAddress());
			}		
			if (!F.empty(supplier.getCharterUrl())) {
				whereHql += " and t.charterUrl = :charterUrl";
				params.put("charterUrl", supplier.getCharterUrl());
			}		
			if (!F.empty(supplier.getContacter())) {
				whereHql += " and t.contacter = :contacter";
				params.put("contacter", supplier.getContacter());
			}		
			if (!F.empty(supplier.getContactPhone())) {
				whereHql += " and t.contactPhone = :contactPhone";
				params.put("contactPhone", supplier.getContactPhone());
			}		
			if (!F.empty(supplier.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", supplier.getRemark());
			}		
			if (!F.empty(supplier.getLoginId())) {
				whereHql += " and t.loginId = :loginId";
				params.put("loginId", supplier.getLoginId());
			}
			if (supplier instanceof SupplierQuery) {
				SupplierQuery supplierQuery = (SupplierQuery) supplier;
				if(supplierQuery.getSupplierIds()!=null) {
					whereHql += " and t.id in (:supplierIds)";
					params.put("supplierIds", supplierQuery.getSupplierIds());
				}
			}

		}	
		return whereHql;
	}

	@Override
	public void add(Supplier supplier) {
		Tsupplier t = new Tsupplier();
		BeanUtils.copyProperties(supplier, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		supplierDao.save(t);
	}

	@Override
	public Supplier get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tsupplier t = supplierDao.get("from Tsupplier t  where t.id = :id", params);
		Supplier o = new Supplier();
		if(t!=null) {
			BeanUtils.copyProperties(t, o);
		}else{
			o=null;
		}
		return o;
	}

	@Override
	public void edit(Supplier supplier) {
		Tsupplier t = supplierDao.get(Tsupplier.class, supplier.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(supplier, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		supplierDao.executeHql("update Tsupplier t set t.isdeleted = 1 where t.id = :id",params);
		//supplierDao.delete(supplierDao.get(Tsupplier.class, id));
	}

	@Override
	public List<Supplier> query(Supplier supplier) {
		List<Supplier> ol = new ArrayList<Supplier>();
		String hql = " from Tsupplier t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(supplier, params);
		List<Tsupplier> l = supplierDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (Tsupplier t : l) {
				Supplier s = new Supplier();
				BeanUtils.copyProperties(t, s);
				ol.add(s);
			}
		}
		return ol;
	}

}
