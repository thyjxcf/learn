package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.DeptDao;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;

import org.apache.commons.lang.StringUtils;

public class DeptDaoImpl extends BaseDao<Dept> implements DeptDao {

    private static final String SQL_FIND_DEPT_BY_ID = "SELECT * FROM base_dept WHERE is_deleted = 0 and id=?";
    
    private static final String SQL_FIND_DEPT_BY_NAME = "SELECT * FROM base_dept WHERE is_deleted = 0 and unit_id = ? and dept_name=?";

    private static final String SQL_FIND_DEPTS_BY_IDS = "SELECT * FROM base_dept WHERE is_deleted=0 and id IN";
    
    private static final String SQL_FIND_DEPTS_BY_UNITID = "SELECT * FROM base_dept where is_deleted=0 and unit_id = ? order by display_order";

    private static final String SQL_FIND_DEPTS_BY_PARENTID = "SELECT * FROM base_dept where is_deleted=0 and parent_id = ? order by display_order";

    private static final String SQL_FIND_DEPTS_BY_PARENTID_INSTITUTEID = "SELECT * FROM base_dept where is_deleted=0 and parent_id = ? and institute_id = ? order by display_order";
        
    private static final String SQL_FIND_DEPTS_BY_UNITID_PARENTID = "SELECT * FROM base_dept where unit_id = ? and parent_id = ? and is_deleted=0 order by display_order";

    private static final String SQL_FIND_DEPTS_BY_UNITID_PARENTID_INSTITUTEID = "SELECT * FROM base_dept where unit_id = ? and parent_id = ? AND institute_id = ? and is_deleted=0 order by display_order";
    
    public Dept setField(ResultSet rs) throws SQLException {
        Dept dept = new Dept();
        dept.setId(rs.getString("id"));
        dept.setUnitId(rs.getString("unit_id"));
        dept.setDeptname(rs.getString("dept_name"));
        dept.setDeptCode(rs.getString("dept_code"));
        dept.setAbout(rs.getString("remark"));
        dept.setMark(rs.getInt("dept_state"));
        dept.setDepttel(rs.getString("dept_tel"));
        dept.setJymark(rs.getInt("dept_type"));
        dept.setPrincipan(rs.getString("teacher_id"));
        dept.setParentid(rs.getString("parent_id"));
        dept.setInstituteId(rs.getString("institute_id"));
        dept.setOrderid(rs.getLong("display_order"));
        dept.setLeaderId(rs.getString("leader_id"));
        dept.setCreationTime(rs.getTimestamp("creation_time"));
        dept.setModifyTime(rs.getTimestamp("modify_time"));
        dept.setDefault(rs.getBoolean("is_default"));
        dept.setAreaId(rs.getString("area_id"));
        dept.setDeputyHeadId(rs.getString("deputy_head_id"));
        dept.setDeptShortName(rs.getString("dept_short_name"));
        return dept;
    }
    
    public List<Dept> getDeptsByDeptId(String deptId, String deptName) {
    	StringBuffer sql =new StringBuffer("SELECT a.* FROM base_dept a,(SELECT unit_id as unitid,parent_id as parentid ");
    		sql.append("FROM base_dept where id= ? and is_deleted=0) b ");
    		sql.append("where unit_id = unitid and parent_id = parentid and is_deleted=0 ");
    	if(StringUtils.isBlank(deptName)){
    		sql.append(" order by display_order");
    		return query(sql.toString(), deptId, new MultiRow());
    	}else{
    		sql.append(" and dept_name like ? order by display_order");
    		return query(sql.toString(), new Object[]{deptId,deptName+"%"}, new MultiRow());
    	}
    }
    
    public Dept getDept(String deptId) {
        return query(SQL_FIND_DEPT_BY_ID, deptId, new SingleRow());
    }
    
    @Override
    public Dept getDeptByName(String unitId, String deptName) {
    	return query(SQL_FIND_DEPT_BY_NAME, new Object[]{unitId,deptName}, new SingleRow());
    }
    
    public Map<String, Dept> getDeptMap(String[] deptIds) {
        return queryForInSQL(SQL_FIND_DEPTS_BY_IDS, null, deptIds, new MapRow());
    }

    
    public List<Dept> getDepts(String unitId) {
        return query(SQL_FIND_DEPTS_BY_UNITID, new String[] { unitId }, new MultiRow());
    }

    
    public List<Dept> getDeptsByParentId(String parentId) {
        return (List<Dept>) query(SQL_FIND_DEPTS_BY_PARENTID, new String[] { parentId },
                new MultiRow());
    }

    public List<Dept> getDirectDeptsByInstituteId(String instituteId) {
        return query(SQL_FIND_DEPTS_BY_PARENTID_INSTITUTEID, new Object[] { Dept.TOP_GROUP_GUID,
                instituteId }, new MultiRow());
    }
    
    public List<Dept> getDepts(String unitId, String parentId) {
        return query(SQL_FIND_DEPTS_BY_UNITID_PARENTID, new String[] { unitId,
                parentId }, new MultiRow());
    }

    public List<Dept> getDirectDepts(String unitId) {
        return query(SQL_FIND_DEPTS_BY_UNITID_PARENTID_INSTITUTEID, new String[] { unitId,
                Dept.TOP_GROUP_GUID, Dept.TOP_GROUP_GUID }, new MultiRow());
    }
    
    public Map<String, Dept> getDeptMap(String unitId) {
        return queryForMap(SQL_FIND_DEPTS_BY_UNITID, new String[] { unitId }, new MapRow());
    }

	public List<Dept> getDeptsByParentId(String parentId, String deptName) {
		String sql = "SELECT * FROM base_dept where is_deleted=0 and parent_id = ? and dept_name like ? order by display_order";
		return query(sql, new String[] { parentId,
				deptName+"%"}, new MultiRow());
	}

	public List<Dept> getDepts(String unitId, String parentId, String deptName) {
		String sql = "SELECT * FROM base_dept where unit_id = ? and parent_id = ? and dept_name like ? and is_deleted=0 order by display_order";
		return query(sql, new String[] {unitId, parentId,
				deptName+"%"}, new MultiRow());
	}
	public List<Dept> getDirectDepts(String unitId, String deptName) {
		String sql = "SELECT * FROM base_dept where unit_id = ? and parent_id = ? AND institute_id = ? and dept_name like ? and is_deleted=0 order by display_order";
        return query(sql, new String[] { unitId,
                Dept.TOP_GROUP_GUID, Dept.TOP_GROUP_GUID,deptName+"%" }, new MultiRow());
    }

	@Override
	public List<Dept> getDirectDepts(String unitId, int deptType,
			String deptName) {
		String sql ="";
		if(StringUtils.isNotBlank(deptName))
		{
			sql = "SELECT * FROM base_dept where unit_id = ? and parent_id = ? AND institute_id = ? and dept_type=? and  dept_name like '"+deptName+"%'";
		}else{
			sql = "SELECT * FROM base_dept where unit_id = ? and parent_id = ? AND institute_id = ? and dept_type=? ";
		}
		sql +=" and is_deleted=0 order by area_id,display_order";
        return query(sql, new Object[] { unitId,
                Dept.TOP_GROUP_GUID, Dept.TOP_GROUP_GUID,deptType}, new MultiRow());
	}

	@Override
	public String isTeacherGroupHead(String teacherId) {
		String sql = "SELECT * FROM base_dept where teacher_id = ? and parent_id = ? AND institute_id = ? and dept_type=2  and is_deleted=0";
		List<Dept> dept = query(sql, new Object[] { teacherId,
                Dept.TOP_GROUP_GUID, Dept.TOP_GROUP_GUID}, new MultiRow());
		if(dept==null || dept.size()==0){
			return "";
		}
		return dept.get(0).getId();
	}

	@Override
	public String isPrincipanGroupHead(String userId) {
		String sql = "select * from base_dept where teacher_id=? and is_deleted=0";
		List<Dept> dept = query(sql, new Object[] { userId}, new MultiRow());
		if(dept==null || dept.size()==0){
			return "";
		}
		return dept.get(0).getId();
	}
	
	public boolean isDeputyHead(String unitId, String userId){
		String sql = "select count(1) from base_dept where unit_id=? and deputy_head_id=? and is_deleted=0";
		int i = queryForInt(sql, new Object[] {unitId,userId});
		if(i > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public List<Dept> getDeputyHead(String unitId, String userId) {
		String sql = "SELECT * FROM base_dept where unit_id = ? and deputy_head_id = ? and is_deleted=0";
		return query(sql, new String[] {unitId,userId}, new MultiRow());
	}

	@Override
	public boolean isLeader(String unitId, String userId) {
		String sql = "select count(1) from base_dept where unit_id=? and leader_id=? and is_deleted=0";
		int i = queryForInt(sql, new Object[] {unitId,userId});
		if(i > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public List<Dept> getDeptsByAreaId(String areaId) {
		String sql = "select * from base_dept where is_deleted = 0 and area_id = ? ";
		return query(sql, areaId, new MultiRow());
	}
	
	@Override
	public List<Dept> getDeptList(String[] deptIds) {
		String sql = "select * from base_dept where is_deleted = 0 and id in ";
		return queryForInSQL(sql, null, deptIds, new MultiRow());
	}
	
	@Override
	public String[] getUserIdsByLeaderId(String unitId, String leaderId) {
		String sql = "select teacher_id from base_dept where unit_id=? and leader_id=? and is_deleted=0";
		List<String> list = query(sql, new Object[] {unitId,leaderId}, new MultiRowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("teacher_id");
			}
		});
		return list.toArray(new String[0]);
	}
}
