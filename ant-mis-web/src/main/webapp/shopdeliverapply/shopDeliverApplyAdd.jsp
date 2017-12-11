<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/shopDeliverApplyController/add?shopId='+${shopId},
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
					parent.$.modalDialog.handler.dialog('close');
                    parent.$.modalDialog.opener_url.reload();
				} else {
					parent.$.messager.alert('提示', result.msg, 'info');
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
					<th>账号ID：</th>
					<td>
											<jb:selectGrid name="accountId" dataType="accountId"></jb:selectGrid>
					</td>
					<th>配送方式</th>
					<td>
											<jb:select dataType="DAW" name="deliveryWay"></jb:select>	
					</td>							
				</tr>
			</table>		
		</form>
	</div>
</div>