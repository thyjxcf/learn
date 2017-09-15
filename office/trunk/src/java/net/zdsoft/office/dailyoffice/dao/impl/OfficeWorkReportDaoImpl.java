package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeWorkReportDao;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;

import org.apache.commons.lang.StringUtils;
/**
 * office_work_report
 * @author 
 * 
 */
public class OfficeWorkReportDaoImpl extends BaseDao<OfficeWorkReport> implements OfficeWorkReportDao{

	@Override
	public OfficeWorkReport setField(ResultSet rs) throws SQLException{
		OfficeWorkReport officeWorkReport = new OfficeWorkReport();
		officeWorkReport.setId(rs.getString("id"));
		officeWorkReport.setReportType(rs.getString("report_type"));
		officeWorkReport.setBeginTime(rs.getTimestamp("begin_time"));
		officeWorkReport.setEndTime(rs.getTimestamp("end_time"));
		officeWorkReport.setReceiveUserId(rs.getString("receive_user_id"));
		officeWorkReport.setContent(rs.getString("content"));
		officeWorkReport.setState(rs.getString("state"));
		officeWorkReport.setCreateUserId(rs.getString("create_user_id"));
		officeWorkReport.setCreateTime(rs.getTimestamp("create_time"));
		officeWorkReport.setUnitId(rs.getString("unit_id"));
		officeWorkReport.setDeptId(rs.getString("dept_id"));
		officeWorkReport.setCreateUserName(rs.getString("create_user_name"));
		officeWorkReport.setReadUserId(rs.getString("read_user_id"));
		officeWorkReport.setInvalidUserId(rs.getString("invalid_user_id"));
		officeWorkReport.setInvalidTime(rs.getTimestamp("invalid_time"));
		return officeWorkReport;
	}

	@Override
	public OfficeWorkReport save(OfficeWorkReport officeWorkReport){
		String sql = "insert into office_work_report(id, report_type, begin_time, end_time, receive_user_id, content, state, create_user_id, create_time, unit_id, dept_id,create_user_name,read_user_id,invalid_user_id,invalid_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeWorkReport.getId())){
			officeWorkReport.setId(createId());
		}
		Object[] args = new Object[]{
			officeWorkReport.getId(), officeWorkReport.getReportType(), 
			officeWorkReport.getBeginTime(), officeWorkReport.getEndTime(), 
			officeWorkReport.getReceiveUserId(), officeWorkReport.getContent(), 
			officeWorkReport.getState(), officeWorkReport.getCreateUserId(), 
			officeWorkReport.getCreateTime(), officeWorkReport.getUnitId(), 
			officeWorkReport.getDeptId(), officeWorkReport.getCreateUserName(),
			officeWorkReport.getReadUserId(),officeWorkReport.getInvalidUserId(),
			officeWorkReport.getInvalidTime()
		};
		update(sql, args);
		return officeWorkReport;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_work_report where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeWorkReport officeWorkReport){
		String sql = "update office_work_report set report_type = ?, begin_time = ?,invalid_user_id=?,invalid_time=?, end_time = ?, receive_user_id = ?, content = ?, state = ? ,read_user_id= ? ,create_time=sysdate where id = ?";
		Object[] args = new Object[]{
			officeWorkReport.getReportType(), officeWorkReport.getBeginTime(),officeWorkReport.getInvalidUserId(),officeWorkReport.getInvalidTime(), 
			officeWorkReport.getEndTime(), officeWorkReport.getReceiveUserId(), 
			officeWorkReport.getContent(), officeWorkReport.getState(), 
			officeWorkReport.getReadUserId(),officeWorkReport.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeWorkReport getOfficeWorkReportById(String id){
		String sql = "select * from office_work_report where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWorkReport> getOfficeWorkReportMapByIds(String[] ids){
		String sql = "select * from office_work_report where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportList(){
		String sql = "select * from office_work_report";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportPage(Pagination page){
		String sql = "select * from office_work_report";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdList(String unitId){
		String sql = "select * from office_work_report where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_work_report where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdPageContent(
			String unit,Pagination page,String userId,String content,String beginTime,String endTime,
			String reportType,String state) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("select * from office_work_report where unit_id=? and create_user_id=?");
		args.add(unit);
		args.add(userId);
		if(StringUtils.isNotBlank(content)){
			sql.append(" and content like '%"+ content +"%'");
		}
		if(StringUtils.isNotBlank(beginTime)){
			sql.append(" and to_date(?, 'yyyy-MM-dd') <= to_date(to_char(begin_time,'yyyy-MM-dd'), 'yyyy-MM-dd')");
			args.add(beginTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and to_date(?, 'yyyy-MM-dd') >= to_date(to_char(begin_time,'yyyy-MM-dd'), 'yyyy-MM-dd')");
			args.add(endTime);
		}
		if(StringUtils.isNotBlank(reportType)){
			sql.append(" and report_type=?");
			args.add(reportType);
		}
		if(StringUtils.isNotBlank(state)){
			sql.append(" and state=? ");
			args.add(state);
		}
		sql.append(" order by case when state=1 then 1 when state=8 then 2 when state=2 then 3 end, begin_time desc");
		//return queryForInSQL(sql.toString(), null, ids, new MultiRow());
		return query(sql.toString(),args.toArray(), new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdPageContentCreateUserName(
			String userId, Pagination page, String content, String beginTime,
			String endTime, String reportType, String createUserName) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("select * from office_work_report where  receive_user_id like '%"+ userId +"%' and state='2'");
		if(StringUtils.isNotBlank(content)){
			sql.append(" and content like '%"+ content +"%'");
		}
		if(StringUtils.isNotBlank(beginTime)){
			sql.append(" and to_date(?, 'yyyy-MM-dd') <= to_date(to_char(begin_time,'yyyy-MM-dd'), 'yyyy-MM-dd')");
			args.add(beginTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and to_date(?, 'yyyy-MM-dd') >= to_date(to_char(begin_time,'yyyy-MM-dd'), 'yyyy-MM-dd')");
			args.add(endTime);
		}
		if(StringUtils.isNotBlank(reportType)){
			sql.append(" and report_type=?");
			args.add(reportType);
		}
		if(StringUtils.isNotBlank(createUserName)){
			sql.append(" and create_user_name like '%"+createUserName+"%' ");
		}
		sql.append(" order by begin_time desc");
		return query(sql.toString(),args.toArray(), new MultiRow(), page);
	}
	
	public List<OfficeWorkReport> getOfficeWorkReportList(String receiveUserId, String userId, String createUserName, String content, Pagination page){
		StringBuffer sql=new StringBuffer("select * from office_work_report where  1=1");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(receiveUserId)){
			sql.append(" and receive_user_id like ?");
			args.add("%"+ receiveUserId +"%");
		}
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and create_user_id=?");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(content)){
			sql.append(" and content like '%"+ content +"%'");
		}
		if(StringUtils.isNotBlank(createUserName)){
			sql.append(" and create_user_name like '%"+createUserName+"%' ");
		}
//		sql.append(" and state = 2");
		sql.append(" order by create_time desc");
		if(page == null)
			return query(sql.toString(), args.toArray(), new MultiRow());
		
		return query(sql.toString(), args.toArray(), new MultiRow(), page);
	}
	
	public List<OfficeWorkReport> getOfficeWorkReportList(String receiveUserId, String userId, String createUserName, String content, String type,String createTime,Pagination page){
		StringBuffer sql=new StringBuffer("select * from office_work_report where  1=1");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(receiveUserId)){
			sql.append(" and receive_user_id like ?");
			args.add("%"+ receiveUserId +"%");
			if(!StringUtils.isNotBlank(createTime)){
				sql.append(" and (read_user_id  IS NULL or instr(read_user_id, ?) = 0)");
				args.add(receiveUserId);
			}
			sql.append(" and state = 2");
		}
		
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and create_user_id=?");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(type)){
			sql.append(" and report_type=?");
			args.add(type);
		}
		if(StringUtils.isNotBlank(content)){
			sql.append(" and content like '%"+ content +"%'");
		}
		if(StringUtils.isNotBlank(createUserName)){
			sql.append(" and create_user_name like '%"+createUserName+"%' ");
		}
		if(StringUtils.isNotBlank(createTime)){
			sql.append(" and ? =to_char(create_time,'yyyy-MM-dd')");
			args.add(createTime);
		}
		//sql.append(" and state = 2");
		sql.append(" order by create_time desc");
		if(page == null)
			return query(sql.toString(), args.toArray(), new MultiRow());
		
		return query(sql.toString(), args.toArray(), new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportListICanRead(
			String receiveUserId, String type, Pagination page) {
		StringBuffer sql=new StringBuffer("select * from (select to_char(create_time,'yyyy-MM-dd') as time,count(*) as cnt from office_work_report where state = 2");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(receiveUserId)){
			sql.append(" and receive_user_id like ?");
			args.add("%"+ receiveUserId +"%");
		}
		if(StringUtils.isNotBlank(type)){
			sql.append(" and report_type=?");
			args.add(type);
		}
		sql.append(" group by to_char(create_time,'yyyy-MM-dd')) order by time desc");
		if(page == null)
			return query(sql.toString(), args.toArray(), new MultiRowMapper<OfficeWorkReport>() {
				@Override
				public OfficeWorkReport mapRow(ResultSet rs,
						int rowNum) throws SQLException {
					OfficeWorkReport entity = new OfficeWorkReport();
					entity.setTime(rs.getString("time"));
					entity.setCount(rs.getString("cnt"));
					return entity;
				}
			});
		
		return query(sql.toString(), args.toArray(),  new MultiRowMapper<OfficeWorkReport>() {
			@Override
			public OfficeWorkReport mapRow(ResultSet rs,
					int rowNum) throws SQLException {
				OfficeWorkReport entity = new OfficeWorkReport();
				entity.setTime(rs.getString("time"));
				entity.setCount(rs.getString("cnt"));
				return entity;
			}
		}, page);
	}

	@Override
	public Integer updateReadUserId(String readUserId,String id) {
		String sql = "update office_work_report set read_user_id = ? where id = ?";
		return update(sql, new Object[]{readUserId,id});
	}

}