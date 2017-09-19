<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.controller.BaseController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String url = request.getContextPath()+"/api/apiOrderController/shopCartList";
%>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#shop_cart_list_Form').form({
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
				$("#shop_cart_list_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'center'">
			<form id="shop_cart_list_Form" action="">
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
							value="提交" onclick="javascript:$('#shop_cart_list_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
			<div id="shop_cart_list_result"></div>
			<div>
				结果说明：1、json格式<br /> 2、success:true 成功<br /> 3、obj:数组格式<br />

<table x:str="" cellpadding="0" cellspacing="0">
    <colgroup>
        <col width="72" span="5" style="width:54pt"/>
    </colgroup>
    <tbody>
        <tr height="19" style="height:14.25pt" class="firstRow">
            <td height="14" class="xl22" width="54" style="">
                id
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
                varchar
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            	36
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
          		PK
            </td>
            <td class="xl22" width="171" style="border-left-style: none;">
                主键
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                accountId
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                varchar
            </td>
            <td class="xl22" style="border-top:none;border-left:none">36</td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
              用户ID
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                businessId
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                varchar
            </td>
            <td class="xl22" align="right" style="border-top:none;border-left:none" x:num="">
                36
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
              业务ID
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                businessType
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                varchar
            </td>
            <td class="xl22" align="right" style="border-top:none;border-left:none" x:num="">
                4
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
                业务类型{BT}
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                number
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                int
            </td>
            <td class="xl22" align="right" style="border-top:none;border-left:none" x:num="">
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
                数量
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                price
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                float
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
                价格（单个）
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                businessName
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                varchar
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
               业务商品名称
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                businessIcon
            </td>
            <td class="xl22" style="border-top:none;border-left:none">
                varchar
            </td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none"></td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
               业务商品图片地址
            </td>
        </tr>
    </tbody>
</table>

			</div>
		</div>
	</div>
</body>
</html>