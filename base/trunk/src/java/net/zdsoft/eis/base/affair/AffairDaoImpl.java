/* 
 * @(#)AffairDaoImpl.java    Created on Dec 30, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.affair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 30, 2010 2:14:30 PM $
 */
public class AffairDaoImpl extends BaseDao<Affair> implements AffairDao {

	private static final String SQL_INSERT_AFFAIR = "INSERT INTO sys_affair(id,identifier,title,"
			+ "content,receiver_id,sender_id,complete,affair_source,module_id,operation_heading,"
			+ "subsystem_id,creation_time,modify_time,begin_date,end_date,show_type) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_AFFAIR_BY_IDS = "DELETE FROM sys_affair WHERE id IN ";

	private static final String SQL_DELETE_AFFAIR_BY_IDENTIFIER_RECEIVER = "DELETE FROM sys_affair WHERE identifier=? AND receiver_id=?";

	private static final String SQL_UPDATE_AFFAIR = "UPDATE sys_affair SET identifier=?,title=?,"
			+ "content=?,receiver_id=?,sender_id=?,complete=?,affair_source=?,module_id=?,operation_heading=?,"
			+ "subsystem_id=?,modify_time=?,begin_date=?,end_date=?,show_type=? WHERE id=?";

	private static final String SQL_UPDATE_AFFAIR_COMPLETE = "UPDATE sys_affair SET "
			+ "complete=?,modify_time=? WHERE identifier=? AND receiver_id=?";

	private static final String SQL_UPDATE_AFFAIR_RECEIVERID = "UPDATE sys_affair SET "
			+ "receiver_id=?,modify_time=? WHERE affair_source=?";

	private static final String SQL_FIND_AFFAIR_BY_ID = "SELECT * FROM sys_affair WHERE id=?";
	private static final String SQL_FIND_AFFAIR_BY__IDENTIFIER_RECEIVER_SENDER = "SELECT * FROM sys_affair "
			+ "WHERE identifier=? AND receiver_id=? AND sender_id=?";
	private static final String SQL_FIND_AFFAIRS_BY_SOURCE = "SELECT * FROM sys_affair WHERE affair_source=? ";

	private static final String FIND_UNCOMPLETED_AFFAIR_BY_RECEIVER_ID = "select * from sys_affair "
			+ "where receiver_id = ? And complete = 0 order by modify_time desc";

	public Affair setField(ResultSet rs) throws SQLException {
		Affair affair = new Affair();
		affair.setId(rs.getString("id"));
		affair.setIdentifier(rs.getString("identifier"));
		affair.setTitle(rs.getString("title"));
		affair.setContent(rs.getString("content"));
		affair.setReceiverId(rs.getString("receiver_id"));
		affair.setSenderId(rs.getString("sender_id"));
		affair.setComplete(rs.getBoolean("complete"));
		affair.setAffairSource(rs.getInt("affair_source"));
		affair.setModuleId(rs.getInt("module_id"));
		affair.setOperationHeading(rs.getString("operation_heading"));
		affair.setSubsystemId(rs.getInt("subsystem_id"));
		affair.setCreationTime(rs.getTimestamp("creation_time"));
		affair.setModifyTime(rs.getTimestamp("modify_time"));
		affair.setBeginDate(rs.getString("begin_date"));
		affair.setEndDate(rs.getString("end_date"));
		affair.setShowType(rs.getInt("show_type"));
		return affair;
	}

	public void addAffair(Affair affair) {
		affair.setId(createId());
		affair.setCreationTime(new Date());
		update(SQL_INSERT_AFFAIR,
				new Object[] { affair.getId(), affair.getIdentifier(),
						affair.getTitle(), affair.getContent(),
						affair.getReceiverId(), affair.getSenderId(),
						affair.isComplete(), affair.getAffairSource(),
						affair.getModuleId(), affair.getOperationHeading(),
						affair.getSubsystemId(), affair.getCreationTime(),
						affair.getModifyTime(), affair.getBeginDate(),
						affair.getEndDate(), affair.getShowType() }, new int[] {
						Types.CHAR, Types.VARCHAR, Types.VARCHAR,
						Types.VARCHAR, Types.CHAR, Types.CHAR, Types.INTEGER,
						Types.INTEGER, Types.INTEGER, Types.VARCHAR,
						Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP,
						Types.CHAR, Types.CHAR, Types.INTEGER });
	}

	public void deleteAffairs(String[] affairIds) {
		updateForInSQL(SQL_DELETE_AFFAIR_BY_IDS, null, affairIds);
	}

	public void deleteAffair(String identifier, String receiverId) {
		update(SQL_DELETE_AFFAIR_BY_IDENTIFIER_RECEIVER, new Object[] {
				identifier, receiverId });
	}

	public void updateAffair(Affair affair) {
		update(SQL_UPDATE_AFFAIR,
				new Object[] { affair.getIdentifier(), affair.getTitle(),
						affair.getContent(), affair.getReceiverId(),
						affair.getSenderId(), affair.isComplete(),
						affair.getAffairSource(), affair.getModuleId(),
						affair.getOperationHeading(), affair.getSubsystemId(),
						affair.getModifyTime(), affair.getBeginDate(),
						affair.getEndDate(), affair.getShowType(),
						affair.getId() }, new int[] { Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.CHAR,
						Types.INTEGER, Types.INTEGER, Types.INTEGER,
						Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP,
						Types.CHAR, Types.CHAR, Types.INTEGER, Types.CHAR });
	}

	public void updateAffairCompleteSign(Affair affair) {
		update(SQL_UPDATE_AFFAIR_COMPLETE, new Object[] {
				affair.isComplete() ? 1 : 0, new Date(),
				affair.getIdentifier(), affair.getReceiverId() }, new int[] {
				Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR, Types.CHAR });
	}

	public void updateAffairSystemReceiverId(String receiverId) {
		update(SQL_UPDATE_AFFAIR_RECEIVERID, new Object[] { receiverId,
				new Date(), Affair.AFFAIR_SOURCE_SYSTEM }, new int[] {
				Types.CHAR, Types.TIMESTAMP, Types.INTEGER });
	}

	public Affair getAffair(String affairId) {
		return query(SQL_FIND_AFFAIR_BY_ID, affairId, new SingleRow());
	}

	public Affair getAffair(String identifier, String receiverId,
			String senderId) {
		return query(SQL_FIND_AFFAIR_BY__IDENTIFIER_RECEIVER_SENDER,
				new Object[] { identifier, receiverId, senderId },
				new SingleRow());
	}

	public List<Affair> getAffairs(int affairSource) {
		return query(SQL_FIND_AFFAIRS_BY_SOURCE, new Object[] { affairSource },
				new MultiRow());
	}

	public List<Affair> getAffairByReceiverId(String receiverId) {
		return query(FIND_UNCOMPLETED_AFFAIR_BY_RECEIVER_ID,
				new Object[] { receiverId }, new MultiRow());
	}
	
	public List<Affair> getTopAffairByReceiverId(String receiverId,int rowNum) {
		return queryForTop(FIND_UNCOMPLETED_AFFAIR_BY_RECEIVER_ID,
				new Object[] { receiverId },new int[]{Types.CHAR}, new MultiRow(),rowNum);
	}

}
