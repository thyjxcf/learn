package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.dao.BaseSchtypeSectionDao;
import net.zdsoft.eis.base.data.entity.SchtypeSection;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;

import org.apache.commons.lang.StringUtils;

/**
 * @author yanb
 * 
 */
public class BaseSchtypeSectionDaoImpl extends BaseDao<SchtypeSection> implements
        BaseSchtypeSectionDao {

    private static final String SQL_INSERT_SCHTYPESECTION = "INSERT INTO base_schtype_section(id,school_type,section) "
            + "VALUES(?,?,?)";

    private static final String SQL_DELETE_SCHTYPESECTION_BY_IDS = "DELETE base_schtype_section WHERE id IN ";

    private static final String SQL_UPDATE_SCHTYPESECTION = "UPDATE base_schtype_section SET school_type=?,section=? WHERE id=?";

    private static final String SQL_FIND_SCHTYPESECTION_BY_ID = "SELECT * FROM base_schtype_section WHERE id=?";

    private static final String SQL_FIND_SCHTYPESECTIONS_BY_IDS = "SELECT * FROM base_schtype_section WHERE id IN";

    private static final String SQL_FIND_SCHTYPESECTIONS = "SELECT * FROM base_schtype_section ";

    public SchtypeSection setField(ResultSet rs) throws SQLException {
        SchtypeSection schtypeSection = new SchtypeSection();
        schtypeSection.setId(rs.getString("id"));
        schtypeSection.setSchoolType(rs.getString("school_type"));
        schtypeSection.setSection(rs.getString("section"));
        return schtypeSection;
    }

    public void insertSchtypeSection(SchtypeSection schtypeSection) {
        if (StringUtils.isNotBlank(schtypeSection.getId()))
            schtypeSection.setId(createId());
        update(SQL_INSERT_SCHTYPESECTION, new Object[] { schtypeSection.getId(),
                schtypeSection.getSchoolType(), schtypeSection.getSection() }, new int[] {
                Types.CHAR, Types.CHAR, Types.VARCHAR });
    }

    public void deleteSchtypeSection(String[] schtypeSectionIds, EventSourceType eventSource) {
        updateForInSQL(SQL_DELETE_SCHTYPESECTION_BY_IDS, null, schtypeSectionIds);
    }

    public void updateSchtypeSection(SchtypeSection schtypeSection) {
        update(SQL_UPDATE_SCHTYPESECTION, new Object[] { schtypeSection.getSchoolType(),
                schtypeSection.getSection(), schtypeSection.getId() }, new int[] { Types.CHAR,
                Types.VARCHAR, Types.CHAR });
    }

    public SchtypeSection getSchtypeSection(String schtypeSectionId) {
        return (SchtypeSection) query(SQL_FIND_SCHTYPESECTION_BY_ID, schtypeSectionId,
                new SingleRow());
    }

    public Map<String, SchtypeSection> getSchtypeSections(String[] schtypeSectionIds) {
        return (Map<String, SchtypeSection>) queryForInSQL(SQL_FIND_SCHTYPESECTIONS_BY_IDS, null,
                schtypeSectionIds, new MapRow());
    }

    public List<SchtypeSection> getSchtypeSections() {
        return (List<SchtypeSection>) query(SQL_FIND_SCHTYPESECTIONS, null, null, new MultiRow());
    }

}
