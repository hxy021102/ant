<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbWarehouse" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>MbWarehouse管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.scheme}://api.map.baidu.com/api?v=2.0&ak=uVkZBmjLC0KGflQtsXRc4rh4&s=1"></script>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWarehouseController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWarehouseController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWarehouseController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/mbWarehouseController/dataGrid',
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
				field : 'code',
				title : '<%=TmbWarehouse.ALIAS_CODE%>',
				width : 50		
				}, {
				field : 'name',
				title : '<%=TmbWarehouse.ALIAS_NAME%>',
				width : 80
				}, {
                field : 'warehouseTypeName',
                title : '<%=TmbWarehouse.ALIAS_WAREHOUSE_TYPE%>',
                width : 50
            	}, {
			    field : 'regionPath',
                title : '<%=TmbWarehouse.ALIAS_REGION_ID%>',
                width : 100
				}, {
				field : 'address',
				title : '<%=TmbWarehouse.ALIAS_ADDRESS%>',
				width : 150
				}, {
				field : 'action',
				title : '操作',
				width : 30,
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
				$.post('${pageContext.request.contextPath}/mbWarehouseController/delete', {
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
			href : '${pageContext.request.contextPath}/mbWarehouseController/editPage?id=' + id,
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
			href : '${pageContext.request.contextPath}/mbWarehouseController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbWarehouseController/addPage',
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
	        url:'${pageContext.request.contextPath}/mbWarehouseController/download',
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

    function showWarehouseMap() {
        //$('#shopLayout').layout('collapse','east');
        $('#WarehouseLayout').layout('expand','east');
        var queryParams = $.serializeObject($('#searchForm'));
        $.post('${pageContext.request.contextPath}/mbWarehouseController/getWarehouseMap',queryParams,
            function (result) {
                if (result.success) {
                    //result.obj
                    renderMap(result.obj);
                }
            }, 'JSON');
    }
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th style="width: 50px"><%=TmbWarehouse.ALIAS_CODE%></th>
						<td>
							<input type="text" name="code" maxlength="32" class="span2"/>
						</td>
						<th style="width: 50px"><%=TmbWarehouse.ALIAS_NAME%></th>
						<td>
							<input type="text" name="name" maxlength="128" class="span2"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<div id="WarehouseLayout" class="easyui-layout" data-options="fit : true,border : false">
				<div data-options="region:'east',split:true,collapsed:true" title="地图模式">
					<div id="allmap" style="height: 100%">
					</div>
					<script type="text/javascript">
                        var map;
                        function renderMap(mapData) {
                            var mapArray = new Array(mapData.length);
                            for (var k = 0; k < mapArray.length; k++) {
                                mapArray[k] = new Array(3);
                            }
                            for (var i = 0; i < mapArray.length; i++) {
                                for (var j = 0; j < mapArray[i].length; j++) {
                                    if (j == 0) {
                                        mapArray[i][j] = mapData[i].longitude;
                                    } else if (j == 1) {
                                        mapArray[i][j] = mapData[i].latitude
                                    } else if (j == 2) {
                                        mapArray[i][j] = mapData[i].address
                                    }
                                }
                            }
                            // 百度地图API功能
                            map = new BMap.Map("allmap");
                            map.centerAndZoom(new BMap.Point(121.56, 31.12), 12.5);
                            map.enableScrollWheelZoom(true);     //开启鼠标滚缩放

                            for (var i = 0; i < mapArray.length; i++) {
                                var marker = new BMap.Marker(new BMap.Point(mapArray[i][0], mapArray[i][1]));  // 创建标注
                                var content = mapArray[i][2];
                                map.addOverlay(marker);               // 将标注添加到地图中
                                addClickHandler(content, marker);
                            }
                          }
                            function addClickHandler(content, marker) {
                                marker.addEventListener("click", function (e) {
                                        openInfo(content, e)
                                    }
                                );
                            }

                            function openInfo(content, e) {
                                var opts = {
                                    width: 250,     // 信息窗口宽度
                                    height: 80,     // 信息窗口高度
									/*title : "信息窗口" , // 信息窗口标题*/
                                    enableMessage: true//设置允许信息窗发送短息
                                };
                                var p = e.target;
                                var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
                                var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
                                map.openInfoWindow(infoWindow, point); //开启信息窗口
                            }


					</script>
				</div>
		<div data-options="region:'center'">
			<table id="dataGrid"></table>
		</div>
	 </div>
	</div>
</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWarehouseController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWarehouseController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbWarehouseController/getWarehouseMap')}">
			<a onclick="showWarehouseMap();" href="javascript:void(0);" class="easyui-linkbutton"
			   data-options="plain:true,iconCls:''">仓库地图</a>
		</c:if>
	</div>	
</body>
</html>