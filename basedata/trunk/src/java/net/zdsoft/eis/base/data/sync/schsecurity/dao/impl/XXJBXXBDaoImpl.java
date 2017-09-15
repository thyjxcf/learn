package net.zdsoft.eis.base.data.sync.schsecurity.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.sync.schsecurity.dao.XXJBXXBDao;
import net.zdsoft.eis.base.data.sync.schsecurity.entity.XXJBXXB;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.DateUtils;

public class XXJBXXBDaoImpl extends BaseDao<XXJBXXB> implements XXJBXXBDao{

	@Override
	public XXJBXXB setField(ResultSet rs) throws SQLException {
		XXJBXXB entity = new XXJBXXB();
		entity.setXXID(rs.getString("xxid"));
		//这里拿的是国家机构码
		entity.setXXBSM(rs.getString("xxjgbsm"));
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
		return entity;
	}

	@Override
	public XXJBXXB getById(String id) {
		String sql = "select * from zfim_xxjbxxb where xxid = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<XXJBXXB> getListByGxsj(Date beginTime) {
		String sql = "select * from zfim_xxjbxxb where xxlbm in ('111','211','218','311','312','341','342','345','362','364','365','366','369','371','511','512','513','514') and gxsj > ?";
		return query(sql, new Object[] {DateUtils.date2String(beginTime, "yyyyMMddHHmmss")}, new MultiRow());
	}

	@Override
	public String getLastGxsj() {
		String sql = "select max(gxsj) as gxsj from zfim_xxjbxxb";
		return query(sql, new SingleRowMapper<String>(){

			@Override
			public String mapRow(ResultSet rs) throws SQLException {
				return rs.getString("gxsj");
			}});
	}
}
