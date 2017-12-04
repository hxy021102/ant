<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bx.ant.model.TdriverOrderShopBill" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/driverOrderShopBillController/edit',
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
				<input type="hidden" name="id" value = "${driverOrderShopBill.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_TENANT_ID%></th>	
					<td>
											<input class="span2" name="tenantId" type="text" value="${driverOrderShopBill.tenantId}"/>
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TdriverOrderShopBill.FORMAT_ADDTIME%>'})"   maxlength="0" value="${driverOrderShopBill.addtime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TdriverOrderShopBill.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${driverOrderShopBill.updatetime}"/>
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${driverOrderShopBill.isdeleted}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_DRIVER_ACCOUNT_ID%></th>	
					<td>
											<input class="span2" name="driverAccountId" type="text" value="${driverOrderShopBill.driverAccountId}"/>
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_SHOP_ID%></th>	
					<td>
											<input class="span2" name="shopId" type="text" value="${driverOrderShopBill.shopId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_HANDLE_STATUS%></th>	
					<td>
											<jb:select dataType="DHS" name="handleStatus" value="${driverOrderShopBill.handleStatus}"></jb:select>	
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_HANDLE_REMARK%></th>	
					<td>
											<input class="span2" name="handleRemark" type="text" value="${driverOrderShopBill.handleRemark}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_HANDLE_LOGIN_ID%></th>	
					<td>
											<input class="span2" name="handleLoginId" type="text" value="${driverOrderShopBill.handleLoginId}"/>
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_AMOUNT%></th>	
					<td>
											<input class="span2" name="amount" type="text" value="${driverOrderShopBill.amount}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_START_DATE%></th>	
					<td>
					<input class="span2" name="startDate" type="text" onclick="WdatePicker({dateFmt:'<%=TdriverOrderShopBill.FORMAT_START_DATE%>'})"   maxlength="0" value="${driverOrderShopBill.startDate}"/>
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_END_DATE%></th>	
					<td>
					<input class="span2" name="endDate" type="text" onclick="WdatePicker({dateFmt:'<%=TdriverOrderShopBill.FORMAT_END_DATE%>'})"   maxlength="0" value="${driverOrderShopBill.endDate}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_PAY_WAY%></th>	
					<td>
											<jb:select dataType="DDPW" name="payWay" value="${driverOrderShopBill.payWay}"></jb:select>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>