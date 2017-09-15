package net.zdsoft.eis.base.common.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eis.base.simple.entity.SimpleStudent;
import net.zdsoft.eis.base.simple.service.SimpleStudentService;

public class StudentDivAction extends ObjectDivAction<SimpleStudent> {

	private static final long serialVersionUID = -7734117014607973171L;

	protected List<SimpleStudent> studentList;
	private String submitMethod;
	private String closeMethod;
	private String cancelMethod;
	private String studentName;

	protected SimpleStudentService simpleStudentService;

	public StudentDivAction() {
		setShowLetterIndex(true);
	}

	@Override
	public List<SimpleStudent> getDatasByGroupId() {
		List<SimpleStudent> list = new ArrayList<SimpleStudent>();
		studentList = new ArrayList<SimpleStudent>();
		if (!(StringUtils.isBlank(groupId))) {
			list = simpleStudentService.getStudents(groupId);
		}
		if (StringUtils.isNotBlank(studentName)) {
			for (SimpleStudent stu : list) {
				if (stu.getStuname().indexOf(studentName) != -1) {
					studentList.add(stu);
				}
			}
		} else {
			studentList = list;
		}
		return studentList;
	}

	/**
	 * 左匹配姓名 学籍号查询学生信息
	 * 
	 * @return
	 */
	@Override
	public List<SimpleStudent> getDatasFaintness() {
		Unit unit = unitService.getUnit(getLoginInfo().getUnitID());
		String unionid = unit.getUnionid();

		if (codeType == 1) {
			studentList = simpleStudentService.queryStudentsFaintness(
					queryObjectCode, queryObjectName, unionid);
		} else {
			studentList = simpleStudentService
					.getStudentsByFaintnessStudentCode(getLoginInfo()
							.getUnitID(), queryObjectName, queryObjectCode);

		}
		return studentList;
	}

	protected void toObject(SimpleStudent stu, SimpleObject object) {
		if (stu == null) {
			return;
		}
		if (object == null) {
			return;
		}

		object.setId(stu.getId());
		object.setObjectCode(stu.getStucode());
		object.setObjectName(stu.getStuname());
		object.setUnitiveCode(stu.getUnitivecode());
		object.setGroupId(stu.getClassid());
		object.setUnitId(stu.getSchid());
	}

	/*
	 * set and get
	 */
	public String getClassId() {
		return groupId;
	}

	public void setClassId(String classId) {
		this.groupId = classId;
	}

	public void setSimpleStudentService(
			SimpleStudentService simpleStudentService) {
		this.simpleStudentService = simpleStudentService;
	}

	public String getSubmitMethod() {
		return submitMethod;
	}

	public void setSubmitMethod(String submitMethod) {
		this.submitMethod = submitMethod;
	}

	public String getCloseMethod() {
		return closeMethod;
	}

	public void setCloseMethod(String closeMethod) {
		this.closeMethod = closeMethod;
	}

	public String getCancelMethod() {
		return cancelMethod;
	}

	public void setCancelMethod(String cancelMethod) {
		this.cancelMethod = cancelMethod;
	}

	public List<SimpleStudent> getStudentList() {
		return studentList;
	}

	public String getQueryStudentCode() {
		return queryObjectCode;
	}

	public void setQueryStudentCode(String queryStudentCode) {
		this.queryObjectCode = queryStudentCode;
	}

	public String getQueryStudentName() {
		return queryObjectName;
	}

	public void setQueryStudentName(String queryStudentName) {
		this.queryObjectName = queryStudentName;
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

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
}
