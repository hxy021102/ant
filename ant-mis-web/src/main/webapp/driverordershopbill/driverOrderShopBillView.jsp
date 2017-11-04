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
                    ${driverOrderShopBill.addtime}
                </td>

                <th>更新时间</th>
                <td>
                    ${driverOrderShopBill.updatetime}
                </td>
            </tr>
            <tr>
                <th>骑手账户</th>
                <td>
                    ${driverOrderShopBill.driverAccountId}
                </td>
                <th>门店ID</th>
                <td>
                    ${driverOrderShopBill.shopId}
                </td>
            </tr>
            <tr>
                <th>审核状态</th>
                <td>
                    ${driverOrderShopBill.handleStatusName}
                </td>
                <th>审核人</th>
                <td>
                    ${driverOrderShopBill.handleLoginName}
                </td>
            </tr>
            <tr>
                <th>账单金额</th>
                <td>
                    ${driverOrderShopBill.amount}
                </td>
                <th>支付方式</th>
                <td>
                    ${driverOrderShopBill.payWayName}
                </td>
            </tr>
            <tr>
                <th>开始时间</th>
                <td>
                    ${driverOrderShopBill.startDate}
                </td>
                <th>结束时间</th>
                <td>
                    ${driverOrderShopBill.endDate}
                </td>
            </tr>
            <tr>
                <th>审核备注</th>
                <td>
                    ${driverOrderShopBill.handleRemark}
                </td>
            </tr>
        </table>
    </div>
</div>