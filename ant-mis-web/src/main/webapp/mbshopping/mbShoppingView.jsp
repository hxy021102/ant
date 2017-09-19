<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbShopping" %>
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
					<th><%=TmbShopping.ALIAS_TENANT_ID%></th>	
					<td>
						${mbShopping.tenantId}							
					</td>							
					<th><%=TmbShopping.ALIAS_ADDTIME%></th>	
					<td>
						${mbShopping.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopping.ALIAS_UPDATETIME%></th>	
					<td>
						${mbShopping.updatetime}							
					</td>							
					<th><%=TmbShopping.ALIAS_ISDELETED%></th>	
					<td>
						${mbShopping.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopping.ALIAS_USER_ID%></th>	
					<td>
						${mbShopping.userId}							
					</td>							
					<th><%=TmbShopping.ALIAS_ITEM_ID%></th>	
					<td>
						${mbShopping.itemId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopping.ALIAS_QUANTITY%></th>	
					<td>
						${mbShopping.quantity}							
					</td>							
				</tr>		
		</table>
	</div>
</div>