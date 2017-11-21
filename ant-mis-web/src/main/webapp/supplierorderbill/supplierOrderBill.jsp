<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>SupplierOrderBill管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierOrderBillController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierOrderBillController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierOrderBillController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierOrderBillController/editStatus')}">
	<script type="text/javascript">
        $.editStatus = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/supplierOrderBillController/dataGrid',
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
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '账单ID',
				width : 30,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
				}, {
				field : 'supplierName',
				title : '供应商名称',
				width : 90
				}, {
				field : 'statusName',
				title : '审核状态',
				width : 50		
				}, {
				field : 'remark',
				title : '审核备注',
				width : 50		
				}, {
				field : 'amount',
				title : '总金额',
				width : 50	,
				formatter : function (value,row,index) {
					return $.formatMoney(row.amount)
                }
				}, {
				field : 'startDate',
				title : '开始日期',
				width : 80
				}, {
				field : 'endDate',
				title : '结束日期',
				width : 80
				}, {
				field : 'payWayName',
				title : '结算方式',
				width : 50
			}, {
				field : 'action',
				title : '操作',
				width : 50,
				formatter : function(value, row, index) {
					var str = '';
					<%--if ($.canEdit) {--%>
						<%--str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');--%>
					<%--}--%>
					<%--str += '&nbsp;';--%>
					<%--if ($.canDelete) {--%>
						<%--str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_delete.png');--%>
					<%--}--%>
					str += '&nbsp;';
					if ($.editStatus && row.status == 'BAS01') {
						str += $.formatString('<img onclick="editStatusFun(\'{0}\');" src="{1}" title="审核"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/joystick.png');
					}
					return str;
				}
			} ] ],
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
				$.post('${pageContext.request.contextPath}/supplierOrderBillController/delete', {
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
			href : '${pageContext.request.contextPath}/supplierOrderBillController/editPage?id=' + id,
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
			href : '${pageContext.request.contextPath}/supplierOrderBillController/view?id=' + id +"&isView="+ 1
		});
	}
	//审核账单
    function editStatusFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '审核数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/supplierOrderBillController/view?id=' + id ,
            buttons : [ {
                text: '同意',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=isAgree]").val(true);
                    f.submit();
                }
            }, {
				text: '拒绝',
				handler: function () {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;
					var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=isAgree]").val(false);
					f.submit();
                    }
                }]
        });
	}
	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/supplierOrderBillController/addPage',
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
	        url:'${pageContext.request.contextPath}/supplierOrderBillController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
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
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
						<tr>	
							<th style="width:50px">供应商</th>
							<td>
								<jb:selectGrid  dataType="deliverSupplierId" name="supplierId"></jb:selectGrid>
							</td>
							<th>创建时间</th>
							<td>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY%>'})" id="startDateBegin" name="startDate"/>
							至
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY%>'})" id="endDateBegin" name="endDate"/>
							</td>
							<%--<th>结算方式</th>--%>
							<%--<td>--%>
								<%--<jb:select dataType="DPW" name="payWay"></jb:select>--%>
							<%--</td>--%>
							<%--<th>审核状态</th>--%>
							<%--<td>--%>
											<%--<jb:select dataType="BAS" name="status"></jb:select>	--%>
							<%--</td>--%>
						</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierOrderBillController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>