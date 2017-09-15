package net.zdsoft.eis.base.common.service.impl;

import java.util.List;

import net.zdsoft.eis.base.common.dao.BaseCourseDao;
import net.zdsoft.eis.base.common.entity.BaseCourse;
import net.zdsoft.eis.base.common.service.BaseCourseService;

public class BaseCourseServiceImpl implements BaseCourseService {
	private BaseCourseDao baseCourseDao;
	
	public void setBaseCourseDao(BaseCourseDao baseCourseDao) {
		this.baseCourseDao = baseCourseDao;
	}
	@Override
	public List<BaseCourse> getBaseCoureList(String unitid) {
		// TODO Auto-generated method stub
		return baseCourseDao.getBaseCoureList(unitid);
	}
	
	public BaseCourse getBaseCourseByCode(String Code){
		return baseCourseDao.getBaseCourseByCode(Code);
	}
	
	public List<BaseCourse> getBaseCoursesByIds(String[] ids){
		return baseCourseDao.getBaseCoursesByIds(ids);
	}
}
