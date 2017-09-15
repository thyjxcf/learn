/**
 * 
 */
package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseSchoolDao {
    /**
     * 增加
     * 
     * @param school
     */
    public void insertSchool(BaseSchool school);

    /**
     * 删除
     * 
     * @param schoolId
     * @param eventSource 是否发送消息
     */
    public void deleteSchool(String schoolId, EventSourceType eventSource);

    /**
     * 更新
     * 
     * @param school
     */
    public void updateSchool(BaseSchool school);

    /**
     * 检查该学校是否存在指定的学区
     * 
     * @param schdistriId 学区id
     * @return true:表示存在；反之,表示不存在
     */
    public boolean isExistSchDistrict(String schdistriId);
    
    /**
     * 判断指定ID的学校是否存在
     * 
     * @param schid 学校ID
     * @return boolean
     */
    public boolean isExistSchoolCode(String schid);
    
    /**
     * 取学校信息
     * 
     * @param schoolId
     * @return
     */
    public BaseSchool getBaseSchool(String schoolId);
    

    /**
     * 取下属学校信息
     * 
     * @param unionCode 单位编号 左匹配
     * @param name 左右匹配
     * @return
     */
    public List<BaseSchool> getUnderlingSchoolsFaintness(String unionCode, String name);
    
    /**
     * 根据学校id数组取得学校列表
     * 
     * @param schoolIds 学校id数组
     * @return 学校列表
     */
    public List<BaseSchool> getBaseSchools(String[] schoolIds);



}
