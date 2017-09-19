<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div id="index_activity_tabs" class="easyui-tabs" data-options="fit:true">
		<div title="活动列表" data-options="href:'api_activity_list.jsp'"
			style="padding: 1px"></div>
		<div title="详情页" data-options="href:'api_activity_detail.jsp'"
			style="padding: 1px"></div>
		<div title="添加评论" data-options="href:'api_activity_add_comment.jsp'"
			style="padding: 1px"></div>	
		<div title="报名" data-options="href:'api_activity_apply.jsp'"
			style="padding: 1px"></div>			
		<div title="取消报名" data-options="href:'api_activity_unapply.jsp'"
			style="padding: 1px"></div>			
		<div title="首页热门" data-options="href:'api_activity_hot.jsp'"
			style="padding: 1px"></div>			
	</div>

</body>
</html>