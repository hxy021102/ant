<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
</head>
<body class="easyui-layout">

	<div data-options="region:'center',border:false">
		<div id="index_tabs" class="easyui-tabs" data-options="fit:true">
			<div title="基础数据" data-options="href:'api_base.jsp'"
				style="padding: 1px"></div>
			<div title="潜水首页(废弃)" data-options="href:'api_homePage.jsp'"
				style="padding: 1px"></div>
			<div title="个人中心" data-options="href:'api_people_center.jsp'"
				style="padding: 1px"></div>
			<div title="装备" data-options="href:'api_equip.jsp'"
				style="padding: 1px"></div>
			<div title="潜点" data-options="href:'api_address.jsp'"
				style="padding: 1px"></div>
			<div title="度假村/潜店" data-options="href:'api_store.jsp'"
				style="padding: 1px"></div>		
			<div title="活动" data-options="href:'api_activity.jsp'"
				style="padding: 1px"></div>
			<div title="船宿" data-options="href:'api_travel.jsp'"
				style="padding: 1px"></div>
			<div title="视频" data-options="href:'api_course.jsp'"
				style="padding: 1px"></div>
			<div title="潜记" data-options="href:'api_log.jsp'"
				style="padding: 1px"></div>
			<div title="潜聊" data-options="href:'api_dive_chat.jsp'"
				style="padding: 1px"></div>
			<div title="赞/收藏" data-options="href:'api_point.jsp'"
				style="padding: 1px"></div>	
			<div title="订单" data-options="href:'api_order.jsp'"
				style="padding: 1px"></div>	
			<div title="提交审核" data-options="href:'api_audit.jsp'"
				style="padding: 1px"></div>	
			<div title="APP错误日志上传" data-options="href:'api_app_errorlog.jsp'"
				style="padding: 1px"></div>
		</div>
	</div>
</body>
</html>