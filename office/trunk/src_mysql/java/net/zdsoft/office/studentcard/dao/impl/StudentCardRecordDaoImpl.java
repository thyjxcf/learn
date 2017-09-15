package net.zdsoft.office.studentcard.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.office.studentcard.dao.StudentCardRecordDao;
import net.zdsoft.office.studentcard.entity.StudentCardRecord;

public class StudentCardRecordDaoImpl extends BaseDao<StudentCardRecord> implements StudentCardRecordDao{

	@Override
	public StudentCardRecord setField(ResultSet rs) throws SQLException {
		StudentCardRecord stuCard=new StudentCardRecord();
		stuCard.setFlag(rs.getInt("flag"));
		stuCard.setRefshCardDate(rs.getDate("refsh_card_date"));
		stuCard.setStudentId(rs.getString("student_id"));
		stuCard.setId(rs.getString("id"));
		return stuCard;
	}

	@Override
	public List<StudentCardRecord> findStudentCardRecordbyTimes(Date startTime,
			Date endTime,Integer flag) {
		StringBuffer sql =new StringBuffer();
		List<Object> objList=new ArrayList<Object>();//str_to_date(?, '%Y-%m-%d') >= str_to_date(date_format(calendar_time, '%Y-%m-%d'), '%Y-%m-%d')
		sql.append("select * from office_student_cardrecord where str_to_date(date_format(refsh_card_date,'%Y-%m-%d'),'%Y-%m-%d') >=? and str_to_date(date_format(refsh_card_date,'%Y-%m-%d'),'%Y-%m-%d')<=? ");
		objList.add(startTime);
		objList.add(endTime);
		if(flag!=null){
			sql.append(" and flag=? ");
			objList.add(flag);
		}
		return query(sql.toString(), objList.toArray(new Object[0]),new MultiRow());
	}
	
	

}
