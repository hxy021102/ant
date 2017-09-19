package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbOrderCallbackItemDaoI;
import com.mobian.model.TmbOrderCallbackItem;
import com.mobian.pageModel.*;
import com.mobian.service.MbItemServiceI;
import com.mobian.service.MbOrderCallbackItemServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbOrderCallbackItemServiceImpl extends BaseServiceImpl<MbOrderCallbackItem> implements MbOrderCallbackItemServiceI {

	@Autowired
	private MbOrderCallbackItemDaoI mbOrderCallbackItemDao;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private MbItemServiceI mbItemService;

	private static Logger log = Logger.getLogger(MbOrderCallbackItemServiceImpl.class);

	@Override
	public DataGrid dataGrid(MbOrderCallbackItem mbOrderCallbackItem, PageHelper ph) {
		List<MbOrderCallbackItem> ol = new ArrayList<MbOrderCallbackItem>();
		String hql = " from TmbOrderCallbackItem t ";
		DataGrid dg = dataGridQuery(hql, ph, mbOrderCallbackItem, mbOrderCallbackItemDao);
		@SuppressWarnings("unchecked")
		List<TmbOrderCallbackItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbOrderCallbackItem t : l) {
				MbOrderCallbackItem o = new MbOrderCallbackItem();
				BeanUtils.copyProperties(t, o);

				if (o.getLoginId() != null) {
					User user = userService.get(o.getLoginId());
					if (user != null) {
						o.setLoginName(user.getName());
					}
				}
				fillOrderCallbackItem(o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	private void fillOrderCallbackItem(MbOrderCallbackItem mbOrderCallbackItem){
		fillItemInfo(mbOrderCallbackItem);
	}
	private void fillItemInfo(MbOrderCallbackItem mbOrderCallbackItem){
		if(mbOrderCallbackItem.getItemId()!=null){
			MbItem mbItem = mbItemService.get(mbOrderCallbackItem.getItemId());
			if(mbItem !=null ){
				mbOrderCallbackItem.setItemName(mbItem.getName());
			}
		}
	}

	protected String whereHql(MbOrderCallbackItem mbOrderCallbackItem, Map<String, Object> params) {
		String whereHql = "";	
		if (mbOrderCallbackItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbOrderCallbackItem.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbOrderCallbackItem.getTenantId());
			}		
			if (!F.empty(mbOrderCallbackItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbOrderCallbackItem.getIsdeleted());
			}		
			if (!F.empty(mbOrderCallbackItem.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", mbOrderCallbackItem.getOrderId());
			}		
			if (!F.empty(mbOrderCallbackItem.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", mbOrderCallbackItem.getItemId());
			}		
			if (!F.empty(mbOrderCallbackItem.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", mbOrderCallbackItem.getQuantity());
			}
			if (!F.empty(mbOrderCallbackItem.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbOrderCallbackItem.getRemark());
			}
			if (!F.empty(mbOrderCallbackItem.getLoginId())) {
				whereHql += " and t.loginId = :loginId";
				params.put("loginId", mbOrderCallbackItem.getLoginId());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(MbOrderCallbackItem mbOrderCallbackItem) {
		TmbOrderCallbackItem t = new TmbOrderCallbackItem();
		BeanUtils.copyProperties(mbOrderCallbackItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbOrderCallbackItemDao.save(t);
	}

	@Override
	public MbOrderCallbackItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbOrderCallbackItem t = mbOrderCallbackItemDao.get("from TmbOrderCallbackItem t  where t.id = :id", params);
		MbOrderCallbackItem o = new MbOrderCallbackItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbOrderCallbackItem mbOrderCallbackItem) {
		TmbOrderCallbackItem t = mbOrderCallbackItemDao.get(TmbOrderCallbackItem.class, mbOrderCallbackItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbOrderCallbackItem, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbOrderCallbackItemDao.executeHql("update TmbOrderCallbackItem t set t.isdeleted = 1 where t.id = :id",params);
		//mbOrderCallbackItemDao.delete(mbOrderCallbackItemDao.get(TmbOrderCallbackItem.class, id));
	}

	@Override
	public List<MbOrderCallbackItem> query(MbOrderCallbackItem mbOrderCallbackItem){
		String hql = " from TmbOrderCallbackItem t ";
		Map<String , Object> params = new HashMap<String , Object>();
		String where = whereHql(mbOrderCallbackItem,params);
		List<TmbOrderCallbackItem> l = mbOrderCallbackItemDao.find(hql + where,params);
		List<MbOrderCallbackItem> ol = new ArrayList<MbOrderCallbackItem>();
		for (TmbOrderCallbackItem t : l
			 ) {
			MbOrderCallbackItem o = new MbOrderCallbackItem();
			BeanUtils.copyProperties( t , o);
			ol.add(o);
		}
		return ol;
	}
}
