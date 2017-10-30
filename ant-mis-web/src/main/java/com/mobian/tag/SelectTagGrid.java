package com.mobian.tag;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bx.ant.service.ShopDeliverAccountServiceI;
import com.bx.ant.service.SupplierServiceI;
import com.mobian.absx.F;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.bx.ant.pageModel.ShopDeliverAccount;
import com.bx.ant.pageModel.Supplier;
import com.mobian.service.*;
import com.mobian.util.BeanUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SelectTagGrid extends TagSupport {


    /**
     *
     */
    private static final long serialVersionUID = -2709846727239749266L;
    private String name;
    private String dataType;
    private String value;
    private String disabled;
    private boolean mustSelect;
    private boolean multiple;
    private String onselect;
    private boolean required;
    private String params;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        DiveRegionServiceI diveRegionService = BeanUtil.getBean(DiveRegionServiceI.class);
        try {
            String _disabled = "false";
            if (!F.empty(disabled)) {
                _disabled = disabled;
            }
            String url = getURI();
            StringBuffer sb = new StringBuffer();
            String gClass = "grid_" + new Date().getTime();
            sb.append("<select name=\"" + name + "\" id=\"" + name + "\" class=\"easyui-combogrid easyui-validatebox " + gClass + "\"  data-options=\"");
            sb.append("width:140,height:29,panelWidth: 440,");
            if (required) {
                sb.append("		required: " + required + ",");
            }
            if(multiple) {
                sb.append("		multiple: " + multiple + ",");
            }
            sb.append("		disabled: " + _disabled + ",");
            sb.append("		idField: 'id',");
            sb.append("		textField: 'text',");
            //接受onselect标签传递过来的函数名,当该函数名有效时会在select的option被选中并且有值改变时会执行该onselect的函数
            if (!F.empty(onselect)) {
                //执行onselect函数体
                sb.append("		onSelect: function(selectIndex){" +
                        "           if(Object.prototype.toString.call(" + onselect + ") === '[object Function]' ){" +
                        "               console.log('" + name + "..selectIndex:' + selectIndex );" +
                        "               var selectValue = $('#" + name + "').combobox('getValue');" +
                        "               console.log('" + name + " selectValue:' +  selectValue);" +
                        "               "+onselect + "(selectValue);" +
                        "           }" +
                        "       },");
            }

            String showName = null;
            if (!F.empty(value)) {
                Map<String, Object> data = getData();
                showName = (String) data.get("text");
                String dataJson = JSON.toJSONString(data, SerializerFeature.UseSingleQuotes, SerializerFeature.QuoteFieldNames);
                sb.append("		mode: 'local',");
                //sb.append("		onShowPanel: function(){alert(1)},");
                sb.append("		onShowPanel: function(e){" +
                        "var select = $('." + gClass + "');" +
                        "if(!select.attr('enableRemote')){" +


                        "select.combogrid({ mode: 'remote',method: 'post',url: '" + url + "'});" +
                        //"setTimeout(function(){select.combogrid('setText','"+showName+"')},1000);" +
                        "select.next().find('input').val('" + showName + "');" +
                        "var grid = select.combogrid('grid');" +
                        "grid.datagrid({'queryParams':{id:'" + value + "',q: '" + showName + "'}});" +
                        "select.attr('enableRemote',true);}}," +
                        "onHidePanel:function(e){" +
                        "var select = $('." + gClass + "');" +
                        "var val = select.combogrid('getValue');" +
                        "if(val == '" + showName + "'){select.combogrid('setValue','" + value + "')}else if(isNaN(val)){select.combogrid('setValue','')}" +
                        "},");
                sb.append("		value:'" + value + "',data:[" + dataJson + "],");
            } else {
                sb.append("		url: '" + url + "',");
                sb.append("		method: 'post',");
                sb.append("		mode: 'remote',");

            }
            sb.append(getHeader());

            sb.append("\">");
            out.print(sb.toString());
            if (!F.empty(showName)) {
                /*out.print("<script type=\"text/javascript\">$(function() {" +
						"$('."+gClass+"').combogrid({keyHandler: {down:function(e){alert(1)}}});" +
						"});</script>");*/
            }

            out.print("</select>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
        //return TagSupport.EVAL_BODY_INCLUDE;//输出标签体内容  
        //return TagSupport.SKIP_BODY;//不输出标签体内容  
    }

    private Map getData() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", value);
        if ("itemId".equals(dataType)) {
            MbItemServiceI mbItemService = BeanUtil.getBean(MbItemServiceI.class);
            MbItem mbItem = mbItemService.getFromCache(Integer.parseInt(value));
            data.put("text", mbItem.getName());
            data.put("parentName", mbItem.getCategoryName());
        } else if ("shopId".equals(dataType)) {
            MbShopServiceI mbShopService = BeanUtil.getBean(MbShopServiceI.class);
            MbShop mbShop = mbShopService.getFromCache(Integer.parseInt(value));
            if (mbShop != null) {
                data.put("text", mbShop.getName());
                data.put("parentName", mbShop.getRegionPath());
                String pid = "";
                if(!F.empty(mbShop.getParentId())) {
                    mbShop = mbShopService.getFromCache(mbShop.getParentId());
                    pid = mbShop.getName();
                }
                data.put("pid", pid);
            }
        } else if ("warehouseId".equals(dataType)) {
            MbWarehouseServiceI mbWarehouseService = BeanUtil.getBean(MbWarehouseServiceI.class);
            MbWarehouse mbWarehouse = mbWarehouseService.getFromCache(Integer.parseInt(value));
            if (mbWarehouse != null) {
                data.put("text", mbWarehouse.getName());
                data.put("parentName", mbWarehouse.getRegionPath());
            }
        } else if ("supplierId".equals(dataType)) {

            MbSupplierServiceI mbSupplierService = BeanUtil.getBean(MbSupplierServiceI.class);
            MbSupplier mbSupplier = mbSupplierService.getFromCache(Integer.parseInt(value));
            if (mbSupplier != null) {
                data.put("text", mbSupplier.getName());
                data.put("parentName", mbSupplier.getRegionPath());
            }
        } else if ("mbUserId".equals(dataType)) {
            MbUserServiceI mbUserService = BeanUtil.getBean(MbUserServiceI.class);
            MbUser mbUser = mbUserService.getFromCache(Integer.parseInt(value));
            if (mbUser != null) {
                data.put("text", mbUser.getUserName());
                data.put("parentName", mbUser.getNickName());
            }
        } else if ("accountId".equals(dataType)) {
            ShopDeliverAccountServiceI shopDeliverAccountService = BeanUtil.getBean(ShopDeliverAccountServiceI.class);
            ShopDeliverAccount shopDeliverAccount= shopDeliverAccountService.getFromCache(Integer.parseInt(value));
            if (shopDeliverAccount != null) {
                data.put("text", shopDeliverAccount.getUserName());
                data.put("parentName", shopDeliverAccount.getNickName());
            }
        } else if ("couponsId".equals(dataType)) {
            MbCouponsServiceI mbCouponsService =BeanUtil.getBean(MbCouponsServiceI.class);
            MbCoupons mbCoupons = mbCouponsService.getFromCache(Integer.parseInt(value));
            if (mbCoupons != null) {
                data.put("text", mbCoupons.getName());
                data.put("parentName", mbCoupons.getCode());
                data.put("pid", mbCoupons.getPrice());
            }
        }else if ("deliverSupplierId".equals(dataType)) {
            SupplierServiceI supplierService =BeanUtil.getBean(SupplierServiceI.class);
            Supplier supplier = supplierService.get(Integer.parseInt(value));
            if (supplier != null) {
                data.put("text", supplier.getName());
                data.put("parentName", supplier.getContacter());
            }
        } else if ("assignShopId".equals(dataType)) {
            MbShopServiceI mbShopService = BeanUtil.getBean(MbShopServiceI.class);
            MbShop mbShop = mbShopService.getFromCache(Integer.parseInt(value));
            if (mbShop != null) {
                data.put("text", mbShop.getName());
                data.put("parentName", mbShop.getContactPhone());
                String pid = "";
                if(!F.empty(mbShop.getParentId())) {
                    mbShop = mbShopService.getFromCache(mbShop.getParentId());
                    pid = mbShop.getName();
                }
             //   data.put("pid", pid);
            }
        }else {

            //行政区划
            DiveRegionServiceI diveRegionService = BeanUtil.getBean(DiveRegionServiceI.class);
            DiveRegion region = diveRegionService.getFromCache(value);
            if (region != null) {
                data.put("text", region.getRegionNameZh());
                region = diveRegionService.getFromCache(region.getRegionParentId());
                if (region != null)
                    data.put("parentName", region.getRegionNameZh());
            }
        }
        return data;
    }

    private String getURI() {
        String path = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
        String url;
        if ("itemId".equals(dataType)) {
            url = path + "/mbItemController/selectQuery";
        } else if ("shopId".equals(dataType)) {
            url = path + "/mbShopController/selectQuery";
            if(!F.empty(params)) url += "?params=" + params.replace("{", "%7b").replace("}", "%7d");
        } else if ("warehouseId".equals(dataType)) {
            url = path + "/mbWarehouseController/selectQuery";
        } else if ("supplierId".equals(dataType)) {
            url = path + "/mbSupplierController/selectQuery";
        } else if ("mbUserId".equals(dataType)) {
            url = path + "/mbUserController/selectQuery";
        } else if ("couponsId".equals(dataType)) {
            url = path + "/mbCouponsController/selectQuery";
        } else if ("accountId".equals(dataType)) {
            url = path + "/shopDeliverAccountController/selectQuery";
        }else if ("deliverSupplierId".equals(dataType)) {
            url = path + "/supplierController/selectQuery";
        }else if ("assignShopId".equals(dataType)) {
            url = path + "/mbShopController/selectssignShopQuery";
            System.out.println(URLEncoder.encode(params));
             if(!F.empty(params)) url += "?params=" + params.replace("{", "%7b").replace("}", "%7d").replaceAll("\"", "%22");
        }  else {
            url = path + "/diveRegionController/selectQuery";
        }
        return url;
    }

    private String getHeader() {
        StringBuffer sb = new StringBuffer();
        if ("itemId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:30},");
            sb.append("{field:'code',title:'编码',width:130},");
            sb.append("{field:'text',title:'名称',width:210},");
            sb.append("{field:'parentName',title:'分类',width:50}");
            sb.append("]]");
        } else if ("shopId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:80},");
            sb.append("{field:'text',title:'名称',width:150},");
            sb.append("{field:'pid',title:'主店名称',width:150},");
            sb.append("{field:'parentName',title:'区县',width:100}");
            sb.append("]]");
        } else if ("warehouseId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100},");
            sb.append("{field:'text',title:'名称',width:180},");
            sb.append("{field:'parentName',title:'区县',width:180}");
            sb.append("]]");
        } else if ("supplierId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100},");
            sb.append("{field:'text',title:'名称',width:180},");
            sb.append("{field:'parentName',title:'区县',width:180}");
            sb.append("]]");
        } else if ("mbUserId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100},");
            sb.append("{field:'text',title:'用户名',width:180},");
            sb.append("{field:'parentName',title:'昵称',width:180}");
            sb.append("]]");
        } else if ("couponsId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100,hidden:true},");
            sb.append("{field:'text',title:'券票名称',width:150},");
            sb.append("{field:'parentName',title:'券编码',width:150},");
            sb.append("{field:'pid',title:'价格',width:80,align:'right',formatter:function(value){ return $.formatMoney(value);}}");
            sb.append("]]");
        }else if ("accountId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100},");
            sb.append("{field:'text',title:'用户名',width:180},");
            sb.append("{field:'parentName',title:'昵称',width:180}");
            sb.append("]]");
        }else if ("deliverSupplierId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100},");
            sb.append("{field:'text',title:'供应商名称',width:180},");
            sb.append("{field:'parentName',title:'联系人',width:180}");
            sb.append("]]");
        } else if ("assignShopId".equals(dataType)) {
            sb.append("		columns: [[");
            sb.append("{field:'text',title:'名称',width:150},");
            sb.append("{field:'parentName',title:'联系号码',width:130},");
            sb.append("{field:'distance',title:'距离',width:100}");
            sb.append("]]");
        } else {
            sb.append("		columns: [[");
            sb.append("{field:'id',title:'ID',width:100},");
            sb.append("{field:'text',title:'名称',width:180},");
            sb.append("{field:'parentName',title:'上级',width:180}");
            sb.append("]]");
        }
        return sb.toString();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMustSelect() {
        return mustSelect;
    }

    public void setMustSelect(boolean mustSelect) {
        this.mustSelect = mustSelect;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getOnselect() {
        return onselect;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
