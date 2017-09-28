<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<title>MbItem管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderItemController/delete') and mbOrder.deliveryStatus=='DS01' and mbOrder.status=='OD10'}">
        <script type="text/javascript">
            $.canDelete = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderCallbackItemController/add') and mbOrder.deliveryStatus=='DS30' and mbOrder.status=='OD35'}">
      <script type="text/javascript">
          $.mbOrderCallbackItemCanDelete = true;
      </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderRefundItemController/add') and mbOrder.deliveryStatus=='DS30' and mbOrder.status=='OD35'}">
        <script type="text/javascript">
            $.mbOrderRefundItemCanDelete = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderInvoiceController/view')}">
        <script type="text/javascript">
            $.viewOrderInvoice = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderLogController/delete')}">
        <script type="text/javascript">
            $.canDeleteOrderLog = true;
        </script>
    </c:if>

<script type="text/javascript">
    function doInvoiceOrder() {
        parent.$.modalDialog({
            title: '订单开票',
            width: 780,
            height: 200,
            href: '${pageContext.request.contextPath}/mbOrderController/editInvoicePage?id='+${mbOrder.id},
            buttons: [{
                text: '提交',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            }]
        });
    }
    function auditOrder() {
        parent.$.modalDialog({
            title: '汇款审核',
            width: 780,
            height: 200,
            href: '${pageContext.request.contextPath}/mbOrderController/updateAuditPage?id=${mbOrder.id}',
            buttons: [{
                text: '通过',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=status]").val("OD10");
                    var payCode = f.find("input[name=payCode]");
                    payCode.addClass('easyui-validatebox').attr("data-options",'required:true');
                    $.parser.parse(payCode.parent());
                    setTimeout(function(){
                        payCode.focus();
                    },10);
                    f.submit();
                }
            },
                {
                    text: '拒绝',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name=status]").val("OD06");
                        var payCode = f.find("input[name=payCode]");
                        payCode.removeClass('easyui-validatebox validatebox-text validatebox-invalid').removeAttr("data-options");
                        $.parser.parse(payCode.parent());
                        f.submit();
                    }
                }
            ]
        });
    }

   function completeDelivery(id){
		parent.$.modalDialog({
			title: '配送确认',
			width: 780,
			height: 200,
			href: '${pageContext.request.contextPath}/mbOrderController/completeDelivery?id=' + ${mbOrder.id} ,
			buttons: [{
				text: '确定',
				handler: function () {
					parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.find("input[name=deliveryStatus]").val("DS30");
                    f.find("input[name=status]").val("OD35");
                    f.submit();
				}
			}
			]
		});
    }

    function updateAddPayment() {
        parent.$.modalDialog({
            title: '余额付款',
            width: 780,
            height: 200,
            href: '${pageContext.request.contextPath}/mbOrderController/updateAddPaymentPage?id=${mbOrder.id}',
            buttons: [{
                text: '确认付款',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            }
            ]
        });
    }

    function printOrder() {
        $('.print').linkbutton("disable");
        $('#printIframe').attr("src",'${pageContext.request.contextPath}/mbOrderController/printOrderView?id=${mbOrder.id}');
        <%--window.open('${pageContext.request.contextPath}/mbOrderController/printOrderView?id=${mbOrder.id}');--%>
    }

    /**
     *   打印完成
     */
    function printComplete(orderId) {
        if(!orderId) return;
        if(orderId != ${mbOrder.id}) {
            parent.$.messager.alert('提示', '订单号有误，与当前订单号不一致！', 'info', function(){
                $('#scanInp').val('').focus();
            });
            return;
        }

        parent.$.messager.progress({
            title : '提示',
            text : '数据处理中，请稍后....'
        });
        $.post('${pageContext.request.contextPath}/mbOrderController/editOrderPrint', {
            id : orderId
        }, function(result) {
            if (result.success) {
                window.location.reload();
            } else {
                parent.$.messager.alert('提示', result.msg, 'info', function(){
                    $('#scanInp').val('').focus();
                });
            }
            parent.$.messager.progress('close');
        }, 'JSON');
    }

    function confirmPrintComplete() {
        parent.$.messager.confirm('询问', "请确认是否已打印，并且没有重复打印", function (b) {
            if (b) {
                printComplete();
            }
        });
    }

    function deliveryItem() {
        parent.$.modalDialog({
            title: '发货',
            width: 780,
            height: 230,
            href: '${pageContext.request.contextPath}/mbOrderController/deliveryItemPage?id=${mbOrder.id}',
            buttons: [{
                text: '发货',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=status]").val("OD20");
                    f.submit();
                }
            }]
        });
    }

    function updateDeliveryDriver() {
        parent.$.modalDialog({
            title: '分配司机',
            width: 780,
            height: 230,
            href: '${pageContext.request.contextPath}/mbOrderController/updateDeliveryDriverPage?id=${mbOrder.id}',
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            }]
        });
    }

    function updateCancelOrder() {
        parent.$.modalDialog({
            title: '超级取消',
            width: 780,
            height: 230,
            href: '${pageContext.request.contextPath}/mbOrderController/updateCancelOrderPage?id=${mbOrder.id}',
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            }]
        });
    }

    function confirmMbOrderCallbackItemAlert() {
        alert("提交之前,请先确认已将[空桶明细]与[商品退回]录入完成");
        confirmMbOrderCallbackItem();
    }
    function confirmMbOrderCallbackItem() {
        parent.$.modalDialog({
            title: '回桶确认',
            width: 780,
            height: 200,
            href: '${pageContext.request.contextPath}/mbOrderController/confirmMbOrderCallbackItemView?id=${mbOrder.id}',
            buttons: [{
                text: '提交',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=status]").val("OD40");
                    f.submit();
                }
            }]
        });
    }
    function addMbOrderCallbackItem() {
        parent.$.modalDialog({
            title: '空桶登记',
            width: 780,
            height: 200,
            href: '${pageContext.request.contextPath}/mbOrderCallbackItemController/addPage?id=' + ${mbOrder.id} ,
            buttons: [{
                text: '添加',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = gridMap[6].grid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            }
            ]
        });
    }

    function addMbOrderRefundItem() {
        parent.$.modalDialog({
            title: '退回登记',
            width: 780,
            height: 230,
            href: '${pageContext.request.contextPath}/mbOrderRefundItemController/addPage?id=' + ${mbOrder.id} ,
            buttons: [{
                text: '添加',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = gridMap[7].grid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            }
            ]
        });
    }
    function handleItem() {
        parent.$.modalDialog({
            title: '接单处理',
            width: 780,
            height: 200,
            href: '${pageContext.request.contextPath}/mbOrderController/editOrderAcceptAuditPage?id=${mbOrder.id}',
            buttons: [{
                text: '同意接单',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=status]").val("OD12");
                    f.submit();
                }
            },{
                text: '不同意，退余额',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=status]").val("OD31");
                    f.submit();
                }
            }]
        });
    }
    var gridMap = {};
    $(function() {
        gridMap = {
            handle:function(obj,clallback){
                if (obj.grid == null) {
                    obj.grid = clallback();
                } else {
                    obj.grid.datagrid('reload');
                }
            },
            0: {
                invoke: function () {
                    gridMap.handle(this,loadItemDataGrid);
                }, grid: null
            }, 1: {
                invoke: function () {
                    gridMap.handle(this,loadDeliveryDataGrid);
                }, grid: null
            }, 2: {
                invoke: function () {
                    gridMap.handle(this,loadPayDataGrid);
                }, grid: null
            }, 3: {
                invoke: function () {
                    gridMap.handle(this,loadInvoiceDataGrid);
                }, grid: null
            }, 4: {
                invoke: function () {
                    gridMap.handle(this, loadLogDataGrid);
                }, grid: null
            }, 5: {
                invoke: function () {
                    gridMap.handle(this, loadRefundDataGrid);
                }, grid: null
            }, 6: {
                invoke: function () {
                    gridMap.handle(this, loadMbOrderCallbackItemDataGrid);
                }, grid: null
            }, 7: {
                invoke: function () {
                    gridMap.handle(this, loadMbOrderRefundItemDataGrid);
                }, grid: null
            }

        };
        $('#order_view_tabs').tabs({
            onSelect: function (title, index) {
                gridMap[index].invoke();
            }
        });

        $('.moneyFormatter').each(function(){
             $(this).text($.formatMoney($(this).text().trim()));
        });
        gridMap[0].invoke();
        //$('#order_view_tabs').tabs('select',0);

        if ('${mbOrder.status}' == "OD35") {
            $('#order_view_tabs').tabs('select', 6);
        }
        if($('#scanInp').length != 0) {
            $('#scanInp').focus();

            /*$("#scanInp").bind("input propertychange", function() {
                var inp = $.trim($(this).val());
                if(inp != '') {
                    //printComplete(inp);
                }
            });*/

            $('#scanInp').keyup(function(event) {
                if (event.keyCode == '13') {
                    var inp = $.trim($(this).val());
                    if(inp != '') {
                        printComplete(inp);
                    }
                }
            });
        }

    });

    function loadItemDataGrid() {
        return $('#itemDataGrid').datagrid({
            url: '${pageContext.request.contextPath}/mbOrderItemController/dataGrid?orderId=${mbOrder.id}',
            fit:true,
            fitColumns: true,
            border: false,
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
                field: 'itemCode',
                title: '<%=TmbItem.ALIAS_CODE%>',
                width: 50,
                formatter:function(value,row){
                    return row.item.code;
                }
            }, {
                field: 'itemName',
                title: '<%=TmbItem.ALIAS_NAME%>',
                width: 100,
                formatter:function(value,row){
                    return row.item.name;
                }
            }, {
                field: 'marketPrice',
                title: '<%=TmbOrderItem.ALIAS_MARKET_PRICE%>',
                width: 50,
                align:'right',
                formatter:function(value){
                    return $.formatMoney(value);
                }
            }, {
                field: 'buyPrice',
                title: '<%=TmbOrderItem.ALIAS_BUY_PRICE%>',
                width: 50,
                align:'right',
                formatter:function(value){
                    return $.formatMoney(value);
                }
            }, {
                field: 'quantity',
                title: '<%=TmbOrderItem.ALIAS_QUANTITY%>',
                width: 50
            },{
                field: 'usableQuantity',
                title: '<%=TmbOrderItem.ALIAS_USABLE_QUANTITY%>',
                width: 50
            }, {
                field: 'unit',
                title: '单位',
                width: 50,
                formatter:function(value,row){
                    return row.item.quantityUnitName;
                }
            }, {
                field : 'action',
                title : '操作',
                width : 30,
                formatter : function(value, row, index) {
                    var str = '';
                    str += '&nbsp;';
                    if ($.canDelete&&row.buyPrice<=0) {
                        str += $.formatString('<img onclick="deleteItemFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                    }
                    return str;
                }
            }]],
            toolbar : '#itemToolbar'
        });
    }

    function loadDeliveryDataGrid() {
        return $('#deliveryDataGrid').datagrid({
            url: '${pageContext.request.contextPath}/mbOrderController/deliveryDataGrid?id=${mbOrder.id}',
            fitColumns: true,//自适应列
            fit:true,//自适应
            border: false,//
            pagination: true,//是否分页
            idField: 'id',//关联id
            pageSize: 10,//页大小
            pageList: [10, 20, 30, 40, 50],
            sortName: 'id',//默认排序字段
            sortOrder: 'desc',//排序方式:顺序倒序
            checkOnSelect: false,
            selectOnCheck: false,
            nowrap: false,
            striped: true,
            rownumbers: true,//显示
            singleSelect: true,
            columns: [[{
                field: 'id',
                title: '编号',
                width: 150,
                hidden: true
            }, {
                field: 'contactPeople',
                title: '<%=TmbOrder.ALIAS_CONTACT_PEOPLE%>',
                width: 60
            }, {
                field: 'contactPhone',
                title: '<%=TmbOrder.ALIAS_CONTACT_PHONE%>',
                width: 70
            }, {
                field: 'deliveryAddress',
                title: '<%=TmbOrder.ALIAS_DELIVERY_ADDRESS%>',
                width: 200
            }, {
                field: 'deliveryWayName',
                title: '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
                width: 60
            }, {
                field: 'deliveryPrice',
                title: '<%=TmbOrder.ALIAS_DELIVERY_PRICE%>',
                width: 40,
                align:'right',
                formatter:function(value){
                    return $.formatMoney(value);
                }
            }, {
                field: 'deliveryDriverName',
                title: '<%=TmbOrder.ALIAS_DELIVERY_DRIVER%>',
                width: 60,
            }, {
                field: 'deliveryRequireTime',
                title: '<%=TmbOrder.ALIAS_DELIVERY_TIME%>',
                width: 80,
            }, {
                field: 'deliveryStatusName',
                title: '<%=TmbOrder.ALIAS_DELIVERY_STATUS%>',
                width: 50
            }]]
        });
    }

    <%--function loadPayDataGrid() {--%>
        <%--return $('#payDataGrid').datagrid({--%>
            <%--url: '${pageContext.request.contextPath}/mbPaymentController/dataGrid?orderId=${mbOrder.id}',--%>
            <%--fitColumns: true,--%>
            <%--fit:true,--%>
            <%--border: false,--%>
            <%--pagination: true,--%>
            <%--idField: 'id',--%>
            <%--pageSize: 10,--%>
            <%--pageList: [10, 20, 30, 40, 50],--%>
            <%--sortName: 'id',--%>
            <%--sortOrder: 'desc',--%>
            <%--checkOnSelect: false,--%>
            <%--selectOnCheck: false,--%>
            <%--nowrap: false,--%>
            <%--striped: true,--%>
            <%--rownumbers: true,--%>
            <%--singleSelect: true,--%>
            <%--columns: [[{--%>
                <%--field: 'id',--%>
                <%--title: '编号',--%>
                <%--width: 150,--%>
                <%--hidden: true--%>
            <%--}, {--%>
                <%--field: 'payWayName',--%>
                <%--title: '<%=TmbPayment.ALIAS_PAY_WAY%>',--%>
                <%--width: 50--%>
            <%--}, {--%>
                <%--field: 'amount',--%>
                <%--title: '<%=TmbPayment.ALIAS_AMOUNT%>',--%>
                <%--width: 50,--%>
                <%--align:'right',--%>
                <%--formatter:function(value){--%>
                    <%--return $.formatMoney(value);--%>
                <%--}--%>
            <%--}, {--%>
                <%--field: 'status',--%>
                <%--title: '<%=TmbPayment.ALIAS_STATUS%>',--%>
                <%--width: 50,--%>
                <%--formatter: function (value, row, index) {//value该字段记录值,row行对象--%>
                    <%--if (row.status == true) {--%>
                        <%--return "成功";--%>
                    <%--}--%>
                    <%--return "失败";--%>
                <%--}--%>
            <%--}, {--%>
                <%--field : 'payConsist',--%>
                <%--title : '<%=TmbPayment.ALIAS_PAY_CONSIST%>',--%>
                <%--width : 200,--%>
                <%--formatter:function(value, row){--%>
                    <%--var paymentItems = row.mbPaymentItems;--%>
                    <%--var payConsist = "";--%>
                    <%--for(var i = 0;i<paymentItems.length;i++){--%>
                        <%--var item = paymentItems[i];--%>
                        <%--payConsist +=item.payWayName+$.formatMoney(item.amount)+" ";--%>
                    <%--}--%>
                    <%--return payConsist;--%>
                <%--}--%>
                <%--}, {--%>
                <%--field: 'bankCodeName',--%>
                <%--title: '<%=TmbPaymentItem.ALIAS_BANK_CODE_NAME%>',--%>
                <%--width: 100--%>
                <%--},{--%>
                <%--field: 'remitter',--%>
                <%--title: '<%=TmbPaymentItem.ALIAS_REMITTER%>',--%>
                <%--width: 50--%>
                <%--}, {--%>
                <%--field: 'remitterTime',--%>
                <%--title: '<%=TmbPaymentItem.ALIAS_REMITTER_TIME%>',--%>
                <%--width: 120--%>
                <%--}, {--%>
                <%--field: 'remark',--%>
                <%--title: '<%=TmbPaymentItem.ALIAS_REMARK%>',--%>
                <%--width: 200--%>
                <%--}, {--%>
                <%--field: 'refId',--%>
                <%--title: '流水号',--%>
                <%--width: 200--%>
            <%--}]]--%>
        <%--});--%>
    <%--}--%>
   function loadPayDataGrid() {
        return $('#payDataGrid').datagrid({
            url: '${pageContext.request.contextPath}/mbPaymentItemController/dataGridByOrderId?orderId=${mbOrder.id}',
            fitColumns: true,
            fit:true,
            border: false,
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
                field: 'payWayName',
                title: '<%=TmbPayment.ALIAS_PAY_WAY%>',
                width: 50
            }, {
                field: 'amount',
                title: '支付数额',
                width: 50,
                align:'right',
                formatter:function(value,row,index){
                    if (row.payWay == "PW04") return value + "张";
                    return $.formatMoney(value);
                }
            },
                <%--{--%>
                <%--field: 'status',--%>
                <%--title: '<%=TmbPayment.ALIAS_STATUS%>',--%>
                <%--width: 50,--%>
                <%--formatter: function (value, row, index) {//value该字段记录值,row行对象--%>
                    <%--if (row.status == true) {--%>
                        <%--return "成功";--%>
                    <%--}--%>
                    <%--return "失败";--%>
                <%--}--%>
            <%--}, --%>
                {
                field: 'bankCodeName',
                title: '<%=TmbPaymentItem.ALIAS_BANK_CODE_NAME%>',
                width: 100
                },{
                field: 'remitter',
                title: '<%=TmbPaymentItem.ALIAS_REMITTER%>',
                width: 50
                }, {
                field: 'remitterTime',
                title: '<%=TmbPaymentItem.ALIAS_REMITTER_TIME%>',
                width: 120
                }, {
                field: 'couponsName',
                title: '<%=TmbPaymentItem.ALIAS_REMARK%>',
                width: 200
                }, {
                field: 'refId',
                title: '流水号',
                width: 200
            }]]
        });
    }
    function loadInvoiceDataGrid() {
        return $('#invoiceDataGrid').datagrid({
            url: '${pageContext.request.contextPath}/mbOrderInvoiceController/dataGrid?orderId=${mbOrder.id}',
            fitColumns: true,
            fit:true,
            border: false,
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
                field: 'addtime',
                title: '<%=TmbOrderInvoice.ALIAS_ADDTIME%>',
                width: 130
            }, {
                field: 'companyName',
                title: '<%=TmbOrderInvoice.ALIAS_COMPANY_NAME%>',
                width: 100
            }, {
                field: 'companyTfn',
                title: '<%=TmbOrderInvoice.ALIAS_COMPANY_TFN%>',
                width: 100
            }, {
                field: 'registerAddress',
                title: '<%=TmbOrderInvoice.ALIAS_REGISTER_ADDRESS%>',
                width: 200
            }, {
                field: 'registerPhone',
                title: '<%=TmbOrderInvoice.ALIAS_REGISTER_PHONE%>',
                width: 100
            }, {
                field: 'bankName',
                title: '<%=TmbOrderInvoice.ALIAS_BANK_NAME%>',
                width: 100
            }, {
                field: 'bankNumber',
                title: '<%=TmbOrderInvoice.ALIAS_BANK_NUMBER%>',
                width: 150
            }, {
                field: 'invoiceUseName',
                title: '<%=TmbOrderInvoice.ALIAS_INVOICE_USE%>',
                width: 70
            }, {
                field: 'invoiceStatusName',
                title: '<%=TmbOrderInvoice.ALIAS_INVOICE_STATUS%>',
                width: 70
            }, {
                field: 'loginName',
                title: '<%=TmbOrderInvoice.ALIAS_LOGIN_ID%>',
                width: 50
            }, {
                field: 'action',
                title: '操作',
                width: 50,
                formatter: function (value, row, index) {
                    var str = '';
                    str += '&nbsp;';
                    if ($.viewOrderInvoice) {
                        str += $.formatString('<img onclick="viewOrderInvoice(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/map/magnifier.png');
                    }
                    return str;
                }
            }

            ]]
        });
    }
    var logDataGrid;
    function loadLogDataGrid() {
        return logDataGrid=$('#logDataGrid').datagrid({
            url: '${pageContext.request.contextPath}/mbOrderLogController/dataGrid?orderId=${mbOrder.id}',
            fitColumns: true,
            fit:true,
            border: false,
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
            columns : [ [ {
                field : 'id',
                title : '编号',
                width : 150,
                hidden : true
            }, {
                field : 'updatetime',
                title : '<%=TmbOrderLog.ALIAS_UPDATETIME%>',
                width : 70
            }, {
                field : 'logTypeName',
                title : '<%=TmbOrderLog.ALIAS_LOG_TYPE%>',
                width : 40
            }, {
                field : 'content',
                title : '<%=TmbOrderLog.ALIAS_CONTENT%>',
                width : 150
            }, {
                field : 'remark',
                title : '<%=TmbOrderLog.ALIAS_REMARK%>',
                width : 150
            }, {
                field : 'loginName',
                title : '<%=TmbOrderLog.ALIAS_LOGIN_NAME%>',
                width : 30
            } ,{
                field : 'action',
                title : '操作',
                width : 20,
                formatter : function(value, row, index) {
                    var str = '';
                    str += '&nbsp;';
                    if ($.canDeleteOrderLog && (row.logType=="LT011" || row.logType=="LT012" || row.logType=="LT013")) {
                        str += $.formatString('<img onclick="deleteOrderLog(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                    }
                    return str;
                }
            }]],
            toolbar:"#logDataGridbar"
        });
    }

    function loadRefundDataGrid() {
        return $('#refundDataGrid').datagrid({
            url: '${pageContext.request.contextPath}/mbOrderRefundLogController/dataGrid?orderId=${mbOrder.id}',
            fitColumns: true,
            fit:true,
            border: false,
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
            columns : [ [ {
                field : 'id',
                title : '编号',
                width : 150,
                hidden : true
            }, {
                field : 'updatetime',
                title : '<%=TmbOrderRefundLog.ALIAS_UPDATETIME%>',
                width : 70
            }, {
                field : 'amount',
                title : '<%=TmbOrderRefundLog.ALIAS_AMOUNT%>',
                width : 50,
                align:'right',
                formatter:function(value,row,index){
                    if(row.payWay == 'PW04') return value + '张';
                    return $.formatMoney(value);
                }
            }, {
                field : 'refundWayName',
                title : '<%=TmbOrderRefundLog.ALIAS_REFUND_WAY%>',
                width : 50
            }, {
                field: 'payWayName',
                title: '<%=TmbOrderRefundLog.ALIAS_PAY_WAY%>',
                width : 50
            }   , {
                field: 'couponsName',
                title: '券票名称',
                width: 70
            },{
                field : 'reason',
                title : '<%=TmbOrderRefundLog.ALIAS_REASON%>',
                width : 200
            }]]
        });
    }
    function loadMbOrderCallbackItemDataGrid(){
        return $('#mbOrderCallBackItemDataGrid').datagrid({
            url:'${pageContext.request.contextPath}/mbOrderCallbackItemController/dataGrid?orderId=${mbOrder.id}',
            fitColumns: true,
            fit:true,
            border: false,
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
            columns:[[{
                field : 'id',
                title : '编号',
                width : 50,
                hidden: true
            },{
                field : 'updatetime',
                title : '<%=TmbOrderCallbackItem.ALIAS_UPDATETIME%>',
                width : 70,
            },{
                field: 'itemName',
                title : '<%=TmbOrderCallbackItem.ALIAS_ITEM_NAME%>',
                width : 100,
            },{
                field: 'quantity',
                title : '<%=TmbOrderCallbackItem.ALIAS_QUANTITY%>',
                width : 20,
            },{
                field: 'remark',
                title : '<%=TmbOrderCallbackItem.ALIAS_REMARK%>',
                width : 150,
            },{
                field: 'loginName',
                title : '<%=TmbOrderCallbackItem.ALIAS_LOGIN_NAME%>',
                width : 50,
            },{
                field: 'action',
                title : '操作',
                width :30 ,
                formatter : function(value, row, index) {

                    var str = '';
                    str += '&nbsp;';
                    if ($.mbOrderCallbackItemCanDelete) {
                        str += $.formatString('<img onclick="deleteMbOrderCallbackItemFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                    }
                    return str;
                }
            }

            ]],
           toolbar:"#mbOrderCallbackItemToolbar"
        });
    }

    function deleteMbOrderCallbackItemFun(id) {
        if (id == undefined) {
            var rows = gridMap[0].grid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
            if (b) {
                parent.$.messager.progress({
                    title : '提示',
                    text : '数据处理中，请稍后....'
                });
                $.post('${pageContext.request.contextPath}/mbOrderCallbackItemController/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        gridMap[6].grid.datagrid('reload');
                    }
                    parent.$.messager.progress('close');
                }, 'JSON');
            }
        });
    }

    function  viewOrderInvoice(id) {

        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '查看数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbOrderInvoiceController/view?id=' + id
        });


    }
    function addItemFun() {
        parent.$.modalDialog({
            title : '添加数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbOrderItemController/addOffsetPage?orderId=${mbOrder.id}',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = gridMap[0].grid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            } ]
        });
    }
    function deleteItemFun(id) {
        if (id == undefined) {
            var rows = gridMap[0].grid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
            if (b) {
                parent.$.messager.progress({
                    title : '提示',
                    text : '数据处理中，请稍后....'
                });
                $.post('${pageContext.request.contextPath}/mbOrderItemController/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        gridMap[0].grid.datagrid('reload');
                    }
                    parent.$.messager.progress('close');
                }, 'JSON');
            }
        });
    }
    function loadMbOrderRefundItemDataGrid(){
        return $('#mbOrderRefundItemDataGrid').datagrid({
            url:'${pageContext.request.contextPath}/mbOrderRefundItemController/dataGrid?orderId=${mbOrder.id}',
            fitColumns: true,
            fit:true,
            border: false,
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
            columns:[[{
                field : 'id',
                title : '编号',
                width : 50,
                hidden: true
            },{
                field : 'updatetime',
                title : '<%=TmbOrderRefundItem.ALIAS_UPDATETIME%>',
                width : 60,
            },{
                field: 'itemName',
                title : '<%=TmbOrderRefundItem.ALIAS_ITEM_NAME%>',
                width : 100,
            },{
                field: 'quantity',
                title : '<%=TmbOrderRefundItem.ALIAS_QUANTITY%>',
                width : 30,
            },{
                field: 'typeName',
                title : '<%=TmbOrderRefundItem.ALIAS_REFUND_TYPE%>',
                width : 30,
            }, {
                field: 'remark',
                title: '<%=TmbOrderRefundItem.ALIAS_REMARK%>',
                width: 150,
            }, {
                field: 'loginName',
                title: '<%=TmbOrderRefundItem.ALIAS_LOGIN_NAME%>',
                width: 50,
            }, {
                field: 'action',
                title: '操作',
                width: 30,
                formatter: function (value, row, index) {

                    var str = '';
                    str += '&nbsp;';
                    if ($.mbOrderRefundItemCanDelete) {
                        str += $.formatString('<img onclick="deleteMbOrderRefundItemFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                    }
                    return str;
                }
            }

            ]],
            toolbar:"#mbOrderRefundItemToolbar"
        });
    }
    function deleteMbOrderRefundItemFun(id) {
        if (id == undefined) {
            var rows = gridMap[0].grid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
            if (b) {
                parent.$.messager.progress({
                    title : '提示',
                    text : '数据处理中，请稍后....'
                });
                $.post('${pageContext.request.contextPath}/mbOrderRefundItemController/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        gridMap[7].grid.datagrid('reload');
                    }
                    parent.$.messager.progress('close');
                }, 'JSON');
            }
        });
    }

    function viewShop(id) {
        var href = '${pageContext.request.contextPath}/mbShopController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title: '门店详情-' + id,
            content: '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable: true
        });
    }
    function addOrderProblem(id) {
        parent.$.modalDialog({
            title : '添加数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbProblemTrackController/addPage?id=' + id,
            buttons : [ {
                text : '添加',
                handler : function() {
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            } ]
        });
    }
    function addOrderLog(title,logType) {
        parent.$.modalDialog({
            title : title,
            width : 780,
            height : 230,
            href : '${pageContext.request.contextPath}/mbOrderLogController/addPage?orderId='+${mbOrder.id},
            buttons : [ {
                text : '保存',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = logDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=logType]").val(logType);
                    f.submit();
                }
            } ]
        });
    }
    function deleteOrderLog(id) {
        if (id == undefined) {
            var rows = logDataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.messager.confirm('询问', '您是否要删除当前数据？', function (b) {
            if (b) {
                parent.$.messager.progress({
                    title: '提示',
                    text: '数据处理中，请稍后....'
                });
                $.post('${pageContext.request.contextPath}/mbOrderLogController/delete', {
                    id: id
                }, function (result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        logDataGrid.datagrid('reload');
                    }
                    parent.$.messager.progress('close');
                }, 'JSON');
            }
        });
    }
</script>
</head>
<body>
    <div class="easyui-layout" data-options="fit : true,border:false">
        <div data-options="region:'north',title:'基本信息',border:false" style="height: 260px; overflow: hidden;">
            <table class="table">
                <tr>
                    <th><%=TmbOrder.ALIAS_ID%>：</th>
                    <td>
                        ${mbOrder.id}
                        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/printOrderView') and mbOrder.status=='OD12'}">
                            <a href="javascript:void(0);" class="easyui-linkbutton print"  onclick="printOrder()">打印</a>
                        </c:if>
                        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbProblemTrackController/addPage')}">
                            <a onclick="addOrderProblem(${mbOrder.id});" href="javascript:void(0);" class="easyui-linkbutton">添加问题订单</a>
                        </c:if>
                        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/updateCancelOrderPage') and fn:contains(cancelStatusList, mbOrder.status)}">
                            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="updateCancelOrder();">超级取消</a>
                        </c:if>
                    </td>

                    <th><%=TmbOrder.ALIAS_CONTACT_PHONE%>：</th>
                    <td>
                        ${mbOrder.contactPhone}
                    </td>
                    <th><%=TmbOrder.ALIAS_USER_ID%>：</th>
                    <td>
                       ${mbOrder.userId}
                    </td>
                    <th><%=TmbOrder.ALIAS_USER_NICK_NAME%>：</th>
                    <td>
                        ${mbOrder.userNickName}
                    </td>

                </tr>
                <tr>
                    <th><%=TmbOrder.ALIAS_STATUS%>：</th>
                    <td>
                        ${mbOrder.orderStatusName}
                        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/updateAuditPage') and mbOrder.payWay=='PW03' and mbOrder.status=='OD05'}">
                            <a href="javascript:void(0);" class="easyui-linkbutton"  onclick="auditOrder();">审核</a>
                        </c:if>
                        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/deliveryItemPage') and mbOrder.deliveryStatus=='DS01' and (mbOrder.status=='OD10' or mbOrder.status=='OD09')}">
                            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="handleItem();">接单</a>
                        </c:if>
                        <c:if test="${fn:contains(sessionInfo.resourceList,'/mbOrderController/confirmMbOrderCallbackItem' ) and mbOrder.deliveryStatus=='DS30' and mbOrder.status=='OD35'}">
                            <a href="javascript:void(0);" class="easyui-linkbutton"  onclick="confirmMbOrderCallbackItemAlert();">回桶确认</a>
                        </c:if>
                        <c:if test="${fn:contains(sessionInfo.resourceList,'/mbOrderController/updateDeliveryDriverPage' ) and (mbOrder.status=='OD12' or mbOrder.status=='OD15' or mbOrder.status=='OD20')}">
                            <a href="javascript:void(0);" class="easyui-linkbutton"  onclick="updateDeliveryDriver();">分配司机</a>
                        </c:if>
                        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/printOrderView') and mbOrder.status=='OD12'}">
                            <!--<a href="javascript:void(0);" class="easyui-linkbutton"  onclick="confirmPrintComplete()">确认已打印</a>-->
                            <input type="text" id="scanInp" maxlength="10" class="span2" placeholder="扫码确认打印" style="width: 85px;margin-bottom: 0;"/>
                        </c:if>
                    </td>
                    <th><%=TmbOrder.ALIAS_SHOP_ID%>：</th>
                    <td>
                        <a href="javascript:void(0);" onclick="viewShop(${mbOrder.shopId})">${mbOrder.shopId}</a>
                    </td>
                    <th><%=TmbOrder.ALIAS_SHOP_NAME%>：</th>
                    <td>
                        ${mbOrder.shopName}
                    </td>
                    <th>下单渠道：</th>
                    <td>

                        <c:choose>
                            <c:when test="${mbOrder.addLoginId!=null}">
                                客服(${addLoginName})
                            </c:when>
                            <c:otherwise>
                                公众号
                            </c:otherwise>
                        </c:choose>
                    </td>


                </tr>
                <tr>

                    <th><%=TmbOrder.ALIAS_ORDER_PRICE%>：</th>
                    <td class="moneyFormatter">
                        ${mbOrder.orderPrice}
                    </td>
                    <th><%=TmbOrder.ALIAS_TOTAL_PRICE%>：</th>
                    <td class="moneyFormatter">
                        ${mbOrder.totalPrice}
                    </td>

                    <%--<th><%=TmbOrder.ALIAS_DELIVERY_PRICE%>：</th>
                    <td class="moneyFormatter">
                        ${mbOrder.deliveryPrice}
                    </td>--%>
                    <th><%=TmbOrder.ALIAS_PAY_STATUS%>：</th>
                    <td>
                        ${mbOrder.payStatusName}
                        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/updateAddPaymentPage') and mbOrder.payStatus=='PS01' and mbOrder.status=='OD40'}">
                            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="updateAddPayment();">付款</a>
                        </c:if>
                    </td>
                    <th><%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>：</th>
                    <td>${mbOrder.deliveryRequireTime}</td>

                </tr>
                <tr>
                    <th><%=TmbOrder.ALIAS_TOTAL_REFUND_AMOUNT%>：</th>
                    <td class="moneyFormatter">
                        ${mbOrder.totalRefundAmount}
                    </td>
                    <th><%=TmbOrder.ALIAS_DELIVERY_STATUS%>：</th>
                    <td>
                        ${mbOrder.deliveryStatusName}
                        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/deliveryItemPage') and mbOrder.deliveryStatus=='DS01' and mbOrder.status=='OD15'}">
                            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="deliveryItem();">发货</a>
                        </c:if>

                        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/completeDelivery') and (mbOrder.deliveryStatus=='DS10' or mbOrder.deliveryStatus=='DS30') and (mbOrder.status=='OD30' or mbOrder.status=='OD20')}">
                            <a href="javascript:void(0);" class="easyui-linkbutton" onclick="completeDelivery();">配送完成</a>
                        </c:if>

                    </td>
                    <th><%=TmbOrder.ALIAS_DELIVERY_WAY%>：</th>
                    <td>
                        ${mbOrder.deliveryWayName}
                    </td>
                    <th><%=TmbOrder.ALIAS_ADDTIME%>：</th>
                    <td>${mbOrder.addtime}</td>
                </tr>

                <tr>
                    <th>发货地：</th>
                    <td colspan="7">
                        ${warehouseName}
                    </td>


                </tr>

                <tr>

                    <th><%=TmbOrder.ALIAS_USER_REMARK%>：</th>
                    <td colspan="7">
                        ${mbOrder.userRemark}
                    </td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <div id="order_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">

                <div title="商品明细">
                    <table id="itemDataGrid"></table>
                </div>
                <div title="配送明细">
                    <table id="deliveryDataGrid"></table>
                </div>
                <div title="支付明细">
                    <table id="payDataGrid"></table>
                </div>
                <div title="发票明细">
                    <table id="invoiceDataGrid"></table>
                </div>
                <div title="订单日志">
                    <table id="logDataGrid"></table>
                </div>
                <div title="退款明细">
                    <table id="refundDataGrid"></table>
                </div>
                <div title="空桶明细">
                    <table id="mbOrderCallBackItemDataGrid"></table>
                </div>
                <div title="商品退回">
                    <table id="mbOrderRefundItemDataGrid"></table>
                </div>
            </div>
        </div>
    </div>

    <div id="itemToolbar" style="display: none;">
        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderItemController/addOffsetPage') and mbOrder.deliveryStatus=='DS01' and (mbOrder.status=='OD10' or mbOrder.status=='OD09')}">
            <a onclick="addItemFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">补偿</a>
        </c:if>
    </div>
    <div id="mbOrderCallbackItemToolbar" style="display: none;">
        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderCallbackItemController/add') and mbOrder.deliveryStatus=='DS30' and mbOrder.status=='OD35'}">
            <a href="javascript:void(0);" class="easyui-linkbutton"  onclick="addMbOrderCallbackItem();" data-options="plain:true,iconCls:'pencil_add'">添加</a>
        </c:if>
    </div>
    </div>
    <div id="mbOrderRefundItemToolbar" style="display: none;">
        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderRefundItemController/add') and mbOrder.deliveryStatus=='DS30' and mbOrder.status=='OD35'}">
            <a href="javascript:void(0);" class="easyui-linkbutton"  onclick="addMbOrderRefundItem();" data-options="plain:true,iconCls:'pencil_add'">添加</a>
        </c:if>
    </div>
    <div id="logDataGridbar" style="display: none;">
        <c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderLogController/add') }">
            <a href="javascript:void(0);" class="easyui-linkbutton"  onclick="addOrderLog('催单','LT011');" data-options="plain:true,iconCls:'pencil_add'">催单</a>
            <a href="javascript:void(0);" class="easyui-linkbutton"  onclick="addOrderLog('回单','LT012');" data-options="plain:true,iconCls:'pencil_add'">回单</a>
            <a href="javascript:void(0);" class="easyui-linkbutton"  onclick="addOrderLog('留言','LT013');" data-options="plain:true,iconCls:'pencil_add'">留言</a>


        </c:if>
    </div>
    <iframe id="printIframe" style="display: none;"></iframe>
</body>
</html>