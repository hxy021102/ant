<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbItemController/add',
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
			<table class="table table-hover table-condensed">
				<tr>	
					<th style="width: 50px;"><%=TmbItem.ALIAS_CODE%></th>
					<td>
					
						<input  name="code" type="text" class="easyui-validatebox span2" data-options="required:true"/>
					</td>							
					<th style="width: 50px;"><%=TmbItem.ALIAS_NAME%></th>
					<td>
					
						<input  name="name" type="text" class="easyui-validatebox span2" data-options="required:true"/>
					</td>							
				</tr>
				<tr>
					<th style="width: 50px;"><%=TmbItem.ALIAS_ISSHELVES%></th>
					<td>
						<select class="easyui-combobox" name="isShelves" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="true" selected="selected">是</option>
							<option value="false">否</option>
						</select>
					</td>
					<th style="width: 50px;"><%=TmbItem.ALIAS_PURCHASE_PRICE%></th>
					<td>
						<input class="span2 money_input easyui-validatebox" name="purchasePriceStr" type="text" data-options="required:true"/>
						<input type="hidden" name="purchasePrice">
					</td>
				</tr>
				<tr>	
					<th style="width: 50px;"><%=TmbItem.ALIAS_CATEGORY_ID%></th>
					<td>
						<jb:selectSql dataType="SQ001" name="categoryId" required="true"></jb:selectSql>
					</td>
					<th style="width: 50px;"><%=TmbItem.ALIAS_QUANTITY_UNIT%></th>
					<td>
						<input  name="quantityUnitName" type="text" class="easyui-validatebox span2" data-options="required:true"/>
					</td>
				</tr>	
				<tr>	
					<th style="width: 50px;"><%=TmbItem.ALIAS_MARKET_PRICE%></th>
					<td>
						<input class="span2 money_input easyui-validatebox" name="marketPriceStr" type="text" data-options="required:true"/>
						<input type="hidden" name="marketPrice">
					</td>
					<th style="width: 50px;"><%=TmbItem.ALIAS_WEIGHT%></th>
					<td>
						<input class="span2 weight_input easyui-validatebox" name="weightStr" type="text" data-options="required:true"/>
						<input type="hidden" name="weight">
					</td>
				</tr>
				<tr>
					<th style="width: 50px;"><%=TmbItem.ALIAS_QUANTITY%></th>
					<td>
						<input class="span2 easyui-validatebox" name="quantity" type="number" data-options="required:true"/>
					</td>
					<th style="width: 50px;">排序</th>
					<td >
						<input class="span2 easyui-validatebox" name="seq" type="number" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th style="width: 50px;"><%=TmbItem.ALIAS_ISPACK%></th>
					<td>
						<select class="easyui-combobox" name="ispack" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="true">是</option>
							<option value="false" selected="selected">否</option>
						</select>
					</td>
					<th style="width: 50px;"><%=TmbItem.ALIAS_PACK_ID%></th>
					<td>
						<jb:selectSql dataType="SQ006" name="packId"></jb:selectSql>
					</td>
				</tr>
				<tr>
					<th>条形码</th>
					<td>
						<input class="span2 easyui-validatebox" name="barCode" type="text" data-options="required:true"/>
					</td>
					<th>箱规</th>
					<td>
						<input class="span2 easyui-validatebox" name="carton" type="text" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th>规格</th>
					<td colspan="4">
						<input class="span2 easyui-validatebox" name="standard" type="text" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td colspan="4" height="5">
						<img class="img-preview" src=""/>
					</td>
				</tr>
				<tr>
					<th style="width: 50px;">图标</th>
					<td colspan="3">
						<input type="file" id="iconFile" name="equipIconFile">
					</td>
				</tr>

			</table>
		</form>
	</div>
</div>