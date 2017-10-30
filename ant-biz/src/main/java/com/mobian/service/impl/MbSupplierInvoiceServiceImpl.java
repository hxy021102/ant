package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierInvoiceDaoI;
import com.mobian.model.TmbSupplierInvoice;
import com.mobian.pageModel.MbSupplierInvoice;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbSupplierInvoiceExt;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbSupplierInvoiceServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class MbSupplierInvoiceServiceImpl extends BaseServiceImpl<MbSupplierInvoice> implements MbSupplierInvoiceServiceI {

	@Autowired
	private MbSupplierInvoiceDaoI mbSupplierInvoiceDao;

	@Override
	public DataGrid dataGrid(MbSupplierInvoice mbSupplierInvoice, PageHelper ph) {
		List<MbSupplierInvoiceExt> ol = new ArrayList<MbSupplierInvoiceExt>();
		String hql = " from TmbSupplierInvoice t ";
		DataGrid dg = dataGridQuery(hql, ph, mbSupplierInvoice, mbSupplierInvoiceDao);
		@SuppressWarnings("unchecked")
		List<TmbSupplierInvoice> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbSupplierInvoice t : l) {
				MbSupplierInvoiceExt o = new MbSupplierInvoiceExt();
				BeanUtils.copyProperties(t, o);
				o.setInvoiceTypeName(t.getInvoiceType());
				o.setInvoiceUseName(t.getInvoiceUse());
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbSupplierInvoice mbSupplierInvoice, Map<String, Object> params) {
		String whereHql = "";	
		if (mbSupplierInvoice != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbSupplierInvoice.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbSupplierInvoice.getTenantId());
			}		
			if (!F.empty(mbSupplierInvoice.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbSupplierInvoice.getIsdeleted());
			}		
			if (!F.empty(mbSupplierInvoice.getSupplierId())) {
				whereHql += " and t.supplierId = :supplierId";
				params.put("supplierId", mbSupplierInvoice.getSupplierId());
			}		
			if (!F.empty(mbSupplierInvoice.getCompanyName())) {
				whereHql += " and t.companyName = :companyName";
				params.put("companyName", mbSupplierInvoice.getCompanyName());
			}		
			if (!F.empty(mbSupplierInvoice.getCompanyTfn())) {
				whereHql += " and t.companyTfn = :companyTfn";
				params.put("companyTfn", mbSupplierInvoice.getCompanyTfn());
			}		
			if (!F.empty(mbSupplierInvoice.getRegisterAddress())) {
				whereHql += " and t.registerAddress = :registerAddress";
				params.put("registerAddress", mbSupplierInvoice.getRegisterAddress());
			}		
			if (!F.empty(mbSupplierInvoice.getRegisterPhone())) {
				whereHql += " and t.registerPhone = :registerPhone";
				params.put("registerPhone", mbSupplierInvoice.getRegisterPhone());
			}		
			if (!F.empty(mbSupplierInvoice.getBankName())) {
				whereHql += " and t.bankName = :bankName";
				params.put("bankName", mbSupplierInvoice.getBankName());
			}		
			if (!F.empty(mbSupplierInvoice.getBankNumber())) {
				whereHql += " and t.bankNumber = :bankNumber";
				params.put("bankNumber", mbSupplierInvoice.getBankNumber());
			}		
			if (!F.empty(mbSupplierInvoice.getInvoiceUse())) {
				whereHql += " and t.invoiceUse = :invoiceUse";
				params.put("invoiceUse", mbSupplierInvoice.getInvoiceUse());
			}		
			if (!F.empty(mbSupplierInvoice.getInvoiceType())) {
				whereHql += " and t.invoiceType = :invoiceType";
				params.put("invoiceType", mbSupplierInvoice.getInvoiceType());
			}		
			if (!F.empty(mbSupplierInvoice.getInvoiceDefault())) {
				whereHql += " and t.invoiceDefault = :invoiceDefault";
				params.put("invoiceDefault", mbSupplierInvoice.getInvoiceDefault());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbSupplierInvoice mbSupplierInvoice) {
		TmbSupplierInvoice t = new TmbSupplierInvoice();
		BeanUtils.copyProperties(mbSupplierInvoice, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbSupplierInvoiceDao.save(t);
	}

	@Override
	public MbSupplierInvoice get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbSupplierInvoice t = mbSupplierInvoiceDao.get("from TmbSupplierInvoice t  where t.id = :id", params);
		MbSupplierInvoice o = new MbSupplierInvoice();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbSupplierInvoice mbSupplierInvoice) {
		TmbSupplierInvoice t = mbSupplierInvoiceDao.get(TmbSupplierInvoice.class, mbSupplierInvoice.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbSupplierInvoice, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbSupplierInvoiceDao.executeHql("update TmbSupplierInvoice t set t.isdeleted = 1 where t.id = :id",params);
		//mbSupplierInvoiceDao.delete(mbSupplierInvoiceDao.get(TmbSupplierInvoice.class, id));
	}

	@Override
	public List<MbSupplierInvoice> query(MbSupplierInvoice mbSupplierInvoice) {
		List<MbSupplierInvoice> ol = new ArrayList<MbSupplierInvoice>();
		String hql = " from TmbSupplierInvoice t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(mbSupplierInvoice, params);
		List<TmbSupplierInvoice> l = mbSupplierInvoiceDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbSupplierInvoice t : l) {
				MbSupplierInvoice o = new MbSupplierInvoice();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
