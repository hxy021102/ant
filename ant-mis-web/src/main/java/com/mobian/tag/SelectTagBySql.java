package com.mobian.tag;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.listener.Application;
import com.mobian.service.BasedataServiceI;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.*;

public class SelectTagBySql extends TagSupport{
	private static Logger logger = Logger.getLogger(SelectTagBySql.class);
	
	/**
	 *
	 */
	private static final long serialVersionUID = -2709846727239749266L;
	private String name;
	private String dataType;
	private String value;
	private String onselect;
	private boolean multiple;
	//查询数据的条件,字符串,格式是JavaScript 的 JSON格式字符串作为参数,但是要求是只是键值对的形式,该JSON对象内不能再封装对象只能是键和值(基础类型)
	private String parameters;
	private boolean required;
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
	@SuppressWarnings("rawtypes")
	@Override  
    public int doStartTag() throws JspException {  
		JspWriter out = pageContext.getOut();  		  
        try{
			if(required) {
				out.print("<select name=\"" + name + "\" class=\"easyui-combobox easyui-validatebox\" data-options=\"width:140,height:29,editable:false,required:true,");
			}else{
				if(multiple) {
					out.print("<select name=\"" + name + "\" class=\"easyui-combobox easyui-validatebox\" data-options=\"width:140,height:29,editable:false,panelHeight:'auto',required:true,multiple:true,");
				}else {
					out.print("<select name=\"" + name + "\" class=\"easyui-combobox\" data-options=\"width:140,height:29,editable:false,panelHeight:'auto',");
				}

				out.print("<select name=\"" + name + "\" class=\"easyui-combobox\" data-options=\"width:140,height:29,editable:false,");
			}
			BasedataServiceI service = Application.getBasedataService();
        	String sql = Application.get(dataType).getDescription();
			Map < String , Object > params = null ;
			//若onselect中有值,则对则会在select选中某条选项时触发easyUI的onSelect事件,从而执行onselect传递过来的函数体,并将选中的值作为参数
			if (!F.empty(onselect)){
				out.print(" onSelect: function(selectIndex){" +
						"       console.log('" + name + ".selectTag.selectIndex:' + selectIndex );" +
						"       if(Object.prototype.toString.call(" + onselect + ") === '[object Function]'){" +
						"           var selectValue = $(this).combobox('getValue');" +
						"           console.log('" + name + ".selectTag.selectValue:' + selectValue );" +
						"           " + onselect + "(selectValue);" +
						"       }" +
						"   },");
			}
			out.print("\">");
        	if(!F.empty(parameters)){
        		try {
					params = jsonString2Map(parameters) ;
				}catch (Exception e){
        			params = null;
				}
			}

			List<Map> baseDataList = service.getSelectMapList(sql, params);
        	if(!required)
        	out.print("<option value=\"\">    </option>");
        	String value;
        	String text;
        	for(Map bd : baseDataList){
        		value = bd.get("value").toString();
        		text = bd.get("text").toString();
        		if(F.empty(this.value)||!this.value.equals(value)){
                	out.print("<option value=\""+value+"\">"+text+"</option>");
        		}else{
                	out.print("<option value=\""+value+"\" selected=\"selected\">"+text+"</option>");
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
	public void setName(String name) {
		this.name = name;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}


	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}


	/**
	 * JSON格式字符串转Map<String ,Object>
	 * 只处理一级JSON,即JSON内不包含除基本类型的其他对象类型
	 * @param jsonString
	 * @return
	 * @throws JSONException
	 */
	private Map<String, Object> jsonString2Map( String jsonString ) throws JSONException {
		logger.debug(" SelectTagBySql.jsonString2Map : " + jsonString );
		Map<String, Object> om = new HashMap<String, Object>();
		JSONObject jsonObject = JSON.parseObject( jsonString );
		Set keyset = jsonObject.keySet();
		Iterator<?> keyIter = keyset.iterator();
		while (keyIter.hasNext()) {
			String key =  (String) keyIter.next();
			Object value = jsonObject.get(key);
			if ( value instanceof JSONObject ) {
				logger.debug("JSON Incomin value is of JSONObject : " + value +" will not convert to Map ! " );
			}else if ( value instanceof JSONArray) {
				logger.debug("JSON Incomin value is of JSONArray : "+ value +" will not convert to Map ! ");
			} else {
				logger.debug("JSON Incomin value is of Basic Type : "+ value +" will convert to Map ! " );
				om.put( key, value );
			}
		}
		return om;
	}

	public String getOnselect() {
		return onselect;
	}

	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}
}
