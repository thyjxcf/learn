package net.zdsoft.office.convertflow.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.WeikeAppConstant;
import net.zdsoft.eis.base.subsystemcall.entity.OfficeMsgSendingDto;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.component.push.client.WeikePushClient;
import net.zdsoft.eis.component.push.entity.WKPushParm;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.eis.remote.enums.WeikeAppEnum;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.service.OfficeFlowSendMsgService;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.dailyoffice.entity.OfficeJtgoOut;
import net.zdsoft.office.enums.WeikeAppUrlEnum;
import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;
import net.zdsoft.office.msgcenter.entity.OfficeBusinessJump;
import net.zdsoft.office.msgcenter.service.OfficeBusinessJumpService;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class OfficeFlowSendMsgServiceImpl implements OfficeFlowSendMsgService {
	
	private UserService userService;
	private UnitService unitService;
	private OfficeSubsystemService officeSubsystemService;
	private TaskHandlerService taskHandlerService;
	private McodedetailService mcodedetailService;
	private OfficeBusinessJumpService officeBusinessJumpService;
	
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setOfficeSubsystemService(
			OfficeSubsystemService officeSubsystemService) {
		this.officeSubsystemService = officeSubsystemService;
	}
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}
	
	public void setOfficeBusinessJumpService(
			OfficeBusinessJumpService officeBusinessJumpService) {
		this.officeBusinessJumpService = officeBusinessJumpService;
	}

	//工作流启动流程时发送信息提醒
	public void startFlowSendMsg(Object obj, Integer type){
		try {
			User user = null;
			String flowId = "";
			String title = "";
			String content = "";
			Integer msgType = 0;
			Set<String> userIds = new HashSet<String>();
			if(ConvertFlowConstants.OFFICE_GO_OUT == type){//外出
				OfficeGoOut ent = (OfficeGoOut)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_GO_OUT;
				title = "外出申请信息提醒";
				content = "您好！您有外出申请需要处理。申请人："+user.getRealname() 
						+ ",开始时间：" + DateUtils.date2String(ent.getBeginTime(), "yyyy-MM-dd HH:mm")
						+ ",结束时间：" + DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd HH:mm")+ "。";
			}else if(ConvertFlowConstants.OFFICE_TEACHER_LEAVE == type){//请假
				OfficeTeacherLeave ent = (OfficeTeacherLeave)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_LEAVE;
				title = "请假申请信息提醒";
				content = "您好！您有请假申请需要处理。申请人："+user.getRealname() 
						+ ",开始时间：" + DateUtils.date2String(ent.getLeaveBeignTime(), "yyyy-MM-dd")
						+ ",结束时间：" + DateUtils.date2String(ent.getLeaveEndTime(), "yyyy-MM-dd")+ "。";
			}else if(ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE == type){//教师考勤
				OfficeAttendanceColckApply ent = (OfficeAttendanceColckApply)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_ATTENDANCE;
				title = "考勤补卡申请信息提醒";
				content = "您好！您有补卡申请需要处理。申请人："+user.getRealname()
						+ ",补卡班次：" + DateUtils.date2String(ent.getAttenceDate(), "yyyy-MM-dd")
						+ "  " +ent.getTypeWeekTime()+ "。";
			}else if(ConvertFlowConstants.OFFICE_EXPENSE == type){//报销
				OfficeExpense ent = (OfficeExpense)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_EXPENSE;
				title = "报销申请信息提醒";
				DecimalFormat df=new DecimalFormat("#.00");
				content = "您好！您有报销申请需要处理。申请人："+user.getRealname() 
						+ ",报销金额：" +df.format(ent.getExpenseMoney()!=null?ent.getExpenseMoney():0)+ "。";
			}else if(ConvertFlowConstants.OFFICE_EVECTION == type){//出差
				OfficeBusinessTrip ent = (OfficeBusinessTrip)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_EVECTION;
				title = "出差申请信息提醒";
				content = "您好！您有出差申请需要处理。申请人："+user.getRealname() 
						+ ",开始时间：" + DateUtils.date2String(ent.getBeginTime(), "yyyy-MM-dd")
						+ ",结束时间：" + DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd")+ "。";
			}else if(ConvertFlowConstants.OFFICE_ATTENDLECTURE == type){//听课
				OfficeAttendLecture ent = (OfficeAttendLecture)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_ATTENDLECTURE;
				title = "听课申请信息提醒";
				content = "您好！您有听课申请需要处理。申请人："+user.getRealname() 
						+ ",听课时间：" + DateUtils.date2String(ent.getAttendDate(), "yyyy-MM-dd")
						+ ",学科："+ ent.getSubjectName()
						+ "。";
			}else if(ConvertFlowConstants.OFFICE_JTGO_OUT == type){//集体外出
				OfficeJtgoOut ent = (OfficeJtgoOut)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_GO_OUT;
				Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-JTWC", ent.getOutType());
				String typeName = "";
				if(mcodedetail != null){
					typeName = ",外出类型：" + mcodedetail.getContent();
				}
				title = "集体外出申请信息提醒";
				content = "您好！您有集体外出申请需要处理。申请人："+user.getRealname() 
						+ typeName
						+ ",外出时间：" + ent.getDays()
						+ ",外出人员：" + ent.getTripPerson()+ "。";
			}else if(ConvertFlowConstants.OFFICE_NEWJTGO_OUT == type){//集体外出管理
				OfficeJtGoout ent = (OfficeJtGoout)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_JTGOOUT;
				title = "集体外出管理申请信息提醒";
				String typeName="";
				if(StringUtils.isNotBlank(ent.getType())&&StringUtils.equals("1", ent.getType())){
					typeName=",外出类型：学生集体活动";
				}else{
					typeName=",外出类型：教师集体培训";
				}
				content = "您好！您有集体外出管理申请需要处理。申请人："+user.getRealname() 
						+ typeName
						+ ",开始时间：" + DateUtils.date2String(ent.getStartTime(), "yyyy-MM-dd")
						+ ",结束时间：" + DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd")+ "。";
			}else{
				return;
			}
			
			//获取下一步审核人信息
			Unit unit = unitService.getUnit(user.getUnitid());
			List<TaskDescription> list = taskHandlerService.getTodoTasks(flowId);
			for(TaskDescription t : list){
				userIds = new HashSet<String>();
				if(CollectionUtils.isEmpty(t.getCandidateUsers()))
					continue;
				for(String userId : t.getCandidateUsers()){
					userIds.add(userId);
				}
				String msgId = officeSubsystemService.sendMsgDetail(user, unit, title, content, content, false, msgType, userIds.toArray(new String[0]), null);
				this.pushMsgUrl(obj, type, msgId, t.getTaskId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
				startFlowSendMsgToWeiKe(obj, type);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void completeTaskSendMsg(String curUserId, boolean isPass, Object obj, Integer type, TaskHandlerResult result){
		try {
			String passStr = isPass?"已经审核通过":"审核未通过";
			String title = "";
			String content = "";
			Integer msgType = 0;
			User user = null;
			
			String auditTitle = "";
			String auditContent = "";
			String flowId = "";
			if(ConvertFlowConstants.OFFICE_GO_OUT == type){//外出
				OfficeGoOut ent = (OfficeGoOut)obj;
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_GO_OUT;
				title = "外出申请信息提醒";
				content = "您好！您有外出申请需要处理。申请人："+user.getRealname() 
						+ ",开始时间：" + DateUtils.date2String(ent.getBeginTime(), "yyyy-MM-dd HH:mm")
						+ ",结束时间：" + DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd HH:mm")
						+ "。";
				
				auditTitle = "外出审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr+ "。";
			}else if(ConvertFlowConstants.OFFICE_TEACHER_LEAVE == type){//请假
				OfficeTeacherLeave ent = (OfficeTeacherLeave)obj;
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_LEAVE;
				title = "请假申请信息提醒";
				content = "您好！您有请假申请需要处理。申请人："+user.getRealname() 
						+ ",开始时间：" + DateUtils.date2String(ent.getLeaveBeignTime(), "yyyy-MM-dd")
						+ ",结束时间：" + DateUtils.date2String(ent.getLeaveEndTime(), "yyyy-MM-dd")
						+ "。";
				
				auditTitle = "请假审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr+ "。";
			}else if(ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE == type){//教师考勤
				OfficeAttendanceColckApply ent = (OfficeAttendanceColckApply)obj;
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_ATTENDANCE;
				title = "考勤补卡申请信息提醒";
				content = "您好！您有补卡申请需要处理。申请人："+user.getRealname() 
						+ "补卡班次：" + DateUtils.date2String(ent.getAttenceDate(), "yyyy-MM-dd")
						+ "  " + ent.getTypeWeekTime()
						+ "。";
				
				auditTitle = "补卡审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr+ "。";
			}else if(ConvertFlowConstants.OFFICE_EXPENSE == type){//报销
				OfficeExpense ent = (OfficeExpense)obj;
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_EXPENSE;
				title = "报销申请信息提醒";
				DecimalFormat df=new DecimalFormat("#.00");
				content = "您好！您有报销申请需要处理。申请人："+user.getRealname() 
						+ ",报销金额：" + df.format(ent.getExpenseMoney()!=null?ent.getExpenseMoney():0)
						+ "。";
				
				auditTitle = "报销审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr+ "。";
			}else if(ConvertFlowConstants.OFFICE_EVECTION == type){//出差
				OfficeBusinessTrip ent = (OfficeBusinessTrip)obj;
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_EVECTION;
				title = "出差申请信息提醒";
				content = "您好！您有出差申请需要处理。申请人："+user.getRealname() 
						+ ",开始时间：" + DateUtils.date2String(ent.getBeginTime(), "yyyy-MM-dd")
						+ ",结束时间：" + DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd")
						+ "。";
				
				auditTitle = "出差审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr + "。";
			}else if(ConvertFlowConstants.OFFICE_ATTENDLECTURE == type){//听课
				OfficeAttendLecture ent = (OfficeAttendLecture)obj;
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_ATTENDLECTURE;
				title = "听课申请信息提醒";
				content = "您好！您有听课申请需要处理。申请人："+user.getRealname() 
						+ ",听课时间：" + DateUtils.date2String(ent.getAttendDate(), "yyyy-MM-dd")
						+ ",学科："+ ent.getSubjectName()
						+ "。";
				
				auditTitle = "听课审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr + "。";
			}else if(ConvertFlowConstants.OFFICE_JTGO_OUT == type){//集体外出
				OfficeJtgoOut ent = (OfficeJtgoOut)obj;
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_GO_OUT;
				Mcodedetail mcodedetail = mcodedetailService.getMcodeDetail("DM-JTWC", ent.getOutType());
				String typeName = "";
				if(mcodedetail != null){
					typeName = ",外出类型：" + mcodedetail.getContent();
				}
				title = "集体外出申请信息提醒";
				content = "您好！您有集体外出申请需要处理。申请人："+user.getRealname() 
						+ typeName
						+ ",外出时间：" + ent.getDays()
						+ ",外出人员：" + ent.getTripPerson()+ "。";
				
				auditTitle = "集体外出审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr+ "。";
			}else if(ConvertFlowConstants.OFFICE_NEWJTGO_OUT == type){//集体外出
				OfficeJtGoout ent = (OfficeJtGoout)obj;
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_JTGOOUT;
				String typeName="";
				if(StringUtils.isNotBlank(ent.getType())&&StringUtils.equals("1", ent.getType())){
					typeName=",外出类型：学生集体活动";
				}else{
					typeName=",外出类型：教师集体培训";
				}
				title = "集体外出管理申请信息提醒";
				content = "您好！您有集体外出申请需要处理。申请人："+user.getRealname() 
						+ typeName
						+ ",开始时间：" + DateUtils.date2String(ent.getStartTime(), "yyyy-MM-dd")
						+ ",结束时间：" + DateUtils.date2String(ent.getEndTime(), "yyyy-MM-dd")+ "。";
				
				auditTitle = "集体外出管理审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr+ "。";
			}else{
				return;
			}
			
			User curUser = userService.getUser(curUserId);
			Unit unit = unitService.getUnit(curUser.getUnitid());
			if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
				title = auditTitle;
				content = auditContent;
				//流转结束 通知申请人 
				officeSubsystemService.sendMsgDetail(curUser, unit, title, content, content, false, msgType, new String[]{user.getId()}, null);
			}else{
				List<TaskDescription> list = taskHandlerService.getTodoTasks(flowId);
				Set<String> userIds = new HashSet<String>();
				for(TaskDescription t : list){
					userIds = new HashSet<String>();
					if(CollectionUtils.isEmpty(t.getCandidateUsers()))
						continue;
					for(String userId : t.getCandidateUsers()){
						userIds.add(userId);
					}
					String msgId = officeSubsystemService.sendMsgDetail(user, unit, title, content, content, false, msgType, userIds.toArray(new String[0]), null);
					this.pushMsgUrl(obj, type, msgId, t.getTaskId());
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		try {
				completeTaskSendMsgToWeiKe(curUserId, isPass,obj, type, result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//工作流启动流程时推送微课消息
	public void startFlowSendMsgToWeiKe(Object obj, Integer type) {
		try {
			User user = null;
			String flowId = "";
			String title = "";
			String content = "";
			Integer msgType = 0;
			String businessId = "";
			Set<String> userIds = new HashSet<String>();
			
			List<String> rows = new ArrayList<String>();
			WKPushParm parm = new WKPushParm();
			parm.setFootContent("详情");
			if (ConvertFlowConstants.OFFICE_GO_OUT == type) {// 外出
				OfficeGoOut ent = (OfficeGoOut) obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				businessId = ent.getId();
				
				parm.setMsgTitle("外出申请");
				parm.setHeadContent(user.getRealname());
//				if(StringUtils.isNotBlank(ent.getOutType()))
//				parm.setBodyTitle("因公外出");
				parm.setBodyTitle("外出申请");
				rows.add("开始时间：" + DateUtils.date2String(ent.getBeginTime(),"yyyy-MM-dd HH:mm"));
				rows.add("结束时间：" + DateUtils.date2String(ent.getEndTime(),"yyyy-MM-dd HH:mm"));
				rows.add("外出时间：" + ent.getHours() +"小时");
				
			} else if (ConvertFlowConstants.OFFICE_TEACHER_LEAVE == type) {// 请假
				OfficeTeacherLeave ent = (OfficeTeacherLeave) obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				businessId = ent.getId();
				
				parm.setMsgTitle("请假申请");
				parm.setHeadContent(user.getRealname());
				parm.setBodyTitle("请假申请");
				rows.add("开始时间：" + DateUtils.date2String(ent.getLeaveBeignTime(),"yyyy-MM-dd"));
				rows.add("结束时间：" + DateUtils.date2String(ent.getLeaveEndTime(),"yyyy-MM-dd"));
				rows.add("请假时间：" + ent.getDays() +"天");
			} else if (ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE == type) {// 教师考勤
				OfficeAttendanceColckApply ent = (OfficeAttendanceColckApply) obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				businessId = ent.getId();
				
				parm.setMsgTitle("补卡申请");
				parm.setHeadContent(user.getRealname());
				parm.setBodyTitle("补卡申请");
				rows.add("补卡班次：" + DateUtils.date2String(ent.getAttenceDate(), "yyyy-MM-dd")+ "  " + ent.getTypeWeekTime());
			} else if (ConvertFlowConstants.OFFICE_EXPENSE == type) {// 报销
				OfficeExpense ent = (OfficeExpense) obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				businessId = ent.getId();
				
				parm.setMsgTitle("报销申请");
				parm.setHeadContent(user.getRealname());
				parm.setBodyTitle("报销申请");
				DecimalFormat df=new DecimalFormat("#.00");
				rows.add("报销金额：" + df.format(ent.getExpenseMoney()!=null?ent.getExpenseMoney():0));
				
			} else if (ConvertFlowConstants.OFFICE_EVECTION == type) {// 出差
				OfficeBusinessTrip ent = (OfficeBusinessTrip) obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				businessId = ent.getId();
				
				parm.setMsgTitle("出差申请");
				parm.setHeadContent(user.getRealname());
				parm.setBodyTitle("出差申请");
				rows.add("开始时间：" + DateUtils.date2String(ent.getBeginTime(),"yyyy-MM-dd"));
				rows.add("结束时间：" + DateUtils.date2String(ent.getEndTime(),"yyyy-MM-dd"));
				
			} else if (ConvertFlowConstants.OFFICE_ATTENDLECTURE == type) {// 听课
				OfficeAttendLecture ent = (OfficeAttendLecture) obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				businessId = ent.getId();
				
				parm.setMsgTitle("听课申请");
				parm.setHeadContent(user.getRealname());
				parm.setBodyTitle("听课申请");
				
				rows.add("听课时间：" + DateUtils.date2String(ent.getAttendDate(),"yyyy-MM-dd"));
				rows.add("学科：" + ent.getSubjectName());
				
			} else if (ConvertFlowConstants.OFFICE_NEWJTGO_OUT == type) {// 集体外出管理
				OfficeJtGoout ent = (OfficeJtGoout)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				businessId = ent.getId();
				
				parm.setMsgTitle("集体外出申请");
				parm.setHeadContent(user.getRealname());
				parm.setBodyTitle("集体外出申请");
				if(StringUtils.isNotBlank(ent.getType())&&StringUtils.equals("1", ent.getType())){
					rows.add("外出类型：学生集体活动");
				}else{
					rows.add("外出类型：教师集体培训");
				}
				rows.add("开始时间：" + DateUtils.date2String(ent.getStartTime(),"yyyy-MM-dd HH:mm"));
				rows.add("结束时间：" + DateUtils.date2String(ent.getEndTime(),"yyyy-MM-dd HH:mm"));
			} else {
				return;
			}

			parm.setRowsContent(rows.toArray(new String[0]));
			
			String domain = RedisUtils.get("EIS.BASE.PATH.V6");
			if(StringUtils.isNotBlank(domain)){
				parm.setJumpType(WeikeAppConstant.JUMP_TYPE_0);
				
				//获取下一步审核人信息
				List<TaskDescription> list = taskHandlerService
						.getTodoTasks(flowId);
				for (TaskDescription t : list) {
					if (CollectionUtils.isEmpty(t.getCandidateUsers()))
						continue;
					List<String> userIdList = t.getCandidateUsers();
					//并行步骤处理
					String url = WeikeAppUrlEnum.getWeikeFlowUrl(type+"", WeikeAppConstant.AUDIT_URL, businessId, t.getTaskId());
					parm.setUrl(domain + url);
					WeikePushClient.getInstance().pushMessage("", userIdList.toArray(new String[0]), parm);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//推送消息到微课
	public void completeTaskSendMsgToWeiKe(String curUserId, boolean isPass, Object obj, Integer type, TaskHandlerResult result){
		try {
			int status = isPass?Constants.LEAVE_APPLY_FLOW_FINSH_PASS:Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS;//统一用工作流的流程状态
			String passStr = isPass?"审核通过":"审核未通过";
			String title = "";
			Integer msgType = 0;
			Set<String> userIds = new HashSet<String>();
			User user = null;
			String auditTitle = "";
			String businessId = "";
			String flowId = "";
			
			WKPushParm parm = new WKPushParm();
			List<String> rows = new ArrayList<String>();
			if(ConvertFlowConstants.OFFICE_GO_OUT == type){//外出
				OfficeGoOut ent = (OfficeGoOut)obj;
				user = userService.getUser(ent.getApplyUserId());
				businessId = ent.getId();
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_GO_OUT;
				title = "外出申请";
				auditTitle = "外出审批";
				
				rows.add("开始时间：" + DateUtils.date2String(ent.getBeginTime(),"yyyy-MM-dd HH:mm"));
				rows.add("结束时间：" + DateUtils.date2String(ent.getEndTime(),"yyyy-MM-dd HH:mm"));
				rows.add("外出时间：" + ent.getHours() +"小时");
				
			}else if(ConvertFlowConstants.OFFICE_TEACHER_LEAVE == type){//请假
				OfficeTeacherLeave ent = (OfficeTeacherLeave)obj;
				businessId = ent.getId();
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_LEAVE;
				title = "请假申请";
				auditTitle = "请假审批";
				
				rows.add("开始时间：" + DateUtils.date2String(ent.getLeaveBeignTime(),"yyyy-MM-dd"));
				rows.add("结束时间：" + DateUtils.date2String(ent.getLeaveEndTime(),"yyyy-MM-dd"));
				rows.add("请假时间：" + ent.getDays() +"天");
				
			}else if(ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE == type){//教师考勤
				OfficeAttendanceColckApply ent = (OfficeAttendanceColckApply)obj;
				businessId = ent.getId();
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_ATTENDANCE;
				title = "补卡申请";
				auditTitle = "补卡审批";
				
				rows.add("补卡班次：" + DateUtils.date2String(ent.getAttenceDate(), "yyyy-MM-dd")+ "  " + ent.getTypeWeekTime());
				if(!isPass){
					rows.add("不通过原因："+ent.getAuditTextComment());
				}
			}else if(ConvertFlowConstants.OFFICE_EXPENSE == type){//报销
				OfficeExpense ent = (OfficeExpense)obj;
				businessId = ent.getId();
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_EXPENSE;
				title = "报销申请";
				auditTitle = "报销审批";
				DecimalFormat df=new DecimalFormat("#.00");
				rows.add("报销金额：" + df.format(ent.getExpenseMoney()!=null?ent.getExpenseMoney():0) + "元");
				
			}else if(ConvertFlowConstants.OFFICE_EVECTION == type){//出差
				OfficeBusinessTrip ent = (OfficeBusinessTrip)obj;
				businessId = ent.getId();
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_EVECTION;
				title = "出差申请";
				auditTitle = "出差审批";
				
				rows.add("开始时间：" + DateUtils.date2String(ent.getBeginTime(),"yyyy-MM-dd"));
				rows.add("结束时间：" + DateUtils.date2String(ent.getEndTime(),"yyyy-MM-dd"));
			}else if(ConvertFlowConstants.OFFICE_ATTENDLECTURE == type){//听课
				OfficeAttendLecture ent = (OfficeAttendLecture)obj;
				businessId = ent.getId();
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_ATTENDLECTURE;
				title = "听课申请";
				auditTitle = "听课审批";
				
				rows.add("听课时间：" + DateUtils.date2String(ent.getAttendDate(),"yyyy-MM-dd"));
				rows.add("学科：" + ent.getSubjectName());
			}else if(ConvertFlowConstants.OFFICE_NEWJTGO_OUT == type){//集体外出
				OfficeJtGoout ent = (OfficeJtGoout)obj;
				businessId = ent.getId();
				flowId = ent.getFlowId();
				user = userService.getUser(ent.getApplyUserId());
				title = "集体外出申请";
				auditTitle = "集体外出审批";
				
				
				if(StringUtils.isNotBlank(ent.getType())&&StringUtils.equals("1", ent.getType())){
					rows.add("外出类型：学生集体活动");
				}else{
					rows.add("外出类型：教师集体培训");
				}
				rows.add("开始时间：" + DateUtils.date2String(ent.getStartTime(),"yyyy-MM-dd HH:mm"));
				rows.add("结束时间：" + DateUtils.date2String(ent.getEndTime(),"yyyy-MM-dd HH:mm"));
			}else{
				return;
			}
			
			if(user == null)
				return;
			
			
			String domain = RedisUtils.get("EIS.BASE.PATH.V6");
			parm.setJumpType(WeikeAppConstant.JUMP_TYPE_0);
			if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
				parm.setMsgTitle(auditTitle);
				parm.setBodyTitle(auditTitle);
				rows.add("审批结果：" + passStr);
				if(StringUtils.isNotBlank(domain)){
					String url = WeikeAppUrlEnum.getWeikeFlowUrl(type+"", WeikeAppConstant.DETAILE_URL, businessId, "");
					parm.setUrl(domain + url);
					parm.setFootContent("详情");
				}
				//流转结束 通知申请人 
				userIds.add(user.getId());//申请人
				parm.setRowsContent(rows.toArray(new String[0]));
				
				WeikePushClient.getInstance().pushMessage("", userIds.toArray(new String[0]), parm); 
			}else{
				parm.setHeadContent(user.getRealname());
				parm.setMsgTitle(title);
				parm.setBodyTitle(title);
				
				if(StringUtils.isNotBlank(domain)){
					List<TaskDescription> todoTaskList = taskHandlerService.getTodoTasks(flowId);
					
					for(TaskDescription t : todoTaskList){
						List<String> userIdList = t.getCandidateUsers();
						
						String url = WeikeAppUrlEnum.getWeikeFlowUrl(type+"", WeikeAppConstant.AUDIT_URL, businessId, t.getTaskId());
						parm.setUrl(domain + url);
						parm.setFootContent("详情");
						parm.setRowsContent(rows.toArray(new String[0]));
						
						WeikePushClient.getInstance().pushMessage("", userIdList.toArray(new String[0]), parm);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送消息时绑定跳转相应业务URL
	 * 参数包括：消息ID，模块ID，接收者，接收者类别，跳转的URL，选中的TAB页(非必)，需要的权限roleCode(非必)
	 * @param obj
	 * @param type
	 * @param msgId
	 */
	public void pushMsgUrl(Object obj, Integer type, String msgId, String taskId){
		try {
			OfficeMsgSendingDto msgSendingDto;
			if(StringUtils.isNotBlank(msgId) && StringUtils.isNotBlank(taskId)){
				msgSendingDto = officeSubsystemService.getSendMsgById(msgId);
				if(msgSendingDto != null){
					String contextPath = ServletActionContext.getRequest().getContextPath();
					String objId = "";
					OfficeBusinessJump businessJump = new OfficeBusinessJump();
					List<TaskDescription> todoTaskList = new ArrayList<TaskDescription>();
					
					if(ConvertFlowConstants.OFFICE_GO_OUT == type){//外出
						OfficeGoOut ent = (OfficeGoOut)obj;
						businessJump.setUnitId(msgSendingDto.getUnitId());
						businessJump.setMsgId(msgId);
						businessJump.setModules("70027,70527");
						businessJump.setReceivers(msgSendingDto.getUserIds());
						businessJump.setReceiverType("2");
						businessJump.setCreateTime(new Date());
						
						objId = ent.getId();
						JSONObject json = new JSONObject();
						json.put("index", "1");
						json.put("loadObject", "#goOutDiv");
						json.put("url", contextPath+"/office/goout/goout-goOutAudit.action?officeGoOut.id="+objId+"&taskId="+taskId);
						json.put("taskId", taskId);
						businessJump.setContent(json.toString());
						
						officeBusinessJumpService.save(businessJump);
						
					}else if(ConvertFlowConstants.OFFICE_TEACHER_LEAVE == type){//请假
						OfficeTeacherLeave ent = (OfficeTeacherLeave)obj;
						businessJump.setUnitId(msgSendingDto.getUnitId());
						businessJump.setMsgId(msgId);
						businessJump.setModules("70013,70513");
						businessJump.setReceivers(msgSendingDto.getUserIds());
						businessJump.setReceiverType("2");
						businessJump.setCreateTime(new Date());
						
						objId = ent.getId();
						JSONObject json = new JSONObject();
						json.put("index", "1");
						json.put("loadObject", "#showListDiv");
						json.put("url", contextPath+"/office/teacherLeave/teacherLeave-auditTeacherLeave.action?id="+objId+"&taskId="+taskId);
						json.put("taskId", taskId);
						businessJump.setContent(json.toString());
							
						officeBusinessJumpService.save(businessJump);
						
					}else if(ConvertFlowConstants.OFFICE_EXPENSE == type){//报销
						OfficeExpense ent = (OfficeExpense)obj;
						businessJump.setUnitId(msgSendingDto.getUnitId());
						businessJump.setMsgId(msgId);
						businessJump.setModules("70026,70526");
						businessJump.setReceivers(msgSendingDto.getUserIds());
						businessJump.setReceiverType("2");
						businessJump.setCreateTime(new Date());
						
						objId = ent.getId();
						JSONObject json = new JSONObject();
						json.put("index", "1");
						json.put("loadObject", "#adminDiv");
						json.put("url", contextPath+"/office/expense/expense-expenseAudit-edit.action?officeExpense.id="+objId+"&taskId="+taskId);
						json.put("taskId", taskId);
						businessJump.setContent(json.toString());
						
						officeBusinessJumpService.save(businessJump);
						
					}else if(ConvertFlowConstants.OFFICE_EVECTION == type){//出差
						OfficeBusinessTrip ent = (OfficeBusinessTrip)obj;
						businessJump.setUnitId(msgSendingDto.getUnitId());
						businessJump.setMsgId(msgId);
						businessJump.setModules("70025,70525");
						businessJump.setReceivers(msgSendingDto.getUserIds());
						businessJump.setReceiverType("2");
						businessJump.setCreateTime(new Date());
						
						objId = ent.getId();
						JSONObject json = new JSONObject();
						json.put("index", "1");
						json.put("loadObject", "#businessTripeDiv");
						json.put("url", contextPath+"/office/businesstrip/businessTrip-businessTripAudit.action?officeBusinessTrip.id="+objId+"&taskId="+taskId);
						json.put("taskId", taskId);
						businessJump.setContent(json.toString());
						
						officeBusinessJumpService.save(businessJump);
						
					}else if(ConvertFlowConstants.OFFICE_ATTENDLECTURE == type){//听课
						OfficeAttendLecture ent = (OfficeAttendLecture)obj;
						businessJump.setUnitId(msgSendingDto.getUnitId());
						businessJump.setMsgId(msgId);
						businessJump.setModules("70033,70533");
						businessJump.setReceivers(msgSendingDto.getUserIds());
						businessJump.setReceiverType("2");
						businessJump.setCreateTime(new Date());
						
						objId = ent.getId();
						JSONObject json = new JSONObject();
						json.put("index", "1");
						json.put("loadObject", "#showListDiv");
						json.put("url", contextPath+"/office/attendLecture/attendLecture-auditEdit.action?officeAttendLecture.id="+objId+"&taskId="+taskId);
						json.put("taskId", taskId);
						businessJump.setContent(json.toString());
						
						officeBusinessJumpService.save(businessJump);
						
					}else if(ConvertFlowConstants.OFFICE_JTGO_OUT == type){//集体外出
						OfficeJtgoOut ent = (OfficeJtgoOut)obj;
						
						objId = ent.getId();
					}else if(ConvertFlowConstants.OFFICE_NEWJTGO_OUT == type){//集体外出管理
						OfficeJtGoout ent = (OfficeJtGoout)obj;
						businessJump.setUnitId(msgSendingDto.getUnitId());
						businessJump.setMsgId(msgId);
						businessJump.setModules("70038,70538");
						businessJump.setReceivers(msgSendingDto.getUserIds());
						businessJump.setReceiverType("2");
						businessJump.setCreateTime(new Date());
						
						objId = ent.getId();
						JSONObject json = new JSONObject();
						json.put("index", "1");
						json.put("loadObject", "#goOutDiv");
						json.put("url", contextPath+"/office/jtgooutmanage/jtgooutmanage-jtGoOutAuditEdit.action?jtGoOutId="+objId+"&taskId="+taskId);
						json.put("taskId", taskId);
						businessJump.setContent(json.toString());
						
						officeBusinessJumpService.save(businessJump);
						
					}else{
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} 
	}
}

