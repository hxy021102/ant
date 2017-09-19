﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierStockIn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbSupplierStockIn管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierStockInController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierStockInController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierStockInController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierStockInController/payPage')}">
	<script type="text/javascript">
		$.canPay = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierStockInController/invoicePage')}">
	<script type="text/javascript">
		$.canInvoice = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/mbSupplierStockInController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
            sortName : 'addtime',
            sortOrder : 'desc',
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 30,
                formatter : function (value, row, index) {
                    if ($.canView) {
                        return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                    }
                    else{
                        return value;
                    }
                }
				},{
                field : 'supplierName',
                title : '<%=TmbSupplierStockIn.ALIAS_SUPPLIER_NAME%>',
                width : 90
                },{
				field : 'addtime',
				title : '<%=TmbSupplierStockIn.ALIAS_ADDTIME%>',
				width : 75
				},{
				field : 'signDate',
				title : '<%=TmbSupplierStockIn.ALIAS_SIGN_DATE%>',
				width : 75
				}, {
                    field : 'signPeopleName',
                    title : '<%=TmbSupplierStockIn.ALIAS_SIGN_PEOPLE_ID%>',
                    width : 40
                },{
				field : 'loginName',
				title : '操作人',
				width : 40
				}, {
				field : 'payStatusName',
				title : '<%=TmbSupplierStockIn.ALIAS_PAY_STATUS%>',
				width : 40
				}, {
				field : 'invoiceStatusName',
				title : '<%=TmbSupplierStockIn.ALIAS_INVOICE_STATUS%>',
				width : 40
				},
                {
                    field : 'supplierOrderId',
                    title : '订单ID',
                    width : 40,
                }, {
                    field : 'warehouseName',
                    title : '<%=TmbSupplierStockIn.ALIAS_WAREHOUSE_ID%>',
                    width : 50
                },{
				field : 'action',
				title : '操作',
				width : 45,
				formatter : function(value, row, index) {
					var str = '';
					<%--if ($.canEdit) {--%>
					 <%--str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');--%>
					 <%--}--%>
					str += '&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
                    str += '&nbsp;';
                    if ($.canPay&&row.payStatus=='FS01') {
                        str += $.formatString('<img onclick="canPay(\'{0}\');" src="{1}" title="付款"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/money_yen.png');
                    }
                    str += '&nbsp;';
                    if ($.canInvoice&&row.payStatus=='FS02'&&row.invoiceStatus=='IS01') {
                        str += $.formatString('<img onclick="caninvoice(\'{0}\');" src="{1}" title="开票"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/money.png');
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
				$.post('${pageContext.request.contextPath}/mbSupplierStockInController/delete', {
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
			href : '${pageContext.request.contextPath}/mbSupplierStockInController/editPage?id=' + id,
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
			href : '${pageContext.request.contextPath}/mbSupplierStockInController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbSupplierStockInController/addPage',
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
    function canPay(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }

        $.post('${pageContext.request.contextPath}/mbSupplierStockInController/checkPayTimeOut?id=' + id, function (result) {
            var msg = "您确认付款吗？";
            if (result.success) {
                msg = result.msg;
            }

            parent.$.messager.confirm('询问', msg, function (b) {
                if (b) {
                    parent.$.modalDialog({
                        title: '付款',
                        width: 300,
                        height: 300,
                        href: '${pageContext.request.contextPath}/mbSupplierStockInController/payPage?id=' + id,
                        buttons: [{
                            text: '确认',
                            handler: function () {
                                parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                                var f = parent.$.modalDialog.handler.find('#form');
                                f.submit();
                            }
                        }]
                    });
                }
            });

        }, "JSON");

    }



    function caninvoice(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '开票',
            width : 320,
            height : 300,
            href : '${pageContext.request.contextPath}/mbSupplierStockInController/invoicePage?id=' + id,
            buttons : [ {
                text : '确认',
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
	        url:'${pageContext.request.contextPath}/mbSupplierStockInController/download',
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
						<td>
							<strong><%=TmbSupplierStockIn.ALIAS_SUPPLIER_ORDER_ID%></strong>&nbsp;&nbsp;<input type="text" name="supplierOrderId" maxlength="10" class="span2"/>
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;<strong><%=TmbSupplierStockIn.ALIAS_SIGN_PEOPLE_ID%></strong>&nbsp;&nbsp;<jb:selectSql dataType="SQ010" name="signPeopleId"></jb:selectSql>
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;<strong>&nbsp;&nbsp;<%=TmbSupplierStockIn.ALIAS_WAREHOUSE_ID%></strong>&nbsp;&nbsp;<jb:selectSql dataType="SQ005" name="warehouseId"></jb:selectSql>
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;<strong><%=TmbSupplierStockIn.ALIAS_PAY_STATUS%></strong>&nbsp;&nbsp;<jb:select dataType="FS" name="payStatus"></jb:select>
						</td>
						<td>
							<strong><%=TmbSupplierStockIn.ALIAS_INVOICE_STATUS%></strong>&nbsp;&nbsp;<jb:select dataType="IS" name="invoiceStatus"></jb:select>
						</td>
						<td>
							<strong>入库时间</strong>&nbsp;&nbsp;<input class="span2" name="stockinTimeBegin"
								   placeholder="点击选择时间"
								   onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								   readonly="readonly"/>
							至<input class="span2" name="stockinTimeEnd"
								   placeholder="点击选择时间"
								   onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								   readonly="readonly"/>
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
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierStockInController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>