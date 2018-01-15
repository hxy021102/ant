<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>应收款报表管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
    $(function() {
        $('#searchForm table').show();
        parent.$.messager.progress('close');
    });
    $(function () {
        dataGrid = $('#dataGrid').datagrid({
            fit : true,
            fitColumns : true,
            border : false,
            idField : 'id',
            pageList : [ 10, 20, 30, 40, 50 ],
            sortOrder : 'desc',
            sortName:'updatetime',
            checkOnSelect : false,
            selectOnCheck : false,
            nowrap : false,
            striped : true,
            rownumbers : true,
            showFooter : true,
            singleSelect : true,
            columns : [ [ {
                field : 'id',
                title : '账单ID',
                width : 30,
                formatter: function (value, row) {
                    if (value == null)
                        return null;
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
            }, {
                field : 'addtime',
                title : '创建时间',
                width : 50,
            }, {
                field : 'supplierName',
                title : '供应商名称',
                width : 90
            }, {
                    field : 'amount',
                    title : '应收金额',
                    align:"right",
                    width : 20,
                    formatter: function (value) {
                        if (value == null)
                            return "";
                        return $.formatMoney(value);
                    }
                }] ],
            toolbar : '#toolbar',
        });
    });


    function viewFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '查看数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/supplierOrderBillController/view?id=' + id +"&isView="+ 1
        });
    }
    function downloadTable(){
        var options = dataGrid.datagrid("options");
        var $colums = [];
        $.merge($colums, options.columns);
        $.merge($colums, options.frozenColumns);
        var columsStr = JSON.stringify($colums);
        $('#downloadTable').form('submit', {
            url:'${pageContext.request.contextPath}/receivablesController/download',
            onSubmit: function(param){
                $.extend(param, $.serializeObject($('#searchForm')));
                param.downloadFields = columsStr;
                param.page = options.pageNumber;
            }
        });
    }

    function searchFun() {
        var isValid = $('#searchForm').form('validate');
        if (isValid) {
            var options = {};
            options.url = '${pageContext.request.contextPath}/receivablesController/queryUnReceivableBill',
                options.queryParams = $.serializeObject($('#searchForm'));
            dataGrid.datagrid(options);
        }
    }
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
		<form id="searchForm">
			<table class="table table-hover table-condensed" style="display: none;">
				<tr>
					<th style="width: 50px">账单ID:</th>
					<td><input type="text" name="id"  class="span2"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
</div>
<div id="toolbar" style="display: none;">
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/receivablesController/download')}">
		<a onclick="downloadTable();" href="javascript:void(0);" class="easyui-linkbutton"
		   data-options="iconCls:'server_go',plain:true">导出</a>
		<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
		</form>
		<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
	</c:if>
</div>
</body>
</html>