<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrderRefundLog" %>
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
					<th><%=TmbOrderRefundLog.ALIAS_TENANT_ID%></th>	
					<td>
						${mbOrderRefundLog.tenantId}							
					</td>							
					<th><%=TmbOrderRefundLog.ALIAS_ADDTIME%></th>	
					<td>
						${mbOrderRefundLog.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderRefundLog.ALIAS_UPDATETIME%></th>	
					<td>
						${mbOrderRefundLog.updatetime}							
					</td>							
					<th><%=TmbOrderRefundLog.ALIAS_ISDELETED%></th>	
					<td>
						${mbOrderRefundLog.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderRefundLog.ALIAS_ORDER_ID%></th>	
					<td>
						${mbOrderRefundLog.orderId}							
					</td>							
					<th><%=TmbOrderRefundLog.ALIAS_ORDER_TYPE%></th>	
					<td>
						${mbOrderRefundLog.orderType}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderRefundLog.ALIAS_PAYMENT_ITEM_ID%></th>	
					<td>
						${mbOrderRefundLog.paymentItemId}							
					</td>							
					<th><%=TmbOrderRefundLog.ALIAS_AMOUNT%></th>	
					<td>
						${mbOrderRefundLog.amount}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderRefundLog.ALIAS_PAY_WAY%></th>	
					<td>
						${mbOrderRefundLog.payWay}							
					</td>							
					<th><%=TmbOrderRefundLog.ALIAS_REFUND_WAY%></th>	
					<td>
						${mbOrderRefundLog.refundWay}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderRefundLog.ALIAS_REASON%></th>	
					<td>
						${mbOrderRefundLog.reason}							
					</td>							
				</tr>		
		</table>
	</div>
</div>