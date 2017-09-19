<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbShopCoupons" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbShopCouponsController/add',
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
				<input type="hidden" name="shopId" value="${shopId}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbShopCoupons.ALIAS_COUPONS_NAME%></th>
					<td>
                        <jb:selectGrid name="couponsId" dataType="couponsId" required="true"></jb:selectGrid>
					</td>
					<th><%=TmbShopCoupons.ALIAS_QUANTITY_TOTAL%></th>
					<td>
						<input  name="quantityTotal" type="number" class="easyui-validatebox span2" data-options="required:true"/>
					</td>

				</tr>
				<tr>
					<th>券票添加方式</th>
					<td>
						<%--<select name="payRate" class="easyui-validatebox span2" data-options="required:true">--%>
<%--0							<option value="100">余额购买</option>--%>
							<%--<option value="0">免费赠送</option>--%>
						<%--</select>--%>
						<jb:select name="payType" dataType="PT" required="true"></jb:select>
					</td>
					<th>初始状态</th>
					<td>

						<select name="status" required="true" style="width: 140px">
								<option value="NS001" >有效</option>
								<option value="NS005" >无效</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><%=TmbShopCoupons.ALIAS_TIME_START%></th>
					<td>
						<input class="span2" name="timeStart" type="text" onclick="WdatePicker({dateFmt:'<%=TmbShopCoupons.FORMAT_TIME_START%>'})"  maxlength="0" class="" />
					</td>
					<th><%=TmbShopCoupons.ALIAS_TIME_END%></th>
					<td>
						<input class="span2" name="timeEnd" type="text" onclick="WdatePicker({dateFmt:'<%=TmbShopCoupons.FORMAT_TIME_END%>'})"  maxlength="0" class="" />
					</td>
				</tr>
				<tr>
					<th><%=TmbShopCoupons.ALIAS_REMARK%></th>	
					<td colspan="3">
						<textarea class="span8" name="remark" />
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>