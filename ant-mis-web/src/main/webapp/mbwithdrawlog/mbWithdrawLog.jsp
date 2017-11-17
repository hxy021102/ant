<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbWithdrawLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbWithdrawLog管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWithdrawLogController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWithdrawLogController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWithdrawLogController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWithdrawLogController/editAudit')}">
	<script type="text/javascript">
		$.canEditAuditt = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbUserController/viewBalance')}">
	<script type="text/javascript">
		$.canViewBalance = true;
	</script>
</c:if>

<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/mbWithdrawLogController/dataGridView',
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
				title : '编号',
				width : 150,
				hidden : true
				}, {
				field : 'addtime',
				title : '<%=TmbWithdrawLog.ALIAS_ADDTIME%>',
				width : 90
				}, {
                field : 'shopId',
                title : '门店ID',
                width : 40
                },{
                field : 'shopName',
                title : '门店名称',
                width : 80
                }, {
                field : 'applyLoginId',
                title : '申请人ID',
                width : 40
                },{
                field : 'applyLoginName',
                title : '申请人名称',
                width : 50
                },{
				field : 'balanceId',
				title : '<%=TmbWithdrawLog.ALIAS_BALANCE_ID%>',
				width : 40,
				formatter:function (value, row) {
                    if ($.canViewBalance && row.shopId !=null)
                        return '<a onclick="viewBalance(' + row.shopId + ')">' + value + '</a>';
                    return value;
                }
				}, {
				field : 'amount',
				title : '<%=TmbWithdrawLog.ALIAS_AMOUNT%>',
				width : 40,
				align: "right",
				formatter: function (value, row, index) {
				    if (value != null)
						return $.formatMoney(value);
				    else
				        return '--';
                }
				}, {
				field : 'refTypeName',
				title : '<%=TmbWithdrawLog.ALIAS_REF_TYPE%>',
				width : 80
				},{
                    field : 'receiverAccount',
                    title : '收款账户',
                    width : 135
                }, {
				field : 'recevierTime',
				title : '<%=TmbWithdrawLog.ALIAS_REMITTER_TIME%>',
				width : 90
				}, {
				field : 'handleStatusName',
				title : '<%=TmbWithdrawLog.ALIAS_HANDLE_STATUS%>',
				width : 40
				}, {
				field : 'handleLoginName',
				title : '<%=TmbWithdrawLog.ALIAS_HANDLE_LOGIN_ID%>',
				width : 40
				}, {
				field : 'payCode',
				title : '<%=TmbWithdrawLog.ALIAS_PAY_CODE%>',
				width : 60
				}, {
				field : 'content',
				title : '<%=TmbWithdrawLog.ALIAS_CONTENT%>',
				width : 50,
				hidden:true
				}, {
				field : 'action',
				title : '操作',
				width : 40,
				formatter : function(value, row, index) {
					var str = '';
					<%--if ($.canEdit) {--%>
						<%--str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');--%>
					<%--}--%>
//					str += '&nbsp;';
                    if ($.canDelete) {
                        //str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                    }
                    str += '&nbsp;';
                    if ($.canView) {
                        str += $.formatString('<img onclick="viewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/link.png');
                    }
					str += '&nbsp;';
					if ($.canEditAuditt && row.handleStatus != 'HS02'){
                        str += $.formatString('<img onclick="editAuditFun(\'{0}\');" src="{1}" title="审核"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/joystick.png');
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
				$.post('${pageContext.request.contextPath}/mbWithdrawLogController/delete', {
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
			href : '${pageContext.request.contextPath}/mbWithdrawLogController/editPage?id=' + id,
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
			href : '${pageContext.request.contextPath}/mbWithdrawLogController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbWithdrawLogController/addPage',
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
	        url:'${pageContext.request.contextPath}/mbWithdrawLogController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        });

	}function editAuditFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        console.log('ididididididiid:'+id);
        parent.$.modalDialog({
            title: '提现审核',
            width: 780,
            height: 200,
            href: '${pageContext.request.contextPath}/mbWithdrawLogController/editAuditPage?id=' + id ,
            buttons: [{
                text: '同意',
                handler: function () {
					parent.$.messager.confirm('询问', '同意提现，是否继续？', function(b) {
						if (b) {
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#form');
							f.find("input[name=handleStatus]").val("HS02");
							f.submit();
						}
					});
                }
            },
                {
                    text: '拒绝',
                    handler: function () {
						parent.$.messager.confirm('询问', '拒绝提现，是否继续？', function(b) {
							if (b) {
								parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
								var f = parent.$.modalDialog.handler.find('#form');
								f.find("input[name=handleStatus]").val("HS03");
								f.submit();
							}
						});
                    }
                }
            ]
        });
    }
    function viewBalance(id) {
        var href = '${pageContext.request.contextPath}/mbUserController/viewBalance?shopId=' + id;
        parent.$("#index_tabs").tabs('add', {
            title: '余额-' + id,
            content: '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable: true
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
							<th><%=TmbWithdrawLog.ALIAS_ADDTIME%></th>
							<td>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_ADDTIME%>'})" id="addtimeBegin" name="addtimeBegin"/>至
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_ADDTIME%>'})" id="addtimeEnd" name="addtimeEnd"/>
							</td>
							<th><%=TmbWithdrawLog.ALIAS_HANDLE_TIME%></th>
							<td>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_HANDLE_TIME%>'})" id="handleTimeBegin" name="handleTimeBegin"/>至
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_HANDLE_TIME%>'})" id="handleTimeEnd" name="handleTimeEnd"/>
							</td>

							<th><%=TmbWithdrawLog.ALIAS_REMITTER_TIME%></th>	
							<td>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_REMITTER_TIME%>'})" id="remitterTimeBegin" name="remitterTimeBegin"/>至
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TmbWithdrawLog.FORMAT_REMITTER_TIME%>'})" id="remitterTimeEnd" name="remitterTimeEnd"/>
							</td>

						</tr>
						<tr>
							<th><%=TmbWithdrawLog.ALIAS_HANDLE_STATUS%></th>	
							<td>
											<jb:select dataType="HS" name="handleStatus"></jb:select>	
							</td>
							<th><%=TmbWithdrawLog.ALIAS_REMITTER%></th>
							<td>
								<input type="text" name="remitter" maxlength="32" class="span2"/>
							</td>
							<th><%=TmbWithdrawLog.ALIAS_PAY_CODE%></th>
							<td >
											<input type="text" name="payCode" maxlength="64" class="span4"/>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWithdrawLogController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWithdrawLogController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>