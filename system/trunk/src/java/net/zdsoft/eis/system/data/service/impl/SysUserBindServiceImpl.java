package net.zdsoft.eis.system.data.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.eis.system.data.dao.SysUserBindDao;
import net.zdsoft.eis.system.data.entity.SysUserBind;
import net.zdsoft.eis.system.data.service.SysUserBindService;
/**
 * sys_user_bind
 * @author 
 * 
 */
public class SysUserBindServiceImpl implements SysUserBindService{
	private SysUserBindDao sysUserBindDao;

	@Override
	public void save(SysUserBind sysUserBind){
		SysUserBind ex = null;
		if (StringUtils.isNotBlank(sysUserBind
					.getRemoteUserId())) {
			ex = sysUserBindDao.getSysUserBindById(sysUserBind
					.getRemoteUserId());
		}
		Date now = new Date();
		sysUserBind.setModifyTime(now);
		if(ex == null){
			sysUserBind.setCreationTime(now);
			sysUserBindDao.save(sysUserBind);
		} else {
			sysUserBindDao.update(sysUserBind);
		}
		
	}

	@Override
	public Integer delete(String[] ids){
		return sysUserBindDao.delete(ids);
	}

	@Override
	public SysUserBind getSysUserBindById(String id){
		return sysUserBindDao.getSysUserBindById(id);
	}
	
	public SysUserBind getSysUserBindByUsername(String remoteUsername){
		return sysUserBindDao.getSysUserBindByUsername(remoteUsername);
	}
	
	public SysUserBind getSysUserBindByUserId(String userId){
		return sysUserBindDao.getSysUserBindByUserId(userId);
	}
	
	public String getRemoteUserIdByUserId(String userId){
		return sysUserBindDao.getRemoteUserIdByUserId(userId);
	}

	public void setSysUserBindDao(SysUserBindDao sysUserBindDao){
		this.sysUserBindDao = sysUserBindDao;
	}
}
