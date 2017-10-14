<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TdeliverOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/deliverOrderController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
				<input type="hidden" name="id" value = "${deliverOrder.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TdeliverOrder.ALIAS_TENANT_ID%></th>	
					<td>
											<input class="span2" name="tenantId" type="text" value="${deliverOrder.tenantId}"/>
					</td>							
					<th><%=TdeliverOrder.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TdeliverOrder.FORMAT_ADDTIME%>'})"   maxlength="0" value="${deliverOrder.addtime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdeliverOrder.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TdeliverOrder.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${deliverOrder.updatetime}"/>
					</td>							
					<th><%=TdeliverOrder.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${deliverOrder.isdeleted}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdeliverOrder.ALIAS_SUPPLIER_ID%></th>	
					<td>
											<input class="span2" name="supplierId" type="text" value="${deliverOrder.supplierId}"/>
					</td>							
					<th><%=TdeliverOrder.ALIAS_AMOUNT%></th>	
					<td>
											<input class="span2" name="amount" type="text" value="${deliverOrder.amount}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdeliverOrder.ALIAS_STATUS%></th>	
					<td>
											<jb:select dataType="DOS" name="status" value="${deliverOrder.status}"></jb:select>	
					</td>							
					<th><%=TdeliverOrder.ALIAS_DELIVERY_STATUS%></th>	
					<td>
											<jb:select dataType="DDS" name="deliveryStatus" value="${deliverOrder.deliveryStatus}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TdeliverOrder.ALIAS_DELIVERY_REQUIRE_TIME%></th>	
					<td>
					<input class="span2" name="deliveryRequireTime" type="text" onclick="WdatePicker({dateFmt:'<%=TdeliverOrder.FORMAT_DELIVERY_REQUIRE_TIME%>'})"   maxlength="0" value="${deliverOrder.deliveryRequireTime}"/>
					</td>							
					<th><%=TdeliverOrder.ALIAS_DELIVERY_ADDRESS%></th>	
					<td>
											<input class="span2" name="deliveryAddress" type="text" value="${deliverOrder.deliveryAddress}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdeliverOrder.ALIAS_DELIVERY_REGION%></th>	
					<td>
											<input class="span2" name="deliveryRegion" type="text" value="${deliverOrder.deliveryRegion}"/>
					</td>							
					<th><%=TdeliverOrder.ALIAS_PAY_STATUS%></th>	
					<td>
											<jb:select dataType="DPS" name="payStatus" value="${deliverOrder.payStatus}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TdeliverOrder.ALIAS_SHOP_PAY_STATUS%></th>	
					<td>
											<jb:select dataType="SPS" name="shopPayStatus" value="${deliverOrder.shopPayStatus}"></jb:select>	
					</td>							
					<th><%=TdeliverOrder.ALIAS_PAY_WAY%></th>	
					<td>
											<jb:select dataType="DPW" name="payWay" value="${deliverOrder.payWay}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TdeliverOrder.ALIAS_CONTACT_PHONE%></th>	
					<td>
											<input class="span2" name="contactPhone" type="text" value="${deliverOrder.contactPhone}"/>
					</td>							
					<th><%=TdeliverOrder.ALIAS_CONTACT_PEOPLE%></th>	
					<td>
											<input class="span2" name="contactPeople" type="text" value="${deliverOrder.contactPeople}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TdeliverOrder.ALIAS_REMARK%></th>	
					<td>
											<input class="span2" name="remark" type="text" value="${deliverOrder.remark}"/>
					</td>							
					<th><%=TdeliverOrder.ALIAS_SHOP_ID%></th>	
					<td>
											<input class="span2" name="shopId" type="text" value="${deliverOrder.shopId}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>