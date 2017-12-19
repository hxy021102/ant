<%@ page import="com.mobian.pageModel.MbOrderItem" %><%--
  Created by IntelliJ IDEA.
  User: wanxp
  Date: 7/5/17
  Time: 11:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <script src="${pageContext.request.contextPath}/jslib/jquery-1.8.3.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-barcode.min.js" charset="utf-8"></script>
    <OBJECT  ID="jatoolsPrinter" CLASSID="CLSID:B43D3361-D075-4BE2-87FE-057188254255"
             codebase="jatoolsPrinter.cab#version=8,6,0,0"></OBJECT>
    <script>

        $(function(){
            $(".bcTarget").each(function(){
                var orderId = $(this).attr('orderId');
                $(this).barcode(orderId, "codabar",{
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
            });

        })

        //参考api http://printfree.jatools.com/document.html
        function doPrint() {
            var myDoc = {
                settings:{
                    paperName:'BX6L',
                    orientation:1,
                    topMargin:50,
                    //printer:'OKi5530',//设置到打印机 'OKi5530',如果不设置打印机，控件则输出到默认打印机
                    leftMargin:0,
                    bottomMargin:50,
                    rightMargin:0/*,
                    paperWidth:2100,
                    paperHeight:1400*/
                },
                documents: document,
                /*
                 要打印的div 对象在本文档中，控件将从本文档中的 id 为 'page1' 的div对象，
                 作为首页打印id 为'page2'的作为第二页打印            */
                copyrights: '杰创软件拥有版权  www.jatools.com' // 版权声明,必须
            };
            document.getElementById("jatoolsPrinter").print(myDoc,false); // 直接打印，不弹出打印机设置对话框
        }
        function doPreview() {
            var myDoc = {
                settings:{
                    paperName:'BX6L',
                    orientation:1,
                    topMargin:0,
                    leftMargin:10,
                    bottomMargin:50,
                    rightMargin:0 },
                documents: document,
                /*
                 要打印的div 对象在本文档中，控件将从本文档中的 id 为 'page1' 的div对象，
                 作为首页打印id 为'page2'的作为第二页打印            */
                copyrights: '杰创软件拥有版权  www.jatools.com' // 版权声明,必须
            };
            document.getElementById("jatoolsPrinter").printPreview(myDoc); // 直接打印，不弹出打印机设置对话框
        }
        function init(){
            if(parent){
                try {
                    doPrint();
                }catch(e){

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
<input type="button" value="预览" onClick='doPreview()' style="display: none;">&nbsp;<input type="button" value="打印" onClick='doPrint()' style="display: none;">
<c:forEach items="${deliverOrderExtList}" var="deliverOrderExt" varStatus="status">
    <div id='page${status.count}'>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr>
                <td colspan="3"></td>
                <td rowspan="2" align="center"><div class="bcTarget" orderId="${deliverOrderExt.id}"></div></td>
            </tr>
            <tr>
                <td colspan="4" align="center" valign="top">上海奔翔实业有限公司配送单</td>
            </tr>
            <tr>
                <td align="left" width="15%">&nbsp;&nbsp;&nbsp;订单时间：</td>
                <td width="35%"><fmt:formatDate value="${deliverOrderExt.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td align="left" width="15%">发货仓：</td>
                <td width="35%">王桥电商仓</td>
            </tr>
            <tr>
                <td align="left" width="15%">&nbsp;&nbsp;&nbsp;订单编号：</td>
                <td width="35%">${deliverOrderExt.id}</td>
                <td align="left" width="15%">打单时间：</td>
                <td width="35%">${printTime}</td>
            </tr>
        </table>
        <table id="sample" cellpadding="0" style="border-collapse: collapse" width="100%">
            <tr>
                <td>代送点编号</td>
                <td colspan="2">${deliverOrderExt.mbShop.id}</td>
                <td>代送点名称</td>
                <td colspan="6">${deliverOrderExt.mbShop.name}</td>
                <td>代送点电话</td>
                <td colspan="3">${deliverOrderExt.mbShop.contactPhone}</td>
            </tr>
            <tr>
                <td>代送点地址</td>
                <td colspan="8">${deliverOrderExt.mbShop.address}</td>
                <td>所属区域</td>
                <td colspan="3">${deliverOrderExt.mbShop.regionPath}</td>
            </tr>
            <tr>
                <td>客户名称</td>
                <td colspan="2">${deliverOrderExt.contactPeople}</td>
                <td>客户电话</td>
                <td colspan="3">${deliverOrderExt.contactPhone}</td>
                <td>店铺名称</td>
                <td colspan="3">${deliverOrderExt.originalShop}</td>
                <td>原订单号</td>
                <td colspan="2">${deliverOrderExt.originalOrderId}</td>

            </tr>
            <tr>
                <td>客户地址</td>
                <td colspan="12">${deliverOrderExt.deliveryAddress}</td>
            </tr>
            <tr>
                <td>订单备注</td>
                <td colspan="12">${deliverOrderExt.remark}</td>
            </tr>
            <tr>
                <td colspan="7">品名</td>
                <td>数量</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>实送量</td>
            </tr>
            <c:forEach items="${deliverOrderExt.deliverOrderShopItemList}" var="mbOrderItem" varStatus="status">
                <c:set value="${mbOrderItem.quantity + totalQuantity}" var="totalQuantity"></c:set>
               <tr style="border-top:none;border-bottom:none;" valign="top">
                    <td colspan="7">${mbOrderItem.itemName}[${mbOrderItem.itemCode}]</td>
                    <td>${mbOrderItem.quantity}</td>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        &nbsp;
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="7">总计</td>
                <td>${totalQuantity}</td>
                <td></td>
                <td>

                </td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
        <table width="100%" cellspacing="0" cellpadding="0">
            <tr>

                <td>客户签名：</td>
                <td>配送员签名：</td>
                <td>仓库发货员签名：</td>
            </tr>

        </table>
    </div>
</c:forEach>
</body>
</html>

