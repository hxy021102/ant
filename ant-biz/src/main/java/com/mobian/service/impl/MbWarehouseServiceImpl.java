package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbWarehouseDaoI;
import com.mobian.model.TmbWarehouse;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbWarehouse;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.DiveRegionServiceI;
import com.mobian.service.MbWarehouseServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbWarehouseServiceImpl extends BaseServiceImpl<MbWarehouse> implements MbWarehouseServiceI {

	@Autowired
	private MbWarehouseDaoI mbWarehouseDao;

	@Autowired
	private DiveRegionServiceI diveRegionService;

	@Override
	public DataGrid dataGrid(MbWarehouse mbWarehouse, PageHelper ph) {
		List<MbWarehouse> ol = new ArrayList<MbWarehouse>();
		String hql = " from TmbWarehouse t ";
		DataGrid dg = dataGridQuery(hql, ph, mbWarehouse, mbWarehouseDao);
		@SuppressWarnings("unchecked")
		List<TmbWarehouse> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbWarehouse t : l) {
				MbWarehouse o = new MbWarehouse();
				BeanUtils.copyProperties(t, o);

				if (o.getRegionId() != null) {
                    String regionPath = diveRegionService.getRegionPath(String.valueOf(o.getRegionId()));
                    o.setRegionPath(regionPath);
                }

				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbWarehouse mbWarehouse, Map<String, Object> params) {
		String whereHql = "";	
		if (mbWarehouse != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbWarehouse.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbWarehouse.getTenantId());
			}		
			if (!F.empty(mbWarehouse.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbWarehouse.getIsdeleted());
			}		
			if (!F.empty(mbWarehouse.getCode())) {
				whereHql += " and t.code = :code";
				params.put("code", mbWarehouse.getCode());
			}		

			if (!F.empty(mbWarehouse.getName())) {
				whereHql += " and t.name LIKE :name";
				params.put("name", mbWarehouse.getName() + "%");
			}
			if (!F.empty(mbWarehouse.getRegionId())) {
				whereHql += " and t.regionId = :regionId";
				params.put("regionId", mbWarehouse.getRegionId());
			}		
			if (!F.empty(mbWarehouse.getAddress())) {
				whereHql += " and t.address = :address";
				params.put("address", mbWarehouse.getAddress());
			}		
			if (!F.empty(mbWarehouse.getWarehouseType())) {
				whereHql += " and t.warehouseType = :warehouseType";
				params.put("warehouseType", mbWarehouse.getWarehouseType());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbWarehouse mbWarehouse) {
		TmbWarehouse t = new TmbWarehouse();
		BeanUtils.copyProperties(mbWarehouse, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbWarehouseDao.save(t);
	}

	@Override
	public MbWarehouse get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbWarehouse t = mbWarehouseDao.get("from TmbWarehouse t  where t.id = :id", params);
		MbWarehouse o = new MbWarehouse();
		BeanUtils.copyProperties(t, o);
		return o;
	}

    @Override
    public MbWarehouse getFromCache(Integer id) {
        TmbWarehouse tmbWarehouse = mbWarehouseDao.getById(id);
        MbWarehouse mbWarehouse = new MbWarehouse();
        BeanUtils.copyProperties(tmbWarehouse, mbWarehouse);
        return mbWarehouse;
    }

    @Override
	public void edit(MbWarehouse mbWarehouse) {
		TmbWarehouse t = mbWarehouseDao.get(TmbWarehouse.class, mbWarehouse.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbWarehouse, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbWarehouseDao.executeHql("update TmbWarehouse t set t.isdeleted = 1 where t.id = :id",params);
		//mbWarehouseDao.delete(mbWarehouseDao.get(TmbWarehouse.class, id));
	}

	@Override
	public MbWarehouse getByCode(String code) {
		Map<String, Object> params = new HashMap<>();
		params.put("code", code);
		List<TmbWarehouse> warehouseList = mbWarehouseDao.find("from TmbWarehouse t where t.code = :code", params);
		if (warehouseList.size() == 0) {
			return null;
		}else{
			MbWarehouse mbWarehouse = new MbWarehouse();
			BeanUtils.copyProperties(warehouseList.get(0), mbWarehouse);
			return mbWarehouse;
		}
	}

	@Override
	public boolean isWarehouseExists(MbWarehouse mbWarehouse) {
		Map<String, Object> params = new HashMap<>();
		params.put("code", mbWarehouse.getCode());
		List<TmbWarehouse> warehouseList = mbWarehouseDao.find("from TmbWarehouse t where t.code = :code", params);
		if (warehouseList.size() == 0) {
		    return false;
        }
		return true;
	}

	public List<TmbWarehouse> getWarehouseListByWarehouseType(String warehouseType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("warehouseType", warehouseType);
		List<TmbWarehouse> warehouseList = mbWarehouseDao.find("from TmbWarehouse w  where w.isdeleted = 0 and w.warehouseType = :warehouseType", params);
		return warehouseList;
	}
}
