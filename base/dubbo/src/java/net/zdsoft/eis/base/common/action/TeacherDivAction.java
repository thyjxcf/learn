package net.zdsoft.eis.base.common.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;

public class TeacherDivAction extends ObjectDivAction<Teacher> {

	private static final long serialVersionUID = -2208233936295371156L;

	private TeacherService teacherService;

	private String submitMethod;
	private String closeMethod;
	private String cancelMethod;
	private String queryType;// 按部门还是按院系
	private String teacherType;// 教师状态
	private String eusing; //是否在职  在1  不在0
	private List<Teacher> teacherList = new ArrayList<Teacher>(); // 教师列表

	public TeacherDivAction() {
		setUrl("/common/getTeacherData.action");
		setShowLetterIndex(true);
	}

	@Override
	public List<Teacher> getDatasByUnitId() {
		teacherList = teacherService.getTeachers(this.getLoginInfo()
				.getUnitID());
		return teacherList;
	}

	@Override
	public List<Teacher> getDatasByGroupId() {
		if ("institute".equals(queryType)) {
			teacherList = teacherService.getTeacherByInstituteId(getUnitId(),
					groupId);
		} else if ("exception".equals(queryType)) {
			teacherList = teacherService.getExceptionTeachersByDeptId(
					getUnitId(), groupId);
		} else {
			if (StringUtils.isNotBlank(teacherType)
					&& "normal".equals(teacherType)) {
				if (StringUtils.isNotBlank(groupId)) {
					teacherList = teacherService.getTeachersByDeptIdOrUserState(
							groupId);
				} else {
					teacherList = teacherService.getTeachersByUserState(
							getUnitId());
				}
			} else {
				teacherList = teacherService.getTeachers(getUnitId(), groupId);
			}
		}
		//剔除非在职教师
		if("1".equals(eusing)){
			List<Teacher> newTeachers=new ArrayList<Teacher>();
			for (Teacher teacher : teacherList) {
				if (StringUtils.isNotBlank(teacher.getEusing()))
					// 剔除非在职教师
					if (teacher.getEusing().equals("11")
							|| teacher.getEusing().equals("10")
							|| teacher.getEusing().equals("12")
							|| teacher.getEusing().equals("13")
							|| teacher.getEusing().equals("14") || teacher
							.getEusing().equals("18")) {
						newTeachers.add(teacher);
					}
			}
			teacherList = newTeachers;
		}
		return teacherList;
	}

	@Override
	protected List<Teacher> getDatasFaintness() {
		teacherList = teacherService.getTeachersByFaintness(getLoginInfo()
				.getUnitID(), queryObjectName, queryObjectCode);

		return teacherList;
	}

	protected void toObject(Teacher teacher, SimpleObject object) {
		if (teacher == null) {
			return;
		}
		if (object == null) {
			return;
		}

		object.setId(teacher.getId());
		object.setObjectCode(teacher.getTchId());
		object.setObjectName(teacher.getName());
		object.setUnitiveCode("");
		if ("institute".equals(queryType)) {
			object.setGroupId(teacher.getInstituteId());
		} else {
			object.setGroupId(teacher.getDeptid());
		}
		object.setUnitId(teacher.getUnitid());
	}

	public String getTdName() {
		return getNameObjectId();
	}

	public void setTdName(String tdName) {
		setNameObjectId(tdName);
	}

	public String getIdName() {
		return getIdObjectId();
	}

	public void setIdName(String idName) {
		setIdObjectId(idName);
	}

	public List<Teacher> getTeacherList() {
		return teacherList;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public String getCloseMethod() {
		return closeMethod;
	}

	public void setCloseMethod(String closeMethod) {
		this.closeMethod = closeMethod;
	}

	public String getSubmitMethod() {
		return submitMethod;
	}

	public void setSubmitMethod(String submitMethod) {
		this.submitMethod = submitMethod;
	}

	public String getCancelMethod() {
		return cancelMethod;
	}

	public void setCancelMethod(String cancelMethod) {
		this.cancelMethod = cancelMethod;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getTeacherType() {
		return teacherType;
	}

	public void setTeacherType(String teacherType) {
		this.teacherType = teacherType;
	}

	public String getEusing() {
		return eusing;
	}

	public void setEusing(String eusing) {
		this.eusing = eusing;
	}
	
	
}
