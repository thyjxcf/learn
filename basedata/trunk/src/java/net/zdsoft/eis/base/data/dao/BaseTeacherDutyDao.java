package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.data.entity.BaseTeacherDuty;
import net.zdsoft.eis.base.sync.EventSourceType;

public interface BaseTeacherDutyDao {

    /**
     * 新增教师职务
     * 
     * @param duty
     */
    public void insertTeacherDuty(BaseTeacherDuty duty);

    /**
     * 更新教师职务
     * 
     * @param duty
     */
    public void updateTeacherDuty(BaseTeacherDuty duty);

    /**
     * 批量新增教师职务
     * 
     * @param dutyList
     */
    public void insertTeacherDuties(List<BaseTeacherDuty> dutyList);
    
    /**
     * 批量更新教师职务
     * 
     * @param dutyList
     */
    public void updateTeacherDuties(List<BaseTeacherDuty> dutyList);
    
    /**
     * 根据教师ids批量删除教师职务
     * 
     * @param teacherIds
     * @param eventSource
     */
    public void deleteTeacherDutiesByTeacherIds(String[] teacherIds, EventSourceType eventSource);
    
    /**
     * 根据ids批量删除教师职务
     * @param ids
     * @param eventSource
     */
    public void deleteTeacherDutiesByIds(String[] ids, EventSourceType eventSource);

    /**
     * 根据教师id获取教师职务列表
     * 
     * @param teacherId
     * @return
     */
    public List<BaseTeacherDuty> getTeacherDutyListByTeacherId(String teacherId);

    /**
     * 根据教师ids获取教师职务列表
     * 
     * @param teacherIds
     * @return
     */
    public List<BaseTeacherDuty> getTeacherDutyListByTeacherIds(String[] teacherIds);

}
