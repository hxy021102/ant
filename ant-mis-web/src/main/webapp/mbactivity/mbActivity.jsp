<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbActivity" %>
<%@ page import="com.mobian.model.TmbActivityAction" %>
<%@ page import="com.mobian.model.TmbActivityRule" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<!DOCTYPE html>
<html>
<head>
    <title>MbActivity管理</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbActivityController/editPage')}">
        <script type="text/javascript">
            $.canEdit = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbActivityController/delete')}">
        <script type="text/javascript">
            $.canDelete = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbActivityController/view')}">
        <script type="text/javascript">
            $.canView = true;
        </script>
    </c:if>
    <script type="text/javascript">
        var activityDataDrid;
        $(function () {
            activityDataDrid = $('#activityDataDrid').datagrid({
                url: '${pageContext.request.contextPath}/mbActivityController/dataGrid',
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
                nowrap: true,
                striped: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[{
                    field: 'id',
                    title: '编号',
                    width: 150,
                    hidden: true
                }, {
                    field: 'name',
                    title: '<%=TmbActivity.ALIAS_NAME%>',
                    width: 80
                }, {
                    field: 'expiryDateStart',
                    title: '<%=TmbActivity.ALIAS_EXPIRY_DATE_START%>',
                    width: 80
                }, {
                    field: 'expiryDateEnd',
                    title: '<%=TmbActivity.ALIAS_EXPIRY_DATE_END%>',
                    width: 80
                }, {
                    field: 'validName',
                    title: '<%=TmbActivity.ALIAS_VALID%>',
                    width: 40
                }, {
                    field: 'remark',
                    title: '<%=TmbActivity.ALIAS_REMARK%>',
                    width: 50
                }, {
                    field: 'action',
                    title: '操作',
                    width: 40,
                    formatter: function (value, row, index) {
                        var str = '';
                        if ($.canEdit) {
                            str += $.formatString('<img onclick="activityEditFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
                        }
                        str += '&nbsp;';
                        if ($.canDelete) {
                            str += $.formatString('<img onclick="activityDeleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                        }
                        str += '&nbsp;';
                        if ($.canView) {
                            str += $.formatString('<img onclick="activityViewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/map/magnifier.png');
                        }
                        return str;
                    }
                }]],
                toolbar: '#Activitytoolbar',
                onLoadSuccess: function () {
                    $('#searchForm table').show();
                    parent.$.messager.progress('close');
                    $(this).datagrid('tooltip');
                },
                onSelect: function (index, row) {
                    activityDataDrid.selectRow = row;
                    var options = {};
                    options.url = '${pageContext.request.contextPath}/mbActivityRuleController/dataGrid?activityId=' + row.id;
                    ruleDataGrid.datagrid(options);
                    ruleDataGrid.datagrid('clearSelections');
                    actionDataGrid.datagrid('clearSelections');
                    checkOnselect(row);

                }


            });
        });
        var checkId=undefined;//记录每次点击的id值，判断两次值是否一样。
        function checkOnselect(row) {
            if(checkId!=row.id){
                actionDataGrid.datagrid('loadData', {"rows": [], "total": 0});
            }
            checkId=row.id;
        }
        function activityDeleteFun(id) {
            if (id == undefined) {
                var rows = activityDataDrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.messager.confirm('询问', '您是否要删除当前数据？', function (b) {
                if (b) {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '数据处理中，请稍后....'
                    });
                    $.post('${pageContext.request.contextPath}/mbActivityController/delete', {
                        id: id
                    }, function (result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            activityDataDrid.datagrid('reload');
                            ruleDataGrid.datagrid('loadData',{"rows":[],"total":0});
                            actionDataGrid.datagrid('loadData',{"rows":[],"tatal":0});
                        }
                        parent.$.messager.progress('close');
                    }, 'JSON');
                }
            });
        }

        function activityEditFun(id) {
            if (id == undefined) {
                var rows = activityDataDrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '编辑数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbActivityController/editPage?id=' + id,
                buttons: [{
                    text: '编辑',
                    handler: function () {
                        parent.$.modalDialog.openner_activityDataDrid = activityDataDrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                }]
            });
        }

        function activityViewFun(id) {
            if (id == undefined) {
                var rows = activityDataDrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '查看数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbActivityController/view?id=' + id
            });
        }

        function activityAddFun() {
            parent.$.modalDialog({
                title: '添加数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbActivityController/addPage',
                buttons: [{
                    text: '添加',
                    handler: function () {
                        parent.$.modalDialog.openner_activityDataGrid = activityDataDrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                }]
            });
        }
        function activityDownloadTable() {
            var options = dataGrid.datagrid("options");
            var $colums = [];
            $.merge($colums, options.columns);
            $.merge($colums, options.frozenColumns);
            var columsStr = JSON.stringify($colums);
            $('#downloadTable').form('submit', {
                url: '${pageContext.request.contextPath}/mbActivityController/download',
                onSubmit: function (param) {
                    $.extend(param, $.serializeObject($('#searchForm')));
                    param.downloadFields = columsStr;
                    param.page = options.pageNumber;
                    param.rows = options.pageSize;

                }
            });
        }
        function activitySearchFun() {
            activityDataDrid.datagrid('load', $.serializeObject($('#searchForm')));
        }
        function activityCleanFun() {
            $('#searchForm input').val('');
            activityDataDrid.datagrid('load', {});
        }
        var actionDataGrid;
        $(function () {
            actionDataGrid = $('#actionDataGrid').datagrid({
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
                nowrap: true,
                striped: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[{
                    field: 'id',
                    title: '编号',
                    width: 150,
                    hidden: true
                }, {
                    field: 'name',
                    title: '<%=TmbActivityAction.ALIAS_NAME%>',
                    width: 80
                }, {
                    field: 'seq',
                    title: '<%=TmbActivityAction.ALIAS_SEQ%>',
                    width: 30
                }, {
                    field: 'actionTypeName',
                    title: '<%=TmbActivityAction.ALIAS_ACTION_TYPE%>',
                    width: 50
                }, {
                    field: 'parameter1',
                    title: '<%=TmbActivityAction.ALIAS_PARAMETER1%>',
                    width: 50
                }, {
                    field: 'parameter2',
                    title: '<%=TmbActivityAction.ALIAS_PARAMETER2%>',
                    width: 50
                }, {
                    field: 'action',
                    title: '操作',
                    width: 40,
                    formatter: function (value, row, index) {
                        var str = '';
                        if ($.canEdit) {
                            str += $.formatString('<img onclick="actionEditFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
                        }
                        str += '&nbsp;';
                        if ($.canDelete) {
                            str += $.formatString('<img onclick="actionDeleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                        }
                        str += '&nbsp;';
                        if ($.canView) {
                            str += $.formatString('<img onclick="actionViewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/map/magnifier.png');
                        }
                        return str;
                    }
                }]],
                toolbar: '#Actiontoolbar',
                onLoadSuccess: function () {
                    $('#searchForm table').show();
                    parent.$.messager.progress('close');

                    $(this).datagrid('tooltip');
                }
            });
        });
        function actionDeleteFun(id) {
            if (id == undefined) {
                var rows = actionDataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.messager.confirm('询问', '您是否要删除当前数据？', function (b) {
                if (b) {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '数据处理中，请稍后....'
                    });
                    $.post('${pageContext.request.contextPath}/mbActivityActionController/delete', {
                        id: id
                    }, function (result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            actionDataGrid.datagrid('reload');
                        }
                        parent.$.messager.progress('close');
                    }, 'JSON');
                }
            });
        }

        function actionEditFun(id) {
            if (id == undefined) {
                var rows = actionDataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '编辑数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbActivityActionController/editPage?id=' + id,
                buttons: [{
                    text: '编辑',
                    handler: function () {
                        parent.$.modalDialog.openner_actionDataGrid = actionDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                }]
            });
        }

        function actionViewFun(id) {
            if (id == undefined) {
                var rows = actionDataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '查看数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbActivityActionController/view?id=' + id
            });
        }

        function actionAddFun(id) {
            if (id == undefined) {
                var rows = ruleDataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '添加数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbActivityActionController/addPage',
                buttons: [{
                    text: '添加',
                    handler: function () {
                        parent.$.modalDialog.openner_actionDataGrid = actionDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name=activityRuleId]").val(id);
                        f.submit();
                    }
                }]
            });
        }
        function actionDownloadTable() {
            var options = actionDataGrid.datagrid("options");
            var $colums = [];
            $.merge($colums, options.columns);
            $.merge($colums, options.frozenColumns);
            var columsStr = JSON.stringify($colums);
            $('#downloadTable').form('submit', {
                url: '${pageContext.request.contextPath}/mbActivityActionController/download',
                onSubmit: function (param) {
                    $.extend(param, $.serializeObject($('#searchForm')));
                    param.downloadFields = columsStr;
                    param.page = options.pageNumber;
                    param.rows = options.pageSize;

                }
            });
        }
        function actionSearchFun() {
            dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
        }
        function actionCleanFun() {
            $('#searchForm input').val('');
            dataGrid.datagrid('load', {});
        }
        var ruleDataGrid;
        $(function () {
            ruleDataGrid = $('#ruleDataGrid').datagrid({
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
                nowrap: true,
                striped: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[{
                    field: 'id',
                    title: '编号',
                    width: 150,
                    hidden: true
                }, {
                    field: 'name',
                    title: '<%=TmbActivityRule.ALIAS_NAME%>',
                    width: 80
                }, {
                    field: 'seq',
                    title: '<%=TmbActivityRule.ALIAS_SEQ%>',
                    width: 30
                }, {
                    field: 'leftValue',
                    title: '<%=TmbActivityRule.ALIAS_LEFT_VALUE%>',
                    width: 50
                }, {
                    field: 'operator',
                    title: '<%=TmbActivityRule.ALIAS_OPERATOR%>',
                    width: 50
                }, {
                    field: 'rightValue',
                    title: '<%=TmbActivityRule.ALIAS_RIGHT_VALUE%>',
                    width: 50
                }, {
                    field: 'remark',
                    title: '<%=TmbActivityRule.ALIAS_REMARK%>',
                    width: 50
                }, {
                    field: 'action',
                    title: '操作',
                    width: 45,
                    formatter: function (value, row, index) {
                        var str = '';
                        if ($.canEdit) {
                            str += $.formatString('<img onclick="ruleEditFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
                        }
                        str += '&nbsp;';
                        if ($.canDelete) {
                            str += $.formatString('<img onclick="ruleDeleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                        }
                        str += '&nbsp;';
                        if ($.canView) {
                            str += $.formatString('<img onclick="ruleViewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/map/magnifier.png');
                        }
                        return str;
                    }
                }]],
                toolbar: '#Ruletoolbar',
                onLoadSuccess: function () {
                    $('#searchForm table').show();
                    parent.$.messager.progress('close');

                    $(this).datagrid('tooltip');
                },
                onSelect: function (index, row) {
                    ruleDataGrid.selectRow = row;
                    var options = {};
                    options.url = '${pageContext.request.contextPath}/mbActivityActionController/dataGrid?activityRuleId=' + row.id;
                    actionDataGrid.datagrid(options);

                }

            });
        });
        function ruleDeleteFun(id) {
            if (id == undefined) {
                var rows = ruleDataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.messager.confirm('询问', '您是否要删除当前数据？', function (b) {
                if (b) {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '数据处理中，请稍后....'
                    });
                    $.post('${pageContext.request.contextPath}/mbActivityRuleController/delete', {
                        id: id
                    }, function (result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            ruleDataGrid.datagrid('reload');
                            actionDataGrid.datagrid('loadData', {"rows": [], "total": 0});
                        }
                        parent.$.messager.progress('close');
                    }, 'JSON');
                }
            });
        }

        function ruleEditFun(id) {
            if (id == undefined) {
                var rows = ruleDataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '编辑数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbActivityRuleController/editPage?id=' + id,
                buttons: [{
                    text: '编辑',
                    handler: function () {
                        parent.$.modalDialog.openner_ruleDataGrid = ruleDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                }]
            });
        }

        function ruleViewFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '查看数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbActivityRuleController/view?id=' + id
            });
        }
        function ruleAddFun(id) {
            if (id == undefined) {
                var rows = activityDataDrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '添加数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbActivityRuleController/addPage',
                buttons: [{
                    text: '添加',
                    handler: function () {
                        parent.$.modalDialog.openner_ruleDataGrid = ruleDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name=activityId]").val(id);
                        f.submit();
                    }
                }]
            });
        }
        function ruleDownloadTable() {
            var options = dataGrid.datagrid("options");
            var $colums = [];
            $.merge($colums, options.columns);
            $.merge($colums, options.frozenColumns);
            var columsStr = JSON.stringify($colums);
            $('#downloadTable').form('submit', {
                url: '${pageContext.request.contextPath}/mbActivityRuleController/download',
                onSubmit: function (param) {
                    $.extend(param, $.serializeObject($('#searchForm')));
                    param.downloadFields = columsStr;
                    param.page = options.pageNumber;
                    param.rows = options.pageSize;

                }
            });
        }
        function ruleSearchFun() {
            dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
        }
        function ruleCleanFun() {
            $('#searchForm input').val('');
            dataGrid.datagrid('load', {});
        }

        function testFun() {
            $.ajax({
                url: '${pageContext.request.contextPath}/mbOrderController/edit?orderPrice=' + 20 +'&shopId=' + 1332,
                data: {"orderPrice":20,"shopId":1332},
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
                    }else{
                        //data
                        parent.$.messager.alert('错误', data.msg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    parent.$.messager.progress('close');
                }
            });
        }
    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'west',border:false" style="width: 600px; overflow: hidden;">
        <div class="easyui-layout" data-options="fit : true,border : false">
            <div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
                <form id="searchForm">
                    <table class="table table-hover table-condensed" style="display: none;">
                        <tr>
                            <th style="width: 50px"><%=TmbActivity.ALIAS_NAME%>
                            </th>
                            <td>
                                <input type="text" name="name" maxlength="128" class="span2"/>
                            </td>
                        </tr>

                    </table>
                </form>
            </div>
            <div data-options="region:'center',border:false">
                <table id="activityDataDrid"></table>
            </div>
        </div>
    </div>
    <div data-options="region:'center',border:false">
        <div class="easyui-layout" data-options="fit : true,border : false">
            <div data-options="region:'north',border:false" style="height: 160px; overflow: hidden;">
                <table id="ruleDataGrid"></table>
            </div>
            <div data-options="region:'center',border:false">
                <table id="actionDataGrid"></table>
            </div>
        </div>
    </div>
</div>
<div id="Activitytoolbar" style="display: none;">
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbActivityController/addPage')}">
        <a onclick="activityAddFun();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'pencil_add'">添加活动</a>
    </c:if>
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="activitySearchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="activityCleanFun();">清空条件</a>
            <a onclick="testFun();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'pencil_add'">测试功能</a>
</div>
<div id="Actiontoolbar" style="display: none;">
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbActivityActionController/addPage')}">
        <a onclick="actionAddFun();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'pencil_add'">添加行为</a>
    </c:if>
</div>
<div id="Ruletoolbar" style="display: none;">
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbActivityRuleController/addPage')}">
        <a onclick="ruleAddFun();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'pencil_add'">添加规则</a>
    </c:if>
</div>

</body>
</html>