<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
	<title>MbUser管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractController/searchPrice')}">
		<script type="text/javascript">
            $.canSearch = true;
		</script>
	</c:if>
	<script type="text/javascript">
        var dataGrid;
        $(function () {
            dataGrid = $('#dataGrid').datagrid({
                url : '',
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
                nowrap : true,
                striped : true,
                rownumbers : true,
                singleSelect : true,
                columns : [ [ {
                    field : 'id',
                    title : '编号',
                    width : 150,
                    hidden : true
                }, {
                    field : 'code',
                    title : '<%=TmbSupplierContract.ALIAS_CODE%>',
                    width : 35,
                },  {
                    field : 'supplierName',
                    title : '<%=TmbSupplierContract.ALIAS_SUPPLIER_NAME%>',
                    width : 50,
                }, {
                    field : 'expiryDateStart',
                    title : '<%=TmbSupplierContract.ALIAS_EXPIRY_DATE_START%>',
                    width : 60
                }, {
                    field : 'expiryDateEnd',
                    title : '<%=TmbSupplierContract.ALIAS_EXPIRY_DATE_END%>',
                    width : 60
                }, {
                    field : 'price',
                    title : '<%=TmbContractItem.ALIAS_PRICE%>',
                    width : 20,
                    align:'right',
                    formatter:function(value){
                        return $.formatMoney(value);
                    }
                },{
                    field : 'action',
                    title : '操作',
                    width : 30,
                    formatter : function(value, row, index) {
                        var str = '';
                        str += '&nbsp;';
                        if ( $.canSearch) {
                            str += $.formatString('<img onclick="addOrder(\'{0}\');" src="{1}" title="下单"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/book_previous.png');
                        }
                        return str;
                    }
                } ] ],
                toolbar: '#toolbar',

            });
            $('#orderForm').show();
            parent.$.messager.progress('close');
        });
        function addOrder(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title : '添加数据',
                width : 780,
                height : 500,
                href : '${pageContext.request.contextPath}/mbSupplierOrderController/addPage?id=' + id,
            });
        }
        function searchFun() {
            var isValid = $('#searchForm').form('validate');
            if (isValid) {
                var options = {};
                options.url = '${pageContext.request.contextPath}/mbSupplierContractItemController/dataGrid';
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
<div class="easyui-layout" data-options="fit : true,border : false"     >
	<div data-options="region:'north',border:false" style="height: 500px; overflow: hidden;">
		<div class="easyui-layout" data-options="fit : true,border : false">
			<div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;">
				<form id="searchForm">
					<input type="hidden" name="auditStatus" value="AS02"/>
					<table class="table table-hover table-condensed">
						<tr>
							<td>
								<strong><%=TmbSupplierContractItem.ALIAS_ITEM_NAME%></strong>&nbsp;&nbsp;<jb:selectGrid dataType="itemId" name="itemId"  required="true"></jb:selectGrid>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<table id="dataGrid"></table>
			</div>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>
</div>
</body>
</html>