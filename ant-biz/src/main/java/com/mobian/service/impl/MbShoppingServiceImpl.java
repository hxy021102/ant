package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbShoppingDaoI;
import com.mobian.model.TmbShopping;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbShopping;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbShoppingServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbShoppingServiceImpl extends BaseServiceImpl<MbShopping> implements MbShoppingServiceI {

	@Autowired
	private MbShoppingDaoI mbShoppingDao;

	@Override
	public DataGrid dataGrid(MbShopping mbShopping, PageHelper ph) {
		List<MbShopping> ol = new ArrayList<MbShopping>();
		String hql = " from TmbShopping t ";
		DataGrid dg = dataGridQuery(hql, ph, mbShopping, mbShoppingDao);
		@SuppressWarnings("unchecked")
		List<TmbShopping> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbShopping t : l) {
				MbShopping o = new MbShopping();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbShopping mbShopping, Map<String, Object> params) {
		String whereHql = "";	
		if (mbShopping != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbShopping.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbShopping.getTenantId());
			}		
			if (!F.empty(mbShopping.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbShopping.getIsdeleted());
			}		
			if (!F.empty(mbShopping.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", mbShopping.getUserId());
			}		
			if (!F.empty(mbShopping.getItemId())) {
				whereHql += " and t.itemId = :itemId";
				params.put("itemId", mbShopping.getItemId());
			}		
			if (!F.empty(mbShopping.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", mbShopping.getQuantity());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbShopping mbShopping) {
		TmbShopping t = new TmbShopping();
		BeanUtils.copyProperties(mbShopping, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbShoppingDao.save(t);
	}

	@Override
	public MbShopping get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbShopping t = mbShoppingDao.get("from TmbShopping t  where t.id = :id", params);
		MbShopping o = new MbShopping();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbShopping mbShopping) {
		TmbShopping t = mbShoppingDao.get(TmbShopping.class, mbShopping.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbShopping, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbShoppingDao.executeHql("update TmbShopping t set t.isdeleted = 1 where t.id = :id",params);
		//mbShoppingDao.delete(mbShoppingDao.get(TmbShopping.class, id));
	}

	@Override
	public MbShopping get(MbShopping mbShopping) {
		String whereHql = "";
		if(mbShopping != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			if(!F.empty(mbShopping.getUserId()) && !F.empty(mbShopping.getItemId())) {
				whereHql += " where t.userId = :userId and t.itemId = :itemId";
				params.put("userId", mbShopping.getUserId());
				params.put("itemId", mbShopping.getItemId());
			}
			TmbShopping t = mbShoppingDao.get("from TmbShopping t" + whereHql, params);
			if(t != null && t.getId() != null) {
				MbShopping o = new MbShopping();
				BeanUtils.copyProperties(t, o);
				return o;
			}
		}
		return null;
	}

	@Override
	public void editMbShopping(MbShopping mbShopping) {
		TmbShopping t = mbShoppingDao.get(TmbShopping.class, mbShopping.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbShopping, t, new String[]{"id", "addtime", "updatetime"}, true);
		}
	}

	@Override
	public void deleteMbShopping(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("quantity", 0);
		mbShoppingDao.executeHql("update TmbShopping t set t.isdeleted=1, t.quantity = :quantity where t.id = :id",params);
	}

	@Override
	public Long count(Integer userId) {
		return mbShoppingDao.count("select count(*) from TmbShopping t where t.isdeleted = 0 and t.userId=" + userId);
	}

}
