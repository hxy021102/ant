<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierContractClause" %>
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
					<th><%=TmbSupplierContractClause.ALIAS_SUPPLIER_CONTRACT_ID%></th>	
					<td>
						${mbSupplierContractClause.supplierContractId}							
					</td>							
					<th><%=TmbSupplierContractClause.ALIAS_CLAUSE_CODE%></th>	
					<td>
						${mbSupplierContractClause.clauseName}
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierContractClause.ALIAS_VALUE%></th>	
					<td colspan="4">
						${mbSupplierContractClause.value}							
					</td>
				</tr>
			    <tr>
					<th><%=TmbSupplierContractClause.ALIAS_REMARK%></th>	
					<td colspan="4">
						${mbSupplierContractClause.remark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>