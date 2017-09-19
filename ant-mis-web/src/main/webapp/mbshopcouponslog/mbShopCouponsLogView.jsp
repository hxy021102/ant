<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbShopCouponsLog" %>
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
					<th><%=TmbShopCouponsLog.ALIAS_TENANT_ID%></th>	
					<td>
						${mbShopCouponsLog.tenantId}							
					</td>							
					<th><%=TmbShopCouponsLog.ALIAS_ADDTIME%></th>	
					<td>
						${mbShopCouponsLog.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopCouponsLog.ALIAS_UPDATETIME%></th>	
					<td>
						${mbShopCouponsLog.updatetime}							
					</td>							
					<th><%=TmbShopCouponsLog.ALIAS_ISDELETED%></th>	
					<td>
						${mbShopCouponsLog.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopCouponsLog.ALIAS_SHOP_COUPONS_ID%></th>	
					<td>
						${mbShopCouponsLog.shopCouponsId}							
					</td>							
					<th><%=TmbShopCouponsLog.ALIAS_QUANTITY%></th>	
					<td>
						${mbShopCouponsLog.quantity}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopCouponsLog.ALIAS_LOGIN_ID%></th>	
					<td>
						${mbShopCouponsLog.loginId}							
					</td>							
					<th><%=TmbShopCouponsLog.ALIAS_REF_ID%></th>	
					<td>
						${mbShopCouponsLog.refId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopCouponsLog.ALIAS_REF_TYPE%></th>	
					<td>
						${mbShopCouponsLog.refType}							
					</td>							
					<th><%=TmbShopCouponsLog.ALIAS_REASON%></th>	
					<td>
						${mbShopCouponsLog.reason}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TmbShopCouponsLog.ALIAS_REMARK%></th>	
					<td>
						${mbShopCouponsLog.remark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>