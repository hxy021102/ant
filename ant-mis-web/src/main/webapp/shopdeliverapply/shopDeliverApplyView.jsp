<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.scheme}://api.map.baidu.com/api?v=2.0&ak=uVkZBmjLC0KGflQtsXRc4rh4&s=1"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
	<script type="text/javascript">
    $(function () {
        $('.money_input').each(function(){
            $(this).text($.formatMoney($(this).text().trim()));
        });
        showShopMap()
    });

    function showShopMap() {
        $('#shopLayout').layout('expand','east');
        var shopId = ${shopDeliverApplyQuery.shopId};
        $.post('${pageContext.request.contextPath}/mbShopController/getShopApplyMap?shopId='+shopId,
            function (result) {
                if (result.success) {
                    renderMap(result.obj);
                }
            }, 'JSON');
    }
</script>
	<script type="text/javascript">
        var polygon = new BMap.Polygon([
            new BMap.Point(121.700332,31.19777),
            new BMap.Point(121.719332,31.19877),
            new BMap.Point(121.707332,31.20777),
            new BMap.Point(121.713332,31.18777),
            new BMap.Point(121.715332,31.19977)
        ], {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});  //创建多边形
        var map;
        function renderMap(mapData) {
            // 百度地图API功能
            map = new BMap.Map("allmap");
            map.centerAndZoom(new BMap.Point(mapData.longitude, mapData.latitude), 15);
            map.enableScrollWheelZoom(true);     //开启鼠标滚缩放

            var marker = new BMap.Marker(new BMap.Point(mapData.longitude, mapData.latitude));  // 创建标注
            var content = mapData.address;
            map.addOverlay(marker);               // 将标注添加到地图中
            addClickHandler(content, marker);
            map.addOverlay(polygon);   //增加多边形

            polygon.addEventListener("mousemove",function(e){
                var rangeArr = polygon.getPath();
               // alert("获取的数据为："+rangeArr[0].lng+" "+rangeArr[0].lat);
                 $("#distributeRange").val(JSON.stringify(rangeArr));
            });
        }
        function addClickHandler(content,marker){
            marker.addEventListener("click",function(e){
                openInfo(content,e); }
            );
        }
        function openInfo(content,e){
            var opts = {
                width: 250,     // 信息窗口宽度
                height: 95,     // 信息窗口高度
				/*title : "信息窗口" , // 信息窗口标题*/
                enableMessage: true//设置允许信息窗发送短息
            };
            var p = e.target;
            var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
            var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象
            map.openInfoWindow(infoWindow,point); //开启信息窗口
        }
        function  tijiao() {
            var distributeRangeValue=$("#distributeRange").val();
			alert("你好"+distributeRangeValue);
        }

        function updateDistributeRange() {
            var distributeRangeValue = $("#distributeRange").val();
            $.post('${pageContext.request.contextPath}/shopDeliverApplyController/updateDistributeRange?id=' + ${shopDeliverApplyQuery.id}+"&distributeRange=" + distributeRangeValue,
                function (result) {
                    if (result.success) {
                        parent.$.messager.alert('提示',result.msg, 'info');
                    }
                }, 'JSON');
        }
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false,">
		<table class="table table-hover table-condensed">
			<tr>
				<th>添加时间</th>
				<td>
					<fmt:formatDate value="${shopDeliverApplyQuery.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>修改时间</th>
				<td>
					<fmt:formatDate value="${shopDeliverApplyQuery.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>门店ID</th>
				<td>
					${shopDeliverApplyQuery.shopId}
				</td>
				<th>门店名称</th>
				<td>
					${shopDeliverApplyQuery.shopName}
				</td>
			</tr>
			<tr>
				<th>门店账号</th>
				<td>
					${shopDeliverApplyQuery.accountId}
				</td>
				<th>审核状态</th>
				<td>
					${shopDeliverApplyQuery.statusName}
				</td>
				<th>最大配送距离</th>
				<td>
					<c:if test="${shopDeliverApplyQuery.maxDeliveryDistance != null}">
						${shopDeliverApplyQuery.maxDeliveryDistance}米
					</c:if>
				</td>
				<th>是否短信通知</th>
				<td>
					<c:if test="${shopDeliverApplyQuery.smsRemind == false}">
						否
					</c:if>
					<c:if test="${shopDeliverApplyQuery.smsRemind == true}">
						是
					</c:if>
				</td>
			</tr>
			<tr>
				<th>是否必须上传回单</th>
				<td>
					<c:if test="${shopDeliverApplyQuery.uploadRequired == false}">
						否
					</c:if>
					<c:if test="${shopDeliverApplyQuery.uploadRequired == true}">
						是
					</c:if>
				</td>
				<th>是否营业</th>
				<td>
					<c:if test="${shopDeliverApplyQuery.online == false}">
						否
					</c:if>
					<c:if test="${shopDeliverApplyQuery.online == true}">
						是
					</c:if>
				</td>
				<th>派单类型</th>
				<td >
					${shopDeliverApplyQuery.deliveryTypeName}
				</td>
				<th>配送方式</th>
				<td>
					${shopDeliverApplyQuery.deliveryWayName}
				</td>
			</tr>
			<tr>
				<th>冻结状态</th>
				<td>
					<c:if test="${shopDeliverApplyQuery.frozen}">冻结</c:if>
					<c:if test="${!shopDeliverApplyQuery.frozen}">正常</c:if>
				</td>
				<th>转入权限</th>
				<td>
					<c:if test="${shopDeliverApplyQuery.transferAuth == false}">无权限</c:if>
					<c:if test="${shopDeliverApplyQuery.transferAuth == true}">允许</c:if>
				</td>
				<th>运费</th>
				<td colspan="4">
					<font   class="money_input">${shopDeliverApplyQuery.freight}</font>
				</td>
			</tr>
			<tr>
				<th>结果</th>
				<td colspan="9">
					${shopDeliverApplyQuery.result}
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<div id="allmap" style="height: 100%"></div>
		<div id="control">
			<input type="hidden" id="distributeRange" name="distributeRange" />
			<button onclick="polygon.enableEditing();">开启编辑功能</button>
			<button onclick="polygon.disableEditing();updateDistributeRange()">关闭编辑并提交</button>
		</div>
	</div>
</div>
</body>
</html>