<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
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
					<th>门店账号ID</th>
					<td>
						${shopDeliverApplyQuery.accountId}
					</td>
					<th>审核状态</th>
					<td>
						${shopDeliverApplyQuery.statusName}
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