package net.zdsoft.eisu.base.common.service;

import java.util.List;

import net.zdsoft.eisu.base.common.entity.SpecialtyCatalog;

public interface SpecialtyCatalogService {
	
	public List<SpecialtyCatalog> getBySpecialtyTypeId(String specialtyTypeId);

}
