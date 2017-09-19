<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TfmOptions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 

<c:if test="${fn:contains(sessionInfo.resourceList, '/fmOptionsController/editPage')}">
	<script type="text/javascript">
		$.canEditOption = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fmOptionsController/delete')}">
	<script type="text/javascript">
		$.canDeleteOption = true;
	</script>
</c:if>

<script type="text/javascript">
	var dataGridOption;
	$(function() {
		dataGridOption = $('#dataGridOption').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'seq',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : 'ID',
				width : 150,
				hidden : false
				},{
				field : 'propertiesId',
				title : '<%=TfmOptions.ALIAS_PROPERTIES_ID%>',
				width : 50,
				hidden : true
				}, {
				field : 'value',
				title : '<%=TfmOptions.ALIAS_VALUE%>',
				width : 50		
				}, {
				field : 'seq',
				title : '<%=TfmOptions.ALIAS_SEQ%>',
				width : 30
			}, {
				field : 'action',
				title : '操作',
				width : 30,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEditOption) {
						str += $.formatString('<img onclick="editFunOption(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					str += '&nbsp;';
					if ($.canDeleteOption) {
						str += $.formatString('<img onclick="deleteFunOption(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbarOption',
			onLoadSuccess : function() {
				$('#searchFormOption table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
		});
	});

	function deleteFunOption(id) {
		if (id == undefined) {
			var rows = dataGridOption.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/fmOptionsController/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGridOption.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function editFunOption(id) {
		if (id == undefined) {
			var rows = dataGridOption.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 120,
			href : '${pageContext.request.contextPath}/fmOptionsController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGridOption;//因为添加成功之后，需要刷新这个dataGridOption，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#formOption');
					f.submit();
				}
			} ]
		});
	}

	/*function viewFunOption(id) {
		if (id == undefined) {
			var rows = dataGridOption.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/fmOptionsController/view?id=' + id
		});
	}*/

	function addFunOption() {
		if(!dataGridOption.propertiesId)return;
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 120,
			href : '${pageContext.request.contextPath}/fmOptionsController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGridOption;//因为添加成功之后，需要刷新这个dataGridOption，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#formOption');
					f.find("#propertiesIdAdd").val(dataGridOption.propertiesId);
					f.submit();
				}
			} ]
		});
	}
	/*function downloadTable(){
		var options = dataGridOption.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/fmOptionsController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchFormOption')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}*/
	function searchFunOption(propertiesId) {
		var opts = dataGridOption.datagrid("options");
		opts.url = '${pageContext.request.contextPath}/fmOptionsController/dataGrid';
		$("#propertiesId").val(propertiesId);
		dataGridOption.propertiesId = propertiesId;
		dataGridOption.datagrid('load', $.serializeObject($('#searchFormOption')));
	}
	function cleanFunOption() {
		$('#searchFormOption input').val('');
		/*var opts = dataGridOption.datagrid("options");
		opts.url = null;*/
		dataGridOption.propertiesId = null;
		dataGridOption.datagrid('loadData',{"rows":[],"total":0});
	}
</script>

<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<form id="searchFormOption" style="display:none;">
			<input type="hidden" name="propertiesId" id="propertiesId">
		</form>
		<table id="dataGridOption"></table>
	</div>
</div>
<div id="toolbarOption" style="display: none;">
	<c:if test="${fn:contains(sessionInfo.resourceList, '/fmOptionsController/addPage')}">
		<a onclick="addFunOption();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'common_add'">添加</a>
	</c:if>
</div>	
