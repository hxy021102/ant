<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierContract" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbSupplierContractController/add',
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
        var file = document.getElementById('file').files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function ( event ) {
                var txt = event.target.result;
                $('.img-preview').attr('src',txt);
            };
        }
        reader.readAsDataURL(file);
    }
    $(document).delegate('#file','change',function () {
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
		<form id="form" method="post">		
				<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbSupplierContract.ALIAS_CODE%></th>	
					<td>
						<input name="code" type="text" class="easyui-validatebox span2" data-options="required:true"/>
					</td>							
					<th><%=TmbSupplierContract.ALIAS_NAME%></th>	
					<td>
						<input name="name" type="text" class="easyui-validatebox span2" data-options="required:true"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TmbSupplierContract.ALIAS_SUPPLIER_NAME%></th>
					<td>
						<jb:selectGrid dataType="supplierId" name="supplierId" required="true"></jb:selectGrid>
					</td>
					<th><%=TmbSupplierContract.ALIAS_VALID%></th>
					<td>
						<select class="easyui-combobox" name="valid" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="true">是</option>
							<option value="false">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><%=TmbSupplierContract.ALIAS_RATE%></th>
					<td>
						<input name="rate" type="number" class="easyui-validatebox span2" data-options="required:true"/>%
					</td>
					<th><%=TmbSupplierContract.ALIAS_PAYMENT_DAYS%></th>
					<td>
						<input name="paymentDays" class="span2" type="number">天
					</td>
				</tr>
				<tr>
					<th><%=TmbSupplierContract.ALIAS_EXPIRY_DATE_START%></th>
					<td>
						<input  name="expiryDateStart" class="easyui-validatebox span2" data-options="required:true" type="text" onclick="WdatePicker({dateFmt:'<%=TmbSupplierContract.FORMAT_EXPIRY_DATE_START%>'})"  maxlength="0" class="" />
					</td>
					<th><%=TmbSupplierContract.ALIAS_EXPIRY_DATE_END%></th>	
					<td>
						<input   name="expiryDateEnd" type="text" class="easyui-validatebox span2" data-options="required:true" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbSupplierContract.FORMAT_EXPIRY_DATE_END%>'})"  maxlength="0" class="" />
					</td>							

				</tr>
				<tr>
					<th><%=TmbSupplierContract.ALIAS_ATTACHMENT%></th>
					<td colspan="3">
						<input type="file" id="file" name="file">
					</td>
				</tr>
				<tr>
					<td colspan="4" height="155">
						<img class="img-preview" src="" width="100%" height="100%"/>
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>