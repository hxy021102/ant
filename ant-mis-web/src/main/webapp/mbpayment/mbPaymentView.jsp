<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbPayment" %>
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
					<th><%=TmbPayment.ALIAS_TENANT_ID%></th>	
					<td>
						${mbPayment.tenantId}							
					</td>							
					<th><%=TmbPayment.ALIAS_ADDTIME%></th>	
					<td>
						${mbPayment.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbPayment.ALIAS_UPDATETIME%></th>	
					<td>
						${mbPayment.updatetime}							
					</td>							
					<th><%=TmbPayment.ALIAS_ISDELETED%></th>	
					<td>
						${mbPayment.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbPayment.ALIAS_ORDER_ID%></th>	
					<td>
						${mbPayment.orderId}							
					</td>							
					<th><%=TmbPayment.ALIAS_ORDER_TYPE%></th>	
					<td>
						${mbPayment.orderType}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbPayment.ALIAS_AMOUNT%></th>	
					<td>
						${mbPayment.amount}							
					</td>							
					<th><%=TmbPayment.ALIAS_PAY_WAY%></th>	
					<td>
						${mbPayment.payWay}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbPayment.ALIAS_STATUS%></th>	
					<td>
						${mbPayment.status}							
					</td>							
					<th><%=TmbPayment.ALIAS_REASON%></th>	
					<td>
						${mbPayment.reason}							
					</td>							
				</tr>		
		</table>
	</div>
</div>