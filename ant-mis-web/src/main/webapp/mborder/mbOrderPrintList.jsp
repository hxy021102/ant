<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbOrder管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
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
			sortName : 'deliveryRequireTime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
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
                width : 80
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
				width : 30
				}, {
                field : 'deliveryStatusName',
                title : '<%=TmbOrder.ALIAS_DELIVERY_STATUS%>',
                width : 30
                }, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 40
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

	function viewOrder(id) {
        var href = '${pageContext.request.contextPath}/mbOrderController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '订单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>',
            closable : true
        });
	}

	function searchFun() {
		var options = {};
		options.url = '${pageContext.request.contextPath}/mbOrderController/dataGridPrintList';
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
	</div>	
</body>
</html>