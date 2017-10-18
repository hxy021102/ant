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
                field : 'updatetime',
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
                width : 80,
                formatter : function (value, row, index) {
                    if(row.refType=="BT016" || row.refType =="BT017" || row.refType == "BT018"  || row.refType=="BT005" || row.refType=="BT006" || row.refType=="BT002") {
                        return '<a onclick="viewOrder('+ row.refId +',\''+row.refType+'\')">' + row.refId + '</a>';
                    }
                    else {
                        return row.refId
                    }

                }
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
                    if ($.canEdit&& ${readOnly!=true}) {
                        str = '<a onclick="editShow(' + row.id + ',' + row.isShow + ');" href="javascript:void(0);" class="switch" is-show="'+row.isShow+'">'+(row.isShow ? '点击影藏' : '点击显示')+'</a>';
                    }

                    return str;
                }
            }]],
            toolbar : '#toolbar',
            onLoadSuccess: function () {
                $('#searchForm table').show();
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
    function viewOrder(id, type) {
        var href = '${pageContext.request.contextPath}/mbOrderController/view?id=' + id +"&type=" +type;
        parent.$("#index_tabs").tabs('add', {
            title : '订单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
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
    function searchFun() {
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }


    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
    }
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
        <form id="searchForm">
            <table class="table table-hover table-condensed" style="display: none;">
                <tr>
                    <th style="width: 50px;">时间查询</th>
                    <td>
                        <input type="text" class="span2" id="updatetimeBegin" name="updatetimeBegin"

                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'updatetimeEnd\',{d:-1});}'})"
                                />
                        <input type="text" class="span2" id="updatetimeEnd" name="updatetimeEnd"

                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'updatetimeBegin\',{d:1});}'})"
                                />
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
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true"
       onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/addShopMoneyPage') and readOnly!=true}">
        <a onclick="addShopMoney();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">充值</a>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/addShopCashChargePage') and readOnly!=true}">
        <a onclick="addShopCashCharge();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">现金充值</a>
    </c:if>
</div>
</body>
</html>