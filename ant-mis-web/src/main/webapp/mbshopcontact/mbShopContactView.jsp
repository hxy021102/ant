<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbShopContact" %>
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
					<th><%=TmbShopContact.ALIAS_TENANT_ID%></th>	
					<td>
						${mbShopContact.tenantId}							
					</td>							
					<th><%=TmbShopContact.ALIAS_ADDTIME%></th>	
					<td>
						${mbShopContact.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopContact.ALIAS_UPDATETIME%></th>	
					<td>
						${mbShopContact.updatetime}							
					</td>							
					<th><%=TmbShopContact.ALIAS_ISDELETED%></th>	
					<td>
						${mbShopContact.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopContact.ALIAS_SHOP_ID%></th>	
					<td>
						${mbShopContact.shopId}							
					</td>							
					<th><%=TmbShopContact.ALIAS_CONTACT_NAME%></th>	
					<td>
						${mbShopContact.contactName}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopContact.ALIAS_TEL_NUMBER%></th>	
					<td>
						${mbShopContact.telNumber}							
					</td>							
				</tr>		
		</table>
	</div>
</div>