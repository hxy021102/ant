<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemStockLog" %>
<%@ page import="com.mobian.model.TmbItemStock" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbItemStockLog管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockLogController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockLogController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockLogController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		parent.$.messager.progress('close');
		dataGrid = $('#dataGrid').datagrid({
			//url : '${pageContext.request.contextPath}/mbItemStockLogController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 100, 500, 1000, 2000, 5000 ],
			sortName : 'id',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			toolbar: '#toolbar',
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
				$.post('${pageContext.request.contextPath}/mbItemStockLogController/delete', {
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
			href : '${pageContext.request.contextPath}/mbItemStockLogController/editPage?id=' + id,
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
			title : '查看数据',
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
			href : '${pageContext.request.contextPath}/mbItemStockLogController/addPage',
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
	        url:'${pageContext.request.contextPath}/mbItemStockLogController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function searchFun() {
		var isValid = $('#searchForm').form('validate');
		if (isValid) {
			var options = {};
			options.url = '${pageContext.request.contextPath}/mbItemStockLogController/dataGridReport';
			options.queryParams = $.serializeObject($('#searchForm'));
			dataGrid.datagrid(options);
		}
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
				<table class="table table-hover table-condensed">
						<tr>
							<th><%=TmbItemStockLog.ALIAS_ADDTIME%></th>	
							<td>
								<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbItemStockLog.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'stockLogEndTime\',{M:-1});}',maxDate:'#F{$dp.$D(\'stockLogEndTime\',{d:-1});}'})" id="stockLogStartTime" name="stockLogStartTime"/>
								<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbItemStockLog.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'stockLogStartTime\',{d:1});}',maxDate:'#F{$dp.$D(\'stockLogStartTime\',{M:1});}'})" id="stockLogEndTime" name="stockLogEndTime"/>
							</td>
							<th>发货仓库</th>
							<td>
								<jb:selectSql dataType="SQ004" name="warehouseId"></jb:selectSql>
							</td>
							<th><%=TmbItemStock.ALIAS_ITEM_NAME%></th>
							<td>
								<jb:selectGrid dataType="itemId" name="itemId"></jb:selectGrid>
							</td>
						</tr>	

				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table class="easyui-datagrid" id="dataGrid"
				   data-options="rownumbers:true,singleSelect:true,url:'',showFooter:true,method:'post'">
				<thead>
				<tr>
					<th data-options="field:'addtime',width:80" rowspan="2">时间</th>
					<th data-options="field:'refTypeName',width:40" rowspan="2">单据类型</th>
					<th data-options="field:'summary',width:100" rowspan="2">摘要</th>
					<th data-options="field:'itemName',width:100" rowspan="2">商品名称</th>
					<th colspan="3">期初结存</th>
					<th colspan="3">本期进货</th>
					<th colspan="3">本期出货</th>
					<th colspan="3">期末结存</th>
				</tr>
				<tr>
					<th data-options="field:'initQuantity',width:30,align:'right'">数量</th>
					<th data-options="field:'initPrice',width:30,align:'right'">进价</th>
					<th data-options="field:'initAmount',width:30">金额</th>

					<th data-options="field:'inQuantity',width:30,align:'right'">数量</th>
					<th data-options="field:'inPrice',width:30,align:'right',
				formatter:function(value){
				if(value==undefined)return'';
					return $.formatMoney(value);
				}">进价
					</th>
					<th data-options="field:'inAmount',width:30,align:'right',
											formatter:function(value,row){
												if(value==undefined)return'';
												return $.formatMoney(value);
											}">金额
					</th>

					<th data-options="field:'outQuantity',width:30,align:'right'">数量</th>
					<th data-options="field:'outPrice',width:30,align:'right',
				formatter:function(value){
					if(value==undefined)return'';
					return $.formatMoney(value);
				}">进价
					</th>
					<th data-options="field:'outAmount',width:30,align:'right',
				formatter:function(value,row){
					if(value==undefined)return'';
					return $.formatMoney(value);
				}">金额
					</th>

					<th data-options="field:'endQuantity',width:30,align:'right'">数量</th>
					<th data-options="field:'costPrice',width:30,align:'right',
				formatter:function(value){
				if(value==undefined)return'';
					return $.formatMoney(value);
				}">进价
					</th>
					<th data-options="field:'costAmount',width:40,align:'right',
				formatter:function(value,row){
				if(row.costPrice==undefined)return'';
					return $.formatMoney(row.endQuantity*row.costPrice);
				}">金额
					</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockLogController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockLogController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>