package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.dao.TeacherDutyDao;
import net.zdsoft.eis.base.common.entity.TeacherDuty;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author chens
 * @version 创建时间：2015-2-28 下午4:29:24
 * 
 */
public class TeacherDutyDaoImpl extends BaseDao<TeacherDuty> implements TeacherDutyDao {

	private static final String SQL_FIND_TEACHER_DUTYS_BY_TEACHER_IDS = "select * from base_teacher_duty where is_deleted =0 and duty_code = ? and teacher_id IN";
	//部门领导职务过滤掉科员，微代码为114
	private static final String SQL_FIND_TEACHER_DUTYS_BY_TEACHER_IDS_ = "select * from base_teacher_duty where is_deleted =0 and duty_code <> '114' and teacher_id IN";
	
	
	@Override
	public TeacherDuty setField(ResultSet rs) throws SQLException {
		TeacherDuty duty = new TeacherDuty();
        duty.setId(rs.getString("id"));
        duty.setTeacherId(rs.getString("teacher_id"));
        duty.setDutyCode(rs.getString("duty_code"));
        return duty;
	}
	@Override
	public List<TeacherDuty> getTeacherDutyListByTeacherIds(String dutyCode,
			String[] teacherIds) {
		return queryForInSQL(SQL_FIND_TEACHER_DUTYS_BY_TEACHER_IDS, new Object[]{dutyCode}, teacherIds, new MultiRow(), " order by teacher_id, duty_code");
	}
	
	@Override
	public List<TeacherDuty> getTeacherDutyListByTeacherIds(String[] teacherIds) {
		return queryForInSQL(SQL_FIND_TEACHER_DUTYS_BY_TEACHER_IDS_, null, teacherIds, new MultiRow(), " order by teacher_id, duty_code");
	}

}
