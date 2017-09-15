package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.SpecialtyCatalogDao;
import net.zdsoft.eisu.base.common.entity.SpecialtyCatalog;

public class SpecialtyCatalogDaoImpl extends BaseDao<SpecialtyCatalog> implements SpecialtyCatalogDao {

	@Override
	public SpecialtyCatalog setField(ResultSet rs) throws SQLException {
		SpecialtyCatalog specialtyCatalog = new SpecialtyCatalog();
		specialtyCatalog.setId(rs.getString("id"));
		specialtyCatalog.setUnitId(rs.getString("unit_id"));
		specialtyCatalog.setSpecialtyTypeId(rs.getString("specialty_type_id"));
		specialtyCatalog.setSpecialtyCode(rs.getString("specialty_code"));
		specialtyCatalog.setSpecialtyName(rs.getString("specialty_name"));
		specialtyCatalog.setCreationTime(rs.getTimestamp("creation_time"));
		return specialtyCatalog;
	}

	@Override
	public List<SpecialtyCatalog> getBySpecialtyTypeId(String specialtyTypeId) {
		return query("select * from base_specialty_catalog where specialty_type_id=?", specialtyTypeId, new MultiRow());
	}
}
