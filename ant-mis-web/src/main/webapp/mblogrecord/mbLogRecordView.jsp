<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbLogRecord" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>
					<th>操作时间:</th>
					<td>
						${mbLogRecord.addtime}							
					</td>
					<th><%=TmbLogRecord.ALIAS_LOG_USER_ID%>:</th>
					<td>
						${mbLogRecord.logUserId}							
					</td>
				</tr>
			    <tr>
					<th><%=TmbLogRecord.ALIAS_LOG_USER_NAME%>:</th>
					<td >
						${mbLogRecord.logUserName}							
					</td>
					<th><%=TmbLogRecord.ALIAS_URL%>:</th>
					<td>
						${mbLogRecord.url}							
					</td>
				</tr>
				<tr>
					<th>时长:</th>
					<td >
						${mbLogRecord.processTime}
					</td>
					<th>是否成功:</th>
					<td>
						${mbLogRecord.isSuccess}
					</td>
				</tr>
			    <tr>
					<th><%=TmbLogRecord.ALIAS_URL_NAME%>:</th>
					<td colspan="3">
						${mbLogRecord.urlName}							
					</td>							
				</tr>		
				<tr>
					<th width="100"><%=TmbLogRecord.ALIAS_FORM_DATA%>:</th>
					 <td colspan="3">${mbLogRecord.formData} </td>
				</tr>
				<tr>
					<th>result:</th>
					<td colspan="3">${mbLogRecord.result} </td>
				</tr>
		</table>
	</div>
</div>