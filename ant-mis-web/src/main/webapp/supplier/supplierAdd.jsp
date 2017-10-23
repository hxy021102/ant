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
        $('.money_input').blur(function () {
            var source = $(this);
            var target = source.next();
            if (!/^([1-9]\d*|0)(\.\d{2})?$/.test(source.val())) {
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
        $('.weight_input').blur(function () {
            var source = $(this);
            var target = source.next();
            if (!/^([1-9]\d*|0)(\.\d{1,3})?$/.test(source.val())) {
                source.val("").focus();
            }
            var val = source.val().trim();
            if (val.indexOf('.') > -1) {
                if(val.substring(val.length-2,val.length).indexOf('.')>-1){
                    val = val.replace('.', "");
                    val +="00";
                }else if(val.substring(val.length-3,val.length).indexOf('.')>-1){
                    val = val.replace('.', "");
                    val +="0";
                }else{
                    val = val.replace('.', "");
                }
            } else if (val != '') {
                val += "000";
            }
            target.val(val);
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">	
		<form id="form" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id"/>
                <input type="hidden" name="loginId" value="${supplier.loginId}">
			<table class="table table-hover table-condensed">
				<tr>
					<th style="width: 60px">接入方名称</th>
					<td>
											<input class="span2" name="name" type="text"/>
					</td>
					<th style="width: 50px">状态</th>
					<td>
						<jb:select dataType="SLS" name="status" required="true"></jb:select>
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
						<input class="span2" name="address" type="text" style="width:610px"/>
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3">
						<textarea style="width: 90%" cols="30" rows="3" name="remark"></textarea>
					</td>
				</tr>
				<tr>
					<th style="width: 50px;">营业执照</th>
					<td colspan="3">
						<input type="file" id="iconFile" name="equipIconFile">
					</td>
				</tr>
			</table>		
		</form>
	</div>
</div>