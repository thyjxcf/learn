package net.zdsoft.eis.base.data.sync.schsecurity.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.dao.JGJBXXDao;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.JGJBXX;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.DateUtils;

public class JGJBXXDaoImpl extends BaseDao<JGJBXX> implements JGJBXXDao{
	
	@Override
	public JGJBXX setField(ResultSet rs) throws SQLException {
		JGJBXX entity = new JGJBXX();
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
		return entity;
	}
	
	@Override
	public JGJBXX getById(String id) {
		String sql = "select * from zfim_jgjbxxb where jgid = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<JGJBXX> getListByGxsj(Date beginTime) {
		String sql = "select * from zfim_jgjbxxb where gxsj > ? and jglbm in ('611','621','631') order by jglbm";
		return query(sql, new Object[] {DateUtils.date2String(beginTime, "yyyyMMddHHmmss")}, new MultiRow());
	}

	@Override
	public String getLastGxsj() {
		String sql = "select max(gxsj) as gxsj from zfim_jgjbxxb where jglbm in ('611','621','631')";
		return query(sql, new SingleRowMapper<String>(){

			@Override
			public String mapRow(ResultSet rs) throws SQLException {
				return rs.getString("gxsj");
			}});
	}
}
