package net.zdsoft.eisu.base.common.service.impl;

import java.util.List;

import net.zdsoft.eisu.base.common.dao.SpecialtyCatalogDao;
import net.zdsoft.eisu.base.common.entity.SpecialtyCatalog;
import net.zdsoft.eisu.base.common.service.SpecialtyCatalogService;

public class SpecialtyCatalogServiceImpl implements SpecialtyCatalogService {
	
	private SpecialtyCatalogDao specialtyCatalogDao;

	@Override
	public List<SpecialtyCatalog> getBySpecialtyTypeId(String specialtyTypeId) {
		return specialtyCatalogDao.getBySpecialtyTypeId(specialtyTypeId);
	}

	public void setSpecialtyCatalogDao(SpecialtyCatalogDao specialtyCatalogDao) {
		this.specialtyCatalogDao = specialtyCatalogDao;
	}

}
