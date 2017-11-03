<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bx.ant.model.TdriverOrderShopBill" %>
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
					<th><%=TdriverOrderShopBill.ALIAS_TENANT_ID%></th>	
					<td>
						${driverOrderShopBill.tenantId}							
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_ADDTIME%></th>	
					<td>
						${driverOrderShopBill.addtime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_UPDATETIME%></th>	
					<td>
						${driverOrderShopBill.updatetime}							
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_ISDELETED%></th>	
					<td>
						${driverOrderShopBill.isdeleted}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_DRIVER_ACCOUNT_ID%></th>	
					<td>
						${driverOrderShopBill.driverAccountId}							
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_SHOP_ID%></th>	
					<td>
						${driverOrderShopBill.shopId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_HANDLE_STATUS%></th>	
					<td>
						${driverOrderShopBill.handleStatus}							
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_HANDLE_REMARK%></th>	
					<td>
						${driverOrderShopBill.handleRemark}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_HANDLE_LOGIN_ID%></th>	
					<td>
						${driverOrderShopBill.handleLoginId}							
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_AMOUNT%></th>	
					<td>
						${driverOrderShopBill.amount}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_START_DATE%></th>	
					<td>
						${driverOrderShopBill.startDate}							
					</td>							
					<th><%=TdriverOrderShopBill.ALIAS_END_DATE%></th>	
					<td>
						${driverOrderShopBill.endDate}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TdriverOrderShopBill.ALIAS_PAY_WAY%></th>	
					<td>
						${driverOrderShopBill.payWay}							
					</td>							
				</tr>		
		</table>
	</div>
</div>