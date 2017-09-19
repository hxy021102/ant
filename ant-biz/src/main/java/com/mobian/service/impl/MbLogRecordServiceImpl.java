package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbLogRecordDaoI;
import com.mobian.model.TmbLogRecord;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbLogRecord;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbLogRecordServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbLogRecordServiceImpl extends BaseServiceImpl<MbLogRecord> implements MbLogRecordServiceI {

	@Autowired
	private MbLogRecordDaoI mbLogRecordDao;

	@Override
	public DataGrid dataGrid(MbLogRecord mbLogRecord, PageHelper ph) {
		List<MbLogRecord> ol = new ArrayList<MbLogRecord>();
		String hql = " from TmbLogRecord t ";
		DataGrid dg = dataGridQuery(hql, ph, mbLogRecord, mbLogRecordDao);
		@SuppressWarnings("unchecked")
		List<TmbLogRecord> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbLogRecord t : l) {
				MbLogRecord o = new MbLogRecord();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbLogRecord mbLogRecord, Map<String, Object> params) {
		String whereHql = "";	
		if (mbLogRecord != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbLogRecord.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbLogRecord.getTenantId());
			}		
			if (!F.empty(mbLogRecord.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbLogRecord.getIsdeleted());
			}		
			if (!F.empty(mbLogRecord.getLogUserId())) {
				whereHql += " and t.logUserId = :logUserId";
				params.put("logUserId", mbLogRecord.getLogUserId());
			}		
			if (!F.empty(mbLogRecord.getLogUserName())) {
				whereHql += " and t.logUserName = :logUserName";
				params.put("logUserName", mbLogRecord.getLogUserName());
			}		
			if (!F.empty(mbLogRecord.getUrl())) {
				whereHql += " and t.url = :url";
				params.put("url", mbLogRecord.getUrl());
			}		
			if (!F.empty(mbLogRecord.getUrlName())) {
				whereHql += " and t.urlName = :urlName";
				params.put("urlName", mbLogRecord.getUrlName());
			}		
			if (!F.empty(mbLogRecord.getFormData())) {
				whereHql += " and t.formData = :formData";
				params.put("formData", mbLogRecord.getFormData());
			}
			if (mbLogRecord.getStartTime()!=null) {
				whereHql += " and t.addtime >= :startTime";
				params.put("startTime", mbLogRecord.getStartTime());
			}
			if (mbLogRecord.getEndTime() != null) {
				whereHql += " and t.addtime <= :endTime";
				params.put("endTime", mbLogRecord.getEndTime());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbLogRecord mbLogRecord) {
		TmbLogRecord t = new TmbLogRecord();
		BeanUtils.copyProperties(mbLogRecord, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbLogRecordDao.save(t);
	}

	@Override
	public MbLogRecord get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbLogRecord t = mbLogRecordDao.get("from TmbLogRecord t  where t.id = :id", params);
		MbLogRecord o = new MbLogRecord();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbLogRecord mbLogRecord) {
		TmbLogRecord t = mbLogRecordDao.get(TmbLogRecord.class, mbLogRecord.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbLogRecord, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbLogRecordDao.executeHql("update TmbLogRecord t set t.isdeleted = 1 where t.id = :id",params);
		//mbLogRecordDao.delete(mbLogRecordDao.get(TmbLogRecord.class, id));
	}

}
