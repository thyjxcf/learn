package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveFixedDeptDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveFixedDept;

import org.apache.commons.lang.StringUtils;
/**
 * office_executive_fixed_dept
 * @author 
 * 
 */
public class OfficeExecutiveFixedDeptDaoImpl extends BaseDao<OfficeExecutiveFixedDept> implements OfficeExecutiveFixedDeptDao{

	@Override
	public OfficeExecutiveFixedDept setField(ResultSet rs) throws SQLException{
		OfficeExecutiveFixedDept officeExecutiveFixedDept = new OfficeExecutiveFixedDept();
		officeExecutiveFixedDept.setId(rs.getString("id"));
		officeExecutiveFixedDept.setUnitId(rs.getString("unit_id"));
		officeExecutiveFixedDept.setDeptId(rs.getString("dept_id"));
		return officeExecutiveFixedDept;
	}

	@Override
	public OfficeExecutiveFixedDept save(OfficeExecutiveFixedDept officeExecutiveFixedDept){
		String sql = "insert into office_executive_fixed_dept(id, unit_id, dept_id) values(?,?,?)";
		if (StringUtils.isBlank(officeExecutiveFixedDept.getId())){
			officeExecutiveFixedDept.setId(createId());
		}
		Object[] args = new Object[]{
			officeExecutiveFixedDept.getId(), officeExecutiveFixedDept.getUnitId(), 
			officeExecutiveFixedDept.getDeptId()
		};
		update(sql, args);
		return officeExecutiveFixedDept;
	}
	
	@Override
	public void batchSave(List<OfficeExecutiveFixedDept> list) {
		String sql = "insert into office_executive_fixed_dept(id, unit_id, dept_id) values(?,?,?)";
		List<Object[]> objs = new ArrayList<Object[]>();
		for(OfficeExecutiveFixedDept fixedDept:list){
			if (StringUtils.isBlank(fixedDept.getId())){
				fixedDept.setId(createId());
			}
			Object[] args = new Object[]{
				fixedDept.getId(), fixedDept.getUnitId(), 
				fixedDept.getDeptId()
			};
			objs.add(args);
		}
		batchUpdate(sql, objs, new int[] {Types.CHAR, Types.CHAR, Types.CHAR});
	}
	
	@Override
	public void deleteByUnitId(String unitId) {
		String sql = "delete from office_executive_fixed_dept where unit_id = ? ";
		update(sql, new Object[]{unitId});
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_executive_fixed_dept where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExecutiveFixedDept officeExecutiveFixedDept){
		String sql = "update office_executive_fixed_dept set unit_id = ?, dept_id = ? where id = ?";
		Object[] args = new Object[]{
			officeExecutiveFixedDept.getUnitId(), officeExecutiveFixedDept.getDeptId(), 
			officeExecutiveFixedDept.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExecutiveFixedDept getOfficeExecutiveFixedDeptById(String id){
		String sql = "select * from office_executive_fixed_dept where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptMapByIds(String[] ids){
		String sql = "select * from office_executive_fixed_dept where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptList(){
		String sql = "select * from office_executive_fixed_dept";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptPage(Pagination page){
		String sql = "select * from office_executive_fixed_dept";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptByUnitIdList(String unitId){
		String sql = "select * from office_executive_fixed_dept where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeExecutiveFixedDept> getOfficeExecutiveFixedDeptByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_executive_fixed_dept where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public boolean isFixedDept(String deptId) {
		String sql = "select count(1) from office_executive_fixed_dept where dept_id = ? ";
		int i = queryForInt(sql, new Object[]{deptId});
		if(i > 0){
			return true;
		}
		return false;
	}
}