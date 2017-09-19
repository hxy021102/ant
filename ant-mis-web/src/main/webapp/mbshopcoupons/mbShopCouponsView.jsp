<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbShopCoupons" %>
<%@ page import="com.mobian.model.TmbShopCouponsLog" %>
<%@ page import="com.mobian.model.TmbCoupons" %>
<%@ page import="com.mobian.model.TmbShop" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
//    $(function () {
//
//    });
    var dataGrid;
    $(function() {
        parent.$.messager.progress('close');
        dataGrid = $('#shopCouponsLogTable').datagrid({
            url : '${pageContext.request.contextPath}/mbShopCouponsLogController/dataGridMbShopCouponsLogView?shopCouponsId='+${mbShopCoupons.id},
//            data: '',
            fit : true,
            fitColumns : true,
            border : false,
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
                field : 'updatetime',
                title : '<%=TmbShopCouponsLog.ALIAS_UPDATETIME%>',
                width : 60
            }, {
                field : 'couponsName',
                title : '<%=TmbCoupons.ALIAS_NAME%>',
                width : 50,
                hidden : true
            }, {
                field : 'quantityUsed',
                title : '<%=TmbShopCouponsLog.ALIAS_QUANTITY_USED%>',
                width : 25,
                align : 'right',
                formatter : function (value) {
                    if (value == null) return 0;
                    return value;
                }
            }, {
                field : 'quantityTotal',
                title : '<%=TmbShopCouponsLog.ALIAS_QUANTITY_TOTAL%>',
                width : 25,
                align : 'right',
                formatter : function (value) {
                    if (value == null) return 0;
                    return value;
                }
            }, {
                field : 'shopCouponsStatusName',
                title : '<%=TmbShopCouponsLog.ALIAS_SHOP_COUPONS_STATUS%>',
                width : 25,
                formatter : function (value) {
                    if (value == null) return '--';
                    return value;
                }
            }, {

                <%--field : 'refId',--%>
                <%--title : '<%=TmbShopCouponsLog.ALIAS_REF_ID%>',--%>
                <%--width : 50--%>
                <%--}, {--%>
                <%--field : 'refTypeName',--%>
                <%--title : '<%=TmbShopCouponsLog.ALIAS_REF_TYPE%>',--%>
                <%--width : 50--%>
                <%--}, {--%>
                field: 'reason',
                title: '<%=TmbShopCouponsLog.ALIAS_REASON%>',
                width: 120
            }, {
                field : 'loginName',
                title : '<%=TmbShopCouponsLog.ALIAS_LOGIN_NAME%>',
                width : 30

//            }
//            ,
                <%--{--%>
                <%--field : 'remark',--%>
                <%--title : '<%=TmbShopCouponsLog.ALIAS_REMARK%>',--%>
                <%--width : 50--%>
            }]]
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table class="table table-hover table-condensed">
            <tr>
                <th><%=TmbCoupons.ALIAS_NAME%>
                </th>
                <td>
                    ${mbShopCoupons.couponsName}
                </td>
                <th><%=TmbShopCoupons.ALIAS_STATUS%>
                </th>
                <td>
                    ${mbShopCoupons.statusName}
                </td>
            </tr>
            <tr>
                <th><%=TmbShopCoupons.ALIAS_QUANTITY_TOTAL%>
                </th>
                <td>
                    ${mbShopCoupons.quantityTotal}
                </td>
                <th><%=TmbShopCoupons.ALIAS_QUANTITY_USED%>
                </th>
                <td>
                    ${mbShopCoupons.quantityUsed}
                </td>
            </tr>
            <tr>
                <th><%=TmbShopCoupons.ALIAS_REMARK%>
                </th>
                <td colspan="3">
                    ${mbShopCoupons.remark}
                </td>
            </tr>
        </table>
        <div style="overflow: auto;height: 357px">
            <table id="shopCouponsLogTable" title="门店券流水"></table>
        </div>
    </div>
</div>