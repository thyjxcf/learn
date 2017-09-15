package net.zdsoft.eis.school.service.impl;

import java.util.List;

import net.zdsoft.eis.base.common.service.impl.SchoolServiceImpl;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.school.dao.BaseSchoolDao;
import net.zdsoft.eis.school.entity.BaseSchool;
import net.zdsoft.eis.school.service.BaseSchoolService;

import org.apache.commons.lang.StringUtils;

/**
 * 学校基本信息service接口实现类
 * 
 * @author weixh
 * @since May 16, 2011
 */
public class BaseSchoolServiceImpl extends SchoolServiceImpl implements BaseSchoolService {
    private BaseSchoolDao eisuBaseSchoolDao;
    
    private int j = 0;

	public void setEisuBaseSchoolDao(BaseSchoolDao eisuBaseSchoolDao) {
		this.eisuBaseSchoolDao = eisuBaseSchoolDao;
	}

	public void updateSchool(BaseSchool school) {
        // 单独处理学段
        String schooltype = school.getType();
        if (StringUtils.isNotBlank(schooltype)) {
            school.setSections(getSections(schooltype));
        }
        eisuBaseSchoolDao.updateSchool(school);
        // 根据维护的来源确定是否需要同步更新单位表信息 TODO
        if (school.isSendMq()) {
//            updateSchoolExt(school);
        }
        clearCache();
    }

    public void addSchool(BaseSchool school) {
        eisuBaseSchoolDao.insertSchool(school);
        clearCache();
    }

    public void deleteSchool(String schoolId, EventSourceType eventSource) {
        eisuBaseSchoolDao.deleteSchool(schoolId, eventSource);
        clearCache();
    }

    public boolean isExistSchoolCode(String schId, String schCode) {
        return eisuBaseSchoolDao.isExistSchoolCode(schId, schCode);
    }

    public BaseSchool getBaseSchool(String schoolId) {
    	j++;
    	System.out.println("now j:" + j);
        // 将所在行政区�码6位代码转换成名称
        BaseSchool school = eisuBaseSchoolDao.getBaseSchool(schoolId);
        if (school !=null && (school.getRegion() != null) && !school.getRegion().trim().equals("")) {
            school.setRegionname(regionService.getFullNameByFullCode(school.getRegion()));
        }
        return school;
    }

    public List<BaseSchool> getUnderlingSchoolsFaintness(String unionCode, String name) {
        return eisuBaseSchoolDao.getUnderlingSchoolsFaintness(unionCode, name);
    }

}
