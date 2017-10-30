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
			fitColumns : true,
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
			columns : [ [ {
				field : 'id',
				title : '运单ID',
				width : 30,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
				},  {
                field : 'addtime',
                title : '创建时间',
                width : 50
                },{
                field : 'updatetime',
                title : '修改时间',
                width : 50
                }, {
                field : 'supplierOrderId',
                title : '供应商订单ID',
                width : 50
				},{
				field : 'supplierId',
				title : '供应商ID',
				width : 30
				}, {
                field : 'supplierName',
                title : '供应商名称',
                width : 80
                }, {
				field : 'amount',
				title : '总金额',
				align:"right",
				width : 30	,
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
				}, {
				field : 'statusName',
				title : '订单状态',
				width : 35
				}, {
				field : 'deliveryStatusName',
				title : '配送状态',
				width : 40
				},{
                field : 'contactPeople',
                title : '收货人',
                width : 30
                }, {
                field : 'deliveryRequireTime',
                title : '要求送达时间',
                width : 50
                }, {
				field : 'shopPayStatusName',
				title : '结算状态',
				width : 35
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
                fitColumns : true,
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
                columns : [ [ {
                    field : 'id',
                    title : '运单ID',
                    width : 30,
                    formatter : function (value, row, index) {
                        return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                    }
                },  {
                    field : 'addtime',
                    title : '创建时间',
                    width : 50
                },{
                    field : 'updatetime',
                    title : '修改时间',
                    width : 50
                }, {
                    field : 'supplierOrderId',
                    title : '供应商订单ID',
                    width : 50
                },{
                    field : 'supplierId',
                    title : '供应商ID',
                    width : 30
                }, {
                    field : 'supplierName',
                    title : '供应商名称',
                    width : 80
                }, {
                    field : 'amount',
                    title : '总金额',
                    align:"right",
                    width : 30	,
                    formatter: function (value) {
                        if (value == null)
                            return "";
                        return $.formatMoney(value);
                    }
                }, {
                    field : 'statusName',
                    title : '订单状态',
                    width : 35
                }, {
                    field : 'deliveryStatusName',
                    title : '配送状态',
                    width : 40
                },{
                    field : 'contactPeople',
                    title : '收货人',
                    width : 30
                }, {
                    field : 'deliveryRequireTime',
                    title : '要求送达时间',
                    width : 50
                }, {
                    field : 'shopPayStatusName',
                    title : '结算状态',
                    width : 35
                }] ],
                toolbar : '#toolbar',
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
		var options = dataGrid.datagrid("options");
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
            options.url += '?status=DOS01'+'&time='+30;
        }
        options.queryParams = $.serializeObject($('#searchForm'));
        selectDatagrid.datagrid(options);
		//dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 120px; overflow: hidden;">
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
					</tr>
					<tr>
						<th>结算状态</th>
						<td>
							<jb:select dataType="SPS" name="shopPayStatus"></jb:select>
						</td>
						<th>配送状态</th>
						<td >
							<jb:select dataType="DDS" name="deliveryStatus"></jb:select>
						</td>
						<th>订单时间</th>
						<td>
							<input type="text" class="span2 easyui-validatebox" data-options="required:false" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'endDate\',{M:-1});}',maxDate:'#F{$dp.$D(\'endDate\',{d:-1});}'})" id="startDate" name="startDate"/>
							至	<input type="text" class="span2 easyui-validatebox" data-options="required:false" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'startDate\',{d:1});}',maxDate:'#F{$dp.$D(\'startDate\',{M:1});}'})" id="endDate" name="endDate"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<div id="deliverOrder_list_tabs" class="easyui-tabs" data-options="fit : true,border:false">
				<div title="所有运单">
					<table id="dataGrid"></table>
				</div>
				<div title="超时未分配运单">
					<table id="dataGridOverTimeOrder"></table>
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
</body>
</html>