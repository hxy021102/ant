package com.bx.ant.service.impl;

import com.bx.ant.dao.SupplierInterfaceConfigDaoI;
import com.bx.ant.model.TsupplierInterfaceConfig;
import com.bx.ant.pageModel.SupplierInterfaceConfig;
import com.bx.ant.service.SupplierInterfaceConfigServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierInterfaceConfigServiceImpl extends BaseServiceImpl<SupplierInterfaceConfig> implements SupplierInterfaceConfigServiceI {

	@Autowired
	private SupplierInterfaceConfigDaoI supplierInterfaceConfigDao;

	@Override
	public DataGrid dataGrid(SupplierInterfaceConfig supplierInterfaceConfig, PageHelper ph) {
		List<SupplierInterfaceConfig> ol = new ArrayList<SupplierInterfaceConfig>();
		String hql = " from TsupplierInterfaceConfig t ";
		DataGrid dg = dataGridQuery(hql, ph, supplierInterfaceConfig, supplierInterfaceConfigDao);
		@SuppressWarnings("unchecked")
		List<TsupplierInterfaceConfig> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TsupplierInterfaceConfig t : l) {
				SupplierInterfaceConfig o = new SupplierInterfaceConfig();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(SupplierInterfaceConfig supplierInterfaceConfig, Map<String, Object> params) {
		String whereHql = "";	
		if (supplierInterfaceConfig != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(supplierInterfaceConfig.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", supplierInterfaceConfig.getTenantId());
			}		
			if (!F.empty(supplierInterfaceConfig.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", supplierInterfaceConfig.getIsdeleted());
			}		
			if (!F.empty(supplierInterfaceConfig.getInterfaceType())) {
				whereHql += " and t.interfaceType = :interfaceType";
				params.put("interfaceType", supplierInterfaceConfig.getInterfaceType());
			}		
			if (!F.empty(supplierInterfaceConfig.getCustomerId())) {
				whereHql += " and t.customerId = :customerId";
				params.put("customerId", supplierInterfaceConfig.getCustomerId());
			}		
			if (!F.empty(supplierInterfaceConfig.getAppKey())) {
				whereHql += " and t.appKey = :appKey";
				params.put("appKey", supplierInterfaceConfig.getAppKey());
			}		
			if (!F.empty(supplierInterfaceConfig.getAppSecret())) {
				whereHql += " and t.appSecret = :appSecret";
				params.put("appSecret", supplierInterfaceConfig.getAppSecret());
			}		
			if (!F.empty(supplierInterfaceConfig.getServiceUrl())) {
				whereHql += " and t.serviceUrl = :serviceUrl";
				params.put("serviceUrl", supplierInterfaceConfig.getServiceUrl());
			}		
			if (!F.empty(supplierInterfaceConfig.getVersion())) {
				whereHql += " and t.version = :version";
				params.put("version", supplierInterfaceConfig.getVersion());
			}		
			if (!F.empty(supplierInterfaceConfig.getWarehouseCode())) {
				whereHql += " and t.warehouseCode = :warehouseCode";
				params.put("warehouseCode", supplierInterfaceConfig.getWarehouseCode());
			}		
			if (!F.empty(supplierInterfaceConfig.getLogisticsCode())) {
				whereHql += " and t.logisticsCode = :logisticsCode";
				params.put("logisticsCode", supplierInterfaceConfig.getLogisticsCode());
			}		
			if (!F.empty(supplierInterfaceConfig.getStatusMap())) {
				whereHql += " and t.statusMap = :statusMap";
				params.put("statusMap", supplierInterfaceConfig.getStatusMap());
			}		
			if (!F.empty(supplierInterfaceConfig.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", supplierInterfaceConfig.getRemark());
			}		
			if (!F.empty(supplierInterfaceConfig.getOnline())) {
				whereHql += " and t.online = :online";
				params.put("online", supplierInterfaceConfig.getOnline());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(SupplierInterfaceConfig supplierInterfaceConfig) {
		TsupplierInterfaceConfig t = new TsupplierInterfaceConfig();
		BeanUtils.copyProperties(supplierInterfaceConfig, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		supplierInterfaceConfigDao.save(t);
	}

	@Override
	public SupplierInterfaceConfig get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TsupplierInterfaceConfig t = supplierInterfaceConfigDao.get("from TsupplierInterfaceConfig t  where t.id = :id", params);
		SupplierInterfaceConfig o = new SupplierInterfaceConfig();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(SupplierInterfaceConfig supplierInterfaceConfig) {
		TsupplierInterfaceConfig t = supplierInterfaceConfigDao.get(TsupplierInterfaceConfig.class, supplierInterfaceConfig.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(supplierInterfaceConfig, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		supplierInterfaceConfigDao.executeHql("update TsupplierInterfaceConfig t set t.isdeleted = 1 where t.id = :id",params);
		//supplierInterfaceConfigDao.delete(supplierInterfaceConfigDao.get(TsupplierInterfaceConfig.class, id));
	}

}
