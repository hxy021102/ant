<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemStock" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbItemStock管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/mbItemStockController/dataGridReport',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : false,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50],
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
				width : 20
				}, {
				field : 'warehouseName',
				title : '<%=TmbItemStock.ALIAS_WAREHOUSE_NAME%>',
				width : 50
				}, {
				field : 'itemName',
				title : '<%=TmbItemStock.ALIAS_ITEM_NAME%>',
				width : 50
				},{
                field : 'averagePrice',
                title : '<%=TmbItemStock.ALIAS_AVERAGE_PRICE%>',
                width : 20,
				align:"right",
                formatter:function(value){
                    return $.formatMoney(value);
                }
                },{
			    field : 'quantity',
				title : '<%=TmbItemStock.ALIAS_QUANTITY%>',
				width : 20,
				formatter: function (value, row) {
					if (value && row.safeQuantity) {
						if (value <= row.safeQuantity) {
							return '<font color="red">'+value+'<font>';
						}
					}
					return value;
				}
			    },{
                field : 'totalPrice',
                title : '总价值',
                width : 20,
                align:"right",
                formatter:function(value){
                    return $.formatMoney(value);
                }
               },  ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
				$(this).datagrid('tooltip');
                computeTotal();
			}
		});
	});
    function computeTotal() {
        //返回当前页的所有行
        var arr = $("#dataGrid").datagrid("getRows");
        if (arr.length!=0) {
            var sumQuantity = 0;
            var sumTotalPrice=0
            //累加
            for (var i = 0; i < arr.length; i++) {
                sumQuantity += arr[i].quantity
                sumTotalPrice += arr[i].totalPrice
            }

            //新增一行显示统计信息
            $('#dataGrid').datagrid('appendRow', {
                warehouseCode:"合计",
                averagePrice:"",
                quantity: sumQuantity,
                totalPrice:sumTotalPrice,
            });
        }
    }

    function viewChart() {
        var isValid = $('#searchForm').form('validate');
        var fromObj = $.serializeObject($('#searchForm'));
        var re = /^[0-9]+.?[0-9]*$/;
        if (re.test(fromObj.warehouseId)) {
            parent.$.modalDialog({
                title : '查看图表',
                width : 900,
                height : 500,
                href : '${pageContext.request.contextPath}/mbItemStockController/viewChart?warehouseId=' + fromObj.warehouseId+'&itemIds=' + (fromObj.itemIds || ''),
            });
        }
	}

	function downloadTable(){
        var isValid = $('#searchForm').form('validate');
        if(isValid) {
		var options = dataGrid.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/mbItemStockController/downloadWithTotalPrice',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
       	 }
        });
		}
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
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 80px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<td>
							<strong><%=TmbItemStock.ALIAS_WAREHOUSE_NAME%>&nbsp;&nbsp;</strong><jb:selectGrid dataType="warehouseId" name="warehouseId" required="true"></jb:selectGrid>
						</td>
						<td>
							<strong><%=TmbItemStock.ALIAS_ITEM_NAME%>&nbsp;&nbsp;</strong><jb:selectGrid dataType="itemId" name="itemIds" multiple="true"></jb:selectGrid>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbItemStockController/viewChart')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'shape_align_bottom',plain:true" onclick="viewChart();">查看图表</a>
		</c:if>
	</div>	
</body>
</html>