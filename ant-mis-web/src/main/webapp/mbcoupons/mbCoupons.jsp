<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbCoupons" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<!DOCTYPE html>
<html>
<head>
    <title>MbCoupons管理</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbCouponsController/editPage')}">
        <script type="text/javascript">
            $.canEdit = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbCouponsController/delete')}">
        <script type="text/javascript">
            $.canDelete = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbCouponsController/view')}">
        <script type="text/javascript">
            $.canView = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbCouponsController/cancel')}">
        <script type="text/javascript">
            $.canCancel = true;
        </script>
    </c:if>
    <script type="text/javascript">
        var dataGrid;
        $(function () {
            dataGrid = $('#dataGrid').datagrid({
                url: '${pageContext.request.contextPath}/mbCouponsController/dataGrid',
                fit: true,
                fitColumns: true,
                border: false,
                pagination: true,
                idField: 'id',
                pageSize: 10,
                pageList: [10, 20, 30, 40, 50],
                sortName: 'id',
                sortOrder: 'desc',
                checkOnSelect: false,
                selectOnCheck: false,
                nowrap: true,
                striped: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[
                    {
                        field: 'code',
                        title: '<%=TmbCoupons.ALIAS_CODE%>',
                        width: 60
                    }, {
                        field: 'name',
                        title: '<%=TmbCoupons.ALIAS_NAME%>',
                        width: 100
                    }, {
                        field: 'typeName',
                        title: '<%=TmbCoupons.ALIAS_TYPE%>',
                        width: 70
                    }, {
                        field: 'discount',
                        title: '<%=TmbCoupons.ALIAS_DISCOUNT%>',
                        width: 50,
                        formatter: function (value, row, index) {
                            if (value == null) return ;
                            if ('CT001' == row.type) {
                                return $.formatMoney(row.discount);
                            }
                        }
                    }, {
                        field: 'price',
                        title: '<%=TmbCoupons.ALIAS_PRICE%>',
                        width: 50,
                        align:'right',
                        formatter: function (value, row, index) {
                                return $.formatMoney(row.price);
                        }
                    }, {
                        field: 'quantityUsed',
                        title: '<%=TmbCoupons.ALIAS_QUANTITY_USED%>',
                        width: 50
                    }, {
                        field: 'quantityTotal',
                        title: '<%=TmbCoupons.ALIAS_QUANTITY_TOTAL%>',
                        width: 50
                    }, {
                        field: 'timeOpen',
                        title: '<%=TmbCoupons.ALIAS_TIME_OPEN%>',
                        width: 90
                    }, {
                        field: 'timeClose',
                        title: '<%=TmbCoupons.ALIAS_TIME_CLOSE%>',
                        width: 90
                    }, {
                        field: 'statusName',
                        title: '<%=TmbCoupons.ALIAS_STATUS%>',
                        width: 50
                    }, {
                        field: 'action',
                        title: '操作',
                        width: 60,
                        formatter: function (value, row, index) {
                            var str = '';
                            if ($.canEdit) {
                                str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
                            }
                            str += '&nbsp;';
                            if ($.canView) {
                                str += $.formatString('<img onclick="viewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/map/magnifier.png');
                            }
                            str += '&nbsp;';
                            if ($.canDelete) {
                                str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                            }
                            str += '&nbsp;';
                            if ($.canCancel) {
                                str += $.formatString('<img onclick="cancelFun(\'{0}\');" src="{1}" title="取消"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/delete.png');
                            }
                            return str;
                        }
                    }]],
                toolbar: '#toolbar',
                onLoadSuccess: function () {
                    $('#searchForm table').show();
                    parent.$.messager.progress('close');

                    $(this).datagrid('tooltip');
                }
            });
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
                    $.post('${pageContext.request.contextPath}/mbCouponsController/delete', {
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
                href: '${pageContext.request.contextPath}/mbCouponsController/editPage?id=' + id,
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
                href: '${pageContext.request.contextPath}/mbCouponsController/view?id=' + id
            });
        }
        function cancelFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.messager.confirm('询问', '您是否要将当前券票设置为无效？     注意:将本优惠券设置无效后将不能再被门店添加购买,但是门店拥有的券将可以继续使用.', function (b) {
                if (b) {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '数据处理中，请稍后....'
                    });
                    $.post('${pageContext.request.contextPath}/mbCouponsController/edit', {
                        id: id,
                        status: 'NS005'
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

        function addFun() {
            parent.$.modalDialog({
                title: '添加数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbCouponsController/addPage',
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
                url: '${pageContext.request.contextPath}/mbCouponsController/download',
                onSubmit: function (param) {
                    $.extend(param, $.serializeObject($('#searchForm')));
                    param.downloadFields = columsStr;
                    param.page = options.pageNumber;
                    param.rows = options.pageSize;

                }
            });
        }
        function searchFun() {
            dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
        }
        function cleanFun() {
            $('#searchForm input').val('');
            dataGrid.datagrid('load', {});
        }
    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 120px; overflow: hidden;">
        <form id="searchForm">
            <table class="table table-hover table-condensed" style="display: none;">

                <tr>
                    <th style="width: 50px;"><%=TmbCoupons.ALIAS_NAME%>
                    </th>
                    <td>
                        <input type="text" name="name" maxlength="128" class="span2"/>
                    </td>
                    <th style="width: 50px;"><%=TmbCoupons.ALIAS_CODE%>
                    </th>
                    <td>
                        <input type="text" name="code" maxlength="32" class="span2"/>
                    </td>
                    <th style="width: 50px;"><%=TmbCoupons.ALIAS_TYPE%>
                    </th>
                    <td>
                        <jb:select name="type" dataType="CT"></jb:select>
                    </td>
                </tr>
                <tr>
                    <th style="width: 50px;"><%=TmbCoupons.ALIAS_TIME_OPEN%>
                    </th>
                    <td>
                        <input type="text" class="span2"
                               onclick="WdatePicker({dateFmt:'<%=TmbCoupons.FORMAT_TIME_OPEN%>'})" id="timeOpenBegin"
                               name="timeOpenBegin"/>
                        <input type="text" class="span2"
                               onclick="WdatePicker({dateFmt:'<%=TmbCoupons.FORMAT_TIME_OPEN%>'})" id="timeOpenEnd"
                               name="timeOpenEnd"/>
                    </td>
                    <th style="width: 50px;"><%=TmbCoupons.ALIAS_TIME_CLOSE%>
                    </th>
                    <td colspan="3">
                        <input type="text" class="span2"
                               onclick="WdatePicker({dateFmt:'<%=TmbCoupons.FORMAT_TIME_CLOSE%>'})" id="timeCloseBegin"
                               name="timeCloseBegin"/>
                        <input type="text" class="span2"
                               onclick="WdatePicker({dateFmt:'<%=TmbCoupons.FORMAT_TIME_CLOSE%>'})" id="timeCloseEnd"
                               name="timeCloseEnd"/>
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
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbCouponsController/addPage')}">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'pencil_add'">添加</a>
    </c:if>
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true"
       onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton"
                                         data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbCouponsController/download')}">
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true"
           onclick="downloadTable();">导出</a>
        <form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
        </form>
        <iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
    </c:if>
</div>
</body>
</html>