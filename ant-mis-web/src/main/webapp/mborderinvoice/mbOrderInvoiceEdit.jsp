<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrderInvoice" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbOrderInvoiceController/edit',
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
				<input type="hidden" name="id" value = "${mbOrderInvoice.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_TENANT_ID%></th>	
					<td>
											<input class="span2" name="tenantId" type="text" value="${mbOrderInvoice.tenantId}"/>
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbOrderInvoice.FORMAT_ADDTIME%>'})"   maxlength="0" value="${mbOrderInvoice.addtime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbOrderInvoice.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${mbOrderInvoice.updatetime}"/>
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbOrderInvoice.isdeleted}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_ORDER_ID%></th>	
					<td>
											<input class="span2" name="orderId" type="text" value="${mbOrderInvoice.orderId}"/>
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_INVOICE_STATUS%></th>	
					<td>
											<jb:select dataType="IS" name="invoiceStatus" value="${mbOrderInvoice.invoiceStatus}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_COMPANY_NAME%></th>	
					<td>
											<input class="span2" name="companyName" type="text" value="${mbOrderInvoice.companyName}"/>
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_COMPANY_TFN%></th>	
					<td>
											<input class="span2" name="companyTfn" type="text" value="${mbOrderInvoice.companyTfn}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_REGISTER_ADDRESS%></th>	
					<td>
											<input class="span2" name="registerAddress" type="text" value="${mbOrderInvoice.registerAddress}"/>
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_REGISTER_PHONE%></th>	
					<td>
											<input class="span2" name="registerPhone" type="text" value="${mbOrderInvoice.registerPhone}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_BANK_NAME%></th>	
					<td>
											<input class="span2" name="bankName" type="text" value="${mbOrderInvoice.bankName}"/>
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_BANK_NUMBER%></th>	
					<td>
											<input class="span2" name="bankNumber" type="text" value="${mbOrderInvoice.bankNumber}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_INVOICE_USE%></th>	
					<td>
											<jb:select dataType="IU" name="invoiceUse" value="${mbOrderInvoice.invoiceUse}"></jb:select>	
					</td>							
					<th><%=TmbOrderInvoice.ALIAS_LOGIN_ID%></th>	
					<td>
											<input class="span2" name="loginId" type="text" value="${mbOrderInvoice.loginId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbOrderInvoice.ALIAS_REMARK%></th>	
					<td>
											<input class="span2" name="remark" type="text" value="${mbOrderInvoice.remark}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>