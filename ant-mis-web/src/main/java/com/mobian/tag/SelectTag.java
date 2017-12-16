package com.mobian.tag;

import com.mobian.absx.F;
import com.mobian.listener.Application;
import com.mobian.pageModel.BaseData;
import com.mobian.service.BasedataServiceI;
import com.mobian.util.ConvertNameUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectTag extends TagSupport {


    /**
     *
     */
    private static final long serialVersionUID = -2709846727239749266L;
    private String name;
    private String dataType;
    private String value;
    private String onselect;
    private boolean mustSelect;
    private boolean multiple;
    private boolean required;
    private String noShowValue;

    public String getNoShowValue() {
        return noShowValue;
    }

    public void setNoShowValue(String noShowValue) {
        this.noShowValue = noShowValue;
    }

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
        try {
            String editable = "false";
            if (dataType.equals("GN")) {
                String path = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
                StringBuffer sb = new StringBuffer();
                String gClass = "grid_" + new Date().getTime();
                sb.append("<select name=\"" + name + "\" id=\"" + name + "\" class=\"easyui-combogrid " + gClass + "\"  data-options=\"");
                sb.append("width:140,height:29,panelWidth: 400,");
                sb.append("		idField: 'id',");
                sb.append("		textField: 'text',");
                //若onselect中有值,则对则会在select选中某条选项时触发easyUI的onSelect事件,从而执行onselect传递过来的函数体,并将选中的值作为参数
                if (!F.empty(onselect)) {
                    sb.append("       onSelect: function(selectIndex){" +
                            "               console.log('" + name + ".onselect.selectIndex:' + selectIndex);" +
                            //判定onselect传递过来的String值是否是JavaScript函数
                            "               if(Object.prototype.toString.call(" + onselect + ") === '[object Function]'){" +
                            "                   var selectValue = $('#" + name + "').combobox('getValue');" +
                            "                   console.log('" + name + ".selectTag.selectValue' + selectValue);" +
                            onselect + "(selectValue);" +
                            "               }" +
                            "            },");
                }
                String showName = null;
                if (!F.empty(value)) {
                    showName = ConvertNameUtil.getString(value);
                    sb.append("		mode: 'local',");
                    //sb.append("		onShowPanel: function(){alert(1)},");
                    sb.append("		onShowPanel: function(e){" +
                            "var select = $('." + gClass + "');" +
                            "if(!select.attr('enableRemote')){" +


                            "select.combogrid({ mode: 'remote',method: 'post',url: '" + path + "/basedataController/goodsQuery'});" +
                            //"setTimeout(function(){select.combogrid('setText','"+showName+"')},1000);" +
                            "select.next().find('input').val('" + showName + "');" +
                            "var grid = select.combogrid('grid');" +
                            "grid.datagrid({'queryParams':{id:'" + value + "',q: '" + showName + "'}});" +
                            "select.attr('enableRemote',true);}}," +
                            "onHidePanel:function(e){" +
                            "var select = $('." + gClass + "');" +
                            "var val = select.combogrid('getValue');" +
                            "if(val == '" + showName + "'){select.combogrid('setValue','" + value + "')}else if(val.indexOf('GN')!=0){select.combogrid('setValue','')}" +
                            "},");
                    sb.append("		value:'" + value + "',data:[{id:'" + value + "',text:'" + showName + "'}],");
                } else {
                    sb.append("		url: '" + path + "/basedataController/goodsQuery',");
                    sb.append("		method: 'post',");
                    sb.append("		mode: 'remote',");

                }
                sb.append("		columns: [[");
                sb.append("{field:'id',title:'ID',width:100},");
                sb.append("{field:'text',title:'名称',width:180}");
                sb.append("]]");
                sb.append("\">");
                out.print(sb.toString());
                if (!F.empty(showName)) {
                    /*out.print("<script type=\"text/javascript\">$(function() {" +
                            "$('."+gClass+"').combogrid({keyHandler: {down:function(e){alert(1)}}});" +
							"});</script>");*/
                }
            } else {
                out.print("<select name=\"" + name + "\" id=\"" + name + "\" class=\"easyui-combobox\" data-options=\"width:140,height:29,multiple:" + multiple + ",editable:" + editable);
                if (!F.empty(onselect)) {
                    out.print(",onSelect: function(selectIndex){" +
                            "       console.log('" + name + ".selectTag.selectIndex:' + selectIndex );" +
                            "       if(Object.prototype.toString.call(" + onselect + ") === '[object Function]'){" +
                            "           var selectValue = $('#" + name + "').combobox('getValue');" +
                            "           console.log('" + name + ".selectTag.selectValue' + selectValue);" +
                            "           " + onselect + "(selectValue);" +
                            "       }" +
                            "   }");
                }
                out.print("\">");
                BasedataServiceI service = Application.getBasedataService();
                BaseData baseData = new BaseData();
                baseData.setBasetypeCode(dataType);
                List<BaseData> baseDataList = service.getBaseDatas(baseData);
                List<String> noShowValueList = new ArrayList<String>();
                if (!F.empty(noShowValue)) {
                    for (String value : noShowValue.split(",")) {
                        noShowValueList.add(value);
                    }
                }
                if (!required)
                    out.print("<option value=\"\">    </option>");
                for (BaseData bd : baseDataList) {
                    if (multiple) {
                        out.print("<option value=\"" + bd.getId() + "\">" + bd.getName() + "</option>");
                    } else {
                        if (F.empty(value) || !value.equals(bd.getId())) {
                            if (noShowValueList != null && noShowValueList.size() > 0) {
                                if (noShowValueList.contains(bd.getId())) continue;
                            }
                            out.print("<option value=\"" + bd.getId() + "\">" + bd.getName() + "</option>");
                        } else {
                            out.print("<option value=\"" + bd.getId() + "\" selected=\"selected\">" + bd.getName() + "</option>");
                        }
                    }

                }
            }
            out.print("</select>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
        //return TagSupport.EVAL_BODY_INCLUDE;//输出标签体内容  
        //return TagSupport.SKIP_BODY;//不输出标签体内容  
    }

    public String getName() {
        return name;
    }

    public String getOnselect() {
        return onselect;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
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
}
