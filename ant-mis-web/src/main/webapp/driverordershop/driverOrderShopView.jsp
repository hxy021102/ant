<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>
					<th>添加时间</th>
					<td>
						${driverOrderShop.addtime}							
					</td>
					<th>更新时间</th>
					<td>
						${driverOrderShop.updatetime}							
					</td>
				</tr>		
				<tr>
					<th>门店订单</th>
					<td>
						${driverOrderShop.deliverOrderShopId}							
					</td>
					<th>门店Id</th>
					<td>
						${driverOrderShop.shopId}							
					</td>							
				</tr>		
				<tr>
					<th>订单状态</th>
					<td>
						${driverOrderShop.statusName}
					</td>
					<th>配送费</th>
					<td>
						${driverOrderShop.amount}							
					</td>							
				</tr>		
				<tr>
					<th>支付状态</th>
					<td>
						${driverOrderShop.payStatusName}
					</td>
					<th>账单</th>
					<td>
						${driverOrderShop.driverOrderShopBillId}							
					</td>							
				</tr>		
		</table>
	</div>
</div>