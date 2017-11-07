package com.bx.ant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DeliverOrderPay;
import com.bx.ant.pageModel.Supplier;
import com.bx.ant.service.DeliverOrderPayServiceI;
import com.bx.ant.service.DeliverOrderServiceI;
import com.bx.ant.service.SupplierServiceI;
import com.mobian.absx.F;
import com.bx.ant.dao.SupplierOrderBillDaoI;
import com.bx.ant.model.TsupplierOrderBill;
import com.bx.ant.pageModel.SupplierOrderBill;
import com.mobian.exception.ServiceException;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.service.SupplierOrderBillServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

import javax.annotation.Resource;

@Service
public class SupplierOrderBillServiceImpl extends BaseServiceImpl<SupplierOrderBill> implements SupplierOrderBillServiceI {

	@Autowired
	private SupplierOrderBillDaoI supplierOrderBillDao;
	@Resource
	private SupplierServiceI supplierService;
	@Resource
	private DeliverOrderPayServiceI deliverOrderPayService;
	@Resource
	private DeliverOrderServiceI deliverOrderService;

	@Override
	public DataGrid dataGrid(SupplierOrderBill supplierOrderBill, PageHelper ph) {
		List<SupplierOrderBill> ol = new ArrayList<SupplierOrderBill>();
		String hql = " from TsupplierOrderBill t ";
		DataGrid dg = dataGridQuery(hql, ph, supplierOrderBill, supplierOrderBillDao);
		@SuppressWarnings("unchecked")
		List<TsupplierOrderBill> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TsupplierOrderBill t : l) {
				SupplierOrderBill o = new SupplierOrderBill();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
				if (o.getSupplierId() != null) {
					Supplier supplier = supplierService.get(o.getSupplierId());
					o.setSupplierName(supplier.getName());
				}
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(SupplierOrderBill supplierOrderBill, Map<String, Object> params) {
		String whereHql = "";	
		if (supplierOrderBill != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(supplierOrderBill.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", supplierOrderBill.getTenantId());
			}		
			if (!F.empty(supplierOrderBill.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", supplierOrderBill.getIsdeleted());
			}		
			if (!F.empty(supplierOrderBill.getSupplierId())) {
				whereHql += " and t.supplierId = :supplierId";
				params.put("supplierId", supplierOrderBill.getSupplierId());
			}		
			if (!F.empty(supplierOrderBill.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", supplierOrderBill.getStatus());
			}		
			if (!F.empty(supplierOrderBill.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", supplierOrderBill.getRemark());
			}		
			if (!F.empty(supplierOrderBill.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", supplierOrderBill.getAmount());
			}		
			if (!F.empty(supplierOrderBill.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", supplierOrderBill.getPayWay());
			}
			if (supplierOrderBill.getStartDate() != null) {
				whereHql += " and t.startDate >= :startDate";
				params.put("startDate", supplierOrderBill.getStartDate());
			}
			if (supplierOrderBill.getEndDate() != null) {
				whereHql += " and t.endDate <= :endDate";
				params.put("endDate", supplierOrderBill.getEndDate());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(SupplierOrderBill supplierOrderBill) {
		TsupplierOrderBill t = new TsupplierOrderBill();
		BeanUtils.copyProperties(supplierOrderBill, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		supplierOrderBillDao.save(t);
	    supplierOrderBill.setId(t.getId());
	}

	@Override
	public SupplierOrderBill get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TsupplierOrderBill t = supplierOrderBillDao.get("from TsupplierOrderBill t  where t.id = :id", params);
		SupplierOrderBill o = new SupplierOrderBill();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(SupplierOrderBill supplierOrderBill) {
		TsupplierOrderBill t = supplierOrderBillDao.get(TsupplierOrderBill.class, supplierOrderBill.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(supplierOrderBill, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		supplierOrderBillDao.executeHql("update TsupplierOrderBill t set t.isdeleted = 1 where t.id = :id",params);
		//supplierOrderBillDao.delete(supplierOrderBillDao.get(TsupplierOrderBill.class, id));
	}

	@Override
	public Integer editBillStatus(SupplierOrderBill supplierOrderBill, Boolean isAgree) {
		Integer result = 0;
		if(isAgree) {
			supplierOrderBill.setStatus("BAS02");//审核通过
		}else {
			supplierOrderBill.setStatus("BAS03");//审核拒绝
		}
		edit(supplierOrderBill);
		List<DeliverOrderPay> list = deliverOrderPayService.getBySupplierOrderBillId(supplierOrderBill.getId().intValue());
		for(DeliverOrderPay d : list) {
			DeliverOrder deliverOrder = deliverOrderService.get(d.getDeliverOrderId());
			if(isAgree) {
				d.setStatus("DPS02");//已结算
				deliverOrder.setPayStatus("DPS02");//修改订单状态为已结算
			}else {
				d.setStatus("DPS04");//审核拒绝
				deliverOrder.setPayStatus("DPS01");//订单变成未结算
			}
			d.setPayWay(supplierOrderBill.getPayWay());
			deliverOrderPayService.edit(d);
			try {
				result = deliverOrderService.editOrderStatus(deliverOrder);
			}catch (Exception e) {
				e.printStackTrace();
			}
			if (result <= 0 ) {
                throw  new ServiceException("修改账单状态失败！");
			}

		}
		return result;
	}
}
