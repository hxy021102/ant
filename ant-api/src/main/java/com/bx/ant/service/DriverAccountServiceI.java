package com.bx.ant.service;

import com.bx.ant.pageModel.DriverAccount;
import com.bx.ant.pageModel.DriverAccountQuery;
import com.bx.ant.pageModel.DriverAccountView;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface DriverAccountServiceI {

	String HANDLE_STATUS_ADUIT = "DAHS01";
	String HANDLE_STATUS_AGREE = "DAHS02";
	String HANDLE_STATUS_REFUSE = "DAHS03";


	/**
	 * 获取DriverAccount数据表格
	 * 
	 * @param driverAccount
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(DriverAccount driverAccount, PageHelper ph);

	/**
	 * 添加DriverAccount
	 * 
	 * @param driverAccount
	 */
	public void add(DriverAccount driverAccount);

	/**
	 * 获得DriverAccount对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverAccount get(Integer id);

	/**
	 * 修改DriverAccount
	 * 
	 * @param driverAccount
	 */
	public void edit(DriverAccount driverAccount);

	/**
	 * 删除DriverAccount
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 * 获取DriverAccountView
	 * @param id
	 * @return
	 */
    DriverAccountView getView(Integer id);

	/**
	 * 获取DataGrid.DriverAccountView
	 * @param driverAccountQuery
	 * @param pageHelper
	 * @return
	 */
	DataGrid dataGridView(DriverAccountQuery driverAccountQuery, PageHelper pageHelper);

	/**
	 * 通过微信信息获取骑手
	 * @param refId
	 * @param refType
	 * @param handleStatus
	 * @return
	 */
    DriverAccount getByRef(String refId, String refType, String handleStatus);

    /**
	 * 通过ReId获取DriverAccount
	 * @param refId
	 * @param refType
	 * @return
	 */
    DriverAccount getByRef(String refId, String refType);

	/**
	 * 获取有效骑手
	 * @return
	 */
	List<DriverAccount> getAvilableAndWorkDriver();

	/**
	 *
	 * @param userName
	 * @return
	 */
	boolean checkUserName(String userName);

	/**
	 * 查询DriverAccount的集合
	 * @param driverAccount
	 * @return
	 */
	List<DriverAccount> query(DriverAccount driverAccount);

	/**
	 * 创建骑手所分配的订单在redis中的表名
	 * @param id
	 * @return
	 */
    String buildAllocationOrderKey(Integer id);

	/**
	 * 创建骑手拒绝的订单在redis中的表名
	 * @param accountId
	 * @return
	 */
	String buildRefuseOrderKey(Integer accountId);
}
