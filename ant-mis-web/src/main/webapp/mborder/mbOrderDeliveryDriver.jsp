<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbOrderController/updateDeliveryDriver',
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
			<table class="table table-hover table-condensed">
				<c:choose>
					<c:when test="${mbOrder.deliveryWay == 'DW01'}">

					</c:when>
					<c:otherwise>
						<tr>
							<th>司机</th>
							<td colspan="3">
								<jb:selectSql dataType="SQ007" name="deliveryDriver" required="true" value="${mbOrder.deliveryDriver}"></jb:selectSql>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<textarea class ="easyui-validatebox" data-options="required:true" name="remark" style="width: 90%" placeholder="请录入备注"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>