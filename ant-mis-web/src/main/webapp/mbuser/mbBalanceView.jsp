<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbBalanceLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
<title>余额管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList,'/mbBalanceLogController/edit' )}">
    <script type="text/javascript">
        $.canEdit = true;
    </script>
</c:if>
<style>
    .l-btn-text {
        margin:0 10px;
    }
    .l-btn-selected, .l-btn-selected:hover {
        background : #f6383a;
    }

</style>
<script type="text/javascript">
    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${pageContext.request.contextPath}/mbBalanceLogController/dataGrid?balanceId=${mbBalance.id}',
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
                title : '<%=TmbBalanceLog.ALIAS_TIME%>',
                width : 100
            }, {
                field : 'amount',
                title : '<%=TmbBalanceLog.ALIAS_AMOUNT%>',
                width : 50,
                align:'right',
                formatter:function(value){
                    return $.formatMoney(value);
                }
            }, {
                field : 'refTypeName',
                title : '<%=TmbBalanceLog.ALIAS_REF_TYPE%>',
                width : 100
            }, {
                field : 'refId',
                title : '<%=TmbBalanceLog.ALIAS_REF_ID%>',
                width : 80
            }, {
                field : 'reason',
                title : '<%=TmbBalanceLog.ALIAS_REASON%>',
                width : 100
            }, {
                field : 'remark',
                title : '<%=TmbBalanceLog.ALIAS_REMARK%>',
                width : 100
            }, {
                field: 'action',
                title: '操作',
                width: 50,
                hidden: $.canEdit ? false : true,
                formatter: function (value, row, index) {
                    var str = '';
                    if ($.canEdit) {
                        str = '<a onclick="editShow(' + row.id + ',' + row.isShow + ');" href="javascript:void(0);" class="switch" is-show="'+row.isShow+'">'+(row.isShow ? '点击影藏' : '点击显示')+'</a>';
                    }

                    return str;
                }
            }]],
            toolbar : '#toolbar',
            onLoadSuccess: function () {
                $('.switch').each(function(){
                    var isShow = $(this).attr('is-show');
                    $(this).linkbutton({
                        toggle:true,
                        selected : isShow == 'true' ? false : true
                    });
                });
            }
        });
    });
    function  addShopMoney() {
        parent.$.modalDialog({
            title : '添加数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbRechargeLogController/addShopMoneyPage?shopId='+${mbBalance.refId},
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            } ]
        });
    }
    function  addShopCashCharge() {
        parent.$.modalDialog({
            title : '添加数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbRechargeLogController/addShopCashChargePage?shopId='+${mbBalance.refId},
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            } ]
        });
    }
    function editShow(id, isShow) {
        isShow = isShow ? 0 : 1;
        $.post('${pageContext.request.contextPath}/mbBalanceLogController/edit', {
            id: id, isShow: isShow
        }, function (result) {
            if (result.success) {
                dataGrid.datagrid('reload');
            }
        }, 'JSON');
    }
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="dataGrid"></table>
    </div>
</div>
<div id="toolbar" style="display: none;">
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/addShopMoneyPage')}">
        <a onclick="addShopMoney();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">充值</a>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/addShopCashChargePage')}">
        <a onclick="addShopCashCharge();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">现金充值</a>
    </c:if>
</div>
</body>
</html>