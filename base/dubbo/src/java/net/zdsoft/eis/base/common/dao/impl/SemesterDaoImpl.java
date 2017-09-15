package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.basedata.remote.service.SemesterRemoteService;
import net.zdsoft.eis.base.common.dao.SemesterDao;
import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.DateUtils;

public class SemesterDaoImpl extends BaseDao<Semester> implements SemesterDao {
	
	@Autowired
	private SemesterRemoteService semesterRemoteService;
	
    private static final String SQL_FIND_SEMESTER_BY_ACADYEAR_SEMESTER = "SELECT * FROM base_semester "
            + "WHERE is_deleted=0 AND acadyear = ? AND semester = ?";

    private static final String SQL_FIND_SEMESTER_CURRENT = "SELECT acadyear,semester FROM base_semester "
            + "WHERE (to_date(?,'YYYY-mm-DD')-work_begin ) >=0 AND (to_date(?,'YYYY-mm-DD') - work_end ) <=0 AND is_deleted = 0";

    private static final String SQL_FIND_SEMESTER_CURRENT2 = "SELECT acadyear,semester FROM base_semester "
            + "WHERE (to_date(?,'YYYY-mm-DD')- 60 - work_begin ) >=0 AND (to_date(?,'YYYY-mm-DD') - 60 - work_end ) <=0 AND is_deleted = 0";

    private static final String SQL_FIND_SEMESTER_CURRENT3 = "SELECT acadyear,semester FROM base_semester "
        + "WHERE (to_date(?,'YYYY-mm-DD')- ? - work_begin ) >=0 AND (to_date(?,'YYYY-mm-DD') - ? - work_end ) <=0 AND is_deleted = 0"; 

    private static final String SQL_FIND_SEMESTERS = "SELECT * FROM base_semester WHERE is_deleted = 0 ORDER BY acadyear DESC, semester DESC";

    private static final String SQL_FIND_ACADYEAR = "SELECT DISTINCT acadyear FROM base_semester WHERE is_deleted=0 ORDER BY acadyear DESC";
    
    private static final String SQL_FIND_PRE_BASICSEMESTER = "SELECT DISTINCT acadyear FROM base_semester "
        + "WHERE is_deleted = '0' AND work_begin <= ? ORDER BY acadyear DESC";

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
        semester.setMornperiods(rs.getShort("morn_periods"));
        semester.setCreationTime(rs.getTimestamp("creation_time"));
        semester.setModifyTime(rs.getTimestamp("modify_time"));
        semester.setRegisterdate(rs.getTimestamp("register_date"));
        semester.setClasshour(rs.getShort("class_hour"));
        return semester;
    }

    public CurrentSemester getCurrentSemester() {
    	
    	String currentDay = DateUtils.currentDate2StringByDay();
    	Semester semester = Semester.dc(semesterRemoteService.findByCurrentDay(currentDay));
    	CurrentSemester currentSemester = null;
		try {
			currentSemester = mapRow(semester);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	int c = 1;
        while(currentSemester == null && c <= 50){
        	
        	Semester semester1 = Semester.dc(semesterRemoteService.findByCurrentDay(currentDay,c*60));
        	try {
				currentSemester=mapRow(semester1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         	c ++;
         }
         return currentSemester;
         
         
//        CurrentSemester semester = query(SQL_FIND_SEMESTER_CURRENT, new Object[] { currentDay,
//                currentDay }, new SingleRow2());
//        int c = 1;
//        while(semester == null && c <= 50){
//        	semester = (CurrentSemester) query(SQL_FIND_SEMESTER_CURRENT3, new Object[] {
//                    currentDay, c * 60, currentDay, c * 60 }, new SingleRow2());
//        	c ++;
//        }
//        return semester;
        
    }
    
    public CurrentSemester getRealCurrentSemester() {
    	String currentDay = DateUtils.currentDate2StringByDay();
    	Semester semester = Semester.dc(semesterRemoteService.findByCurrentDay(currentDay));
    	CurrentSemester currentSemester = null;
		try {
			currentSemester = mapRow(semester);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return currentSemester;
    	
    	
//        String currentDay = DateUtils.currentDate2StringByDay();
//        return query(SQL_FIND_SEMESTER_CURRENT, new Object[] { currentDay,
//                currentDay }, new SingleRow2());
    }

    private class SingleRow2 implements SingleRowMapper<CurrentSemester> {
        public CurrentSemester mapRow(ResultSet rs) throws SQLException {
            CurrentSemester semester = new CurrentSemester();
            semester.setAcadyear(rs.getString("acadyear"));
            semester.setSemester(rs.getString("semester"));
            return semester;
        }
    }
    
    

    public Semester getSemester(String acadyear, String semester) {
     return	Semester.dc(semesterRemoteService.findByAcadYearAndSemester(acadyear,semester));
    	
//        return (Semester) query(SQL_FIND_SEMESTER_BY_ACADYEAR_SEMESTER, new String[] { acadyear,
//                semester }, new SingleRow());
    }

    public List<Semester> getSemesters() {
    	return	Semester.dt(semesterRemoteService.findSemesters());
//        return query(SQL_FIND_SEMESTERS, new MultiRow());
    }

    public List<String> getAcadyears() {
    	List<Semester> semesters=Semester.dt(semesterRemoteService.findDistinctSemesters());
    	List<String> acadYearsList=new ArrayList<String>();
        for (Semester semester : semesters) {
        	acadYearsList.add(semester.getAcadyear());
		}
    	return acadYearsList;
//    	return query(SQL_FIND_ACADYEAR, new MultiRowMapper<String>() {
//            public String mapRow(ResultSet rs, int arg1) throws SQLException {
//                return rs.getString("acadyear");
//            }
//        });
    }
    
    @Override
	public List<String> getPreAcadyears() {
    	Date date=new Date();
    	List<Semester> semesters=Semester.dt(semesterRemoteService.findSemestersByDate(date));
    	List<String> acadYearsList=new ArrayList<String>();
        for (Semester semester : semesters) {
        	acadYearsList.add(semester.getAcadyear());
		}
    	return acadYearsList;
    	
//		return query(SQL_FIND_PRE_BASICSEMESTER, new Object[] { new Date() },
//                new MultiRowMapper<String>() {
//                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//                        return rs.getString(1);
//                    }
//                });
	}
    
    
   private CurrentSemester mapRow(Semester rs) throws SQLException {
            CurrentSemester semester = new CurrentSemester();
            semester.setAcadyear(rs.getAcadyear());
            semester.setSemester(rs.getSemester());
            return semester;
       }
    
}
