<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierContractClause" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbSupplierContractClauseController/add',
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
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">	
		<form id="form" method="post">		
				<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbSupplierContractClause.ALIAS_SUPPLIER_CONTRACT_ID%>
					</th>
					<td>
						<input class="span2" name="supplierContractId" type="text" readonly value="${param.supplierContractId}"/>
					</td>
					<th><%=TmbSupplierContractClause.ALIAS_CLAUSE_CODE%>
					</th>
					<td>
						<jb:select dataType="CC" name="clauseCode"></jb:select>
					</td>
				</tr>
				<tr>
					<th><%=TmbSupplierContractClause.ALIAS_VALUE%>
					</th>
					<td colspan="4">
						<input class="span2" name="value" type="text"/>
					</td>
				</tr>
				<tr>
					<th><%=TmbSupplierContractClause.ALIAS_REMARK%>
					</th>
					<td colspan="4">
						<textarea name="remark" style="width: 97%" rows="5s" class="easyui-validatebox"> </textarea>
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>