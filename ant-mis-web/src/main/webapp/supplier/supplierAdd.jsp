<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/supplierController/add',
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
			    <input type="hidden" value="${supplier.loginId}" name="loginId">
			<table class="table table-hover table-condensed">
				<tr>	
					<th>appkey</th>
					<td>
											<input class="span2" name="appKey" type="text"/>
					</td>							
					<th>appSecret</th>
					<td>
											<input class="span2" name="appSecret" type="text"/>
					</td>							
				</tr>	
				<tr>
					<th>接入方名称</th>
					<td>
											<input class="span2" name="name" type="text"/>
					</td>
					<th>状态</th>
					<td>
						<jb:select dataType="SLS" name="status"></jb:select>
					</td>
				</tr>
				<tr>	
					<th>联系人</th>
					<td>
											<input class="span2" name="contacter" type="text"/>
					</td>							
					<th>联系电话</th>
					<td>
											<input class="span2" name="contactPhone" type="text"/>
					</td>							
				</tr>
				<tr>
					<th>地址</th>
					<td colspan="3">
						<input class="span2" name="address" type="text" style="width:520px"/>
					</td>

				</tr>
			</table>		
		</form>
	</div>
</div>