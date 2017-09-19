<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div id="index_store_tabs" class="easyui-tabs" data-options="fit:true">
		
		<div title="列表" data-options="href:'api_store_list.jsp'"
			style="padding: 1px"></div>
		<div title="热门" data-options="href:'api_store_hot.jsp'"
			style="padding: 1px"></div>
		<div title="搜索" data-options="href:'api_store_search.jsp'"
			style="padding: 1px"></div>
		<div title="详情" data-options="href:'api_store_detail.jsp'"
			style="padding: 1px"></div>
	</div>

</body>
</html>