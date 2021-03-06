<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbPaymentItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbPaymentItemController/add',
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
				<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_TENANT_ID%></th>	
					<td>
											<input class="span2" name="tenantId" type="text"/>
					</td>							
					<th><%=TmbPaymentItem.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbPaymentItem.FORMAT_ADDTIME%>'})"  maxlength="0" class="required " />
					</td>							
				</tr>	
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbPaymentItem.FORMAT_UPDATETIME%>'})"  maxlength="0" class="required " />
					</td>							
					<th><%=TmbPaymentItem.ALIAS_ISDELETED%></th>	
					<td>
					
											<input  name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_PAYMENT_ID%></th>	
					<td>
											<input class="span2" name="paymentId" type="text"/>
					</td>							
					<th><%=TmbPaymentItem.ALIAS_PAY_WAY%></th>	
					<td>
											<jb:select dataType="PW" name="payWay"></jb:select>	
					</td>							
				</tr>	
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_AMOUNT%></th>	
					<td>
											<input class="span2" name="amount" type="text"/>
					</td>							
					<th><%=TmbPaymentItem.ALIAS_REMITTER%></th>	
					<td>
											<input class="span2" name="remitter" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_REMITTER_TIME%></th>	
					<td>
					<input class="span2" name="remitterTime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbPaymentItem.FORMAT_REMITTER_TIME%>'})"  maxlength="0" class="" />
					</td>							
					<th><%=TmbPaymentItem.ALIAS_REMARK%></th>	
					<td>
											<input class="span2" name="remark" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TmbPaymentItem.ALIAS_REF_ID%></th>	
					<td>
											<input class="span2" name="refId" type="text"/>
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>