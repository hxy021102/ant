<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbWithdrawLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbWithdrawLogController/add',
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
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
        $('.money_input').blur(function () {
            var source = $(this);
            var target = source.next();
            if (!/^([-1-9]\d*|0)(\.\d{2})?$/.test(source.val())) {
                source.val("").focus();
            }
            var val = source.val().trim();
            if (val.indexOf('.') > -1) {
                val = val.replace('.', "");
            } else if (val != '') {
                val += "00";
            }
            target.val(val);
        });
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">	
		<form id="form" method="post">		
				<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_BALANCE_ID%></th>	
					<td>
						<input  name="balanceId" type="text" class="easyui-validatebox span2" data-options="required:true"/>
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_AMOUNT%></th>	
					<td>
						<input  name="amountStr" type="text" class="easyui-validatebox span2 money_input" data-options="required:true"/>
						<input type="hidden" name="amount">
					</td>							
				</tr>	
				<tr>	
					<th><%=TmbWithdrawLog.ALIAS_REF_TYPE%></th>	
					<td>
						<input  name="refType" type="text" class="easyui-validatebox span2" data-options="required:true" value="BT101"/>
					</td>							
					<th><%=TmbWithdrawLog.ALIAS_APPLY_LOGIN_ID%>(shopAccountId) </th>
					<td>
						<input class="span2" name="applyLoginId" type="number" data-options="required:true"/>
					</td>							
				</tr>	
				<tr>	
					<th>申请人名称</th>
					<td colspan="4">
						<input class="span2" name="receiver" type="text"/>
					</td>
				</tr>	
				<tr>
					<th>提现微信账户</th>
					<td colspan="4">
						<input class="span6" name="receiverAccount" type="text" data-options="required:true"/>
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>