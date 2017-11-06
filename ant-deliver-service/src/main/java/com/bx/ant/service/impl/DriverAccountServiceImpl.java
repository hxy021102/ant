package com.bx.ant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.bx.ant.dao.DriverAccountDaoI;
import com.bx.ant.model.TdriverAccount;
import com.bx.ant.pageModel.DriverAccount;
import com.bx.ant.pageModel.DriverAccountView;
import com.bx.ant.service.DriverAccountServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.pageModel.User;
import com.mobian.service.UserServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverAccountServiceImpl extends BaseServiceImpl<DriverAccount> implements DriverAccountServiceI {

	@Autowired
	private DriverAccountDaoI driverAccountDao;

	@Autowired
	private UserServiceI userService;

	@Override
	public DataGrid dataGrid(DriverAccount driverAccount, PageHelper ph) {
		List<DriverAccount> ol = new ArrayList<DriverAccount>();
		String hql = " from TdriverAccount t ";
		DataGrid dg = dataGridQuery(hql, ph, driverAccount, driverAccountDao);
		@SuppressWarnings("unchecked")
		List<TdriverAccount> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TdriverAccount t : l) {
				DriverAccount o = new DriverAccount();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(DriverAccount driverAccount, Map<String, Object> params) {
		String whereHql = "";	
		if (driverAccount != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(driverAccount.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", driverAccount.getTenantId());
			}		
			if (!F.empty(driverAccount.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", driverAccount.getIsdeleted());
			}		
			if (!F.empty(driverAccount.getUserName())) {
				whereHql += " and t.userName = :userName";
				params.put("userName", driverAccount.getUserName());
			}		
			if (!F.empty(driverAccount.getPassword())) {
				whereHql += " and t.password = :password";
				params.put("password", driverAccount.getPassword());
			}		
			if (!F.empty(driverAccount.getNickName())) {
				whereHql += " and t.nickName = :nickName";
				params.put("nickName", driverAccount.getNickName());
			}		
			if (!F.empty(driverAccount.getIcon())) {
				whereHql += " and t.icon = :icon";
				params.put("icon", driverAccount.getIcon());
			}		
			if (!F.empty(driverAccount.getSex())) {
				whereHql += " and t.sex = :sex";
				params.put("sex", driverAccount.getSex());
			}		
			if (!F.empty(driverAccount.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", driverAccount.getRefId());
			}		
			if (!F.empty(driverAccount.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", driverAccount.getRefType());
			}		
			if (!F.empty(driverAccount.getConactPhone())) {
				whereHql += " and t.conactPhone = :conactPhone";
				params.put("conactPhone", driverAccount.getConactPhone());
			}		
			if (!F.empty(driverAccount.getType())) {
				whereHql += " and t.type = :type";
				params.put("type", driverAccount.getType());
			}		
			if (!F.empty(driverAccount.getHandleStatus())) {
				whereHql += " and t.handleStatus = :handleStatus";
				params.put("handleStatus", driverAccount.getHandleStatus());
			}		
			if (!F.empty(driverAccount.getHandleLoginId())) {
				whereHql += " and t.handleLoginId = :handleLoginId";
				params.put("handleLoginId", driverAccount.getHandleLoginId());
			}		
			if (!F.empty(driverAccount.getHandleRemark())) {
				whereHql += " and t.handleRemark = :handleRemark";
				params.put("handleRemark", driverAccount.getHandleRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(DriverAccount driverAccount) {
		TdriverAccount t = new TdriverAccount();
		BeanUtils.copyProperties(driverAccount, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		driverAccountDao.save(t);
		driverAccount.setId(t.getId());
	}

	@Override
	public DriverAccount get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TdriverAccount t = driverAccountDao.get("from TdriverAccount t  where t.id = :id", params);
		DriverAccount o = new DriverAccount();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(DriverAccount driverAccount) {
		TdriverAccount t = driverAccountDao.get(TdriverAccount.class, driverAccount.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(driverAccount, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		driverAccountDao.executeHql("update TdriverAccount t set t.isdeleted = 1 where t.id = :id",params);
		//driverAccountDao.delete(driverAccountDao.get(TdriverAccount.class, id));
	}

	@Override
	public DriverAccountView getView(Integer id) {
		DriverAccount driverAccount = get(id);
		DriverAccountView driverAccountView = new DriverAccountView();
		if (driverAccount != null) {
			BeanUtils.copyProperties(driverAccount, driverAccountView);
			fillUserInfo(driverAccountView);
		}
		return driverAccountView;
	}

	@Override
	public DataGrid dataGridView(DriverAccount driverAccount, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		List<DriverAccount> driverAccounts = dataGrid(driverAccount, pageHelper).getRows();
		List<DriverAccountView> ol = new ArrayList<DriverAccountView>();
		if (CollectionUtils.isNotEmpty(driverAccounts)) {
			int size = driverAccounts.size();
			for (int i = 0 ; i < size; i++) {
				DriverAccountView o = new DriverAccountView();
				BeanUtils.copyProperties(driverAccounts.get(i), o);
				fillUserInfo(o);
				ol.add(o);
			}
			dataGrid.setRows(ol);
		}
		return dataGrid;
	}

	protected void fillUserInfo(DriverAccountView driverAccountView) {
		if (!F.empty(driverAccountView.getHandleLoginId())){
			User user  = userService.getFromCache(driverAccountView.getHandleLoginId());
			if (user != null) {
				driverAccountView.setHandleLoginName(user.getName());
			}
		}
	}
	@Override
	public DriverAccount getByRef(String refId, String refType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refId", refId);
		params.put("refType", refType);
		TdriverAccount t = driverAccountDao.get("from TDriverAccount t  where t.isdeleted = 0 and t.refId = :refId and t.refType = :refType", params);
		if(t != null) {
			DriverAccount o = new DriverAccount();
			BeanUtils.copyProperties(t, o);
			return o;
		}
		return null;
	}
}
