<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemStockLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbItemStockLogController/edit',
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
				<input type="hidden" name="id" value = "${mbItemStockLog.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbItemStockLog.ALIAS_TENANT_ID%></th>	
					<td>
											<input class="span2" name="tenantId" type="text" value="${mbItemStockLog.tenantId}"/>
					</td>							
					<th><%=TmbItemStockLog.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbItemStockLog.FORMAT_ADDTIME%>'})"   maxlength="0" value="${mbItemStockLog.addtime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbItemStockLog.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbItemStockLog.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${mbItemStockLog.updatetime}"/>
					</td>							
					<th><%=TmbItemStockLog.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbItemStockLog.isdeleted}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbItemStockLog.ALIAS_ITEM_STOCK_ID%></th>	
					<td>
											<input class="span2" name="itemStockId" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbItemStockLog.itemStockId}"/>
					</td>							
					<th><%=TmbItemStockLog.ALIAS_QUANTITY%></th>	
					<td>
											<input class="span2" name="quantity" type="text" value="${mbItemStockLog.quantity}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbItemStockLog.ALIAS_LOGIN_ID%></th>	
					<td>
											<input class="span2" name="loginId" type="text" value="${mbItemStockLog.loginId}"/>
					</td>							
					<th><%=TmbItemStockLog.ALIAS_LOG_TYPE%></th>	
					<td>
											<jb:select dataType="SL" name="logType" value="${mbItemStockLog.logType}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbItemStockLog.ALIAS_REASON%></th>	
					<td>
											<input class="span2" name="reason" type="text" value="${mbItemStockLog.reason}"/>
					</td>							
					<th><%=TmbItemStockLog.ALIAS_REMARK%></th>	
					<td>
											<input class="span2" name="remark" type="text" value="${mbItemStockLog.remark}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>