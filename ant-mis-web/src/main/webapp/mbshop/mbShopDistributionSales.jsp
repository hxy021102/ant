<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbShop" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<!DOCTYPE html>
<html>
<head>
    <title>MbShop管理</title>
    <jsp:include page="../inc.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.scheme}://api.map.baidu.com/api?v=2.0&ak=uVkZBmjLC0KGflQtsXRc4rh4&s=1"></script>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbShopController/view')}">
        <script type="text/javascript">
            $.canView = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList,'/mbShopController/editShopInChargeUserPage' )}">
        <script type="text/javascript">
            $.canEditInChargerUser = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList,'/mbShopController/editMainShopPage' )}">
        <script type="text/javascript">
            $.canEditMainShop = true;
        </script>
    </c:if>
    <script type="text/javascript">

        var dataGrid;
        $(function () {
            dataGrid = $('#dataGrid').datagrid({

                url: '',
                fit: true,
                fitColumns: true,
                border: false,
                pagination: true,
                idField: 'id',
                pageSize: 10,
                pageList: [10, 20, 30, 40, 50],
                sortName: 'id',
                sortOrder: 'desc',
                checkOnSelect: false,
                selectOnCheck: false,
                nowrap: true,
                striped: true,
                rownumbers: true,
                singleSelect: true,
                columns: [[
                    {field: 'ck', checkbox: true},
                    {
                        field: 'id',
                        title: '门店ID',
                        width: 25,
                        formatter: function (value, row, index) {
                            return '<a onclick="viewShop(' + row.id + ')">' + row.id + '</a>';
                        }
                    }, {
                        field: 'name',
                        title: '<%=TmbShop.ALIAS_NAME%>',
                        width: 60,
                        formatter: function (value, row, index) {
                            var str = value;
                            if(row.parentId == -1) str = '<font color="red">(主)</font>' + value;
                            return str;
                        }
                    }, {
                        field: 'parentName',
                        title: '主店名称',
                        width: 60
                    }, {
                        field: 'regionPath',
                        title: '<%=TmbShop.ALIAS_REGION_ID%>',
                        width: 70
                    }, {
                        field: 'address',
                        title: '<%=TmbShop.ALIAS_ADDRESS%>',
                        width: 130
                    }, {
                        field: 'contactPhone',
                        title: '<%=TmbShop.ALIAS_CONTACT_PHONE%>',
                        width: 45
                    }, {
                        field: 'contactPeople',
                        title: '<%=TmbShop.ALIAS_CONTACT_PEOPLE%>',
                        width: 30
                    }, {
                        field: 'shopTypeName',
                        title: '<%=TmbShop.ALIAS_SHOP_TYPE%>',
                        width: 30
                    }, {
                        field: 'balanceAmount',
                        title: '余额',
                        width: 60,
                        align: 'right',
                        formatter: function (value, row) {
                            if(row.balanceAmount == undefined)return "";
                            return '<a onclick="viewBalance(' + row.id + ')">' + $.formatMoney(row.balanceAmount) + '</a>';
                        }
                    }, {
                        field: 'cashBalanceAmount',
                        title: '桶余额',
                        width: 50,
                        align: 'right',
                        formatter: function (value, row) {
                            if(row.cashBalanceAmount == undefined)return "";
                            return '<a onclick="viewCashBalance(' + row.cashBalanceId + ',' + row.id + ')">' + $.formatMoney(row.cashBalanceAmount) + '</a>';
                        }
                    }, {
                        field: 'auditStatusName',
                        title: '审核状态',
                        width: 30
                    }]],
                toolbar: '#toolbar',
                onLoadSuccess: function () {
                    $('#searchForm table').show();
                    parent.$.messager.progress('close');

                    //$(this).datagrid('tooltip');
                }
            });
            setTimeout(function () {
                searchFun();
            }, 100);
        });
        function viewBalance(id) {
            var href = '${pageContext.request.contextPath}/mbUserController/viewBalance?shopId=' + id;
            parent.$("#index_tabs").tabs('add', {
                title: '余额-' + id,
                content: '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
                closable: true
            });
        }



        function viewCashBalance(balanceId,shopId) {
            var href = '${pageContext.request.contextPath}/mbBalanceController/viewCash?id=' + balanceId+"&shopId="+shopId;
            parent.$("#index_tabs").tabs('add', {
                title: '桶余额-' + shopId,
                content: '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
                closable: true
            });
        }
        function deleteFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.messager.confirm('询问', '您是否要删除当前数据？', function (b) {
                if (b) {
                    parent.$.messager.progress({
                        title: '提示',
                        text: '数据处理中，请稍后....'
                    });
                    $.post('${pageContext.request.contextPath}/mbShopController/delete', {
                        id: id
                    }, function (result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            dataGrid.datagrid('reload');
                        }
                        parent.$.messager.progress('close');
                    }, 'JSON');
                }
            });
        }
        function viewShop(id) {
            var href = '${pageContext.request.contextPath}/mbShopController/view?id=' + id;
            parent.$("#index_tabs").tabs('add', {
                title: '门店详情-' + id,
                content: '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
                closable: true
            });
        }

        function viewFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '查看数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbShopController/view?id=' + id
            });
        }

        function addFun() {
            parent.$.modalDialog({
                title: '添加数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbShopController/addPage',
                buttons: [{
                    text: '添加',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                }]
            });
        }
        function downloadTable() {
            var options = dataGrid.datagrid("options");
            var $colums = [];
            $.merge($colums, options.columns);
            $.merge($colums, options.frozenColumns);
            var columsStr = JSON.stringify($colums);
            $('#downloadTable').form('submit', {
                url: '${pageContext.request.contextPath}/mbShopController/download',
                onSubmit: function (param) {
                    $.extend(param, $.serializeObject($('#searchForm')));
                    param.downloadFields = columsStr;
                    param.page = options.pageNumber;
                    param.rows = options.pageSize;

                }
            });
        }

        function searchFun() {
            var options = {};
            options.url = '${pageContext.request.contextPath}/mbShopController/distributionSalesDataGrid';
            options.queryParams = $.serializeObject($('#searchForm'));
            dataGrid.datagrid(options);
        }

        function cleanFun() {
            $('#searchForm input').val('');
            dataGrid.datagrid('load', {});
        }

        function showShopMap() {
            //$('#shopLayout').layout('collapse','east');
            $('#shopLayout').layout('expand','east');
            var queryParams = $.serializeObject($('#searchForm'));
            $.post('${pageContext.request.contextPath}/mbShopController/getShopMap',queryParams,
                function (result) {
                    if (result.success) {
                        //result.obj
                        renderMap(result.obj);
                    }
                }, 'JSON');
        }

        function  addShopSales() {
            var rows = $('#dataGrid').datagrid('getChecked');
            parent.$.modalDialog({
                title: '添加数据',
                width: 250,
                height: 200,
                href: '${pageContext.request.contextPath}/mbShopController/addShopSalesPage',
                buttons: [{
                    text: '确定',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name= mbShopList]").val(JSON.stringify(rows));
                        f.submit();
                    }
                }]
            });
        }

    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 115px; overflow: hidden;">
        <form id="searchForm">
            <table class="table table-hover table-condensed" style="display: none;">
                <tr>
                    <th style="width: 50px">审核状态
                    </th>
                    <td>
                        <jb:select dataType="AS" name="auditStatus" value="${auditStatus}" ></jb:select>
                    </td>
                    <th>销售</th>
                    <td>
                        <jb:selectSql name="salesLoginId" dataType="SQ018"></jb:selectSql>
                    </td>
                </tr>
                <tr>
                    <th style="width: 50px"><%=TmbShop.ALIAS_REGION_ID%>
                    </th>
                    <td>
                        <jb:selectGrid dataType="region" name="regionId" value="${mbShop.regionId}"></jb:selectGrid>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false">
        <div id="shopLayout" class="easyui-layout" data-options="fit : true,border : false">
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
                        console.log(map)
                        map.centerAndZoom(new BMap.Point(121.56, 31.12), 12.5);
                        map.enableScrollWheelZoom(true);     //开启鼠标滚缩放

                        for (var i = 0; i < mapArray.length; i++) {
                            var marker = new BMap.Marker(new BMap.Point(mapArray[i][0], mapArray[i][1]));  // 创建标注
                            var content = mapArray[i][2];
                            map.addOverlay(marker);               // 将标注添加到地图中
                            addClickHandler(content, marker);
                        }
                    }
                    function addClickHandler(content,marker){
                        marker.addEventListener("click",function(e){
                            openInfo(content,e)}
                        );
                    }
                    function openInfo(content,e){
                        var opts = {
                            width: 250,     // 信息窗口宽度
                            height: 95,     // 信息窗口高度
                            /*title : "信息窗口" , // 信息窗口标题*/
                            enableMessage: true//设置允许信息窗发送短息
                        };
                        var p = e.target;
                        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
                        var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象
                        map.openInfoWindow(infoWindow,point); //开启信息窗口
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
    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true"
       onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbShopController/download')}">
        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true"
           onclick="downloadTable();">导出</a>

        <form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
        </form>
        <iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbShopController/getShopMap')}">
        <a onclick="showShopMap();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:''">门店地图</a>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbShopController/addShopSalesPage')}">
        <a onclick="addShopSales();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'pencil'">批量分配销售</a>
    </c:if>
</div>
</body>
</html>