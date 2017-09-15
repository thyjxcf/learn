package net.zdsoft.office.dailyoffice.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.dailyoffice.dao.OfficeApplyNumberDao;
import net.zdsoft.office.dailyoffice.entity.OfficeApplyNumber;

import org.apache.commons.lang.StringUtils;
/**
 * office_apply_number
 * @author 
 * 
 */
public class OfficeApplyNumberDaoImpl extends BaseDao<OfficeApplyNumber> implements OfficeApplyNumberDao{

	@Override
	public OfficeApplyNumber setField(ResultSet rs) throws SQLException{
		OfficeApplyNumber officeApplyNumber = new OfficeApplyNumber();
		officeApplyNumber.setId(rs.getString("id"));
		officeApplyNumber.setUnitId(rs.getString("unit_id"));
		officeApplyNumber.setType(rs.getString("type"));
		officeApplyNumber.setPurpose(rs.getString("purpose"));
		officeApplyNumber.setApplyUserId(rs.getString("apply_user_id"));
		officeApplyNumber.setApplyDate(rs.getTimestamp("apply_date"));
		officeApplyNumber.setCreateTime(rs.getTimestamp("create_time"));
		officeApplyNumber.setContent(rs.getString("content"));
		officeApplyNumber.setCardNumber(rs.getString("card_number"));
		officeApplyNumber.setState(rs.getInt("state"));
		officeApplyNumber.setAuditUserId(rs.getString("audit_user_id"));
		officeApplyNumber.setAuditTime(rs.getTimestamp("audit_time"));
		officeApplyNumber.setRemark(rs.getString("remark"));
		officeApplyNumber.setMeetingTheme(rs.getString("meeting_theme"));
		officeApplyNumber.setHostUserId(rs.getString("host_user_id"));
		officeApplyNumber.setDeptIds(rs.getString("dept_ids"));
		officeApplyNumber.setFeedback(rs.getString("feedback"));
		officeApplyNumber.setCourseId(rs.getString("course_id"));
		officeApplyNumber.setLabInfoId(rs.getString("lab_info_id"));
		return officeApplyNumber;
	}
	
	@Override
	public OfficeApplyNumber save(OfficeApplyNumber officeApplyNumber){
		String sql = "insert into office_apply_number(id, unit_id, type, purpose, apply_user_id, apply_date, create_time, content, card_number, state, audit_user_id, audit_time, remark,meeting_theme,host_user_id,dept_ids,course_id,lab_info_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeApplyNumber.getId())){
			officeApplyNumber.setId(createId());
		}
		Object[] args = new Object[]{
			officeApplyNumber.getId(), officeApplyNumber.getUnitId(), 
			officeApplyNumber.getType(), officeApplyNumber.getPurpose(), 
			officeApplyNumber.getApplyUserId(), officeApplyNumber.getApplyDate(), new Date(),
			officeApplyNumber.getContent(), officeApplyNumber.getCardNumber(), 
			officeApplyNumber.getState(), officeApplyNumber.getAuditUserId(), 
			officeApplyNumber.getAuditTime(), officeApplyNumber.getRemark(),
			officeApplyNumber.getMeetingTheme(),officeApplyNumber.getHostUserId(),
			officeApplyNumber.getDeptIds(), officeApplyNumber.getCourseId(),
			officeApplyNumber.getLabInfoId()
		};
		update(sql, args);
		return officeApplyNumber;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_apply_number where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public OfficeApplyNumber getOfficeApplyNumberById(String id){
		String sql = "select * from office_apply_number where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeApplyNumber> getOfficeApplyNumberMapByIds(String[] ids){
		String sql = "select * from office_apply_number where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumberList(String[] ids){
		String sql = "select * from office_apply_number where id in ";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumberPage(Pagination page){
		String sql = "select * from office_apply_number";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumberByUnitIdList(String unitId){
		String sql = "select * from office_apply_number where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumberByUnitIdPage(String unitId, String userId, Date startTime,Date endTime,String roomType, String auditState, Pagination page){
		StringBuffer str = new StringBuffer("select * from office_apply_number where unit_id = ? and apply_user_id = ? ");
		List<Object> objects = new ArrayList<Object>();
		objects.add(unitId);
		objects.add(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(startTime!=null){
			str.append(" and to_char(apply_date,'yyyy-MM-dd') >= ? ");
			objects.add(sdf.format(startTime));
		}
		if(endTime!=null){
			str.append(" and to_char(apply_date,'yyyy-MM-dd') <= ? ");
			objects.add(sdf.format(endTime));
		}
		if(StringUtils.isNotBlank(roomType)){
			str.append(" and type = ? ");
			objects.add(roomType);
		}
		if(StringUtils.isNotBlank(auditState)){
			str.append(" and state = ? ");
			objects.add(auditState);
		}
		str.append(" order by state, create_time desc ");
		return query(str.toString(), objects.toArray(), new MultiRow(), page);
	}
	
	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumberAuditPage(String unitId,
			Date startTime, Date endTime, String roomType, String auditState, String searchSubject, Pagination page) {
		StringBuffer str = new StringBuffer("select * from office_apply_number where unit_id = ? ");
		List<Object> objects = new ArrayList<Object>();
		objects.add(unitId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(startTime!=null){
			str.append(" and to_char(apply_date,'yyyy-MM-dd') >= ? ");
			objects.add(sdf.format(startTime));
		}
		if(endTime!=null){
			str.append(" and to_char(apply_date,'yyyy-MM-dd') <= ? ");
			objects.add(sdf.format(endTime));
		}
		if(StringUtils.isNotBlank(roomType)){
			str.append(" and type = ? ");
			objects.add(roomType);
		}
		if(StringUtils.isNotBlank(auditState)){
			str.append(" and state = ? ");
			objects.add(auditState);
		}
		if(StringUtils.isNotBlank(searchSubject)){
			str.append(" and lab_info_id in(select id from office_lab_info where subject = ?)");
			objects.add(searchSubject);
		}
		str.append(" order by state, create_time desc ");
		return query(str.toString(), objects.toArray(), new MultiRow(), page);
	}
	
	@Override
	public void updatePass(String[] ids, String userId, Integer state) {
		String sql = "update office_apply_number set state = ?, audit_user_id = ?, audit_time = sysdate where id in";
		updateForInSQL(sql, new Object[]{state, userId}, ids);
	}
	
	@Override
	public void update(String id, String state, String remark, String userId) {
		String sql = "update office_apply_number set state = ?, audit_user_id = ?, remark = ?, audit_time = sysdate where id = ? ";
		update(sql, new Object[]{state,userId,remark,id});
	}
	
	@Override
	public void updateFeedback(OfficeApplyNumber officeApplyNumber) {
		String sql = "update office_apply_number set feedback = ? where id = ? ";
		update(sql, new Object[]{officeApplyNumber.getFeedback(), officeApplyNumber.getId()});
	}
	
	@Override
	public List<OfficeApplyNumber> getOfficeApplyNumbersByConditions(String unitId, Date startTime, Date endTime, String[] applyUserIds, String[] labInfoIds, Pagination page){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select an.* from office_apply_number an where exists(select 1 from office_utility_number un,office_utility_apply ua where an.id=un.number_id  and un.utility_id=ua.id) and an.state = 3 and an.type= 11 and an.unit_id = ?");
		args.add(unitId);
		if(startTime!=null){
			sql.append(" and to_char(an.apply_date,'yyyy-MM-dd') >= ? ");
			args.add(sdf.format(startTime));
		}
		if(endTime!=null){
			sql.append(" and to_char(an.apply_date,'yyyy-MM-dd') <= ? ");
			args.add(sdf.format(endTime));
		}
		if(applyUserIds != null){
			sql.append(" and an.apply_user_id in ").append(SQLUtils.toSQLInString(applyUserIds));
		}
		sql.append(" and an.lab_info_id in");
		if(page != null){
			return queryForInSQL(sql.toString(), args.toArray(), labInfoIds, new MultiRow(), " order by create_time desc", page);
		}else{
			return queryForInSQL(sql.toString(), args.toArray(), labInfoIds, new MultiRow(), " order by create_time desc");
		}
	}
}	