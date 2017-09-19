package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbUserInvoiceDaoI;
import com.mobian.model.TmbUserInvoice;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbUserInvoice;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbUserInvoiceServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbUserInvoiceServiceImpl extends BaseServiceImpl<MbUserInvoice> implements MbUserInvoiceServiceI {

	@Autowired
	private MbUserInvoiceDaoI mbUserInvoiceDao;

	@Override
	public DataGrid dataGrid(MbUserInvoice mbUserInvoice, PageHelper ph) {
		List<MbUserInvoice> ol = new ArrayList<MbUserInvoice>();
		String hql = " from TmbUserInvoice t ";
		DataGrid dg = dataGridQuery(hql, ph, mbUserInvoice, mbUserInvoiceDao);
		@SuppressWarnings("unchecked")
		List<TmbUserInvoice> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbUserInvoice t : l) {
				MbUserInvoice o = new MbUserInvoice();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbUserInvoice mbUserInvoice, Map<String, Object> params) {
		String whereHql = "";	
		if (mbUserInvoice != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbUserInvoice.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbUserInvoice.getTenantId());
			}		
			if (!F.empty(mbUserInvoice.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbUserInvoice.getIsdeleted());
			}		
			if (!F.empty(mbUserInvoice.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", mbUserInvoice.getUserId());
			}		
			if (!F.empty(mbUserInvoice.getCompanyName())) {
				whereHql += " and t.companyName = :companyName";
				params.put("companyName", mbUserInvoice.getCompanyName());
			}		
			if (!F.empty(mbUserInvoice.getCompanyTfn())) {
				whereHql += " and t.companyTfn = :companyTfn";
				params.put("companyTfn", mbUserInvoice.getCompanyTfn());
			}		
			if (!F.empty(mbUserInvoice.getRegisterAddress())) {
				whereHql += " and t.registerAddress = :registerAddress";
				params.put("registerAddress", mbUserInvoice.getRegisterAddress());
			}		
			if (!F.empty(mbUserInvoice.getRegisterPhone())) {
				whereHql += " and t.registerPhone = :registerPhone";
				params.put("registerPhone", mbUserInvoice.getRegisterPhone());
			}		
			if (!F.empty(mbUserInvoice.getBankName())) {
				whereHql += " and t.bankName = :bankName";
				params.put("bankName", mbUserInvoice.getBankName());
			}		
			if (!F.empty(mbUserInvoice.getBankNumber())) {
				whereHql += " and t.bankNumber = :bankNumber";
				params.put("bankNumber", mbUserInvoice.getBankNumber());
			}		
			if (!F.empty(mbUserInvoice.getInvoiceUse())) {
				whereHql += " and t.invoiceUse = :invoiceUse";
				params.put("invoiceUse", mbUserInvoice.getInvoiceUse());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbUserInvoice mbUserInvoice) {
		TmbUserInvoice t = new TmbUserInvoice();
		BeanUtils.copyProperties(mbUserInvoice, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbUserInvoiceDao.save(t);
	}

	@Override
	public MbUserInvoice get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbUserInvoice t = mbUserInvoiceDao.get("from TmbUserInvoice t  where t.id = :id", params);
		MbUserInvoice o = new MbUserInvoice();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbUserInvoice mbUserInvoice) {
		TmbUserInvoice t = mbUserInvoiceDao.get(TmbUserInvoice.class, mbUserInvoice.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbUserInvoice, t, new String[]{"id", "addtime", "isdeleted", "updatetime", "userId"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbUserInvoiceDao.executeHql("update TmbUserInvoice t set t.isdeleted = 1 where t.id = :id",params);
		//mbUserInvoiceDao.delete(mbUserInvoiceDao.get(TmbUserInvoice.class, id));
	}

}
