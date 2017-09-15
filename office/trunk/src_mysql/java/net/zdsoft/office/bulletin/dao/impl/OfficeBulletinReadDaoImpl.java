package net.zdsoft.office.bulletin.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinReadDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;

import org.apache.commons.lang.StringUtils;
/**
 * office_bulletin_read
 * @author 
 * 
 */
public class OfficeBulletinReadDaoImpl extends BaseDao<OfficeBulletinRead> implements OfficeBulletinReadDao{

	@Override
	public OfficeBulletinRead setField(ResultSet rs) throws SQLException{
		OfficeBulletinRead officeBulletinRead = new OfficeBulletinRead();
		officeBulletinRead.setId(rs.getString("id"));
		officeBulletinRead.setUnitId(rs.getString("unit_id"));
		officeBulletinRead.setBulletinId(rs.getString("bulletin_id"));
		officeBulletinRead.setBulletinType(rs.getString("bulletin_type"));
		officeBulletinRead.setUserId(rs.getString("user_id"));
		return officeBulletinRead;
	}

	@Override
	public OfficeBulletinRead save(OfficeBulletinRead officeBulletinRead){
		String sql = "insert into office_bulletin_read(id, unit_id, bulletin_id, bulletin_type, user_id) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeBulletinRead.getId())){
			officeBulletinRead.setId(createId());
		}
		Object[] args = new Object[]{
			officeBulletinRead.getId(), officeBulletinRead.getUnitId(), 
			officeBulletinRead.getBulletinId(), officeBulletinRead.getBulletinType(), 
			officeBulletinRead.getUserId()
		};
		update(sql, args);
		return officeBulletinRead;
	}
	
	@Override
	public boolean isExist(OfficeBulletinRead obr) {
		String sql = "select count(1) from office_bulletin_read where unit_id = ? and bulletin_id = ? and user_id = ? ";
		int i = queryForInt(sql, new Object[]{obr.getUnitId(),obr.getBulletinId(),obr.getUserId()});
		if(i > 0){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, String> getReadMapByBulletinIds(String userId, String[] bulletinIds) {
		String sql = "select * from office_bulletin_read where user_id = ? and bulletin_id in ";
		return queryForInSQL(sql, new Object[]{userId}, bulletinIds, new MapRowMapper() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("bulletin_id");
			}
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("user_id");
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, String> getClickMapByBulletinIds(String[] bulletinIds) {
		String sql = "select bulletin_id, count(1) cou from office_bulletin_read where bulletin_id in";
		return queryForInSQL(sql, null, bulletinIds, new MapRowMapper() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("bulletin_id");
			}
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("cou");
			}
		}, " group by bulletin_id ");
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_bulletin_read where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeBulletinRead officeBulletinRead){
		String sql = "update office_bulletin_read set unit_id = ?, bulletin_id = ?, bulletin_type = ?, user_id = ? where id = ?";
		Object[] args = new Object[]{
			officeBulletinRead.getUnitId(), officeBulletinRead.getBulletinId(), 
			officeBulletinRead.getBulletinType(), officeBulletinRead.getUserId(), 
			officeBulletinRead.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeBulletinRead getOfficeBulletinReadById(String id){
		String sql = "select * from office_bulletin_read where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeBulletinRead> getOfficeBulletinReadMapByIds(String[] ids){
		String sql = "select * from office_bulletin_read where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeBulletinRead> getOfficeBulletinReadList(){
		String sql = "select * from office_bulletin_read";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeBulletinRead> getOfficeBulletinReadPage(Pagination page){
		String sql = "select * from office_bulletin_read";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeBulletinRead> getOfficeBulletinReadByUnitIdList(String unitId){
		String sql = "select * from office_bulletin_read where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeBulletinRead> getOfficeBulletinReadByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_bulletin_read where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public Map<String, List<OfficeBulletinRead>> getOfficeBulletinReadMap(String[] bulletinIds){
		String sql = "select * from office_bulletin_read where bulletin_id in ";
		List<OfficeBulletinRead> list = queryForInSQL(sql, null, bulletinIds, 
				new MultiRowMapper<OfficeBulletinRead>(){
			@Override
			public OfficeBulletinRead mapRow(ResultSet rs, int arg1)
					throws SQLException {
				return setField(rs);
			}
		});
		Map<String, List<OfficeBulletinRead>> map = new HashMap<String, List<OfficeBulletinRead>>();
		for(OfficeBulletinRead item : list){
			String key = item.getBulletinId();
			List<OfficeBulletinRead> l = map.get(key);
			if(l == null){
				l = new ArrayList<OfficeBulletinRead>();
				map.put(key, l);
			}
			l.add(item);
		}
		return map;	
	}
}