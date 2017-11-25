<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbSupplierOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
    $(function() {
        parent.$.messager.progress('close');
        $('#form').form({
            url : '${pageContext.request.contextPath}/shopDeliverApplyController/edit',
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
            <input type="hidden" name="id" value = "${shopDeliverApply.id}"/>
            <table class="table table-hover table-condensed">
                <tr>
                    <th style="width: 100px;">最大配送距离</th>
                    <td>
                        <input name="maxDeliveryDistance" type="text" class="span2" value="${shopDeliverApply.maxDeliveryDistance}"><font color="red">(单位：米)默认5000米,-1不限制</font>
                    </td>
                </tr>
                <tr>
                    <th>是否短信通知</th>
                    <td>
                     <select class="easyui-combobox" name="smsRemind" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                         <c:if test="${shopDeliverApply.smsRemind == false}">
                             <option value="1">是</option>
                             <option value="0" selected="selected">否</option>
                         </c:if>
                         <c:if test="${shopDeliverApply.smsRemind == true}">
                             <option value="1" selected="selected">是</option>
                             <option value="0">否</option>
                         </c:if>
                     </select>
                    </td>
                </tr>
                <tr>
                    <th>是否必须上传回单</th>
                    <td>
                        <select class="easyui-combobox" name="uploadRequired" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <c:if test="${shopDeliverApply.uploadRequired == false}">
                                <option value="1">是</option>
                                <option value="0" selected="selected">否</option>
                            </c:if>
                            <c:if test="${shopDeliverApply.uploadRequired == true}">
                                <option value="1" selected="selected">是</option>
                                <option value="0">否</option>
                            </c:if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>派单类型</th>
                    <td>
                    <jb:select name="deliveryType" dataType="DAT" required="true" value="${shopDeliverApply.deliveryType}"></jb:select>
                    </td>
                </tr>
                    <th>配送方式</th>
					<td>
                        <jb:select dataType="DAW" name="deliveryWay" required="true" value="${shopDeliverApply.deliveryWay}"></jb:select>
					</td>
                <tr>
                    <th>冻结状态</th>
                    <td>
                        <select class="easyui-combobox" name="frozen" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="1" <c:if test="${shopDeliverApply.frozen}">selected="selected"</c:if>>冻结</option>
                            <option value="0" <c:if test="${!shopDeliverApply.frozen}">selected="selected"</c:if>>正常</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>