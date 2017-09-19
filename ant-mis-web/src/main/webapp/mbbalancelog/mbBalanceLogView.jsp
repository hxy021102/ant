<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbBalanceLog" %>
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
					<th><%=TmbBalanceLog.ALIAS_TENANT_ID%></th>	
					<td>
						${mbBalanceLog.tenantId}							
					</td>							
					<th><%=TmbBalanceLog.ALIAS_ADDTIME%></th>	
					<td>
						${mbBalanceLog.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbBalanceLog.ALIAS_UPDATETIME%></th>	
					<td>
						${mbBalanceLog.updatetime}							
					</td>							
					<th><%=TmbBalanceLog.ALIAS_ISDELETED%></th>	
					<td>
						${mbBalanceLog.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbBalanceLog.ALIAS_BALANCE_ID%></th>	
					<td>
						${mbBalanceLog.balanceId}							
					</td>							
					<th><%=TmbBalanceLog.ALIAS_AMOUNT%></th>	
					<td>
						${mbBalanceLog.amount}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbBalanceLog.ALIAS_REF_ID%></th>	
					<td>
						${mbBalanceLog.refId}							
					</td>							
					<th><%=TmbBalanceLog.ALIAS_REF_TYPE%></th>	
					<td>
						${mbBalanceLog.refType}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbBalanceLog.ALIAS_REASON%></th>	
					<td>
						${mbBalanceLog.reason}							
					</td>							
					<th><%=TmbBalanceLog.ALIAS_REMARK%></th>	
					<td>
						${mbBalanceLog.remark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>