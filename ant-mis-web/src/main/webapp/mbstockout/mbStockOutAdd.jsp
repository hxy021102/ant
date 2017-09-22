<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbStockOut" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbStockOutController/add',
			onSubmit : function() {
                accept();
                getItem();
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});

</script>
<script type="text/javascript">
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

        var editors = $.fn.datagrid.defaults.editors;
        for(var editor in editors){
            var opts = editors[editor];
            (function(){
                var init = opts.init;
                opts.init = function(container, options){
                    var field = $(container).closest('td[field]').attr('field');
                    var ed = getCacheEditor(container, field);
                    if (ed.length){
                        ed.appendTo(container);
                        return ed.find('.datagrid-editable-input');
                    } else {
                        return init(container, options);
                    }
                }
            })();
            (function(){
                var destroy = opts.destroy;
                opts.destroy = function(target){
                    if ($(target).hasClass('datagrid-editable-input')){
                        var field = $(target).closest('td[field]').attr('field');
                        setCacheEditor(target, field, $(target).parent().children());
                    } else if (destroy){
                        destroy(target);
                    }
                }
            })();
        }


        dgItem = $('#dg').datagrid({
            fit: true,
            iconCls: 'icon-edit',
            singleSelect: true,
            toolbar: '#tb',
            method: 'get',
            fitColumns : true,
            onClickCell: onClickCell,
            onEndEdit: onEndEdit,
            columns: [[{
                field: 'itemId',
                title: '商品名称',
                width: 250,
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
                            {field:'code',title:'编码',width:180},
                            {field: 'text', title: '名称', width: 180},
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
                width: 50,
                editor:{type:'numberbox',options:{required: true}}
            }]],
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
    function getItem(){
        var rows=$('#dg').datagrid('getRows');
        $('#dataGrid').val(JSON.stringify(rows));
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
    function accept() {
        if (endEditing()) {
            $('#dg').datagrid('acceptChanges');
        }
    }

    function confirmFun(id) {
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
                $.post('${pageContext.request.contextPath}/mbOrderController/delete', {
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

    function reject(){
        $('#dg').datagrid('rejectChanges');
        editIndex = undefined;
    }
    function getChanges(){
        var rows = $('#dg').datagrid('getChanges');
        return rows;
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
				<input type="hidden" name="id"/>
			<input type="hidden" name="loginId" value="${sessionInfo.id}">
            <input type="hidden" name="dataGrid" id="dataGrid">
			<table class="table table-hover table-condensed">
				<tr>
					<th style="width: 50px;"><%=TmbStockOut.ALIAS_STOCK_OUT_PEOPLE_ID%></th>
					<td>
						<jb:selectSql dataType="SQ010" name="stockOutPeopleId" required="true"></jb:selectSql>
					</td>

					<th style="width: 50px;"><%=TmbStockOut.ALIAS_STOCK_OUT_TIME%></th>
					<td>
						<input  name="stockOutTime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbStockOut.FORMAT_STOCK_OUT_TIME%>'})"  maxlength="0" class="easyui-validatebox span2" data-options="required:true"  />
					</td>
				</tr>
				<tr>
					<th style="width: 50px;"><%=TmbStockOut.ALIAS_WAREHOUSE_ID%>
					</th>
					<td>
						<jb:selectSql dataType="SQ005" name="warehouseId" required="true"></jb:selectSql>
					</td>
					<th style="width: 50px;"><%=TmbStockOut.ALIAS_STOCK_OUT_TYPE%></th>
					<td>
						<jb:select name="stockOutType" dataType="OT" required="true"></jb:select>
					</td>
				</tr>
				<tr>
					<th style="width: 50px;"><%=TmbStockOut.ALIAS_REMARK%></th>
					<td colspan="3"><textarea style="width: 90%" cols="30" rows="3" name="remark" class="easyui-validatebox span2" data-options="required:true"></textarea></td>
					</td>
				</tr>
			</table>
			<div style="overflow: auto;height: 230px">
            <table id="dg" class="easyui-datagrid"  style="height:auto">
			</table>
			</div>

            <div id="tb" style="height:auto;display: none;">
                <a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_add',plain:true" onclick="append()">添加</a>
                <a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_delete',plain:true" onclick="removeit()">删除</a>
                <%--<a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'disk',plain:true" onclick="accept()">保存</a>--%>
            </div>




		</form>
	</div>
</div>