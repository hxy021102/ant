package com.mobian.service.impl;

import com.mobian.dao.BugTypeDaoI;
import com.mobian.model.Tbugtype;
import com.mobian.service.BugTypeServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BugTypeServiceImpl implements BugTypeServiceI {

	@Autowired
	private BugTypeDaoI bugType;

	@Override
	@Cacheable(value = "bugTypeServiceCache", key = "'bugTypeList'")
	public List<Tbugtype> getBugTypeList() {
		return bugType.find("from Tbugtype t");
	}

}
