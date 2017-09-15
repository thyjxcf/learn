package net.zdsoft.office.basic.dao.impl;


import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.basic.dao.OfficeAppParmDao;
import net.zdsoft.office.basic.entity.OfficeAppParm;
/**
 * office_app_parm(APP权限设置)
 * @author 
 * 
 */
public class OfficeAppParmDaoImpl extends BaseDao<OfficeAppParm> implements OfficeAppParmDao{

	@Override
	public OfficeAppParm setField(ResultSet rs) throws SQLException{
		OfficeAppParm officeAppParm = new OfficeAppParm();
		officeAppParm.setId(rs.getString("id"));
		officeAppParm.setUnitId(rs.getString("unit_id"));
		officeAppParm.setIsUsing(rs.getBoolean("is_using"));
		officeAppParm.setType(rs.getString("type"));
		return officeAppParm;
	}

	@Override
	public OfficeAppParm save(OfficeAppParm officeAppParm){
		String sql = "insert into office_app_parm(id, unit_id, is_using, type) values(?,?,?,?)";
		if (StringUtils.isBlank(officeAppParm.getId())){
			officeAppParm.setId(createId());
		}
		Object[] args = new Object[]{
			officeAppParm.getId(), officeAppParm.getUnitId(), 
			officeAppParm.getIsUsing(), officeAppParm.getType()
		};
		update(sql, args);
		return officeAppParm;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_app_parm where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAppParm officeAppParm){
		String sql = "update office_app_parm set unit_id = ?, is_using = ?, type = ? where id = ?";
		Object[] args = new Object[]{
			officeAppParm.getUnitId(), officeAppParm.getIsUsing(), 
			officeAppParm.getType(), officeAppParm.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAppParm getOfficeAppParmByUnitId(String unitId, String type){
		String sql = "select * from office_app_parm where unit_id = ? and type = ?";
		return query(sql, new Object[]{unitId, type}, new SingleRow());
	}

	public List<OfficeAppParm> getListByUnitId(String unitId, boolean isUsing){
		String sql = "select * from office_app_parm where unit_id = ?";
		if(isUsing){//获取启用的模块
			sql += " and is_using = 1";
		}
		return query(sql, unitId, new MultiRow());
	}
}