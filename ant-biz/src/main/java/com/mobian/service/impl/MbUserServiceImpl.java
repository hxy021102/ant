package com.mobian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.dao.MbUserDaoI;
import com.mobian.listener.Application;
import com.mobian.model.TmbUser;
import com.mobian.pageModel.*;
import com.mobian.service.MbBalanceServiceI;
import com.mobian.service.MbShopServiceI;
import com.mobian.service.MbUserServiceI;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbUserServiceImpl extends BaseServiceImpl<MbUser> implements MbUserServiceI {

    @Autowired
    private MbUserDaoI mbUserDao;

    @Autowired
    private MbShopServiceI mbShopService;

    @Autowired
    private MbBalanceServiceI mbBalanceService;

    @Override
    public DataGrid dataGrid(MbUser mbUser, PageHelper ph) {
        List<MbUser> ol = new ArrayList<MbUser>();
        String hql = " from TmbUser t ";
        DataGrid dg = dataGridQuery(hql, ph, mbUser, mbUserDao);
        @SuppressWarnings("unchecked")
        List<TmbUser> l = dg.getRows();
        if (l != null && l.size() > 0) {
            for (TmbUser t : l) {
                MbUser o = new MbUser();
                BeanUtils.copyProperties(t, o);

                setBalance(o);

                ol.add(o);
            }
        }
        dg.setRows(ol);
        return dg;
    }

    private void setBalance(MbUser user) {
        if (user.getShopId() != null) {
            MbShop shop = mbShopService.getFromCache(user.getShopId());
            MbBalance balance = mbBalanceService.queryByShopId(shop.getId());
            if (balance != null) {
                user.setBalance(balance.getAmount());
                user.setBalanceId(balance.getId());
            }
        }
    }

    protected String whereHql(MbUser mbUser, Map<String, Object> params) {
        String whereHql = "";
        if (mbUser != null) {
            whereHql += " where t.isdeleted = 0 ";
            if (!F.empty(mbUser.getTenantId())) {
                whereHql += " and t.tenantId = :tenantId";
                params.put("tenantId", mbUser.getTenantId());
            }
            if (!F.empty(mbUser.getIsdeleted())) {
                whereHql += " and t.isdeleted = :isdeleted";
                params.put("isdeleted", mbUser.getIsdeleted());
            }
            if (!F.empty(mbUser.getUserName())) {
                whereHql += " and t.userName = :userName";
                params.put("userName", mbUser.getUserName());
            }
            if (!F.empty(mbUser.getPassword())) {
                whereHql += " and t.password = :password";
                params.put("password", mbUser.getPassword());
            }
            if (!F.empty(mbUser.getNickName())) {
                whereHql += " and t.nickName = :nickName";
                params.put("nickName", mbUser.getNickName());
            }
            if (!F.empty(mbUser.getPhone())) {
                whereHql += " and t.phone = :phone";
                params.put("phone", mbUser.getPhone());
            }
            if (!F.empty(mbUser.getIcon())) {
                whereHql += " and t.icon = :icon";
                params.put("icon", mbUser.getIcon());
            }
            if (!F.empty(mbUser.getSex())) {
                whereHql += " and t.sex = :sex";
                params.put("sex", mbUser.getSex());
            }
            if (!F.empty(mbUser.getShopId())) {
                whereHql += " and t.shopId = :shopId";
                params.put("shopId", mbUser.getShopId());
            }
            if (!F.empty(mbUser.getRefId())) {
                whereHql += " and t.refId = :refId";
                params.put("refId", mbUser.getRefId());
            }
            if (!F.empty(mbUser.getRefType())) {
                whereHql += " and t.refType = :refType";
                params.put("refType", mbUser.getRefType());
            }
        }
        return whereHql;
    }

    @Override
    public void add(MbUser mbUser) {
        TmbUser t = new TmbUser();
        BeanUtils.copyProperties(mbUser, t);
        //t.setId(jb.absx.UUID.uuid());
        t.setIsdeleted(false);
        mbUserDao.save(t);
    }

    @Override
    public MbUser get(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        TmbUser t = mbUserDao.get("from TmbUser t  where t.id = :id", params);
        MbUser o = new MbUser();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public MbUser getFromCache(Integer id) {
        TmbUser source = mbUserDao.getById(id);
        MbUser target = new MbUser();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Override
    public void edit(MbUser mbUser) {
        TmbUser t = mbUserDao.get(TmbUser.class, mbUser.getId());
        if (t != null) {
            MyBeanUtils.copyProperties(mbUser, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
        }
    }

    @Override
    public void delete(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        mbUserDao.executeHql("update TmbUser t set t.isdeleted = 1 where t.id = :id", params);
        //mbUserDao.delete(mbUserDao.get(TmbUser.class, id));
    }

    @Override
    public MbUser get(MbUser mbUser) {
        String whereHql = "";
        if (mbUser != null) {
            whereHql += " where t.isdeleted = 0 ";
            Map<String, Object> params = new HashMap<String, Object>();
            if (!F.empty(mbUser.getId())) {
                whereHql += " and t.id = :id";
                params.put("id", mbUser.getId());
            }
            if (!F.empty(mbUser.getUserName()) && !F.empty(mbUser.getPassword())) {
                whereHql += " and t.userName = :userName and t.password = :password";
                params.put("userName", mbUser.getUserName());
                params.put("password", MD5Util.md5(mbUser.getPassword()));
            }
            if (!F.empty(mbUser.getPhone()) && !F.empty(mbUser.getPassword())) {
                whereHql += " and t.phone = :phone and t.password = :password";
                params.put("phone", mbUser.getPhone());
                params.put("password", MD5Util.md5(mbUser.getPassword()));
            }
            if (!F.empty(mbUser.getRefId()) && !F.empty(mbUser.getRefType())) {
                whereHql += " and t.refId = :refId and t.refType = :refType";
                params.put("refId", mbUser.getRefId());
                params.put("refType", mbUser.getRefType());
            }
            TmbUser t = mbUserDao.get("from TmbUser t" + whereHql, params);
            if (t != null && t.getId() != null) {
                MbUser o = new MbUser();
                BeanUtils.copyProperties(t, o);
                return o;
            }
        }
        return null;
    }

    @Override
    public MbUser addMbUser(MbUser mbUser) {
        TmbUser t = new TmbUser();
        BeanUtils.copyProperties(mbUser, t);
        t.setIsdeleted(false);
        mbUserDao.save(t);
        MbUser o = new MbUser();
        BeanUtils.copyProperties(t, o);
        return o;
    }

    @Override
    public boolean hasPhone(String phone) {
        if (!F.empty(phone)) {
            List<TmbUser> l = mbUserDao.find("from TmbUser t where t.isdeleted = 0 and phone='" + phone + "'", 1, 1);
            if (l != null || l.size() < 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public MbUser loginByWx(String code, boolean snsapi_userinfo) {
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getAuthorizeUrl(code), "get", null));
        if (jsonObject == null || !jsonObject.containsKey("openid") || F.empty(jsonObject.getString("openid")))
            return null;
        String access_token = null;
        if (snsapi_userinfo) access_token = jsonObject.getString("access_token");
        return login(jsonObject.getString("openid"), access_token);
    }

    private MbUser login(String openid, String access_token) {
        MbUser mbUser = new MbUser();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refId", openid);
        params.put("refType", "wx");
        TmbUser t = mbUserDao.get("from TmbUser t where t.refId = :refId and t.refType = :refType and t.isdeleted = 0", params);
        if (t != null) {
            BeanUtils.copyProperties(t, mbUser);
        } else {
            t = new TmbUser();
            mbUser.setRefId(openid);
            mbUser.setRefType("wx");
            mbUser.setIsdeleted(false);
            RedisUtil redisUtil = BeanUtil.getBean(RedisUtil.class);

            JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getUserInfoUrl(openid, access_token), "GET", null));
            // 未关注公众号
            if (F.empty(access_token) && jsonObject.getIntValue("subscribe") == 0) {
                return null;
            }

            mbUser.setPassword(MD5Util.md5(Constants.DEFAULT_PASSWORD));
            String nickname = Util.filterEmoji(jsonObject.getString("nickname"));
            //nickname = nickname.length() > 8 ? nickname.substring(0, 8) : nickname;
            params = new HashMap<String, Object>();
            params.put("nickName", nickname);
            if (mbUserDao.count("select count(*) from TmbUser t where t.nickName = :nickName and t.isdeleted = 0", params) > 0) {
                nickname += Util.CreateNonceNumstr(4);
            }
            mbUser.setNickName(nickname);
            mbUser.setSex(jsonObject.getInteger("sex") + "");
            mbUser.setIcon(jsonObject.getString("headimgurl"));
            MyBeanUtils.copyProperties(mbUser, t, true);
            mbUserDao.save(t);
            mbUser.setId(t.getId());
        }
        return mbUser;
    }

    /**
     * 将shopID置为空
     */
    @Override
    public void editMbShopToNull(MbUser mbUser) {
        TmbUser tmbUser = mbUserDao.get(TmbUser.class, mbUser.getId());
        if (tmbUser != null) {
            tmbUser.setShopId(null);
        }
    }
}
