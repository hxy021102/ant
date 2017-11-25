﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrder" %>
<%@ page import="com.mobian.model.TmbShop" %>
<%@ page import="com.mobian.model.TmbItem" %>
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
            singleSelect : false,
            columns : [ [  {
                field : 'ck',
                checkbox:true,
                width : 30
            },{
                field : 'addtime',
                title : '分配时间',
                width : 60,
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
                width : 40
            },{
                field : 'amount',
                title : '总金额',
                align:"right",
                width : 30,
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
            }] ],
            onCheckAll: function (index, row) {
                listDeliverOrderShopItem();
            },
            onCheck: function (index, row) {
                listDeliverOrderShopItem();
            },
            onUncheckAll: function (index, row) {
                listDeliverOrderShopItem();
            },
            onUncheck: function (index, row) {
                listDeliverOrderShopItem();
            }
        });
    });

    $(function () {
        return mbItemDataGrid=$('#mbItemDataGrid').datagrid({
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
                field : 'code',
                title : '<%=TmbItem.ALIAS_CODE%>',
                width : 40
            }, {
                field : 'name',
                title : '<%=TmbItem.ALIAS_NAME%>',
                width : 50
            }, {
                field: 'quantityUnitName',
                title: '<%=TmbItem.ALIAS_QUANTITY_UNIT%>',
                width: 30
            },{
                field : 'marketPrice',
                title : '<%=TmbItem.ALIAS_MARKET_PRICE%>',
                width : 35,
                align:'right',
                formatter:function(value){
                    return $.formatMoney(value);
                }
            }, {
                field: 'buyPrice',
                title: '购买价',
                width: 35,
                align:'right',
                formatter:function(value){
                    if(value == undefined)return "";
                    return $.formatMoney(value);
                }
            }, {
                field: 'quantity',
                title: '数量',
                width: 20
            }] ],
            toolbar: '#toolbarmbItem',
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
        deliverOrderShopDataGrid.datagrid('clearChecked');
        cleanmbItem()
        var options = deliverOrderShopDataGrid.datagrid('options');
        options.url = '${pageContext.request.contextPath}/deliverOrderShopController/dataGrid',
        $('#shopId').val(shopId);
        deliverOrderShopDataGrid.datagrid('load', $.serializeObject($('#deliverOrderSearchForm')));

    }
    //加载配货商品列表
    function listDeliverOrderShopItem() {
        var rows = $('#deliverOrderShopDataGrid').datagrid('getChecked');
        if (rows.length > 0) {
            var deliverOrderShopIds = new Array();
            for (var i = 0; i < rows.length; i++) {
                deliverOrderShopIds[i] = parseFloat(rows[i].id);
            }
            var options = mbItemDataGrid.datagrid('options');
            options.url = '${pageContext.request.contextPath}/mbItemController/dataGridWidthDeliverOrderShop',
                $('#selectshopId').val(rows[0].shopId);
            $('#deliverOrderShopIds').val(deliverOrderShopIds);
            mbItemDataGrid.datagrid('load', $.serializeObject($('#mbItemSearchForm')));
        } else {
            cleanmbItem()
        }
    }

    function cleanmbItem() {
        $('#deliverOrderShopIds').val("");
        $('#selectshopId').val("");
        mbItemDataGrid.datagrid('load', $.serializeObject($('#mbItemSearchForm')));
    }

    //批量创建补货订单
    function addBatchOrder() {
        var rows = $("#mbItemDataGrid").datagrid("getRows");
        if (rows.length > 0) {
            var data = $.serializeObject($('#mbItemSearchForm'));
            data.mbOrderItemList = rows;
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i].buyPrice * rows[i].quantity;
            }
            data.deliveryWay = 'DW02';
            var message = "该订单总金额：" + $.formatMoney(total) + ",系统自动扣余额";
            parent.$.messager.confirm("询问", message, function (result) {
                if (result) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/mbOrderController/addByCall',
                        data: JSON.stringify(data),
                        dataType: "json",
                        type: "POST",
                        contentType: "application/json;charset=UTF-8",
                        beforeSend: function (request) {
                            parent.$.messager.progress({
                                title: '提示',
                                text: '数据处理中，请稍后....'
                            });
                        },
                        success: function (result) {
                            parent.$.messager.progress('close');
                            if (result.success) {
                                parent.$.messager.alert('提示', "创建订单成功！");
                                listDeliverOrderShop( dataGrid.datagrid('getSelections')[0].id)
                                cleanmbItem();
                            } else {
                                parent.$.messager.alert('错误', result.msg);
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            parent.$.messager.progress('close');
                        }
                    });
                }
            });
        } else {
            parent.$.messager.alert("提示", "请选择要创建的门店订单！")
        }
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
						 <form id="mbItemSearchForm" style="display: none">
							 <input type="hidden" name="deliverOrderShopIds" id = "deliverOrderShopIds">
							 <input type="hidden" name="shopId" id = "selectshopId">
						 </form>
					 </div>
					 <div data-options="region:'center',border:false">
						 <table id="mbItemDataGrid"></table>
					 </div>
				 </div>
			</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>
	<div id="toolbarmbItem" style="display: none;">
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/addByCall')}">
		<a onclick="addBatchOrder();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">创建订单</a>
	</c:if>
	</div>
</body>
</html>