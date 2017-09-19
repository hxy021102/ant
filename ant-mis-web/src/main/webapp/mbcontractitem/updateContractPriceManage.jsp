<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<!DOCTYPE html>
<html>
<head>
	<title>MbUser管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>

	<script type="text/javascript">
        var dataGrid;
        $(function () {
            dataGrid = $('#dataGrid').datagrid({
              //  url :  '${pageContext.request.contextPath}/mbContractItemController/dataGrid',
                fit : true,
                fitColumns : true,
                border : false,
                pagination : false,
                idField : 'id',
                pageSize : 10,
                nowrap : true,
                rownumbers : true,
                selectOnCheck: true,
                checkOnSelect: true,
                columns : [ [ {
                    field : 'id',
                    title : '编号',
                    width : 150,
                    hidden : true
                }, {
                    field : 'ck',
					checkbox:true,
                    width : 150
                }, {
                    field : 'code',
                    title : '<%=TmbContract.ALIAS_CODE%>',
                    width : 50
                }, {
                    field : 'itemName',
                    title : '<%=TmbContractItem.ALIAS_ITEM_NAME%>',
                    width : 50
                }, {
                    field : 'price',
                    title : '<%=TmbContractItem.ALIAS_PRICE%>',
                    width : 20,
                    sortable:true,
                    align:'right',
                    formatter:function(value){
                        return $.formatMoney(value);
                    }
                },{
                    field : 'shopName',
                    sortable:true,
                    title : '<%=TmbContract.ALIAS_SHOP_NAME%>',
                    width : 90
                }, {
                    field : 'expiryDateStart',
                    title : '<%=TmbContract.ALIAS_EXPIRY_DATE_START%>',
                    width : 60
                }, {
                    field : 'expiryDateEnd',
                    title : '<%=TmbContract.ALIAS_EXPIRY_DATE_END%>',
                    width : 60
                }] ],
                toolbar: '#toolbar',

            });

            parent.$.messager.progress('close');
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
            } );
        });


         function editContractPrice() {
             parent.$.modalDialog({
                 title : '编辑数据',
                 width : 780,
                 height : 200,
                 href : '${pageContext.request.contextPath}/mbContractItemController/editContractPricePage',
                 buttons : [ {
                     text : '编辑',
                     handler : function() {
                         parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                         var rows =$('#dataGrid').datagrid('getChecked')
                         var f = parent.$.modalDialog.handler.find('#form');
                         f.find("input[name= mbContractItemList]").val(JSON.stringify(rows));
                         f.submit();
                     }
                 } ]
             });
            }

        function searchFun() {
            var options = {};
            options.url =  '${pageContext.request.contextPath}/mbContractItemController/queryContractItem';
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
<div class="easyui-layout" data-options="fit : true,border : false"     >
	<div data-options="region:'north',border:false" style="height: 500px; overflow: hidden;">
		<div class="easyui-layout" data-options="fit : true,border : false">
			<div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;">
				<form id="searchForm">
					<table class="table table-hover table-condensed">
						<tr>
							<th><%=TmbSupplierContractItem.ALIAS_ITEM_NAME%></th>
							<td>
								<jb:selectGrid dataType="itemId" name="itemId" ></jb:selectGrid>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<table id="dataGrid"   ></table>
			</div>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbContractItemController/editContractPricePage')}">
			<a onclick="editContractPrice();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil'">批量修改</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/mbContractItemController/queryContractItem')}">
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>
</div>
</body>
</html>