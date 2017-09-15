package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.dao.SchoolSemesterDao;
import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;

public class SchoolSemesterDaoImpl extends BaseDao<SchoolSemester> implements SchoolSemesterDao {

    private static final String SQL_FIND_BY_SCHID_ACAD_SEMESTER = "SELECT * FROM stusys_semester WHERE is_deleted = '0'"
            + " AND school_id=? AND acadyear=? AND semester=? ";

    private static final String SQL_FIND_BASICSEMESTERS_BY_SCHID = "SELECT * FROM stusys_semester WHERE "
            + "is_deleted = '0' AND school_id=? ORDER BY acadyear DESC, semester ";

    private static final String SQL_FIND_PRE_BASICSEMESTER = "SELECT DISTINCT acadyear FROM stusys_semester "
            + "WHERE is_deleted = '0' AND school_id=? AND work_begin <= ? ORDER BY acadyear DESC";

    private static final String SQL_FIND_BASICSEMESTER_BY_NAME = "SELECT bc.school_name,bs.* FROM base_school bc,stusys_semester bs,base_unit u "
            + "where bc.id = bs.school_id AND bc.school_name like ? AND bc.id=u.id AND u.union_code like ? "
            + " AND bc.is_deleted = '0' AND bs.is_deleted = '0' AND u.is_deleted = '0' ORDER BY acadyear desc";

    public SchoolSemester setField(ResultSet rs) throws SQLException {
        SchoolSemester schoolSemester = new SchoolSemester();
        schoolSemester.setId(rs.getString("id"));
        schoolSemester.setSchid(rs.getString("school_id"));
        schoolSemester.setAcadyear(rs.getString("acadyear"));
        schoolSemester.setSemester(rs.getString("semester"));
        schoolSemester.setWorkbegin(rs.getTimestamp("work_begin"));
        schoolSemester.setWorkend(rs.getTimestamp("work_end"));
        schoolSemester.setSemesterbegin(rs.getTimestamp("semester_begin"));
        schoolSemester.setSemesterend(rs.getTimestamp("semester_end"));
        schoolSemester.setRegisterdate(rs.getTimestamp("register_date"));
        schoolSemester.setEdudays(rs.getShort("edu_days"));
        schoolSemester.setClasshour(rs.getShort("class_hour"));
        schoolSemester.setAmperiods(rs.getShort("am_periods"));
        schoolSemester.setPmperiods(rs.getShort("pm_periods"));
        schoolSemester.setNightperiods(rs.getShort("night_periods"));
        schoolSemester.setMornperiods(rs.getShort("morn_periods"));
        schoolSemester.setWeek(rs.getShort("week"));
        return schoolSemester;
    }

    public SchoolSemester getSemester(String schoolId, String acadyear, String semester) {
        return query(SQL_FIND_BY_SCHID_ACAD_SEMESTER,
                new String[] { schoolId, acadyear, semester }, new SingleRow());
    }

    public List<SchoolSemester> getSemesters(String schoolId) {
        return query(SQL_FIND_BASICSEMESTERS_BY_SCHID, new String[] { schoolId }, new MultiRow());
    }

    public List<String> getPreAcadyears(String schoolId) {
        return query(SQL_FIND_PRE_BASICSEMESTER, new Object[] { schoolId, new Date() },
                new MultiRowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                });
    }

    // 根据学校名称模糊查询学期信息

    public List<Object[]> getSemesterByName(String name, String unionId) {
        return query(SQL_FIND_BASICSEMESTER_BY_NAME,
                new String[] { "%" + name + "%", unionId + "%" }, new MultiRowMapper<Object[]>() {
                    public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String schoolName = rs.getString("school_name");
                        SchoolSemester bs = setField(rs);
                        Object[] objs = new Object[] { schoolName, bs };
                        return objs;
                    }
                });
    }

}
