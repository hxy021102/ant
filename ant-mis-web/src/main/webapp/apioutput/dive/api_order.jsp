<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div id="index_order_tabs" class="easyui-tabs" data-options="fit:true">
		
		<div title="订单首页数量" data-options="href:'api_order_number_count.jsp'"
			style="padding: 1px"></div>
		<div title="购物车查询" data-options="href:'api_order_shop_cart_list.jsp'"
			style="padding: 1px"></div>
		<div title="加入购物车" data-options="href:'api_order_add_shop_cart.jsp'"
			style="padding: 1px"></div>
		<div title="修改数量" data-options="href:'api_order_update_number.jsp'"
			style="padding: 1px"></div>
		<div title="删除购物车" data-options="href:'api_order_del_shop_cart.jsp'"
			style="padding: 1px"></div>
		<div title="订单列表" data-options="href:'api_order_list.jsp'"
			style="padding: 1px"></div>
		<div title="订单详情" data-options="href:'api_order_detail.jsp'"
			style="padding: 1px"></div>
		<div title="订单详情物品删除" data-options="href:'api_order_del_detail.jsp'"
			style="padding: 1px"></div>
		<div title="订单创建" data-options="href:'api_order_create.jsp'"
			style="padding: 1px"></div>
		<div title="支付" data-options="href:'api_order_pay.jsp'"
			style="padding: 1px"></div>
		<div title="订单取消" data-options="href:'api_order_cancel.jsp'"
			style="padding: 1px"></div>
	</div>

</body>
</html>