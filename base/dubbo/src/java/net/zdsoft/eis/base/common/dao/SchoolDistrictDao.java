package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SchoolDistrict;

/**
 * 
 * 学区基本信息--数据库访问接口
 * 
 * @author Kobe Su,2007-5-17
 */
public interface SchoolDistrictDao {

    /**
     * 根据id取得学区列表
     * 
     * @param districtId
     * @return
     */
    public SchoolDistrict getSchoolDistrict(String districtId);

    /**
     * 根据教育局id取得学区列表
     * 
     * @param eduId 教育局id
     */
    public List<SchoolDistrict> getSchoolDistricts(String eduId);

}
