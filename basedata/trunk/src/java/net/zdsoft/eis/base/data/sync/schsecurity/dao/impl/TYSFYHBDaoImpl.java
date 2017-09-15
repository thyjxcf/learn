package net.zdsoft.eis.base.data.sync.schsecurity.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.dao.TYSFYHBDao;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.TYSFYHB;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.DateUtils;

/**
 * 同步统一身份用户表日志 
 * @author 
 * 
 */
public class TYSFYHBDaoImpl extends BaseDao<TYSFYHB> implements TYSFYHBDao{
	@Override
	public TYSFYHB setField(ResultSet rs) throws SQLException{
		TYSFYHB syncTysfyhbLog = new TYSFYHB();
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
		return syncTysfyhbLog;
	}

	@Override
	public TYSFYHB getTysfyhbById(String id) {
		String sql = "select * from zfim_tysfyhb where yhid = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public List<TYSFYHB> getListByGxsj(Date beginTime) {
		String sql = "select a.* from zfim_tysfyhb_aq a, zfim_jgjbxxb b where a.dwh = b.jgid and b.jglbm in('611','621','631') and a.gxsj > ? union (select c.* from zfim_tysfyhb_aq c, zfim_xxjbxxb d where c.dwh = d.xxid and d.xxlbm in ('111','211','218','311','312','341','342','345','362','364','365','366','369','371','511','512','513','514') and c.gxsj > ?)";
		return query(sql, new Object[] {DateUtils.date2String(beginTime, "yyyyMMddHHmmss"),DateUtils.date2String(beginTime, "yyyyMMddHHmmss")}, new MultiRow());
	}
	
	
	@Override
	public String getLastGxsj() {
		String sql = "select max(a.gxsj) as gxsj from ((select a.* from zfim_tysfyhb_aq a, zfim_jgjbxxb b where a.dwh = b.jgid and b.jglbm in('611','621','631')) "
				+ "union (select c.* from zfim_tysfyhb_aq c, zfim_xxjbxxb d where c.dwh = d.xxid and d.xxlbm in ('111','211','218','311','312','341','342','345','362','364','365','366','369','371','511','512','513','514'))) a";
		return query(sql, new SingleRowMapper<String>(){

			@Override
			public String mapRow(ResultSet rs) throws SQLException {
				return rs.getString("gxsj");
			}});
	}
	
}