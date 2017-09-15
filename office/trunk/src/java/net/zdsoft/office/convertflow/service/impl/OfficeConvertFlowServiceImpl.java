package net.zdsoft.office.convertflow.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.dao.OfficeConvertFlowDao;
import net.zdsoft.office.convertflow.dto.OfficeConvertDto;
import net.zdsoft.office.convertflow.dto.OfficeConvertFlowDto;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlowTask;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowTaskService;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
import net.zdsoft.office.goodmanage.service.OfficeGoodsService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeAuthService;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_convert_flow
 * @author 
 * 
 */
public class OfficeConvertFlowServiceImpl implements OfficeConvertFlowService{
	private OfficeConvertFlowDao officeConvertFlowDao;
	private TaskHandlerService taskHandlerService;
	private OfficeConvertFlowTaskService officeConvertFlowTaskService;
	private UserService userService;
	private OfficeGoodsTypeAuthService officeGoodsTypeAuthService;
	private OfficeGoodsService officeGoodsService;
	
	
	//工作流相关模块
	private List<Integer> getBusinessTypes(){
		List<Integer> list = new ArrayList<Integer>();
		list.add(ConvertFlowConstants.OFFICE_GO_OUT);
		list.add(ConvertFlowConstants.OFFICE_EVECTION);
		list.add(ConvertFlowConstants.OFFICE_EXPENSE);
		list.add(ConvertFlowConstants.OFFICE_TEACHER_LEAVE);
		list.add(ConvertFlowConstants.OFFICE_ATTENDLECTURE);
		list.add(ConvertFlowConstants.OFFICE_NEWJTGO_OUT);
		list.add(ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE);
		return list;
	}
	
	public void deleteFlow(String businessId){
		//1.删除convertFlow关联表信息
		OfficeConvertFlow officeConvertFlow = officeConvertFlowDao.getObjByBusinessId(businessId);
		if(officeConvertFlow!=null){
			officeConvertFlowTaskService.deleteByConvertFlowId(officeConvertFlow.getId());
			officeConvertFlowDao.deleteByBusinessId(businessId);
		}
	}
	
	//启动流程
	public void startFlow(Object obj, Integer type){
		
		OfficeConvertFlowDto dto = new OfficeConvertFlowDto();
		//往中间表插入数据 手机端调用
		if(ConvertFlowConstants.OFFICE_GO_OUT == type){//外出
			OfficeGoOut ent = (OfficeGoOut)obj;
			dto.setUnitId(ent.getUnitId());
			dto.setBusinessId(ent.getId());
			dto.setApplyUserId(ent.getApplyUserId());
			dto.setFlowId(ent.getFlowId());
		}else if(ConvertFlowConstants.OFFICE_TEACHER_LEAVE == type){//请假
			OfficeTeacherLeave ent = (OfficeTeacherLeave)obj;
			dto.setUnitId(ent.getUnitId());
			dto.setBusinessId(ent.getId());
			dto.setApplyUserId(ent.getApplyUserId());
			dto.setFlowId(ent.getFlowId());
		}else if(ConvertFlowConstants.OFFICE_EXPENSE == type){//报销
			OfficeExpense ent = (OfficeExpense)obj;
			dto.setUnitId(ent.getUnitId());
			dto.setBusinessId(ent.getId());
			dto.setApplyUserId(ent.getApplyUserId());
			dto.setFlowId(ent.getFlowId());
		}else if(ConvertFlowConstants.OFFICE_EVECTION == type){//出差
			OfficeBusinessTrip ent = (OfficeBusinessTrip)obj;
			dto.setUnitId(ent.getUnitId());
			dto.setBusinessId(ent.getId());
			dto.setApplyUserId(ent.getApplyUserId());
			dto.setFlowId(ent.getFlowId());
		}else if(ConvertFlowConstants.OFFICE_ATTENDLECTURE == type){//听课
			OfficeAttendLecture ent = (OfficeAttendLecture)obj;
			dto.setUnitId(ent.getUnitId());
			dto.setBusinessId(ent.getId());
			dto.setApplyUserId(ent.getApplyUserId());
			dto.setFlowId(ent.getFlowId());
		}else if(ConvertFlowConstants.OFFICE_GOODS == type){//物品
			OfficeGoodsReq ent = (OfficeGoodsReq)obj;
			dto.setUnitId(ent.getUnitId());
			dto.setBusinessId(ent.getId());
			dto.setApplyUserId(ent.getGetUserid());
			dto.setAuditParm(ent.getGoodsId());
		}else if(ConvertFlowConstants.OFFICE_NEWJTGO_OUT == type){//集体外出
			OfficeJtGoout ent = (OfficeJtGoout)obj;
			dto.setUnitId(ent.getUnitId());
			dto.setBusinessId(ent.getId());
			dto.setApplyUserId(ent.getApplyUserId());
			dto.setFlowId(ent.getFlowId());
		}else if(ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE == type){//教师考勤-补卡申请
			OfficeAttendanceColckApply ent = (OfficeAttendanceColckApply)obj;
			dto.setUnitId(ent.getUnitId());
			dto.setBusinessId(ent.getId());
			dto.setApplyUserId(ent.getApplyUserId());
			dto.setFlowId(ent.getFlowId());
		}
		dto.setType(type);
		if(StringUtils.isBlank(dto.getBusinessId()))
			return;
		
		try {
			this.startFlow(dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//流转中--工作流使用
	public void completeTask(String businessId, String flowId, String curUserId, String taskId, TaskHandlerResult result, boolean isPass){
		OfficeConvertFlowDto dto = new OfficeConvertFlowDto();
		dto.setBusinessId(businessId);
		dto.setFlowId(flowId);
		dto.setCurUserId(curUserId);
		dto.setParm(taskId);
		dto.setStatus(isPass?Constants.LEAVE_APPLY_FLOW_FINSH_PASS:Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS);
		
		try {
			this.completeTask(dto, result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//普通审核完成时调用--如物品管理只有一步审核流程
	public void completeAudit(boolean isPass, String businessId){
		OfficeConvertFlow ent = officeConvertFlowDao.getObjByBusinessId(businessId);
		int status = isPass?Constants.LEAVE_APPLY_FLOW_FINSH_PASS:Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS;//统一用工作流的流程状态
		//根据businessid更新数据
		try {
			officeConvertFlowDao.update(status, ent.getBusinessId());
			officeConvertFlowTaskService.updateByConvertFlowId(status, ent.getId());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//---------------------------------------------------------------------------
	//获取审核list
	public List<OfficeConvertDto> getAuditList(String unitId, String userId, String dataType, Integer type, String searchStr, Pagination page){
		List<OfficeConvertDto> returnlist = new ArrayList<OfficeConvertDto>();
		Set<String> queryUserIds = new HashSet<String>();
		if(StringUtils.isNotBlank(searchStr)){//申请人姓名模糊查询
			List<User> ulist = userService.getUsersBySearchName(searchStr, null);
			for(User user : ulist){
				queryUserIds.add(user.getId());
			}
			if(CollectionUtils.isEmpty(queryUserIds)){
				return returnlist;
			}
		}
		
		String otherAuditParm = "";
		if(ConvertFlowConstants.OFFICE_GOODS == type || ConvertFlowConstants.OFFICE_ALL == type){//物品
			//获取用户的物品类型权限
			List<OfficeGoodsTypeAuth> authlist = officeGoodsTypeAuthService.getOfficeGoodsTypeAuthByUserId(unitId, userId);
			List<String> typelist = new ArrayList<String>();
			if(authlist.size() > 0){
				typelist = Arrays.asList(authlist.get(0).getTypeId().split(","));
			}
			
			List<OfficeGoods> goodslist = officeGoodsService.getOfficeGoodsByConditions(unitId, typelist.toArray(new String[0]), null,false, null);
			
			StringBuffer goodIdStr = new StringBuffer();
			int m = 0;
			for(OfficeGoods goods : goodslist){
				if(m == 0){
					goodIdStr.append(goods.getId());
				}else{
					goodIdStr.append(",").append(goods.getId());
				}
				m++;
			}
			
			otherAuditParm = goodIdStr.toString();
		}else{
			
		}
		if(ConvertFlowConstants.OFFICE_GOODS == type && StringUtils.isBlank(otherAuditParm)){//说明没有权限
			return returnlist;
		}
		
		List<OfficeConvertFlow> list = null;
		if(ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_2 == Integer.valueOf(dataType)){
			list = officeConvertFlowDao.getHaveDoAuditList(unitId, queryUserIds.toArray(new String[0]), userId, otherAuditParm, dataType, type, page);
		}else{
			list = officeConvertFlowDao.getAuditList(unitId, queryUserIds.toArray(new String[0]), userId, otherAuditParm, dataType, type, page);
		}
		Set<String> userIds = new HashSet<String>();
		for(OfficeConvertFlow ent : list){
			userIds.add(ent.getApplyUserId());
		}
		
		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		
		for(OfficeConvertFlow ent : list){
			OfficeConvertDto dto = new OfficeConvertDto();
			
			dto.setType(ent.getType());
			dto.setBusinessId(ent.getBusinessId());
			dto.setDateStr(DateUtils.date2String(ent.getCreateTime(), "yyyy-MM-dd"));
			if(getBusinessTypes().contains(ent.getType())){
				dto.setTaskId(ent.getParm());
			}
			
			String title = "";
			if(userMap.containsKey(ent.getApplyUserId())){
				User user = userMap.get(ent.getApplyUserId());
				if(user != null)
					title = getTitleStr(user.getRealname(), ent.getType());
			}
			
			dto.setTitle(title);
			dto.setSubtitle(getSubtitleStr(ent, Integer.valueOf(dataType)));
			
			returnlist.add(dto);
		}
		
		return returnlist;
	}
	
	
	public List<OfficeConvertDto> getApplyList(String applyUserId, Integer type, Pagination page){
		List<OfficeConvertFlow> list = officeConvertFlowDao.getApplyList(applyUserId, type, page);
		List<OfficeConvertDto> returnlist = new ArrayList<OfficeConvertDto>();
		
		User user = userService.getUser(applyUserId);
		for(OfficeConvertFlow ent : list){
			OfficeConvertDto dto = new OfficeConvertDto();
			dto.setType(ent.getType());
			dto.setBusinessId(ent.getBusinessId());
			dto.setDateStr(DateUtils.date2String(ent.getCreateTime(), "yyyy-MM-dd"));
//			if(getBusinessTypes().contains(ent.getType())){
//				dto.setTaskId(ent.getParm());
//			}
			
			String title = "";
			if(user != null)
				title = getTitleStr(user.getRealname(), ent.getType());
			
			dto.setTitle(title);
			dto.setSubtitle(getSubtitleStr(ent, ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_3));
			
			returnlist.add(dto);
		}
		
		return returnlist;
	}
	
	
	//--------------------------私有方法------------------------------------------
	
	//返回标题
	private String getTitleStr(String userName, int type){
		String str = userName;
		switch (type) {
		case ConvertFlowConstants.OFFICE_TEACHER_LEAVE:
			str += "的请假";
			break;
		case ConvertFlowConstants.OFFICE_GO_OUT:
			str += "的外出";
			break;
		case ConvertFlowConstants.OFFICE_EVECTION:
			str += "的出差";
			break;
		case ConvertFlowConstants.OFFICE_EXPENSE:
			str += "的报销";
			break;
		case ConvertFlowConstants.OFFICE_GOODS:
			str += "的物品领用";
			break;
		case ConvertFlowConstants.OFFICE_ATTENDLECTURE:
			str += "的听课";
			break;
		case ConvertFlowConstants.OFFICE_NEWJTGO_OUT:
			str += "的集体外出";
			break;
		case ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE:
			str += "的补卡申请";
			break;
		default:
			break;
		}
		
		return str;
	}
	
	//返回副标题
	private String getSubtitleStr(OfficeConvertFlow ent, int state){
		String subtitle = "";
		if(ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_1 == state){
			subtitle = "待审批";
		}else if(ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_2 == state || ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_3 == state){
			int applyStatus = ent.getStatus();
//			int auditStatus = ent.getState();
			if(Constants.APPLY_STATE_NEED_AUDIT == applyStatus){//待审核
				subtitle = "审批中";
			}else if(Constants.APPLY_STATE_PASS == applyStatus){//通过
				subtitle = "审批完成（同意）";
			}else if(Constants.APPLY_STATE_NOPASS == applyStatus){//不通过
				subtitle = "审批完成（拒绝）";
			}
		}
		
		return subtitle;
	}
	
	//启动流程
	private void startFlow(OfficeConvertFlowDto dto){

		//新增OfficeConvertFlow
		OfficeConvertFlow ocf = new OfficeConvertFlow();
		ocf.setUnitId(dto.getUnitId());
		ocf.setBusinessId(dto.getBusinessId());
		ocf.setType(dto.getType());
		ocf.setApplyUserId(dto.getApplyUserId());
		ocf.setStatus(Constants.APPLY_STATE_NEED_AUDIT);//审核中
		ocf = officeConvertFlowDao.save(ocf);
		
		List<OfficeConvertFlowTask> insertList = new ArrayList<OfficeConvertFlowTask>();
		if(dto.getType()!=null){
			if(getBusinessTypes().contains(dto.getType())){
				//工作流相关
				//新增OfficeConvertFlowTask  审核步骤  一般只有一步  因为web端暂时不支持并行步骤
				List<TaskDescription> list = taskHandlerService.getTodoTasks(dto.getFlowId());
				for(TaskDescription t : list){
					OfficeConvertFlowTask ent = new OfficeConvertFlowTask();
					ent.setConvertFlowId(ocf.getId());
					if(CollectionUtils.isEmpty(t.getCandidateUsers()))
						continue;
					StringBuffer userIdStr = new StringBuffer();
					int m = 0;
					for(String userId : t.getCandidateUsers()){
						if(m == 0){
							userIdStr.append(userId);
						}else{
							userIdStr.append(",").append(userId);
						}
						m++;
					}
					ent.setAuditParm(userIdStr.toString());
					ent.setStatus(Constants.APPLY_STATE_NEED_AUDIT);
					ent.setParm(t.getTaskId());
					
					insertList.add(ent);
				}
			}else{
				if(dto.getType() == ConvertFlowConstants.OFFICE_GOODS){
					//物品
					OfficeConvertFlowTask ent = new OfficeConvertFlowTask();
					ent.setConvertFlowId(ocf.getId());
					ent.setAuditParm(dto.getAuditParm());
					ent.setStatus(Constants.APPLY_STATE_NEED_AUDIT);
					insertList.add(ent);
				}
			}
		}
		officeConvertFlowTaskService.batchInsert(insertList);
	}
	
	//处理流程
	private void completeTask(OfficeConvertFlowDto dto, TaskHandlerResult result){
		//更新当前步骤信息
		officeConvertFlowTaskService.updateByParm(dto.getCurUserId(), dto.getStatus(), dto.getParm());//根据taskid更新
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			//流转结束  更新状态
			
			officeConvertFlowDao.update(dto.getStatus(), dto.getBusinessId());
		}else{
			if(dto.getStatus() == Constants.APPLY_STATE_NOPASS){//如果审核不通过  主表也要更新一下
				officeConvertFlowDao.update(dto.getStatus(), dto.getBusinessId());
			}
			
			
			//流转中 插入下一步审核信息
			OfficeConvertFlow ocf = officeConvertFlowDao.getObjByBusinessId(dto.getBusinessId());
			
			List<OfficeConvertFlowTask> taskList = officeConvertFlowTaskService.getListByCfId(ocf.getId());
			Set<String> taskIds = new HashSet<String>();
			for(OfficeConvertFlowTask t : taskList){
				taskIds.add(t.getParm());
			}
			
			List<TaskDescription> list = taskHandlerService.getTodoTasks(dto.getFlowId());
			Map<String, String> taskMap = new HashMap<String, String>();
			for(TaskDescription t : list){
				List<String> nextUserIds = t.getCandidateUsers();
				String taskId = t.getTaskId();
				String auditUserId = StringUtils.join(nextUserIds, ",");//下一步处理人  逗号隔开
				if(taskMap.containsKey(taskId)){
					taskMap.put(taskId, taskMap.get(taskId)+","+auditUserId);
				}else{
					taskMap.put(taskId, auditUserId);
				}
			}
			
			List<OfficeConvertFlowTask> insertList = new ArrayList<OfficeConvertFlowTask>();
			for(String key : taskMap.keySet()){
				if(taskIds.contains(key))//已经存在的就不需要新增了
					continue;
				
				OfficeConvertFlowTask ent = new OfficeConvertFlowTask();
				ent.setConvertFlowId(ocf.getId());
				ent.setAuditParm(taskMap.get(key));
				ent.setParm(key);
				ent.setStatus(Constants.APPLY_STATE_NEED_AUDIT);
				insertList.add(ent);
			}
		
			officeConvertFlowTaskService.batchInsert(insertList);
		}
	}
	
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setOfficeConvertFlowTaskService(
			OfficeConvertFlowTaskService officeConvertFlowTaskService) {
		this.officeConvertFlowTaskService = officeConvertFlowTaskService;
	}

	public void setOfficeConvertFlowDao(
			OfficeConvertFlowDao officeConvertFlowDao) {
		this.officeConvertFlowDao = officeConvertFlowDao;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setOfficeGoodsTypeAuthService(
			OfficeGoodsTypeAuthService officeGoodsTypeAuthService) {
		this.officeGoodsTypeAuthService = officeGoodsTypeAuthService;
	}
	public void setOfficeGoodsService(OfficeGoodsService officeGoodsService) {
		this.officeGoodsService = officeGoodsService;
	}

	@Override
	public int deleteByBusinessId(String businessId) {
		return officeConvertFlowDao.deleteByBusinessId(businessId);
	}

	@Override
	public OfficeConvertFlow getObjByBusinessId(String businessId) {
		return officeConvertFlowDao.getObjByBusinessId(businessId);
	}

	@Override
	public void update(Integer status, String businessId) {
		officeConvertFlowDao.update(status, businessId);
	}
	
	@Override
	public OfficeConvertFlow getOfficeConvertFlowById(String id){
		return officeConvertFlowDao.getOfficeConvertFlowById(id);
	}
	
}