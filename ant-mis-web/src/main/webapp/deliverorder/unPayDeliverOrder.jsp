﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>DeliverOrder管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 0,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [
			    {field: 'ck', checkbox: true},{
				field : 'id',
				title : '运单ID',
				width : 50,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
				},  {
				field : 'addtime',
				title : '添加时间',
				width : 50		
				}, {
                field : 'supplierName',
                title : '供应商名称',
                width : 80
                }, {
				field : 'amount',
				title : '总金额',
				align:"right",
				width : 50	,
				formatter : function (value,row,index) {
					return $.formatMoney(value);
                }
				}, {
                field : 'payStatusName',
                title : '接入方结算状态',
                width : 50
            	}, {
				field : 'statusName',
				title : '订单状态',
				width : 50		
				}, {
				field : 'deliveryStatusName',
				title : '配送状态',
				width : 50		
				}] ],
			toolbar : '#toolbar',
		});
        $('#searchForm table').show();
        parent.$.messager.progress('close');
        $(this).datagrid('tooltip');


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
				$.post('${pageContext.request.contextPath}/deliverOrderController/delete', {
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
			href : '${pageContext.request.contextPath}/deliverOrderController/editPage?id=' + id,
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
        var href = '${pageContext.request.contextPath}/deliverOrderController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '运单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
        });
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/deliverOrderController/addPage',
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
	        url:'${pageContext.request.contextPath}/deliverOrderController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
       	 }
        }); 
	}
	function searchFun() {
        var isValid = $('#searchForm').form('validate');
        if (isValid) {
        var options = {};
        options.url = '${pageContext.request.contextPath}/deliverOrderController/unPayOrderDataGrid';
        options.queryParams = $.serializeObject($('#searchForm'));//queryParams是请求的时候发送的额外参数
        dataGrid.datagrid(options);
		}
		return isValid;
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
    function addOrderBill() {
        var rows = $('#dataGrid').datagrid('getChecked');
        var total = 0;
        for(var i = 0; i<rows.length; i++) {
            total += rows[i].amount;
			}
        if(rows.length == 0) {
            alert("您还没有选择订单！")
		}
        if(rows.length != 0) {
                parent.$.messager.confirm('询问', '账单总金额为：'+ $.formatMoney(total) +'</br> 开始时间为：'+ $('#startDate').val() +'</br>结束时间为：'+$('#endDate').val()+' </br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您确认创建账单？', function(b) {
                    if (b) {
                        parent.$.messager.progress({
                            title : '提示',
                            text : '数据处理中，请稍后....'
                        });
                        $.post('${pageContext.request.contextPath}/deliverOrderController/addOrderBill',{supplierId: rows[0].supplierId,unpayDeliverOrders: JSON.stringify(rows),startTime:$('#startDate').val(),endTime:$('#endDate').val()},function (result) {
                            if(result.success) {
                                parent.$.messager.alert('提示', result.msg, 'info');
                                dataGrid.datagrid('reload');
                            }
                            parent.$.messager.progress('close');
                        },"JSON");
                    }
                });
            }

	}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed">
					<tr>
						<th style="width: 50px;">接入商</th>
						<td>
							<jb:selectGrid  dataType="deliverSupplierId" name="supplierId" required="true"></jb:selectGrid>
						</td>
						<th style="width: 50px">订单时间</th>
						<td>
							<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'endDate\',{M:-1});}',maxDate:'#F{$dp.$D(\'endDate\',{d:-1});}'})" id="startDate" name="addtimeBegin"/>
							至	<input type="text" class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\',{d:1});}',maxDate:'#F{$dp.$D(\'startDate\',{M:1});}'})" id="endDate" name="addtimeEnd"/>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/deliverOrderController/addOrderBill')}">
			<a onclick="addOrderBill();" href="javascript:void(0);" class="easyui-linkbutton"
			   data-options="plain:true,iconCls:'pencil'">创建账单</a>
		</c:if>
	</div>	
</body>
</html>