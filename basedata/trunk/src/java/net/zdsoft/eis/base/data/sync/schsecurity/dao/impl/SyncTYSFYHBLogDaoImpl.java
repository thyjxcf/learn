package net.zdsoft.eis.base.data.sync.schsecurity.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.constant.JBSyncConstant;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.SyncTYSFYHBLogDao;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.TYSFYHB;
import net.zdsoft.eis.frame.client.BaseDao;

import org.apache.commons.lang3.StringUtils;

/**
 * 同步统一身份用户表日志 
 * @author 
 * 
 */
public class SyncTYSFYHBLogDaoImpl extends BaseDao<TYSFYHB> implements SyncTYSFYHBLogDao{
	@Override
	public TYSFYHB setField(ResultSet rs) throws SQLException{
		TYSFYHB syncTysfyhbLog = new TYSFYHB();
		syncTysfyhbLog.setId(rs.getString("id"));
		syncTysfyhbLog.setYHID(rs.getString("yhid"));
		syncTysfyhbLog.setYHSX(rs.getString("yhsx"));
		syncTysfyhbLog.setYHM(rs.getString("yhm"));
		syncTysfyhbLog.setXM(rs.getString("xm"));
		syncTysfyhbLog.setDWH(rs.getString("dwh"));
		syncTysfyhbLog.setZJLX(rs.getString("zjlx"));
		syncTysfyhbLog.setZJHM(rs.getString("zjhm"));
		syncTysfyhbLog.setSJHM(rs.getString("sjhm"));
		syncTysfyhbLog.setSTATUS(rs.getString("status"));
		syncTysfyhbLog.setXB(rs.getString("xb"));
		syncTysfyhbLog.setGXSJ(rs.getString("gxsj"));
		syncTysfyhbLog.setLJSCBZ(rs.getString("ljscbz"));
		syncTysfyhbLog.setSJBHLX(rs.getString("sjbhlx"));
		syncTysfyhbLog.setSyncTime(rs.getTimestamp("sync_time"));
		syncTysfyhbLog.setSyncResult(rs.getString("sync_result"));
		syncTysfyhbLog.setException(rs.getString("exception"));
		syncTysfyhbLog.setSyncVersion(rs.getInt("sync_version"));
		syncTysfyhbLog.setModifyTime(rs.getTimestamp("modify_time"));
		return syncTysfyhbLog;
	}
	
	public TYSFYHB save(TYSFYHB syncTysfyhbLog){
		String sql = "insert into sync_tysfyhb_log(yhid, yhsx, yhm, xm, dwh, zjlx, zjhm, sjhm, status, xb, gxsj, ljscbz, sjbhlx, id, sync_time, sync_result, exception, sync_version, modify_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(syncTysfyhbLog.getId())){
			syncTysfyhbLog.setId(createId());
		}
		Object[] args = new Object[]{
			syncTysfyhbLog.getYHID(), syncTysfyhbLog.getYHSX(),
			syncTysfyhbLog.getYHM(), syncTysfyhbLog.getXM(),
			syncTysfyhbLog.getDWH(), syncTysfyhbLog.getZJLX(), 
			syncTysfyhbLog.getZJHM(), syncTysfyhbLog.getSJHM(), 
			syncTysfyhbLog.getSTATUS(), syncTysfyhbLog.getXB(), 
			syncTysfyhbLog.getGXSJ(), syncTysfyhbLog.getLJSCBZ(), 
			syncTysfyhbLog.getSJBHLX(), syncTysfyhbLog.getId(), 
			syncTysfyhbLog.getSyncTime(), syncTysfyhbLog.getSyncResult(), 
			syncTysfyhbLog.getException(), syncTysfyhbLog.getSyncVersion(), 
			syncTysfyhbLog.getModifyTime()
		};
		update(sql, args);
		return syncTysfyhbLog;
	}
	
	@Override
	public Integer update(TYSFYHB syncTysfyhbLog){
		String sql = "update sync_tysfyhb_log set yhid = ?, yhsx = ?, yhm = ?, xm = ?, dwh = ?, zjlx = ?, zjhm = ?, sjhm = ?, status = ?, xb = ?, gxsj = ?, ljscbz = ?, sjbhlx = ?, sync_time = ?, sync_result = ?, exception = ?, sync_version = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			syncTysfyhbLog.getYHID(), syncTysfyhbLog.getYHSX(),
			syncTysfyhbLog.getYHM(), syncTysfyhbLog.getXM(), 
			syncTysfyhbLog.getDWH(), syncTysfyhbLog.getZJLX(), 
			syncTysfyhbLog.getZJHM(), syncTysfyhbLog.getSJHM(), 
			syncTysfyhbLog.getSTATUS(), syncTysfyhbLog.getXB(), 
			syncTysfyhbLog.getGXSJ(), syncTysfyhbLog.getLJSCBZ(), 
			syncTysfyhbLog.getSJBHLX(), syncTysfyhbLog.getSyncTime(), 
			syncTysfyhbLog.getSyncResult(), syncTysfyhbLog.getException(), 
			syncTysfyhbLog.getSyncVersion(), syncTysfyhbLog.getModifyTime(), 
			syncTysfyhbLog.getId(),
		};
		return update(sql, args);
	}

	@Override
	public TYSFYHB getSyncTysfyhbLogById(String id) {
		String sql = "select * from sync_tysfyhb_log where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public List<TYSFYHB> getListByResults(String[] syncResults) {
		String sql = "select * from sync_tysfyhb_log where sync_result in";
		return queryForInSQL(sql, null, syncResults, new MultiRow(), " order by gxsj");
	}

	@Override
	public int getLastVersion() {
		String sql = "select max(sync_version) from sync_tysfyhb_log";
		return queryForInt(sql);
	}
	
	@Override
	public void closeFailRecord(String yhid) {
		String sql = "update sync_tysfyhb_log set sync_result = " + JBSyncConstant.SYNC_RESULT_CLOSE + "where yhid = ? and sync_result = " + JBSyncConstant.SYNC_RESULT_FAIL;
		update(sql, new Object[] {yhid});
	}
}