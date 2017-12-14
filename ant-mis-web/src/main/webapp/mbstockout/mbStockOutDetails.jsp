<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
	<title>DeliverOrder管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	 <script type="text/javascript">
		 var dataGridmbStockOutOrder;
         function loadDataGridmbStockOutOrder() {
           return dataGridmbStockOutOrder = $('#dataGridmbStockOutOrder').datagrid({
			     url : '${pageContext.request.contextPath}/mbStockOutOrderController/dataGrid?mbStockOutId='+${mbStockOut.id},
                 fit : true,
                 fitColumns : true,
                 border : false,
                 pagination : false,
                 idField : 'id',
                 pageSize : 10,
                 pageList : [ 10, 20, 30, 40, 50 ],
                 sortName : 'id',
                 sortOrder : 'desc',
                 checkOnSelect : false,
                 selectOnCheck : false,
                 nowrap : false,
                 striped : true,
                 rownumbers : true,
                 singleSelect : true,
                 columns : [ [ {
                     field : 'id',
                     title : '编号',
                     width : 150,
                     hidden : true
                 }, {
                     field : 'addtime',
                     title : '创建时间',
                     width : 70
                 }, {
                     field : 'updatetime',
                     title : '<%=TmbStockOutOrder.ALIAS_UPDATETIME%>',
                     width : 70
                 }, {
                     field : 'mbStockOutId',
                     title : '<%=TmbStockOutOrder.ALIAS_MB_STOCK_OUT_ID%>',
                     width : 30
                 }, {
                     field : 'deliverOrderId',
                     title : '<%=TmbStockOutOrder.ALIAS_DELIVER_ORDER_ID%>',
                     width : 30,
                     formatter: function (value, row) {
                         return '<a onclick="viewFun(' + row.deliverOrderId + ')">' + row.deliverOrderId + '</a>';
                     }
                 }] ],
                 onLoadSuccess : function() {
                     parent.$.messager.progress('close');
                     $(this).datagrid('tooltip');
                 }
             });
		 }

         function loadDeliverItemDataGrid() {
             return $('#deliverItemDataGrid').datagrid({
				 url : '${pageContext.request.contextPath}/deliverOrderShopItemController/dataGrid?deliverOrderIds='+'${mbStockOut.deliverOrderIds}',
                 fit : true,
                 fitColumns : true,
                 border : false,
                 pagination : false,
                 idField : 'id',
                 pageSize : 10,
                 pageList : [ 10, 20, 30, 40, 50 ],
                 sortOrder : 'desc',
                 checkOnSelect : false,
                 selectOnCheck : false,
                 nowrap : false,
                 striped : true,
                 rownumbers : true,
                 singleSelect : true,
                 columns : [ [ {
                     field : 'itemCode',
                     title : '商品编码',
                     width : 50,
                 },  {
                     field : 'itemName',
                     title : '商品名称',
                     width : 80
                 },   {
                     field : 'quantity',
                     title : '数量',
                     width : 30
                 }] ],
                 onLoadSuccess : function() {
                     parent.$.messager.progress('close');
                     $(this).datagrid('tooltip');
                 }
             });
         }



         var gridMap = {};
         $(function() {
             gridMap = {
                 handle:function(obj,clallback){
                     if (obj.grid == null) {
                         obj.grid = clallback();
                     } else {
                         obj.grid.datagrid('reload');
                     }
                 },0: {
                     invoke: function () {
                         gridMap.handle(this,loadDataGridmbStockOutOrder);
                     }, grid: null
                 }, 1: {
                     invoke: function () {
                         gridMap.handle(this,loadDeliverItemDataGrid);
                     }, grid: null
                 },
             };
             $('#stockout_view_tabs').tabs({
                 onSelect: function (title, index) {
                     gridMap[index].invoke();
                 }
             });
             gridMap[0].invoke();

         });

         function viewFun(id) {
             var href = '${pageContext.request.contextPath}/deliverOrderController/view?id=' + id;
             parent.$("#index_tabs").tabs('add', {
                 title : '运单详情-' + id,
                 content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
                 closable : true
             });
         }

	 </script>
</head>

<body>
<div class="easyui-layout" data-options="fit : true,border:false">
	<div data-options="region:'north',title:'基本信息',border:false" style="height: 146px; overflow: hidden;">
		<table class="table">
			<tr>
				<th><%=TmbStockOut.ALIAS_STOCK_OUT_PEOPLE_ID%></th>
				<td>
					${mbStockOut.stockOutPeopleName}
				</td>
				<th><%=TmbStockOut.ALIAS_LOGIN_ID%></th>
				<td>
					${mbStockOut.loginName}
				</td>
				<th><%=TmbStockOut.ALIAS_WAREHOUSE_ID%>
				</th>
				<td >
					${mbStockOut.warehouseName}
				</td>
				<th><%=TmbStockOut.ALIAS_STOCK_OUT_TIME%></th>
				<td>
					${mbStockOut.stockOutTime}
				</td>
			</tr>
			<tr>
				<th><%=TmbStockOut.ALIAS_STOCK_OUT_TYPE%></th>
				<td colspan="7">
					${mbStockOut.stockOutTypeName}
				</td>
			</tr>
			<tr>
				<th><%=TmbStockOut.ALIAS_REMARK%></th>
				<td colspan="7">
					${mbStockOut.remark}
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<div id="stockout_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="出库订单">
				<table id="dataGridmbStockOutOrder"></table>
			</div>
			<div title="门店商品">
				<table id="deliverItemDataGrid"></table>
			</div>
		</div>
	</div>
</div>
</body>
</html>

