package net.zdsoft.office.expenditure.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureReception;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureReceptionDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_reception
 * @author 
 * 
 */
public class OfficeExpenditureReceptionDaoImpl extends BaseDao<OfficeExpenditureReception> implements OfficeExpenditureReceptionDao{

	@Override
	public OfficeExpenditureReception setField(ResultSet rs) throws SQLException{
		OfficeExpenditureReception officeExpenditureReception = new OfficeExpenditureReception();
		officeExpenditureReception.setId(rs.getString("id"));
		officeExpenditureReception.setExpenditureId(rs.getString("expenditure_id"));
		officeExpenditureReception.setGuestUnitName(rs.getString("guest_unit_name"));
		officeExpenditureReception.setGuestName(rs.getString("guest_name"));
		officeExpenditureReception.setGuestDuty(rs.getString("guest_duty"));
		officeExpenditureReception.setLetters(rs.getString("letters"));
		officeExpenditureReception.setNum(rs.getInt("num"));
		officeExpenditureReception.setStartTime(rs.getTimestamp("start_time"));
		officeExpenditureReception.setEndTime(rs.getTimestamp("end_time"));
		officeExpenditureReception.setDays(rs.getInt("days"));
		officeExpenditureReception.setAccompanyNum(rs.getInt("accompany_num"));
		officeExpenditureReception.setSum(rs.getDouble("sum"));
		officeExpenditureReception.setHotelStandardFee(rs.getDouble("hotel_standard_fee"));
		officeExpenditureReception.setHotelFee(rs.getDouble("hotel_fee"));
		officeExpenditureReception.setRepastStandardFee(rs.getDouble("repast_standard_fee"));
		officeExpenditureReception.setRepastFee(rs.getDouble("repast_fee"));
		officeExpenditureReception.setOtherFee(rs.getDouble("other_fee"));
		officeExpenditureReception.setCreationTime(rs.getTimestamp("creation_time"));
		return officeExpenditureReception;
	}

	@Override
	public OfficeExpenditureReception save(OfficeExpenditureReception officeExpenditureReception){
		String sql = "insert into office_expenditure_reception(id, expenditure_id, guest_unit_name, guest_name, guest_duty, letters, num, start_time, end_time, days, accompany_num, sum, hotel_standard_fee, hotel_fee, repast_standard_fee, repast_fee, other_fee, creation_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExpenditureReception.getId())){
			officeExpenditureReception.setId(createId());
		}
		Object[] args = new Object[]{
			officeExpenditureReception.getId(), officeExpenditureReception.getExpenditureId(), 
			officeExpenditureReception.getGuestUnitName(), officeExpenditureReception.getGuestName(), 
			officeExpenditureReception.getGuestDuty(), officeExpenditureReception.getLetters(), 
			officeExpenditureReception.getNum(), officeExpenditureReception.getStartTime(), 
			officeExpenditureReception.getEndTime(), officeExpenditureReception.getDays(), 
			officeExpenditureReception.getAccompanyNum(), officeExpenditureReception.getSum(), 
			officeExpenditureReception.getHotelStandardFee(), officeExpenditureReception.getHotelFee(), 
			officeExpenditureReception.getRepastStandardFee(), officeExpenditureReception.getRepastFee(), 
			officeExpenditureReception.getOtherFee(), officeExpenditureReception.getCreationTime()
		};
		update(sql, args);
		return officeExpenditureReception;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_expenditure_reception where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExpenditureReception officeExpenditureReception){
		String sql = "update office_expenditure_reception set expenditure_id = ?, guest_unit_name = ?, guest_name = ?, guest_duty = ?, letters = ?, num = ?, start_time = ?, end_time = ?, days = ?, accompany_num = ?, sum = ?, hotel_standard_fee = ?, hotel_fee = ?, repast_standard_fee = ?, repast_fee = ?, other_fee = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeExpenditureReception.getExpenditureId(), officeExpenditureReception.getGuestUnitName(), 
			officeExpenditureReception.getGuestName(), officeExpenditureReception.getGuestDuty(), 
			officeExpenditureReception.getLetters(), officeExpenditureReception.getNum(), 
			officeExpenditureReception.getStartTime(), officeExpenditureReception.getEndTime(), 
			officeExpenditureReception.getDays(), officeExpenditureReception.getAccompanyNum(), 
			officeExpenditureReception.getSum(), officeExpenditureReception.getHotelStandardFee(), 
			officeExpenditureReception.getHotelFee(), officeExpenditureReception.getRepastStandardFee(), 
			officeExpenditureReception.getRepastFee(), officeExpenditureReception.getOtherFee(), 
			officeExpenditureReception.getCreationTime(), officeExpenditureReception.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExpenditureReception getOfficeExpenditureReceptionById(String id){
		String sql = "select * from office_expenditure_reception where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public OfficeExpenditureReception getOfficeExpenditureReceptionByExId(
			String officeExpenditureId) {
		String sql = "select * from office_expenditure_reception where expenditure_id=?";
		return query(sql, new Object[]{officeExpenditureId}, new SingleRow());
	}
	
	@Override
	public Map<String, OfficeExpenditureReception> getOfficeExpenditureReceptionByExIds(String[] officeExpenditureIds) {
		String sql = "select * from office_expenditure_reception where expenditure_id in";
		return queryForInSQL(sql, null, officeExpenditureIds, new MapRowMapper<String, OfficeExpenditureReception>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("expenditure_id");
			}

			@Override
			public OfficeExpenditureReception mapRowValue(ResultSet rs,
					int rowNum) throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public void deleteByExId(String officeExpenditureId) {
		String sql = "delete from office_expenditure_reception where expenditure_id=?";
		update(sql, new Object[]{officeExpenditureId});
	}
	
}
