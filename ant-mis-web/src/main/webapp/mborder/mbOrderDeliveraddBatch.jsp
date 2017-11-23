﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrder" %>
<%@ page import="com.mobian.model.TmbShop" %>
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

<script type="text/javascript">
    var dataGrid,deliverOrderShopDataGrid;
    $(function () {
        dataGrid = $('#dataGrid').datagrid({
            url: '',
            fit: true,
            fitColumns: true,
            border: false,
            pagination: false,
            idField: 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50,2000],
           // sortName: 'id',
            sortOrder: 'desc',
            checkOnSelect: false,
            selectOnCheck: false,
            nowrap: true,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            columns: [[
                {
                    field: 'id',
                    title: '门店ID',
                    width: 25,
                    formatter: function (value, row, index) {
                        return '<a onclick="viewShop(' + row.id + ')">' + row.id + '</a>';
                    }
                }, {
                    field: 'name',
                    title: '<%=TmbShop.ALIAS_NAME%>',
                    width: 60,
                    formatter: function (value, row, index) {
                        var str = value;
                        if(row.parentId == -1) str = '<font color="red">(主)</font>' + value;
                        return str;
                    }
                },{
                    field: 'shopTypeName',
                    title: '<%=TmbShop.ALIAS_SHOP_TYPE%>',
                    width: 30
                }]],
            toolbar: '#toolbar',
            onSelect : function(index, row) {
                listDeliverOrderShop(row.id);
            }
        });
        parent.$.messager.progress('close');
    });

   $(function () {
        return deliverOrderShopDataGrid=$('#deliverOrderShopDataGrid').datagrid({
           /* url : '${pageContext.request.contextPath}/deliverOrderShopController/dataGrid',*/
            fit : true,
            fitColumns : true,
            border : false,
            pagination : false,
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
    });

    $(function () {
        return deliverOrderShopItemDataGrid=$('#deliverOrderShopItemDataGrid').datagrid({
            /*url : '${pageContext.request.contextPath}/deliverOrderShopItemController/dataGrid?deliverOrderId='+${deliverOrder.id},*/
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
    });


    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
    }

    function searchFun() {
        var options = {};
        options.url = '${pageContext.request.contextPath}/mbShopController/dataGridDeliverOrderShop';
        options.queryParams = $.serializeObject($('#searchForm'));
        dataGrid.datagrid(options);
    }
    function listDeliverOrderShop(shopId) {
        var options = deliverOrderShopDataGrid.datagrid('options');
        options.url = '${pageContext.request.contextPath}/deliverOrderShopController/dataGrid',
			alert(shopId)
        $('#shopId').val(shopId);
        deliverOrderShopDataGrid.datagrid('load', $.serializeObject($('#deliverOrderSearchForm')));
    }
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
			<div data-options="region:'west'" style="width: 400px; overflow: hidden;">
				<div class="easyui-layout" data-options="fit : true,border : false">
					<div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;">
						<form id="searchForm">
							<table class="table table-hover table-condensed">
									 <tr>
										 <th>订单时间</th>
										 <td >
											 <input type="text" class="span2 easyui-validatebox" data-options="required:false" onclick="WdatePicker({dateFmt:'<%=TmbOrder.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'endDate\',{M:-1});}',maxDate:'#F{$dp.$D(\'endDate\',{d:-1});}'})" id="startDate" name="startDate"/>
										   至<input type="text" class="span2 easyui-validatebox" data-options="required:false" onclick="WdatePicker({dateFmt:'<%=TmbOrder.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'startDate\',{d:1});}',maxDate:'#F{$dp.$D(\'startDate\',{M:1});}'})" id="endDate" name="endDate"/>
										 </td>
									 </tr>
							</table>
						</form>
						<form id="deliverOrderSearchForm" style="display: none">
							<input type="hidden" name="shopId" id = "shopId">
							<input type="hidden" name="status" value="DSS02,DSS04,DSS06">
							<input type="hidden" name="orderId" value="null">
						</form>
					</div>
					<div data-options="region:'center',border:false">
						<table id="dataGrid"></table>
					</div>
				</div>
			</div>
			<div data-options="region:'center'" style="width: 220px; overflow: hidden;">
			     <div class="easyui-layout" data-options="fit : true,border : false">
					 <div data-options="region:'north',border:false" style="height: 200px; overflow: hidden;">
						 <table id="deliverOrderShopDataGrid"></table>
					 </div>
					 <div data-options="region:'center',border:false">
						 <table id="deliverOrderShopItemDataGrid"></table>
					 </div>
				 </div>
			</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>
</body>
</html>