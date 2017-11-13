<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/driverOrderShopBillController/editState',
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
				result = $.parseJSON(result);
				if (result.success) {
                    parent.$.modalDialog.opener_url.reload();
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
        $('.money_input').each(function(){
            $(this).val($.formatMoney($(this).val().trim()));
        });
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
				<input type="hidden" name="id" value = "${driverOrderShopBill.id}"/>
				<input type="hidden" name="handleStatus" value = ""/>
			<table class="table table-hover table-condensed">
				<tr>
					<th>骑手账号</th>
					<td>
						<input class="span2" name="userName" readonly type="text" value="${driverOrderShopBill.userName}"/>
					</td>
					<th>支付总金额</th>
					<td>
						<input class="span2 easyui-validatebox money_input" name="priceStr" type="text" readonly value="${driverOrderShopBill.amount}" data-options="required:true"/>
						<input class="span2" name="amount"  type="hidden" value="${driverOrderShopBill.amount}"/>
					</td>
				</tr>
				 <tr>
					<th>审核备注</th>
					<td colspan="4">
						<textarea name="handleRemark" style="width: 97%" rows="4"    class="easyui-validatebox"    > </textarea>
					</td>
				</tr>
			</table>				
		</form>
	</div>
</div>