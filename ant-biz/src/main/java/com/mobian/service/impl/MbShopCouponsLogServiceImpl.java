package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbShopCouponsLogDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbShopCouponsLog;
import com.mobian.pageModel.*;
import com.mobian.service.MbShopCouponsLogServiceI;
import com.mobian.service.MbShopCouponsServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbShopCouponsLogServiceImpl extends BaseServiceImpl<MbShopCouponsLog> implements MbShopCouponsLogServiceI {

	@Autowired
	private MbShopCouponsLogDaoI mbShopCouponsLogDao;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private MbShopCouponsServiceI mbShopCouponsService;

	@Override
	public DataGrid dataGrid(MbShopCouponsLog mbShopCouponsLog, PageHelper ph) {
		List<MbShopCouponsLog> ol = new ArrayList<MbShopCouponsLog>();
		String hql = " from TmbShopCouponsLog t ";
		DataGrid dg = dataGridQuery(hql, ph, mbShopCouponsLog, mbShopCouponsLogDao);
		@SuppressWarnings("unchecked")
		List<TmbShopCouponsLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbShopCouponsLog t : l) {
				MbShopCouponsLog o = new MbShopCouponsLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbShopCouponsLog mbShopCouponsLog, Map<String, Object> params) {
		String whereHql = "";	
		if (mbShopCouponsLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbShopCouponsLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbShopCouponsLog.getTenantId());
			}		
			if (!F.empty(mbShopCouponsLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbShopCouponsLog.getIsdeleted());
			}		
			if (!F.empty(mbShopCouponsLog.getShopCouponsId())) {
				whereHql += " and t.shopCouponsId = :shopCouponsId";
				params.put("shopCouponsId", mbShopCouponsLog.getShopCouponsId());
			}		
			if (!F.empty(mbShopCouponsLog.getQuantityUsed())) {
				whereHql += " and t.quantityUsed = :quantityUsed";
				params.put("quantityUsed", mbShopCouponsLog.getQuantityUsed());
			}
			if (!F.empty(mbShopCouponsLog.getQuantityUsed())) {
				whereHql += " and t.quantityTotal = :quantityTotal";
				params.put("quantityTotal", mbShopCouponsLog.getQuantityTotal());
			}
			if (!F.empty(mbShopCouponsLog.getLoginId())) {
				whereHql += " and t.loginId = :loginId";
				params.put("loginId", mbShopCouponsLog.getLoginId());
			}		
			if (!F.empty(mbShopCouponsLog.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", mbShopCouponsLog.getRefId());
			}		
			if (!F.empty(mbShopCouponsLog.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", mbShopCouponsLog.getRefType());
			}		
			if (!F.empty(mbShopCouponsLog.getReason())) {
				whereHql += " and t.reason = :reason";
				params.put("reason", mbShopCouponsLog.getReason());
			}		
			if (!F.empty(mbShopCouponsLog.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbShopCouponsLog.getRemark());
			}
			if (!F.empty(mbShopCouponsLog.getShopCouponsStatus())) {
				whereHql += " and t.shopCouponsStatus = :shopCouponsStatus";
				params.put("shopCouponsStatus", mbShopCouponsLog.getShopCouponsStatus());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbShopCouponsLog mbShopCouponsLog) {
		TmbShopCouponsLog t = new TmbShopCouponsLog();
		BeanUtils.copyProperties(mbShopCouponsLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbShopCouponsLogDao.save(t);
	}

	@Override
	public MbShopCouponsLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbShopCouponsLog t = mbShopCouponsLogDao.get("from TmbShopCouponsLog t  where t.id = :id", params);
		MbShopCouponsLog o = new MbShopCouponsLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbShopCouponsLog mbShopCouponsLog) {
		TmbShopCouponsLog t = mbShopCouponsLogDao.get(TmbShopCouponsLog.class, mbShopCouponsLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbShopCouponsLog, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbShopCouponsLogDao.executeHql("update TmbShopCouponsLog t set t.isdeleted = 1 where t.id = :id",params);
		//mbShopCouponsLogDao.delete(mbShopCouponsLogDao.get(TmbShopCouponsLog.class, id));
	}
	@Override
	public void addLogAndUpdateShopCoupons(MbShopCouponsLog mbShopCouponsLog) {
		add(mbShopCouponsLog);
		String hqlValue = "  ";
		if (mbShopCouponsLog.getQuantityTotal() != null) hqlValue += " t.quantityTotal=t.quantityTotal+"+ mbShopCouponsLog.getQuantityTotal() + ",";
		if (mbShopCouponsLog.getQuantityUsed() !=null) hqlValue += " t.quantityUsed=t.quantityUsed+"+mbShopCouponsLog.getQuantityUsed() + ",";
		if (mbShopCouponsLog.getShopCouponsStatus() != null) hqlValue += " t.status='" + mbShopCouponsLog.getShopCouponsStatus()+"',";
		if (mbShopCouponsLog.getRemark() != null) hqlValue += " t.remark='" + mbShopCouponsLog.getRemark()+"',";
		String hql = "update TmbShopCoupons t set "
				+ hqlValue.substring(0,hqlValue.length()-1) + " where t.id=" + mbShopCouponsLog.getShopCouponsId();
		int i = mbShopCouponsLogDao.executeHql(hql);
		if (i != 1) {
			throw new ServiceException("门店券更新失败");
		}
	}

	@Override
	public void updateLogAndShopCoupons(MbShopCouponsLog mbShopCouponsLog) {
		edit(mbShopCouponsLog);
		if(!mbShopCouponsLog.getIsdeleted()) {
			String hqlValue = "  ";
			if (mbShopCouponsLog.getQuantityTotal() != null) hqlValue += " t.quantityTotal=t.quantityTotal+"+ mbShopCouponsLog.getQuantityTotal() + ",";
			if (mbShopCouponsLog.getQuantityUsed() !=null) hqlValue += " t.quantityUsed=t.quantityUsed+"+mbShopCouponsLog.getQuantityUsed() + ",";
			if (mbShopCouponsLog.getShopCouponsStatus() != null) hqlValue += " t.status='" + mbShopCouponsLog.getShopCouponsStatus()+"',";
			if (mbShopCouponsLog.getRemark() != null) hqlValue += " t.remark='" + mbShopCouponsLog.getRemark()+"',";
			String hql = "update TmbShopCoupons t set "
					+ hqlValue.substring(0,hqlValue.length()-1) + " where t.id=" + mbShopCouponsLog.getShopCouponsId();
			int i = mbShopCouponsLogDao.executeHql(hql);
			if (i != 1) {
				throw new ServiceException("门店券更新失败");
			}
		}
	}
	@Override
	public DataGrid dataGridShopCouponsLogView(MbShopCouponsLog shopCouponsLog) {
		List<MbShopCouponsLog> ol = new ArrayList<MbShopCouponsLog>();
		PageHelper ph = new PageHelper();
		ph.setSort("updatetime");
		ph.setOrder("desc");
		String hql = " from TmbShopCouponsLog t ";
		DataGrid dg = dataGridQuery(hql, ph, shopCouponsLog, mbShopCouponsLogDao);
		@SuppressWarnings("unchecked")
		List<TmbShopCouponsLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbShopCouponsLog t : l) {
				MbShopCouponsLogView o = new MbShopCouponsLogView();
				BeanUtils.copyProperties(t, o);
				fillMbShopCouponsLogView(o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	private void fillMbShopCouponsLogView(MbShopCouponsLogView shopCouponsLogView) {
		fillUserInfo(shopCouponsLogView);
		fillCouponsInfo(shopCouponsLogView);
	}
	private void fillUserInfo(MbShopCouponsLogView shopCouponsLogView) {
		if (!F.empty(shopCouponsLogView.getLoginId())) {
			User user = userService.get(shopCouponsLogView.getLoginId());
			if (user != null) {
				shopCouponsLogView.setLoginName(user.getName());
			}
		}
	}
	private void fillCouponsInfo(MbShopCouponsLogView shopCouponsLogView) {
		if (!F.empty(shopCouponsLogView.getShopCouponsId())) {
			MbShopCouponsView shopCouponsView = mbShopCouponsService.getShopCouponsView(shopCouponsLogView.getShopCouponsId());
			if (shopCouponsView != null) {
				shopCouponsLogView.setCouponsName(shopCouponsView.getCouponsName());
			}
		}
	}
	@Override
	public List<MbShopCouponsLogView> listShopCouponsLogView(MbShopCouponsLog shopCouponsLog) {
		return dataGridShopCouponsLogView(shopCouponsLog).getRows();
	}

}
