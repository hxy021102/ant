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

<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
            url : '',
            fit : true,
            fitColumns : true,
            border : false,
            pagination : false,
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
                title : '订单ID',
                width : 30,
            }, {
                field : 'addtime',
                title : '创建时间',
                width : 50,
            },{
                field : 'shopId',
                title : '门店ID',
                width : 30,
            },{
                field : 'shopName',
                title : '名店名称',
                width : 80
            }, {
                field : 'statusName',
                title : '状态',
                width : 50
            },{
                field : 'amount',
                title : '总金额',
                align:"right",
                width : 50,
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
            }] ],
            toolbar : '#toolbar',
            onLoadSuccess : function() {
                $(this).datagrid('tooltip');
            }
        });
	});
    function addShopOrderBill() {
        var rows = $('#dataGrid').datagrid('getRows');
        var totalAmount = 0;
        for (var i = 0; i < rows.length; i++) {
            totalAmount += rows[i].amount;
        }
        var startDate = $.serializeObject($('#searchForm')).startDate;
        var endDate = $.serializeObject($('#searchForm')).endDate;
        var shopId = $.serializeObject($('#searchForm')).shopId;
        parent.$.modalDialog({
            title: '创建账单',
            width: 780,
            height: 230,
            href: '${pageContext.request.contextPath}/deliverShopArtificialPayController/addShopOrderBillPage?shopId=' + shopId + "&startDate=" + startDate + "&endDate=" + endDate + "&totalAmount=" + totalAmount,
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            }]
        });
    }


    $(function() {
        $('#searchForm table').show();
        parent.$.messager.progress('close');
	});
    function searchFun() {
        var isValid = $('#searchForm').form('validate');
        if (isValid) {
            var options = {};
            options.url = '${pageContext.request.contextPath}/deliverShopArtificialPayController/dataGrid',
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
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 80px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th style="width: 50px">门店名称</th>
						<td>
							<jb:selectGrid dataType="shopId" name="shopId" required="true"></jb:selectGrid>
						</td>
						<th>订单时间</th>
						<td>
								<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'endDate\',{M:-1});}',maxDate:'#F{$dp.$D(\'endDate\',{d:-1});}'})" id="startDate" name="startDate"/>
							至	<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'startDate\',{d:1});}',maxDate:'#F{$dp.$D(\'startDate\',{M:1});}'})" id="endDate" name="endDate"/>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverShopArtificialPayController/addShopOrderBillPage')}">
			<a onclick="addShopOrderBill();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">创建账单</a>
		</c:if>
	</div>

</body>
</html>