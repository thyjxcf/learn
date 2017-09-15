package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Grade;

public interface GradeDao {

    /**
     * 设置年级为毕业状态
     * 
     * @param schId
     * @param acadYear
     * @param section
     * @param schoolingLength
     */
    public void updateGraduate(String schId, String acadYear, int section, int schoolingLength);
    
    /**
     * 取年级信息
     * 
     * @param gradeId
     * @return
     */
    public Grade getGrade(String gradeId);
    
    public List<Grade> getGradesByIds(String[] gradeIds);

    /**
     * 年级信息
     * 
     * @param schoolId
     * @param acadyear
     * @param section
     * @return
     */
    public Grade getGrade(String schoolId, String acadyear, int section);

    
    /**
     * 年级列表
     * 
     * @param schoolId
     * @return
     */
    public List<Grade> getGrades(String schoolId);

    /**
     * 年级列表
     * 
     * @param schoolId
     * @param section
     * @return
     */
    public List<Grade> getGrades(String schoolId, int section);

    /**
     * 根据教师ID得到该教师是年级组长的年级列表
     * 
     * @param teacherId
     * @return
     */
    public List<Grade> getGradesByTeacherId(String teacherId);
	/**
	 * 获取学校年级map<id,grade>
	 * @param schId
	 * @return
	 */
	public Map<String, Grade> getGradeMapBySchid(String schId);
	
	public List<Grade> getBaseGradesBySchidSectionAcadyear(String schoolId, String acadyear, Integer[] section);

}
