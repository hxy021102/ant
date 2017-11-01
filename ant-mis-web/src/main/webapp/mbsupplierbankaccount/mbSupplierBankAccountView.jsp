<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierBankAccount" %>
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
					<th><%=TmbSupplierBankAccount.ALIAS_SUPPLIER_ID%></th>	
					<td>
						${mbSupplierBankAccount.supplierId}							
					</td>							
					<th><%=TmbSupplierBankAccount.ALIAS_ACCOUNT_NAME%></th>	
					<td>
						${mbSupplierBankAccount.accountName}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierBankAccount.ALIAS_ACCOUNT_BANK%></th>	
					<td>
						${mbSupplierBankAccount.accountBank}							
					</td>							
					<th><%=TmbSupplierBankAccount.ALIAS_BANK_NUMBER%></th>	
					<td>
						${mbSupplierBankAccount.bankNumber}							
					</td>							
				</tr>		
		</table>
	</div>
</div>