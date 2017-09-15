package net.zdsoft.office.msgcenter.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.dao.OfficeMsgFolderDao;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolder;

import org.apache.commons.lang.StringUtils;
/**
 * 信息文件夹
 * @author 
 * 
 */
public class OfficeMsgFolderDaoImpl extends BaseDao<OfficeMsgFolder> implements OfficeMsgFolderDao{

	@Override
	public OfficeMsgFolder setField(ResultSet rs) throws SQLException{
		OfficeMsgFolder officeMsgFolder = new OfficeMsgFolder();
		officeMsgFolder.setId(rs.getString("id"));
		officeMsgFolder.setFolderName(rs.getString("folder_name"));
		officeMsgFolder.setCreationTime(rs.getTimestamp("creation_time"));
		officeMsgFolder.setOrderId(rs.getInt("order_id"));
		officeMsgFolder.setUserId(rs.getString("user_id"));
		return officeMsgFolder;
	}

	@Override
	public OfficeMsgFolder save(OfficeMsgFolder officeMsgFolder){
		String sql = "insert into office_msg_folder(id, folder_name, creation_time, order_id, user_id) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeMsgFolder.getId())){
			officeMsgFolder.setId(createId());
		}
		Object[] args = new Object[]{
			officeMsgFolder.getId(), officeMsgFolder.getFolderName(), 
			officeMsgFolder.getCreationTime(), officeMsgFolder.getOrderId(), 
			officeMsgFolder.getUserId()
		};
		update(sql, args);
		return officeMsgFolder;
	}
	
	@Override
	public boolean isExist(String id, String userId, String folderName) {
		String sql = "select count(1) from office_msg_folder where user_id = ? and folder_name = ? ";
		if(StringUtils.isNotBlank(id)){
			sql += " and id <> ? ";
			int i = queryForInt(sql, new Object[]{userId, folderName, id});
			if(i > 0){
				return true;
			}
		}else{
			int i = queryForInt(sql, new Object[]{userId, folderName});
			if(i > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_msg_folder where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeMsgFolder officeMsgFolder){
		String sql = "update office_msg_folder set folder_name = ? where id = ? ";
		Object[] args = new Object[]{
			officeMsgFolder.getFolderName(), officeMsgFolder.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeMsgFolder getOfficeMsgFolderById(String id){
		String sql = "select * from office_msg_folder where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeMsgFolder> getOfficeMsgFolderMapByIds(String[] ids){
		String sql = "select * from office_msg_folder where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeMsgFolder> getOfficeMsgFolderList(String userId){
		String sql = "select * from office_msg_folder where user_id = ? order by creation_time ";
		return query(sql, userId, new MultiRow());
	}

	@Override
	public List<OfficeMsgFolder> getOfficeMsgFolderPage(Pagination page){
		String sql = "select * from office_msg_folder";
		return query(sql, new MultiRow(), page);
	}
}