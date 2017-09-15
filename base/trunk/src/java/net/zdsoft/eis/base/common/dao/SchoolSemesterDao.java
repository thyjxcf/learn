package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SchoolSemester;

public interface SchoolSemesterDao {

    /**
     * 根据学校ID、学年、学期获得本校的学年学期信息
     * 
     * @param schoolId 学校ID
     * @param acadyear 学年（格式为：2006-2007）
     * @param semester 学期（微代码表示，1第一学期 2第二学期）
     * @return
     */
    public SchoolSemester getSemester(String schoolId, String acadyear, String semester);

    /**
     * 根据学校编号取得学年学期entity列表,按学年倒序排序
     * 
     * @param schoolId 学校编号
     * @return list
     */
    public List<SchoolSemester> getSemesters(String schoolId);

    /**
     * 根据学校编号，取得当前时间以前的所有学年列表，按照学年倒序排列
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
     * @return List<Object>:Object[]{String,BasicSemester}
     */
    public List<Object[]> getSemesterByName(String name, String unionId);

}
