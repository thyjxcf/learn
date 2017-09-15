package net.zdsoft.office.boardroom.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.boardroom.entity.OfficeBoardroomApplyXj;
import net.zdsoft.office.boardroom.dao.OfficeBoardroomApplyXjDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_boardroom_apply_xj
 * @author 
 * 
 */
public class OfficeBoardroomApplyXjDaoImpl extends BaseDao<OfficeBoardroomApplyXj> implements OfficeBoardroomApplyXjDao{

	@Override
	public OfficeBoardroomApplyXj setField(ResultSet rs) throws SQLException{
		OfficeBoardroomApplyXj officeBoardroomApplyXj = new OfficeBoardroomApplyXj();
		officeBoardroomApplyXj.setId(rs.getString("id"));
		officeBoardroomApplyXj.setUnitId(rs.getString("unit_id"));
		officeBoardroomApplyXj.setApplyUserId(rs.getString("apply_user_id"));
		officeBoardroomApplyXj.setApplyDeptId(rs.getString("apply_dept_id"));
		officeBoardroomApplyXj.setRoomId(rs.getString("room_id"));
		officeBoardroomApplyXj.setApplyDate(rs.getTimestamp("apply_date"));
		officeBoardroomApplyXj.setContent(rs.getString("content"));
		officeBoardroomApplyXj.setState(rs.getString("state"));
		officeBoardroomApplyXj.setAuditUserId(rs.getString("audit_user_id"));
		officeBoardroomApplyXj.setAuditDate(rs.getTimestamp("audit_date"));
		officeBoardroomApplyXj.setAuditOpinion(rs.getString("audit_opinion"));
		officeBoardroomApplyXj.setCreateTime(rs.getTimestamp("create_time"));
		return officeBoardroomApplyXj;
	}

	@Override
	public OfficeBoardroomApplyXj save(OfficeBoardroomApplyXj officeBoardroomApplyXj){
		String sql = "insert into office_boardroom_apply_xj(id, unit_id, apply_user_id, apply_dept_id, apply_date, content, state, audit_user_id, audit_date, audit_opinion, create_time,room_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeBoardroomApplyXj.getId())){
			officeBoardroomApplyXj.setId(createId());
		}
		Object[] args = new Object[]{
			officeBoardroomApplyXj.getId(), officeBoardroomApplyXj.getUnitId(), 
			officeBoardroomApplyXj.getApplyUserId(), officeBoardroomApplyXj.getApplyDeptId(), 
			officeBoardroomApplyXj.getApplyDate(), officeBoardroomApplyXj.getContent(), 
			officeBoardroomApplyXj.getState(), officeBoardroomApplyXj.getAuditUserId(), 
			officeBoardroomApplyXj.getAuditDate(), officeBoardroomApplyXj.getAuditOpinion(), 
			officeBoardroomApplyXj.getCreateTime(),officeBoardroomApplyXj.getRoomId()
		};
		update(sql, args);
		return officeBoardroomApplyXj;
	}
	
	@Override
	public void batchSave(Map<String, OfficeBoardroomApplyXj> map){
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		String sql = "insert into office_boardroom_apply_xj(id, unit_id, apply_user_id, apply_dept_id, apply_date, content, state, audit_user_id, audit_date, audit_opinion, create_time,room_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		for (String key : map.keySet()) {
			OfficeBoardroomApplyXj officeBoardroomApplyXj = map.get(key);
			if (StringUtils.isBlank(officeBoardroomApplyXj.getId()))
				officeBoardroomApplyXj.setId(getGUID());
			Object[] args = new Object[]{
					officeBoardroomApplyXj.getId(), officeBoardroomApplyXj.getUnitId(), 
					officeBoardroomApplyXj.getApplyUserId(), officeBoardroomApplyXj.getApplyDeptId(), 
					officeBoardroomApplyXj.getApplyDate(), officeBoardroomApplyXj.getContent(), 
					officeBoardroomApplyXj.getState(), officeBoardroomApplyXj.getAuditUserId(), 
					officeBoardroomApplyXj.getAuditDate(), officeBoardroomApplyXj.getAuditOpinion(), 
					officeBoardroomApplyXj.getCreateTime(),officeBoardroomApplyXj.getRoomId()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR,
				Types.CHAR, Types.CHAR,
				Types.DATE, Types.VARCHAR,
				Types.CHAR, Types.CHAR,
				Types.DATE, Types.VARCHAR,
				Types.DATE, Types.CHAR
				};
		batchUpdate(sql, listOfArgs, argTypes);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_boardroom_apply_xj where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeBoardroomApplyXj officeBoardroomApplyXj){
		String sql = "update office_boardroom_apply_xj set unit_id = ?, apply_user_id = ?, apply_dept_id = ?, apply_date = ?, content = ?, state = ?, audit_user_id = ?, audit_date = ?, audit_opinion = ?, create_time = ?,room_id=? where id = ?";
		Object[] args = new Object[]{
			officeBoardroomApplyXj.getUnitId(), officeBoardroomApplyXj.getApplyUserId(), 
			officeBoardroomApplyXj.getApplyDeptId(), officeBoardroomApplyXj.getApplyDate(), 
			officeBoardroomApplyXj.getContent(), officeBoardroomApplyXj.getState(), 
			officeBoardroomApplyXj.getAuditUserId(), officeBoardroomApplyXj.getAuditDate(), 
			officeBoardroomApplyXj.getAuditOpinion(), officeBoardroomApplyXj.getCreateTime(), 
			officeBoardroomApplyXj.getRoomId(),officeBoardroomApplyXj.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeBoardroomApplyXj getOfficeBoardroomApplyXjById(String id){
		String sql = "select * from office_boardroom_apply_xj where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjMapByIds(String[] ids){
		String sql = "select * from office_boardroom_apply_xj where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjList(){
		String sql = "select * from office_boardroom_apply_xj";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjPage(Pagination page){
		String sql = "select * from office_boardroom_apply_xj";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjByUnitIdList(String unitId){
		String sql = "select * from office_boardroom_apply_xj where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_boardroom_apply_xj where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeBoardroomApplyXj> getOfficeBoardroomApplyXjByDeptIdUnitIdPage(
			String applyStartDate, String applyEndDate,String auditState, String roomId,
			String deptId, String unitId, Pagination page) {
		StringBuffer str = new StringBuffer("select * from office_boardroom_apply_xj where unit_id = ?  ");
		List<Object> objects = new ArrayList<Object>();
		objects.add(unitId);
		if(StringUtils.isNotBlank(applyStartDate)){
			str.append(" and to_char(apply_date,'yyyy-MM-dd') >= ? ");
			objects.add(applyStartDate);
		}
		if(StringUtils.isNotBlank(applyEndDate)){
			str.append(" and to_char(apply_date,'yyyy-MM-dd') <= ? ");
			objects.add(applyEndDate);
		}
		if(StringUtils.isNotBlank(auditState)&&StringUtils.equals("0", auditState)){
			str.append(" and state=2 ");
		}
		if(StringUtils.isNotBlank(auditState)&&StringUtils.equals("1", auditState)){
			str.append(" and (state=3 or state=4) ");
		}
		if(StringUtils.isNotBlank(roomId)){
			str.append(" and room_id = ? ");
			objects.add(roomId);
		}
		if(StringUtils.isNotBlank(deptId)){
			str.append(" and apply_dept_id = ? ");
			objects.add(deptId);
		}
		str.append(" order by apply_date desc ");
		return query(str.toString(), objects.toArray(), new MultiRow(), page);
	}

	@Override
	public void updateStateByIds(String[] ids,String userId,String applyOption,java.util.Date auditDate, String state) {
		String sql1="update office_boardroom_apply_xj set state =?,audit_date=?,audit_user_id=?  where id in";
		String sql2="update office_boardroom_apply_xj set state =?,audit_opinion=?,audit_date=?,audit_user_id=? where id in";
		if(StringUtils.isNotBlank(state)&&StringUtils.equals("3", state)){
			updateForInSQL(sql1, new Object[]{state,auditDate,userId}, ids);
		}
		if(StringUtils.isNotBlank(state)&&StringUtils.equals("4", state)){
			updateForInSQL(sql2, new Object[]{state,applyOption,auditDate,userId}, ids);
		}
	}

}
