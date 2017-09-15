package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.DateUtils;

/**
 * @author yanb
 * 
 */
public class BaseSchoolSemesterDaoImpl extends BaseDao<SchoolSemester> implements
        net.zdsoft.eis.base.data.dao.BaseSchoolSemesterDao {
    private static final String SQL_INSERT_BASICSEMESTER = "INSERT INTO stusys_semester(id,school_id,acadyear,"
            + "semester,work_begin,work_end,semester_begin,semester_end,register_date,edu_days,"
            + "class_hour,am_periods,pm_periods,night_periods,is_deleted,updatestamp) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_BASICSEMESTER_BY_ID = "UPDATE stusys_semester SET is_deleted = '1',updatestamp=? WHERE id = ?";

    private static final String SQL_UPDATE_BASICSEMESTER = "UPDATE stusys_semester SET school_id=?,acadyear=?,"
            + "semester=?,work_begin=?,work_end=?,semester_begin=?,semester_end=?,register_date=?,edu_days=?,"
            + "class_hour=?,am_periods=?,pm_periods=?,night_periods=?,is_deleted=?,updatestamp=? WHERE id=?";

    private static final String SQL_FIND_MAX_SEMESTER = "SELECT * FROM (SELECT * FROM stusys_semester WHERE school_id=? AND is_deleted = '0' "
            + "ORDER BY acadyear desc,semester desc) WHERE ROWNUM = 1";

    private static final String SQL_FIND_BASICSEMESTER_BY_ID = "SELECT * FROM stusys_semester WHERE id=?";

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
        return schoolSemester;
    }

    public void insertSemester(SchoolSemester schoolSemester) {
        schoolSemester.setId(createId());
        schoolSemester.setUpdatestamp(System.currentTimeMillis());
        schoolSemester.setIsdeleted(false);
        update(SQL_INSERT_BASICSEMESTER, new Object[] { schoolSemester.getId(),
                schoolSemester.getSchid(), schoolSemester.getAcadyear(),
                schoolSemester.getSemester(), schoolSemester.getWorkbegin(),
                schoolSemester.getWorkend(), schoolSemester.getSemesterbegin(),
                schoolSemester.getSemesterend(), schoolSemester.getRegisterdate(),
                schoolSemester.getEdudays(), schoolSemester.getClasshour(),
                schoolSemester.getAmperiods(), schoolSemester.getPmperiods(),
                schoolSemester.getNightperiods(), schoolSemester.getIsdeleted(),
                schoolSemester.getUpdatestamp() }, new int[] { Types.CHAR, Types.CHAR, Types.CHAR,
                Types.CHAR, Types.DATE, Types.DATE, Types.DATE, Types.DATE, Types.DATE,
                Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                Types.CHAR, Types.INTEGER });
    }

    public void updateSemester(SchoolSemester schoolSemester) {
        update(SQL_UPDATE_BASICSEMESTER, new Object[] { schoolSemester.getSchid(),
                schoolSemester.getAcadyear(), schoolSemester.getSemester(),
                schoolSemester.getWorkbegin(), schoolSemester.getWorkend(),
                schoolSemester.getSemesterbegin(), schoolSemester.getSemesterend(),
                schoolSemester.getRegisterdate(), schoolSemester.getEdudays(),
                schoolSemester.getClasshour(), schoolSemester.getAmperiods(),
                schoolSemester.getPmperiods(), schoolSemester.getNightperiods(),
                schoolSemester.getIsdeleted(), schoolSemester.getUpdatestamp(),
                schoolSemester.getId() }, new int[] { Types.CHAR, Types.CHAR, Types.CHAR,
                Types.DATE, Types.DATE, Types.DATE, Types.DATE, Types.DATE, Types.INTEGER,
                Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.CHAR,
                Types.INTEGER, Types.CHAR });
    }

    public void deleteSemester(String semesterId) {
        update(SQL_DELETE_BASICSEMESTER_BY_ID, new Object[] { getUpdatestamp(), semesterId });
    }

    public SchoolSemester getMaxSemester(String schoolId) {
        return (SchoolSemester) query(SQL_FIND_MAX_SEMESTER, schoolId, new SingleRow());
    }

    public boolean isCrossDate(String schid, String acadyear, String semester, Date workbegin,
            Date workend, Date semesterbegin, Date semesterend) {

        int intxn = Integer.parseInt(acadyear.substring(0, 4));
        String lastxn = String.valueOf(intxn - 1) + "-" + String.valueOf(intxn);
        String nextxn = String.valueOf(intxn + 1) + "-" + String.valueOf(intxn + 2);
        String lastxq = "2";
        String nextxq = "1";

        if (semester.equals("2")) {
            lastxn = acadyear;
            lastxq = "1";
        } else {
            nextxn = acadyear;
            nextxq = "2";
        }

        // 转换日期为字符串
        String workbeginStr = DateUtils.date2StringByDay(workbegin);
        String semesterbeginStr = DateUtils.date2StringByDay(semesterbegin);
        String workendStr = DateUtils.date2StringByDay(workend);
        String semesterendStr = DateUtils.date2StringByDay(semesterend);

        // 判断工作结束日期是否有交叉
        StringBuffer sql1 = new StringBuffer();
        sql1.append("SELECT COUNT(*) FROM stusys_semester WHERE school_id='").append(schid);
        sql1.append("' AND acadyear='").append(lastxn).append("' AND semester='").append(lastxq);
        sql1.append("' AND (to_date('").append(workbeginStr).append("','YYYY-mm-DD')-work_end)<1");
        sql1.append(" AND is_deleted = '0'");

        // 判断学期结束日期是否有交叉
        StringBuffer sql3 = new StringBuffer();
        sql3.append("SELECT COUNT(*) FROM stusys_semester WHERE school_id='").append(schid);
        sql3.append("' AND acadyear='").append(lastxn).append("' AND semester='").append(lastxq);
        sql3.append("' AND (to_date('").append(semesterbeginStr).append(
                "','YYYY-mm-DD')-semester_end)<1");
        sql3.append(" AND is_deleted = '0'");

        // 判断工作开始日期是否有交叉
        StringBuffer sql2 = new StringBuffer();
        sql2.append("SELECT COUNT(*) FROM stusys_semester WHERE school_id='").append(schid);
        sql2.append("' AND acadyear='").append(nextxn).append("' AND semester='").append(nextxq);
        sql2.append("' AND (to_date('").append(workendStr).append("','YYYY-mm-DD')-work_begin)>=0");
        sql2.append(" AND is_deleted = '0'");

        // 判断学期开始日期是否有交叉
        StringBuffer sql4 = new StringBuffer();
        sql4.append("SELECT COUNT(*) FROM stusys_semester WHERE school_id='").append(schid);
        sql4.append("' AND acadyear='").append(nextxn).append("' AND semester='").append(nextxq);
        sql4.append("' AND (to_date('").append(semesterendStr).append(
                "','YYYY-mm-DD')-semester_begin)>=0");
        sql4.append(" AND is_deleted = '0'");

        int rtn1 = queryForInt(sql1.toString());
        int rtn2 = queryForInt(sql2.toString());
        int rtn3 = queryForInt(sql3.toString());
        int rtn4 = queryForInt(sql4.toString());

        if (rtn1 > 0 || rtn2 > 0 || rtn3 > 0 || rtn4 > 0) {
            return true;
        } else {
            return false;
        }
    }

    public SchoolSemester getSemester(String semesterId) {
        return query(SQL_FIND_BASICSEMESTER_BY_ID, new String[] { semesterId }, new SingleRow());
    }
}
