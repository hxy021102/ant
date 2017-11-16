<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 	parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/shopItemController/add',
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
		$('.money_input').each(function(){
			$(this).val($.formatMoney($(this).val().trim()));
		});
	});
	function computerPrice() {
		var inPrice = parseFloat($("#inPrice").val());
		var freight = parseFloat($("#freight").val());
		var totalPrice=(inPrice*100+freight*100)/100;
		if (!isNaN(inPrice) && !isNaN(freight)) {
			$("#price").val(totalPrice);
		}
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id"/>
			<input type="hidden" name="shopId" value="${shopId}" />

			<table class="table table-hover table-condensed">
				<tr>
					<th>商品
					</th>
					<td>
						<jb:selectGrid dataType="itemId" name="itemId" required="true"></jb:selectGrid>
					</td>
					<th>价格
					</th>
					<td>
						<input class="span2 money_input" id="price" name="totalPrice" readonly type="text"/>
						<input class="span2" name="price" type="hidden"/>
					</td>

				</tr>
				<tr>

					<th>
						采购价
					</th>
					<td>
						<input class="span2 easyui-validatebox money_input" data-options="required:true" id="inPrice" name="inPriceStr" type="text" onblur="computerPrice()"/>
						<input class="span2" name="inPrice" type="hidden"/>

					</td>
					<th>
						运费
					</th>
					<td>
						<input class="span2 easyui-validatebox money_input" data-options="required:true" id="freight" name="freightStr" type="text" onblur="computerPrice()"/>
						<input class="span2" name="freight" type="hidden"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>