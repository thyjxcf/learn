package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.entity.EisBaseStudent;
import net.zdsoft.eis.base.data.service.BaseClassService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.EisBaseStudentService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.leadin.util.ExportUtil;
import net.zdsoft.leadin.util.PWD;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;

public class StudentAction extends PageAction implements
	ModelDriven<EisBaseStudent>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1881200134915601465L;

	private EisBaseStudent studentDto = new EisBaseStudent();
	private BaseUnit unitDto = new BaseUnit();
	private List<EisBaseStudent> listOfStudentDto = new ArrayList<EisBaseStudent>();
	private List<Grade> gradeList = new ArrayList<Grade>();
	private List<BasicClass> classList = new ArrayList<BasicClass>();
	
	private BaseUnitService baseUnitService;
	private GradeService gradeService;
	private BaseClassService baseClassService;
	private EisBaseStudentService eisBaseStudentService;
	private BaseUserService baseUserService;
	private McodeService mcodeService;
	private String unitId;
	private String gradeId;
	private String studentId;
	private String searchName;
	private String[] checkids;
	private boolean isEdu = true;//是否为教育局登陆
	
	private String mcodeName = "DM-XB";
	
	@Override
	public EisBaseStudent getModel() {
		return this.studentDto;
	}
	
	public String getStudentAdmin() {
		unitId = getLoginInfo().getUnitID();
		return SUCCESS;
	}
	
	public String getStudentList() {
		if(StringUtils.isBlank(unitId)){
			unitId = getLoginInfo().getUnitID();
		}
		unitDto = baseUnitService.getBaseUnit(unitId);
		if(Unit.UNIT_CLASS_EDU == unitDto.getUnitclass()){
			listOfStudentDto = new ArrayList<EisBaseStudent>();
		} 
		else {
			Map<String, Grade> gradeMap = gradeService.getGradeMapBySchid(unitId);
			Map<String, BasicClass> classMap = baseClassService.getClassMap(unitId);
			Set<String> set = new HashSet<String>();
			if(StringUtils.isBlank(searchName)){
				searchName = "";
			}
			listOfStudentDto = eisBaseStudentService.getStudentsByStudentNameClassId(unitId, searchName, null, getPage());
			for(Student stu : listOfStudentDto){
				set.add(stu.getId());
			}
			Map<String, User> userMap = baseUserService.getUserMapByOwner(User.STUDENT_LOGIN, set.toArray(new String[0]));
			for(EisBaseStudent stu : listOfStudentDto){
				BasicClass cls = classMap.get(stu.getClassid());
				if(cls != null){
					Grade grade = gradeMap.get(cls.getGradeId());
					if(grade != null)
						stu.setClassName(grade.getGradename() + cls.getClassname());
					else
						stu.setClassName(cls.getClassname());
				}
				User user = userMap.get(stu.getId());
				if(user != null){
					stu.setUserName(user.getName());
				}
			}
		}
		return SUCCESS;
	}
	
	public String exportStudent(){
		if(StringUtils.isBlank(unitId)){
			unitId = getLoginInfo().getUnitID();
		}
		unitDto = baseUnitService.getBaseUnit(unitId);
		if(Unit.UNIT_CLASS_EDU == unitDto.getUnitclass()){
			listOfStudentDto = new ArrayList<EisBaseStudent>();
		} 
		else {
			Map<String, Grade> gradeMap = gradeService.getGradeMapBySchid(unitId);
			Map<String, BasicClass> classMap = baseClassService.getClassMap(unitId);
			Mcode mcode = mcodeService.getMcode(mcodeName);
			Set<String> set = new HashSet<String>();
			if(StringUtils.isBlank(searchName)){
				searchName = "";
			}
			listOfStudentDto = eisBaseStudentService.getStudentsByStudentNameClassId(unitId, searchName, null, null);
			for(Student stu : listOfStudentDto){
				set.add(stu.getId());
			}
			Map<String, User> userMap = baseUserService.getUserMapByOwner(User.STUDENT_LOGIN, set.toArray(new String[0]));
			for(EisBaseStudent stu : listOfStudentDto){
				BasicClass cls = classMap.get(stu.getClassid());
				if(cls != null){
					Grade grade = gradeMap.get(cls.getGradeId());
					if(grade != null)
						stu.setClassName(grade.getGradename() + cls.getClassname());
					else
						stu.setClassName(cls.getClassname());
				}
				User user = userMap.get(stu.getId());
				if(user != null){
					stu.setUserName(user.getName());
				}
				stu.setSexStr(mcode.get(String.valueOf(stu.getSex())));
			}
		}
		String[] fieldTitles = null;
		String[] propertyNames = null;
        fieldTitles = new String[] { "班级","姓名","性别","账号"};
        propertyNames = new String[] {"className", "stuname", "sexStr", "userName"};
        Map<String,List<EisBaseStudent>> map = new HashMap<String,List<EisBaseStudent>>();
		
        map.put("学生账号信息", listOfStudentDto);
        String fileName = "学生账号信息表";
		ExportUtil exportUtil = new ExportUtil();
		exportUtil.exportXLSFile(fieldTitles, propertyNames, map, fileName);
		return NONE;
	}
	public String editStudent() {
		gradeList = gradeService.getGrades(unitId);
		if(StringUtils.isBlank(studentId)){
			studentDto = new EisBaseStudent();
		} else {
			studentDto = eisBaseStudentService.getStudent(studentId);
			BasicClass cls = baseClassService.getClass(studentDto.getClassid());
			if(cls != null){
				gradeId = cls.getGradeId();
				Grade grade = gradeService.getGrade(cls.getGradeId());
				if(grade != null){
					classList = baseClassService.getClassesByGrade(gradeId);
				}
			}
			Map<String, User> userMap = baseUserService.getUserMapByOwner(User.STUDENT_LOGIN, new String[]{studentId});
			User user = userMap.get(studentId);
			if(user != null)
				studentDto.setUserName(user.getName());
		}
		return SUCCESS;
	}
	
	public String saveStudent() {
		setPromptMessageDto(new PromptMessageDto());
		promptMessageDto.setOperateSuccess(false);
		if(StringUtils.isBlank(studentDto.getId())){//新增需校验账号名、密码
			if (StringUtils.isBlank(studentDto.getUserName())) {
				promptMessageDto.setErrorMessage("请输入账号");
				return SUCCESS;
			}
			if (!studentDto.getUserName().matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
				promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_NAME_ALERT));
				return SUCCESS;
			}
			if (baseUserService.getUserNameCount(studentDto.getUserName()) > 0) {
				promptMessageDto.setErrorMessage("该账号已存在");
				return SUCCESS;
			}
			if (StringUtils.isBlank(studentDto.getPassword())) {
				promptMessageDto.setErrorMessage("请输入密码");
				return SUCCESS;
			}
			// 密码加密
			PWD pwd = new PWD();
			String password = studentDto.getPassword();
			if (StringUtils.isBlank(password)) {
				pwd.setPassword(BaseConstant.PASSWORD_INIT);
			} else {
				pwd.setPassword(password);
			}
			
			if (!studentDto.getPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
				promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
				return SUCCESS;
			}
			
			if (studentDto.getPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_STRONG))) {
				promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
				return SUCCESS;
			}
			
			password = pwd.encode();
			studentDto.setPassword(password);
		}
		
		EisBaseStudent stu = new EisBaseStudent();
		unitDto = baseUnitService.getBaseUnit(unitId);
		BasicClass classDto = baseClassService.getClass(studentDto.getClassid());
		if(StringUtils.isNotBlank(studentDto.getId())){
			stu = eisBaseStudentService.getStudent(studentDto.getId());
		}
		else{
			stu.setId(UUIDGenerator.getUUID());
			stu.setSchid(unitDto.getId());
			stu.setEventSource(EventSourceType.LOCAL);
			stu.setRegionCode(unitDto.getRegion());
			stu.setLeavesign(BasedataConstants.LEAVESIGN_IN);// 是否离校，默认在校
			stu.setUserName(studentDto.getUserName());
			stu.setPassword(studentDto.getPassword());
		}
		stu.setClassid(studentDto.getClassid());
		stu.setStuname(studentDto.getStuname());
		stu.setSex(studentDto.getSex());
		stu.setEnrollYear(classDto.getAcadyear());// 班级的入学学年
		stu.setNowState(BasedataConstants.DJ);
		stu.setIsFreshman(BasedataConstants.ISFRESHMAN_NOT);
		
		try{
			if(StringUtils.isBlank(studentDto.getId())){
				eisBaseStudentService.insertStudent(stu);
			}
			else{
				eisBaseStudentService.updateStudent(stu);
			}
		} catch(Exception e){
			promptMessageDto.setErrorMessage("保存学生信息失败：");
			System.out.println(e.getMessage());
			return SUCCESS;
		}
		promptMessageDto.setPromptMessage("保存学生信息成功！");
		promptMessageDto.setOperateSuccess(true);
		return SUCCESS;
	}
	
	public String deleteStudent() {
		try{
			eisBaseStudentService.deleteStudents(checkids);
			baseUserService.deleteUsersByOwner(User.STUDENT_LOGIN, checkids);
		} catch(Exception e){
			promptMessageDto.setErrorMessage("删除学生信息失败：");
			promptMessageDto.setOperateSuccess(false);
			return SUCCESS;
		}
		promptMessageDto.setPromptMessage("删除学生信息成功！");
		promptMessageDto.setOperateSuccess(true);
		return SUCCESS;
	}
	
	/**
	 * 验证用户名称的可用性 true存在,false不存在
	 * 
	 * @param userName
	 * @return
	 */
	public String validateUserNameAvaliable() {
		setPromptMessageDto(new PromptMessageDto());
		promptMessageDto.setOperateSuccess(false);
		if (StringUtils.isBlank(studentDto.getUserName())) {
			promptMessageDto.setErrorMessage("请输入账号");
			return SUCCESS;
		}
		if (!studentDto.getUserName().matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
			promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_NAME_ALERT));
			return SUCCESS;
		}
		if (baseUserService.getUserNameCount(studentDto.getUserName()) <= 0) {
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("");
		} else {
			promptMessageDto.setErrorMessage("该账号已存在");
		}
		return SUCCESS;
	}
	
	public String getStuClassList() {
		classList = baseClassService.getClassesByGrade(gradeId);
		return SUCCESS;
	}
	
	public Student getStudentDto() {
		return studentDto;
	}

	public void setStudentDto(EisBaseStudent studentDto) {
		this.studentDto = studentDto;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public boolean getIsEdu() {
		if(Unit.UNIT_CLASS_EDU == getLoginInfo().getUnitClass())
			isEdu = true;
		else
			isEdu = false;
		return isEdu;
	}

	public void setIsEdu(boolean isEdu) {
		this.isEdu = isEdu;
	}

	public List<EisBaseStudent> getListOfStudentDto() {
		return listOfStudentDto;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public void setEisBaseStudentService(EisBaseStudentService eisBaseStudentService) {
		this.eisBaseStudentService = eisBaseStudentService;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public BaseUnit getUnitDto() {
		return unitDto;
	}

	public void setUnitDto(BaseUnit unitDto) {
		this.unitDto = unitDto;
	}

	public String[] getCheckids() {
		return checkids;
	}

	public void setCheckids(String[] checkids) {
		this.checkids = checkids;
	}

	public void setBaseClassService(BaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public List<Grade> getGradeList() {
		return gradeList;
	}

	public List<BasicClass> getClassList() {
		return classList;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}

}
