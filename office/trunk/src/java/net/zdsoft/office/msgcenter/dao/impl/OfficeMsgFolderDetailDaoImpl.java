package net.zdsoft.office.msgcenter.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dao.OfficeMsgFolderDetailDao;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolderDetail;

import org.apache.commons.lang.StringUtils;
/**
 * 文件夹详细信息
 * @author 
 * 
 */
public class OfficeMsgFolderDetailDaoImpl extends BaseDao<OfficeMsgFolderDetail> implements OfficeMsgFolderDetailDao{

	private static final String SQL_INSERT = "insert into office_msg_folder_detail(id, folder_id, reference_id, reference_state, creation_time, title, is_emergency, send_time, user_id, msg_type, is_deleted, is_copy) values(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	@Override
	public OfficeMsgFolderDetail setField(ResultSet rs) throws SQLException{
		OfficeMsgFolderDetail officeMsgFolderDetail = new OfficeMsgFolderDetail();
		officeMsgFolderDetail.setId(rs.getString("id"));
		officeMsgFolderDetail.setFolderId(rs.getString("folder_id"));
		officeMsgFolderDetail.setReferenceId(rs.getString("reference_id"));
		officeMsgFolderDetail.setReferenceState(rs.getInt("reference_state"));
		officeMsgFolderDetail.setCreationTime(rs.getTimestamp("creation_time"));
		officeMsgFolderDetail.setTitle(rs.getString("title"));
		officeMsgFolderDetail.setIsEmergency(rs.getInt("is_emergency"));
		officeMsgFolderDetail.setSendTime(rs.getTimestamp("send_time"));
		officeMsgFolderDetail.setUserId(rs.getString("user_id"));
		officeMsgFolderDetail.setMsgType(rs.getInt("msg_type"));
		officeMsgFolderDetail.setIsDeleted(rs.getBoolean("is_deleted"));
		officeMsgFolderDetail.setIsCopy(rs.getInt("is_copy"));
		return officeMsgFolderDetail;
	}

	@Override
	public OfficeMsgFolderDetail save(OfficeMsgFolderDetail officeMsgFolderDetail){
		if (StringUtils.isBlank(officeMsgFolderDetail.getId())){
			officeMsgFolderDetail.setId(createId());
		}
		Object[] args = new Object[]{
			officeMsgFolderDetail.getId(), officeMsgFolderDetail.getFolderId(), 
			officeMsgFolderDetail.getReferenceId(), officeMsgFolderDetail.getReferenceState(), 
			officeMsgFolderDetail.getCreationTime(), officeMsgFolderDetail.getTitle(), 
			officeMsgFolderDetail.getIsEmergency(), officeMsgFolderDetail.getSendTime(), 
			officeMsgFolderDetail.getUserId(), officeMsgFolderDetail.getMsgType(),
			officeMsgFolderDetail.getIsDeleted(),officeMsgFolderDetail.getIsCopy()
		};
		update(SQL_INSERT, args);
		return officeMsgFolderDetail;
	}
	
	
	@Override
	public void batchSave(List<OfficeMsgFolderDetail> officeMsgFolderDetails) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < officeMsgFolderDetails.size(); i++) {
			OfficeMsgFolderDetail officeMsgFolderDetail = officeMsgFolderDetails.get(i);
			if (StringUtils.isBlank(officeMsgFolderDetail.getId()))
				officeMsgFolderDetail.setId(getGUID());
			Object[] args = new Object[]{
					officeMsgFolderDetail.getId(), officeMsgFolderDetail.getFolderId(), 
					officeMsgFolderDetail.getReferenceId(), officeMsgFolderDetail.getReferenceState(), 
					officeMsgFolderDetail.getCreationTime(), officeMsgFolderDetail.getTitle(), 
					officeMsgFolderDetail.getIsEmergency(), officeMsgFolderDetail.getSendTime(), 
					officeMsgFolderDetail.getUserId(), officeMsgFolderDetail.getMsgType(),
					officeMsgFolderDetail.getIsDeleted(), officeMsgFolderDetail.getIsCopy()
				};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR,
				Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR, Types.INTEGER,
				Types.TIMESTAMP, Types.CHAR, Types.INTEGER, Types.BOOLEAN,
				Types.INTEGER
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
		
	}
	
	@Override
	public void updateFolderIdByIds(String[] ids, String folderId) {
		//这里加上is_deleted，主要为了从废件箱还原，将is_deleted设为0
		String sql = "update office_msg_folder_detail set folder_id = ?, is_deleted = 0 where id in ";
		updateForInSQL(sql, new Object[]{folderId}, ids);
	}
	
	@Override
	public void deleteById(String id) {
		String sql = "delete from office_msg_folder_detail where id = ?";
		update(sql, id);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_msg_folder_detail set is_deleted = 1 where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public boolean isExist(String folderId) {
		String sql = "select count(1) count from office_msg_folder_detail where is_deleted = 0 and folder_id = ? ";
		int i = queryForInt(sql, new Object[]{folderId});
		if(i > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public void updateRevertById(String id) {
		String sql = "update office_msg_folder_detail set is_deleted = 0 where id = ? ";
		update(sql,id);
	}

	@Override
	public Integer update(OfficeMsgFolderDetail officeMsgFolderDetail){
		String sql = "update office_msg_folder_detail set folder_id = ?, reference_id = ?, reference_state = ?, creation_time = ?, title = ?, is_emergency = ?, send_time = ?, user_id = ?, msg_type = ?, is_deleted = ? where id = ?";
		Object[] args = new Object[]{
			officeMsgFolderDetail.getFolderId(), officeMsgFolderDetail.getReferenceId(), 
			officeMsgFolderDetail.getReferenceState(), officeMsgFolderDetail.getCreationTime(), 
			officeMsgFolderDetail.getTitle(), officeMsgFolderDetail.getIsEmergency(), 
			officeMsgFolderDetail.getSendTime(), officeMsgFolderDetail.getUserId(), 
			officeMsgFolderDetail.getMsgType(), officeMsgFolderDetail.getIsDeleted(),
			officeMsgFolderDetail.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeMsgFolderDetail getOfficeMsgFolderDetailById(String id){
		String sql = "select * from office_msg_folder_detail where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeMsgFolderDetail> getOfficeMsgFolderDetailMapByIds(String[] ids){
		String sql = "select * from office_msg_folder_detail where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeMsgFolderDetail> getOfficeMsgFolderDetailList(String[] ids){
		String sql = "select * from office_msg_folder_detail where id in";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public List<OfficeMsgFolderDetail> getOfficeMsgFolderDetailPage(String searchTitle, String folderId, Pagination page){
		String sql = "select * from office_msg_folder_detail where is_deleted = 0 and folder_id = ? ";
		if(StringUtils.isNotBlank(searchTitle)){
			sql += " and title like ? order by creation_time desc";
			return query(sql, new Object[]{folderId, "%"+searchTitle+"%"}, new MultiRow(), page);
		}
		sql += " order by creation_time desc";
		return query(sql, folderId, new MultiRow(), page);
	}
}