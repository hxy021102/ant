<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbRechargeLog" %>
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
					<th><%=TmbRechargeLog.ALIAS_TENANT_ID%></th>	
					<td>
						${mbRechargeLog.tenantId}							
					</td>							
					<th><%=TmbRechargeLog.ALIAS_ADDTIME%></th>	
					<td>
						${mbRechargeLog.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_UPDATETIME%></th>	
					<td>
						${mbRechargeLog.updatetime}							
					</td>							
					<th><%=TmbRechargeLog.ALIAS_ISDELETED%></th>	
					<td>
						${mbRechargeLog.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_BALANCE_ID%></th>	
					<td>
						${mbRechargeLog.balanceId}							
					</td>							
					<th><%=TmbRechargeLog.ALIAS_AMOUNT%></th>	
					<td>
						${mbRechargeLog.amount}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_APPLY_LOGIN_ID%></th>	
					<td>
						${mbRechargeLog.applyLoginId}							
					</td>							
					<th><%=TmbRechargeLog.ALIAS_CONTENT%></th>	
					<td>
						${mbRechargeLog.content}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_HANDLE_STATUS%></th>	
					<td>
						${mbRechargeLog.handleStatus}							
					</td>							
					<th><%=TmbRechargeLog.ALIAS_HANDLE_LOGIN_ID%></th>	
					<td>
						${mbRechargeLog.handleLoginId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_HANDLE_REMARK%></th>	
					<td>
						${mbRechargeLog.handleRemark}							
					</td>							
					<th><%=TmbRechargeLog.ALIAS_HANDLE_TIME%></th>	
					<td>
						${mbRechargeLog.handleTime}							
					</td>							
				</tr>		
		</table>
	</div>
</div>