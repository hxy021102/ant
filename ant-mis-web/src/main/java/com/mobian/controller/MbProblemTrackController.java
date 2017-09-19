package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.pageModel.*;
import com.mobian.service.MbProblemTrackServiceI;
import com.mobian.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * MbProblemTrack管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbProblemTrackController")
public class MbProblemTrackController extends BaseController {

    @Autowired
    private MbProblemTrackServiceI mbProblemTrackService;
    @Autowired
    private UserServiceI userService;


    /**
     * 跳转到MbProblemTrack管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mbproblemtrack/mbProblemTrack";
    }

    /**
     * 跳转到MbProblemTrackItem管理页面
     *
     * @return
     */
    @RequestMapping("/myProblemManager")
    public String myProblemManager(HttpServletRequest request) {
        return "/mbproblemtrack/myProblem";
    }

    /**
     * 获取MbProblemTrack数据表格
     *
     * @param
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbProblemTrack mbProblemTrack, PageHelper ph) {
        return mbProblemTrackService.dataGridWithSetName(mbProblemTrack, ph);
    }

    /**
     * 获取该我处理的订单问题数据表格
     *
     * @param mbProblemTrack
     * @param ph
     * @param session
     * @return
     */
    @RequestMapping("/myOrderProblemDataGrid")
    @ResponseBody
    public DataGrid myOrderProblemDataGrid(MbProblemTrack mbProblemTrack, PageHelper ph, HttpSession session) {
        return mbProblemTrackService.orderProblemDataGrid(mbProblemTrack, ph, session);
    }

    /**
     * 获取MbProblemTrack数据表格excel
     *
     * @param
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(MbProblemTrack mbProblemTrack, PageHelper ph, String downloadFields, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        DataGrid dg = dataGrid(mbProblemTrack, ph);
        downloadFields = downloadFields.replace("&quot;", "\"");
        downloadFields = downloadFields.substring(1, downloadFields.length() - 1);
        List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbProblemTrack页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, Integer id) {
        MbProblemTrack mbProblemTrack = new MbProblemTrack();
        request.setAttribute("problemOrderId", id);
        return "/mbproblemtrack/mbProblemTrackAdd";
    }

    /**
     * 添加MbProblemTrack
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbProblemTrack mbProblemTrack) {
        Json j = new Json();
        mbProblemTrackService.add(mbProblemTrack);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到MbProblemTrack查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbProblemTrack mbProblemTrack = mbProblemTrackService.get(id);
        if (mbProblemTrack.getOwnerId() != null) {
            User user = userService.getFromCache(mbProblemTrack.getOwnerId());
            if (user.getName() != null) {
                mbProblemTrack.setOwnerName(user.getName());
            }
        }
        request.setAttribute("mbProblemTrack", mbProblemTrack);
        return "/mbproblemtrack/dealOrderProblem";
    }

    /**
     * 跳转到MbProblemTrack修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbProblemTrack mbProblemTrack = mbProblemTrackService.get(id);
        request.setAttribute("mbProblemTrack", mbProblemTrack);
        return "/mbproblemtrack/mbProblemTrackEdit";
    }

    /**
     * 修改MbProblemTrack
     *
     * @param mbProblemTrack
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbProblemTrack mbProblemTrack) {
        Json j = new Json();
        mbProblemTrackService.edit(mbProblemTrack);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbProblemTrack
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbProblemTrackService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

}
