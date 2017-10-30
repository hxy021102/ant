<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplier" %>
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
					<th><%=TmbSupplier.ALIAS_ADDTIME%></th>
					<td>
						${mbSupplier.addtime}							
					</td>
					<th><%=TmbSupplier.ALIAS_UPDATETIME%></th>
					<td>
						${mbSupplier.updatetime}
					</td>
				</tr>		
				<tr>	

				<tr>	
					<th><%=TmbSupplier.ALIAS_NAME%></th>	
					<td>
						${mbSupplier.name}							
					</td>
					<th><%=TmbSupplier.ALIAS_SUPPLIER_CODE%></th>
					<td>
						${mbSupplier.supplierCode}
					</td>
				</tr>		
				<tr>	
					<th><%=TmbSupplier.ALIAS_ADDRESS%></th>	
					<td colspan="3">
						${mbSupplier.address}							
					</td>							

				</tr>		
				<tr>	
					<th><%=TmbSupplier.ALIAS_CONTACT_PEOPLE%></th>	
					<td>
						${mbSupplier.contactPeople}							
					</td>
					<th><%=TmbSupplier.ALIAS_CONTACT_PHONE%></th>
					<td>
						${mbSupplier.contactPhone}
					</td>
				</tr>
				<tr>
					<th><%=TmbSupplier.ALIAS_FINANCIAL_CONTACT_ID%></th>
					<td>
						<c:if test="${mbSupplier.financialContactId != null}">
							${mbSupplier.financialContactPeople}
						</c:if>
					</td>
					<th><%=TmbSupplier.ALIAS_FINANCIAL_CONTACT_PHONE%></th>
					<td>
						${mbSupplier.financialContactPhone}
					</td>
				</tr>
				<tr>
					<th><%=TmbSupplier.ALIAS_REGION_NAME%></th>
					<td>
						${mbSupplier.regionName}
					</td>
					<th><%=TmbSupplier.ALIAS_WAREHOUSE_ID%></th>	
					<td colspan="3">
						${mbSupplier.warehouseName}
					</td>
					<%--<th><%=TmbSupplier.ALIAS_CERTIFICATE_LIST%></th>
					<td>
						${mbSupplier.certificateList}
					</td>			--%>
				</tr>		
		</table>
	</div>
</div>