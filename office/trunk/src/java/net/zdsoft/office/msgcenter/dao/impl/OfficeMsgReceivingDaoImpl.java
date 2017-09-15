package net.zdsoft.office.msgcenter.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.msgcenter.dao.OfficeMsgReceivingDao;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;

import org.apache.commons.lang.StringUtils;
/**
 * 信息接受信息表
 * @author 
 * 
 */
public class OfficeMsgReceivingDaoImpl extends BaseDao<OfficeMsgReceiving> implements OfficeMsgReceivingDao{

	private static final String SQL_INSERT = "insert into office_msg_receiving(id, message_id, send_user_id, receive_user_id, send_username, title, receiver_type, msg_type, is_read, is_deleted, state, is_emergency, send_time, read_time, is_download_attachment, is_sms_received, reply_msg_id, has_attached, has_star, is_withdraw) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_FIND_MSG_READ_STATUS = "select t1.message_id,totalnum,nvl(readnum,0) readnum from (select message_id ,count(1) totalnum from office_msg_receiving group by message_id ) t1,(select message_id ,count(1) readnum from office_msg_receiving where is_read = 1 group by message_id )t2 where t1.message_id = t2.message_id(+) and t1.message_id in";
	
	private static final String SQL_FIND_RECEIVEUSERID = "SELECT * FROM (SELECT  DISTINCT receive_user_id FROM"
            + " (SELECT receive_user_id FROM office_msg_receiving WHERE receiver_type = 2 and SEND_USER_ID=? AND is_deleted =0  ORDER BY send_time DESC))WHERE rownum <?";
	
	@Override
	public OfficeMsgReceiving setField(ResultSet rs) throws SQLException{
		OfficeMsgReceiving officeMsgReceiving = new OfficeMsgReceiving();
		officeMsgReceiving.setId(rs.getString("id"));
		officeMsgReceiving.setMessageId(rs.getString("message_id"));
		officeMsgReceiving.setSendUserId(rs.getString("send_user_id"));
		officeMsgReceiving.setReceiveUserId(rs.getString("receive_user_id"));
		officeMsgReceiving.setSendUsername(rs.getString("send_username"));
		officeMsgReceiving.setTitle(rs.getString("title"));
		officeMsgReceiving.setReceiverType(rs.getInt("receiver_type"));
		officeMsgReceiving.setMsgType(rs.getInt("msg_type"));
		officeMsgReceiving.setIsRead(rs.getInt("is_read"));
		officeMsgReceiving.setIsDeleted(rs.getBoolean("is_deleted"));
		officeMsgReceiving.setState(rs.getInt("state"));
		officeMsgReceiving.setIsEmergency(rs.getInt("is_emergency"));
		officeMsgReceiving.setSendTime(rs.getTimestamp("send_time"));
		officeMsgReceiving.setReadTime(rs.getTimestamp("read_time"));
		officeMsgReceiving.setIsDownloadAttachment(rs.getBoolean("is_download_attachment"));
		officeMsgReceiving.setIsSmsReceived(rs.getBoolean("is_sms_received"));
		officeMsgReceiving.setReplyMsgId(rs.getString("reply_msg_id"));
		officeMsgReceiving.setHasAttached(rs.getInt("has_attached"));
		officeMsgReceiving.setHasStar(rs.getInt("has_star"));
		officeMsgReceiving.setIsWithdraw(rs.getBoolean("is_withdraw"));
		officeMsgReceiving.setNeedTodo(rs.getInt("need_todo"));
		return officeMsgReceiving;
	}

	@Override
	public OfficeMsgReceiving save(OfficeMsgReceiving officeMsgReceiving){
		if (StringUtils.isBlank(officeMsgReceiving.getId())){
			officeMsgReceiving.setId(createId());
		}
		Object[] args = new Object[]{
			officeMsgReceiving.getId(), officeMsgReceiving.getMessageId(), 
			officeMsgReceiving.getSendUserId(), officeMsgReceiving.getReceiveUserId(), 
			officeMsgReceiving.getSendUsername(), officeMsgReceiving.getTitle(), 
			officeMsgReceiving.getReceiverType(), officeMsgReceiving.getMsgType(), 
			officeMsgReceiving.getIsRead(), officeMsgReceiving.getIsDeleted(), 
			officeMsgReceiving.getState(), officeMsgReceiving.getIsEmergency(), 
			officeMsgReceiving.getSendTime(), officeMsgReceiving.getReadTime(), 
			officeMsgReceiving.getIsDownloadAttachment(), officeMsgReceiving.getIsSmsReceived(), 
			officeMsgReceiving.getReplyMsgId(), officeMsgReceiving.getHasAttached(),
			officeMsgReceiving.getHasStar(), officeMsgReceiving.getIsWithdraw()
		};
		update(SQL_INSERT, args);
		return officeMsgReceiving;
	}
	
	@Override
	public void batchSave(List<OfficeMsgReceiving> officeMsgReceivings) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < officeMsgReceivings.size(); i++) {
			OfficeMsgReceiving officeMsgReceiving = officeMsgReceivings.get(i);
			if (StringUtils.isBlank(officeMsgReceiving.getId()))
				officeMsgReceiving.setId(getGUID());
			Object[] args = new Object[]{
					officeMsgReceiving.getId(), officeMsgReceiving.getMessageId(), 
					officeMsgReceiving.getSendUserId(), officeMsgReceiving.getReceiveUserId(), 
					officeMsgReceiving.getSendUsername(), officeMsgReceiving.getTitle(), 
					officeMsgReceiving.getReceiverType(), officeMsgReceiving.getMsgType(), 
					officeMsgReceiving.getIsRead(), officeMsgReceiving.getIsDeleted(), 
					officeMsgReceiving.getState(), officeMsgReceiving.getIsEmergency(), 
					officeMsgReceiving.getSendTime(), officeMsgReceiving.getReadTime(), 
					officeMsgReceiving.getIsDownloadAttachment(), officeMsgReceiving.getIsSmsReceived(), 
					officeMsgReceiving.getReplyMsgId(), officeMsgReceiving.getHasAttached(),
					officeMsgReceiving.getHasStar(), officeMsgReceiving.getIsWithdraw()
				};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.BOOLEAN, Types.INTEGER,
				Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP, Types.BOOLEAN,
				Types.BOOLEAN, Types.CHAR, Types.INTEGER, Types.INTEGER, Types.BOOLEAN
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
		
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_msg_receiving set is_deleted = 1 where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public void updateRevertById(String id) {
		String sql = "update office_msg_receiving set is_deleted = 0 where id = ? ";
		update(sql,id);
	}
	
	@Override
	public void deleteByReplyMsgIds(String receiveUserId, String[] replyMsgIds) {
		String sql = "update office_msg_receiving set is_deleted = 1 where receive_user_id = ? and is_deleted = 0 and reply_msg_id in";
		updateForInSQL(sql, new Object[]{receiveUserId}, replyMsgIds);
		
	}

	@Override
	public Integer update(OfficeMsgReceiving officeMsgReceiving){
		String sql = "update office_msg_receiving set message_id = ?, send_user_id = ?, receive_user_id = ?, send_username = ?, title = ?, receiver_type = ?, msg_type = ?, is_read = ?, is_deleted = ?, state = ?, is_emergency = ?, send_time = ?, read_time = ?, is_download_attachment = ?, is_sms_received = ?, reply_msg_id = ?, has_attached = ?, has_star = ? where id = ?";
		Object[] args = new Object[]{
			officeMsgReceiving.getMessageId(), officeMsgReceiving.getSendUserId(), 
			officeMsgReceiving.getReceiveUserId(), officeMsgReceiving.getSendUsername(), 
			officeMsgReceiving.getTitle(), officeMsgReceiving.getReceiverType(), 
			officeMsgReceiving.getMsgType(), officeMsgReceiving.getIsRead(), 
			officeMsgReceiving.getIsDeleted(), officeMsgReceiving.getState(), 
			officeMsgReceiving.getIsEmergency(), officeMsgReceiving.getSendTime(), 
			officeMsgReceiving.getReadTime(), officeMsgReceiving.getIsDownloadAttachment(), 
			officeMsgReceiving.getIsSmsReceived(), officeMsgReceiving.getReplyMsgId(),
			officeMsgReceiving.getHasAttached(),officeMsgReceiving.getHasStar(),
			officeMsgReceiving.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public void updateReadAll(String receiveUserId) {
		String sql = "update office_msg_receiving set is_read = 1, read_time=? where receive_user_id = ? and is_deleted = 0 ";
		update(sql,  new Object[]{new Date(), receiveUserId} );
	}
	
	@Override
	public void updateRead(String id) {
		String sql = "update office_msg_receiving set is_read = 1, read_time = ? where id = ? and is_read = 0 ";
		update(sql,new Object[]{new Date(), id});
	}
	@Override
	public void changeState(String receiveUserId,String[] ids,int state) {
		String sql = "update office_msg_receiving set is_read =? ";
		if(state==1){
			Date date=new Date();
			sql+=", read_time =? where is_read=0 and  receive_user_id =? and reply_msg_id in";
			updateForInSQL(sql, new Object[]{state,date,receiveUserId}, ids);
		}else{
			sql+=" where receive_user_id =? and reply_msg_id in";
			updateForInSQL(sql, new Object[]{state,receiveUserId}, ids);
		}
	}
	@Override
	public void updateIsWithdraw(String messageId) {
		String sql = "update office_msg_receiving set is_withdraw = 1 where message_id = ? ";
		update(sql, messageId);
	}
	
	@Override
	public Integer getNumber(String userId, String readType) {
		String sql = "select count(1) from office_msg_receiving where receive_user_id = ? and is_deleted = 0 ";
		int i = 0;
		if(StringUtils.isNotBlank(readType)){
			sql += " and is_read = ? ";
			i = queryForInt(sql, new Object[]{userId, readType});
			return i;
		}
		i = queryForInt(sql, new Object[]{userId});
		return i;
	}
	public Integer getNumberForReceive(MessageSearch search, String msgType, String readType){
		String sql = "select count(1) from office_msg_receiving where is_deleted = 0 and ( 1!=1 ";
		int i = 0;
		List<Object> objs = new ArrayList<Object>();
		if(StringUtils.isNotBlank(search.getReceiveUserId())){
			sql+=" or receive_user_id = ? ";
			objs.add(search.getReceiveUserId());
		}
		if(StringUtils.isNotBlank(search.getReceiveDeptId())){
			sql+=" or receive_user_id = ? ";
			objs.add(search.getReceiveDeptId());
		}
		if(StringUtils.isNotBlank(search.getReceiveUnitId())){
			sql+=" or receive_user_id = ? ";
			objs.add(search.getReceiveUnitId());
		}
		sql+=" ) ";
		if(StringUtils.isNotBlank(msgType)){
			if(StringUtils.equals(msgType, BaseConstant.MSG_TYPE_OFFICE+"")){
				sql += " and msg_type <>1 and msg_type <>2 ";
			}else{
				sql += " and msg_type = ? ";
				objs.add(msgType);
			}
		}
		if(StringUtils.isNotBlank(readType)){
			sql += " and is_read = ? ";
			objs.add(readType);
		}
		i = queryForInt(sql, objs.toArray());
		return i;
	}
	@Override
	public Integer getNumber(String userId, String msgType, String readType) {
		String sql = "select count(1) from office_msg_receiving where receive_user_id = ? and is_deleted = 0 ";
		int i = 0;
		List<Object> objs = new ArrayList<Object>();
		objs.add(userId);
		if(StringUtils.isNotBlank(msgType)){
			if(StringUtils.equals(msgType, BaseConstant.MSG_TYPE_OFFICE+"")){
				sql += " and msg_type <>1 and msg_type <>2 ";
			}else{
				sql += " and msg_type = ? ";
				objs.add(msgType);
			}
		}
		if(StringUtils.isNotBlank(readType)){
			sql += " and is_read = ? ";
			objs.add(readType);
		}
		i = queryForInt(sql, objs.toArray());
		return i;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> findMessageReadStatus(String[] msgSendIds) {
    	String sql = SQL_FIND_MSG_READ_STATUS;
    	return queryForInSQL(sql, null, msgSendIds, new MapRowMapper() {
            @Override
            public Object mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("message_id");
            }

            @Override
            public Object mapRowValue(ResultSet rs, int rowNum) throws SQLException {
                return "[" + rs.getInt("readnum") + "]/" + rs.getInt("totalnum");
            }

        });
    }

	@Override
	public OfficeMsgReceiving getOfficeMsgReceivingById(String id){
		String sql = "select * from office_msg_receiving where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public List<String> getIsReadInfo(String messageId, Integer isRead) {
		String sql = "select receive_user_id from office_msg_receiving where message_id = ? and is_read = ? ";
		return query(sql, new Object[]{messageId, isRead}, new MultiRowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("receive_user_id");
			}
		});
	}

	@Override
	public Map<String, OfficeMsgReceiving> getOfficeMsgReceivingMapByIds(String[] ids){
		String sql = "select * from office_msg_receiving where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingList(
			String receiveUserId, String[] replyMsgIds) {
		String sql = "select * from office_msg_receiving where receive_user_id = ? and is_deleted = 0 and reply_msg_id in ";
		return queryForInSQL(sql, new Object[]{receiveUserId}, replyMsgIds, new MultiRow());
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingList(String[] ids) {
		String sql = "select * from office_msg_receiving where id in ";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingList(MessageSearch messageSearch, Pagination page){
		String sql = "select * from (select office_msg_receiving.*, row_number() " +
				" over(partition by reply_msg_id order by send_time desc) cn from office_msg_receiving" +
				" where is_deleted = 0 and (  1!=1 ";
		StringBuffer sbf = new StringBuffer(sql);
		List<Object> objs = new ArrayList<Object>();
		if(StringUtils.isNotBlank(messageSearch.getReceiveUserId())){
			sbf.append(" or receive_user_id = ? ");
			objs.add(messageSearch.getReceiveUserId());
		}
		if(StringUtils.isNotBlank(messageSearch.getReceiveDeptId())){
			sbf.append(" or receive_user_id = ? ");
			objs.add(messageSearch.getReceiveDeptId());
		}
		if(StringUtils.isNotBlank(messageSearch.getReceiveUnitId())){
			sbf.append(" or receive_user_id = ? ");
			objs.add(messageSearch.getReceiveUnitId());
		}
		sbf.append(") ");
		
		if(messageSearch.getMsgType()!=null && messageSearch.getMsgType()!=0){
			sbf.append(" and msg_type  = ? ");
			objs.add(messageSearch.getMsgType());
		}
		if(StringUtils.isNotBlank(messageSearch.getSearchTitle())){
			sbf.append(" and title like ? ");
			objs.add("%"+messageSearch.getSearchTitle()+"%");
		}
		if(StringUtils.isNotBlank(messageSearch.getReadType())){
			sbf.append(" and is_read = ? ");
			objs.add(messageSearch.getReadType());
		}
		if(StringUtils.isNotBlank(messageSearch.getSearchSender())){
			sbf.append(" and send_username like ? ");
			objs.add("%"+messageSearch.getSearchSender()+"%");
		}
		if(StringUtils.isNotBlank(messageSearch.getSearchBeginTime())){
			sbf.append(" and to_char(send_time,'yyyy-MM-dd') >= ? ");
			objs.add(messageSearch.getSearchBeginTime());
		}
		if(StringUtils.isNotBlank(messageSearch.getSearchEndTime())){
			sbf.append(" and to_char(send_time,'yyyy-MM-dd') <= ? ");
			objs.add(messageSearch.getSearchEndTime());
		}
		if(StringUtils.isNotBlank(messageSearch.getSearchTitleORSender())){
			sbf.append(" and (title like ? or send_username like ?) ");
			objs.add("%"+messageSearch.getSearchTitleORSender()+"%");
			objs.add("%"+messageSearch.getSearchTitleORSender()+"%");
		}
		
		sbf.append(") where cn = 1 order by send_time desc");
		return query(sbf.toString(), objs.toArray(), new MultiRow(), page);
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingImportList(
			MessageSearch messageSearch, Pagination page) {
		String sql = "select * from (select office_msg_receiving.*, row_number() " +
				" over(partition by reply_msg_id order by send_time desc) cn from office_msg_receiving" +
				" where receive_user_id = ? and is_deleted = 0 ";
		StringBuffer sbf = new StringBuffer(sql);
		List<Object> objs = new ArrayList<Object>();
		objs.add(messageSearch.getReceiveUserId());
		if(messageSearch.getMsgType()!=null && messageSearch.getMsgType()!=0){
			sbf.append(" and msg_type  = ? ");
			objs.add(messageSearch.getMsgType());
		}
		if(StringUtils.isNotBlank(messageSearch.getSearchTitle())){
			sbf.append(" and title like ? ");
			objs.add("%"+messageSearch.getSearchTitle()+"%");
		}
		sbf.append(" and reply_msg_id in (select distinct reply_msg_id from office_msg_receiving where receive_user_id = ? and is_deleted = 0 ");
		objs.add(messageSearch.getReceiveUserId());
		if(messageSearch.getMsgType()!=null && messageSearch.getMsgType()!=0){
			sbf.append(" and msg_type  = ? ");
			objs.add(messageSearch.getMsgType());
		}
		sbf.append(" and has_star = 1)) where cn = 1 order by send_time desc");
		return query(sbf.toString(), objs.toArray(), new MultiRow(), page);
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingTodoList(
			MessageSearch messageSearch, Pagination page) {
		String sql = "select * from (select office_msg_receiving.*, row_number() " +
				" over(partition by reply_msg_id order by send_time desc) cn from office_msg_receiving" +
				" where receive_user_id = ? and is_deleted = 0 ";
		StringBuffer sbf = new StringBuffer(sql);
		List<Object> objs = new ArrayList<Object>();
		objs.add(messageSearch.getReceiveUserId());
		if(messageSearch.getMsgType()!=null && messageSearch.getMsgType()!=0){
			sbf.append(" and msg_type  = ? ");
			objs.add(messageSearch.getMsgType());
		}
		if(StringUtils.isNotBlank(messageSearch.getSearchTitle())){
			sbf.append(" and title like ? ");
			objs.add("%"+messageSearch.getSearchTitle()+"%");
		}
		sbf.append(" and reply_msg_id in (select distinct reply_msg_id from office_msg_receiving where receive_user_id = ? and is_deleted = 0 ");
		objs.add(messageSearch.getReceiveUserId());
		if(messageSearch.getMsgType()!=null && messageSearch.getMsgType()!=0){
			sbf.append(" and msg_type  = ? ");
			objs.add(messageSearch.getMsgType());
		}
		sbf.append(" and need_todo = 1)) where cn = 1 order by send_time desc ");
		return query(sbf.toString(), objs.toArray(), new MultiRow(), page);
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByMessageId(
			String messageId) {
		String sql = "select * from office_msg_receiving where message_id = ? ";
		return query(sql, messageId, new MultiRow());
	}
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByMessageIds(String[] messageIds){
		String sql = "select * from office_msg_receiving where message_id in ";
		return queryForInSQL(sql,null, messageIds,  new MultiRow());
	}
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByReplyMsgIdForMobile(
			String replyMsgId, String[] allIds) {
		String sql = "select * from office_msg_receiving where  is_deleted = 0 and reply_msg_id = ? and receive_user_id in";
		return queryForInSQL(sql, new Object[]{replyMsgId}, allIds, new MultiRow());
	}
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByReplyMsgId(
			String replyMsgId, String userId) {
		String sql = "select * from office_msg_receiving where receive_user_id = ? and is_deleted = 0 and reply_msg_id = ? ";
		return query(sql, new Object[]{userId, replyMsgId}, new MultiRow());
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListSend(
			String replyMsgId, String userId) {
		String sql = "select * from (select office_msg_receiving.*, row_number() " +
				" over(partition by message_id order by send_time desc) cn from office_msg_receiving" +
				" where reply_msg_id = ? and send_user_id = ?) where cn = 1 ";
		return query(sql, new Object[]{replyMsgId, userId}, new MultiRow());
	}
	
	@Override
	public Map<String, Integer> getOfficeMsgReceivingCountMap(
			String[] replyMsgId, String userId) {
		String sqlInCondition = SQLUtils.toSQLInString(replyMsgId);
		String sql = "select reply_msg_id, count(reply_msg_id) as countNum from (select * from (select office_msg_receiving.*, row_number() " +
				" over(partition by message_id order by send_time desc) cn from office_msg_receiving" +
				" where reply_msg_id in "+sqlInCondition+" and (send_user_id = ? or (receive_user_id = ? and is_deleted = 0))) where cn = 1) group by reply_msg_id ";
		return queryForMap(sql, new Object[]{userId,userId}, new MapRowMapper<String, Integer>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("reply_msg_id");
			}
			@Override
			public Integer mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("countNum");
			}
		});
	}
	
	@Override
	public Map<String, String> getStarMapByreplyMsgIds(String receiveUserId, String[] replyMsgIds) {
		String sql = "select distinct reply_msg_id from office_msg_receiving where receive_user_id = ? and is_deleted = 0 and has_star = 1 and reply_msg_id in ";
		return queryForInSQL(sql, new Object[]{receiveUserId}, replyMsgIds, new MapRowMapper<String, String>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("reply_msg_id");
			}
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("reply_msg_id");
			}
		});
	}
	
	@Override
	public Map<String, String> getNeedTodoMapByreplyMsgIds(
			String receiveUserId, String[] replyMsgIds) {
		String sql = "select distinct reply_msg_id from office_msg_receiving where receive_user_id = ? and is_deleted = 0 and need_todo = 1 and reply_msg_id in ";
		return queryForInSQL(sql, new Object[]{receiveUserId}, replyMsgIds, new MapRowMapper<String, String>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("reply_msg_id");
			}
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("reply_msg_id");
			}
		});
	}
	
	@Override
	public Map<String, String> getReceivingUnReadMapByreplyMsgIds(
			String receiveUserId, String[] replyMsgIds) {
		String sql = "select distinct reply_msg_id from office_msg_receiving where receive_user_id = ? and is_deleted = 0 and is_read = 0 and reply_msg_id in ";
		return queryForInSQL(sql, new Object[]{receiveUserId}, replyMsgIds, new MapRowMapper<String, String>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("reply_msg_id");
			}
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("reply_msg_id");
			}
		});
	}
	
	@Override
	public void updateAllStar(String receiveUserId, String replyMsgId,
			Integer hasStar) {
		String sql = "update office_msg_receiving set has_star = ? where receive_user_id = ? and is_deleted = 0 and reply_msg_id = ? ";
		update(sql, new Object[]{hasStar,receiveUserId,replyMsgId});
	}
	
	@Override
	public void updateAllNeedTodo(String receiveUserId, String replyMsgId,
			Integer needTodo) {
		String sql = "update office_msg_receiving set need_todo = ? where receive_user_id = ? and is_deleted = 0 and reply_msg_id = ? ";
		update(sql, new Object[]{needTodo,receiveUserId,replyMsgId});
	}
	
	@Override
	public void updateNeedTodo(String id, Integer needTodo) {
		String sql = "update office_msg_receiving set need_todo = ? where is_deleted = 0 and id = ? ";
		update(sql, new Object[]{needTodo, id});
	}
	
	@Override
	public void updateStar(String id, Integer hasStar) {
		String sql = "update office_msg_receiving set has_star = ? where is_deleted = 0 and id = ? ";
		int i=update(sql, new Object[]{hasStar, id});
		System.out.println(i);
	}
	
	@Override
	public List<String> findReceiveUserIds(String sendUserId, int size) {
        return query(SQL_FIND_RECEIVEUSERID, new Object[] { sendUserId, size + 1 }, new MultiRowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int arg1) throws SQLException {
                return rs.getString("receive_user_id");
            }
        });
    }
	
	@Override
	public String[] getChangedReceivingUserIds(String time) {
		String sql = "select distinct receive_user_id from office_msg_receiving where send_time > to_date(?,'yyyy-MM-dd HH24:mi:ss') or read_time > to_date(?,'yyyy-MM-dd HH24:mi:ss')";
		List<String> userIdList = query(sql, new Object[]{time,time}, new MultiRowMapper(){
    		@Override
    		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
    			return rs.getString("receive_user_id");
    		}
    	});
    	return userIdList.toArray(new String[0]);
	}
	
	@Override
	public Map<String, Integer> getMsgReceivingMap(String[] userIds) {
		String sql = "select receive_user_id, count(1) countNum from office_msg_receiving where is_read = 0 and is_deleted = 0 and receive_user_id in ";
		return queryForInSQL(sql, null, userIds, new MapRowMapper() {
			
			@Override
			public Object mapRowValue(ResultSet rs, int arg1) throws SQLException {
				return rs.getInt("countNum");
			}
			
			@Override
			public Object mapRowKey(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("receive_user_id");
			}
		}, " group by receive_user_id ");
	}
	
	@Override
	public List<OfficeMsgReceiving> getMsgUnPushed(Pagination page) {
		String sql = " select * from office_msg_receiving where is_read = 0 and is_deleted = 0 and is_push = 0 ";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public void updatePushed(String[] ids) {
		String sql = " update office_msg_receiving set is_push = 1 where id in ";
		updateForInSQL(sql, null, ids);
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivings(String receiveUserId){
		String sql = "select * from (select office_msg_receiving.*, row_number() " +
				" over(partition by reply_msg_id order by send_time desc) cn from office_msg_receiving" +
				" where receive_user_id = ? and is_deleted = 0 ) where cn = 1 order by send_time desc";
		return query(sql, new Object[]{receiveUserId}, new MultiRow());
	}
	
	@Override
	public Map<String, Date> getReadDate(String messageId){
		String sql = "select * from office_msg_receiving where message_id = ? and is_read = 1 ";
		return queryForMap(sql, new Object[]{messageId}, new MapRowMapper<String, Date>() {
			
			@Override
			public Date mapRowValue(ResultSet rs, int arg1) throws SQLException {
				return rs.getTimestamp("read_time");
			}
			
			@Override
			public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("receive_user_id");
			}
		});
	}
}