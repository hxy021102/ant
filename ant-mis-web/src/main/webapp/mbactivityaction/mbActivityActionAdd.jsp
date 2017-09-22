<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbActivityAction" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbActivityActionController/add',
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
				<input type="hidden" name="id"/>
			    <input type="hidden" name="activityRuleId">
			<table class="table table-hover table-condensed">
				<tr>
					<th style="width: 60px;"><%=TmbActivityAction.ALIAS_NAME%></th>
					<td colspan="3">
						<input class="span2" name="name" type="text"/>
					</td>							

					<th style="width: 50px;"><%=TmbActivityAction.ALIAS_SEQ%></th>
					<td>
						<input  name="seq" type="text" class="easyui-validatebox span2" data-options="required:true"/>
					</td>							
				</tr>
				<tr>	
					<th style="width: 50px;"><%=TmbActivityAction.ALIAS_PARAMETER1%></th>
					<td  colspan="6">
						<%--<input  name="parameter1" type="text" class="easyui-validatebox span2" data-options="required:true"/>--%>
                        <textarea style="width: 90%;" name="parameter1"  data-options="required:true" rows="10"></textarea>
					</td>
				</tr>
				<tr>
				<th style="width: 50px;"><%=TmbActivityAction.ALIAS_PARAMETER2%></th>
					<td>
						<input  name="parameter2" type="text" class="easyui-validatebox span2" data-options="required:false"/>
					</td>
					<th style="width: 50px;">类型</th>
					<td>
						<jb:select name="actionType" dataType="AM" required="true"></jb:select>
					</td>
				</tr>	
			</table>		
		</form>
	</div>
</div>