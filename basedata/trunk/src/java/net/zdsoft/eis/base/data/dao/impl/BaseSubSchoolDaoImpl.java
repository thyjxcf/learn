/**
 * 
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.eis.base.data.dao.BaseSubSchoolDao;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author yanb
 * 
 */
public class BaseSubSchoolDaoImpl extends BaseDao<SubSchool> implements BaseSubSchoolDao {
    private static final String SQL_INSERT_BASICSUBSCHOOLINFO = "INSERT INTO stusys_subschool(id,school_id,name,"
            + "address,is_deleted,updatestamp) " + "VALUES(?,?,?,?,?,?)";

    private static final String SQL_DELETE_BASICSUBSCHOOLINFO_BY_IDS = "UPDATE stusys_subschool SET is_deleted = '1',updatestamp=? WHERE id IN ";

    private static final String SQL_UPDATE_BASICSUBSCHOOLINFO = "UPDATE stusys_subschool SET school_id=?,name=?,"
            + "address=?,is_deleted=?,updatestamp=? WHERE id=?";

    private static final String SQL_FIND_BASICSUBSCHOOLINFO_BY_SCHID = "SELECT * FROM stusys_subschool WHERE school_id=? AND is_deleted = '0'";

    public SubSchool setField(ResultSet rs) throws SQLException {
        SubSchool subSchool = new SubSchool();
        subSchool.setId(rs.getString("id"));
        subSchool.setSchid(rs.getString("school_id"));
        subSchool.setName(rs.getString("name"));
        subSchool.setAddress(rs.getString("address"));
        subSchool.setIsdeleted(rs.getBoolean("is_deleted"));
        subSchool.setUpdatestamp(rs.getLong("updatestamp"));
        return subSchool;
    }

    public void insertSubSchool(SubSchool subSchool) {
        subSchool.setId(getGUID());
        subSchool.setIsdeleted(false);
        subSchool.setUpdatestamp(System.currentTimeMillis());
        update(SQL_INSERT_BASICSUBSCHOOLINFO, new Object[] { subSchool.getId(),
                subSchool.getSchid(), subSchool.getName(), subSchool.getAddress(),
                subSchool.getIsdeleted(), subSchool.getUpdatestamp() }, new int[] { Types.CHAR,
                Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.INTEGER });
    }

    public void deleteSubSchool(String[] subSchoolIds) {
        updateForInSQL(SQL_DELETE_BASICSUBSCHOOLINFO_BY_IDS, new Object[] { System
                .currentTimeMillis() }, subSchoolIds);
    }

    public void updateSubSchool(SubSchool subSchool) {
        subSchool.setIsdeleted(false);
        subSchool.setUpdatestamp(System.currentTimeMillis());
        update(SQL_UPDATE_BASICSUBSCHOOLINFO, new Object[] { subSchool.getSchid(),
                subSchool.getName(), subSchool.getAddress(), subSchool.getIsdeleted(),
                subSchool.getUpdatestamp(), subSchool.getId() }, new int[] { Types.CHAR,
                Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.INTEGER, Types.CHAR });
    }

    
    public List<SubSchool> getSubSchools(String schoolId) {
        return query(SQL_FIND_BASICSUBSCHOOLINFO_BY_SCHID, schoolId, new MultiRow());
    }
}
