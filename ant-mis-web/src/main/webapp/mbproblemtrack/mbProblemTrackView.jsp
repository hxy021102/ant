<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbProblemTrack" %>
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
					<th><%=TmbProblemTrack.ALIAS_TENANT_ID%></th>	
					<td>
						${mbProblemTrack.tenantId}							
					</td>							
					<th><%=TmbProblemTrack.ALIAS_ADDTIME%></th>	
					<td>
						${mbProblemTrack.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_UPDATETIME%></th>	
					<td>
						${mbProblemTrack.updatetime}							
					</td>							
					<th><%=TmbProblemTrack.ALIAS_ISDELETED%></th>	
					<td>
						${mbProblemTrack.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_TITLE%></th>	
					<td>
						${mbProblemTrack.title}							
					</td>							
					<th><%=TmbProblemTrack.ALIAS_DETAILS%></th>	
					<td>
						${mbProblemTrack.details}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_STATUS%></th>	
					<td>
						${mbProblemTrack.status}							
					</td>							
					<th><%=TmbProblemTrack.ALIAS_OWNER_ID%></th>	
					<td>
						${mbProblemTrack.ownerId}
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_REF_TYPE%></th>	
					<td>
						${mbProblemTrack.refType}
					</td>							
					<th><%=TmbProblemTrack.ALIAS_ORDER_ID%></th>	
					<td>
						${mbProblemTrack.orderId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbProblemTrack.ALIAS_LAST_OWNER_ID%></th>	
					<td>
						${mbProblemTrack.lastOwnerId}							
					</td>							
				</tr>		
		</table>
	</div>
</div>