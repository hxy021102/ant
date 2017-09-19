<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbProblemTrack" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbProblemTrackController/edit',
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
				<input type="hidden" name="id" value = "${mbProblemTrack.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_TENANT_ID%></th>	
					<td>
											<input class="span2" name="tenantId" type="text" value="${mbProblemTrack.tenantId}"/>
					</td>							
					<th><%=TmbProblemTrack.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbProblemTrack.FORMAT_ADDTIME%>'})"   maxlength="0" value="${mbProblemTrack.addtime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbProblemTrack.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${mbProblemTrack.updatetime}"/>
					</td>							
					<th><%=TmbProblemTrack.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbProblemTrack.isdeleted}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_TITLE%></th>	
					<td>
											<input class="span2" name="title" type="text" value="${mbProblemTrack.title}"/>
					</td>							
					<th><%=TmbProblemTrack.ALIAS_DETAILS%></th>	
					<td>
											<input class="span2" name="details" type="text" value="${mbProblemTrack.details}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_STATUS%></th>	
					<td>
											<jb:select dataType="KK" name="status" value="${mbProblemTrack.status}"></jb:select>	
					</td>							
					<th><%=TmbProblemTrack.ALIAS_OWNER_ID%></th>	
					<td>
											<input class="span2" name="ownerId" type="text" value="${mbProblemTrack.ownerId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_REF_TYPE%></th>	
					<td>
											<input class="span2" name="refType" type="text" value="${mbProblemTrack.refType}"/>
					</td>							
					<th><%=TmbProblemTrack.ALIAS_ORDER_ID%></th>	
					<td>
											<input class="span2" name="orderId" type="text" value="${mbProblemTrack.orderId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_LAST_OWNER_ID%></th>	
					<td>
											<input class="span2" name="lastOwnerId" type="text" value="${mbProblemTrack.lastOwnerId}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>