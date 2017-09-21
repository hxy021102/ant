package com.bx.ant.service.impl;

import com.mobian.absx.F;
import com.bx.ant.dao.SupplierItemRelationDaoI;
import com.bx.ant.model.TsupplierItemRelation;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.SupplierItemRelation;
import com.bx.ant.service.SupplierItemRelationServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierItemRelationServiceImpl extends BaseServiceImpl<SupplierItemRelation> implements SupplierItemRelationServiceI {

	@Autowired
	private SupplierItemRelationDaoI supplierItemRelationDao;

	@Override
	public DataGrid dataGrid(SupplierItemRelation supplierItemRelation, PageHelper ph) {
		List<SupplierItemRelation> ol = new ArrayList<SupplierItemRelation>();
		String hql = " from TsupplierItemRelation t ";
		DataGrid dg = dataGridQuery(hql, ph, supplierItemRelation, supplierItemRelationDao);
		@SuppressWarnings("unchecked")
		List<TsupplierItemRelation> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TsupplierItemRelation t : l) {
				SupplierItemRelation o = new SupplierItemRelation();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(SupplierItemRelation supplierItemRelation, Map<String, Object> params) {
		String whereHql = "";	
		if (supplierItemRelation != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(supplierItemRelation.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", supplierItemRelation.getTenantId());
			}		
			if (!F.empty(supplierItemRelation.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", supplierItemRelation.getIsdeleted());
			}		
			if (!F.empty(supplierItemRelation.getSupplierId())) {
				whereHql += " and t.supplierId = :supplierId";
				params.put("supplierId", supplierItemRelation.getSupplierId());
			}		
			if (!F.empty(supplierItemRelation.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", supplierItemRelation.getItemId());
			}		
			if (!F.empty(supplierItemRelation.getPrice())) {
				whereHql += " and t.price = :price";
				params.put("price", supplierItemRelation.getPrice());
			}		
			if (!F.empty(supplierItemRelation.getInPrice())) {
				whereHql += " and t.inPrice = :inPrice";
				params.put("inPrice", supplierItemRelation.getInPrice());
			}		
			if (!F.empty(supplierItemRelation.getFreight())) {
				whereHql += " and t.freight = :freight";
				params.put("freight", supplierItemRelation.getFreight());
			}		

		}	
		return whereHql;
	}

	@Override
	public void add(SupplierItemRelation supplierItemRelation) {
		TsupplierItemRelation t = new TsupplierItemRelation();
		BeanUtils.copyProperties(supplierItemRelation, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		supplierItemRelationDao.save(t);
	}

	@Override
	public SupplierItemRelation get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TsupplierItemRelation t = supplierItemRelationDao.get("from TsupplierItemRelation t  where t.id = :id", params);
		SupplierItemRelation o = new SupplierItemRelation();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(SupplierItemRelation supplierItemRelation) {
		TsupplierItemRelation t = supplierItemRelationDao.get(TsupplierItemRelation.class, supplierItemRelation.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(supplierItemRelation, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		supplierItemRelationDao.executeHql("update TsupplierItemRelation t set t.isdeleted = 1 where t.id = :id",params);
		//supplierItemRelationDao.delete(supplierItemRelationDao.get(TsupplierItemRelation.class, id));
	}

}
