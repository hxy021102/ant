<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.controller.BaseController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String url = request.getContextPath()+"/api/apiAuditController/add_audit";
%>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#add_audit_Form').form({
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
				$("#add_audit_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'center'">
			<form id="add_audit_Form" action="" method="post">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td><%=url%></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值)：</label>
						<td><input name="tokenId" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>businessId(业务id)：</label></td>
						<td><input name="businessId" type="text" class="span2" value="" />（活动或船宿等）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>businessType(业务类型)：</label></td>
						<td><input name="businessType" type="text" class="span2" value="" /></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>realname(姓名)：</label></td>
						<td><input name="realname" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>mobile(手机号)：</label></td>
						<td><input name="mobile" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>address(地址)：</label></td>
						<td><input name="address" type="text" class="span2" value=""/></td>
					</tr>

					<tr>
						<td colspan="2" align="center"><input type="button"
							value="提交" onclick="javascript:$('#add_audit_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：提交审核结果集，查看基础数据接口</label>
			<div id="add_audit_result"></div>
			<div>
				结果说明：1、json格式<br /> 2、success:true 成功<br /> 



			</div>
		</div>
	</div>
</body>
</html>