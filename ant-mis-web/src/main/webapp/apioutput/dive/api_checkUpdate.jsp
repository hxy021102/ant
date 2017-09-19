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
		$('#checkUpdate_Form').form({
			url : '${pageContext.request.contextPath}/api/apiCommon/checkUpdate',
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
				$("#checkUpdate_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'">
			<form id="checkUpdate_Form" action="">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td>${pageContext.request.contextPath}/api/apiCommon/checkUpdate</td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>versionNo(当前版本号)：</label></td>
						<td><input name="versionNo" type="text" class="span2" value=""/>(不传默认提示升级)</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
						<input type="button"
							value="提交" onclick="javascript:$('#checkUpdate_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
				<div id="checkUpdate_result">
				</div>
			<div>
				结果说明：1、json格式<br/>
					2、success:true 成功<br/>
					<table x:str="" cellpadding="0" cellspacing="0">
    <colgroup>
        <col width="72" span="5" style="width:54pt"/>
    </colgroup>
    <tbody>
        <tr height="19" style="height:14.25pt" class="firstRow">
            <td height="14" class="xl22" width="54" style="">
                andriod_mark
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="171" style="border-left-style: none;">
                andriod升级标记（true:升级）
            </td>
        </tr>
        <tr height="19" style="height:14.25pt" class="firstRow">
            <td height="14" class="xl22" width="54" style="">
                ios_mark
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="171" style="border-left-style: none;">
               ios升级标记（true:升级）
            </td>
        </tr>
        <tr height="19" style="height:14.25pt" class="firstRow">
            <td height="14" class="xl22" width="54" style="">
                android_filePath
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="171" style="border-left-style: none;">
               andriod版本下载路径
            </td>
        </tr>
        <tr height="19" style="height:14.25pt" class="firstRow">
            <td height="14" class="xl22" width="54" style="">
                ios_downloadUrl
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="171" style="border-left-style: none;">
              ios下载地址
            </td>
        </tr>
	</tbody>
</table>
			</div>
		</div>
	</div>
</body>
</html>