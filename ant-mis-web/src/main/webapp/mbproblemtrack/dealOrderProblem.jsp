<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
	<title>Mbsupplierorder管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/mbProblemTrackItemController/view')}">
		<script type="text/javascript">
            $.canView= true;
		</script>
	</c:if>
	 <script type="text/javascript">
         var data;
         $(function() {
             parent.$.messager.progress('close');
         });
         $(function() {
            data= $('#itemDataGrid').datagrid({
                url : '${pageContext.request.contextPath}/mbProblemTrackItemController/dataGrid?problemOrderId='+${mbProblemTrack.id},
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
                },{
                    field : 'content',
                    title : '<%=TmbProblemTrackItem.ALIAS_CONTENT%>',
                    width : 50
                }, {
                    field : 'file',
                    title : '<%=TmbProblemTrackItem.ALIAS_FILE%>',
                    width : 50
                }, {
                    field : 'ownerId',
                    title : '<%=TmbProblemTrackItem.ALIAS_OWNER_ID%>',
                    width : 50
                }, {
                    field : 'lastOwnerId',
                    title : '<%=TmbProblemTrackItem.ALIAS_LAST_OWNER_ID%>',
                    width : 50
                },{
                    field : 'action',
                    title : '操作',
                    width : 100,
                    formatter : function(value, row, index) {
                        var str = '';
                        if ($.canView) {
                            str += "<a href='javascript:void(0);' onclick='viewFun(" + row.id + ")'>处理详细</a>";
                        }
                        return str;
                    }
                }] ],
            });
		});
         function viewFun(id) {
             if (id == undefined) {
                 var rows = dataGrid.datagrid('getSelections');
                 id = rows[0].id;
             }
             parent.$.modalDialog({
                 title : '查看数据',
                 width : 780,
                 height : 500,
                 href : '${pageContext.request.contextPath}/mbProblemTrackItemController/view?id=' + id
             });
         }

         function addDealOrderProblem(id,ownerId) {
             parent.$.modalDialog({
                 title : '添加数据',
                 width : 780,
                 height : 500,
                 href : '${pageContext.request.contextPath}/mbProblemTrackItemController/addPage?problemOrderId='+id+"&ownerId="+ownerId,
                 buttons : [
                     {
                         text : '关闭问题处理',
                         handler : function() {
                             parent.$.modalDialog.openner_dataGrid = data;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                             var f = parent.$.modalDialog.handler.find('#form');
                             f.find("input[name=status]").val("KK02");
                             f.submit();
                         }
                     }, {
                     text : '添加',
                     handler : function() {
                         parent.$.modalDialog.openner_dataGrid = data;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                         var f = parent.$.modalDialog.handler.find('#form');
                         f.submit();
                     }
                 } ]
             });
         }
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border:false">
	<div data-options="region:'north',title:'基本信息',border:false" style="height: 170px; overflow: hidden;">
		<table class="table">
			<tr>
				<th>问题ID:</th>
				<td>
					${mbProblemTrack.id}
					<c:if test="${fn:contains(sessionInfo.resourceList, '/mbProblemTrackItemController/addPage') and mbProblemTrack.status!='KK02' }">
					   <a onclick="addDealOrderProblem('${mbProblemTrack.id}','${mbProblemTrack.ownerId}');" href="javascript:void(0);" class="easyui-linkbutton">处理</a>
				    </c:if>
				</td>
				<th><%=TmbProblemTrack.ALIAS_TITLE%>:</th>
				<td>
					${mbProblemTrack.title}
				</td>
				<th><%=TmbProblemTrack.ALIAS_STATUS%>:</th>
				<td>
					${mbProblemTrack.statusName}
				</td>
				<th><%=TmbProblemTrack.ALIAS_OWNER_ID%>:</th>
				<td>
					${mbProblemTrack.ownerName}
				</td>
			</tr>
			<tr>
				<th><%=TmbProblemTrack.ALIAS_REF_TYPE%>:</th>
				<td>
					${mbProblemTrack.refTypeName}
				</td>
				<th><%=TmbProblemTrack.ALIAS_ORDER_ID%>:</th>
				<td colspan="5">
					${mbProblemTrack.orderId}
				</td>
			</tr>
			<tr>
				<th><%=TmbProblemTrack.ALIAS_DETAILS%>:</th>
				<td colspan="6">
					${mbProblemTrack.details}
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<div id="order_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="订单问题处理明细">
				<table id="itemDataGrid"></table>
			</div>
		</div>
	</div>
</div>
</body>
</html>

