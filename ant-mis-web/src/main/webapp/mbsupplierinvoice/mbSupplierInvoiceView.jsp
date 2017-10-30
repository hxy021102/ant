<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierInvoice" %>
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
					<th><%=TmbSupplierInvoice.ALIAS_COMPANY_NAME%></th>	
					<td>
						${mbSupplierInvoice.companyName}
					</td>
					<th><%=TmbSupplierInvoice.ALIAS_COMPANY_TFN%></th>
					<td>
						${mbSupplierInvoice.companyTfn}
					</td>
				</tr>		
				<tr>
					<th><%=TmbSupplierInvoice.ALIAS_REGISTER_ADDRESS%></th>	
					<td>
						${mbSupplierInvoice.registerAddress}
					</td>
					<th><%=TmbSupplierInvoice.ALIAS_REGISTER_PHONE%></th>
					<td>
						${mbSupplierInvoice.registerPhone}
					</td>
				</tr>		
				<tr>
					<th><%=TmbSupplierInvoice.ALIAS_BANK_NAME%></th>	
					<td>
						${mbSupplierInvoice.bankName}
					</td>
					<th><%=TmbSupplierInvoice.ALIAS_BANK_NUMBER%></th>
					<td>
						${mbSupplierInvoice.bankNumber}
					</td>
				</tr>		
				<tr>
					<th><%=TmbSupplierInvoice.ALIAS_INVOICE_USE%></th>	
					<td>
						${mbSupplierInvoice.invoiceUseName}
					</td>
					<th><%=TmbSupplierInvoice.ALIAS_INVOICE_TYPE%></th>
					<td>
						${mbSupplierInvoice.invoiceTypeName}
					</td>
				</tr>
		</table>
	</div>
</div>