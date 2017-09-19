<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbContract" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbContractController/edit',
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
            var file = document.getElementById('contractFile').files[0];
            if (file) {
                var reader = new FileReader();
                reader.onload = function ( event ) {
                    var txt = event.target.result;
                    $('.img-preview').attr('src',txt);
                };
            }
            reader.readAsDataURL(file);
        }
        $(document).delegate('#contractFile','change',function () {
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
				<input type="hidden" name="id" value = "${mbContract.id}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbContract.ALIAS_CODE%></th>	
					<td>
						<input class="span2" name="code" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbContract.code}"/>					</td>
					<th><%=TmbContract.ALIAS_NAME%></th>	
					<td>
						<input class="span2" name="name" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbContract.name}"/>
					</td>							
			</tr>	
				<tr>
					<th><%=TmbContract.ALIAS_SHOP_NAME%></th>
					<td>
						<jb:selectGrid dataType="shopId" name="shopId" required="true" value="${mbContract.shopId}"></jb:selectGrid>
					<th><%=TmbContract.ALIAS_EXPIRY_DATE_START%></th>
					<td>
					<input class="easyui-validatebox span2" data-options="required:true" name="expiryDateStart" type="text" onclick="WdatePicker({dateFmt:'<%=TmbContract.FORMAT_EXPIRY_DATE_START%>'})"   maxlength="0" value="${mbContract.expiryDateStart}"/>
					</td>
			</tr>	
				<tr>
					<th><%=TmbContract.ALIAS_EXPIRY_DATE_END%></th>
					<td>
						<input class="easyui-validatebox span2" data-options="required:true" name="expiryDateEnd" type="text" onclick="WdatePicker({dateFmt:'<%=TmbContract.FORMAT_EXPIRY_DATE_END%>'})"   maxlength="0" value="${mbContract.expiryDateEnd}"/>
					</td>
					<th><%=TmbContract.ALIAS_VALID%></th>	
					<td>
						<select class="easyui-combobox easyui-validatebox" name="valid" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<c:if test="${mbContract.valid == true}">
								<option value="true" selected="selected">是</option>
								<option value="false">否</option>
							</c:if>
							<c:if test="${mbContract.valid == false}">
								<option value="true">是</option>
								<option value="false" selected="selected">否</option>
							</c:if>
						</select>
					</td>
			</tr>
				<tr>
					<th><%=TmbContract.ALIAS_ATTACHMENT%></th>
					<td colspan="3">
						<input type="file" id="contractFile" name="file">
					</td>
				</tr>
				<tr>
					<td colspan="4" height="155">
						<img class="img-preview" src="${mbContract.attachment}" width="100%" height="100%"/>
					</td>
				</tr>
			</table>				
		</form>
	</div>
</div>