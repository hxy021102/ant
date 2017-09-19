package com.mobian.service;

import com.mobian.model.TmbContract;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbContract;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface MbContractServiceI {

	/**
	 * 获取MbContract数据表格
	 * 
	 * @param mbContract
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbContract mbContract, PageHelper ph);

	/**
	 * 添加MbContract
	 * 
	 * @param mbContract
	 */
	public void add(MbContract mbContract);

	/**
	 * 获得MbContract对象
	 * 
	 * @param id
	 * @return
	 */
	public MbContract get(Integer id);

	MbContract getFromCache(Integer id);

	boolean isContractExists(MbContract mbContract);

	/**
	 * 修改MbContract
	 * 
	 * @param mbContract
	 */
	public void edit(MbContract mbContract);

	/**
	 * 删除MbContract
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 获得MbContract对象
	 *
	 * @param shopId
	 */
	MbContract getNewMbContract(Integer shopId);

	/**
	 * 获得List<MbContract>对象
	 */
	List<TmbContract> queryAllMbContract();

}
