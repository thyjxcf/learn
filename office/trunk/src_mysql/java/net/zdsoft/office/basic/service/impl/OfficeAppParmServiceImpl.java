package net.zdsoft.office.basic.service.impl;

import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.basic.dao.OfficeAppParmDao;
import net.zdsoft.office.basic.entity.OfficeAppParm;
import net.zdsoft.office.basic.service.OfficeAppParmService;
/**
 * office_app_parm(APP权限设置)
 * @author 
 * 
 */
public class OfficeAppParmServiceImpl implements OfficeAppParmService{
	private OfficeAppParmDao officeAppParmDao;
	
	public void setOfficeAppParmDao(OfficeAppParmDao officeAppParmDao) {
		this.officeAppParmDao = officeAppParmDao;
	}

	@Override
	public OfficeAppParm save(OfficeAppParm officeAppParm){
		return officeAppParmDao.save(officeAppParm);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAppParmDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAppParm officeAppParm){
		return officeAppParmDao.update(officeAppParm);
	}

	@Override
	public OfficeAppParm getOfficeAppParmByUnitId(String unitId, String type){
		return officeAppParmDao.getOfficeAppParmByUnitId(unitId, type);
	}

	public Map<String, OfficeAppParm> getMapByUnitId(String unitId, boolean isUsing){
		Map<String, OfficeAppParm> map = new HashMap<String, OfficeAppParm>();
		List<OfficeAppParm> list = officeAppParmDao.getListByUnitId(unitId, isUsing);
		for(OfficeAppParm ent : list){
			map.put(ent.getType(), ent);
		}
		return map;
	}
}