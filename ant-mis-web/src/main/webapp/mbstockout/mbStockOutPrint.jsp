<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head lang="en">

<meta charset="UTF-8">
 <title></title>

    <OBJECT  ID="jatoolsPrinter" CLASSID="CLSID:B43D3361-D075-4BE2-87FE-057188254255"
             codebase="jatoolsPrinter.cab#version=8,6,0,0"></OBJECT>

    <script>
    //参考api http://printfree.jatools.com/document.html
    function doPrint() {
        var myDoc = {
            settings: {
                paperName: 'BX6L',
                orientation: 1,
                topMargin: 50,
                //printer:'OKi5530',//设置到打印机 'OKi5530',如果不设置打印机，控件则输出到默认打印机
                leftMargin: 0,
                bottomMargin: 50,
                rightMargin: 0/*,
                 paperWidth:2100,
                 paperHeight:1400*/
            },
            documents: document,
            /*
             要打印的div 对象在本文档中，控件将从本文档中的 id 为 'page1' 的div对象，
             作为首页打印id 为'page2'的作为第二页打印            */
            copyrights: '杰创软件拥有版权  www.jatools.com' // 版权声明,必须
        };
        document.getElementById("jatoolsPrinter").print(myDoc, false); // 直接打印，不弹出打印机设置对话框
    }
    function doPreview() {
        alert("你好sdd")
        var myDoc = {
            settings: {
                paperName: 'BX6L',
                orientation: 1,
                topMargin: 0,
                leftMargin: 10,
                bottomMargin: 50,
                rightMargin: 0
            },
            documents: document,
            /*
             要打印的div 对象在本文档中，控件将从本文档中的 id 为 'page1' 的div对象，
             作为首页打印id 为'page2'的作为第二页打印            */
            copyrights: '杰创软件拥有版权  www.jatools.com' // 版权声明,必须
        };

        document.getElementById("jatoolsPrinter").printPreview(myDoc,true); // 直接打印，不弹出打印机设置对话框
    }
    function init() {
        if (parent && parent.printComplete) {
            try {
                alert("你好")
                doPreview();
            } catch (e) {

            }
            setTimeout(function () {
                parent.printComplete();
            }, 1000);
        }
    }
</script>
<style type="text/css">
    #sample td {
        border: solid 1px black
    }
</style>

</head>
<body onload="init();">
<input type="button" value="预览" onClick='doPreview()'>&nbsp;<input type="button" value="打印" onClick='doPrint()'>
<div id='page1'>
    <table width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td colspan="4" align="center">上海奔翔实业有限公司出库单</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;出库单号：</td>
            <td width="35%">${mbStockOut.id}</td>
            <td align="left" width="15%">出库人：</td>
            <td width="35%">${mbStockOut.stockOutPeopleName}</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;仓库:</td>
            <td width="35%">${mbStockOut.warehouseName}</td>
            <td align="left" width="15%">出库类型：</td>
            <td width="35%">${mbStockOut.stockOutTypeName}</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;出库时间:</td>
            <td width="35%">${mbStockOut.stockOutTime}</td>
            <td align="left" width="15%">操作人：</td>
            <td width="35%">${mbStockOut.loginName}</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;备注:</td>
            <td width="35%">${mbStockOut.remark}</td>
        </tr>
    </table>
    <table id='sample' cellpadding="0" style="border-collapse: collapse" width="100%">
        <tr>
            <td>商品代码</td>
            <td>商品名称</td>
            <td>出库量</td>
            <td>实际出库量</td>
        </tr>
        <c:forEach items="${deliverOrderShopItemList }" var="deliverOrderShopItem">
            <tr style="border-top:none;border-bottom:none;" valign="top">
                <td>${deliverOrderShopItem.itemCode}</td>
                <td>${deliverOrderShopItem.itemName}</td>
                <td>${deliverOrderShopItem.quantity}</td>
                <td></td>
            </tr>
        </c:forEach>
    </table>
    <table width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td>出库人签名：</td>
        </tr>
    </table>
</div>
</body>
</html>
