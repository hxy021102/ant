<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbUserInvoice" %>
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
					<th><%=TmbUserInvoice.ALIAS_TENANT_ID%></th>	
					<td>
						${mbUserInvoice.tenantId}							
					</td>							
					<th><%=TmbUserInvoice.ALIAS_ADDTIME%></th>	
					<td>
						${mbUserInvoice.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbUserInvoice.ALIAS_UPDATETIME%></th>	
					<td>
						${mbUserInvoice.updatetime}							
					</td>							
					<th><%=TmbUserInvoice.ALIAS_ISDELETED%></th>	
					<td>
						${mbUserInvoice.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbUserInvoice.ALIAS_USER_ID%></th>	
					<td>
						${mbUserInvoice.userId}							
					</td>							
					<th><%=TmbUserInvoice.ALIAS_COMPANY_NAME%></th>	
					<td>
						${mbUserInvoice.companyName}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbUserInvoice.ALIAS_COMPANY_TFN%></th>	
					<td>
						${mbUserInvoice.companyTfn}							
					</td>							
					<th><%=TmbUserInvoice.ALIAS_REGISTER_ADDRESS%></th>	
					<td>
						${mbUserInvoice.registerAddress}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbUserInvoice.ALIAS_REGISTER_PHONE%></th>	
					<td>
						${mbUserInvoice.registerPhone}							
					</td>							
					<th><%=TmbUserInvoice.ALIAS_BANK_NAME%></th>	
					<td>
						${mbUserInvoice.bankName}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbUserInvoice.ALIAS_BANK_NUMBER%></th>	
					<td>
						${mbUserInvoice.bankNumber}							
					</td>							
					<th><%=TmbUserInvoice.ALIAS_INVOICE_USE%></th>	
					<td>
						${mbUserInvoice.invoiceUse}							
					</td>							
				</tr>		
		</table>
	</div>
</div>