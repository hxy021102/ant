﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>Supplier管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/add')}">
		<script type="text/javascript">
            $.canAddOrder = true;
		</script>
	</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/supplierController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 50,
                formatter : function(value,row,index) {
                    return  '<a onclick="viewFun('+ row.id +')">'+ row.id + '</a>';
                }}, {
				field : 'appKey',
				title : 'appKey',
				width : 50
				}, {
				field : 'name',
				title : '接入方名称',
				width : 120
				}, {
                field : 'statusName',
                title : '状态',
                width : 50
            	}, {
				field : 'address',
				title : '接入方地址',
				width : 150
				}, {
				field : 'contacter',
				title : '联系人',
				width : 50
				}, {
				field : 'contactPhone',
				title : '联系电话',
				width : 50
				}, {
				field : 'action',
				title : '操作',
				width : 40,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					str += '&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
                    if ($.canAddOrder) {
                        str += $.formatString('<img onclick="addOrderFun(\'{0}\');" src="{1}" title="下单"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/book_previous.png');
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
				$.post('${pageContext.request.contextPath}/supplierController/delete', {
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
			href : '${pageContext.request.contextPath}/supplierController/editPage?id=' + id,
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
    function addOrderFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '编辑数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/deliverOrderController/addPage?supplierId=' + id,
            buttons : [ {
                text : '确认下单',
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
            id = rows.id;
        }
        var href = '${pageContext.request.contextPath}/supplierController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '接入方详情：' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:97%;"></iframe>',
            closable : true
        });
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/supplierController/addPage',
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
	        url:'${pageContext.request.contextPath}/supplierController/download',
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
							<th style="width: 50px">编号</th>
							<td>
											<input type="text" name="id" maxlength="32" class="span2"/>
							</td>
							<th style="width: 60px">接入方名称</th>
							<td>
								<input type="text" name="name" maxlength="128" class="span2"/>
							</td>
							<th style="width: 50px">状态</th>
							<td>
											<jb:select dataType="SLS" name="status"></jb:select>	
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>