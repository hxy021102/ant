<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>属性管理</title>
<jsp:include page="../inc.jsp"></jsp:include>

<script type="text/javascript">
	var properties_index_layout;
	var center_index_layout;
	var itemId = ${id};
	$(function() {
		properties_index_layout = $('#properties_index_layout').layout({
			fit : true
		});

		center_index_layout = $('#center_index_layout').layout({
			fit : true
		});
	});
</script>
</head>
<body>
	<div id="properties_index_layout">
		<div data-options="region:'center'" style="overflow: hidden;">
			<div id="center_index_layout">
				<div data-options="region:'west',href:'${pageContext.request.contextPath}/fmproperties/fmProperties.jsp',split:true" style="width: 600px; overflow: hidden;"></div>
				<div data-options="region:'center',href:'${pageContext.request.contextPath}/fmoptions/fmOptions.jsp',split:true" style="overflow: hidden;">

				</div>
			</div>
		</div>
	</div>
</body>
</html>