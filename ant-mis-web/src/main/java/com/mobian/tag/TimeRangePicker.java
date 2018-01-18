package com.mobian.tag;

import com.mobian.absx.F;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Date;

/**
 * Created by w9777 on 2018/1/11.
 * 该类提供jb:timeRangePicker标签的类支持
 * 具有以下限制:
 * 1.最大时间为当前时间
 * 2.右时间一定会大于左时间,左时间一定会小于右时间
 * 功能:时间段摄取组件
 * 依赖:前端moment.js,easyUI框架
 */
public class TimeRangePicker extends TagSupport {
    private String leftTimeName;//开始时间名,必要
    private String rightTimeName;//结束时间名,必要
    private String leftTimeValue;//开始时间,可选,默认空
    private String rightTimeValue;//结束时间,可选,默认空
    private String minDate;//最小日期,可选
    private String timeLimit;//起止时间最大间隔,单位天,默认180天
    private Boolean required;//是否必填,true or false,默认false
    private String format;//时间格式,必填,默认yyyy-MM-dd HH:mm:ss
    private Boolean onlyDate;

    @SuppressWarnings("rawtypes")
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            StringBuffer buffer = new StringBuffer();
            Date d = null;
            if (F.empty(required)) required = false;
//            if (F.empty(showSeconds)) showSeconds = false;
            if (F.empty(timeLimit)) timeLimit = "180";
            Integer timeLimitInteger = Integer.parseInt(timeLimit);
            buffer.append("                            查询时间跨度: &nbsp&nbsp&nbsp&nbsp\n" +
                    "                            <input type=\"radio\" name=\"timeRangePickerTagRadio\" value=\"0\" onclick=\"myRadioChange(this)\">今天&nbsp\n");
            if (timeLimitInteger >= 7) {
                buffer.append(
                    "                            <input type=\"radio\"  name=\"timeRangePickerTagRadio\"  value=\"1\" onclick=\"myRadioChange(this)\">近7天&nbsp\n");
            }
            if (timeLimitInteger >= 30) {
                buffer.append(
                    "                            <input type=\"radio\" name=\"timeRangePickerTagRadio\"  value=\"2\" onclick=\"myRadioChange(this)\">近30天&nbsp\n");
            }
            if (timeLimitInteger >= 90 ) {
                buffer.append(
                    "                            <input type=\"radio\" name=\"timeRangePickerTagRadio\"  value=\"3\" onclick=\"myRadioChange(this)\">近3个月\n");
            }
                buffer.append(
                    "                            <br>从&nbsp<input class=\"span2 leftTimeClass easyui-validatebox\" data-options=\"required:" + required +"\" id=\""+ leftTimeName +"\" name=\"" + leftTimeName + "\" type=\"text\" onclick=\"WdatePicker({\n" +
                    "                            dateFmt:'" + format + "',\n" +
                    "                            minDate:'#F{$dp.$D(\\'" + leftTimeName + "\\',{d:-" + timeLimit + "})");
            if (!F.empty(minDate)) {
                buffer.append("||\\'" + minDate + "\\'");
            }
             buffer.append(
                    "}',\n" +
                    "                            maxDate:'#F{$dp.$D(\\'" + leftTimeName +"\\')||\\'%y-%M-%d\\'}'\n" +
                    "                            })\"   maxlength=\"0\" value=\"" + (leftTimeValue == null ? "" : leftTimeValue) + "\" style=\"width: 135px\"/>\n" +
                    "                            到&nbsp<input class=\"span2 rightTimeClass easyui-validatebox\" data-options=\"required:" + required + "\" id=\"" + rightTimeName + "\" name=\"" + rightTimeName + "\" type=\"text\"  onclick=\"WdatePicker({\n" +
                    "                            dateFmt:'" + format + "',\n" +
                    "                            minDate:'#F{$dp.$D(\\'" + leftTimeName + "\\')}',\n" +
                    "                            maxDate:'%y-%M-%d'\n" +
                    "                            })\"   maxlength=\"0\" value=\" " + (rightTimeValue == null ? "" : rightTimeValue) + "\"  style=\"width: 135px\"/>\n" +
                    "                        </td>\n" +
                    "                        <script type=\"text/javascript\">\n" +
                    "                            function myRadioChange(myRadio) {\n" +
                    "                                var num, type, value = myRadio.value;\n" +
                    "                                switch (value) {\n" +
                    "                                    case \"0\": //今日\n" +
                    "                                        num = 0;\n" +
                    "                                        type = 'd';\n" +
                    "                                        break;\n" );
            if (timeLimitInteger >= 7) {
                buffer.append(
                    "                                    case \"1\"://近7日\n" +
                    "                                        num = -6;\n" +
                    "                                        type = 'd';\n" +
                    "                                        break;\n");
            }
            if (timeLimitInteger >= 30 ) {
                buffer.append(
                    "                                    case \"2\"://近30日\n" +
                    "                                        num = -29;\n" +
                    "                                        type = 'd';\n" +
                    "                                        break;\n");
            }
            if (timeLimitInteger >= 90) {
                buffer.append(
                    "                                    case \"3\"://前三个月\n" +
                    "                                        num = -2;\n" +
                    "                                        type = 'M';\n");
            }
            buffer.append(
                    "                                };\n" +
                    "                            $('.leftTimeClass').val(moment('00:00:00','HH:mm:ss').add(num, type).format('" + format.replaceAll("y","Y").replaceAll("d", "D") + "'));    // set datetimebox value\n" +
                    "                                $('.rightTimeClass').val(moment().format('" + format.replaceAll("y","Y").replaceAll("d", "D") +"'));    // set datetimebox value\n" +
                    "                            }\n" +
                    "</script>\n");
            out.print(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public String getLeftTimeName() {
        return leftTimeName;
    }

    public void setLeftTimeName(String leftTimeName) {
        this.leftTimeName = leftTimeName;
    }

    public String getRightTimeName() {
        return rightTimeName;
    }

    public void setRightTimeName(String rightTimeName) {
        this.rightTimeName = rightTimeName;
    }

    public String getLeftTimeValue() {
        return leftTimeValue;
    }

    public void setLeftTimeValue(String leftTimeValue) {
        this.leftTimeValue = leftTimeValue;
    }

    public String getRightTimeValue() {
        return rightTimeValue;
    }

    public void setRightTimeValue(String rightTimeValue) {
        this.rightTimeValue = rightTimeValue;
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



    public Boolean getOnlyDate() {
        return onlyDate;
    }

    public void setOnlyDate(Boolean onlyDate) {
        this.onlyDate = onlyDate;
    }
}
