package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.SchoolDistrict;

/**
 * 
 * 学区基本信息--逻辑业务操作接口
 * 
 * @author Kobe Su,2007-5-17
 */
public interface SchoolDistrictService {

    /**
     * 根据id取得学区信息
     * 
     * @param districtId
     *            学区id
     * @return 学区对象(JwSchDistrict)
     */
    public SchoolDistrict getSchoolDistrict(String districtId);

    /**
     * 根据教育局id取得学区列表
     * 
     * @param eduId
     *            教育局id
     */
    public List<SchoolDistrict> getSchoolDistricts(String eduId);


    /**
     * 根据教育局id取得学区列表
     * 
     * @param eduId
     *            教育局id
     */
    public List<String[]> getSchoolDistrictNames(String eduId);

    /**
     * 根据教育局id取得学区map
     * 
     * @param eduId
     *            教育局id
     */
    public Map<String, String> getSchoolDistrictNameMap(String eduId);




}
