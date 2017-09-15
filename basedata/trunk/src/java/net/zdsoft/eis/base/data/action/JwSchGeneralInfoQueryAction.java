package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.EduInfoService;
import net.zdsoft.eis.base.common.service.SchoolDistrictService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.frame.action.BaseSemesterAction;

public class JwSchGeneralInfoQueryAction extends BaseSemesterAction {
	private static final long serialVersionUID = 1L;
	
	private UnitService unitService;
	BaseSchoolService baseSchoolService;   //学校基本信息
	BasicClassService basicClassService;         //班级基本信息
	private StudentService  studentService;
	EduInfoService eduInfoService;
	private SchoolDistrictService schoolDistrictService;
	private TeacherService teacherService;
	
	private BaseSchool schoolinfo=new BaseSchool();  //学校详细信息
	private SchoolSemester semesterDto=new SchoolSemester();     //学期详细信息
	private BasicClass classDto=new BasicClass();              //班级详细信息
	
	private String name = "";       //查询条件：学校名称
	private String schid = "";      //学校id
	private String classid = "";    //学校id
	private String acadyear;
    private String semester;
	private String searchType = ""; //页面下拉框选中类型
	private List<BaseSchool> basicSchoolInfo = new ArrayList<BaseSchool>();    //学校基本信息列表
	private List<SchoolSemester> basicSemesterInfo = new ArrayList<SchoolSemester>();  //学期基本信息列表
	private List<BasicClass> basicClassInfo = new ArrayList<BasicClass>();     //班级基本信息列表
	

	
	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

	public String execute() throws Exception {
		return SUCCESS;
	}
	
	//查询学校基本信息
	public String queryBasicSchool() {
		if(StringUtils.isBlank(name)) {
			addFieldError("name", "请输入学校名称！");
			return INPUT;
		}
		basicSchoolInfo = baseSchoolService.getUnderlingSchoolsFaintness(unitService.getUnit(getLoginInfo().getUnitID()).getUnionid(),name);
		return SUCCESS;
	}
	
	//查询学校详细信息	
	public String queryDetailSchool() {
		schoolinfo = baseSchoolService.getBaseSchool(schid);
		// 所在学区编码转换为名称
        if (schoolinfo.getSchdistrictid() != null
                && !schoolinfo.getSchdistrictid().trim().equals("")) {
            SchoolDistrict district = schoolDistrictService.getSchoolDistrict(schoolinfo
                    .getSchdistrictid());
            if (district == null) {
                schoolinfo.setSchdistrictname("");
            } else {
                schoolinfo.setSchdistrictname(district.getName());
            }
        }
       
		schoolinfo.setEducode(eduInfoService.getEduInfo(unitService.getUnit(schid).getParentid()).getEduCode());
		return SUCCESS;
	}

	//查询学年学期基本信息	
	public String queryBasicSemester() {
		basicSemesterInfo = schoolSemesterService.getSemesterByName(name,unitService.getUnit(getLoginInfo().getUnitID()).getUnionid());
		return SUCCESS;
	}

	//查询学年学期详细信息	
	public String queryDetailSemester() {
		semesterDto = schoolSemesterService.getSemester(schid, acadyear, semester);
		return SUCCESS;
	}
	
	//查询班级基本信息	
	public String queryBasicClass() {
		basicClassInfo = getClassBySchname(name,unitService.getUnit(getLoginInfo().getUnitID()).getUnionid());
		setstuCount(basicClassInfo);
		return SUCCESS;
	}
    private void setstuCount(List<BasicClass> list) {
        String[] classIds = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            BasicClass cls = (BasicClass) list.get(i);
            classIds[i] = cls.getId();
        }
        Map<String, Integer> map = studentService.getStudentCountMap(classIds);
        Integer count;
        for (int i = 0; i < list.size(); i++) {
            BasicClass cls = (BasicClass) list.get(i);
            count = map.get(cls.getId());
            if (null != count) {
                cls.setStucount(count);
            }
        }
    }
    
	// 根据学校名称模糊查询班级信息
    private List<BasicClass> getClassBySchname(String name, String unionId) {
        List<BasicClass> clsList = new ArrayList<BasicClass>();

        List<BaseSchool> schList = baseSchoolService.getUnderlingSchoolsFaintness(unionId, name);
        if (schList.size() == 0)
            return clsList;

        // 取得学校id数组
        for (int i = 0; i < schList.size(); i++) {
            BaseSchool sch = schList.get(i);
            List<BasicClass> _tmpList = basicClassService.getClasses(sch.getId());

            for (BasicClass cls : _tmpList) {
                cls.setName(sch.getName()); // 设置学校名称
            }
            clsList.addAll(_tmpList);
        }
        return clsList;
    }

    
	//查询班级详细信息	
	public String queryDetailClass() {
		classDto = basicClassService.getClass(classid);
		String teacherId = classDto.getTeacherid();
		String viceTeacherId = classDto.getViceTeacherId();
		String studentId = classDto.getStuid();
		if (StringUtils.isNotBlank(teacherId) && StringUtils.isBlank(classDto.getTeachername())){
			Teacher teacher = teacherService.getTeacher(teacherId);
			if (teacher != null)
				classDto.setTeachername(teacher.getName());
		}
		if (StringUtils.isNotBlank(viceTeacherId) && StringUtils.isBlank(classDto.getViceTeacherName())){
			Teacher teacher = teacherService.getTeacher(viceTeacherId);
			if (teacher != null)
				classDto.setViceTeacherName(teacher.getName());
		}
		if (StringUtils.isNotBlank(studentId) && StringUtils.isBlank(classDto.getMonitor())){
			Student student = studentService.getStudent(studentId);
			if (student != null)
				classDto.setMonitor(student.getStuname());
		}
		return SUCCESS;
	}
	
    @SuppressWarnings("unused")
    private BasicClass getReportClass(String classId) {
        BasicClass entity = basicClassService.getClass(classId);
        if (entity == null)
            return null;

        List<String> listOfTeacherId = new ArrayList<String>();
        if (entity.getTeacherid() != null)
            listOfTeacherId.add(entity.getTeacherid());
        if (entity.getViceTeacherId() != null)
            listOfTeacherId.add(entity.getViceTeacherId());

        if (listOfTeacherId != null && listOfTeacherId.size() > 0) {
            Map<String, Teacher> mapOfTeacherName = teacherService.getTeacherMap(listOfTeacherId
                    .toArray(new String[0]));
            if (entity.getTeacherid() != null) {
                entity.setTeachername(mapOfTeacherName.get(entity.getTeacherid()).getName());
            }
            if (entity.getViceTeacherId() != null) {
                entity.setViceTeacherName(mapOfTeacherName.get(entity.getViceTeacherId())
                                .getName());
            }
        }

        if (StringUtils.isNotEmpty(entity.getStuid())) {
            entity.setMonitor(studentService.getStudent(entity.getStuid()).getStuname());
        }

        if (entity.getGraduatesign() == BasicClass.GRADUATE_SIGN_NO) {
            entity.setGraduatesign(BasicClass.GRADUATE_SIGN_NO);
        } else {
            entity.setGraduatesign(BasicClass.GRADUATE_SIGN_YES);
        }
        
        return entity;
    }
	
	//相关参数的set,get方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public List<BaseSchool> getBasicSchoolInfo() {
		return basicSchoolInfo;
	}

	public void setBasicSchoolInfo(List<BaseSchool> basicSchoolInfo) {
		this.basicSchoolInfo = basicSchoolInfo;
	}	

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
        this.baseSchoolService = baseSchoolService;
    }

    public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}

	public BaseSchool getSchoolinfo() {
		return schoolinfo;
	}

	public void setSchoolinfo(BaseSchool schoolinfo) {
		this.schoolinfo = schoolinfo;
	}

	public List<SchoolSemester> getBasicSemesterInfo() {
		return basicSemesterInfo;
	}

	public void setBasicSemesterInfo(List<SchoolSemester> basicSemesterInfo) {
		this.basicSemesterInfo = basicSemesterInfo;
	}

	public SchoolSemester getSemesterDto() {
		return semesterDto;
	}

	public void setSemesterDto(SchoolSemester semesterDto) {
		this.semesterDto = semesterDto;
	}

	public BasicClassService getBasicClassService() {
		return basicClassService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public List<BasicClass> getBasicClassInfo() {
		return basicClassInfo;
	}

	public void setBasicClassInfo(List<BasicClass> basicClassInfo) {
		this.basicClassInfo = basicClassInfo;
	}

	public BasicClass getClassDto() {
		return classDto;
	}

	public void setClassDto(BasicClass classDto) {
		this.classDto = classDto;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setEduInfoService(EduInfoService eduInfoService) {
		this.eduInfoService = eduInfoService;
	}

    public void setSchoolDistrictService(SchoolDistrictService schoolDistrictService) {
        this.schoolDistrictService = schoolDistrictService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

  
	
}
