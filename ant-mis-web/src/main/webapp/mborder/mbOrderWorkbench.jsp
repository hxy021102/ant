<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbOrder管理</title>
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
	var gridMap = {}, selectDatagrid;
	$(function() {
		gridMap = {
			handle:function(obj,clallback){
				obj.grid = clallback();
				selectDatagrid = obj.grid;

				setTimeout(function(){
					searchFun();
				},100);
			},
			0: {
				invoke: function () {
					gridMap.handle(this,loadDataGrid_05);
				}, grid: null
			}, 1: {
				invoke: function () {
					gridMap.handle(this,loadDataGrid_09_10);
				}, grid: null
			}, 2: {
				invoke: function () {
					gridMap.handle(this,loadDataGrid_12);
				}, grid: null
			}, 3: {
				invoke: function () {
					gridMap.handle(this,loadDataGrid_15);
				}, grid: null
			}, 4: {
				invoke: function () {
					gridMap.handle(this, loadDataGrid_20);
				}, grid: null
			}, 5: {
				invoke: function () {
					gridMap.handle(this, loadDataGrid_30_35);
				}, grid: null
			}, 6: {
				invoke: function () {
					gridMap.handle(this, loadDataGrid_40_NoPay);
				}, grid: null
			}

		};
		$('#order_list_tabs').tabs({
			onSelect: function (title, index) {
				gridMap[index].invoke();

				if(index == 0) $('#status').val('OD05');
				else if(index == 1) $('#status').val('OD09,OD10');
				else if(index == 2) $('#status').val('OD12');
				else if(index == 3) $('#status').val('OD15');
				else if(index == 4) $('#status').val('OD20,OD30');
				else if(index == 5) $('#status').val('OD35');
				else if(index == 6) $('#status').val('OD40');

				$.cookie('workbench_tab_index', index, {expires:7}); // 7天有效期
			}
		});

		var index = $.cookie('workbench_tab_index');
		if(!index) index = 0;
		index = parseInt(index);
		$('#order_list_tabs').tabs('select', index);
		if(index == 0) gridMap[index].invoke();


        var value = $.cookie('warehouse_value');
        if(value!=null){
            $('[comboname=deliveryWarehouseId]').combobox('setValue',value);
        }
        setInterval(function(){ searchFun();}, 60000);//每分钟自查询一次
	});

	// 支付成功-待审核
	function loadDataGrid_05() {
		return $('#dataGrid_05').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 100, 200 ],
			sortName : 'addtime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '<%=TmbOrder.ALIAS_ID%>',
				width : 30,
				formatter : function (value, row, index) {
					return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
				}
			}, {
				field : 'shopId',
				title : '<%=TmbOrder.ALIAS_SHOP_ID%>',
				width : 20
			}, {
				field : 'shopName',
				title : '<%=TmbOrder.ALIAS_SHOP_NAME%>',
				width : 80
			}, {
				field: 'orderPrice',
				title: '<%=TmbOrder.ALIAS_ORDER_PRICE%>',
				width: 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'totalPrice',
				title : '<%=TmbOrder.ALIAS_TOTAL_PRICE%>',
				width : 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'orderStatusName',
				title : '<%=TmbOrder.ALIAS_STATUS%>',
				width : 40
			}, {
				field : 'deliveryStatusName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_STATUS%>',
				width : 30
			}, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 40
			}, {
				field : 'deliveryRequireTime',
				title : '<%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>',
				width : 50
			}, {
				field : 'addtime',
				title : '<%=TmbOrder.ALIAS_ADDTIME%>',
				width : 50,
				sortable:true
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
			}
		});
	}


	// 客服审核
	function loadDataGrid_09_10() {
		return $('#dataGrid_09_10').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 100, 200 ],
			sortName : 'addtime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '<%=TmbOrder.ALIAS_ID%>',
				width : 30,
				formatter : function (value, row, index) {
					return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
				}
			}, {
				field : 'shopId',
				title : '<%=TmbOrder.ALIAS_SHOP_ID%>',
				width : 20
			}, {
				field : 'shopName',
				title : '<%=TmbOrder.ALIAS_SHOP_NAME%>',
				width : 80
			}, {
				field: 'orderPrice',
				title: '<%=TmbOrder.ALIAS_ORDER_PRICE%>',
				width: 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'totalPrice',
				title : '<%=TmbOrder.ALIAS_TOTAL_PRICE%>',
				width : 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'orderStatusName',
				title : '<%=TmbOrder.ALIAS_STATUS%>',
				width : 30
			}, {
				field : 'deliveryStatusName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_STATUS%>',
				width : 30
			}, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 40
			}, {
				field : 'deliveryRequireTime',
				title : '<%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>',
				width : 50
			}, {
				field : 'addtime',
				title : '<%=TmbOrder.ALIAS_ADDTIME%>',
				width : 50,
				sortable:true
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
			}
		});
	}

	// 打单中
	function loadDataGrid_12() {
		return $('#dataGrid_12').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 100, 200 ],
			sortName : 'deliveryRequireTime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '<%=TmbOrder.ALIAS_ID%>',
				width : 30,
				formatter : function (value, row, index) {
					return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
				}
			}, {
				field : 'shopId',
				title : '<%=TmbOrder.ALIAS_SHOP_ID%>',
				width : 20
			}, {
				field : 'shopName',
				title : '<%=TmbOrder.ALIAS_SHOP_NAME%>',
				width : 80
			}, {
				field: 'orderPrice',
				title: '<%=TmbOrder.ALIAS_ORDER_PRICE%>',
				width: 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'totalPrice',
				title : '<%=TmbOrder.ALIAS_TOTAL_PRICE%>',
				width : 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'orderStatusName',
				title : '<%=TmbOrder.ALIAS_STATUS%>',
				width : 30
			}, {
				field : 'deliveryStatusName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_STATUS%>',
				width : 30
			}, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 40
			}, {
				field : 'deliveryRequireTime',
				title : '<%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>',
				width : 50,
				sortable:true
			}, {
				field : 'addtime',
				title : '<%=TmbOrder.ALIAS_ADDTIME%>',
				width : 50
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
			}
		});
	}

	// 仓库捡货
	function loadDataGrid_15() {
		return $('#dataGrid_15').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 100, 200 ],
			sortName : 'deliveryRequireTime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '<%=TmbOrder.ALIAS_ID%>',
				width : 30,
				formatter : function (value, row, index) {
					return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
				}
			}, {
				field : 'shopId',
				title : '<%=TmbOrder.ALIAS_SHOP_ID%>',
				width : 20
			}, {
				field : 'shopName',
				title : '<%=TmbOrder.ALIAS_SHOP_NAME%>',
				width : 80
			}, {
				field: 'orderPrice',
				title: '<%=TmbOrder.ALIAS_ORDER_PRICE%>',
				width: 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'totalPrice',
				title : '<%=TmbOrder.ALIAS_TOTAL_PRICE%>',
				width : 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'orderStatusName',
				title : '<%=TmbOrder.ALIAS_STATUS%>',
				width : 30
			}, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 40
			}, {
				field : 'deliveryDriverName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_DRIVER%>',
				width : 30
			}, {
				field : 'deliveryRequireTime',
				title : '<%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>',
				width : 50,
				sortable:true,
                styler: function (value) {
                    if (DateDiff(value) > 2) {
                        return 'color: red';
                    }
                }
			}, {
				field : 'addtime',
				title : '<%=TmbOrder.ALIAS_ADDTIME%>',
				width : 50
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
			}
		});
	}
	// 已发货
	function loadDataGrid_20() {
		return $('#dataGrid_20').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 100, 200 ],
			sortName : 'deliveryTime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '<%=TmbOrder.ALIAS_ID%>',
				width : 30,
				formatter : function (value, row, index) {
					return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
				}
			}, {
				field : 'shopId',
				title : '<%=TmbOrder.ALIAS_SHOP_ID%>',
				width : 20
			}, {
				field : 'shopName',
				title : '<%=TmbOrder.ALIAS_SHOP_NAME%>',
				width : 80
			}, {
				field: 'orderPrice',
				title: '<%=TmbOrder.ALIAS_ORDER_PRICE%>',
				width: 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'totalPrice',
				title : '<%=TmbOrder.ALIAS_TOTAL_PRICE%>',
				width : 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'orderStatusName',
				title : '<%=TmbOrder.ALIAS_STATUS%>',
				width : 30
			}, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 40
			}, {
				field : 'deliveryDriverName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_DRIVER%>',
				width : 30
			}, {
				field : 'deliveryRequireTime',
				title : '<%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>',
				width : 50
			},{
                field : 'deliveryTime',
                title : '<%=TmbOrder.ALIAS_DELIVERY_TIME%>',
                width : 50,
                sortable: true,
                styler: function(value){
                    if (DateDiff(value) > 2) {
                        return 'color: red';
                    }
				}
            }, {
				field : 'addtime',
				title : '<%=TmbOrder.ALIAS_ADDTIME%>',
				width : 50
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
			}
		});
	}

	// 已签收
	function loadDataGrid_30_35() {
		return $('#dataGrid_30_35').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 100, 200 ],
			sortName : 'deliveryTime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '<%=TmbOrder.ALIAS_ID%>',
				width : 30,
				formatter : function (value, row, index) {
					return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
				}
			}, {
				field : 'shopId',
				title : '<%=TmbOrder.ALIAS_SHOP_ID%>',
				width : 20
			}, {
				field : 'shopName',
				title : '<%=TmbOrder.ALIAS_SHOP_NAME%>',
				width : 80
			}, {
				field: 'orderPrice',
				title: '<%=TmbOrder.ALIAS_ORDER_PRICE%>',
				width: 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'totalPrice',
				title : '<%=TmbOrder.ALIAS_TOTAL_PRICE%>',
				width : 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'orderStatusName',
				title : '<%=TmbOrder.ALIAS_STATUS%>',
				width : 30
			}, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 40
			}, {
				field : 'deliveryDriverName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_DRIVER%>',
				width : 30
			}, {
				field : 'deliveryRequireTime',
				title : '<%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>',
				width : 50
			},{
                field : 'deliveryTime',
                title : '<%=TmbOrder.ALIAS_DELIVERY_TIME%>',
                width : 50,
				sortable:true,
                styler: function(value){
                    if (DateDiff(value) > 2) {
                        return 'color: red';
                    }
                }
            }, {
				field : 'addtime',
				title : '<%=TmbOrder.ALIAS_ADDTIME%>',
				width : 50
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
			}
		});
	}

	// 交易完成，未付款
	function loadDataGrid_40_NoPay() {
		return $('#dataGrid_40_NoPay').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 100, 200 ],
			sortName : 'deliveryTime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '<%=TmbOrder.ALIAS_ID%>',
				width : 30,
				formatter : function (value, row, index) {
					return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
				}
			}, {
				field : 'shopId',
				title : '<%=TmbOrder.ALIAS_SHOP_ID%>',
				width : 20
			}, {
				field : 'shopName',
				title : '<%=TmbOrder.ALIAS_SHOP_NAME%>',
				width : 80
			}, {
				field: 'orderPrice',
				title: '<%=TmbOrder.ALIAS_ORDER_PRICE%>',
				width: 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'totalPrice',
				title : '<%=TmbOrder.ALIAS_TOTAL_PRICE%>',
				width : 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'orderStatusName',
				title : '<%=TmbOrder.ALIAS_STATUS%>',
				width : 30
			}, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 40
			}, {
				field : 'deliveryDriverName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_DRIVER%>',
				width : 30
			}, {
				field : 'deliveryRequireTime',
				title : '<%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>',
				width : 50
			},{
                field : 'deliveryTime',
                title : '<%=TmbOrder.ALIAS_DELIVERY_TIME%>',
                width : 50,
				sortable:true,
                styler: function(value){
                    if (DateDiff(value) > 2) {
                        return 'color: red';
                    }
                }
            }, {
				field : 'addtime',
				title : '<%=TmbOrder.ALIAS_ADDTIME%>',
				width : 50
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
			}
		});
	}
	//计算两个时间差的天数
    function DateDiff(value) {
	    var date=new Date(value);
        var diff = (new Date().getTime() - date.getTime()) / 1000 / 60 / 60 / 24;
        return diff;
    }
	function viewOrder(id) {
		var href = '${pageContext.request.contextPath}/mbOrderController/view?id=' + id;
		parent.$("#index_tabs").tabs('add', {
			title : '订单详情-' + id,
			content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
			closable : true
		});
	}
	function downloadTable(){
		var options = selectDatagrid.datagrid("options");
		var $colums = [];
		$.merge($colums, options.columns);
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
		$('#downloadTable').form('submit', {
			url:'${pageContext.request.contextPath}/mbOrderController/download',
			onSubmit: function(param){
				$.extend(param, $.serializeObject($('#searchForm')));
				param.downloadFields = columsStr;
				param.page = options.pageNumber;
				param.rows = options.pageSize;

				var tab = $('#order_list_tabs').tabs('getSelected');
				var index = $('#order_list_tabs').tabs('getTabIndex',tab);
				if(index == 6) {
					param.payStatus = 'PS01';
				}

			}
		});
	}
	function searchFun() {
		var options = {};
		options.url = '${pageContext.request.contextPath}/mbOrderController/dataGrid';

		var tab = $('#order_list_tabs').tabs('getSelected');
		var index = $('#order_list_tabs').tabs('getTabIndex',tab);
		if(index == 6) {
			options.url += '?payStatus=PS01';
		}

		options.queryParams = $.serializeObject($('#searchForm'));
		selectDatagrid.datagrid(options);

	}
	function cleanFun() {
		$('#searchForm input:not(:hidden)').val('');
		selectDatagrid.datagrid('load', {status:$('#status').val()});
	}

    function changeWarehouse(selectValue){
        $.cookie('warehouse_value', selectValue, {expires:7}); // 7天有效期
    }

</script>
</head>
<body>

<div class="easyui-layout" data-options="fit : true,border:false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 115px; overflow: hidden;">
		<form id="searchForm">
			<table class="table table-hover table-condensed">
				<input type="hidden" name="status" id="status" value="OD05"/>
				<tr>
					<th style="width: 50px;"><%=TmbOrder.ALIAS_ID%>
					</th>
					<td>
						<input type="text" name="id" maxlength="10" class="span2"/>
					</td>
					<th style="width: 50px;"><%=TmbOrder.ALIAS_USER_ID%>
					</th>
					<td>
						<input type="text" name="userId" maxlength="10" class="span2"/>
					</td>
					<th style="width: 50px;"><%=TmbOrder.ALIAS_CONTACT_PHONE%>
					</th>
					<td>
						<input type="text" name="contactPhone" maxlength="32" class="span2"/>
					</td>
				</tr>
				<tr>
					<th style="width: 50px;">门店
					</th>
					<td>
						<jb:selectGrid dataType="shopId" name="shopId"></jb:selectGrid>
					</td>
					<th style="width: 50px;">发货仓库</th>
					<td colspan="3">
						<jb:selectSql name="deliveryWarehouseId" dataType="SQ004" onselect="changeWarehouse"></jb:selectSql>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<div id="order_list_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="支付成功-待审核">
				<table id="dataGrid_05"></table>
			</div>
			<div title="客服审核">
				<table id="dataGrid_09_10"></table>
			</div>
			<div title="打单中">
				<table id="dataGrid_12"></table>
			</div>
			<div title="仓库捡货">
				<table id="dataGrid_15"></table>
			</div>
			<div title="已发货">
				<table id="dataGrid_20"></table>
			</div>
			<div title="已签收">
				<table id="dataGrid_30_35"></table>
			</div>
			<div title="未付款">
				<table id="dataGrid_40_NoPay"></table>
			</div>
		</div>
	</div>
</div>

<div id="toolbar" style="display: none;">
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/download')}">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>
		<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
		</form>
		<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
	</c:if>
</div>
</body>
</html>