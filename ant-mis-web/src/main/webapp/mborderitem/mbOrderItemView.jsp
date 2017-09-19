<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrderItem" %>
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
					<th><%=TmbOrderItem.ALIAS_TENANT_ID%></th>	
					<td>
						${mbOrderItem.tenantId}							
					</td>							
					<th><%=TmbOrderItem.ALIAS_ADDTIME%></th>	
					<td>
						${mbOrderItem.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderItem.ALIAS_UPDATETIME%></th>	
					<td>
						${mbOrderItem.updatetime}							
					</td>							
					<th><%=TmbOrderItem.ALIAS_ISDELETED%></th>	
					<td>
						${mbOrderItem.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderItem.ALIAS_ITEM_ID%></th>	
					<td>
						${mbOrderItem.itemId}							
					</td>							
					<th><%=TmbOrderItem.ALIAS_QUANTITY%></th>	
					<td>
						${mbOrderItem.quantity}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderItem.ALIAS_MARKET_PRICE%></th>	
					<td>
						${mbOrderItem.marketPrice}							
					</td>							
					<th><%=TmbOrderItem.ALIAS_BUY_PRICE%></th>	
					<td>
						${mbOrderItem.buyPrice}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderItem.ALIAS_ORDER_ID%></th>	
					<td>
						${mbOrderItem.orderId}							
					</td>							
				</tr>		
		</table>
	</div>
</div>