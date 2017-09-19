<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrderInvoice" %>
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
					<th><%=TmbOrderInvoice.ALIAS_ORDER_ID%></th>
					<td>
						${mbOrderInvoice.orderId}
					</td>
					<th><%=TmbOrderInvoice.ALIAS_ADDTIME%></th>	
					<td>
						${mbOrderInvoice.addtime}							
					</td>							
				</tr>
				<tr>
					<th><%=TmbOrderInvoice.ALIAS_LOGIN_ID%></th>
					<td>
						${mbOrderInvoice.loginName}
					</td>
					<th><%=TmbOrderInvoice.ALIAS_INVOICE_STATUS%></th>	
					<td>
						${mbOrderInvoice.invoiceStatusName}
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_COMPANY_NAME%></th>	
					<td>
						${mbOrderInvoice.companyName}							
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_COMPANY_TFN%></th>	
					<td>
						${mbOrderInvoice.companyTfn}							
					</td>							
				</tr>		
				<tr>
					<th><%=TmbOrderInvoice.ALIAS_REGISTER_ADDRESS%></th>	
					<td>
						${mbOrderInvoice.registerAddress}							
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_REGISTER_PHONE%></th>	
					<td>
						${mbOrderInvoice.registerPhone}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_BANK_NAME%></th>	
					<td>
						${mbOrderInvoice.bankName}							
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_BANK_NUMBER%></th>	
					<td>
						${mbOrderInvoice.bankNumber}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_INVOICE_USE%></th>	
					<td colspan="3">
						${mbOrderInvoice.invoiceUseName}
					</td>
				</tr>		
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_REMARK%></th>	
					<td colspan="3">
						${mbOrderInvoice.remark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>