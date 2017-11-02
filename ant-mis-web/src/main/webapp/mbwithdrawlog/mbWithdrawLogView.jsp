<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbWithdrawLog" %>
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
					<th><%=TmbWithdrawLog.ALIAS_ADDTIME%></th>
					<td>
						${mbWithdrawLog.addtime}							
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_UPDATETIME%></th>
					<td>
						${mbWithdrawLog.updatetime}
					</td>							
				</tr>
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_BALANCE_ID%></th>	
					<td>
						${mbWithdrawLog.balanceId}							
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_AMOUNT%></th>	
					<td>
						${mbWithdrawLog.amount / 100.00}
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_REF_TYPE%></th>	
					<td>
						${mbWithdrawLog.refTypeName}
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_APPLY_LOGIN_ID%></th>	
					<td>
						${mbWithdrawLog.applyLoginId}
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_REMITTER%></th>	
					<td>
						${mbWithdrawLog.receiver}
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_REMITTER_TIME%></th>	
					<td>
						${mbWithdrawLog.receiverTime}
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_CONTENT%></th>	
					<td colspan="4">
						${mbWithdrawLog.content}							
					</td>
				</tr>		
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_HANDLE_STATUS%></th>	
					<td>
						${mbWithdrawLog.handleStatusName}
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_HANDLE_LOGIN_ID%></th>	
					<td>
						${mbWithdrawLog.handleLoginName}
					</td>							
				</tr>		
				<tr>
					<th><%=TmbWithdrawLog.ALIAS_HANDLE_TIME%></th>	
					<td>
						${mbWithdrawLog.handleTime}
					</td>
					<th><%=TmbWithdrawLog.ALIAS_PAY_CODE%></th>
					<td>
						${mbWithdrawLog.payCode}
					</td>
				</tr>		
				<tr>
					<th><%=TmbWithdrawLog.ALIAS_HANDLE_REMARK%></th>
					<td colspan="4">
						${mbWithdrawLog.handleRemark}
					</td>
				</tr>		
		</table>
	</div>
</div>