<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbUser" %>
<%@ page import="com.mobian.model.TmbUserAddress" %>
<%@ page import="com.mobian.model.TmbUserInvoice" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>MbItem管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
    $(function() {
        var gridMap = {
            handle : function(obj, clallback){
                if (obj.grid == null) {
                    obj.grid = clallback();
                } else {
                    obj.grid.datagrid('reload');
                }
            },
            0: {
                invoke: function () {
                    gridMap.handle(this,loadAddressDataGrid);
                }, grid: null
            }, 1: {
                invoke: function () {
                    gridMap.handle(this,loadInvoiceDataGrid);
                }, grid: null
            }
        };

        $('#user_view_tabs').tabs({
            onSelect: function (title, index) {
                gridMap[index].invoke();
            }
        });
    });

    function loadAddressDataGrid() {
        return $('#addressDataGrid').datagrid({
            url: '${pageContext.request.contextPath}/mbUserAddressController/dataGrid?userId=${mbUser.id}',
            fitColumns: true,
            border: false,
            fit:true,
            pagination: true,
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
            columns: [[{
                field: 'id',
                title: '编号',
                width: 150,
                hidden: true
            }, {
                field: 'userName',
                title: '<%=TmbUserAddress.ALIAS_USER_NAME%>',
                width: 50
            }, {
                field: 'telNumber',
                title: '<%=TmbUserAddress.ALIAS_TEL_NUMBER%>',
                width: 50
            }, {
                field: 'address',
                title: '<%=TmbUserAddress.ALIAS_ADDRESS%>',
                width: 200
            }, {
                field: 'defaultAddress',
                title: '<%=TmbUserAddress.ALIAS_DEFAULT_ADDRESS%>',
                width: 100,
                formatter: function (value, row, index) {
                    if (row.defaultAddress == true) {
                        return "是";
                    }
                    return "否";
                }
            }]]
        });
    }

    function loadInvoiceDataGrid() {
        return $('#invoiceDataGrid').datagrid({
            url : '${pageContext.request.contextPath}/mbUserInvoiceController/dataGrid?userId=${mbUser.id}',
            fitColumns : true,
            border : false,
            fit:true,
            pagination : true,
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
            columns : [ [ {
                field : 'id',
                title : '编号',
                width : 150,
                hidden : true
            }, {
                field : 'companyName',
                title : '<%=TmbUserInvoice.ALIAS_COMPANY_NAME%>',
                width : 100
            }, {
                field : 'companyTfn',
                title : '<%=TmbUserInvoice.ALIAS_COMPANY_TFN%>',
                width : 100
            }, {
                field : 'registerAddress',
                title : '<%=TmbUserInvoice.ALIAS_REGISTER_ADDRESS%>',
                width : 200
            }, {
                field : 'registerPhone',
                title : '<%=TmbUserInvoice.ALIAS_REGISTER_PHONE%>',
                width : 100
            }, {
                field : 'bankName',
                title : '<%=TmbUserInvoice.ALIAS_BANK_NAME%>',
                width : 100
            }, {
                field : 'bankNumber',
                title : '<%=TmbUserInvoice.ALIAS_BANK_NUMBER%>',
                width : 100
            }, {
                field : 'invoiceUseName',
                title : '<%=TmbUserInvoice.ALIAS_INVOICE_USE%>',
                width : 100
            }]]
        });
    }
</script>
</head>
<body>

    <div class="easyui-layout" data-options="fit : true,border:false">
        <div data-options="region:'north',title:'基本信息',border:false" style="height: 150px; overflow: hidden;">
            <table class="table">
                <tr>
                    <th><%=TmbUser.ALIAS_USER_ID%>：</th>
                    <td>
                        ${mbUser.id}
                    </td>
                    <th><%=TmbUser.ALIAS_USER_NAME%>：</th>
                    <td>
                        ${mbUser.userName}
                    </td>
                    <th><%=TmbUser.ALIAS_NICK_NAME%>：</th>
                    <td>
                        ${mbUser.nickName}
                    </td>
                    <th><%=TmbUser.ALIAS_ICON%>：</th>
                    <td rowspan="3">
                        <img src="${mbUser.icon}" width="80" height="80"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbUser.ALIAS_PHONE%>：</th>
                    <td>
                        ${mbUser.phone}
                    </td>
                    <th><%=TmbUser.ALIAS_SHOP_ID%>：</th>
                    <td>
                        ${mbUser.shopId}
                    </td>
                    <th><%=TmbUser.ALIAS_SHOP_NAME%>：</th>
                    <td>
                        ${mbUser.shopName}
                    </td>
                </tr>
                <tr>
                    <th><%=TmbUser.ALIAS_REF_TYPE%>：</th>
                    <td>
                        ${mbUser.refType}
                    </td>
                    <th><%=TmbUser.ALIAS_REF_ID%>：</th>
                    <td>
                        ${mbUser.refId}
                    </td>
                    <th><%=TmbUser.ALIAS_SEX%>：</th>
                    <td>
                        <c:if test="${mbUser.sex == 1}">
                            男
                        </c:if>
                        <c:if test="${mbUser.sex == 2}">
                            女
                        </c:if>
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <div id="user_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
                <div title="收件地址">
                    <table id="addressDataGrid"></table>
                </div>
                <div title="发票信息">
                    <table id="invoiceDataGrid"></table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>