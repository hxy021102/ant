<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/supplierController/edit',
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
		<form id="form" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id" value = "${supplier.id}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th>接入方名称</th>
					<td>
						<input class="span2" name="name" type="text" value="${supplier.name}"/>
					</td>
					<th>状态</th>
					<td>
						<jb:select dataType="SLS" name="status" value="${supplier.status}"></jb:select>
					</td>

				</tr>
				<tr>
					<th>联系人</th>
					<td>
											<input class="span2" name="contacter" type="text" value="${supplier.contacter}"/>
					</td>							
					<th>联系电话</th>
					<td>
											<input class="span2" name="contactPhone" type="text" value="${supplier.contactPhone}"/>
					</td>							
				</tr>
				<tr>
					<th>地址</th>
					<td colspan="3">
						<input class="span2" name="address" type="text" value="${supplier.address}" style="width:610px"/>
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3">
						<textarea style="width: 90%" cols="30" rows="3" name="remark">${supplier.remark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<img src="${supplier.charterUrl}" style="width: 220px;height: 120px"/>
					</td>
				</tr>
				<tr>
					<th style="width: 50px;">营业执照</th>
					<td colspan="4">
						<input type="file" id="iconFile" name="equipIconFile">
					</td>
				</tr>
			</table>				
		</form>
	</div>
</div>