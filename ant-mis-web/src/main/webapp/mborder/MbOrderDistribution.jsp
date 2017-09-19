﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbOrder分布管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
    parent.$.messager.progress('close');
    function showChart(chartData,titleName){
        var data = eval(chartData);
        var categories = [], series = [], order_data=[];
        parent.$.messager.progress('close');
        for(var i=0; i<data.length; i++) {
            if (data[i].orderKindName) {
                order_data.push([data[i].orderKindName+","+data[i].orderTotal,data[i].orderTotal]);
            }
        }
        $('#container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text:  titleName
            },
            tooltip: {
                headerFormat: '{series.name}<br>',
                pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
            },
            credits: {
                enabled: false
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    },
                    showInLegend : true
                }
            },
            series: [{
                type: 'pie',
                name: '订单比例',
                data:  order_data
            }]
        });
	}
    function searchFun() {
        var datas = $.serializeObject($('#searchForm'));
        $.ajax({
            type:"POST",
            url:'${pageContext.request.contextPath}/mbOrderController/viewChart',
            data: JSON.stringify(datas),
            dataType:'json',
            contentType: "application/json;charset=UTF-8",
            success:function(data){//data即为后台反馈回来的json数据；
                showChart(data,"订单分布汇总")
            }
        });
    }
    function cleanFun() {
        $('#searchForm input').val('');
        showChart(0,"");
    }

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 120px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="">
					<tr>
						<td>
							<strong>下单时间&nbsp;&nbsp;</strong><input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'orderTimeEnd\',{M:-1});}',maxDate:'#F{$dp.$D(\'orderTimeEnd\',{d:-1});}'})" id="orderTimeBegin" name="orderTimeBegin"/>
							至<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrderItem.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'orderTimeBegin\',{d:1});}',maxDate:'#F{$dp.$D(\'orderTimeBegin\',{M:1});}'})" id="orderTimeEnd" name="orderTimeEnd"/>
						</td>
					</tr>
				</table>
			</form>
			<div id="toolbar" style="">
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
			</div>
		</div>
		<div data-options="region:'center',border:false" style=" overflow: hidden;">
			<div id="container"></div>
		</div>
	</div>
</body>
</html>