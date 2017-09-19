<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierFinanceLog" %>
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
					<th><%=TmbSupplierFinanceLog.ALIAS_TENANT_ID%></th>	
					<td>
						${mbSupplierFinanceLog.tenantId}							
					</td>							
					<th><%=TmbSupplierFinanceLog.ALIAS_ADDTIME%></th>	
					<td>
						${mbSupplierFinanceLog.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierFinanceLog.ALIAS_UPDATETIME%></th>	
					<td>
						${mbSupplierFinanceLog.updatetime}							
					</td>							
					<th><%=TmbSupplierFinanceLog.ALIAS_ISDELETED%></th>	
					<td>
						${mbSupplierFinanceLog.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierFinanceLog.ALIAS_SUPPLIER_STOCK_IN_ID%></th>	
					<td>
						${mbSupplierFinanceLog.supplierStockInId}							
					</td>							
					<th><%=TmbSupplierFinanceLog.ALIAS_PAY_LOGIN_ID%></th>	
					<td>
						${mbSupplierFinanceLog.payLoginId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierFinanceLog.ALIAS_PAY_STATUS%></th>	
					<td>
						${mbSupplierFinanceLog.payStatus}							
					</td>							
					<th><%=TmbSupplierFinanceLog.ALIAS_INVOICE_STATUS%></th>	
					<td>
						${mbSupplierFinanceLog.invoiceStatus}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierFinanceLog.ALIAS_INVOICE_LOGIN_ID%></th>	
					<td>
						${mbSupplierFinanceLog.invoiceLoginId}							
					</td>							
					<th><%=TmbSupplierFinanceLog.ALIAS_PAY_REMARK%></th>	
					<td>
						${mbSupplierFinanceLog.payRemark}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbSupplierFinanceLog.ALIAS_INVOICE_REMARK%></th>	
					<td>
						${mbSupplierFinanceLog.invoiceRemark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>