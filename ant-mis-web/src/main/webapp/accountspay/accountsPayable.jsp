<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>应付款报表管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
    $(function() {
        $('#searchForm table').show();
        parent.$.messager.progress('close');
    });
    $(function () {
        if (!$("#driver").attr("checked") && !$("#shop").attr("checked")) {
            $("#shop").attr("checked", true);
        }
        var column;
        column = {
            field: 'shopName',
            title: '名店名称',
            width: 80
        };
        showDataGrid(column);
        cleanFun();
        $(":radio").click(function () {
            if ($("#driver").attr("checked")) {
                column = {
                    field: 'userName',
                    title: '骑手账号',
                    width: 80
                };
                showDataGrid(column);
                cleanFun();
            } else if ($("#shop").attr("checked")) {
                column = {
                    field: 'shopName',
                    title: '名店名称',
                    width: 80
                };
                showDataGrid(column);
                cleanFun();
            }
        });
    });
    function  showDataGrid(column) {
        dataGrid = $('#dataGrid').datagrid({
            fit : true,
            fitColumns : true,
            border : false,
            idField : 'id',
           // pageSize : 10,
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
            },
                column,
                {
                    field : 'amount',
                    title : '应付金额',
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
    }

    function viewFun(id) {
        if ($("#driver").attr("checked")) {
            var href = '${pageContext.request.contextPath}/driverOrderShopBillController/view?id=' + id;
        } else {
            var href = '${pageContext.request.contextPath}/deliverShopArtificialPayController/viewBill?id=' + id;
        }
        parent.$("#index_tabs").tabs('add', {
            title : '账单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
        });
    }
    function downloadTable(){
        var options =dataGrid.datagrid("options");
        var $colums = [];
        $.merge($colums, options.columns);
        $.merge($colums, options.frozenColumns);
        var columsStr = JSON.stringify($colums);
        var urls;
        if ($("#driver").attr("checked")) {
            urls = '${pageContext.request.contextPath}/accountsPayableController/download?payer=' + "driver";
        } else if ($("#shop").attr("checked")) {
            urls = '${pageContext.request.contextPath}/accountsPayableController/download?payer=' + "shop";
        }
        $('#downloadTable').form('submit', {
            url:urls,
            onSubmit: function(param){
                $.extend(param, $.serializeObject($('#searchForm')));
                param.downloadFields = columsStr;
                param.page = options.pageNumber;
            }
        });
    }

    function searchFun() {
        var options = {};
        var urls;
        if ($("#driver").attr("checked")) {
            urls = '${pageContext.request.contextPath}/accountsPayableController/queryUnShopBillOrUnDriverBill?payer=' + "driver";
        } else if ($("#shop").attr("checked")) {
            urls = '${pageContext.request.contextPath}/accountsPayableController/queryUnShopBillOrUnDriverBill?payer=' + "shop";
        }
        if ($("#id").val() != null) {
            urls += "&id=" + $("#id").val();
        }
        if ($("#name").val() != null) {
            urls += "&name=" + $("#name").val();
        }
        options.url = urls;
        dataGrid.datagrid(options);
    }
    function cleanFun() {
        $('#searchForm input').val('');
        var options = {};
        options.url = '${pageContext.request.contextPath}/accountsPayableController/queryUnShopBillOrUnDriverBill';
        dataGrid.datagrid(options);
    }
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
		<form id="searchForm">
			<table class="table table-hover table-condensed" style="display: none;">
				<tr>
					<th style="width: 80px">应付单位:</th>
					<td>
						<input id="shop" type="radio" name="payer">门店
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="driver" type="radio" name="payer">骑手
					</td>
					<td><input  type="text" id="name" name="name" placeholder="输入门店名称或骑手账号"></td>
					<th style="width: 50px">账单ID:</th>
					<td><input type="text" name="id"  class="span2" id="id"/></td>
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

	<c:if test="${fn:contains(sessionInfo.resourceList, '/accountsPayableController/download')}">
		<a onclick="downloadTable();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true">导出</a>
		<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
		</form>
		<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
	</c:if>
</div>
</body>
</html>