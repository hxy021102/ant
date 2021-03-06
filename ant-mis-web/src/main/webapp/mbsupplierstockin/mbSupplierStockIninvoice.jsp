<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbSupplierFinanceLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>

<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbSupplierStockInController/editInvoiceStatus',

            onSubmit: function () {

                parent.$.messager.progress({
                    title: '提示',
                    text: '数据处理中，请稍后....'
                });
                var isValid = $(this).form('validate');
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

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="height:300px;overflow: hidden;">
        <form id="form" method="post">
            <input type="hidden" name="dataGrid" id="dataGrid"/>
            <input type="hidden" name="id" value="${mbSupplierStockIn.id}">
            <table class="table table-hover table-condensed">
                <tr>
                    <th><%=TmbSupplierFinanceLog.ALIAS_INVOICE_NO%>
                    </th>
                    <td>
                        <input name="invoiceNo" type="text" class="easyui-validatebox span2" data-options="required:true"/>
                    </td>

                </tr>
                <tr>
                    <th>备注
                    </th>
                    <td>
                        <textarea name="remark" cols="30" rows="5"
                                  style="width: 90%"></textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>