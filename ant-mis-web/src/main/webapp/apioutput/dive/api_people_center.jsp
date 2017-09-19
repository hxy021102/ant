<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body >
	<div id="index_people_center_tabs" class="easyui-tabs" data-options="fit:true">
		<div title="注册" data-options="href:'api_register.jsp'"
			style="padding: 1px"></div>
		<div title="登录" data-options="href:'api_login.jsp'"
			style="padding: 1px"></div>
		<div title="第三方登录" data-options="href:'api_login_thirdparty.jsp'"
			style="padding: 1px"></div>
		<div title="忘记密码" data-options="href:'api_forget_password.jsp'"
			style="padding: 1px"></div>
		<div title="修改密码" data-options="href:'api_updatePass.jsp'"
			style="padding: 1px"></div>
		<div title="检查更新" data-options="href:'api_checkUpdate.jsp'"
			style="padding: 1px"></div>
		<!-- <div title="个人主页" data-options="href:'api_people_center_personHome.jsp'"
			style="padding: 1px"></div> -->
		<div title="个人主页/信息（查）" data-options="href:'api_people_center_personInfo.jsp'"
			style="padding: 1px"></div>
		<div title="个人信息（改）" data-options="href:'api_people_center_personInfoUpdate.jsp'"
			style="padding: 1px"></div>
		<div title="头像上传" data-options="href:'api_people_center_headImageUpload.jsp'"
			style="padding: 1px"></div>
		<div title="潜水认证（查）" data-options="href:'api_people_center_certificateInfo.jsp'"
			style="padding: 1px"></div>
		<div title="潜水认证（改）" data-options="href:'api_people_center_certificateInfoUpdate.jsp'"
			style="padding: 1px"></div>
		<div title="收藏主页" data-options="href:'api_people_center_collectHome.jsp'"
			style="padding: 1px"></div>
		<div title="船宿收藏列表" data-options="href:'api_people_center_travelCollectList.jsp'"
			style="padding: 1px"></div>
		<div title="潜点收藏列表" data-options="href:'api_people_center_addressCollectList.jsp'"
			style="padding: 1px"></div>
		<div title="装备收藏列表" data-options="href:'api_people_center_equipCollectList.jsp'"
			style="padding: 1px"></div>
		<div title="活动收藏列表" data-options="href:'api_people_center_activityCollectList.jsp'"
			style="padding: 1px"></div>
		<div title="视频收藏列表" data-options="href:'api_people_center_courseCollectList.jsp'"
			style="padding: 1px"></div>
		<div title="潜水日志收藏列表" data-options="href:'api_people_center_logCollectList.jsp'"
			style="padding: 1px"></div>
		<div title="退出登录" data-options="href:'api_logout.jsp'"
			style="padding: 1px"></div>
	</div>
</body>
</html>