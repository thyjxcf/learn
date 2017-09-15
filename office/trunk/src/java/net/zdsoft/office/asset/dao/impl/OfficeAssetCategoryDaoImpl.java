package net.zdsoft.office.asset.dao.impl;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.office.asset.dao.OfficeAssetCategoryDao;
import net.zdsoft.office.asset.entity.OfficeAssetCategory;
/**
 * office_asset_category
 * @author 
 * 
 */
public class OfficeAssetCategoryDaoImpl extends BaseDao<OfficeAssetCategory> implements OfficeAssetCategoryDao{
	
	private static final String SQL_INSERT = "insert into office_asset_category(id, unit_id, asset_name, leader_id, is_deleted,dept_leader_id,is_dept_leader,is_leader,is_master,is_meeting) values(?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "update office_asset_category set unit_id = ?, asset_name = ?, leader_id = ?, is_deleted = ?, dept_leader_id=?,is_dept_leader=?,is_leader=?,is_master=?,is_meeting=? where id = ?";
	
	private static final String SQL_FIND_BY_ID = "select * from office_asset_category where id = ? and is_deleted=0";
	
	private static final String SQL_DELETE_BY_IDS = "update office_asset_category set is_deleted=1 where id in";
	
	private static final String SQL_FIND_LIST_BY_UNITID = "select * from office_asset_category where unit_id = ? and is_deleted=0";
	
	private static final String SQL_FIND_MAP_BY_UNITID = "select * from office_asset_category where unit_id = ?";
	@Override
	public OfficeAssetCategory setField(ResultSet rs) throws SQLException{
		OfficeAssetCategory officeAssetCategory = new OfficeAssetCategory();
		officeAssetCategory.setId(rs.getString("id"));
		officeAssetCategory.setUnitId(rs.getString("unit_id"));
		officeAssetCategory.setAssetName(rs.getString("asset_name"));
		officeAssetCategory.setLeaderId(rs.getString("leader_id"));
		officeAssetCategory.setIsdeleted(rs.getBoolean("is_deleted"));
		officeAssetCategory.setDeptLeaderId(rs.getString("dept_leader_id"));
		officeAssetCategory.setIs_DeptLeader(rs.getBoolean("is_dept_leader"));
		officeAssetCategory.setIs_Leader(rs.getBoolean("is_leader"));
		officeAssetCategory.setIs_master(rs.getBoolean("is_master"));
		officeAssetCategory.setIs_meeting(rs.getBoolean("is_meeting"));
		return officeAssetCategory;
	}

	@Override
	public OfficeAssetCategory save(OfficeAssetCategory officeAssetCategory){
		if (StringUtils.isBlank(officeAssetCategory.getId())){
			officeAssetCategory.setId(createId());
		}
		Object[] args = new Object[]{
			officeAssetCategory.getId(), officeAssetCategory.getUnitId(), 
			officeAssetCategory.getAssetName(), officeAssetCategory.getLeaderId(), 
			officeAssetCategory.getIsdeleted(), officeAssetCategory.getDeptLeaderId(),
			officeAssetCategory.isIs_DeptLeader(),officeAssetCategory.isIs_Leader(),
			officeAssetCategory.isIs_master(),officeAssetCategory.isIs_meeting()
		};
		update(SQL_INSERT, args);
		return officeAssetCategory;
	}

	@Override
	public Integer delete(String[] ids){
		return updateForInSQL(SQL_DELETE_BY_IDS, null, ids);
	}

	@Override
	public Integer update(OfficeAssetCategory officeAssetCategory){
		Object[] args = new Object[]{
			officeAssetCategory.getUnitId(), officeAssetCategory.getAssetName(), 
			officeAssetCategory.getLeaderId(), officeAssetCategory.getIsdeleted(),officeAssetCategory.getDeptLeaderId(),
			officeAssetCategory.isIs_DeptLeader(),officeAssetCategory.isIs_Leader(),officeAssetCategory.isIs_master(),officeAssetCategory.isIs_meeting(),
			officeAssetCategory.getId()
		};
		return update(SQL_UPDATE, args);
	}

	@Override
	public OfficeAssetCategory getOfficeAssetCategoryById(String id){
		return query(SQL_FIND_BY_ID, new Object[]{id }, new SingleRow());
	}
	
	public List<OfficeAssetCategory> getOfficeAssetCategoryList(String unitId){
		return query(SQL_FIND_LIST_BY_UNITID, unitId, new MultiRow());
	}
	
	public List<OfficeAssetCategory> getOfficeAssetCategoryList(String unitId, String assetName, String id){
		StringBuffer sql = new StringBuffer(SQL_FIND_LIST_BY_UNITID);
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		if(StringUtils.isNotBlank(assetName)){
			sql.append(" and asset_name = ?");
			objs.add(assetName);
		}
			
		if(StringUtils.isNotBlank(id)){
			sql.append(" and id <> ?");
			objs.add(id);
		}
		
		return query(sql.toString(), objs.toArray(), new MultiRow());
	}
	
	public Map<String, OfficeAssetCategory> getOfficeAssetCategoryMap(String unitId){
		return queryForMap(SQL_FIND_MAP_BY_UNITID, new Object[]{unitId}, new MapRowMapper<String, OfficeAssetCategory>() {
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}

			@Override
			public OfficeAssetCategory mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
	
	public List<OfficeAssetCategory> getOfficeAssetCategoryListByLeaderId(String unitId, String leaderId){
		String sql = "select * from office_asset_category where unit_id=? and leader_id=? and is_deleted=0";
		return query(sql, new Object[]{unitId, leaderId}, new MultiRow());
	}
	
	public List<OfficeAssetCategory> getOfficeAssetCategoryListByDeptLeaderId(String unitId, String deptLeaderId){
		String sql = "select * from office_asset_category where unit_id=? and dept_leader_id like ? and is_deleted=0";
		return query(sql, new Object[]{unitId, "%"+deptLeaderId+"%"}, new MultiRow());
	}
}
