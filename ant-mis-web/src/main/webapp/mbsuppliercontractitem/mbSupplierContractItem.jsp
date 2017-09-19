<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierContractItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
<title>MbSupplierContractItem管理</title>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractItemController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractItemController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractItemController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var mbsupitemDataGrid;
    var chosenContractId;
	$(function() {
        mbsupitemDataGrid= $('#mbsupitemDataGrid').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
				}, {
				field : 'itemName',
				title : '<%=TmbSupplierContractItem.ALIAS_ITEM_NAME%>',
				width : 100
				}, {
				field : 'price',
				title : '<%=TmbSupplierContractItem.ALIAS_PRICE%>',
				width : 30,
                formatter:function(value){
                    return $.formatMoney(value);
                }
			}, {
				field : 'action',
				title : '操作',
				width : 20,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						str += $.formatString('<img onclick="supEditFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					str += '&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="supDeleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					return str;
				}
			} ] ],
			toolbar : '#supitemtoolbar',
			onLoadSuccess : function() {
				$('#mbsupitemSearchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
		});
	});

	function supDeleteFun(id) {
		if (id == undefined) {
			var rows = mbsupitemDataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/mbSupplierContractItemController/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
                        mbsupitemDataGrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function supEditFun(id) {
		if (id == undefined) {
			var rows = mbsupitemDataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbSupplierContractItemController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = mbsupitemDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function viewFun(id) {
		if (id == undefined) {
			var rows = mbsupitemDataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbSupplierContractItemController/view?id=' + id
		});
	}

	function supitemaddFun() {
        if (chosenContractId != undefined) {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbSupplierContractItemController/addPage?supplierContractId=' + chosenContractId,
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = mbsupitemDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
        } else {
            $.messager.alert('错误', "请先选择合同！", 'error');
        }
	}
	function downloadTable(){
		var options = mbsupitemDataGrid.datagrid("options");
		var $colums = [];
		$.merge($colums, options.columns);
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/mbSupplierContractItemController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#mbsupitemSearchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;

       	 }
        });
	}
	function  supitemsearchFun() {
        mbsupitemDataGrid.datagrid('load', $.serializeObject($('#mbsupitemSearchForm')));
	}
	function cleanFun() {
		$('#mbsupitemSearchForm input').val('');
        mbsupitemDataGrid.datagrid('load', {});
	}
    function listsupContractItem(supplierContractId) {
        var options = mbsupitemDataGrid.datagrid('options');
        options.url = '${pageContext.request.contextPath}/mbSupplierContractItemController/dataGrid';
        $('#supplierContractId').val(supplierContractId);
        chosenContractId = supplierContractId;
        mbsupitemDataGrid.datagrid('load', $.serializeObject($('#mbsupitemSearchForm')));
    }
</script>
</head>
<body>
	<div id="supitemtoolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractItemController/addPage')}">
			<a onclick="supitemaddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractItemController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>
</body>
</html>