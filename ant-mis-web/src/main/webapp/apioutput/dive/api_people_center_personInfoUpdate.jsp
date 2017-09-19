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
		$('#personInfoUpdate_Form').form({
			url : '${pageContext.request.contextPath}/api/apiAccountController/updatePersonInfo',
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
				$("#personInfoUpdate_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'">
			<form id="personInfoUpdate_Form" action="" method="post">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td>${pageContext.request.contextPath}/api/apiAccountController/updatePersonInfo</td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值)：</label></td>
						<td><input name="tokenId" type="text" class="span2" value=""/></td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>nickname(昵称)：</label></td>
						<td><input name="nickname" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>sex(性别)：</label></td>
						<td><input name="sex" type="text" class="span2" value=""/>（SX01：男；SX02：女）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>birthdayStr(生日)：</label></td>
						<td><input name="birthdayStr" type="text" class="span2" value=""/>(格式：yyyy-MM-dd)</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>city(我的地址)：</label></td>
						<td><input name="city" type="text" class="span2" value=""/>（格式：洲_国_省_市区; 如：亚洲_中国_上海市_浦东新区）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>email(邮箱)：</label></td>
						<td><input name="email" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>personality(个性签名)：</label></td>
						<td><input name="personality" type="text" class="span2" value=""/></td>
					</tr>
					
					<tr>
						<td colspan="2">每次启动手机app则调用此接口进行修改经纬度</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>longitude(经度)：</label></td>
						<td><input name="longitude" type="text" class="span2" value=""/>（小数点不得超过10位有效数字）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>latitude(纬度)：</label></td>
						<td><input name="latitude" type="text" class="span2" value=""/>（小数点不得超过10位有效数字）</td>
					</tr>
					
					<tr>
						<td colspan="2" align="center">
						<input type="button"
							value="提交" onclick="javascript:$('#personInfoUpdate_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>接口说明：个人信息修改，更新数据接口</label>
			<label>结果：</label>
				<div id="personInfoUpdate_result">
				</div>
			<div>
				结果说明：1、json格式<br/>
					2、success:true 成功<br/>
					
			</div>
		</div>
	</div>
</body>
</html>