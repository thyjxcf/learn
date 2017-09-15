package net.zdsoft.eis.base.common.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import net.zdsoft.eis.base.common.dao.FieldsDisplayDao;
import net.zdsoft.eis.base.common.entity.FieldsDisplay;
import net.zdsoft.eis.base.common.service.FieldsDisplayService;
import net.zdsoft.eis.base.constant.BaseConstant;

/**
 * 
 *@author weixh
 *@since 2013-3-7
 */
public class FieldsDisplayServiceImpl implements FieldsDisplayService {
	private FieldsDisplayDao fieldsDisplayDao;

	public void setFieldsDisplayDao(FieldsDisplayDao fieldsDisplayDao) {
		this.fieldsDisplayDao = fieldsDisplayDao;
	}

	public List<FieldsDisplay> getColsDisplays(String unitId, String type) {
		List<FieldsDisplay> display = fieldsDisplayDao.getColsDisplays(unitId, type);
		if(CollectionUtils.isEmpty(display) && !BaseConstant.ZERO_GUID.equals(unitId)){
			display = fieldsDisplayDao.getColsDisplays(BaseConstant.ZERO_GUID, type);
		}
		return display;
	}

	public List<FieldsDisplay> getColsDisplays(String unitId, String type,
			Integer colsUse) {
		List<FieldsDisplay> display = fieldsDisplayDao.getColsDisplays(unitId, type, colsUse);
		if(CollectionUtils.isEmpty(display) && !BaseConstant.ZERO_GUID.equals(unitId)){
			display = fieldsDisplayDao.getColsDisplays(BaseConstant.ZERO_GUID, type, colsUse);
		}
		return display;
	}

}
