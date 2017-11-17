<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
    <title>DeliverOrder管理</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/driverAccountController/view')}">
    <script type="text/javascript">
        $.canViewAccount = true;
    </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/view')}">
    <script type="text/javascript">
        $.canViewDeliverOrder = true;
    </script>
    </c:if>
<script type="text/javascript">
    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${pageContext.request.contextPath}/driverOrderPayController/dataGrid?driverOrderShopBillId='+${driverOrderShopBill.id},
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
            },{
                field : 'addtime',
                title : '添加时间',
                width : 70
            },{
                field : 'userName',
                title : '骑手账号',
                width : 30,
                formatter : function (value, row) {
                    if ($.canViewAccount && value != undefined)
                        return '<a onclick="viewAccount(' + row.driverAccountId + ')">' + row.userName + '</a>';
                    return value == null ? "" : value;

                }
            },/*{
                field : 'driverOrderShopId',
                title : '骑手运单ID',
                width : 30
            },*/ {
                field : 'deliverOrderShopId',
                title : '运单ID',
                width : 30
            }, {
                field : 'driverOrderShopBillId',
                title : '账单ID',
                width : 30
            }, {
                field : 'amount',
                title : '运费',
                width : 30
            }, {
                field : 'status',
                title : '支付状态',
                width : 40
            }, {
                field : 'payWay',
                title : '支付方式',
                width : 40
            }] ],
            toolbar : '#toolbar',
            onLoadSuccess : function() {
                $('#searchForm table').show();
                parent.$.messager.progress('close');

                $(this).datagrid('tooltip');
            }
        });
    });


    function viewAccount(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '查看数据',
            width : 780,
            height : 400,
            href : '${pageContext.request.contextPath}/driverAccountController/view?id=' + id
        });
    }
    function examineFun() {
        parent.$.modalDialog({
            title : '审核并结算账单',
            width : 780,
            height : 260,
            href : '${pageContext.request.contextPath}/driverOrderShopBillController/examinePage?id=' + ${driverOrderShopBill.id},
            buttons: [{
                text: '通过',
                handler: function () {
                    parent.$.modalDialog.opener_url = window.location;
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=handleStatus]").val("DHS02");
                    f.submit();
                }
            },
                {
                    text: '拒绝',
                    handler: function () {
                        parent.$.modalDialog.opener_url = window.location;
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name=handleStatus]").val("DHS03");
                        f.submit();
                    }
                }
            ]
        });
    }
</script>
</head>
 <div class="easyui-layout" data-options="fit : true,border:false">
        <div data-options="region:'north',title:'基本信息',border:false" style="height: 180px; overflow: hidden;">
            <table class="table">
            <tr>
                <th>账单ID</th>
                <td>
                    ${driverOrderShopBill.id}
                        <c:if test="${fn:contains(sessionInfo.resourceList, '/driverOrderShopBillController/examinePage') and driverOrderShopBill.handleStatus=='DHS01' }">
                            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="examineFun();">审核并支付</a>
                        </c:if>
                </td>
                <th>添加时间</th>
                <td>
                    <fmt:formatDate value="${driverOrderShopBill.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <th>更新时间</th>
                <td>
                    <fmt:formatDate value="${driverOrderShopBill.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <th>骑手账户</th>
                <td>
                    ${driverOrderShopBill.driverAccountId}
                </td>
            </tr>
            <tr>
                <th>开始时间</th>
                <td>
                    <fmt:formatDate value="${driverOrderShopBill.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <th>结束时间</th>
                <td>
                    <fmt:formatDate value="${driverOrderShopBill.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <th>账单金额</th>
                <td>
                    ${driverOrderShopBill.amount/100.0}
                </td>
                <th>支付方式</th>
                <td>
                    ${driverOrderShopBill.payWayName}
                </td>
            </tr>
            <tr>
                <th>审核状态</th>
                <td >
                    ${driverOrderShopBill.handleStatusName}
                </td>
                <th>审核人</th>
                <td colspan="5">
                    ${driverOrderShopBill.handleLoginName}
                </td>
            </tr>
            <tr>
                <th>审核备注</th>
                <td colspan="7">
                    ${driverOrderShopBill.handleRemark}
                </td>
            </tr>
        </table>
    </div>
     <div data-options="region:'center',border:false">
         <div id="bill_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
             <div title="骑手运单">
                 <table id="dataGrid"></table>
             </div>
         </div>
     </div>
 </div>
</body>
</html>