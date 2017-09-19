<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

    var chartData = '${chartData}';
    var data = eval("(" + chartData + ")");
    var categories = [], series = [], data_0 = [], data_1 = [],data_2 = [];
    $(function() {
        parent.$.messager.progress('close');

        for(var i=0; i<data.length; i++) {
            if(i == 10) break; // top10
            if(data[i].itemName) {
                categories.push(data[i].itemName);
                data_0.push(data[i].quantity);
                data_1.push({
                    y:data[i].salesQuantity,
                    totalPrice:data[i].totalPrice,
                    backQuantity:data[i].backQuantity,
                });

            }

        }
        series[0] = {
            name : '销售',
            color:'#00FF00',
            data : data_0
        };
        series[1] = {
            name : '实际',
            color: '#748fff',
            data : data_1
        };

        $('#container').highcharts({
            credits : {
                enabled : false
            },
            chart : {
                type : 'column'
            },
            title : {
                text : '销售数据汇总'
            },
            xAxis : {
                categories : categories,
                labels : {
                    rotation : -45,
                    align : 'right',
                    style : {
                        fontSize : '13px',
                        fontFamily : 'Verdana, sans-serif'
                    }
                }
            },
            yAxis : {
                min : 0,
                title : {
                    text : '数量'
                }
            },
            tooltip: {
                shared: true,
                formatter: function() {
                    console.log(this);
//					var priceH = '';
//					if(this.point.totalPrice) {
//						priceH = '<br>' + this.series.name + '总价: <b>' + $.formatMoney(this.point.totalPrice) + '元</b>';
//					}
//					return this.x + '<br>'
//						+ this.series.name + '数量: <b>' + Highcharts.numberFormat(this.y, 0, ',') + '</b>' + priceH;

                    return this.x + '<br>'
                        + this.points[0].series.name + '数量: <b>' + Highcharts.numberFormat(this.points[0].y, 0, ',') + '</b><br>'
                        + '退回数量: <b>' + Highcharts.numberFormat(this.points[1].point.backQuantity, 0, ',') + '</b><br>'
                        + this.points[1].series.name + '数量: <b>' + Highcharts.numberFormat(this.points[1].y, 0, ',') + '</b><br>'
                        + this.points[1].series.name + '总价: <b>' + $.formatMoney(this.points[1].point.totalPrice) + '元</b>';
                }
            },
            plotOptions: {
                series: {
                    dataLabels: {
                        enabled: true,
                        rotation: -45,
                        y: -10,
                        formatter : function(){
                            return Highcharts.numberFormat(this.y, 0, ',');
                        }
                    }
                }
            },
            series : series
        });

    });
</script>

<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'center',border:false">
        <div id="container"></div>
    </div>
</div>