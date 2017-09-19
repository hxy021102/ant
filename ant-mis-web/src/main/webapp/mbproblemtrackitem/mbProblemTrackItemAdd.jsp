<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbProblemTrackItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbProblemTrackItemController/add',
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
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
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
                reader.readAsDataURL(file);
            }

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
				<input type="hidden" name="id"/>
			    <input type="hidden" name="status" />
				<input type="hidden" name="problemOrderId" value="${param.problemOrderId}"/>
			    <input type="hidden" name="lastOwnerId" value="${param.ownerId}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th>指定订单处理人</th>
					<td>
						<jb:selectSql dataType="SQ013" name="ownerId" ></jb:selectSql>
					</td>

				</tr>
				<tr>
					<th>图片</th>
					<td >
						<input type="file" id="iconFile" name="fileName">
					</td>
				</tr>
				<tr>
					<td colspan="4" height="50">
						<img class="img-preview" src=""/>
					</td>
				</tr>
				<tr>
					<th><%=TmbProblemTrackItem.ALIAS_CONTENT%></th>
					<td >
						<textarea name="content" style="width: 98%" rows="5"   class="easyui-validatebox" ></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>