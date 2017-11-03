<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItemStockLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
    $(function() {
        var dataGrid = $('#dataGrid').datagrid({
            url : '${pageContext.request.contextPath}/mbItemStockLogController/dataGrid?itemStockId=${itemStockId}',
            fitColumns : true,
            fit : true,
            border : false,
            pagination : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50 ],
            sortName : 'addtime',
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
                field : 'addtime',
                title : '<%=TmbItemStockLog.ALIAS_ADDTIME%>',
                width : 70
            }, {
                field : 'quantity',
				title : '<%=TmbItemStockLog.ALIAS_QUANTITY%>',
				width : 30
			}, {
                field : 'loginName',
				title : '<%=TmbItemStockLog.ALIAS_LOGIN_NAME%>',
				width : 50
			}, {
                field : 'logTypeName',
				title : '<%=TmbItemStockLog.ALIAS_LOG_TYPE%>',
				width : 40
			}, {
                field : 'endQuantity',
                title : '期末库存',
                width : 40
            }, {
                field : 'reason',
				title : '<%=TmbItemStockLog.ALIAS_REASON%>',
				width : 100
			}, {
                field : 'remark',
				title : '<%=TmbItemStockLog.ALIAS_REMARK%>',
				width : 50
			}]],
            onLoadSuccess : function() {
                parent.$.messager.progress('close');
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
</div>