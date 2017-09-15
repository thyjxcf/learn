package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.dao.StudentGraduateDao;
import net.zdsoft.eis.base.common.entity.StudentGraduate;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 */
public class StudentGraduateDaoImpl extends BaseDao<StudentGraduate> implements StudentGraduateDao {
    private static final String SQL_FIND_STUDENTGRADUATE_BY_SCHID_SEMESTER = "SELECT * FROM student_graduate WHERE schid=? AND acadyear=? AND semester=? ";

    private static final String SQL_FIND_STUDENTGRADUATE_BY_SCHID_STUIDS = "SELECT * FROM student_graduate WHERE schid=? AND stuid IN";

    private static final String SQL_FIND_STUDENTGRADUATE_BY_STUIDS = "SELECT * FROM student_graduate WHERE stuid IN";

    private static final String SQL_FIND_STUDENTGRADUATE_BY_BYNUM = "SELECT * FROM student_graduate WHERE bynumber=?";

    private static final String SQL_FIND_GRADUATED_STU_BY_CLSID = "SELECT * FROM student_graduate WHERE class_id =? ";
    
    private static final String SQL_FIND_GRADUATED_STU_BY_CLSIDS = "SELECT g.* FROM student_graduate g, base_student s WHERE s.id = g.stuid and s.now_state = '41' and " +
    		"s.is_leave_school = 1 and s.is_deleted = 0 and g.class_id IN ";
    
    private static final String SQL_FIND_GRADUATED_STU_BY_CLSIDS_STUNAME = "SELECT g.* FROM student_graduate g, base_student s WHERE s.id = g.stuid and s.now_state = '41' and " +
	"s.is_leave_school = 1 and s.is_deleted = 0 and s.student_name like ? and s.student_code like ? and g.class_id IN ";

    // ==============base_class表关联的查询==================
    private static final String SQL_FIND_STUINFO_AND_CLS_BY_ACADYEAR = "SELECT si.stuid,bc.section,"
            + "(cast(substr(?,6,9) AS integer) - cast(substr(bc.acadyear,1,4) AS integer)) as grade "
            + "FROM student_graduate si, base_class bc "
            + "WHERE si.schid = ? AND si.class_id = bc.id AND "
            + "bc.is_deleted = 0 AND bc.school_id=? AND "
            + "(cast(substr(?,6,9) AS integer) - cast(substr(bc.acadyear,1,4) AS integer)) <= bc.schooling_length AND "
            + "(cast(substr(?,6,9) AS integer) - cast(substr(bc.acadyear,1,4) AS integer)) >0 "
            + "ORDER BY bc.section ASC,bc.acadyear DESC,bc.class_code ASC";
    
    public StudentGraduate setField(ResultSet rs) throws SQLException {
        StudentGraduate studentGraduate = new StudentGraduate();
        studentGraduate.setId(rs.getString("byid"));
        studentGraduate.setSchid(rs.getString("schid"));
        studentGraduate.setStuid(rs.getString("stuid"));
        studentGraduate.setBytype(rs.getString("bytype"));
        studentGraduate.setBydate(rs.getTimestamp("bydate"));
        studentGraduate.setBynumber(rs.getString("bynumber"));
        studentGraduate.setByto(rs.getString("byto"));
        studentGraduate.setFlowexplain(rs.getString("flowexplain"));
        studentGraduate.setAcadyear(rs.getString("acadyear"));
        studentGraduate.setSemester(rs.getString("semester"));
        studentGraduate.setOperator(rs.getString("operator"));
        studentGraduate.setOperateunit(rs.getString("operateunit"));
        studentGraduate.setUpdatestamp(rs.getLong("updatestamp"));
        studentGraduate.setGraduateReason(rs.getString("graduate_reason"));
        studentGraduate.setClassid(rs.getString("class_id"));
        return studentGraduate;
    }

    public List<StudentGraduate> getStudentGraduates(String schoolId, String acadyear,
            String semester) {
        return query(SQL_FIND_STUDENTGRADUATE_BY_SCHID_SEMESTER, new Object[] { schoolId, acadyear,
                semester }, new MultiRow());
    }

    public List<StudentGraduate> getStudentGraduates(String schoolId, String[] studentIds) {
        return queryForInSQL(SQL_FIND_STUDENTGRADUATE_BY_SCHID_STUIDS, new Object[] { schoolId },
                studentIds, new MultiRow());
    }

    public List<StudentGraduate> getStudentGraduates(String[] studentIds) {
        return queryForInSQL(SQL_FIND_STUDENTGRADUATE_BY_STUIDS, null, studentIds, new MultiRow());
    }

    public List<StudentGraduate> getStudentGraduates(String schoolId, String acadyear,
            String semester, String byType) {
        String sql = SQL_FIND_STUDENTGRADUATE_BY_SCHID_SEMESTER;
        Object[] objs = new Object[] { schoolId, acadyear, semester };

        if (byType != null) {
            sql += " AND bytype=?";
            objs = new Object[] { schoolId, acadyear, semester, byType };
        }
        return query(sql, objs, new MultiRow());
    }
    
    public List<StudentGraduate> getStudentGraduates(String schoolId, String acadyear,
            String semester, String byType, Pagination page) {
        String sql = SQL_FIND_STUDENTGRADUATE_BY_SCHID_SEMESTER;
        Object[] objs = new Object[] { schoolId, acadyear, semester };

        if (byType != null) {
            sql += " AND bytype=?";
            objs = new Object[] { schoolId, acadyear, semester, byType };
        }
        return query(sql, objs, new MultiRow(), page);
    }

    public List<StudentGraduate> getStudentGraduates(String classId) {
        return query(SQL_FIND_GRADUATED_STU_BY_CLSID, new Object[] { classId }, new MultiRow());
    }
    
    public List<StudentGraduate> getStudentGraduates(String[] classIds, String studentName, String studentCode, Pagination page) {
    	if(StringUtils.isNotBlank(studentCode) || StringUtils.isNotBlank(studentName)){
    		studentCode = "%" + studentCode + "%";
    		studentName = "%" + studentName + "%";
    		return queryForInSQL(SQL_FIND_GRADUATED_STU_BY_CLSIDS_STUNAME, new Object[]{studentName, studentCode}, classIds, new MultiRow(), null, page);
    	}
    	return queryForInSQL(SQL_FIND_GRADUATED_STU_BY_CLSIDS, new Object[]{}, classIds, new MultiRow(), null, page);
    }

    public boolean isExistsGraduateCode(String schoolId, String studentId, String byNumber) {
        String sql = SQL_FIND_STUDENTGRADUATE_BY_BYNUM;
        StringBuffer buf = new StringBuffer();
        if (studentId != null && !"".equals(studentId)) {
            buf.append(" AND stuid <> '");
            buf.append(studentId + "'");
        }
        if (schoolId != null && !"".equals(schoolId)) {
            buf.append(" AND schid = '");
            buf.append(schoolId);
            buf.append("'");
        }
        sql += buf.toString();
        List<StudentGraduate> list = query(sql, new Object[] { byNumber.toLowerCase() },
                new MultiRow());
        if (list != null && list.size() >= 1) {
            return true;
        } else {
            return false;
        }
    }
    public List<String[]> getStuGradueteAndClass(String schid, String acadyear) {
        return query(SQL_FIND_STUINFO_AND_CLS_BY_ACADYEAR, new Object[] { acadyear, schid, schid,
                acadyear, acadyear }, new MultiRowMapper() {
            public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new String[] { 
                		rs.getString("stuid"), 
                		rs.getString("section"), 
                		rs.getString("grade") };}
        });
    }
}
