<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/driverFreightRuleController/edit',
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
				<input type="hidden" name="id" value = "${driverFreightRule.id}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th>重量下限(包含)</th>
					<td>
						<input class="span2" name="weightLower" type="number" value="${driverFreightRule.weightLower}"/>克
					</td>
					<th>重量上限</th>
					<td>
						<input class="span2" name="weightUpper" type="number" value="${driverFreightRule.weightUpper}"/>克
					</td>
				</tr>
				<tr>
					<th>距离量下限(包含)</th>
					<td>
						<input class="span2" name="distanceLower" type="number" value="${driverFreightRule.distanceLower}"/>米
					</td>
					<th>距离上限</th>
					<td>
						<input class="span2" name="distanceUpper" type="number" value="${driverFreightRule.distanceUpper}"/>米
					</td>
				</tr>
				<tr>
					<th>地域</th>
					<td>
						<jb:selectGrid dataType="region" name="regionId" required="true" value="${driverFreightRule.regionId}"></jb:selectGrid>
					</td>
					<th>费用</th>
					<td>
						<input class="span2 money_input" name="freightTemp" type="number" value="${driverFreightRule.freight / 100.00}"/>元
						<input  name="freight" type="hidden"/>
					</td>
				</tr>
			</table>				
		</form>
	</div>
</div>