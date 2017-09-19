<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
    $(function() {
        parent.$.messager.progress('close');
        $('#form').form({
            url : '${pageContext.request.contextPath}/mbSupplierOrderController/edit',
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
<script type="text/javascript">{
    var editIndex = undefined;
    var dgItem;
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

    /*加载下拉选择*/
    $(function () {
        $.extend($.fn.datagrid.defaults.editors, {
            combogrid: {
                init: function(container, options){
                    var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
                    input.combogrid(options);
                    return input;
                },
                destroy: function(target){
                    $(target).combogrid('destroy');
                },
                getValue: function(target){
                    return $(target).combogrid('getValue');
                },
                setValue: function(target, value){
                    $(target).combogrid('setValue', value);
                },
                resize: function(target, width){
                    $(target).combogrid('resize',width);
                }
            }
        });


        function getCacheContainer(t){
            var view = $(t).closest('div.datagrid-view');
            var c = view.children('div.datagrid-editor-cache');
            if (!c.length){
                c = $('<div class="datagrid-editor-cache" style="position:absolute;display:none"></div>').appendTo(view);
            }
            return c;
        }
        function getCacheEditor(t, field){
            var c = getCacheContainer(t);
            return c.children('div.datagrid-editor-cache-' + field);
        }
        function setCacheEditor(t, field, editor){
            var c = getCacheContainer(t);
            c.children('div.datagrid-editor-cache-' + field).remove();
            var e = $('<div class="datagrid-editor-cache-' + field + '"></div>').appendTo(c);
            e.append(editor);
        }



        dgItem = $('#dg').datagrid({
            url: '${pageContext.request.contextPath}/mbSupplierOrderItemController/dataGrid?supplierOrderId=${mbSupplierOrder.id}',
            iconCls: 'icon-edit',
            fit: false,
            fitColumns : true,
            singleSelect: true,
            toolbar: '#tbb',
            method: 'get',
            onClickCell: onClickCell,
            onEndEdit: onEndEdit,
            columns: [[
                {
                    field: 'itemId',
                    title: '商品名称',
                    width: 300,
                    align:"center",
                    editor: {
                        type: 'combogrid',
                        options: {
                            panelWidth: 300,
                            idField: 'id',
                            textField: 'itemName',
                            method: 'post',
                            mode: 'remote',
                            url: '${pageContext.request.contextPath}/mbSupplierOrderController/selectQuery?supplierContractId='+${mbSupplierOrder.supplierContractId},
                            required: true,
                            columns: [[
                                {field: 'id', title: 'ID', width: 30},
                                {field: 'itemName', title: '名称', width: 270},
                            ]]
                        }
                    },
                    formatter:function(value,row){
                        return row.itemName;
                    }
                }, {
                    field: 'price',
                    title: '单价',
                    width: 230,
                    align:"center",
                    formatter:function(value){
                        if(value == undefined)return "";
                        return $.formatMoney(value);
                    }
                }, {
                    field: 'quantity',
                    title: '数量',
                    width: 230,
                    align:"center",
                    editor:{type:'numberbox',options:{required: true}}
                },
            ]],
            onLoadSuccess: function () {
                parent.$.messager.progress('close');
            }
        });
    });

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
                setTimeout(function(){
                    $('#dg').datagrid('selectRow', editIndex);
                },0);
            }
        }
    }
    function onEndEdit(index, row) {
        var ed = $(this).datagrid('getEditor', {
            index: index,
            field: 'itemId'
        });
        row.itemName = $(ed.target).combogrid('getText');
        var g = $(ed.target).combogrid('grid');
        row.price = g.datagrid('getSelected').price;
    }
    function append(){
        if (endEditing()){
            $('#dg').datagrid('appendRow',{});
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
    function saveUpdate(){
        if (endEditing()) {
            var rows = getChanges();
            var data = $.serializeObject($('#form'));
            data.mbSupplierOrderItemList=rows;
            $.ajax({
                url:  '${pageContext.request.contextPath}/mbSupplierOrderController/edit',
                data: JSON.stringify(data),
                dataType: "json",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                beforeSend: function (request) {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '数据处理中，请稍后....'
                    });
                },
                success: function (data) {
                    parent.$.messager.progress('close');
                    if(data.success) {
                        $('#dg').datagrid('acceptChanges');
                        parent.$.modalDialog.handler.dialog('close');
                      //  parent.location.reload();
                        parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    }else{
                        parent.$.messager.alert('错误', data.msg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    parent.$.messager.progress('close');
                }
            });

        }
    }

    function reject(){
        $('#dg').datagrid('rejectChanges');
        editIndex = undefined;
    }
    function getChanges(){
        var rows=$('#dg').datagrid('getRows');
        //var rows = $('#dg').datagrid('getChanges');
        return rows;
    }
}
</script>


<div class="easyui-layout" data-options="fit:true,border:false" buttons="#dlg-buttons">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;" buttons="#dlg-buttons">
        <form id="form" method="post" buttons="#dlg-buttons">
            <input type="hidden" name="id" value = "${mbSupplierOrder.id}"/>
            <table class="table table-hover table-condensed">
                <tr>
                    <th><%=TmbSupplierOrder.ALIAS_SUPPLIER_NAME%></th>
                    <td>
                        <input   name="supplierName" type="text" class="easyui-validatebox span2" value="${mbSupplierOrder.supplierName}" readonly="readonly"/>
                        <input   name="supplierid"  class="easyui-validatebox span2" value="${mbSupplierOrder.supplierId}" type="hidden"  />
                    </td>
                    <th><%=TmbSupplierContract.ALIAS_CODE%></th>
                    <td>
                        <input   name="code" type="text" class="easyui-validatebox span2" value="${mbSupplierOrder.code}" readonly="readonly"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbSupplierOrder.ALIAS_PLAN_STOCK_IN_DATE%></th>
                    <td>
                        <input   name="planStockInDate" type="text" class="easyui-validatebox span2"   onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrder.FORMAT_PLAN_STOCK_IN_DATE%>'})"   maxlength="0" value="${mbSupplierOrder.planStockInDate}"/>
                    </td>
                    <th><%=TmbSupplierOrder.ALIAS_WAREHOUSE_NAME%></th>
                    <td>
                        <jb:selectSql dataType="SQ004" name="warehouseId" value="${mbSupplierOrder.warehouseId}" ></jb:selectSql>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbSupplierOrder.ALIAS_REMARK%></th>
                    <td colspan="3">
                        <textarea name="remark" style="width: 90%" rows="4"  class="easyui-validatebox"    >${mbSupplierOrder.remark}</textarea>
                    </td>
                </tr>
            </table>
            <div style="overflow: auto;height: 200px">
            <table id="dg" title="修改订单详细信息" ></table>
            </div>
            <div id="tbb" >
                <a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_add',plain:true" onclick="append()">添加</a>
                <a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_delete',plain:true" onclick="removeit()">删除</a>
                <a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options=" plain:true" onclick="saveUpdate()">保存修改</a>
            </div>
        </form>
    </div>
</div>