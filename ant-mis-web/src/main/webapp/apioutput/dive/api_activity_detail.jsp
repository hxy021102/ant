<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.controller.BaseController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String url = request.getContextPath()+"/api/apiActivityController/getActivityDetail";
%>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#activity_detail_Form').form({
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
				$("#activity_detail_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'center'">
			<form id="activity_detail_Form" action="">
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
						<td align="right" style="width: 180px;"><label>id(id)：</label></td>
						<td><input name="id" type="text" class="span2" value="" /></td>
					</tr>
					
					<tr>
						<td colspan="2" align="center"><input type="button"
							value="提交" onclick="javascript:$('#activity_detail_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：DiveAccount信息参考个人中心接口</label>
			<div id="activity_detail_result"></div>
			<div>
				结果说明：1、json格式<br /> 2、success:true 成功<br /> 3、obj:数组格式<br />
<table x:str="" cellpadding="0" cellspacing="0" width="872" style="width: 828px;">
    <colgroup>
        <col width="240" style=";width:240px"/>
        <col width="161" span="2" style=";width:161px"/>
        <col width="72" style="width:72px"/>
        <col width="237" style=";width:237px"/>
    </colgroup>
    <tbody>
        <tr height="19" style="height:19px" class="firstRow">
            <td height="19" width="240" style="">
                start_date
            </td>
            <td width="161" style="border-left-style: none;">
                date
            </td>
            <td width="161" style="border-left-style: none;"></td>
            <td width="72" style="border-left-style: none;"></td>
            <td width="237" style="border-left-style: none;">
                开始时间
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                end_date
            </td>
            <td style="border-top:none;border-left:none">
                date
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                结束时间
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                start_addr
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none" x:num="">
                128
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                始发地
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                business_id
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none" x:num="">
                36
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
          业务ID
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                business_type
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none" x:num="">
                36
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
          业务类型
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                end_addr
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none" x:num="">
                128
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                目的地
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                description
            </td>
            <td style="border-top:none;border-left:none">
                longtext
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                描述
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                room_type
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                房型
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                diver_price
            </td>
            <td style="border-top:none;border-left:none">
                float
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                潜水员/位
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                non_drive_price
            </td>
            <td style="border-top:none;border-left:none">
                float
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
               非潜水员
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                single_room_price
            </td>
            <td style="border-top:none;border-left:none">
                float
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
               单人房差/位
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                peer_name
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
              同行
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                isCollect
            </td>
            <td style="border-top:none;border-left:none">
                boolean
            </td>
            <td style="border-top:none;border-left:none" x:num="">
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                是否已收藏（true:是；false:否）
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                praise
            </td>
            <td style="border-top:none;border-left:none">
                boolean
            </td>
            <td style="border-top:none;border-left:none" x:num="">
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                是否已赞（true:是；false:否）
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                isApply
            </td>
            <td style="border-top:none;border-left:none">
                boolean
            </td>
            <td style="border-top:none;border-left:none" x:num="">
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                是否已报名（true:是；false:否）
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                collectNum
            </td>
            <td style="border-top:none;border-left:none">
                int
            </td>
            <td style="border-top:none;border-left:none" x:num="">
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                收藏数量
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                praiseNum
            </td>
            <td style="border-top:none;border-left:none">
                int
            </td>
            <td style="border-top:none;border-left:none" x:num="">
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                赞数量
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                business
            </td>
            <td style="border-top:none;border-left:none">
                Object对象
            </td>
            <td style="border-top:none;border-left:none" x:num="">
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
               业务信息
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="">
                applies
            </td>
            <td style="border-left:none">
                List&lt;DiveAccount&gt;
            </td>
            <td></td>
            <td></td>
            <td>
                报名者列表
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="">
                diveActivityCommentList
            </td>
            <td style="border-left:none" x:str="List&lt;DiveActivityComment&gt; ">
                List&lt;DiveActivityComment&gt;&nbsp;
            </td>
            <td></td>
            <td></td>
            <td>
                评论列表
            </td>
        </tr>
    </tbody>
</table>

评论信息
<table x:str="" cellpadding="0" cellspacing="0" width="673">
    <colgroup>
        <col width="192" style=";width:192px"/>
        <col width="117" style=";width:117px"/>
        <col width="72" span="2" style="width:72px"/>
        <col width="148" style=";width:148px"/>
        <col width="72" style="width:72px"/>
    </colgroup>
    <tbody>
        <tr height="19" style="height:19px" class="firstRow">
            <td height="19" width="192" style="">
                activity_id
            </td>
            <td width="117" style="border-left-style: none;">
                varchar
            </td>
            <td width="72" style="border-left-style: none;" x:num="">
                36
            </td>
            <td width="72" style="border-left-style: none;"></td>
            <td width="148" style="border-left-style: none;">
                活动ID
            </td>
            <td width="72" style="border-left-style: none;"></td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                comment
            </td>
            <td style="border-top:none;border-left:none">
                text
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                评论
            </td>
            <td style="border-top:none;border-left:none"></td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                pid
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none" x:num="">
                36
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                评论上级(当pid为空时为最上级评论)
            </td>
            <td style="border-top:none;border-left:none"></td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                user_id
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none" x:num="">
                36
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                用户ID
            </td>
            <td style="border-top:none;border-left:none"></td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                addtime
            </td>
            <td style="border-top:none;border-left:none">
                date
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                评论时间
            </td>
            <td style="border-top:none;border-left:none"></td>
        </tr>
    </tbody>
</table>

业务信息
<table x:str="" cellpadding="0" cellspacing="0" width="673">
    <colgroup>
        <col width="192" style=";width:192px"/>
        <col width="117" style=";width:117px"/>
        <col width="72" span="2" style="width:72px"/>
        <col width="148" style=";width:148px"/>
        <col width="72" style="width:72px"/>
    </colgroup>
    <tbody>
        <tr height="19" style="height:19px" class="firstRow">
            <td height="19" width="192" style="">
                id
            </td>
            <td width="117" style="border-left-style: none;">
                varchar
            </td>
            <td width="72" style="border-left-style: none;" x:num="">
                36
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
                name
            </td>
            <td style="border-top:none;border-left:none">
                varchar
            </td>
            <td style="border-top:none;border-left:none" x:num="">
                128
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                名称
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                icon
            </td>
            <td style="border-top:none;border-left:none">
                longtext
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                图片地址
            </td>
        </tr>
        <tr height="19" style="height:19px">
            <td height="19" style="border-top-style: none;">
                description
            </td>
            <td style="border-top:none;border-left:none">
                longtext
            </td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none"></td>
            <td style="border-top:none;border-left:none">
                详情地址
            </td>
        </tr>
    </tbody>
</table>
			</div>
		</div>
	</div>
</body>
</html>