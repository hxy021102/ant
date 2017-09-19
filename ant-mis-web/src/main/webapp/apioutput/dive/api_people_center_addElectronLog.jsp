<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.controller.BaseController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String url = request.getContextPath()+"/api/apiLogController/addElectronLog";
%>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#add_electronLog_Form').form({
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
				$("#add_electronLog_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'center'">
			<form id="add_electronLog_Form"  method="post" enctype="multipart/form-data">
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
						<td align="right" style="width: 180px;"><label>id(日志id)：</label>
						<td><input name="id" type="text" class="span2"  value=""/>(传id即为修改接口)</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>logType(日志类型)：</label>
						<td><input name="logType" type="text" class="span2"  value="LT01"/>(LT01：电子日志；LT02：纸张日志)</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>diveDateStr(潜水时间)：</label>
						<td><input name="diveDateStr" type="text" class="span2"  value=""/>(格式：yyyy-MM-dd HH:mm)</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>diveAddress(潜水地点)：</label>
						<td><input name="diveAddress" type="text" class="span2"  value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>diveType(潜水类型)：</label>
						<td><input name="diveType" type="text" class="span2"  value="DT01"/>（DT01：休闲潜水；DT02：观光潜水；DT03：体验潜水；DT04：探险潜水）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>weather(天气)：</label>
						<td><input name="weather" type="text" class="span2"  value="WT01"/>（WT01：晴天；WT02：阴天；WT03：晴转多云；WT04：小雨；WT05：中雨；WT06：大雨；WT07：阵雨；WT08：雷阵雨；WT09：雾）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>weatherTemperature(气温)：</label>
						<td><input name="weatherTemperature" type="text" class="span2"  value="20"/>（单位：°C）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>waterTemperature(水温)：</label>
						<td><input name="waterTemperature" type="text" class="span2"  value="10"/>（单位：°C）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>seeing(能见度)：</label>
						<td><input name="seeing" type="text" class="span2"  value="20"/>（单位：m）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>windPower(风力)：</label>
						<td><input name="windPower" type="text" class="span2"  value="WP10"/>(参考基础数据类型WP)</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>inTimeStr(入水时间)：</label>
						<td><input name="inTimeStr" type="text" class="span2"  value=""/>（格式：HH:mm,固定为潜水时间的HH:mm）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>outTimeStr(出水时间)：</label>
						<td><input name="outTimeStr" type="text" class="span2"  value=""/>（格式：HH:mm）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>diveHeight(潜水深度)：</label>
						<td><input name="diveHeight" type="text" class="span2"  value="20"/>（单位：m）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>highGas(含氧量)：</label>
						<td><input name="highGas" type="text" class="span2"  value=""/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>gasStart(开始氧量)：</label>
						<td><input name="gasStart" type="text" class="span2"  value="200"/>（单位：bar）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>gasEnd(结束氧量)：</label>
						<td><input name="gasEnd" type="text" class="span2"  value="50"/>（单位：bar）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>imageFiles(图片上传)：</label>
						<td><input name="imageFiles" type="file" class="span2" value=""/>（支持多个同时上传）</td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>sumary(描述/标题)：</label>
						<td><input name="sumary" type="text" class="span2"  value=""/></td>
					</tr>
					
					<tr>
						<td colspan="2" align="center"><input type="button"
							value="提交" onclick="javascript:$('#add_electronLog_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：添加电子潜水日志接口</label>
			<div id="add_electronLog_result"></div>
			<div>
				结果说明：1、json格式<br /> 2、success:true 成功<br />


			</div>
		</div>
	</div>
</body>
</html>