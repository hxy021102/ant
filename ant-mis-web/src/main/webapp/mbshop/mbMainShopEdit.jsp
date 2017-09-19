<%@ page import="com.mobian.model.TmbUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbShopController/editMainShop',
            onSubmit: function () {
                parent.$.messager.progress({
                    title: '提示',
                    text: '数据处理中，请稍后....'
                });
                var isValid = $(this).form('validate');
                var parentId = $('#parentId').combo("getValue");
                var parentName = $('#parentId').combo("getText");
                if(parentId != '' && parentId == parentName) {
                    $.messager.alert('Warning', "请选择正确的主门店");
                    isValid = false;
                }
                if(parentId == $('input[name=id]').val()) {
                    $.messager.alert('Warning', "不允许自绑定，请选择其他门店");
                    isValid = false;
                }

                if (!isValid) {
                    parent.$.messager.progress('close');
                }
                return isValid;
            },
            success: function (result) {
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
<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
    <form id="form" method="post">
        <input type="hidden" name="id" value="${shopId}"/>
        <input type="hidden" name="balanceAmount" value="${balanceAmount}"/>
        <input type="hidden" name="cashBalanceAmount" value="${cashBalanceAmount}"/>
        <table class="table table-hover table-condensed">
            <tr>
                <th style="width: 80px;">选择主门店：</th>
                <td>
                    <jb:selectGrid dataType="shopId" name="parentId" required="true" params="{onlyMain:true}"></jb:selectGrid>
                </td>
            </tr>
        </table>
    </form>
</div>