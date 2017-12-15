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
        $(function(){
            $("#bcTarget").barcode("${mbOrder.id}", "codabar",{
                output:'css',       //渲染方式 css/bmp/svg/canvas
                //bgColor: '#ff0000', //条码背景颜色
                color: '#000000',   //条码颜色
                barWidth: 3,        //单条条码宽度
                barHeight: 45,     //单体条码高度
//                moduleSize: 5,   //条码大小
//                posX: 10,        //条码坐标X
//                posY: 5,         //条码坐标Y
                addQuietZone: false  //是否添加空白区（内边距）
            });
        })

        //参考api http://printfree.jatools.com/document.html
        /*function doPrint() {
         var myDoc = {
         settings:{
         paperName:'BX6L',
         orientation:1,
         topMargin:50,
         //printer:'OKi5530',//设置到打印机 'OKi5530',如果不设置打印机，控件则输出到默认打印机
         leftMargin:0,
         bottomMargin:50,
         rightMargin:0/!*,
         paperWidth:2100,
         paperHeight:1400*!/
         },
         documents: document,
         /!*
         要打印的div 对象在本文档中，控件将从本文档中的 id 为 'page1' 的div对象，
         作为首页打印id 为'page2'的作为第二页打印            *!/
         copyrights: '杰创软件拥有版权  www.jatools.com' // 版权声明,必须
         };
         var jcp = getJCP();
         jcp.print(myDoc,true);

         //  document.getElementById("jatoolsPrinter").print(myDoc,true); // 直接打印，不弹出打印机设置对话框
         }*/
        function doPrint(how) {
            var myDoc = {
                //		logPage : true,
                //	xuc : "7",
                settings: {
                    paperName: 'BX6L',
                    portrait: true,
                    marginLeft: 15,
                    marginTop: 15,
                    marginRight: 15,
                    marginBottom: 15
                },
                documents: document,
                copyrights: '杰创软件拥有版权  www.jatools.com'
                /*settings:{
                 paperName:'BX6L',
                 orientation:1,
                 topMargin:0,
                 leftMargin:10,
                 bottomMargin:50,
                 rightMargin:0 },
                 documents: document,
                 /!*
                 要打印的div 对象在本文档中，控件将从本文档中的 id 为 'page1' 的div对象，
                 作为首页打印id 为'page2'的作为第二页打印            *!/
                 copyrights: '杰创软件拥有版权  www.jatools.com' // 版权声明,必须*/
            };
            /*  document.getElementById("jatoolsPrinter").printPreview(myDoc); */
            // 直接打印，不弹出打印机设置对话框
            if (how == '打印预览') {
                document.getElementById("jatoolsPrinter").printPreview(myDoc, false);
                //  jcp.printPreview(myDoc, false); // 打印预览
            } else if (how == "打印") {
                document.getElementById("jatoolsPrinter").print(myDoc,false);
                //jcp.print(myDoc, true); // 打印前弹出打印设置对话框
            }
        }
        function init(){
            if(parent&&parent.printComplete){
                try {
                    doPrint("打印预览");
                } catch (e) {

                }
                /*setTimeout(function(){
                 parent.printComplete();
                 },1000);*/
            }
        }
    </script>
    <style type="text/css">
        #sample td{border:solid 1px black}
        .jatools-printing{}
        /*
                .jatools-printing #sample td{border:0px solid white;}
        */
    </style>

</head>
<body onload="init();">
<input type="button" value="预览" onClick='doPrint("打印预览")'>&nbsp;<input type="button" value="打印" onClick='doPrint("打印")'>
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
         <c:if test="${status.count==10||(status.count-10)%18==0}">
           </table> </div><div id='page${(status.count-10)/18==0?2:fn:substring(((status.count-10)/18+2),0,fn:indexOf(((status.count-10)/18), '.'))}' class="breakable" style='width: 100%; margin: 0px; padding: 0; background-color: white;'>
           <table id='sample' cellpadding="0" style="border-collapse: collapse" width="100%">
        </c:if>
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
