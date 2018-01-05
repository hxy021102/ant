package com.bx.ant.service.impl;

import com.bx.ant.dao.ShopDeliverApplyDaoI;
import com.bx.ant.model.TshopDeliverApply;
import com.bx.ant.pageModel.DeliverOrder;
import com.bx.ant.pageModel.DistributeRangeMap;
import com.bx.ant.pageModel.ShopDeliverApplyQuery;
import com.bx.ant.service.ShopDeliverApplyServiceI;
import com.mobian.absx.F;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.MbAssignShop;
import com.mobian.pageModel.MbShop;
import com.mobian.pageModel.PageHelper;
import com.bx.ant.pageModel.ShopDeliverApply;
import com.mobian.service.MbShopServiceI;
import com.mobian.util.ConvertNameUtil;
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
public class ShopDeliverApplyServiceImpl extends BaseServiceImpl<ShopDeliverApply> implements ShopDeliverApplyServiceI {

	@Autowired
	private ShopDeliverApplyDaoI shopDeliverApplyDao;

	@Resource
	private MbShopServiceI mbShopService;

	@Override
	public DataGrid dataGrid(ShopDeliverApply shopDeliverApply, PageHelper ph) {
		List<ShopDeliverApply> ol = new ArrayList<ShopDeliverApply>();
		String hql = " from TshopDeliverApply t ";
		DataGrid dg = dataGridQuery(hql, ph, shopDeliverApply, shopDeliverApplyDao);
		@SuppressWarnings("unchecked")
		List<TshopDeliverApply> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TshopDeliverApply t : l) {
				ShopDeliverApply o = new ShopDeliverApply();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(ShopDeliverApply shopDeliverApply, Map<String, Object> params) {
		String whereHql = "";	
		if (shopDeliverApply != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(shopDeliverApply.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", shopDeliverApply.getTenantId());
			}		
			if (!F.empty(shopDeliverApply.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", shopDeliverApply.getIsdeleted());
			}		
			if (!F.empty(shopDeliverApply.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", shopDeliverApply.getShopId());
			}		
			if (!F.empty(shopDeliverApply.getDeliveryWay())) {
				whereHql += " and t.deliveryWay = :deliveryWay";
				params.put("deliveryWay", shopDeliverApply.getDeliveryWay());
			}		
			if (!F.empty(shopDeliverApply.getOnline())) {
				whereHql += " and t.online = :online";
				params.put("online", shopDeliverApply.getOnline());
			}		
			if (!F.empty(shopDeliverApply.getResult())) {
				whereHql += " and t.result = :result";
				params.put("result", shopDeliverApply.getResult());
			}		
			if (!F.empty(shopDeliverApply.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", shopDeliverApply.getStatus());
			}		
			if (!F.empty(shopDeliverApply.getAccountId())) {
				whereHql += " and t.accountId = :accountId";
				params.put("accountId", shopDeliverApply.getAccountId());
			}
			if (shopDeliverApply.getShopIds() != null && shopDeliverApply.getShopIds().length > 0) {
				whereHql += " and t.shopId in (:shopIds)";
				params.put("shopIds", shopDeliverApply.getShopIds());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(ShopDeliverApply shopDeliverApply) {
		TshopDeliverApply t = new TshopDeliverApply();
		BeanUtils.copyProperties(shopDeliverApply, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		if(F.empty(shopDeliverApply.getStatus())) t.setStatus(DAS_01);
		shopDeliverApplyDao.save(t);
	}

	@Override
	public ShopDeliverApply get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TshopDeliverApply t = shopDeliverApplyDao.get("from TshopDeliverApply t  where t.id = :id", params);
		ShopDeliverApply o = new ShopDeliverApply();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(ShopDeliverApply shopDeliverApply) {
		TshopDeliverApply t = shopDeliverApplyDao.get(TshopDeliverApply.class, shopDeliverApply.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(shopDeliverApply, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		shopDeliverApplyDao.executeHql("update TshopDeliverApply t set t.isdeleted = 1 where t.id = :id",params);
		//shopDeliverApplyDao.delete(shopDeliverApplyDao.get(TshopDeliverApply.class, id));
	}

	@Override
	public ShopDeliverApply getByAccountId(Integer accountId) {
		ShopDeliverApply o = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountId", accountId);
		TshopDeliverApply t = shopDeliverApplyDao.get("from TshopDeliverApply t where t.isdeleted = 0 and t.accountId = :accountId", params);
		if(t != null) {
			o = new ShopDeliverApply();
			BeanUtils.copyProperties(t, o);
		}

		return o;
	}

	@Override
	public DataGrid dataGridWithName(ShopDeliverApply shopDeliverApply, PageHelper ph) {
		DataGrid dataGrid = dataGrid(shopDeliverApply, ph);
		List<ShopDeliverApply> shopDeliverApplies = dataGrid.getRows();
		if (CollectionUtils.isNotEmpty(shopDeliverApplies)) {
			List<ShopDeliverApplyQuery> shopDeliverApplyQueries = new ArrayList<ShopDeliverApplyQuery>();
			for (ShopDeliverApply deliverApply : shopDeliverApplies) {
				ShopDeliverApplyQuery shopDeliverApplyQuery = new ShopDeliverApplyQuery();
				BeanUtils.copyProperties(deliverApply, shopDeliverApplyQuery);
				MbShop shop = mbShopService.getFromCache(deliverApply.getShopId());
				shopDeliverApplyQuery.setShopName(shop.getName());
				shopDeliverApplyQueries.add(shopDeliverApplyQuery);
			}
			DataGrid dg = new DataGrid();
			dg.setRows(shopDeliverApplyQueries);
			dg.setTotal(dataGrid.getTotal());
			return dg;
		}
		return dataGrid;
	}

	@Override
	public ShopDeliverApplyQuery getViewMessage(Integer id) {
		ShopDeliverApply shopDeliverApply = get(id);
		ShopDeliverApplyQuery shopDeliverApplyQuery = new ShopDeliverApplyQuery();
		BeanUtils.copyProperties(shopDeliverApply, shopDeliverApplyQuery);
		MbShop shop = mbShopService.getFromCache(shopDeliverApply.getShopId());
		shopDeliverApplyQuery.setShopName(shop.getName());
		shopDeliverApplyQuery.setDeliveryTypeName(shopDeliverApply.getDeliveryType());
		shopDeliverApplyQuery.setDeliveryWayName(shopDeliverApply.getDeliveryWay());
		return shopDeliverApplyQuery;
	}

	@Override
	public List<ShopDeliverApply> getAvailableAndWorkShop(BigDecimal longitude,BigDecimal latitude) {
		ShopDeliverApply shopDeliverApply = new ShopDeliverApply();
		PageHelper ph = new PageHelper();
		ph.setHiddenTotal(true);
//		shopDeliverApply.setOnline(true);
		shopDeliverApply.setFrozen(false); // 未冻结
		shopDeliverApply.setStatus(DAS_02);
		DataGrid dataGrid = dataGrid(shopDeliverApply, ph);
		List<ShopDeliverApply> shopDeliverApplyList = dataGrid.getRows();
		Iterator<ShopDeliverApply> iterator = shopDeliverApplyList.iterator();
		while (iterator.hasNext()) {
			ShopDeliverApply deliverApply = iterator.next();
			MbShop shop = mbShopService.getFromCache(deliverApply.getShopId());
			boolean flag = false;
			if (MbShopServiceI.AS_02.equals(shop.getAuditStatus()) && (MbShopServiceI.ST01.equals(shop.getShopType()) || MbShopServiceI.ST03.equals(shop.getShopType()))) {
				if (shop.getLatitude() != null && shop.getLongitude() != null) {
                    flag = true;
                    if (!F.empty(deliverApply.getDistributeRange())) {
                        DistributeRangeMap distributeRangeMap = new DistributeRangeMap();
                        distributeRangeMap.setLng(longitude.doubleValue());
                        distributeRangeMap.setLat(latitude.doubleValue());
                        JSONArray json = JSONArray.fromObject(deliverApply.getDistributeRange());
                        //把json字符串转换成对象
                        List<DistributeRangeMap> distributeRangeMaps = (List<DistributeRangeMap>) JSONArray.toCollection(json, DistributeRangeMap.class);
                        flag = chechPointInPolygon(distributeRangeMap, distributeRangeMaps);
                    }
                    deliverApply.setMbShop(shop);
				}
			}
			//不满足的删除掉
			if (!flag) {
				iterator.remove();
			}
		}
		return shopDeliverApplyList;
	}

	@Override
	public List<ShopDeliverApply> query(ShopDeliverApply shopDeliverApply) {
		List<ShopDeliverApply> ol = new ArrayList<ShopDeliverApply>();
		String hql = " from TshopDeliverApply t ";
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(shopDeliverApply, params);
		List<TshopDeliverApply> l = shopDeliverApplyDao.find(hql + where, params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TshopDeliverApply t : l) {
				ShopDeliverApply o= new ShopDeliverApply();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public List<MbAssignShop> queryAssignShopList(DeliverOrder deliverOrder) {
		//1、查询开通了派单功能,且为直营或者加盟的门店，包括在线或不在线的门店
		List<ShopDeliverApply> shopDeliverApplyList = getAvailableAndWorkShop(deliverOrder.getLongitude(),deliverOrder.getLatitude());
		//2、获取最大配送距离
		BigDecimal maxDistance = new BigDecimal(ConvertNameUtil.getString("DSV200", "5000"));
		List<MbAssignShop> mbAssignShopArrayList = new ArrayList<MbAssignShop>();
		//3、首先判断是否能解析派送地址，能，则计算符合配送距离的门店
		if (deliverOrder.getLatitude() != null && deliverOrder.getLongitude() != null) {
			for (ShopDeliverApply shopDeliverApply : shopDeliverApplyList) {
				MbShop mbShop = shopDeliverApply.getMbShop();
				double distance = GeoUtil.getDistance(deliverOrder.getLongitude().doubleValue(), deliverOrder.getLatitude().doubleValue(), mbShop.getLongitude().doubleValue(), mbShop.getLatitude().doubleValue());
				if (shopDeliverApply.getMaxDeliveryDistance() != null) {
					maxDistance = shopDeliverApply.getMaxDeliveryDistance();
				}
				if (distance > maxDistance.doubleValue()) continue;
				MbAssignShop mbAssignShop = new MbAssignShop();
				BeanUtils.copyProperties(mbShop, mbAssignShop);
				mbAssignShop.setDistance(BigDecimal.valueOf(distance));
				mbAssignShopArrayList.add(mbAssignShop);
			}
		}
		//4、不管是否有符合派送距离的门店，都要添加兜底门店
		String shopIds = ConvertNameUtil.getString("DSV800");
		if (!F.empty(shopIds)) {
			for (String shopId : shopIds.split(",")) {
				MbShop mbShop = mbShopService.getFromCache(Integer.parseInt(shopId));
				MbAssignShop mbAssignShop = new MbAssignShop();
				BeanUtils.copyProperties(mbShop, mbAssignShop);
				double distance = GeoUtil.getDistance(deliverOrder.getLongitude().doubleValue(), deliverOrder.getLatitude().doubleValue(), mbShop.getLongitude().doubleValue(), mbShop.getLatitude().doubleValue());
				mbAssignShop.setContactPhone("<font color='red'>" + mbShop.getContactPhone() + "</font>");
				mbAssignShop.setDistance(BigDecimal.valueOf(distance));
				mbAssignShopArrayList.add(mbAssignShop);
			}
		}
		Collections.sort(mbAssignShopArrayList);
		return mbAssignShopArrayList;
	}

	@Override
	public Boolean chechPointInPolygon(DistributeRangeMap distributeRangeMap, List<DistributeRangeMap> distributeRangeMaps) {
        int size = distributeRangeMaps.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;   //cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        DistributeRangeMap pointOne, pointTwo;
        DistributeRangeMap point = distributeRangeMap;
        pointOne = distributeRangeMaps.get(0);//left vertex
        for (int i = 1; i <= size; ++i) {//check all rays
            if (point.equals(pointOne)) {
                return boundOrVertex;//p is an vertex
            }
            pointTwo = distributeRangeMaps.get(i % size);//right vertex
            if (point.getLng() < Math.min(pointOne.getLng(), pointTwo.getLng()) || point.getLng() > Math.max(pointOne.getLng(), pointTwo.getLng())) {//ray is outside of our interests
                pointOne = pointTwo;
                continue;//next ray left point
            }
            if (point.getLng() > Math.min(pointOne.getLng(), pointTwo.getLng()) && point.getLng() < Math.max(pointOne.getLng(), pointTwo.getLng())) {//ray is crossing over by the algorithm (common part of)
                if (point.getLat() <= Math.max(pointOne.getLat(), pointTwo.getLat())) {//x is before of ray
                    if (pointOne.getLng() == pointTwo.getLng() && point.getLat() >= Math.min(pointOne.getLat(), pointTwo.getLat())) {//overlies on a horizontal ray
                        return boundOrVertex;
                    }
                    if (pointOne.getLat() == pointTwo.getLat()) {//ray is vertical
                        if (pointOne.getLat() == point.getLat()) {//overlies on a vertical ray
                            return boundOrVertex;
                        } else {//before ray
                            ++intersectCount;
                        }
                    } else {//cross point on the left side
                        double xinters = (point.getLng() - pointOne.getLng()) * (pointTwo.getLat() - pointOne.getLat()) / (pointTwo.getLng() - pointOne.getLng()) + pointOne.getLat();//cross point of y
                        if (Math.abs(point.getLat() - xinters) < precision) {//overlies on a ray
                            return boundOrVertex;
                        }
                        if (point.getLat() < xinters) {//before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {//special case when ray is crossing through the vertex
                if (point.getLng() == pointTwo.getLng() && point.getLat() <= pointTwo.getLat()) {//p crossing over p2
                    DistributeRangeMap pointThree = distributeRangeMaps.get((i + 1) % size); //next vertex
                    if (point.getLng() >= Math.min(pointOne.getLng(), pointThree.getLng()) && point.getLng() <= Math.max(pointOne.getLng(), pointThree.getLng())) {//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            pointOne = pointTwo;//next ray left point
        }
        if (intersectCount % 2 == 0) {//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }
    }


}
