package net.zdsoft.eis.school.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.school.entity.BaseSchool;
import net.zdsoft.eis.school.service.BaseSchoolService;
import net.zdsoft.eisu.base.common.service.SpecialtyService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 学校综合信息查询
 * 
 * @author weixh
 * @since Aug 12, 2011
 */
@SuppressWarnings("serial")
public class SchoolInfoQueryAction extends BaseAction {
	private UnitService unitService;
	private SpecialtyService specialtyService;
	private BaseSchoolService eisuBaseSchoolService;
	private TeacherService teacherService;
	private StudentService studentService;
	private String schName;// 查询条件：学校名称
	private String schId;
	
	private List<BaseSchool> schools = new ArrayList<BaseSchool>();// 学校列表
	private BaseSchool schoolInfo = new BaseSchool();

	// 首页
	public String execute() {
		return SUCCESS;
	}

	// 查询
	public String querySchool() {
		if (StringUtils.isBlank(schName)) {
			this.addFieldError("schName", "请输入学校名称！");
			return SUCCESS;
		}
		schools = eisuBaseSchoolService.getUnderlingSchoolsFaintness(
				unitService.getUnit(getUnitId()).getUnionid(), schName);
		return SUCCESS;
	}

	// 单个学校信息
	public String toSchoolInfoPage() {
		return SUCCESS;
	}

	// 学校基本信息
	public String getSchoolInfoDetail() {
		schoolInfo = eisuBaseSchoolService.getBaseSchool(schId);
		return SUCCESS;
	}

	// 专业开设信息
	public String getSchoolSpecDetail() {
		// TODO
		return SUCCESS;
	}

	// setters & getters
	public String getSchName() {
		return schName;
	}

	public void setSchName(String schName) {
		this.schName = StringUtils.trim(schName);
	}

	public String getSchId() {
		return schId;
	}

	public void setSchId(String schId) {
		this.schId = schId;
	}

	public List<BaseSchool> getSchools() {
		return schools;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setSpecialtyService(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

	public void setEisuBaseSchoolService(BaseSchoolService eisuBaseSchoolService) {
		this.eisuBaseSchoolService = eisuBaseSchoolService;
	}

	public BaseSchool getSchoolInfo() {
		return schoolInfo;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	// 学生人数
	public int getStuNum(){
		int stuNum = 0;
		List<Student> stuList = studentService.getStudentsByFaintness(schId, "");
		if(CollectionUtils.isNotEmpty(stuList)){
			for(Student stu : stuList){
				if(0 == stu.getLeavesign()){
					stuNum ++;
				}
			}
		}
		return stuNum;
	}
	
	// 教职工人数
	public int getTeacherNum(){
		int count = 0;
		List<Teacher> teas = teacherService.getTeachers(schId);
		if(CollectionUtils.isNotEmpty(teas)){
			Set<String> eusing = new HashSet<String>();
			eusing.add("10"); // 兼职
			eusing.add("11"); // 在职
			eusing.add("12"); // 试用
			for(Teacher tea : teas){
				if(eusing.contains(tea.getEusing())){
					++ count;
				}
			}
			eusing = null;
		}
		return count;
	}
}
