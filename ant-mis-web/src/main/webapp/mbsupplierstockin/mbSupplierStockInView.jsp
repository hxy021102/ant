<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbSupplierStockIn" %>
<%@ page import="com.mobian.model.TmbSupplierStockInItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
    var dataGrid;
    $(function () {
        parent.$.messager.progress('close');
        dataGrid = $('#dataGrid').datagrid({
            url: '${pageContext.request.contextPath}/mbSupplierStockInItemController/dataGrid?supplierStockInId=' +${mbSupplierStockIn.id},
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
                    field: 'productName',
                    title: '<%=TmbSupplierStockInItem.ALIAS_ITEM_NAME%>',
                    width: 100
                }, {
                    field: 'quantity',
                    title: '<%=TmbSupplierStockInItem.ALIAS_QUANTITY%>',
                    width: 100
                }
            ]]
        })
    })

    function printStockIn() {
//        $('.print').linkbutton("disable");
        $('#printIframe').attr("src",'${pageContext.request.contextPath}/mbSupplierStockInController/printStockIn?id=${mbSupplierStockIn.id}');
        <%--window.open('${pageContext.request.contextPath}/mbSupplierStockInController/stockInPrint?id=${mbSupplierStockIn.id}');--%>
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 250px">
        <table class="table table-hover table-condensed">
            <tr>
                <th><%=TmbSupplierStockIn.ALIAS_ID%></th>

                <td>
                    ${mbSupplierStockIn.id}
                    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierStockInController/printStockIn')}">
                        <a href="javascript:void(0);" class="easyui-linkbutton print" onclick="printStockIn()">打印</a>
                    </c:if>
                </td>
            </tr>
            <tr>
                <th><%=TmbSupplierStockIn.ALIAS_ADDTIME%>
                </th>
                <td>
                    ${mbSupplierStockIn.addtime}
                </td>
                <th><%=TmbSupplierStockIn.ALIAS_SIGN_DATE%>
                </th>
                <td>
                    ${mbSupplierStockIn.signDate}
                </td>
            </tr>
            <tr>
                <th><%=TmbSupplierStockIn.ALIAS_SIGN_PEOPLE_ID%>
                </th>
                <td>
                    ${mbSupplierStockIn.signPeopleName}
                </td>
                <th><%=TmbSupplierStockIn.ALIAS_DRIVER_LOGIN_ID%>
                </th>
                <td>
                    ${mbSupplierStockIn.driverName}
                </td>
            </tr>
            <%--<tr>
                <th><%=TmbSupplierStockIn.ALIAS_RECEIVE_URL%></th>
                <td>
                    ${mbSupplierStockIn.receiveUrl}
                </td>
                <th><%=TmbSupplierStockIn.ALIAS_LOGIN_ID%></th>
                <td>
                    ${mbSupplierStockIn.loginId}
                </td>
            </tr>		--%>
            <tr>
                <th><%=TmbSupplierStockIn.ALIAS_PAY_STATUS%>
                </th>
                <td>
                    ${mbSupplierStockIn.payStatusName}
                </td>
                <th><%=TmbSupplierStockIn.ALIAS_INVOICE_STATUS%>
                </th>
                <td>
                    ${mbSupplierStockIn.invoiceStatusName}
                </td>
            </tr>
            <tr>
                <th>支付人</th>
                <td>${payLoginName}</td>
                <th>开票人</th>
                <td>${invoiceLoginName}</td>
            </tr>
            <tr>
                <th>支付备注</th>
                <td>${payRemark}</td>
                <th>开票备注</th>
                <td>${invoiceRemark}</td>
            </tr>
            <tr>
                <th><%=TmbSupplierStockIn.ALIAS_WAREHOUSE_NAME%>
                </th>
                <td >
                    ${mbSupplierStockIn.warehouseName}
                </td>
                <%--<th><%=TmbSupplierStockIn.ALIAS_STATUS%></th>--%>
                <%--<td>--%>
                <%--${mbSupplierStockIn.status}--%>
                <%--</td>--%>
                <th><%=TmbSupplierStockIn.ALIAS_SUPPLIER_NAME%>
                </th>
                <td>
                    ${mbSupplierStockIn.supplierName}
                </td>
            </tr>
            <tr>

                <th>备注</th>
                <td colspan="3">
                    ${mbSupplierStockIn.remark}
                </td>
            </tr>
        </table>

    </div>
    <div data-options="region:'center',border:false">
        <table id="dataGrid"></table>
    </div>
    <iframe id="printIframe" style="display: none;"></iframe>

</div>
