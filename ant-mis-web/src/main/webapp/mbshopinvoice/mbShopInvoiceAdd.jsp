<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbShopInvoice" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbShopInvoiceController/add',
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
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
        <form id="form" method="post">
            <input type="hidden" name="id"/>
            <input type="hidden" name="shopId" value="${mbShopInvoice.shopId}">
            <table class="table table-hover table-condensed">
                <tr>
                    <th><%=TmbShopInvoice.ALIAS_COMPANY_NAME%>
                    </th>
                    <td>
                        <input class="span2" name="companyName" type="text"/>
                    </td>
                    <th><%=TmbShopInvoice.ALIAS_COMPANY_TFN%>
                    </th>
                    <td>
                        <input class="span2" name="companyTfn" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbShopInvoice.ALIAS_REGISTER_ADDRESS%>
                    </th>
                    <td>
                        <input class="span2" name="registerAddress" type="text"/>
                    </td>
                    <th><%=TmbShopInvoice.ALIAS_REGISTER_PHONE%>
                    </th>
                    <td>
                        <input class="span2" name="registerPhone" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbShopInvoice.ALIAS_BANK_NAME%>
                    </th>
                    <td>
                        <input class="span2" name="bankName" type="text"/>
                    </td>
                    <th><%=TmbShopInvoice.ALIAS_BANK_NUMBER%>
                    </th>
                    <td>
                        <input class="span2" name="bankNumber" type="text"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbShopInvoice.ALIAS_INVOICE_USE%>
                    </th>
                    <td>
                        <jb:select dataType="IU" name="invoiceUse" required="true"></jb:select>
                    </td>
                    <th><%=TmbShopInvoice.ALIAS_INVOICE_TYPE%>
                    </th>
                    <td>
                        <jb:select dataType="IT" name="invoiceType" required="true"></jb:select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>