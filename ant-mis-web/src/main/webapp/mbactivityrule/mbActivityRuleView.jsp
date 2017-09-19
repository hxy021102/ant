<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbActivityRule" %>
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
					<th><%=TmbActivityRule.ALIAS_NAME%></th>	
					<td colspan="3">
						${mbActivityRule.name}							
					</td>
				</tr>		
				<tr>
					<th><%=TmbActivityRule.ALIAS_LEFT_VALUE%></th>	
					<td>
						${mbActivityRule.leftValue}							
					</td>
					<th><%=TmbActivityRule.ALIAS_RIGHT_VALUE%></th>
					<td>
						${mbActivityRule.rightValue}
					</td>
				</tr>		
				<tr>	
					<th><%=TmbActivityRule.ALIAS_OPERATOR%></th>	
					<td>
						${mbActivityRule.operator}							
					</td>
					<th><%=TmbActivityRule.ALIAS_SEQ%></th>
					<td>
						${mbActivityRule.seq}
					</td>
				</tr>		
				<tr>	
					<th><%=TmbActivityRule.ALIAS_REMARK%></th>	
					<td colspan="3">
						${mbActivityRule.remark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>