package net.zdsoft.office.msgcenter.dao.impl;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.office.msgcenter.dao.OfficeMsgSendingDao;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;

import org.apache.commons.lang.StringUtils;
/**
 * office_msg_sending
 * @author 
 * 
 */
public class OfficeMsgSendingDaoImpl extends BaseDao<OfficeMsgSending> implements OfficeMsgSendingDao{

	@Override
	public OfficeMsgSending setField(ResultSet rs) throws SQLException{
		OfficeMsgSending officeMsgSending = new OfficeMsgSending();
		officeMsgSending.setId(rs.getString("id"));
		officeMsgSending.setTitle(rs.getString("title"));
		officeMsgSending.setIsEmergency(rs.getInt("is_emergency"));
		Clob clob = rs.getClob("content");
		if(clob != null){
			officeMsgSending.setContent(clob.getSubString(1, (int)clob.length()));
		}
		officeMsgSending.setMsgType(rs.getInt("msg_type"));
		officeMsgSending.setIsNeedsms(rs.getBoolean("is_needsms"));
		officeMsgSending.setSmsContent(rs.getString("sms_content"));
		officeMsgSending.setState(rs.getInt("state"));
		officeMsgSending.setCreateUserId(rs.getString("create_user_id"));
		officeMsgSending.setUnitId(rs.getString("unit_id"));
		officeMsgSending.setCreateTime(rs.getTimestamp("create_time"));
		officeMsgSending.setSendTime(rs.getTimestamp("send_time"));
		officeMsgSending.setIsRead(rs.getInt("is_read"));
		officeMsgSending.setReplyMsgId(rs.getString("reply_msg_id"));
		officeMsgSending.setHasAttached(rs.getInt("has_attached"));
		officeMsgSending.setIsWithdraw(rs.getBoolean("is_withdraw"));
		Clob clob1 = rs.getClob("simple_content");
		if(clob1 != null){
			officeMsgSending.setSimpleContent(clob1.getSubString(1, (int)clob1.length()));
		}
		return officeMsgSending;
	}

	@Override
	public OfficeMsgSending save(OfficeMsgSending officeMsgSending){
		String sql = "insert into office_msg_sending(id, title, is_emergency, content, msg_type, is_needsms, sms_content, state, create_user_id, unit_id, create_time, send_time, is_read, reply_msg_id, has_attached, is_withdraw, simple_content) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeMsgSending.getId())){
			officeMsgSending.setId(createId());
		}
		Object[] args = new Object[]{
			officeMsgSending.getId(), officeMsgSending.getTitle(), 
			officeMsgSending.getIsEmergency(), officeMsgSending.getContent(), 
			officeMsgSending.getMsgType(), officeMsgSending.getIsNeedsms(), 
			officeMsgSending.getSmsContent(), officeMsgSending.getState(), 
			officeMsgSending.getCreateUserId(), officeMsgSending.getUnitId(), 
			officeMsgSending.getCreateTime(), officeMsgSending.getSendTime(), 
			officeMsgSending.getIsRead(), officeMsgSending.getReplyMsgId(), 
			officeMsgSending.getHasAttached(), officeMsgSending.getIsWithdraw(),
			officeMsgSending.getSimpleContent()
		};
		update(sql, args);
		return officeMsgSending;
	}

	@Override
	public void updateStateByIds(String[] ids,Integer state){
		String sql = "update office_msg_sending set state = ? where id in";
		updateForInSQL(sql, new Object[]{state}, ids);
	}
	
	@Override
	public void updateWithdraw(String id) {
		String sql = "update office_msg_sending set is_withdraw = 1 where id = ? ";
		update(sql, id);
	}
	
	@Override
	public void updateRevertById(String id, Integer state) {
		String sql = "update office_msg_sending set state = ? where id = ? ";
		update(sql,new Object[]{state,id});
	}

	@Override
	public Integer update(OfficeMsgSending officeMsgSending){
		String sql = "update office_msg_sending set title = ?, is_emergency = ?, content = ?, msg_type = ?, is_needsms = ?, sms_content = ?, state = ?, create_user_id = ?, unit_id = ?, create_time = ?, send_time = ?, is_read = ?, reply_msg_id = ?, has_attached = ?, is_withdraw = ?, simple_content = ? where id = ?";
		Object[] args = new Object[]{
			officeMsgSending.getTitle(), officeMsgSending.getIsEmergency(), 
			officeMsgSending.getContent(), officeMsgSending.getMsgType(), 
			officeMsgSending.getIsNeedsms(), officeMsgSending.getSmsContent(), 
			officeMsgSending.getState(), officeMsgSending.getCreateUserId(), 
			officeMsgSending.getUnitId(), officeMsgSending.getCreateTime(), 
			officeMsgSending.getSendTime(), officeMsgSending.getIsRead(), 
			officeMsgSending.getReplyMsgId(), officeMsgSending.getHasAttached(), 
			officeMsgSending.getIsWithdraw(), officeMsgSending.getSimpleContent(),
			officeMsgSending.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeMsgSending getOfficeMsgSendingById(String id){
		String sql = "select * from office_msg_sending where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public List<OfficeMsgSending> getOfficeMsgSendingList(String[] ids) {
		String sql = "select * from office_msg_sending where id in";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public Map<String, OfficeMsgSending> getOfficeMsgSendingMapByIds(String[] ids){
		String sql = "select * from office_msg_sending where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	public List<OfficeMsgSending> getOfficeMsgSendingList(MessageSearch messageSearch,Pagination page) {
    	String str = "select * from office_msg_sending where create_user_id = ? and state = ? ";
		StringBuffer sql = new StringBuffer(str);
    	List<Object> objs = new ArrayList<Object>();
    	objs.add(messageSearch.getUserId());
    	objs.add(messageSearch.getState());
    	if(messageSearch.getMsgType()!=null && messageSearch.getMsgType()!=0){
			sql.append(" and msg_type  = ? ");
			objs.add(messageSearch.getMsgType());
		}
    	if (!Validators.isEmpty(messageSearch.getSearchTitle())) {
    		sql.append(" and title like ? ");
    		objs.add("%"+messageSearch.getSearchTitle()+"%");
    	}
    	if(!Validators.isEmpty(messageSearch.getSearchBeginTime())){
    		sql.append(" and  to_char(send_time,'yyyy-MM-dd') >= ? ");
    		objs.add(messageSearch.getSearchBeginTime());
    	}
    	if(!Validators.isEmpty(messageSearch.getSearchEndTime())){
    		sql.append(" and  to_char(send_time,'yyyy-MM-dd') <= ? ");
			objs.add(messageSearch.getSearchEndTime());
    	}
    	sql.append(" order by create_time desc");
    	return query(sql.toString(), objs.toArray(), new MultiRow(), page);
    }

	@Override
	public List<OfficeMsgSending> getOfficeMsgSendingPage(Pagination page){
		String sql = "select * from office_msg_sending";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeMsgSending> getOfficeMsgSendingByUnitIdList(String unitId){
		String sql = "select * from office_msg_sending where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeMsgSending> getOfficeMsgSendingByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_msg_sending where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public Integer getUnSendNum(String userId) {
		String sql = "select count(1) from office_msg_sending where state = 1 and create_user_id = ? ";
		return queryForInt(sql, new Object[]{userId});
	}
}