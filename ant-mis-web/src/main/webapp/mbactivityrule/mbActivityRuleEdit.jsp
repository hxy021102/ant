<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbActivityRule" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbActivityRuleController/edit',
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
					parent.$.modalDialog.openner_ruleDataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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
				<input type="hidden" name="id" value = "${mbActivityRule.id}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbActivityRule.ALIAS_NAME%></th>	
					<td colspan="3">
											<input class="span2" name="name" type="text" value="${mbActivityRule.name}"/>
					</td>							
			</tr>	
				<tr>	

					<th><%=TmbActivityRule.ALIAS_LEFT_VALUE%></th>	
					<td>
											<input class="span2" name="leftValue" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbActivityRule.leftValue}"/>
					</td>
					<th><%=TmbActivityRule.ALIAS_RIGHT_VALUE%></th>
					<td>
						<input class="span2" name="rightValue" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbActivityRule.rightValue}"/>
					</td>
				</tr>
				<tr>	
					<th><%=TmbActivityRule.ALIAS_OPERATOR%></th>	
					<td>
											<jb:select dataType="OP" name="operator" value="${mbActivityRule.operator}" mustSelect="true"></jb:select>	
					</td>
					<th><%=TmbActivityRule.ALIAS_SEQ%></th>
					<td>
						<input class="span2" name="seq" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbActivityRule.seq}"/>
					</td>

				</tr>
				<tr>	
					<th><%=TmbActivityRule.ALIAS_REMARK%></th>
					<td colspan="3">
						<textarea name="remark" style="width: 90%" cols="30" rows="5">${mbActivity.remark}</textarea>
					</td>
			</tr>	
			</table>				
		</form>
	</div>
</div>