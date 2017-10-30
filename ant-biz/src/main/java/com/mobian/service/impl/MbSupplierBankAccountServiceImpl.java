package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierBankAccountDaoI;
import com.mobian.model.TmbSupplierBankAccount;
import com.mobian.pageModel.MbSupplierBankAccount;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbSupplierBankAccountServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class MbSupplierBankAccountServiceImpl extends BaseServiceImpl<MbSupplierBankAccount> implements MbSupplierBankAccountServiceI {

	@Autowired
	private MbSupplierBankAccountDaoI mbSupplierBankAccountDao;

	@Override
	public DataGrid dataGrid(MbSupplierBankAccount mbSupplierBankAccount, PageHelper ph) {
		List<MbSupplierBankAccount> ol = new ArrayList<MbSupplierBankAccount>();
		String hql = " from TmbSupplierBankAccount t ";
		DataGrid dg = dataGridQuery(hql, ph, mbSupplierBankAccount, mbSupplierBankAccountDao);
		@SuppressWarnings("unchecked")
		List<TmbSupplierBankAccount> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbSupplierBankAccount t : l) {
				MbSupplierBankAccount o = new MbSupplierBankAccount();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbSupplierBankAccount mbSupplierBankAccount, Map<String, Object> params) {
		String whereHql = "";	
		if (mbSupplierBankAccount != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbSupplierBankAccount.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbSupplierBankAccount.getTenantId());
			}		
			if (!F.empty(mbSupplierBankAccount.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbSupplierBankAccount.getIsdeleted());
			}		
			if (!F.empty(mbSupplierBankAccount.getSupplierId())) {
				whereHql += " and t.supplierId = :supplierId";
				params.put("supplierId", mbSupplierBankAccount.getSupplierId());
			}		
			if (!F.empty(mbSupplierBankAccount.getAccountName())) {
				whereHql += " and t.accountName = :accountName";
				params.put("accountName", mbSupplierBankAccount.getAccountName());
			}		
			if (!F.empty(mbSupplierBankAccount.getAccountBank())) {
				whereHql += " and t.accountBank = :accountBank";
				params.put("accountBank", mbSupplierBankAccount.getAccountBank());
			}		
			if (!F.empty(mbSupplierBankAccount.getBankNumber())) {
				whereHql += " and t.bankNumber = :bankNumber";
				params.put("bankNumber", mbSupplierBankAccount.getBankNumber());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbSupplierBankAccount mbSupplierBankAccount) {
		TmbSupplierBankAccount t = new TmbSupplierBankAccount();
		BeanUtils.copyProperties(mbSupplierBankAccount, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbSupplierBankAccountDao.save(t);
	}

	@Override
	public MbSupplierBankAccount get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbSupplierBankAccount t = mbSupplierBankAccountDao.get("from TmbSupplierBankAccount t  where t.id = :id", params);
		MbSupplierBankAccount o = new MbSupplierBankAccount();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbSupplierBankAccount mbSupplierBankAccount) {
		TmbSupplierBankAccount t = mbSupplierBankAccountDao.get(TmbSupplierBankAccount.class, mbSupplierBankAccount.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbSupplierBankAccount, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbSupplierBankAccountDao.executeHql("update TmbSupplierBankAccount t set t.isdeleted = 1 where t.id = :id",params);
		//mbSupplierBankAccountDao.delete(mbSupplierBankAccountDao.get(TmbSupplierBankAccount.class, id));
	}

}
