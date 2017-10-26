<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrderRefundItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
    $(function() {
        parent.$.messager.progress('close');
        $('#form').form({
            url : '${pageContext.request.contextPath}/mbOrderRefundItemController/addRefund',
            onSubmit : function() {
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
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
        <form id="form" method="post">
            <input name="orderId" type="hidden" value="${orderId}"/>
            <table class="table table-hover table-condensed">
                <tr>
                    <th><%=TmbOrderRefundItem.ALIAS_ITEM_ID%></th>
                    <td >

                        <jb:selectSql dataType="SQ009" name="itemId" parameters="{'orderId':${orderId}}" required="true"></jb:selectSql>
                    </td>
                    <th><%=TmbOrderRefundItem.ALIAS_QUANTITY%></th>
                    <td>
                        <input class="span2 easyui-validatebox" name="quantity" type="number" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbOrderRefundItem.ALIAS_REFUND_TYPE%></th>
                    <td colspan="3">
                        <jb:select dataType="RI" name="type" mustSelect="true" required="true"></jb:select>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbOrderRefundItem.ALIAS_REMARK%></th>
                    <td colspan="3">
                        <textarea  name="remark" class="span6 easyui-validatebox"  ></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
