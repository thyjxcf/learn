package net.zdsoft.office.jtgoout.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.office.jtgoout.entity.GooutTeacherEx;
import net.zdsoft.office.jtgoout.dao.GooutTeacherExDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goout_teacher
 * @author 
 * 
 */
public class GooutTeacherExDaoImpl extends BaseDao<GooutTeacherEx> implements GooutTeacherExDao{

	@Override
	public GooutTeacherEx setField(ResultSet rs) throws SQLException{
		GooutTeacherEx gooutTeacherEx = new GooutTeacherEx();
		gooutTeacherEx.setId(rs.getString("id"));
		gooutTeacherEx.setUnitId(rs.getString("unit_id"));
		gooutTeacherEx.setJtgooutId(rs.getString("jtgoout_id"));
		gooutTeacherEx.setContent(rs.getString("content"));
		gooutTeacherEx.setPartakePersonId(rs.getString("partake_person_id"));
		return gooutTeacherEx;
	}

	@Override
	public GooutTeacherEx save(GooutTeacherEx gooutTeacherEx){
		String sql = "insert into office_goout_teacher(id, unit_id, jtgoout_id, content, partake_person_id) values(?,?,?,?,?)";
		if (StringUtils.isBlank(gooutTeacherEx.getId())){
			gooutTeacherEx.setId(createId());
		}
		Object[] args = new Object[]{
			gooutTeacherEx.getId(), gooutTeacherEx.getUnitId(), 
			gooutTeacherEx.getJtgooutId(), gooutTeacherEx.getContent(), 
			gooutTeacherEx.getPartakePersonId()
		};
		update(sql, args);
		return gooutTeacherEx;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_goout_teacher where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public void deleteByjtGooutId(String jtGooutId) {
		String sql="delete from office_goout_teacher where jtgoout_id=?";
		update(sql, jtGooutId);
	}

	@Override
	public Integer update(GooutTeacherEx gooutTeacherEx){
		String sql = "update office_goout_teacher set unit_id = ?, jtgoout_id = ?, content = ?, partake_person_id = ? where id = ?";
		Object[] args = new Object[]{
			gooutTeacherEx.getUnitId(), gooutTeacherEx.getJtgooutId(), 
			gooutTeacherEx.getContent(), gooutTeacherEx.getPartakePersonId(), 
			gooutTeacherEx.getId()
		};
		return update(sql, args);
	}

	@Override
	public GooutTeacherEx getGooutTeacherExById(String id){
		String sql = "select * from office_goout_teacher where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public GooutTeacherEx getGooutTeacherExByJtGooutId(String jtGooutId) {
		String sql="select * from office_goout_teacher where jtgoout_id=?";
		return query(sql, new Object[]{jtGooutId}, new SingleRow());
	}

	@Override
	public List<GooutTeacherEx> getGooutTeacherExListByjtId(String[] jtGoOutIds) {
		String sql="select * from office_goout_teacher where jtgoout_id in";
		return queryForInSQL(sql, null, jtGoOutIds, new MultiRow());
	}
	
	@Override
	public Map<String, GooutTeacherEx> getGooutTeacherExMapByIds(String[] ids){
		String sql = "select * from office_goout_teacher where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<GooutTeacherEx> getGooutTeacherExList(){
		String sql = "select * from office_goout_teacher";
		return query(sql, new MultiRow());
	}

	@Override
	public List<GooutTeacherEx> getGooutTeacherExPage(Pagination page){
		String sql = "select * from office_goout_teacher";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdList(String unitId){
		String sql = "select * from office_goout_teacher where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_goout_teacher where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<GooutTeacherEx> getGooutTeacherExByUnitIdJtIds(String unitId,
			String[] jtIds) {
		// TODO Auto-generated method stub
		String sql = "select * from office_goout_teacher where unit_id = ? and jtgoout_id in ";
		return queryForInSQL(sql.toString(),new Object[]{unitId}, jtIds, new MultiRow());
	}
}
