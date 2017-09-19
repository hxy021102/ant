<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div id="index_chat_tabs" class="easyui-tabs" data-options="fit:true">		
		<div title="潜友搜索" data-options="href:'api_dive_chat_search.jsp'"
			style="padding: 1px"></div>
		<div title="潜友列表" data-options="href:'api_dive_chat_friendList.jsp'"
			style="padding: 1px"></div>
		<div title="附近人" data-options="href:'api_dive_chat_nearbyList.jsp'"
			style="padding: 1px"></div>
	</div>

</body>
</html>