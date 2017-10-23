<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbBalanceLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbBalanceLog管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbBalanceLogController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbBalanceLogController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbBalanceLogController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		$('#searchForm table').show();
		parent.$.messager.progress('close');
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/mbBalanceLogController/dataGridFlow',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : false,
			idField : 'id',
			/*pageSize : 10,*/
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
            showFooter : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
				}, {
				field : 'shopName',
				title : '门店名称',
				width : 45,
                styler:function(value,row,index){
                    if (value == '合计'){
                        return 'font-weight: bold;';
                    }
                }
				},{
                field : 'updatetime',
                title : '下单时间',
                width : 40
			    },{
				field : 'amount',
				title : '<%=TmbBalanceLog.ALIAS_AMOUNT%>',
				width : 20
				},{
				field : 'refTypeName',
				title : '<%=TmbBalanceLog.ALIAS_REF_TYPE%>',
				width : 30
				}, {
				field : 'reason',
				title : '<%=TmbBalanceLog.ALIAS_REASON%>',
				width : 100
				}, {
				field : 'remark',
				title : '<%=TmbBalanceLog.ALIAS_REMARK%>',
				width : 50		
			}] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
				$(this).datagrid('tooltip');
			}
		});
	});


    function deleteFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/mbBalanceLogController/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbBalanceLogController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbBalanceLogController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbBalanceLogController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	function downloadTable(){
		var options = dataGrid.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/mbBalanceLogController/downloadBalanceLog',
	        onSubmit: function(param){
				var isValid = $('#searchForm').form('validate');
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	return isValid;
       	 }
        }); 
	}
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<td>
							<strong>下单时间&nbsp;&nbsp;</strong><input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbBalanceLog.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'updatetimeEnd\',{M:-1});}',maxDate:'#F{$dp.$D(\'updatetimeEnd\',{d:-1});}'})" id="updatetimeBegin" name="updatetimeBegin"/>
							至<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbBalanceLog.FORMAT_UPDATETIME%>',minDate:'#F{$dp.$D(\'updatetimeBegin\',{d:1});}',maxDate:'#F{$dp.$D(\'updatetimeBegin\',{M:1});}'})" id="updatetimeEnd" name="updatetimeEnd"/>
						</td>
						<td>
							<strong>门店名称&nbsp;&nbsp;</strong><jb:selectGrid dataType="shopId" name="shopId" params="{onlyMain:true}"></jb:selectGrid>
						</td>
						<td>
							<strong>业务类型&nbsp;&nbsp;</strong><jb:selectSql dataType="SQ016" name="refTypes"  multiple="true" ></jb:selectSql>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbBalanceLogController/downloadBalanceLog')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>