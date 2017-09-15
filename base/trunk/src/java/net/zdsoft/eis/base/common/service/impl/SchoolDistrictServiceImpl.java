package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.SchoolDistrictDao;
import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.base.common.service.SchoolDistrictService;

/**
 * 
 * 学区基本信息--逻辑业务操作实现类
 * 
 * @author Kobe Su,2007-5-17
 */
public class SchoolDistrictServiceImpl implements SchoolDistrictService {

    private SchoolDistrictDao schoolDistrictDao;

    public void setSchoolDistrictDao(SchoolDistrictDao schoolDistrictDao) {
        this.schoolDistrictDao = schoolDistrictDao;
    }

    // ==========================以上是set================================

    public SchoolDistrict getSchoolDistrict(String districtId) {
        return schoolDistrictDao.getSchoolDistrict(districtId);
    }

    public List<SchoolDistrict> getSchoolDistricts(String eduId) {
        return schoolDistrictDao.getSchoolDistricts(eduId);
    }

    public List<String[]> getSchoolDistrictNames(String eduId) {
        List<String[]> rtnList = new ArrayList<String[]>();
        List<SchoolDistrict> entityList = schoolDistrictDao.getSchoolDistricts(eduId);
        for (SchoolDistrict schoolDistrict : entityList) {
            rtnList.add(new String[] { schoolDistrict.getId(), schoolDistrict.getName() });
        }
        return rtnList;
    }

    public Map<String, String> getSchoolDistrictNameMap(String eduId) {
        Map<String, String> map = new HashMap<String, String>();
        List<SchoolDistrict> entityList = schoolDistrictDao.getSchoolDistricts(eduId);
        for (Object object : entityList) {
            SchoolDistrict entity = (SchoolDistrict) object;
            map.put(entity.getId(), entity.getName());
        }
        return map;
    }

}
