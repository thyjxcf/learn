package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.KinClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.simple.service.AbstractClassService;

public interface BasicClassService extends AbstractClassService<BasicClass> {
    // ======================维护=======================
    /**
     * 毕业标志
     * 
     * @param classId
     * @param sign
     */
    public void updateGraduateSign(String classId, int sign);
    public void updateGraduateSign(String[] classIds, int sign);
    // ======================单个对象=======================
    /**
     * 根据班级GUID取得班级的信息
     * 
     * @param classId 班级GUID
     * @return
     */
    public BasicClass getClass(String classId, String currentAcadyear);

    // ======================判断时使用=======================

    /**
     * 根据班级编号组取得所有班级信息
     * 
     * @param classIds
     * @return
     */
    public List<BasicClass> getClasses(String[] classIds);

    /**
     * 根据班级编号组取得所有班级信息
     * 
     * @param classIds
     * @param curAcadYear
     * @return
     */
    public List<BasicClass> getClasses(String[] classIds, String curAcadYear);

    /**
     * 根据班级编号组取得所有班级信息的map
     * 
     * @param classIds
     * @param curAcadyear 当前学年
     * @return
     */
    public Map<String, BasicClass> getClassMap(String[] classIds, String curAcadyear);
    
    

    // ======================按(副)班主任teacherId查询班级，权限使用=======================
    /**
     * 根据教师ID取班级列表，包括副班主任的情况
     * 
     * @param teacherId 教师ID
     * @return List
     */
    public List<BasicClass> getClassesByTeacherId(String teacherId);

    /**
     * 根据教师ID和分校区ID得到班级列表，包括副班主任的情况
     * 
     * @param campusId 分校区ID
     * @param teacherId 教师ID
     * 
     * @return List
     */
    public List<BasicClass> getClassesByTeacherId(String campusId, String teacherId);

    /**
     * 根据教师ID和毕业学年得到指定学年的已经毕业的班级列表，包括副班主任的情况，包括副班主任的情况
     * 
     * @param teacherId 教师ID
     * @param graduateAcadyear 毕业学年
     * @return List
     */
    public List<BasicClass> getGraduatingClassesByTeacherId(String teacherId,
            String graduateAcadyear);

    /**
     * 根据分校区ID，教师ID和毕业学年得到班主任有权操作的指定学年毕业班列表，包括副班主任的情况
     * 
     * @param teacherId 教师ID
     * @param campusId 分校区ID
     * @param graduateAcadyear 毕业学年
     * @return List
     */
    public List<BasicClass> getGraduatingClassesByTeacherId(String campusId, String teacherId,
            String graduateAcadyear);

    /**
     * 根据分校区ID，教师ID和毕业学年得到班主任有权操作的指定学年已经毕业的班级列表，包括副班主任的情况
     * 
     * @param teacherId 教师ID
     * @param campusId 分校区ID
     * @param graduateAcadyear 毕业学年
     * @return List
     */
    public List<BasicClass> getGraduatedClassesByTeacherId(String campusId, String teacherId,
            String graduateAcadyear);

    /**
     * 根据教师ID和毕业学年得到指定学年的毕业班列表，包括副班主任的情况
     * 
     * @param teacherId 教师ID
     * @param graduateAcadyear 毕业学年
     * @return List
     */
    public List<BasicClass> getGraduatedClassesByTeacherId(String teacherId, String graduateAcadyear);

    /**
     * 根据学校取班级信息
     * 
     * @param schoolId
     * @return 以（副）班主任为key
     */
    public Map<String, List<BasicClass>> getClassesMapKeyTeacherId(String schoolId);

    // ======================按年级组长teacherId查询班级，权限使用=======================

    /**
     * 根据年级组长id，得到年级组长所在年级的班级列表
     * 
     * @param gradeTeacherId 年级组长id
     * @return List
     */
    public List<BasicClass> getClassesByGradeTeacherId(String gradeTeacherId);
    
    /**
     * 根据教师id，得到年级组长或班主任或副班主任的班级列表
     * 
     * @param teacherId 教师id
     * @return List
     */
    public List<BasicClass> getClassesByGradeTeacherIdOrClassTeacherId(String teacherId);

    /**
     * 根据年级组长id、分校区id，得到年级组长所在年级的班级列表
     * 
     * @param gradeTeacherId 年级组长id
     * @param campusId 分校区id
     * @return List
     */
    public List<BasicClass> getClassesByGradeTeacherId(String gradeTeacherId, String campusId);

    /**
     * 根据年级组长id、毕业学年，得到年级组长所在年级的毕业班级列表
     * 
     * @param gradeTeacherId
     * @param graduateAcadyear
     * @return
     */
    public List<BasicClass> getGraduatingClassesByGradeTeacherId(String gradeTeacherId,
            String graduateAcadyear);

    /**
     * 根据年级组长id、分校区id、毕业学年，得到年级组长所在年级的毕业班级列表
     * 
     * @param gradeTeacherId
     * @param campusId
     * @param graduateAcadyear
     * @return
     */
    public List<BasicClass> getGraduatingClassesByGradeTeacherId(String gradeTeacherId,
            String campusId, String graduateAcadyear);

    /**
     * 根据年级组长id、毕业学年，得到年级组长所在年级的已毕业班级列表
     * 
     * @param gradeTeacherId
     * @param graduateAcadyear
     * @return
     */
    public List<BasicClass> getGraduatedClassesByGradeTeacherId(String gradeTeacherId,
            String graduateAcadyear);

    /**
     * 根据年级组长id、分校区id、毕业学年，得到年级组长所在年级的已毕业班级列表
     * 
     * @param gradeTeacherId
     * @param campusId
     * @param graduateAcadyear
     * @return
     */
    public List<BasicClass> getGraduatedClassesByGradeTeacherId(String gradeTeacherId,
            String campusId, String graduateAcadyear);

    // ======================按schoolId查询班级=======================

    /**
     * 根据学校ID得到班级列表，包括毕业班级
     * 
     * @param schoolId 学校ID
     * @return List
     */
    public List<BasicClass> getAllClasses(String schoolId);

    /**
     * 根据学校ID和学段得到班级列表
     * 
     * @param schoolId 学校ID
     * @param section 学段
     * @return List
     */
    public List<BasicClass> getClasses(String schoolId, int section);

    /**
     * 根据学校GUID、学段和入学学年得到班级列表
     * 
     * @param schoolId 学校GUID
     * @param section 学段
     * @param enrollyear 入学学年
     * @return List
     */
    public List<BasicClass> getClasses(String schoolId, int section, String enrollyear);

    /**
     * 根据学校GUID、学段、入学学年和年制得到班级列表
     * 
     * @param schoolId 学校GUID
     * @param section 学段
     * @param enrollyear 入学学年
     * @param schoolingLen 年制
     * @return List
     */
    public List<BasicClass> getClasses(String schoolId, int section, String enrollyear,
            int schoolingLen);

    /**
     * 根据学校ID和指定学年（非入学学年）得到班级列表
     * 
     * @param schoolId 学校GUID
     * @param curAcadyear 指定学年（非入学学年）（格式：2005-2006）
     * @return List
     */
    public List<BasicClass> getClasses(String schoolId, String curAcadyear);

    /**
     * 得到指定学校的当前学年中，超过学制而没有毕业的班级列表
     * 
     * @param schoolId 学校ID
     * @param curAcadyear 当前学年
     * @return List
     */
    public List<BasicClass> getOverSchoolinglenClasses(String schoolId, String curAcadyear);

    /**
     * 根据学校，毕业学年得到毕业班列表（即：指定学年内的毕业班列表，不论毕业标志是0还是1）
     * 
     * @param schoolId 学校ID
     * @param acadyear 学年（格式为：2005-2006）
     * @return List
     */
    public List<BasicClass> getGraduatingClasses(String schoolId, String graduateAcadyear);

    /**
     * 根据学校，毕业学年得到已经毕业了的班级（即：指定学年内毕业标志为1的班级列表）
     * 
     * @param schoolId 学校ID
     * @param acadyear 学年（格式为：2005-2006）
     * @return List
     */
    public List<BasicClass> getGraduatedClasses(String schoolId, String graduateAcadyear);

    // ======================按campusId查询班级=======================
    /**
     * 根据分校区ID得到该分校区中的班级列表，并接所属学段、入学学年、班级编码分别升序排序
     * 
     * @param campusId 分校区ID
     * @return List
     */
    public List<BasicClass> getClassesByCampusId(String campusId);

    /**
     * 根据分校区，毕业学年得到毕业班列表（即：指定学年内的毕业班列表，不论毕业标志是0还是1）
     * 
     * @param campusId
     * @param graduateAcadyear
     * @return List
     */
    public List<BasicClass> getGraduatingClassesByCampusId(String campusId, String graduateAcadyear);

    /**
     * 根据学校，毕业学年得到已经毕业了的班级（即：指定学年内毕业标志为1的班级列表）
     * 
     * @param campusId 学校ID
     * @param graduateAcadyear
     * @return List
     */
    public List<BasicClass> getGraduatedClassesByCampusId(String campusId, String graduateAcadyear);

    // ======================根据班级查询年级=======================

    /**
     * 根据学校得到年级表列
     * 
     * @param schoolId
     * @return List
     */
    public List<Grade> getGrades(String schoolId);

    /**
     * 年级列表
     * 
     * @param schoolId 学校GUID
     * @param curAcadyear 指定学年，非入学学年
     * @return List
     */
    public List<Grade> getGrades(String schoolId, String curAcadyear);

    /**
     * 根据学校，毕业学年得到将要毕业的年级列表
     * 
     * @param schoolId 学校ID
     * @param acadyear 学年（格式为：2005-2006）
     * @return List
     */
    public List<Grade> getGraduatingGrades(String schoolId, String graduateAcadyear);
    /**
     * 根据学校，学年得到当年非毕业的年级列表 不包括当前期年度毕业班级
     * 
     * @param schoolId 学校ID
     * @param acadyear 学年（格式为：2005-2006）
     * @return List
     */
    public List<Grade> getNotGraduatingGrades(String schoolId, String acadyear);

    /**
     * 根据学校和教师ID得到该班主任所教年级列表
     * 
     * @param schoolId
     * @param employeeId
     * @return
     */
    public List<Grade> getGradesByHeadTeacherId(String schoolId, String teacherId);

    /**
     * 取出年级的map
     * 
     * @param schoolId
     * @return <section+grade,Grade>
     */
    public Map<String, Grade> getGradeMap(String schoolId);

    // ======================根据年级查询班级=======================
    /**
     * 根据年级得到所属的班级列表
     * 
     * @param grade
     * @return List
     */
    public List<BasicClass> getClassesByGrade(Grade grade);

    /**
     * 班级列表
     * 
     * @param grade
     * @param curAcadyear 学年
     * @return
     */
    public List<BasicClass> getClassesByGrade(Grade grade, String curAcadyear);

    /**
     * 根据年级id，取得班级列表
     * @param grade
     * @param curAcadyear
     * @return
     */
    public List<BasicClass> getClassesByGrade(String gradeId);
    
    // ======================根据同类班级查询班级=======================
    /**
     * 根据学校、学段、入学学年取得不重复的某年级班级文理类型
     * 
     * @param schoolId 学校id
     * @param section 学段
     * @param acadyear 入学学年
     * @return List(artsciencetype)
     */
    public List<String> getClassType(String schoolId, int section, String acadyear);

    /**
     * 根据学校得到同类班级表列
     * 
     * @param schoolId
     * @return List
     */
    public List<KinClass> getKinClass(String schoolId);

    /**
     * 同类班级列表
     * 
     * @param schoolId
     * @param section
     * @param enrollyear
     * @param artScienceType
     * @param curAcadyear
     * @return
     */
    public List<BasicClass> getClassesByKinClass(String schoolId, int section, String enrollyear,
            String artScienceType, String curAcadyear);

    /**
     * 根据年级和文理类型得到所属的班级列表
     * 
     * @param grade
     * @return List
     */
    public List<BasicClass> getClassesByKinClass(Grade grade, String artScienceType);

    /**
     * 根据同类班级编号取得其对应的所有班级id列表
     * 
     * @param schoolId 学校主id
     * @param kinClassCode 同类班级编号
     * @return list(BasicClass)
     */
    public List<BasicClass> getClassesByKinClass(String schoolId, String kinClassCode);

    // ======================Map=======================
    /**
     * 取班级名称map
     * 
     * @param schoolId
     * @return
     */
    public Map<String, BasicClass> getClassMap(String schoolId);

    /**
     * 取班级名称map
     * 
     * @param schoolId
     * @param curAcadyear
     * @return
     */
    public Map<String, BasicClass> getClassMap(String schoolId, String curAcadyear);

    /**
     * 取班级名称map
     * 
     * @param schoolId
     * @return <classId,className>
     */
    public Map<String, String> getClassNameMap(String schoolId);

    /**
     * 取班级毕业年份map
     * 
     * @param schoolId 学校id
     * @return
     */
    public Map<String, String> getGraduateYearMap(String schoolId);

    /**
     * 根据学号取得所在班级gradename，选课结果导入时使用
     * 
     * @param stucode
     * @return
     */
    public Map<String, BasicClass> getGradeNameByUnitivecode(String[] unitivecode);

    // ======================自定义报表使用=======================
    /**
     * 取学校或教育局的年级
     * 
     * @param unitClass 类型
     * @param unitId 单位
     * @return
     */
    public List<String[]> getGradeNames(int unitClass, String unitId);

    /**
     * 取教育局年级
     * 
     * @param unitClass 类型
     * @param unitId 学校
     * @return
     */
    public Map<String, String> getGradeNameMap(int unitClass, String unitId);

    /**
     * 取班级列表
     * 
     * @param schoolId
     * @return
     */
    public List<String[]> getAllClassNames(String schoolId);

    /**
     * 取班级名称map
     * 
     * @param schoolId
     * @return
     */
    public Map<String, String> getAllClassMap(String schoolId);

    /**
     * 根据用户id和校区id取出该用户在该校区包含的班级的编号
     * 
     * @param userid 用户id
     * @param subschoolid 校区id
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
    
    
    public List<BasicClass> getClassesByGradeAll(String gradeId);

    
    public List<BasicClass> getClassesByGradeIds(String schId, String[] gradeId);
    
	public List<BasicClass> getClassesByTeacherIdAll(String schoolId, String teacherId);
	
	public List<BasicClass> getClassesByGradeId(String schoolId,
			String gradeId, String teacherId);
}
