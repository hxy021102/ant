<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbContractItem" %>
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
					<th><%=TmbContractItem.ALIAS_TENANT_ID%></th>	
					<td>
						${mbContractItem.tenantId}							
					</td>							
					<th><%=TmbContractItem.ALIAS_ADDTIME%></th>	
					<td>
						${mbContractItem.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbContractItem.ALIAS_UPDATETIME%></th>	
					<td>
						${mbContractItem.updatetime}							
					</td>							
					<th><%=TmbContractItem.ALIAS_ISDELETED%></th>	
					<td>
						${mbContractItem.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbContractItem.ALIAS_CONTRACT_ID%></th>	
					<td>
						${mbContractItem.contractId}							
					</td>							
					<th><%=TmbContractItem.ALIAS_ITEM_ID%></th>	
					<td>
						${mbContractItem.itemId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbContractItem.ALIAS_PRICE%></th>	
					<td>
						${mbContractItem.price}							
					</td>							
				</tr>		
		</table>
	</div>
</div>