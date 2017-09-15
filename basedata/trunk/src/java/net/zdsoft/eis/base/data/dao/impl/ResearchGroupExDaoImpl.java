package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.dao.ResearchGroupExDao;
import net.zdsoft.eis.base.data.entity.ResearchGroupEx;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MapRow;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;

public class ResearchGroupExDaoImpl extends BaseDao<ResearchGroupEx> implements ResearchGroupExDao{

	private static final String SQL_FIND_RESEARCHGROUPEX_BY_ID = "SELECT * FROM base_teach_group_ex WHERE teach_group_id=?";
	private static final String SQL_INSERT_RESEARCHGROUPEX = "INSERT INTO base_teach_group_ex(id,teach_group_id,"
			+ "type,teacher_id) VALUES(?,?,?,?)";
	private static final String SQL_DELETE_RESEARCHGROUPEX_BY_ID = "DELETE FROM base_teach_group_ex WHERE teach_group_id=?";
	private static final String SQL_DELETE_RESEARCHGROUPEX_BY_GROUPID = "DELETE FROM base_teach_group_ex WHERE id=?";
	private static final String SQL_FIND_RESEARCHGROUPEX_BY_TEACHER_ID = "SELECT * FROM base_teach_group_ex WHERE teacher_id=?";
	
	@Override
	public ResearchGroupEx setField(ResultSet rs) throws SQLException {
		ResearchGroupEx researchGroupEx = new ResearchGroupEx();
		researchGroupEx.setId(rs.getString("id"));
		researchGroupEx.setTeachGroupId(rs.getString("teach_group_id"));
		researchGroupEx.setType(rs.getInt("type"));
		researchGroupEx.setTeacherId(rs.getString("teacher_id"));
		return researchGroupEx;
	}

	@Override
	public List<ResearchGroupEx> getResearchGroupExs(String id) {
		return query(SQL_FIND_RESEARCHGROUPEX_BY_ID, id, new MultiRow());
	}
	
	@Override
	public List<ResearchGroupEx> getresearchGroupExList(String id) {
		return query(SQL_FIND_RESEARCHGROUPEX_BY_TEACHER_ID, id, new MultiRow());
	}

	@Override
	public void save(ResearchGroupEx researchGroupEx) {
		update(SQL_INSERT_RESEARCHGROUPEX,
			new Object[] { researchGroupEx.getId(), researchGroupEx.getTeachGroupId(),
				researchGroupEx.getType(), researchGroupEx.getTeacherId()}, new int[] {
				Types.CHAR, Types.CHAR, Types.INTEGER,Types.CHAR});
	}

	@Override
	public void delete(String id) {
		update(SQL_DELETE_RESEARCHGROUPEX_BY_ID, new Object[] {id});
	}

	@Override
	public void deleteByid(String id) {
		update(SQL_DELETE_RESEARCHGROUPEX_BY_GROUPID, new Object[] {id});
	}


}
