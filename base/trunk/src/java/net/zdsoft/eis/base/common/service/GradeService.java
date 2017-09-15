package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Grade;

public interface GradeService {
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
     * 根据年级id取得年级信息
     * 
     * @param gradeId
     * @return
     */
    public Grade getGrade(String gradeId);
    
    public List<Grade> getGradesByIds(String[] gradeIds);
    
    /**
     * 根据年级id和学年取得年级信息
     * @param gradeId
     * @param acadyear
     * @return
     */
    public Grade getGrade(String gradeId,String acadyear);

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
     * 年级信息
     * 
     * @param classId
     * @return
     */
    public Grade getGradeByClassId(String classId);

    /**
     * 取得年级名称
     * 
     * @param schoolId
     * @param acadyear
     * @param section
     * @param schoolingLength
     * @return
     */
    public String getGradeName(String schoolId, String acadyear, int section, int schoolingLength);

    /**
     * 年级信息
     * 
     * @param schoolId
     * @return
     */
    public List<Grade> getGrades(String schoolId);

    /**
     * 年级信息
     * 
     * @param schoolId
     * @param section
     * @return
     */
    public List<Grade> getGrades(String schoolId, int section);

    /**
     * 根据班级取得在用的年级列表
     * 
     * @param schoolId
     * @param acadyear 
     * @return
     */
    public List<Grade> getUsingGrades(String schoolId, String acadyear);

    /**
     * 根据教师ID得到该教师是年级组长的年级列表
     * 
     * @param teacherId
     * @return List
     */
    public List<Grade> getGradesByTeacherId(String teacherId);
	/**
	 * 获取学校年级map<id,grade>
	 * @param schId
	 * @return
	 */
	public Map<String, Grade> getGradeMapBySchid(String schId);
	/**
	 * 获取年级
	 * @param schoolId
	 * @param acadyear
	 * @param section
	 * @return
	 */
	public List<Grade> getBaseGradesBySchidSectionAcadyear(String schoolId, String acadyear, Integer[] section);
}
