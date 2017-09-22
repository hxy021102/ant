<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TmbOrder" %>
<%@ page import="com.mobian.model.TmbShop" %>
<%@ page import="com.mobian.model.TmbOrderItem " %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<title>MbUser管理</title>
	<jsp:include page="../inc.jsp"></jsp:include>
	<script type="text/javascript">
		var dataGrid;
        var dgOrder;
		$(function () {
			dataGrid = $('#dataGrid').datagrid({
				url: '',
				fit: true,
				fitColumns: true,
				border: false,
				pagination: false,
				idField: 'id',
				pageSize: 10,
				pageList: [10, 20, 30, 40, 50],
				sortName: 'id',
				sortOrder: 'desc',
				checkOnSelect: false,
				selectOnCheck: false,
				nowrap: false,
				striped: true,
				rownumbers: true,
				singleSelect: true,
				columns: [[{
					field: 'id',
					title: 'ID',
					width: 20
				}, {
					field: 'name',
					title: '<%=TmbShop.ALIAS_NAME%>',
					width: 50,
					formatter: function (value, row, index) {
						var str = value;
						if(row.parentId == -1) str = '<font color="red">(主)</font>' + value;
						return str;
					}
				}, {
					field: 'parentName',
					title: '主店名称',
					width: 50
				}, {
					field: 'regionPath',
					title: '<%=TmbShop.ALIAS_REGION_ID%>',
					width: 70
				}, {
					field: 'address',
					title: '<%=TmbShop.ALIAS_ADDRESS%>',
					width: 150
				}, {
					field: 'contactPhone',
					title: '<%=TmbShop.ALIAS_CONTACT_PHONE%>',
					width: 50,
					formatter: function (value, row) {
						//return value + "," + row.latitude;
						return '<a onclick="addContactPhone(' + row.id + ')">' + value + '</a>';
					}
				}, {
					field: 'contactPeople',
					title: '<%=TmbShop.ALIAS_CONTACT_PEOPLE%>',
					width: 40
				}, {
					field: 'shopTypeName',
					title: '<%=TmbShop.ALIAS_SHOP_TYPE%>',
					width: 30
				}, {
					field: 'longitude',
					title: '坐标',
					width: 60,
					formatter: function (value, row, index) {
						return value + "," + row.latitude;
					}
				}, {
					field: 'auditStatusName',
					title: '审核状态',
					width: 30
				}]],
				toolbar: '#toolbar',
				onLoadSuccess: function () {
					//$('#searchForm table').show();
					parent.$.messager.progress('close');

					//$(this).datagrid('tooltip');
				},
				onSelect:function(index,row){
					$('.item_add').linkbutton("enable");
					dataGrid.selectRow = row;
					$.post('${pageContext.request.contextPath}/mbShopController/getPropertyGrid', {
						shopId : row.id
					}, function(data) {
						$('#orderForm')[0].reset();
						$('#deliveryWay').combobox('setValue', 'DW02');
						$('#balanceText').text($.formatMoney(data.balance));
						dataGrid.balaceAmount = data.balance;
                        $('#arrears').text($.formatMoney(data.debtMoney ));


					}, 'JSON');

					$.post('${pageContext.request.contextPath}/mbContractController/getMbContractItemMap', {
						shopId : row.id
					}, function(data) {
						dataGrid.contractPriceMap = data.obj;
						parent.$.messager.progress('close');
					}, 'JSON');

                    $.post('${pageContext.request.contextPath}/mbShopCouponsController/shopCouponsQuantityQuery', {
                        shopId: row.id
                    }, function(result) {
                        if (result.success) {
                            dataGrid.shopCouponsMap = result.obj;
                            parent.$.messager.progress('close');
                        }
                    }, 'JSON');

					reject();
                    listOrderItem(row.id);
				}
			});
			/*setTimeout(function () {
				searchFun();
			}, 100);*/
			$('#orderForm').show();
			parent.$.messager.progress('close');

            dgOrder = $('#dataOrder').datagrid({
                url: '',
                fit: true,
                fitColumns: true,
                border: false,
                pagination: false,
                idField: 'id',
                sortOrder: 'desc',
                checkOnSelect: false,
                selectOnCheck: false,
                nowrap: false,
                striped: true,
                singleSelect: true,
                columns: [[{
                    field: 'addtime',
                    title: '<%=TmbOrderItem.ALIAS_ADDTIME%>',
                    width: 130,
                }, {
                    field: 'id',
                    title: '<%=TmbOrderItem.ALIAS_ORDER_ID%>',
                    width: 50,
                    formatter : function (value, row, index) {
                        return '<a onclick="viewOrder(' + row.id + ')">' + row.id + '</a>';
                    }
                }]],
                onLoadSuccess: function () {
                    parent.$.messager.progress('close');
                }
            });
              $('#dataTotal').datagrid({
                  pagination:false,
                  fit: true,
                  columns: [[{
                    field: 'totalQuantity',
                    title: '商品总数',
                    width: 100,
                }, {
                    field: 'totalPrice',
                    title: '商品总金额',
					align:"right",
                    width: 100,
                }]],
            });
		});

		function clearOrderForm(){
			reject();
			$('#orderForm')[0].reset();
			$('#deliveryWay').combobox('setValue', 'DW02');
			$('#balanceText').text($.formatMoney(0));
            $('#arrears').text($.formatMoney(0));
			dataGrid.balaceAmount = null;
			dataGrid.selectRow = null;
			$('.item_add').linkbutton("disable");
		}

		function addContactPhone(shopId){
			var href = '${pageContext.request.contextPath}/mbShopContactController/manager?shopId=' + shopId;
			parent.$("#index_tabs").tabs('add', {
				title : '门店联系人-' + shopId,
				content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>',
				closable : true
			});
		}

		function searchFun() {
			clearOrderForm();
			var options = {};
			options.url = '${pageContext.request.contextPath}/mbShopController/dataGridKeyword';
			options.queryParams = $.serializeObject($('#searchForm'));
			dataGrid.datagrid(options);
		}
		function cleanFun() {
			clearOrderForm();
			$('#searchForm input').val('');
			dataGrid.datagrid('load', {});
		}
        function listOrderItem(shopId) {
            var options =dgOrder.datagrid('options');
            options.url = '${pageContext.request.contextPath}/mbOrderController/listOrderItem';
            $('#shopId').val(shopId);
            options.queryParams = $.serializeObject($('#order'));
            dgOrder.datagrid(options);
        }
        function viewOrder(id) {
            var href = '${pageContext.request.contextPath}/mbOrderController/view?id=' + id;
            parent.$("#index_tabs").tabs('add', {
                title : '订单详情-' + id,
                content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>',
                closable : true
            });
        }
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false" style="height: 200px; overflow: hidden;">
		<div class="easyui-layout" data-options="fit : true,border : false">
			<div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;">
				<form id="searchForm">
					<input type="hidden" name="auditStatus" value="AS02"/>
					<table class="table table-hover table-condensed">
						<tr>
							<th><%=TmbShop.ALIAS_CONTACT_PHONE%>
							</th>
							<td>
								<input type="text" name="contactPhone" maxlength="128" class="span2"/>
							</td>
							<th><%=TmbShop.ALIAS_NAME%>
							</th>
							<td>
								<input type="text" name="name" maxlength="128" class="span2"/>
							</td>
							<th>主店名称：</th>
							<td>
								<jb:selectGrid dataType="shopId" name="parentId" params="{onlyMain:true}"></jb:selectGrid>
							</td>
							<th><%=TmbShop.ALIAS_REGION_ID%>
							</th>
							<td>
								<jb:selectGrid dataType="region" name="regionId" value="${mbShop.regionId}"></jb:selectGrid>
							</td>
							<th><%=TmbShop.ALIAS_ADDRESS%>
							</th>
							<td>
								<input type="text" name="address" maxlength="128" class="span2"/>
							</td>
						</tr>
					</table>
				</form>
				<form id="order">
					<input type="hidden" name="shopId" id="shopId" />
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<table id="dataGrid"></table>
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit : true,border : false">
			<div data-options="region:'west',border:false" style="width: 250px; overflow: hidden;">
				<form method="post" id="orderForm" style="display: none;">
					<table class="table table-hover table-condensed">
						<tr>
							<th>余额</th>
							<td id="balanceText">0.00
							</td>
							<th>欠款</th>
							<td id="arrears">0.00
							</td>

						</tr>

						<tr>
							<th><%=TmbOrder.ALIAS_DELIVERY_WAY%></th>
							<td colspan="3">
								<jb:select dataType="DW" name="deliveryWay"  value="DW02"></jb:select>
							</td>
						</tr>
						<tr>
							<th><%=TmbOrder.ALIAS_DELIVERY_REQUIRE_TIME%></th>
							<td colspan="3">
								<input class="span2" name="deliveryRequireTime" type="text" onclick="WdatePicker({dateFmt:'<%=TmbOrder.FORMAT_DELIVERY_REQUIRE_TIME%>'})"  maxlength="0" class="" />
							</td>
						</tr>
						<tr>
							<th><%=TmbOrder.ALIAS_DELIVERY_ADDRESS%></th>
							<td colspan="3">
								<textarea class="span2" name="deliveryAddress"></textarea>
							</td>
						</tr>
						<tr>
							<th><%=TmbOrder.ALIAS_USER_REMARK%></th>
							<td colspan="3">
								<textarea class="span2" name="userRemark"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
		    <div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit : true,border : false">
		   <div data-options="region:'center',border:false">
		    <table id="dg" class="easyui-datagrid"  style="width:700px;height:auto"></table>
		   </div>

			<div id="tb" style="height:auto;display: none;">
				<a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_add',disabled:true,plain:true" onclick="append()">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'pencil_delete',disabled:true,plain:true" onclick="removeit()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton item_add" data-options="iconCls:'disk',disabled:true,plain:true" onclick="accept()">保存</a>
				&nbsp;&nbsp;&nbsp;<input type="checkbox" name="checkPayByVoucher" id="checkPayByVoucher" checked/><a class="easyui-linkbutton item_add" data-options="disabled:true,plain:true" >使用水票</a>

			</div>
			<div data-options="region:'south',border:false" style="height: 50px;">
				<table id="dataTotal" class="easyui-datagrid"></table>
			</div>
		<script type="text/javascript">
			var editIndex = undefined;
			var dgItem;
			function endEditing(){
				if (editIndex == undefined){return true}
				if ($('#dg').datagrid('validateRow', editIndex)){
					$('#dg').datagrid('endEdit', editIndex);
					editIndex = undefined;
					return true;
				} else {
					return false;
				}
			}

			$(function () {

				$.extend($.fn.datagrid.defaults.editors, {
					combogrid: {
						init: function(container, options){
							var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
							options.queryParams = {shopId:dataGrid.selectRow.id};
							input.combogrid(options);
							return input;
						},
						destroy: function(target){
							$(target).combogrid('destroy');
						},
						getValue: function(target){
							return $(target).combogrid('getValue');
						},
						setValue: function(target, value){
							$(target).combogrid('setValue', value);
						},
						resize: function(target, width){
							$(target).combogrid('resize',width);
						}
					}
				});


				function getCacheContainer(t){
					var view = $(t).closest('div.datagrid-view');
					var c = view.children('div.datagrid-editor-cache');
					if (!c.length){
						c = $('<div class="datagrid-editor-cache" style="position:absolute;display:none"></div>').appendTo(view);
					}
					return c;
				}
				function getCacheEditor(t, field){
					var c = getCacheContainer(t);
					return c.children('div.datagrid-editor-cache-' + field);
				}
				function setCacheEditor(t, field, editor){
					var c = getCacheContainer(t);
					c.children('div.datagrid-editor-cache-' + field).remove();
					var e = $('<div class="datagrid-editor-cache-' + field + '"></div>').appendTo(c);
					e.append(editor);
				}

				var editors = $.fn.datagrid.defaults.editors;
				for(var editor in editors){
					var opts = editors[editor];
					(function(){
						var init = opts.init;
						opts.init = function(container, options){
							var field = $(container).closest('td[field]').attr('field');
							var ed = getCacheEditor(container, field);
							if (ed.length){
								ed.appendTo(container);
								return ed.find('.datagrid-editable-input');
							} else {
								return init(container, options);
							}
						}
					})();
					(function(){
						var destroy = opts.destroy;
						opts.destroy = function(target){
							if ($(target).hasClass('datagrid-editable-input')){
								var field = $(target).closest('td[field]').attr('field');
								setCacheEditor(target, field, $(target).parent().children());
							} else if (destroy){
								destroy(target);
							}
						}
					})();
				}


				dgItem = $('#dg').datagrid({
					fit: true,
					iconCls: 'icon-edit',
					singleSelect: true,
					toolbar: '#tb',
					method: 'get',
					onClickCell: onClickCell,
					onEndEdit: onEndEdit,
					columns: [[{
						field: 'itemName',
						title: '商品名称',
						width: 250,
						editor: {
							type: 'combogrid',
							options: {
								panelWidth: 450,
								idField: 'id',
								textField: 'text',
								method: 'post',
								mode: 'remote',
								url: '${pageContext.request.contextPath}/mbItemController/selectQuery',
								required: true,
								columns: [[
									{field: 'id', title: 'ID', width: 30},
									{field:'code',title:'编码',width:180},
									{field: 'text', title: '名称', width: 180},
									{field: 'parentName', title: '分类', width: 50}
								]]
							}
						},
						formatter:function(value,row){
							return row.itemName;
						}
					}, {
						field: 'quantity',
						title: '数量',
						width: 50,
						editor:{type:'numberbox',options:{required: true}}
					}, {
						field: 'marketPrice',
						title: '市场价',
						width: 50,
						align:'right',
						formatter:function(value){
							if(value == undefined)return "";
							return $.formatMoney(value);
						}
					}, {
						field: 'buyPrice',
						title: '购买价',
						width: 50,
						align:'right',
						formatter:function(value){
							if(value == undefined)return "";
							return $.formatMoney(value);
						}
					}, {
                        field: 'isContract',
                        title: '签合同',
                        width: 50,
                        formatter: function (value) {
                            if (value == undefined)return "";
                            return value == true ? "有" : "无";
                        }
                    }, {
						field: 'voucherQuantityTotal',
						title: '券数',
						width: 50
					}]],
					onLoadSuccess: function () {
						parent.$.messager.progress('close');
					}
				});
			});
			/*计算购买的商品总数量和总金额*/
            function computerQuantity() {
                var sumQuantity = 0, sumPrice = 0;
                var dgArr = $("#dg").datagrid("getRows");
                for (var i = 0; i < dgArr.length; i++) {
                    sumQuantity += parseInt(dgArr[i].quantity);
                    sumPrice += parseInt(dgArr[i].quantity) * dgArr[i].buyPrice;
                }
				sumPrice = $.formatMoney(sumPrice);
                var arr = $("#dataTotal").datagrid("getRows");
                if (arr.length == 0) {
                    $('#dataTotal').datagrid('appendRow', {totalQuantity: sumQuantity, totalPrice:sumPrice});
                } else {
                    $('#dataTotal').datagrid('updateRow', {index: 0, row: {totalQuantity: sumQuantity, totalPrice: sumPrice}});
                }
            }
			function onClickCell(index, field){
                $('#dg').datagrid('endEdit', index);
				if (editIndex != index){
					if (endEditing()){
						$('#dg').datagrid('selectRow', index)
								.datagrid('beginEdit', index);
						var ed = $('#dg').datagrid('getEditor', {index:index,field:field});
						if (ed){
							($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
						}
						editIndex = index;
					} else {
						setTimeout(function(){
							$('#dg').datagrid('selectRow', editIndex);
						},0);
					}
				}
			}

			function onEndEdit(index, row) {
				var ed = $(this).datagrid('getEditor', {
					index: index,
					field: 'itemName'
				});
				var map = dataGrid.contractPriceMap;
				row.itemName = $(ed.target).combogrid('getText');
				var g = $(ed.target).combogrid('grid');
				if(g.datagrid('getSelected')!=null) {
					 row.marketPrice = g.datagrid('getSelected').marketPrice;
                	row.itemId=g.datagrid('getSelected').id;
					row.marketPrice = row.marketPrice;
                }


                if (map && map[row.itemId]) {
                    var cItem = map[row.itemId];
                    row.buyPrice = cItem.price;
                    row.isContract = true;
                } else {
                    row.buyPrice = row.marketPrice;
                    row.isContract = false;
                }
                var shopCouponsMap = dataGrid.shopCouponsMap;
                if (shopCouponsMap && shopCouponsMap[row.itemId]) {
                    var shopCoupons = shopCouponsMap[row.itemId];
                    row.voucherQuantityTotal = shopCoupons.quantityTotal - shopCoupons.quantityUsed;
                } else {
                    row.voucherQuantityTotal = 0;
                }
                computerQuantity();
				console.log(row);
			}
			function append(){
				if (endEditing()){
					$('#dg').datagrid('appendRow',{});
					editIndex = $('#dg').datagrid('getRows').length-1;
					$('#dg').datagrid('selectRow', editIndex)
							.datagrid('beginEdit', editIndex);
				}
			}
			function removeit(){
				if (editIndex == undefined){return}
				$('#dg').datagrid('cancelEdit', editIndex)
						.datagrid('deleteRow', editIndex);
				editIndex = undefined;
			}
			function accept(){

				if (endEditing()) {
					var rows = getChanges();
					var data = $.serializeObject($('#orderForm'));
					data.mbOrderItemList = rows;
					data.shopId = dataGrid.selectRow.id;
					var total = 0 ;
					for(var i =0;i<rows.length;i++){
						total += rows[i].buyPrice*rows[i].quantity;
					}

                    var totalN = countPayByVoucherTotal(rows,total);
					console.log(totalN+":"+total);
					var messageN = "";
					if (totalN != total) {
					    messageN = ",已使用水票支付,余下的需用余额支付" + $.formatMoney(totalN) ;
					}

                    var message = "";
					if (dataGrid.balaceAmount >= totalN) {
						message = "该订单总金额：" + $.formatMoney(total) + messageN + ",系统自动扣余额";
					}else{
					    if (total != totalN) {
                            message = "该订单总金额：" + $.formatMoney(total) + messageN + ",但余额不足,无法在使用水票且余额不足情况下继续下单.";
                            $.messager.alert('Warning', message);
                            return ;
						}else {
                            message = "该订单总金额：" + $.formatMoney(total) + ",余额不足，是否继续下单";
						}
					}
					parent.$.messager.confirm('询问', message, function (b) {
						if (b) {
                            var isCheckedPayByVoucher = $('#checkPayByVoucher').attr('checked');
							$.ajax({
								url: '${pageContext.request.contextPath}/mbOrderController/addByCall?isCheckedPayByVoucher=' + isCheckedPayByVoucher ,
								data: JSON.stringify(data),
								dataType: "json",
								type: "POST",
								contentType: "application/json;charset=UTF-8",
								beforeSend: function (request) {
								    console.log("ajax.beforeSend.data"  + JSON.stringify(data));
									parent.$.messager.progress({
										title: '提示',
										text: '数据处理中，请稍后....'
									});
								},
								success: function (data) {
									parent.$.messager.progress('close');
									if(data.success) {
										$('#dg').datagrid('acceptChanges');
										viewOrder(data.obj);
										window.location.reload();
									}else{
										//data
										parent.$.messager.alert('错误', data.msg);
									}
								},
								error: function (XMLHttpRequest, textStatus, errorThrown) {
									parent.$.messager.progress('close');
								}
							});

						}
					});
				}
			}

			function confirmFun(id) {
				if (id == undefined) {
					var rows = dataGrid.datagrid('getSelections');
					id = rows[0].id;
				}
				parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
					if (b) {
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post('${pageContext.request.contextPath}/mbOrderController/delete', {
							id : id
						}, function(result) {
							if (result.success) {
								parent.$.messager.alert('提示', result.msg, 'info');
								dataGrid.datagrid('reload');
							}
							parent.$.messager.progress('close');
						}, 'JSON');
					}
				});
			}

			function reject(){
				$('#dg').datagrid('rejectChanges');
				editIndex = undefined;
			}
			function getChanges(){
				var rows = $('#dg').datagrid('getChanges');
				return rows;
			}
			function countPayByVoucherTotal(rows,total) {
			    var total = total;
			    console.log('count.before:' + JSON.stringify(rows))
			    var voucherQuantityTotal = 0;
			    var itemQuantity = 0;
				var isChecked = $('#checkPayByVoucher').attr('checked');
				if (isChecked) {
				    total = 0;
                    for(var i =0;i<rows.length;i++){
						voucherQuantityTotal =  rows[i].voucherQuantityTotal == undefined ? 0 : rows[i].voucherQuantityTotal;
                        itemQuantity = rows[i].quantity - voucherQuantityTotal;
                        itemQuantity = itemQuantity < 0 ? 0 : itemQuantity;
                        console.log(total+ '-' +itemQuantity + '....' + rows[i].buyPrice );
                        total += rows[i].buyPrice * itemQuantity;
                    }
				}
                console.log(total);
				return total;
            }
		</script>
	 </div>
	</div>
			<div  data-options="region:'east'"  style="width: 220px; overflow: hidden;">
				<table id="dataOrder"></table>
			</div>
	    </div>
    </div>
</div>
<div id="toolbar" style="display: none;">
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
</div>
</body>
</html>