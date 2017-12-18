<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mobian.model.TmbSupplierOrderItem" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>DeliverOrder管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
    var gridMap = {}, selectDatagrid;
    $(function() {
        gridMap = {
            handle: function (obj, clallback) {
                obj.grid = clallback();
                selectDatagrid = obj.grid;
                setTimeout(function () {
                    searchFun();
                }, 100);
            },
            0: {
                invoke: function () {
                    gridMap.handle(this,loadDatagrid );
                }, grid: null
            }, 1: {
                invoke: function () {
                    gridMap.handle(this, loadDataGridOverTimeOrder);
                }, grid: null
            }, 2: {
                invoke: function () {
                    gridMap.handle(this, loadDataGridNotDriver);
                }, grid: null
            }, 3: {
                invoke: function () {
                    gridMap.handle(this, loadDataGridAgentStatus_DTS01);
                }, grid: null
            }, 4: {
                invoke: function () {
                    gridMap.handle(this, loadDataGridAgentStatus_DTS02);
                }, grid: null
            }, 5: {
                invoke: function () {
                    gridMap.handle(this, loadDataGridAgentStatus_DTS03);
                }, grid: null
            }
        };
        $('#deliverOrder_list_tabs').tabs({
            onSelect: function (title, index) {
                gridMap[index].invoke();
            }
        });
        gridMap[0].invoke();
        setInterval(function(){ searchFun();}, 60000);//每分钟自查询一次
    });
    var dataGrid;
	function loadDatagrid() {
		return dataGrid = $('#dataGrid').datagrid({
			//url : '${pageContext.request.contextPath}/deliverOrderController/dataGrid?id',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortOrder : 'desc',
            sortName:'updatetime',
            sortable:true,
            checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
            frozenColumns:[[{
                field : 'id',
                title : '运单ID',
                width : 60,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
            }, {
                field : 'statusName',
                title : '订单状态',
                width : 60
            }, {
                field : 'deliveryStatusName',
                title : '配送状态',
                width : 55
            }, {
                field : 'supplierOrderId',
                title : '订单ID',
                width : 125
            }, {
                field : 'shopName',
                title : '门店名称',
                width : 125
            }]],
			columns : [ [ {
                field : 'originalShop',
                title : '店铺',
                width : 80
            }, {
                field : 'originalOrderId',
                title : '订单号',
                width : 170
            },  {
                field : 'addtime',
                title : '创建时间',
                width : 125
            },{
                field : 'contactPeople',
                title : '收货人',
                width : 60
                }, {
                field : 'deliveryRequireTime',
                title : '要求送达时间',
                width : 125
                }, {
				field : 'shopPayStatusName',
				title : '门店结算',
				width : 60
				}, {
                field : 'payStatusName',
                title : '供应商结算',
                width : 60
                },{
                field : 'supplierId',
                title : '供应商ID',
                width : 30
            }, {
                field : 'supplierName',
                title : '供应商名称',
                width : 125
            }, {
                field : 'amount',
                title : '总金额',
                align:"right",
                width : 60	,
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
            },{
                field : 'updatetime',
                title : '修改时间',
                width : 125
            }] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
			}
		});
	}
        function loadDataGridOverTimeOrder() {
            return  $('#dataGridOverTimeOrder').datagrid({
              //  url : '${pageContext.request.contextPath}/deliverOrderController/dataGrid?id',
                fit : true,
                fitColumns : false,
                border : false,
                pagination : true,
                idField : 'id',
                pageSize : 10,
                pageList : [ 10, 20, 30, 40, 50 ],
                sortOrder : 'desc',
                sortName:'updatetime',
                sortable:true,
                checkOnSelect : false,
                selectOnCheck : false,
                nowrap : false,
                striped : true,
                rownumbers : true,
                singleSelect : true,
                frozenColumns:[[{
                    field : 'id',
                    title : '运单ID',
                    width : 60,
                    formatter : function (value, row, index) {
                        return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                    }
                }, {
                    field : 'supplierOrderId',
                    title : '订单ID',
                    width : 125
                }]],
                columns : [ [ {
                    field : 'originalShop',
                    title : '店铺',
                    width : 80
                }, {
                    field : 'originalOrderId',
                    title : '订单号',
                    width : 170
                },  {
                    field : 'addtime',
                    title : '创建时间',
                    width : 125
                },{
                    field : 'contactPeople',
                    title : '收货人',
                    width : 60
                }, {
                    field : 'deliveryRequireTime',
                    title : '要求送达时间',
                    width : 125
                }, {
                    field : 'shopPayStatusName',
                    title : '门店结算',
                    width : 60
                }, {
                    field : 'payStatusName',
                    title : '供应商结算',
                    width : 60
                },{
                    field : 'supplierId',
                    title : '供应商ID',
                    width : 30
                }, {
                    field : 'supplierName',
                    title : '供应商名称',
                    width : 125
                }, {
                    field : 'amount',
                    title : '总金额',
                    align:"right",
                    width : 60	,
                    formatter: function (value) {
                        if (value == null)
                            return "";
                        return $.formatMoney(value);
                    }
                },{
                    field : 'updatetime',
                    title : '修改时间',
                    width : 125
                }] ],
                toolbar : '#toolbar',
                onLoadSuccess : function() {
                    $('#searchForm table').show();
                    parent.$.messager.progress('close');
                }
            });
		}

    function loadDataGridNotDriver() {
        return  $('#dataGridNotDriver').datagrid({
            //  url : '${pageContext.request.contextPath}/deliverOrderController/dataGrid?id',
            fit : true,
            fitColumns : false,
            border : false,
            pagination : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50 ],
            sortOrder : 'desc',
            sortName:'updatetime',
            sortable:true,
            checkOnSelect : false,
            selectOnCheck : false,
            nowrap : false,
            striped : true,
            rownumbers : true,
            singleSelect : true,
            frozenColumns:[[{
                field : 'id',
                title : '运单ID',
                width : 60,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
            }, {
                field : 'supplierOrderId',
                title : '订单ID',
                width : 125
            }, {
                field : 'shopName',
                title : '门店名称',
                width : 125
            }]],
            columns : [ [ {
                field : 'originalShop',
                title : '店铺',
                width : 80
            }, {
                field : 'originalOrderId',
                title : '订单号',
                width : 170
            },  {
                field : 'addtime',
                title : '创建时间',
                width : 125
            },{
                field : 'contactPeople',
                title : '收货人',
                width : 60
            }, {
                field : 'deliveryRequireTime',
                title : '要求送达时间',
                width : 125
            }, {
                field : 'shopPayStatusName',
                title : '门店结算',
                width : 60
            }, {
                field : 'payStatusName',
                title : '供应商结算',
                width : 60
            },{
                field : 'supplierId',
                title : '供应商ID',
                width : 30
            }, {
                field : 'supplierName',
                title : '供应商名称',
                width : 125
            }, {
                field : 'amount',
                title : '总金额',
                align:"right",
                width : 60	,
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
            },{
                field : 'updatetime',
                title : '修改时间',
                width : 125
            }] ],
            toolbar : '#toolbar',
            onLoadSuccess : function() {
                $('#searchForm table').show();
                parent.$.messager.progress('close');
            }
        });
    }

    function loadDataGridAgentStatus_DTS01() {
        return  $('#dataGridAgentStatus_DTS01').datagrid({
            fit : true,
            fitColumns : false,
            border : false,
            pagination : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 100,200 ],
            sortOrder : 'desc',
            sortName:'updatetime',
            sortable:true,
            checkOnSelect : false,
            selectOnCheck : false,
            nowrap : false,
            striped : true,
            rownumbers : true,
            singleSelect : true,
            frozenColumns:[[{
                field : 'checkbox',
                checkbox:true,
                width : 30
            },{
                field : 'id',
                title : '运单ID',
                width : 60,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
            }, {
                field : 'statusName',
                title : '订单状态',
                width : 60
            }, {
                field : 'deliveryStatusName',
                title : '配送状态',
                width : 55
            }, {
                field : 'supplierOrderId',
                title : '订单ID',
                width : 125
            }, {
                field : 'shopName',
                title : '门店名称',
                width : 125,
                sortable : true
            }]],
            columns : [ [ {
                field : 'originalShop',
                title : '店铺',
                width : 80
            }, {
                field : 'originalOrderId',
                title : '订单号',
                width : 170
            },  {
                field : 'addtime',
                title : '创建时间',
                width : 125
            },{
                field : 'contactPeople',
                title : '收货人',
                width : 60
            }, {
                field : 'deliveryRequireTime',
                title : '要求送达时间',
                width : 125
            }, {
                field : 'shopPayStatusName',
                title : '门店结算',
                width : 60
            }, {
                field : 'payStatusName',
                title : '供应商结算',
                width : 60
            },{
                field : 'supplierId',
                title : '供应商ID',
                width : 30
            }, {
                field : 'supplierName',
                title : '供应商名称',
                width : 125
            }, {
                field : 'amount',
                title : '总金额',
                align:"right",
                width : 60	,
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
            },{
                field : 'updatetime',
                title : '修改时间',
                width : 125
            }] ],
            toolbar : '#toolbar01',
            onLoadSuccess : function() {
                $('#searchForm table').show();
                parent.$.messager.progress('close');
            }
        });
    }

    function loadDataGridAgentStatus_DTS02() {
        return  $('#dataGridAgentStatus_DTS02').datagrid({
            fit : true,
            fitColumns : false,
            border : false,
            pagination : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 100,200 ],
            sortOrder : 'desc',
            sortName:'updatetime',
            sortable:true,
            checkOnSelect : false,
            selectOnCheck : false,
            nowrap : false,
            striped : true,
            rownumbers : true,
            singleSelect : true,
            frozenColumns:[[{
                field : 'checkbox',
                checkbox:true,
                width : 30
            },{
                field : 'id',
                title : '运单ID',
                width : 60,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
            }, {
                field : 'statusName',
                title : '订单状态',
                width : 60
            }, {
                field : 'deliveryStatusName',
                title : '配送状态',
                width : 55
            }, {
                field : 'supplierOrderId',
                title : '订单ID',
                width : 125
            }, {
                field : 'shopName',
                title : '门店名称',
                width : 125,
                sortable : true
            }]],
            columns : [ [ {
                field : 'originalShop',
                title : '店铺',
                width : 80
            }, {
                field : 'originalOrderId',
                title : '订单号',
                width : 170
            },  {
                field : 'addtime',
                title : '创建时间',
                width : 125
            },{
                field : 'contactPeople',
                title : '收货人',
                width : 60
            }, {
                field : 'deliveryRequireTime',
                title : '要求送达时间',
                width : 125
            }, {
                field : 'shopPayStatusName',
                title : '门店结算',
                width : 60
            }, {
                field : 'payStatusName',
                title : '供应商结算',
                width : 60
            },{
                field : 'supplierId',
                title : '供应商ID',
                width : 30
            }, {
                field : 'supplierName',
                title : '供应商名称',
                width : 125
            }, {
                field : 'amount',
                title : '总金额',
                align:"right",
                width : 60	,
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
            },{
                field : 'updatetime',
                title : '修改时间',
                width : 125
            }] ],
            toolbar : '#toolbar02',
            onLoadSuccess : function() {
                $('#searchForm table').show();
                parent.$.messager.progress('close');
            }
        });
    }

    function loadDataGridAgentStatus_DTS03() {
        return  $('#dataGridAgentStatus_DTS03').datagrid({
            fit : true,
            fitColumns : false,
            border : false,
            pagination : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 100,200 ],
            sortOrder : 'desc',
            sortName:'updatetime',
            sortable:true,
            checkOnSelect : false,
            selectOnCheck : false,
            nowrap : false,
            striped : true,
            rownumbers : true,
            singleSelect : true,
            frozenColumns:[[{
                field : 'id',
                title : '运单ID',
                width : 60,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
            }, {
                field : 'statusName',
                title : '订单状态',
                width : 60
            }, {
                field : 'deliveryStatusName',
                title : '配送状态',
                width : 55
            }, {
                field : 'supplierOrderId',
                title : '订单ID',
                width : 125
            }, {
                field : 'shopName',
                title : '门店名称',
                width : 125
            }]],
            columns : [ [ {
                field : 'originalShop',
                title : '店铺',
                width : 80
            }, {
                field : 'originalOrderId',
                title : '订单号',
                width : 170
            },  {
                field : 'addtime',
                title : '创建时间',
                width : 125
            },{
                field : 'contactPeople',
                title : '收货人',
                width : 60
            }, {
                field : 'deliveryRequireTime',
                title : '要求送达时间',
                width : 125
            }, {
                field : 'shopPayStatusName',
                title : '门店结算',
                width : 60
            }, {
                field : 'payStatusName',
                title : '供应商结算',
                width : 60
            },{
                field : 'supplierId',
                title : '供应商ID',
                width : 30
            }, {
                field : 'supplierName',
                title : '供应商名称',
                width : 125
            }, {
                field : 'amount',
                title : '总金额',
                align:"right",
                width : 60	,
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
            },{
                field : 'updatetime',
                title : '修改时间',
                width : 125
            }] ],
            toolbar : '#toolbar03',
            onLoadSuccess : function() {
                $('#searchForm table').show();
                parent.$.messager.progress('close');
            }
        });
    }

	function deleteFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/deliverOrderController/delete', {
					id : id
				}, function(result) {
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
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/deliverOrderController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function viewFun(id) {
        var href = '${pageContext.request.contextPath}/deliverOrderController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '运单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
        });
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/deliverOrderController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	function downloadTable(){
		var options = selectDatagrid.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/deliverOrderController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function uploadTable(id) {
//        if (id == undefined) {
//            var rows = dataGrid.datagrid('getSelections');
//            id = rows[0].id;
//        }
		parent.$.modalDialog({
			title:'批量导入',
			width:780,
			height:200,
			href:'${pageContext.request.contextPath}/deliverOrderController/uploadPage',
			buttons:[{
			    text:'保存',
				handler:function () {
					parent.$.modalDialog.openner_dataGrid = dataGrid;
					var f = parent.$.modalDialog.handler.find("#form");
					f.submit();
                }
			}]
		});
    }
	function searchFun() {
        var options = {};
        options.url = '${pageContext.request.contextPath}/deliverOrderController/dataGrid';
        var tab = $('#deliverOrder_list_tabs').tabs('getSelected');
        var index = $('#deliverOrder_list_tabs').tabs('getTabIndex',tab);
        if(index == 1) {
            options.url += '?time=30';
        }else if(index == 2){
            options.url +='?status=notDriver';
        } else if(index == 3) {
            options.url +='?agentStatus=DTS01&deliveryWay=DAW04,DAW05';
        } else if(index == 4) {
            options.url +='?agentStatus=DTS02&deliveryWay=DAW04,DAW05';
        } else if(index == 5) {
            options.url +='?agentStatus=DTS03&deliveryWay=DAW04,DAW05';
        }
        options.queryParams = $.serializeObject($('#searchForm'));
        selectDatagrid.datagrid(options);
		//dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
        selectDatagrid.datagrid('load', {});
	}

    var isPrint = true;
    function batchPrint() {
        if(!isPrint) {
            parent.$.messager.show({
                title : '提示',
                msg : '操作频繁请稍后重试或刷新页面！'
            });
            return;
        }
        var rows = selectDatagrid.datagrid('getChecked');
        var ids = [];
        if (rows.length > 0) {
            parent.$.messager.confirm('确认', '打印前请确认，避免重复打印？', function(r) {
                if (r) {
                    isPrint = false;
                    for ( var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].id);
                    }
                    $('#printIframe').attr("src",'${pageContext.request.contextPath}/deliverOrderController/printView?deliverOrderIds=' + ids);
                    setTimeout(function(){
                        isPrint = true;
                    }, 30000);
                }
            });
        } else {
            parent.$.messager.show({
                title : '提示',
                msg : '请勾选要打印的运单！'
            });
        }

    }

    function batchDeliver() {
        var rows = selectDatagrid.datagrid('getChecked');
        var ids = [], nonStockOutIds = [];
        if (rows.length > 0) {
            for ( var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
                if(!rows[i].stockOutNum || rows[i].stockOutNum == 0) {
                    nonStockOutIds.push(rows[i].id);
                }
            }
            var msg = '您是否确认当前选中的运单已发货？';
            if(nonStockOutIds.length > 0) {
                msg = '运单' + nonStockOutIds.join(',') + '<font color="red">未创建出库单</font>，是否继续已发货？';
            }
            parent.$.messager.confirm('确认', msg, function(r) {
                if (r) {
                    parent.$.messager.progress({
                        title : '提示',
                        text : '数据处理中，请稍后....'
                    });
                    $.post('${pageContext.request.contextPath}/deliverOrderController/batchUpdateOrderDeliver', {
                        deliverOrderIds : ids.join(',')
                    }, function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            selectDatagrid.datagrid('reload');
                        }
                        parent.$.messager.progress('close');
                    }, 'JSON');
                }
            });
        } else {
            parent.$.messager.show({
                title : '提示',
                msg : '请勾选要打印的运单！'
            });
        }
    }

    function addStockOut() {
        var rows = selectDatagrid.datagrid('getChecked');
        var ids = [], stockOutIds = [];
        if (rows.length > 0) {
            for ( var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
                if(rows[i].stockOutNum && rows[i].stockOutNum > 0) {
                    stockOutIds.push(rows[i].id);
                }
            }
            if(stockOutIds.length > 0) {
                parent.$.messager.confirm('确认', '运单' + stockOutIds.join(',') + '<font color="red">已出库</font>，是否继续创建！', function(r) {
                    if (r) {
                        addStockOutPage(ids);
                    }
                });
            } else {
                addStockOutPage(ids);
            }

        } else {
            parent.$.messager.show({
                title : '提示',
                msg : '请勾选要发货的运单！'
            });
        }
    }

    function addStockOutPage(ids) {
        parent.$.modalDialog({
            title : '创建出库单',
            width : 780,
            height : 540,
            href : '${pageContext.request.contextPath}/mbStockOutOrderController/addStockOutPage?deliverOrderIds=' + ids ,
            buttons : [ {
                text: '关闭',
                handler: function () {
                    parent.$.modalDialog.handler.dialog('close');
                }
            }, {
                text: '创建',
                handler: function () {
                    parent.$.messager.confirm('询问', '请确认是否创建出库单？', function(b) {
                        if (b) {
                            parent.$.modalDialog.openner_dataGrid = selectDatagrid;
                            var f = parent.$.modalDialog.handler.find('#form');
                            f.submit();
                        }
                    });

                }
            }]
        });
    }
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 165px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th>运单ID</th>
						<td>
							<input type="text" name="id" maxlength="10" class="span2"/>
						</td>
						<th>供应商名称</th>
						<td>
							<jb:selectGrid dataType="deliverSupplierId" name="supplierId"></jb:selectGrid>
						</td>
						<th>订单状态</th>
						<td>
							<jb:select dataType="DOS" name="status"></jb:select>
						</td>
						<th>门店名称</th>
						<td>
							<jb:selectGrid dataType="shopId" name="shopId" value="${shopId}"></jb:selectGrid>
						</td>
					</tr>
					<tr>
						<th>门店结算</th>
						<td>
							<jb:select dataType="SPS" name="shopPayStatus"></jb:select>
						</td>
						<th>配送状态</th>
						<td >
							<jb:select dataType="DDS" name="deliveryStatus"></jb:select>
						</td>
						<th>订单ID</th>
						<td >
							<input type="text" name="supplierOrderId" class="span2"/>
						</td>
						<th>店铺</th>
						<td >
							<input type="text" name="originalShop" class="span2"/>
						</td>
					</tr>
					<tr>
						<th>原订单号</th>
						<td >
							<input type="text" name="originalOrderId" class="span2"/>
						</td>
						<th >订单时间</th>
						<td colspan="10">
							<input type="text" class="span2 easyui-validatebox" data-options="required:false" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'endDate\',{M:-1});}',maxDate:'#F{$dp.$D(\'endDate\',{d:-1});}'})" id="startDate" name="startDate"/>
							至	<input type="text" class="span2 easyui-validatebox" data-options="required:false" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'startDate\',{d:1});}',maxDate:'#F{$dp.$D(\'startDate\',{M:1});}'})" id="endDate" name="endDate"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<div id="deliverOrder_list_tabs" class="easyui-tabs" data-options="fit : true,border:false">
				<div title="所有">
					<table id="dataGrid"></table>
				</div>
				<div title="超时未分配">
					<table id="dataGridOverTimeOrder"></table>
				</div>
				<div title="超时未配送">
					<table id="dataGridNotDriver"></table>
				</div>
                <div title="未打单">
                    <table id="dataGridAgentStatus_DTS01"></table>
                </div>
                <div title="已打单">
                    <table id="dataGridAgentStatus_DTS02"></table>
                </div>
                <div title="[代送]待签收">
                    <table id="dataGridAgentStatus_DTS03"></table>
                </div>
		     </div>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>

		<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/upload')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="uploadTable();">导入</a>
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>

	</div>
    <div id="toolbar01" style="display: none;">
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
        <c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/printView')}">
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="batchPrint();">批量打单</a>
        </c:if>
    </div>
    <div id="toolbar02" style="display: none;">
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbStockOutOrderController/addStockOutPage')}">
            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="addStockOut();">创建出库单</a>
        </c:if>
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="batchDeliver();">批量确认发货</a>

    </div>
    <div id="toolbar03" style="display: none;">
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>

    </div>
    <iframe id="printIframe" style="display: none;"></iframe>
</body>
</html>