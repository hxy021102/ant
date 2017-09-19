<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/mbItemController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				setHideUrl('introduceList',ITEM_INTRODUCE.rows);
				setHideUrl('imageList',ITEM_CAROUSEL.rows);
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
		function ProcessFile() {
			var file = document.getElementById('iconFile').files[0];
			if (file) {
				var reader = new FileReader();
				reader.onload = function ( event ) {
					var txt = event.target.result;
					$('.img-preview').attr('src',txt);
				};
			}
			reader.readAsDataURL(file);
		}
		$(document).delegate('#iconFile','change',function () {
			ProcessFile();
		});
		$('.img-preview').each(function(){
			var $this = $(this);
			$this.css('height',$this.parent().attr('height'));
		});
		$('.money_input').blur(function () {
			var source = $(this);
			var target = source.next();
			if (!/^([1-9]\d*|0)(\.\d{2})?$/.test(source.val())) {
				source.val("").focus();
			}
			var val = source.val().trim();
			if (val.indexOf('.') > -1) {
				val = val.replace('.', "");
			} else if (val != '') {
				val += "00";
			}
			target.val(val);
		});
		$('.money_input').each(function(){
			$(this).val($.formatMoney($(this).val().trim()));
		});
        $('.weight_input').blur(function () {
            var source = $(this);
            var target = source.next();
            if (!/^([1-9]\d*|0)(\.\d{1,3})?$/.test(source.val())) {
                source.val("").focus();
            }
            var val = source.val().trim();
            if (val.indexOf('.') > -1) {
                if(val.substring(val.length-2,val.length).indexOf('.')>-1){
                    val = val.replace('.', "");
                    val +="00";
				}else if(val.substring(val.length-3,val.length).indexOf('.')>-1){
                    val = val.replace('.', "");
                    val +="0";
				}else{
                    val = val.replace('.', "");
				}
            } else if (val != '') {
                val += "000";
            }
            target.val(val);
        });
        $('.weight_input').each(function(){
            $(this).val($.formatWeight($(this).val().trim()));
        });
		function setHideUrl(domId,rows){
			var imageUrl="";
			for(var i = 0;i<rows.length;i++){
				imageUrl+=";"+rows[i].image;
			}
			if(imageUrl.length>0) {
				$('#form').find('#' + domId).val(imageUrl.substr(1));
			}
		}

	});


	var mbItem_loadData = function(dataList){
		dataList = dataList.split(';');
		var rows = new Array();
		for(var i = 0;i<dataList.length;i++){
			if(dataList[i].trim() !="")
			rows.push({"image":dataList[i]});
		}
		return rows;
	}
	var ITEM_CAROUSEL = {rows:mbItem_loadData('${mbItem.imageList}')};
	var ITEM_INTRODUCE = {rows:mbItem_loadData('${mbItem.introduceList}')};

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<div id="item_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">

			<div title="基本信息">
				<form id="form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" value = "${mbItem.id}"/>
					<input type="hidden" name="imageList" id="imageList"/>
					<input type="hidden" name="introduceList" id="introduceList"/>
					<table class="table table-hover table-condensed">
						<tr>
							<th><%=TmbItem.ALIAS_CODE%></th>
							<td>
								<input class="span2" name="code" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbItem.code}"/>
							</td>
							<th><%=TmbItem.ALIAS_NAME%></th>
							<td>
								<input class="span2" name="name" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbItem.name}"/>
							</td>
						</tr>
						<tr>
							<th><%=TmbItem.ALIAS_ISSHELVES%></th>
							<td>
								<select class="easyui-combobox easyui-validatebox" name="isShelves" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<c:if test="${mbItem.isShelves == true}">
										<option value="true" selected="selected">是</option>
										<option value="false">否</option>
									</c:if>
									<c:if test="${mbItem.isShelves == false}">
										<option value="true">是</option>
										<option value="false" selected="selected">否</option>
									</c:if>
								</select>
							</td>
							<th><%=TmbItem.ALIAS_PURCHASE_PRICE%></th>
							<td>
								<input class="span2 money_input easyui-validatebox" name="purchasePriceStr" type="text" data-options="required:true" value="${mbItem.purchasePrice}"/>
								<input type="hidden" name="purchasePrice" value="${mbItem.purchasePrice}">
							</td>
						</tr>
						<tr>
							<th><%=TmbItem.ALIAS_CATEGORY_ID%></th>
							<td>
								<jb:selectSql dataType="SQ001" name="categoryId" required="true" value="${mbItem.categoryId}"></jb:selectSql>
							</td>
							<th><%=TmbItem.ALIAS_QUANTITY_UNIT%></th>
							<td>
								<input  name="quantityUnitName" type="text" class="easyui-validatebox span2" data-options="required:true" value="${mbItem.quantityUnitName}"/>
							</td>
						</tr>
						<tr>
							<th><%=TmbItem.ALIAS_MARKET_PRICE%></th>
							<td>
								<input class="span2 money_input easyui-validatebox" name="marketPriceStr" type="text" value="${mbItem.marketPrice}" data-options="required:true"/>
								<input type="hidden" name="marketPrice" value="${mbItem.marketPrice}">
							</td>
							<th><%=TmbItem.ALIAS_WEIGHT%></th>
							<td>
								<input class="span2 weight_input easyui-validatebox" name="weightStr" type="text" value="${mbItem.weight}" data-options="required:true"/>
								<input type="hidden" name="weight" value="${mbItem.weight}">
							</td>
						</tr>
						<tr>
							<th><%=TmbItem.ALIAS_QUANTITY%></th>
							<td>
								<input class="span2 easyui-validatebox" name="quantity" type="number" value="${mbItem.quantity}" data-options="required:true"/>
							</td>
							<th>排序</th>
							<td>
								<input class="span2 easyui-validatebox" name="seq" type="number" value="${mbItem.seq}" data-options="required:true"/>
							</td>
						</tr>
						<tr>
							<th><%=TmbItem.ALIAS_ISPACK%></th>
							<td>
								<select class="easyui-combobox easyui-validatebox" name="ispack" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<c:if test="${mbItem.ispack == true}">
										<option value="true" selected="selected">是</option>
										<option value="false">否</option>
									</c:if>
									<c:if test="${mbItem.ispack == false}">
										<option value="true">是</option>
										<option value="false" selected="selected">否</option>
									</c:if>
								</select>
							</td>
							<th><%=TmbItem.ALIAS_PACK_ID%></th>
							<td>
								<jb:selectSql dataType="SQ006" name="packId" value="${mbItem.packId}"></jb:selectSql>
							</td>
						</tr>
						<tr>
							<td colspan="4" height="50">
								<img class="img-preview" src="${mbItem.url}"/>
							</td>
						</tr>
						<tr>
							<th>图标</th>
							<td colspan="3">
								<input type="file" id="iconFile" name="equipIconFile">
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div title="轮播图" data-options="href:'${pageContext.request.contextPath}/mbitem/mbItemCarousel.jsp',split:true">

			</div>
			<div title="商品参数" data-options="href:'${pageContext.request.contextPath}/mbitem/mbItemIntroduce.jsp',split:true">

			</div>



		</div>

	</div>
</div>