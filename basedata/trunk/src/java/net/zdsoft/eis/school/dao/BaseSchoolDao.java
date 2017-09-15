package net.zdsoft.eis.school.dao;

import java.util.List;

import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.school.entity.BaseSchool;

/**
 * 
 * 
 * @author weixh
 * @since May 16, 2011
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
     * 判断学校代码是否已经存在
     * 
     * @param schid 学校ID
     * @param schCode 学校代码
     * @return boolean
     */
    public boolean isExistSchoolCode(String schid, String schCode);
    
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



}
