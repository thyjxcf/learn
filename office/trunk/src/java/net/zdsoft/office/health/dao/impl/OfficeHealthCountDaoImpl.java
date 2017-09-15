package net.zdsoft.office.health.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.health.constant.HealthConstants;
import net.zdsoft.office.health.dao.OfficeHealthCountDao;
import net.zdsoft.office.health.entity.OfficeHealthCount;

import org.apache.commons.lang.StringUtils;

/**
 * office_health_count 
 * @author 
 * 
 */
public class OfficeHealthCountDaoImpl extends BaseDao<OfficeHealthCount> implements OfficeHealthCountDao{
	@Override
	public OfficeHealthCount setField(ResultSet rs) throws SQLException{
		OfficeHealthCount officeHealthCount = new OfficeHealthCount();
		officeHealthCount.setId(rs.getString("id"));
		officeHealthCount.setStudentId(rs.getString("student_id"));
		officeHealthCount.setType(rs.getInt("type"));
		officeHealthCount.setDate(rs.getTimestamp("check_date"));
		officeHealthCount.setHour(rs.getInt("hour"));
		officeHealthCount.setStep(rs.getInt("step"));
		officeHealthCount.setDistance(rs.getDouble("distance"));
		officeHealthCount.setCalorie(rs.getDouble("calorie"));
		officeHealthCount.setCreationTime(rs.getTimestamp("creation_time"));
		return officeHealthCount;
	}
	
	public OfficeHealthCount save(OfficeHealthCount officeHealthCount){
		String sql = "insert into office_health_count(id, student_id, type, check_date, hour, step, distance, calorie, creation_time) values(?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeHealthCount.getId())){
			officeHealthCount.setId(createId());
		}
		Object[] args = new Object[]{
			officeHealthCount.getId(), officeHealthCount.getStudentId(), 
			officeHealthCount.getType(), officeHealthCount.getDate(), 
			officeHealthCount.getHour(), officeHealthCount.getStep(), 
			officeHealthCount.getDistance(), officeHealthCount.getCalorie(), 
			officeHealthCount.getCreationTime()
		};
		update(sql, args);
		return officeHealthCount;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_health_count where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeHealthCount officeHealthCount){
		String sql = "update office_health_count set student_id = ?, type = ?, check_date = ?, hour = ?, step = ?, distance = ?, calorie = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeHealthCount.getStudentId(), officeHealthCount.getType(), 
			officeHealthCount.getDate(), officeHealthCount.getHour(), 
			officeHealthCount.getStep(), officeHealthCount.getDistance(), 
			officeHealthCount.getCalorie(), officeHealthCount.getCreationTime(), 
			officeHealthCount.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeHealthCount getOfficeHealthCountById(String id){
		String sql = "select * from office_health_count where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	@Override
	public List<OfficeHealthCount> getOfficeHealthCountByStudentId(String studentId,String nowDateStr,String beforeDateStr){
		String sql = "select * from office_health_count where student_id = ? ";
		if(StringUtils.isBlank(beforeDateStr)){
			sql+=" AND check_date= to_date(?,'yyyy-mm-dd')";
			return query(sql, new Object[]{studentId,nowDateStr}, new MultiRow());
		}else{
			sql+=" AND type=? AND check_date  between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') order by check_date";
			return query(sql, new Object[]{studentId,HealthConstants.HEALTH_TYPE_DAY,beforeDateStr,nowDateStr}, new MultiRow());
		}
	}
	@Override
	public List<OfficeHealthCount> getClassAllStuByStudentIds(String[] studentIds,String queryDate){
		String sql = "select * from office_health_count where type=? and check_date=to_date(?,'yyyy-mm-dd') and  student_id in ";
		return queryForInSQL(sql, new Object[]{HealthConstants.HEALTH_TYPE_DAY,queryDate}, studentIds,new MultiRow()," order by step desc");
	}
	@Override
	public Map<String, OfficeHealthCount> getOfficeHealthCountMapByIds(String[] ids){
		String sql = "select * from office_health_count where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeHealthCount> getOfficeHealthCountList(){
		String sql = "select * from office_health_count";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeHealthCount> getOfficeHealthCountPage(Pagination page){
		String sql = "select * from office_health_count";
		return query(sql, new MultiRow(), page);
	}
	
}
