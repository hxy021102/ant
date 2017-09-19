<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbCoupons" %>
<%@ page import="com.mobian.model.TmbItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
    $('#itemListTable').datagrid({
        url: '${pageContext.request.contextPath}/mbCouponsItemController/dataGrid?couponsId=' + ${mbCoupons.id},
        fit: false,
        fitColumns:true,
        iconCls: '',
        singleSelect: true,
        columns:[[{
            field:'itemName',
            title:'<%=TmbItem.ALIAS_NAME%>',
            width:300,
		}, {
            field:'itemCode',
            title:'<%=TmbItem.ALIAS_CODE%>',
            width:200
		}, {
            field:'itemMarketPrice',
            title:'<%=TmbItem.ALIAS_MARKET_PRICE%>',
            width:100,
            align:'right',
            formatter: function (value,row,index) {
				if(value != undefined){
					return $.formatMoney(value);
				}
			}
		}
        ]]
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbCoupons.ALIAS_NAME%></th>	
					<td>
						${mbCoupons.name}							
					</td>							
					<th><%=TmbCoupons.ALIAS_CODE%></th>	
					<td>
						${mbCoupons.code}							
					</td>							

					<th><%=TmbCoupons.ALIAS_TYPE%></th>	
					<td>
						${mbCoupons.typeName}
					</td>
				</tr>
				<tr>
					<th><%=TmbCoupons.ALIAS_PRICE%></th>
					<td>
						${mbCoupons.price /100.00}元
					</td>
					<th><%=TmbCoupons.ALIAS_QUANTITY_TOTAL%></th>	
					<td>
						${mbCoupons.quantityTotal}							
					</td>							
					<th><%=TmbCoupons.ALIAS_QUANTITY_USED%></th>	
					<td>
						${mbCoupons.quantityUsed}							
					</td>							
				</tr>		
				<tr>
					<th><%=TmbCoupons.ALIAS_STATUS%></th>
					<td>
						${mbCoupons.statusName}
					</td>
					<th><%=TmbCoupons.ALIAS_MONEY_THRESHOLD%></th>	
					<td>
						${mbCoupons.moneyThreshold /100.00} 元
					</td>

					<th><%=TmbCoupons.ALIAS_DISCOUNT%></th>
					<td>
						${mbCoupons.discount / 100.00}
					</td>
				</tr>		
				<tr>	
					<th><%=TmbCoupons.ALIAS_TIME_OPEN%></th>	
					<td>
						${mbCoupons.timeOpen}							
					</td>							
					<th><%=TmbCoupons.ALIAS_TIME_CLOSE%></th>	
					<td colspan="4">
						${mbCoupons.timeClose}							
					</td>							
				</tr>		
				<tr>
					<th><%=TmbCoupons.ALIAS_DESCRIPTION%></th>
					<td colspan="6">
						${mbCoupons.description}
					</td>							
				</tr>

		</table>
		<div style="overflow: auto;height: 150px">
			<table id="itemListTable" title="优惠/兑换商品列表"></table>
		</div>
	</div>
</div>