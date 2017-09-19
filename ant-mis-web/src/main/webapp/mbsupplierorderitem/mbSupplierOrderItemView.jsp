<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierOrderItem" %>
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
					<th><%=TmbSupplierOrderItem.ALIAS_TENANT_ID%></th>
					<td>
						${mbSupplierOrderItem.tenantId}
					</td>
					<th><%=TmbSupplierOrderItem.ALIAS_ADDTIME%></th>
					<td>
						${mbSupplierOrderItem.addtime}
					</td>
				</tr>
				<tr>
					<th><%=TmbSupplierOrderItem.ALIAS_UPDATETIME%></th>
					<td>
						${mbSupplierOrderItem.updatetime}
					</td>
					<th><%=TmbSupplierOrderItem.ALIAS_ISDELETED%></th>
					<td>
						${mbSupplierOrderItem.isdeleted}
					</td>
				</tr>
				<tr>	
					<th><%=TmbSupplierOrderItem.ALIAS_SUPPLIER_ORDER_ID%></th>	
					<td>
						${mbSupplierOrderItem.supplierOrderId}							
					</td>							
					<th><%=TmbSupplierOrderItem.ALIAS_ITEM_ID%></th>	
					<td>
						${mbSupplierOrderItem.itemId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierOrderItem.ALIAS_QUANTITY%></th>	
					<td>
						${mbSupplierOrderItem.quantity}							
					</td>							
					<th><%=TmbSupplierOrderItem.ALIAS_PRICE%></th>	
					<td>
						${mbSupplierOrderItem.price}							
					</td>							
				</tr>		
		</table>
	</div>
</div>