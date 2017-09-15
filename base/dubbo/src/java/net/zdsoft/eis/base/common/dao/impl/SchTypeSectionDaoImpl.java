package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.basedata.remote.service.SchtypeSectionRemoteService;
import net.zdsoft.eis.base.common.dao.SchTypeSectionDao;
import net.zdsoft.eis.base.data.entity.SchtypeSection;
import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.SingleRowMapper;

public class SchTypeSectionDaoImpl extends BasicDAO implements
		SchTypeSectionDao {

	@Autowired
	private SchtypeSectionRemoteService sectionRemoteService;
	
	private static final String SQL_FIND_SECTION_BY_SCHOOLTYPE = "select section from base_schtype_section where school_type=?";
	@Override
	public String getSections(String schoolType) {
		SchtypeSection schtypeSection = SchtypeSection.dc(sectionRemoteService.findBySchoolType(schoolType));
		return schtypeSection.getSection();
		/*return query(SQL_FIND_SECTION_BY_SCHOOLTYPE, schoolType, new SingleRowMapper<String>() {

            public String mapRow(ResultSet rs) throws SQLException {
                return rs.getString("section");
            }
        });*/
	}
}
