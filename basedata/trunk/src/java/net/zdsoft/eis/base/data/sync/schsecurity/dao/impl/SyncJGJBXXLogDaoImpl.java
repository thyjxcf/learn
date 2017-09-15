package net.zdsoft.eis.base.data.sync.schsecurity.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.constant.JBSyncConstant;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.SyncJGJBXXLogDao;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.JGJBXX;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;

import org.apache.commons.lang3.StringUtils;

public class SyncJGJBXXLogDaoImpl extends BaseDao<JGJBXX> implements SyncJGJBXXLogDao{

	@Override
	public JGJBXX setField(ResultSet rs) throws SQLException{
		JGJBXX entity = new JGJBXX();
		entity.setId(rs.getString("id"));
		entity.setJGID(rs.getString("jgid"));
		entity.setJGBSM(rs.getString("jgbsm"));
		entity.setJGMC(rs.getString("jgmc"));
		entity.setLSJGM(rs.getString("lsjgm"));
		entity.setJGLBM(rs.getString("jglbm"));
		entity.setJGYXBS(rs.getString("jgyxbs"));
		entity.setXSSH(rs.getInt("xssh"));
		entity.setDQBH(rs.getString("dqbh"));
		entity.setGXSJ(rs.getString("gxsj"));
		entity.setLJSCBZ(rs.getString("ljscbz"));
		entity.setSJBHLX(rs.getString("sjbhlx"));
		entity.setSyncTime(rs.getTimestamp("sync_time"));
		entity.setSyncResult(rs.getString("sync_result"));
		entity.setException(rs.getString("exception"));
		entity.setSyncVersion(rs.getInt("sync_version"));
		entity.setModifyTime(rs.getTimestamp("modify_time"));
		return entity;
	}
	
	public JGJBXX save(JGJBXX entity){
		String sql = "insert into sync_jgjbxxb_log(id, jgid, jgbsm, jgmc, lsjgm, jglbm, jgyxbs, xssh, dqbh, gxsj, ljscbz, sjbhlx, sync_time, sync_result, exception, sync_version, modify_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getJGID(), 
			entity.getJGBSM(), entity.getJGMC(), 
			entity.getLSJGM(), entity.getJGLBM(), 
			entity.getJGYXBS(), entity.getXSSH(), 
			entity.getDQBH(), entity.getGXSJ(), 
			entity.getLJSCBZ(), entity.getSJBHLX(), 
			entity.getSyncTime(), entity.getSyncResult(), 
			entity.getException(), entity.getSyncVersion(),
			entity.getModifyTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer update(JGJBXX entity){
		String sql = "update sync_jgjbxxb_log set jgid = ?, jgbsm = ?, jgmc = ?, lsjgm = ?, jglbm = ?, jgyxbs = ?, xssh = ?, dqbh = ?, gxsj = ?, ljscbz = ?, sjbhlx = ?, sync_time = ?, sync_result = ?, exception = ?, sync_version = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getJGID(), entity.getJGBSM(), 
			entity.getJGMC(), entity.getLSJGM(), 
			entity.getJGLBM(), entity.getJGYXBS(), 
			entity.getXSSH(), entity.getDQBH(), 
			entity.getGXSJ(), entity.getLJSCBZ(), 
			entity.getSJBHLX(), entity.getSyncTime(), 
			entity.getSyncResult(), entity.getException(), 
			entity.getSyncVersion(), entity.getModifyTime(),
			entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public JGJBXX getSyncJgjbxxbLogById(String id){
		String sql = "select * from sync_jgjbxxb_log where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<JGJBXX> getListByResults(String[] syncResults) {
		String sql = "select * from sync_jgjbxxb_log where sync_result in";
		return queryForInSQL(sql, null, syncResults, new MultiRow(), " order by jglbm, gxsj");
	}

	@Override
	public int getLastVersion() {
		String sql = "select max(sync_version) from sync_jgjbxxb_log";
		return queryForInt(sql);
	}

	@Override
	public void closeFailRecord(String jgid) {
		String sql = "update sync_jgjbxxb_log set sync_result = " + JBSyncConstant.SYNC_RESULT_CLOSE + "where jgid = ? and sync_result = " + JBSyncConstant.SYNC_RESULT_FAIL;
		update(sql, new Object[] {jgid});
	}
}
