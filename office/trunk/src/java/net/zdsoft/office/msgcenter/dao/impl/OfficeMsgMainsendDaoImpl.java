package net.zdsoft.office.msgcenter.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dao.OfficeMsgMainsendDao;
import net.zdsoft.office.msgcenter.entity.OfficeMsgMainsend;

import org.apache.commons.lang.StringUtils;
/**
 * 信息主送信息表
 * @author 
 * 
 */
public class OfficeMsgMainsendDaoImpl extends BaseDao<OfficeMsgMainsend> implements OfficeMsgMainsendDao{

	private static final String SQL_INSERT = "insert into office_msg_mainsend(id, message_id, receiver_id, receiver_name, receiver_type) values(?,?,?,?,?)";
	
	@Override
	public OfficeMsgMainsend setField(ResultSet rs) throws SQLException{
		OfficeMsgMainsend officeMsgMainsend = new OfficeMsgMainsend();
		officeMsgMainsend.setId(rs.getString("id"));
		officeMsgMainsend.setMessageId(rs.getString("message_id"));
		officeMsgMainsend.setReceiverId(rs.getString("receiver_id"));
		officeMsgMainsend.setReceiverName(rs.getString("receiver_name"));
		officeMsgMainsend.setReceiverType(rs.getInt("receiver_type"));
		return officeMsgMainsend;
	}

	@Override
	public OfficeMsgMainsend save(OfficeMsgMainsend officeMsgMainsend){
		if (StringUtils.isBlank(officeMsgMainsend.getId())){
			officeMsgMainsend.setId(createId());
		}
		Object[] args = new Object[]{
			officeMsgMainsend.getId(), officeMsgMainsend.getMessageId(), 
			officeMsgMainsend.getReceiverId(), officeMsgMainsend.getReceiverName(), 
			officeMsgMainsend.getReceiverType()
		};
		update(SQL_INSERT, args);
		return officeMsgMainsend;
	}
	
	@Override
	public void batchSave(List<OfficeMsgMainsend> officeMsgMainsends) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeMsgMainsend officeMsgMainsend : officeMsgMainsends) {
			if (StringUtils.isBlank(officeMsgMainsend.getId())){
				officeMsgMainsend.setId(createId());
			}
			Object[] args = new Object[]{
				officeMsgMainsend.getId(), officeMsgMainsend.getMessageId(), 
				officeMsgMainsend.getReceiverId(), officeMsgMainsend.getReceiverName(), 
				officeMsgMainsend.getReceiverType()
			};
			listOfArgs.add(args);


        }
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.VARCHAR, Types.INTEGER };
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
		
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_msg_mainsend where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public void deleteByMessageId(String messageId) {
		String sql = "delete from office_msg_mainsend where message_id = ? ";
		update(sql,messageId);
	}

	@Override
	public Integer update(OfficeMsgMainsend officeMsgMainsend){
		String sql = "update office_msg_mainsend set message_id = ?, receiver_id = ?, receiver_name = ?, receiver_type = ? where id = ?";
		Object[] args = new Object[]{
			officeMsgMainsend.getMessageId(), officeMsgMainsend.getReceiverId(), 
			officeMsgMainsend.getReceiverName(), officeMsgMainsend.getReceiverType(), 
			officeMsgMainsend.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeMsgMainsend getOfficeMsgMainsendById(String id){
		String sql = "select * from office_msg_mainsend where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeMsgMainsend> getOfficeMsgMainsendMapByIds(String[] ids){
		String sql = "select * from office_msg_mainsend where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeMsgMainsend> getOfficeMsgMainsendList(String messageId){
		String sql = "select * from office_msg_mainsend where message_id = ? ";
		return query(sql, messageId, new MultiRow());
	}
	@Override
	public List<OfficeMsgMainsend> getOfficeMsgMainsendLists(String[] messageIds){
		String sql = "select * from office_msg_mainsend where message_id in ";
		return queryForInSQL(sql, null, messageIds, new MultiRow());
	}

	@Override
	public List<OfficeMsgMainsend> getOfficeMsgMainsendPage(Pagination page){
		String sql = "select * from office_msg_mainsend";
		return query(sql, new MultiRow(), page);
	}
}