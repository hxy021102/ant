<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbStockOutItem" %>
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
					<th><%=TmbStockOutItem.ALIAS_TENANT_ID%></th>	
					<td>
						${mbStockOutItem.tenantId}							
					</td>							
					<th><%=TmbStockOutItem.ALIAS_ADDTIME%></th>	
					<td>
						${mbStockOutItem.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbStockOutItem.ALIAS_UPDATETIME%></th>	
					<td>
						${mbStockOutItem.updatetime}							
					</td>							
					<th><%=TmbStockOutItem.ALIAS_ISDELETED%></th>	
					<td>
						${mbStockOutItem.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbStockOutItem.ALIAS_MB_STOCK_OUT_ID%></th>	
					<td>
						${mbStockOutItem.mbStockOutId}							
					</td>							
					<th><%=TmbStockOutItem.ALIAS_ITEM_ID%></th>	
					<td>
						${mbStockOutItem.itemId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbStockOutItem.ALIAS_QUANTITY%></th>	
					<td>
						${mbStockOutItem.quantity}							
					</td>							
				</tr>		
		</table>
	</div>
</div>