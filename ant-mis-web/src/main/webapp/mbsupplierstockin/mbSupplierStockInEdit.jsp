<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierStockIn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbSupplierStockInController/edit',
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
				<input type="hidden" name="id" value = "${mbSupplierStockIn.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<%--<th><%=TmbSupplierStockIn.ALIAS_SUPPLIER_ORDER_ID%></th>
					<td>
						<input class="span2" name="supplierOrderId" type="text" value="${mbSupplierStockIn.supplierOrderId}"/>
					</td>--%>
					<th ><%=TmbSupplierStockIn.ALIAS_STATUS%></th>
					<td>
											<jb:select dataType="SI" name="status" value="${mbSupplierStockIn.status}"></jb:select>	
					</td>
					<th>
						<td></td>
					</th>
			    </tr>
				<tr>	
					<th><%=TmbSupplierStockIn.ALIAS_SIGN_PEOPLE_ID%></th>	
					<td>
											<input class="span2" name="signPeopleId" type="text" value="${mbSupplierStockIn.signPeopleId}"/>
					</td>							
					<th><%=TmbSupplierStockIn.ALIAS_SIGN_DATE%></th>	
					<td>
					<input class="span2" name="signDate" type="text" onclick="WdatePicker({dateFmt:'<%=TmbSupplierStockIn.FORMAT_SIGN_DATE%>'})"   maxlength="0" value="${mbSupplierStockIn.signDate}"/>
					</td>							
			</tr>	
				<tr>	
					<%--<th><%=TmbSupplierStockIn.ALIAS_RECEIVE_URL%></th>
					<td>
											<input class="span2" name="receiveUrl" type="text" value="${mbSupplierStockIn.receiveUrl}"/>
					</td>	--%>
				<%--	<th><%=TmbSupplierStockIn.ALIAS_LOGIN_ID%></th>
					<td>
											<input class="span2" name="loginId" type="text" value="${mbSupplierStockIn.loginId}"/>
					</td>	--%>
			</tr>	
				<tr>	
					<th><%=TmbSupplierStockIn.ALIAS_PAY_STATUS%></th>	
					<td>
											<jb:select dataType="FS" name="payStatus" value="${mbSupplierStockIn.payStatus}"></jb:select>	
					</td>							
					<th><%=TmbSupplierStockIn.ALIAS_INVOICE_STATUS%></th>	
					<td>
											<jb:select dataType="FP" name="invoiceStatus" value="${mbSupplierStockIn.invoiceStatus}"></jb:select>	
					</td>							
			</tr>	
				<tr>
					<th><%=TmbSupplierStockIn.ALIAS_WAREHOUSE_ID%></th>	
					<td colspan="3">
						<jb:selectSql dataType="SQ005" name="warehouseId" required="true" ></jb:selectSql>

					</td>							
			</tr>
				<tr>
					<th><%=TmbSupplierStockIn.ALIAS_WAREHOUSE_ID%></th>



				</tr>
			</table>				
		</form>
	</div>
</div>