package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbSupplierContractDaoI;
import com.mobian.model.TmbSupplierContract;
import com.mobian.pageModel.*;
import com.mobian.service.MbSupplierContractServiceI;
import com.mobian.service.MbSupplierServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbSupplierContractServiceImpl extends BaseServiceImpl<MbSupplierContract> implements MbSupplierContractServiceI {

    @Autowired
    private MbSupplierContractDaoI mbSupplierContractDao;
    @Autowired
    private MbSupplierServiceI mbSupplierService;

    @Override
    public DataGrid dataGrid(MbSupplierContract mbSupplierContract, PageHelper ph) {
        List<MbSupplierContract> ol = new ArrayList<MbSupplierContract>();
        String hql = " from TmbSupplierContract t ";
        DataGrid dg = dataGridQuery(hql, ph, mbSupplierContract, mbSupplierContractDao);
        @SuppressWarnings("unchecked")
        List<TmbSupplierContract> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbSupplierContract t : l) {
                MbSupplierContract o = new MbSupplierContract();
                BeanUtils.copyProperties(t, o);
                if (o.getSupplierId() != null) {
                    MbSupplier supplier = mbSupplierService.getFromCache(o.getSupplierId());
                    if (supplier != null) {
                        o.setSupplierName(supplier.getName());
                    }
                }
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }


    protected String whereHql(MbSupplierContract mbSupplierContract, Map<String, Object> params) {
        String whereHql = "";
        if (mbSupplierContract != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbSupplierContract.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbSupplierContract.getTenantId());
            }
            if (!F.empty(mbSupplierContract.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbSupplierContract.getIsdeleted());
            }
            if (!F.empty(mbSupplierContract.getCode())) {
                whereHql += " and t.code = :code";
                params.put("code", mbSupplierContract.getCode());
            }
            if (!F.empty(mbSupplierContract.getName())) {
                whereHql += " and t.name = :name";
                params.put("name", mbSupplierContract.getName());
            }
            if (!F.empty(mbSupplierContract.getSupplierId())) {
                whereHql += " and t.supplierId = :supplierId";
                params.put("supplierId", mbSupplierContract.getSupplierId());
            }
            if (!F.empty(mbSupplierContract.getValid())) {
                whereHql += " and t.valid = :valid";
                params.put("valid", mbSupplierContract.getValid());
            }
            if (!F.empty(mbSupplierContract.getAttachment())) {
                whereHql += " and t.attachment = :attachment";
                params.put("attachment", mbSupplierContract.getAttachment());
            }
            if (!F.empty(mbSupplierContract.getContractType())) {
                whereHql += " and t.contractType = :contractType";
                params.put("contractType", mbSupplierContract.getContractType());
            }
        }
        return whereHql;
    }
    public Json editMbSupplierContract(MbSupplierContract mbSupplierContract){
        Json j = new Json();
        MbSupplierContract mbSup =  get(mbSupplierContract.getId());
        if (!mbSup.getCode().equals(mbSupplierContract.getCode())) {                           //合同代码不相同时
            if ( isContractExists(mbSupplierContract)) {
                j.setSuccess(false);
                j.setMsg("该供应商合同已经存在！");
            } else if ( isSupplierExists(mbSupplierContract)) {
                j.setSuccess(false);
                j.setMsg("有相同的供应商合同存在，请将之前合同的有效性改为：否！");
            } else {
                edit(mbSupplierContract);
                j.setSuccess(true);
                j.setMsg("编辑成功！");
            }
        } else {                                                                            //合同代码相同时
            if (mbSup.getSupplierId() == mbSupplierContract.getSupplierId() && mbSup.getValid() == true) {
                 edit(mbSupplierContract);
                j.setSuccess(true);
                j.setMsg("编辑成功！");
            } else if (mbSup.getSupplierId() == mbSupplierContract.getSupplierId() && mbSup.getValid() != true) {
                if (isSupplierExists(mbSupplierContract)) {
                    j.setSuccess(false);
                    j.setMsg("有相同的供应商合同存在，请将之前合同的有效性改为：否！");
                } else {
                    edit(mbSupplierContract);
                    j.setSuccess(true);
                    j.setMsg("编辑成功！");
                }
            } else if (isSupplierExists(mbSupplierContract)) {   //上面两种情况排除后，查询剩余情况
                j.setSuccess(false);
                j.setMsg("有相同的供应商合同存在，请将之前合同的有效性改为：否！");
            } else {
                 edit(mbSupplierContract);
                j.setSuccess(true);
                j.setMsg("添加成功！");
            }
        }
        return j;
    }
    public boolean isContractExists(MbSupplierContract mbSupplierContract) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", mbSupplierContract.getCode());
        List<TmbSupplierContract> contractList = mbSupplierContractDao.find("from TmbSupplierContract t where t.code = :code", params);
        if (contractList.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean isSupplierExists(MbSupplierContract mbSupplierContract) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("supplierId", mbSupplierContract.getSupplierId());
        List<TmbSupplierContract> contractList = mbSupplierContractDao.find("from TmbSupplierContract t where t.supplierId = :supplierId", params);
        for (TmbSupplierContract t : contractList) {
            MbSupplierContract o = new MbSupplierContract();
            BeanUtils.copyProperties(t, o);
            if (o.getSupplierId() != null && o.getValid() == true) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(MbSupplierContract mbSupplierContract) {
        TmbSupplierContract t = new TmbSupplierContract();
        BeanUtils.copyProperties(mbSupplierContract, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbSupplierContractDao.save(t);
    }

    @Override
    public MbSupplierContract get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbSupplierContract t = mbSupplierContractDao.get("from TmbSupplierContract t  where t.id = :id", params);
        MbSupplierContract o = new MbSupplierContract();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(MbSupplierContract mbSupplierContract) {
        TmbSupplierContract t = mbSupplierContractDao.get(TmbSupplierContract.class, mbSupplierContract.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbSupplierContract, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbSupplierContractDao.executeHql("update TmbSupplierContract t set t.isdeleted = 1 where t.id = :id", params);
        //mbSupplierContractDao.delete(mbSupplierContractDao.get(TmbSupplierContract.class, id));
    }


}
