package net.zdsoft.eis.base.common.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.TeacherDuty;

public interface TeacherDutyService {
    
    /**
     * 根据职务code及教师ids获取教师职务列表
     * 
     * @param teacherIds
     * @return
     */
    public List<TeacherDuty> getTeacherDutyListByTeacherIds(String dutyCode, String[] teacherIds);
    
    /**
     * 根据教师id获取教师对应的职务
     * @param teacherIds
     * @return
     */
    public List<TeacherDuty> getTeacherDutyListByTeacherIds(String[] teacherIds);

}
