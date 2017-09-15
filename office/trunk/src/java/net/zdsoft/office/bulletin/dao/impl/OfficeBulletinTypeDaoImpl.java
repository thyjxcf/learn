package net.zdsoft.office.bulletin.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinTypeDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinType;

import org.apache.commons.lang.StringUtils;
/**
 * office_bulletin_type
 * @author 
 * 
 */
public class OfficeBulletinTypeDaoImpl extends BaseDao<OfficeBulletinType> implements OfficeBulletinTypeDao{

	@Override
	public OfficeBulletinType setField(ResultSet rs) throws SQLException{
		OfficeBulletinType officeBulletinType = new OfficeBulletinType();
		officeBulletinType.setId(rs.getString("id"));
		officeBulletinType.setName(rs.getString("name"));
		officeBulletinType.setType(rs.getString("type"));
		officeBulletinType.setShow(rs.getBoolean("is_show"));
		officeBulletinType.setNeedSms(rs.getBoolean("need_sms"));
		return officeBulletinType;
	}

	@Override
	public OfficeBulletinType save(OfficeBulletinType officeBulletinType){
		String sql = "insert into office_bulletin_type(id, name, type) values(?,?,?)";
		if (StringUtils.isBlank(officeBulletinType.getId())){
			officeBulletinType.setId(createId());
		}
		Object[] args = new Object[]{
			officeBulletinType.getId(), officeBulletinType.getName(), 
			officeBulletinType.getType()
		};
		update(sql, args);
		return officeBulletinType;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_bulletin_type where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeBulletinType officeBulletinType){
		String sql = "update office_bulletin_type set name = ?, type = ? where id = ?";
		Object[] args = new Object[]{
			officeBulletinType.getName(), officeBulletinType.getType(), 
			officeBulletinType.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeBulletinType getOfficeBulletinTypeById(String id){
		String sql = "select * from office_bulletin_type where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public OfficeBulletinType getOfficeBulletinTypeByType(String type) {
		String sql = "select * from office_bulletin_type where type = ?";
		return query(sql, new Object[]{type }, new SingleRow());
	}

	@Override
	public Map<String, OfficeBulletinType> getOfficeBulletinTypeMapByIds(String[] ids){
		String sql = "select * from office_bulletin_type where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeBulletinType> getOfficeBulletinTypeList(Integer showNumber){
		String sql = "select * from office_bulletin_type where is_show = ? ";
		return query(sql, new Object[]{showNumber}, new MultiRow());
	}

	@Override
	public List<OfficeBulletinType> getOfficeBulletinTypePage(Pagination page){
		String sql = "select * from office_bulletin_type";
		return query(sql, new MultiRow(), page);
	}
}
