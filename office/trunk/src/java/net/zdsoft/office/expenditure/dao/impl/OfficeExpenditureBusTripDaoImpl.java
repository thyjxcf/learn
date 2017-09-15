package net.zdsoft.office.expenditure.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureBusTrip;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureOutlay;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureBusTripDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_bus_trip
 * @author 
 * 
 */
public class OfficeExpenditureBusTripDaoImpl extends BaseDao<OfficeExpenditureBusTrip> implements OfficeExpenditureBusTripDao{

	@Override
	public OfficeExpenditureBusTrip setField(ResultSet rs) throws SQLException{
		OfficeExpenditureBusTrip officeExpenditureBusTrip = new OfficeExpenditureBusTrip();
		officeExpenditureBusTrip.setId(rs.getString("id"));
		officeExpenditureBusTrip.setExpenditureId(rs.getString("expenditure_id"));
		officeExpenditureBusTrip.setReason(rs.getString("reason"));
		officeExpenditureBusTrip.setLeaderName(rs.getString("leader_name"));
		officeExpenditureBusTrip.setNum(rs.getInt("num"));
		officeExpenditureBusTrip.setStartTime(rs.getTimestamp("start_time"));
		officeExpenditureBusTrip.setEndTime(rs.getTimestamp("end_time"));
		officeExpenditureBusTrip.setDays(rs.getInt("days"));
		officeExpenditureBusTrip.setStartPlace(rs.getString("start_place"));
		officeExpenditureBusTrip.setEndPlace(rs.getString("end_place"));
		officeExpenditureBusTrip.setDistance(rs.getString("distance"));
		officeExpenditureBusTrip.setIsCar(rs.getBoolean("is_car"));
		officeExpenditureBusTrip.setTrafficFee(rs.getDouble("traffic_fee"));
		officeExpenditureBusTrip.setHotelFee(rs.getDouble("hotel_fee"));
		officeExpenditureBusTrip.setRepastFee(rs.getDouble("repast_fee"));
		officeExpenditureBusTrip.setCityTrafficFee(rs.getDouble("city_traffic_fee"));
		officeExpenditureBusTrip.setOtherFee(rs.getDouble("other_fee"));
		officeExpenditureBusTrip.setSum(rs.getDouble("sum"));
		officeExpenditureBusTrip.setCreationTime(rs.getTimestamp("creation_time"));
		return officeExpenditureBusTrip;
	}

	@Override
	public OfficeExpenditureBusTrip save(OfficeExpenditureBusTrip officeExpenditureBusTrip){
		String sql = "insert into office_expenditure_bus_trip(id, expenditure_id, reason, leader_name, num, start_time, end_time, days, start_place, end_place, distance, is_car, traffic_fee, hotel_fee, repast_fee, city_traffic_fee, other_fee, sum, creation_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExpenditureBusTrip.getId())){
			officeExpenditureBusTrip.setId(createId());
		}
		Object[] args = new Object[]{
			officeExpenditureBusTrip.getId(), officeExpenditureBusTrip.getExpenditureId(), 
			officeExpenditureBusTrip.getReason(), officeExpenditureBusTrip.getLeaderName(), 
			officeExpenditureBusTrip.getNum(), officeExpenditureBusTrip.getStartTime(), 
			officeExpenditureBusTrip.getEndTime(), officeExpenditureBusTrip.getDays(), 
			officeExpenditureBusTrip.getStartPlace(), officeExpenditureBusTrip.getEndPlace(), 
			officeExpenditureBusTrip.getDistance(), officeExpenditureBusTrip.getIsCar(), 
			officeExpenditureBusTrip.getTrafficFee(), officeExpenditureBusTrip.getHotelFee(), 
			officeExpenditureBusTrip.getRepastFee(), officeExpenditureBusTrip.getCityTrafficFee(), 
			officeExpenditureBusTrip.getOtherFee(), officeExpenditureBusTrip.getSum(), 
			officeExpenditureBusTrip.getCreationTime()
		};
		update(sql, args);
		return officeExpenditureBusTrip;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_expenditure_bus_trip where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExpenditureBusTrip officeExpenditureBusTrip){
		String sql = "update office_expenditure_bus_trip set expenditure_id = ?, reason = ?, leader_name = ?, num = ?, start_time = ?, end_time = ?, days = ?, start_place = ?, end_place = ?, distance = ?, is_car = ?, traffic_fee = ?, hotel_fee = ?, repast_fee = ?, city_traffic_fee = ?, other_fee = ?, sum = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeExpenditureBusTrip.getExpenditureId(), officeExpenditureBusTrip.getReason(), 
			officeExpenditureBusTrip.getLeaderName(), officeExpenditureBusTrip.getNum(), 
			officeExpenditureBusTrip.getStartTime(), officeExpenditureBusTrip.getEndTime(), 
			officeExpenditureBusTrip.getDays(), officeExpenditureBusTrip.getStartPlace(), 
			officeExpenditureBusTrip.getEndPlace(), officeExpenditureBusTrip.getDistance(), 
			officeExpenditureBusTrip.getIsCar(), officeExpenditureBusTrip.getTrafficFee(), 
			officeExpenditureBusTrip.getHotelFee(), officeExpenditureBusTrip.getRepastFee(), 
			officeExpenditureBusTrip.getCityTrafficFee(), officeExpenditureBusTrip.getOtherFee(), 
			officeExpenditureBusTrip.getSum(), officeExpenditureBusTrip.getCreationTime(), 
			officeExpenditureBusTrip.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExpenditureBusTrip getOfficeExpenditureBusTripById(String id){
		String sql = "select * from office_expenditure_bus_trip where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public OfficeExpenditureBusTrip getOfficeExpenditureBusTripByExId(
			String expenditureId) {
		String sql = "select * from office_expenditure_bus_trip where expenditure_id = ?";
		return query(sql, new Object[]{expenditureId}, new SingleRow());
	}
	
	@Override
	public Map<String, OfficeExpenditureBusTrip> getOfficeExpenditureBusTripByExIds(String[] officeExpenditureIds) {
		String sql = "select * from office_expenditure_bus_trip where expenditure_id in";
		return queryForInSQL(sql, null, officeExpenditureIds, new MapRowMapper<String, OfficeExpenditureBusTrip>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("expenditure_id");
			}

			@Override
			public OfficeExpenditureBusTrip mapRowValue(ResultSet rs,
					int rowNum) throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public void deleteByExId(String officeExpenditureId) {
		String sql = "delete from office_expenditure_bus_trip where expenditure_id=?";
		update(sql, new Object[]{officeExpenditureId});
	}

}
