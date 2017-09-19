<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbShopCoupons" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbShopCouponsController/edit',
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
            <input type="hidden" name="id" value="${mbShopCoupons.id}"/>
            <table class="table table-hover table-condensed">
                <tr>
                    <th>券票状态</th>
                    <td>
                        <select name="status" required="true" style="width: 140px" >
                            <c:if test="${mbShopCoupons.status == 'NS001'}">
                                <option value="NS001" selected>有效</option>
                                <option value="NS005">无效</option>
                            </c:if>
                            <c:if test="${mbShopCoupons.status == 'NS005'}">
                                <option value="NS005" selected>无效</option>
                                <option value="NS001" >有效</option>
                            </c:if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbShopCoupons.ALIAS_REMARK%>
                    </th>
                    <td colspan="3">
                        <textarea class="span8" name="remark"  value="${mbShopCoupons.remark}"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>