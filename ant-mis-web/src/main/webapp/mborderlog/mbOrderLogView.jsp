<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrderLog" %>
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
					<th><%=TmbOrderLog.ALIAS_TENANT_ID%></th>	
					<td>
						${mbOrderLog.tenantId}							
					</td>							
					<th><%=TmbOrderLog.ALIAS_ADDTIME%></th>	
					<td>
						${mbOrderLog.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderLog.ALIAS_UPDATETIME%></th>	
					<td>
						${mbOrderLog.updatetime}							
					</td>							
					<th><%=TmbOrderLog.ALIAS_ISDELETED%></th>	
					<td>
						${mbOrderLog.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderLog.ALIAS_ORDER_ID%></th>	
					<td>
						${mbOrderLog.orderId}							
					</td>							
					<th><%=TmbOrderLog.ALIAS_LOGIN_ID%></th>	
					<td>
						${mbOrderLog.loginId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderLog.ALIAS_CONTENT%></th>	
					<td>
						${mbOrderLog.content}							
					</td>							
					<th><%=TmbOrderLog.ALIAS_REMARK%></th>	
					<td>
						${mbOrderLog.remark}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderLog.ALIAS_LOG_TYPE%></th>	
					<td>
						${mbOrderLog.logType}							
					</td>							
				</tr>		
		</table>
	</div>
</div>