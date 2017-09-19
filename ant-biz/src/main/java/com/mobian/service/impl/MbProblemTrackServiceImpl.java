package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.MbProblemTrackDaoI;
import com.mobian.model.TmbProblemTrack;
import com.mobian.pageModel.*;
import com.mobian.service.MbProblemTrackServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.util.ConfigUtil;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MbProblemTrackServiceImpl extends BaseServiceImpl<MbProblemTrack> implements MbProblemTrackServiceI {

	@Autowired
	private MbProblemTrackDaoI mbProblemTrackDao;
	@Autowired
	private UserServiceI userService;

	@Override
	public DataGrid dataGrid(MbProblemTrack mbProblemTrack, PageHelper ph) {
		List<MbProblemTrack> ol = new ArrayList<MbProblemTrack>();
		String hql = " from TmbProblemTrack t ";
		DataGrid dg = dataGridQuery(hql, ph, mbProblemTrack, mbProblemTrackDao);
		@SuppressWarnings("unchecked")
		List<TmbProblemTrack> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmbProblemTrack t : l) {
				MbProblemTrack o = new MbProblemTrack();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	public DataGrid dataGridWithSetName(MbProblemTrack mbProblemTrack, PageHelper ph){
		DataGrid dataGrid=dataGrid(mbProblemTrack,ph);
		List<MbProblemTrack> mbProblemTracks=dataGrid.getRows();
		for(MbProblemTrack problemTrack: mbProblemTracks){
			if (problemTrack.getOwnerId() != null) {
				User user = userService.getFromCache(problemTrack.getOwnerId());
				if (user != null) {
					problemTrack.setOwnerName(user.getName());
				}
			}
			if(problemTrack.getDetails()!=null&&problemTrack.getDetails().length()>=5){
				String detail=problemTrack.getDetails().substring(0,4);
				problemTrack.setDetails(detail+"......");
			}
		}
		return  dataGrid;
	}
	public DataGrid orderProblemDataGrid(MbProblemTrack mbProblemTrack, PageHelper ph, HttpSession session){
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		mbProblemTrack.setOwnerId(sessionInfo.getId());
		DataGrid dataGrid=dataGrid(mbProblemTrack,ph);
		List<MbProblemTrack> mbProblemTracks=dataGrid.getRows();
		for(MbProblemTrack problemTrack: mbProblemTracks){
			if(sessionInfo.getName()!=null) {
				problemTrack.setOwnerName(sessionInfo.getName());
			}
		}
		return dataGrid;
	}

	protected String whereHql(MbProblemTrack mbProblemTrack, Map<String, Object> params) {
		String whereHql = "";	
		if (mbProblemTrack != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(mbProblemTrack.getTenantId())) {
				whereHql += " and t.tenantId = :tenantId";
				params.put("tenantId", mbProblemTrack.getTenantId());
			}		
			if (!F.empty(mbProblemTrack.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", mbProblemTrack.getIsdeleted());
			}		
			if (!F.empty(mbProblemTrack.getTitle())) {
				whereHql += " and t.title = :title";
				params.put("title", mbProblemTrack.getTitle());
			}		
			if (!F.empty(mbProblemTrack.getDetails())) {
				whereHql += " and t.details = :details";
				params.put("details", mbProblemTrack.getDetails());
			}		
			if (!F.empty(mbProblemTrack.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", mbProblemTrack.getStatus());
			}		
			if (!F.empty(mbProblemTrack.getOwnerId())) {
				whereHql += " and t.ownerId = :ownerId";
				params.put("ownerId", mbProblemTrack.getOwnerId());
			}		
			if (!F.empty(mbProblemTrack.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", mbProblemTrack.getRefType());
			}		
			if (!F.empty(mbProblemTrack.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", mbProblemTrack.getOrderId());
			}		
			if (!F.empty(mbProblemTrack.getLastOwnerId())) {
				whereHql += " and t.lastOwnerId = :lastOwnerId";
				params.put("lastOwnerId", mbProblemTrack.getLastOwnerId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MbProblemTrack mbProblemTrack) {
		TmbProblemTrack t = new TmbProblemTrack();
		BeanUtils.copyProperties(mbProblemTrack, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		mbProblemTrackDao.save(t);
	}

	@Override
	public MbProblemTrack get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmbProblemTrack t = mbProblemTrackDao.get("from TmbProblemTrack t  where t.id = :id", params);
		MbProblemTrack o = new MbProblemTrack();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MbProblemTrack mbProblemTrack) {
		TmbProblemTrack t = mbProblemTrackDao.get(TmbProblemTrack.class, mbProblemTrack.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(mbProblemTrack, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		mbProblemTrackDao.executeHql("update TmbProblemTrack t set t.isdeleted = 1 where t.id = :id",params);
		//mbProblemTrackDao.delete(mbProblemTrackDao.get(TmbProblemTrack.class, id));
	}

}
