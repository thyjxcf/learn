package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.dailyoffice.entity.OfficeLabInfo;
import net.zdsoft.office.dailyoffice.dao.OfficeLabInfoDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_lab_info
 * @author 
 * 
 */
public class OfficeLabInfoDaoImpl extends BaseDao<OfficeLabInfo> implements OfficeLabInfoDao{

	@Override
	public OfficeLabInfo setField(ResultSet rs) throws SQLException{
		OfficeLabInfo officeLabInfo = new OfficeLabInfo();
		officeLabInfo.setId(rs.getString("id"));
		officeLabInfo.setUnitId(rs.getString("unit_id"));
		officeLabInfo.setLabSetId(rs.getString("lab_set_id"));
		officeLabInfo.setClassName(rs.getString("class_name"));
		officeLabInfo.setStudentNum(rs.getInt("student_num"));
		officeLabInfo.setTeacherId(rs.getString("teacher_id"));
		officeLabInfo.setLabMode(rs.getString("lab_mode"));
		officeLabInfo.setSubject(rs.getString("subject"));
		return officeLabInfo;
	}

	@Override
	public OfficeLabInfo save(OfficeLabInfo officeLabInfo){
		String sql = "insert into office_lab_info(id, unit_id, lab_set_id, class_name, student_num, teacher_id, lab_mode, subject) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeLabInfo.getId())){
			officeLabInfo.setId(createId());
		}
		Object[] args = new Object[]{
			officeLabInfo.getId(), officeLabInfo.getUnitId(), 
			officeLabInfo.getLabSetId(), officeLabInfo.getClassName(), 
			officeLabInfo.getStudentNum(), officeLabInfo.getTeacherId(), 
			officeLabInfo.getLabMode(), officeLabInfo.getSubject()
		};
		update(sql, args);
		return officeLabInfo;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_lab_info where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeLabInfo officeLabInfo){
		String sql = "update office_lab_info set unit_id = ?, lab_set_id = ?, class_name = ?, student_num = ?, teacher_id = ?, lab_mode = ?, subject = ? where id = ?";
		Object[] args = new Object[]{
			officeLabInfo.getUnitId(), officeLabInfo.getLabSetId(), 
			officeLabInfo.getClassName(), officeLabInfo.getStudentNum(), 
			officeLabInfo.getTeacherId(), officeLabInfo.getLabMode(), 
			officeLabInfo.getSubject(), officeLabInfo.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeLabInfo getOfficeLabInfoById(String id){
		String sql = "select * from office_lab_info where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeLabInfo> getOfficeLabInfoMapByIds(String[] ids){
		String sql = "select * from office_lab_info where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeLabInfo> getOfficeLabInfoList(){
		String sql = "select * from office_lab_info";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeLabInfo> getOfficeLabInfoPage(Pagination page){
		String sql = "select * from office_lab_info";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeLabInfo> getOfficeLabInfoByUnitIdList(String unitId){
		String sql = "select * from office_lab_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeLabInfo> getOfficeLabInfoByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_lab_info where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public Map<String, OfficeLabInfo> getOfficeLabInfoMapByConditions(String unitId, String searchLabMode, String[] labSetIds){
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_lab_info where unit_id = ?");
		args.add(unitId);
		if(StringUtils.isNotBlank(searchLabMode)){
			sql.append(" and lab_mode = ?");
			args.add(searchLabMode);
		}
		sql.append(" and lab_set_id in");
		return queryForInSQL(sql.toString(), args.toArray(), labSetIds, new MapRowMapper<String, OfficeLabInfo>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}
			
			@Override
			public OfficeLabInfo mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
}
