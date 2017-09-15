package net.zdsoft.office.remote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
import net.zdsoft.office.attendLecture.service.OfficeAttendLectureService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Administrator
 *
 */
public class RemoteAttendLectureAction extends OfficeJsonBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FlowManageService flowManageService;
	private UserService userService;
	private UserSetService userSetService;
	private DeptService deptService;
	private OfficeAttendLectureService officeAttendLectureService;
	private GradeService gradeService;
	private BasicClassService basicClassService;
	private McodedetailService mcodedetailService;

	private OfficeAttendLecture officeAttendLecture;

	private String userId;
	private String unitId;
	private String userName;
	private String id;

	private List<Flow> flowList;
	private String taskId;
	private TaskHandlerService taskHandlerService;
	private String taskHandlerSaveJson;
	private boolean isPass;
	private String currentStepId;
	private String flowId;

	public void applyAttendLecture() {
		if (StringUtils.isNotEmpty(id)) {
			officeAttendLecture = officeAttendLectureService
					.getOfficeAttendLectureById(id);
			if (officeAttendLecture == null) {
				officeAttendLecture = new OfficeAttendLecture();
				officeAttendLecture.setApplyUserId(userId);
				officeAttendLecture.setUnitId(unitId);
			}
		} else {
			officeAttendLecture = new OfficeAttendLecture();
			officeAttendLecture.setApplyUserId(userId);
			officeAttendLecture.setUnitId(unitId);
		}
		flowList = flowManageService.getFinishFlowList(unitId,
				FlowConstant.FLOW_OWNER_UNIT,
				FlowConstant.OFFICE_ATTEND_LECTURE,
				FlowConstant.OFFICE_SUBSYSTEM,
				FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		JSONObject json = new JSONObject();
		json.put("id", officeAttendLecture.getId());
		json.put("unitId", officeAttendLecture.getUnitId());
		json.put("applyUserId", officeAttendLecture.getApplyUserId());
		json.put("attendDate", DateUtils.date2String(
				officeAttendLecture.getAttendDate(), "yyyy-MM-dd"));
		json.put("attendPeriod", officeAttendLecture.getAttendPeriod());
		json.put("attendPeriodNum", officeAttendLecture.getAttendPeriodNum());
		json.put("attendPeriodMcodes",
				RemoteOfficeUtils.getMcodeArrays("DM-TKSD"));
		json.put("attendPeriodNumMcodes",
				RemoteOfficeUtils.getMcodeArrays("DM-TKJC"));
		json.put("classId", officeAttendLecture.getClassId());
		json.put("gradeId", officeAttendLecture.getGradeId());
		json.put("projectName", officeAttendLecture.getProjectName());
		json.put("projectContent", officeAttendLecture.getProjectContent());
		json.put("projectOpinion", officeAttendLecture.getProjectOpinion());
		json.put("subjectName", officeAttendLecture.getSubjectName());
		json.put("teacherName", officeAttendLecture.getTeacherName());
		json.put("flowId", officeAttendLecture.getFlowId());

		JSONArray flowArray = new JSONArray();
		if (CollectionUtils.isNotEmpty(flowList)) {
			for (Flow flow : flowList) {
				JSONObject flowObject = new JSONObject();
				flowObject.put("flowId", flow.getFlowId());
				flowObject.put("flowName", flow.getFlowName());
				flowObject.put("isDefaultFlow", flow.isDefaultFlow());
				flowArray.add(flowObject);
			}
		}
		json.put("flowArray", flowArray);
		List<Grade> grades = gradeService.getGrades(unitId);
		JSONArray gradeArray = new JSONArray();
		if (CollectionUtils.isNotEmpty(grades)) {
			for (Grade grade : grades) {
				JSONObject gradeObject = new JSONObject();
				gradeObject.put("gradeId", grade.getId());
				gradeObject.put("gradeName", grade.getGradename());
				gradeArray.add(gradeObject);
			}
		}
		List<BasicClass> classes = basicClassService.getClasses(unitId);
		JSONArray classArray = new JSONArray();
		if (CollectionUtils.isNotEmpty(classes)) {
			for (BasicClass basicClass : classes) {
				JSONObject classObject = new JSONObject();
				classObject.put("classId", basicClass.getId());
				classObject.put("className", basicClass.getClassnamedynamic());
				classObject.put("gradeId", basicClass.getGradeId());
				classArray.add(classObject);
			}
		}
		json.put("gradeArray", gradeArray);
		json.put("classArray", classArray);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	public void submitAttendLecture() {
		try {
			officeAttendLecture.setApplyUserId(userId);
			officeAttendLecture.setCreateTime(new Date());
			officeAttendLecture.setState(String
					.valueOf(Constants.LEAVE_APPLY_FLOWING));
			officeAttendLectureService.startFlow(officeAttendLecture, userId);
			jsonMap.put("result", 1);
		} catch (Exception e) {
			jsonMap.put("result", 0);
			e.printStackTrace();
			if (e.getCause() != null) {
				if (e.getCause().getMessage() != null) {
					jsonMap.put("msg", "提交失败:" + e.getCause().getMessage());
				} else {
					jsonMap.put("msg", "未设置流程审核人员");
				}
			} else {
				jsonMap.put("msg", "提交失败:" + e.getMessage());
			}
		}
		responseJSON(jsonMap);
	}

	public void auditAttendLecture() {
		officeAttendLecture = officeAttendLectureService
				.getOfficeAttendLectureById(id);
		if (officeAttendLecture == null) {
			officeAttendLecture = new OfficeAttendLecture();
		}
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService
				.getTaskHandlerShow(taskId, userId);
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(userId);
		taskHandlerSave.setCurrentUnitId(unitId);
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(userId);
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		taskHandlerSave.setComment(comment);
		JSONObject jsonStr = JSONObject.fromObject(taskHandlerSave);
		taskHandlerSaveJson = jsonStr.toString();
		officeAttendLecture.setTaskName(taskHandlerSave.getCurrentTask()
				.getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeAttendLecture.getFlowId();

		User user1 = userService.getUser(officeAttendLecture.getApplyUserId());
		String photoUrl = userSetService.getUserPhotoUrl(officeAttendLecture
				.getApplyUserId());
		String deptName = "";
		if (user1 != null) {
			Dept dept = deptService.getDept(user1.getDeptid());
			userName = user1.getRealname();
			if (dept != null) {
				deptName = dept.getDeptname();
			}
		}

		JSONObject json = new JSONObject();
		json.put("id", officeAttendLecture.getId());
		json.put("unitId", officeAttendLecture.getUnitId());
		json.put("photoUrl", photoUrl);
		json.put("deptName", deptName);
		json.put("applyUserName", userName);
		json.put("attendDate", DateUtils.date2String(
				officeAttendLecture.getAttendDate(), "yyyy-MM-dd"));
		Mcodedetail mcodedetail1 = mcodedetailService.getMcodeDetail("DM-TKSD",
				officeAttendLecture.getAttendPeriod());
		Mcodedetail mcodedetail2 = mcodedetailService.getMcodeDetail("DM-TKJC",
				officeAttendLecture.getAttendPeriodNum());
		json.put("attendPeriod",
				mcodedetail1.getContent() + " " + mcodedetail2.getContent());
		Grade grade = gradeService.getGrade(officeAttendLecture.getGradeId());
		if (grade != null) {
			json.put("gradeName", grade.getGradename());
		} else {
			json.put("gradeName", "");
		}
		BasicClass basicClass = basicClassService.getClass(officeAttendLecture
				.getClassId());
		if (basicClass != null) {
			json.put("className", basicClass.getClassnamedynamic());
		} else {
			json.put("className", "");
		}
		json.put("projectName", officeAttendLecture.getProjectName());
		json.put("projectContent", officeAttendLecture.getProjectContent());
		json.put("projectOpinion", officeAttendLecture.getProjectOpinion());
		json.put("subjectName", officeAttendLecture.getSubjectName());
		json.put("teacherName", officeAttendLecture.getTeacherName());
		json.put("hisTaskCommentArray",
				RemoteOfficeUtils.createHisTaskCommentArray(officeAttendLecture
						.getHisTaskList()));
		json.put("applyStatus", officeAttendLecture.getState());
		json.put("attachmentArray", RemoteOfficeUtils
				.createAttachmentArray(new ArrayList<Attachment>()));
		json.put("currentStepId", currentStepId);
		json.put("flowId", flowId);
		json.put("taskHandlerSaveJson", taskHandlerSaveJson);
		json.put("taskName", taskHandlerSave.getCurrentTask().getTaskName());
		json.put("hisTaskCommentArray",
				RemoteOfficeUtils.createHisTaskCommentArray(officeAttendLecture
						.getHisTaskList()));

		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	public void auditPassAttendLecture() {
		try {
			JSONObject object = JSONObject.fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave = (TaskHandlerSave) JSONObject
					.toBean(object, TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeAttendLectureService.doAudit(isPass(), taskHandlerSave, id);
			jsonMap.put("result", 1);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("result", 0);
			if (e.getCause() != null) {
				jsonMap.put("msg", "审核失败:" + e.getCause().getMessage());
			} else {
				jsonMap.put("msg", "审核失败:" + e.getMessage());
			}
		}
		responseJSON(jsonMap);
	}

	public void applyDetail() {
		officeAttendLecture = officeAttendLectureService
				.getOfficeAttendLectureById(id);
		if (officeAttendLecture == null) {
			officeAttendLecture = new OfficeAttendLecture();
		}

		String photoUrl = userSetService.getUserPhotoUrl(officeAttendLecture
				.getApplyUserId());
		User user = userService.getUser(officeAttendLecture.getApplyUserId());
		String deptName = "";
		if (user != null) {
			userName = user.getRealname();
			Dept dept = deptService.getDept(user.getDeptid());
			if (dept != null) {
				deptName = dept.getDeptname();
			}
		}

		JSONObject json = new JSONObject();
		json.put("id", officeAttendLecture.getId());
		json.put("unitId", officeAttendLecture.getUnitId());
		json.put("photoUrl", photoUrl);
		json.put("deptName", deptName);
		json.put("applyUserName", userName);
		// json.put("applyUserId", officeAttendLecture.getApplyUserId());
		json.put("attendDate", DateUtils.date2String(
				officeAttendLecture.getAttendDate(), "yyyy-MM-dd"));
		Mcodedetail mcodedetail1 = mcodedetailService.getMcodeDetail("DM-TKSD",
				officeAttendLecture.getAttendPeriod());
		Mcodedetail mcodedetail2 = mcodedetailService.getMcodeDetail("DM-TKJC",
				officeAttendLecture.getAttendPeriodNum());
		json.put("attendPeriod",
				mcodedetail1.getContent() + " " + mcodedetail2.getContent());
		// json.put("attendPeriodNum",
		// officeAttendLecture.getAttendPeriodNum());
		Grade grade = gradeService.getGrade(officeAttendLecture.getGradeId());
		if (grade != null) {
			json.put("gradeName", grade.getGradename());
		} else {
			json.put("gradeName", "");
		}
		BasicClass basicClass = basicClassService.getClass(officeAttendLecture
				.getClassId());
		if (basicClass != null) {
			json.put("className", basicClass.getClassnamedynamic());
		} else {
			json.put("className", "");
		}
		json.put("projectName", officeAttendLecture.getProjectName());
		json.put("projectContent", officeAttendLecture.getProjectContent());
		json.put("projectOpinion", officeAttendLecture.getProjectOpinion());
		json.put("subjectName", officeAttendLecture.getSubjectName());
		json.put("teacherName", officeAttendLecture.getTeacherName());
		json.put("hisTaskCommentArray",
				RemoteOfficeUtils.createHisTaskCommentArray(officeAttendLecture
						.getHisTaskList()));
		json.put("applyStatus", officeAttendLecture.getState());
		json.put("attachmentArray", RemoteOfficeUtils
				.createAttachmentArray(new ArrayList<Attachment>()));
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public OfficeAttendLecture getOfficeAttendLecture() {
		return officeAttendLecture;
	}

	public void setOfficeAttendLecture(OfficeAttendLecture officeAttendLecture) {
		this.officeAttendLecture = officeAttendLecture;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Flow> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskHandlerSaveJson() {
		return taskHandlerSaveJson;
	}

	public void setTaskHandlerSaveJson(String taskHandlerSaveJson) {
		this.taskHandlerSaveJson = taskHandlerSaveJson;
	}

	public String getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setOfficeAttendLectureService(
			OfficeAttendLectureService officeAttendLectureService) {
		this.officeAttendLectureService = officeAttendLectureService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

}
