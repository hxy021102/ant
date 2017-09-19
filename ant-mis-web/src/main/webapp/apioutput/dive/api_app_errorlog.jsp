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
		$('#app_errorlog_Form').form({
			url : '${pageContext.request.contextPath}/api/apiCommon/upload_errorlog',
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
				$("#app_errorlog_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'">
			<form id="app_errorlog_Form" method="post"  enctype="multipart/form-data">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td>${pageContext.request.contextPath}/api/apiCommon/upload_errorlog</td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>logFile(log日志文件)：</label></td>
						<td><input name="logFile" type="file" class="span2" value=""/>（请上传后缀名为.log）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>systemType(系统类型)：</label></td>
						<td><input name="systemType" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>phoneModel(手机型号)：</label></td>
						<td><input name="phoneModel" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>createdatetime(时间戳)：</label></td>
						<td><input name="createdatetime" type="text" class="span2" value=""/>（格式：yyyy-MM-dd HH:mm:ss）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>note(描述)：</label></td>
						<td><input name="note" type="text" class="span2" value=""/></td>
					</tr>
					
					<tr>
						<td colspan="2" align="center">
						<input type="button"
							value="提交" onclick="javascript:$('#app_errorlog_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
				<div id="app_errorlog_result">
				</div>
			<div>
				结果说明：1、json格式<br/>
					2、success:true 成功<br/>
			</div>
		</div>
	</div>
</body>
</html>