<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierStockInItem" %>
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
					<th><%=TmbSupplierStockInItem.ALIAS_TENANT_ID%></th>	
					<td>
						${mbSupplierStockInItem.tenantId}							
					</td>							
					<th><%=TmbSupplierStockInItem.ALIAS_ADDTIME%></th>	
					<td>
						${mbSupplierStockInItem.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierStockInItem.ALIAS_UPDATETIME%></th>	
					<td>
						${mbSupplierStockInItem.updatetime}							
					</td>							
					<th><%=TmbSupplierStockInItem.ALIAS_ISDELETED%></th>	
					<td>
						${mbSupplierStockInItem.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierStockInItem.ALIAS_SUPPLIER_STOCK_IN_ID%></th>	
					<td>
						${mbSupplierStockInItem.supplierStockInId}							
					</td>							
					<th><%=TmbSupplierStockInItem.ALIAS_ITEM_ID%></th>	
					<td>
						${mbSupplierStockInItem.itemId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierStockInItem.ALIAS_QUANTITY%></th>	
					<td>
						${mbSupplierStockInItem.quantity}							
					</td>							
					<th><%=TmbSupplierStockInItem.ALIAS_PRICE%></th>	
					<td>
						${mbSupplierStockInItem.price}							
					</td>							
				</tr>		
		</table>
	</div>
</div>