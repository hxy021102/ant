<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbWarehouse" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbWarehouseController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		function createMap(x,y){

			var lng = $('#gisLng');
			var lat = $('#gisLat');
			//  百度地图API功能
			var map = new BMap.Map("allmap");  // 创建Map实例
			map.enableScrollWheelZoom(true);
			var marker;
			map.addEventListener("click",function(e){
				//marker.setPosition
				var p = marker.getPosition();
				p.lng = e.point.lng;
				p.lat = e.point.lat;
				marker.setPosition(p);
				geoc.getLocation(p, function(rs){
					var addComp = rs.addressComponents;
					//var addr = addComp.city+"|"+addComp.district;
					lng.val(p.lng);
					lat.val(p.lat);
				});
			});
			var cityName = $("#address").val();
			var point;
			var geoc = new BMap.Geocoder();

			if(x&&y){
				point = new BMap.Point(x,y);
			}else if(cityName){
				var myGeo = new BMap.Geocoder();
				// 将地址解析结果显示在地图上,并调整地图视野
				myGeo.getPoint(cityName, function(point){
					if (point) {

						map.centerAndZoom(point, 15);
						marker = new BMap.Marker(point);
						map.addOverlay(marker);
						marker.enableDragging();
						geoc.getLocation(point, function(rs){
							//var addComp = rs.addressComponents;
							//var addr = addComp.city+"|"+addComp.district;
							lng.val(p.lng);
							lat.val(p.lat);
						});
					}else{
						//isR = true;
						alert("您选择地址没有解析到结果!");
					}
				});
				return;
			}else{

				var flg = false;
				if(lng){
					var lngVal = lng.val();
					var latVal = lat.val();
					point = new BMap.Point(lngVal,latVal);
					flg = true;

				}
				if(!flg)
					point = new BMap.Point(121.487899,31.249162);
			}

			marker = new BMap.Marker(point);// 创建标注
			map.addOverlay(marker);
			map.centerAndZoom(point, 15);
			marker.enableDragging();
		}
		$('#mapDingwei').click(createMap);
		var gisLng = $('#gisLng');
		var gisLat = $('#gisLat');
		if (gisLng.val() && gisLat.val()) {
			createMap(gisLng.val(), gisLat.val());
		}
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
				<input type="hidden" name="id" value = "${mbWarehouse.id}"/>
			<table class="table table-hover table-condensed">
			<tr>
				<th><%=TmbWarehouse.ALIAS_CODE%></th>
				<td>
					<input class="span2" name="code" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbWarehouse.code}"/>
				</td>
				<th><%=TmbWarehouse.ALIAS_NAME%></th>
				<td>
					<input class="span2" name="name" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbWarehouse.name}"/>
				</td>
			</tr>	
			<tr>
				<th><%=TmbWarehouse.ALIAS_WAREHOUSE_TYPE%></th>
				<td>
					<jb:select dataType="WT" name="warehouseType" value="${mbWarehouse.warehouseType}" mustSelect="true" required="true"></jb:select>
				</td>
				<th><%=TmbWarehouse.ALIAS_REGION_ID%></th>
                <td>
                    <jb:selectGrid dataType="region" name="regionId" value="${mbWarehouse.regionId}" required="true"></jb:selectGrid>
                </td>
            </tr>
            <tr>
				<th><%=TmbWarehouse.ALIAS_ADDRESS%></th>
				<td colspan="3">
					<input name="longitude" id="gisLng" type="hidden" value="${mbWarehouse.longitude}">
					<input name="latitude" id="gisLat" type="hidden" value="${mbWarehouse.latitude}">
					<input class="easyui-validatebox span2" style="width: 80%" data-options="required:true" name="address" id="address" type="text" value="${mbWarehouse.address}"/>
					<input type="button" value="定位" id="mapDingwei">
				</td>
			</tr>
			</table>				
		</form>
		<div id="allmap" style="height: 500px;"></div>
	</div>
</div>