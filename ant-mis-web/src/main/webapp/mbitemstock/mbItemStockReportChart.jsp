<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

    var chartData = '${chartData}';
	var data = eval("(" + chartData + ")");
	var categories = [], series = [], stock_data=[];
    $(function () {
        parent.$.messager.progress('close');
        for(var i=0; i<data.length; i++) {
            if (data[i].itemName) {
                stock_data.push([data[i].itemName,data[i].totalPrice]);
            }
        }
        $('#container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text:  '库存商品金额汇总'
            },
            credits: {
                enabled: false
            },
            tooltip: {
                headerFormat: '{series.name}<br>',
                pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
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
					//显示各类商品的标志
                    showInLegend : true
                }
            },
            series: [{
                type: 'pie',
                name: '商品价值比例',
                data: stock_data
            }]
        });
    });

</script>

<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<div id="container"></div>
	</div>
</div>