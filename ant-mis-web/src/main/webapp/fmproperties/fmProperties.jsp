<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TfmProperties" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<!DOCTYPE html>

<c:if test="${fn:contains(sessionInfo.resourceList, '/fmPropertiesController/editPage')}">
    <script type="text/javascript">
        $.canEdit = true;
    </script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fmPropertiesController/delete')}">
    <script type="text/javascript">
        $.canDelete = true;
    </script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fmPropertiesController/view')}">
    <script type="text/javascript">
        $.canView = true;
    </script>
</c:if>
<script type="text/javascript">
    var dataGrid;
    $(function () {
        dataGrid = $('#dataGrid').datagrid({
            fit: true,
            fitColumns: true,
            border: false,
            pagination: true,
            idField: 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            sortName: 'seq',
            sortOrder: 'asc',
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
                field: 'addtime',
                title: '<%=TfmProperties.ALIAS_ADDTIME%>',
                width: 50,
                hidden: true
            }, {
                field: 'updatetime',
                title: '<%=TfmProperties.ALIAS_UPDATETIME%>',
                width: 50,
                hidden: true
            }, {
                field: 'name',
                title: '<%=TfmProperties.ALIAS_NAME%>',
                width: 50
            }, {
                field: 'goodNameName',
                title: '<%=TfmProperties.ALIAS_GOOD_NAME%>',
                width: 50
            }, {
                field: 'description',
                title: '<%=TfmProperties.ALIAS_DESCRIPTION%>',
                width: 50,
                hidden: true
            }, {
                field: 'fieldTypeName',
                title: '<%=TfmProperties.ALIAS_FIELD_TYPE%>',
                width: 50
            }, {
                field: 'requireName',
                title: '<%=TfmProperties.ALIAS_REQUIRE%>',
                width: 50
            }, {
                field: 'seq',
                title: '排序',
                width: 30
            }, {
                field: 'groupId',
                title: '组',
                width: 30
            }, {
                field: 'defaultValue',
                title: '<%=TfmProperties.ALIAS_DEFAULT_VALUE%>',
                width: 50
            }, {
                field: 'action',
                title: '操作',
                width: 100,
                formatter: function (value, row, index) {
                    var str = '';
                    if ($.canEdit) {
                        str += $.formatString('<input type="button" class="button gray" onclick="editFun(\'{0}\');" value="修改"/>', row.id);
                    }
                    str += '&nbsp;';
                    if ($.canDelete) {
                        str += $.formatString('<input type="button" class="button gray" onclick="deleteFun(\'{0}\');" value="删除"/>', row.id);
                    }
                    str += '&nbsp;';
                    if ($.canView) {
                        str += $.formatString('<input type="button" class="button gray" onclick="viewFun(\'{0}\');" value="查看"/>', row.id);
                    }
                    return str;
                }
            }]],
            toolbar: '#toolbar',
            onLoadSuccess: function () {
                $('#searchForm table').show();
                parent.$.messager.progress('close');

                $(this).datagrid('tooltip');
            },
            onSelect:function(index,row){
                searchFunOption(row.id);
            }
        });

        searchFun(itemId);
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
                $.post('${pageContext.request.contextPath}/fmPropertiesController/delete', {
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
            title: '编辑属性',
            width: 780,
            height: 350,
            href: '${pageContext.request.contextPath}/fmPropertiesController/editPage?id=' + id,
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
            title: '查看属性',
            width: 780,
            height: 350,
            href: '${pageContext.request.contextPath}/fmPropertiesController/view?id=' + id
        });
    }

    function addFun() {
        var goodName = $("#goodName").val();
        if(!goodName)return;
        parent.$.modalDialog({
            title: '添加属性',
            width: 780,
            height: 350,
            href: '${pageContext.request.contextPath}/fmPropertiesController/addPage?goodName='+goodName,
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
            url: '${pageContext.request.contextPath}/fmPropertiesController/download',
            onSubmit: function (param) {
                $.extend(param, $.serializeObject($('#searchForm')));
                param.downloadFields = columsStr;
                param.page = options.pageNumber;
                param.rows = options.pageSize;

            }
        });
    }
    function searchFun(goodName) {
        var opts = dataGrid.datagrid("options");
        opts.url = '${pageContext.request.contextPath}/fmPropertiesController/dataGrid';
        $("#goodName").val(goodName);
        cleanFunOption();
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
    }
</script>

<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'center',border:false">
        <form id="searchForm" style="display: none">
            <input type="hidden" name="goodName" id = "goodName">
        </form>
        <table id="dataGrid"></table>
    </div>
</div>
<div id="toolbar" style="display: none;">
    <c:if test="${fn:contains(sessionInfo.resourceList, '/fmPropertiesController/addPage')}">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'common_add'">添加</a>
    </c:if>
</div>
