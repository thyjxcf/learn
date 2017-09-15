package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.dao.SchoolSemesterDao;
import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.service.SchoolSemesterService;
import net.zdsoft.eis.base.common.service.SemesterService;

public class SchoolSemesterServiceImpl implements SchoolSemesterService {

    private SchoolSemesterDao schoolSemesterDao;
    protected SemesterService semesterService;

    public void setSchoolSemesterDao(SchoolSemesterDao schoolSemesterDao) {
        this.schoolSemesterDao = schoolSemesterDao;
    }

    public void setSemesterService(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    // ===================以上为set方法=============

    public SchoolSemester getCurrentAcadyear(String schoolId) {
        CurrentSemester cur = semesterService.getCurrentSemester();
        if (cur == null)
            return null;
        
        SchoolSemester schoolSemester = getSemester(schoolId, cur.getAcadyear(), cur.getSemester());
        if(schoolSemester == null){
            Semester semester = semesterService.getSemester(cur.getAcadyear(), cur.getSemester());
            
            schoolSemester = new SchoolSemester();
            schoolSemester.setAcadyear(cur.getAcadyear());
            schoolSemester.setSemester(cur.getSemester());
            schoolSemester.setAmperiods(semester.getAmPeriods());
            schoolSemester.setPmperiods(semester.getPmPeriods());
            schoolSemester.setNightperiods(semester.getNightPeriods());
            schoolSemester.setWorkbegin(semester.getWorkBegin());
            schoolSemester.setWorkend(semester.getWorkEnd());
            schoolSemester.setSemesterbegin(semester.getSemesterBegin());
            schoolSemester.setSemesterend(semester.getSemesterEnd());            
            schoolSemester.setEdudays(semester.getEduDays());
 
        }
        return schoolSemester;
    }

    public SchoolSemester getSemester(String schoolId, String acadyear, String semester) {
        return schoolSemesterDao.getSemester(schoolId, acadyear, semester);
    }

    public List<SchoolSemester> getSemesters(String schoolId) {
        return schoolSemesterDao.getSemesters(schoolId);
    }

    public List<String> getPreAcadyears(String schoolId) {
        return schoolSemesterDao.getPreAcadyears(schoolId);
    }

    // 根据学校名称模糊查询学期信息
    public List<SchoolSemester> getSemesterByName(String name, String unionId) {
        List<Object[]> entityList = schoolSemesterDao.getSemesterByName(name, unionId);
        List<SchoolSemester> dtoList = new ArrayList<SchoolSemester>();
        Object[] obj;
        if (entityList != null && entityList.size() > 0) {
            for (int i = 0; i < entityList.size(); i++) {
                obj = (Object[]) entityList.get(i);
                String schName = (String) (obj[0]);
                SchoolSemester bse = (SchoolSemester) (obj[1]);
                bse.setName(schName);
                dtoList.add(bse);
            }
        }

        return dtoList;
    }

}
