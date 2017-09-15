package net.zdsoft.eis.base.common.service.impl;

import java.util.List;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.SemesterDao;
import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

public class SemesterServiceImpl extends DefaultCacheManager implements SemesterService {
    private SemesterDao semesterDao;

    public void setSemesterDao(SemesterDao semesterDao) {
        this.semesterDao = semesterDao;
    }

    public String getCurrentAcadyear() {
        CurrentSemester semester = getCurrentSemester();
        if (null == semester)
            return null;
        
        return getCurrentSemester().getAcadyear();
    }
    
    public CurrentSemester getCurrentSemester() {
        return getObjectFromCache(new CacheObjectParam<CurrentSemester>() {

            public CurrentSemester fetchObject() {
                return semesterDao.getCurrentSemester();
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_SEMESTER_CURRENT;
            }
        });
    }
   //为了获取节次
    public Semester getSemesterCache(final String acadyear, final String semester) {
        return getObjectFromCache(new CacheObjectParam<Semester>() {

            public Semester fetchObject() {
                return semesterDao.getSemester(acadyear, semester);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_SEMESTER_CURRENT+acadyear+semester;
            }
        });
    }
    /**
     * 得到真实的当前学年学期信息
     * @return
     */
    public CurrentSemester getRealCurrentSemester(){
    	return semesterDao.getRealCurrentSemester();
    }

    public Semester getSemester(String acadyear, String semester) {
        return semesterDao.getSemester(acadyear, semester);
    }
    
    public List<Semester> getSemesters() {
        return semesterDao.getSemesters();
    }

    public List<String> getAcadyears() {
        return semesterDao.getAcadyears();
    }

	@Override
	public List<String> getPreAcadyears() {
		return semesterDao.getPreAcadyears();
	}

}
