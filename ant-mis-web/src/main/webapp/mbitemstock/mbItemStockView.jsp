<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemStock" %>
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
					<th><%=TmbItemStock.ALIAS_TENANT_ID%></th>	
					<td>
						${mbItemStock.tenantId}							
					</td>							
					<th><%=TmbItemStock.ALIAS_ADDTIME%></th>	
					<td>
						${mbItemStock.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItemStock.ALIAS_UPDATETIME%></th>	
					<td>
						${mbItemStock.updatetime}							
					</td>							
					<th><%=TmbItemStock.ALIAS_ISDELETED%></th>	
					<td>
						${mbItemStock.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItemStock.ALIAS_ITEM_ID%></th>	
					<td>
						${mbItemStock.itemId}							
					</td>							
					<th><%=TmbItemStock.ALIAS_WAREHOUSE_ID%></th>	
					<td>
						${mbItemStock.warehouseId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItemStock.ALIAS_QUANTITY%></th>	
					<td>
						${mbItemStock.quantity}							
					</td>							
				</tr>		
		</table>
	</div>
</div>