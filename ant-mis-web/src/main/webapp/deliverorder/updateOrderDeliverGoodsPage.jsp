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
     *   发货完成
     */
    function printComplete(deliverOrderId) {
        if(!deliverOrderId) return;
        parent.$.messager.progress({
            title : '提示',
            text : '数据处理中，请稍后....'
        });
        $.post('${pageContext.request.contextPath}/deliverOrderController/updateOrderDeliverGoods', {
            deliverOrderId : deliverOrderId
        }, function(result) {
            if (result.success) {
                showResult(true,result.msg);
            } else {
                showResult(false,result.msg);
            }
            parent.$.messager.progress('close');
        }, 'JSON');
    }

    function cleanFun() {
		$('#scanInp').val('').focus();
	}

    function showResult(result, message) {
        var resultId = document.getElementById("resultId");
        var messageStr;
        if (result) {
            messageStr = '<font color="green" size="4">' + message + '</font>';
        } else {
            messageStr = '<font color="red" size="4">' + message + '</font>';

        }
        resultId.innerHTML = messageStr;
        cleanFun();
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
								运单ID:<input type="text" id="scanInp"  maxlength="16" class="span2" placeholder="扫码发货" style="width: 150px;margin-bottom: 0;"/>
							<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="cleanFun ()"  >清空</a>
								<div id="resultId"></div>
							</div>
						</td>
					</tr>
				</table>
		</div>
	</div>
</body>
</html>