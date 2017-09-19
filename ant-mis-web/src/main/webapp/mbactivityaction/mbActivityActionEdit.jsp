<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbActivityAction" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbActivityActionController/edit',
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
					parent.$.modalDialog.openner_actionDataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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
				<input type="hidden" name="id" value = "${mbActivityAction.id}"/>
			<table class="table table-hover table-condensed">

				<tr>	

					<th><%=TmbActivityAction.ALIAS_NAME%></th>	
					<td colspan="3">
											<input class="span2" name="name" type="text" value="${mbActivityAction.name}"/>
					</td>							
			</tr>	
				<tr>
					<th><%=TmbActivityAction.ALIAS_ACTION_TYPE%></th>	
					<td>
											<jb:select dataType="AT" name="actionType" value="${mbActivityAction.actionType}" mustSelect="true"></jb:select>	
					</td>
					<th><%=TmbActivityAction.ALIAS_SEQ%></th>
					<td>
						<input class="span2" name="seq" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbActivityAction.seq}"/>
					</td>
				</tr>
				<tr>	
					<th><%=TmbActivityAction.ALIAS_PARAMETER1%></th>	
					<td>
											<input class="span2" name="parameter1" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbActivityAction.parameter1}"/>
					</td>							
					<th><%=TmbActivityAction.ALIAS_PARAMETER2%></th>	
					<td>
											<input class="span2" name="parameter2" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbActivityAction.parameter2}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>