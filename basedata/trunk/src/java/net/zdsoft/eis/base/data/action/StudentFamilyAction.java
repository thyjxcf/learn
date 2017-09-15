/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author wangsn
 * @since 1.0
 * @version $Id: StudentFamilyAction.java,v 1.8 2006/12/15 06:40:48 zhaosf Exp $
 */
package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.enumeration.VersionType;
import net.zdsoft.eis.base.data.service.BaseStudentFamilyService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eisu.base.common.entity.EisuClass;
import net.zdsoft.eisu.base.common.entity.EisuStudent;
import net.zdsoft.eisu.base.common.service.EisuClassService;
import net.zdsoft.eisu.base.common.service.EisuStudentService;
import net.zdsoft.keel.util.RandomUtils;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class StudentFamilyAction extends PageSemesterAction implements

		ModelDriven<Family> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6213363864538267841L;

	private BaseStudentFamilyService baseStudentFamilyService;
	private Family family = new Family();
	private StudentService studentService;
	private String phonetype = null;
	private UserService userService;
	private UnitService unitService;
	private String famId;

	private String stuid; //学生

	private String queryStudentCode;	
	private String queryStudentName;
	private List<EisuStudent> students;
	private Map<String, EisuClass> classesMap;
	private EisuStudentService eisuStudentService;
	private EisuClassService eisuClassService;
	
	public Family getModel() {
		return family;
	}

	public String admin() {
		this.getLoginInfo().getUnitClass();
		return SUCCESS;
	}

	public String execute() throws Exception {

		return SUCCESS;
	}

	public String add() throws Exception {

		Student stu = studentService.getStudent(family.getStudentId());
		if (family.getName() == null || family.getName().equals("")) {
			family.setName(stu.getStuname() + "的家长");
		}
		if (family.getSchoolId() == null || family.getSchoolId().equals("")) {
			family.setSchoolId(stu.getSchid());
		}
		return SUCCESS;
	}

	public String save() throws Exception {

		String randomNumber = RandomUtils.getRandomStr(8);

		// 虚拟手机号的类型,mobile移动,unicom联通,other小灵通
		if (phonetype != null) {
			if (phonetype.equals("mobile")) {
				family.setLinkPhone("0001" + randomNumber);
			} else if (phonetype.equals("unicom")) {
				family.setLinkPhone("0002" + randomNumber);
			} else if (phonetype.equals("other")) {
				family.setLinkPhone("0003" + randomNumber);
			}
		}
		if (StringUtils.isNotBlank(family.getMobilePhone())) {
			List<Family> list = baseStudentFamilyService.getFamilies(family
					.getStudentId(), family.getMobilePhone());
			for (Family sf : list) {
				if (!sf.getId().equals(family.getId())) {
					jsonError = "该生另一家长已存在相同的手机号，请重新输入";
					return SUCCESS;
				}
			}

		}
		Unit unit = unitService.getUnit(family.getSchoolId());
		if (unit != null) {
			family.setRegionCode(unit.getRegion());
		}
		family.setCreationTime(new Date());
		family.setModifyTime(family.getCreationTime());
		//置默认值 
		if (StringUtils.isBlank(String.valueOf(family.getLeaveSchool())))
            family.setLeaveSchool(0);
        baseStudentFamilyService.saveFamily(family);

		SystemLog.log("SCH006", StringUtils.isNotBlank(family.getId()) ? "修改"
				: "新增" + family.getName() + "家庭成员信息成功！");

		return SUCCESS;
	}

	public String remove() throws Exception {
		promptMessageDto = new PromptMessageDto();
		if(StringUtils.isBlank(famId)){
			promptMessageDto.setErrorMessage("没有选择要删除的家长信息！");
			return SUCCESS;
		}
		String[] ids = StringUtils.split(famId, ",");
		//界面操作 是否发送消息默认置true
		String msgStr="";
		try {
        	Map<String, Object> verifyDataDeleteMap = userService.getVerifyDelete(User.FAMILY_LOGIN,ids);
    		String[] ownerIds=(String[]) verifyDataDeleteMap.get("yesIds");
    		msgStr=(String) verifyDataDeleteMap.get("msg");
    		
			Set<String> noDeletedFamilyIds = baseStudentFamilyService.deleteFamiliesByFamilyIds(ownerIds, EventSourceType.LOCAL);
			promptMessageDto.setOperateSuccess(true);
			if (noDeletedFamilyIds.size() > 0) {
			    promptMessageDto.setPromptMessage("成功删除" + (ownerIds.length - noDeletedFamilyIds.size())
			            + "个家长，" + noDeletedFamilyIds.size() + " 个家长对应的用户存在个人定购关系，故不能删除。"+msgStr);
			    return SUCCESS;
			} else {
				promptMessageDto.setPromptMessage("操作成功！"+msgStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			promptMessageDto.setErrorMessage("删除家长失败！");
		}
		
		SystemLog.log("SCH006", "删除学生家庭成员信息。"+msgStr);
		return SUCCESS;
	}

	public String edit() throws Exception {

		family = baseStudentFamilyService.getFamily(family.getId());
		if (family.getRelation().equals("51")
				|| family.getRelation().equals("52")) {
			family.setSex(String
					.valueOf(Integer.parseInt(family.getRelation()) - 50));
		}
		return "success";
	}

	public String list() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String stuid = family.getStudentId();

		if (stuid == null || stuid.trim().equals("")) {
			return "input";
		}

		List<Family> result = baseStudentFamilyService
				.getFamiliesByStudentId(stuid);
		request.setAttribute("familyMemberList", result);
		return "success";
	}
	
	public String list2() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		students = eisuStudentService.getStudentsByFaintnessStudentCode(getUnitId(), queryStudentName, queryStudentCode);
		List<EisuStudent> studentsDel = new ArrayList<EisuStudent>();
		for(EisuStudent student : students){
			if(BaseConstant.LEAVESIGN_OUT==student.getLeavesign()){
				studentsDel.add(student);
			}
		}
		students.removeAll(studentsDel);
		String[] classIds = new String[students.size()];
		int i = 0;
		for(EisuStudent student : students){
			classIds[i] = student.getClassid();
			i++;
		}
		classesMap = eisuClassService.getClassMap(classIds);
		for(EisuStudent student : students){
			EisuClass eisuClass=classesMap.get(student.getClassid());
			if(eisuClass!=null){
				student.setClassName(eisuClass.getClassname());	
			}
		}
		if(students!=null && students.size() == 1){
			String stuid = 	students.get(0).getId();
			family.setStudentId(stuid);
			List<Family> result = baseStudentFamilyService
					.getFamiliesByStudentId(stuid);
			request.setAttribute("familyMemberList", result);
			return SUCCESS;
		}else{
			return "input";
		}
	}

	public String detail() throws Exception {
		return "success";
	}

	public String getPhonetype() {
		return phonetype;
	}

	public void setPhonetype(String phonetype) {
		this.phonetype = phonetype;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setBaseStudentFamilyService(
			BaseStudentFamilyService baseStudentFamilyService) {
		this.baseStudentFamilyService = baseStudentFamilyService;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public void setFamId(String famId) {
		this.famId = famId;
	}

	public String getQueryStudentCode() {
		return queryStudentCode;
	}

	public void setQueryStudentCode(String queryStudentCode) {
		this.queryStudentCode = queryStudentCode;
	}

	public String getQueryStudentName() {
		return queryStudentName;
	}

	public void setQueryStudentName(String queryStudentName) {
		this.queryStudentName = queryStudentName;
	}

	public EisuStudentService getEisuStudentService() {
		return eisuStudentService;
	}

	public void setEisuStudentService(EisuStudentService eisuStudentService) {
		this.eisuStudentService = eisuStudentService;
	}

	public List<EisuStudent> getStudents() {
		return students;
	}

	public void setStudents(List<EisuStudent> students) {
		this.students = students;
	}

	public EisuClassService getEisuClassService() {
		return eisuClassService;
	}

	public void setEisuClassService(EisuClassService eisuClassService) {
		this.eisuClassService = eisuClassService;
	}

	public String getSystemDeploySchVersion(){
		if (systemDeployService == null) {
			systemDeployService = (SystemDeployService) ContainerManager
					.getComponent("systemDeployService");
		}
		if(VersionType.EISU == systemDeployService.getVersionType()){
			return "eisu";
		}
		return "eis";
	}
}
