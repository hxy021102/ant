<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
        $('.money_input').each(function(){
            $(this).text($.formatMoney($(this).text().trim()));
        });
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>
					<th>添加时间</th>
					<td>
						<fmt:formatDate value="${shopDeliverApplyQuery.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<th>修改时间</th>
					<td>
						<fmt:formatDate value="${shopDeliverApplyQuery.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>		
				 <tr>
					<th>门店ID</th>
					<td>
						${shopDeliverApplyQuery.shopId}
					</td>
					<th>门店名称</th>
					<td>
						${shopDeliverApplyQuery.shopName}
					</td>
				</tr>
			    <tr>
				<th>门店账号</th>
				<td>
					${shopDeliverApplyQuery.accountId}
				</td>
				<th>审核状态</th>
				<td>
					${shopDeliverApplyQuery.statusName}
				</td>
			    </tr>
			    <tr>
					<th>最大配送距离</th>
					<td>
						<c:if test="${shopDeliverApplyQuery.maxDeliveryDistance != null}">
							${shopDeliverApplyQuery.maxDeliveryDistance}米
						</c:if>
					</td>
					<th>是否短信通知</th>
					<td>
						<c:if test="${shopDeliverApplyQuery.smsRemind == false}">
							否
						</c:if>
						<c:if test="${shopDeliverApplyQuery.smsRemind == true}">
							是
						</c:if>
					</td>
				</tr>
		     	<tr>
				<th>是否必须上传回单</th>
				  <td>
					<c:if test="${shopDeliverApplyQuery.uploadRequired == false}">
						否
					</c:if>
					<c:if test="${shopDeliverApplyQuery.uploadRequired == true}">
						 是
					</c:if>
				  </td>
				<th>是否营业</th>
					<td>
						<c:if test="${shopDeliverApplyQuery.online == false}">
							否
						</c:if>
						<c:if test="${shopDeliverApplyQuery.online == true}">
							是
						</c:if>
					</td>
			  </tr>
			  <tr>
				<th>派单类型</th>
				<td >
					${shopDeliverApplyQuery.deliveryTypeName}
				</td>
				<th>配送方式</th>
				 <td>
					${shopDeliverApplyQuery.deliveryWayName}
				 </td>
			  </tr>
			<tr>
				<th>冻结状态</th>
				<td>
					<c:if test="${shopDeliverApplyQuery.frozen}">冻结</c:if>
					<c:if test="${!shopDeliverApplyQuery.frozen}">正常</c:if>
				</td>
				<th>转入权限</th>
				<td>
					<c:if test="${shopDeliverApplyQuery.transferAuth == false}">无权限</c:if>
					<c:if test="${shopDeliverApplyQuery.transferAuth == true}">允许</c:if>
				</td>
			 </tr>
			 <tr>
				<th>运费</th>
				<td colspan="4">
					<font   class="money_input">${shopDeliverApplyQuery.freight}</font>
				</td>
			  </tr>
			  <tr>
					<th>结果</th>
					<td colspan="3">
						${shopDeliverApplyQuery.result}
					</td>							
			  </tr>
		</table>
	</div>
</div>