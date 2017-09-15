/* 
 * @(#)BaseDeptDaoImpl.java    Created on Nov 20, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.data.dao.BaseDeptDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 20, 2009 10:03:54 AM $
 */
public class BaseDeptDaoImpl extends BaseDao<Dept> implements BaseDeptDao {
    private static final String SQL_INSERT_DEPT = "INSERT INTO base_dept(id,unit_id,dept_name,"
            + "dept_code,remark,dept_state,dept_tel,dept_type,teacher_id,parent_id,"
            + "display_order,creation_time,modify_time,is_deleted,event_source,leader_id,is_default) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_DEPT_BY_IDS_MQ = "UPDATE base_dept SET is_deleted = 1,modify_time = ?,event_source=? WHERE id IN ";

    private static final String SQL_UPDATE_DEPT = "UPDATE base_dept SET unit_id=?,dept_name=?,"
            + "dept_code=?,remark=?,dept_state=?,dept_tel=?,dept_type=?,teacher_id=?,parent_id=?,"
            + "display_order=?,modify_time=?,is_deleted=?,event_source=?,leader_id=? WHERE id=?";
    
    private static final String SQL_UPDATE_DEPT_MQ = "UPDATE base_dept SET is_default=?,unit_id=?,dept_name=?,"
        + "dept_code=?,remark=?,dept_tel=?,dept_type=?,teacher_id=?,parent_id=?,"
        + "display_order=?,modify_time=?,is_deleted=?,event_source=? WHERE id=?";
    
    private static final String SQL_FIND_DEPT_BY_DEPTNAME = "select * from base_dept where unit_id = ? and dept_name=? and is_deleted = 0 ";

    private static final String SQL_FIND_DEPTS_BY_UNITID_DEPTCODE = "SELECT count(id) FROM base_dept where is_deleted=0 and unit_id = ? and dept_code = ?";

    private static final String SQL_FIND_MAX_UNIT_ORDERID = "SELECT max(display_order) FROM base_dept WHERE is_deleted=0 AND unit_id=?";

    private static final String SQL_FIND_DEPTS_BY_UNITID_DEPTNAME = "SELECT count(id) FROM base_dept where is_deleted=0 and unit_id = ? and dept_name = ?";

    private static final String SQL_FIND_DEFAULT_DEPTS_COUNT_BY_UNITID="SELECT count(id) FROM base_dept where is_deleted=0 and unit_id = ? and is_default=1";
    
    private static final String SQL_FIND_DEFAULT_DEPTS_BY_UNITID="SELECT * FROM base_dept where is_deleted=0 and unit_id = ? and is_default=1";
    
    private static final String SQL_FIND_DEPTS_BY_UNIT_NO_DEPT = "SELECT * FROM base_dept where unit_id = ? and id <> ? and is_deleted=0 order by display_order";

    private static final String SQL_FIND_MAX_UNIT_DEPTCODE = "SELECT max(dept_code) FROM base_dept WHERE is_deleted=0 AND unit_id=?";

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
        dept.setLeaderId(rs.getString("leader_id"));
        dept.setParentid(rs.getString("parent_id"));
        dept.setInstituteId(rs.getString("institute_id"));
        dept.setOrderid(rs.getLong("display_order"));
        dept.setCreationTime(rs.getTimestamp("creation_time"));
        dept.setModifyTime(rs.getTimestamp("modify_time"));
        return dept;
    }

    public void insertDept(Dept dept) {
        if (StringUtils.isEmpty(dept.getId())) {
            dept.setId(getGUID());
        }
        dept.setCreationTime(new Date());
        dept.setModifyTime(new Date());
        dept.setIsdeleted(false);
        update(SQL_INSERT_DEPT, new Object[] { dept.getId(), dept.getUnitId(), dept.getDeptname(),
                dept.getDeptCode(), dept.getAbout(), dept.getMark(), dept.getDepttel(),
                dept.getJymark(), dept.getPrincipan(), dept.getParentid(), dept.getOrderid(),
                dept.getCreationTime(), dept.getModifyTime(), dept.getIsdeleted() ? 1 : 0,dept.getEventSourceValue(),dept.getLeaderId(),dept.isDefault()? 1 : 0 }, new int[] {
                Types.CHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
                Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.CHAR, Types.BIGINT, Types.TIMESTAMP,
                Types.TIMESTAMP, Types.INTEGER ,Types.INTEGER ,Types.CHAR,Types.INTEGER});
    }

    public void deleteDept(String[] deptids, EventSourceType eventSource) {
        updateForInSQL(SQL_DELETE_DEPT_BY_IDS_MQ, new Object[] { new Date(),eventSource.getValue() }, deptids);
    }

    public void updateDept(Dept dept) {
        dept.setModifyTime(new Date());
        dept.setIsdeleted(false);
        update(SQL_UPDATE_DEPT, new Object[] { dept.getUnitId(), dept.getDeptname(),
                dept.getDeptCode(), dept.getAbout(), dept.getMark(), dept.getDepttel(),
                dept.getJymark(), dept.getPrincipan(), dept.getParentid(), dept.getOrderid(),
                dept.getModifyTime(), dept.getIsdeleted() ? 1 : 0,dept.getEventSourceValue(), dept.getLeaderId(),dept.getId() }, new int[] { Types.CHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR,
                Types.INTEGER, Types.CHAR, Types.CHAR, Types.BIGINT, Types.TIMESTAMP, Types.INTEGER,Types.INTEGER ,
                Types.CHAR,Types.CHAR });
    }

    public void saveBatchDepts(List<Dept> depts) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        Dept dept;
        for (int i = 0; i < depts.size(); i++) {
            dept = depts.get(i);
            if (StringUtils.isEmpty(dept.getId())){
                dept.setId(getGUID());
            }
            dept.setCreationTime(new Date());
            dept.setModifyTime(new Date());
            dept.setIsdeleted(false);
            listOfArgs.add(new Object[] { dept.getId(), dept.getUnitId(), dept.getDeptname(),
                    dept.getDeptCode(), dept.getAbout(), dept.getMark(), dept.getDepttel(),
                    dept.getJymark(), dept.getPrincipan(), dept.getParentid(), dept.getOrderid(),
                    dept.getCreationTime(), dept.getModifyTime(), dept.getIsdeleted()?1:0,dept.getEventSourceValue(),dept.getLeaderId() ,dept.isDefault()?1:0});
        }
        int[] argTypes = { Types.CHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.CHAR, Types.BIGINT,
                Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER , Types.INTEGER,Types.CHAR,Types.INTEGER};

        batchUpdate(SQL_INSERT_DEPT, listOfArgs, argTypes);

    }
    public void updateBatchDepts4Mq(List<Dept> depts) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (Dept dept : depts) {
            listOfArgs.add(new Object[] { dept.isDefault()? 1 : 0,dept.getUnitId(), dept.getDeptname(), dept.getDeptCode(),
                    dept.getAbout(),  dept.getDepttel(), dept.getJymark(),
                    dept.getPrincipan(), dept.getParentid(), dept.getOrderid(),
                    dept.getModifyTime(), dept.getIsdeleted() ? 1 : 0,dept.getEventSourceValue(), dept.getId() });

        }
        int[] argTypes = { Types.INTEGER, Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.CHAR, Types.BIGINT, Types.TIMESTAMP,
                Types.INTEGER, Types.INTEGER,  Types.CHAR  };

        batchUpdate(SQL_UPDATE_DEPT_MQ, listOfArgs, argTypes);

    }
    
    public Dept getDept(String unitId,String deptName) {
        return query(SQL_FIND_DEPT_BY_DEPTNAME, new Object[]{unitId,deptName}, new SingleRow());
    }

    public boolean isExistsDeptCode(String deptCode, String unitId) {
        int isExists = queryForInt(SQL_FIND_DEPTS_BY_UNITID_DEPTCODE, new String[] { unitId,
                deptCode });
        return isExists == 0 ? false : true;
    }

    public Long getAvaOrder(String unitId) {
        Long max = queryForLong(SQL_FIND_MAX_UNIT_ORDERID, new String[] { unitId });
        if (max == null) {
            max = 0L;
        } else {
            max = max + 1;
        }
        return max;
    }

    public boolean isExistsDeptName(String deptName, String unitId) {
        int isExists = queryForInt(SQL_FIND_DEPTS_BY_UNITID_DEPTNAME, new String[] { unitId,
                deptName.trim() });

        return isExists == 0 ? false : true;
    }
    
    public boolean isExistsDefaultDeptName(String unitId) {
        int isExists = queryForInt(SQL_FIND_DEFAULT_DEPTS_COUNT_BY_UNITID, new String[] { unitId});

        return isExists == 0 ? false : true;
    }

    public Map<String, String> existsDeptNameMap(String[] deptnames) {
        String sql = "select dept_name,id,unit_id from base_dept where is_deleted = 0  and dept_name in";
        return queryForInSQL(sql, new String[] {  }, deptnames,
                new MapRowMapper<String, String>() {

                    public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
                        return rs.getString(1);
                    }

                    public String mapRowValue(ResultSet rs, int arg1) throws SQLException {
                        return rs.getString(2)+rs.getString(3);
                    }
                });

    }

    public Map<String, String> existsDeptCodesMap(String[] deptcodes) {
        String sql = "select dept_code,id,unit_id from base_dept where is_deleted = 0  and dept_code in";
        return queryForInSQL(sql, new String[] {  }, deptcodes,
                new MapRowMapper<String, String>() {

                    public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
                        return rs.getString(1);
                    }

                    public String mapRowValue(ResultSet rs, int arg1) throws SQLException {
                        return rs.getString(2)+rs.getString(3);
                    }
                });

    }

    public boolean isExistsDeptName(String deptname, String excludeDeptId, String unitId) {
        List<Dept> list = getAllUnitDeptExceptSelf(unitId, excludeDeptId);
        if (list == null) {
            return false;
        }
        Dept dept;
        for (int i = 0; i < list.size(); i++) {
            dept = list.get(i);
            if (dept.getDeptname().trim().equals(deptname.trim())) {
                return true;
            }
        }
        return false;
    }

    public List<Dept> getAllUnitDeptExceptSelf(String unitId, String excludeDeptId) {
        return query(SQL_FIND_DEPTS_BY_UNIT_NO_DEPT, new String[] { unitId, excludeDeptId },
                new MultiRow());
    }

    public boolean isExistsDeptCode(String deptCode, String excludeDeptId, String unitId) {
        List<Dept> list = getAllUnitDeptExceptSelf(unitId, excludeDeptId);
        if (list == null) {
            return false;
        }
        Dept dept;
        for (int i = 0; i < list.size(); i++) {
            dept = (Dept) list.get(i);
            if (dept.getDeptCode().trim().equals(deptCode.trim())) {
                return true;
            }
        }
        return false;
    }

    public Map<String, Integer> getDeptCount(String[] parentIds) {
        String sql = "select parent_id,count(id) from base_dept where is_deleted = 0 and parent_id in";
        return queryForInSQL(sql, null, parentIds, new MapRowMapper<String, Integer>() {

            public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
                return rs.getString(1);
            }

            public Integer mapRowValue(ResultSet rs, int arg1) throws SQLException {
                return rs.getInt(2);
            }
        }, " Group by parent_id");

    }

    public String getAvaDeptCode(String unitId) {

        int max = queryForInt(SQL_FIND_MAX_UNIT_DEPTCODE, new String[] { unitId });

        max = max + 1;

        String formatStr = "%1$0" + Dept.GROUPID_LENGTH + "d";
        String deptCode = String.format(formatStr, max);

        return deptCode;
    }

	@Override
	public Dept getDefaultDept(String unitId) {
		return query(SQL_FIND_DEFAULT_DEPTS_BY_UNITID, unitId, new SingleRow());
	}

}
