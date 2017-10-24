<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/resourceController/getResourceUserList?resourceId=${resourceId}',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : false,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : true,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150
			}, {
				field : 'name',
				title : '登录名称',
				width : 80,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'nickname',
				title : '姓名',
				width : 70
			}, {
				field : 'email',
				title : '邮箱',
				width : 100
			}, {
				field : 'phone',
				title : '手机号',
				width : 100
			}, {
				field : 'utypeName',
				title : '状态',
				width : 40
			}, {
				field : 'orgNameName',
				title : '部门',
				width : 100
			}, {
				field : 'roleNames',
				title : '所属角色名称',
				width : 150
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

			}
		});

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
</div>