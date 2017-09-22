<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierOrderItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbSupplierOrderItem管理</title>
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
<script type="text/javascript">
	var dataGrid, viewType = 'list';
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/mbSupplierOrderReportController/dataGrid',
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
			showFooter : true,
			columns : [ [ {
				field : 'itemCode',
				title : '商品代码',
				width : 50
			}, {
				field : 'itemName',
				title : '商品名称',
				width : 80,
				styler:function(value,row,index){
					if (value == '合计'){
						return 'font-weight: bold;';
					}
				}
			},  {
				field : 'quantity',
				title : '采购数量',
				width : 50
			},{
				field : 'stockInQuantity',
				title : '入库数量',
				width : 50,
				formatter:function(value, row){
					value = value || 0;
					if(value == row.quantity) return value;
					else if(value < row.quantity) return value +'<font color="#f6383a;">(-'+(row.quantity-value)+')</font>';
					else return value + '<font color="#4cd964;">(+'+(value-row.quantity)+')</font>';
				}
			}, {
				field : 'totalPrice',
				title : '入库总价',
				width : 50,
				align:"right",
				formatter:function(value){
					if(value)
						return $.formatMoney(value);
					else return '';
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

	function downloadTable(){
		var options = dataGrid.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/mbSupplierOrderReportController/download',
	        onSubmit: function(param){
				var isValid = $('#searchForm').form('validate');
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	return isValid
       	 	}
        }); 
	}
	function searchFun() {
		var isValid = $('#searchForm').form('validate');
		if(isValid)
			dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}

	function viewChart() {
		var isValid = $('#searchForm').form('validate');
		if(isValid) {
			var fromObj = $.serializeObject($('#searchForm'));
			parent.$.modalDialog({
				title : '查看图标',
				width : 900,
				height : 500,
				href : '${pageContext.request.contextPath}/mbSupplierOrderReportController/viewChart?startDate=' + fromObj.startDate + '&endDate=' + fromObj.endDate + '&itemIds=' + (fromObj.itemIds || '')
			});
		}
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<td>
							入库时间：
							<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'endDate\',{M:-1});}',maxDate:'#F{$dp.$D(\'endDate\',{d:-1});}'})" id="startDate" name="startDate"/>
							<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'startDate\',{d:1});}',maxDate:'#F{$dp.$D(\'startDate\',{M:1});}'})" id="endDate" name="endDate"/>
						</td>
						<td>
							商品名称：
							<jb:selectGrid dataType="itemId" name="itemIds" multiple="true"></jb:selectGrid>
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
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderReportController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderReportController/viewChart')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'shape_align_bottom',plain:true" onclick="viewChart();">查看图表</a>
		</c:if>
	</div>	
</body>
</html>