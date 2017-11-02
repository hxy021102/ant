<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbShopInvoice" %>
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
					<th><%=TmbShopInvoice.ALIAS_COMPANY_NAME%></th>	
					<td>
						${mbShopInvoice.companyName}							
					</td>
					<th><%=TmbShopInvoice.ALIAS_COMPANY_TFN%></th>
					<td>
						${mbShopInvoice.companyTfn}
					</td>
				</tr>		
				<tr>	

					<th><%=TmbShopInvoice.ALIAS_REGISTER_ADDRESS%></th>	
					<td>
						${mbShopInvoice.registerAddress}							
					</td>
					<th><%=TmbShopInvoice.ALIAS_REGISTER_PHONE%></th>
					<td>
						${mbShopInvoice.registerPhone}
					</td>
				</tr>		
				<tr>	

					<th><%=TmbShopInvoice.ALIAS_BANK_NAME%></th>	
					<td>
						${mbShopInvoice.bankNames}
					</td>
					<th><%=TmbShopInvoice.ALIAS_BANK_NUMBER%></th>
					<td>
						${mbShopInvoice.bankNumber}
					</td>
				</tr>		
				<tr>	

					<th><%=TmbShopInvoice.ALIAS_INVOICE_USE%></th>	
					<td>
						${mbShopInvoice.invoiceUseName}
					</td>
					<th><%=TmbShopInvoice.ALIAS_INVOICE_TYPE%></th>
					<td>
						${mbShopInvoice.invoiceTypeName}
					</td>
				</tr>		

		</table>
	</div>
</div>