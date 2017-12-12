<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbStockOutOrder" %>
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
					<th><%=TmbStockOutOrder.ALIAS_TENANT_ID%></th>	
					<td>
						${mbStockOutOrder.tenantId}							
					</td>							
					<th><%=TmbStockOutOrder.ALIAS_ADDTIME%></th>	
					<td>
						${mbStockOutOrder.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbStockOutOrder.ALIAS_UPDATETIME%></th>	
					<td>
						${mbStockOutOrder.updatetime}							
					</td>							
					<th><%=TmbStockOutOrder.ALIAS_ISDELETED%></th>	
					<td>
						${mbStockOutOrder.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbStockOutOrder.ALIAS_MB_STOCK_OUT_ID%></th>	
					<td>
						${mbStockOutOrder.mbStockOutId}							
					</td>							
					<th><%=TmbStockOutOrder.ALIAS_DELIVER_ORDER_ID%></th>	
					<td>
						${mbStockOutOrder.deliverOrderId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbStockOutOrder.ALIAS_STATUS%></th>	
					<td>
						${mbStockOutOrder.status}							
					</td>							
				</tr>		
		</table>
	</div>
</div>