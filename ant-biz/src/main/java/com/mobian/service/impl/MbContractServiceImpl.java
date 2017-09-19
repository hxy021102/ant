package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbContractDaoI;
import com.mobian.model.TmbContract;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbContract;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.MbContractServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MbContractServiceImpl extends BaseServiceImpl<MbContract> implements MbContractServiceI {

	@Autowired
	private MbContractDaoI mbContractDao;

	@Autowired
	private MbShopServiceI mbShopService;

	@Override
	public DataGrid dataGrid(MbContract mbContract, PageHelper ph) {
		List<MbContract> ol = new ArrayList<MbContract>();
		String hql = " from TmbContract t ";
		DataGrid dg = dataGridQuery(hql, ph, mbContract, mbContractDao);
		@SuppressWarnings("unchecked")
		List<TmbContract> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbContract t : l) {
				MbContract o = new MbContract();
				BeanUtils.copyProperties(t, o);

				if (o.getShopId() != null) {
                    MbShop shop = mbShopService.getFromCache(o.getShopId());
                    if (shop != null) {
                        o.setShopName(shop.getName());
                    }
                }

				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbContract mbContract, Map<String, Object> params) {
		String whereHql = "";	
		if (mbContract != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbContract.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbContract.getTenantId());
			}		
			if (!F.empty(mbContract.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbContract.getIsdeleted());
			}		
			if (!F.empty(mbContract.getCode())) {
				whereHql += " and t.code = :code";
				params.put("code", mbContract.getCode());
			}		
			if (!F.empty(mbContract.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", mbContract.getName());
			}		
			if (!F.empty(mbContract.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", mbContract.getShopId());
			}		
			if (!F.empty(mbContract.getValid())) {
				whereHql += " and t.valid = :valid";
				params.put("valid", mbContract.getValid());
			}		
			if (!F.empty(mbContract.getAttachment())) {
				whereHql += " and t.attachment = :attachment";
				params.put("attachment", mbContract.getAttachment());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbContract mbContract) {
		TmbContract t = new TmbContract();
		BeanUtils.copyProperties(mbContract, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbContractDao.save(t);
	}

	@Override
	public MbContract get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbContract t = mbContractDao.get("from TmbContract t  where t.id = :id", params);
		MbContract o = new MbContract();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public MbContract getFromCache(Integer id) {
		TmbContract source = mbContractDao.getById(id);
		MbContract target = new MbContract();
		BeanUtils.copyProperties(source, target);
		return target;
	}

	@Override
	public boolean isContractExists(MbContract mbContract) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", mbContract.getCode());
		List<TmbContract> contractList = mbContractDao.find("from TmbContract t where t.code = :code", params);
		if (contractList.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public void edit(MbContract mbContract) {
		TmbContract t = mbContractDao.get(TmbContract.class, mbContract.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbContract, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbContractDao.executeHql("update TmbContract t set t.isdeleted = 1 where t.id = :id",params);
		//mbContractDao.delete(mbContractDao.get(TmbContract.class, id));
	}

	@Override
	public MbContract getNewMbContract(Integer shopId) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(d);
		List<TmbContract> l = mbContractDao.find("from TmbContract t where t.isdeleted = 0 and valid = 1 and shopId=" + shopId + " and expiryDateStart <= '" + now + "' and expiryDateEnd >= '" + now + "' order by t.addtime desc", 1, 1);
		if(l != null && l.size() > 0) {
			MbContract o = new MbContract();
			BeanUtils.copyProperties(l.get(0), o);
			return o;
		}
		return null;
	}

	@Override
	public List<TmbContract> queryAllMbContract() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(d);
		return mbContractDao.find("from TmbContract t where t.isdeleted = 0 and valid = 1 and expiryDateStart <= '" + now + "' and expiryDateEnd >= '" + now + "' order by t.updatetime asc");
	}

}
