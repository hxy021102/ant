<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbContractItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbContractItem管理</title>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbContractItemController/editPage')}">
	<script type="text/javascript">
		$.canItemEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbContractItemController/delete')}">
	<script type="text/javascript">
		$.canItemDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbContractItemController/view')}">
	<script type="text/javascript">
		$.canItemView = true;
	</script>
</c:if>
<script type="text/javascript">
	var itemDataGrid;
    var chosenContractId;
	$(function() {
		itemDataGrid = $('#itemDataGrid').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : false,
			idField : 'id',
			pageSize : 100,
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
				title : '<%=TmbContractItem.ALIAS_ITEM_NAME%>',
				width : 50		
				}, {
				field : 'price',
				title : '<%=TmbContractItem.ALIAS_PRICE%>',
				width : 20,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'action',
				title : '操作',
				width : 20,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canItemEdit) {
						str += $.formatString('<img onclick="itemEditFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					str += '&nbsp;';
					if ($.canItemDelete) {
						str += $.formatString('<img onclick="itemDeleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					return str;
				}
			} ] ],
			toolbar : '#itemToolbar',
			onLoadSuccess : function() {
				$('#itemSearchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
		});
	});

	function itemDeleteFun(id) {
		if (id == undefined) {
			var rows = itemDataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/mbContractItemController/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						itemDataGrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function itemEditFun(id) {
		if (id == undefined) {
			var rows = itemDataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbContractItemController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = itemDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function itemViewFun(id) {
		if (id == undefined) {
			var rows = itemDataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbContractItemController/view?id=' + id
		});
	}

	function itemAddFun() {
	    if (chosenContractId != undefined) {
            parent.$.modalDialog({
                title : '添加数据',
                width : 780,
                height : 500,
                href : '${pageContext.request.contextPath}/mbContractItemController/addPage?contractId=' + chosenContractId,
                buttons : [ {
                    text : '添加',
                    handler : function() {
                        parent.$.modalDialog.openner_dataGrid = itemDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                } ]
            });
        } else {
            $.messager.alert('错误', "请先选择合同！", 'error');
        }
	}

	function itemUpload() {
		if (chosenContractId != undefined) {
			parent.$.modalDialog({
				title : '批量导入',
				width : 780,
				height : 200,
				href : '${pageContext.request.contextPath}/mbContractItemController/uploadPage?contractId=' + chosenContractId,
				buttons : [ {
					text : '保存',
					handler : function() {
						parent.$.modalDialog.openner_dataGrid = itemDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			});
		} else {
			$.messager.alert('错误', "请先选择合同！", 'error');
		}
	}

	function itemDownloadTable(){
		var options = itemDataGrid.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#itemDownloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/mbContractItemController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#itemSearchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function itemSearchFun() {
		itemDataGrid.datagrid('load', $.serializeObject($('#itemSearchForm')));
	}
	function itemCleanFun() {
		$('#itemSearchForm input').val('');
		itemDataGrid.datagrid('load', {});
	}

	function listContractItem(contractId) {
        var options = itemDataGrid.datagrid('options');
        options.url = '${pageContext.request.contextPath}/mbContractItemController/dataGrid';
        $('#contractId').val(contractId);
        chosenContractId = contractId;
        itemDataGrid.datagrid('load', $.serializeObject($('#itemSearchForm')));
    }
</script>
</head>
<body>
	<div id="itemToolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbContractItemController/addPage')}">
			<a onclick="itemAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbContractItemController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="itemDownloadTable();">导出</a>
			<form id="itemDownloadTable" target="itemDownloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="itemDownloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbContractItemController/uploadPage')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_add',plain:true" onclick="itemUpload();">导入</a>
		</c:if>
	</div>	
</body>
</html>