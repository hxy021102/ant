<%@ page import="com.mobian.pageModel.MbOrderItem" %>
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
                doPrint();
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
            <td colspan="4" align="center">上海奔翔实业有限公司入库单</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;入库单号：</td>
            <td width="35%">${mbSupplierStockIn.id}</td>
            <td align="left" width="15%">采购人：</td>
            <td width="35%">${mbSupplierOrder.supplierPeopleName}</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;采购订单号:</td>
            <td width="35%">${mbSupplierStockIn.supplierOrderId}</td>
            <td align="left" width="15%">入库人：</td>
            <td width="35%">${mbSupplierStockIn.signPeopleName}</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;供应商:</td>
            <td width="35%">${mbSupplierStockIn.supplierName}</td>
            <td align="left" width="15%">付款人：</td>
            <td width="35%">${mbSupplierFinanceLog.payLoginName}</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;备注:</td>
            <td width="35%">${mbSupplierStockIn.remark}</td>
        </tr>
    </table>
    <table id='sample' cellpadding="0" style="border-collapse: collapse" width="100%">
        <tr>
            <td>商品代码</td>
            <td>商品名称</td>
            <td>规格</td>
            <td>类型</td>
            <td>入库量</td>
            <td>退桶量</td>
            <td>单价</td>
            <td>金额</td>
        </tr>
        <c:forEach items="${mbSupplierStockInItemList }" var="mbSupplierStockInItem">
            <c:set value="${mbSupplierStockInItem.quantity + totalQuantity}" var="totalQuantity"></c:set>
            <c:set value="${mbSupplierStockInItem.price * mbSupplierStockInItem.quantity}" var="sumPrice"></c:set>
            <c:set value="${mbSupplierStockInItem.price * mbSupplierStockInItem.quantity + totalItemPrice}"
                   var="totalItemPrice"></c:set>
            <tr style="border-top:none;border-bottom:none;" valign="top">
                <td>${mbSupplierStockInItem.code}</td>
                <td>${mbSupplierStockInItem.productName}</td>
                <td>${mbSupplierStockInItem.quantityUnitName}</td>
                <td>${mbSupplierStockInItem.categoryName}</td>
                <td>${mbSupplierStockInItem.quantity}</td>
                <td></td>
                <td>${mbSupplierStockInItem.price/100.00}</td>
                <td>${sumPrice/100.00}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="4">总计</td>
            <td>${totalQuantity}</td>
            <td></td>
            <td></td>
            <td>
                ${totalItemPrice/100.00}
            </td>
        </tr>


    </table>
    <table width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td>采购人签名：</td>
            <td>入库人签名：</td>
            <td>付款人签名：</td>
        </tr>

    </table>
</div>
</body>
</html>
