package com.bx.ant.service.impl;

import com.bx.ant.dao.ShopDeliverAccountDaoI;
import com.bx.ant.model.TshopDeliverAccount;
import com.bx.ant.service.ShopDeliverAccountServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.pageModel.ShopDeliverAccount;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopDeliverAccountServiceImpl extends BaseServiceImpl<ShopDeliverAccount> implements ShopDeliverAccountServiceI {

	@Autowired
	private ShopDeliverAccountDaoI shopDeliverAccountDao;

	@Override
	public DataGrid dataGrid(ShopDeliverAccount shopDeliverAccount, PageHelper ph) {
		List<ShopDeliverAccount> ol = new ArrayList<ShopDeliverAccount>();
		String hql = " from TshopDeliverAccount t ";
		DataGrid dg = dataGridQuery(hql, ph, shopDeliverAccount, shopDeliverAccountDao);
		@SuppressWarnings("unchecked")
		List<TshopDeliverAccount> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TshopDeliverAccount t : l) {
				ShopDeliverAccount o = new ShopDeliverAccount();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ShopDeliverAccount shopDeliverAccount, Map<String, Object> params) {
		String whereHql = "";	
		if (shopDeliverAccount != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(shopDeliverAccount.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", shopDeliverAccount.getTenantId());
			}		
			if (!F.empty(shopDeliverAccount.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", shopDeliverAccount.getIsdeleted());
			}		
			if (!F.empty(shopDeliverAccount.getUserName())) {
				whereHql += " and t.userName = :userName";
				params.put("userName", shopDeliverAccount.getUserName());
			}		
			if (!F.empty(shopDeliverAccount.getPassword())) {
				whereHql += " and t.password = :password";
				params.put("password", shopDeliverAccount.getPassword());
			}		
			if (!F.empty(shopDeliverAccount.getNickName())) {
				whereHql += " and t.nickName = :nickName";
				params.put("nickName", shopDeliverAccount.getNickName());
			}		
			if (!F.empty(shopDeliverAccount.getIcon())) {
				whereHql += " and t.icon = :icon";
				params.put("icon", shopDeliverAccount.getIcon());
			}		
			if (!F.empty(shopDeliverAccount.getSex())) {
				whereHql += " and t.sex = :sex";
				params.put("sex", shopDeliverAccount.getSex());
			}		
			if (!F.empty(shopDeliverAccount.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", shopDeliverAccount.getRefId());
			}		
			if (!F.empty(shopDeliverAccount.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", shopDeliverAccount.getRefType());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(ShopDeliverAccount shopDeliverAccount) {
		TshopDeliverAccount t = new TshopDeliverAccount();
		BeanUtils.copyProperties(shopDeliverAccount, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		shopDeliverAccountDao.save(t);
		shopDeliverAccount.setId(t.getId());
	}

	@Override
	public ShopDeliverAccount get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TshopDeliverAccount t = shopDeliverAccountDao.get("from TshopDeliverAccount t  where t.id = :id", params);
		ShopDeliverAccount o = new ShopDeliverAccount();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ShopDeliverAccount shopDeliverAccount) {
		TshopDeliverAccount t = shopDeliverAccountDao.get(TshopDeliverAccount.class, shopDeliverAccount.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(shopDeliverAccount, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		shopDeliverAccountDao.executeHql("update TshopDeliverAccount t set t.isdeleted = 1 where t.id = :id",params);
		//shopDeliverAccountDao.delete(shopDeliverAccountDao.get(TshopDeliverAccount.class, id));
	}

	@Override
	public ShopDeliverAccount getByRef(String refId, String refType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refId", refId);
		params.put("refType", refType);
		TshopDeliverAccount t = shopDeliverAccountDao.get("from TshopDeliverAccount t  where t.isdeleted = 0 and t.refId = :refId and t.refType = :refType", params);
		if(t != null) {
			ShopDeliverAccount o = new ShopDeliverAccount();
			BeanUtils.copyProperties(t, o);
			return o;
		}
		return null;
	}

	@Override
	public boolean checkUserName(String userName) {
		if (!F.empty(userName)) {
			List<TshopDeliverAccount> l = shopDeliverAccountDao.find("from TshopDeliverAccount t where t.isdeleted = 0 and t.userName = '" + userName + "'", 1, 1);
			if (l != null || l.size() < 1) {
				return false;
			}
		}
		return true;
	}

}
