<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbRechargeLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbRechargeLog管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/editAuditPage')}">
	<script type="text/javascript">
		$.canEditAudit = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {

		dataGrid = $('#dataGrid').datagrid({
			url : '',
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
				field : 'addtime',
				title : '<%=TmbRechargeLog.ALIAS_ADDTIME%>',
				width : 80
				},{
				field : 'refTypeName',
				title : '<%=TmbRechargeLog.ALIAS_REF_TYPE%>',
				width : 40
				}, {
				field : 'shopId',
				title : '门店ID',
				width : 30
            	}, {
					field : 'shopName',
					title : '门店名称',
					width : 80
				}, {
				field : 'amount',
				title : '<%=TmbRechargeLog.ALIAS_AMOUNT%>',
				width : 60	,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
				}, {
				field : 'applyLoginName',
				title : '<%=TmbRechargeLog.ALIAS_APPLY_LOGIN_ID%>',
				width : 40
				} , {
				field : 'bankCodeName',
				title : '<%=TmbRechargeLog.ALIAS_BANK_CODE_NAME%>',
				width : 50
				} , {

                field : 'payCode',
                title : '<%=TmbRechargeLog.ALIAS_PAYCODE%>',
                width : 60
            	}, {

				field : 'remitter',
				title : '<%=TmbRechargeLog.ALIAS_REMITTER%>',
				width : 40
				} , {
				field : 'remitterTime',
				title : '<%=TmbRechargeLog.ALIAS_REMITTER_TIME%>',
				width : 80
				} , {
                field : 'content',
                title : '<%=TmbRechargeLog.ALIAS_CONTENT%>',
                width : 80
				} , {
				field : 'handleStatusName',
				title : '<%=TmbRechargeLog.ALIAS_HANDLE_STATUS%>',
				width : 50,
				formatter:function(value, row){
					if(row.refType == 'BT003' ||(row.refType == 'BT013'&&row.payCode)){
						return value;
					}else{
						return '';
					}
				}
				} , {
				field : 'action',
				title : '操作',
				width : 30,
				formatter : function(value, row, index) {
					var str = '';
                    if ($.canEditAudit && (row.refType == 'BT003' ||(row.refType == 'BT013'&&row.payCode)) && row.handleStatus == 'HS01') {
						str += $.formatString('<img onclick="editAuditFun(\'{0}\');" src="{1}" title="审核"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/joystick.png');
					}
					return str;
				}
            }] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
		});
		setTimeout(function(){
			searchFun();
		},100);
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
				$.post('${pageContext.request.contextPath}/mbRechargeLogController/delete', {
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
			href : '${pageContext.request.contextPath}/mbRechargeLogController/editPage?id=' + id,
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

	function editAuditFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title: '汇款审核',
			width: 780,
			height: 200,
			href: '${pageContext.request.contextPath}/mbRechargeLogController/editAuditPage?id=' + id ,
			buttons: [{
				text: '通过',
				handler: function () {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.find("input[name=handleStatus]").val("HS02");
                    var payCode = f.find("input[name=payCode]");
                    payCode.addClass('easyui-validatebox').attr("data-options",'required:true');
                    $.parser.parse(payCode.parent());
                   	setTimeout(function () {
                        payCode.focus();
                    },10);
                    f.submit();

				}
			},
				{
					text: '拒绝',
					handler: function () {
						parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name=handleStatus]").val("HS03");
                        var payCode = f.find("input[name=payCode]");
                        payCode.removeClass('easyui-validatebox validatebox-text validatebox-invalid').removeAttr("data-options");
                        $.parser.parse(payCode.parent());
						f.submit();
					}
				}
			]
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
			href : '${pageContext.request.contextPath}/mbRechargeLogController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbRechargeLogController/addPage',
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
	        url:'${pageContext.request.contextPath}/mbRechargeLogController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function searchFun() {
		var options = {};
		options.url = '${pageContext.request.contextPath}/mbRechargeLogController/dataGrid';
		options.queryParams = $.serializeObject($('#searchForm'));
		dataGrid.datagrid(options);
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
							<th style="width: 50px">门店ID</th>
							<td>
								<input type="text" name="shopId" maxlength="10" class="span2"/>
							</td>
							<th style="width: 50px">处理状态</th>
							<td>
								<jb:select dataType="HS" name="handleStatus" value="HS01"></jb:select>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbRechargeLogController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>