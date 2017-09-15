package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.TeacherDuty;

/**
 * @author chens
 * @version 创建时间：2015-2-28 下午4:25:44
 * 
 */
public interface TeacherDutyDao {
	/**
     * 根据职务code及教师ids获取教师职务列表
     * @param dutyCode
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
