<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.controller.BaseController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
String url = request.getContextPath()+"/api/apiOrderController/getOrderNumber";
%>
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#order_number_count_Form').form({
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
				$("#order_number_count_result").text(result);
			}
		});
	});
</script>

	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'center'">
			<form id="order_number_count_Form" action="">
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
							value="提交" onclick="javascript:$('#order_number_count_Form').submit();" /></td>
					</tr>
				</table>
			</form>
			<label>结果：</label>
			<div id="order_number_count_result"></div>
			<div>
				结果说明：1、json格式<br /> 2、success:true 成功<br /> 3、obj:对象格式<br />
<table x:str="" cellpadding="0" cellspacing="0">
    <colgroup>
        <col width="72" span="5" style="width:54pt"/>
    </colgroup>
    <tbody>
        <tr height="19" style="height:14.25pt" class="firstRow">
            <td height="14" class="xl22" width="54" style="">
                complete_number
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
             int
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="171" style="border-left-style: none;">
                已完成订单数量
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                uncomplete_number
            </td>
             <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
             int
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
              未完成订单数量
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                cancel_number
            </td>
             <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
             int
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
              已取消订单数量
            </td>
        </tr>
        <tr height="19" style="height:14.25pt">
            <td height="14" class="xl22" style="border-top-style: none;">
                cart_number
            </td>
             <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
             int
            </td>
            <td class="xl22" width="54" style="border-left-style: none;">
            </td>
            <td class="xl22" style="border-top:none;border-left:none" width="117">
              购物车数量
            </td>
        </tr>
    </tbody>
</table>

			</div>
		</div>
	</div>
</body>
</html>