<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbStockOut" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
    var dataGrid;
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbStockOutController/add',
            onSubmit: function () {
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
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
                parent.$.messager.progress('close');
            }
        });

        dataGrid = $('#dataGrid').datagrid({
            fit: true,
            fitColumns: true,
            border: false,
            pagination: false,
            idField: 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            sortName: 'id',
            sortOrder: 'desc',
            checkOnSelect: false,
            selectOnCheck: false,
            nowrap: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            columns : [ [ {
                field : 'itemId',
                title : '商品id',
                width : 30,
                hidden: true
            },{
                field : 'itemCode',
                title : '商品编码',
                width : 50
            },  {
                field : 'itemName',
                title : '商品名称',
                width : 80
            }, {
                field : 'quantity',
                title : '数量',
                width : 30
            }] ]
        })
    })

    function getItem(){
        var rows = dataGrid.datagrid('getRows');
        $('#itemDataGrid').val(JSON.stringify(rows));
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<form id="form" method="post">
        <input type="hidden" name="loginId" value="${sessionInfo.id}">
		<input type="hidden" name="deliverOrderIds" value="${deliverOrderIds}">
		<input type="hidden" name="stockOutType" value="OT06">
        <input type="hidden" name="dataGrid" id="itemDataGrid">
        <table class="table table-hover table-condensed">
            <tr>
                <th style="width: 50px;"><%=TmbStockOut.ALIAS_STOCK_OUT_PEOPLE_ID%></th>
                <td>
                    <jb:selectSql dataType="SQ010" name="stockOutPeopleId" required="true"></jb:selectSql>
                </td>

                <th style="width: 50px;"><%=TmbStockOut.ALIAS_STOCK_OUT_TIME%></th>
                <td>
                    <input  name="stockOutTime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbStockOut.FORMAT_STOCK_OUT_TIME%>'})"  maxlength="0" class="easyui-validatebox span2" data-options="required:true"  />
                </td>
            </tr>
            <tr>
                <th style="width: 50px;"><%=TmbStockOut.ALIAS_WAREHOUSE_ID%>
                </th>
                <td colspan="3">
                    <jb:selectSql dataType="SQ005" name="warehouseId" required="true"></jb:selectSql>
                </td>
            </tr>
            <tr>
                <th style="width: 50px;"><%=TmbStockOut.ALIAS_REMARK%></th>
                <td colspan="3"><textarea style="width: 90%" cols="30" rows="3" name="remark" class="easyui-validatebox span2" data-options="required:true"></textarea></td>
                </td>
            </tr>
            <tr>
                <th style="width: 50px;">运单ID</th>
                <td colspan="3">${deliverOrderIds}</td>
                </td>
            </tr>
        </table>
        <div style="overflow: auto;height: 230px">
            <table id="dataGrid" style="height:auto"></table>
        </div>
	</form>
</div>