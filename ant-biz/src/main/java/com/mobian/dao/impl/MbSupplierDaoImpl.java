package com.mobian.dao.impl;

import com.mobian.dao.MbSupplierDaoI;
import com.mobian.model.TmbSupplier;
import org.springframework.stereotype.Repository;

@Repository
public class MbSupplierDaoImpl extends BaseDaoImpl<TmbSupplier> implements MbSupplierDaoI {
    @Override
    public TmbSupplier getById(Integer id) {return super.get(TmbSupplier.class, id);
    }
}
