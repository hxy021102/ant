<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplier" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierBankAccountController/editPage')}">
		<script type="text/javascript">
            $.canEditBankAccount = true;
		</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierBankAccountController/delete')}">
		<script type="text/javascript">
            $.canDeleteBankAccount = true;
		</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierInvoiceController/editPage')}">
		<script type="text/javascript">
            $.canEditSupplierInvoice = true;
		</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierInvoiceController/delete')}">
		<script type="text/javascript">
            $.canDeleteSupplierInvoice = true;
		</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierInvoiceController/view')}">
		<script type="text/javascript">
            $.canViewSupplierInvoice = true;
		</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierInvoiceController/setInvoiceDefault')}">
		<script type="text/javascript">
            $.canSetInvoiceDefault = true;
		</script>
</c:if>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
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
                    gridMap.handle(this, loadSupplierBankAccountDataGrid);
                }, grid: null
            },
            1: {
                invoke: function () {
                    gridMap.handle(this, loadSupplierInvoiceDataGrid);
                }, grid: null
            }
        };
        $('#shop_view_tabs').tabs({
            onSelect: function (title, index) {
                gridMap[index].invoke();
            }
        });
        gridMap[0].invoke();
    });
    var supplierBankAccountDataGrid;
    function loadSupplierBankAccountDataGrid() {
        return supplierBankAccountDataGrid = $('#supplierBankAccountDataGrid').datagrid({
            url : '${pageContext.request.contextPath}/mbSupplierBankAccountController/dataGrid?supplierId='+${mbSupplier.id},
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
                title : '添加时间',
                width : 50
            }, {
                field : 'updatetime',
                title : '更新时间',
                width : 50
            }, {
                field : 'supplierId',
                title : '供应商ID',
                width : 50
            }, {
                field : 'accountName',
                title : '开户名称',
                width : 50
            }, {
                field : 'accountBankName',
                title : '开户银行',
                width : 50
            }, {
                field : 'bankNumber',
                title : '银行卡号',
                width : 50
            }, {
                field : 'action',
                title : '操作',
                width : 30,
                formatter : function(value, row, index) {
                    var str = '';
                    if ($.canEditBankAccount) {
                        str += $.formatString('<img onclick="editSupplierBankAccount(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
                    }
                    str += '&nbsp;';
                    if ($.canDeleteBankAccount) {
                        str += $.formatString('<img onclick="deleteSupplierBankAccount(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                    }
                    return str;
                }
            } ] ],
            toolbar : '#supplierBankAccountToolBar',
            onLoadSuccess : function() {
                $('#searchForm table').show();
                parent.$.messager.progress('close');
                $(this).datagrid('tooltip');
            }
        });
    };
    	var supplierInvoiceDataGrid;
    	function loadSupplierInvoiceDataGrid() {
        return supplierInvoiceDataGrid = $('#supplierInvoiceDataGrid').datagrid({
    		url : '${pageContext.request.contextPath}/mbSupplierInvoiceController/dataGrid?supplierId='+${mbSupplier.id},
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
       	 	field : 'companyName',
        	title : '公司抬头',
        	width : 80
    		}, {
        	field : 'companyTfn',
        	title : '公司税号',
        	width : 50
    		}, {
        	field : 'registerAddress',
        	title : '注册地址',
        	width : 130
    		}, {
        	field : 'registerPhone',
        	title : '注册电话',
        	width : 80
    		}, {
        	field : 'bankNames',
        	title : '银行名称',
        	width : 80
    		}, {
        	field : 'bankNumber',
        	title : '银行卡号',
        	width : 100
    		}, {
        	field : 'invoiceUseName',
        	title : '发票用途',
			width : 50
    		}, {
        	field : 'invoiceTypeName',
        	title : '发票类型',
        	width : 50
    		}, {
        	field : 'action',
        	title : '操作',
        	width : 50,
        	formatter : function(value, row, index) {
            var str = '';
            if ($.canEditSupplierInvoice) {
                str += $.formatString('<img onclick="editSupplierInvoice(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
            }
            str += '&nbsp;';
            if ($.canDeleteSupplierInvoice) {
                str += $.formatString('<img onclick="deleteSupplierInvoice(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
            }
            str += '&nbsp;';
            if ($.canViewSupplierInvoice) {
                str += $.formatString('<img onclick="viewSupplierInvoice(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/map/magnifier.png');
            }
            str += '&nbsp;';
            if ($.canSetInvoiceDefault && row.invoiceDefault == '0') {
                str += $.formatString('<img onclick="setDefaultInvoice(\'{0}\');" src="{1}" title="设置默认模板"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/wrench.png');
			}
            return str;
        }
    	} ] ],
        toolbar : '#supplierInvoiceToolBar',
        onLoadSuccess : function() {
        $('#searchForm table').show();
        parent.$.messager.progress('close');

        $(this).datagrid('tooltip');
    }
    });
    };
    function addSupplierBankAccount() {
        parent.$.modalDialog({
            title : '添加数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbSupplierBankAccountController/addPage?supplierId='+${mbSupplier.id},
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = supplierBankAccountDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            } ]
        });
    }
    function addSupplierInvoice() {
        parent.$.modalDialog({
            title : '添加数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbSupplierInvoiceController/addPage?supplierId='+${mbSupplier.id},
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = supplierInvoiceDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            } ]
        });
    }
    function viewSupplierInvoice(id) {
        if (id == undefined) {
            var rows = supplierInvoiceDataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '查看数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbSupplierInvoiceController/view?id=' + id
        });
    }
    function editSupplierBankAccount(id) {
        if (id == undefined) {
            var rows = supplierBankAccountDataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '编辑数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbSupplierBankAccountController/editPage?id=' + id,
            buttons : [ {
                text : '编辑',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = supplierBankAccountDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            } ]
        });
    }
    function editSupplierInvoice(id) {
        if (id == undefined) {
            var rows = supplierInvoiceDataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '编辑数据',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbSupplierInvoiceController/editPage?id=' + id,
            buttons : [ {
                text : '编辑',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = supplierInvoiceDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            } ]
        });
    }
    function deleteSupplierInvoice(id) {
        if (id == undefined) {
            var rows = supplierInvoiceDataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
            if (b) {
                parent.$.messager.progress({
                    title : '提示',
                    text : '数据处理中，请稍后....'
                });
                $.post('${pageContext.request.contextPath}/mbSupplierInvoiceController/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        supplierInvoiceDataGrid.datagrid('reload');
                    }
                    parent.$.messager.progress('close');
                }, 'JSON');
            }
        });
    }
    function deleteSupplierBankAccount(id) {
        if (id == undefined) {
            var rows = supplierBankAccountDataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
            if (b) {
                parent.$.messager.progress({
                    title : '提示',
                    text : '数据处理中，请稍后....'
                });
                $.post('${pageContext.request.contextPath}/mbSupplierBankAccountController/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        supplierBankAccountDataGrid.datagrid('reload');
                    }
                    parent.$.messager.progress('close');
                }, 'JSON');
            }
        });
    }
    function setDefaultInvoice(id) {
        if (id == undefined) {
            var rows = supplierInvoiceDataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.messager.confirm('询问', '您是否要把当前模板设为默认值？', function (b) {
            if (b) {
                parent.$.messager.progress({
                    title: '提示',
                    text: '数据处理中，请稍后....'
                });
                $.post('${pageContext.request.contextPath}/mbSupplierInvoiceController/setInvoiceDefault?supplierId=' +${mbSupplier.id}, {
                    id: id
                }, function (result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        supplierInvoiceDataGrid.datagrid('reload');
                    }
                    parent.$.messager.progress('close');
                }, 'JSON');
            }
        });
    }
    //获取供应商钱包日志
    function viewBalance(id) {
        var href = '${pageContext.request.contextPath}/mbUserController/viewBalance?supplierId=' + id;
        parent.$("#index_tabs").tabs('add', {
            title: '余额-' + id,
            content: '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable: true
        });
    }
    $(function () {
        $('.money_input').each(function () {
            $(this).text($.formatMoney($(this).text().trim()));
        });
    });
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 180px; overflow: hidden;">
		<table class="table table-hover table-condensed">
				<tr>
					<th><%=TmbSupplier.ALIAS_ADDTIME%></th>
					<td>
						${mbSupplier.addtime}							
					</td>
					<th><%=TmbSupplier.ALIAS_UPDATETIME%></th>
					<td>
						${mbSupplier.updatetime}
					</td>
					<th><%=TmbSupplier.ALIAS_NAME%></th>
					<td>
						${mbSupplier.name}
					</td>
					<th><%=TmbSupplier.ALIAS_SUPPLIER_CODE%></th>
					<td>
						${mbSupplier.supplierCode}
					</td>
				</tr>
				</tr>		
				<tr>
					<th><%=TmbSupplier.ALIAS_WAREHOUSE_ID%></th>
					<td>
						${mbSupplier.warehouseName}
					</td>
					<th>余额</th>
					<td class="money_input">
						${mbSupplier.balanceAmount+mbSupplier.unPayBalanceAmount}
					</td>
					<th>实际余额</th>
					<td>
						<a href="javascript:void(0);" onclick="viewBalance('${mbSupplier.id}')" class="money_input">${mbSupplier.balanceAmount}</a>
					</td>
					<th>应付金额</th>
					<td class="money_input">
						${mbSupplier.unPayBalanceAmount}
					</td>
				</tr>		
				<tr>	
					<th><%=TmbSupplier.ALIAS_CONTACT_PEOPLE%></th>	
					<td>
						${mbSupplier.contactPeople}							
					</td>
					<th><%=TmbSupplier.ALIAS_CONTACT_PHONE%></th>
					<td>
						${mbSupplier.contactPhone}
					</td>
					<th><%=TmbSupplier.ALIAS_FINANCIAL_CONTACT%></th>
					<td>
						${mbSupplier.financialContact}
					</td>
					<th><%=TmbSupplier.ALIAS_FINANCIAL_CONTACT_PHONE%></th>
					<td>
						${mbSupplier.financialContactPhone}
					</td>
				</tr>
				<tr>
					<th><%=TmbSupplier.ALIAS_REGION_NAME%></th>
					<td>
						${mbSupplier.regionName}
					</td>
					<th><%=TmbSupplier.ALIAS_ADDRESS%></th>
					<td colspan="5">
						${mbSupplier.address}
					</td>
				</tr>		
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<div id="shop_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="开户管理">
				<table id="supplierBankAccountDataGrid"></table>
			</div>
			<div title="发票模板">
				<table id="supplierInvoiceDataGrid"></table>
			</div>
		</div>
	</div>
</div>
<div id="supplierBankAccountToolBar">
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierBankAccountController/addPage')}">
		<a onclick="addSupplierBankAccount();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
	</c:if>
</div>
<div id="supplierInvoiceToolBar">
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierInvoiceController/addPage')}">
		<a onclick="addSupplierInvoice();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
	</c:if>
</div>
</body>
</html>