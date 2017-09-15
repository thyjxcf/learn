package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeCarSubsidyDao;
import net.zdsoft.office.dailyoffice.entity.OfficeCarSubsidy;

import org.apache.commons.lang.StringUtils;
/**
 * office_car_subsidy
 * @author 
 * 
 */
public class OfficeCarSubsidyDaoImpl extends BaseDao<OfficeCarSubsidy> implements OfficeCarSubsidyDao{
	private static final String SQL_INSERT = "insert into office_car_subsidy(id, unit_id, scope, subsidy, is_detele) values(?,?,?,?,?)";
	private static final String SQL_UPDATE = "update office_car_subsidy set scope = ?, subsidy = ? where id = ?";
	@Override
	public OfficeCarSubsidy setField(ResultSet rs) throws SQLException{
		OfficeCarSubsidy officeCarSubsidy = new OfficeCarSubsidy();
		officeCarSubsidy.setId(rs.getString("id"));
		officeCarSubsidy.setScope(rs.getString("scope"));
		officeCarSubsidy.setSubsidy(rs.getFloat("subsidy"));
		officeCarSubsidy.setIsDetele(rs.getBoolean("is_detele"));
		officeCarSubsidy.setUnitId(rs.getString("unit_id"));
		return officeCarSubsidy;
	}

	@Override
	public OfficeCarSubsidy save(OfficeCarSubsidy officeCarSubsidy){
		String sql = "insert into office_car_subsidy(id, scope, subsidy, unit_id, is_detele) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeCarSubsidy.getId())){
			officeCarSubsidy.setId(createId());
		}
		Object[] args = new Object[]{
			officeCarSubsidy.getId(), officeCarSubsidy.getScope(), 
			officeCarSubsidy.getSubsidy(),officeCarSubsidy.getUnitId(), 
			officeCarSubsidy.getIsDetele()
		};
		update(sql, args);
		return officeCarSubsidy;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_car_subsidy set is_detele = 1 where id in ";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeCarSubsidy officeCarSubsidy){
		String sql = "update office_car_subsidy set scope = ?, subsidy = ?, is_detele = ? where id = ?";
		Object[] args = new Object[]{
			officeCarSubsidy.getScope(), officeCarSubsidy.getSubsidy(), 
			officeCarSubsidy.getIsDetele(), officeCarSubsidy.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeCarSubsidy getOfficeCarSubsidyById(String id){
		String sql = "select * from office_car_subsidy where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeCarSubsidy> getOfficeCarSubsidyMapByIds(String[] ids){
		String sql = "select * from office_car_subsidy where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeCarSubsidy> getOfficeCarSubsidyList(){
		String sql = "select * from office_car_subsidy";
		return query(sql, new MultiRow());
	}
	
	@Override
	public List<OfficeCarSubsidy> getOfficeCarSubsidyList(String unitId) {
		String sql = "select * from office_car_subsidy where unit_id = ? and is_detele = 0";
		return query(sql,new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeCarSubsidy> getOfficeCarSubsidyPage(Pagination page){
		String sql = "select * from office_car_subsidy";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public void batchSave(List<OfficeCarSubsidy> insertList) {
		// TODO Auto-generated method stub
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeCarSubsidy officeCarSubsidy:insertList) {
			if (StringUtils.isBlank(officeCarSubsidy.getId()))
				officeCarSubsidy.setId(getGUID());
				Object[] args = new Object[]{
						officeCarSubsidy.getId(), officeCarSubsidy.getUnitId(), 
						officeCarSubsidy.getScope(), officeCarSubsidy.getSubsidy(), 
						officeCarSubsidy.getIsDetele()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR, Types.VARCHAR,
				Types.FLOAT, Types.BOOLEAN
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
	}
	
	public void batchUpdate(List<OfficeCarSubsidy> updateList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeCarSubsidy officeCarSubsidy:updateList) {
			Object[] args = new Object[]{
					officeCarSubsidy.getScope(), officeCarSubsidy.getSubsidy(),
					officeCarSubsidy.getId()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.VARCHAR, Types.FLOAT, Types.CHAR
				};
		batchUpdate(SQL_UPDATE, listOfArgs, argTypes);
		
	};
}