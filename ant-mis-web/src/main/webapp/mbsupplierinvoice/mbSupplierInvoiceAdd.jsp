<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierInvoice" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbSupplierInvoiceController/add',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">	
		<form id="form" method="post">		
				<input type="hidden" name="id"/>
			    <input type="hidden" name="supplierId" value="${supplierId}">
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbSupplierInvoice.ALIAS_COMPANY_NAME%></th>	
					<td>
											<input class="span2" name="companyName" type="text"/>
					</td>
					<th><%=TmbSupplierInvoice.ALIAS_COMPANY_TFN%></th>
					<td>
						<input class="span2" name="companyTfn" type="text"/>
					</td>
				</tr>	
				<tr>
					<th><%=TmbSupplierInvoice.ALIAS_REGISTER_ADDRESS%></th>	
					<td>
											<input class="span2" name="registerAddress" type="text"/>
					</td>
					<th><%=TmbSupplierInvoice.ALIAS_REGISTER_PHONE%></th>
					<td>
						<input class="span2" name="registerPhone" type="text"/>
					</td>
				</tr>	
				<tr>
					<th><%=TmbSupplierInvoice.ALIAS_BANK_NAME%></th>	
					<td>
						<jb:select name="bankName" dataType="BN"></jb:select>
					</td>
					<th><%=TmbSupplierInvoice.ALIAS_BANK_NUMBER%></th>
					<td>
						         <input class="span2" name="bankNumber" type="text"/>
					</td>
				</tr>	
				<tr>
					<th><%=TmbSupplierInvoice.ALIAS_INVOICE_USE%></th>	
					<td>
											<jb:select dataType="SIU" name="invoiceUse"></jb:select>	
					</td>
					<th><%=TmbSupplierInvoice.ALIAS_INVOICE_TYPE%></th>
					<td>
						<jb:select dataType="SIT" name="invoiceType"></jb:select>
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>