<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbStockOut" %>
<%@ page import="com.mobian.model.TmbStockOutItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<script>
    var dataGrid;
    $(function () {
        parent.$.messager.progress('close');
        dataGrid = $('#dataGrid').datagrid({
            url: '${pageContext.request.contextPath}/mbStockOutItemController/dataGrid?mbStockOutId='+${mbStockOut.id},
            fit: true,
            fitColumns: true,
            border: false,
            pagination: false,
            idField: 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            sortName: 'id',
            sortOrder: 'desc',
            checkOnSelect: false,
            selectOnCheck: false,
            nowrap: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            columns: [[
                {
                    field: 'itemName',
                    title: '<%=TmbStockOutItem.ALIAS_ITEM_NAME%>',
                    width: 100
                }, {
                    field: 'quantity',
                    title: '<%=TmbStockOutItem.ALIAS_QUANTITY%>',
                    width: 100
                }
            ]]
        })
    })
</script>


<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'north',border:false" style="height: 150px">
		<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TmbStockOut.ALIAS_STOCK_OUT_PEOPLE_ID%></th>	
					<td>
						${mbStockOut.stockOutPeopleName}
					</td>
					<th><%=TmbStockOut.ALIAS_LOGIN_ID%></th>
					<td>
						${mbStockOut.loginName}
					</td>
				</tr>
			    <tr>
					<th><%=TmbStockOut.ALIAS_WAREHOUSE_ID%>
					</th>
					<td >
						${mbStockOut.warehouseName}
					</td>
					<th><%=TmbStockOut.ALIAS_STOCK_OUT_TIME%></th>
					<td colspan="4">
						${mbStockOut.stockOutTime}
					</td>

				</tr>
			    <tr>
				<th><%=TmbStockOut.ALIAS_STOCK_OUT_TYPE%></th>
					<td colspan="4">
						${mbStockOut.stockOutTypeName}
					</td>

			    </tr>
				<tr>	
					<th><%=TmbStockOut.ALIAS_REMARK%></th>	
					<td colspan="4">
						${mbStockOut.remark}							
					</td>							
				</tr>		
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
</div>