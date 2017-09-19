<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbPaymentItem" %>
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
					<th><%=TmbPaymentItem.ALIAS_TENANT_ID%></th>	
					<td>
						${mbPaymentItem.tenantId}							
					</td>							
					<th><%=TmbPaymentItem.ALIAS_ADDTIME%></th>	
					<td>
						${mbPaymentItem.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_UPDATETIME%></th>	
					<td>
						${mbPaymentItem.updatetime}							
					</td>							
					<th><%=TmbPaymentItem.ALIAS_ISDELETED%></th>	
					<td>
						${mbPaymentItem.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_PAYMENT_ID%></th>	
					<td>
						${mbPaymentItem.paymentId}							
					</td>							
					<th><%=TmbPaymentItem.ALIAS_PAY_WAY%></th>	
					<td>
						${mbPaymentItem.payWay}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_AMOUNT%></th>	
					<td>
						${mbPaymentItem.amount}							
					</td>							
					<th><%=TmbPaymentItem.ALIAS_REMITTER%></th>	
					<td>
						${mbPaymentItem.remitter}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_REMITTER_TIME%></th>	
					<td>
						${mbPaymentItem.remitterTime}							
					</td>							
					<th><%=TmbPaymentItem.ALIAS_REMARK%></th>	
					<td>
						${mbPaymentItem.remark}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_REF_ID%></th>	
					<td>
						${mbPaymentItem.refId}							
					</td>							
				</tr>		
		</table>
	</div>
</div>