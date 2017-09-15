package net.zdsoft.office.survey.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.survey.entity.OfficeSurveyApply;
import net.zdsoft.office.survey.constant.OfficeSurveyConstants;
import net.zdsoft.office.survey.dao.OfficeSurveyApplyDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
/**
 * office_survey_apply
 * @author 
 * 
 */
public class OfficeSurveyApplyDaoImpl extends BaseDao<OfficeSurveyApply> implements OfficeSurveyApplyDao{

	@Override
	public OfficeSurveyApply setField(ResultSet rs) throws SQLException{
		OfficeSurveyApply officeSurveyApply = new OfficeSurveyApply();
		officeSurveyApply.setId(rs.getString("id"));
		officeSurveyApply.setUnitId(rs.getString("unit_id"));
		officeSurveyApply.setApplyUserId(rs.getString("apply_user_id"));
		officeSurveyApply.setApplyUserName(rs.getString("apply_user_name"));
		officeSurveyApply.setSurveyName(rs.getString("survey_name"));
		officeSurveyApply.setPlace(rs.getString("place"));
		officeSurveyApply.setAmount(rs.getInt("amount"));
		officeSurveyApply.setStartTime(rs.getTimestamp("start_time"));
		officeSurveyApply.setEndTime(rs.getTimestamp("end_time"));
		officeSurveyApply.setRemark(rs.getString("remark"));
		officeSurveyApply.setState(rs.getInt("state"));
		officeSurveyApply.setApplyDate(rs.getTimestamp("apply_date"));
		officeSurveyApply.setAuditDate(rs.getTimestamp("audit_date"));
		return officeSurveyApply;
	}

	@Override
	public OfficeSurveyApply save(OfficeSurveyApply officeSurveyApply){
		String sql = "insert into office_survey_apply(id, unit_id, apply_user_id, apply_user_name, survey_name, place, amount, start_time, end_time, remark, state, apply_date, audit_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeSurveyApply.getId())){
			officeSurveyApply.setId(createId());
		}
		Object[] args = new Object[]{
			officeSurveyApply.getId(), officeSurveyApply.getUnitId(), 
			officeSurveyApply.getApplyUserId(), officeSurveyApply.getApplyUserName(), 
			officeSurveyApply.getSurveyName(), officeSurveyApply.getPlace(), 
			officeSurveyApply.getAmount(), officeSurveyApply.getStartTime(), 
			officeSurveyApply.getEndTime(), officeSurveyApply.getRemark(), 
			officeSurveyApply.getState(), officeSurveyApply.getApplyDate(), 
			officeSurveyApply.getAuditDate()
		};
		update(sql, args);
		return officeSurveyApply;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_survey_apply where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSurveyApply officeSurveyApply){
		String sql = "update office_survey_apply set unit_id = ?, apply_user_id = ?, apply_user_name = ?, survey_name = ?, place = ?, amount = ?, start_time = ?, end_time = ?, remark = ?, state = ?, apply_date = ?, audit_date = ? where id = ?";
		Object[] args = new Object[]{
			officeSurveyApply.getUnitId(), officeSurveyApply.getApplyUserId(), 
			officeSurveyApply.getApplyUserName(), officeSurveyApply.getSurveyName(), 
			officeSurveyApply.getPlace(), officeSurveyApply.getAmount(), 
			officeSurveyApply.getStartTime(), officeSurveyApply.getEndTime(), 
			officeSurveyApply.getRemark(), officeSurveyApply.getState(), 
			officeSurveyApply.getApplyDate(), officeSurveyApply.getAuditDate(), 
			officeSurveyApply.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSurveyApply getOfficeSurveyApplyById(String id){
		String sql = "select * from office_survey_apply where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSurveyApply> getOfficeSurveyApplyMapByIds(String[] ids){
		String sql = "select * from office_survey_apply where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSurveyApply> getOfficeSurveyApplyList(){
		String sql = "select * from office_survey_apply";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSurveyApply> getOfficeSurveyApplyPage(Pagination page){
		String sql = "select * from office_survey_apply";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSurveyApply> getOfficeSurveyApplyByUnitIdList(String unitId){
		String sql = "select * from office_survey_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSurveyApply> getOfficeSurveyApplyByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_survey_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeSurveyApply> getSurveyApplyListByConditions(String unitId, String searchType, String searchPlace, String appayUserId,
			String searchName, Date searchStartTime, Date searchEndTime, Pagination page){
		List<Object> argsList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_survey_apply where unit_id = ? ");
		argsList.add(unitId);
		if(StringUtils.isNotBlank(searchType)){
			if(StringUtils.equals(searchType, OfficeSurveyConstants.OFFCIE_SURVEY_ALL + "")){
				sql.append(" and state in ").append(
						SQLUtils.toSQLInString(new String[]{"1","2","3"}));
			}else{
				sql.append(" and state = ?");
				argsList.add(Integer.parseInt(searchType));
			}
		}
		if(StringUtils.isNotBlank(searchPlace)){
			sql.append(" and place = ?");
			argsList.add(searchPlace);
		}
		if(StringUtils.isNotBlank(appayUserId)){
			sql.append(" and apply_user_id = ?");
			argsList.add(appayUserId);
		}
		if(StringUtils.isNotBlank(searchName)){
			sql.append(" and apply_user_name like '%" + searchName + "%'");
		}
		if(searchStartTime != null){
			sql.append(" and to_date(to_char(start_time,'yyyy-MM-dd'),'yyyy-MM-dd') >= ? ");
			argsList.add(searchStartTime);
		}
		if(searchEndTime != null){
			sql.append(" and to_date(to_char(end_time,'yyyy-MM-dd'),'yyyy-MM-dd') <= ? ");
			argsList.add(searchEndTime);
		}
		sql.append(" order by apply_date desc, apply_user_name asc");
		if(page != null)
			return query(sql.toString(), argsList.toArray(), new MultiRow(), page);
		else
			return query(sql.toString(), argsList.toArray(), new MultiRow());
	}

	@Override
	public List<OfficeSurveyApply> getSurveyApplyList(String userId, String unitId,
			String applyStatus, String searchPlace, Pagination page) {
		// TODO Auto-generated method stub
		List<Object> argsList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_survey_apply where unit_id = ? ");
		argsList.add(unitId);
		if(StringUtils.isNotBlank(applyStatus)&&"0".equals(applyStatus)){
			sql.append(" and state = 1");
		}else{
			sql.append(" and (state = 2 or state=3)");
		}
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and apply_user_id = ?");
			argsList.add(userId);
		}
		if(StringUtils.isNotBlank(searchPlace)){
			sql.append(" and place = ?");
			argsList.add(searchPlace);
		}
		sql.append(" order by apply_date desc, apply_user_name asc");
		if(page != null)
			return query(sql.toString(), argsList.toArray(), new MultiRow(), page);
		else
			return query(sql.toString(), argsList.toArray(), new MultiRow());
	}
}
