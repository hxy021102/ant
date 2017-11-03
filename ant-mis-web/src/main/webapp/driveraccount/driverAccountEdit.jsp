<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bx.ant.model.TdriverAccount" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/driverAccountController/edit',
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
				<input type="hidden" name="id" value = "${driverAccount.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TdriverAccount.ALIAS_TENANT_ID%></th>	
					<td>
											<input class="span2" name="tenantId" type="text" value="${driverAccount.tenantId}"/>
					</td>							
					<th><%=TdriverAccount.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TdriverAccount.FORMAT_ADDTIME%>'})"   maxlength="0" value="${driverAccount.addtime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverAccount.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TdriverAccount.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${driverAccount.updatetime}"/>
					</td>							
					<th><%=TdriverAccount.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${driverAccount.isdeleted}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverAccount.ALIAS_USER_NAME%></th>	
					<td>
											<input class="span2" name="userName" type="text" value="${driverAccount.userName}"/>
					</td>							
					<th><%=TdriverAccount.ALIAS_PASSWORD%></th>	
					<td>
											<input class="span2" name="password" type="text" value="${driverAccount.password}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverAccount.ALIAS_NICK_NAME%></th>	
					<td>
											<input class="span2" name="nickName" type="text" value="${driverAccount.nickName}"/>
					</td>							
					<th><%=TdriverAccount.ALIAS_ICON%></th>	
					<td>
											<input class="span2" name="icon" type="text" value="${driverAccount.icon}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverAccount.ALIAS_SEX%></th>	
					<td>
											<jb:select dataType="SX" name="sex" value="${driverAccount.sex}"></jb:select>	
					</td>							
					<th><%=TdriverAccount.ALIAS_REF_ID%></th>	
					<td>
											<input class="span2" name="refId" type="text" value="${driverAccount.refId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverAccount.ALIAS_REF_TYPE%></th>	
					<td>
											<jb:select dataType="RT" name="refType" value="${driverAccount.refType}"></jb:select>	
					</td>							
					<th><%=TdriverAccount.ALIAS_CONACT_PHONE%></th>	
					<td>
											<input class="span2" name="conactPhone" type="text" value="${driverAccount.conactPhone}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverAccount.ALIAS_TYPE%></th>	
					<td>
											<jb:select dataType="DATP" name="type" value="${driverAccount.type}"></jb:select>	
					</td>							
					<th><%=TdriverAccount.ALIAS_HANDLE_STATUS%></th>	
					<td>
											<jb:select dataType="DAHS" name="handleStatus" value="${driverAccount.handleStatus}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TdriverAccount.ALIAS_HANDLE_LOGIN_ID%></th>	
					<td>
											<input class="span2" name="handleLoginId" type="text" value="${driverAccount.handleLoginId}"/>
					</td>							
					<th><%=TdriverAccount.ALIAS_HANDLE_REMARK%></th>	
					<td>
											<input class="span2" name="handleRemark" type="text" value="${driverAccount.handleRemark}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>