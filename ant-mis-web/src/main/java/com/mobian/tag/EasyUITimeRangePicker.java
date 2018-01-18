package com.mobian.tag;

import com.mobian.absx.F;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by w9777 on 2018/1/11.
 * 功能:时间段摄取组件
 * 依赖:前端moment.js,easyUI框架
 *
 */
public class EasyUITimeRangePicker extends TagSupport {
    private String startTimeName;//开始时间名
    private String endTimeName;//结束时间名
    private String startTimeValue;//开始时间
    private String endTimeValue;//结束时间
    private String minDate;//最小日期,默认1995-10-09
    private Boolean required;//是否必须true/false 默认false
    private String format;//时间格式 默认yyyy-MM-dd hh:mm:ss

    @SuppressWarnings("rawtypes")
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            StringBuffer buffer = new StringBuffer();
            String sds = "";
            String eds = "";
            Date d = null;
            if (F.empty(required)) required = false;
            if (F.empty(format)) format = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format.replace("Y", "y")
                    .replace("D", "d")
                    .replace("H", "h"));
            SimpleDateFormat dateBoxFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            buffer.append("<script type=\"text/javascript\">\n" +
                    "                                //输出指定格式的时间格式\n" +
                    "                                Date.prototype.Format = function (fmt) { //author: meizz\n" +
                    "                                      fmt = fmt.replace(/H/g,'h');\n" +
                    "                                      fmt = fmt.replace(/D/g,'d');\n" +
                    "                                      fmt = fmt.replace(/Y/g,'y');\n" +
                    "                                    var o = {\n" +
                    "                                        \"M+\": this.getMonth() + 1, //月份\n" +
                    "                                        \"d+\": this.getDate(), //日\n" +
                    "                                        \"h+\": this.getHours(), //小时\n" +
                    "                                        \"m+\": this.getMinutes(), //分\n" +
                    "                                        \"s+\": this.getSeconds(), //秒\n" +
                    "                                        \"q+\": Math.floor((this.getMonth() + 3) / 3), //季度\n" +
                    "                                        \"S+\": this.getMilliseconds() //毫秒\n" +
                    "                                    };\n" +
                    "                                    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + \"\").substr(4 - RegExp.$1.length));\n" +
                    "                                    for (var k in o)\n" +
                    "                                        if (new RegExp(\"(\" + k + \")\").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : ((\"00\" + o[k]).substr((\"\" + o[k]).length)));\n" +
                    "                                    return fmt;\n" +
                    "                                }\n" +
                    "                            </script>");
            buffer.append("              <div>\n");
            if (!F.empty(startTimeName) && !F.empty(endTimeName)) {
                buffer.append(
                        "                                查询时间跨度: &nbsp&nbsp\n" +
                                "                                <input type=\"radio\" name=\"timerange\" value=\"0\" onclick=\"myRadioChange(this)\">今天&nbsp\n" +
                                "                                <input type=\"radio\" name=\"timerange\" value=\"1\" onclick=\"myRadioChange(this)\">近7天&nbsp\n" +
                                "                                <input type=\"radio\" name=\"timerange\" value=\"2\" onclick=\"myRadioChange(this)\" >近30天&nbsp\n" +
                                "                                <input type=\"radio\" name=\"timerange\" value=\"3\" onclick=\"myRadioChange(this)\">近3个月<br>\n"
                );
            }
            if (!F.empty(startTimeValue)) {
                try {
                    d = sdf.parse(startTimeValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sds = dateBoxFormat.format(d);
            }
            if (!F.empty(startTimeName)) {
                buffer.append(
                        "从&nbsp<input id=\"" + startTimeName + "\" name=\"" + startTimeName + "\" type=\"text\" class=\"easyui-datetimebox myStartTimeClass\" data-options=\"required:" +
                                required  + "\"  value=\"" + sds + "\" style=\"width:135px\" onkeydown=\"console.log('from onkeydown : ' + event.keyCode);\" onchange=\"console.log('from onchange : ' + this.value);\">"
                );
            }
            if (!F.empty(endTimeValue)) {
                try {
                    d = sdf.parse(endTimeValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                eds = dateBoxFormat.format(d);
            }
            if (!F.empty(endTimeName)) {
                buffer.append(
                        "到&nbsp<input id=\"" + endTimeName + "\" name=\"" + endTimeName + "\"  type=\"text\" class=\"easyui-datetimebox myEndTimeClass\" data-options=\"required:" +
                                required  + "\"    value=\"" + eds + "\" style=\"width:135px\">\n"
                );
            }
            buffer.append("</div>\n" +
                    "                            <script type=\"text/javascript\">\n" +
                    "                                //监听选择的radio\n" +
                    "                                function myRadioChange(myRadio) {\n" +
                    "                                     var num, type, value = myRadio.value;\n" +
                    "                                     switch (value) {\n" +
                    "                                         case \"0\": //今日\n" +
                    "                                             num = 0;\n" +
                    "                                             type = 'd';\n" +
                    "                                             break;\n" +
                    "                                         case \"1\"://近7日\n" +
                    "                                             num = -6;\n" +
                    "                                             type = 'd';\n" +
                    "                                             break;\n" +
                    "                                         case \"2\"://近30日\n" +
                    "                                             num = -29;\n" +
                    "                                             type = 'd';\n" +
                    "                                             break;\n" +
                    "                                         case \"3\"://前三个月\n" +
                    "                                            num = -2;\n" +
                    "                                             type = 'M';\n" +
                    "                                     }\n");
            if (!F.empty(startTimeName)) {
                buffer.append(
                        "                                    $('.myStartTimeClass').datetimebox('setValue', moment().add(num, type).format('YYYY-MM-DD'));    // set datetimebox value\n"
                );
            }
            if (!F.empty(endTimeName)) {
                buffer.append(
                        "                                    $('.myEndTimeClass').datetimebox('setValue', moment().format('YYYY-MM-DD HH:mm:ss'));    // set datetimebox value\n"
                );
            }
            buffer.append(
                    "                                }\n"
            );
            String minDateStr;
            if (!F.empty(minDate)) {
                try {
                    d = sdf.parse(minDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                minDateStr = dateBoxFormat.format(d);
            } else {
                minDateStr = "1995-10-09 00:00:00";
            }
                buffer.append("" +
                        "$('.easyui-datetimebox').datetimebox({\n" +
                        "        formatter:function(date){\n" +
                        "           var minDate = moment(\"" + minDateStr + "\").toDate();\n" +
                        "       if( minDate <= date){\n" +
                        "              dt =  date;\n" +
                        "           }else{\n" +
                        "               alert(\"最小日期:" + minDateStr + "，请重新选择！\");\n" +
                        "               dt = minDate;\n" +
                        "           }\n" +
                        "return dt.Format('" + format + "');" +
                        "        }\n" +
                        "});\n");
            buffer.append(
                    "                            </script>");
            out.print(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public String getStartTimeName() {
        return startTimeName;
    }

    public void setStartTimeName(String startTimeName) {
        this.startTimeName = startTimeName;
    }

    public String getEndTimeName() {
        return endTimeName;
    }

    public void setEndTimeName(String endTimeName) {
        this.endTimeName = endTimeName;
    }

    public String getStartTimeValue() {
        return startTimeValue;
    }

    public void setStartTimeValue(String startTimeValue) {
        this.startTimeValue = startTimeValue;
    }

    public String getEndTimeValue() {
        return endTimeValue;
    }

    public void setEndTimeValue(String endTimeValue) {
        this.endTimeValue = endTimeValue;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
