<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/supplierItemRelationController/edit',
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
				<input type="hidden" name="id" value = "${supplierItemRelation.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	

					<th>商品</th>
					<td colspan="3">
						<jb:selectGrid name="itemId" dataType="itemId" value="${supplierItemRelation.itemId}"></jb:selectGrid>
					</td>							
			</tr>	
				<tr>	
					<th>价格</th>
					<td>
						<input class="span2" name="price" type="text" value="${supplierItemRelation.price}"/>
					</td>							
					<th>采购价</th>
					<td>
						<input class="span2" name="inPrice" type="text" value="${supplierItemRelation.inPrice}"/>
					</td>							
			</tr>	
				<tr>	
					<th>运费</th>
					<td>
						<input class="span2" name="freight" type="text" value="${supplierItemRelation.freight}"/>
					</td>							
					<th>上下架</th>
					<td>
						<select name="online" class="easyui-combobox" data-options="width:140,height:29">
							<c:if test="${supplierItemRelation.online == false}">
								<option value="1">上架</option>
								<option value="0" selected="selected">下架</option>
							</c:if>
							<c:if test="${supplierItemRelation.online == true}">
								<option value="1" selected="selected">上架</option>
								<option value="0">下架</option>
							</c:if>
						</select>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>