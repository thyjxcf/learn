package net.zdsoft.office.remote;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.teacherLeave.service.OfficeTeacherLeaveService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author chens
 * @version 创建时间：2015-3-10 上午11:03:53
 * 
 */
public class RemoteTeacherLeaveAction extends OfficeJsonBaseAction {

	private static final long serialVersionUID = 409443973348389654L;

	private List<OfficeTeacherLeave> officeTeacherLeaveList;
	private OfficeTeacherLeaveService officeTeacherLeaveService;
	private UserService userService;
	private OfficeTeacherLeave officeTeacherLeave;
	private FlowManageService flowManageService;
	private McodedetailService mcodedetailService;
	private UserSetService userSetService;
	private DeptService deptService;
	private OfficeFlowService officeFlowService;

	private int applyStatus;
	private String leaveType;

	private String userId;
	private String unitId;
	private String userName;
	private String deptId;
	private Date startTime;
	private Date endTime;
	private String id;
	private String state;

	private List<Flow> flowList;
	private String taskId;
	private TaskHandlerService taskHandlerService;
	private String taskHandlerSaveJson;
	private boolean isPass;
	private String currentStepId;
	private String flowId;

	private String removeAttachment;

	private int auditStatus;
	private String fromTab;

	private Map<String, String> sumMap;
	private List<User> users;

	private String[] detailTime;

	public String execute() throws Exception {
		User user = userService.getUser(userId);
		userName = user.getRealname();
		return SUCCESS;
	}

	public void applyList() {
		Pagination page = getPage();
		// page.setPageSize(15);
		officeTeacherLeaveList = officeTeacherLeaveService.getApplyList(userId,
				unitId, applyStatus, page);
		Map<String, Mcodedetail> mcodeMap = mcodedetailService
				.getMcodeDetailMap("DM-QJLX");
		JSONArray array = new JSONArray();
		for (OfficeTeacherLeave leave : officeTeacherLeaveList) {
			JSONObject json = new JSONObject();
			json.put("id", leave.getId());
			json.put("beginTime",
					DateUtils.date2String(leave.getLeaveBeignTime(), "MM-dd"));
			json.put("leaveTypeName", mcodeMap.get(leave.getLeaveType())
					.getContent());
			String content = leave.getLeaveReason();
			if (content.length() > 25) {
				content = content.substring(0, 25) + "...";
			}
			json.put("content", content);
			json.put("applyStatus", leave.getApplyStatus());
			array.add(json);
		}

		jsonMap.put("leaveTypeMcodes",
				RemoteOfficeUtils.getMcodeArrays("DM-QJLX"));

		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);

		responseJSON(jsonMap);
	}

	public void applyTeacherLeave() {
		if (StringUtils.isNotEmpty(id)) {
			officeTeacherLeave = officeTeacherLeaveService
					.getOfficeTeacherLeaveById(id);
			if (officeTeacherLeave == null) {
				officeTeacherLeave = new OfficeTeacherLeave();
				User user = userService.getUser(userId);
				officeTeacherLeave.setApplyUserId(user.getId());
				officeTeacherLeave.setApplyUserName(user.getRealname());
				officeTeacherLeave.setUnitId(unitId);
				officeTeacherLeave.setDeptId(user.getDeptid());
			}
		} else {
			officeTeacherLeave = new OfficeTeacherLeave();
			User user = userService.getUser(userId);
			officeTeacherLeave.setApplyUserId(user.getId());
			officeTeacherLeave.setApplyUserName(user.getRealname());
			officeTeacherLeave.setUnitId(unitId);
			officeTeacherLeave.setDeptId(user.getDeptid());
			officeTeacherLeave = officeTeacherLeaveService
					.getOfficeTeacherByOfficeLog(officeTeacherLeave, unitId,
							user.getId());
			flowList = officeTeacherLeave.getFlowList();
		}
		// flowList = flowManageService.getFinishFlowList(unitId,
		// FlowConstant.FLOW_OWNER_UNIT,
		// FlowConstant.OFFICE_LEAVE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		JSONObject json = new JSONObject();
		json.put("id", officeTeacherLeave.getId());
		json.put("unitId", officeTeacherLeave.getUnitId());
		json.put("deptId", officeTeacherLeave.getDeptId());
		json.put("applyUserId", officeTeacherLeave.getApplyUserId());
		json.put("applyUserName", officeTeacherLeave.getApplyUserName());
		json.put("leaveBeginTime", DateUtils.date2String(
				officeTeacherLeave.getLeaveBeignTime(), "yyyy-MM-dd"));
		json.put("leaveEndTime", DateUtils.date2String(
				officeTeacherLeave.getLeaveEndTime(), "yyyy-MM-dd"));
		json.put("days", officeTeacherLeave.getDays());
		json.put("leaveType", officeTeacherLeave.getLeaveType());
		json.put("leaveReason", officeTeacherLeave.getLeaveReason());
		json.put("leaveTypeMcodes", RemoteOfficeUtils.getMcodeArrays("DM-QJLX"));
		json.put("attachmentArray", RemoteOfficeUtils
				.createAttachmentArray(officeTeacherLeave.getAttachments()));
		json.put("noticePersonIds", officeTeacherLeave.getNoticePersonIds());
		json.put("noticePersonNames", officeTeacherLeave.getNoticePersonNames());
		json.put("flowId", officeTeacherLeave.getFlowId());
		JSONArray flowArray = new JSONArray();
		if (CollectionUtils.isEmpty(flowList)) {
			flowList = flowManageService.getFinishFlowList(unitId,
					FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_LEAVE,
					FlowConstant.OFFICE_SUBSYSTEM,
					FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			User user = userService.getUser(userId);
			if (user != null && StringUtils.isNotBlank(user.getDeptid())) {
				List<Flow> flowList2 = flowManageService.getFinishFlowList(
						user.getDeptid(), FlowConstant.FLOW_OWNER_STRING,
						FlowConstant.OFFICE_LEAVE,
						FlowConstant.OFFICE_SUBSYSTEM,
						FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				if (CollectionUtils.isNotEmpty(flowList2)) {
					flowList.addAll(flowList2);
				}
			}
			flowList = officeTeacherLeaveService.filterFlow(flowList, unitId,
					userId);
		}
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

		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	public void saveTeacherLeave() {
		// UploadFile file = null;
		JSONObject json = new JSONObject();
		try {
			List<UploadFile> uploadFileList = StorageFileUtils.handleFiles(
					new String[] {}, 5 * 1024, 50 * 1024);
			officeTeacherLeave.setApplyStatus(Constants.LEAVE_APPLY_SAVE);
			officeTeacherLeave.setCreateUserId(userId);

			JSONObject djson = new JSONObject();
			if (ArrayUtils.isNotEmpty(detailTime)) {
				for (String des : detailTime) {
					String[] desYear = StringUtils.split(des, "_");
					djson.put(desYear[0], desYear[1]);
				}
				officeTeacherLeave.setDescription(djson.toString());
			}

			if (StringUtils.isNotEmpty(officeTeacherLeave.getId())) {
				String[] removeAttachmentArray = removeAttachment.split(",");
				officeTeacherLeaveService.update(officeTeacherLeave,
						uploadFileList, true, removeAttachmentArray);
			} else {
				officeTeacherLeaveService.add(officeTeacherLeave,
						uploadFileList, true);
			}
			json.put("result", 1);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", 0);
		}
		responseJSON(json);
	}

	public void submitTeacherLeave() {
		JSONObject json = new JSONObject();
		try {
			List<UploadFile> uploadFileList = StorageFileUtils.handleFiles(
					new String[] {}, 5 * 1024, 50 * 1024);
			officeTeacherLeave.setCreateUserId(userId);

			String[] removeAttachmentArray = null;
			if (StringUtils.isNotBlank(removeAttachment)) {
				removeAttachmentArray = removeAttachment.split(",");
			}

			JSONObject djson = new JSONObject();
			if (ArrayUtils.isNotEmpty(detailTime)) {
				for (String des : detailTime) {
					String[] desYear = StringUtils.split(des, "_");
					djson.put(desYear[0], desYear[1]);
				}
				officeTeacherLeave.setDescription(djson.toString());
			}
			officeTeacherLeaveService.startFlow(officeTeacherLeave, userId,
					uploadFileList, true, removeAttachmentArray);
			json.put("id", officeTeacherLeave.getId());
			json.put("result", 1);
		} catch (Exception e) {
			json.put("result", 0);
			e.printStackTrace();
			if (e.getCause() != null) {
				if (e.getCause().getMessage() != null) {
					json.put("msg", "提交失败:" + e.getCause().getMessage());
				} else {
					json.put("msg", "未设置流程审核人员");
				}
			} else {
				json.put("msg", "提交失败:" + e.getMessage());
			}
		}
		responseJSON(json);
	}

	public void deleteLeave() {
		JSONObject json = new JSONObject();
		if (StringUtils.isNotEmpty(id)) {
			try {
				officeTeacherLeaveService.delete(new String[] { id });
				json.put("result", 1);
			} catch (Exception e) {
				json.put("result", 0);
			}
		} else {
			json.put("result", 0);
		}
		responseJSON(json);
	}

	public void doDeleteApply() {
		JSONObject json = new JSONObject();
		if (StringUtils.isNotEmpty(id)) {
			try {
				officeTeacherLeaveService.deleteRevoke(id);
				json.put("result", 1);
			} catch (Exception e) {
				json.put("result", 0);
			}
		} else {
			json.put("result", 0);
		}
		responseJSON(json);
	}

	public void auditList() {
		Pagination page = getPage();
		// page.setPageSize(15);
		if (auditStatus == 0) {
			officeTeacherLeaveList = officeTeacherLeaveService.toDoAudit(
					userId, page);
		} else {
			officeTeacherLeaveList = officeTeacherLeaveService.HaveDoAudit(
					userId, false, page);
		}
		Map<String, Mcodedetail> mcodeMap = mcodedetailService
				.getMcodeDetailMap("DM-QJLX");
		JSONArray array = new JSONArray();
		for (OfficeTeacherLeave leave : officeTeacherLeaveList) {
			JSONObject json = new JSONObject();
			json.put("id", leave.getId());
			json.put("beginTime",
					DateUtils.date2String(leave.getLeaveBeignTime(), "MM-dd"));
			json.put("leaveTypeName", mcodeMap.get(leave.getLeaveType())
					.getContent());
			json.put("applyUserName", leave.getApplyUserName());
			String content = leave.getLeaveReason();
			if (content.length() > 25) {
				content = content.substring(0, 25) + "...";
			}
			json.put("content", content);
			json.put("applyStatus", leave.getApplyStatus());
			json.put("taskId", leave.getTaskId());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	public void auditTeacherLeave() {
		officeTeacherLeave = officeTeacherLeaveService
				.getOfficeTeacherLeaveById(id);
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService
				.getTaskHandlerShow(taskId, userId);
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(userId);
		taskHandlerSave.setCurrentUnitId(unitId);
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(userId);
		// TODO
		// textComment=officeTeacherLeaveService.getTextComment(getUnitId(),user.getId());

		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject jsonStr = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = jsonStr.toString();
		officeTeacherLeave.setTaskName(taskHandlerSave.getCurrentTask()
				.getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeTeacherLeave.getFlowId();

		String photoUrl = userSetService.getUserPhotoUrl(officeTeacherLeave
				.getApplyUserId());
		String deptName = "";
		if (user != null) {
			Dept dept = deptService.getDept(user.getDeptid());
			if (dept != null) {
				deptName = dept.getDeptname();
			}
		}

		JSONObject json = new JSONObject();
		json.put("id", officeTeacherLeave.getId());
		json.put("deptName", deptName);
		json.put("applyUserName", officeTeacherLeave.getApplyUserName());
		json.put("photoUrl", photoUrl);
		json.put("leaveBeginTime", DateUtils.date2String(
				officeTeacherLeave.getLeaveBeignTime(), "yyyy-MM-dd"));
		json.put("leaveEndTime", DateUtils.date2String(
				officeTeacherLeave.getLeaveEndTime(), "yyyy-MM-dd"));
		json.put("days", officeTeacherLeave.getDays());
		Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-QJLX",
				officeTeacherLeave.getLeaveType());
		json.put("leaveTypeName", mcodedetail.getContent());
		json.put("leaveReason", officeTeacherLeave.getLeaveReason());
		json.put("noticePersonNames", officeTeacherLeave.getNoticePersonNames());
		json.put("attachmentArray", RemoteOfficeUtils
				.createAttachmentArray(officeTeacherLeave.getAttachments()));
		json.put("taskHandlerSaveJson", taskHandlerSaveJson);
		json.put("taskName", taskHandlerSave.getCurrentTask().getTaskName());
		json.put("userName", userName);
		json.put("currentStepId", currentStepId);
		json.put("flowId", flowId);
		json.put("hisTaskCommentArray", RemoteOfficeUtils
				.createHisTaskCommentArray(officeTeacherLeave.getHisTaskList(), officeTeacherLeave.getUnitId(), flowId));
		json.put("times", officeTeacherLeave.getTimes());

		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	public void auditPassLeave() {
		JSONObject json = new JSONObject();
		try {
			JSONObject object = new JSONObject()
					.fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave = (TaskHandlerSave) JSONObject
					.toBean(object, TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeTeacherLeaveService.passFlow(isPass(), taskHandlerSave, id, currentStepId);
			json.put("result", 1);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result", 0);
			if (e.getCause() != null) {
				json.put("msg", "审核失败:" + e.getCause().getMessage());
			} else {
				json.put("msg", "审核失败:" + e.getMessage());
			}
		}
		responseJSON(json);
	}

	public void applyDetail() {
		officeTeacherLeave = officeTeacherLeaveService
				.getOfficeTeacherLeaveById(id);
		if (officeTeacherLeave == null) {
			officeTeacherLeave = new OfficeTeacherLeave();
		}

		String photoUrl = userSetService.getUserPhotoUrl(officeTeacherLeave
				.getApplyUserId());
		User user = userService.getUser(officeTeacherLeave.getApplyUserId());
		String deptName = "";
		if (user != null) {
			Dept dept = deptService.getDept(user.getDeptid());
			if (dept != null) {
				deptName = dept.getDeptname();
			}
		}

		JSONObject json = new JSONObject();
		json.put("applyUserName", officeTeacherLeave.getApplyUserName());
		json.put("deptName", deptName);
		json.put("photoUrl", photoUrl);
		json.put("leaveBeginTime", DateUtils.date2String(
				officeTeacherLeave.getLeaveBeignTime(), "yyyy-MM-dd"));
		json.put("leaveEndTime", DateUtils.date2String(
				officeTeacherLeave.getLeaveEndTime(), "yyyy-MM-dd"));
		json.put("days", officeTeacherLeave.getDays());
		Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-QJLX",
				officeTeacherLeave.getLeaveType());
		json.put("leaveTypeName", mcodedetail.getContent());
		json.put("leaveReason", officeTeacherLeave.getLeaveReason());
		json.put("noticePersonNames", officeTeacherLeave.getNoticePersonNames());
		json.put("attachmentArray", RemoteOfficeUtils
				.createAttachmentArray(officeTeacherLeave.getAttachments()));
		json.put("hisTaskCommentArray", RemoteOfficeUtils
				.createHisTaskCommentArray(officeTeacherLeave.getHisTaskList(), officeTeacherLeave.getUnitId(), officeTeacherLeave.getFlowId()));
		json.put("applyStatus", officeTeacherLeave.getApplyStatus());
		json.put("times", officeTeacherLeave.getTimes());
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	/**
	 * 获取下一步处理人 （流程相关模块通用）
	 */
	public void getFlowAuditUser() {
		// state 0-未进入流程；1-进入流程
		try {
			String result = officeFlowService.getNextTaskInfo(unitId, userId,
					state, flowId, taskId, null);
			// {"result_array":[{"userTaskId":"UserTask_7001_1","taskName":"1",
			// "assigneeId":"402896C6544B054301544B0AB5380002,402896C654318CE80154319093230002,67F2098476534F408DEEDECD09C020BE",
			// "assigneeName":"杜拉拉,钱多多,杜美美"}],"result_status":1}

			JSONArray rArray = new JSONArray();
			Set<String> userIds = new HashSet<String>();
			if (StringUtils.isNotBlank(result)) {
				JSONObject json = JSONObject.fromObject(result);
				if (json.containsKey("result_status")
						&& json.getInt("result_status") == 1) {
					JSONArray array = json.getJSONArray("result_array");
					for (int i = 0; i < array.size(); i++) {
						JSONObject fj = array.getJSONObject(i);
						String taskName = fj.getString("taskName");
						String assigneeId = fj.getString("assigneeId");
						String assigneeName = fj.getString("assigneeName");

						JSONObject rJson = new JSONObject();
						rJson.put("taskName", taskName);
						if (StringUtils.isNotBlank(assigneeId)
								&& StringUtils.isNotBlank(assigneeName)) {
							String[] assigneeIds = assigneeId.split(",");
							String[] assigneeNames = assigneeName.split(",");

							JSONArray userArray = new JSONArray();
							for (int j = 0; j < assigneeIds.length; j++) {
								//默认最多只显示三个
								if(j>=3)
									break;
								
								JSONObject userJson = new JSONObject();
								userIds.add(assigneeIds[j]);
								userJson.put("assigneeId", assigneeIds[j]);
								if (j < assigneeNames.length) {
									userJson.put("assigneeName",
											assigneeNames[j]);
								} else {
									break;
								}

								userArray.add(userJson);
							}

							rJson.put("userArray", userArray);
						}

						rArray.add(rJson);
					}
				}
			}

			Map<String, String> map = userSetService.getUserPhotoMap(userIds
					.toArray(new String[0]));
			for (int i = 0; i < rArray.size(); i++) {
				JSONObject o = rArray.getJSONObject(i);
				JSONArray a = o.getJSONArray("userArray");
				for (int j = 0; j < a.size(); j++) {
					String userId = a.getJSONObject(j).getString("assigneeId");
					if (map.containsKey(userId)) {
						a.getJSONObject(j).put("photoUrl", map.get(userId));
					}
				}
			}

			System.out.println(rArray.toString());

			jsonMap.put(getListObjectName(), rArray);
			jsonMap.put("result", 1);
			responseJSON(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("result", 0);
			responseJSON(jsonMap);
		}
	}

	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<OfficeTeacherLeave> getOfficeTeacherLeaveList() {
		return officeTeacherLeaveList;
	}

	public void setOfficeTeacherLeaveList(
			List<OfficeTeacherLeave> officeTeacherLeaveList) {
		this.officeTeacherLeaveList = officeTeacherLeaveList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setOfficeTeacherLeaveService(
			OfficeTeacherLeaveService officeTeacherLeaveService) {
		this.officeTeacherLeaveService = officeTeacherLeaveService;
	}

	public OfficeTeacherLeave getOfficeTeacherLeave() {
		return officeTeacherLeave;
	}

	public void setOfficeTeacherLeave(OfficeTeacherLeave officeTeacherLeave) {
		this.officeTeacherLeave = officeTeacherLeave;
	}

	public int getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
	}

	public List<Flow> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
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

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
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

	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}

	public Map<String, String> getSumMap() {
		return sumMap;
	}

	public void setSumMap(Map<String, String> sumMap) {
		this.sumMap = sumMap;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getRemoveAttachment() {
		return removeAttachment;
	}

	public void setRemoveAttachment(String removeAttachment) {
		this.removeAttachment = removeAttachment;
	}

	public String[] getDetailTime() {
		return detailTime;
	}

	public void setDetailTime(String[] detailTime) {
		this.detailTime = detailTime;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
