package com.mobian.service.impl;

import com.bx.ant.pageModel.DistributeRangeMap;
import com.bx.ant.pageModel.ShopDeliverApply;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.absx.F;
import com.mobian.dao.MbShopDaoI;
import com.mobian.dao.MbUserDaoI;
import com.mobian.dao.MbWarehouseDaoI;
import com.mobian.interceptors.TokenManage;
import com.mobian.interceptors.TokenWrap;
import com.mobian.model.TmbShop;
import com.mobian.model.TmbWarehouse;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.GeoUtil;
import com.mobian.util.MyBeanUtils;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class MbShopServiceImpl extends BaseServiceImpl<MbShop> implements MbShopServiceI {

    @Autowired
    private MbShopDaoI mbShopDao;

    @Autowired
    private DiveRegionServiceI diveRegionService;

    @Autowired
    private MbWarehouseDaoI mbWarehouseDao;

    @Autowired
    private MbWarehouseServiceI mbWarehouseService;

    @Autowired
    private RedisUserServiceImpl redisUserService;

    @Autowired
    private MbUserDaoI mbUserDao;

    @Autowired
    private MbShopContactServiceI mbShopContactService;
    @Autowired
    private MbUserServiceI mbUserService;
    @javax.annotation.Resource
    private TokenManage tokenManage;

    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Autowired
    private MbBalanceLogServiceI mbBalanceLogService;
    @Autowired
    private MbOrderServiceI mbOrderService;
    @Resource
    private ShopDeliverApplyServiceI shopDeliverApplyService;

    @Override
    public DataGrid dataGrid(MbShop mbShop, PageHelper ph) {
        List<MbShop> ol = new ArrayList<MbShop>();
        String hql = " from TmbShop t ";
        DataGrid dg = dataGridQuery(hql, ph, mbShop, mbShopDao);
        @SuppressWarnings("unchecked")
        List<TmbShop> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbShop t : l) {
                MbShop o = new MbShop();
                BeanUtils.copyProperties(t, o);
                o.setRegionPath(diveRegionService.getRegionPath(o.getRegionId() + ""));
                if(!F.empty(t.getParentId()) && t.getParentId() != -1) {
                    MbShop pShop = this.getFromCache(t.getParentId());
                    o.setParentName(pShop.getName());
                }
                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }

    @Override
    public DataGrid dataGridKeyword(MbShop mbShop) {

        PageHelper ph = new PageHelper();
        ph.setRows(100);
        ph.setHiddenTotal(true);
        mbShop.setAuditStatus(AS_02);
        DataGrid dg = dataGrid(mbShop, ph);
        if (CollectionUtils.isEmpty(dg.getRows())) {
            if (!F.empty(mbShop.getContactPhone())) {
                MbShopContact mbShopContact = new MbShopContact();
                PageHelper ph1 = new PageHelper();
                mbShopContact.setTelNumber(mbShop.getContactPhone());
                ph1.setRows(50);
                ph1.setHiddenTotal(true);
                DataGrid dgContact = mbShopContactService.dataGrid(mbShopContact, ph1);
                if (CollectionUtils.isNotEmpty(dgContact.getRows())) {
                    List<MbShopContact> mbShopList = (List<MbShopContact>) dgContact.getRows();
                    Integer[] ids = new Integer[mbShopList.size()];
                    for (int i = 0; i < mbShopList.size(); i++) {
                        ids[i] = mbShopList.get(i).getShopId();
                    }
                    List<MbShop> ol = new ArrayList<MbShop>();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("alist", ids);
                    List<TmbShop> l = mbShopDao.find("from TmbShop t where  t.id in (:alist)", params);
                    if (CollectionUtils.isNotEmpty(l)) {
                        for (TmbShop t : l) {
                            MbShop o = new MbShop();
                            BeanUtils.copyProperties(t, o);
                            o.setRegionPath(diveRegionService.getRegionPath(o.getRegionId() + ""));
                            if(!F.empty(t.getParentId()) && t.getParentId() != -1) {
                                MbShop pShop = this.getFromCache(t.getParentId());
                                o.setParentName(pShop.getName());
                            }
                            ol.add(o);
                        }
                    }
                    dg.setRows(ol);
                }
            }
        }

        // 查询所有分店
        if (CollectionUtils.isNotEmpty(dg.getRows())) {
            List<MbShop> shops = (List<MbShop>) dg.getRows();
            List<Integer> mainShopIds = new ArrayList<Integer>(); // 主店ID集合
            List<Integer> branchShopIds = new ArrayList<Integer>(); // 分店ID集合
            for(MbShop shop : shops) {
                if(F.empty(shop.getParentId()) || shop.getParentId() == -1) {
                    mainShopIds.add(shop.getId());
                } else {
                    branchShopIds.add(shop.getId());
                }
            }

            if(CollectionUtils.isNotEmpty(mainShopIds)) {
                Integer[] ids = new Integer[mainShopIds.size()];
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("alist", mainShopIds.toArray(ids));
                List<TmbShop> l = mbShopDao.find("from TmbShop t where  t.parentId in (:alist)", params);
                if (CollectionUtils.isNotEmpty(l)) {
                    for (TmbShop t : l) {
                        if(branchShopIds.contains(t.getId())) continue;

                        MbShop o = new MbShop();
                        BeanUtils.copyProperties(t, o);
                        o.setRegionPath(diveRegionService.getRegionPath(o.getRegionId() + ""));
                        if(!F.empty(t.getParentId()) && t.getParentId() != -1) {
                            MbShop pShop = this.getFromCache(t.getParentId());
                            o.setParentName(pShop.getName());
                        }
                        shops.add(o);
                    }
                }
            }

        }
        return dg;
    }


    protected String whereHql(MbShop mbShop, Map<String, Object> params) {
        String whereHql = "";
        if (mbShop != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbShop.getId())) {
                whereHql += " and t.id = :id";
                params.put("id", mbShop.getId());
            }
            if (!F.empty(mbShop.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbShop.getTenantId());
            }
            if (!F.empty(mbShop.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbShop.getIsdeleted());
            }

            if (!F.empty(mbShop.getName())) {
                whereHql += " and t.name LIKE :name";
                params.put("name", "%" +mbShop.getName() + "%");
            }
            if (!F.empty(mbShop.getRegionId())) {
                whereHql += " and t.regionId = :regionId";
                params.put("regionId", mbShop.getRegionId());
            }
            if (!F.empty(mbShop.getAddress())) {
                whereHql += " and t.address LIKE :address";
                params.put("address", "%" + mbShop.getAddress() + "%");
            }
            if (!F.empty(mbShop.getWarehouseId())) {
                whereHql += " and t.warehouseId = :warehouseId";
                params.put("warehouseId", mbShop.getWarehouseId());
            }
            if (!F.empty(mbShop.getContactPhone())) {
                whereHql += " and t.contactPhone = :contactPhone";
                params.put("contactPhone", mbShop.getContactPhone());
            }
            if (!F.empty(mbShop.getContactPeople())) {
                whereHql += " and t.contactPeople = :contactPeople";
                params.put("contactPeople", mbShop.getContactPeople());
            }
            if (!F.empty(mbShop.getAuditStatus())) {
                whereHql += " and t.auditStatus = :auditStatus";
                params.put("auditStatus", mbShop.getAuditStatus());
            }

            if(!F.empty(mbShop.getParentId())) {
                if(mbShop.isOnlyBranch()) {
                    whereHql += " and t.parentId = :parentId";
                } else {
                    whereHql += " and (t.parentId = :parentId or t.id = :parentId)";
                }

                params.put("parentId", mbShop.getParentId());
            }
            if(mbShop.isOnlyMain()) {
                whereHql += " and (t.parentId is null or t.parentId = -1)"; // 只查询主店
            }
            if (mbShop.getIds()!=null) {
                whereHql += " and t.id in (:ids)";
                params.put("ids", mbShop.getIds());
            }

            if (!F.empty(mbShop.getShopType())) {
                whereHql += " and t.shopType = :shopType";
                params.put("shopType", mbShop.getShopType());
            }
            if (!F.empty(mbShop.getSalesLoginId())) {
                whereHql += " and t.salesLoginId = :salesLoginId";
                params.put("salesLoginId", mbShop.getSalesLoginId());
            }
        }
        return whereHql;
    }

    @Override
    public void add(MbShop mbShop) {

        TmbShop tmbShop = new TmbShop();
        MyBeanUtils.copyProperties(mbShop, tmbShop);
        tmbShop.setIsdeleted(false);
        tmbShop.setAuditStatus(AS_01);
        mbShopDao.save(tmbShop);
        mbShop.setId(tmbShop.getId());
    }

    @Override
    public MbShop get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbShop t = mbShopDao.get("from TmbShop t  where t.id = :id", params);
        if (t == null) return null;
        MbShop o = new MbShop();
        BeanUtils.copyProperties(t, o);
        if (o.getRegionId() != null)
            o.setRegionPath(diveRegionService.getRegionPath(o.getRegionId() + "").replaceAll("-", ""));
        return o;
    }

    @Override
    public MbShop getFromCache(Integer id) {
        TmbShop source = mbShopDao.getById(id);
        if (source != null) {
            MbShop target = new MbShop();
            BeanUtils.copyProperties(source, target);
            return target;
        } else {
            return null;
        }
    }

    @Override
    public void unboundUser(MbShop mbShop, MbUser mbUser) {
        if (!F.empty(mbShop.getId()) && !F.empty(mbUser.getId())) {
            tokenManage.destroyTokenByMbUserId(mbUser.getId().toString());
            editUserIdToNull(mbShop);
            mbUserService.editMbShopToNull(mbUser);
            mbUserDao.clearUserCache(mbUser);
            mbShopDao.clearShopCache(mbShop);
        }
    }


    @Override
    public void boundUser(MbShop mbShop, MbUser mbUser) {
        if (!F.empty(mbShop.getId()) && !F.empty(mbUser.getId())) {
            edit(mbShop);
            mbUserService.edit(mbUser);
            mbUserDao.clearUserCache(mbUser);
            mbShopDao.clearShopCache(mbShop);
        }
    }

    @Override
    public void editMainShop(MbShop mbShop, int balanceAmount, int cashBalanceAmount) {
        if (!F.empty(mbShop.getId()) && !F.empty(mbShop.getParentId())) {
            MbShop source = getFromCache(mbShop.getId());
            Integer oldParentId = source.getParentId();

            MbUser mbUser = new MbUser();
            mbUser.setId(source.getUserId());

            // 绑定主店
            edit(mbShop);

            // 主门店parentId设为-1
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", mbShop.getParentId());
            mbShopDao.executeHql("update TmbShop t set t.parentId = -1 where t.id = :id", params);

            // 若为解绑，原绑定的主门店如果不存在分店则将parentId设为null
            if(!F.empty(oldParentId)) {
                params = new HashMap<String, Object>();
                params.put("parentId", oldParentId);
                if(mbShopDao.count("select count(*) from TmbShop t where t.parentId = :parentId", params) == 0) {
                    mbShopDao.executeHql("update TmbShop t set t.parentId = null where t.id = :parentId", params);
                }
            }

            // 余额绑定
            if(balanceAmount != 0) {
                // 分店余额转出
                MbBalance balance = mbBalanceService.queryByRealShopId(mbShop.getId());
                MbBalanceLog log = new MbBalanceLog();
                log.setBalanceId(balance.getId());
                log.setAmount(-balanceAmount);
                log.setRefType("BT040"); // 绑定主店-余额转出
                log.setRefId(mbShop.getParentId()+"");
                mbBalanceLogService.addAndUpdateBalance(log);

                // 主店余额转入
                balance = mbBalanceService.queryByRealShopId(mbShop.getParentId());
                log = new MbBalanceLog();
                log.setBalanceId(balance.getId());
                log.setAmount(balanceAmount);
                log.setRefType("BT041"); // 绑定主店-余额转入
                log.setRefId(mbShop.getId()+"");
                mbBalanceLogService.addAndUpdateBalance(log);
            }

            // 桶余额绑定
            if(cashBalanceAmount != 0) {
                // 分店桶桶余额转出
                MbBalance balance = mbBalanceService.getCashByRealShopId(mbShop.getId());
                MbBalanceLog log = new MbBalanceLog();
                log.setBalanceId(balance.getId());
                log.setAmount(-cashBalanceAmount);
                log.setRefType("BT042"); // 绑定主店-桶余额转出
                log.setRefId(mbShop.getParentId()+"");
                mbBalanceLogService.addAndUpdateBalance(log);

                // 主店桶余额转入
                balance = mbBalanceService.getCashByRealShopId(mbShop.getParentId());
                log = new MbBalanceLog();
                log.setBalanceId(balance.getId());
                log.setAmount(cashBalanceAmount);
                log.setRefType("BT043"); // // 绑定主店-桶余额转入
                log.setRefId(mbShop.getId()+"");
                mbBalanceLogService.addAndUpdateBalance(log);
            }

            // 解除门店用户绑定关系
            if(!F.empty(mbUser.getId())) {
                tokenManage.destroyTokenByMbUserId(mbUser.getId().toString());
                editUserIdToNull(mbShop);
                mbUserService.editMbShopToNull(mbUser);
                mbUserDao.clearUserCache(mbUser);
            }
            // 绑定主店清除cache
            mbShopDao.clearShopCache(mbShop);
        }

    }

    /**
     * 查询用户所有门店（包括分店）
     * @param shopId
     * @return
     */
    @Override
    public List<MbShop> queryListById(Integer shopId) {
        List<MbShop> shops = null;
        MbShop mbShop = get(shopId);

        if(!mbShop.getIsdeleted() && AS_02.equals(mbShop.getAuditStatus())) {
            shops = new ArrayList<MbShop>();
            mbShop.setRegionPath(diveRegionService.getRegionPath(mbShop.getRegionId() + ""));
            shops.add(mbShop);

            //int parentId = shopId;
            //if(!F.empty(mbShop.getParentId()) && mbShop.getParentId() != -1) parentId = mbShop.getParentId();

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("parentId", shopId);
            List<TmbShop> l = mbShopDao.find("from TmbShop t where t.isdeleted = 0 and t.auditStatus = '"+AS_02+"' and t.parentId = :parentId", params);
            if (CollectionUtils.isNotEmpty(l)) {
                for (TmbShop t : l) {
                    MbShop o = new MbShop();
                    BeanUtils.copyProperties(t, o);
                    o.setRegionPath(diveRegionService.getRegionPath(o.getRegionId() + ""));
                    shops.add(o);
                }
            }
        }

        return shops;
    }

    //TODO 无法解决更改缓存的问题
    @Override
    public void editUserIdToNull(MbShop mbShop) {
        TmbShop tmbShop = mbShopDao.get(TmbShop.class, mbShop.getId());
        if (tmbShop != null) {
            tmbShop.setUserId(null);
        }
    }

    @Override
    public void edit(MbShop mbShop) {
        mbShopDao.clearShopCache(mbShop);
        TmbShop tmbShop = mbShopDao.get(TmbShop.class, mbShop.getId());
        if (tmbShop != null) {
            MyBeanUtils.copyProperties(mbShop, tmbShop, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }

        if (tmbShop.getWarehouseId() != null) {
            TmbWarehouse tmbWarehouse = mbWarehouseDao.get(TmbWarehouse.class, tmbShop.getWarehouseId());
            if (tmbWarehouse != null) {
                MyBeanUtils.copyProperties(mbShop, tmbWarehouse, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
            }
        }
    }

    @Override
    public void editAudit(MbShop mbShop) {
        TmbShop tmbShop = mbShopDao.get(TmbShop.class, mbShop.getId());
        if (tmbShop != null) {
            setShopLocation(mbShop);
            tmbShop.setAuditDate(new Date());
            MyBeanUtils.copyProperties(mbShop, tmbShop, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
            //通过后建立对应的仓库
            if (AS_02.equals(mbShop.getAuditStatus())) {
                TmbWarehouse tmbWarehouse = new TmbWarehouse();
                BeanUtils.copyProperties(tmbShop, tmbWarehouse);
                tmbWarehouse.setIsdeleted(false);
                tmbWarehouse.setWarehouseType("WT01");
                tmbWarehouse.setCode(tmbWarehouse.getWarehouseType() + "_" + tmbShop.getId());
                MbWarehouse mbWarehouse = mbWarehouseService.getByCode(tmbWarehouse.getCode());
                if (mbWarehouse == null) {
                    Integer warehouseId = (Integer) mbWarehouseDao.save(tmbWarehouse);
                    tmbShop.setWarehouseId(warehouseId);
                } else {
                    BeanUtils.copyProperties(tmbWarehouse, mbWarehouse);
                    mbWarehouseService.edit(mbWarehouse);
                }
                //建个余额账号
                mbBalanceService.addOrGetMbBalance(mbShop.getId());

                //刷新token
                TokenWrap tokenWrap = redisUserService.getTokenByUserId(tmbShop.getUserId());
                if (tokenWrap != null) {
                    tokenWrap.setShopId(tmbShop.getId());
                    redisUserService.setToken(tokenWrap);
                }
            }
        }
    }

    @Override
    public void delete(Integer id) {

        MbShop shop = new MbShop();
        shop.setId(id);
        mbShopDao.clearShopCache(shop);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbShopDao.executeHql("update TmbShop t set t.isdeleted = 1 where t.id = :id", params);
        //mbShopDao.delete(mbShopDao.get(TmbShop.class, id));
    }

    @Override
    public List<MbShop> getByPhone(String phone) {
        List<MbShop> ol = new ArrayList<MbShop>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contactPhone", phone);
        List<TmbShop> l = mbShopDao.find("from TmbShop t where t.isdeleted = 0 and t.contactPhone = :contactPhone order by t.addtime desc", params);

        if (CollectionUtils.isNotEmpty(l)) {
            for (TmbShop t : l) {
                MbShop o = new MbShop();
                BeanUtils.copyProperties(t, o);
                ol.add(o);
            }
        }
        return ol;
    }

    @Override
    public void addOrUpdate(MbShop mbShop) {
        if (F.empty(mbShop.getId())) {
            add(mbShop);
        } else {
            edit(mbShop);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", mbShop.getUserId());
        params.put("shopId", mbShop.getId());
        mbUserDao.executeHql("update TmbUser t set t.shopId = :shopId where t.id = :id", params);
    }

    @Override
    public DataGrid dataGridShopArrears(MbShop mbShop, PageHelper ph) {
        List<MbBalance> mbBalances = mbBalanceService.queryByrefTypeAndAmount(1, 0);
        DataGrid dataGrid=new DataGrid();
        if (!CollectionUtils.isEmpty(mbBalances)) {
            return dataGridShopBarrelAndArrears(mbShop, mbBalances, ph);
        }else {
            dataGrid.setTotal(0L);
        }
        return dataGrid;
    }

    @Override
    public DataGrid dataGridShopBarrel(MbShop mbShop, PageHelper ph) {
        List<MbBalance> mbBalances = mbBalanceService.queryByrefTypeAndAmount(4, 0);
        DataGrid dataGrid=new DataGrid();
        if(!CollectionUtils.isEmpty(mbBalances)) {
           return dataGridShopBarrelAndArrears(mbShop,mbBalances,ph);
        }else {
            dataGrid.setTotal(0L);
        }
        return dataGrid;
    }

    @Override
    public DataGrid dataGridShopBarrelAndArrears(MbShop mbShop, List<MbBalance> mbBalances, PageHelper ph) {
        Integer[] ids = new Integer[mbBalances.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = mbBalances.get(i).getRefId();
        }
        List<MbOrder> mbOrders = mbOrderService.queryDebtOrders();
        Integer[] debtIds = new Integer[mbOrders.size()];
        for (int i = 0; i < debtIds.length; i++) {
            debtIds[i] = mbOrders.get(i).getShopId();
        }
        Integer[] shopIds = new Integer[mbBalances.size()+mbOrders.size()];
        System.arraycopy(ids,0,shopIds,0,ids.length);
        System.arraycopy(debtIds,0,shopIds,ids.length,debtIds.length);
        mbShop.setIds(shopIds);
        DataGrid dataGrid = dataGrid(mbShop, ph);
        List<MbShop> mbShopList = dataGrid.getRows();
        if (!CollectionUtils.isEmpty(mbShopList)) {
            List<MbShopExt> mbShopExtList = new ArrayList<MbShopExt>();
            for (MbShop shop : mbShopList) {
                MbShopExt mbShopExt = new MbShopExt();
                BeanUtils.copyProperties(shop, mbShopExt);
                MbBalance mbBalance = mbBalanceService.queryByShopId(shop.getId());
                Integer sumDebtMoney= mbOrderService.getOrderDebtMoney(shop.getId());
                mbShopExtList.add(mbShopExt);
                if (mbBalance != null) {
                    mbShopExt.setBalanceAmount(mbBalance.getAmount());
                    mbShopExt.setBalanceId(mbBalance.getId());
                }
                if (AS_02.equals(mbShopExt.getAuditStatus())) {
                    mbBalance = mbBalanceService.addOrGetMbBalanceCash(shop.getId());
                    if (mbBalance != null) {
                        mbShopExt.setCashBalanceId(mbBalance.getId());
                        mbShopExt.setCashBalanceAmount(mbBalance.getAmount());
                    }
                }
                if(sumDebtMoney !=null) {
                    mbShopExt.setDebt(sumDebtMoney);
                    if(mbShopExt.getBalanceAmount() < 0) {
                        mbShopExt.setTotalDebt(mbShopExt.getBalanceAmount() - sumDebtMoney);
                    }else {
                        mbShopExt.setTotalDebt(-sumDebtMoney);
                    }
                }
            }
            dataGrid.setRows(mbShopExtList);
        }else {
            dataGrid.setTotal(0L);
        }
        return dataGrid;
    }

    @Override
    public List<MbShop> getNullLocation() {
        List<TmbShop> tmbShopList = mbShopDao.find("from TmbShop t  where t.isdeleted = 0 and t.auditStatus = '"+AS_02+"' and (t.longitude is null or t.latitude is null)");
        List<MbShop> mbShopList = new ArrayList<MbShop>();
        for (TmbShop tmbShop : tmbShopList) {
            MbShop mbShop = new MbShop();
            BeanUtils.copyProperties(tmbShop, mbShop);
            mbShopList.add(mbShop);
        }
        return mbShopList;
    }

    @Override
    public List<MbShop> query(MbShop mbShop) {
        List<MbShop> ol = new ArrayList<>();
        String hql = "from TmbShop t";
        Map<String, Object> params = new HashMap<>();
        String whereHql = whereHql(mbShop, params);
        List<TmbShop> l = mbShopDao.find(hql + whereHql, params);
        if (!CollectionUtils.isEmpty(l)) {
            for (TmbShop t : l) {
                MbShop m = new MbShop();
                BeanUtils.copyProperties(t, m);
                ol.add(m);
            }
        }
        return ol;
    }

    @Override
    public void setShopLocation(MbShop mbShop) {
        BigDecimal[] point = GeoUtil.getPosition(mbShop.getAddress());
        if (point != null) {
            mbShop.setLongitude(point[0]);
            mbShop.setLatitude(point[1]);
        }
    }

    @Override
    public List<MbShopMap> getShopMapData(MbShop mbShop) {
        List<MbShop> mbShopList = query(mbShop);
        if (CollectionUtils.isNotEmpty(mbShopList)) {
            List<MbShopMap> mbShopMaps = new ArrayList<MbShopMap>();
            for (MbShop shop : mbShopList) {
                MbShopMap mbShopMap = new MbShopMap();
                mbShopMap.setAddress("门店名称：" + shop.getName() + "<br/>联系人：" + shop.getContactPeople() + "<br/>联系电话：" + shop.getContactPhone() + "<br/>地址：" + shop.getAddress());
                mbShopMap.setLongitude(shop.getLongitude());
                mbShopMap.setLatitude(shop.getLatitude());
                mbShopMap.setShopType(shop.getShopType());
                mbShopMaps.add(mbShopMap);
            }
            return mbShopMaps;
        }
        return null;
    }

    @Override
    public MbShopMap getShopApplyMapData(Integer shopId,Integer shopDeliverApplyId) {
        MbShop shop = get(shopId);
        if (shop != null) {
            MbShopMap mbShopMap = new MbShopMap();
            mbShopMap.setAddress("门店名称：" + shop.getName() + "<br/>联系人：" + shop.getContactPeople() + "<br/>联系电话：" + shop.getContactPhone() + "<br/>地址：" + shop.getAddress());
            mbShopMap.setLongitude(shop.getLongitude());
            mbShopMap.setLatitude(shop.getLatitude());
            mbShopMap.setShopType(shop.getShopType());
            ShopDeliverApply shopDeliverApply=shopDeliverApplyService.get(shopDeliverApplyId);
            if (shopDeliverApply != null) {
                if (!F.empty(shopDeliverApply.getDistributeRange())) {
                    JSONArray json = JSONArray.fromObject(shopDeliverApply.getDistributeRange());
                    List<DistributeRangeMap> distributeRangeMaps = (List<DistributeRangeMap>) JSONArray.toCollection(json, DistributeRangeMap.class);
                    if (CollectionUtils.isNotEmpty(distributeRangeMaps)) {
                        mbShopMap.setDistributeRangeMapList(distributeRangeMaps);
                    }
                }
            }
            return mbShopMap;
        }
        return null;
    }

    @Override
    public List<MbShop> getMbshopListByName(String name) {
        List<TmbShop> tmbShopList = mbShopDao.find("from TmbShop t  where t.isdeleted = 0 and t.name LIKE  '" + '%' + name + '%' + "'");
        List<MbShop> mbShopList = new ArrayList<MbShop>();
        if (CollectionUtils.isNotEmpty(tmbShopList)) {
            for (TmbShop tmbShop : tmbShopList) {
                MbShop mbShop = new MbShop();
                BeanUtils.copyProperties(tmbShop, mbShop);
                mbShopList.add(mbShop);
            }
        }
        return mbShopList;
    }
}
