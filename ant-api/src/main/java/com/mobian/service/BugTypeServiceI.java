package com.mobian.service;

import com.mobian.model.Tbugtype;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface BugTypeServiceI {

	/**
	 * 获得BUG类型列表
	 * 
	 * @return
	 */
	public List<Tbugtype> getBugTypeList();

}
