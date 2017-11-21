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
			url : '',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : false,
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
			columns : [ [  {
                field : 'ck',
                checkbox:true,
                width : 30
                }, {
				field : 'id',
				title : '骑手运单ID',
				width : 50,
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
                    if ($.canViewAccount)
                        return '<a onclick="viewAccount(' + row.driverAccountId + ')">' + row.userName + '</a>';
                    return value;
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
				} ] ],
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
    function addDriverOrderBill() {
        var isValid = $('#searchForm').form('validate');
        var rows = $('#dataGrid').datagrid('getChecked');
        if (isValid && rows.length > 0) {
            var driverOrderShopBillView = $.serializeObject($('#searchForm'));
            var totalAmount = 0;
            var orderShopIds = new Array(rows.length);
            for (var i = 0; i < rows.length; i++) {
                totalAmount += rows[i].amount;
                orderShopIds[i] = rows[i].id;
            }
            driverOrderShopBillView.orderShopIds=orderShopIds;
            driverOrderShopBillView.amount=totalAmount;
            driverOrderShopBillView.driverOrderShopList=rows;
            parent.$.messager.confirm("询问","总金额："+$.formatMoney(totalAmount) +"<br/>起始时间："+driverOrderShopBillView.addtimeBegin+"<br/>结束时间:"+driverOrderShopBillView.addtimeEnd+"<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>是否确认创建账单?</strong>",function (result) {
                if(result)  {
                    $.ajax({
                        url:  '${pageContext.request.contextPath}/driverOrderShopBillController/addDriverOrderBill',
                        data: JSON.stringify(driverOrderShopBillView),
                        dataType: "json",
                        type: "POST",
                        contentType: "application/json;charset=UTF-8",
                        beforeSend: function (request) {
                            parent.$.messager.progress({
                                title: '提示',
                                text: '数据处理中，请稍后....'
                            });
                        },

                        success: function (result) {
                            parent.$.messager.progress('close');
                            if(result.success) {
                                parent.$.messager.alert('提示', result.msg);
                                dataGrid.datagrid('clearChecked');
                                dataGrid.datagrid('reload');
                            }else{
                                parent.$.messager.alert('错误', result.msg);
                                dataGrid.datagrid('clearChecked');
                                dataGrid.datagrid('reload');
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            parent.$.messager.progress('close');
                        }
                    });
                }
            });
        }else{
            parent.$.messager.alert("提示","请选择要创建的运单！")
        }
    }

    $(function() {
        $('#searchForm table').show();
        parent.$.messager.progress('close');
    });

	function searchFun() {
        var isValid = $('#searchForm').form('validate');
        if (isValid) {
            var options = {};
            options.url = '${pageContext.request.contextPath}/driverOrderShopController/dataGridArtificial',
			options.queryParams = $.serializeObject($('#searchForm'));
            dataGrid.datagrid(options);
        }
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
							<th>骑手账号</th>
							<td>
								<input type="text" name="userName" maxlength="15" class="span2 easyui-validatebox" data-options="required:true"/>
							</td>
						    <th>添加时间</th>
						    <td>
							 <input type="text"  class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbUser.FORMAT_ADDTIME%>'})" id="addtimeBegin" name="addtimeBegin"/>至
							 <input type="text"  class="span2 easyui-validatebox" data-options="required:true" onclick="WdatePicker({dateFmt:'<%=TmbUser.FORMAT_ADDTIME%>'})" id="addtimeEnd" name="addtimeEnd"/>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/driverOrderShopController/dataGridArtificial')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/driverOrderShopBillController/addDriverOrderBill')}">
			<a onclick="addDriverOrderBill();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">创建账单</a>
		</c:if>
	</div>	
</body>
</html>