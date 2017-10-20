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
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbShopController/editPage')}">
        <script type="text/javascript">
            $.canEdit = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbShopController/editAuditPage')}">
        <script type="text/javascript">
            $.canEditAudit = true;
        </script>
    </c:if>
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbShopController/delete')}">
        <script type="text/javascript">
            $.canDelete = true;
        </script>
    </c:if>
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
                    }, {
                        field: 'action',
                        title: '操作',
                        width: 45,
                        formatter: function (value, row, index) {
                            var str = '';
                            <%--if ($.canEdit) {--%>
                            <%--str += $.formatString('<img onclick="editShopInChargeUserFun(\'{0}\');" src="{1}" title="更改负责人"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');--%>
                            <%--}--%>
                            str += '&nbsp;';
                            if ($.canEdit) {
                                str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
                            }
                            str += '&nbsp;';
                            if ($.canEditAudit && row.auditStatus == 'AS01') {
                                str += $.formatString('<img onclick="editAuditFun(\'{0}\');" src="{1}" title="审核"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/joystick.png');
                            }
                            str += '&nbsp;';
                            if ($.canEditInChargerUser && row.auditStatus == 'AS02') {
                                str += $.formatString('<img onclick="editShopInChargeUserFun(\'{0}\');" src="{1}" title="更改负责人"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');
                            }
                            str += '&nbsp;';
                            if ($.canEditMainShop && row.auditStatus == 'AS02' && row.parentId != -1) {
                                str += $.formatString('<img onclick="editMainShopFun(\'{0}\');" src="{1}" title="绑定主店"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/sitemap.png');
                            }
                            str += '&nbsp;';
                            if ($.canDelete) {
                                str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png  ');
                            }
                            str += '&nbsp;';

                            return str;
                        }
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

        function editShopInChargeUserFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '门店负责人修改',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbShopController/editShopInChargeUserPage?id=' + id,
                buttons: [{
                    text: '编辑',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                }]
            });
        }

        function editMainShopFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '绑定主店',
                width: 600,
                height: 180,
                href: '${pageContext.request.contextPath}/mbShopController/editMainShopPage?id=' + id,
                buttons: [{
                    text: '绑定',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        var balanceAmount = f.find('input[name=balanceAmount]').val();
                        var cashBalanceAmount = f.find('input[name=cashBalanceAmount]').val();
                        var parentId = f.find('#parentId').combo("getValue");
                        var parentName = f.find('#parentId').combo("getText");
                        if((balanceAmount == 0 && cashBalanceAmount == 0) || parentId == '' || parentId == parentName) f.submit();
                        else {
                            var msg = '该门店下';
                            if(balanceAmount != 0) {
                                msg += '【余额' + $.formatMoney(balanceAmount) + '元】';
                            }
                            if(cashBalanceAmount != 0) {
                                msg += '【桶余额' + $.formatMoney(cashBalanceAmount) + '元】';
                            }
                            msg += '全部绑定至【'+parentName+'】门店下，是否继续？';

                            parent.$.messager.confirm('询问', msg, function(b) {
                                if (b) {
                                    f.submit();
                                }
                            });
                        }
                    }
                }]
            });
        }

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

        function editFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '编辑数据',
                width: 780,
                height: 500,
                href: '${pageContext.request.contextPath}/mbShopController/editPage?id=' + id,
                buttons: [{
                    text: '编辑',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.submit();
                    }
                }]
            });
        }

        function editAuditFun(id) {
            if (id == undefined) {
                var rows = dataGrid.datagrid('getSelections');
                id = rows[0].id;
            }
            parent.$.modalDialog({
                title: '编辑数据',
                width: 780,
                height: 600,
                href: '${pageContext.request.contextPath}/mbShopController/editAuditPage?id=' + id,
                buttons: [{
                    text: '同意',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name=auditStatus]").val("AS02");
                        f.submit();
                    }
                }, {
                    text: '拒绝',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#form');
                        f.find("input[name=auditStatus]").val("AS03");
                        f.submit();
                    }

                }]
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
            options.url = '${pageContext.request.contextPath}/mbShopController/dataGrid';
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

        function getAllShopLocation() {
            $.post('${pageContext.request.contextPath}/mbShopController/getAllShopLocation',
                function (result) {
                if (result.success) {
                    parent.$.messager.alert('提示', "获取成功",'info');
                }
            }, 'JSON');
        }

    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 115px; overflow: hidden;">
        <form id="searchForm">
            <table class="table table-hover table-condensed" style="display: none;">
                <tr>
                    <th style="width: 50px">ID
                    </th>
                    <td >
                        <input type="text" name="id" maxlength="128" class="span2" value="${id}"/>
                    </td>
                    <th style="width: 50px"><%=TmbShop.ALIAS_NAME%>
                    </th>
                    <td>
                        <input type="text" name="name" maxlength="128" class="span2"/>
                    </td>
                    <th style="width: 50px">主店名称</th>
                    <td>
                        <jb:selectGrid dataType="shopId" name="parentId" params="{onlyMain:true}"></jb:selectGrid>
                    </td>
                    <th style="width: 50px">审核状态
                    </th>
                    <td>
                        <jb:select dataType="AS" name="auditStatus" value="${auditStatus}" ></jb:select>
                    </td>
                    <th style="width: 50px;"><%=TmbShop.ALIAS_SHOP_TYPE%>
                    </th>
                    <td>
                        <jb:select dataType="ST" name="shopType" mustSelect="true"></jb:select>
                    </td>
                </tr>
                <tr>

                    <th style="width: 50px"><%=TmbShop.ALIAS_CONTACT_PHONE%>
                    </th>
                    <td>
                        <input type="text" name="contactPhone" class="span2"/>
                    </td>
                    <th style="width: 50px"><%=TmbShop.ALIAS_CONTACT_PEOPLE%>
                    </th>
                    <td>
                        <input type="text" name="contactPeople" class="span2"/>
                    </td>
                    <th style="width: 50px"><%=TmbShop.ALIAS_REGION_ID%>
                    </th>
                    <td>
                        <jb:selectGrid dataType="region" name="regionId" value="${mbShop.regionId}"></jb:selectGrid>
                    </td>
                    <th style="width: 50px">
                        <%=TmbShop.ALIAS_ADDRESS%>
                    </th>
                    <td colspan="3">
                        <input type="text" name="address" class="span2"/>
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
                            mapArray[k] = new Array(4);
                        }
                        for (var i = 0; i < mapArray.length; i++) {
                            for (var j = 0; j < mapArray[i].length; j++) {
                                if (j == 0) {
                                    mapArray[i][j] = mapData[i].longitude;
                                } else if (j == 1) {
                                    mapArray[i][j] = mapData[i].latitude
                                } else if (j == 2) {
                                    mapArray[i][j] = mapData[i].address
                                } else if (j == 3) {
                                    mapArray[i][j] = mapData[i].shopType;
                                }
                            }
                        }
                        // 百度地图API功能
                        map = new BMap.Map("allmap");
                        console.log(map)
                        map.centerAndZoom(new BMap.Point(121.56, 31.12), 12.5);
                        map.enableScrollWheelZoom(true);     //开启鼠标滚缩放
                       // var joinIcon = new  BMap.Icon('${pageContext.request.contextPath}/style/images/map/directStoreIcon.png', new BMap.Size(20,40));
                        var directUnitIcon = new  BMap.Icon('${pageContext.request.contextPath}/style/images/map/directUnitIcon.jpg', new BMap.Size(20,40));
                        var directStoreIcon = new  BMap.Icon('${pageContext.request.contextPath}/style/images/map/directStoreIcon.png', new BMap.Size(20,40));
                        var testIcon = new  BMap.Icon('${pageContext.request.contextPath}/style/images/map/testIcon.png', new BMap.Size(20,40));
                        for (var i = 0; i < mapArray.length; i++) {
                            var storeIcon;
                            switch (mapArray[i][3]){
                                case "ST01":
                                    storeIcon = null;break;
                                case  "ST02":
                                    storeIcon = directUnitIcon;break;
                                case "ST03" :
                                    storeIcon = directStoreIcon;break;
                                case "ST10":
                                    storeIcon = testIcon;break;
                            }
                            if(storeIcon ==null) {
                                var marker = new BMap.Marker(new BMap.Point(mapArray[i][0], mapArray[i][1]));  // 创建标注
                            }else{
                                var marker = new BMap.Marker(new BMap.Point(mapArray[i][0], mapArray[i][1]), {icon: storeIcon});  // 创建标注

                            }
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
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbShopController/addPage')}">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'pencil_add'">添加</a>
    </c:if>
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
    <c:if test="${fn:contains(sessionInfo.resourceList, '/mbShopController/getAllShopLocation')}">
        <a onclick="getAllShopLocation();" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'pencil_add'">获取所有门店数字地址</a>
    </c:if>
</div>
</body>
</html>