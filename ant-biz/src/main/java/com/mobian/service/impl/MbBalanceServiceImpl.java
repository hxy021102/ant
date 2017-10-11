package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbBalanceDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbBalance;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
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
public class MbBalanceServiceImpl extends BaseServiceImpl<MbBalance> implements MbBalanceServiceI {

	@Autowired
	private MbBalanceDaoI mbBalanceDao;

	@Autowired
	private MbShopServiceI mbShopService;
	@Autowired
	private MbBalanceLogServiceI mbBalanceLogService;

	@Override
	public DataGrid dataGrid(MbBalance mbBalance, PageHelper ph) {
		List<MbBalance> ol = new ArrayList<MbBalance>();
		String hql = " from TmbBalance t ";
		DataGrid dg = dataGridQuery(hql, ph, mbBalance, mbBalanceDao);
		@SuppressWarnings("unchecked")
		List<TmbBalance> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbBalance t : l) {
				MbBalance o = new MbBalance();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbBalance mbBalance, Map<String, Object> params) {
		String whereHql = "";	
		if (mbBalance != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbBalance.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbBalance.getTenantId());
			}		
			if (!F.empty(mbBalance.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbBalance.getIsdeleted());
			}		
			if (!F.empty(mbBalance.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", mbBalance.getAmount());
			}		
			if (!F.empty(mbBalance.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", mbBalance.getRefId());
			}		
			if (!F.empty(mbBalance.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", mbBalance.getRefType());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbBalance mbBalance) {
		TmbBalance t = new TmbBalance();
		BeanUtils.copyProperties(mbBalance, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbBalanceDao.save(t);
		mbBalance.setId(t.getId());
	}

	@Override
	public MbBalance get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbBalance t = mbBalanceDao.get("from TmbBalance t  where t.id = :id", params);
		MbBalance o = new MbBalance();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbBalance mbBalance) {
		TmbBalance t = mbBalanceDao.get(TmbBalance.class, mbBalance.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbBalance, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbBalanceDao.executeHql("update TmbBalance t set t.isdeleted = 1 where t.id = :id",params);
		//mbBalanceDao.delete(mbBalanceDao.get(TmbBalance.class, id));
	}

	@Override
	public MbBalance addOrGetMbBalance(Integer refId) {
		return addOrGetMbBalance(refId,1,0);
	}

	@Override
	public MbBalance queryByShopId(Integer shopId) {
		MbShop mbShop = mbShopService.getFromCache(shopId);
		if (mbShop != null && !F.empty(mbShop.getParentId()) && mbShop.getParentId() != -1) {
			shopId = mbShop.getParentId();
		}
		TmbBalance tmbBalance = mbBalanceDao.get("from TmbBalance t where t.refType = 1 and t.refId=" + shopId);
		if (tmbBalance != null) {
		    MbBalance mbBalance = new MbBalance();
		    BeanUtils.copyProperties(tmbBalance, mbBalance);
		    return mbBalance;
        }
		return null;
	}

	@Override
	public MbBalance addOrGetMbBalanceCash(Integer shopId) {
		return addOrGetMbBalance(shopId,4,-10000000);
	}
	@Override
	public MbBalance addOrGetMbBalanceDelivery(Integer shopId) {
		return addOrGetMbBalance(shopId,10,0);
	}


	@Override
	public MbBalance getCashByShopId(Integer shopId) {
		MbShop mbShop = mbShopService.getFromCache(shopId);
		if (mbShop != null && !F.empty(mbShop.getParentId()) && mbShop.getParentId() != -1) {
			shopId = mbShop.getParentId();
		}
		TmbBalance tmbBalance = mbBalanceDao.get("from TmbBalance t where t.refType = 4 and t.refId=" + shopId);
		if (tmbBalance != null) {
			MbBalance mbBalance = new MbBalance();
			BeanUtils.copyProperties(tmbBalance, mbBalance);
			return mbBalance;
		}
		return null;
	}

	@Override
	public List<MbBalance> queryByrefTypeAndAmount(Integer refType, Integer amount) {
		Map<String,Object> params=new HashMap<String,Object>();
		List<MbBalance> mbBalanceList=new ArrayList<MbBalance>();
		params.put("refType",refType);
		params.put("amount",amount);
		List<TmbBalance> tmbBalances=mbBalanceDao.find("from TmbBalance t  where  t.isdeleted = 0 and t.refType = :refType and t.amount <:amount", params);
		if(!CollectionUtils.isEmpty(tmbBalances)){
           for(TmbBalance tmbBalance : tmbBalances){
			   MbBalance mbBalance = new MbBalance();
			   BeanUtils.copyProperties(tmbBalance, mbBalance);
			   mbBalanceList.add(mbBalance);
		   }
		   return  mbBalanceList;
		}
		return null;
	}

	@Override
	public List<MbBalance> queryBalanceListByShopId(Integer shopId) {
		MbShop mbShop = mbShopService.getFromCache(shopId);
		if (mbShop != null && !F.empty(mbShop.getParentId()) && mbShop.getParentId() != -1) {
			shopId = mbShop.getParentId();
		}
		List<TmbBalance> balances = mbBalanceDao.find("from TmbBalance t where t.isdeleted = 0 and refId=" + shopId);
		List<MbBalance> mbBalances = new ArrayList<MbBalance>();
		if (!CollectionUtils.isEmpty(balances)) {
			for (TmbBalance t : balances) {
				MbBalance o = new MbBalance();
				BeanUtils.copyProperties(t, o);
				mbBalances.add(o);
			}
			return mbBalances;
		}
		return null;
	}
	@Override
	public MbBalance addOrGetMbBalance(Integer refId, Integer refType, Integer initAmount) {
		MbShop mbShop = mbShopService.getFromCache(refId);
		if (mbShop != null && !F.empty(mbShop.getParentId()) && mbShop.getParentId() != -1) {
			refId = mbShop.getParentId();
		}
		MbBalance o;
		TmbBalance t = mbBalanceDao.get("from TmbBalance t where t.isdeleted = 0 and t.refType = " + refType + " and refId=" + refId);
		if(t != null && t.getId() != null) {
			o = new MbBalance();
			BeanUtils.copyProperties(t, o);
		} else {
			if(refId == null)
				throw new ServiceException("shopId 不能为空");
			if(refType == null)
				throw new ServiceException("refType 不能为空");
			if(refType == null)
				throw new ServiceException("initAmount 不能为空");
			o = new MbBalance();
			o.setAmount(initAmount);
			o.setRefId(refId);
			o.setRefType(refType);
			add(o);
		}
		return o;
	}

	/**
	 *
	 * @param shopId
	 * @param amount
	 * @param balanceSourceType
	 * @param balanceTargetType
	 */
	@Override
	public void transform(Integer shopId, Integer amount, Integer balanceSourceType, Integer balanceTargetType, Integer initTargetMoney) {

		//判定是否自身
		if (balanceSourceType == balanceTargetType)
			throw new ServiceException("不能转移自身余额");

		//获取目标
		MbBalance balance = new MbBalance();
		balance.setRefId(shopId);
		balance.setRefType(balanceSourceType);
		List<MbBalance> balanceList = dataGrid(balance, new PageHelper()).getRows();
		if (CollectionUtils.isEmpty(balanceList))
			throw new ServiceException("源账户不得为空");
		MbBalance balanceSource = balanceList.get(0);
		MbBalance balanceTarget = addOrGetMbBalance(shopId, balanceTargetType, initTargetMoney);

		//判定金额是否合规
		if (amount < 0)
			throw new ServiceException("转移金额必须为整数");
		if (balanceSource.getAmount() - amount < 0)
			throw new ServiceException("源账户余额不足");

		//转移金额
		transform(amount, balanceSource, balanceTarget);
	}

	@Override
	public void transform(Integer amount, MbBalance balanceSource, MbBalance balanceTarget) {
		//源账户减少金额
		MbBalanceLog mbBalanceLogSource = new MbBalanceLog();
		mbBalanceLogSource.setBalanceId(balanceSource.getId());
		mbBalanceLogSource.setAmount( -amount);
		mbBalanceLogSource.setRefId(balanceTarget.getId() + "");
		mbBalanceLogSource.setRefType("BT050");
		mbBalanceLogSource.setReason("门店账户金额转出");

		//目标账户增加金额
		MbBalanceLog mbBalanceLogTarget = new MbBalanceLog();
		mbBalanceLogTarget.setBalanceId(balanceTarget.getId());
		mbBalanceLogTarget.setAmount( +amount);
		mbBalanceLogTarget.setRefId(balanceSource.getId() + "");
		mbBalanceLogTarget.setRefType("BT050");
		mbBalanceLogTarget.setReason("门店账户金额转入");

		mbBalanceLogService.addAndUpdateBalance(mbBalanceLogSource);
		mbBalanceLogService.addAndUpdateBalance(mbBalanceLogTarget);
	}
}
