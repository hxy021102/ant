<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    ${driverAccount.addtime}
                </td>
                <th>更新时间</th>
                <td>
                    ${driverAccount.updatetime}
                </td>
            </tr>
            <tr>
                <th>用户名</th>
                <td>
                    ${driverAccount.userName}
                </td>

                <th>昵称</th>
                <td>
                    ${driverAccount.nickName}
                </td>
            </tr>
            <tr>
                <th>图标</th>
                <td>
                    ${driverAccount.icon}
                </td>

                <th>性别</th>
                <td>
                    ${driverAccount.sexName}
                </td>
            </tr>
            <tr>
                <th>refId</th>
                <td>
                    ${driverAccount.refId}
                </td>

                <th>refType</th>
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
                <th>处理意见</th>
                <td>
                    ${driverAccount.handleRemark}
                </td>
            </tr>
        </table>
    </div>
</div>