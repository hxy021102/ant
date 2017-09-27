 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbShop" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
 <!DOCTYPE html>
 <html>
 <head>
<title>门店地图管理</title>
 <style type="text/css">
	 body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
 </style>
 <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CEADSBSqQi7DC6a5YxqPY6FvgV3Kamc4"></script>
</head>
 <body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<div id="allmap" style="height: 800px;"></div>
	</div>
</div>
</body>
</html>
 <script type="text/javascript">
     // 百度地图API功能
     var map = new BMap.Map("allmap");
     var point = new BMap.Point(116.404, 39.915);
     map.centerAndZoom(point, 15);
     map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
     // 编写自定义函数,创建标注
     function addMarker(point,label){
         var marker = new BMap.Marker(point);   //创建标注
         map.addOverlay(marker);          //将标注添加到地图中
         marker.setLabel(label);
     }
     // 随机向地图添加25个标注
     var bounds = map.getBounds();
     var sw = bounds.getSouthWest();
     var ne = bounds.getNorthEast();
     var lngSpan = Math.abs(sw.lng - ne.lng);
     var latSpan = Math.abs(ne.lat - sw.lat);
     for (var i = 0; i < 25; i ++) {
         var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7), ne.lat - latSpan * (Math.random() * 0.7));
         var label = new BMap.Label("我是id="+i,{offset:new BMap.Size(20,-10)});
         addMarker(point,label);
     }
     parent.$.messager.progress('close');
 </script>