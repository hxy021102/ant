<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
	<!-- 引入jQuery -->
	<script src="${pageContext.request.contextPath}/jslib/jquery-1.8.3.js"
			type="text/javascript" charset="utf-8"></script>
	<!-- 引入EasyUI -->
	<link id="easyuiTheme" rel="stylesheet"
		  href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css"
		  type="text/css">
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/jquery.easyui.min.js"
			charset="utf-8"></script>
	<!-- 扩展jQuery -->
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/jslib/extJquery.js?v=201305301341"
			charset="utf-8"></script>

<script type="text/javascript" src="${pageContext.request.scheme}://api.map.baidu.com/api?v=2.0&ak=uVkZBmjLC0KGflQtsXRc4rh4&s=1"></script>
	<script type="text/javascript">
    $(function () {
        $('.money_input').each(function(){
            $(this).text($.formatMoney($(this).text().trim()));
        });
        showShopMap()
    });

    function showShopMap() {
        var shopId = ${shopDeliverApplyQuery.shopId};
        $.post('${pageContext.request.contextPath}/mbShopController/getShopApplyMap?shopId='+shopId+"&shopDeliverApplyId="+${shopDeliverApplyQuery.id},
            function (result) {
                if (result.success) {
                    renderMap(result.obj);
                }
            }, 'JSON');
    }
</script>
	<script type="text/javascript">
        var polygon;
        var map;
        function renderMap(mapData) {
            var distributeRanges = mapData.distributeRangeMapList;
            var pts = [];
            if (distributeRanges != null && distributeRanges.length > 0) {
                for (var i = 0; i < distributeRanges.length; i++) {
                    pts[i] = new BMap.Point(distributeRanges[i].lng, distributeRanges[i].lat);
                }
            } else {
                var pt1 = new BMap.Point(mapData.longitude - 0.009, mapData.latitude + 0.002);
                var pt2 = new BMap.Point(mapData.longitude + 0.006, mapData.latitude - 0.006);
                var pt3 = new BMap.Point(mapData.longitude + 0.008, mapData.latitude + 0.005);
                pts.push(pt1);
                pts.push(pt2);
                pts.push(pt3);
            }
            polygon = new BMap.Polygon(pts,{strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});
            // 百度地图API功能
            map = new BMap.Map("allmap");
            map.centerAndZoom(new BMap.Point(mapData.longitude, mapData.latitude), 16);
            map.enableScrollWheelZoom(true);     //开启鼠标滚缩放

            var marker = new BMap.Marker(new BMap.Point(mapData.longitude, mapData.latitude));  // 创建标注
            var content = mapData.address;
            map.addOverlay(marker);               // 将标注添加到地图中
            addClickHandler(content, marker);
            map.addOverlay(polygon);   //增加多边形

            polygon.addEventListener("mousemove",function(e){
                var rangeArr = polygon.getPath();
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
        function updateDistributeRange() {
            var distributeRange = $("#distributeRange").val();
            if(distributeRange!=null){
            $.ajax({
                url:  '${pageContext.request.contextPath}/shopDeliverApplyController/updateDistributeRange?id=' + ${shopDeliverApplyQuery.id},
                data: distributeRange,
                dataType: "json",
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                beforeSend: function (request) {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '数据处理中，请稍后....'
                    });
                },
                success: function (data) {
                    parent.$.messager.progress('close');
                    if(data.success) {
                        parent.$.messager.alert('提示',data.msg, 'info');
                    }else{
                        parent.$.messager.alert('错误', data.msg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    parent.$.messager.progress('close');
                }
            });
			}else{
                parent.$.messager.progress('close');
                parent.$.messager.alert('错误', "获取多边形顶点失败!");
            }
        }
        function clearEmptyRange() {
            parent.$.messager.confirm('询问', '您是否要重置配送范围？', function (b) {
                if (b) {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '数据处理中，请稍后....'
                    });
                    $.post('${pageContext.request.contextPath}/shopDeliverApplyController/deleteDistributeRange', {
                        id: ${shopDeliverApplyQuery.id}
                    }, function (result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            location.reload()

                        }
                        parent.$.messager.progress('close');
                    }, 'JSON');
                }
            });
        }
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false," style="height: 130px; overflow: hidden;">
		<table class="table table-hover table-condensed">
			<tr>
				<th width="140">添加时间</th>
				<td width="300">
					<fmt:formatDate value="${shopDeliverApplyQuery.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th width="100">修改时间</th>
				<td width="300">
					<fmt:formatDate value="${shopDeliverApplyQuery.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th width="100">门店ID</th>
				<td width="200">
					${shopDeliverApplyQuery.shopId}
				</td>
				<th width="100">门店名称</th>
				<td  width="200">
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
					<c:choose>
						<c:when test="${shopDeliverApplyQuery.maxDeliveryDistance != null && (shopDeliverApplyQuery.distributeRange ==null||empty shopDeliverApplyQuery.distributeRange)}">
							${shopDeliverApplyQuery.maxDeliveryDistance}米 <font color="red">（有效）</font>
						</c:when>
						<c:otherwise>
							${shopDeliverApplyQuery.maxDeliveryDistance}米 <font color="red">（无效）</font>
						</c:otherwise>
					</c:choose>
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
		<div id="control">
			<input type="hidden" id="distributeRange" name="distributeRange" />
			<c:if test="${fn:contains(sessionInfo.resourceList, '/shopDeliverApplyController/updateDistributeRange')}">
				<button onclick="polygon.enableEditing();">开启编辑功能</button>
				<button onclick="polygon.disableEditing();updateDistributeRange()">关闭编辑并提交</button>
			</c:if>
			<c:if test="${fn:contains(sessionInfo.resourceList, '/shopDeliverApplyController/deleteDistributeRange')}">
				<button onclick="clearEmptyRange()">重置配送范围</button>
			</c:if>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<div id="allmap" style="height: 100%"></div>

	</div>
</div>
</body>
</html>