<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>shopdeliverapply管理</title>
	<!-- 引入jQuery -->
	<script src="${pageContext.request.contextPath}/jslib/jquery-1.8.3.js"
			type="text/javascript" charset="utf-8"></script>
	<!-- 引入EasyUI -->
	<link id="easyuiTheme" rel="stylesheet"
		  href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css"
		  type="text/css">
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/jquery.easyui.min.js"
			charset="utf-8"></script>
	<!-- 扩展jQuery -->
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/jslib/extJquery.js?v=201305301341"
			charset="utf-8"></script>
	<!-- 扩展EasyUI Icon -->
	<link rel="stylesheet"
		  href="${pageContext.request.contextPath}/style/extEasyUIIcon.css?v=20170830"
		  type="text/css">
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"
			charset="utf-8"></script>

	<script type="text/javascript" src="${pageContext.request.scheme}://api.map.baidu.com/api?v=2.0&ak=uVkZBmjLC0KGflQtsXRc4rh4&s=1"></script>

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
				title : 'ID',
				width : 50,
                hidden:true
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
				width : 50,
				formatter : function(value, row, index) {
                    var str = '<a onclick="viewFun(' + row.id + ')">详情</a>';


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

				//$(this).datagrid('tooltip');
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
            id = rows.id;
        }
        var href = '${pageContext.request.contextPath}/shopDeliverApplyController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '派单申请详情'+ id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
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
            height : 490,
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
            width : 500,
            height : 400,
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

    function getAllShopRangeMap() {
        $('#shopLayout').layout('expand','east');
        var queryParams = $.serializeObject($('#searchForm'));
        $.post('${pageContext.request.contextPath}/shopDeliverApplyController/getAllShopRangeMap',queryParams,
            function (result) {
                if (result.success) {
                    getMaps(result.obj);
                }
            }, 'JSON');
     }

    var polygon ;
    var map;
    function getMaps(mapData) {
        var distributeRanges;
        // 百度地图API功能
        map = new BMap.Map("allmap");
        map.centerAndZoom(new BMap.Point(121.56, 31.12), 16);
        map.enableScrollWheelZoom(true);     //开启鼠标滚缩放
        for (var i = 0; i < mapData.length; i++) {
            distributeRanges = mapData[i].distributeRangeMapList;
            var pts = [];
            if (distributeRanges == null) {
                var pt1 = new BMap.Point(mapData[i].longitude - 0.009, mapData[i].latitude + 0.002);
                var pt2 = new BMap.Point(mapData[i].longitude + 0.006, mapData[i].latitude - 0.006);
                var pt3 = new BMap.Point(mapData[i].longitude + 0.008, mapData[i].latitude + 0.005);
                pts.push(pt1);
                pts.push(pt2);
                pts.push(pt3);
            } else {
                for (var k = 0; k < distributeRanges.length; k++) {
                    pts[k] = new BMap.Point(distributeRanges[k].lng, distributeRanges[k].lat);
                }
            }
            polygon = new BMap.Polygon(pts, {strokeColor: "blue", strokeWeight: 2, strokeOpacity: 0.5});
            var marker = new BMap.Marker(new BMap.Point(mapData[i].longitude, mapData[i].latitude));  // 创建标注
            var content = mapData[i].address;
            map.addOverlay(marker);               // 将标注添加到地图中
            addClickHandler(content, marker);
            map.addOverlay(polygon);   //增加多边形
            openEditing(polygon);
            getMapPoint(polygon, mapData[i].shopDeliverApplyId);
        }
    }
    function openEditing(polygon) {
        polygon.addEventListener("click", function (e) {
            polygon.enableEditing();
        });
    }
    function getMapPoint(polygon, shopDeliverApplyId) {
        polygon.addEventListener("mousemove", function (e) {
            var rangeArr = polygon.getPath();
            $("#distributeRange").val(JSON.stringify(rangeArr));
            $("#shopDeliverApplyId").val(shopDeliverApplyId);
        });
    }
    function addClickHandler(content, marker) {
        marker.addEventListener("click", function (e) {
                openInfo(content, e);
            }
        );
    }
    function openInfo(content, e) {
        var opts = {
            width: 250,     // 信息窗口宽度
            height: 95,     // 信息窗口高度
            enableMessage: true//设置允许信息窗发送短息
        };
        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, point); //开启信息窗口
    }

    function updateDistributeRange() {
        parent.$.messager.confirm('询问', '您是否要修改配送范围？', function (b) {
        if (b) {
        var distributeRange = $("#distributeRange").val();
        var shopDeliverApplyId = $("#shopDeliverApplyId").val();
        if (distributeRange != null) {
            $.ajax({
                url: '${pageContext.request.contextPath}/shopDeliverApplyController/updateDistributeRange?id=' + shopDeliverApplyId,
                data: distributeRange,
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
                    if (data.success) {
                        parent.$.messager.alert('提示', data.msg, 'info');
                        location.reload();
                    } else {
                        parent.$.messager.alert('错误', data.msg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    parent.$.messager.progress('close');
                }
            });
        } else {
            parent.$.messager.progress('close');
            parent.$.messager.alert('错误', "获取多边形顶点失败!");
           }
         }
       });
     }

    function clearEmptyRange() {
        var shopDeliverApplyId = $("#shopDeliverApplyId").val();
        parent.$.messager.confirm('询问', '您是否要重置配送范围？', function (b) {
            if (b) {
                parent.$.messager.progress({
                    title: '提示',
                    text: '数据处理中，请稍后....'
                });
                $.post('${pageContext.request.contextPath}/shopDeliverApplyController/deleteDistributeRange', {
                    id:shopDeliverApplyId
                }, function (result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        location.reload()
                    }
                    parent.$.messager.progress('close');
                }, 'JSON');
            }
        });
    }
</script>
<script type="text/javascript">


</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 65px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th style="width: 50px;">门店名称</th>
						<td>
							<jb:selectGrid dataType="shopId" name="shopId"></jb:selectGrid>
						</td>
						<th style="width: 200px;text-align: right">门店账号</th>
						<td>
							<input  class="easyui-textbox" style="width:90%;height:23px" name="accountId" maxlength="10"  />
						</td>
						<th style="width: 200px;text-align: right">审核状态
						</th>
						<td>
							<jb:select dataType="DAS" name="status" ></jb:select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<div id="shopLayout" class="easyui-layout" data-options="fit : true,border : false">
				<div data-options="region:'east',split:true,collapsed:true" title="地图模式">
					<div id="allmap" style="height: 100%"></div>
					<div id="control">
						<input type="hidden" id="distributeRange" name="distributeRange" />
						<input type="hidden" id="shopDeliverApplyId" name="shopDeliverApplyId" />
						<c:if test="${fn:contains(sessionInfo.resourceList, '/shopDeliverApplyController/updateDistributeRange')}">
							<button onclick="updateDistributeRange()">提交</button>
						</c:if>
						<c:if test="${fn:contains(sessionInfo.resourceList, '/shopDeliverApplyController/deleteDistributeRange')}">
							<button onclick="clearEmptyRange()">重置配送范围</button>
						</c:if>
					</div>
				</div>
				<div data-options="region:'center'">
					<table id="dataGrid"></table>
				</div>
			</div>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/shopDeliverApplyController/getAllShopRangeMap')}">
			<a onclick="getAllShopRangeMap();" href="javascript:void(0);" class="easyui-linkbutton"
			   data-options="plain:true,iconCls:''">网络大盘</a>
		</c:if>
	</div>	
</body>
</html>