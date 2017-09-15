package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.dao.ResearchGroupDao;
import net.zdsoft.eis.base.data.entity.ResearchGroup;
import net.zdsoft.eis.frame.client.BaseDao;

public class ResearchGroupDaoImpl extends BaseDao<ResearchGroup> implements ResearchGroupDao{

	private static final String SQL_FIND_RESEARCHGROUP_BY_UNITID = "SELECT * FROM base_teach_group WHERE school_id=? AND is_deleted = '0' ORDER BY creation_time DESC";
	private static final String SQL_FIND_NAMES_BY_UNITID = "SELECT * FROM base_teach_group WHERE school_id=?";
	private static final String SQL_INSERT_RESEARCHGROUP = "INSERT INTO base_teach_group(id,school_id,teach_group_name,"
			+ "subject_id,creation_time,modify_time,is_deleted) VALUES(?,?,?,?,?,?,?)";
	private static final String SQL_DELETE_RESEARCHGROUP_BY_IDS = "UPDATE base_teach_group SET is_deleted = '1',modify_time = ? WHERE id IN ";
	private static final String SQL_FIND_RESEARCHGROUP_BY_ID = "select * from base_teach_group where id = ? and is_deleted = 0 ";
	
	private static final String SQL_UPDATE_RESEARCHGROUP = "UPDATE base_teach_group SET teach_group_name=?,"
            + "subject_id=?, modify_time=? WHERE id=?";
	private static final String SQL_FIND_NAMES_BY_IDS = "SELECT * FROM base_teach_group WHERE is_deleted = '0' and id IN";
	
	@Override
	public List<ResearchGroup> getResearchGroups(String unitId) {
		 return query(SQL_FIND_RESEARCHGROUP_BY_UNITID, unitId, new MultiRow());
	}

	@Override
	public ResearchGroup setField(ResultSet rs) throws SQLException {
		ResearchGroup researchGroup = new ResearchGroup();
		researchGroup.setId(rs.getString("id"));
		researchGroup.setSchoolId(rs.getString("school_id"));
		researchGroup.setTeachGroupName(rs.getString("teach_group_name"));
		researchGroup.setSubjectId(rs.getString("subject_id"));
		researchGroup.setCreationTime(rs.getTimestamp("creation_time"));
		researchGroup.setModifyTime(rs.getTimestamp("modify_time"));
		researchGroup.setIsdeleted(rs.getInt("is_deleted"));
		return researchGroup;
	}

	@Override
	public List<ResearchGroup> getTeachGroupNames(String unitId) {
		return query(SQL_FIND_NAMES_BY_UNITID, unitId, new MultiRow());
	}

	@Override
	public List<ResearchGroup> getResearchGroupByIds(String[] researchGroupIds) {
		return queryForInSQL(SQL_FIND_NAMES_BY_IDS, null, researchGroupIds,new MultiRow());
	}
	
	@Override
	public void save(ResearchGroup researchGroup) {
		update(SQL_INSERT_RESEARCHGROUP,
				new Object[] { researchGroup.getId(), researchGroup.getSchoolId(),
						researchGroup.getTeachGroupName(), researchGroup.getSubjectId(),
						researchGroup.getCreationTime(), researchGroup.getModifyTime(),
						researchGroup.getIsdeleted()}, new int[] {
						Types.CHAR, Types.CHAR, Types.VARCHAR,
						Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP, 
						Types.INTEGER,});
	}

	@Override
	public void delete(String[] ids) {
		updateForInSQL(SQL_DELETE_RESEARCHGROUP_BY_IDS, new Object[] {new Date()}, ids);
	}

	@Override
	public ResearchGroup getResearchGroup(String id) {
		return query(SQL_FIND_RESEARCHGROUP_BY_ID, new Object[]{id}, new SingleRow());
	}

	@Override
	public void update(ResearchGroup researchGroup) {
        update(SQL_UPDATE_RESEARCHGROUP, new Object[] { researchGroup.getTeachGroupName(), researchGroup.getSubjectId(),
        		researchGroup.getModifyTime(), researchGroup.getId() }, new int[] { Types.VARCHAR,
                Types.VARCHAR, Types.TIMESTAMP, Types.CHAR});
	}


}
