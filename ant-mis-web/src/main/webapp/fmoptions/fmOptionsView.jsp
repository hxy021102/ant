<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TfmOptions" %>
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
					<th><%=TfmOptions.ALIAS_ADDTIME%></th>	
					<td>
						${fmOptions.addtime}							
					</td>							
					<th><%=TfmOptions.ALIAS_UPDATETIME%></th>	
					<td>
						${fmOptions.updatetime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TfmOptions.ALIAS_ISDELETED%></th>	
					<td>
						${fmOptions.isdeleted}							
					</td>							
					<th><%=TfmOptions.ALIAS_PROPERTIES_ID%></th>	
					<td>
						${fmOptions.propertiesId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TfmOptions.ALIAS_VALUE%></th>	
					<td>
						${fmOptions.value}							
					</td>							
					<th><%=TfmOptions.ALIAS_SEQ%></th>	
					<td>
						${fmOptions.seq}							
					</td>							
				</tr>		
		</table>
	</div>
</div>