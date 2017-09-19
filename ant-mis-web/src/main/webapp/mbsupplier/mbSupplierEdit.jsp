<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobian.model.TmbSupplier" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbSupplierController/edit',
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
            <input type="hidden" name="id" value="${mbSupplier.id}"/>
            <table class="table table-hover table-condensed">
                <tr>

                    <th><%=TmbSupplier.ALIAS_ADDTIME%>
                    </th>
                    <td>
                        ${mbSupplier.addtime}
                    </td>
                    <th><%=TmbSupplier.ALIAS_UPDATETIME%>
                    </th>
                    <td>
                       ${mbSupplier.updatetime}
                    </td>
                </tr>

                <tr>
                    <th><%=TmbSupplier.ALIAS_NAME%>
                    </th>
                    <td>
                        <input name="name" type="text" class="easyui-validatebox span2" data-options="required:true"
                               value="${mbSupplier.name}"/>
                    </td>
                    <th><%=TmbSupplier.ALIAS_REGION_ID%>
                    </th>
                    <td>
                        <jb:selectGrid dataType="region" name="regionId" value="${mbSupplier.regionId}"></jb:selectGrid>

                    </td>
                </tr>
                <tr>
                    <th><%=TmbSupplier.ALIAS_ADDRESS%>
                    </th>
                    <td colspan='4'>
                        <input class="span2" name="address" type="text" value="${mbSupplier.address}"
                               style="width:90%"/>
                    </td>

                </tr>
                <tr>
                    <th><%=TmbSupplier.ALIAS_CONTACT_PEOPLE%>
                    </th>
                    <td>
                        <input class="span2" name="contactPeople" type="text" value="${mbSupplier.contactPeople}"/>
                    </td>
                    <th><%=TmbSupplier.ALIAS_CONTACT_PHONE%>
                    </th>
                    <td>
                        <input class="span2" name="contactPhone" type="text" value="${mbSupplier.contactPhone}"/>
                    </td>

                </tr>
                <tr>
                    <th><%=TmbSupplier.ALIAS_WAREHOUSE_ID%>
                    </th>
                    <td colspan="3">
                        <jb:selectSql dataType="SQ005" name="warehouseId" required="true"
                                      value="${mbSupplier.warehouseId}"></jb:selectSql>

                    </td>
                    <%--<th><%=TmbSupplier.ALIAS_CERTIFICATE_LIST%></th>
                    <td>
                        <input class="span2" name="certificateList" type="text" value="${mbSupplier.certificateList}"/>
                    </td>--%>
                </tr>
            </table>
        </form>
    </div>
</div>
<script>

    /* var submit;
     $("#regionId").blur(
     function() {
     var val=$("#regionId").val();
     if(!IsNum(val)){
     $("#msg").text("请输入数字!");
     /!*alert("请输入数字!")*!/
     submit=false;
     }else {
     submit= true;
     }

     });
     function IsNum(num) {
     var reNum = /^\d*$/;
     return (reNum.test(num));
     }
     */
</script>