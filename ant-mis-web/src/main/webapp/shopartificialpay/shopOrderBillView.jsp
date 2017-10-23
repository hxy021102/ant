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
	 <script type="text/javascript">
		 var shopBillDataGrid;
         $(function() {
             shopBillDataGrid= $('#shopBillDataGrid').datagrid({
             url : '${pageContext.request.contextPath}/deliverOrderShopPayController/dataGrid?shopOrderBillId='+${shopOrderBill.id},
             fit : true,
             fitColumns : true,
             border : false,
             pagination : true,
             idField : 'id',
             pageSize : 10,
             pageList : [ 10, 20, 30, 40, 50 ],
             sortOrder : 'desc',
             checkOnSelect : false,
             selectOnCheck : false,
             nowrap : false,
             striped : true,
             rownumbers : true,
             singleSelect : true,
             columns : [ [{
                 field : 'deliverOrderId',
                 title : '运单ID',
                 width : 30,
             }, {
                 field : 'shopId',
                 title : '门店ID',
                 width : 30,
             },{
                 field : 'shopName',
                 title : '名店名称',
                 width : 80
             }, {
                 field : 'statusName',
                 title : '状态',
                 width : 50
             },{
                 field : 'amount',
                 title : '总价',
				 align:"right",
                 width : 50,
                 formatter: function (value) {
                     if (value == null)
                         return "";
                     return $.formatMoney(value);
                 }
             },{
                 field : 'payWay',
                 title : '结算方式',
                 align:"right",
                 width : 50
             }] ],
             onLoadSuccess : function() {
                 parent.$.messager.progress('close');
                 $(this).datagrid('tooltip');
             }
         });
	 })

         function examineFun(id) {
             parent.$.modalDialog({
                 title : '审核派单',
                 width : 780,
                 height : 300,
                 href : '${pageContext.request.contextPath}/shopOrderBillController/examinePage?id=' + ${shopOrderBill.id},
                 buttons: [{
                     text: '通过',
                     handler: function () {
                         parent.$.modalDialog.openner_dataGrid =  shopBillDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                         var f = parent.$.modalDialog.handler.find('#form');
                         f.find("input[name=status]").val("BAS02");
                         f.submit();
                     }
                 },
                     {
                         text: '拒绝',
                         handler: function () {
                             parent.$.modalDialog.openner_dataGrid =  shopBillDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                             var f = parent.$.modalDialog.handler.find('#form');
                             f.find("input[name=status]").val("BAS03");
                             f.submit();
                         }
                     }
                 ]
             });
         }

         function payShopBill() {
             parent.$.modalDialog({
                 title : '结算账单',
                 width : 780,
                 height : 230,
                 href : '${pageContext.request.contextPath}/shopOrderBillController/payShopBillPage?id=' +${shopOrderBill.id},
                 buttons : [ {
                     text : '确认',
                     handler : function() {
                         parent.$.modalDialog.openner_dataGrid = shopBillDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                         var f = parent.$.modalDialog.handler.find('#form');
                         f.submit();
                     }
                 } ]
             });
         }
	 </script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border:false">
	<div data-options="region:'north',title:'基本信息',border:false" style="height: 150px; overflow: hidden;">
		<table class="table">
			<tr>
				<th>账单ID</th>
				<td>
					${shopOrderBill.id}
					    <c:if test="${fn:contains(sessionInfo.resourceList, '/shopOrderBillController/examinePage') and shopOrderBill.status=='BAS01' }">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="examineFun();">审核</a>
						</c:if>
						<c:if test="${fn:contains(sessionInfo.resourceList, '/shopOrderBillController/examinePage') and shopOrderBill.status=='BAS02' }">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="payShopBill();">支付</a>
						</c:if>
				</td>
				<th>创建时间</th>
				<td>
					<fmt:formatDate value="${shopOrderBill.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>开始时间</th>
				<td>
					<fmt:formatDate value="${shopOrderBill.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>结束时间</th>
				<td>
					<fmt:formatDate value="${shopOrderBill.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th>总金额</th>
				<td >
					${shopOrderBill.amount}
				</td>
				<th>支付方式</th>
				<td >
					${shopOrderBill.payWay}
				</td>
				<th>审核人</th>
				<td colspan="3">
					${shopOrderBill.reviewerName}
				</td>
			</tr>
			<tr>
				<th>审核备注</th>
				<td colspan="8">
					${shopOrderBill.remark}
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<div id="bill_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="运单门店结算">
				<table id="shopBillDataGrid"></table>
			</div>
		</div>
	</div>
</div>
</body>
</html>

