﻿
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<!DOCTYPE html>
<html>
<head>
    <title>MbOrderInvoice管理</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/editPage')}">
        <script type="text/javascript">
            $.canEdit = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/delete')}">
        <script type="text/javascript">
            $.canDelete = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/view')}">
        <script type="text/javascript">
            $.canView = true;
        </script>
    </c:if>

    <script type="text/javascript">
        var dataGrid;
        $(function () {
            dataGrid = $('#dataGrid').datagrid({
                url: '',
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
                columns: [[
                    {field: 'ck', checkbox: true}, {
                        field: 'id',
                        title: '<%=TmbOrder.ALIAS_ID%>',
                        width: 30,
//                        formatter: function (value, row, index) {
//                            return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
//                        }
                    }, {
                        field: 'shopId',
                        title: '<%=TmbOrder.ALIAS_SHOP_ID%>',
                        width: 20
                    }, {
                        field: 'shopName',
                        title: '<%=TmbOrder.ALIAS_SHOP_NAME%>',
                        width: 80
                    }, {
                        field: 'orderPrice',
                        title: '<%=TmbOrder.ALIAS_ORDER_PRICE%>',
                        width: 25,
                        align: 'right',
                        formatter: function (value) {
                            return $.formatMoney(value);
                        }
                    }, {
                        field: 'deliveryPrice',
                        title: '<%=TmbOrder.ALIAS_DELIVERY_PRICE%>',
                        width: 25,
                        align: 'right',
                        formatter: function (value) {
                            return $.formatMoney(value);
                        }
                    }, {
                        field: 'totalPrice',
                        title: '<%=TmbOrder.ALIAS_TOTAL_PRICE%>',
                        width: 25,
                        align: 'right',
                        formatter: function (value) {
                            return $.formatMoney(value);
                        }
                    }, {
                        field: 'orderStatusName',
                        title: '<%=TmbOrder.ALIAS_STATUS%>',
                        width: 30
                    }, {
                        field: 'deliveryStatusName',
                        title: '<%=TmbOrder.ALIAS_DELIVERY_STATUS%>',
                        width: 30
                    }, {
                        field: 'deliveryWayName',
                        title: '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
                        width: 40
                    }, {
                        field: 'addtime',
                        title: '<%=TmbOrder.ALIAS_ADDTIME%>',
                        width: 50
                    }, {
                        field: 'payTime',
                        title: '<%=TmbOrder.ALIAS_PAY_TIME%>',
                        width: 50
                    }
                ]],

                toolbar: '#toolbar',

            });
            $('#searchForm table').show();
            parent.$.messager.progress('close');
            $(this).datagrid('tooltip');

        });

        function deleteFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.messager.confirm('询问', '您是否要删除当前数据？', function (b) {
                if (b) {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '数据处理中，请稍后....'
                    });
                    $.post('${pageContext.request.contextPath}/mbOrderController/delete', {
                        id: id
                    }, function (result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            dataGrid.datagrid('reload');
                        }
                        parent.$.messager.progress('close');
                    }, 'JSON');
                }
            });
        }

        function editFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '编辑数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbOrderController/editPage?id=' + id,
                buttons: [{
                    text: '编辑',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                }]
            });
        }

        function viewFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '查看数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbOrderController/view?id=' + id
            });
        }

        function addFun() {
            parent.$.modalDialog({
                title: '添加数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbOrderController/addPage',
                buttons: [{
                    text: '添加',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                }]
            });
        }
        function downloadTable() {
            var options = dataGrid.datagrid("options");
            var $colums = [];
            $.merge($colums, options.columns);
            $.merge($colums, options.frozenColumns);
            var columsStr = JSON.stringify($colums);
            $('#downloadTable').form('submit', {
                url: '${pageContext.request.contextPath}/mbOrderController/download',
                onSubmit: function (param) {
                    $.extend(param, $.serializeObject($('#searchForm')));
                    param.downloadFields = columsStr;
                    param.page = options.pageNumber;
                    param.rows = options.pageSize;

                }
            });
        }
        function searchFun() {
            var isValid = $('#searchForm').form('validate');
            if (isValid) {
                var options = {};
                options.url = '${pageContext.request.contextPath}/mbOrderController/queryOrderDataGrid';
                options.queryParams = $.serializeObject($('#searchForm'));//queryParams是请求的时候发送的额外参数
                dataGrid.datagrid(options);
            }
            return isValid;
        }


        function cleanFun() {
            $('#searchForm input').val('');
            dataGrid.datagrid('load', {});
        }

        function editOrderInvoice() {
            var rows = $('#dataGrid').datagrid('getChecked');
            $.post('${pageContext.request.contextPath}/mbOrderInvoiceController/queryShopInvoice', {shopId: rows[0].shopId}, function (result) {
                if (result.success) {
                    parent.$.messager.alert('提示', '该门店还没有添加发票模板，请先添加后再批量开票！');
                } else {
                    parent.$.modalDialog({
                        title: '编辑数据',
                        width: 780,
                        height: 500,
                        href: '${pageContext.request.contextPath}/mbOrderInvoiceController/editOrderInvoicePage?shopId=' + rows[0].shopId,
                        buttons: [{
                            text: '开票',
                            handler: function () {
                                parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                                var f = parent.$.modalDialog.handler.find('#form');
                                f.find("input[name= mbOrderInvoiceList]").val(JSON.stringify(rows));
                                f.submit();
                            }
                        }]
                    });
                }
            }, "JSON");
        }


    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
        <form id="searchForm">
            <input type="hidden" name="mbOrderList" id="mbOrderList">
            <table class="table table-hover table-condensed" style="display: none;">
                <tr>
                    <th style="width: 50px;">门店名称</th>
                    <td>
                        <jb:selectGrid  dataType="shopId" name="shopId" required="true"></jb:selectGrid>
                    </td>
                    <th style="width: 50px;">订单时间</th>
                    <td>
                        <input class="easyui-validatebox span2" data-options="required:true" name="orderTimeBegin"
                               placeholder="点击选择时间"
                               onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               readonly="readonly" />
                        <input class="easyui-validatebox span2" data-options="required:true" name="orderTimeEnd"
                               placeholder="点击选择时间"
                               onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               readonly="readonly" />

                    </td>

                </tr>


            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false">
        <table id="dataGrid"></table>
    </div>
</div>
<div id="toolbar" style="display: none;">
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderInvoiceController/editOrderInvoicePage')}">
        <a onclick="editOrderInvoice();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'pencil'">批量开票</a>
    </c:if>
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true"
       onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/download')}">
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true"
           onclick="downloadTable();">导出</a>
        <form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
        </form>
        <iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
    </c:if>
</div>
</body>
</html>