<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbProblemTrack" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbProblemTrackController/add',
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
			    <input type="hidden" name="status" value="KK01"/>
			    <input type="hidden" name="refType" value="PB01"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbProblemTrack.ALIAS_ORDER_ID%></th>
					<td>
						<input class="span2" name="orderId" type="text" readonly value="${param.id}"/>
					</td>
					<th><%=TmbProblemTrack.ALIAS_TITLE%></th>	
					<td>
											<input class="span2" name="title" type="text"/>
					</td>
				</tr>
				<tr>
					<th><%=TmbProblemTrack.ALIAS_OWNER_ID%></th>
					<td>
						<jb:selectSql dataType="SQ013" name="ownerId"    required="true"></jb:selectSql>
					</td>
				</tr>
				<tr>
					<th><%=TmbProblemTrack.ALIAS_DETAILS%></th>
					<td colspan="3">
						<textarea name="details" style="width: 98%" rows="3"   class="easyui-validatebox" ></textarea>
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>