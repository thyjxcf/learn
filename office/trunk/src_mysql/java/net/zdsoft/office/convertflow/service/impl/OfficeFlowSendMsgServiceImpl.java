package net.zdsoft.office.convertflow.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlowTask;
import net.zdsoft.office.convertflow.service.OfficeFlowSendMsgService;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;

public class OfficeFlowSendMsgServiceImpl implements OfficeFlowSendMsgService {
	
	private UserService userService;
	private UnitService unitService;
	private OfficeSubsystemService officeSubsystemService;
	private TaskHandlerService taskHandlerService;
	
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
			}else if(ConvertFlowConstants.OFFICE_EXPENSE == type){//报销
				OfficeExpense ent = (OfficeExpense)obj;
				user = userService.getUser(ent.getApplyUserId());
				flowId = ent.getFlowId();
				msgType = BaseConstant.MSG_TYPE_EXPENSE;
				title = "报销申请信息提醒";
				content = "您好！您有报销申请需要处理。申请人："+user.getRealname() 
						+ ",报销金额：" + ent.getExpenseMoney()+ "。";
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
			}else{
				return;
			}
			
			//获取下一步审核人信息
			List<TaskDescription> list = taskHandlerService.getTodoTasks(flowId);
			for(TaskDescription t : list){
				OfficeConvertFlowTask ent = new OfficeConvertFlowTask();
				if(CollectionUtils.isEmpty(t.getCandidateUsers()))
					continue;
				for(String userId : t.getCandidateUsers()){
					userIds.add(userId);
				}
			}
			
			Unit unit = unitService.getUnit(user.getUnitid());
			officeSubsystemService.sendMsgDetail(user, unit, title, content, content, false, msgType, userIds.toArray(new String[0]), null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void completeTaskSendMsg(String curUserId, boolean isPass, Object obj, Integer type, TaskHandlerResult result){
		try {
			int status = isPass?Constants.LEAVE_APPLY_FLOW_FINSH_PASS:Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS;//统一用工作流的流程状态
			String passStr = isPass?"已经审核通过":"审核未通过";
			String title = "";
			String content = "";
			Integer msgType = 0;
			Set<String> userIds = new HashSet<String>();
			User user = null;
			
			String auditTitle = "";
			String auditContent = "";
			if(ConvertFlowConstants.OFFICE_GO_OUT == type){//外出
				OfficeGoOut ent = (OfficeGoOut)obj;
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
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_LEAVE;
				title = "请假申请信息提醒";
				content = "您好！您有请假申请需要处理。申请人："+user.getRealname() 
						+ ",开始时间：" + DateUtils.date2String(ent.getLeaveBeignTime(), "yyyy-MM-dd")
						+ ",结束时间：" + DateUtils.date2String(ent.getLeaveEndTime(), "yyyy-MM-dd")
						+ "。";
				
				auditTitle = "请假审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr+ "。";
			}else if(ConvertFlowConstants.OFFICE_EXPENSE == type){//报销
				OfficeExpense ent = (OfficeExpense)obj;
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_EXPENSE;
				title = "报销申请信息提醒";
				content = "您好！您有报销申请需要处理。申请人："+user.getRealname() 
						+ ",报销金额：" + ent.getExpenseMoney()
						+ "。";
				
				auditTitle = "报销审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr+ "。";
			}else if(ConvertFlowConstants.OFFICE_EVECTION == type){//出差
				OfficeBusinessTrip ent = (OfficeBusinessTrip)obj;
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
				user = userService.getUser(ent.getApplyUserId());
				msgType = BaseConstant.MSG_TYPE_ATTENDLECTURE;
				title = "听课申请信息提醒";
				content = "您好！您有听课申请需要处理。申请人："+user.getRealname() 
						+ ",听课时间：" + DateUtils.date2String(ent.getAttendDate(), "yyyy-MM-dd")
						+ ",学科："+ ent.getSubjectName()
						+ "。";
				
				auditTitle = "听课审核信息提醒";
				auditContent = "您好！您申请的信息"+passStr + "。";
			}else{
				return;
			}
			
			if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
				title = auditTitle;
				content = auditContent;
				//流转结束 通知申请人 
				userIds.add(user.getId());//申请人
			}else{
				if(status == Constants.APPLY_STATE_NOPASS){//审核不通过 
					userIds.add(user.getId());//申请人
				}else{
					//流转中 插入下一步审核信息
					List<String> parms = result.getTodoList();
					List<OfficeConvertFlowTask> insertList = new ArrayList<OfficeConvertFlowTask>();
					for(String parm : parms){
						String[] p = parm.split(",");
						if(p.length<3)
							continue;
						String auditUserId = p[0];//审核人
						String[] userIdArrays = auditUserId.split(",");
						for(int i = 0; i < userIdArrays.length; i++){
							userIds.add(userIdArrays[i]);
						}
					}
				}
			}
			
			User curUser = userService.getUser(curUserId);
			Unit unit = unitService.getUnit(curUser.getUnitid());
			officeSubsystemService.sendMsgDetail(curUser, unit, title, content, content, false, msgType, userIds.toArray(new String[0]), null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

