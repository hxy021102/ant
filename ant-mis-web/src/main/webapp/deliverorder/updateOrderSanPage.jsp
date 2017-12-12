<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>DeliverOrder扫码</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
    $(function () {
        parent.$.messager.progress('close');
        if($('#scanInp').length != 0) {
            $('#scanInp').focus();
            $('#scanInp').keyup(function(event) {
                if (event.keyCode == '13') {
                    var inp = $.trim($(this).val());
                    if(inp != '') {
                        printComplete(inp);
                    }
                }
            });
        }
    });

    /**
     *   打印完成
     */
    function printComplete(deliverOrderId) {
        if(!deliverOrderId) return;
        parent.$.messager.progress({
            title : '提示',
            text : '数据处理中，请稍后....'
        });
        $.post('${pageContext.request.contextPath}/deliverOrderController/updateOrderSan', {
            deliverOrderId : deliverOrderId
        }, function(result) {
            if (result.success) {
                window.location.reload();
                var href = '${pageContext.request.contextPath}/deliverOrderController/view?id=' + deliverOrderId;
                parent.$("#index_tabs").tabs('add', {
                    title : '运单详情-' + deliverOrderId,
                    content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
                    closable : true
                });
            } else {
                parent.$.messager.alert('提示', result.msg, 'info', function(){
                    $('#scanInp').val('').focus();
                });
            }
            parent.$.messager.progress('close');
        }, 'JSON');
    }

    function cleanFun() {
		$('#scanInp').val('');
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:' ',border:false" style="height: 165px;  ">
				<table class="table table-hover table-condensed"  >
					<tr>
						<td>
							<div style="margin-bottom:20px">
								运单ID:<input type="text" id="scanInp"  maxlength="16" class="span2" placeholder="扫码打单" style="width: 150px;margin-bottom: 0;"/>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reset"
							   onclick="cleanFun ()"  >清&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;空</a>
							</div>
						</td>
					</tr>
				</table>
		</div>
	</div>
</body>
</html>