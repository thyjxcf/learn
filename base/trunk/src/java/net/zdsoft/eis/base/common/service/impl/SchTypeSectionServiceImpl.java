package net.zdsoft.eis.base.common.service.impl;

import net.zdsoft.eis.base.common.dao.SchTypeSectionDao;
import net.zdsoft.eis.base.common.service.SchTypeSectionService;

public class SchTypeSectionServiceImpl implements SchTypeSectionService {

	private SchTypeSectionDao schTypeSectionDao;
	@Override
	public String getSections(String schoolType) {
		return schTypeSectionDao.getSections(schoolType);
	}
	public void setSchTypeSectionDao(SchTypeSectionDao schTypeSectionDao) {
		this.schTypeSectionDao = schTypeSectionDao;
	}

}
