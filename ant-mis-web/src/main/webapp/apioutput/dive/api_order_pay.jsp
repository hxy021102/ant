<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.controller.BaseController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String url = request.getContextPath()+"/api/apiOrderController/pay";
%>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#pay_success_Form').form({
			url : '<%=url%>',
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
				$("#pay_success_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'center'">
			<form id="pay_success_Form" action="">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td><%=url%></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值)：</label>
						<td><input name="tokenId" type="text" class="span2"  value="<%=BaseController.DEFAULT_TOKEN%>"/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>id(订单ID)：</label></td>
						<td><input name="id" type="text" class="span2" value="" /></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>address(地址)：</label></td>
						<td><input name="address" type="text" class="span2" value="" />(地址 联系人 联系方式)</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>expressName(快递公司)：</label></td>
						<td><input name="expressName" type="text" class="span2" value="" /></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>expressNo(快递单号)：</label></td>
						<td><input name="expressNo" type="text" class="span2" value="" /></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>payWay(支付方式)：</label></td>
						<td><input name="payWay" type="text" class="span2" value="" /></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>remark(备注)：</label></td>
						<td><input name="remark" type="text" class="span2" value="" /></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>channel(第三方支付渠道)：</label></td>
						<td><input name="channel" type="text" class="span2" value="alipay"/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>amount(订单总金额)：</label></td>
						<td><input name="amount" type="text" class="span2" value=""/>(单位元)</td>
					</tr>

					<tr>
						<td colspan="2" align="center"><input type="button"
							value="提交" onclick="javascript:$('#pay_success_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
			<div id="pay_success_result"></div>
			<div>
				结果说明：1、json格式<br /> 2、success:true 成功<br />

			</div>
		</div>
	</div>
</body>
</html>