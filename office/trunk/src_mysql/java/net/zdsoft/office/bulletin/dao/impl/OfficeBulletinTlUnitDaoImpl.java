package net.zdsoft.office.bulletin.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.office.bulletin.dao.OfficeBulletinTlUnitDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinTlUnit;

import org.apache.commons.lang.StringUtils;
/**
 * office_bulletin_tl_unit
 * @author 
 * 
 */
public class OfficeBulletinTlUnitDaoImpl extends BaseDao<OfficeBulletinTlUnit> implements OfficeBulletinTlUnitDao{

	@Override
	public OfficeBulletinTlUnit setField(ResultSet rs) throws SQLException{
		OfficeBulletinTlUnit officeBulletinTlUnit = new OfficeBulletinTlUnit();
		officeBulletinTlUnit.setId(rs.getString("id"));
		officeBulletinTlUnit.setBulletinId(rs.getString("bulletin_id"));
		officeBulletinTlUnit.setReceiveUnitId(rs.getString("receive_unit_id"));
		return officeBulletinTlUnit;
	}

	@Override
	public void batchSave(List<OfficeBulletinTlUnit> list) {
		String sql = "insert into office_bulletin_tl_unit(id, bulletin_id, receive_unit_id) values(?,?,?)";
		List<Object[]> tlUnit = new ArrayList<Object[]>(); 
		for(OfficeBulletinTlUnit officeBulletinTlUnit:list){
			if (StringUtils.isBlank(officeBulletinTlUnit.getId())){
				officeBulletinTlUnit.setId(createId());
			}
			Object[] args = new Object[]{
				officeBulletinTlUnit.getId(), officeBulletinTlUnit.getBulletinId(), 
				officeBulletinTlUnit.getReceiveUnitId()
			};
			tlUnit.add(args);
		}
		batchUpdate(sql, tlUnit, new int[]{Types.CHAR,Types.CHAR,Types.CHAR});
	}
	
	@Override
	public void deleteByBulletinId(String bulletinId) {
		String sql = "delete from office_bulletin_tl_unit where bulletin_id = ? ";
		update(sql,new Object[]{bulletinId});
	}
	
	@Override
	public List<OfficeBulletinTlUnit> getOfficeBulletinTlUnitList(
			String bulletinId) {
		String sql = "select * from office_bulletin_tl_unit where bulletin_id = ? ";
		return query(sql, bulletinId, new MultiRow());
	}
	
	@Override
	public Map<String, List<OfficeBulletinTlUnit>> getOfficeBulletinTlUnitMap(String[] bulletinIds){
		String sql = "select * from office_bulletin_tl_unit where bulletin_id in ";
		List<OfficeBulletinTlUnit> list = queryForInSQL(sql, null, bulletinIds, 
				new MultiRowMapper<OfficeBulletinTlUnit>(){
			@Override
			public OfficeBulletinTlUnit mapRow(ResultSet rs, int arg1)
					throws SQLException {
				return setField(rs);
			}
		});
		
		Map<String, List<OfficeBulletinTlUnit>> map = new HashMap<String, List<OfficeBulletinTlUnit>>();
		for(OfficeBulletinTlUnit item : list){
			String key = item.getBulletinId();
			List<OfficeBulletinTlUnit> l = map.get(key);
			if(l == null){
				l = new ArrayList<OfficeBulletinTlUnit>();
				map.put(key, l);
			}
			l.add(item);
		}
		return map;	
	}
}