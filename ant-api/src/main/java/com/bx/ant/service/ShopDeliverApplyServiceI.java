package com.bx.ant.service;

import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.ShopDeliverApplyQuery;
import com.bx.ant.pageModel.ShopDeliverApply;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbAssignShop;
import com.mobian.pageModel.PageHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface ShopDeliverApplyServiceI {

	String DAS_01 = "DAS01"; //待审核
	String DAS_02 = "DAS02"; //审核通过
	String DAS_03 = "DAS03"; //审核拒绝

	String  DELIVER_WAY_SHOP = "DAW01";
	String DELIVER_WAY_CUSTOMER = "DAW02";
	String DELIVER_WAY_DRIVER = "DAW03";

	/**
	 * 获取ShopDeliverApply数据表格
	 * 
	 * @param shopDeliverApply
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(ShopDeliverApply shopDeliverApply, PageHelper ph);

	/**
	 * 添加ShopDeliverApply
	 * 
	 * @param shopDeliverApply
	 */
	public void add(ShopDeliverApply shopDeliverApply);

	/**
	 * 获得ShopDeliverApply对象
	 * 
	 * @param id
	 * @return
	 */
	public ShopDeliverApply get(Integer id);

	/**
	 * 修改ShopDeliverApply
	 * 
	 * @param shopDeliverApply
	 */
	public void edit(ShopDeliverApply shopDeliverApply);

	/**
	 * 删除ShopDeliverApply
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	ShopDeliverApply getByAccountId(Integer accountId);

	/**
	 *获取派单申请列表并以名称新式显示
	 * @param shopDeliverApply
	 * @param ph
	 * @return
	 */
	DataGrid dataGridWithName(ShopDeliverApply shopDeliverApply, PageHelper ph);

	/**
	 * 获取详细信息
	 * @param id
	 * @return
	 */
	ShopDeliverApplyQuery getViewMessage(Integer id);

	/**
	 * 查询到在工作且可用的门店
	 * @return
	 */
	List<ShopDeliverApply> getAvailableAndWorkShop();
	List<ShopDeliverApply> query(ShopDeliverApply shopDeliverApply);

	/**
	 * 获取满足指派要求的门店
	 * @param deliverOrder
	 * @return
	 */
	List<MbAssignShop> queryAssignShopList(DeliverOrder deliverOrder);


}
