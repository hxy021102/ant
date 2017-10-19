<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
    var dataGrid;
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/supplierOrderBillController/editStatus',
            onSubmit: function () {

                parent.$.messager.progress({
                    title: '提示',
                    text: '数据处理中，请稍后....'
                });
                return true;
            },
            success: function (result) {
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        dataGrid = $('#dataGrid').datagrid({
            url: '${pageContext.request.contextPath}/deliverOrderPayController/dataGrid?supplierOrderBillId='+${supplierOrderBill.id},
            fit: true,
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
            columns: [[
                {
                    field : 'id',
                    title : '编号',
                    width : 150,
                    hidden : true
                }, {
                    field : 'deliverOrderId',
                    title : '运单Id',
                    width : 50
                }, {
                    field : 'supplierOrderBillId',
                    title : '账单Id',
                    width : 50
                }, {
                    field : 'statusName',
                    title : '结算状态',
                    width : 50
                }, {
                    field : 'amount',
                    title : '总金额',
                    width : 50 ,
					formatter : function (value,row,index) {
                        if(value != null) {
                            return $.formatMoney(value);
						}
                    }
                }, {
                    field : 'payWay',
                    title : '支付方式',
                    width : 50
                }, {
                    field : 'supplierName',
                    title : '供应商名称',
                    width : 50
                }
            ]]
        })
    })
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<form id="form" method="post">
		<input type="hidden" name="supplierOrderBillId" value="${supplierOrderBill.id}">
		<input type="hidden" name="isAgree">
	<div data-options="region:'north',border:false" style="height: 170px">
		<table class="table table-hover table-condensed">
				<tr>	
					<th style="width: 60px;">供应商名称</th>
					<td>
						${supplierOrderBill.supplierName}
					</td>							
					<th style="width: 60px;">审核状态</th>
					<td>
						${supplierOrderBill.statusName}
					</td>							
				</tr>		
				<tr>
					<th>支付方式</th>
					<td>
						${supplierOrderBill.payWay}
					</td>
					<th>总金额</th>
					<td>
						<font class="money_input">${supplierOrderBill.amount}</font>
					</td>							
				</tr>		
				<tr>	
					<th>开始日期</th>
					<td>
						${supplierOrderBill.startDate}							
					</td>							
					<th>结束日期</th>
					<td>
						${supplierOrderBill.endDate}							
					</td>							
				</tr>
			<c:if test="${isView == 1}">
				<tr>
					<th>审核备注</th>
					<td colspan="3">
							${supplierOrderBill.remark}
					</td>
				</tr>
			</c:if>
			<c:if test="${isView == null}">
				<tr>
					<th>审核备注
					</th>
					<td colspan="3">
						<textarea name="remark" style="width: 90%" cols="30" rows="2"></textarea>
					</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	</form>
</div>