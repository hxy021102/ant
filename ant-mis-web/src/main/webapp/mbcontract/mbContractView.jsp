<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbContract" %>
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
					<th><%=TmbContract.ALIAS_TENANT_ID%></th>	
					<td>
						${mbContract.tenantId}							
					</td>							
					<th><%=TmbContract.ALIAS_ADDTIME%></th>	
					<td>
						${mbContract.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbContract.ALIAS_UPDATETIME%></th>	
					<td>
						${mbContract.updatetime}							
					</td>							
					<th><%=TmbContract.ALIAS_ISDELETED%></th>	
					<td>
						${mbContract.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbContract.ALIAS_CODE%></th>	
					<td>
						${mbContract.code}							
					</td>							
					<th><%=TmbContract.ALIAS_NAME%></th>	
					<td>
						${mbContract.name}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbContract.ALIAS_SHOP_ID%></th>	
					<td>
						${mbContract.shopId}							
					</td>							
					<th><%=TmbContract.ALIAS_EXPIRY_DATE_START%></th>	
					<td>
						${mbContract.expiryDateStart}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbContract.ALIAS_EXPIRY_DATE_END%></th>	
					<td>
						${mbContract.expiryDateEnd}							
					</td>							
					<th><%=TmbContract.ALIAS_VALID%></th>	
					<td>
						${mbContract.valid}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbContract.ALIAS_ATTACHMENT%></th>	
					<td>
						${mbContract.attachment}							
					</td>							
				</tr>		
		</table>
	</div>
</div>