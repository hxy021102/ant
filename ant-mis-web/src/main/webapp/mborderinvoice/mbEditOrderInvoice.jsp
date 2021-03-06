<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbShopInvoice" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbOrderInvoiceController/editOrderInvoice',
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
                    parent.$.modalDialog.openner_dataGrid.datagrid('clearChecked');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
//选择下拉栏刷新页面数据
    function changeInvoice(selectValue) {
        $.post('${pageContext.request.contextPath}/mbShopInvoiceController/queryShopInvoice', {id: selectValue}, function (result) {
            if (result.success) {
                $("input[name='companyName']").val(result.obj.companyName);
                $("input[name='companyTfn']").val(result.obj.companyTfn);
                $("input[name='registerAddress']").val(result.obj.registerAddress);
                $("input[name='registerPhone']").val(result.obj.registerPhone);
                $("input[name='bankName']").val(result.obj.bankName);
                $("input[name='bankNumber']").val(result.obj.bankNumber);
                $("#invoiceUse").combobox('setValue', result.obj.invoiceUse);
                $("#invoiceType").combobox('setValue', result.obj.invoiceType);
            } else {
                $("#form input").val("");
            }
        }, "JSON");
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div  data-options="region:'center',border:false" title="" style="overflow: hidden;">
        <form id="form" method="post">
            <input type="hidden" name="ShopId" value="${mbShopInvoice.shopId}"/>
            <input type="hidden" name="loginId" value="${sessionInfo.id}">
            <input type="hidden" name="mbOrderInvoiceList">
            <table class="table table-hover table-condensed">
                <tr>
                    <th>选择开票模板
                    </th>
                    <td colspan="3">
                        <jb:selectSql dataType="SQ012" name="shopInvoiceId"
                                      parameters="{'shopId':${mbShopInvoice.shopId}}" required="true"
                                      onselect="changeInvoice" value="${mbShopInvoice.id}"></jb:selectSql>
                    </td>

                </tr>
                <tr>
                    <th><%=TmbShopInvoice.ALIAS_COMPANY_NAME%>
                    </th>
                    <td>
                        <input class="span2" name="companyName" type="text" value="${mbShopInvoice.companyName}"/>
                    </td>
                    <th><%=TmbShopInvoice.ALIAS_COMPANY_TFN%>
                    </th>
                    <td>
                        <input class="span2" name="companyTfn" type="text" value="${mbShopInvoice.companyTfn}"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbShopInvoice.ALIAS_REGISTER_ADDRESS%>
                    </th>
                    <td>
                        <input class="span2" name="registerAddress" type="text"
                               value="${mbShopInvoice.registerAddress}"/>
                    </td>
                    <th><%=TmbShopInvoice.ALIAS_REGISTER_PHONE%>
                    </th>
                    <td>
                        <input class="span2" name="registerPhone" type="text" value="${mbShopInvoice.registerPhone}"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbShopInvoice.ALIAS_BANK_NAME%>
                    </th>
                    <td>
                        <input class="span2" name="bankName" type="text" value="${mbShopInvoice.bankName}"/>
                    </td>
                    <th><%=TmbShopInvoice.ALIAS_BANK_NUMBER%>
                    </th>
                    <td>
                        <input class="span2" name="bankNumber" type="text" value="${mbShopInvoice.bankNumber}"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbShopInvoice.ALIAS_INVOICE_USE%>
                    </th>
                    <td>
                        <jb:select dataType="IU" name="invoiceUse" value="${mbShopInvoice.invoiceUse}"></jb:select>
                    </td>
                    <th><%=TmbShopInvoice.ALIAS_INVOICE_TYPE%>
                    </th>
                    <td>
                        <jb:select dataType="IT" name="invoiceType" value="${mbShopInvoice.invoiceType}"></jb:select>
                    </td>
                </tr>
                <tr>
                    <th>开票备注</th>
                    <td colspan="3">
                        <textarea style="width: 90%" cols="30" rows="3" name="remark"></textarea>
                    </td>

                </tr>
            </table>
        </form>
    </div>
</div>