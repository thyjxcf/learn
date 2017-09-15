package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SchoolDistrict;

/**
 * @author yanb
 * 
 */
public interface BaseSchoolDistrictDao {
    /**
     * 增加
     * 
     * @param schoolDistrict
     */
    public void insertSchoolDistrict(SchoolDistrict schoolDistrict);

    /**
     * 删除
     * 
     * @param districtIds
     */
    public void deleteSchoolDistrict(String[] districtIds);

    /**
     * 更新
     * 
     * @param schoolDistrict
     */
    public void updateSchoolDistrict(SchoolDistrict schoolDistrict);

    /**
     * 校验是否已存在相同的学区名称
     * 
     * @param eduId 学区所在教育局id
     * @param name 学区名称
     * @param id 学区id
     * @return true:表示存在;反之,表示不存在
     */
    public boolean isExistsName(String eduId, String name, String id);

    /**
     * 根据ids取得学区列表
     * 
     * @param districtIds
     * @return
     */
    public List<SchoolDistrict> getSchoolDistricts(String[] districtIds);

    /**
     * 根据教育局id取得学区编号列表
     * 
     * @param eduId 教育局id
     */
    public List<String> getSchoolDistrictCode(String eduId);

}
