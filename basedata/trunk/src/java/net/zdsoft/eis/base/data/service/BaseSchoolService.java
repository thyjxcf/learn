/**
 * 
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseSchoolService extends SchoolService {
    /**
     * 保持学校的基本信息
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
     * 
     * @param distriId
     * @return
     */
    public boolean isExistSchoolDistrict(String distriId);
    
    /**
     * 检查学校是否存在（即：base_school表中是否已经schid指定的行）
     * 
     * @param schoolId 学校ID
     * @return boolean true：存在，false：不在存
     */
    public boolean isExistSchoolCode(String schoolId);
    
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
     * 根据学校id检索列表
     * 
     * @param schoolIds
     * @return
     */
    public List<BaseSchool> getBaseSchools(String[] schoolIds);

}
