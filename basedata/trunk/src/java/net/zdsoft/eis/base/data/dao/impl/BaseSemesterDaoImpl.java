/**
 * 
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.data.dao.BaseSemesterDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author yanb
 * 
 */
public class BaseSemesterDaoImpl extends BaseDao<Semester> implements BaseSemesterDao {
    private static final String SQL_INSERT_SEMESTER = "INSERT INTO base_semester(id,acadyear,semester,"
            + "work_begin,work_end,semester_begin,semester_end,edu_days,am_periods,pm_periods,"
            + "night_periods,creation_time,modify_time,is_deleted,register_date,class_hour,event_source) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_SEMESTER_BY_IDS = "UPDATE base_semester SET is_deleted = 1,modify_time=?,event_source=? WHERE id IN ";

    private static final String SQL_UPDATE_SEMESTER = "UPDATE base_semester SET acadyear=?,semester=?,"
            + "work_begin=?,work_end=?,semester_begin=?,semester_end=?,edu_days=?,am_periods=?,pm_periods=?,"
            + "night_periods=?,modify_time=?,is_deleted=? ,register_date=?,class_hour=?,event_source=? WHERE id=?";

    private static final String SQL_FIND_SEMESTER_BY_ID = "SELECT * FROM base_semester WHERE id=?";

    private static final String SQL_FIND_SEMESTER_MAX = "SELECT * FROM base_semester where is_deleted=0 ORDER BY acadyear DESC, semester DESC";

    private static final String SQL_FIND_SEMESTER_EXISTS_WORKBEGIN = "select count(1) from base_semester "
            + "where acadyear= ? and semester=? and (to_date(?,'YYYY-mm-DD') - work_end )<1 and is_deleted=0";

    private static final String SQL_FIND_SEMESTER_EXISTS_WORKEND = "select count(1) from base_semester "
            + "where acadyear=? and semester=? and (to_date(?,'YYYY-mm-DD') - work_begin )>=0 and is_deleted=0";

    @Override
    public Semester setField(ResultSet rs) throws SQLException {
        Semester semester = new Semester();
        semester.setId(rs.getString("id"));
        semester.setAcadyear(rs.getString("acadyear"));
        semester.setSemester(rs.getString("semester"));
        semester.setWorkBegin(rs.getTimestamp("work_begin"));
        semester.setWorkEnd(rs.getTimestamp("work_end"));
        semester.setSemesterBegin(rs.getTimestamp("semester_begin"));
        semester.setSemesterEnd(rs.getTimestamp("semester_end"));
        semester.setEduDays(rs.getShort("edu_days"));
        semester.setAmPeriods(rs.getShort("am_periods"));
        semester.setPmPeriods(rs.getShort("pm_periods"));
        semester.setNightPeriods(rs.getShort("night_periods"));
        semester.setCreationTime(rs.getTimestamp("creation_time"));
        semester.setModifyTime(rs.getTimestamp("modify_time"));
        semester.setRegisterdate(rs.getTimestamp("register_date"));
        semester.setClasshour(rs.getShort("class_hour"));
        return semester;
    }

    public void insertSemester(Semester semester) {
        if (StringUtils.isBlank(semester.getId()))
            semester.setId(createId());
        semester.setCreationTime(new Date());
        semester.setModifyTime(new Date());
        semester.setIsdeleted(false);
        update(SQL_INSERT_SEMESTER,
                new Object[] { semester.getId(), semester.getAcadyear(), semester.getSemester(),
                        semester.getWorkBegin(), semester.getWorkEnd(),
                        semester.getSemesterBegin(), semester.getSemesterEnd(),
                        semester.getEduDays(), semester.getAmPeriods(), semester.getPmPeriods(),
                        semester.getNightPeriods(), semester.getCreationTime(),
                        semester.getModifyTime(), semester.getIsdeleted(),
                        semester.getRegisterdate(), semester.getClasshour(),
                        semester.getEventSourceValue() }, new int[] { Types.CHAR, Types.CHAR,
                        Types.INTEGER, Types.DATE, Types.DATE, Types.DATE, Types.DATE,
                        Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP,
                        Types.TIMESTAMP, Types.INTEGER, Types.DATE, Types.INTEGER, Types.INTEGER });
    }

    public void deleteSemester(String[] semesterIds, EventSourceType eventSource) {
        updateForInSQL(SQL_DELETE_SEMESTER_BY_IDS, new Object[] { new Date(),
				eventSource.getValue() }, semesterIds);
    }

    public void updateSemester(Semester semester) {
        semester.setModifyTime(new Date());
        semester.setIsdeleted(false);
        update(SQL_UPDATE_SEMESTER, new Object[] { semester.getAcadyear(), semester.getSemester(),
                semester.getWorkBegin(), semester.getWorkEnd(), semester.getSemesterBegin(),
                semester.getSemesterEnd(), semester.getEduDays(), semester.getAmPeriods(),
                semester.getPmPeriods(), semester.getNightPeriods(), semester.getModifyTime(),
                semester.getIsdeleted(), semester.getRegisterdate(), semester.getClasshour(),
                semester.getEventSourceValue(), semester.getId() }, new int[] { Types.CHAR,
                Types.INTEGER, Types.DATE, Types.DATE, Types.DATE, Types.DATE, Types.INTEGER,
                Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.DATE,
                Types.INTEGER, Types.INTEGER, Types.CHAR });
    }

    public Semester getSemester(String semesterId) {
        return query(SQL_FIND_SEMESTER_BY_ID, semesterId, new SingleRow());
    }

    public Semester getMaxSemester() {
        List<Semester> list = query(SQL_FIND_SEMESTER_MAX, new MultiRow());
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public boolean isSemesterCross(String lastAcadyear, String lastSemester, String nextAcadyear,
            String nextSemester, String workBegin, String workEnd) {

        int rtn = queryForInt(SQL_FIND_SEMESTER_EXISTS_WORKBEGIN, new String[] { lastAcadyear,
                lastSemester, workBegin });
        if (rtn > 0)
            return true;

        rtn = queryForInt(SQL_FIND_SEMESTER_EXISTS_WORKEND, new String[] { nextAcadyear,
                nextSemester, workEnd });
        if (rtn > 0)
            return true;

        return false;
    }

}
