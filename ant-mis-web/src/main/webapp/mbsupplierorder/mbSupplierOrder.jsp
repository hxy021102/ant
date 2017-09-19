<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
<title>MbSupplierOrder管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierController/view')}">
		<script type="text/javascript">
            $.canStockin = true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderController/examinePage')}">
		<script type="text/javascript">
            $.canExamine= true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderController/deleteOrder')}">
		<script type="text/javascript">
            $.canDeleteOrder = true;
		</script>
	</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderController/updateSupplierOrderStatus')}">
		<script type="text/javascript">
            $.updateSupplierOrderStatus = true;
		</script>
	</c:if>

<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/mbSupplierOrderController/dataGrid',
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
            sortName : 'addtime',
            sortOrder : 'desc',
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '订单ID',
				width : 30,
                formatter : function (value, row, index) {
                    if ($.canView) {
                        return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
                    }
                    else{
                        return value;
                    }
                }
				},{
                field : 'code',
                title : '<%=TmbSupplierContract.ALIAS_CODE%>',
                width : 40,
             }, {
				field : 'supplierName',
				title : '<%=TmbSupplierOrder.ALIAS_SUPPLIER_NAME%>',
				width : 100	,
				}, {
				field : 'totalPrice',
				title : '<%=TmbSupplierOrder.ALIAS_TOTAL_PRICE%>',
				width : 35,
				align:"right",
                formatter:function(value){
                    return $.formatMoney(value);
                }
				},{
				field : 'supplierPeopleName',
				title : '采购人',
				width : 30,
				}, {
                field : 'addtime',
                title : '<%=TmbSupplierOrder.ALIAS_ADDTIME%>',
                width : 60,
                }, {
				field : 'planStockInDate',
				title : '<%=TmbSupplierOrder.ALIAS_PLAN_STOCK_IN_DATE%>',
				width : 60,
				},  {
                field : 'warehouseName',
                title : '<%=TmbSupplierOrder.ALIAS_WAREHOUSE_NAME%>',
                width : 50,
               },  {
                field :  'statusName',
                title : '<%=TmbSupplierOrder.ALIAS_STATUS%>',
                width : 30,
                },{
                    field : 'reviewerName',
                    title : '审核人',
                    width : 35,
                },
				{
				field : 'action',
				title : '操作',
				width : 55,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit&& row.status == 'SS04') {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					str += '&nbsp;';
					if ($.canDelete&& row.status == 'SS04') {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					str += '&nbsp;';
					if ($.canExamine&& row.status == 'SS04') {
                        str += $.formatString('<img onclick="examineFun(\'{0}\');" src="{1}" title="审核"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/joystick.png');
					}
                    str += '&nbsp;';
                    if ($.canStockin&&row.status=='SS02') {
                        str += $.formatString('<img onclick="Stock(\'{0}\');" src="{1}" title="入库"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/carAndCartAndBasket/basket_put.png');
                    }
                    str += '&nbsp;';
                    if ($.canDeleteOrder) {
                        str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
                    }
                    str += '&nbsp;';
                    if ($.updateSupplierOrderStatus&&row.status=='SS02') {
                        str += $.formatString('<img onclick="updateSupplierOrderStatus(\'{0}\');" src="{1}" title="入库完成"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/database_go.png');
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
				$.post('${pageContext.request.contextPath}/mbSupplierOrderController/delete', {
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
         parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
		 parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbSupplierOrderController/editPage?id=' + id,
		});
	}
    function examineFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.modalDialog({
            title : '审核订单',
            width : 780,
            height : 300,
            href : '${pageContext.request.contextPath}/mbSupplierOrderController/examinePage?id=' + id,
            buttons: [{
                text: '通过',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid =  dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=status]").val("SS02");
                    f.submit();
                }
            },
                {
                    text: '拒绝',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid =  dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name=status]").val("SS01");
                        f.submit();

                    }
                }
            ]
        });
    }

	function viewOrder(id) {
        var href = '${pageContext.request.contextPath}/mbSupplierOrderController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '采购订单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
        });
    }

	function addFun() {
        $("#dlg").dialog("open").dialog("setTitle", "添加数据");
	}
    function Stock(id) {
        parent.$.modalDialog({
            title : '采购入库',
            width : 780,
            height : 500,
            href : '${pageContext.request.contextPath}/mbSupplierStockInController/addPage?id=' + id,
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
    function updateSupplierOrderStatus(id){
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        }
        parent.$.messager.confirm('询问', '您是否确认此订单已经全部入库？', function(b) {
            if (b) {
                parent.$.messager.progress({
                    title : '提示',
                    text : '数据处理中，请稍后....'
                });
                $.post('${pageContext.request.contextPath}/mbSupplierOrderController/updateSupplierOrderStatus', {
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
	function downloadTable(){
		var options = dataGrid.datagrid("options");
		var $colums = [];
		$.merge($colums, options.columns);
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/mbSupplierOrderController/download',
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


<%--添加订单详细信息--%>
<script type="text/javascript">{
        var editIndex = undefined;
        var dgItem;
        function endEditing(){
            if (editIndex == undefined){return true}
            if ($('#dg').datagrid('validateRow', editIndex)){
                $('#dg').datagrid('endEdit', editIndex);
                editIndex = undefined;
                return true;
            } else {
                return false;
            }
        }

        /*加载下拉选择*/
        $(function () {

            $.extend($.fn.datagrid.defaults.editors, {
                combogrid: {
                    init: function(container, options){
                     var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
                        input.combogrid(options);
                        return input;
                    },
                     destroy: function(target){
                        $(target).combogrid('destroy');
                    },
                    getValue: function(target){
                        return $(target).combogrid('getValue');
                    },
                    setValue: function(target, value){
                        $(target).combogrid('setValue', value);
                    },
                    resize: function(target, width){
                        $(target).combogrid('resize',width);
                    }
                }
            });


            function getCacheContainer(t){
                var view = $(t).closest('div.datagrid-view');
                var c = view.children('div.datagrid-editor-cache');
                if (!c.length){
                    c = $('<div class="datagrid-editor-cache" style="position:absolute;display:none"></div>').appendTo(view);
                }
                return c;
            }
            function getCacheEditor(t, field){
                var c = getCacheContainer(t);
                return c.children('div.datagrid-editor-cache-' + field);
            }
            function setCacheEditor(t, field, editor){
                var c = getCacheContainer(t);
                c.children('div.datagrid-editor-cache-' + field).remove();
                var e = $('<div class="datagrid-editor-cache-' + field + '"></div>').appendTo(c);
                e.append(editor);
            }

            $('.money_input').blur(function () {
                var source = $(this);
                var target = source.next();
                if (!/^([1-9]\d*|0)(\.\d{2})?$/.test(source.val())) {
                    source.val("").focus();
                }
                var val = source.val().trim();
                if (val.indexOf('.') > -1) {
                    val = val.replace('.', "");
                } else if (val != '') {
                    val += "00";
                }
                target.val(val);
            });

          dgItem = $('#dg').datagrid({
                fit: true,
                iconCls: 'icon-edit',
                singleSelect: true,
                toolbar: '#tbb',
                method: 'get',
                onClickCell: onClickCell,
                onEndEdit: onEndEdit,
                columns: [[
                 {
                    field: 'itemId',
                    title: '商品名称',
                    width: 250,
					align:"center",
                    editor: {
                        type: 'combogrid',
                        options: {
                            panelWidth: 450,
                            idField: 'id',
                            textField: 'text',
                            method: 'post',
                            mode: 'remote',
                            url: '${pageContext.request.contextPath}/mbItemController/selectQuery',
                            required: true,
                            columns: [[
                                {field: 'id', title: 'ID', width: 30},
                                {field:'code',title:'编码',width:100},
                                {field: 'text', title: '名称', width: 100},
                                {field: 'parentName', title: '分类', width: 50}
                            ]]
                        }
                    },
                   formatter:function(value,row){
                        return row.itemName;
                    }
                }, {
                    field: 'quantity',
                    title: '数量',
                    width: 230,
                    align:"center",
                    editor:{type:'numberbox',options:{required: true}}
                },{
                        field: 'priceStr',
                        title: '商品单价',
                        width: 220,
                        align:"center",
                        class:"money_input",
                        editor:{type:'text',options:{required: true}},
                    },{
                     field: 'price',
					 title: '单价',
					 width: 220,
                     align:"center",
                      hidden : true,
					}
				]],
                onLoadSuccess: function () {
                    parent.$.messager.progress('close');
                }
            });
        });
        function onClickCell(index, field){
            if (editIndex != index){
                if (endEditing()){
                    $('#dg').datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                    var ed = $('#dg').datagrid('getEditor', {index:index,field:field});
                    if (ed){
                        ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                    }
                    editIndex = index;
                } else {
                    setTimeout(function(){
                        $('#dg').datagrid('selectRow', editIndex);
                    },0);
                }
            }
        }
        function onEndEdit(index, row) {
            var ed = $(this).datagrid('getEditor', {
                index: index,
                field: 'itemId'
            });
            row.itemName = $(ed.target).combogrid('getText');
        }
        function append(){
            if (endEditing()){
                $('#dg').datagrid('appendRow',{});
                editIndex = $('#dg').datagrid('getRows').length-1;
                $('#dg').datagrid('selectRow', editIndex)
                    .datagrid('beginEdit', editIndex);
            }
        }
        function removeit(){
            if (editIndex == undefined){return}
            $('#dg').datagrid('cancelEdit', editIndex)
                .datagrid('deleteRow', editIndex);
            editIndex = undefined;
        }
        function save(){
            if (endEditing()) {
                var rows = getChanges();
                var data = $.serializeObject($('#form'));
                data.mbSupplierOrderItemList=rows;
                        $.ajax({
                            url: "${pageContext.request.contextPath}/mbSupplierOrderController/add",
                            data: JSON.stringify(data),
                            dataType: "json",
                            type: "POST",
                            contentType: "application/json;charset=UTF-8",
                            beforeSend: function (request) {
                                parent.$.messager.progress({
                                    title: '提示',
                                    text: '数据处理中，请稍后....'
                                });
                            },
                            success: function (data) {
                                parent.$.messager.progress('close');
                                if(data.success) {
                                    $('#dg').datagrid('acceptChanges');
                                    window.location.reload();
                                }else{
                                    //data
                                    parent.$.messager.alert('错误', data.msg);
                                }
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                parent.$.messager.progress('close');
                            }
                        });

                    }
        }

        function reject(){
            $('#dg').datagrid('rejectChanges');
            editIndex = undefined;
        }
        function getChanges(){
            var rows = $('#dg').datagrid('getChanges');
            return rows;
        }
 }
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
						<tr>
							<td>
								<strong><%=TmbSupplierOrder.ALIAS_SUPPLIER_NAME%></strong>&nbsp;&nbsp;<jb:selectGrid dataType="supplierId" name="supplierId"></jb:selectGrid>
							</td>
							</td>
							<td>
								<strong><%=TmbSupplierOrder.ALIAS_STATUS%></strong>&nbsp;&nbsp;<jb:select dataType="SS" name="status" value="${status}"></jb:select>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbSupplierOrderController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>
    <%--弹框--%>
	<div  id="dlg" class="easyui-dialog"    closed="true" 	style="width: 730px; height: 500px;align:center" buttons="#dlg-buttons">
		<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
			<form id="form" method="post">
				<input type="hidden" name="id"/>
				<table class="table table-hover table-condensed">
					<tr>
						<th><%=TmbSupplierOrder.ALIAS_SUPPLIER_NAME%></th>
						<td>
							<jb:selectGrid dataType="supplierId" name="supplierId" required="true"></jb:selectGrid>
						</td>
						<th><%=TmbSupplierOrder.ALIAS_PLAN_STOCK_IN_DATE%></th>
						<td>
							<input  class="easyui-validatebox span2" data-options="required:true" name="planStockInDate" type="text" onclick="WdatePicker({dateFmt:'<%=TmbSupplierOrder.FORMAT_PLAN_STOCK_IN_DATE%>'})"  maxlength="0" class="" />
						</td>
					</tr>
					<tr>
						<th><%=TmbSupplierOrder.ALIAS_WAREHOUSE_NAME%></th>
						<td>
							<jb:selectSql dataType="SQ004" name="warehouseId" required="true"></jb:selectSql>
						</td>
						<th><%=TmbSupplierOrder.ALIAS_REMARK%></th>
						<td>
							<input class="span2" name="remark" id="remark" type="text"/>
						</td>
					</tr>
				</table>
				<table id="dg" class="easyui-datagrid" title="订单详细信息" style="width:700px;height:auto"></table>
				<div id="tbb" >
					<a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_add',plain:true" onclick="append()">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_delete',plain:true" onclick="removeit()">删除</a>
				</div>
				<div id="dlg-buttons">
					<a href="#" class="easyui-linkbutton"   onclick="save()">保存</a>
					<a href="#" class="easyui-linkbutton"  onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>