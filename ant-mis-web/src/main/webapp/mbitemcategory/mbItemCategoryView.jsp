<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemCategory" %>
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
					<th><%=TmbItemCategory.ALIAS_TENANT_ID%></th>	
					<td>
						${mbItemCategory.tenantId}							
					</td>							
					<th><%=TmbItemCategory.ALIAS_ADDTIME%></th>	
					<td>
						${mbItemCategory.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItemCategory.ALIAS_UPDATETIME%></th>	
					<td>
						${mbItemCategory.updatetime}							
					</td>							
					<th><%=TmbItemCategory.ALIAS_ISDELETED%></th>	
					<td>
						${mbItemCategory.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItemCategory.ALIAS_CODE%></th>	
					<td>
						${mbItemCategory.code}							
					</td>							
					<th><%=TmbItemCategory.ALIAS_NAME%></th>	
					<td>
						${mbItemCategory.name}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItemCategory.ALIAS_PARENT_ID%></th>	
					<td>
						${mbItemCategory.parentId}							
					</td>							
				</tr>		
		</table>
	</div>
</div>