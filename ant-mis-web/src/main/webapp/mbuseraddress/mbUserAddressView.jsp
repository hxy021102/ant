<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbUserAddress" %>
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
					<th><%=TmbUserAddress.ALIAS_TENANT_ID%></th>	
					<td>
						${mbUserAddress.tenantId}							
					</td>							
					<th><%=TmbUserAddress.ALIAS_ADDTIME%></th>	
					<td>
						${mbUserAddress.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbUserAddress.ALIAS_UPDATETIME%></th>	
					<td>
						${mbUserAddress.updatetime}							
					</td>							
					<th><%=TmbUserAddress.ALIAS_ISDELETED%></th>	
					<td>
						${mbUserAddress.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbUserAddress.ALIAS_USER_ID%></th>	
					<td>
						${mbUserAddress.userId}							
					</td>							
					<th><%=TmbUserAddress.ALIAS_REGION_ID%></th>	
					<td>
						${mbUserAddress.regionId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbUserAddress.ALIAS_ADDRESS%></th>	
					<td>
						${mbUserAddress.address}							
					</td>							
				</tr>		
		</table>
	</div>
</div>