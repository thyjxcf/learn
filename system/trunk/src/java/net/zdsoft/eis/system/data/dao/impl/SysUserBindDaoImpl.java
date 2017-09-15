package net.zdsoft.eis.system.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.data.dao.SysUserBindDao;
import net.zdsoft.eis.system.data.entity.SysUserBind;

import org.apache.commons.lang3.StringUtils;
/**
 * sys_user_bind
 * @author 
 * 
 */
public class SysUserBindDaoImpl extends BaseDao<SysUserBind> implements SysUserBindDao{

	@Override
	public SysUserBind setField(ResultSet rs) throws SQLException{
		SysUserBind sysUserBind = new SysUserBind();
		sysUserBind.setRemoteUserId(rs.getString("remote_user_id"));
		sysUserBind.setUserId(rs.getString("user_id"));
		sysUserBind.setCreationTime(rs.getTimestamp("creation_time"));
		sysUserBind.setModifyTime(rs.getTimestamp("modify_time"));
		sysUserBind.setRemotePwd(rs.getString("remote_password"));
		sysUserBind.setRemoteUsername(rs.getString("remote_username"));
		return sysUserBind;
	}

	@Override
	public SysUserBind save(SysUserBind sysUserBind){
		String sql = "insert into sys_user_bind(remote_user_id, remote_username, remote_password, user_id, creation_time, modify_time) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(sysUserBind.getRemoteUserId())){
			sysUserBind.setRemoteUserId(createId());
		}
		Object[] args = new Object[]{
			sysUserBind.getRemoteUserId(), sysUserBind.getRemoteUsername(),
			sysUserBind.getRemotePwd(), sysUserBind.getUserId(), 
			sysUserBind.getCreationTime(), sysUserBind.getModifyTime()
		};
		update(sql, args);
		return sysUserBind;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from sys_user_bind where remote_user_id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(SysUserBind sysUserBind){
		String sql = "update sys_user_bind set user_id = ?, remote_username=?, remote_password=?, modify_time = ? where remote_user_id = ?";
		Object[] args = new Object[]{
			sysUserBind.getUserId(), sysUserBind.getRemoteUsername(),
			sysUserBind.getRemotePwd(),
			sysUserBind.getModifyTime(), sysUserBind.getRemoteUserId()
		};
		return update(sql, args);
	}

	@Override
	public SysUserBind getSysUserBindById(String id){
		String sql = "select * from sys_user_bind where remote_user_id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	public SysUserBind getSysUserBindByUsername(String remoteUsername){
		String sql = "select * from sys_user_bind where remote_username = ?";
		return query(sql, new Object[]{remoteUsername}, new SingleRow());
	}
	
	public SysUserBind getSysUserBindByUserId(String userId){
		String sql = "select * from sys_user_bind where user_id = ?";
		return query(sql, new Object[]{userId}, new SingleRow());
	}
	
	public String getRemoteUserIdByUserId(String userId){
		String sql = "select * from sys_user_bind where user_id = ?";
		SysUserBind bi = query(sql, new Object[]{userId }, new SingleRow());
		if(bi == null){
			return null;
		}
		return bi.getRemoteUserId();
	}

}
