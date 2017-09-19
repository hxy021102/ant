<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.controller.BaseController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String url = request.getContextPath()+"/api/apiLogController/addLogDetail";
%>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#add_log_detail_Form').form({
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
				$("#add_log_detail_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'center'">
			<form id="add_log_detail_Form" method="post" enctype="multipart/form-data">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td><%=url%></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值)：</label>
						<td><input name="tokenId" type="text" class="span2"  value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>logId(日志id)：</label>
						<td><input name="logId" type="text" class="span2"  value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>imageFiles(图片上传)：</label>
						<td><input name="imageFiles" type="file" class="span2" value=""/>（支持多个同时上传）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>sumary(描述)：</label>
						<td><input name="sumary" type="text" class="span2"  value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>lgX(经度)：</label>
						<td><input name="lgX" type="text" class="span2"  value=""/>（备用）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>lgY(纬度)：</label>
						<td><input name="lgY" type="text" class="span2"  value=""/>（备用）</td>
					</tr>

					<tr>
						<td colspan="2" align="center"><input type="button"
							value="提交" onclick="javascript:$('#add_log_detail_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：添加潜记明细接口</label>
			<div id="add_log_detail_result"></div>
			<div>
				结果说明：1、json格式<br /> 2、success:true 成功<br /> 

			</div>
		</div>
	</div>
</body>
</html>