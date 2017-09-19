package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.FmOptionsDaoI;
import com.mobian.model.TfmOptions;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.FmOptions;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.FmOptionsServiceI;
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
public class FmOptionsServiceImpl extends BaseServiceImpl<FmOptions> implements FmOptionsServiceI {

	@Autowired
	private FmOptionsDaoI fmOptionsDao;

	@Override
	public DataGrid dataGrid(FmOptions fmOptions, PageHelper ph) {
		List<FmOptions> ol = new ArrayList<FmOptions>();
		String hql = " from TfmOptions t ";
		DataGrid dg = dataGridQuery(hql, ph, fmOptions, fmOptionsDao);
		@SuppressWarnings("unchecked")
		List<TfmOptions> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TfmOptions t : l) {
				FmOptions o = new FmOptions();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public List<FmOptions> query(FmOptions fmOptions) {
		List<FmOptions> ol = new ArrayList<FmOptions>();
		String hql = " from TfmOptions t ";
		@SuppressWarnings("unchecked")
		List<TfmOptions> l = query(hql, fmOptions, fmOptionsDao);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TfmOptions t : l) {
				FmOptions o = new FmOptions();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}


	protected String whereHql(FmOptions fmOptions, Map<String, Object> params) {
		String whereHql = "";	
		if (fmOptions != null) {
			whereHql += " where t.isdeleted = 0 ";

			if (!F.empty(fmOptions.getPropertiesId())) {
				whereHql += " and t.propertiesId = :propertiesId";
				params.put("propertiesId", fmOptions.getPropertiesId());
			}		
			if (!F.empty(fmOptions.getValue())) {
				whereHql += " and t.value = :value";
				params.put("value", fmOptions.getValue());
			}		

		}	
		return whereHql;
	}

	@Override
	public void add(FmOptions fmOptions) {
		TfmOptions t = new TfmOptions();
		BeanUtils.copyProperties(fmOptions, t);
		t.setId(com.mobian.absx.UUID.uuid());
		t.setIsdeleted(false);
		fmOptionsDao.save(t);
	}

	@Override
	public FmOptions get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TfmOptions t = fmOptionsDao.get("from TfmOptions t  where t.id = :id", params);
		FmOptions o = new FmOptions();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(FmOptions fmOptions) {
		TfmOptions t = fmOptionsDao.get(TfmOptions.class, fmOptions.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(fmOptions, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		fmOptionsDao.executeHql("update TfmOptions t set t.isdeleted = 1 where t.id = :id",params);
		//fmOptionsDao.delete(fmOptionsDao.get(TfmOptions.class, id));
	}

}
