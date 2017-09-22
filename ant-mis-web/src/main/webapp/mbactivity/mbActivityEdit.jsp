<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbActivity" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbActivityController/edit',
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
                    parent.$.modalDialog.openner_activityDataDrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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
            <input type="hidden" name="id" value="${mbActivity.id}"/>
            <table class="table table-hover table-condensed">
                <tr>
                    <th><%=TmbActivity.ALIAS_NAME%>
                    </th>
                    <td>
                        <input class="span2" name="name" type="text" value="${mbActivity.name}"/>
                    </td>
                    <th><%=TmbActivity.ALIAS_VALID%>
                    </th>
                    <td>
                        <select  class="span2"  name="valid">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbActivity.ALIAS_EXPIRY_DATE_START%>
                    </th>
                    <td>
                        <input class="span2" name="expiryDateStart" type="text"
                               onclick="WdatePicker({dateFmt:'<%=TmbActivity.FORMAT_EXPIRY_DATE_START%>'})"
                               maxlength="0" value="${mbActivity.expiryDateStart}"/>
                    </td>
                    <th><%=TmbActivity.ALIAS_EXPIRY_DATE_END%>
                    </th>
                    <td>
                        <input class="span2" name="expiryDateEnd" type="text"
                               onclick="WdatePicker({dateFmt:'<%=TmbActivity.FORMAT_EXPIRY_DATE_END%>'})" maxlength="0"
                               value="${mbActivity.expiryDateEnd}"/>
                    </td>

                </tr>
                <tr>
                    <th>活动类型
                    </th>
                    <td>
                        <jb:select name="type" dataType="AT" required="true" value="${mbActivity.type}"></jb:select>
                    </td>
                    <th>代码类型
                    </th>
                    <td>
                        <jb:select name="languageType" dataType="CS" required="true" value="${mbActivity.languageType}"></jb:select>
                    </td>
                </tr>
                <tr>
                    <th><%=TmbActivity.ALIAS_REMARK%>
                    </th>
                    <td colspan="3">
                        <textarea name="remark" style="width: 90%" cols="30" rows="5">${mbActivity.remark}</textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>