<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbCouponsItem" %>
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
					<th><%=TmbCouponsItem.ALIAS_TENANT_ID%></th>	
					<td>
						${mbCouponsItem.tenantId}							
					</td>							
					<th><%=TmbCouponsItem.ALIAS_ADDTIME%></th>	
					<td>
						${mbCouponsItem.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbCouponsItem.ALIAS_UPDATETIME%></th>	
					<td>
						${mbCouponsItem.updatetime}							
					</td>							
					<th><%=TmbCouponsItem.ALIAS_ISDELETED%></th>	
					<td>
						${mbCouponsItem.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbCouponsItem.ALIAS_COUPONS_ID%></th>	
					<td>
						${mbCouponsItem.couponsId}							
					</td>							
					<th><%=TmbCouponsItem.ALIAS_ITEM_ID%></th>	
					<td>
						${mbCouponsItem.itemId}							
					</td>							
				</tr>		
		</table>
	</div>
</div>