<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.controller.BaseController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String url = request.getContextPath()+"/api/apiBaseDataController/region";
%>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#area_query_Form').form({
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
				$("#area_query_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'center'">
			<form id="area_query_Form" action="">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td><%=url%></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值)：
						<td><input name="tokenId" type="text" class="span2"  value="<%=BaseController.DEFAULT_TOKEN%>"/></td>
					</tr>
					<tr>
						<td align="right" style="width: 180px;"><label>regionParentId(父级编码)：</label></td>
						<td><input name="regionParentId" type="text" class="span2" value="0" />（不传查全部，0代表查洲）</td>
					</tr>
					
					<tr>
						<td colspan="2" align="center"><input type="button"
							value="提交" onclick="javascript:$('#area_query_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：行政区域结果集，查看基础数据接口</label>
			<div id="area_query_result"></div>
			<div>
				结果说明：1、json格式<br /> 2、success:true 成功<br /> 3、obj:数组格式<br />


<table x:str="" cellpadding="0" cellspacing="0" width="601">
    <colgroup>
        <col width="192" style=";width:192px"/>
        <col width="117" style=";width:117px"/>
        <col width="72" span="2" style="width:72px"/>
        <col width="148" style=";width:148px"/>
    </colgroup>
    <tbody>
        <tr height="19" style="height:19px" class="firstRow">
            <td height="19" width="192" style="">
                id
            </td>
            <td width="117" style="border-left-style: none;">
                int
            </td>
            <td width="72" style="border-left-style: none;" x:num="">
            </td>
            <td width="72" style="border-left-style: none;">
                PK
            </td>
            <td width="148" style="border-left-style: none;">
                主键
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                region_level
            </td>
            <td style="border-top:none;border-left:none">
                int
            </td>
            <td style="border-top:none;border-left:none" x:num="">
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                区域级别(1：洲；2：国家；3：省；4：市区)
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                region_name_zh
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none" x:num="">
                128
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                区域中文名
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                region_name_en
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none">
            	128
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
               区域英文名
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                region_parent_id
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none">
            	10
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
              区域父编码（0表示最上级洲）
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                region_id
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none">
            	10
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
              区域编码
            </td>
        </tr>
    </tbody>
</table>

			</div>
		</div>
	</div>
</body>
</html>