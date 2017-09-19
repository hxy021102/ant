﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url:'',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'updatetime',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : true,
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
				width : 70
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
				width : 50
			}, {
				field : 'deliveryStatusName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_STATUS%>',
				width : 30
			}, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 30
			}, {
				field : 'deliveryRequireTime',
				title : '<%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>',
				width : 50
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
		setTimeout(function(){
			searchFun();
		},100);
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
				$.post('${pageContext.request.contextPath}/mbOrderController/delete', {
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
			href : '${pageContext.request.contextPath}/mbOrderController/editPage?id=' + id,
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

	function viewOrder(id) {
        var href = '${pageContext.request.contextPath}/mbOrderController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '订单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
        });
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbOrderController/addPage',
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
	        url:'${pageContext.request.contextPath}/mbOrderController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function searchFun() {
		var options = {};
		options.url = '${pageContext.request.contextPath}/mbOrderController/dataGrid';
		options.queryParams = $.serializeObject($('#searchForm'));
		dataGrid.datagrid(options);
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
						<th><%=TmbOrder.ALIAS_ID%>
						</th>
						<td>
							<input type="text" name="id" maxlength="10" class="span2"/>
						</td>
						<th><%=TmbOrder.ALIAS_CONTACT_PHONE%>
						</th>
						<td>
							<input type="text" name="contactPhone" maxlength="32" class="span2"/>
						</td>
						<th><%=TmbOrder.ALIAS_USER_ID%>
						</th>
						<td>
							<input type="text" name="userId" maxlength="10" class="span2"/>
						</td>
						<th>门店
						</th>
						<td>
							<jb:selectGrid dataType="shopId" name="shopId"></jb:selectGrid>
						</td>
					</tr>
					<tr>
						<th><%=TmbOrder.ALIAS_STATUS%>
						</th>
						<td>
							<jb:select dataType="OD" name="status" value="${status}"></jb:select>
						</td>
						<th><%=TmbOrder.ALIAS_DELIVERY_STATUS%>
						</th>
						<td>
							<jb:select dataType="DS" name="deliveryStatus"></jb:select>
						</td>
						<th><%=TmbOrder.ALIAS_PAY_STATUS%>
						</th>
						<td>
							<jb:select dataType="PS" name="payStatus"></jb:select>
						</td>
						<th><%=TmbOrder.ALIAS_PAY_WAY%>
						</th>
						<td>
							<jb:select dataType="PW" name="payWay"></jb:select>
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