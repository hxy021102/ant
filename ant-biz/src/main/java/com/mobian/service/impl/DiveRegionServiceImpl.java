package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.DiveRegionDaoI;
import com.mobian.model.TdiveRegion;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.DiveRegion;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.DiveRegionServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiveRegionServiceImpl extends BaseServiceImpl<DiveRegion> implements DiveRegionServiceI {

	@Autowired
	private DiveRegionDaoI diveRegionDao;

	@Override
	public DataGrid dataGrid(DiveRegion diveRegion, PageHelper ph) {
		List<DiveRegion> ol = new ArrayList<DiveRegion>();
		String hql = " from TdiveRegion t ";
		DataGrid dg = dataGridQuery(hql, ph, diveRegion, diveRegionDao);
		@SuppressWarnings("unchecked")
		List<TdiveRegion> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdiveRegion t : l) {
				DiveRegion o = new DiveRegion();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DiveRegion diveRegion, Map<String, Object> params) {
		String whereHql = "";	
		if (diveRegion != null) {
			whereHql += " where 1=1 ";
			if (diveRegion.getRegionLevel() != null) {
				whereHql += " and t.regionLevel <= :regionLevel";
				params.put("regionLevel", diveRegion.getRegionLevel());
			}		
			if (!F.empty(diveRegion.getRegionNameZh())) {
				whereHql += " and t.regionNameZh like :regionNameZh";
				params.put("regionNameZh", "%%" + diveRegion.getRegionNameZh() + "%%");
			}		
			if (!F.empty(diveRegion.getRegionNameEn())) {
				whereHql += " and t.regionNameEn = :regionNameEn";
				params.put("regionNameEn", diveRegion.getRegionNameEn());
			}		
			if (!F.empty(diveRegion.getRegionParentId())) {
				whereHql += " and t.regionParentId = :regionParentId";
				params.put("regionParentId", diveRegion.getRegionParentId());
			}		
			if (!F.empty(diveRegion.getRegionId())) {
				whereHql += " and t.regionId = :regionId";
				params.put("regionId", diveRegion.getRegionId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(DiveRegion diveRegion) {
		TdiveRegion t = new TdiveRegion();
		BeanUtils.copyProperties(diveRegion, t);
		diveRegionDao.save(t);
	}

	@Override
	public DiveRegion get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdiveRegion t = diveRegionDao.get("from TdiveRegion t  where t.id = :id", params);
		DiveRegion o = new DiveRegion();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public DiveRegion getFromCache(String regionId) {
		TdiveRegion t = diveRegionDao.getById(regionId);
		if (t != null) {
			DiveRegion diveRegion = new DiveRegion();
			BeanUtils.copyProperties(t, diveRegion);
			return diveRegion;
		}
		return null;
	}

	@Override
	public String getRegionPath(String regionId) {
		DiveRegion region = getFromCache(regionId);
		String path = "";
		if (region != null) {
			path = region.getRegionNameZh();
			DiveRegion city = getFromCache(region.getRegionParentId());
			if (city != null) {
				path = city.getRegionNameZh() + "-" + path;
				DiveRegion province = getFromCache(city.getRegionParentId());
				if (province != null) {
					path = province.getRegionNameZh() + "-" + path;
				}
			}
		}
		return path;
	}

	@Override
	public void edit(DiveRegion diveRegion) {
		TdiveRegion t = diveRegionDao.get(TdiveRegion.class, diveRegion.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(diveRegion, t, new String[]{"id", "createdatetime"}, true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(Integer id) {
		diveRegionDao.delete(diveRegionDao.get(TdiveRegion.class, id));
	}


	@Override
	public List<DiveRegion> findAllByParams(DiveRegion diveRegion) {
		List<DiveRegion> r = new ArrayList<DiveRegion>();
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(diveRegion, params);
		List<TdiveRegion> l = diveRegionDao.find("from TdiveRegion t " + whereHql + " order by t.regionId asc", params);
		if (l != null && l.size() > 0) {
			for (TdiveRegion t : l) {
				DiveRegion o = new DiveRegion();
				BeanUtils.copyProperties(t, o);
				r.add(o);
			}
		}
		return r;
	}

	@Override
	public Boolean isParent(String parentRegionId, String sonRegionId) {
		if (parentRegionId.equals(sonRegionId)) return true;
		DiveRegion realParent;
		String realParentRegionId = sonRegionId;
		while ((realParent = getFromCache(realParentRegionId)) != null) {
			if (parentRegionId.equals(realParent.getRegionId())) {
				return true;
			}else {
				realParentRegionId = realParent.getRegionParentId();
			}
		}
		return false;
	}

}
