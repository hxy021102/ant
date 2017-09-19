<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#register_Form').form({
			url : '${pageContext.request.contextPath}/api/apiAccountController/register',
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
				$("#register_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'">
			<form id="register_Form" method="post" enctype="multipart/form-data">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td>${pageContext.request.contextPath}/api/apiAccountController/register</td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>iconFile(头像地址)：</label></td>
						<td><input name="iconFile" type="file" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>userName(账号)：</label></td>
						<td><input name="userName" type="text" class="span2" value="18700000000"/>（手机/邮箱）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>nickname(昵称)：</label></td>
						<td><input name="nickname" type="text" class="span2" value="John"/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>password(密码)：</label></td>
						<td><input name="password" type="text" class="span2" value="123456"/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>email(邮箱)：</label></td>
						<td><input name="email" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>recommend(推荐人账号)：</label></td>
						<td><input name="recommend" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
						<input type="button"
							value="提交" onclick="javascript:$('#register_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
				<div id="register_result">
				</div>
			<div>
				结果说明：1、json格式<br/>
					2、success:true 成功<br/>
					3、tokenId：token值
			</div>
		</div>
	</div>
</body>
</html>