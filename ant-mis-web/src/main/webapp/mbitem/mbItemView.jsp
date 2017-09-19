<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItem" %>
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
					<th><%=TmbItem.ALIAS_TENANT_ID%></th>	
					<td>
						${mbItem.tenantId}							
					</td>							
					<th><%=TmbItem.ALIAS_ADDTIME%></th>	
					<td>
						${mbItem.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItem.ALIAS_UPDATETIME%></th>	
					<td>
						${mbItem.updatetime}							
					</td>							
					<th><%=TmbItem.ALIAS_ISDELETED%></th>	
					<td>
						${mbItem.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItem.ALIAS_CODE%></th>	
					<td>
						${mbItem.code}							
					</td>							
					<th><%=TmbItem.ALIAS_NAME%></th>	
					<td>
						${mbItem.name}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItem.ALIAS_CATEGORY_ID%></th>	
					<td>
						${mbItem.categoryId}							
					</td>							
					<th><%=TmbItem.ALIAS_QUANTITY_UNIT%></th>	
					<td>
						${mbItem.quantityUnit}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbItem.ALIAS_MARKET_PRICE%></th>	
					<td>
						${mbItem.marketPrice}							
					</td>							
				</tr>		
		</table>
	</div>
</div>