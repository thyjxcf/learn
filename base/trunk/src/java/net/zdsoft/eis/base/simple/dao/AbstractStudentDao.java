/* 
 * @(#)AbstractStudentDao.java    Created on May 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.simple.entity.SimpleStudent;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.QueryConditionUtils.QueryCondition;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 16, 2011 11:44:50 AM $
 */
public interface AbstractStudentDao<E extends SimpleStudent> {

    // =====================单个学生=============================
    /**
     * 根据学生主键查找学生
     * 
     * @param studentId
     * @return
     */
    public E getStudent(String studentId);

    /**
     * 根据学校ID和学籍号或学生号查询
     * 
     * @param schoolId
     * @param code 学籍号或学生号
     * @return
     */
    public E getStudentBy2Code(String schoolId, String code);

    /**
     * 根据学籍号查询
     * 
     * @param unitivecode 学籍号
     * @return StudentInfo
     */
    public E getStudentByUnitivecode(String unitivecode);

    /**
     * 根据学籍号查找学生信息（含密码）
     * 
     * @param unitivecode
     * @return
     */
    public E getStudentPwdByUnitivecode(String unitivecode);

    /**
     * 根据学校ID和学籍号查询
     * 
     * @param schoolId 学校ID
     * @param unitiveCode 学籍号
     * @return StudentInfo
     */
    public E getStudentByUnitivecode(String schoolId, String unitiveCode);

    // =====================一般查询列表=============================
    /**
     * * 根据学生id数组取得学生基本信息（不包括删除的学生）
     * 
     * @param sstudentIds
     * @return
     */
    public List<E> getStudentsByIds(String[] studentIds);
    
    /**
     * * 根据学生id数组取得学生基本信息（包括删除的学生）
     * 
     * @param sstudentIds
     * @return
     */
    public List<E> getStudentsByIdsWithDeleted(String[] studentIds);

    /**
     * 根据学校id，学号数组查询
     * 
     * @param schoolId
     * @param studentCodes
     * @return
     */
    public List<E> getStudents(String schoolId, String[] studentCodes);

    /**
     * 根据学生id数组取得学生信息
     * 
     * @param unitiveCodes
     * @return
     */
    public List<E> getStudentsByUnitiveCodes(String[] unitiveCodes);

    /**
     * 根据学生的身份证号，检索学生id信息
     * 
     * @param identityCard
     * @return
     */
    public List<E> getStudentsByIdentityCard(String[] identityCard);

    // =====================按班级查询列表=============================

    /**
     * 根据班级ID获取学生信息列表（在校生）
     * 
     * @param classId 班级ID
     * @return List
     */
    public List<E> getStudents(String classId);

    /**
     * 得到该班级的学生信息（包括在校生和离校生）
     * 
     * @param classIds
     * @return
     */
    public List<E> getAllStudents(String[] classIds);

    /**
     * 根据班级ID获取学生信息列表（学生异动，不包括借读生）
     * 
     * @param classId 班级id
     * @return
     */
    public List<E> getStudentsForAbnormalflow(String classId);

    /**
     * 得到该班级下得
     * 
     * @param classIds
     * @return
     */
    public List<E> getStudents(String[] classIds);

    /**
     * 根据班级ID获取已经毕业的学生Entity信息列表
     * 
     * @param classId 班级ID
     * @return List
     */
    public List<E> getStudentsForGraduated(String classId);

    // =====================模糊查询列表=============================
    /**
     * 模糊查询
     * 
     * @param schoolId
     * @param studentName 左匹配
     * @return
     */
    public List<E> getStudentsByFaintness(String schoolId,
            String studentName);

    /**
     * 根据学生姓名、学籍号模糊查询学生信息，取得学生id列表
     * 
     * @param schoolId
     * @param studentName
     * @param unitiveCode
     * @return List(String) 学生id列表
     */
    public List<String> getStudentsByFaintness(String schoolId,
            String studentName, String unitiveCode);
    
    
    /**
     * 学生信息
     * 
     * @param schoolId
     * @param studentName 左匹配
     * @param studentCode 左匹配
     * @return
     */
    public List<E> getStudentsByFaintnessStudentCode(String schoolId,
            String studentName, String studentCode);
    
    /**
     * 学生信息 classId为空则是本单位
     * @param schoolId
     * @param studentName
     * @param classId
     * @param page 
     * @return
     */
    public List<E> getStudentsByStudentNameClassId(
    		String schoolId,String studentName, String classId, Pagination page);
    
    /**
     * 学生信息 clsIds为空则是本单位
     * @param schId
     * @param studentName
     * @param clsIds
     * @param page
     * @return
     */
    public List<E> getStudentsByNameClsIds(String schId, String studentName, String[] clsIds, Pagination page);
    
    /**
     * 
     * @param stuname 左匹配
     * @param unionid 左匹配
     * @param unitviecode 精确查找
     * @return
     */
    public List<E> queryStudentsFaintness(String unitviecode,
            String stuname, String unionid);

    /**
     * 查找学生
     * 
     * @param field 字段
     * @param value 左匹配
     * @return
     */
    public List<E> getStudentsByField(String field, String value);

    /**
     * 根据根据条件查询学生信息列表
     * 
     * @return List 学生列表
     */
    public List<E> getStudentsByAnyConditions(List<QueryCondition> list);

    // =====================返回数值=============================
    /**
     * 取得班级人数
     * 
     * @param classId 班级ID
     * @return int
     */
    public int getStudentCount(String classId);

    /**
     * 取得指定班级的学生总人数
     * 
     * @param classIds
     * @return 班级的学生总人数
     */
    public int getStudentCount(String[] classIds);

    /**
     * 判断该学生是否属于指定的学区
     * 
     * @param schdistriId 学区id
     * @return true:属于，false:不属于
     */
    public boolean isExistsStuByDistrict(String schdistriId);

    // =====================Map=============================
    /**
     * 取得班级人数
     * 
     * @param classIds 班级ID
     * @return
     */
    public Map<String, Integer> getStudentCountMap(String[] classIds);
    
    /**
     * 班级各性别人数
     * @param classIds
     * @return key:classId+sex
     */
    public Map<String, Integer> getStudentSexCountMap(String[] classIds);

    /**
     * 根据学籍号查询
     * 
     * @param unitiveCodes 学籍号
     * @return key=unitiveCode
     */
    public Map<String, E> getStudentMapByUnitiveCodes(
            String[] unitiveCodes);

    /**
     * 取学生map
     * 
     * @param studentIds
     * @return
     */
    public Map<String, E> getStudentMap(String[] studentIds);
    
    /**
     * 取学生map(包括删除的学生)
     * 
     * @param studentIds
     * @return
     */
    public Map<String, E> getStudentMapWithDeleted(String[] studentIds);

    /**
     * 取当前在校生信息(不包含新生)
     * 
     * @param schoolId
     * @return
     */
    public Map<String, E> getStudentMapBySchoolId(String schoolId);

    /**
     * 根据班级ID获取学生信息列表（包括在校，离校全部该班级中的学生）
     * 
     * @param classId 班级ID
     * @return List
     */
    public List<E> getAllStudents(String classId);
    /**
     * 根据学生Id获取某特定证件类型的证件号
     * @param stuId
     * @param type
     * @return
     */
    public String getIdentityTypeCard(String stuId,String type);
    
    /**
     * 取出班级学生，按姓名排序
     * @param classId
     * @return
     */
    public List<E> getStudentsOrderByName(String classId);
    
    /**
	 * 通过classid过滤学生
	 * @param classId
	 * @param studentIds
	 * @return
	 */
	public List<String> filterStudentIdsByClassId(String classId, String[] studentIds);
	
	/**
     * 根据班级获取学生信息（包含在校生和毕业结业的离校生）
     * @param classId
     * @return
     */
    public List<E> getStudentsForState(String classId);
    
    /**
     * 据KinClass的部分获取同类班级的学校id-学生数Map
     * @param artScienceType
     * @param gradeCode
     * @param curAcadyear TODO
     * @return
     */
	public Map<String, Integer> getSchIdStuNumMapByKinClass(String artScienceType,
			String gradeCode, String curAcadyear);
	 /**
     * 根据班级ids获取不是团员的数据
     * @param claIds
     * @return
     */
    public Map<String,Integer> getBackgrouStuNumMapByClaIds(String[] claIds);
    /**
     * 教育局下属全部学校各学段男女生人数
     * @param unitId
     * @param unionCode
     * @return key：unit_use_type+sex
     */
	public Map<String, Integer> getUnderSchoolNumUseTypeMap(String unitId,
			String unionCode);
	
	 /**
     *教育局下属学校所有学生，各学生类别数量 
     * @param unitId
     * @param unionCode
     * @return
     */
    Map<String, Integer> getUnderSchoolNumXSLBMap(String unitId, String unionCode); 
    /**
     * 教育局下属学校各个年级学生数量
     * key:gradeCode
     * value:stuNum
     */
   Map<String,Integer> getUnderSchoolNumGradeCodeMap(String unionCode);  
    /**
     * 教育局下属全部学校各学段户口生人数
     * @param unitId
     * @param unionCode
     * @return key：unit_use_type+native_type
     */
	public Map<String, Integer> getUnderSchoolNumHkMap(String unitId,
			String unionCode);
}