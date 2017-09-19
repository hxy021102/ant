package com.mobian.service;

import com.mobian.pageModel.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author John
 * 
 */
public interface MbShopCouponsServiceI {


	//水票类型对应编码
	String COUPONS_TYPE_1_VOUCHER = "CT001";

	//门店水票状态编码:有效
	String SHOP_COUPONS_STATUS_ACTIVE = "NS001";

	//门店水票状态编码:无效
	String SHOP_COUPONS_STATUS_INACTIVE = "NS005";

	//门店水票状态编码:删除
	String SHOP_COUPONS_STATUS_ISDELETED = "NS010";
	//后台添加购买水票时在余额更改记录mbBalanceLog写的原因reason
	String EDIT_BALANCE_REASON = "购买水票";

	//后台添加购买水票时在余额更改记录mbBalanceLog写的备注remark
	String EDIT_BALANCE_REMARK = "[OK]";

	//后台添加购买水票时修改账户余额,无账户时抛出的异常携带的信息
	String NO_ENOUGH_MONEY_ERROR = "账户余额不足!";

	//后台添加购买水票时修改账户余额,对应的类型
	String BALANCELOG_TYPE_BUY_VOUCHER = "BT011";

	//删除门店水票返款至余额
	String BALANCELOG_TYPE_DELETE_VOUCHER = "BT030";

	//支付方式为水票支付
	String PAY_WAY_VOUCHER = "PW04";

	String COUPONS_PAY_TYPE_FREE = "PT04";


	/**
	 * 获取MbShopCoupons数据表格
	 * 
	 * @param mbShopCoupons
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(MbShopCoupons mbShopCoupons, PageHelper ph);

    void fillCouponsInfo(MbShopCouponsView m);

    MbShopCouponsView getShopCouponsView(Integer id);

    /**
	 * 添加MbShopCoupons
	 * 
	 * @param mbShopCoupons
	 */
	public void add(MbShopCoupons mbShopCoupons);

	/**
	 * 获得MbShopCoupons对象
	 * 
	 * @param id
	 * @return
	 */
	public MbShopCoupons get(Integer id);

	/**
	 * 修改MbShopCoupons
	 * 
	 * @param mbShopCoupons
	 */
	public void edit(MbShopCoupons mbShopCoupons);

	/**
	 * 删除MbShopCoupons
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	/**
	 *
	 * @param mbShopCoupons
	 * @return
	 */
	List<MbShopCoupons> listMbShopCoupons(MbShopCoupons mbShopCoupons);

	/**
	 * 通过门店id获得有效的门店券票
	 * @param shopId
	 * @return
	 */
    List<MbShopCoupons> listActiveShopCouponsByShopId(Integer shopId);

	/**
	 * 通过门店id获得所有的优惠券-类型:兑换券(水票),并对兑换券进行同水票的数量统计
	 * @param shopId
	 * @return
	 */
	List<MbShopCoupons> listSameActiveShopCouponsTypeVoucherByShopId(Integer shopId);

	/**
	 * 通过门店id和商品id找到对应的优惠券-类型:兑换券(水票),并对该兑换券的进行数量统计
	 * @param shopId
	 * @param itemId
	 * @return
	 */
	MbShopCoupons getSameActiveVouhcerByShopIdAndItemId(Integer shopId, Integer itemId);



    void editShopCouponsAndAddPaymentItem(Integer shopId, Integer itemId, MbPaymentItem paymentItem, String loginId, Integer orderId);

    /**
	 * 添加mbShopCoupons
	 */
	void addShopCouponsAndEditBalance(MbShopCoupons mbShopCoupons);

	/**
	 * 计算门店相同的券总数
	 * @param mbShopCoupons
	 * @return
	 */
	MbShopCoupons countSameShopCouponsTypeVoucher(MbShopCoupons mbShopCoupons);


	void deleteAndRefund(MbShopCoupons mbShopCoupons);

	/**
	 * 查询水票数
	 * @param shopId
	 * @return
	 */
	Map<String, MbShopCoupons> getShopCouponsMapByShopId(Integer shopId);
}
