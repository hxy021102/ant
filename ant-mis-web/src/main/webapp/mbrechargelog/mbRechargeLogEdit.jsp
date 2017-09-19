<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbRechargeLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbRechargeLogController/edit',
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
				<input type="hidden" name="id" value = "${mbRechargeLog.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_TENANT_ID%></th>	
					<td>
											<input class="span2" name="tenantId" type="text" value="${mbRechargeLog.tenantId}"/>
					</td>							
					<th><%=TmbRechargeLog.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbRechargeLog.FORMAT_ADDTIME%>'})"   maxlength="0" value="${mbRechargeLog.addtime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbRechargeLog.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${mbRechargeLog.updatetime}"/>
					</td>							
					<th><%=TmbRechargeLog.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbRechargeLog.isdeleted}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_BALANCE_ID%></th>	
					<td>
											<input class="span2" name="balanceId" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbRechargeLog.balanceId}"/>
					</td>							
					<th><%=TmbRechargeLog.ALIAS_AMOUNT%></th>	
					<td>
											<input class="span2" name="amount" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbRechargeLog.amount}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_APPLY_LOGIN_ID%></th>	
					<td>
											<input class="span2" name="applyLoginId" type="text" value="${mbRechargeLog.applyLoginId}"/>
					</td>							
					<th><%=TmbRechargeLog.ALIAS_CONTENT%></th>	
					<td>
											<input class="span2" name="content" type="text" value="${mbRechargeLog.content}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_HANDLE_STATUS%></th>	
					<td>
											<jb:select dataType="HS" name="handleStatus" value="${mbRechargeLog.handleStatus}"></jb:select>	
					</td>							
					<th><%=TmbRechargeLog.ALIAS_HANDLE_LOGIN_ID%></th>	
					<td>
											<input class="span2" name="handleLoginId" type="text" value="${mbRechargeLog.handleLoginId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbRechargeLog.ALIAS_HANDLE_REMARK%></th>	
					<td>
											<input class="span2" name="handleRemark" type="text" value="${mbRechargeLog.handleRemark}"/>
					</td>							
					<th><%=TmbRechargeLog.ALIAS_HANDLE_TIME%></th>	
					<td>
					<input class="span2" name="handleTime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbRechargeLog.FORMAT_HANDLE_TIME%>'})"   maxlength="0" value="${mbRechargeLog.handleTime}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>