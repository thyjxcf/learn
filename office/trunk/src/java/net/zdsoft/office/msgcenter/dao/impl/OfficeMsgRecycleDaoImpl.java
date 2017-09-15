package net.zdsoft.office.msgcenter.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dao.OfficeMsgRecycleDao;
import net.zdsoft.office.msgcenter.entity.OfficeMsgRecycle;

import org.apache.commons.lang.StringUtils;
/**
 * 信息回收
 * @author 
 * 
 */
public class OfficeMsgRecycleDaoImpl extends BaseDao<OfficeMsgRecycle> implements OfficeMsgRecycleDao{

	private static final String SQL_INSERT = "insert into office_msg_recycle(id, reference_id, delete_time, state, custom_folder_id, title, is_emergency, send_time, user_id, msgtype) values(?,?,?,?,?,?,?,?,?,?)";
	
	@Override
	public OfficeMsgRecycle setField(ResultSet rs) throws SQLException{
		OfficeMsgRecycle officeMsgRecycle = new OfficeMsgRecycle();
		officeMsgRecycle.setId(rs.getString("id"));
		officeMsgRecycle.setReferenceId(rs.getString("reference_id"));
		officeMsgRecycle.setDeleteTime(rs.getTimestamp("delete_time"));
		officeMsgRecycle.setState(rs.getInt("state"));
		officeMsgRecycle.setCustomFolderId(rs.getString("custom_folder_id"));
		officeMsgRecycle.setTitle(rs.getString("title"));
		officeMsgRecycle.setIsEmergency(rs.getInt("is_emergency"));
		officeMsgRecycle.setSendTime(rs.getTimestamp("send_time"));
		officeMsgRecycle.setUserId(rs.getString("user_id"));
		officeMsgRecycle.setMsgtype(rs.getInt("msgtype"));
		return officeMsgRecycle;
	}

	@Override
	public OfficeMsgRecycle save(OfficeMsgRecycle officeMsgRecycle){
		
		if (StringUtils.isBlank(officeMsgRecycle.getId())){
			officeMsgRecycle.setId(createId());
		}
		Object[] args = new Object[]{
			officeMsgRecycle.getId(), officeMsgRecycle.getReferenceId(), 
			officeMsgRecycle.getDeleteTime(), officeMsgRecycle.getState(), 
			officeMsgRecycle.getCustomFolderId(), officeMsgRecycle.getTitle(), 
			officeMsgRecycle.getIsEmergency(), officeMsgRecycle.getSendTime(), 
			officeMsgRecycle.getUserId(), officeMsgRecycle.getMsgtype()
		};
		update(SQL_INSERT, args);
		return officeMsgRecycle;
	}
	
	@Override
	public void batchSave(List<OfficeMsgRecycle> officeMsgRecycles) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < officeMsgRecycles.size(); i++) {
			OfficeMsgRecycle officeMsgRecycle = officeMsgRecycles.get(i);
			if (StringUtils.isBlank(officeMsgRecycle.getId()))
				officeMsgRecycle.setId(getGUID());
			Object[] args = new Object[]{
					officeMsgRecycle.getId(), officeMsgRecycle.getReferenceId(), 
					officeMsgRecycle.getDeleteTime(), officeMsgRecycle.getState(), 
					officeMsgRecycle.getCustomFolderId(), officeMsgRecycle.getTitle(), 
					officeMsgRecycle.getIsEmergency(), officeMsgRecycle.getSendTime(), 
					officeMsgRecycle.getUserId(), officeMsgRecycle.getMsgtype()
				};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.TIMESTAMP,
				Types.INTEGER, Types.CHAR, Types.VARCHAR, Types.INTEGER,
				Types.TIMESTAMP, Types.CHAR, Types.INTEGER
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
		
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_msg_recycle where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeMsgRecycle officeMsgRecycle){
		String sql = "update office_msg_recycle set reference_id = ?, delete_time = ?, state = ?, custom_folder_id = ?, title = ?, is_emergency = ?, send_time = ?, user_id = ?, msgtype = ? where id = ?";
		Object[] args = new Object[]{
			officeMsgRecycle.getReferenceId(), officeMsgRecycle.getDeleteTime(), 
			officeMsgRecycle.getState(), officeMsgRecycle.getCustomFolderId(), 
			officeMsgRecycle.getTitle(), officeMsgRecycle.getIsEmergency(), 
			officeMsgRecycle.getSendTime(), officeMsgRecycle.getUserId(), 
			officeMsgRecycle.getMsgtype(), officeMsgRecycle.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeMsgRecycle getOfficeMsgRecycleById(String id){
		String sql = "select * from office_msg_recycle where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeMsgRecycle> getOfficeMsgRecycleMapByIds(String[] ids){
		String sql = "select * from office_msg_recycle where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeMsgRecycle> getOfficeMsgRecycleList(String[] ids){
		String sql = "select * from office_msg_recycle where id in ";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public List<OfficeMsgRecycle> getOfficeMsgRecyclePage(String userId, String searchTitle, Pagination page){
		String sql = "select * from office_msg_recycle where user_id = ? ";
		List<Object> objs = new ArrayList<Object>();
		objs.add(userId);
		if(StringUtils.isNotBlank(searchTitle)){
			sql += " and title like ? ";
			objs.add("%"+searchTitle+"%");
		}
		sql += " order by delete_time desc ";
		return query(sql, objs.toArray(),new MultiRow(), page);
	}
}