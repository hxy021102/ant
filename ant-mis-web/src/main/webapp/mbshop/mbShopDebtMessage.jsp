<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbShop" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<!DOCTYPE html>
<html>
<head>
    <title>MbShop管理</title>
    <title>MbSupplierOrder管理</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var dataGrid;
        $(function () {
            dataGrid = $('#dataGrid').datagrid({
                url:  '',
                fit: true,
                fitColumns: true,
                border: false,
                pagination: true,
                idField: 'id',
                pageSize: 10,
                pageList: [100, 200 ],
                sortName: '',
                sortOrder: 'desc',
                checkOnSelect: false,
                selectOnCheck: false,
                nowrap: false,
                striped: true,
                rownumbers: true,
                singleSelect: true,
                showFooter:true,
                columns: [[
                    {
                        field: 'id',
                        title: '门店ID',
                        width: 20,
                        formatter: function (value, row, index) {
                            if(value)
                            return '<a onclick="viewShop(' + row.id + ')">' + row.id + '</a>';
                        }
                    }, {
                        field: 'name',
                        title: '<%=TmbShop.ALIAS_NAME%>',
                        width: 50
                    }, {
                        field: 'regionPath',
                        title: '<%=TmbShop.ALIAS_REGION_ID%>',
                        width: 70
                    }, {
                        field: 'address',
                        title: '<%=TmbShop.ALIAS_ADDRESS%>',
                        width: 100
                    }, {
                        field: 'contactPhone',
                        title: '<%=TmbShop.ALIAS_CONTACT_PHONE%>',
                        width: 50
                    }, {
                        field: 'contactPeople',
                        title: '<%=TmbShop.ALIAS_CONTACT_PEOPLE%>',
                        width: 40
                    }, {
                        field: 'shopTypeName',
                        title: '<%=TmbShop.ALIAS_SHOP_TYPE%>',
                        width: 30
                    }, {
                        field: 'balanceAmount',
                        title: '余额',
                        width: 30,
                        align: 'right',
                        formatter: function (value, row) {
                            if(row.balanceAmount == undefined)return "";
                            if(row.id==undefined)return $.formatMoney(row.balanceAmount);
                            return '<a onclick="viewBalance(' + row.id + ')">' + $.formatMoney(row.balanceAmount) + '</a>';
                        }
                    }, {
                        field: 'debt',
                        title: '欠款',
                        width: 30,
                        align: 'right',
                        formatter: function (value) {
                            return $.formatMoney(-value);
                        }
                    }, {
                        field: 'totalDebt',
                        title: '总欠款',
                        width: 30,
                        align: 'right',
                        formatter: function (value) {
                            return $.formatMoney(value);
                        }
                    }, {
                        field: 'cashBalanceAmount',
                        title: '桶余额',
                        width: 40,
                        align: 'right',
                        formatter: function (value, row) {
                            if(row.cashBalanceAmount == undefined)return "";
                            return '<a onclick="viewCashBalance(' + row.cashBalanceId + ',' + row.id + ')">' + $.formatMoney(row.cashBalanceAmount) + '</a>';
                        }
                    }, {
                        field: 'auditStatusName',
                        title: '审核状态',
                        width: 30
                    }]],
                onLoadSuccess : function() {
                    parent.$.messager.progress('close');
                }
            });
        });
        function loadTongDebt() {
            return $('#tongDataGrid').datagrid({
                url:  '',
                fit: true,
                fitColumns: true,
                border: false,
                pagination: true,
                idField: 'id',
                pageSize: 10,
                pageList: [100, 200 ],
                sortName: '',
                sortOrder: 'desc',
                checkOnSelect: false,
                selectOnCheck: false,
                nowrap: false,
                striped: true,
                rownumbers: true,
                singleSelect: true,
                showFooter:true,
                columns: [[
                    {
                        field: 'id',
                        title: '门店ID',
                        width: 20,
                        formatter: function (value, row, index) {
                            if(value)
                            return '<a onclick="viewShop(' + row.id + ')">' + row.id + '</a>';
                        }
                    }, {
                        field: 'name',
                        title: '<%=TmbShop.ALIAS_NAME%>',
                        width: 50
                    }, {
                        field: 'regionPath',
                        title: '<%=TmbShop.ALIAS_REGION_ID%>',
                        width: 70
                    }, {
                        field: 'address',
                        title: '<%=TmbShop.ALIAS_ADDRESS%>',
                        width: 100
                    }, {
                        field: 'contactPhone',
                        title: '<%=TmbShop.ALIAS_CONTACT_PHONE%>',
                        width: 50
                    }, {
                        field: 'contactPeople',
                        title: '<%=TmbShop.ALIAS_CONTACT_PEOPLE%>',
                        width: 40
                    }, {
                        field: 'shopTypeName',
                        title: '<%=TmbShop.ALIAS_SHOP_TYPE%>',
                        width: 30
                    }, {
                        field: 'balanceAmount',
                        title: '余额',
                        width: 30,
                        align: 'right',
                        formatter: function (value, row) {
                            if(row.balanceAmount == undefined)return "";
                            if(row.id == undefined)return $.formatMoney(row.balanceAmount);
                            return '<a onclick="viewBalance(' + row.id + ')">' + $.formatMoney(row.balanceAmount) + '</a>';
                        }
                    }, {
                        field: 'cashBalanceAmount',
                        title: '桶余额',
                        width: 30,
                        align: 'right',
                        formatter: function (value, row) {
                            if(row.cashBalanceAmount == undefined)return "";
                            if(row.id == undefined)return $.formatMoney(row.cashBalanceAmount);
                            return '<a onclick="viewCashBalance(' + row.cashBalanceId + ',' + row.id + ')">' + $.formatMoney(row.cashBalanceAmount) + '</a>';
                        }
                    }, {
                        field: 'auditStatusName',
                        title: '审核状态',
                        width: 30
                    }]],
                onLoadSuccess : function() {
                    parent.$.messager.progress('close');
                }
            });
        }

        function viewBalance(id) {
            var href = '${pageContext.request.contextPath}/mbUserController/viewBalance?shopId=' + id;
            parent.$("#index_tabs").tabs('add', {
                title: '余额-' + id,
                content: '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>',
                closable: true
            });
        }



        function viewCashBalance(balanceId,shopId) {
            var href = '${pageContext.request.contextPath}/mbBalanceController/viewCash?id=' + balanceId+"&shopId="+shopId;
            parent.$("#index_tabs").tabs('add', {
                title: '桶余额-' + shopId,
                content: '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>',
                closable: true
            });
        }




        function viewShop(id) {
            var href = '${pageContext.request.contextPath}/mbShopController/view?id=' + id;
            parent.$("#index_tabs").tabs('add', {
                title: '门店详情-' + id,
                content: '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>',
                closable: true
            });
        }

        var gridMap = {};
        $(function() {
            gridMap = {
                handle: function (obj, clallback) {
                    if (obj.grid == null) {
                        obj.grid = clallback();
                    } else {
                        var options = {};
                        options.url = obj.gridUrl;
                        options.queryParams = $.serializeObject($('#searchForm'));
                        obj.grid.datagrid(options);
                    }
                }, 1: {
                    invoke: function () {
                        gridMap.handle(this);
                    }, grid: loadTongDebt(),
                    gridUrl: '${pageContext.request.contextPath}/mbShopController/dataGridShopBarrel'
                }, 0: {
                    invoke: function () {
                        gridMap.handle(this);
                    }, grid: dataGrid,
                    gridUrl: '${pageContext.request.contextPath}/mbShopController/dataGridShopArrears'
                }
            };
            $('#shop_view_tabs').tabs({
                onSelect: function (title, index) {
                    gridMap[index].invoke();
                }
            });
        });

        function reloadDataTable(){
            var tab = $('#shop_view_tabs').tabs('getSelected');
            var index = $('#shop_view_tabs').tabs('getTabIndex',tab);
            gridMap[index].invoke();
        }

    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
        <form id="searchForm">
            <table class="table table-hover table-condensed">
                <tr>
                    <th><%=TmbShop.ALIAS_SHOP_TYPE%>
                    </th>
                    <td>
                        <jb:select dataType="ST" name="shopType" mustSelect="true" onselect="reloadDataTable"></jb:select>
                    </td>
                </tr>

            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false">
        <div id="shop_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
            <div title="余额欠款">
                <table id="dataGrid"></table>
            </div>
            <div title="桶账欠款">
                <table id="tongDataGrid"></table>
            </div>
        </div>
    </div>
</div>
</body>
</html>