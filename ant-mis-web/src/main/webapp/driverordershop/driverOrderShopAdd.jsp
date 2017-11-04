<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/driverOrderShopController/add',
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
					<th>门店运单</th>
					<td>
						<input class="span2" name="deliverOrderShopId" type="text"/>
					</td>							
					<th>门店</th>
					<td>
						<input class="span2" name="shopId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th>骑手运单状态</th>
					<td>
						<jb:select dataType="DDSS" name="status"></jb:select>
					</td>							
					<th>配送费</th>
					<td>
						<input class="span2" name="amount" type="number"/>
					</td>							
				</tr>	
				<tr>	
					<th>支付状态</th>
					<td>
						<jb:select dataType="DDPS" name="payStatus"></jb:select>
					</td>							
					<th>支付账单</th>
					<td>
						<input class="span2" name="driverOrderShopBillId" type="text"/>
					</td>							
				</tr>
				<tr>
					<%--<th>门店运单ID</th>--%>
					<%--<td>--%>
						<%--<input class="span2" name="deliverOrderShopId" type="text" />--%>
					<%--</td>--%>
				</tr>
			</table>		
		</form>
	</div>
</div>