<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>shopdeliverapply管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/shopDeliverApplyController/examinePage')}">
		<script type="text/javascript">
            $.canExamine= true;
		</script>
	</c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/shopDeliverApplyController/delete')}">
	    <script type="text/javascript">
          $.canDelete = true;
	    </script>
    </c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/shopDeliverApplyController/editPage')}">
		<script type="text/javascript">
            $.canEdit = true;
		</script>
	</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/shopDeliverApplyController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '派单ID',
				width : 50,
                formatter : function (value, row, index) {
                    return '<a onclick="viewFun(' + row.id + ')">' + row.id + '</a>';
                }
				}, {
				field : 'shopId',
				title : '门店ID',
				width : 50
				}, {
				field : 'shopName',
				title : '门店名称',
				width : 50		
				}, {
                field : 'accountId',
                title : '门店账号',
                width : 50
                },  {
                field : 'statusName',
                title : '审核状态',
                width : 50
                },  {
                field : 'result',
                title : '结果',
                width : 150
                },  {
				field : 'action',
				title : '操作',
				width : 20,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
                    str += '&nbsp;';
                    if ($.canExamine&&row.status=="DAS01") {
						str += $.formatString('<img onclick="examineFun(\'{0}\');" src="{1}" title="审核"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/joystick.png');
                    }
                    str += '&nbsp;';
                    if ($.canEdit&&row.status=="DAS02") {
                        str += $.formatString('<img onclick="editDeliverDistance(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
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
				$.post('${pageContext.request.contextPath}/shopDeliverApplyController/delete', {
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

	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '派单数据',
			width : 780,
			height : 300,
			href : '${pageContext.request.contextPath}/shopDeliverApplyController/view?id=' + id
		});
	}
    function examineFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '审核派单',
            width : 780,
            height : 300,
            href : '${pageContext.request.contextPath}/shopDeliverApplyController/examinePage?id=' + id,
            buttons: [{
                text: '通过',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid =  dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=status]").val("DAS02");
                    f.submit();
                }
            },
                {
                    text: '拒绝',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid =  dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name=status]").val("DAS03");
                        f.submit();

                    }
                }
            ]
        });
    }
    function editDeliverDistance(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '修改最大配送距离',
            width : 250,
            height : 250,
            href : '${pageContext.request.contextPath}/shopDeliverApplyController/editPage?id=' + id,
            buttons: [{
                text: '确认',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid =  dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.submit();
                }
            }]
        });
    }
    function downloadTable(){
        var options = dataGrid.datagrid("options");
        var $colums = [];
        $.merge($colums, options.columns);
        $.merge($colums, options.frozenColumns);
        var columsStr = JSON.stringify($colums);
        $('#downloadTable').form('submit', {
            url:'${pageContext.request.contextPath}/shopDeliverApplyController/download',
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
		dataGrid.datagrid("reload",{ });
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th style="width: 50px">门店名称</th>
						<td>
							<jb:selectGrid dataType="shopId" name="shopId"></jb:selectGrid>
						</td>
						<th style="width: 50px">门店账号</th>
						<td>
							<input type="text" name="accountId" maxlength="10" class="span2"/>
						</td>
						<th style="width: 50px">审核状态
						</th>
						<td>
							<jb:select dataType="DAS" name="status" ></jb:select>
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
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/shopDeliverApplyController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>