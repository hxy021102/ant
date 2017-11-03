<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bx.ant.model.TdriverOrderShop" %>
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
					<th><%=TdriverOrderShop.ALIAS_TENANT_ID%></th>	
					<td>
						${driverOrderShop.tenantId}							
					</td>							
					<th><%=TdriverOrderShop.ALIAS_ADDTIME%></th>	
					<td>
						${driverOrderShop.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShop.ALIAS_UPDATETIME%></th>	
					<td>
						${driverOrderShop.updatetime}							
					</td>							
					<th><%=TdriverOrderShop.ALIAS_ISDELETED%></th>	
					<td>
						${driverOrderShop.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShop.ALIAS_DELIVER_ORDER_SHOP_ID%></th>	
					<td>
						${driverOrderShop.deliverOrderShopId}							
					</td>							
					<th><%=TdriverOrderShop.ALIAS_SHOP_ID%></th>	
					<td>
						${driverOrderShop.shopId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShop.ALIAS_STATUS%></th>	
					<td>
						${driverOrderShop.status}							
					</td>							
					<th><%=TdriverOrderShop.ALIAS_AMOUNT%></th>	
					<td>
						${driverOrderShop.amount}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShop.ALIAS_PAY_STATUS%></th>	
					<td>
						${driverOrderShop.payStatus}							
					</td>							
					<th><%=TdriverOrderShop.ALIAS_DRIVER_ORDER_SHOP_BILL_ID%></th>	
					<td>
						${driverOrderShop.driverOrderShopBillId}							
					</td>							
				</tr>		
		</table>
	</div>
</div>