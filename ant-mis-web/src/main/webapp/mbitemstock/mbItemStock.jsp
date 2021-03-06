﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemStock" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbItemStock管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/editAveragePricePage')}">
		<script type="text/javascript">
            $.canEditPrce = true;
		</script>
	</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/editSafePage')}">
	<script type="text/javascript">
		$.canEditSafe = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockLogController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/editStockQuantityPage')}">
	<script type="text/javascript">
        $.canEditStock = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/mbItemStockController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50,500 ],
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
				field : 'warehouseCode',
				title : '<%=TmbItemStock.ALIAS_WAREHOUSE_CODE%>',
				width : 20,
                formatter : function (value, row, index) {
				    if ($.canView){
                        return '<a onclick="viewFun(' + row.id + ')">' + row.warehouseCode + '</a>';
                    }else {
				        return row.warehouseCode;
					}
                }
				}, {
				field : 'warehouseName',
				title : '<%=TmbItemStock.ALIAS_WAREHOUSE_NAME%>',
				width : 50
				}, {
					field : 'itemCode',
					title : '商品编码',
					width : 50
				}, {
				field : 'itemName',
				title : '<%=TmbItemStock.ALIAS_ITEM_NAME%>',
				width : 50
				},{
                field : 'averagePrice',
                title : '<%=TmbItemStock.ALIAS_AVERAGE_PRICE%>',
                width : 25,
				align:"right",
                formatter:function(value){
                    return $.formatMoney(value);
                }
                },{
			    field : 'quantity',
				title : '<%=TmbItemStock.ALIAS_QUANTITY%>',
				width : 25,
				formatter: function (value, row) {
					if (value && row.safeQuantity) {
						if (value <= row.safeQuantity) {
							return '<font color="red">'+value+'<font>';
						}
					}
					return value;
				}
			},{
                field : 'orderQuantity',
                title : '<%=TmbItemStock.ALIAS_ORDER_QUANTITY%>',
                width : 25
            },{
				field : 'od15Quantity',
				title : '捡货中',
				width : 20
			},
                {
				field : 'safeQuantity',
				title : '<%=TmbItemStock.ALIAS_SAFE_QUANTITY%>',
				width : 15
			}, {
				field : 'action',
				title : '操作',
				width : 30,
				formatter : function(value, row, index) {
					var str = '';
                    if ($.canEditPrce) {
                        str += $.formatString('<img onclick="editPrice(\'{0}\');" src="{1}" title="编辑价格"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/coins.png');
                    }
					if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
                    if ($.canEditStock && row.warehouseType == "WT03" ) {
                        str += $.formatString('<img onclick="editStock(\'{0}\');" src="{1}" title="编辑库存"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil_go.png');
                    }
					if($.canEditSafe){
						str += $.formatString('<img onclick="editSafeFun(\'{0}\');" src="{1}" title="编辑安全库存"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/brick_edit.png');
                    }


					str += '&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
		});
	});

    function itemStockUpload(){
            parent.$.modalDialog({
                title : '批量导入',
                width : 780,
                height : 200,
                href : '${pageContext.request.contextPath}/mbItemStockController/uploadPage',
                buttons : [ {
                    text : '保存',
                    handler : function() {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                } ]
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
				$.post('${pageContext.request.contextPath}/mbItemStockController/delete', {
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
    function editPrice(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '编辑数据',
            width : 400,
            height : 250,
            href : '${pageContext.request.contextPath}/mbItemStockController/editAveragePricePage?id=' + id,
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

    function editStock(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '编辑数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbItemStockController/editStockQuantityPage?id=' + id,
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
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbItemStockController/editPage?id=' + id,
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

	function editSafeFun(id){
			if (id == undefined) {
				var rows = dataGrid.datagrid('getSelections');
				id = rows[0].id;
			}
			parent.$.modalDialog({
				title : '编辑数据',
				width : 400,
				height : 250,
				href : '${pageContext.request.contextPath}/mbItemStockController/editSafePage?id=' + id,
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
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '库存变更日志',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbItemStockLogController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbItemStockController/addPage',
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
	        url:'${pageContext.request.contextPath}/mbItemStockController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
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
						<th style="width: 50px"><%=TmbItemStock.ALIAS_WAREHOUSE_NAME%></th>
						<td>
							<jb:selectGrid dataType="warehouseId" name="warehouseId"></jb:selectGrid>

						</td>
						<th style="width: 50px"><%=TmbItemStock.ALIAS_ITEM_NAME%></th>
						<td>
							<jb:selectGrid dataType="itemId" name="itemId"></jb:selectGrid>
						</td>
                        <th><%=TmbItemStock.ALIAS_IS_SAFE%></th>
                        <td>
                            <select name="safe" class="easyui-combobox" data-options="width:140,height:29">
                                <option value=""></option>
                                <option value="1">大于等于安全库存</option>
                                <option value="0">小于安全库存</option>
                            </select>
                        </td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/uploadPage')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_add',plain:true" onclick="itemStockUpload();">导入</a>
		</c:if>
	</div>	
</body>
</html>