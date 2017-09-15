package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeCarInfoDao;
import net.zdsoft.office.dailyoffice.entity.OfficeCarInfo;

import org.apache.commons.lang.StringUtils;
/**
 * office_car_info
 * @author 
 * 
 */
public class OfficeCarInfoDaoImpl extends BaseDao<OfficeCarInfo> implements OfficeCarInfoDao{

	@Override
	public OfficeCarInfo setField(ResultSet rs) throws SQLException{
		OfficeCarInfo officeCarInfo = new OfficeCarInfo();
		officeCarInfo.setId(rs.getString("id"));
		officeCarInfo.setUnitId(rs.getString("unit_id"));
		officeCarInfo.setCarNumber(rs.getString("car_number"));
		officeCarInfo.setCarType(rs.getString("car_type"));
		officeCarInfo.setBuyDate(rs.getTimestamp("buy_date"));
		officeCarInfo.setBuyPrice(rs.getInt("buy_price"));
		officeCarInfo.setSeating(rs.getInt("seating"));
		officeCarInfo.setIsDeleted(rs.getBoolean("is_deleted"));
		officeCarInfo.setRemark(rs.getString("remark"));
		return officeCarInfo;
	}

	@Override
	public OfficeCarInfo save(OfficeCarInfo officeCarInfo){
		String sql = "insert into office_car_info(id, unit_id, car_number, car_type, buy_date, buy_price, seating, is_deleted, remark) values(?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeCarInfo.getId())){
			officeCarInfo.setId(createId());
		}
		Object[] args = new Object[]{
			officeCarInfo.getId(), officeCarInfo.getUnitId(), 
			officeCarInfo.getCarNumber(), officeCarInfo.getCarType(), 
			officeCarInfo.getBuyDate(), officeCarInfo.getBuyPrice(), 
			officeCarInfo.getSeating(), officeCarInfo.getIsDeleted(), 
			officeCarInfo.getRemark()
		};
		update(sql, args);
		return officeCarInfo;
	}
	
	@Override
	public void delete(String id) {
		String sql = "update office_car_info set is_deleted = 1 where id = ? ";
		update(sql, id);
	}

	@Override
	public void delete(String[] ids){
		String sql = "update office_car_info set is_deleted = 1 where id in";
		updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeCarInfo officeCarInfo){
		String sql = "update office_car_info set car_number = ?, car_type = ?, buy_date = ?, buy_price = ?, seating = ?, remark = ? where id = ?";
		Object[] args = new Object[]{
			officeCarInfo.getCarNumber(), officeCarInfo.getCarType(), 
			officeCarInfo.getBuyDate(), officeCarInfo.getBuyPrice(), 
			officeCarInfo.getSeating(), officeCarInfo.getRemark(), 
			officeCarInfo.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeCarInfo getOfficeCarInfoById(String id){
		String sql = "select * from office_car_info where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeCarInfo> getOfficeCarInfoMapByIds(String[] ids){
		String sql = "select * from office_car_info where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}
	
	@Override
	public Map<String, OfficeCarInfo> getOfficeCarInfoMapByUnitId(String unitId) {
		String sql = "select * from office_car_info where unit_id = ? ";
		return queryForMap(sql, new Object[]{unitId}, new MapRow());
	}

	@Override
	public List<OfficeCarInfo> getOfficeCarInfoList(){
		String sql = "select * from office_car_info";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeCarInfo> getOfficeCarInfoPage(Pagination page){
		String sql = "select * from office_car_info where is_deleted = 0 ";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeCarInfo> getOfficeCarInfoByUnitIdList(String unitId){
		String sql = "select * from office_car_info where is_deleted = 0 and unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeCarInfo> getOfficeCarInfoByUnitIdPage(String unitId, String carNumber, Pagination page){
		String sql = "select * from office_car_info where is_deleted = 0 and unit_id = ?";
		if(StringUtils.isNotBlank(carNumber)){
			sql += " and car_number like "+"'%"+carNumber+"%'";
		}
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}