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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/guide.css" />
  <%--  <script type="text/javascript" src="${pageContext.request.contextPath}/web/js/jcp.js"></script>--%>

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
                    doPrint("打印");
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
<input type="button" value="预览" onClick='doPrint("打印预览")' style="display: none;">&nbsp;<input type="button" value="打印" onClick='doPrint("打印")' style="display: none;">
<div id='page1' class="breakable" style='width: 100%; margin: 0px; padding: 0; background-color: white;'>
   <%-- <div id="Content">--%>
    <table width="100%" cellspacing="0" cellpadding="0"  border="0" id="table2">
    <tr>
            <td colspan="3"></td>
            <td rowspan="2" align="center"><div id="bcTarget"></div></td>
        </tr>
        <tr>
            <td colspan="4" align="center" valign="top">上海奔翔实业有限公司配送单</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;订单时间：</td>
            <td width="35%"><fmt:formatDate value="${mbOrder.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td align="left" width="15%">发货仓：</td>
            <td width="35%">${mbWarehouseName}</td>
        </tr>
        <tr>
            <td align="left" width="15%">&nbsp;&nbsp;&nbsp;订单编号：</td>
            <td width="35%">${mbOrder.id}</td>
            <td align="left" width="15%">打单时间：</td>
            <td width="35%">${printTime}</td>
        </tr>
    </table>
    <table id='sample' cellpadding="0" style="border-collapse: collapse" width="100%">
        <tr>
            <td>客户编号</td>
            <td colspan="2">${mbOrder.shopId}</td>
            <td>客户名称</td>
            <td colspan="6">${mbOrder.shopName}</td>
            <td>电话</td>
            <td colspan="2">${mbOrder.contactPhone}</td>
        </tr>
        <tr>
            <td>配送地址</td>
            <td colspan="8">${mbOrder.deliveryAddress}</td>
            <td>所属区域</td>
            <td colspan="3">${mbShop.regionPath}</td>
        </tr>
        <tr>
            <td>订单备注</td>
            <td colspan="8">${mbOrder.userRemark}</td>
            <td>录入人</td>
            <td colspan="3">
                <c:choose>
                    <c:when test="${mbOrder.addLoginId!=null}">
                        客服
                    </c:when>
                    <c:otherwise>
                        公众号
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td colspan="7">品名</td>
            <td>数量</td>
            <td>单价</td>
            <td>金额</td>
            <td>回桶</td>
            <td>回票</td>
            <td>实送量</td>
        </tr>
        <c:forEach items="${mbOrderItemList }" var="mbOrderItem" varStatus="status">
            <c:set value="${mbOrderItem.quantity + totalQuantity}" var="totalQuantity"></c:set>
            <c:set value="${mbOrderItem.buyPrice * mbOrderItem.quantity + totalItemPrice}" var="totalItemPrice"></c:set>
           <tr style="border-top:none;border-bottom:none;" valign="top">
                <td colspan="7">${mbOrderItem.item.name}&nbsp;${mbOrderItem.item.quantityUnitName}</td>
                <td>${mbOrderItem.quantity}</td>
                <td>
                    ${mbOrderItem.buyPrice/100.00}
                </td>
                <td>
                    ${mbOrderItem.buyPrice * mbOrderItem.quantity/100.00}
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            </tr>
           <c:if test="${status.count==9||(status.count-9)%18==0}">
           </table> </div><div id='page${(status.count-9)/18==0?2:fn:substring(((status.count-9)/18+2),0,fn:indexOf(((status.count-9)/18), '.'))}' class="breakable" style='width: 100%; margin: 0px; padding: 0; background-color: white;'>
               <table id='sample' cellpadding="0" style="border-collapse: collapse" width="100%">
           </c:if>
        </c:forEach>
        <tr>
            <td colspan="7">总计</td>
            <td>${totalQuantity}</td>
            <td></td>
            <td>
                ${totalItemPrice/100.00}
            </td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>付款方式</td>
            <td  >${mbPayment.payWayName}</td>

            <td colspan="2">券票</td>

            <td>欠桶数</td>
            <td>还桶数</td>
            <td colspan="2">累计欠桶量</td>
            <td>&nbsp;</td>
            <td>本单欠款</td>
            <td colspan="3">
                <c:if test="${mbOrder.payStatus=='PS01'}">
                    ${totalItemPrice/100.00}
                </c:if>
            </td>
        </tr>
        <tr>
            <td>${mbOrder.payStatusName}</td>
            <td  >${mbPayment.amount/100.00}</td>

            <td colspan="2">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td colspan="2">累计欠结</td>
            <td></td>
            <td>账户余额</td>
            <td colspan="3">${mbBalance.amount/100.00}</td>
        </tr>
    </table>
    <table width="100%" cellspacing="0" cellpadding="0">
        <tr>

            <td>客户签名：</td>
            <td>配送员签名：</td>
            <td>仓库发货员签名：</td>
            <td>仓库收桶员签名：</td>
        </tr>

    </table>
 <%--  </div>--%>
</div>
</body>
</html>

