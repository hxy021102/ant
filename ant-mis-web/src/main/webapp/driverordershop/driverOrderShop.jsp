 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>DriverOrderShop管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/driverOrderShopController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/driverOrderShopController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/driverOrderShopController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/driverAccountController/view')}">
	<script type="text/javascript">
		$.canViewAccount = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/view')}">
	<script type="text/javascript">
        $.canViewDeliverOrder = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/driverOrderShopController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'updatetime',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
				},  {
				field : 'addtime',
                title : '添加时间',
				width : 50
				}, {
				field : 'updatetime',
                title : '更新时间',
				width : 50
				}, {
                field : 'shopName',
                title : '门店名称',
                width : 70
                },{
				field : 'deliverOrderShopId',
                title : '门店运单ID',
				width : 30,
                formatter : function (value, row) {
                   if ($.canViewDeliverOrder)
                     return '<a onclick="viewDeliverOrder(' + row.deliverOrderShopId + ')">' + row.deliverOrderShopId + '</a>';
                   return value;
                }
				}, {
                field : 'userName',
                title : '骑手账号',
                width : 40,
                formatter : function (value, row) {
                    if ($.canViewAccount && value != undefined)
                        return '<a onclick="viewAccount(' + row.driverAccountId + ')">' + row.userName + '</a>';
                    return value == null ? "" : value;

                }
                }, {
				field : 'statusName',
                title : '运单状态',
				width : 30
				}, {
				field : 'amount',
                title : '金额',
				width : 30,
				align:"right",
                formatter: function (value) {
                    if (value == null)
                        return "";
                    return $.formatMoney(value);
                }
				}, {
				field : 'payStatusName',
                title : '支付状态',
				width : 30
				}] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
		});
	});

    function viewDeliverOrder(id) {
        var href = '${pageContext.request.contextPath}/deliverOrderController/view?deliverOrderShopId=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '运单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
        });
    }

    function viewAccount(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '查看数据',
            width : 780,
            height : 400,
            href : '${pageContext.request.contextPath}/driverAccountController/view?id=' + id
        });
    }


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
				$.post('${pageContext.request.contextPath}/driverOrderShopController/delete', {
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
			href : '${pageContext.request.contextPath}/driverOrderShopController/editPage?id=' + id,
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
			href : '${pageContext.request.contextPath}/driverOrderShopController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/driverOrderShopController/addPage',
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
	        url:'${pageContext.request.contextPath}/driverOrderShopController/download',
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
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 120px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
						<tr>
							<th>门店运单ID</th>
							<td>
								<input type="text" name="deliverOrderShopId" maxlength="10" class="span2"/>
							</td>
							<th>运单状态</th>
							<td>
								<jb:select dataType="DDSS" name="status"></jb:select>
							</td>
							<th>支付状态</th>
							<td>
								<jb:select dataType="DDPS" name="payStatus"></jb:select>
							</td>
						</tr>
					    <tr>
							<th>门店名称</th>
							<td>
								<jb:selectGrid dataType="shopId" name="shopId" value="${shopId}"></jb:selectGrid>
							</td>
							<th>骑手账号</th>
							<td>
								<input type="text" name="userName" maxlength="15" class="span2"/>
							</td>
						    <th>添加时间</th>
						    <td>
							 <input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TmbUser.FORMAT_ADDTIME%>'})" id="addtimeBegin" name="addtimeBegin"/>至
							 <input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TmbUser.FORMAT_ADDTIME%>'})" id="addtimeEnd" name="addtimeEnd"/>
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
		<%--<c:if test="${fn:contains(sessionInfo.resourceList, '/driverOrderShopController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>--%>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/driverOrderShopController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>