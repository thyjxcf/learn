package net.zdsoft.eis.base.common.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import net.zdsoft.eis.base.common.dao.BasicSubjectDao;
import net.zdsoft.eis.base.common.service.BasicSubjectService;

public class BasicSubjectServiceImpl implements BasicSubjectService {
	@Resource
	private BasicSubjectDao basicSubjectDao;
	@Override
	public Map<String, String> getSubjectMap(String unitId, int isUsing, String likeName) {
		return basicSubjectDao.getSubjectMap(unitId, isUsing, null);
	}
	@Override
	public Map<String, String> getSubjectMap(String[] subids) {
		return basicSubjectDao.getSubjectMap(subids);
	}
	@Override
	public Map<String, String> getSubjectMapNo(String[] subids) {
		return basicSubjectDao.getSubjectMapNo(subids);
	}


}
