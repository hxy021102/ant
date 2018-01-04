<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/supplierInterfaceConfigController/add',
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
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">	
		<form id="form" method="post">
			<input class="span2" name="supplierId" type="hidden" value="${param.supplierId}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th>接口类型</th>
					<td>
						<jb:select dataType="IFT" name="interfaceType" required="true"></jb:select>
					</td>
					<th>appkey</th>
					<td>
						 <input class="easyui-validatebox span2"  required="true" name="appKey" type="text"/>
					</td>
				</tr>
				<tr>
				<th>appSecret</th>
					<td>
						 <input  class="easyui-validatebox span2"  required="true" name="appSecret" type="text"/>
					</td>
					<th>serviceUrl</th>
					<td>
						  <input class="easyui-validatebox span2"  required="true" name="serviceUrl" type="text"/>
					</td>
				</tr>
				<tr>
				<th>版本</th>
					<td>
						  <input class="easyui-validatebox span2"  required="true" name="version" type="text"/>
					</td>
					<th>仓库代码</th>
					<td>
							<input class="easyui-validatebox span2"  required="true" name="warehouseCode" type="text"/>
					</td>
				</tr>
				<tr>
					<th>物流公司</th>
					<td>
						<input class="easyui-validatebox span2"  required="true" name="logisticsName" type="text"/>
					</td>
				<th>物流公司代码</th>
					<td>
							<input class="easyui-validatebox span2"  required="true" name="logisticsCode" type="text"/>
					</td>
				</tr>
				<tr>
					<th>状态映射</th>
					<td>
							<input class="easyui-validatebox span2"  required="true" name="statusMap" type="text"/>
					</td>
				<th>客户ID</th>
					<td>
						<input class="easyui-validatebox span2" required="true" name="customerId" type="text"/>
				    </td>
				</tr>
				<tr>
					<th>运费</th>
					<td>
						<input class="span2 easyui-validatebox money_input" name="priceStr" type="text"  data-options="required:true"/>
						<input type="hidden" name="freight"  >
					</td>
			     	<th>是否上线</th>
					<td>
						<select class="easyui-combobox" name="online" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="1">是</option>
							<option value="0" selected="selected">否</option>
						</select>
					</td>
				</tr>	
				<tr>
					<th>备注</th>
					<td colspan="4">
						<textarea name="remark" style="width: 97%" rows="3"    class="easyui-validatebox"    > </textarea>
					</td>
				</tr>	
			</table>		
		</form>
	</div>
</div>