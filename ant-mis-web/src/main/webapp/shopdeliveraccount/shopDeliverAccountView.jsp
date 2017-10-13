<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
	});
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>
					<th style="width: 70px">账号：</th>
					<td>
						${shopDeliverAccount.userName}							
					</td>
					<th style="width: 90px">昵称：</th>
					<td>
						${shopDeliverAccount.nickName}
					</td>
					<th>性别：</th>
					<td style="width: 70px">
						<c:if test="${shopDeliverAccount.sex == 1}">
							男
						</c:if>
						<c:if test="${shopDeliverAccount.sex == 2}">
							女
						</c:if>

					</td>
					<th>头像：</th>
					<td rowspan="2">
						<img src="${shopDeliverAccount.icon}" width="80px" height="80px"/>
					</td>
				</tr>
				<tr>
					<th>第三方ID：</th>
					<td>
						${shopDeliverAccount.refId}							
					</td>
					<th>第三方类型：</th>
					<td colspan="4">
						${shopDeliverAccount.refType}							
					</td>
				</tr>		
		</table>
	</div>
</div>
</body>
</html>