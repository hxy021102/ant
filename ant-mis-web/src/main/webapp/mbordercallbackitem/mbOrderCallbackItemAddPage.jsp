<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrderCallbackItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbOrderCallbackItemController/add',
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
		<input name="orderId" type="hidden" value="${orderId}"/>
		<input name="shopId" type="hidden" value="${shopId}">
		<table class="table table-hover table-condensed">
			<tr>
				<th><%=TmbOrderCallbackItem.ALIAS_ITEM_ID%></th>
				<td>
					<%--<jb:selectGrid dataType="itemId" name="itemId" required="true"></jb:selectGrid>--%>
                    <jb:selectSql dataType="SQ006" name="packId" required="true"></jb:selectSql>
				</td>
				<th><%=TmbOrderCallbackItem.ALIAS_QUANTITY%></th>
				<td>
					<input class="span2 easyui-validatebox" name="quantity" type="number" data-options="required:true" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				</td>
			</tr>
			<tr>
				<th><%=TmbOrderCallbackItem.ALIAS_REMARK%></th>
				<td colspan="3">
					<textarea class="span4 easyui-validatebox" style="width: 90%;" name="remark"  data-options="required:true"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
