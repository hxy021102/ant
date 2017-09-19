<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbWarehouse" %>
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
					<th><%=TmbWarehouse.ALIAS_TENANT_ID%></th>	
					<td>
						${mbWarehouse.tenantId}							
					</td>							
					<th><%=TmbWarehouse.ALIAS_ADDTIME%></th>	
					<td>
						${mbWarehouse.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbWarehouse.ALIAS_UPDATETIME%></th>	
					<td>
						${mbWarehouse.updatetime}							
					</td>							
					<th><%=TmbWarehouse.ALIAS_ISDELETED%></th>	
					<td>
						${mbWarehouse.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbWarehouse.ALIAS_CODE%></th>	
					<td>
						${mbWarehouse.code}							
					</td>							
					<th><%=TmbWarehouse.ALIAS_NAME%></th>	
					<td>
						${mbWarehouse.name}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbWarehouse.ALIAS_REGION_ID%></th>	
					<td>
						${mbWarehouse.regionId}							
					</td>							
					<th><%=TmbWarehouse.ALIAS_ADDRESS%></th>	
					<td>
						${mbWarehouse.address}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbWarehouse.ALIAS_WAREHOUSE_TYPE%></th>	
					<td>
						${mbWarehouse.warehouseType}							
					</td>							
				</tr>		
		</table>
	</div>
</div>