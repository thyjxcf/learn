package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.KinClass;
import net.zdsoft.eis.base.simple.dao.AbstractClassDao;

public interface BasicClassDao extends AbstractClassDao<BasicClass> {
	/**
	 * 毕业标志
	 * 
	 * @param classId
	 * @param sign
	 */
	public void updateGraduateSign(String classId, int sign);
	public void updateGraduateSign(String[] classIds, int sign);
	/**
	 * 根据班级编号组取得所有班级信息
	 * 
	 * @param classIds
	 * @return
	 */
	public List<BasicClass> getClasses(String[] classIds);

	// ======================按(副)班主任teacherId查询班级，权限使用=======================
	/**
	 * 根据老师ID，得到班主任是该老师的班级列表（去除软删除和已毕业的班级），包括副班主任的情况
	 * 
	 * @param teacherId
	 * @return List
	 */
	public List<BasicClass> getClassesByTeacherId(String teacherId);

	/**
	 * 根据分校区ID和教师ID得到班级列表 （去除软删除和已毕业的班级），包括副班主任的情况
	 * 
	 * @param campusId
	 *            分校区ID
	 * @param teacherId
	 *            教师ID
	 * @return List
	 */
	public List<BasicClass> getClassesByTeacherId(String campusId,String teacherId);

	/**
	 * 根据教师ID和毕业学年得到毕业班列表（去除软删除），包括副班主任的情况
	 * 
	 * @param teacherId 教师ID
	 * @param graduateAcadyear 毕业学期（格式为：2005-2006）
	 * @return List
	  */
	  	public List<BasicClass> getGraduatingClassesByTeacherId(String teacherId,String graduateAcadyear);
		
		
		
		
		
		
		
		
		
		
		
		
	/**
	 * 根据分校区ID、教师ID和毕业学年得到毕业班列表（去除软删除），包括副班主任的情况
	 * 
	 * @param campusId
	 *            分校区ID
	 * @param teacherId
	 *            教师ID
	 * @param graduateAcadyear
	 *            毕业学年
	 * @return List
	 */
	public List<BasicClass> getGraduatingClassesByTeacherId(String campusId,
			String teacherId, String graduateAcadyear);

	/**
	 * 根据教师ID和毕业学年得到已经毕业的班级列表（去除软删除），包括副班主任的情况
	 * 
	 * @param teacheId
	 *            教师ID
	 * @param graduateAcadyear
	 *            毕业学期（格式为：2005-2006）
	 * @return List
	 */
	public List<BasicClass> getGraduatedClassesByTeacherId(String teacheId,
			String graduateAcadyear);

	/**
	 * 根据分校区ID、教师ID和毕业学年得到已经毕业的班级列表（去除软删除），包括副班主任的情况
	 * 
	 * @param campusId
	 *            分校区ID
	 * @param teacherId
	 *            教师ID
	 * @param graduateAcadyear
	 *            毕业学年
	 * @return List
	 */
	public List<BasicClass> getGraduatedClassesByTeacherId(String campusId,
			String teacherId, String graduateAcadyear);

	// ======================按年级组长teacherId查询班级，权限使用=======================
	/**
	 * 根据年级组长id、分校区id，得到年级组长所在年级的班级列表
	 * 
	 * @param gradeTeacherId
	 *            年级组长id
	 * @param campusId
	 *            分校区id
	 * @return List
	 */
	public List<BasicClass> getClassesByGradeTeacherId(String gradeTeacherId,
			String campusId);

	/**
	 * 根据教师id，得到年级组长或班主任或副班主任的班级列表
	 * 
	 * @param teacherId
	 *            教师id
	 * @return List
	 */
	public List<BasicClass> getClassesByGradeTeacherIdOrClassTeacherId(
			String teacherId, String campusId);

	/**
	 * 根据年级组长id、分校区id、毕业学年，得到年级组长所在年级的毕业班级列表
	 * 
	 * @param gradeTeacherId
	 * @param campusId
	 * @param graduateAcadyear
	 * @return
	 */
	public List<BasicClass> getGraduatingClassesByGradeTeacherId(
			String gradeTeacherId, String campusId, String graduateAcadyear);

	/**
	 * 根据年级组长id、分校区id、毕业学年，得到年级组长所在年级的已毕业班级列表
	 * 
	 * @param gradeTeacherId
	 * @param campusId
	 * @param graduateAcadyear
	 * @return
	 */
	public List<BasicClass> getGraduatedClassesByGradeTeacherId(
			String gradeTeacherId, String campusId, String graduateAcadyear);

	// ======================按schoolId查询班级=======================

	/**
	 * 班级列表(包括已毕业的班级)，自定义报表使用
	 * 
	 * @param schoolId
	 */
	public List<BasicClass> getAllClasses(String schoolId);

	public List<BasicClass> getClassesByOpenAcadyear(String schoolId,
			String openAcadyear);
	
	public List<BasicClass> getClassesByGradeId(String schoolId,
			String gradeId, String teacherId);

	/**
	 * 取班级毕业年份 取得的list中第一个字段为班级id 第二个为毕业年份
	 * 
	 * @param schoolId
	 *            学校id
	 * @return <classId,graduateYear>
	 */
	public Map<String, String> getGraduateYearMap(String schoolId);

	/**
	 * 根据学校GUID、学段、入学学年和学制得到班级列表
	 * 
	 * @param schoolId
	 *            学校GUID
	 * @param section
	 *            学段
	 * @param enrollyear
	 *            入学学年
	 * @param schoolingLen
	 *            学制
	 * @return List
	 */
	public List<BasicClass> getClasses(String schoolId, int section,
			String enrollyear, int schoolingLen);

	/**
	 * 根据学校ID和指定学年（非入学学年）得到有效的班级列表
	 * 
	 * @param schoolId
	 *            学校GUID
	 * @param curAcadyear
	 *            指定学年（非入学学年）（格式：2005-2006）
	 * @return List
	 */
	public List<BasicClass> getClasses(String schoolId, String curAcadyear);
    /**
     * 根据学校ID和指定学年（非入学学年）得到有效的班级列表
     * 
     * @param schoolId 学校GUID
     * @param curAcadyear 指定学年（非入学学年）（格式：2005-2006）
     * @param section 学段
     * @return List
     */
    public List<BasicClass> getClassesBy(String schoolId, String curAcadyear, int section);

	/**
	 * 得到学校中，在当前学年超过学制而还没有毕业的班级列表
	 * 
	 * @param schoolId
	 *            学校ID
	 * @param curAcadyear
	 *            当前学年
	 * @return List
	 */
	public List<BasicClass> getOverSchoolinglenClasses(String schoolId,
			String curAcadyear);

	/**
	 * 根据学校，毕业学年得到毕业班列表（即：指定学年内的毕业班列表，不论毕业标志是0还是1）
	 * 
	 * @param schoolId
	 * @param graduateAcadyear
	 * @return List
	 */
	public List<BasicClass> getGraduatingClasses(String schoolId,
			String graduateAcadyear);

	/**
	 * 根据学校，毕业学年得到已经毕业了的班级（即：指定学年内毕业标志为1的班级列表）
	 * 
	 * @param schoolId
	 * @param acadyear
	 * @return List
	 */
	public List<BasicClass> getGraduatedClasses(String schoolId,
			String graduateAcadyear);

	// ======================按campusId查询班级=======================
	/**
	 * 根据校区ID获得班级信息列表，并按所属学段、入学学年、班级编码分别升序排序
	 * 
	 * @param campusId
	 *            校区ID
	 * @return List
	 */
	public List<BasicClass> getClassesByCampusId(String campusId);

	/**
	 * 根据分校区ID和毕业学年得到毕业班列表（即：指定分校区的毕业班列表，不论毕业标志是0还是1）
	 * 
	 * @param campusId
	 * @param graduateAcadyear
	 * @return List
	 */
	public List<BasicClass> getGraduatingClassesByCampusId(String campusId,
			String graduateAcadyear);

	/**
	 * 根据分校区ID和毕业学年得到毕业班列表（即：指定学年内毕业标志为1的班级列表）
	 * 
	 * @param campusId
	 * @param graduateAcadyear
	 * @return List
	 */
	public List<BasicClass> getGraduatedClassesByCampusId(String campusId,
			String graduateAcadyear);

	// ======================根据班级查询年级=======================

	/**
	 * 根指指定的学校ID得到班级中属于同一个年级的班级部分字段列表
	 * 
	 * @param schoolId
	 *            学校GUID
	 * @return List
	 */
	public List<Grade> getGrades(String schoolId);

	/**
	 * 根指指定的学校ID和指定学期得到班级中属于同一个年级的班级部分字段列表
	 * 
	 * @param schoolId
	 *            学校GUID
	 * @return List
	 */
	public List<Grade> getGrades(String schoolId, String curAcadyear);

	/**
	 * 根指指定的学校ID和指定学期得到班级中属于同一个年级的班级部分字段列表
	 * 
	 * @param schoolId
	 *            学校GUID
	 * @return List
	 */
	public List<Grade> getGraduatingGrades(String schoolId,
			String graduateAcadyear);
	/**
     * 根据学校，学年得到当年非毕业的年级列表 不包括当前期年度毕业班级
     * 
     * @param schoolId 学校ID
     * @param acadyear 学年（格式为：2005-2006）
     * @return List
     */
    public List<Grade> getNotGraduatingGrades(String schoolId, String acadyear);

	/**
	 * 根指指定的学校ID和班主任ID得到由该班主任任教班级中属于同一个年级的班级部分字段列表
	 * 
	 * @param schoolId
	 *            学校GUID
	 * @param teacherId
	 *            班主任GUID
	 * @return List
	 */
	public List<Grade> getGradesByTeacherId(String schoolId, String teacherId);

	// ======================根据年级查询班级=======================

	/**
	 * 得到同一个年级下的班级表列（无分校区）
	 * 
	 * @param schoolId
	 * @param section
	 * @param enrollyear
	 * @param schoolingLen
	 * @return
	 */
	public List<BasicClass> getClassesByGrade(String schoolId, int section,
			String enrollyear, int schoolingLen);

	/**
	 * 得到同一个年级下的班级列表（有分校区）
	 * 
	 * @param schoolId
	 * @param campusId
	 * @param section
	 * @param enrollyear
	 * @param schoolingLen
	 * @return List
	 */
	public List<BasicClass> getClassesByGrade(String schoolId, String campusId,
			int section, String enrollyear, int schoolingLen);

	// ======================根据同类班级查询班级=======================
	/**
	 * 根指指定的学校ID得到班级中属于同一类型班级的列表
	 * 
	 * @param schoolId
	 *            学校GUID
	 * @return List
	 */
	public List<KinClass> getKinClasses(String schoolId);

	/**
	 * 根据学校、学段、入学学年取得不重复的某年级班级文理类型
	 * 
	 * @param schoolId
	 *            学校id
	 * @param section
	 *            学段
	 * @param acadyear
	 *            入学学年
	 * @return List(artsciencetype)
	 */
	public List<String> getClassType(String schoolId, int section,
			String acadyear);

	/**
	 * 根据学校、学段、入学学年取得班级
	 * 
	 * @param schoolId
	 *            学校id
	 * @param section
	 *            学段
	 * @param enrollyear
	 *            入学学年
	 * @param artScienceType
	 *            文理类型
	 * @return List<BasicClass>
	 */
	public List<BasicClass> getClassesByKinClass(String schoolId, int section,
			String enrollyear, String artScienceType);

	/**
	 * 得到同一个年级下相同文理类型的班级表列（无分校区）
	 * 
	 * @param schoolId
	 * @param section
	 * @param enrollyear
	 * @param schoolingLen
	 * @param artScienceType
	 * @return
	 */
	public List<BasicClass> getClassesByKinClass(String schoolId, int section,
			String enrollyear, int schoolingLen, String artScienceType);

	/**
	 * 得到同一个年级下相同文理类型的班级列表（有分校区）
	 * 
	 * @param schoolId
	 * @param campusId
	 * @param section
	 * @param enrollyear
	 * @param schoolingLen
	 * @return List
	 */
	public List<BasicClass> getClassesByKinClass(String schoolId,
			String campusId, int section, String enrollyear, int schoolingLen,
			String artScienceType);

	// ======================Map=======================

	/**
	 * 根据学号取得所在班级gradename
	 * 
	 * @param unitiveCode
	 * @return
	 */
	public Map<String, BasicClass> getClassMapByUnitivecodes(
			String[] unitiveCode);

	/**
	 * 根据用户id和校区id取出该用户在该校区包含的班级的编号
	 * 
	 * @param userid
	 *            用户id
	 * @param subschoolid
	 *            校区id
	 * @return
	 */
	public String[] getClassidByUserid(String userid, String subschoolid);
    /**
     * 根据学校id和年级id获取班级列表
     * @param schoolId
     * @param gradeId
     * @return
     */
    public List<BasicClass> getClassesBySchoolIdGradeId(String schoolId,String gradeId);
    
    
    public List<BasicClass> getClassesByGradeId(String gradeId);
    
    public List<BasicClass> getClassesByGradeIdAll(String gradeId);
    
	public List<BasicClass> getClassesByGradeIds(String schoolId, String[] gradeIds);
	
	public List<BasicClass> getClassesByTeacherIdAll(String schoolId, String teacherId);
}
