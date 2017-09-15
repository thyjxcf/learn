package net.zdsoft.eisu.base.common.dao;

import java.util.List;

import net.zdsoft.eisu.base.common.entity.SpecialtyCatalog;

public interface SpecialtyCatalogDao {
	
	public List<SpecialtyCatalog> getBySpecialtyTypeId(String specialtyTypeId);
}
