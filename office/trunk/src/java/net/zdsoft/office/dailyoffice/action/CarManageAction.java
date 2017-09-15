package net.zdsoft.office.dailyoffice.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.eis.sms.dto.SendDetailDto;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.dailyoffice.dto.OfficeCarApplyDto;
import net.zdsoft.office.dailyoffice.dto.UseCarInfoDto;
import net.zdsoft.office.dailyoffice.entity.OfficeCarApply;
import net.zdsoft.office.dailyoffice.entity.OfficeCarDriver;
import net.zdsoft.office.dailyoffice.entity.OfficeCarInfo;
import net.zdsoft.office.dailyoffice.entity.OfficeCarSubsidy;
import net.zdsoft.office.dailyoffice.service.OfficeCarApplyService;
import net.zdsoft.office.dailyoffice.service.OfficeCarDriverService;
import net.zdsoft.office.dailyoffice.service.OfficeCarInfoService;
import net.zdsoft.office.dailyoffice.service.OfficeCarSubsidyService;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author chens
 * @version 创建时间：2014-12-31 下午3:24:01
 * 
 */
@SuppressWarnings("serial")
public class CarManageAction extends PageAction {
	/**
	 * 车辆管理员，人员权限设置
	 */
	public static final String OFFCIE_CAR_AUTH = "office_carAuth";
	/**
	 *车辆申请权限
	 */
	public static final String OFFCIE_CAR_APPLY = "office_carApply";
	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeCarInfoService officeCarInfoService;
	private OfficeCarApplyService officeCarApplyService;
	private OfficeCarDriverService officeCarDriverService;
	private OfficeCarSubsidyService officeCarSubsidyService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private DeptService deptService;
	private UserService userService;
	private TeacherService teacherService;
	private UnitService unitService;
	private SmsClientService smsClientService;
	
	private List<OfficeCarInfo> officeCarInfos;
	private List<OfficeCarApply> officeCarApplies;
	private List<OfficeCarDriver> officeCarDrivers = new ArrayList<OfficeCarDriver>();
	List<OfficeCarApplyDto> officeCarApplyDtos;
	private List<UseCarInfoDto> useCarInfolist;
	private List<OfficeCarSubsidy> officeSubsidys;
	
	private OfficeCarInfo officeCarInfo;
	private OfficeCarApply officeCarApply;
	private UseCarInfoDto useCarInfoDto;
	
	private String[] driverIds;
	private String[] driverNames;
	private String[] mobilePhones;
	
	private String[] subsidyIds;
	private String[] scopeNames;
	private String[] subsidys;
	
	private String carId;
	private String applyId;
	private String driverId;
	private String remark;//审核意见
	private Integer state;//状态
	private String applyType;
	
	private String queryStartTime;
	private String queryEndTime;
	private String querySumType;//汇总类型，1：车辆，2：驾驶员
	private String queryState;
	private String carNumber;
	
	private String jsonString;
	private String phoneNumber;//联系手机号码
	
	private String[] carIds;//车辆ids
	
	private boolean carApply;//是否有车辆申请权限
	
	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}
	public String execute() {
		//判断是否有车辆申请权限
		carApply = isCustomRole(OFFCIE_CAR_APPLY);
		return SUCCESS;
	}
	/**
	 *车辆申请
	 */
	public String carApply(){
		return SUCCESS;
	}
	public String carApplyList(){
		if(BaseConstant.SYS_DEPLOY_SCHOOL_ZHZG.equals(getSystemDeploySchool())){//镇海申请人能查看本单位下所有申请情况
			officeCarApplies = officeCarApplyService.getOfficeCarApplyListPage(getUnitId(), null, queryStartTime, queryEndTime, queryState, getPage());
		}else{
			officeCarApplies = officeCarApplyService.getOfficeCarApplyListPage(getUnitId(), getLoginInfo().getUser().getId(), queryStartTime, queryEndTime, queryState, getPage());
		}
		for(OfficeCarApply apply : officeCarApplies){
			if(apply.getState() == Constants.APPLY_STATE_PASS){
				if(apply.getUseTime().compareTo(new Date()) > 0)
					apply.setTimeOut(false);
				else
					apply.setTimeOut(true);
			}
		}
		return SUCCESS;
	}
	public String carApplyEdit(){
		if(StringUtils.isNotBlank(applyId)){
			officeCarApply = officeCarApplyService.getOfficeCarApplyById(applyId);
		}else{
			officeCarApply = new OfficeCarApply();
		}
		officeCarInfos = officeCarInfoService.getOfficeCarInfoByUnitIdPage(getUnitId(), null, getPage());
		return SUCCESS;
	}
	
	/**
	 * 获取手机号码，自动填充
	 * @return
	 */
	public String carApplyGetPhoneNumber(){
		if(StringUtils.isNotBlank(jsonString)){
			try {
				User user = userService.getUser(jsonString);
				Teacher teacher = teacherService.getTeacher(user.getTeacherid());
				phoneNumber = teacher.getPersonTel();
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage(phoneNumber);
			}catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("获取手机号码出错");
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	public String carApplyView(){
		officeCarApply = officeCarApplyService.getOfficeCarApplyById(applyId);
		if(StringUtils.isNotBlank(officeCarApply.getArea())){
			OfficeCarSubsidy officeCarSubsidy = officeCarSubsidyService.getOfficeCarSubsidyById(officeCarApply.getArea());
			if(officeCarSubsidy != null){
				officeCarApply.setAreaStr(officeCarSubsidy.getScope());
				officeCarApply.setAreaSubsidy(officeCarSubsidy.getSubsidy());
			}
		}
		if(officeCarApply.getUseTime() != null){
			try {
				officeCarApply.setXinqi(OfficeUtils.dayForWeek(officeCarApply.getUseTime()));
				if(officeCarApply.getWaitingTime() != null && officeCarApply.getIsNeedWaiting())
					officeCarApply.setXinqi2(OfficeUtils.dayForWeek(officeCarApply.getWaitingTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	public String saveCarApply(){
		officeCarApply.setCarId(toString(carIds));
		if(officeCarApply.getIsGoback() != null && !officeCarApply.getIsGoback()){
			officeCarApply.setIsNeedWaiting(false);
			officeCarApply.setWaitingTime(null);
		}
		if(officeCarApply.getIsNeedWaiting() != null && !officeCarApply.getIsNeedWaiting()){
			officeCarApply.setWaitingTime(null);
		}
		if(officeCarApply.getWaitingTime()!=null){
			if(officeCarApply.getUseTime().compareTo(officeCarApply.getWaitingTime()) >= 0){
				jsonError = "接返时间必须大于用车时间！";
				return SUCCESS;
			}
		}
		try {
			if(StringUtils.isBlank(officeCarApply.getId())){
				officeCarApply.setUnitId(getUnitId());
				officeCarApply.setApplyUserId(getLoginUser().getUserId());
				officeCarApply.setApplyTime(new Date());
				officeCarApply.setDeptId(getLoginInfo().getUser().getDeptid());
				officeCarApply.setIsDeleted(false);
				officeCarApplyService.save(officeCarApply);
			}else{
				officeCarApplyService.update(officeCarApply);
			}
			if(officeCarApply.getState() == 2){
				//TODO 消息推送
				CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), OFFCIE_CAR_AUTH);
				List<CustomRoleUser> roleUserList = customRoleUserService.getCustomRoleUserList(new String[]{role.getId()});
				Dept dept = deptService.getDept(getLoginInfo().getUser().getDeptid());
				StringBuffer roleUser = new StringBuffer();
				StringBuffer userName = new StringBuffer();
				Map<String, User> userMap = userService.getUserMap(getUnitId());
				userName.append(userMap.get(getLoginUser().getUserId()).getRealname());
				for(CustomRoleUser ro : roleUserList){
					if(userMap.containsKey(ro.getUserId())){
						if(roleUser.length() > 0){
							roleUser.append(",");
//							userName.append(",");
						}
						roleUser.append(ro.getUserId());
//						userName.append(userMap.get(ro.getUserId()).getName());
					}
				}
				officeCarApply.setLinkUserName(userMap.get(officeCarApply.getLinkUserId()).getRealname());
				officeCarApply.setApplyUserName(userMap.get(officeCarApply.getApplyUserId()).getRealname());
				
				OfficeMsgSending officeMsgSending = new OfficeMsgSending();
				officeMsgSending.setCreateUserId(officeCarApply.getApplyUserId());
				officeMsgSending.setTitle("出车申请信息提醒");
				officeMsgSending.setContent(getContent(officeCarApply));
				officeMsgSending.setSimpleContent(getContent(officeCarApply));
				officeMsgSending.setUserIds(roleUser.toString());
				officeMsgSending.setSendUserName(userName.toString());
				officeMsgSending.setState(Constants.MSG_STATE_SEND);
				officeMsgSending.setUnitId(getUnitId());
				officeMsgSending.setIsNeedsms(false);
				officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_CAR);
				officeMsgSendingService.save(officeMsgSending, null, null);
				
				//发短信//TODO
				Unit unit=unitService.getUnit(getUnitId());
				MsgDto msgDto = new MsgDto();
				msgDto.setUserId(getLoginUser().getUserId());
				msgDto.setUserName(getLoginInfo().getUser().getName());
				msgDto.setTiming(false);
				msgDto.setContent(getContent(officeCarApply));
				msgDto.setUnitId(getUnitId());
				msgDto.setUnitName(unit.getName());
				List<SendDetailDto> sendDetailDtos=new ArrayList<SendDetailDto>();
				
				String[] userIds=roleUser.toString().split(",");
				Map<String,User> users=userService.getUsersMap(userIds);
				Set<String> teacherId=new HashSet<String>();
				for(String userId: userIds){
					User reciver=users.get(userId);
					if(reciver!=null){
						teacherId.add(reciver.getTeacherid());
					}
				}
				Map<String,Teacher> teacherMap=teacherService.getTeacherMap(teacherId.toArray(new String[]{}));
				for(String userid:userIds){
					User receiver=users.get(userid);
					if(receiver!=null){
						Teacher teacher=teacherMap.get(receiver.getTeacherid());
						if(teacher!=null&&StringUtils.isNotBlank(teacher.getPersonTel())){
							SendDetailDto sendDetailDao=new SendDetailDto();
							sendDetailDao.setUnitId(getUnitId());
							sendDetailDao.setReceiverId(userid);
							sendDetailDao.setReceiverName(receiver.getRealname());
							sendDetailDao.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
							sendDetailDao.setReceiverType(User.TEACHER_LOGIN);
							sendDetailDao.setMobile(teacher.getPersonTel());
							sendDetailDtos.add(sendDetailDao);
						}
					}
				}
				SmsThread smsThread=new SmsThread(msgDto, sendDetailDtos);
				smsThread.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "保存失败！";
		}
		return SUCCESS;
	}
	
	/**
	 * 发送短信
	 * @author Administrator
	 *
	 */
	private class SmsThread extends Thread{
    	private MsgDto msgDto;
    	private List<SendDetailDto> sendDetailDtoList;
    	public SmsThread(MsgDto msgDto, List<SendDetailDto> sendDetailDtoList){
    		this.msgDto = msgDto;
    		this.sendDetailDtoList = sendDetailDtoList;
    	}

		@Override
		public void run() {
			smsClientService.saveSmsBatch(msgDto, sendDetailDtoList);
 		}
    }
	
	private String getContent(OfficeCarApply apply) {
        StringBuffer sb = new StringBuffer();
        sb.append("    您好！您有一条出车申请可以审核。申请人：")
                .append(apply.getApplyUserName()).append("");
        sb.append("，乘车联系人：").append(apply.getLinkUserName());
        sb.append("，联系电话：").append(apply.getMobilePhone());
        sb.append("，乘车人数：").append(apply.getPersonNumber()+"");
        sb.append("，用车时间：").append(DateUtils.date2String(apply.getUseTime(),"yyyy年MM月dd日 HH时mm分"));
        try {
			sb.append("("+OfficeUtils.dayForWeek(apply.getUseTime())+")");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        sb.append("，目的地：").append(apply.getCarLocation());
        if(apply.getIsGoback()){
        	sb.append("，是否往返： 是");
        	if(apply.getIsNeedWaiting()){
        		sb.append("，是否需要候车： 是");
        		sb.append("，接返时间：").append(DateUtils.date2String(apply.getWaitingTime(),"yyyy-MM-dd HH-mm"));
        	}else{
        		sb.append("，是否需要候车： 否");
        	}
        }else{
        	sb.append("，是否往返： 否");
        }
        sb.append("，用车事由：").append(apply.getReason());
        return sb.toString();
    }
	
	public String deleteCarApply(){
		
		try {
			officeCarApplyService.delete(applyId);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "删除失败！";
		}
		return SUCCESS;
	}
	
	/**
	 * 车辆审核
	 */
	public String carAudit(){
		return SUCCESS;
	}
	public String carAuditList(){
		officeCarApplies = officeCarApplyService.getOfficeCarApplyByUnitIdPage(getUnitId(), queryStartTime, queryEndTime, queryState, null, getPage());
		return SUCCESS;
	}
	public String carAuditEdit(){
		officeCarApply = officeCarApplyService.getOfficeCarApplyById(applyId);
		try {
			officeCarApply.setXinqi(OfficeUtils.dayForWeek(officeCarApply.getUseTime()));
			if(officeCarApply.getWaitingTime() != null && officeCarApply.getIsNeedWaiting()){
				officeCarApply.setXinqi2(OfficeUtils.dayForWeek(officeCarApply.getWaitingTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		officeCarDrivers = officeCarDriverService.getOfficeCarDriverByUnitIdList(getUnitId());
		setDriverNames(officeCarDrivers);
		officeCarInfos = officeCarInfoService.getOfficeCarInfoByUnitIdPage(getUnitId(), null, getPage());
		officeSubsidys = officeCarSubsidyService.getOfficeCarSubsidyList(getUnitId());
		return SUCCESS;
	}
	
	public String saveOvertime(){
		try {
			if(officeCarApply.getIsOvertime()){
				if(officeCarApply.getOvertimeNumber() <= 0 ){
					jsonError = "加班天数应大于0！";
					return	 SUCCESS;
				}
			}else{
				officeCarApply.setOvertimeNumber(0f);
			}
			officeCarApplyService.updateOverTimeNumber(officeCarApply);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "保存失败！";
		}
		return SUCCESS;
	}
	public String carUseInfo(){
		useCarInfoDto = officeCarApplyService.getCarUseInfo(carId);
		return SUCCESS;
	}
	//通过
	public String auditInfo(){
		try {
			if(officeCarApply.getState() == Constants.APPLY_STATE_PASS){
				officeCarApply.setCarId(toString(carIds));
				officeCarApply.setDriverId(toString(driverIds));
			}
			officeCarApply.setAuditTime(new Date());
			officeCarApply.setAuditUserId(getLoginUser().getUserId());
			if(!officeCarApply.getIsOvertime()){
				officeCarApply.setOvertimeNumber(0f);
			}
			if(officeCarApply.getIsOvertime()){
				if(officeCarApply.getOvertimeNumber() <= 0 ){
					jsonError = "加班天数应大于0！";
					return	 SUCCESS;
				}
			}
			officeCarApplyService.updateState(officeCarApply);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "审核失败！";
		}
		return SUCCESS;
	}
	
	public String carCancelApply(){
		try{
			officeCarApply = officeCarApplyService.getOfficeCarApplyById(applyId);
			officeCarApply.setState(Constants.APPLY_STATE_CANCEL_NEED_AUDIT);
			officeCarApplyService.updateApplyState(officeCarApply);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "撤销申请失败！";
		}
		return SUCCESS;
	}
	
	public String cancelAuditInfo(){
		try{
			Integer state = officeCarApply.getState();
			String remark = officeCarApply.getRemark();
			officeCarApply = officeCarApplyService.getOfficeCarApplyById(officeCarApply.getId());
			officeCarApply.setState(state);
			officeCarApply.setRemark(remark);
			officeCarApply.setAuditTime(new Date());
			officeCarApply.setAuditUserId(getLoginUser().getUserId());
			officeCarApplyService.updateApplyState(officeCarApply);
		}catch (Exception e) {
			e.printStackTrace();
			jsonError = "审核失败！";
		}
		return SUCCESS;
	}
	
	public String toString(String[] strs){
		StringBuffer sbf = new StringBuffer();
		int i = 0;
		for(String str:strs){
			if(i>0){
				sbf.append(","+str);
			}else{
				sbf.append(str);
			}
			i++;
		}
		return sbf.toString();
	}
	
	/**
	 *车辆管理
	 */
	public String carManage(){
		return SUCCESS;
	}
	public String carManageList(){
		officeCarInfos = officeCarInfoService.getOfficeCarInfoByUnitIdPage(getUnitId(), carNumber, getPage());
		
		return SUCCESS;
	}
	public String carManageEdit(){
		if(StringUtils.isBlank(carId)){
			officeCarInfo = new OfficeCarInfo();
		}else{
			officeCarInfo = officeCarInfoService.getOfficeCarInfoById(carId);
		}
		return SUCCESS;
	}
	public String saveCarInfo(){
		
		try {
			if(StringUtils.isBlank(officeCarInfo.getId())){
				officeCarInfo.setUnitId(getUnitId());
				officeCarInfo.setIsDeleted(false);
				officeCarInfoService.save(officeCarInfo);
			}else{
				officeCarInfoService.update(officeCarInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "保存失败！";
		}
		return SUCCESS;
	}
	
	public String deleteCar(){
		try {
			if(StringUtils.isNotBlank(carId)){
				boolean flag = officeCarApplyService.isExistUnUsed(carId, null);
				if(flag){
					jsonError = "该车辆存在已审核通过但未使用的记录（未到用车时间或接返时间），不能删除！";
					return SUCCESS;
				}
				officeCarInfoService.delete(carId);
			}else{
				jsonError = "传入数值有误！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "删除失败！";
		}
		return SUCCESS;
	}
	/**
	 *驾驶员管理
	 */
	public String driverManage(){
		officeCarDrivers = officeCarDriverService.getOfficeCarDriverByUnitIdList(getUnitId());
		setDriverNames(officeCarDrivers);
		return SUCCESS;
	}
	
	public String saveDriver(){//TODO
		try {
			List<OfficeCarDriver> updateList = new ArrayList<OfficeCarDriver>();
			List<OfficeCarDriver> insertList = new ArrayList<OfficeCarDriver>();
			Set<String> driverIds = new HashSet<String>();
			for(OfficeCarDriver driver : officeCarDrivers){
				if(StringUtils.isNotBlank(driver.getId())){
					updateList.add(driver);
				}
				else{
					driver.setUnitId(getUnitId());
					driver.setIsDeleted(false);
					insertList.add(driver);
				}
				driverIds.add(driver.getDriverId());
			}
			if(driverIds.size() != officeCarDrivers.size()){
				jsonError = "保存失败，存在相同驾驶员！";
				return SUCCESS;
			}
			officeCarDriverService.batchSave(insertList);
			officeCarDriverService.batchUpdate(updateList);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "保存失败！";
		}
		return SUCCESS;
	}
	
	public String deleteDriver(){
		try {
			if(StringUtils.isNotBlank(driverId)){
				boolean flag = officeCarApplyService.isExistUnUsed(null, driverId);
				if(flag){
					jsonError = "该驾驶员存在已审核通过但未出行的记录（未到用车时间或接返时间），不能删除！";
					return SUCCESS;
				}
				officeCarDriverService.delete(new String[]{driverId});
			}else{
				jsonError = "传入数值有误！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "删除失败！";
		}
		return SUCCESS;
	}
	/**
	 * 出车补贴设置
	 */
	public String subsidyManage(){
		officeSubsidys = officeCarSubsidyService.getOfficeCarSubsidyList(getUnitId());
		return SUCCESS;
	}
	public String saveSubsidy(){
		try {
			officeCarSubsidyService.save(getUnitId(),subsidyIds,scopeNames,subsidys);
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "保存失败！";
		}
		return SUCCESS;
	}
	
	public String deleteSubsidy(){
		try {
			if(StringUtils.isNotBlank(driverId)){
				officeCarApplies = officeCarApplyService.getOfficeCarApplyListByArea(getUnitId(), driverId);
				if(CollectionUtils.isNotEmpty(officeCarApplies)){
					jsonError = "该条记录已经被使用，不能删除！";
					return SUCCESS;
				}
				officeCarSubsidyService.delete(new String[]{driverId});
			}else{
				jsonError = "传入数值有误！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "删除失败！";
		}
		return SUCCESS;
	}
	/**
	 * 用车情况汇总
	 */
	public String carUseSummary(){
		if(StringUtils.isBlank(queryStartTime) && StringUtils.isBlank(queryEndTime)){
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 Calendar c = Calendar.getInstance();
			 queryEndTime = df.format(c.getTime());
			 c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			 queryStartTime = df.format(c.getTime());
		}
		return SUCCESS;
	}
	
	public String carUseSummaryList(){
		if(StringUtils.isNotBlank(querySumType)){
			useCarInfolist = officeCarApplyService.getOfficeCarUseInfoList(getUnitId(), queryStartTime, queryEndTime, querySumType);
			return "detail";
		}else{
			officeCarApplies = officeCarApplyService.getOfficeCarApplyByUnitIdPage(getUnitId(), queryStartTime, queryEndTime, String.valueOf(Constants.APPLY_STATE_PASS), null, getPage());
			return SUCCESS;
		}
	}
	
	/**
	 * 用车加班时间
	 */
	public String carOverTime(){
		return SUCCESS;
	}
	
	public String carOverTimeList(){//TODO
		officeCarApplyDtos = officeCarApplyService.getOfficeCarApplyByUnitIdStatePage(getUnitId(),
				 queryStartTime, queryEndTime, String.valueOf(Constants.APPLY_STATE_PASS), driverId);
		return SUCCESS;
	}
	
	public String carOverTimeEdit(){
		officeCarApply = officeCarApplyService.getOfficeCarApplyById(applyId);
		return SUCCESS;
	}
	
	public String carOverTimeSave(){
		try {
			officeCarApplyService.updateOverTimeNumber(officeCarApply);
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
		}
		return SUCCESS;
	}
	
	public String carOverTimeExport(){
		officeCarApplyDtos = officeCarApplyService.getOfficeCarApplyByUnitIdStatePage(getUnitId(),
				 queryStartTime, queryEndTime, String.valueOf(Constants.APPLY_STATE_PASS), driverId);
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("用车加班时间汇总", 5, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("驾驶员",1,style2));
		zdlist.add(new ZdCell("用车时间",1,style2));
		zdlist.add(new ZdCell("用车事由",1,style2));
		zdlist.add(new ZdCell("加班天数",1,style2));
		zdlist.add(new ZdCell("总加班天数",1,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		 DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
		for(OfficeCarApplyDto dto:officeCarApplyDtos){
			zdlist=new ArrayList<ZdCell>();
			zdlist.add(new ZdCell(dto.getDriverName(), 1, dto.getOfficeCarApplyList().size(),style3));
			int i=0;
			for(OfficeCarApply app : dto.getOfficeCarApplyList()){
				zdlist.add(new ZdCell(sdf.format(app.getUseTime()), 1, style3));
				zdlist.add(new ZdCell(app.getReason(), 1, style3));
				BigDecimal mData = new BigDecimal(app.getOvertimeNumber()).setScale(1, BigDecimal.ROUND_HALF_UP);
				BigDecimal mData2 = new BigDecimal(dto.getOvertimeNumber()).setScale(1, BigDecimal.ROUND_HALF_UP);
				zdlist.add(new ZdCell(mData.toString(), 1, style3));
				if(i==0){
					zdlist.add(new ZdCell(mData2.toString(), 1, dto.getOfficeCarApplyList().size(),style3));
				}
				i++;
				zdExcel.add(zdlist.toArray(new ZdCell[0]));
				zdlist = new ArrayList<ZdCell>();
				zdlist.add(new ZdCell());
			}
		}
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("office_car_overtime_export");
		return NONE;
	}
	
	/**
	 * 我的用车详情
	 * @return
	 */
	public String myCarUseDetail(){
		return SUCCESS;
	}
	
	public String myCarUseDetailList(){
		String unitId = getUnitId();
		String userId = getLoginUser().getUserId();
		officeCarApplies = officeCarApplyService.getOfficeCarApplyByUnitIdPage(unitId, queryStartTime, queryEndTime, String.valueOf(Constants.APPLY_STATE_PASS), userId, getPage());
		List<OfficeCarSubsidy> subsidylist = officeCarSubsidyService.getOfficeCarSubsidyList(unitId);
		List<OfficeCarDriver> driverList = officeCarDriverService.getOfficeCarDriverByUnitIdList(unitId);
		String driverId = "";
		for(OfficeCarDriver driver : driverList){
			if(userId.equals(driver.getDriverId()))
				driverId = driver.getId();
		}
		
		for(OfficeCarApply apply : officeCarApplies){
			if(apply.getDriverId().contains(driverId)){
				int i = getNumber(apply.getDriverId().split(","), driverId);
				apply.setDriverName(apply.getDriverName().split(",")[i]);
				apply.setCarNumber(apply.getCarNumber().split(",")[i]);
			}
			for(OfficeCarSubsidy subsidy : subsidylist){
				if(subsidy.getId().equals(apply.getArea())){
					apply.setSubsidy(subsidy.getSubsidy() );
				}
			}
		}
		return SUCCESS;
	}
	
	public int getNumber(String[] strs, String s){
		int i = 0;
		for(String str:strs){
			if(str.equals(s)){
				break;
			}
			i++;
		}
		return i;
	}
	
	public boolean isOfficeCarAdmin(){
		return isCustomRole(OFFCIE_CAR_AUTH);
	}
	
	public boolean isDeptHead(){
		return deptService.isDeputyHead(getUnitId(), getLoginInfo().getUser().getId());
	}
	
	public boolean isDeptLeader(){
		String deptId = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
		if(StringUtils.isNotBlank(deptId))
			return true;
		return false;
	}
	
	/**
	 * 设置驾驶员姓名
	 */
	public void setDriverNames(List<OfficeCarDriver> drivers){
		//TODO
		Map<String,User> userMap = userService.getUserWithDelMap(getUnitId());
		for(OfficeCarDriver driver : drivers){
			if(userMap.get(driver.getDriverId()) != null){
				driver.setDriverName(userMap.get(driver.getDriverId()).getRealname());
			}else{
				driver.setDriverName(driver.getName() + "(已删除)");
			}
		}
	}
	
	/**
	 * 判断当前用户是否指定角色
	 * @param roleCode
	 */
	public boolean isCustomRole(String roleCode){
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), roleCode);
		boolean flag;
		if(role == null){
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(roleUs)){
			for(CustomRoleUser ru : roleUs){
				if(StringUtils.equals(ru.getRoleId(), role.getId())){
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}
	
	
	public OfficeCarInfo getOfficeCarInfo() {
		return officeCarInfo;
	}
	public void setOfficeCarInfo(OfficeCarInfo officeCarInfo) {
		this.officeCarInfo = officeCarInfo;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public void setOfficeCarInfoService(OfficeCarInfoService officeCarInfoService) {
		this.officeCarInfoService = officeCarInfoService;
	}
	public List<OfficeCarInfo> getOfficeCarInfos() {
		return officeCarInfos;
	}
	public void setOfficeCarInfos(List<OfficeCarInfo> officeCarInfos) {
		this.officeCarInfos = officeCarInfos;
	}
	public List<OfficeCarDriver> getOfficeCarDrivers() {
		return officeCarDrivers;
	}
	public void setOfficeCarDrivers(List<OfficeCarDriver> officeCarDrivers) {
		this.officeCarDrivers = officeCarDrivers;
	}
	public void setOfficeCarDriverService(
			OfficeCarDriverService officeCarDriverService) {
		this.officeCarDriverService = officeCarDriverService;
	}
	public String[] getDriverIds() {
		return driverIds;
	}
	public void setDriverIds(String[] driverIds) {
		this.driverIds = driverIds;
	}
	public String[] getDriverNames() {
		return driverNames;
	}
	public void setDriverNames(String[] driverNames) {
		this.driverNames = driverNames;
	}
	public String[] getMobilePhones() {
		return mobilePhones;
	}
	public void setMobilePhones(String[] mobilePhones) {
		this.mobilePhones = mobilePhones;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public OfficeCarApply getOfficeCarApply() {
		return officeCarApply;
	}
	public void setOfficeCarApply(OfficeCarApply officeCarApply) {
		this.officeCarApply = officeCarApply;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setOfficeCarApplyService(OfficeCarApplyService officeCarApplyService) {
		this.officeCarApplyService = officeCarApplyService;
	}
	public List<OfficeCarApply> getOfficeCarApplies() {
		return officeCarApplies;
	}
	public void setOfficeCarApplies(List<OfficeCarApply> officeCarApplies) {
		this.officeCarApplies = officeCarApplies;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public List<UseCarInfoDto> getUseCarInfolist() {
		return useCarInfolist;
	}
	public void setUseCarInfolist(List<UseCarInfoDto> useCarInfolist) {
		this.useCarInfolist = useCarInfolist;
	}
	public String getQueryStartTime() {
		return queryStartTime;
	}
	public void setQueryStartTime(String queryStartTime) {
		this.queryStartTime = queryStartTime;
	}
	public String getQueryEndTime() {
		return queryEndTime;
	}
	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}
	public String getQuerySumType() {
		return querySumType;
	}
	public void setQuerySumType(String querySumType) {
		this.querySumType = querySumType;
	}
	public String getQueryState() {
		return queryState;
	}
	public void setQueryState(String queryState) {
		this.queryState = queryState;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String[] getCarIds() {
		return carIds;
	}
	public void setCarIds(String[] carIds) {
		this.carIds = carIds;
	}
	public UseCarInfoDto getUseCarInfoDto() {
		return useCarInfoDto;
	}
	public void setUseCarInfoDto(UseCarInfoDto useCarInfoDto) {
		this.useCarInfoDto = useCarInfoDto;
	}
	
	public List<OfficeCarSubsidy> getOfficeSubsidys() {
		return officeSubsidys;
	}
	public void setOfficeSubsidys(List<OfficeCarSubsidy> officeSubsidys) {
		this.officeSubsidys = officeSubsidys;
	}
	public String[] getSubsidyIds() {
		return subsidyIds;
	}
	public void setSubsidyIds(String[] subsidyIds) {
		this.subsidyIds = subsidyIds;
	}
	public String[] getScopeNames() {
		return scopeNames;
	}
	public void setScopeNames(String[] scopeNames) {
		this.scopeNames = scopeNames;
	}
	public String[] getSubsidys() {
		return subsidys;
	}
	public void setSubsidys(String[] subsidys) {
		this.subsidys = subsidys;
	}
	public void setOfficeCarSubsidyService(
			OfficeCarSubsidyService officeCarSubsidyService) {
		this.officeCarSubsidyService = officeCarSubsidyService;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public boolean isCarApply() {
		return carApply;
	}
	public void setCarApply(boolean carApply) {
		this.carApply = carApply;
	}
	public List<OfficeCarApplyDto> getOfficeCarApplyDtos() {
		return officeCarApplyDtos;
	}
	public void setOfficeCarApplyDtos(List<OfficeCarApplyDto> officeCarApplyDtos) {
		this.officeCarApplyDtos = officeCarApplyDtos;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}
	
}