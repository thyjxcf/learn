/**
 * 
 */
package net.zdsoft.eis.base.data.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.impl.SchoolServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseSchoolDao;
import net.zdsoft.eis.base.data.dao.BaseSubSchoolDao;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseSchoolSemesterService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.subsystemcall.service.StusysSubsystemService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.keel.util.DateUtils;

/**
 * @author yanb
 * 
 */
public class BaseSchoolServiceImpl extends SchoolServiceImpl implements BaseSchoolService {
    private BaseSchoolDao baseSchoolDao;
    private BaseSubSchoolDao baseSubSchoolDao;
    private SemesterService semesterService;
    private BaseUnitService baseUnitService;
    private BaseSchoolSemesterService baseSchoolSemesterService;
    private StusysSubsystemService stusysSubsystemService;

    public void setStusysSubsystemService(StusysSubsystemService stusysSubsystemService) {
        this.stusysSubsystemService = stusysSubsystemService;
    }

    public void setBaseSchoolDao(BaseSchoolDao baseSchoolDao) {
        this.baseSchoolDao = baseSchoolDao;
    }

    public void setBaseSchoolSemesterService(BaseSchoolSemesterService baseSchoolSemesterService) {
        this.baseSchoolSemesterService = baseSchoolSemesterService;
    }

    public void setBaseUnitService(BaseUnitService baseUnitService) {
        this.baseUnitService = baseUnitService;
    }

    public void setSemesterService(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    public void setBaseSubSchoolDao(BaseSubSchoolDao baseSubSchoolDao) {
        this.baseSubSchoolDao = baseSubSchoolDao;
    }

    public void updateSchool(BaseSchool school) {
        if (school.getId() != null && !("".equals(school.getId()))
                && school.getSynchroSchDistrict() != null
                && school.getSynchroSchDistrict().equals("synchroSchDistrict")) {// 表示要同步学生的应服务学区
            BaseSchool entity = getBaseSchool(school.getId());
            String formerXQId = entity.getSchdistrictid();
            stusysSubsystemService.updateSchDistrict(formerXQId, school.getSchdistrictid(), school
                    .getId());
        }
        // 单独处理学段
        String schooltype = school.getType();
        if (StringUtils.isNotBlank(schooltype)) {
            school.setSections(getSections(schooltype));
        }
        baseSchoolDao.updateSchool(school);
        // 根据维护的来源确定是否需要同步更新单位表信息
        if (school.isSendMq()) {
            updateSchoolExt(school);
        }
        // 检查有无分校区，若没有则添加默认同名的分校区
        if (school.getId() != null && !("".equals(school.getId()))) {
            List<SubSchool> subschList = baseSubSchoolDao.getSubSchools(school.getId());
            if (subschList == null || subschList.size() == 0) {
                SubSchool subschEntity = new SubSchool();
                subschEntity.setSchid(school.getId());
                subschEntity.setAddress(school.getAddress());
                // 因为现在没有教学区的设置，所以默认名字先写死，“校区”
                subschEntity.setName("主校区");
                baseSubSchoolDao.insertSubSchool(subschEntity);
            }
        }
        clearCache();
    }

    private void updateSchoolExt(BaseSchool school) {
        // 同步更新单位信息
        BaseUnit unit = baseUnitService.getBaseUnit(school.getId());
        unit.setAddress(school.getAddress());
        unit.setPostalcode(school.getPostalcode());
        unit.setLinkPhone(school.getLinkphone());
        unit.setFax(school.getFax());
        unit.setEmail(school.getEmail());
        unit.setHomepage(school.getHomepage());
        baseUnitService.updateUnit(unit);
    }

    public void addSchool(BaseSchool school) {
        baseSchoolDao.insertSchool(school);
        // 初始化学校学年学期信息
        List<Semester> list = semesterService.getSemesters();
        for (int i = 0; i < list.size(); i++) {
            Semester s = (Semester) list.get(i);
            SchoolSemester b = new SchoolSemester();
            b.setSchid(school.getId());
            b.setAcadyear(s.getAcadyear());
            b.setSemester(s.getSemester());
            b.setWorkbegin(s.getWorkBegin());
            b.setWorkend(s.getWorkEnd());
            b.setSemesterbegin(s.getSemesterBegin());
            b.setSemesterend(s.getSemesterEnd());
            if (s.getRegisterdate() == null)
                b.setRegisterdate(DateUtils.addDay(s.getSemesterBegin(), 1));
            else
                b.setRegisterdate(s.getRegisterdate());
            b.setClasshour(s.getClasshour());
            b.setEdudays(s.getEduDays());
            b.setAmperiods(s.getAmPeriods());
            b.setPmperiods(s.getPmPeriods());
            b.setNightperiods(s.getNightPeriods());
            baseSchoolSemesterService.insertSemester(b);
        }
        // 检查有无分校区，若没有则添加默认同名的分校区
        if (school.getId() != null && !("".equals(school.getId()))) {
            List<SubSchool> subschList = baseSubSchoolDao.getSubSchools(school.getId());
            if (subschList == null || subschList.size() == 0) {
                SubSchool subschEntity = new SubSchool();
                subschEntity.setSchid(school.getId());
                subschEntity.setAddress(school.getAddress());
                // 因为现在没有教学区的设置，所以默认名字先写死，“校区”
                subschEntity.setName("主校区");
                baseSubSchoolDao.insertSubSchool(subschEntity);
            }
        }
        clearCache();
    }

    public void deleteSchool(String schoolId, EventSourceType eventSource) {
        baseSchoolDao.deleteSchool(schoolId, eventSource);
        clearCache();
    }

    public boolean isExistSchoolDistrict(String distriId) {
        return baseSchoolDao.isExistSchDistrict(distriId);
    }

    public boolean isExistSchoolCode(String schoolId) {
        return baseSchoolDao.isExistSchoolCode(schoolId);
    }

    public BaseSchool getBaseSchool(String schoolId) {
        // 将所在行政区的6位代码转换成名称
        BaseSchool school = baseSchoolDao.getBaseSchool(schoolId);
        if (null == school)
            return null;

        if ((school.getRegion() != null) && !school.getRegion().trim().equals("")) {
            school.setRegionname(regionService.getFullNameByFullCode(school.getRegion()));
        }
        return school;
    }

    public List<BaseSchool> getUnderlingSchoolsFaintness(String unionCode, String name) {
        return baseSchoolDao.getUnderlingSchoolsFaintness(unionCode, name);
    }
    
    public List<BaseSchool> getBaseSchools(String[] schoolIds) {
        return baseSchoolDao.getBaseSchools(schoolIds);
    }

}
