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
		 var deliverDataGrid;
         function loadDeliverDataGrid() {
         return deliverDataGrid=$('#deliverDataGrid').datagrid({
             url : '${pageContext.request.contextPath}/deliverOrderShopController/dataGrid?deliverOrderId='+${deliverOrder.id},
             fit : true,
             fitColumns : true,
             border : false,
             pagination : true,
             idField : 'id',
             pageSize : 10,
             pageList : [ 10, 20, 30, 40, 50 ],
             sortName:'addtime',
             sortOrder : 'desc',
             checkOnSelect : false,
             selectOnCheck : false,
             nowrap : false,
             striped : true,
             rownumbers : true,
             singleSelect : true,
             columns : [ [  {
                 field : 'addtime',
                 title : '分配时间',
                 width : 30,
             },{
                 field : 'shopId',
                 title : '门店ID',
                 width : 30,
             },{
                 field : 'shopName',
                 title : '门店名称',
                 width : 80
             }, {
                 field : 'statusName',
                 title : '状态',
                 width : 50
             },{
                 field : 'amount',
                 title : '总金额',
				 align:"right",
                 width : 50,
                 formatter: function (value) {
                     if (value == null)
                         return "";
                     return $.formatMoney(value);
                 }
             }] ],
             onLoadSuccess : function() {
                 parent.$.messager.progress('close');
                 $(this).datagrid('tooltip');
             }
         });
	 }

         function loadItemDataGrid() {
             return $('#itemDataGrid').datagrid({
                 url : '${pageContext.request.contextPath}/deliverOrderItemController/dataGrid?deliverOrderId='+${deliverOrder.id},
                 fit : true,
                 fitColumns : true,
                 border : false,
                 pagination : true,
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
                 }, {
                     field : 'price',
                     title : '单价',
                     width : 30,
                     formatter: function (value) {
                         if (value == null)
                             return "";
                         return $.formatMoney(value);
                     }
                 }, {
                     field : 'inPrice',
                     title : '成本价',
                     align:"right",
                     width : 30,
                     formatter: function (value) {
                         if (value == null)
                             return "";
                         return $.formatMoney(value);
                     }
                 }, {
                     field : 'freight',
                     title : '运费',
                     align:"right",
                     width : 30,
                     formatter: function (value) {
                         if (value == null)
                             return "";
                         return $.formatMoney(value);
                     }
                 }, {
                     field : 'quantity',
                     title : '数量',
                     width : 30
                 } ] ],
                 onLoadSuccess : function() {
                     parent.$.messager.progress('close');
                     $(this).datagrid('tooltip');
                 }
             });
         }

         function loaddeliverItemDataGrid() {
             return $('#deliverItemDataGrid').datagrid({
                 url : '${pageContext.request.contextPath}/deliverOrderShopItemController/dataGrid?deliverOrderId='+${deliverOrder.id},
                 fit : true,
                 fitColumns : true,
                 border : false,
                 pagination : true,
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
                 }, {
                     field : 'price',
                     title : '单价',
                     width : 30,
                     formatter: function (value) {
                         if (value == null)
                             return "";
                         return $.formatMoney(value);
                     }
                 }, {
                     field : 'inPrice',
                     title : '成本价',
                     align:"right",
                     width : 30,
                     formatter: function (value) {
                         if (value == null)
                             return "";
                         return $.formatMoney(value);
                     }
                 }, {
                     field : 'freight',
                     title : '运费',
                     align:"right",
                     width : 30,
                     formatter: function (value) {
                         if (value == null)
                             return "";
                         return $.formatMoney(value);
                     }
                 }, {
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
         function loadOrderLogDataGrid() {
             return $('#orderLogDataGrid').datagrid({
                 url : '${pageContext.request.contextPath}/deliverOrderLogController/dataGrid?deliverOrderId='+${deliverOrder.id},
                 fit : true,
                 fitColumns : true,
                 border : false,
                 pagination : true,
                 idField : 'id',
                 pageSize : 10,
                 pageList : [ 10, 20, 30, 40, 50 ],
                 sortOrder : 'desc',
                 sortName:'updatetime',
                 checkOnSelect : false,
                 selectOnCheck : false,
                 nowrap : false,
                 striped : true,
                 rownumbers : true,
                 singleSelect : true,
                 columns : [ [ {
                     field : 'addtime',
                     title : '添加时间',
                     width : 50,
                 },  {
                     field : 'logTypeName',
                     title : '日志类型',
                     width : 50
                 }, {
                     field : 'content',
                     title : '内容',
                     width : 130
                 }, {
                     field : 'loginName',
                     title : '操作人',
                     align:"right",
                     width : 30
                 } ] ],
                 onLoadSuccess : function() {
                     parent.$.messager.progress('close');
                     $(this).datagrid('tooltip');
                 }
             });
         }
         var orderShopId;
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
                         gridMap.handle(this,loadDeliverDataGrid);
                     }, grid: null
                 }, 1: {
                     invoke: function () {
                         gridMap.handle(this,loadItemDataGrid);
                     }, grid: null
                 }, 2: {
                     invoke: function () {
                         gridMap.handle(this,loaddeliverItemDataGrid);
                     }, grid: null
                 }, 3: {
                     invoke: function () {
                         gridMap.handle(this,loadOrderLogDataGrid);
                     }, grid: null
                 },
             };
             $('#deliver_view_tabs').tabs({
                 onSelect: function (title, index) {
                     gridMap[index].invoke();
                 }
             });

             gridMap[0].invoke();
             if (isNaN(${deliverOrder.orderShopId})) {
                 orderShopId = 0;
             } else {
                 orderShopId =${deliverOrder.orderShopId};
             }


         });

         //指派运单给门店
         function assignOrderShop() {
             parent.$.modalDialog({
                 title : '指派门店',
                 width : 780,
                 height : 200,
                 href : '${pageContext.request.contextPath}/deliverOrderController/assignOrderShopPage?id=' + ${deliverOrder.id}+'&orderShopId='+orderShopId,
                 buttons : [ {
                     text : '提交',
                     handler : function() {
                         parent.$.modalDialog.opener_url = window.location;
                        // parent.$.modalDialog.openner_dataGrid = deliverDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                         var f = parent.$.modalDialog.handler.find('#form');
                         f.submit();
                     }
                 } ]
             });
         }

	 </script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border:false">
	<div data-options="region:'north',title:'基本信息',border:false" style="height: 270px; overflow: hidden;">
		<table class="table">
			<tr>
				<th>运单ID</th>
				<td>
					${deliverOrder.id}
					<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/assignOrderShopPage') and ( deliverOrder.status=='DOS01' or deliverOrder.status=='DOS15' or deliverOrder.status=='notDriver')}">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="assignOrderShop();">指派</a>
					</c:if>
				</td>
				<th>供应商名称</th>
				<td>
			     	${deliverOrder.supplierName}
				</td>
				<th>订单状态</th>
				<td>
					${deliverOrder.statusName}
				</td>
				<th>结算状态</th>
				<td>
					${deliverOrder.shopPayStatusName}
				</td>
			</tr>
			<tr>
				<th>配送状态</th>
				<td>
					${deliverOrder.deliveryStatusName}
				</td>
				<th>添加时间</th>
				<td>
					<fmt:formatDate value="${deliverOrder.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>修改时间</th>
				<td>
					<fmt:formatDate value="${deliverOrder.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>收货人</th>
				<td >
					${deliverOrder.contactPeople}
				</td>
			</tr>
			<tr>
				<th>联系电话</th>
				<td>
					${deliverOrder.contactPhone}
				</td>
				<th>配送地址</th>
				<td>
					${deliverOrder.deliveryAddress}
				</td>
				<th>配送地区</th>
				<td>
					${deliverOrder.deliveryRegion}
				</td>
				<th>送达时间</th>
				<td>
					<fmt:formatDate value="${deliverOrder.deliveryRequireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th>供应商订单ID</th>
				<td colspan="">${deliverOrder.supplierOrderId}</td>
				<c:choose>
					<c:when test="${deliverOrder.status=='notDriver'}">
					<th>超时</th>
					<td>
						${deliverOrder.showTime}
					</td>
					</c:when>
					<c:otherwise>
					    <th></th><td></td>
					</c:otherwise>
				</c:choose>
				<th>回单</th>
				<c:forEach items="${deliverOrder.image}" var="image">
					<td rowspan="2">
					 <img src="${image}" width="80px" height="80px" />
					</td>
				</c:forEach>
			</tr>
			<tr>
				<th>送达备注</th>
				<td colspan="3">
					${deliverOrder.completeRemark}
				</td>
			</tr>
			<tr>
				<th>商品总重</th>
				<td>${deliverOrder.weight}克</td>
				<th>备注</th>
				<td colspan="3">
					${deliverOrder.remark}
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<div id="deliver_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="配送信息">
				<table id="deliverDataGrid"></table>
			</div>
			<div title="商品信息">
				<table id="itemDataGrid"></table>
			</div>
			<div title="配送商品信息">
				<table id="deliverItemDataGrid"></table>
			</div>
			<div title="订单日志">
				<table id="orderLogDataGrid"></table>
			</div>
		</div>
	</div>
</div>
</body>
</html>

