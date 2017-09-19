<%@ page import="com.mobian.model.TmbUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag" %>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        $('#form').form({
            url: '${pageContext.request.contextPath}/mbShopController/editShopInChargeUser',
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

    function unboundUser(userName) {
        $.messager.alert('Warning', "点击【编辑】按钮后将会解除本门店与账号[" + userName + "]的绑定");
        $('#userId').text(null);
    }

    function boundUser() {
        var f = parent.$.modalDialog.handler.find('#form');
        var userNewId = f.find("input[name=userNewId]").val();
        f.find("input[name=userId]").val(userNewId);
        $.messager.alert('Warning', '点击【编辑】按钮后,本门店将会与输入的账号进行绑定');
    }
    function getUserInfo(newValueId) {
        console.log("newValueId:" + newValueId);
        $.ajax({
            url: '${pageContext.request.contextPath}/mbUserController/query?id=' + newValueId,
            data: newValueId,
            dataType: "json",
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            beforeSend: function (request) {
                parent.$.messager.progress({
                    title: '提示',
                    text: '数据处理中，请稍后....'
                });
            },

            success: function (data) {
                parent.$.messager.progress('close');
                if (data.success) {
                    console.log(data);
                    fillTable(data);
                } else {
                    parent.$.messager.alert('错误', data.msg);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                parent.$.messager.progress('close');
            }
        });
    }

    function fillTable(data) {
        if (data == undefined) {
            return;
        }
        var mbNewUser = data.obj;
        $('#tableUserName').text(mbNewUser.userName);
        $('#tableNickName').text(mbNewUser.nickName);
        $('#tableIcon').attr('src', mbNewUser.icon);
        $('#tablePhone').text(mbNewUser.phone);
        var gender = '';
        if (mbNewUser.sex == 1) {
            gender = '男';
        }
        if (mbNewUser.sex == 2) {
            gender = '女';
        }
        $('#tableSex').text(gender);
    }
</script>
<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
    <form id="form" method="post">
        <input type="hidden" name="id" value="${shopId}"/>
        <input type="hidden" name="userId" id="userId"/>
        <table class="table table-hover table-condensed">
            <tr>
                <th class="span2">负责人账号：</th>
                <td>
                    ${mbUser.userName}
                    <c:if test="${mbUser.id == null }">
                        <jb:selectGrid name="userNewId" dataType="mbUserId" onselect="getUserInfo"></jb:selectGrid>
                        <a href="javascript:void(0);" class="easyui-linkbutton" onclick="boundUser();">绑定账户</a>
                    </c:if>
                    <c:if test="${mbUser.id != null}">
                        <input type="hidden" name="oldUserId" value="${mbUser.id}"/>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           onclick="unboundUser('${mbUser.userName}');">解除绑定</a>
                    </c:if>
                </td>
            </tr>

        </table>
    </form>
</div>
<div class="easyui-layout" data-options="fit : true,border:false">
    <div data-options="region:'north',title:'基本信息',border:false" style="height: 150px; overflow: hidden;">
        <table class="table" id="table">
            <tr>
                <th><%=TmbUser.ALIAS_USER_NAME%>：</th>
                <td id="tableUserName">
                    ${mbUser.userName}
                </td>
                <th><%=TmbUser.ALIAS_NICK_NAME%>：</th>
                <td id="tableNickName">
                    ${mbUser.nickName}
                </td>
                <th><%=TmbUser.ALIAS_ICON%>：</th>
                <td id="tableIcon" rowspan="3">
                    <img src="${mbUser.icon}" width="80" height="80"/>
                </td>
            </tr>
            <tr>
                <th><%=TmbUser.ALIAS_SEX%>：</th>
                <td id="tableSex">
                    <c:if test="${mbUser.sex == 1}">
                        男
                    </c:if>
                    <c:if test="${mbUser.sex == 2}">
                        女
                    </c:if>
                </td>
                <th><%=TmbUser.ALIAS_PHONE%>：</th>
                <td id="tablePhone">
                    ${mbUser.phone}
                </td>
            </tr>
        </table>
    </div>
</div>