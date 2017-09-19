package com.mobian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.Task;
import com.mobian.dao.FmPropertiesDaoI;
import com.mobian.enums.FieldType;
import com.mobian.model.TfmProperties;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.FmOptions;
import com.mobian.pageModel.FmProperties;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.FmOptionsServiceI;
import com.mobian.service.FmPropertiesServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FmPropertiesServiceImpl extends BaseServiceImpl<FmProperties> implements FmPropertiesServiceI {

	@Autowired
	private FmPropertiesDaoI fmPropertiesDao;

	@Autowired
	private FmOptionsServiceI fmOptionsService;

	@Override
	public DataGrid dataGrid(FmProperties fmProperties, PageHelper ph) {
		List<FmProperties> ol = new ArrayList<FmProperties>();
		String hql = " from TfmProperties t ";
		DataGrid dg = dataGridQuery(hql, ph, fmProperties, fmPropertiesDao);
		@SuppressWarnings("unchecked")
		List<TfmProperties> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TfmProperties t : l) {
				FmProperties o = new FmProperties();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public List<FmProperties> query(FmProperties fmProperties) {
		List<FmProperties> ol = new ArrayList<FmProperties>();
		String hql = " from TfmProperties t ";
		@SuppressWarnings("unchecked")
		List<TfmProperties> l = query(hql, fmProperties, fmPropertiesDao);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TfmProperties t : l) {
				FmProperties o = new FmProperties();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public List<FmProperties> getByGoodsId(String goodsId) {
		FmProperties fmProperties = new FmProperties();
		fmProperties.setGoodName(goodsId);
		List<FmProperties> fmPropertiesList = query(fmProperties);
		if(!CollectionUtils.isEmpty(fmPropertiesList)){
			final CompletionService completionService = CompletionFactory.initCompletion();
			Collections.sort(fmPropertiesList, new Comparator<FmProperties>() {
				@Override
				public int compare(FmProperties o1, FmProperties o2) {
					int seq1 = o1.getSeq() == null ? 0 : o1.getSeq();
					int seq2 = o2.getSeq() == null ? 0 : o2.getSeq();
					return seq1 - seq2;
				}
			});
			for (FmProperties properties : fmPropertiesList) {

				completionService.submit(new Task<FmProperties, List<FmOptions>>(properties){
					@Override
					public List<FmOptions> call() throws Exception {
						FmOptions fmOptions = new FmOptions();
						fmOptions.setPropertiesId(getD().getId());
						return fmOptionsService.query(fmOptions);
					}
					protected void set(FmProperties d, List<FmOptions> v) {
						Collections.sort(v, new Comparator<FmOptions>() {
							@Override
							public int compare(FmOptions o1, FmOptions o2) {
								int seq1 = o1.getSeq() == null ? 0 : o1.getSeq();
								int seq2 = o2.getSeq() == null ? 0 : o2.getSeq();
								return seq1 - seq2;
							}
						});
						d.setFmOptionsList(v);
					}
				});
			}
			completionService.sync();
		}
		return fmPropertiesList;
	}

	@Override
	public String getAfterMatching(String goodsId, JSONObject json,String format) {
		List<FmProperties> fmPropertiesList = getByGoodsId(goodsId);
		StringBuffer sb = new StringBuffer();
		for (FmProperties fmProperties : fmPropertiesList) {
			String fieldType = fmProperties.getFieldType();
			if (fieldType == null || FieldType.OPTION.getType().equals(fieldType)) {
				sb.append(String.format(format,fmProperties.getName(),getValueName(json,fmProperties)));
			} else if (FieldType.TEXT.getType().equals(fieldType)) {
				String value = json.getString(fmProperties.getId());
				sb.append(String.format(format,fmProperties.getName(),value == null ? "" : value));
			} else if (FieldType.BOOLEAN.getType().equals(fieldType)) {
				format = format.replaceFirst("%s:%s","%s");
				sb.append(String.format(format,getValueName(json, fmProperties)+fmProperties.getName()));
			}
		}
		return sb.toString();
	}

	private String getValueName(JSONObject json,FmProperties fmProperties){
		String name = "";
		String value = json.getString(fmProperties.getId());
		for (FmOptions fmOptions : fmProperties.getFmOptionsList()) {
			if(fmOptions.getId().equals(value)){
				name = fmOptions.getValue();
				break;
			}
		}
		return name;
	}

	protected String whereHql(FmProperties fmProperties, Map<String, Object> params) {
		String whereHql = "";	
		if (fmProperties != null) {
			whereHql += " where t.isdeleted = 0 ";

			if (!F.empty(fmProperties.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", fmProperties.getName());
			}		
			if (!F.empty(fmProperties.getGoodName())) {
				whereHql += " and t.goodName = :goodName";
				params.put("goodName", fmProperties.getGoodName());
			}		
			if (!F.empty(fmProperties.getDescription())) {
				whereHql += " and t.description = :description";
				params.put("description", fmProperties.getDescription());
			}		
			if (!F.empty(fmProperties.getFieldType())) {
				whereHql += " and t.fieldType = :fieldType";
				params.put("fieldType", fmProperties.getFieldType());
			}		

			if (!F.empty(fmProperties.getDefaultValue())) {
				whereHql += " and t.defaultValue = :defaultValue";
				params.put("defaultValue", fmProperties.getDefaultValue());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(FmProperties fmProperties) {
		TfmProperties t = new TfmProperties();
		BeanUtils.copyProperties(fmProperties, t);
		t.setId(com.mobian.absx.UUID.uuid());
		t.setIsdeleted(false);
		fmPropertiesDao.save(t);
	}

	@Override
	public FmProperties get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TfmProperties t = fmPropertiesDao.get("from TfmProperties t  where t.id = :id", params);
		FmProperties o = new FmProperties();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(FmProperties fmProperties) {
		TfmProperties t = fmPropertiesDao.get(TfmProperties.class, fmProperties.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(fmProperties, t, new String[]{"id", "addtime", "isdeleted", "updatetime"}, true);
		}
	}

	@Override
	public void delete(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		fmPropertiesDao.executeHql("update TfmProperties t set t.isdeleted = 1 where t.id = :id",params);
		//fmPropertiesDao.delete(fmPropertiesDao.get(TfmProperties.class, id));
	}

}
