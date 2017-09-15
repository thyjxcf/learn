package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.dao.BasicClsReqTeaPlanDao;
import net.zdsoft.eis.base.common.entity.BasicClsReqTeaPlan;
import net.zdsoft.eis.frame.client.BaseDao;

public class BasicClsReqTeaPlanDaoImpl extends BaseDao<BasicClsReqTeaPlan> implements BasicClsReqTeaPlanDao {

	@Override
	public List<BasicClsReqTeaPlan> getBasicClsReqTeaPlanList(String unitid,
			String acadyear, String semester) {
		String sql="select * from eduadm_cls_req_tea_plan where unit_id=? and acadyear=? and semester=? and is_deleted=0";
		
		return this.query(sql, new Object[]{unitid,acadyear,semester}, new MultiRow());
	}

	@Override
	public BasicClsReqTeaPlan setField(ResultSet rs) throws SQLException {
		BasicClsReqTeaPlan ent =new BasicClsReqTeaPlan();
		ent.setId(rs.getString("id"));
		ent.setClaid(rs.getString("class_id"));
		ent.setSubid(rs.getString("subject_id"));
		ent.setTeaid(rs.getString("teacher_id"));
		ent.setUnitId(rs.getString("unit_id"));
		ent.setAcadyear(rs.getString("acadyear"));
		ent.setSemster(rs.getString("semester"));
		ent.setWeekCourseHour(rs.getInt("course_hour"));
		ent.setPunchCard(rs.getInt("punch_card"));
		return ent;
	}

}
