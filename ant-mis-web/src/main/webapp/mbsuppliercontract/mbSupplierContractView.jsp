<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierContract" %>
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
					<th><%=TmbSupplierContract.ALIAS_TENANT_ID%></th>	
					<td>
						${mbSupplierContract.tenantId}							
					</td>							
					<th><%=TmbSupplierContract.ALIAS_ADDTIME%></th>	
					<td>
						${mbSupplierContract.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierContract.ALIAS_UPDATETIME%></th>	
					<td>
						${mbSupplierContract.updatetime}							
					</td>							
					<th><%=TmbSupplierContract.ALIAS_ISDELETED%></th>	
					<td>
						${mbSupplierContract.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierContract.ALIAS_CODE%></th>	
					<td>
						${mbSupplierContract.code}							
					</td>
					<th><%=TmbSupplierContract.ALIAS_NAME%></th>
					<td>
						${mbSupplierContract.name}
					</td>
				</tr>		
				<tr>
					<th><%=TmbSupplierContract.ALIAS_SUPPLIER_ID%></th>
					<td>
						${mbSupplierContract.supplierId}
					</td>
					<th><%=TmbSupplierContract.ALIAS_EXPIRY_DATE_START%></th>	
					<td>
						${mbSupplierContract.expiryDateStart}							
					</td>
				</tr>		
				<tr>
					<th><%=TmbSupplierContract.ALIAS_EXPIRY_DATE_END%></th>
					<td>
						${mbSupplierContract.expiryDateEnd}
					</td>
					<th><%=TmbSupplierContract.ALIAS_VALID%></th>	
					<td>
						${mbSupplierContract.valid}							
					</td>
				</tr>		
				<tr>
					<th><%=TmbSupplierContract.ALIAS_ATTACHMENT%></th>
					<td>
						${mbSupplierContract.attachment}
					</td>
					<th><%=TmbSupplierContract.ALIAS_CONTRACT_TYPE%></th>	
					<td>
						${mbSupplierContract.contractType}							
					</td>							
				</tr>		
		</table>
	</div>
</div>