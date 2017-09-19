<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbCoupons" %>
<%@ page import="com.mobian.model.TmbItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbCouponsController/add',
            onSubmit: function (extraParams) {
                var rows = getRows();
                if(rows != undefined || rows != null){
                    extraParams.couponsItems = JSON.stringify(rows);
                }
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
    });
    var queryStr = '';
    var rows = undefined ;
    var firstLineAdded = undefined;
    $('#itemListTable').datagrid({
        fit: false,
        fitColumns: true,
        iconCls: '',
        singleSelect: true,
        toolbar: 'itemTableToolbar',
        onClickCell: onClickCell,
        onEndEdit: onEndEdit,
        columns:[[{
            field:'itemName',
            title:'<%=TmbItem.ALIAS_NAME%>',
            width:300,
            editor: {
                type: 'combogrid',
                options: {
                    width: 300,
                    height: 30,
                    panelWidth: 300,
                    required: true,
                    idField: 'id',
                    textField: 'text',
                    method: 'post',
                    mode: 'remote',
                    onChange: function (nValue, oValue) {
                        queryStr = nValue;
                    },
                    url: '${pageContext.request.contextPath}/mbItemController/selectQuery',
                    data: queryStr,
                    columns: [[
                        {
                            field: 'id',
                            title: '<%=TmbItem.ALIAS_ID%>',
                            width: 50,
                            hidden: true,
                        },{
                            field: 'text',
                            title: '<%=TmbItem.ALIAS_NAME%>',
                            width: 300
                        }
                    ]]
                }
            },
                formatter:function (value,row) {
                return row.itemName;
                }
            }, {
                field:'itemCode',
                title:'<%=TmbItem.ALIAS_CODE%>',
                width:200
            }, {
                field:'itemMarketPrice',
                title:'<%=TmbItem.ALIAS_MARKET_PRICE%>',
                width:100,
                align:'right',
                formatter : function (value) {
                    if (value == null) return '--';
                    return $.formatMoney(value);
                }
            }
        ]]
    });
    var newValueStore = '';
    function outputValue(selectValue) {
        //如果选中的商品兑换券则绘制商品兑换券的输入界面
        //如果是兑换券则只能输入一个商品
        var selectValueStore = selectValue ;
        if(selectValue === 'CT001'){
            var delRows = getRows();
            var delIndex = delRows.length - 1;
            while (delIndex > 0){
                $('#itemListTable').datagrid('cancelEdit',delIndex)
                    .datagrid('deleteRow', delIndex);
                delIndex--;
            }
        }
    }
    var itemEditIndex = undefined;
    function endEditing() {
        if(itemEditIndex == undefined){
            return true;
        }
        //datagrid method:validateRow (index), 判定指定的行是否与传入的index值一致,一致return true
        if($('#itemListTable').datagrid('validateRow',itemEditIndex)){
            $('#itemListTable').datagrid('endEdit',itemEditIndex);
            itemEditIndex = undefined;
            return true;
        }else {
            return false;
        }
    }
    function append() {
        if ($('#form').find('input[name=type]').val() != 'CT001') {
            firstLineAdded = undefined;
        }
        if (endEditing() && firstLineAdded == undefined){
            $('#itemListTable').datagrid('appendRow',{status:'P'});
            itemEditIndex = $('#itemListTable').datagrid('getRows').length-1;
            $('#itemListTable').datagrid('selectRow', itemEditIndex)
                .datagrid('beginEdit', itemEditIndex);
            if ($('#form').find('input[name=type]').val() == 'CT001') {
                firstLineAdded = 1;
            }
        }
    }
    function removeit() {
        if (firstLineAdded != undefined && getRows().length <= 2) {
            firstLineAdded = undefined;
        }
        if (itemEditIndex == undefined) {
            return;
        }
        $('#itemListTable').datagrid('cancelEdit',itemEditIndex)
            .datagrid('deleteRow', itemEditIndex);
        itemEditIndex = undefined;
    }
    function getRows(){
        var rows=$('#itemListTable').datagrid('getRows');
        //var rows = $('#dg').datagrid('getChanges');
        return rows;
    }
    function onClickCell(index,field) {
        console.log("index,field,itemEditIndex,endEditing:"+index+','+field+','+itemEditIndex+','+endEditing());
        if (itemEditIndex != index) {
            if (endEditing()) {
                $('#itemListTable').datagrid('selectRow', index)
                    .datagrid('beginEdit', index);
                var ed = $('#itemListTable').datagrid('getEditor', {index: index, field: field});
                if (ed) {
                    ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                }
                itemEditIndex = index;
            }
        } else {
            setTimeout(function () {
                $('#itemListTable').datagrid('selectRow',itemEditIndex );
            },0);
        }
    }

    function onEndEdit(index, row) {
        var ed = $(this).datagrid('getEditor', {index: index, field: 'itemName'});
        row.itemName = $(ed.target).combobox('getText');
        var g = $(ed.target).combogrid('grid').datagrid('getSelected');
        if (g != null) {
            row.itemMarketPrice = g.marketPrice ;
            row.itemCode = g.code;
            row.itemId = g.id;
        }
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
        <form id="form" method="post">
            <input type="hidden" name="id"/>
            <input type="hidden" name="itemList">
            <input type="hidden" name="status" value="NS001"/>
            <table class="table table-hover table-condensed">
                <tr>
                    <th><%=TmbCoupons.ALIAS_NAME%>:
                    </th>
                    <td>
                        <input name="name" type="text" class="easyui-validatebox span2" data-options="required:true"/>
                    </td>
                    <th><%=TmbCoupons.ALIAS_CODE%>:
                    </th>
                    <td>
                        <input name="code" type="text" class="easyui-validatebox span2" data-options="required:true"/>
                    </td>
                    <th><%=TmbCoupons.ALIAS_TYPE%>:
                    </th>
                    <td>
                        <jb:select name="type" dataType="CT" onselect="outputValue" required="true"></jb:select>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbCoupons.ALIAS_PRICE%></th>
                    <td>
                        <input name="priceTemp" type="text" class="easyui-validatebox span2 money_input" data-options="required:true" />元
                        <input name="price" type="hidden" />
                    </td>
                    <th><%=TmbCoupons.ALIAS_QUANTITY_TOTAL%>:</th>
                    <td></td>
                    <th><%=TmbCoupons.ALIAS_DISCOUNT%>:</th>
                    <td></td>
                </tr>
                <tr>
                    <th><%=TmbCoupons.ALIAS_MONEY_THRESHOLD%>:</th>
                    <td></td>
                    <th><%=TmbCoupons.ALIAS_TIME_OPEN%>:</th>
                    <td></td>
                    <th><%=TmbCoupons.ALIAS_TIME_CLOSE%>:</th>
                    <td></td>
                </tr>
                <tr>
                    <th><%=TmbCoupons.ALIAS_DESCRIPTION%></th>
                    <td colspan="6">
                            <textarea name="description" class="span2 " style="width: 95%;"></textarea>
                    </td>
                </tr>
            </table>
            <div id="itemTableToolbar" style="height:auto">
                <a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_add',plain:true" onclick="append()">添加</a>
                <a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_delete',plain:true" onclick="removeit()">删除</a>
            </div>
            <div style="overflow: auto;height: 150px">
                <table id="itemListTable" title="可优惠/兑换商品列表"></table>
            </div>
        </form>

    </div>
</div>