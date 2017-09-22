<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemStock" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbItemStockController/editStock',
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

	function computerQuantity() {
		var adjustment = $("#adjustment").val();
		var quantityNumber = new Number($("#quantity").val());
		var adjustmentNumber = new Number(adjustment);
		$("#afterAdjust").val(adjustmentNumber + quantityNumber);
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
				<input type="hidden" name="id" value = "${mbItemStock.id}"/>
			    <input type="hidden" name="itemId" value = "${mbItemStock.itemId}"/>
				<input type="hidden" name="shopId" value = "${mbItemStock.shopId}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbItemStock.ALIAS_WAREHOUSE_NAME%></th>
					<td>
						<input class="span2" name="warehouseId" type="text" disabled="true" value="${mbItemStock.warehouseName}"/>
					</td>
					<th><%=TmbItemStock.ALIAS_ITEM_NAME%></th>
					<td>
						<input class="span2" name="itemId" type="text" disabled="true"  value="${mbItemStock.itemName}"/>
					</td>
				</tr>
				<tr>
					<th><%=TmbItemStock.ALIAS_QUANTITY%></th>
					<td>
						<input id="quantity" class="span2" name="quantity" type="text" value="${mbItemStock.quantity}" disabled="disabled"/>
					</td>
					<th><%=TmbItemStock.ALIAS_AFTER_ADJUST%></th>
					<td>
						<input id="afterAdjust" class="span2" name="afterAdjust" type="text" disabled="disabled"/>
					</td>

				</tr>
				<tr>
					<th>盘点类型</th>
					<td>
						<jb:select dataType="SL" name="logType" required="true"></jb:select>
					</td>
					<th><%=TmbItemStock.ALIAS_ADJUSTMENT%></th>
					<td>
						<input id="adjustment" class ="easyui-validatebox span2" data-options="required:true" name="adjustment" type="number" onblur="computerQuantity()"/>
					</td>
				</tr>
				<tr>
					<th>原因</th>
					<td colspan="3">
						<textarea class ="easyui-validatebox" data-options="required:true" name="reason" style="width: 90%" placeholder="请录入原因"></textarea>
					</td>
				</tr>
			</table>				
		</form>
	</div>
</div>