package net.zdsoft.office.expenditure.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureBusTrip;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureMetting;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureMettingDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * 会议费
 * @author 
 * 
 */
public class OfficeExpenditureMettingDaoImpl extends BaseDao<OfficeExpenditureMetting> implements OfficeExpenditureMettingDao{

	@Override
	public OfficeExpenditureMetting setField(ResultSet rs) throws SQLException{
		OfficeExpenditureMetting officeExpenditureMetting = new OfficeExpenditureMetting();
		officeExpenditureMetting.setId(rs.getString("id"));
		officeExpenditureMetting.setExpenditureId(rs.getString("expenditure_id"));
		officeExpenditureMetting.setReason(rs.getString("reason"));
		officeExpenditureMetting.setSpec(rs.getString("spec"));
		officeExpenditureMetting.setName(rs.getString("name"));
		officeExpenditureMetting.setPlace(rs.getString("place"));
		officeExpenditureMetting.setStartTime(rs.getTimestamp("start_time"));
		officeExpenditureMetting.setEndTime(rs.getTimestamp("end_time"));
		officeExpenditureMetting.setDays(rs.getInt("days"));
		officeExpenditureMetting.setNum(rs.getInt("num"));
		officeExpenditureMetting.setSum(rs.getDouble("sum"));
		officeExpenditureMetting.setHotelFee(rs.getDouble("hotel_fee"));
		officeExpenditureMetting.setRepastFee(rs.getDouble("repast_fee"));
		officeExpenditureMetting.setPlaceFee(rs.getDouble("place_fee"));
		officeExpenditureMetting.setGoodsFee(rs.getDouble("goods_fee"));
		officeExpenditureMetting.setIndiaFee(rs.getDouble("india_fee"));
		officeExpenditureMetting.setTrafficFee(rs.getDouble("traffic_fee"));
		officeExpenditureMetting.setLaborFee(rs.getDouble("labor_fee"));
		officeExpenditureMetting.setAdviertisementFee(rs.getDouble("adviertisement_fee"));
		officeExpenditureMetting.setOtherFee(rs.getDouble("other_fee"));
		officeExpenditureMetting.setCreationTime(rs.getTimestamp("creation_time"));
		return officeExpenditureMetting;
	}

	@Override
	public OfficeExpenditureMetting save(OfficeExpenditureMetting officeExpenditureMetting){
		String sql = "insert into office_expenditure_metting(id, expenditure_id, reason, spec, name, place, start_time, end_time, days, num, sum, hotel_fee, repast_fee, place_fee, goods_fee, india_fee, traffic_fee, labor_fee, adviertisement_fee, other_fee, creation_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExpenditureMetting.getId())){
			officeExpenditureMetting.setId(createId());
		}
		Object[] args = new Object[]{
			officeExpenditureMetting.getId(), officeExpenditureMetting.getExpenditureId(), 
			officeExpenditureMetting.getReason(), officeExpenditureMetting.getSpec(), 
			officeExpenditureMetting.getName(), officeExpenditureMetting.getPlace(), 
			officeExpenditureMetting.getStartTime(), officeExpenditureMetting.getEndTime(), 
			officeExpenditureMetting.getDays(), officeExpenditureMetting.getNum(), 
			officeExpenditureMetting.getSum(), officeExpenditureMetting.getHotelFee(), 
			officeExpenditureMetting.getRepastFee(), officeExpenditureMetting.getPlaceFee(), 
			officeExpenditureMetting.getGoodsFee(), officeExpenditureMetting.getIndiaFee(), 
			officeExpenditureMetting.getTrafficFee(), officeExpenditureMetting.getLaborFee(), 
			officeExpenditureMetting.getAdviertisementFee(), officeExpenditureMetting.getOtherFee(), 
			officeExpenditureMetting.getCreationTime()
		};
		update(sql, args);
		return officeExpenditureMetting;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_expenditure_metting where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExpenditureMetting officeExpenditureMetting){
		String sql = "update office_expenditure_metting set expenditure_id = ?, reason = ?, spec = ?, name = ?, place = ?, start_time = ?, end_time = ?, days = ?, num = ?, sum = ?, hotel_fee = ?, repast_fee = ?, place_fee = ?, goods_fee = ?, india_fee = ?, traffic_fee = ?, labor_fee = ?, adviertisement_fee = ?, other_fee = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeExpenditureMetting.getExpenditureId(), officeExpenditureMetting.getReason(), 
			officeExpenditureMetting.getSpec(), officeExpenditureMetting.getName(), 
			officeExpenditureMetting.getPlace(), officeExpenditureMetting.getStartTime(), 
			officeExpenditureMetting.getEndTime(), officeExpenditureMetting.getDays(), 
			officeExpenditureMetting.getNum(), officeExpenditureMetting.getSum(), 
			officeExpenditureMetting.getHotelFee(), officeExpenditureMetting.getRepastFee(), 
			officeExpenditureMetting.getPlaceFee(), officeExpenditureMetting.getGoodsFee(), 
			officeExpenditureMetting.getIndiaFee(), officeExpenditureMetting.getTrafficFee(), 
			officeExpenditureMetting.getLaborFee(), officeExpenditureMetting.getAdviertisementFee(), 
			officeExpenditureMetting.getOtherFee(), officeExpenditureMetting.getCreationTime(), 
			officeExpenditureMetting.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExpenditureMetting getOfficeExpenditureMettingById(String id){
		String sql = "select * from office_expenditure_metting where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public OfficeExpenditureMetting getOfficeExpenditureMettingByPrimarId(String officeExpenditureId) {
		String sql="select * from office_expenditure_metting where expenditure_id= ?";
		return query(sql, new Object[]{officeExpenditureId }, new SingleRow());
	}
	
	@Override
	public Map<String, OfficeExpenditureMetting> getOfficeExpenditureMettingByExIds(String[] officeExpenditureIds) {
		String sql = "select * from office_expenditure_metting where expenditure_id in";
		return queryForInSQL(sql, null, officeExpenditureIds, new MapRowMapper<String, OfficeExpenditureMetting>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("expenditure_id");
			}

			@Override
			public OfficeExpenditureMetting mapRowValue(ResultSet rs,
					int rowNum) throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public void deleteByExId(String officeExpenditureId) {
		String sql = "delete from office_expenditure_metting where expenditure_id=?";
		update(sql, new Object[]{officeExpenditureId});
	}
	
}
