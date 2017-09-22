package com.mobian.service.impl;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.dao.MbShopCouponsDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TmbShopCoupons;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MbShopCouponsServiceImpl extends BaseServiceImpl<MbShopCoupons> implements MbShopCouponsServiceI {

    @Autowired
    private MbShopCouponsDaoI mbShopCouponsDao;
    @Autowired
    private MbCouponsServiceI mbCouponsService;
    @Autowired
    private MbBalanceServiceI mbBalanceService;
    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;
    @Autowired
    private MbCouponsItemServiceI mbCouponsItemService;
    @Autowired
    private MbPaymentItemServiceI mbPaymentItemService;
    @Autowired
    private MbShopCouponsLogServiceI mbShopCouponsLogService;
    @Autowired
    private MbShopServiceI mbShopService;

    @Override
    public DataGrid dataGrid(MbShopCoupons mbShopCoupons, PageHelper ph) {
        List<MbShopCoupons> ol = new ArrayList<MbShopCoupons>();
        String hql = " from TmbShopCoupons t ";
        DataGrid dg = dataGridQuery(hql, ph, mbShopCoupons, mbShopCouponsDao);
        @SuppressWarnings("unchecked")
        List<TmbShopCoupons> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbShopCoupons t : l) {
                MbShopCouponsView o = new MbShopCouponsView();
                BeanUtils.copyProperties(t, o);
                fillShopCouponsInfo(o);
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }

    public void fillShopCouponsInfo(MbShopCouponsView m) {
        fillCouponsInfo(m);
    }
    @Override
    public void fillCouponsInfo(MbShopCouponsView m) {
        MbCoupons coupons = mbCouponsService.get(m.getCouponsId());
        if (coupons != null) {
            m.setCouponsName(coupons.getName());
        }
    }

    @Override
    public MbShopCouponsView getShopCouponsView(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbShopCoupons t = mbShopCouponsDao.get("from TmbShopCoupons t  where t.id = :id", params);
        MbShopCouponsView o = new MbShopCouponsView();
        BeanUtils.copyProperties(t, o);
        fillShopCouponsInfo(o);
        return o;
    }
    protected String whereHql(MbShopCoupons mbShopCoupons, Map<String, Object> params) {
        String whereHql = "";
        if (mbShopCoupons != null) {
            whereHql += " where ( t.isdeleted = 0  ";
            if (!F.empty(mbShopCoupons.getIsdeleted())) {
                whereHql += " or t.isdeleted = :isdeleted )";
                params.put("isdeleted", mbShopCoupons.getIsdeleted());
            }else {
                whereHql +=" )";
            }
            if (!F.empty(mbShopCoupons.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbShopCoupons.getTenantId());
            }

            if (!F.empty(mbShopCoupons.getCouponsId())) {
                whereHql += " and t.couponsId = :couponsId";
                params.put("couponsId", mbShopCoupons.getCouponsId());
            }
            if (!F.empty(mbShopCoupons.getShopId())) {
                whereHql += " and t.shopId = :shopId";
                params.put("shopId", mbShopCoupons.getShopId());
            }
            if (!F.empty(mbShopCoupons.getQuantityTotal())) {
                whereHql += " and t.quantityTotal = :quantityTotal";
                params.put("quantityTotal", mbShopCoupons.getQuantityTotal());
            }
            if (!F.empty(mbShopCoupons.getQuantityUsed())) {
                whereHql += " and t.quantityUsed = :quantityUsed";
                params.put("quantityUsed", mbShopCoupons.getQuantityUsed());
            }
            if (!F.empty(mbShopCoupons.getStatus())) {
                whereHql += " and t.status = :status";
                params.put("status", mbShopCoupons.getStatus());
            }
            if (!F.empty(mbShopCoupons.getRemark())) {
                whereHql += " and t.remark = :remark";
                params.put("remark", mbShopCoupons.getRemark());
            }
            if (!F.empty(mbShopCoupons.getRemark())) {
                whereHql += " and t.loginId = :loginId";
                params.put("loginId", mbShopCoupons.getLoginId());
            }
        }
        return whereHql;
    }

    @Override
    public void add(MbShopCoupons mbShopCoupons) {
        TmbShopCoupons t = new TmbShopCoupons();
        BeanUtils.copyProperties(mbShopCoupons, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbShopCouponsDao.save(t);
        mbShopCoupons.setId(t.getId());
    }

    @Override
    public MbShopCoupons get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbShopCoupons t = mbShopCouponsDao.get("from TmbShopCoupons t  where t.id = :id", params);
        MbShopCoupons o = new MbShopCoupons();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public void edit(MbShopCoupons mbShopCoupons) {
        TmbShopCoupons t = mbShopCouponsDao.get(TmbShopCoupons.class, mbShopCoupons.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbShopCoupons, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbShopCouponsDao.executeHql("update TmbShopCoupons t set t.isdeleted = 1 where t.id = :id", params);
        //mbShopCouponsDao.delete(mbShopCouponsDao.get(TmbShopCoupons.class, id));
    }

    @Override
    public List<MbShopCoupons> listMbShopCoupons(MbShopCoupons mbShopCoupons) {
        List<MbShopCoupons> ol = new ArrayList<MbShopCoupons>();
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = " from TmbShopCoupons t ";
        String whereHql = whereHql(mbShopCoupons, params);
        String orderHql = " order by t.addtime ";
        List<TmbShopCoupons> l = mbShopCouponsDao.find(hql + whereHql + orderHql, params);

        if (CollectionUtils.isNotEmpty(l)) {
            for (TmbShopCoupons t : l
                    ) {
                MbShopCoupons o = new MbShopCoupons();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        return ol;
    }

    @Override
    public MbShopCoupons countSameShopCouponsTypeVoucher(MbShopCoupons mbShopCoupons) {
        //保证参数中包含门店ID和券ID
        if (F.empty(mbShopCoupons.getShopId()) || F.empty(mbShopCoupons.getCouponsId())) {
            return null;
        }
        MbCoupons mbCoupons = mbCouponsService.get(mbShopCoupons.getCouponsId());
        //1.券不是水票时进行的处理:return null
        if (!COUPONS_TYPE_1_VOUCHER.equals(mbCoupons.getType())) {
            return null;
        }
        //2.券是水票是进行的处理:统计相同券的数量
        //2.1填充相关数据
        MbShopCoupons shopCoupons = new MbShopCoupons();
        shopCoupons.setShopId(mbShopCoupons.getShopId());
        shopCoupons.setCouponsId(mbShopCoupons.getCouponsId());
        //2.2统计并填充数据
        List<MbShopCoupons> l = listMbShopCoupons(shopCoupons);
        shopCoupons.setQuantityUsed(0);
        shopCoupons.setQuantityTotal(0);
        if (CollectionUtils.isNotEmpty(l)) {
            for (MbShopCoupons s : l
                    ) {
                //2.2.1 门店券状态必须是有效才会进行统计
                if (s.getStatus() == null || SHOP_COUPONS_STATUS_ACTIVE.equals(s.getStatus())) {
                    shopCoupons.setQuantityTotal(shopCoupons.getQuantityTotal() + s.getQuantityTotal());
                    s.setQuantityUsed(s.getQuantityUsed() == null ? 0 : s.getQuantityUsed());
                    shopCoupons.setQuantityUsed(shopCoupons.getQuantityUsed() + s.getQuantityUsed());
                    shopCoupons.getShopCouponsList().add(s);
                }
            }
        }
        return shopCoupons;
    }

    @Override
    public List<MbShopCoupons> listActiveShopCouponsByShopId(Integer shopId) {
        List<MbShopCoupons> ol = new ArrayList<MbShopCoupons>();
        //搜索未删除的固定门店ID的且状态为有效或为空的门店优惠券
        String hql = " from TmbShopCoupons t ";
        String whereHql = "where t.isdeleted = 0 and t.shopId = '" + shopId + "' and t.status = '" + SHOP_COUPONS_STATUS_ACTIVE + "' order by t.addtime ";
        List<TmbShopCoupons> l = mbShopCouponsDao.find(hql + whereHql);
        if (CollectionUtils.isNotEmpty(l)) {
            for (TmbShopCoupons t : l
                    ) {
                MbShopCoupons o = new MbShopCoupons();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        return ol;
    }

    @Override
    public List<MbShopCoupons> listSameActiveShopCouponsTypeVoucherByShopId(Integer shopId) {
        List<MbShopCoupons> al = listActiveShopCouponsByShopId(shopId);
        List<MbShopCoupons> ol = new ArrayList<MbShopCoupons>();
        Set<Integer> couponsIdSet = new HashSet<Integer>();
        if (CollectionUtils.isNotEmpty(al)) {
            for (MbShopCoupons m : al
                    ) {
                if (couponsIdSet.add(m.getCouponsId())) {
                    ol.add(countSameShopCouponsTypeVoucher(m));
                }
            }
        }
        return ol;
    }

    @Override
    public MbShopCoupons getSameActiveVouhcerByShopIdAndItemId(Integer shopId, Integer itemId) {
        List<MbShopCoupons> asl = listSameActiveShopCouponsTypeVoucherByShopId(shopId);
        MbShopCoupons shopCoupons = new MbShopCoupons();
        if (CollectionUtils.isNotEmpty(asl)) {
            MbCouponsItem couponsItem = new MbCouponsItem();
            couponsItem.setItemId(itemId);
            for (MbShopCoupons m : asl) {
                couponsItem.setCouponsId(m.getCouponsId());
                List<MbCouponsItem> couponsItemList = mbCouponsItemService.listCouponsItem(couponsItem);
                if (CollectionUtils.isNotEmpty(couponsItemList)) {
                    shopCoupons.getShopCouponsList().addAll(m.getShopCouponsList());
                }
            }
        }
        shopCoupons.setQuantityUsed(0);
        shopCoupons.setQuantityTotal(0);
        if (shopCoupons != null && CollectionUtils.isNotEmpty(shopCoupons.getShopCouponsList())) {
            for (MbShopCoupons mbShopCoupons : shopCoupons.getShopCouponsList()) {
                shopCoupons.setQuantityUsed(shopCoupons.getQuantityUsed() + mbShopCoupons.getQuantityUsed());
                shopCoupons.setQuantityTotal(shopCoupons.getQuantityTotal() + mbShopCoupons.getQuantityTotal());
            }
        }
        return shopCoupons;
    }
    @Override
    public Map<String, MbShopCoupons> getShopCouponsMapByShopId(Integer shopId) {
        //判定是否为主门店,若不是则将主门店id赋值给shopId
        MbShop shop = mbShopService.get(shopId);
        if (shop != null) shopId = (shop.getParentId() == null || shop.getParentId() == -1) ? shopId : shop.getParentId();

        Map<String, MbShopCoupons> shopCouponsMap = new HashMap<String, MbShopCoupons>();
        List<MbShopCoupons> shopCoupons = listActiveShopCouponsByShopId(shopId);
        if (CollectionUtils.isNotEmpty(shopCoupons)) {
            for (MbShopCoupons m : shopCoupons) {
                MbCouponsItem couponsItem = new MbCouponsItem();
                couponsItem.setCouponsId(m.getCouponsId());
                List<MbCouponsItem> couponsItems = mbCouponsItemService.listCouponsItem(couponsItem);
                if (CollectionUtils.isEmpty(couponsItems)) continue;
                //TODO 只做了水票
                Integer integerId = couponsItems.get(0).getItemId();
                String itemId = integerId+"";
                if (!shopCouponsMap.containsKey(itemId)) {
                   MbShopCoupons mbShopCoupons =  getSameActiveVouhcerByShopIdAndItemId(shopId, integerId);
                   mbShopCoupons.setShopCouponsList(null);
                    shopCouponsMap.put(itemId, mbShopCoupons);
                }
            }
        }
        return shopCouponsMap;
    }

    @Override
    public void editShopCouponsAndAddPaymentItem(Integer shopId, Integer itemId, MbPaymentItem paymentItem, String loginId,Integer orderId) {

        //判定是否为主门店,若不是则将主门店id赋值给shopId
        MbShop shop = mbShopService.get(shopId);
        if (shop != null) shopId = (shop.getParentId() == null || shop.getParentId() == -1) ? shopId : shop.getParentId();


        int quantityRequired = paymentItem.getAmount();
        if (F.empty(quantityRequired)) return;
        MbShopCoupons shopCoupons = getSameActiveVouhcerByShopIdAndItemId(shopId, itemId);
        if (PAY_WAY_VOUCHER.equals(paymentItem.getPayWay())) {
            mbPaymentItemService.add(paymentItem);
            paymentItem.setAmount(0);
            Iterator<MbShopCoupons> shopCouponsIter = shopCoupons.getShopCouponsList().iterator();
            Map<Integer, Integer> shopCouponsUsed = new HashMap<Integer, Integer>();
            //遍历门店拥有的同类型的票,消耗相应数量的水票
            while (quantityRequired > 0 && shopCouponsIter.hasNext()) {
                MbShopCoupons mbShopCoupons = shopCouponsIter.next();
                MbShopCouponsLog mbShopCouponsLog = new MbShopCouponsLog();
                int quantityUsable = mbShopCoupons.getQuantityTotal() - mbShopCoupons.getQuantityUsed();
                int quantityLeft = quantityUsable - quantityRequired;
                if (quantityLeft <= 0) {
                    mbShopCouponsLog.setQuantityUsed(quantityUsable);
                    mbShopCouponsLog.setShopCouponsStatus(SHOP_COUPONS_STATUS_INACTIVE);

                    shopCouponsUsed.put(mbShopCoupons.getId(), quantityUsable);
                    paymentItem.setAmount(paymentItem.getAmount() + quantityUsable);

                    quantityRequired = -quantityLeft;
                } else {
                    mbShopCouponsLog.setQuantityUsed(quantityRequired);
                    mbShopCouponsLog.setShopCouponsStatus(SHOP_COUPONS_STATUS_ACTIVE);

                    shopCouponsUsed.put(mbShopCoupons.getId(), quantityRequired);
                    paymentItem.setAmount(paymentItem.getAmount() + quantityRequired);

                    quantityRequired = 0;
                }
                mbShopCouponsLog.setShopCouponsId(mbShopCoupons.getId());
                mbShopCouponsLog.setRefId(paymentItem.getId().toString());
                mbShopCouponsLog.setRefType(mbShopCouponsLogService.SHOP_COUPONS_LOG_TYPE_PAY_BY_VOUCHER);
                mbShopCouponsLog.setLoginId(loginId);
                mbShopCouponsLog.setReason(String.format("购买商品,订单[ID:%s]",orderId));
                mbShopCouponsLogService.addLogAndUpdateShopCoupons(mbShopCouponsLog);
            }
            if (quantityRequired > 0 ) throw new ServiceException("该订单商品数据异常:券票量不对等");

            paymentItem.setRemark(JSON.toJSONString(shopCouponsUsed));
//            paymentItem.setCouponsId(shopCoupons.getCouponsId());
            mbPaymentItemService.edit(paymentItem);
        }
    }

    @Override
    public void addShopCouponsAndEditBalance(MbShopCoupons mbShopCoupons) {
        //检测是否给主门店添加券票
        if(F.empty(mbShopCoupons.getShopId())) {
            throw new ServiceException("门店ID不能为空");
        }
        else {
            MbShop shop =  mbShopService.get(mbShopCoupons.getShopId()) ;
            if (shop == null) throw new ServiceException("门店不存在");
            if (!F.empty(shop.getParentId()) && shop.getParentId() != -1) throw new ServiceException("子门店不允许添加券票,请前往主门店[ID:"+shop.getParentId() +"]进行添加");
        }
        //通过水票ID获得水票对象
        MbCoupons mbCoupons = mbCouponsService.get(mbShopCoupons.getCouponsId());
        if (mbCoupons != null && COUPONS_TYPE_1_VOUCHER.equals(mbCoupons.getType())) {
            //通过门店ID获得门店余额账户,若账户不存在则添加
            MbBalance mbBalance = mbBalanceService.queryByShopId(mbShopCoupons.getShopId());
            if (mbBalance == null) {
                mbBalance = mbBalanceService.addOrGetMbBalance(mbShopCoupons.getShopId());
            }

            //添加门店券
            add(mbShopCoupons);
            MbBalanceLog mbBalanceLog = new MbBalanceLog();
            String reason = EDIT_BALANCE_REASON + ":以" + mbShopCoupons.getPayTypeName() + "方式添加[ " +
                    mbCoupons.getName() + " ]" + mbShopCoupons.getQuantityTotal() + "张";
            //若是免费购买则无需修改余额和记录
            if (!COUPONS_PAY_TYPE_FREE.equals(mbShopCoupons.getPayType())) {
                // 计算购买水票总额,判定账户余额是否足够,不足则抛出余额不足的异常
                Integer cost = mbCoupons.getPrice() * mbShopCoupons.getQuantityTotal();
                if ((mbBalance.getAmount() - cost) < 0) {
                    throw new ServiceException(NO_ENOUGH_MONEY_ERROR);
                }
                //账户余额足够则修改余额并增加余额修改记录
                mbBalanceLog.setAmount(-cost);
                mbBalanceLog.setBalanceId(mbBalance.getId());
                mbBalanceLog.setReason(reason);
                mbBalanceLog.setRemark(mbShopCoupons.getRemark());
                mbBalanceLog.setRefType(BALANCELOG_TYPE_BUY_VOUCHER);
                mbBalanceLog.setRefId(listMbShopCoupons(mbShopCoupons).get(0).getId().toString());
                mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);
            }
            //添加门店券并增加记录
            MbShopCouponsLog mbShopCouponsLog = new MbShopCouponsLog();
            mbShopCouponsLog.setQuantityTotal(mbShopCoupons.getQuantityTotal());
            mbShopCouponsLog.setShopCouponsId(mbShopCoupons.getId());
            mbShopCouponsLog.setLoginId(mbShopCoupons.getLoginId());
            //非免费购买则记录下余额修改记录的ID
            if (!F.empty(mbBalanceLog.getId())) {
                mbShopCouponsLog.setRefId(mbBalanceLog.getId().toString());
                mbShopCouponsLog.setRefType(mbShopCouponsLogService.SHOP_COUPONS_LOG_TYPE_ADD_WAY_BALANCE);
            } else {
                mbShopCouponsLog.setRefType(mbShopCouponsLogService.SHOP_COUPONS_LOG_TYPE_ADD_WAY_FREE);
            }
            mbShopCouponsLog.setRemark(mbShopCoupons.getRemark());
            mbShopCouponsLog.setReason(reason);
            mbShopCouponsLogService.add(mbShopCouponsLog);
        }
    }

    @Override
    public void deleteAndRefund(MbShopCoupons mbShopCoupons) {
        MbShopCoupons shopCoupons = get(mbShopCoupons.getId());

        //如果删除券票时检测到是子门店则系统将会把退款退至主门店余额下
        MbShop shop = mbShopService.get(shopCoupons.getShopId());
        if  (shop != null) {
           mbShopCoupons.setShopId((shop.getParentId() == null || shop.getParentId() == -1) ? shop.getId() : shop.getParentId());
        }


        if (shopCoupons == null) throw new RuntimeException("无法找到对应门店拥有的券");
        //免费赠送券取消时无需修改余额直接删除并记录
        if (COUPONS_PAY_TYPE_FREE.equals(shopCoupons.getPayType())) {
            //删除门店券,且将会修改门店券状态为被删除,并增加记录
            MbShopCoupons mbShopCouponsDelete = new MbShopCoupons();
            mbShopCouponsDelete.setId(mbShopCoupons.getId());
            mbShopCouponsDelete.setStatus(SHOP_COUPONS_STATUS_ISDELETED);
            edit(mbShopCouponsDelete);
            delete(mbShopCoupons.getId());
            MbShopCouponsLog mbShopCouponsLog = new MbShopCouponsLog();
            mbShopCouponsLog.setShopCouponsId(mbShopCoupons.getId());
            mbShopCouponsLog.setShopCouponsStatus(SHOP_COUPONS_STATUS_ISDELETED);
            mbShopCouponsLog.setLoginId(mbShopCoupons.getLoginId());
            mbShopCouponsLog.setRemark(mbShopCoupons.getRemark());
            mbShopCouponsLog.setReason(mbShopCouponsLogService.LOG_REASON_DELETE_FREE_GIVEN);
            mbShopCouponsLogService.add(mbShopCouponsLog);
            return;
        }



        //门店购买券删除时需要修改余额并增加记录
        //通过水票ID获得水票对象

        MbCoupons mbCoupons = mbCouponsService.get(shopCoupons.getCouponsId());
        if (mbCoupons != null && COUPONS_TYPE_1_VOUCHER.equals(mbCoupons.getType())) {
            //通过门店ID获得门店余额账户,若账户不存在则添加
            MbBalance mbBalance = mbBalanceService.queryByShopId(shopCoupons.getShopId());
            if (mbBalance == null) {
                mbBalance = mbBalanceService.addOrGetMbBalance(shopCoupons.getShopId());
            }
            //计算购买水票总额
            Integer quantityUsable = shopCoupons.getQuantityTotal() - (shopCoupons.getQuantityUsed() == null ? 0 : shopCoupons.getQuantityUsed());
            Integer cost = mbCoupons.getPrice() * quantityUsable;

            //删除门店券,且将会修改门店券状态为被删除,并增加记录
            MbShopCoupons mbShopCouponsDelete = new MbShopCoupons();
            mbShopCouponsDelete.setId(mbShopCoupons.getId());
            mbShopCouponsDelete.setStatus(SHOP_COUPONS_STATUS_ISDELETED);
            edit(mbShopCouponsDelete);
            delete(shopCoupons.getId());

            //账户余额足够则修改余额并增加余额修改记录
            MbBalanceLog mbBalanceLog = new MbBalanceLog();
            mbBalanceLog.setAmount(cost);
            mbBalanceLog.setBalanceId(mbBalance.getId());
            String reason = "删除[ " + mbCoupons.getName() + " ]" + quantityUsable + "张,返还余额.";
            mbBalanceLog.setReason(reason);
            mbBalanceLog.setRemark(mbShopCoupons.getRemark());
            mbBalanceLog.setRefType(BALANCELOG_TYPE_DELETE_VOUCHER);
            mbBalanceLog.setRefId(shopCoupons.getId().toString());
            mbBalanceLogService.addAndUpdateBalance(mbBalanceLog);

            //增加记录并删除门店券

            MbShopCouponsLog mbShopCouponsLog = new MbShopCouponsLog();
            mbShopCouponsLog.setShopCouponsId(mbShopCoupons.getId());
            mbShopCouponsLog.setShopCouponsStatus(SHOP_COUPONS_STATUS_ISDELETED);
            mbShopCouponsLog.setLoginId(mbShopCoupons.getLoginId());
            mbShopCouponsLog.setRefId(mbBalanceLog.getId().toString());
            mbShopCouponsLog.setRefType(mbShopCouponsLogService.SHOP_COUPONS_LOG_TYPE_DELETE);
            mbShopCouponsLog.setRemark(mbShopCoupons.getRemark());
            mbShopCouponsLog.setReason(reason);
            mbShopCouponsLogService.add(mbShopCouponsLog);
        }
    }
    @Override
    public void addByActivity(Object object, Integer couponsId, Integer quantity) {
        MbOrder order = new MbOrder();
        if (object instanceof MbOrder) {
            order = (MbOrder) object;
        }else {
            throw new ServiceException("活动行为参数格式填写错误,请修改参数1为:mbOrder,券ID,券数量");
        }
        if (order.getShopId() == null) throw new ServiceException("MbShopCouponsService.addByActivity参数mbOrder无shopId");
        MbShopCoupons shopCoupons = new MbShopCoupons();
        shopCoupons.setCouponsId(couponsId);
        shopCoupons.setShopId(order.getShopId());
        shopCoupons.setQuantityTotal(quantity);
        shopCoupons.setStatus("NS001");
        shopCoupons.setPayType("PT05");
        shopCoupons.setRemark(String.format("活动赠送%s张",quantity));
        add(shopCoupons);
        System.out.println("------------------\n-------------------\n----------------\n------------第三个");
    }
}
