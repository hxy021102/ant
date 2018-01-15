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
 */
public class TimeRangePicker extends TagSupport {
    private String startTimeName;//开始时间名
    private String endTimeName;//结束时间名
    private String startTimeValue;//开始时间
    private String endTimeValue;//结束时间
    private String minDate;//最小日期
    private String timeLimit;//起止时间最大间隔,单位天
    private Boolean required;
    private String format;//时间格式yyyy-MM-dd hh:mm:ss
    private Boolean showSeconds;

    @SuppressWarnings("rawtypes")
    @Override
    public int doStartTag() throws JspException {
        JspWriter out =  pageContext.getOut();
        try {
            StringBuffer buffer = new StringBuffer();
            String sds = "";
            String eds = "";
            Date d = null;
            if (F.empty(required)) required = false ;
            if (F.empty(showSeconds)) showSeconds=false;
            if (F.empty(format)) format = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat sdf=new SimpleDateFormat(format.replace("Y","y")
                    .replace("D", "d")
                    .replace("H", "h"));
            SimpleDateFormat dateBoxFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            buffer.append("<script type=\"text/javascript\">\n" +
                    "                                //输出指定格式的时间格式\n" +
                    "                                Date.prototype.Format = function (fmt) { //author: meizz\n" +
                    "                                      fmt = fmt.replace(/H/g,'h');\n" +
                    "                                      fmt = fmt.replace(/D/g,'d');\n" +
                    "                                    var o = {\n" +
                    "                                        \"M+\": this.getMonth() + 1, //月份\n" +
                    "                                        \"d+\": this.getDate(), //日\n" +
                    "                                        \"h+\": this.getHours(), //小时\n" +
                    "                                        \"m+\": this.getMinutes(), //分\n" +
                    "                                        \"s+\": this.getSeconds(), //秒\n" +
                    "                                        \"q+\": Math.floor((this.getMonth() + 3) / 3), //季度\n" +
                    "                                        \"S+\": this.getMilliseconds() //毫秒\n" +
                    "                                    };\n" +
                    "                                    if (/(Y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + \"\").substr(4 - RegExp.$1.length));\n" +
                    "                                    for (var k in o)\n" +
                    "                                        if (new RegExp(\"(\" + k + \")\").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : ((\"00\" + o[k]).substr((\"\" + o[k]).length)));\n" +
                    "                                    return fmt;\n" +
                    "                                }\n" +
                    "                            </script>");
            buffer.append("              <div>\n");
            if (!F.empty(startTimeName) && !F.empty(endTimeName)) {
                buffer.append(
                        "                                查询时间跨度: &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp\n" +
                        "                                <input type=\"radio\" name=\"timerange\" value=\"0\" onclick=\"myRadioChange(this)\">今天&nbsp&nbsp\n" +
                        "                                <input type=\"radio\" name=\"timerange\" value=\"1\" onclick=\"myRadioChange(this)\">近7天&nbsp&nbsp\n" +
                        "                                <input type=\"radio\" name=\"timerange\" value=\"2\" onclick=\"myRadioChange(this)\" >近30天&nbsp&nbsp\n" +
                        "                                <input type=\"radio\" name=\"timerange\" value=\"3\" onclick=\"myRadioChange(this)\">前三个月<br>\n"
                );
            }
            if (!F.empty(startTimeValue)) {
                try {
                    d = sdf.parse(startTimeValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
               sds=dateBoxFormat.format(d);
            }
            if (!F.empty(startTimeName)) {
                buffer.append("<div class=\"timeDivClass\" tabindex=\"1\" >" +
                        "从&nbsp&nbsp<input id=\"" + startTimeName + "\" name=\"" + startTimeName + "\" type=\"text\" class=\"easyui-datetimebox myStartTimeClass\" data-options=\"required:"+
                        required  +"\"  onchange = \"formatDate()\"value=\"" + sds  +"\" style=\"width:140px\">\n"
                );
            }
            if (!F.empty(endTimeValue)) {
                try {
                    d = sdf.parse(endTimeValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                eds=dateBoxFormat.format(d);
            }
            if (!F.empty(endTimeName)) {
                buffer.append(
                        "到&nbsp&nbsp<input id=\"" + endTimeName + "\" name=\"" + endTimeName + "\"  type=\"text\" class=\"easyui-datetimebox myEndTimeClass\" data-options=\"required:" +
                        required + "\"  onchange = \"formatDate()\"  value=\"" + eds  +"\" style=\"width:140px\">\n" +
                        "</div>"
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
                    "                                     }\n" );
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
            String minDateStr = "";
            if (!F.empty(minDate)) {
                try {
                    d = sdf.parse(minDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                minDateStr = dateBoxFormat.format(d);
            }else {
                minDateStr = "1995-10-09 00:00:00";
            }
            if (!F.empty(timeLimit) && !F.empty(startTimeName) && !F.empty(endTimeName)) {

                buffer.append("$('.timeDivClass').blur(");
                buffer.append("function () {\n" +
                        "var myStartTime =  new Date($('.myStartTimeClass').datetimebox(\"getValue\"));\n" +
                        "var myEndTime = new Date($('.myEndTimeClass').datetimebox(\"getValue\"));\n" +
                        "alert(myEndTime.getTime() - myStartTime.getTime());\n" +
                        "if ((myEndTime.getTime() - myStartTime.getTime()) > " + Integer.parseInt(timeLimit)* 24 * 3600 * 1000 + ") {\n" +
                        "       alert(\"时间间隔不得大于" +  timeLimit + "天\");\n" +
                        "   }\n"+
                        "});\n");
            }
                buffer.append("$('.easyui-datetimebox').datetimebox({\n" +
                        "        formatter:function(date){\n" +
                        "           var minDate = new Date(\"" + minDateStr + "\"); " +
                        "           if( minDate <= date){\n" +
                        "               return date.Format('" + format + "');\n" +
                        "           }else{\n" +
                        "               alert(\"最小日期:" + minDateStr + "，请重新选择！\");\n" +
                        "               return minDate.Format('" + format + "');\n" +
                        "           }\n" +
                        "        },\n" +
                        "       showSeconds:" + showSeconds +
                        "});\n");
            buffer.append(
            "                            </script>");
            out.print(buffer.toString());
        }catch (IOException e) {
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

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
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

    public Boolean getShowSeconds() {
        return showSeconds;
    }

    public void setShowSeconds(Boolean showSeconds) {
        this.showSeconds = showSeconds;
    }
}
