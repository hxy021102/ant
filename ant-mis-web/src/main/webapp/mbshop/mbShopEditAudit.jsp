<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbShop" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbShopController/editAudit',
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
		createMap();
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${mbShop.id}"/>
			<input type="hidden" name="auditStatus" value="${mbShop.auditStatus}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbShop.ALIAS_NAME%>
					</th>
					<td>
						${mbShop.name}
					</td>
					<th><%=TmbShop.ALIAS_REGION_ID%>
					</th>
					<td>
						<jb:selectGrid dataType="region" name="regionId" required="true" value="${mbShop.regionId}"></jb:selectGrid>
					</td>
				</tr>
				<tr>
					<th><%=TmbShop.ALIAS_SHOP_TYPE%>
					</th>
					<td colspan="3">
						<jb:select dataType="ST" name="shopType" mustSelect="true" required="true"></jb:select>
					</td>
					<th></th>
					<td>
						<jb:selectSql name="salesLoginId" dataType="SQ018"></jb:selectSql>
					</td>

				</tr>
				<tr>
					<th><%=TmbShop.ALIAS_ADDRESS%>
					</th>
					<td colspan="3">
						${mbShop.address}
						<input name="longitude" id="gisLng" type="hidden" value="${mbShop.longitude}">
						<input name="latitude" id="gisLat" type="hidden" value="${mbShop.latitude}">
						<input name="address" id="address" type="hidden" value="${mbShop.address}">
					</td>
				</tr>
				<tr>
					<th><%=TmbShop.ALIAS_CONTACT_PHONE%>
					</th>
					<td>
						${mbShop.contactPhone}
					</td>
					<th><%=TmbShop.ALIAS_CONTACT_PEOPLE%>
					</th>
					<td>
						${mbShop.contactPeople}
					</td>
				</tr>
				<tr>
					<th>原因
					</th>
					<td colspan="3">
						<textarea class="easyui-validatebox" data-options="required:true" style="width: 90%" name="auditRemark">${mbShop.auditRemark}</textarea>
					</td>
				</tr>
			</table>
		</form>
		<div id="allmap" style="height: 500px;"></div>
	</div>
</div>