package net.zdsoft.eis.base.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;
import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

import org.apache.commons.lang.StringUtils;

public class FieldServiceImpl extends DefaultCacheManager implements FieldService {
	private FieldDao fieldDao;

	/**
	 * 获取类表
	 * @param businessType
	 * @return
	 */
	public List<Field> getFiledList(final int businessType){
		return getEntitiesFromCache(new CacheEntitiesParam<Field>(){

			@Override
			public List<Field> fetchObjects() {
				Map<String, Field> fieldMap = fieldDao.getFieldMap(businessType);
				List<Field> fieldList = new ArrayList<Field>();
				Set<String> fieldSet = fieldMap.keySet();
				for (String key : fieldSet) {
					Field field = fieldMap.get(key);
					
					//判断是否是子列
					if (StringUtils.isNotBlank(field.getParentValue())) continue;
					
					//判断是否存在子列
					if (StringUtils.isNotBlank(field.getChildName())){
						Field childField = fieldMap.get(field.getChildName());
						if (childField != null) field.setChildField(childField);
					}
					fieldList.add(field);
				}
				
				Collections.sort(fieldList, new Comparator<Field>() {
					@Override
					public int compare(Field o1, Field o2) {
						return o1.getOrder() - o2.getOrder();
					}
				});
				return fieldList;
			}

			@Override
			public String fetchKey() {
				return BaseCacheConstants.EIS_BUSINESS_TYPE + businessType;
			}
			
		});
	}
	
	

	@Override
	public List<Field> getFiledList(final int businessType,final ApplyBusinessService<ApplyBusiness> applyBusinessService) {
		List<Field> fields = getFiledList(businessType);
		applyBusinessService.operateField(fields);
		return fields;
	}
	

	/**
	 * 列表标题
	 * 
	 * @return
	 */
	public List<Field> getListFieldHeads(int businessType) {
		List<Field> list = new ArrayList<Field>();
		for (Field field : getFiledList(businessType)) {
			if (field.isListShow()) {
				list.add(field);
			}
		}
		return list;
	}
	
	public void setFieldDao(FieldDao fieldDao) {
		this.fieldDao = fieldDao;
	}


	
}
