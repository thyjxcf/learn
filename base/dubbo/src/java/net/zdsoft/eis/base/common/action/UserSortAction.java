package net.zdsoft.eis.base.common.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BaseCourseService;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.JSONAction;
import net.zdsoft.eis.frame.client.LoginInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class UserSortAction extends JSONAction {

	private TeacherService teacherService;
	
	private DeptService deptService;
	
	private UserService userService;
	
	private UnitService unitService;
	
	private McodedetailService mcodedetailService;
	
	private BaseCourseService baseCourseService;
	
	private StudentService studentService;
	
	private BasicClassService basicClassService;
	
	private StudentFamilyService studentFamilyService;
	
	private GradeService gradeService;
	
	private String unitId;
	
	private boolean showUnit;
	
	private String groupId;
	
	private List<Teacher> teachers = new ArrayList<Teacher>();
	
	private String searchName = "";
	
	private String classId;
	
    //职务、学科
    private String dutyCode;
    private String subjectCode;
	
	public String execute() {
        LoginInfo loginInfo = getLoginInfo();
        unitId = loginInfo.getUser().getUnitid();
        return SUCCESS;
    }
	
	public String getEmployeeList(){
		if (!showUnit && !groupId.equals("null")) {
            teachers = teacherService.getTeachersByDeptId(groupId);
        }
        else {
            teachers = teacherService.getTeachers(unitId);
        }
		return SUCCESS;
	}
	
	public void getGroupList(){
        LoginInfo loginInfo = getLoginInfo();
        unitId = loginInfo.getUser().getUnitid();
        List<Dept> group = deptService.getDepts(unitId);
        List<Dept> groups = new ArrayList<Dept>();
        for (Dept dept : group) {
            Dept depta = new Dept();
            depta.setId(dept.getId());
            depta.setDeptCode(dept.getDeptCode());
            depta.setDeptname(dept.getDeptname());
            groups.add(depta);
        }
        responseJSON(groups);
	}
	
    /**
     * 根据单位ID得到该单位下的部门列表
     * 
     * @param unitid
     * @return
     */
    public void getGroupListByUnitid() {
        responseJSON(getGroupListByUnitidCommon(unitId));
        // return getReply();
    }

    private List<Dept> getGroupListByUnitidCommon(String unitId) {
        List<Dept> group = deptService.getDepts(unitId);
        List<Dept> groups = new ArrayList<Dept>();
        for (Dept dept : group) {
            Dept depta = new Dept();
            depta.setId(dept.getId());
            depta.setDeptCode(dept.getDeptCode());
            depta.setDeptname(dept.getDeptname());
            groups.add(depta);
        }
    	
        return groups;
    }
	
    /**
     * 得到上级单位
     * 
     * @return
     */
    public void getParentUnit() {
        // Reply reply = getReply();
        responseJSON(getParentUnitCommon());
        // return reply;
    }

    private List<Unit> getParentUnitCommon() {
    	Unit loginUnit = unitService.getUnit(getLoginUser().getUnitId());

        List<Unit> unitList = new ArrayList<Unit>();
        // 得到父级单位
        Unit parentUnit = unitService.getUnit(loginUnit.getParentid());
        if (parentUnit != null) {
            unitList.add(parentUnit);
        }

        return unitList;
    }
    
    /**
     * 得到同级单位的列表 同级单位：parentId与当前登陆单位parentId相同的单位
     * 
     * @return
     */
    public void getSameLevelUnits() {
        // Reply reply = getReply();
        responseJSON(getSameLevelUnitsCommon());
        // return reply;
    }

    private List<Unit> getSameLevelUnitsCommon() {

        Unit loginUnit = unitService.getUnit(getLoginUser().getUnitId());
        List<Unit> unitList = unitService.getUnderlingUnits(loginUnit.getParentid());//同级单位
        // 过滤掉自身单位
        for (Unit unit : unitList) {
            if (unit.getId().equals(getLoginInfo().getUser().getUnitid())) {
                unitList.remove(unit);
                break;
            }

        }
        return unitList;
    }
    /**
     * 得到全部下级单位
     */
    public void getAllDownUnits(){
    	responseJSON(unitService.getAllNormalUnits(getLoginUser().getUnitId(), false));
    }
    
	public void getDirectSubUnits(){
		responseJSON(unitService.getUnderlingUnits(getLoginUser().getUnitId()));
	}

	 public void queryAllDutys(){
	    	List<Mcodedetail> list = new ArrayList<Mcodedetail>();
	    	List<Mcodedetail> edulist = mcodedetailService.getMcodeDetails("DM-JYJZW");
	    	for(Mcodedetail m : edulist){
	    		m.setContent("(教育局)"+m.getContent());
	    	}
	    	List<Mcodedetail> schoollist = mcodedetailService.getMcodeDetails("DM-XXZW");
	    	for(Mcodedetail m : schoollist){
	    		m.setContent("(学校)"+m.getContent());
	    	}
	    	list.addAll(edulist);
	    	list.addAll(schoollist);
	    	responseJSON(list);
	 }
	 
	 public void queryAllSubjects(){
	    	responseJSON(baseCourseService.getBaseCoureList(BaseConstant.ZERO_GUID));
	 }
	 
	 public String queryDutyMemeber(){
		 String[] unitIds = getUnitIds(getLoginUser().getUnitId());
		 teachers = teacherService.findTeachersByDutyCode(unitIds, dutyCode);
	     return SUCCESS;
	 }
	    
    public String querySubjectMemeber(){
    	String[] unitIds = getUnitIds(getLoginUser().getUnitId());
    	teachers = teacherService.findTeachersBySubject(unitIds, subjectCode);
        return SUCCESS;
    }
    
    private String[] getUnitIds(String unitId){
    	Set<String> unitIds = new HashSet<String>();
		 //获取上级单位、同级单位以及直属单位职务教师加上下属单位
		 Unit loginUnit = unitService.getUnit(unitId);
		 unitIds.add(loginUnit.getParentid());//上级单位
		 List<Unit> unitList = unitService.getUnderlingUnits(loginUnit.getParentid());//同级单位(包括本单位)
		 for(Unit u : unitList){
			 unitIds.add(u.getId());
		 }
		 List<Unit> unitList1 = unitService.getUnderlingUnits(unitId);//直属单位
		 for(Unit u : unitList1){
			 unitIds.add(u.getId());
		 }
		 List<Unit> allNormalUnits = unitService.getAllNormalUnits(unitId, false);//下属单位
		 for (Unit u : allNormalUnits) {
			 unitIds.add(u.getId());
		}
		 return unitIds.toArray(new String[]{});
    }
    /**
     * 得到班主任的列表
     * 
     * @return
     */
    public String getAdviserTeacherList() {
        // Reply reply = getReply();
        // responseJSON(getAdviserTeacherListCommon());
        teachers = getAdviserTeacherListCommon();

        return SUCCESS;
    }

    private List<Teacher> getAdviserTeacherListCommon() {
        LoginInfo loginInfo = getLoginInfo();
        List<Teacher> teacherList = new ArrayList<Teacher>();

        // 班主任的主键集合,其中包括副班主任
        Set<String> teacherIds = new HashSet<String>();

        // 如果是学生登陆，得到该学生所在班级的班主任
//        if (loginInfo.getUser().getOwnerType() == User.STUDENT_LOGIN) {
//            // 得到学生信息
//        	Student stu = studentService.getStudent(loginInfo.getUser().getTeacherid());
//            // 得到学生所在的班级
//        	BasicClass cls = basicClassService.getClass(stu.getClassid());
//            if (cls != null) {
//                teacherIds.addAll(getTeacherIds(cls)); // 得到该班级下的班主任和副班主任的主键集合
//            }
//        }else if (loginInfo.getUser().getOwnerType() == User.FAMILY_LOGIN) {// 如果是家长登陆，得到该家长的学生所在班级的班主任
//        	Family stufamily = studentFamilyService.getFamily(loginInfo.getUser().getTeacherid());
//            // 得到学生信息
//        	Student stu = studentService.getStudent(stufamily.getStudentId());
//            // 得到学生所在的班级
//            if (stu != null) {
//            	BasicClass cls = basicClassService.getClass(stu.getClassid());
//                // 如果班级不为空，得到该班级的班主任
//                if (cls != null) {
//                    teacherIds.addAll(getTeacherIds(cls));
//                }
//            }
//        }else{// 如果是学校老师登陆，包括普通教师，任课老师，班主任，年级组长登陆得到所有的班主任
        	 // 得到学校所有班级的列表
        	List<BasicClass> classList = basicClassService.getAllClasses(getLoginUser().getUnitId());
            // 根据班级列表得到所有的班主任
            for (BasicClass cls : classList) {
                teacherIds.addAll(getTeacherIds(cls));
            }
//        }

        // 通过上述过程得到班主任和副班主任的主键集合得到班主任的列表
        if (CollectionUtils.isNotEmpty(teacherIds)) {
        	teacherList = teacherService.getTeachers(teacherIds.toArray(new String[]{}));
        }
        return teacherList;
    }

    /**
     * 得到年级组长的列表
     * 
     * @return
     */
    public String getGradeHeadList() {
        try {
            teachers = getGradeHeadListCommon();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    private List<Teacher> getGradeHeadListCommon() throws Exception {
        List<Teacher> teacherList = new ArrayList<Teacher>();
        List<Grade> gradelist = gradeService.getGrades(getLoginUser().getUnitId());
        Set<String> setOfTeacherId = new HashSet<String>();
        for (Grade grade : gradelist) {
            if (grade.getTeacherId() != null) {
                setOfTeacherId.add(grade.getTeacherId());
            }
        }
        if(CollectionUtils.isNotEmpty(setOfTeacherId)){
        	teacherList = teacherService.getTeachers(setOfTeacherId.toArray(new String[]{}));
        }
        return teacherList;
    }
    
    private Set<String> getTeacherIds(BasicClass cls) {
        Set<String> teacherIds = new HashSet<String>();

        if (StringUtils.isNotBlank(cls.getTeacherid())) {
            teacherIds.add(cls.getTeacherid());
        }
        if (StringUtils.isNotBlank(cls.getViceTeacherId())) {
            teacherIds.add(cls.getViceTeacherId());
        }

        return teacherIds;
    }
    
    public String simpleAjaxSearch() {
    	String[] unitIds = getUnitIds(getLoginUser().getUnitId());
    	//搜索上级单位、直属单位、同级单位（包括本单位）教职工信息
        teachers = teacherService.findTeachersByUnitIds(unitIds, searchName);
        return SUCCESS;
    }
    
    /**
     * 获取单位任课教师列表
     */
    public String getTeachingTeacherList() {
    	teachers = teacherService.findTeacheringTeachersByUnitIds(new String[]{getLoginUser().getUnitId()});
        return SUCCESS;
    }
    
    /**
     * 得到学生列表
     * 
     * @param classId
     * @return
     */
    public String getStudentList() {
    	List<Student> stulist = studentService.getStudents(classId);
    	for(Student stu : stulist){
    		Teacher teacher = new Teacher();
    		teacher.setName(stu.getStuname());
    		teacher.setSex(String.valueOf(stu.getSex()));
			List<User> userlist = userService.getUsersByOwner(stu.getId());
			
			User user = new User();
			if(userlist != null && userlist.size()>0){
				user = userlist.get(0);
				teacher.setUserId(user.getId());
				teacher.setOwnerType(user.getOwnerType());
				Unit unit = unitService.getUnit(user.getUnitid());
				if(unit != null){
					teacher.setUnitName(unitService.getUnit(user.getUnitid()).getName());
				}
				teachers.add(teacher);
			}
		}
        return SUCCESS;
    }
    
    /**
     * 得到学生家长列表
     * 
     * @param classId
     * @return
     */
    public String getParentList() {
    	List<Student> stulist = studentService.getStudents(classId);
    	Set<String> stuids = new HashSet<String>();
    	for(Student stu : stulist){
    		stuids.add(stu.getId());
		}
    	List<Family> familyList = studentFamilyService.getFamiliesByStudentId(stuids.toArray(new String[]{}));
    	for(Family family : familyList){
    		Teacher teacher = new Teacher();
    		teacher.setName(family.getName());
    		teacher.setSex(String.valueOf(family.getSex()));
    		List<User> userlist = userService.getUsersByOwner(family.getId());
    		
    		User user = new User();
    		if(userlist != null && userlist.size()>0){
    			user = userlist.get(0);
    			teacher.setUserId(user.getId());
				teacher.setOwnerType(user.getOwnerType());
    			Unit unit = unitService.getUnit(user.getUnitid());
    			if(unit != null){
    				teacher.setUnitName(unitService.getUnit(user.getUnitid()).getName());
    			}
    			teachers.add(teacher);
    		}
    	}
        return SUCCESS;
    }
    
    /**
     * 得到班级列表 该方法会根据当前登陆的用户类别返回过滤后的班级列表
     * 
     * @return
     */
    public void getClassList() {
        responseJSON(getClassListCommon());
    }
    
    /**
     * 获取班级列表
     * 
     * @return
     */
    private List<BasicClass> getClassListCommon() {
    	LoginInfo loginInfo = getLoginInfo();
        List<BasicClass> classList = new ArrayList<BasicClass>();
        // 如果是学生登陆，得到该学生所在班级
//        if (loginInfo.getUser().getOwnerType() == User.STUDENT_LOGIN) {
//            // 得到学生信息
//        	Student stu = studentService.getStudent(loginInfo.getUser().getTeacherid());
//            // 得到学生所在的班级
//        	BasicClass cls = basicClassService.getClass(stu.getClassid());
//        	cls.setClassname(ClassNameFactory.getInstance().getClassNameDyn(cls));
//        	classList.add(cls);
//        }else if (loginInfo.getUser().getOwnerType() == User.FAMILY_LOGIN) {// 如果是家长登陆，得到该家长的学生所在班级
//        	Family stufamily = studentFamilyService.getFamily(loginInfo.getUser().getTeacherid());
//            // 得到学生信息
//        	Student stu = studentService.getStudent(stufamily.getStudentId());
//            // 得到学生所在的班级
//            if (stu != null) {
//            	BasicClass cls = basicClassService.getClass(stu.getClassid());
//            	cls.setClassname(ClassNameFactory.getInstance().getClassNameDyn(cls));
//            	classList.add(cls);
//            }
//        }else{// 如果是学校老师登陆，包括普通教师，任课老师，班主任，年级组长登陆得到所有的班主任
        	 // 得到学校所有班级的列表
        	Map<String, String> classMap = basicClassService.getClassNameMap(getLoginUser().getUnitId());
        	for(Map.Entry<String, String>  entry : classMap.entrySet()){
        		BasicClass cls = new BasicClass();
        		cls.setId(entry.getKey());
        		cls.setClassname(entry.getValue());
        		classList.add(cls);
        	}
//        }
        return classList;
    }
    
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
    /**
     * 是否显示直属单位 parentId为当前用户单位id的单位
     * 
     * @return
     */
    public boolean isShowDirectSubUnit() {
        return true;
    }
    
    /**
     * 判断是否显示上级单位 上级单位：以当前用户单位的parentId为id的单位
     * 
     * @return
     */
    public boolean isShowParentUnit() {
        // return checkConfig("5");
        return true;

    }
    
    /**
     * 判断是否显示同级单位 同级单位：parentId与当前用户单位的parentId相同的单位
     * 
     * @return
     */
    public boolean isShowSameLevelUnit() {
        // return checkConfig("3");
        return true;
    }
    
    public boolean isShowTeacherDuty(){
    	return true;
    }
    
    public boolean isShowTeacherSubject(){
    	return true;
    }

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public boolean isShowUnit() {
		return showUnit;
	}

	public void setShowUnit(boolean showUnit) {
		this.showUnit = showUnit;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<Teacher> getTeachers() {
		if(teachers != null && teachers.size()>0){
			for(Teacher teacher : teachers){
				List<User> userlist = userService.getUsersByOwner(teacher.getId());
				User user = new User();
				if(userlist != null && userlist.size()>0){
					user = userlist.get(0);
					teacher.setUserId(user.getId());
					teacher.setOwnerType(user.getOwnerType());
					Unit unit = unitService.getUnit(user.getUnitid());
					if(unit != null){
						teacher.setUnitName(unitService.getUnit(user.getUnitid()).getName());
					}
				}
			}
		}	
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public void setBaseCourseService(BaseCourseService baseCourseService) {
		this.baseCourseService = baseCourseService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setStudentFamilyService(StudentFamilyService studentFamilyService) {
		this.studentFamilyService = studentFamilyService;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
}
