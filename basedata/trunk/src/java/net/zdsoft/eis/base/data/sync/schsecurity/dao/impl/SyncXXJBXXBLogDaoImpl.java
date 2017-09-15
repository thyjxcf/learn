package net.zdsoft.eis.base.data.sync.schsecurity.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.constant.JBSyncConstant;
import net.zdsoft.eis.base.data.sync.schsecurity.dao.SyncXXJBXXBLogDao;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.XXJBXXB;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;

import org.apache.commons.lang3.StringUtils;


public class SyncXXJBXXBLogDaoImpl extends BaseDao<XXJBXXB> implements SyncXXJBXXBLogDao{
	
	@Override
	public XXJBXXB setField(ResultSet rs) throws SQLException{
		XXJBXXB entity = new XXJBXXB();
		entity.setId(rs.getString("id"));
		entity.setXXID(rs.getString("xxid"));
		entity.setXXBSM(rs.getString("xxbsm"));
		entity.setXXMC(rs.getString("xxmc"));
		entity.setLSJGM(rs.getString("lsjgm"));
		entity.setXXLBM(rs.getString("xxlbm"));
		entity.setXXYXBS(rs.getString("xxyxbs"));
		entity.setXSSH(rs.getInt("xssh"));
		entity.setDQBH(rs.getString("dqbh"));
		entity.setXZ(rs.getString("xz"));
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
	
	public XXJBXXB save(XXJBXXB entity){
		String sql = "insert into sync_xxjbxxb_log(id, xxid, xxbsm, xxmc, lsjgm, xxlbm, xxyxbs, xssh, dqbh, xz, gxsj, ljscbz, sjbhlx, sync_time, sync_result, exception, sync_version, modify_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(createId());
		}
		Object[] args = new Object[]{
			entity.getId(), entity.getXXID(),
			entity.getXXBSM(), entity.getXXMC(),
			entity.getLSJGM(), entity.getXXLBM(),
			entity.getXXYXBS(), entity.getXSSH(),
	        entity.getDQBH(), entity.getXZ(),
			entity.getGXSJ(), entity.getLJSCBZ(),
			entity.getSJBHLX(), entity.getSyncTime(), 
			entity.getSyncResult(), entity.getException(), 
			entity.getSyncVersion(), entity.getModifyTime()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer update(XXJBXXB entity){
		String sql = "update sync_xxjbxxb_log set xxid = ?, xxbsm = ?, xxmc = ?, lsjgm = ?, xxlbm = ?, xxyxbs = ?, xssh = ?, dqbh = ?, xz = ?, gxsj = ?, ljscbz = ?, sjbhlx = ?, sync_time = ?, sync_result = ?, exception = ?, sync_version = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			entity.getXXID(), entity.getXXBSM(), 
			entity.getXXMC(), entity.getLSJGM(), 
			entity.getXXLBM(), entity.getXXYXBS(), 
			entity.getXSSH(), entity.getDQBH(), 
			entity.getXZ(), entity.getGXSJ(), 
			entity.getLJSCBZ(), entity.getSJBHLX(), 
			entity.getSyncTime(), entity.getSyncResult(), 
			entity.getException(), entity.getSyncVersion(), 
			entity.getModifyTime(), entity.getId()
		};
		return update(sql, args);
	}

	@Override
	public XXJBXXB getSyncXxjbxxbLogById(String id){
		String sql = "select * from sync_xxjbxxb_log where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<XXJBXXB> getListByResults(String[] syncResults) {
		String sql = "select * from sync_xxjbxxb_log where sync_result in";
		return queryForInSQL(sql, null, syncResults, new MultiRow(), " order by gxsj");
	}

	@Override
	public int getLastVersion() {
		String sql = "select max(sync_version) from sync_xxjbxxb_log";
		return queryForInt(sql);
	}

	@Override
	public void closeFailRecord(String xxid) {
		String sql = "update sync_xxjbxxb_log set sync_result = " + JBSyncConstant.SYNC_RESULT_CLOSE + "where xxid = ? and sync_result = " + JBSyncConstant.SYNC_RESULT_FAIL;
		update(sql, new Object[] {xxid});
	}
}
