package net.zdsoft.eis.base.data.dao;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.SchoolSemester;

/**
 * @author yanb
 * 
 */
public interface BaseSchoolSemesterDao {

    /**
     * 插入
     * 
     * @param schoolSemester
     */
    public void insertSemester(SchoolSemester schoolSemester);

    /**
     * 更新
     * 
     * @param schoolSemester
     */
    public void updateSemester(SchoolSemester schoolSemester);

    /**
     * 删除
     * 
     * @param semesterId
     */
    public void deleteSemester(String semesterId);

    /**
     * 根据学校ID，获得该学校中最新的一条学年学期记录信息，如果没有数据则返回null
     * 
     * @param String 学校ID
     * @return
     */
    public SchoolSemester getMaxSemester(String schoolId);

    /**
     * 判断新增加的学年学期与本校已存在的学年学期的日期是否存在交叉情况
     * 
     * @param schid 学校Id
     * @param acadyear 学年
     * @param semester 学期
     * @param workbegin 工作开始日期
     * @param workend 工作结束日期
     * @param semesterbegin 学期开始日期
     * @param semesterend 学期结束日期
     * @return boolean true?存在：不存在
     */
    public boolean isCrossDate(String schid, String acadyear, String semester, Date workbegin,
            Date workend, Date semesterbegin, Date semesterend);
    

    /**
     * 根据主键学年学期ID取得学年学期信息
     * 
     * @param semesterId 学年学期ID
     * @return
     */
    public SchoolSemester getSemester(String semesterId);

}
