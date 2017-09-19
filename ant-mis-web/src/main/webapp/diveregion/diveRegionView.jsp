<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TdiveRegion" %>
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
					<th><%=TdiveRegion.ALIAS_REGION_LEVEL%></th>	
					<td>
						${diveRegion.regionLevel}							
					</td>							
					<th><%=TdiveRegion.ALIAS_REGION_NAME_ZH%></th>	
					<td>
						${diveRegion.regionNameZh}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdiveRegion.ALIAS_REGION_NAME_EN%></th>	
					<td>
						${diveRegion.regionNameEn}							
					</td>							
					<th><%=TdiveRegion.ALIAS_REGION_PARENT_ID%></th>	
					<td>
						${diveRegion.regionParentId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdiveRegion.ALIAS_REGION_ID%></th>	
					<td colspan="3">
						${diveRegion.regionId}							
					</td>							
				</tr>		
		</table>
	</div>
</div>