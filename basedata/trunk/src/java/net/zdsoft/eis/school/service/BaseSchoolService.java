package net.zdsoft.eis.school.service;

import java.util.List;

import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.school.entity.BaseSchool;

/**
 * 
 * 
 * @author weixh
 * @since May 16, 2011
 */
public interface BaseSchoolService extends SchoolService {
    /**
     * 更新学校的基本信息
     * 
     * @param school
     */
    public void updateSchool(BaseSchool school);

    /**
     * 保存
     * 
     * @param school
     */
    public void addSchool(BaseSchool school);

    /**
     * 删除
     * 
     * @param schoolId
     * @param eventSource 是否发送消息
     */
    public void deleteSchool(String schoolId, EventSourceType eventSource);
    
    /**
     * 检查学校代码是否已经存在
     * 
     * @param schId 学校ID
     * @param schCode 学校代码
     * @return boolean true：存在，false：不在存
     */
    public boolean isExistSchoolCode(String schId, String schCode);
    
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
     * @param unionCode 单位编号 左匹�配
     * @param name 左右匹配
     * @return
     */
    public List<BaseSchool> getUnderlingSchoolsFaintness(String unionCode, String name);


}
