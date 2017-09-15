package net.zdsoft.eis.base.common.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SchoolSemester;

public interface SchoolSemesterService {
    /**
     * 得到当前学年学期
     * 
     * @param schoolId 学校ID
     * @return
     */
    public SchoolSemester getCurrentAcadyear(String schoolId);

    /**
     * 根据学校ID、学年、学期获得本校的学年学期信息
     * 
     * @param schoolId 学校ID
     * @param acadyear 学年（格式为：2006-2007）
     * @param semester 学期（微代码表示，1第一学期 2第二学期）
     * @return BasicSemester
     */
    public SchoolSemester getSemester(String schoolId, String acadyear, String semester);

    /**
     * 根据学校ID得到学年学期的列表（根据acadyear倒序排列）
     * 
     * @param schoolId 学校ID
     * @return List
     */
    public List<SchoolSemester> getSemesters(String schoolId);

    /**
     * 根据学校编号，取得当前学年以前的所有学年列表
     * 
     * @param schoolId 学样GUID
     * @return List
     */
    public List<String> getPreAcadyears(String schoolId);

    /**
     * 根据学校名称模糊查询学期信息
     * 
     * @param name
     * @param unionId
     * @return
     */
    public List<SchoolSemester> getSemesterByName(String name, String unionId);

}
