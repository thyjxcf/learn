package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.base.common.service.SchoolDistrictService;

/**
 * @author yanb
 * 
 */
public interface BaseSchoolDistrictService extends SchoolDistrictService {
    /**
     * 保存一个学区
     * 
     * @param schoolDistrict 学区信息
     */
    public void insertSchoolDistrict(SchoolDistrict schoolDistrict);

    /**
     * 
     * @param schoolDistrict
     */
    public void updateSchoolDistrict(SchoolDistrict schoolDistrict);

    /**
     * 删除一个或多个学区信息
     * 
     * @param districtIds
     */
    public void deleteSchoolDistrict(String[] districtIds);

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
     * 检查该学校是否存在指定的学区
     * 
     * @param schdistriId 学区id
     * @return true:表示存在；反之,表示不存在
     */
    public boolean isExistSchoolDistrict(String schdistriId);

    /**
     * 根据学区主id数组取得学区列表
     * 
     * @param districtIds 学区主id数组
     * @return 学区列表
     */
    public List<SchoolDistrict> getSchoolDistricts(String[] districtIds);

    /**
     * 根据所属教育局id取得该学区自动增加的学区编号
     * 
     * @param eduid 教育局id
     * @return 自动增加的学区编号
     */
    public String getAutoIncreaseCode(String eduid);
}
