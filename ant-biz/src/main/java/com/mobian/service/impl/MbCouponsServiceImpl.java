package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbCouponsDaoI;
import com.mobian.model.TmbCoupons;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbCoupons;
import com.mobian.pageModel.MbCouponsItem;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbCouponsItemServiceI;
import com.mobian.service.MbCouponsServiceI;
import com.mobian.service.MbItemServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbCouponsServiceImpl extends BaseServiceImpl<MbCoupons> implements MbCouponsServiceI {

	@Autowired
	private MbCouponsDaoI mbCouponsDao;
	@Autowired
	private MbItemServiceI mbItemService;
	@Autowired
	private MbCouponsItemServiceI mbCouponsItemService;

	@Override
	public DataGrid dataGrid(MbCoupons mbCoupons, PageHelper ph) {
		List<MbCoupons> ol = new ArrayList<MbCoupons>();
		String hql = " from TmbCoupons t ";
		DataGrid dg = dataGridQuery(hql, ph, mbCoupons, mbCouponsDao);
		@SuppressWarnings("unchecked")
		List<TmbCoupons> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbCoupons t : l) {
				MbCoupons o = new MbCoupons();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbCoupons mbCoupons, Map<String, Object> params) {
		String whereHql = "";	
		if (mbCoupons != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbCoupons.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbCoupons.getTenantId());
			}		
			if (!F.empty(mbCoupons.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbCoupons.getIsdeleted());
			}		
			if (!F.empty(mbCoupons.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", mbCoupons.getName());
			}		
			if (!F.empty(mbCoupons.getCode())) {
				whereHql += " and t.code = :code";
				params.put("code", mbCoupons.getCode());
			}		
			if (!F.empty(mbCoupons.getType())) {
				whereHql += " and t.type = :type";
				params.put("type", mbCoupons.getType());
			}		
			if (!F.empty(mbCoupons.getDiscount())) {
				whereHql += " and t.discount = :discount";
				params.put("discount", mbCoupons.getDiscount());
			}		
			if (!F.empty(mbCoupons.getQuantityTotal())) {
				whereHql += " and t.quantityTotal = :quantityTotal";
				params.put("quantityTotal", mbCoupons.getQuantityTotal());
			}		
			if (!F.empty(mbCoupons.getQuantityUsed())) {
				whereHql += " and t.quantityUsed = :quantityUsed";
				params.put("quantityUsed", mbCoupons.getQuantityUsed());
			}		
			if (!F.empty(mbCoupons.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", mbCoupons.getStatus());
			}		
			if (!F.empty(mbCoupons.getMoneyThreshold())) {
				whereHql += " and t.moneyThreshold = :moneyThreshold";
				params.put("moneyThreshold", mbCoupons.getMoneyThreshold());
			}		
			if (!F.empty(mbCoupons.getDescription())) {
				whereHql += " and t.description = :description";
				params.put("description", mbCoupons.getDescription());
			}
			if (!F.empty(mbCoupons.getPrice())) {
				whereHql += " and t.price = :price";
				params.put("price", mbCoupons.getPrice());
			}
			if(!F.empty(mbCoupons.getKeyword())){
				whereHql += " and (t.name LIKE :name or code like:code)";
				params.put("name", "%" + mbCoupons.getKeyword() + "%");
				params.put("code",mbCoupons.getKeyword() + "%");
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbCoupons mbCoupons) {
		TmbCoupons t = new TmbCoupons();
		BeanUtils.copyProperties(mbCoupons, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbCouponsDao.save(t);
		mbCoupons.setId(t.getId());
	}

	@Override
	public MbCoupons get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbCoupons t = mbCouponsDao.get("from TmbCoupons t  where t.id = :id", params);
		MbCoupons o = new MbCoupons();
		BeanUtils.copyProperties(t, o);
		return o;
	}
	@Override
	public MbCoupons getFromCache(Integer id) {
		TmbCoupons source = mbCouponsDao.getById(id);
		MbCoupons target = new MbCoupons();
		BeanUtils.copyProperties(source,target);
		return target;
	}

	@Override
	public void edit(MbCoupons mbCoupons) {
		TmbCoupons t = mbCouponsDao.get(TmbCoupons.class, mbCoupons.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbCoupons, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbCouponsDao.executeHql("update TmbCoupons t set t.isdeleted = 1 where t.id = :id",params);
		//mbCouponsDao.delete(mbCouponsDao.get(TmbCoupons.class, id));
	}
	@Override
	public List<MbCoupons> listMbCoupons(MbCoupons mbCoupons){
		String hql = " from TmbCoupons t ";
		List<MbCoupons> ol= new ArrayList<MbCoupons>();
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(mbCoupons, params);
		List<TmbCoupons> ts = mbCouponsDao.find(hql + whereHql,params);
		for (TmbCoupons t : ts
				) {
			MbCoupons o = new MbCoupons();
			BeanUtils.copyProperties(t,o);
			ol.add(o);
		}
		return ol;
	}
	@Override
	public void addCouponsAndCouponsItem(MbCoupons mbCoupons, MbCouponsItem mbCouponsItem){
		add(mbCoupons);
		mbCouponsItem.setCouponsId(mbCoupons.getId());
		mbCouponsItemService.add(mbCouponsItem);
	}

	@Override
	public void  editCouponsAndCouponsItem(MbCoupons mbCoupons, MbCouponsItem mbCouponsItem) {
		edit(mbCoupons);
	}
}
