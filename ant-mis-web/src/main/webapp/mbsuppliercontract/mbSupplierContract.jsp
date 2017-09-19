﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierContract" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
	<title>MbSupplierContract管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	<jsp:include page="../mbsuppliercontractitem/mbSupplierContractItem.jsp"></jsp:include>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractController/editPage')}">
		<script type="text/javascript">
            $.canEdit = true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractController/delete')}">
		<script type="text/javascript">
            $.canDelete = true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractController/view')}">
		<script type="text/javascript">
            $.canView = true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractController/downloadAttachment')}">
		<script type="text/javascript">
            $.canDownloadAttachment =true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractController/download')}">
		<script type="text/javascript">
            $.canDownload =true;
		</script>
	</c:if>
	<script type="text/javascript">
        var dataGrid;
        $(function() {
            dataGrid = $('#dataGrid').datagrid({
                url : '${pageContext.request.contextPath}/mbSupplierContractController/dataGrid',
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
                    field : 'code',
                    title : '<%=TmbSupplierContract.ALIAS_CODE%>',
                    width : 30
                },  {
                    field : 'name',
                    title : '<%=TmbSupplierContract.ALIAS_NAME%>',
                    width : 40
                }, {
                    field : 'supplierName',
                    title : '<%=TmbSupplierContract.ALIAS_SUPPLIER_NAME%>',
                    width : 100
                },
               	{
                    field : 'expiryDateStart',
                    title : '<%=TmbSupplierContract.ALIAS_EXPIRY_DATE_START%>',
                    width : 70
                }, {
                    field : 'expiryDateEnd',
                    title : '<%=TmbSupplierContract.ALIAS_EXPIRY_DATE_END%>',
                    width : 70
                }, {
                    field : 'valid',
                    title : '<%=TmbSupplierContract.ALIAS_VALID%>',
                    width : 25,
                    formatter : function(value, row, index) {
                        if (row.valid == true) {
                            return "是";
                        } else {
                            return "否";
                        }
                    }
                }, {
                    field : 'action',
                    title : '操作',
                    width : 25,
                    formatter : function(value, row, index) {
                        var str = '';
                        if ($.canEdit) {
                            str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
                        }
                        str += '&nbsp;';
                        if ($.canDelete) {
                            str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                        }
                        str += '&nbsp;';
                        if ($.canDownload ) {
                            str += '<a href="${pageContext.request.contextPath}/mbSupplierContractController/downloadAttachment?id=' + row.id + '"><img title="下载" src="${pageContext.request.contextPath}/style/images/extjs_icons/bullet_go.png"/></a>';
                        }
                        return str;
                    }
                } ] ],
                toolbar : '#suptoolbar',
                onLoadSuccess : function() {
                    $('#searchForm table').show();
                    parent.$.messager.progress('close');

                    $(this).datagrid('tooltip');
                },onSelect : function(index, row) {
                    listsupContractItem(row.id);
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
                    $.post('${pageContext.request.contextPath}/mbSupplierContractController/delete', {
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
                href : '${pageContext.request.contextPath}/mbSupplierContractController/editPage?id=' + id,
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
                href : '${pageContext.request.contextPath}/mbSupplierContractController/view?id=' + id
            });
        }

        function addFun() {
            parent.$.modalDialog({
                title : '添加数据',
                width : 780,
                height : 500,
                href : '${pageContext.request.contextPath}/mbSupplierContractController/addPage',
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
                url:'${pageContext.request.contextPath}/mbSupplierContractController/download',
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
	<div data-options="region:'west',split:true" style="width: 910px; overflow: hidden;">
		<div class="easyui-layout" data-options="fit : true,border : false">
			<div data-options="region:'north',title:'查询条件',border:false" style="height: 80px; overflow: hidden;">
			 <form id="searchForm">
			   <table class="table table-hover table-condensed" style="display: none;">
				<tr>
					<td>
						<strong><%=TmbSupplierContract.ALIAS_CODE%></strong>&nbsp;&nbsp;<input type="text" name="code" maxlength="64" class="span2"/>
					</td>
					<td>
						<strong><%=TmbSupplierContract.ALIAS_NAME%></strong>&nbsp;&nbsp;<input type="text" name="name" maxlength="128" class="span2"/>
					</td>
					<td>
						<strong><%=TmbSupplierContract.ALIAS_SUPPLIER_NAME%></strong>&nbsp;&nbsp;<jb:selectGrid dataType="supplierId" name="supplierId"></jb:selectGrid>
					</td>
				</tr>
			   </table>
		     </form>
	        </div>
	      <div data-options="region:'center',border:false">
		  <table id="dataGrid"></table>
	      </div>
		</div>
	</div>
	<div data-options="region:'center',split:true" style="overflow: hidden;">
		<div class="easyui-layout" data-options="fit : true,border : false">
			<div data-options="region:'center',border:false">
				<form id="mbsupitemSearchForm" style="display: none">
					<input type="hidden" name="supplierContractId" id = "supplierContractId">
				</form>
				<table id="mbsupitemDataGrid"></table>
			</div>
		</div>
	</div>
</div>
<div id="suptoolbar" style="display: none;">
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractController/addPage')}">
		<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
	</c:if>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierContractController/download')}">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>
		<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
		</form>
		<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
	</c:if>
</div>
</body>
</html>