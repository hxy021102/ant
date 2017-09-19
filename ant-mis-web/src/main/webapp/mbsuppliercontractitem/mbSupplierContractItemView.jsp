<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierContractItem" %>
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
					<th><%=TmbSupplierContractItem.ALIAS_TENANT_ID%></th>	
					<td>
						${mbSupplierContractItem.tenantId}							
					</td>							
					<th><%=TmbSupplierContractItem.ALIAS_ADDTIME%></th>	
					<td>
						${mbSupplierContractItem.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierContractItem.ALIAS_UPDATETIME%></th>	
					<td>
						${mbSupplierContractItem.updatetime}							
					</td>							
					<th><%=TmbSupplierContractItem.ALIAS_ISDELETED%></th>	
					<td>
						${mbSupplierContractItem.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierContractItem.ALIAS_SUPPLIER_CONTRACT_ID%></th>	
					<td>
						${mbSupplierContractItem.supplierContractId}							
					</td>							
					<th><%=TmbSupplierContractItem.ALIAS_ITEM_ID%></th>	
					<td>
						${mbSupplierContractItem.itemId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierContractItem.ALIAS_PRICE%></th>	
					<td>
						${mbSupplierContractItem.price}							
					</td>							
				</tr>		
		</table>
	</div>
</div>