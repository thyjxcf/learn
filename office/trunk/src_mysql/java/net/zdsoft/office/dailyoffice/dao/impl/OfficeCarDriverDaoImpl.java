package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeCarDriverDao;
import net.zdsoft.office.dailyoffice.entity.OfficeCarDriver;

import org.apache.commons.lang.StringUtils;
/**
 * office_car_driver
 * @author 
 * 
 */
public class OfficeCarDriverDaoImpl extends BaseDao<OfficeCarDriver> implements OfficeCarDriverDao{

	private static final String SQL_INSERT = "insert into office_car_driver(id, unit_id, name, mobile_phone, is_deleted, driver_id) values(?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "update office_car_driver set name = ?, mobile_phone = ?, driver_id = ? where id = ?";
	
	@Override
	public OfficeCarDriver setField(ResultSet rs) throws SQLException{
		OfficeCarDriver officeCarDriver = new OfficeCarDriver();
		officeCarDriver.setId(rs.getString("id"));
		officeCarDriver.setUnitId(rs.getString("unit_id"));
		officeCarDriver.setName(rs.getString("name"));
		officeCarDriver.setMobilePhone(rs.getString("mobile_phone"));
		officeCarDriver.setIsDeleted(rs.getBoolean("is_deleted"));
		officeCarDriver.setDriverId(rs.getString("driver_id"));
		return officeCarDriver;
	}

	@Override
	public OfficeCarDriver save(OfficeCarDriver officeCarDriver){
		if (StringUtils.isBlank(officeCarDriver.getId())){
			officeCarDriver.setId(createId());
		}
		Object[] args = new Object[]{
			officeCarDriver.getId(), officeCarDriver.getUnitId(), 
			officeCarDriver.getName(), officeCarDriver.getMobilePhone(), 
			officeCarDriver.getIsDeleted(), officeCarDriver.getDriverId()
		};
		update(SQL_INSERT, args);
		return officeCarDriver;
	}
	
	@Override
	public void batchSave(List<OfficeCarDriver> officeCarDrivers) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeCarDriver officeCarDriver:officeCarDrivers) {
			if (StringUtils.isBlank(officeCarDriver.getId()))
				officeCarDriver.setId(getGUID());
			Object[] args = new Object[]{
				officeCarDriver.getId(), officeCarDriver.getUnitId(), 
				officeCarDriver.getName(), officeCarDriver.getMobilePhone(), 
				officeCarDriver.getIsDeleted(), officeCarDriver.getDriverId()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.BOOLEAN, Types.CHAR
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
		
	}
	
	@Override
	public void batchUpdate(List<OfficeCarDriver> officeCarDrivers) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeCarDriver officeCarDriver:officeCarDrivers) {
			Object[] args = new Object[]{
				officeCarDriver.getName(), officeCarDriver.getMobilePhone(),
				officeCarDriver.getDriverId(), officeCarDriver.getId()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.CHAR
				};
		batchUpdate(SQL_UPDATE, listOfArgs, argTypes);
		
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_car_driver set is_deleted = 1 where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeCarDriver officeCarDriver){
		Object[] args = new Object[]{
			officeCarDriver.getName(), officeCarDriver.getMobilePhone(),
			officeCarDriver.getDriverId(), officeCarDriver.getId()
		};
		return update(SQL_UPDATE, args);
	}

	@Override
	public OfficeCarDriver getOfficeCarDriverById(String id){
		String sql = "select * from office_car_driver where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeCarDriver> getOfficeCarDriverMapByIds(String[] ids){
		String sql = "select * from office_car_driver where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}
	
	@Override
	public Map<String, OfficeCarDriver> getOfficeCarDriverMapByUnitId(
			String unitId) {
		String sql = "select * from office_car_driver where unit_id = ? ";
		return queryForMap(sql, new Object[]{unitId}, new MapRow());
	}

	@Override
	public List<OfficeCarDriver> getOfficeCarDriverList(){
		String sql = "select * from office_car_driver";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeCarDriver> getOfficeCarDriverPage(Pagination page){
		String sql = "select * from office_car_driver";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeCarDriver> getOfficeCarDriverByUnitIdList(String unitId){
		String sql = "select * from office_car_driver where is_deleted = 0 and unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeCarDriver> getOfficeCarDriverWithDelByUnitIdList(String unitId){
		String sql = "select * from office_car_driver where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}
	
	@Override
	public List<OfficeCarDriver> getOfficeCarDriverByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_car_driver where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}