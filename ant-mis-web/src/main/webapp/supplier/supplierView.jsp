<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierItemRelationController/editPage')}">
	<script type="text/javascript">
            $.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierItemRelationController/delete')}">
	<script type="text/javascript">
            $.canDelete = true;
	</script>
</c:if>
<script type="text/javascript">
    var gridMap = {};
    $(function () {
        gridMap = {
            handle: function (obj, clallback) {
                if (obj.grid == null) {
                    obj.grid = clallback();
                } else {
                    obj.grid.datagrid('reload');
                }
            },
            0: {
                invoke: function () {
                    gridMap.handle(this, dataGrid);
                }, grid: null
            }
        };
        gridMap[0].invoke();
    });
    	var dataGrid;
        function dataGrid() {
            return dataGrid = $('#dataGrid').datagrid({
            url: '${pageContext.request.contextPath}/supplierItemRelationController/dataGrid?supplierId=' +${supplier.id},
            fit: true,
            fitColumns: true,
            border: false,
            pagination: false,
            idField: 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            sortName: 'id',
            sortOrder: 'desc',
            checkOnSelect: false,
            selectOnCheck: false,
            nowrap: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            columns: [[
                {
                    field : 'id',
                    title : '编号',
                    width : 150,
                    hidden : true
                }, {
                    field : 'code',
                    title : '商品编码',
                    width : 50
                }, {
                    field : 'itemName',
                    title : '商品名称',
                    width : 120
                },{
                    field : 'price',
                    title : '单价',
                    width : 40,
					formatter: function (value,row,index) {
                        return $.formatMoney(row.price);
					}
                },{
                    field : 'inPrice',
                    title : '采购价',
                    width : 40,
                    formatter: function (value,row,index) {
                        return $.formatMoney(row.inPrice);
                    }
                },{
                    field : 'freight',
                    title : '运费',
                    width : 40 ,
                    formatter: function (value,row,index) {
                        return $.formatMoney(row.freight);
                    }
                },{
                    field : 'online',
                    title : '上下架',
                    width : 30,
					formatter : function (value,row,index) {
                        if(row.online == 0) {
                            return '已下架';
						}else if(row.online == 1) {
                            return '已上架';
						}
					}
                }, {
                    field : 'action',
                    title : '操作',
                    width : 40,
                    formatter : function(value, row, index) {
                        var str = '';
                        if ($.canEdit) {
                            str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
                        }
                        str += '&nbsp;';
                        if ($.canDelete) {
                            str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                        }
                        return str;
                    }
				}]],
                toolbar: "#toolbar"
            });
        };
    function addFun() {
        parent.$.modalDialog({
            title : '添加数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/supplierItemRelationController/addPage?supplierId='+${supplier.id},
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
    function editFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '编辑数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/supplierItemRelationController/editPage?id=' + id,
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
                $.post('${pageContext.request.contextPath}/supplierItemRelationController/delete', {
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
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false">
		<table class="table table-hover table-condensed">
				<tr>	
					<th style="width:60px;">appKey</th>
					<td>
						${supplier.appKey}							
					</td>							
					<th style="width: 60px">appSecret</th>
					<td>
						${supplier.appSecret}							
					</td>							
				</tr>		
				<tr>
					<th style="width: 60px">接入方名称</th>
					<td>
						${supplier.name}
					</td>
					<th style="width: 60px">状态</th>
					<td>
						${supplier.statusName}
					</td>
				</tr>
				<tr>	
					<th style="width: 60px">联系人</th>
					<td>
						${supplier.contacter}							
					</td>							
					<th style="width: 60px">联系电话</th>
					<td>
						${supplier.contactPhone}							
					</td>							
				</tr>
				<tr>
					<th style="width: 60px">地址</th>
					<td>
						${supplier.address}
					</td>
					<th>营业执照</th>
					<td rowspan="3" height="60">
						<img src="${supplier.charterUrl}" style="height: 120px;width: 220px"/>
					</td>
				</tr>
				<tr>
					<th>操作人</th>
					<td colspan="2">${supplier.loginName}</td>
				</tr>
			<tr>
				<th>备注</th>
				<td colspan="2">
				${supplier.remark}
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<div id="shop_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="商品明细列表">
				<table id="dataGrid"></table>
			</div>
		</div>
	</div>
</div>
<div id="toolbar">
	<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierItemRelationController/addPage')}">
		<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
	</c:if>
</div>
</body>
</html>