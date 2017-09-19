<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div id="index_log_tabs" class="easyui-tabs" data-options="fit:true">
		<div title="潜水日志" data-options="href:'api_people_center_logList.jsp'"
			 style="padding: 1px"></div>
		<div title="电子日志添加" data-options="href:'api_people_center_addElectronLog.jsp'"
			 style="padding: 1px"></div>
		<div title="纸张日志添加" data-options="href:'api_people_center_addPaperLog.jsp'"
			 style="padding: 1px"></div>
		<div title="日志详情" data-options="href:'api_people_center_logDetail.jsp'"
			 style="padding: 1px"></div>
		<div title="日志删除" data-options="href:'api_people_center_del_log.jsp'"
			 style="padding: 1px"></div>
		<div title="日志图片删除" data-options="href:'api_people_center_del_logImage.jsp'"
			 style="padding: 1px"></div>
		<div title="添加日志评论" data-options="href:'api_people_center_add_logcomment.jsp'"
			 style="padding: 1px"></div>
		<div title="明细列表" data-options="href:'api_log_detail_list.jsp'"
			 style="padding: 1px"></div>
		<div title="明细添加" data-options="href:'api_log_detail_add.jsp'"
			 style="padding: 1px"></div>
		<div title="明细删除" data-options="href:'api_log_detail_del.jsp'"
			 style="padding: 1px"></div>
	</div>

</body>
</html>