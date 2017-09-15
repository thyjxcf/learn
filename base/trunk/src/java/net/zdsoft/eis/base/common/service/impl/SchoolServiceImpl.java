package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.SchoolDao;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.keel.util.Pagination;

public class SchoolServiceImpl extends DefaultCacheManager implements SchoolService {
    protected RegionService regionService;
    private SchoolDao schoolDao;
    private McodeService mcodeService;

    public void setMcodeService(McodeService mcodeService) {
        this.mcodeService = mcodeService;
    }

    public void setSchoolDao(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    // ========================以上为set方法============================
    
    // -------------------缓存信息 begin ------------------------
    public School getSchool(final String schoolId) {
        return getEntityFromCache(new CacheEntityParam<School>() {
            public School fetchObject() {
                School school = schoolDao.getSchool(schoolId);
                if (null == school)
                    return null;
                // 将所在行政区的6位代码转换成名称
                if ((school.getRegion() != null) && !school.getRegion().trim().equals("")) {
                    school.setRegionname(regionService.getFullNameByFullCode(school.getRegion()));
                }
                return school;
            }

            public String fetchKey() {
                return fetchCacheEntityKey() + schoolId;
            }
        });
    }
    // -------------------缓存信息 end------------------------
    
    public School getSchoolByCode(String code) {
        return schoolDao.getSchoolByCode(code);
    }

    public String getSchoolIdByCode(String code) {
        School school = getSchoolByCode(code);
        return null == school ? null : school.getId();
    }

    public int getSchoolingLen(String schoolId, int section) {
        return schoolDao.getSchoolingLen(schoolId, section);
    }

    public List<String> getSchoolIds(String parentId, String runschtype, String schoolName,
            int section) {
        return schoolDao.getSchoolIds(parentId, runschtype, schoolName, section);
    }

    public List<School> getSchools(String[] schoolIds) {
        return schoolDao.getSchools(schoolIds);
    }

    @Override
	public Map<String, School> getSchoolsById(String[] schoolIds) {
		return schoolDao.getSchoolsById(schoolIds);
	}

	public List<School> getUnderlingSchools(String districtId) {
        return schoolDao.getUnderlingSchools(districtId);
    }

    public List<School> getUnderlingSchoolsFaintness(String parentId, int section, String schName) {
        return schoolDao.getUnderlingSchoolsFaintness(parentId, section, schName);
    }

    public List<School> getAllSchoolsFaintness(String unionCode, int section, String schName) {
        return schoolDao.getAllSchoolsFaintness(unionCode, section, schName);
    }

    public Map<String, String> getSchoolTypeMap() {
        return schoolDao.getSchoolTypeMap();
    }
    
    public Map<String, School> getSchoolMap() {
    	return schoolDao.getSchoolMap();
    }

    public Map<String, School> getSchoolMapByNames(String[] names) {
        return schoolDao.getSchoolMapByNames(names);
    }

    public List<String[]> getSchoolSections(String schid) {
        School school = getSchool(schid);
        if (school == null) {
            return new ArrayList<String[]>();
        }
        String schSection = school.getSections();
        if (null == schSection) {
            return new ArrayList<String[]>();
        }
        String[] sections = schSection.split(",");

        List<String[]> retList = new ArrayList<String[]>();
        for (int i = 0; i < sections.length; i++) {
            String[] ret = new String[3];
            ret[0] = schid;
            ret[1] = sections[i];
            ret[2] = mcodeService.getMcode("DM-RKXD").get(sections[i]);
            retList.add(ret);
        }
        return retList;
    }

    public List<SubSchool> getSubSchools(String schoolId) {
        return new ArrayList<SubSchool>();
    }

    public String getSections(String schoolType) {
        return schoolDao.getSections(schoolType);
    }

	@Override
	public List<School> getBaseSchoolsByRegiontypeSections(
			String parentid, String regiontype, String section, Pagination page) {
		
		return schoolDao.getBaseSchoolsByRegiontypeSections(parentid, regiontype, section, page);
	}

	@Override
	public School getSchoolBy10Code(String code) {
		// TODO Auto-generated method stub
		return schoolDao.getSchoolBy10Code(code);
	}
}
