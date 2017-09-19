<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrderRefundItem" %>
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
					<th><%=TmbOrderRefundItem.ALIAS_TENANT_ID%></th>	
					<td>
						${mbOrderRefundItem.tenantId}							
					</td>							
					<th><%=TmbOrderRefundItem.ALIAS_ADDTIME%></th>	
					<td>
						${mbOrderRefundItem.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderRefundItem.ALIAS_UPDATETIME%></th>	
					<td>
						${mbOrderRefundItem.updatetime}							
					</td>							
					<th><%=TmbOrderRefundItem.ALIAS_ISDELETED%></th>	
					<td>
						${mbOrderRefundItem.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderRefundItem.ALIAS_ORDER_ID%></th>	
					<td>
						${mbOrderRefundItem.orderId}							
					</td>							
					<th><%=TmbOrderRefundItem.ALIAS_ITEM_ID%></th>	
					<td>
						${mbOrderRefundItem.itemId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderRefundItem.ALIAS_QUANTITY%></th>	
					<td>
						${mbOrderRefundItem.quantity}							
					</td>							
					<th><%=TmbOrderRefundItem.ALIAS_TYPE%></th>	
					<td>
						${mbOrderRefundItem.type}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderRefundItem.ALIAS_LOGIN_ID%></th>	
					<td>
						${mbOrderRefundItem.loginId}							
					</td>							
					<th><%=TmbOrderRefundItem.ALIAS_REMARK%></th>	
					<td>
						${mbOrderRefundItem.remark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>