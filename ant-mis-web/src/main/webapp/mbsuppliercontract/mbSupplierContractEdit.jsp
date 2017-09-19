<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbSupplierContract" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbSupplierContractController/edit',
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
            <input type="hidden" name="id" value="${mbSupplierContract.id}"/>
            <table class="table table-hover table-condensed">
                <tr>
                    <th><%=TmbSupplierContract.ALIAS_CODE%>
                    </th>
                    <td>
                        <input class="easyui-validatebox span2" data-options="required:true" name="code" required="true"
                               type="text" value="${mbSupplierContract.code}"/>
                    </td>
                    <th><%=TmbSupplierContract.ALIAS_NAME%>
                    </th>
                    <td>
                        <input name="name" type="text" class="easyui-validatebox span2" data-options="required:true"
                               value="${mbSupplierContract.name}"/>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbSupplierContract.ALIAS_SUPPLIER_NAME%>
                    </th>
                    <td>
                        <jb:selectGrid dataType="supplierId" name="supplierId" required="true"
                                       value="${mbSupplierContract.supplierId}"></jb:selectGrid>
                    </td>
                    <th><%=TmbSupplierContract.ALIAS_VALID%>
                    </th>
                    <td>
                        <select class="easyui-combobox easyui-validatebox" name="valid"
                                data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <c:if test="${mbSupplierContract.valid == true}">
                                <option value="true" selected="selected">是</option>
                                <option value="false">否</option>
                            </c:if>
                            <c:if test="${mbSupplierContract.valid == false}">
                                <option value="true">是</option>
                                <option value="false" selected="selected">否</option>
                            </c:if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbSupplierContract.ALIAS_RATE%>
                    </th>
                    <td>
                        <input name="rate" type="number" class="easyui-validatebox span2" data-options="required:true" value="${mbSupplierContract.rate}"/>%
                    </td>
                    <th><%=TmbSupplierContract.ALIAS_PAYMENT_DAYS%></th>
                    <td>
                        <input name="paymentDays" class="easyui-validatebox span2" type="number" value="${mbSupplierContract.paymentDays}">天
                    </td>
                </tr>
                <tr>
                    <th><%=TmbSupplierContract.ALIAS_EXPIRY_DATE_START%>
                    </th>
                    <td>
                        <input required="true" name="expiryDateStart" class="easyui-validatebox span2"
                               data-options="required:true" type="text"
                               onclick="WdatePicker({dateFmt:'<%=TmbSupplierContract.FORMAT_EXPIRY_DATE_START%>'})"
                               maxlength="0" value="${mbSupplierContract.expiryDateStart}"/>
                    </td>
                    <th><%=TmbSupplierContract.ALIAS_EXPIRY_DATE_END%>
                    </th>
                    <td>
                        <input name="expiryDateEnd" class="easyui-validatebox span2" data-options="required:true"
                               type="text"
                               onclick="WdatePicker({dateFmt:'<%=TmbSupplierContract.FORMAT_EXPIRY_DATE_END%>'})"
                               maxlength="0" value="${mbSupplierContract.expiryDateEnd}"/>
                    </td>

                </tr>
                <tr>
                    <th><%=TmbSupplierContract.ALIAS_ATTACHMENT%>
                    </th>
                    <td colspan="3">
                        <input type="file" id="attachmentFile" name="file">
                    </td>
                </tr>
                <tr>
                    <td colspan="4" height="155">
                        <img class="img-preview" src="${mbSupplierContract.attachment}" width="100%" height="100%"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>