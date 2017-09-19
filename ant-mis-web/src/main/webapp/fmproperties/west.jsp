<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var layout_west_tree;
	var layout_west_tree_url = '';
	var sessionInfo_userId = '${sessionInfo.id}';
	if (sessionInfo_userId) {
		layout_west_tree_url = '${pageContext.request.contextPath}/basedataController/goodsTree';
	}
	$(function() {
		layout_west_tree = $('#layout_west_tree').tree({
			url : layout_west_tree_url,
			parentField : 'pid',
			//lines : true,
			onClick : function(node) {

				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				searchFun(node.id);
			},
			onBeforeLoad : function(node, param) {
				if (layout_west_tree_url) {//只有刷新页面才会执行这个方法
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
				}
			},
			onLoadSuccess : function(node, data) {
				//layout_west_tree.tree('collapseAll');
				parent.$.messager.progress('close');
			}
		});

	});

</script>
<div class="easyui-accordion" data-options="fit:true,border:false">
	<div title="商品树" style="padding: 5px;"
		data-options="border:false,isonCls:'anchor',tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
					$('#layout_west_tree').tree('reload');
				}
			}, {
				iconCls : 'resultset_next',
				handler : function() {
					var node = $('#layout_west_tree').tree('getSelected');
					if (node) {
						$('#layout_west_tree').tree('expandAll', node.target);
					} else {
						$('#layout_west_tree').tree('expandAll');
					}
				}
			}, {
				iconCls : 'resultset_previous',
				handler : function() {
					var node = $('#layout_west_tree').tree('getSelected');
					if (node) {
						$('#layout_west_tree').tree('collapseAll', node.target);
					} else {
						$('#layout_west_tree').tree('collapseAll');
					}
				}
			} ]">
		<div class="well well-small">
			<ul id="layout_west_tree"></ul>
		</div>
	</div>
</div>