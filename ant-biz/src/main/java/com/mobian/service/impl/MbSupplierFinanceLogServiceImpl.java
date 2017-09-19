package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierFinanceLogDaoI;
import com.mobian.model.TmbSupplierFinanceLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplierFinanceLog;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbSupplierFinanceLogServiceI;
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
public class MbSupplierFinanceLogServiceImpl extends BaseServiceImpl<MbSupplierFinanceLog> implements MbSupplierFinanceLogServiceI {

	@Autowired
	private MbSupplierFinanceLogDaoI mbSupplierFinanceLogDao;

	@Override
	public DataGrid dataGrid(MbSupplierFinanceLog mbSupplierFinanceLog, PageHelper ph) {
		List<MbSupplierFinanceLog> ol = new ArrayList<MbSupplierFinanceLog>();
		String hql = " from TmbSupplierFinanceLog t ";
		DataGrid dg = dataGridQuery(hql, ph, mbSupplierFinanceLog, mbSupplierFinanceLogDao);
		@SuppressWarnings("unchecked")
		List<TmbSupplierFinanceLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbSupplierFinanceLog t : l) {
				MbSupplierFinanceLog o = new MbSupplierFinanceLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbSupplierFinanceLog mbSupplierFinanceLog, Map<String, Object> params) {
		String whereHql = "";	
		if (mbSupplierFinanceLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbSupplierFinanceLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbSupplierFinanceLog.getTenantId());
			}		
			if (!F.empty(mbSupplierFinanceLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbSupplierFinanceLog.getIsdeleted());
			}		
			if (!F.empty(mbSupplierFinanceLog.getSupplierStockInId())) {
				whereHql += " and t.supplierStockInId = :supplierStockInId";
				params.put("supplierStockInId", mbSupplierFinanceLog.getSupplierStockInId());
			}		
			if (!F.empty(mbSupplierFinanceLog.getPayLoginId())) {
				whereHql += " and t.payLoginId = :payLoginId";
				params.put("payLoginId", mbSupplierFinanceLog.getPayLoginId());
			}		
			if (!F.empty(mbSupplierFinanceLog.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", mbSupplierFinanceLog.getPayStatus());
			}		
			if (!F.empty(mbSupplierFinanceLog.getInvoiceStatus())) {
				whereHql += " and t.invoiceStatus = :invoiceStatus";
				params.put("invoiceStatus", mbSupplierFinanceLog.getInvoiceStatus());
			}		
			if (!F.empty(mbSupplierFinanceLog.getInvoiceLoginId())) {
				whereHql += " and t.invoiceLoginId = :invoiceLoginId";
				params.put("invoiceLoginId", mbSupplierFinanceLog.getInvoiceLoginId());
			}		
			if (!F.empty(mbSupplierFinanceLog.getPayRemark())) {
				whereHql += " and t.payRemark = :payRemark";
				params.put("payRemark", mbSupplierFinanceLog.getPayRemark());
			}		
			if (!F.empty(mbSupplierFinanceLog.getInvoiceRemark())) {
				whereHql += " and t.invoiceRemark = :invoiceRemark";
				params.put("invoiceRemark", mbSupplierFinanceLog.getInvoiceRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbSupplierFinanceLog mbSupplierFinanceLog) {
		TmbSupplierFinanceLog t = new TmbSupplierFinanceLog();
		BeanUtils.copyProperties(mbSupplierFinanceLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbSupplierFinanceLogDao.save(t);
	}

	@Override
	public MbSupplierFinanceLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbSupplierFinanceLog t = mbSupplierFinanceLogDao.get("from TmbSupplierFinanceLog t  where t.id = :id", params);
		MbSupplierFinanceLog o = new MbSupplierFinanceLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbSupplierFinanceLog mbSupplierFinanceLog) {
		TmbSupplierFinanceLog t = mbSupplierFinanceLogDao.get(TmbSupplierFinanceLog.class, mbSupplierFinanceLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbSupplierFinanceLog, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbSupplierFinanceLogDao.executeHql("update TmbSupplierFinanceLog t set t.isdeleted = 1 where t.id = :id",params);
		//mbSupplierFinanceLogDao.delete(mbSupplierFinanceLogDao.get(TmbSupplierFinanceLog.class, id));
	}
  @Override
  public List<MbSupplierFinanceLog> query(MbSupplierFinanceLog mbSupplierFinanceLog) {
	  List<MbSupplierFinanceLog> ol = new ArrayList<MbSupplierFinanceLog>();
	  String hql = " from TmbSupplierFinanceLog t ";
	  Map<String, Object> params = new HashMap<String, Object>();
	  String where = whereHql(mbSupplierFinanceLog, params);
	  List<TmbSupplierFinanceLog> l = mbSupplierFinanceLogDao.find(hql  + where, params);
	  if (CollectionUtils.isNotEmpty(l)) {
		  for (TmbSupplierFinanceLog t : l) {
			  MbSupplierFinanceLog o = new MbSupplierFinanceLog();
			  BeanUtils.copyProperties(t, o);
			  ol.add(o);
		  }
	  }
	  return ol;
  }
}
