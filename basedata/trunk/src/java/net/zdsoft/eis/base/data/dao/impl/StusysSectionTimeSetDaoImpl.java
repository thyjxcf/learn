package net.zdsoft.eis.base.data.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.dao.StusysSectionTimeSetDao;
import net.zdsoft.eis.base.data.entity.StusysSectionTimeSet;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.lang.StringUtils;
/**
 * stusys_section_time_set
 * @author 
 * 
 */
public class StusysSectionTimeSetDaoImpl extends BaseDao<StusysSectionTimeSet> implements StusysSectionTimeSetDao{

	@Override
	public StusysSectionTimeSet setField(ResultSet rs) throws SQLException{
		StusysSectionTimeSet stusysSectionTimeSet = new StusysSectionTimeSet();
		stusysSectionTimeSet.setId(rs.getString("id"));
		stusysSectionTimeSet.setAcadyear(rs.getString("acadyear"));
		stusysSectionTimeSet.setSemester(rs.getInt("semester"));
		stusysSectionTimeSet.setUnitId(rs.getString("unit_id"));
		stusysSectionTimeSet.setSectionNumber(rs.getInt("section_number"));
		stusysSectionTimeSet.setBeginTime(rs.getString("begin_time"));
		stusysSectionTimeSet.setEndTime(rs.getString("end_time"));
		stusysSectionTimeSet.setUserId(rs.getString("user_id"));
		return stusysSectionTimeSet;
	}
	
	@Override
	public StusysSectionTimeSet save(StusysSectionTimeSet stusysSectionTimeSet){
		String sql = "insert into stusys_section_time_set(id, acadyear, semester, unit_id, section_number, begin_time, end_time, user_id) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(stusysSectionTimeSet.getId())){
			stusysSectionTimeSet.setId(createId());
		}
		Object[] args = new Object[]{
			stusysSectionTimeSet.getId(), stusysSectionTimeSet.getAcadyear(), 
			stusysSectionTimeSet.getSemester(), stusysSectionTimeSet.getUnitId(), 
			stusysSectionTimeSet.getSectionNumber(), stusysSectionTimeSet.getBeginTime(), 
			stusysSectionTimeSet.getEndTime(), stusysSectionTimeSet.getUserId()
		};
		update(sql, args);
		return stusysSectionTimeSet;
	}
	
	@Override
	public void batchSave(List<StusysSectionTimeSet> stusysSectionTimeSetList) {
		String sql = "insert into stusys_section_time_set(id, acadyear, semester, unit_id, section_number, begin_time, end_time, user_id) values(?,?,?,?,?,?,?,?)";
		List<Object[]> list = new ArrayList<Object[]>(stusysSectionTimeSetList.size()); 
		for (int i = 0; i < stusysSectionTimeSetList.size(); i++) {
			StusysSectionTimeSet stusysSectionTimeSet = stusysSectionTimeSetList.get(i);
			if (StringUtils.isBlank(stusysSectionTimeSet.getId())){
				stusysSectionTimeSet.setId(createId());
			}
			Object[] args = new Object[]{
        			stusysSectionTimeSet.getId(), stusysSectionTimeSet.getAcadyear(), 
        			stusysSectionTimeSet.getSemester(), stusysSectionTimeSet.getUnitId(), 
        			stusysSectionTimeSet.getSectionNumber(), stusysSectionTimeSet.getBeginTime(), 
        			stusysSectionTimeSet.getEndTime(), stusysSectionTimeSet.getUserId()
        		};
            list.add(args);

        }
        int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.INTEGER, Types.CHAR,
                Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.CHAR };
		batchUpdate(sql, list, argTypes);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from stusys_section_time_set where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public void deleteByUnitId(String unitId) {
		String sql = "delete from stusys_section_time_set where unit_id = ?";
		update(sql, unitId);
	}

	@Override
	public Integer update(StusysSectionTimeSet stusysSectionTimeSet){
		String sql = "update stusys_section_time_set set acadyear = ?, semester = ?, unit_id = ?, section_number = ?, begin_time = ?, end_time = ?, user_id = ? where id = ?";
		Object[] args = new Object[]{
			stusysSectionTimeSet.getAcadyear(), stusysSectionTimeSet.getSemester(), 
			stusysSectionTimeSet.getUnitId(), stusysSectionTimeSet.getSectionNumber(), 
			stusysSectionTimeSet.getBeginTime(), stusysSectionTimeSet.getEndTime(), 
			stusysSectionTimeSet.getUserId(), stusysSectionTimeSet.getId()
		};
		return update(sql, args);
	}

	@Override
	public StusysSectionTimeSet getStusysSectionTimeSetById(String id){
		String sql = "select * from stusys_section_time_set where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, StusysSectionTimeSet> getStusysSectionTimeSetMapByIds(String[] ids){
		String sql = "select * from stusys_section_time_set where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<StusysSectionTimeSet> getStusysSectionTimeSetList(){
		String sql = "select * from stusys_section_time_set";
		return query(sql, new MultiRow());
	}

	@Override
	public List<StusysSectionTimeSet> getStusysSectionTimeSetPage(Pagination page){
		String sql = "select * from stusys_section_time_set";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<StusysSectionTimeSet> getStusysSectionTimeSetByUnitIdList(String unitId,String acadyear,String semesterStr){
		String sql = "select * from stusys_section_time_set where unit_id = ? and acadyear = ? and semester = ? order by section_number ";
		return query(sql, new Object[]{unitId,acadyear,semesterStr}, new MultiRow());
	}

	@Override
	public List<StusysSectionTimeSet> getStusysSectionTimeSetByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from stusys_section_time_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}
