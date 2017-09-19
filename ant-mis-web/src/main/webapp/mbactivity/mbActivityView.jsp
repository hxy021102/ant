<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbActivity" %>
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
					<th><%=TmbActivity.ALIAS_NAME%></th>	
					<td>
						${mbActivity.name}							
					</td>
					<th><%=TmbActivity.ALIAS_VALID%></th>
					<td>
						${mbActivity.validName}
					</td>
				</tr>		
				<tr>
					<th><%=TmbActivity.ALIAS_EXPIRY_DATE_START%></th>
					<td>
						${mbActivity.expiryDateStart}
					</td>
					<th><%=TmbActivity.ALIAS_EXPIRY_DATE_END%></th>	
					<td>
						${mbActivity.expiryDateEnd}							
					</td>							

				</tr>		
				<tr>	
					<th><%=TmbActivity.ALIAS_REMARK%></th>	
					<td colspan="3">
						${mbActivity.remark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>