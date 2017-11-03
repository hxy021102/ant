<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.mobian.model.TmbSupplierOrderItem" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>DeliverOrderShop管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/view')}">
		<script type="text/javascript">
            $.canView = true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/shopOrderBillController/examinePage')}">
		<script type="text/javascript">
            $.canExamine = true;
		</script>
	</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
            url : '${pageContext.request.contextPath}/deliverShopArtificialPayController/dataGridOrderBill',
            fit : true,
            fitColumns : true,
            border : false,
            pagination : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50 ],
            sortOrder : 'desc',
            checkOnSelect : false,
            selectOnCheck : false,
            nowrap : false,
            striped : true,
            rownumbers : true,
            singleSelect : true,
            columns : [ [ {
                field : 'id',
                title : '账单ID',
                width : 30,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
            }, {
                field : 'addtime',
                title : '创建时间',
                width : 50,
            },{
                field : 'shopName',
                title : '名店名称',
                width : 80
            }, {
                field : 'statusName',
                title : '状态',
                width : 30
            },{
                field : 'startDate',
                title : '开始时间',
                width : 50
            },{
                field : 'endDate',
                title : '结束时间',
                width : 50
            },{
                field : 'amount',
                title : '总金额',
                align:"right",
                width : 20,
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
            }] ],
            toolbar : '#toolbar',
            onLoadSuccess : function() {
                parent.$.messager.progress('close');
                $(this).datagrid('tooltip');
            }
        });
	});

	function viewFun(id) {
        var href = '${pageContext.request.contextPath}/deliverShopArtificialPayController/viewBill?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '账单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
        });
	}
    $(function() {
        $('#searchForm table').show();
	});
	function searchFun() {
        var options = {};
        options.url = '${pageContext.request.contextPath}/deliverShopArtificialPayController/dataGridOrderBill',
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
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 80px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th style="width: 50px">门店名称</th>
						<td>
							<jb:selectGrid dataType="shopId" name="shopId"></jb:selectGrid>
						</td>
						<th style="width: 50px">状态</th>
						<td>
							<jb:select dataType="BAS" name="status"></jb:select>
						</td>
						<th>创建时间</th>
						<td>
						<td>
							<input type="text" class="span2 easyui-validatebox" data-options="required:false" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'endDate\',{M:-1});}',maxDate:'#F{$dp.$D(\'endDate\',{d:-1});}'})" id="startDate" name="startDate"/>
							至	<input type="text" class="span2 easyui-validatebox" data-options="required:false" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'startDate\',{d:1});}',maxDate:'#F{$dp.$D(\'startDate\',{M:1});}'})" id="endDate" name="endDate"/>
						</td>

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
	</div>
</body>
</html>