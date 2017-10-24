package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierContractClauseDaoI;
import com.mobian.model.TmbSupplierContractClause;
import com.mobian.pageModel.MbSupplierContractClause;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbSupplierContractClauseServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class MbSupplierContractClauseServiceImpl extends BaseServiceImpl<MbSupplierContractClause> implements MbSupplierContractClauseServiceI {

	@Autowired
	private MbSupplierContractClauseDaoI mbSupplierContractClauseDao;

	@Override
	public DataGrid dataGrid(MbSupplierContractClause mbSupplierContractClause, PageHelper ph) {
		List<MbSupplierContractClause> ol = new ArrayList<MbSupplierContractClause>();
		String hql = " from TmbSupplierContractClause t ";
		DataGrid dg = dataGridQuery(hql, ph, mbSupplierContractClause, mbSupplierContractClauseDao);
		@SuppressWarnings("unchecked")
		List<TmbSupplierContractClause> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbSupplierContractClause t : l) {
				MbSupplierContractClause o = new MbSupplierContractClause();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbSupplierContractClause mbSupplierContractClause, Map<String, Object> params) {
		String whereHql = "";	
		if (mbSupplierContractClause != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbSupplierContractClause.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbSupplierContractClause.getTenantId());
			}		
			if (!F.empty(mbSupplierContractClause.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbSupplierContractClause.getIsdeleted());
			}		
			if (!F.empty(mbSupplierContractClause.getSupplierContractId())) {
				whereHql += " and t.supplierContractId = :supplierContractId";
				params.put("supplierContractId", mbSupplierContractClause.getSupplierContractId());
			}		
			if (!F.empty(mbSupplierContractClause.getClauseCode())) {
				whereHql += " and t.clauseCode = :clauseCode";
				params.put("clauseCode", mbSupplierContractClause.getClauseCode());
			}		
			if (!F.empty(mbSupplierContractClause.getValue())) {
				whereHql += " and t.value = :value";
				params.put("value", mbSupplierContractClause.getValue());
			}		
			if (!F.empty(mbSupplierContractClause.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbSupplierContractClause.getRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbSupplierContractClause mbSupplierContractClause) {
		TmbSupplierContractClause t = new TmbSupplierContractClause();
		BeanUtils.copyProperties(mbSupplierContractClause, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbSupplierContractClauseDao.save(t);
	}

	@Override
	public MbSupplierContractClause get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbSupplierContractClause t = mbSupplierContractClauseDao.get("from TmbSupplierContractClause t  where t.id = :id", params);
		MbSupplierContractClause o = new MbSupplierContractClause();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbSupplierContractClause mbSupplierContractClause) {
		TmbSupplierContractClause t = mbSupplierContractClauseDao.get(TmbSupplierContractClause.class, mbSupplierContractClause.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbSupplierContractClause, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbSupplierContractClauseDao.executeHql("update TmbSupplierContractClause t set t.isdeleted = 1 where t.id = :id",params);
		//mbSupplierContractClauseDao.delete(mbSupplierContractClauseDao.get(TmbSupplierContractClause.class, id));
	}

}
