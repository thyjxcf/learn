package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.keel.util.Pagination;

public interface TeacherService {
	/**
     * 包含删除教师
     * @param teacherId
     * @return
     */
    public Teacher getTeacherContainDelete(String teacherId);
	/**
     * 包含删除教师
     * @param tchIds
     * @return
     */
    public List<Teacher> getTeachersAll(String[] tchIds);
    /**
     * 根据员工主键ID得到教职工信息
     * 
     * @param teacherId 教职工id
     * @return Teacher
     */
    public Teacher getTeacher(String teacherId);
    
    /**
     * added by hexq 2010-4-1
     * 
     * 通过教师的政治面貌检索教师信息，党建系统专用
     * 注意：教职工状态除去死亡的
     * 
     * @param unitId
     * @param polities
     * @return
     */
    public List<Teacher> getTeacherListByPolity(String unitId, String[] polities);

    /**
     * 根据员工主键ID得到教职工信息和签名照片信息
     * 
     * @param teacherId 教职工id
     * @return Teacher
     */
    public Teacher getTeacherPhoto(String teacherId);
    
    /**
     * 根据单位获取员工列表(职工已设置所在部门名称)
     * 
     * @param unitId 单位id
     * @return List
     */
    public List<Teacher> getTeachers(String unitId);
    
    /**
     * 根据修改时间，获取这个时间以后修改的教师记录，包含已经删除的
     * @param unitId
     * @param dataModifyTime， 格式 yyyyMMddHHmmssSSS
     * @param page 分页信息
     * @return
     */
    public List<Teacher> getTeachersWithModifyTime(String unitId, String dataModifyTime, Pagination page);
    
    /**
     * 根据修改时间，获取这个时间以后修改的教师记录，包含已经删除的
     * @param dataModifyTime， 格式 yyyyMMddHHmmssSSS
     * @param page 分页信息
     * @return
     */
    public List<Teacher> getTeachersWithModifyTime(String dataModifyTime, Pagination page);
    
    public List<Teacher> getTeachers(String unitId,Pagination page);
    
    /**
     * 电话本调用
     * @param unitId
     * @param deptId
     * @param searchName
     * @param page
     * @return
     */
    public List<Teacher> getTeachers(String unitId, String deptId, String searchName, Pagination page);
    
    /**
     * 电话本调用(姓名完全匹配)
     * @param unitId
     * @param union_code(范围为下属及本身)
     * @param searchName
     * @param page
     * @return
     */
    public List<Teacher> getTeachersByName(String unitId, String unionCode, String searchName, Pagination page);
    /**
     * 电话本高级搜索
     * @param unitId
     * @param union_code(范围为下属及本身)
     * @param searchName
     * @param page
     * @return
     */
    public List<Teacher> getTeachersByOtherName(String unitId, String unionCode, String searchName,String type,
    		String runschtype,String unitName,String deptName,String dutyName, Pagination page);
    
    /**
     * 电话本调用(查询符合角色要求的教职工，姓名模糊匹配)
     * @param unitIds
     * @param teacherName
     * @param roleCode
     * @param page
     * @return
     */
    public List<Teacher> getTeachersByNameAndRole(String[] unitIds, String teacherName, String roleCode, Pagination page);
    
    /**
     * 根据单位和部门获取员工列表
     * 
     * @param unitId 单位id
     * @param deptId 部门id
     * @return List
     */
    public List<Teacher> getTeachers(String unitId, String deptId);
    
    /**
     * 获取部门以外的教师
     * @param deptId
     * @return
     */
    public List<Teacher> getExceptionTeachersByDeptId(String unitId,String deptId);

    /**
     * 根据keyWord查询职工列表
     * 
     * @param unitId 单位ID
     * @param name
     * 
     * @return
     */
    public List<Teacher> getTeachersByFaintness(String unitId, String name);

    /**
     * 根据keyWord查询职工列表
     * 
     * @param unitId
     * @param teacherName 左匹配
     * @param teacherCode 左匹配
     * @return
     */
    public List<Teacher> getTeachersByFaintness(String unitId, String teacherName,
            String teacherCode);
    
    /**
     * 取职工列表
     * 
     * @param teacherIds
     * @return
     */
    public List<Teacher> getTeachers(String[] teacherIds);
    
    /**
     * 取职工列表，包含已删除的
     * 
     * @param teacherIds
     * @return
     */
    public List<Teacher> getTeachersWithDeleted(String[] teacherIds);
    
    /**
     * 根据部门编号得到下属职工
     * 
     * @param deptId
     * @return
     */
    public List<Teacher> getTeachersByDeptId(String deptId);
    public List<Teacher> getTeachersByDeptId(String deptId,Pagination page);
    /**
     * 根据单位获取职工 MAP
     * 
     * @param unitId
     * @return
     */
    public Map<String, Teacher> getTeacherMap(String unitId);

    /**
     * 根据职工ids串,得到对应的职工Map
     * 
     * @param teacherIds
     * @return
     */
    public Map<String, Teacher> getTeacherMap(String[] teacherIds);
    
    /**
     * 根据职工ids串,得到对应的职工Map,包含已删除
     * 
     * @param teacherIds
     * @return
     */
    public Map<String, Teacher> getTeacherWithDeletedMap(String[] teacherIds);
    
    /**
     * 得到部门下职工数量
     * 
     * @param deptids
     * @return
     */
    public Map<String, Integer> getCountInDept(String[] deptids);
    /**
     * 根据院系id获取职工
     * @param unitId
     * @param instituteId
     * @return
     */
    public List<Teacher> getTeacherByInstituteId(String unitId, String instituteId);
    
    /**
     * 根据id数组检索，按姓名排序
     * @param tchIds
     * @return
     */
    public List<Teacher> getTeachersOrder(String[] tchIds);
    /**
     * 判断部门负责人是否本部成员
     * @param teacherId
     * @param deptId
     * @return
     */
    public boolean isExistPrincipan(String userId,String deptId);
    /**
	 * 根据学校id,教师名称code获取教师map{key=name+code，value=ent}
	 * @param 
	 * @return
	 */
	public Map<String,Teacher> getTeacherMapByUnitIdNameCodes(String unitid,String[] nameCodes);
	/**
     * 获取正常用户职工信息
     * @param unitId
     * @param page
     * @return
     */
    public List<Teacher> getTeachersByUserState(String unitId,Pagination page);
    /**
     * 获取正常用户职工信息
     * @param deptId
     * @param page
     * @return
     */
    public List<Teacher> getTeachersByDeptIdOrUserState(String deptId,Pagination page);
	
	 /**
     * 通过编制单位获取教职工信息
     * @param weaveUnitId
     * @return
     */
    public List<Teacher> getTeachersByWeaveUnit(String weaveUnitId);
    
    /**
     * 根据职务获取教职工信息
     * @param unitIds TODO
     * @param dutyCode
     * @return
     */
    public List<Teacher> findTeachersByDutyCode(String[] unitIds, String dutyCode);
	
    /**
     * 根据学科获取教职工信息
     * @param unitIds TODO
     */
	public List<Teacher> findTeachersBySubject(String[] unitIds, String subjectCode);
	
	/**
	 * 根据unitid数组获取教职工信息
	 * @param unitIds
	 * @param searchName TODO
	 * @return
	 */
	public List<Teacher> findTeachersByUnitIds(String[] unitIds, String searchName);
	
	/**
	 * 根据unitid获取该单位下任课教师 关联base_teacher_subject
	 * @param unitIds
	 * @return
	 */
	public List<Teacher> findTeacheringTeachersByUnitIds(String[] unitIds);

	/**
	 * 根据单位id，部门id，教师姓名搜索
	 * @param unitId
	 * @param deptId 可为null
	 * @param name 可为null
	 * @return
	 * @author geyy
	 * @date 2014年7月4日
	 */
	public List<Teacher> getTeachersByDeptAndName(String unitId,String deptId,String name);
	/**
     * 根据身份证号ids获取教职工列表
     * @param identitycards
     * @return
     */
	public List<Teacher> getTeacherMapByIdentityCards(String[] identitycards);
	/**
	 * 根据身份证号ids获取教职工列表
	 * @param identitycards
	 * @return
	 */
	public Map<String,Teacher> getTeacherByIdentityCards(String[] identitycards);
	/**
	 * 根据deptIds获得教职工列表
	 * @param deptIds
	 * @return
	 */
	public List<Teacher> getTeachersByDeptIds(String[] deptIds);
	
	/**
	 * 根据出生日期获取教师列表
	 * @param unitId
	 * @param day 几天内<当日至(当日+day)>
	 * @return
	 */
	public List<Teacher> getTeachersByBirthday(String unitId,int day);
	/**
	 * 获得教育局下属单位的教职工，各个学段各性别，数量
	 * @param unitId
	 * @param unionid
	 * @return key: unit_use_type+","+sex
	 */
	public Map<String, Integer> getUnionSectionSexCount(String unitId,
			String unionid);
}
