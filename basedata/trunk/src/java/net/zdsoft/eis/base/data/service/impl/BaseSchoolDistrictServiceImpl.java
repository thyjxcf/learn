package net.zdsoft.eis.base.data.service.impl;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.impl.SchoolDistrictServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseSchoolDistrictDao;
import net.zdsoft.eis.base.data.service.BaseSchoolDistrictService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;

/**
 * @author yanb
 * 
 */
public class BaseSchoolDistrictServiceImpl extends SchoolDistrictServiceImpl implements
        BaseSchoolDistrictService {
    private BaseSchoolDistrictDao baseSchoolDistrictDao;
    private BaseSchoolService baseSchoolService;
    private UnitService unitService;

    public void setBaseSchoolDistrictDao(BaseSchoolDistrictDao baseSchoolDistrictDao) {
        this.baseSchoolDistrictDao = baseSchoolDistrictDao;
    }

    public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
        this.baseSchoolService = baseSchoolService;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void insertSchoolDistrict(SchoolDistrict schoolDistrict) {
        baseSchoolDistrictDao.insertSchoolDistrict(schoolDistrict);
    }

    public void updateSchoolDistrict(SchoolDistrict schoolDistrict) {
        baseSchoolDistrictDao.updateSchoolDistrict(schoolDistrict);
    }

    public void deleteSchoolDistrict(String[] districtIds) {
        baseSchoolDistrictDao.deleteSchoolDistrict(districtIds);
    }

    public boolean isExistsName(String eduId, String name, String id) {
        return baseSchoolDistrictDao.isExistsName(eduId, name, id);
    }

    public boolean isExistSchoolDistrict(String schdistriId) {
        return baseSchoolService.isExistSchoolDistrict(schdistriId);
    }

    public List<SchoolDistrict> getSchoolDistricts(String[] districtIds) {
        return baseSchoolDistrictDao.getSchoolDistricts(districtIds);
    }

    public String getAutoIncreaseCode(String eduid) {
        List<String> list = baseSchoolDistrictDao.getSchoolDistrictCode(eduid);
        String unionid = unitService.getUnit(eduid).getUnionid();
        String rtnCode = "";
        if (list == null || list.size() < 1) {
            rtnCode = unionid + "001";
        } else {
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                String code = ((String) list.get(i)).trim();
                if (code.indexOf(unionid) >= 0) {
                    code = code.replaceAll(unionid, "");
                    int temp = Integer.parseInt(code);
                    if (temp > count) {
                        count = temp;
                    }
                }
            }

            count++;
            String countStr = String.valueOf(count);
            if (countStr.length() == 1) {
                countStr = "00" + countStr;
            } else if (countStr.length() == 2) {
                countStr = "0" + countStr;
            }
            rtnCode = unionid + countStr;
        }
        return rtnCode;
    }
}
