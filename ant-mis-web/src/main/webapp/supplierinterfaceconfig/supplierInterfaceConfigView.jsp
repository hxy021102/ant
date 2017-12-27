<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
					 <fmt:formatDate value="${supplierInterfaceConfig.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			     </td>
				<th>修改时间</th>
				<td>
					<fmt:formatDate value="${supplierInterfaceConfig.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<th>接口类型</th>
				<td>
					${supplierInterfaceConfig.interfaceType}
				</td>
				<th>客户ID</th>
				<td>
					${supplierInterfaceConfig.customerId}
				</td>
			</tr>
			<tr>
				<th>appkey</th>
				<td>
					${supplierInterfaceConfig.appKey}
				</td>
				<th>appSecret</th>
				<td>
					${supplierInterfaceConfig.appSecret}
				</td>
			</tr>
			<tr>
				<th>serviceUrl</th>
				<td>
					${supplierInterfaceConfig.serviceUrl}
				</td>
				<th>版本</th>
				<td>
					${supplierInterfaceConfig.version}
				</td>
			</tr>
			<tr>
				<th>仓库代码</th>
				<td>
					${supplierInterfaceConfig.warehouseCode}
				</td>
				<th>物流公司代码</th>
				<td>
					${supplierInterfaceConfig.logisticsCode}
				</td>
			</tr>
			<tr>
				<th>状态映射</th>
				<td>
					${supplierInterfaceConfig.statusMap}
				</td>
				<th>是否上线</th>
				<td>
					<c:if test="${supplierInterfaceConfig.online == false}">否</c:if>
					<c:if test="${supplierInterfaceConfig.online == true}">是</c:if>
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3">
					${supplierInterfaceConfig.remark}
				</td>
			</tr>
		</table>
	</div>
</div>