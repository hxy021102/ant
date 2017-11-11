<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
						${driverFreightRule.addtime}							
					</td>							
					<th>更新时间</th>
					<td>
						${driverFreightRule.updatetime}
					</td>
				</tr>
				<tr>
					<th>重量下限(包含)</th>
					<td>
						${driverFreightRule.weightLower}	克
					</td>							
					<th>重量上限</th>
					<td>
						${driverFreightRule.weightUpper}	克
					</td>							
				</tr>		
				<tr>	
					<th>距离下限(包含)</th>
					<td>
						${driverFreightRule.distanceLower}米
					</td>							
					<th>距离上限</th>
					<td>
						${driverFreightRule.distanceUpper}米
					</td>							
				</tr>		
				<tr>	
					<th>区域</th>
					<td>
						${driverFreightRule.regionPath}
					</td>							
					<th>费用</th>
					<td>
						${driverFreightRule.freight / 100.00}元
					</td>							
				</tr>		
		</table>
	</div>
</div>