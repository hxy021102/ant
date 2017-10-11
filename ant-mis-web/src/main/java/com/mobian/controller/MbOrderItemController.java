package com.mobian.controller;

import com.mobian.absx.F;
import com.mobian.concurrent.ThreadCache;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.service.impl.RedisUserServiceImpl;
import com.mobian.util.ConvertNameUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MbOrderItem管理控制器
 *
 * @author John
 */
@Controller
@RequestMapping("/mbOrderItemController")
public class MbOrderItemController extends BaseController {

    @Autowired
    private MbOrderItemServiceI mbOrderItemService;

    @Autowired
    private MbItemServiceI mbItemService;

    @Autowired
    private RedisUserServiceImpl redisUserService;

    @Autowired
    private MbOrderServiceI mbOrderService;
    @Autowired
    private MbShopServiceI mbShopService;
    @Autowired
    private UserServiceI userService;
    @Autowired
    private MbOrderRefundItemServiceI mbOrderRefundItemService;
    @Autowired
    private MbOrderLogServiceI mbOrderLogService;
    @Autowired
    private MbOrderCallbackItemServiceI mbOrderCallbackItemService;


    /**
     * 跳转到MbOrderItem管理页面
     *
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) {
        return "/mborderitem/mbOrderItem";
    }

    /**
     * 获取MbOrderItem数据表格
     *
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(MbOrderItem mbOrderItem, PageHelper ph) {
        return mbOrderItemService.dataGridAndStock(mbOrderItem, ph);
    }

    @RequestMapping("/download")
    public void downloadComplex(MbOrderItem mbOrderItem, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
        PageHelper ph = new PageHelper();
        ph.setRows(10000);
        ph.setHiddenTotal(true);
        DataGrid dg = mbOrderItemService.dataGrid(mbOrderItem, ph);
        List<MbOrderItemExport> mbOrderItemExports = new ArrayList<MbOrderItemExport>();
        List<MbOrderItem> itemExports = dg.getRows();
        ThreadCache mbItemCache = new ThreadCache(MbItem.class) {
            @Override
            protected Object handle(Object key) {
                return mbItemService.getFromCache((Integer) key);
            }
        };
        ThreadCache mbOrderCache = new ThreadCache(MbOrder.class) {
            @Override
            protected Object handle(Object key) {
                return mbOrderService.get((Integer) key);
            }
        };
        ThreadCache userCache = new ThreadCache(User.class) {
            @Override
            protected Object handle(Object key) {
                return userService.getFromCache((String) key);
            }
        };
        ThreadCache shopCache = new ThreadCache(MbShop.class) {
            @Override
            protected Object handle(Object key) {
                return mbShopService.getFromCache((Integer) key);
            }
        };
        ThreadCache callbackItemCache = new ThreadCache(ArrayList.class) {
            @Override
            protected Object handle(Object key) {
                MbOrderCallbackItem mbOrderCallbackItemA = new MbOrderCallbackItem();
                mbOrderCallbackItemA.setOrderId((Integer)key);
                mbOrderCallbackItemA.setIsdeleted(false);
                List<MbOrderCallbackItem> mbOrderCallbackItems = mbOrderCallbackItemService.query(mbOrderCallbackItemA);
                return mbOrderCallbackItems;
            }
        };

        final Map<String, String> statusMap = new HashMap<String, String>();
        statusMap.put("OD12","LT005");
        statusMap.put("OD15","LT015");
        statusMap.put("OD20","LT002");
        statusMap.put("OD31","LT006");
        statusMap.put("OD30","LT007");
        statusMap.put("OD32","LT020");
        statusMap.put("OD35","LT008");
        statusMap.put("OD40","LT009");

        ThreadCache orderLogCache = new ThreadCache(MbOrderLog.class) {
            @Override
            protected Object handle(Object key) {
                String[] keys = key.toString().split("[|]");
                String logType = statusMap.get(keys[1]);
                if (logType == null) return null;
                return mbOrderLogService.getByIdAndType(Integer.parseInt(keys[0]), logType);
            }
        };
        //mbOrderLogService
        Integer total = 0, backTotal = 0,totalAmount = 0;
        NumberFormat nf = new DecimalFormat("#,###.##");
        try {
            for (MbOrderItem itemExport : itemExports) {
                MbOrder mbOrder = mbOrderCache.getValue(itemExport.getOrderId());
                if (mbOrder == null || mbOrder.getId() == null || "OD01_OD31_OD32".indexOf(mbOrder.getStatus())>-1) continue;
                MbOrderItemExport mbOrderItemExport = new MbOrderItemExport();
                BeanUtils.copyProperties(itemExport, mbOrderItemExport);
                mbOrderItemExport.setMarketPriceFormat(nf.format(mbOrderItemExport.getMarketPrice() / 100f));
                mbOrderItemExport.setBuyPriceFormat(nf.format(mbOrderItemExport.getBuyPrice()/100f));
                mbOrderItemExports.add(mbOrderItemExport);
                mbOrderItemExport.setShopId(mbOrder.getShopId());
                mbOrderItemExport.setShopName(mbOrder.getShopName());
                mbOrderItemExport.setDeliveryDriver(mbOrder.getDeliveryDriver());
                mbOrderItemExport.setDeliveryTime(mbOrder.getDeliveryTime());
                mbOrderItemExport.setUserRemark(mbOrder.getUserRemark());
                mbOrderItemExport.setContactPhone(mbOrder.getContactPhone());
                if (!F.empty(mbOrder.getDeliveryDriver())) {
                    User user = userCache.getValue(mbOrder.getDeliveryDriver());
                    if (user != null)
                        mbOrderItemExport.setDeliveryDriverName(user.getNickname());
                }

                if (!F.empty(mbOrderItemExport.getShopId())) {
                    MbShop mbShop = shopCache.getValue(mbOrderItemExport.getShopId());
                    if (mbShop != null) {
                        mbOrderItemExport.setShopTypeName(mbShop.getShopTypeName());
                    }
                }

                MbItem mbItem = mbItemCache.getValue(itemExport.getItemId());
                if (mbItem != null) {
                    mbOrderItemExport.setItemCode(mbItem.getCode());
                    mbOrderItemExport.setItemName(mbItem.getName());
                }
                mbOrderItemExport.setTotalRefundAmount(mbOrder.getTotalRefundAmount());
                mbOrderItemExport.setStatus(ConvertNameUtil.getString(mbOrder.getStatus()));
                MbOrderRefundItem mbOrderRefundItem = new MbOrderRefundItem();
                mbOrderRefundItem.setItemId(itemExport.getItemId());
                mbOrderRefundItem.setOrderId(itemExport.getOrderId());
                mbOrderItemExport.setPayStatus(ConvertNameUtil.getString(mbOrder.getPayStatus()));
                mbOrderItemExport.setDeliveryAddress(mbOrder.getDeliveryAddress());
                List<MbOrderRefundItem> mbOrderRefundItemList = mbOrderRefundItemService.query(mbOrderRefundItem);
                if (CollectionUtils.isNotEmpty(mbOrderRefundItemList)) {
                    MbOrderRefundItem refundItem = mbOrderRefundItemList.get(0);
                    mbOrderItemExport.setRefundQuantity(refundItem.getQuantity());
                    mbOrderItemExport.setRefundType(ConvertNameUtil.getString(refundItem.getType()));
                }
                mbOrderItemExport.setChannel(mbOrder.getAddLoginId() == null ? "公众号" : "客服");
                MbOrderLog mbOrderLog = orderLogCache.getValue(mbOrder.getId()+"|"+mbOrder.getStatus());
                if (mbOrderLog != null)
                    mbOrderItemExport.setNodeTime(mbOrderLog.getAddtime());
                if (itemExport.getQuantity() != null) {
                    total += itemExport.getQuantity();
                    totalAmount += itemExport.getQuantity() * itemExport.getBuyPrice();
                }
                if(mbOrderItemExport.getRefundQuantity() != null) {
                    backTotal += mbOrderItemExport.getRefundQuantity();
                }

                List<MbOrderCallbackItem> mbOrderCallbackItemList = callbackItemCache.getValue(mbOrder.getId());
                if (CollectionUtils.isNotEmpty(mbOrderCallbackItemList)) {
                    mbOrderItemExport.setExtend(new HashMap<String, Integer>());
                    for (MbOrderCallbackItem mbOrderCallbackItem : mbOrderCallbackItemList) {
                        mbOrderItemExport.getExtend().put(mbOrderCallbackItem.getItemId()+"",mbOrderCallbackItem.getQuantity());
                    }
                    mbOrderCallbackItemList.clear();
                }

            }
        } finally {
            ThreadCache.clear();
        }
        //合计
        if(!CollectionUtils.isEmpty(mbOrderItemExports)){
            MbOrderItemExport mbOrderItemExport = new MbOrderItemExport();
            mbOrderItemExport.setShopName("合计");
            mbOrderItemExport.setQuantity(total);
            mbOrderItemExport.setBuyPriceFormat(nf.format(totalAmount / 100f));
            mbOrderItemExport.setRefundQuantity(backTotal);
            mbOrderItemExports.add(mbOrderItemExport);
        }
        dg.setRows(mbOrderItemExports);
        List<Colum> colums = new ArrayList<Colum>();
        Colum colum = new Colum();
        colum.setField("shopName");
        colum.setTitle("门店名称");
        colums.add(colum);
        colum = new Colum();
        colum.setField("shopId");
        colum.setTitle("门店ID");
        colums.add(colum);
        colum = new Colum();
        colum.setField("shopTypeName");
        colum.setTitle("门店类型");
        colums.add(colum);
        colum = new Colum();
        colum.setField("orderId");
        colum.setTitle("订单ID");
        colums.add(colum);
        colum = new Colum();
        colum.setField("status");
        colum.setTitle("状态");
        colums.add(colum);

        colum = new Colum();
        colum.setField("updatetime");
        colum.setTitle("下单时间");
        colums.add(colum);

        colum = new Colum();
        colum.setField("deliveryTime");
        colum.setTitle("出库时间");
        colums.add(colum);

        colum = new Colum();
        colum.setField("nodeTime");
        colum.setTitle("节点时间");
        colums.add(colum);

        colum = new Colum();
        colum.setField("itemId");
        colum.setTitle("商品Id");
        colums.add(colum);
        colum = new Colum();
        colum.setField("itemCode");
        colum.setTitle("商品编码");
        colums.add(colum);
        colum = new Colum();
        colum.setField("itemName");
        colum.setTitle("商品名称");
        colums.add(colum);
        colum = new Colum();
        colum.setField("marketPriceFormat");
        colum.setTitle("市场价");
        colums.add(colum);
        colum = new Colum();
        colum.setField("buyPriceFormat");
        colum.setTitle("成交价");
        colums.add(colum);
        colum = new Colum();
        colum.setField("quantity");
        colum.setTitle("数量");
        colums.add(colum);
        colum = new Colum();
        colum.setField("refundQuantity");
        colum.setTitle("退回数量");
        colums.add(colum);
        colum = new Colum();
        colum.setField("refundType");
        colum.setTitle("退回类型");
        colums.add(colum);

        MbItem mbItem = new MbItem();
        mbItem.setIspack(true);
        List<MbItem> items = mbItemService.query(mbItem);
        for (MbItem item : items) {
            colum = new Colum();
            colum.setField(MAP_+item.getId());
            colum.setTitle(item.getName());
            colums.add(colum);
        }

        colum = new Colum();
        colum.setField("deliveryDriver");
        colum.setTitle("司机编码");
        colums.add(colum);
        colum = new Colum();
        colum.setField("deliveryDriverName");
        colum.setTitle("司机名称");
        colums.add(colum);
        colum = new Colum();
        colum.setField("payStatus");
        colum.setTitle("支付状态");
        colums.add(colum);
        colum = new Colum();
        colum.setField("deliveryAddress");
        colum.setTitle("地址");
        colums.add(colum);
        colum = new Colum();
        colum.setField("channel");
        colum.setTitle("渠道");
        colums.add(colum);
        colum = new Colum();
        colum.setField("contactPhone");
        colum.setTitle("联系电话");
        colums.add(colum);
        colum = new Colum();
        colum.setField("userRemark");
        colum.setTitle("备注");
        colums.add(colum);
        downloadTable(colums, dg, response);
    }

    /**
     * 跳转到添加MbOrderItem页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        MbOrderItem mbOrderItem = new MbOrderItem();
        return "/mborderitem/mbOrderItemAdd";
    }

    @RequestMapping("/addOffsetPage")
    public String addOffsetPage(HttpServletRequest request, Integer orderId) {
        request.setAttribute("orderId", orderId);
        return "/mborderitem/mbOrderItemAddOffset";
    }

    /**
     * 添加MbOrderItem
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(MbOrderItem mbOrderItem) {
        Json j = new Json();
        mbOrderItemService.add(mbOrderItem);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    @RequestMapping("/addOffset")
    @ResponseBody
    public Json addOffset(MbOrderItem mbOrderItem) {
        Json j = new Json();
        MbOrder mbOrder = new MbOrder();
        mbOrder.setId(mbOrderItem.getOrderId());
        mbOrderItemService.addOffSet(mbOrder, mbOrderItem.getItemId(), mbOrderItem.getQuantity());
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到MbOrderItem查看页面
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request, Integer id) {
        MbOrderItem mbOrderItem = mbOrderItemService.get(id);
        request.setAttribute("mbOrderItem", mbOrderItem);
        return "/mborderitem/mbOrderItemView";
    }

    /**
     * 跳转到MbOrderItem修改页面
     *
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Integer id) {
        MbOrderItem mbOrderItem = mbOrderItemService.get(id);
        request.setAttribute("mbOrderItem", mbOrderItem);
        return "/mborderitem/mbOrderItemEdit";
    }

    /**
     * 修改MbOrderItem
     *
     * @param mbOrderItem
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(MbOrderItem mbOrderItem) {
        Json j = new Json();
        mbOrderItemService.edit(mbOrderItem);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除MbOrderItem
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(Integer id) {
        Json j = new Json();
        mbOrderItemService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

}
