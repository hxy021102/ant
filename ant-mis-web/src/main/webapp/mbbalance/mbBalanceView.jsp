<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbBalance" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<div class="easyui-layout" data-options="border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbBalance.ALIAS_TENANT_ID%></th>	
					<td>
						${mbBalance.tenantId}							
					</td>							
					<th><%=TmbBalance.ALIAS_ADDTIME%></th>	
					<td>
						${mbBalance.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbBalance.ALIAS_UPDATETIME%></th>	
					<td>
						${mbBalance.updatetime}							
					</td>							
					<th><%=TmbBalance.ALIAS_ISDELETED%></th>	
					<td>
						${mbBalance.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbBalance.ALIAS_AMOUNT%></th>	
					<td>
						${mbBalance.amount}							
					</td>							
					<th><%=TmbBalance.ALIAS_REF_ID%></th>	
					<td>
						${mbBalance.refId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbBalance.ALIAS_REF_TYPE%></th>	
					<td>
						${mbBalance.refType}							
					</td>							
				</tr>		
		</table>
	</div>
</div>