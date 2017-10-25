<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemStock" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/deliverOrderController/upload',
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
		<a href="${pageContext.request.contextPath}/mbitemstock/mbItemStockTemplate.xlsx" target="_blank">下载模板</a>
		<form id="form" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th>供应商</th>
					<td>
						<jb:selectGrid dataType="deliverSupplierId" name="supplierId" required="true"></jb:selectGrid>
					</td>
				</tr>
				<tr>
					<th>文件</th>
					<td colspan="3">
						<input type="file" id="deliverOrderTable" name="file">
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>