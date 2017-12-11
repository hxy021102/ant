package com.mobian.service.impl;

import com.bx.ant.pageModel.MbBalanceLogDriver;
import com.mobian.absx.F;
import com.mobian.dao.MbBalanceDaoI;
import com.mobian.dao.MbBalanceLogDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbBalance;
import com.mobian.model.TmbBalanceLog;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceLogServiceI;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbBalanceLogServiceImpl extends BaseServiceImpl<MbBalanceLog> implements MbBalanceLogServiceI {

	@Autowired
	private MbBalanceLogDaoI mbBalanceLogDao;

	@Autowired
	private MbBalanceDaoI mbBalanceDao;
	@Autowired
	private MbBalanceServiceI mbBalanceService;
	@Autowired
	private MbShopServiceI mbShopService;

	@Override
	public DataGrid dataGrid(MbBalanceLog mbBalanceLog, PageHelper ph) {
		List<MbBalanceLog> ol = new ArrayList<MbBalanceLog>();
		String hql = " from TmbBalanceLog t ";
		DataGrid dg = dataGridQuery(hql, ph, mbBalanceLog, mbBalanceLogDao);
		@SuppressWarnings("unchecked")
		List<TmbBalanceLog> l = dg.getRows();
		Integer totalAmountOut =0;
		Integer totalAmountIn =0;
		if (l != null && l.size() > 0) {
			for (TmbBalanceLog t : l) {
				MbBalanceLog o = new MbBalanceLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
				if(t.getAmount() !=null && t.getAmount()<0) {
					o.setAmountOut(t.getAmount());
                	totalAmountOut +=t.getAmount();
				}
				if(t.getAmount() !=null && t.getAmount()>=0) {
					o.setAmountIn(t.getAmount());
					totalAmountIn += t.getAmount();
				}
			}
			//合计
			List<MbBalanceLog> footer = new ArrayList<MbBalanceLog>();
			MbBalanceLog totalRow = new MbBalanceLog();
			totalRow.setRefType("合计");
			totalRow.setAmountOut(totalAmountOut);
			totalRow.setAmountIn(totalAmountIn);
			totalRow.setIsShow(true);
			footer.add(totalRow);
			dg.setFooter(footer);
		}


		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MbBalanceLog mbBalanceLog, Map<String, Object> params) {
		String whereHql = "";	
		if (mbBalanceLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbBalanceLog.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbBalanceLog.getTenantId());
			}		
			if (!F.empty(mbBalanceLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbBalanceLog.getIsdeleted());
			}		
			if (!F.empty(mbBalanceLog.getBalanceId())) {
				whereHql += " and t.balanceId = :balanceId";
				params.put("balanceId", mbBalanceLog.getBalanceId());
			}		
			if (!F.empty(mbBalanceLog.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", mbBalanceLog.getAmount());
			}		
			if (!F.empty(mbBalanceLog.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", mbBalanceLog.getRefId());
			}		
			if (!F.empty(mbBalanceLog.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", mbBalanceLog.getRefType());
			}		
			if (!F.empty(mbBalanceLog.getReason())) {
				whereHql += " and t.reason = :reason";
				params.put("reason", mbBalanceLog.getReason());
			}		
			if (!F.empty(mbBalanceLog.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", mbBalanceLog.getRemark());
			}
			if(mbBalanceLog.getUpdatetimeBegin()!=null){
				whereHql += " and t.updatetime >= :updatetimeBegin";
				params.put("updatetimeBegin", mbBalanceLog.getUpdatetimeBegin());
			}
			if(mbBalanceLog.getUpdatetimeEnd()!=null){
				whereHql += " and t.updatetime <= :updatetimeEnd";
				params.put("updatetimeEnd", mbBalanceLog.getUpdatetimeEnd());
			}
			if(mbBalanceLog.getIsShow() != null) {
				whereHql += " and t.isShow = :isShow";
				params.put("isShow", mbBalanceLog.getIsShow());
			}
			if (mbBalanceLog.getBalanceIds() != null) {
				whereHql += " and t.balanceId in (:balanceIds) ";
				params.put("balanceIds", mbBalanceLog.getBalanceIds());
			}
			if(!F.empty(mbBalanceLog.getRefTypes())) {
				whereHql += " and t.refType in (:refTypes)";
				params.put("refTypes", mbBalanceLog.getRefTypes().split(","));
			}

		}
		return whereHql;
	}

	@Override
	public void add(MbBalanceLog mbBalanceLog) {
		TmbBalanceLog t = new TmbBalanceLog();
		BeanUtils.copyProperties(mbBalanceLog, t);
		//t.setId(jb.absx.UUID.uuid());
		if(F.empty(t.getIsdeleted())) t.setIsdeleted(false);
		if(F.empty(t.getIsShow())) t.setIsShow(true);
		mbBalanceLogDao.save(t);
		mbBalanceLog.setId(t.getId());
	}

	@Override
	public void addAndUpdateBalance(MbBalanceLog mbBalanceLog) {
		if (mbBalanceLog.getAmount() == null) {
			throw new ServiceException("余额不允许为null");
		}
		TmbBalance tmbBalance = mbBalanceDao.get(TmbBalance.class, mbBalanceLog.getBalanceId());
		String remark = mbBalanceLog.getRemark() == null ? "" : mbBalanceLog.getRemark();
		mbBalanceLog.setRemark(String.format(remark + "【期末余额:%s分】", tmbBalance.getAmount() + mbBalanceLog.getAmount()));
		add(mbBalanceLog);

		int i = mbBalanceDao.executeHql("update TmbBalance t set t.amount=amount+" + mbBalanceLog.getAmount() + " where t.id=" + mbBalanceLog.getBalanceId());
		if (i != 1) {
			throw new ServiceException("余额更新失败");
		}
	}

	@Override
	public void updateLogAndBalance(MbBalanceLog mbBalanceLog) {
		TmbBalanceLog t = mbBalanceLogDao.get(TmbBalanceLog.class, mbBalanceLog.getId());
		TmbBalance tmbBalance = mbBalanceDao.get(TmbBalance.class, t.getBalanceId());
		String remark = mbBalanceLog.getRemark() == null ? "" : mbBalanceLog.getRemark();
		mbBalanceLog.setRemark(String.format(remark + "【期末余额:%s分】", tmbBalance.getAmount() + t.getAmount()));
		edit(mbBalanceLog);

		if(!mbBalanceLog.getIsdeleted()) {
			if(mbBalanceLog.getAmount() == null) {
				throw new ServiceException("余额不允许为null");
			}
			int i = mbBalanceDao.executeHql("update TmbBalance t set t.amount=amount+" + mbBalanceLog.getAmount() + " where t.id=" + mbBalanceLog.getBalanceId());
			if (i != 1) {
				throw new ServiceException("余额更新失败");
			}
		}
	}

	@Override
	public MbBalanceLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbBalanceLog t = mbBalanceLogDao.get("from TmbBalanceLog t  where t.id = :id", params);
		MbBalanceLog o = new MbBalanceLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbBalanceLog mbBalanceLog) {
		TmbBalanceLog t = mbBalanceLogDao.get(TmbBalanceLog.class, mbBalanceLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbBalanceLog, t, new String[]{"id", "addtime", "updatetime"}, true);
			mbBalanceLog.setBalanceId(t.getBalanceId());
			mbBalanceLog.setAmount(t.getAmount());
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbBalanceLogDao.executeHql("update TmbBalanceLog t set t.isdeleted = 1 where t.id = :id",params);
		//mbBalanceLogDao.delete(mbBalanceLogDao.get(TmbBalanceLog.class, id));
	}

    @Override
    public DataGrid dataGridWithShopName(MbBalanceLog mbBalanceLog, PageHelper ph) {
        if (mbBalanceLog.getShopId() != null) {
            List<MbBalance> mbBalances = mbBalanceService.queryBalanceListByShopId(mbBalanceLog.getShopId());
            if (!CollectionUtils.isEmpty(mbBalances)) {
                Integer[] balanceIds = new Integer[mbBalances.size()];
                Integer i = 0;
                for (MbBalance mbBalance : mbBalances) {
                    balanceIds[i] = mbBalance.getId();
                    i++;
                }
                mbBalanceLog.setBalanceIds(balanceIds);
            }
        }
        DataGrid dataGrid = dataGrid(mbBalanceLog, ph);
        List<MbBalanceLog> mbBalanceLogs = dataGrid.getRows();
        if (!CollectionUtils.isEmpty(mbBalanceLogs)) {
            Integer totalPrice = 0;
            for (MbBalanceLog balanceLog : mbBalanceLogs) {
                MbBalance mbBalance = mbBalanceService.get(balanceLog.getBalanceId());
                MbShop mbShop = mbShopService.getFromCache(mbBalance.getRefId());
                balanceLog.setShopName(mbShop.getName());
                totalPrice += balanceLog.getAmount();
            }
            List<MbBalanceLog> footer = new ArrayList<MbBalanceLog>();
            MbBalanceLog totalRow = new MbBalanceLog();
            totalRow.setShopName("合计");
            totalRow.setAmount(totalPrice);
            footer.add(totalRow);
            dataGrid.setFooter(footer);
            return dataGrid;
        }
        return new DataGrid();
    }

    @Override
	public List<MbBalanceLog> list(MbBalanceLog mbBalanceLog) {
		List<MbBalanceLog> ol = new ArrayList<MbBalanceLog>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TmbBalanceLog t ";
		String where = whereHql(mbBalanceLog, params);
		List<TmbBalanceLog> l = mbBalanceLogDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TmbBalanceLog t : l) {
				MbBalanceLog o = new MbBalanceLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public DataGrid updateDeliveryBalanceLogDataGrid(MbBalanceLog mbBalanceLog, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		if (!F.empty(mbBalanceLog.getShopId())) {
			MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceDelivery(mbBalanceLog.getShopId());
			mbBalanceLog.setBalanceId(mbBalance.getId());
			dataGrid = dataGrid(mbBalanceLog, pageHelper);
		}
		return dataGrid;
	}

	@Override
	public DataGrid updateDriverBalanceLogDataGrid(MbBalanceLog mbBalanceLog, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		if (mbBalanceLog instanceof MbBalanceLogDriver) {
			MbBalanceLogDriver balanceLogDriver = (MbBalanceLogDriver) mbBalanceLog;
			if (!F.empty(balanceLogDriver.getAccountId())) {
				MbBalance mbBalance = mbBalanceService.addOrGetDriverBalance(balanceLogDriver.getAccountId());
				mbBalanceLog.setBalanceId(mbBalance.getId());
				dataGrid = dataGrid(mbBalanceLog, pageHelper);
			}
		}
		return dataGrid;
	}


	@Override
	public DataGrid dataGridBalanceLogDownload(MbBalanceLog mbBalanceLog, PageHelper ph) {
		if (mbBalanceLog.getUpdatetimeBegin() == null || mbBalanceLog.getUpdatetimeEnd() == null) {
			return new DataGrid();
		} else {
			DataGrid dataGrid = dataGridWithShopName(mbBalanceLog, ph);
			List<MbBalanceLog> mbBalanceLogs = dataGrid.getRows();
			if (CollectionUtils.isNotEmpty(mbBalanceLogs)) {
				List<MbBalanceLogExport> balanceLogExports = new ArrayList<MbBalanceLogExport>();
				for (MbBalanceLog balanceLog : mbBalanceLogs) {
					MbBalanceLogExport mbBalanceLogExport = new MbBalanceLogExport();
					BeanUtils.copyProperties(balanceLog, mbBalanceLogExport);
					mbBalanceLogExport.setAmountElement(balanceLog.getAmount() / 100.0);
					balanceLogExports.add(mbBalanceLogExport);
				}
				List<MbBalanceLogExport> footer = new ArrayList<MbBalanceLogExport>();
				List<MbBalanceLog> balanceLogs = dataGrid.getFooter();
				MbBalanceLogExport totalRow = new MbBalanceLogExport();
				totalRow.setShopName("合计");
				totalRow.setAmountElement(balanceLogs.get(0).getAmount() / 100.0);
				DataGrid dg = new DataGrid();
				footer.add(totalRow);
				dg.setFooter(footer);
				dg.setRows(balanceLogExports);
				return dg;
			}
		}
		return new DataGrid();
	}

	@Override
	public Map<String, Integer> totalBalanceByMonth(MbBalanceLog mbBalanceLog) {
		Map<String, Integer> totalBalance = new HashMap<String, Integer>();
		if (!F.empty(mbBalanceLog.getShopId())) {
			MbBalance mbBalance = mbBalanceService.addOrGetMbBalanceDelivery(mbBalanceLog.getShopId());
			String sql = "select sum(case when t.amount > 0 then t.amount end) income, "
					+ " sum(case when t.amount < 0 then t.amount end) expenditure "
					+ " from mb_balance_log t "
					+ " where t.isdeleted = 0 and t.balance_id = :balanceId and t.updatetime >= :updatetimeBegin and t.updatetime <= :updatetimeEnd";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("balanceId", mbBalance.getId());
			params.put("updatetimeBegin", mbBalanceLog.getUpdatetimeBegin());
			params.put("updatetimeEnd", mbBalanceLog.getUpdatetimeEnd());
			List<Map> l = mbBalanceLogDao.findBySql2Map(sql, params);
			if(CollectionUtils.isNotEmpty(l)) {
				Map map = l.get(0);
				if(map.get("income") == null) totalBalance.put("income", 0);
				else totalBalance.put("income", ((BigDecimal)map.get("income")).intValue());

				if(map.get("expenditure") == null) totalBalance.put("expenditure", 0);
				else totalBalance.put("expenditure", ((BigDecimal)map.get("expenditure")).intValue());
			}
		}

		return totalBalance;
	}
	@Override
	public Map<String, Integer> totalBalanceByMonthDriver(MbBalanceLogDriver mbBalanceLogDriver) {
		Map<String, Integer> totalBalance = new HashMap<String, Integer>();
		if (!F.empty(mbBalanceLogDriver.getAccountId())) {
			MbBalance mbBalance = mbBalanceService.addOrGetDriverBalance(mbBalanceLogDriver.getAccountId());
			String sql = "select sum(case when t.amount > 0 then t.amount end) income, "
					+ " sum(case when t.amount < 0 then t.amount end) expenditure "
					+ " from mb_balance_log t "
					+ " where t.isdeleted = 0 and t.balance_id = :balanceId and t.updatetime >= :updatetimeBegin and t.updatetime <= :updatetimeEnd";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("balanceId", mbBalance.getId());
			params.put("updatetimeBegin", mbBalanceLogDriver.getUpdatetimeBegin());
			params.put("updatetimeEnd", mbBalanceLogDriver.getUpdatetimeEnd());
			List<Map> l = mbBalanceLogDao.findBySql2Map(sql, params);
			if(CollectionUtils.isNotEmpty(l)) {
				Map map = l.get(0);
				if(map.get("income") == null) totalBalance.put("income", 0);
				else totalBalance.put("income", ((BigDecimal)map.get("income")).intValue());

				if(map.get("expenditure") == null) totalBalance.put("expenditure", 0);
				else totalBalance.put("expenditure", ((BigDecimal)map.get("expenditure")).intValue());
			}
		}

		return totalBalance;
	}
}
