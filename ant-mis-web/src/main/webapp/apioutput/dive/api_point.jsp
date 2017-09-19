<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div id="index_point_tabs" class="easyui-tabs" data-options="fit:true">		
		<div title="收藏" data-options="href:'api_point_collect.jsp'"
			style="padding: 1px"></div>
		<div title="点赞" data-options="href:'api_point_praise.jsp'"
			style="padding: 1px"></div>
		<div title="取消收藏" data-options="href:'api_point_cancelCollect.jsp'"
			style="padding: 1px"></div>
		<div title="取消赞" data-options="href:'api_point_cancelPraise.jsp'"
			style="padding: 1px"></div>
	</div>

</body>
</html>