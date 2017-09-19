<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbOrderController/editOrderAcceptAudit',
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
					parent.$.modalDialog.openner_dataGrid.location.reload();//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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
			<input type="hidden" name="id" value = "${mbOrder.id}"/>
			<input name="status" type="hidden"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th>到货时间</th>
					<td>
						<input class="span2" name="deliveryRequireTime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbOrder.FORMAT_ADDTIME%>'})"  value="<fmt:formatDate value='${mbOrder.deliveryRequireTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
					</td>
					<th>仓库</th>
					<td>
						<jb:selectSql dataType="SQ005" name="warehouseId" required="true" ></jb:selectSql>
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<textarea class ="easyui-validatebox" data-options="required:true" name="reason" style="width: 90%" placeholder="请录入审核信息"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>