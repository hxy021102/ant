package com.mobian.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.jar.JarOutputStream;

/**
 * Created by w9777 on 2018/1/11.
 */
public class TimeRangePicker extends TagSupport {
    private String startTimeName;//开始时间名
    private String endTimeName;//结束时间名
    private String startTimeValue;//开始时间
    private String endTimeValue;//结束时间
    private String minDate;//最小日期
    private Integer timeLimit;//起止时间最大间隔,单位天
    private boolean required;
    private String format;

    @SuppressWarnings("rawtypes")
    @Override
    public int doStartTag() throws JspException {
        JspWriter out =  pageContext.getOut();
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("<script type=\"text/javascript\">\n" +
                    "    $(document).ready(function (){\n" +
                    "          //时间插件\n" +
                    "          $('#reportrange span').html(moment().subtract('hours', 1).format('YYYY-MM-DD HH:mm:ss') + ' - ' + moment().format('YYYY-MM-DD HH:mm:ss'));\n" +
                    "          $('#reportrange').daterangepicker(\n" +
                    "              {\n" +
                    "                // startDate: moment().startOf('day'),\n" +
                    "                //endDate: moment(),\n" +
                    "                //minDate: '01/01/2012',  //最小时间\n" +
                    "                maxDate : moment(), //最大时间\n" +
                    "                dateLimit : {\n" +
                    "                  days : 30\n" +
                    "                }, //起止时间的最大间隔\n" +
                    "                showDropdowns : true,\n" +
                    "                showWeekNumbers : false, //是否显示第几周\n" +
                    "                timePicker : true, //是否显示小时和分钟\n" +
                    "                timePickerIncrement : 60, //时间的增量，单位为分钟\n" +
                    "                timePicker12Hour : false, //是否使用12小时制来显示时间\n" +
                    "                ranges : {\n" +
                    "                  //'最近1小时': [moment().subtract('hours',1), moment()],\n" +
                    "                  '今日': [moment().startOf('day'), moment()],\n" +
                    "                  '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],\n" +
                    "                  '最近7日': [moment().subtract('days', 6), moment()],\n" +
                    "                  '最近30日': [moment().subtract('days', 29), moment()]\n" +
                    "                },\n" +
                    "                opens : 'right', //日期选择框的弹出位置\n" +
                    "                buttonClasses : [ 'btn btn-default' ],\n" +
                    "                applyClass : 'btn-small btn-primary blue',\n" +
                    "                cancelClass : 'btn-small',\n" +
                    "                format : 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式\n" +
                    "                separator : ' to ',\n" +
                    "                locale : {\n" +
                    "                  applyLabel : '确定',\n" +
                    "                  cancelLabel : '取消',\n" +
                    "                  fromLabel : '起始时间',\n" +
                    "                  toLabel : '结束时间',\n" +
                    "                  customRangeLabel : '自定义',\n" +
                    "                  daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],\n" +
                    "                  monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',\n" +
                    "                      '七月', '八月', '九月', '十月', '十一月', '十二月' ],\n" +
                    "                  firstDay : 1\n" +
                    "                }\n" +
                    "              }, function(start, end, label) {//格式化日期显示框\n" +
                    "                $('#reportrange span').html(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));\n" +
                    "              });\n" +
                    "      //设置日期菜单被选项 --开始--\n" +
                    "     /*\n" +
                    "         var dateOption ;\n" +
                    "         if(\"${riqi}\"=='day') {\n" +
                    "            dateOption = \"今日\";\n" +
                    "         }else if(\"${riqi}\"=='yday') {\n" +
                    "            dateOption = \"昨日\";\n" +
                    "         }else if(\"${riqi}\"=='week'){\n" +
                    "            dateOption =\"最近7日\";\n" +
                    "         }else if(\"${riqi}\"=='month'){\n" +
                    "            dateOption =\"最近30日\";\n" +
                    "         }else if(\"${riqi}\"=='year'){\n" +
                    "            dateOption =\"最近一年\";\n" +
                    "         }else{\n" +
                    "            dateOption = \"自定义\";\n" +
                    "         }\n" +
                    "          $(\".daterangepicker\").find(\"li\").each(function (){\n" +
                    "            if($(this).hasClass(\"active\")){\n" +
                    "              $(this).removeClass(\"active\");\n" +
                    "            }\n" +
                    "            if(dateOption==$(this).html()){\n" +
                    "              $(this).addClass(\"active\");\n" +
                    "            }\n" +
                    "         });*/\n" +
                    "            //设置日期菜单被选项 --结束--\n" +
                    "    })\n" +
                    "</script>");
            buffer.append(
                    "<div class=\"page-content\">\n" +
                    "      <!-- BEGIN PAGE CONTAINER-->\n" +
                    "  <div class=\"container-fluid\">\n" +
                    "    <div class=\"row-fluid\" style=\"margin-top:5px\">\n" +
                    "      <div class=\"span4\">\n" +
                    "        <div class=\"control-group\">\n" +
                    "          <label class=\"control-label\">\n" +
                    "            日期：\n" +
                    "          </label>\n" +
                    "        <div class=\"controls\">\n" +
                    "          <div id=\"reportrange\" class=\"pull-left dateRange\" style=\"width:350px\">\n" +
                    "            <i class=\"glyphicon glyphicon-calendar fa fa-calendar\"></i>\n" +
                    "            <span id=\"searchDateRange\"></span>\n" +
                    "            <b class=\"caret\"></b>\n" +
                    "          </div>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</div>");
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

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
