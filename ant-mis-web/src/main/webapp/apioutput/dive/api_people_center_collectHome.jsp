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
		$('#collectHome_Form').form({
			url : '${pageContext.request.contextPath}/api/apiAccountController/collectHome',
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
				$("#collectHome_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'">
			<form id="collectHome_Form" action="">
				<table align="center" width="90%" class="tablex">
					<tr>
						<td align="right" style="width: 80px;"><label>url：</label></td>
						<td>${pageContext.request.contextPath}/api/apiAccountController/collectHome</td>
					</tr>
					
					<tr>
						<td align="right" style="width: 180px;"><label>tokenId(token值)：</label></td>
						<td><input name="tokenId" type="text" class="span2" value=""/></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
						<input type="button"
							value="提交" onclick="javascript:$('#collectHome_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>接口说明：收藏主页，查看收藏数量数据接口</label>
			<label>结果：</label>
			<div id="collectHome_result"></div>
			<div>
				结果说明：1、json格式<br />
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
                travel_number
            </td>
            <td width="117" style="border-left-style: none;">
                int
            </td>
            <td width="72" style="border-left-style: none;" x:num="">
            </td>
            <td width="72" style="border-left-style: none;"></td>
            <td width="148" style="border-left-style: none;">
                潜水旅游收藏数量
            </td>
        </tr>
        <tr height="19" style="height:19px" class="firstRow">
            <td height="19" width="192" style="">
               address_number
            </td>
            <td width="117" style="border-left-style: none;">
                int
            </td>
            <td width="72" style="border-left-style: none;" x:num=""></td>
            <td width="72" style="border-left-style: none;"></td>
            <td width="148" style="border-left-style: none;">
                潜点收藏数量
            </td>
        </tr>
        <tr height="19" style="height:19px" class="firstRow">
            <td height="19" width="192" style="">
               equip_number
            </td>
            <td width="117" style="border-left-style: none;">
                int
            </td>
            <td width="72" style="border-left-style: none;" x:num=""></td>
            <td width="72" style="border-left-style: none;"></td>
            <td width="148" style="border-left-style: none;">
                装备收藏数量
            </td>
        </tr>
        <tr height="19" style="height:19px" class="firstRow">
            <td height="19" width="192" style="">
               activity_number
            </td>
            <td width="117" style="border-left-style: none;">
                int
            </td>
            <td width="72" style="border-left-style: none;" x:num=""></td>
            <td width="72" style="border-left-style: none;"></td>
            <td width="148" style="border-left-style: none;">
                活动收藏数量
            </td>
        </tr>
        <tr height="19" style="height:19px" class="firstRow">
            <td height="19" width="192" style="">
               course_number
            </td>
            <td width="117" style="border-left-style: none;">
                int
            </td>
            <td width="72" style="border-left-style: none;" x:num=""></td>
            <td width="72" style="border-left-style: none;"></td>
            <td width="148" style="border-left-style: none;">
                视频收藏数量
            </td>
        </tr>
        <tr height="19" style="height:19px" class="firstRow">
            <td height="19" width="192" style="">
               log_number
            </td>
            <td width="117" style="border-left-style: none;">
                int
            </td>
            <td width="72" style="border-left-style: none;" x:num=""></td>
            <td width="72" style="border-left-style: none;"></td>
            <td width="148" style="border-left-style: none;">
                潜水日志收藏数量
            </td>
        </tr>
       
    </tbody>
</table>




			</div>
		</div>
	</div>
</body>
</html>