<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.controller.BaseController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String url = request.getContextPath()+"/api/apiActivityController/activity_hot";
%>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#activity_hot_Form').form({
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
				$("#activity_hot_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'center'">
			<form id="activity_hot_Form" action="">
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
						<td colspan="2" align="center"><input type="button"
							value="提交" onclick="javascript:$('#activity_hot_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
			<div id="activity_hot_result"></div>
			<div>
				结果说明：1、json格式<br /> 2、success:true 成功<br /> 3、obj:数组格式<br />

<table x:str="" cellpadding="0" cellspacing="0">
    <colgroup>
        <col width="72" span="5" style="width:54pt"/>
    </colgroup>
    <tbody>
        <tr height="19" style="height:14.25pt" class="firstRow">
            <td height="14" class="xl22" width="54" style="">
                start_date
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
                date
            </td>
            <td class="xl22" width="54" style="border-left-style: none;"></td>
            <td class="xl22" width="54" style="border-left-style: none;"></td>
            <td class="xl22" width="171" style="border-left-style: none;">
                开始时间
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                end_date
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                date
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
                结束时间
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                start_addr
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                varchar
            </td>
            <td class="xl22" align="right" style="border-top:none;border-left:none" x:num="">
                128
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
                始发地
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                addr_id
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                varchar
            </td>
            <td class="xl22" align="right" style="border-top:none;border-left:none" x:num="">
                36
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
                潜点ID
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                end_addr
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                varchar
            </td>
            <td class="xl22" align="right" style="border-top:none;border-left:none" x:num="">
                128
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
                目的地
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                description
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                longtext
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
                描述
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl23" style="">
                commentNum
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td class="xl23" width="117">
                评论数
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl23" style="">
                applyNum
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td class="xl23" width="117">
                报名数
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl23" style="">
                praiseNum
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td class="xl23" width="117">
                赞数
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl23" style="">
                collectNum
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td class="xl23" width="117">
                收藏数
            </td>
        </tr>
         <tr height="19" style="height:14.25pt">
            <td height="14" class="xl23" style="">
                icon
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td class="xl23" width="117">
                业务图片地址
            </td>
        </tr>
    </tbody>
</table>

			</div>
		</div>
	</div>
</body>
</html>