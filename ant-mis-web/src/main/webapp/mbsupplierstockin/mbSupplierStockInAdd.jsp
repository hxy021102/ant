<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbSupplierStockIn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbSupplierStockInController/add',

            onSubmit: function () {
                accept();
                getItem();
                parent.$.messager.progress({
                    title: '提示',
                    text: '数据处理中，请稍后....'
                });
                var isValid = $(this).form('validate');
                if (!isValid) {
                    parent.$.messager.progress('close');
                }
                return isValid;
            },
            success: function (result) {
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
<script type="text/javascript">
    var editIndex = undefined;
    function endEditing(){
        if (editIndex == undefined){return true}
        if ($('#dg').datagrid('validateRow', editIndex)){
            $('#dg').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickCell(index, field){
        if (editIndex != index){
            if (endEditing()){
                $('#dg').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                var ed = $('#dg').datagrid('getEditor', {index:index,field:field});
                if (ed){
                    ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                }
                editIndex = index;
            } else {
                setTimeout(function () {
                    $('#dg').datagrid('selectRow', editIndex);
                },0);
            }
        }
    }
    function onEndEdit(index, row){
//        var ed = $(this).datagrid('getEditor', {
//            index: index,
//            field: 'itemId'
//        });
//        row.itemName = $(ed.target).combobox('getText');
    }
    function append(){
        if (endEditing()){
            $('#dg').datagrid('appendRow',{status:'P'});
            editIndex = $('#dg').datagrid('getRows').length-1;
            $('#dg').datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);
        }
    }
    function removeit(){
        if (editIndex == undefined){return}
        $('#dg').datagrid('cancelEdit', editIndex)
            .datagrid('deleteRow', editIndex);
        editIndex = undefined;
    }
    function accept(){
        if (endEditing()){
            $('#dg').datagrid('acceptChanges');

        }
    }
    function reject(){
        $('#dg').datagrid('rejectChanges');
        editIndex = undefined;
    }
    function getChanges(){
        var rows = $('#dg').datagrid('getChanges');
        alert(rows.length+' rows are changed!');
    }
    //获取datagrid数据集并转换成json字符串
    function  getItem() {
        var rows=$('#dg').datagrid('getRows');
        $('#dataGrid').val(JSON.stringify(rows));
    }


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="height:300px;overflow: hidden;">
        <form id="form" method="post">
            <input type="hidden" name="dataGrid" id="dataGrid"/>
            <table class="table table-hover table-condensed">
                <input type="hidden" value="${mbSupplierOrder.id}" name="supplierOrderId">
                <input type="hidden" value="${sessionInfo.id}" name="loginId">
                <tr>
                    <th><%=TmbSupplierStockIn.ALIAS_SIGN_PEOPLE_ID%>
                    </th>
                    <td>
                        <jb:selectSql dataType="SQ010" name="signPeopleId" required="true"></jb:selectSql>
                    </td>
                    <th><%=TmbSupplierStockIn.ALIAS_SIGN_DATE%>
                    </th>
                    <td>
                        <input class="span2" name="signDate" type="text"
                               onclick="WdatePicker({dateFmt:'<%=TmbSupplierStockIn.FORMAT_SIGN_DATE%>'})"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbSupplierStockIn.ALIAS_WAREHOUSE_ID%>
                    </th>
                    <td>
                        <jb:selectSql dataType="SQ005" name="warehouseId" required="true"
                                      value="${mbSupplierOrder.warehouseId}"></jb:selectSql>
                    </td>
                    <th><%=TmbSupplierStockIn.ALIAS_DRIVER_LOGIN_ID%>
                    </th>
                    <td>
                        <jb:selectSql dataType="SQ007" name="driverLoginId"></jb:selectSql>
                    </td>
                </tr>
                <tr>
                    <th>备注</th>
                    <td colspan="3"><textarea style="width: 90%" cols="30" rows="3" name="remark"></textarea></td>
                </tr>
            </table>

            <div style="overflow: auto;height: 200px">
            <table id="dg" class="easyui-datagrid" title="" style="width:750px;height:auto;"
                   data-options="
				iconCls: '',
				fit: true,
              fitColumns : true,
				singleSelect: true,
				toolbar: '#tb',
				url: '${pageContext.request.contextPath}/mbSupplierOrderItemController/mbSupplierOrderItem?id='+${orderid},
				method: 'get',
				onClickCell: onClickCell,
				onEndEdit: onEndEdit">
                <thead>
                <tr>
                    <th data-options="field:'itemId',width:80">商品ID</th>
                    <th data-options="field:'itemName',width:180,align:'center',
                        formatter: function (value, row) {
                            if (row.price == 0)
                                value += '<font color=\'red\'>【赠送】</font>';
                                return value;
                            }">
                        商品名称
                    </th>
                    <th data-options="field:'quantity',width:80,align:'center',editor:{type:'numberbox',options:{required: true}}">数量</th>
                </tr>

                </thead>
            </table>
            </div>

            <div id="tb" style="height:auto">
                <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>--%>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'pencil_delete',plain:true"
                   onclick="removeit()">删除</a>
                <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>--%>
            </div>
        </form>
    </div>
</div>