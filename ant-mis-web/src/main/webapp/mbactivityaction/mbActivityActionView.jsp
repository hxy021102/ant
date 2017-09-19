<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbActivityAction" %>
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

					<th><%=TmbActivityAction.ALIAS_NAME%></th>	
					<td colspan="3">
						${mbActivityAction.name}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbActivityAction.ALIAS_SEQ%></th>	
					<td>
						${mbActivityAction.seq}							
					</td>							
					<th><%=TmbActivityAction.ALIAS_ACTION_TYPE%></th>	
					<td>
						${mbActivityAction.actionType}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbActivityAction.ALIAS_PARAMETER1%></th>	
					<td>
						${mbActivityAction.parameter1}							
					</td>							
					<th><%=TmbActivityAction.ALIAS_PARAMETER2%></th>	
					<td>
						${mbActivityAction.parameter2}							
					</td>							
				</tr>		
		</table>
	</div>
</div>