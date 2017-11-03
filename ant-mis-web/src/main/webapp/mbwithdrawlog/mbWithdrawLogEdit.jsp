<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbWithdrawLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbWithdrawLogController/edit',
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
				<input type="hidden" name="id" value = "${mbWithdrawLog.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_TENANT_ID%></th>	
					<td>
											<input class="span2" name="tenantId" type="text" value="${mbWithdrawLog.tenantId}"/>
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_ADDTIME%>'})"   maxlength="0" value="${mbWithdrawLog.addtime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${mbWithdrawLog.updatetime}"/>
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbWithdrawLog.isdeleted}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_BALANCE_ID%></th>	
					<td>
											<input class="span2" name="balanceId" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbWithdrawLog.balanceId}"/>
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_AMOUNT%></th>	
					<td>
											<input class="span2" name="amount" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbWithdrawLog.amount}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_REF_TYPE%></th>	
					<td>
											<input class="span2" name="refType" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbWithdrawLog.refType}"/>
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_APPLY_LOGIN_ID%></th>	
					<td>
											<input class="span2" name="applyLoginId" type="text" value="${mbWithdrawLog.applyLoginId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_REMITTER%></th>	
					<td>
											<input class="span2" name="remitter" type="text" value="${mbWithdrawLog.remitter}"/>
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_REMITTER_TIME%></th>	
					<td>
					<input class="span2" name="remitterTime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_REMITTER_TIME%>'})"   maxlength="0" value="${mbWithdrawLog.remitterTime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_CONTENT%></th>	
					<td>
											<input class="span2" name="content" type="text" value="${mbWithdrawLog.content}"/>
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_BANK_CODE%></th>	
					<td>
											<jb:select dataType="TB" name="bankCode" value="${mbWithdrawLog.bankCode}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_HANDLE_STATUS%></th>	
					<td>
											<jb:select dataType="HS" name="handleStatus" value="${mbWithdrawLog.handleStatus}"></jb:select>	
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_HANDLE_LOGIN_ID%></th>	
					<td>
											<input class="span2" name="handleLoginId" type="text" value="${mbWithdrawLog.handleLoginId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_HANDLE_REMARK%></th>	
					<td>
											<input class="span2" name="handleRemark" type="text" value="${mbWithdrawLog.handleRemark}"/>
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_HANDLE_TIME%></th>	
					<td>
					<input class="span2" name="handleTime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_HANDLE_TIME%>'})"   maxlength="0" value="${mbWithdrawLog.handleTime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_PAY_CODE%></th>	
					<td>
											<input class="span2" name="payCode" type="text" value="${mbWithdrawLog.payCode}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>