<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table class="table table-hover table-condensed">
            <tr>
                <th>添加时间</th>
                <td>
                    <fmt:formatDate value="${driverAccount.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <th>更新时间</th>
                <td>
                    <fmt:formatDate value="${driverAccount.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
            <tr>
                <th>用户名</th>
                <td>
                    ${driverAccount.userName}
                </td>
                <th>图标</th>
                <td rowspan="3">
                    <img src="${driverAccount.icon}" width="80px" height="80px"/>
                </td>
             </tr>
             <tr>
                <th>昵称</th>
                <td>
                    ${driverAccount.nickName}
                </td>
            </tr>
            <tr>
                <th>性别</th>
                <td>
                    <c:if test="${driverAccount.sex == 1}">
                        男
                    </c:if>
                    <c:if test="${driverAccount.sex == 0}">
                        女
                    </c:if>
                </td>
            </tr>
            <tr>
                <th>第三方账号ID</th>
                <td>
                    ${driverAccount.refId}
                </td>

                <th>第三方类型</th>
                <td>
                    ${driverAccount.refType}
                </td>
            </tr>
            <tr>
                <th>联系电话</th>
                <td>
                    ${driverAccount.conactPhone}
                </td>

                <th>骑手类型</th>
                <td>
                    ${driverAccount.typeName}
                </td>
            </tr>
            <tr>
                <th>审核状态</th>
                <td>
                    ${driverAccount.handleStatusName}
                </td>

                <th>审核人</th>
                <td>
                    ${driverAccount.handleLoginName}
                </td>
            </tr>
            <tr>
                <th>可接订单数量</th>
                <td>${driverAccount.orderQuantity}</td>
            </tr>
            <tr>
                <th>审核意见</th>
                <td colspan="3">
                    ${driverAccount.handleRemark}
                </td>
            </tr>
        </table>
    </div>
</div>