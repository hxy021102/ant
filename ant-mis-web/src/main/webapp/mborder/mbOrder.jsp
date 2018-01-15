﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
<title>MbOrder管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url:'',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'updatetime',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '<%=TmbOrder.ALIAS_ID%>',
				width : 30,
				formatter : function (value, row, index) {
					return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
				}
			}, {
				field : 'shopId',
				title : '<%=TmbOrder.ALIAS_SHOP_ID%>',
				width : 20
			}, {
				field : 'shopName',
				title : '<%=TmbOrder.ALIAS_SHOP_NAME%>',
				width : 70
			}, {
				field: 'orderPrice',
				title: '<%=TmbOrder.ALIAS_ORDER_PRICE%>',
				width: 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'totalPrice',
				title : '<%=TmbOrder.ALIAS_TOTAL_PRICE%>',
				width : 25,
				align:'right',
				formatter:function(value){
					return $.formatMoney(value);
				}
			}, {
				field : 'orderStatusName',
				title : '<%=TmbOrder.ALIAS_STATUS%>',
				width : 50
			}, {
				field : 'deliveryStatusName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_STATUS%>',
				width : 30
			}, {
				field : 'deliveryWayName',
				title : '<%=TmbOrder.ALIAS_DELIVERY_WAY%>',
				width : 30
			}, {
				field : 'deliveryRequireTime',
				title : '<%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%>',
				width : 50,
                sortable: true
			}, {
				field : 'addtime',
				title : '<%=TmbOrder.ALIAS_ADDTIME%>',
				width : 50,
                sortable: true
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
			}
		});
		setTimeout(function(){
			searchFun();
		},100);
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

//        ,
//        parser:function(s){
//            if (!s){return new Date();}
//            var dt = s.split(' ');
//            var date = new Date(dt[0][2],dt[0][1],dt[0][0]);
//            if (dt.length>1){
//                date.setHours(dt[1][0]);
//                date.setMinutes(dt[1][1]);
//                date.setSeconds(dt[1][2]);
//            }
//            return date;
//        }
//    });
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbOrderController/editPage?id=' + id,
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

	function viewOrder(id) {
        var href = '${pageContext.request.contextPath}/mbOrderController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '订单详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
        });
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/mbOrderController/addPage',
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
	        url:'${pageContext.request.contextPath}/mbOrderController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function searchFun() {
		var options = {};
		options.url = '${pageContext.request.contextPath}/mbOrderController/dataGrid';
		options.queryParams = $.serializeObject($('#searchForm'));
		dataGrid.datagrid(options);
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 300px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th><%=TmbOrder.ALIAS_ID%>
						</th>
						<td>
							<input type="text" name="id" maxlength="10" class="span2"/>
						</td>
						<th><%=TmbOrder.ALIAS_CONTACT_PHONE%>
						</th>
						<td>
							<input type="text" name="contactPhone" maxlength="32" class="span2"/>
						</td>
						<th><%=TmbOrder.ALIAS_USER_ID%>
						</th>
						<td>
							<input type="text" name="userId" maxlength="10" class="span2"/>
						</td>
						<th>门店
						</th>
						<td>
							<jb:selectGrid dataType="shopId" name="shopId" value="${shopId}"></jb:selectGrid>
						</td>
					</tr>
					<tr>
						<th><%=TmbOrder.ALIAS_STATUS%>
						</th>
						<td>
							<jb:select dataType="OD" name="status" value="${status}"></jb:select>
						</td>
						<th><%=TmbOrder.ALIAS_DELIVERY_STATUS%>
						</th>
						<td>
							<jb:select dataType="DS" name="deliveryStatus"></jb:select>
						</td>
						<th><%=TmbOrder.ALIAS_PAY_STATUS%>
						</th>
						<td>
							<jb:select dataType="PS" name="payStatus" value="${payStatus}"></jb:select>
						</td>
						<th><%=TmbOrder.ALIAS_PAY_WAY%>
						</th>
						<td>
							<jb:select dataType="PW" name="payWay"></jb:select>
						</td>

					</tr>
					<%--<tr>--%>
						<%--<th>111</th>--%>
						<%--<td>--%>
							<%--<div>--%>
								<%--查询时间跨度: &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp--%>
								<%--<input type="radio" name="timerange" value="0" onclick="myRadioChange(this)">今天&nbsp&nbsp--%>
								<%--<input type="radio" name="timerange" value="1" onclick="myRadioChange(this)">近7天&nbsp&nbsp--%>
								<%--<input type="radio" name="timerange" value="2" onclick="myRadioChange(this)" >近30天&nbsp&nbsp--%>
								<%--<input type="radio" name="timerange" value="3" onclick="myRadioChange(this)">前三个月<br>--%>
								<%--从&nbsp&nbsp<input name="startTime" type="text" class="easyui-datetimebox myStartTimeClass" data-options="required:true" value="2010/01/05 22:36:55" style="width:140px">--%>
								<%--到&nbsp&nbsp<input name="endTime" type="text" class="easyui-datetimebox myEndTimeClass" data-options="required:true" value="2010/01/05 22:36:55" style="width:140px">--%>
							<%--</div>--%>
							<%--<script type="text/javascript">--%>
								<%--//输出指定格式的时间格式--%>
                                <%--Date.prototype.Format = function (fmt) { //author: meizz--%>
                                    <%--var o = {--%>
                                        <%--"M+": this.getMonth() + 1, //月份--%>
                                        <%--"d+": this.getDate(), //日--%>
                                        <%--"h+": this.getHours(), //小时--%>
                                        <%--"m+": this.getMinutes(), //分--%>
                                        <%--"s+": this.getSeconds(), //秒--%>
                                        <%--"q+": Math.floor((this.getMonth() + 3) / 3), //季度--%>
                                        <%--"S": this.getMilliseconds() //毫秒--%>
                                    <%--};--%>
                                    <%--if (/(Y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));--%>
                                    <%--for (var k in o)--%>
                                        <%--if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));--%>
                                    <%--return fmt;--%>
                                <%--}--%>
								<%--//监听选择的radio--%>
                                <%--function myRadioChange(myRadio) {--%>
                                     <%--var num, type, value = myRadio.value;--%>
                                     <%--switch (value) {--%>
										 <%--case "0": //今日--%>
                                             <%--num = 0;--%>
                                             <%--type = 'd';--%>
										     <%--break;--%>
										 <%--case "1"://近7日--%>
                                             <%--num = -6;--%>
                                             <%--type = 'd';--%>
                                             <%--break;--%>
										 <%--case "2"://近30日--%>
                                             <%--num = -29;--%>
                                             <%--type = 'd';--%>
                                             <%--break;--%>
                                         <%--case "3"://前三个月--%>
											<%--num = -2;--%>
                                         	<%--type = 'M';--%>
									 <%--}--%>
                                    <%--$('.myStartTimeClass').datetimebox('setValue', moment().add(num, type).format('YYYY-MM-DD'));	// set datetimebox value--%>
                                    <%--$('.myEndTimeClass').datetimebox('setValue', moment().format('YYYY-MM-DD HH:mm:ss'));	// set datetimebox value--%>
									<%--//将时间输出转换至目标格式--%>
                                    <%--var startTemp = $('.myStartTimeClass').datetimebox('getValue');		// get datetimebox value--%>
                                    <%--var endtTemp = $('.myEndTimeClass').datetimebox('getValue');		// get datetimebox value--%>
									<%--var startTime = new Date(startTemp.replace(/-/g, '/')).Format("YYYY*MM*dd hh+mm+ss+S");--%>
                                    <%--var endTime = new Date(endtTemp.replace(/-/g, '/')).Format("YYYY*MM*dd hh+mm+ss+S");--%>
                                <%--}--%>
<%--//                                function myParseDate(dateStr) {--%>
<%--//                                    return moment(dateStr, "YYYY/MM/DD").format("YYYY-MM-DD");--%>
<%--//								}--%>
                                <%--$('.easyui-datetimebox').datetimebox({--%>
                                    <%--min:'2015-10-01',--%>
                                    <%--max:'2019-11-11',--%>
                                    <%--formatter:function(date){--%>
                                        <%--var s1 = [date.getDate(),date.getMonth()+1,date.getFullYear()].join('/');--%>
                                        <%--var s2 = [date.getHours(),date.getMinutes(),date.getSeconds()].join(':');--%>
                                        <%--return s1 + s2;--%>
                                    <%--}--%>
                                <%--});--%>
<%--//                                $('.easyui-datetimebox').datebox({--%>
<%--//                                    min:'2015-10-01',--%>
<%--//                                    max:'2019-11-11',--%>
<%--//                                    onShowPanel:function(){--%>
<%--//                                        var opts = $(this).datebox('options');--%>
<%--//                                        $(this).datebox('calendar').calendar({--%>
<%--//                                            validator: function(date){--%>
<%--//                                                var min = opts.parser(opts.min);--%>
<%--//                                                var max = opts.parser(opts.max);--%>
<%--////                                                console.log(min + '----' + date)--%>
<%--////                                                return true;--%>
<%--//                                                if (min <= date && date <= max){--%>
<%--//                                                    return true;--%>
<%--//                                                } else {--%>
<%--//                                                    return false;--%>
<%--//                                                }--%>
<%--//                                            }--%>
<%--//                                        });--%>
<%--//                                    }--%>
<%--//                                })--%>
							<%--</script>--%>
                        <%--</td>--%>
					<%--</tr>--%>
                    <tr>
						<th>sssss</th>
						<td>
							<jb:timeRangePicker startTimeName="startAddTime" startTimeValue="2011-11-11 22:22:22"
												endTimeName="endAddTime" endTimeValue="2013-11-22 11:11:11" minDate="2011-11-11 00:00:00"
												required="true" format="YYYY-MM-DD HH:mm:ss" showSeconds="false" timeLimit="1"></jb:timeRangePicker>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbOrderController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>