<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
	<title>Mbsupplierorder管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	  <c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderItemController/editPage')}">
		<script type="text/javascript">
            $.canEdit = true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderItemController/delete')}">
		<script type="text/javascript">
            $.canDelete = true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderItemController/view')}">
		<script type="text/javascript">
            $.canView = true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierStockInController/view')}">
		<script type="text/javascript">
            $.canStockInItemView = true;
		</script>
	</c:if>
	 <script type="text/javascript">
         var data;
         $(function() {
             parent.$.messager.progress('close');
         });
         $(function() {
            data= $('#itemDataGrid').datagrid({
                url: '${pageContext.request.contextPath}/mbSupplierOrderItemController/dataGrid?supplierOrderId=${mbSupplierOrder.id}',
                fit : true,
                fitColumns : true,
                border : false,
                pagination : false,
                idField : 'id',
                pageSize : 10,
                pageList : [ 10, 20, 30, 40, 50 ],
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
                    title : '编号',
                    width : 150,
                    hidden : true
                }, {
                    field : 'itemName',
                    title : '<%=TmbSupplierOrderItem.ALIAS_ITEM_NAME%>',
                    width : 150,
					align:"center",
                    formatter: function (value, row) {
                        if (row.price == 0 && row.itemName != "总量")
                            value += '<font color="red">【赠送】</font>';
                        return value;
                    }

                }, {
                    field : 'quantity',
                    title : '<%=TmbSupplierOrderItem.ALIAS_QUANTITY%>',
                    width : 50,
                    align:"center"
                },{
                    field : 'warehouseQuantity',
                    title : '<%=TmbSupplierOrderItem.ALIAS_WAREHOUSE_QUANTITY%>',
                    width : 50,
                    align:"center"
                }, {
                    field : 'price',
                    title : '<%=TmbSupplierOrderItem.ALIAS_PRICE%>',
                    width : 50,
					align:"center",
                    formatter:function(value){
                        return $.formatMoney(value);
                    }
                },{
                    field : 'sumPrice',
                    title : '总额',
                    width : 50,
                    align:"center",
                    formatter:function(value){
                        return $.formatMoney(value);
                    }
                }, ] ],
                onLoadSuccess : function() {
                    computeSupplierOrder();
                },
                toolbar : '#itemDataToolbar'
            });
		 })
         function loadStockInItem() {
             return $('#stockInItem').datagrid({
             url : '${pageContext.request.contextPath}/mbSupplierStockInController/dataGrid?supplierOrderId='+ ${mbSupplierOrder.id},
             fit : true,
             fitColumns : true,
             border : false,
             pagination : false,
             idField : 'id',
             pageSize : 10,
             pageList : [ 10, 20, 30, 40, 50 ],
             checkOnSelect : false,
             selectOnCheck : false,
             nowrap : false,
             striped : true,
             rownumbers : true,
             singleSelect : true,
             sortName : 'addtime',
             sortOrder : 'desc',
             columns : [ [ {
                 field : 'id',
                 title : '编号',
                 width : 30
             },{
                 field : 'supplierName',
                 title : '<%=TmbSupplierStockIn.ALIAS_SUPPLIER_NAME%>',
                 width : 100
             },{
                 field : 'addtime',
                 title : '<%=TmbSupplierStockIn.ALIAS_ADDTIME%>',
                 width : 100
             }, {
                     field : 'signDate',
                     title : '<%=TmbSupplierStockIn.ALIAS_SIGN_DATE%>',
                     width : 100
                 }, {
                     field : 'signPeopleName',
                     title : '<%=TmbSupplierStockIn.ALIAS_SIGN_PEOPLE_ID%>',
                     width : 50
                 },{
                     field : 'loginName',
                     title : '<%=TmbSupplierStockIn.ALIAS_LOGIN_ID%>',
                     width : 50
                 }, {
                     field : 'payStatusName',
                     title : '<%=TmbSupplierStockIn.ALIAS_PAY_STATUS%>',
                     width : 50
                 }, {
                     field : 'invoiceStatusName',
                     title : '<%=TmbSupplierStockIn.ALIAS_INVOICE_STATUS%>',
                     width : 50
                 },
                 {
                     field : 'supplierOrderId',
                     title : '<%=TmbSupplierStockIn.ALIAS_SUPPLIER_ORDER_ID%>',
                     width : 50
                 }
                 ,
                 {
                     field : 'warehouseName',
                     title : '<%=TmbSupplierStockIn.ALIAS_WAREHOUSE_ID%>',
                     width : 70
                 },{
                     field : 'action',
                     title : '操作',
                     width : 60,
                     formatter : function(value, row, index) {
                         var str = '';
                         if ($.canStockInItemView) {
                             str += $.formatString('<img onclick="viewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/map/magnifier.png');
                         }

                         return str;
                     }
                 }] ],
         });}


         var gridMap = {};
         $(function() {
             gridMap = {
                 handle:function(obj,clallback){
                     if (obj.grid == null) {
                         obj.grid = clallback();
                     } else {
                         obj.grid.datagrid('reload');
                     }
                 }, 1: {
                     invoke: function () {
                         gridMap.handle(this,loadStockInItem());
                     }, grid: null
                 },
             };
             $('#order_view_tabs').tabs({
                 onSelect: function (title, index) {
                     gridMap[index].invoke();
                 }
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
                 href : '${pageContext.request.contextPath}/mbSupplierStockInController/view?id=' + id
             });
         }
         function computeSupplierOrder() {
             //返回当前页的所有行
             var arr = $("#itemDataGrid").datagrid("getRows");
             if (arr.length!=0) {
                 var sumQuantity = 0;
                 var sumwarehouseQuantity = 0;
                 var sumTotalPrice=0
                 //累加
                 for (var i = 0; i < arr.length; i++) {
                     sumQuantity += arr[i].quantity
                     sumwarehouseQuantity += arr[i].warehouseQuantity
                     sumTotalPrice += arr[i].sumPrice
                 }
                 if(isNaN(sumwarehouseQuantity)){
                     sumwarehouseQuantity="";
				 }
                 //新增一行显示统计信息
                 $('#itemDataGrid').datagrid('appendRow', {
                     itemName: "总量",
                     quantity: sumQuantity,
                     warehouseQuantity: sumwarehouseQuantity,
					 price:"",
                     sumPrice:sumTotalPrice,
                 });
             }
         }
         function addItemFun() {
             parent.$.modalDialog({
                 title : '添加赠品',
                 width : 780,
                 height : 300,
                 href : '${pageContext.request.contextPath}/mbSupplierOrderItemController/addPage?supplierOrderId=${mbSupplierOrder.id}',
                 buttons : [ {
                     text : '添加',
                     handler : function() {
                         parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
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
	<div data-options="region:'north',title:'基本信息',border:false" style="height: 220px; overflow: hidden;">
		<table class="table">
			<tr>
				<th><%=TmbSupplierOrder.ALIAS_SUPPLIER_NAME%></th>
				<td>
					${mbSupplierOrder.supplierName}
				</td>
				<th><%=TmbSupplierContract.ALIAS_CODE%></th>
				<td>
			     	${mbSupplierOrder.code}
				</td>
				<th><%=TmbSupplierOrder.ALIAS_TOTAL_PRICE%></th>
				<td>
					${mbSupplierOrder.totalPrice/100.0}
				</td>
				<th><%=TmbSupplierOrder.ALIAS_STATUS%></th>
				<td>
					${mbSupplierOrder.statusName}
				</td>
			</tr>
			<tr>
				<th><%=TmbSupplierOrder.ALIAS_SUPPLIER_PEOPLE_NAME%></th>
				<td>
					${mbSupplierOrder.supplierPeopleName}
				</td>
				<th><%=TmbSupplierOrder.ALIAS_PLAN_STOCK_IN_DATE%></th>
				<td>
					${mbSupplierOrder.planStockInDate}
				</td>
				<th><%=TmbSupplierOrder.ALIAS_WAREHOUSE_NAME%></th>
				<td colspan="3">
					${mbSupplierOrder.warehouseName}
				</td>
			</tr>
			<tr>
				<th><%=TmbSupplierOrder.ALIAS_REMARK%></th>
				<td colspan="7">
					${mbSupplierOrder.remark}
				</td>
			</tr>
			<tr>
				<th><%=TmbSupplierOrder.ALIAS_REVIEWER_NAME%></th>
				<td>
					${mbSupplierOrder.reviewerName}
				</td>
				<th><%=TmbSupplierOrder.ALIAS_REVIEW_DATE%></th>
				<td>
					${mbSupplierOrder.reviewDate}
				</td>
			</tr>
			<tr>
				<th><%=TmbSupplierOrder.ALIAS_REVIEW_COMMENT%></th>
				<td colspan="7">
					${mbSupplierOrder.reviewComment}
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<div id="order_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="商品明细">
				<table id="itemDataGrid"></table>
			</div>
			<div title="入库信息">
				<table id="stockInItem"></table>
			</div>
		</div>
        <div id="itemDataToolbar" style="display: none;">
            <c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderItemController/addPage')}">
                <a onclick="addItemFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加赠品</a>
            </c:if>
        </div>
	</div>
</div>
</body>
</html>

