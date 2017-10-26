<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbRechargeLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbRechargeLogController/editAudit',
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
				<input type="hidden" name="id" value = "${id}"/>
				<input name="handleStatus" type="hidden"/>
			<table class="table table-hover table-condensed">
				<tr>

				</tr>
				<c:choose>
					<c:when test="${mbRechargeLog.bankCode == 'TB10'}">
						<th>订单ID</th>
						<td>
							<input type="text" name="payCode" readonly = "true" value="${mbRechargeLog.payCode}"/>
						</td>
					</c:when>
					<c:when test="${mbRechargeLog.balanceId == -1}">
						<th>汇款单号</th>
						<td>
							<input type="text" name="payCode" readonly = "true" value="${mbRechargeLog.payCode}"/>
						</td>
						<th>门店名称</th>
						<td>
							<jb:selectGrid dataType="shopId" name="shopId"></jb:selectGrid>
						</td>
					</c:when>
					<c:otherwise>
						<th><%=TmbRechargeLog.ALIAS_PAYCODE%></th>
						<td>
							<input type="text" name="payCode" style="width: 90%" />
						</td>
					</c:otherwise>
				</c:choose>
				<tr>
					<th>原因</th>
					<td>
						<textarea name="handleRemark"  class="easyui-validatebox" data-options="required:true"  style="width: 90%"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>