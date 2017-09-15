package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeUtilityNumberDao;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityNumber;

import org.apache.commons.lang.StringUtils;
/**
 * office_utility_number
 * @author 
 * 
 */
public class OfficeUtilityNumberDaoImpl extends BaseDao<OfficeUtilityNumber> implements OfficeUtilityNumberDao{

	private static final String SQL_INSERT = "insert into office_utility_number(id, unit_id, utility_id, number_id) values(?,?,?,?)";
	private static final String SQL_FIND_UTILITYAPPLYIDS_BY_APPLYNUMBERIDS = "select utility_id from office_utility_number where number_id in ";
	
	@Override
	public OfficeUtilityNumber setField(ResultSet rs) throws SQLException{
		OfficeUtilityNumber officeUtilityNumber = new OfficeUtilityNumber();
		officeUtilityNumber.setId(rs.getString("id"));
		officeUtilityNumber.setUnitId(rs.getString("unit_id"));
		officeUtilityNumber.setUtilityId(rs.getString("utility_id"));
		officeUtilityNumber.setNumberId(rs.getString("number_id"));
		return officeUtilityNumber;
	}

	@Override
	public void save(OfficeUtilityNumber officeUtilityNumber){
		if (StringUtils.isBlank(officeUtilityNumber.getId())){
			officeUtilityNumber.setId(createId());
		}
		Object[] args = new Object[]{
			officeUtilityNumber.getId(), officeUtilityNumber.getUnitId(), 
			officeUtilityNumber.getUtilityId(), officeUtilityNumber.getNumberId()
		};
		update(SQL_INSERT, args);
	}
	
	@Override
	public void insertOfficeUtilityNumbers(
			List<OfficeUtilityNumber> officeUtilityNumbers) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeUtilityNumber officeUtilityNumber:officeUtilityNumbers) {
			if (StringUtils.isBlank(officeUtilityNumber.getId()))
				officeUtilityNumber.setId(getGUID());
			Object[] args = new Object[]{
					officeUtilityNumber.getId(), officeUtilityNumber.getUnitId(), 
					officeUtilityNumber.getUtilityId(), officeUtilityNumber.getNumberId()
				};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR, Types.CHAR,
				Types.CHAR
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
		
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_utility_number where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeUtilityNumber officeUtilityNumber){
		String sql = "update office_utility_number set unit_id = ?, utility_id = ?, number_id = ? where id = ?";
		Object[] args = new Object[]{
			officeUtilityNumber.getUnitId(), officeUtilityNumber.getUtilityId(), 
			officeUtilityNumber.getNumberId(), officeUtilityNumber.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeUtilityNumber getOfficeUtilityNumberById(String id){
		String sql = "select * from office_utility_number where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeUtilityNumber> getOfficeUtilityNumberMapByIds(String[] ids){
		String sql = "select * from office_utility_number where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeUtilityNumber> getOfficeUtilityNumberList(){
		String sql = "select * from office_utility_number";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeUtilityNumber> getOfficeUtilityNumberPage(Pagination page){
		String sql = "select * from office_utility_number";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeUtilityNumber> getOfficeUtilityNumberByUnitIdList(String unitId){
		String sql = "select * from office_utility_number where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeUtilityNumber> getOfficeUtilityNumberByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_utility_number where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public String[] getUtilityApplyIds(String[] applyNumberIds) {
		List<String> ids = queryForInSQL(SQL_FIND_UTILITYAPPLYIDS_BY_APPLYNUMBERIDS, null, applyNumberIds,
				new MultiRowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("utility_id");
					}
				});
		return ids.toArray(new String[0]);
	}
}