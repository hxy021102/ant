<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbItemCategoryController/edit',
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

		function ProcessFile() {
			var file = document.getElementById('iconFile').files[0];
			if (file) {
				var reader = new FileReader();
				reader.onload = function ( event ) {
					var txt = event.target.result;
					$('.img-preview').attr('src',txt);
				};
			}
			reader.readAsDataURL(file);
		}
		$(document).delegate('#iconFile','change',function () {
			ProcessFile();
		});
		$('.img-preview').each(function(){
			var $this = $(this);
			$this.css('height',$this.parent().attr('height'));
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id" value = "${mbItemCategory.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbItemCategory.ALIAS_CODE%></th>	
					<td>
						<input class="span2" name="code" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbItemCategory.code}"/>
					</td>							
					<th><%=TmbItemCategory.ALIAS_NAME%></th>	
					<td>
						<input class="span2" name="name" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbItemCategory.name}"/>
					</td>
			    </tr>
				<tr>
					<td colspan="4" height="155">
						<img class="img-preview" src="${mbItemCategory.url}"/>
					</td>
				</tr>
				<tr>
					<th>图标</th>
					<td colspan="3">
						<input type="file" id="iconFile" name="equipIconFile">
					</td>
				</tr>
				<tr>
					<th>排序</th>
					<td colspan="3">
						<input class="easyui-validatebox span2" name="seq" type="number" value="${mbItemCategory.seq}" data-options="required:true"/>
					</td>
				</tr>
			</table>				
		</form>
	</div>
</div>