package net.zdsoft.office.dailyoffice.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.eis.sms.dto.SendDetailDto;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeCarApplyDao;
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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ecs.xhtml.a;
/**
 * office_car_apply
 * @author 
 * 
 */
public class OfficeCarApplyServiceImpl implements OfficeCarApplyService{
	
	private UserService userService;
	private TeacherService teacherService;
	private DeptService deptService;
	private UnitService unitService;
	private SmsClientService smsClientService;
	private OfficeMsgSendingService officeMsgSendingService;
	
	private OfficeCarInfoService officeCarInfoService;
	private OfficeCarDriverService officeCarDriverService;
	
	private OfficeCarApplyDao officeCarApplyDao;
	private OfficeCarSubsidyService officeCarSubsidyService;
	@Override
	public OfficeCarApply save(OfficeCarApply officeCarApply){
		return officeCarApplyDao.save(officeCarApply);
	}

	@Override
	public Integer delete(String id){
		return officeCarApplyDao.delete(id);
	}

	@Override
	public Integer update(OfficeCarApply officeCarApply){
		return officeCarApplyDao.update(officeCarApply);
	}
	
	public void updateState(OfficeCarApply officeCarApply){
		if(officeCarApply.getState() == Constants.APPLY_STATE_PASS){
			OfficeCarApply officeCarApplyDetail = officeCarApplyDao.getOfficeCarApplyById(officeCarApply.getId());
			String[] carIds = officeCarApply.getCarId().split(",");
			String[] driverIds=null;
			if(org.apache.commons.lang3.StringUtils.isNoneBlank(officeCarApply.getDriverId())){
				driverIds = officeCarApply.getDriverId().split(",");
			}
			Map<String, OfficeCarInfo> officeCarInfoMap = officeCarInfoService.getOfficeCarInfoMapByUnitId(officeCarApplyDetail.getUnitId());
			Map<String, OfficeCarDriver> officeCarDriverMap = officeCarDriverService.getOfficeCarDriverMapByUnitId(officeCarApplyDetail.getUnitId());
			Map<String, User> userMap = userService.getUserWithDelMap(officeCarApplyDetail.getUnitId());
			Unit unit = unitService.getUnit(officeCarApplyDetail.getUnitId());
			DateFormat df = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分");
			//组织消息跟短信内容
			StringBuffer sbf = new StringBuffer();
			sbf.append("您好，用车信息审核安排如下：");
			
			sbf.append(" 用车联系人：").append(userMap.get(officeCarApplyDetail.getLinkUserId()).getRealname()).append(" 电话：").append(officeCarApplyDetail.getMobilePhone());
			sbf.append(" 用车时间：").append(df.format(officeCarApplyDetail.getUseTime())).append(" 地点：").append(officeCarApplyDetail.getCarLocation());
			if(officeCarApplyDetail.getWaitingTime()!=null){
				sbf.append(" 接返时间：").append(df.format(officeCarApplyDetail.getWaitingTime())).append("；");
			}else{
				sbf.append(" 接返时间： 不需接返 ；");
			}
			if(ArrayUtils.isNotEmpty(driverIds)){
				for(int i=0;i<carIds.length;i++){
					sbf.append("驾驶员").append(i+1).append("：").append(officeCarDriverMap.get(driverIds[i]).getName()).append(" 车牌号：").append(officeCarInfoMap.get(carIds[i]).getCarNumber()).append(" 联系电话：").append(officeCarDriverMap.get(driverIds[i]).getMobilePhone()).append("；");
				}
			}
			//TODO---------------------组织消息内容------------------------
			OfficeMsgSending officeMsgSending = new OfficeMsgSending();
			officeMsgSending.setCreateUserId(officeCarApply.getAuditUserId());
			officeMsgSending.setTitle("车辆审核通过信息提醒");
			officeMsgSending.setContent(sbf.toString());
			officeMsgSending.setSimpleContent(sbf.toString());
			officeMsgSending.setUserIds(officeCarApplyDetail.getLinkUserId());
			officeMsgSending.setSendUserName(userMap.get(officeCarApply.getAuditUserId()).getRealname());
			officeMsgSending.setState(Constants.MSG_STATE_SEND);
			officeMsgSending.setIsNeedsms(false);
			officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_CAR);
			officeMsgSendingService.save(officeMsgSending, null, null);
			//TODO-------------------组织短信内容并发送----------------------
			StringBuffer sbf1 = new StringBuffer();
			sbf1.append("您好，您有新的出车任务：");
			
			sbf1.append(" 用车联系人：").append(userMap.get(officeCarApplyDetail.getLinkUserId()).getRealname()).append(" 电话：").append(officeCarApplyDetail.getMobilePhone());
			sbf1.append(" 用车时间：").append(df.format(officeCarApplyDetail.getUseTime())).append(" 地点：").append(officeCarApplyDetail.getCarLocation());
			if(officeCarApplyDetail.getWaitingTime()!=null){
				sbf1.append(" 接返时间：").append(df.format(officeCarApplyDetail.getWaitingTime())).append("；");
			}else{
				sbf1.append(" 接返时间： 不需接返 ；");
			}
			if(ArrayUtils.isNotEmpty(driverIds)){
				for(int i=0;i<carIds.length;i++){
					sbf1.append("驾驶员").append(i+1).append("：").append(officeCarDriverMap.get(driverIds[i]).getName()).append(" 车牌号：").append(officeCarInfoMap.get(carIds[i]).getCarNumber()).append(" 联系电话：").append(officeCarDriverMap.get(driverIds[i]).getMobilePhone()).append("；");
				}
			}
			MsgDto msgDto = new MsgDto();
			msgDto.setUserId(officeCarApply.getAuditUserId());
			msgDto.setUnitName(unit.getName());
			msgDto.setUnitId(unit.getId());
			msgDto.setUserName(userMap.get(officeCarApply.getAuditUserId()).getRealname());
			msgDto.setContent(sbf1.toString());
			msgDto.setTiming(false);
			List<SendDetailDto> sendDetailDtos = new ArrayList<SendDetailDto>();
			if(ArrayUtils.isNotEmpty(driverIds)){
				for(String driverId:driverIds){
					OfficeCarDriver officeCarDriver = officeCarDriverMap.get(driverId);
					SendDetailDto sendDetailDto = new SendDetailDto();
					sendDetailDto.setReceiverId(officeCarDriver.getDriverId());
					sendDetailDto.setReceiverName(officeCarDriver.getName());
					sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
					sendDetailDto.setMobile(officeCarDriver.getMobilePhone());// 短信接收人手机号码
					sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
					sendDetailDto.setUnitId(officeCarApplyDetail.getUnitId());// 短信接收用户单位id
					sendDetailDtos.add(sendDetailDto);
				}
			}
			SmsThread smsThread = new SmsThread(msgDto, sendDetailDtos);
			smsThread.start();
			//-----------------------发短信结束------------------------
			
			//TODO-------------------发送短信给申请人及乘车联系人----------------------
			StringBuffer sbf2 = new StringBuffer();
			sbf2.append("您好，您于").append(df.format(officeCarApplyDetail.getUseTime())).append(" 时间，地点为").append(officeCarApplyDetail.getCarLocation());
			sbf2.append("的用车情况已经批准，");
			if(ArrayUtils.isNotEmpty(driverIds)){
				sbf2.append("具体安排如下：");
			}
			if(ArrayUtils.isNotEmpty(driverIds)){
				for(int i=0;i<carIds.length;i++){
					sbf2.append("驾驶员").append(i+1).append("：").append(officeCarDriverMap.get(driverIds[i]).getName()).append(" 车牌号：").append(officeCarInfoMap.get(carIds[i]).getCarNumber()).append(" 联系电话：").append(officeCarDriverMap.get(driverIds[i]).getMobilePhone()).append("；");
				}
			}
			MsgDto msgDto1 = new MsgDto();
			msgDto1.setUserId(officeCarApply.getAuditUserId());
			msgDto1.setUnitName(unit.getName());
			msgDto1.setUnitId(unit.getId());
			msgDto1.setUserName(userMap.get(officeCarApply.getAuditUserId()).getRealname());
			msgDto1.setContent(sbf2.toString());
			msgDto1.setTiming(false);
			List<SendDetailDto> sendDetailDtos1 = new ArrayList<SendDetailDto>();
			//申请人
			User user = userMap.get(officeCarApplyDetail.getApplyUserId());
			Teacher teacher = teacherService.getTeacher(user.getTeacherid());
			if(teacher!=null && StringUtils.isNotBlank(teacher.getPersonTel())){
				SendDetailDto sendDetailDto = new SendDetailDto();
				sendDetailDto.setReceiverId(user.getId());
				sendDetailDto.setReceiverName(user.getRealname());
				sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
				sendDetailDto.setMobile(teacher.getPersonTel());// 短信接收人手机号码
				sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
				sendDetailDto.setUnitId(officeCarApplyDetail.getUnitId());// 短信接收用户单位id
				sendDetailDtos1.add(sendDetailDto);
			}
			
			//如果乘车联系人跟申请人不一致，乘车联系人也要发一条短信
			if(!officeCarApplyDetail.getLinkUserId().equals(officeCarApplyDetail.getApplyUserId())){
				User user1 = userMap.get(officeCarApplyDetail.getLinkUserId());
				Teacher teacher1 = teacherService.getTeacher(user1.getTeacherid());
				if(teacher1!=null && StringUtils.isNotBlank(teacher1.getPersonTel())){
					SendDetailDto sendDetailDto1 = new SendDetailDto();
					sendDetailDto1.setReceiverId(user1.getId());
					sendDetailDto1.setReceiverName(user1.getRealname());
					sendDetailDto1.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
					sendDetailDto1.setMobile(teacher1.getPersonTel());// 短信接收人手机号码
					sendDetailDto1.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
					sendDetailDto1.setUnitId(officeCarApplyDetail.getUnitId());// 短信接收用户单位id
					sendDetailDtos1.add(sendDetailDto1);
				}
			}
			if(sendDetailDtos1.size() > 0){
				SmsThread smsThread1 = new SmsThread(msgDto1, sendDetailDtos1);
				smsThread1.start();
			}
			//-----------------------发短信结束------------------------
		}else{
			officeCarApply.setArea("");
			officeCarApply.setRemark("");
		}
		officeCarApplyDao.updateState(officeCarApply);
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
		public synchronized void run() {
			smsClientService.saveSmsBatch(msgDto, sendDetailDtoList);
 		}
    }
	
	@Override
	public void updateOverTimeNumber(OfficeCarApply officeCarApply) {
		officeCarApplyDao.updateOverTimeNumber(officeCarApply);
	}
	
	@Override
	public boolean isExistUnUsed(String carId, String driverId) {
		return officeCarApplyDao.isExistUnUsed(carId, driverId);
	}
	
	@Override
	public OfficeCarApply getOfficeCarApplyById(String id){
		OfficeCarApply officeCarApply = officeCarApplyDao.getOfficeCarApplyById(id);

		Map<String, User> userMap = userService.getUserWithDelMap(officeCarApply.getUnitId());
		if(StringUtils.isNotBlank(officeCarApply.getApplyUserId())){
			if(userMap.containsKey(officeCarApply.getApplyUserId())){
				officeCarApply.setApplyUserName(userMap.get(officeCarApply.getApplyUserId()).getRealname());
			}else{
				officeCarApply.setApplyUserName("用户已删除");
			}
		}
		if(StringUtils.isNotBlank(officeCarApply.getLinkUserId())){
			if(userMap.containsKey(officeCarApply.getLinkUserId())){
				officeCarApply.setLinkUserName(userMap.get(officeCarApply.getLinkUserId()).getRealname());
			}else{
				officeCarApply.setLinkUserName("用户已删除");
			}
		}
		if(StringUtils.isNotBlank(officeCarApply.getAuditUserId())){
			if(StringUtils.isNotBlank(officeCarApply.getAuditUserId())){
				if(userMap.containsKey(officeCarApply.getAuditUserId())){
					officeCarApply.setAuditUserName(userMap.get(officeCarApply.getAuditUserId()).getRealname());
				}else{
					officeCarApply.setAuditUserName("用户已删除");
				}
			}
		}
		if(officeCarApply.getState() == Constants.APPLY_STATE_PASS || officeCarApply.getState() == Constants.APPLY_STATE_CANCEL_NEED_AUDIT || officeCarApply.getState() == Constants.APPLY_STATE_CANCEL_PASS){
			Map<String, OfficeCarInfo> officeCarInfoMap = officeCarInfoService.getOfficeCarInfoMapByUnitId(officeCarApply.getUnitId());
			Map<String, OfficeCarDriver> officeCarDriverMap = officeCarDriverService.getOfficeCarDriverMapByUnitId(officeCarApply.getUnitId());
			setDetail(officeCarInfoMap, officeCarDriverMap, userMap, officeCarApply);
		}
		return officeCarApply;
	}
	
	@Override
	public UseCarInfoDto getCarUseInfo(String carId) {
		OfficeCarInfo officeCarInfo = officeCarInfoService.getOfficeCarInfoById(carId);
		UseCarInfoDto useCarInfoDto = new UseCarInfoDto();
		useCarInfoDto.setSeating(officeCarInfo.getSeating());
		List<OfficeCarApply> officeCarApplies = officeCarApplyDao.getOfficeCarApplyByCarId(carId);
		useCarInfoDto.setOfficeCarApplyList(officeCarApplies);
		return useCarInfoDto;
	}

	@Override
	public List<OfficeCarApply> getOfficeCarApplyListPage(String unitId, String applyUserId, String startTime, String endTime, String state, Pagination page){
		List<OfficeCarApply> officeCarApplys = officeCarApplyDao.getOfficeCarListPage(unitId, applyUserId, startTime, endTime, state, page);
		setDetailInfo(unitId, officeCarApplys);
		return officeCarApplys;
	}
	
	@Override
	public List<OfficeCarApply> getOfficeCarApplyByUnitIdPage(String unitId,
			String startTime, String endTime, String state, String driverId, Pagination page) {
		List<OfficeCarApply> officeCarApplys = officeCarApplyDao.getOfficeCarApplyByUnitIdPage(unitId, startTime, endTime, state, driverId, page);
		setDetailInfo(unitId, officeCarApplys);
		return officeCarApplys;
	}
	
	public List<OfficeCarApply> getOfficeCarPassList(String unitId, String startTime, String endTime, Pagination page){
		List<OfficeCarApply> officeCarApplys = officeCarApplyDao.getOfficeCarApplyByUnitIdPage(unitId, startTime, endTime, String.valueOf(Constants.APPLY_STATE_PASS), null, null);
		setDetailInfo(unitId, officeCarApplys);
		return officeCarApplys;
	}
	
	public List<OfficeCarApplyDto> getOfficeCarApplyByUnitIdStatePage(String unitId,
			String startTime, String endTime, String state,String driverId){//TODO
		List<OfficeCarApply> officeCarApplys = officeCarApplyDao.getOfficeCarApplyByUnitIdStatePage(unitId, startTime, endTime, state, driverId);
		List<OfficeCarApplyDto> officeCarApplyDtos=new ArrayList<OfficeCarApplyDto>();
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
		Map<String, OfficeCarInfo> officeCarInfoMap = officeCarInfoService.getOfficeCarInfoMapByUnitId(unitId);
		Map<String, OfficeCarDriver> officeCarDriverMap = officeCarDriverService.getOfficeCarDriverMapByUnitId(unitId);
		Set<String> driverIdSet = new HashSet<String>();
		for (OfficeCarApply officeCarApply : officeCarApplys) {
			if(org.apache.commons.lang3.StringUtils.isNoneBlank(officeCarApply.getDriverId())){
				String[] driverIds = officeCarApply.getDriverId().split(",");
				driverIdSet.addAll(Arrays.asList(driverIds));
			}
		}
			float overTime=0;
			for(String driverId1:driverIdSet){
				OfficeCarApplyDto applyDto=new OfficeCarApplyDto();
				List<OfficeCarApply> officeCarApplys1 = officeCarApplyDao.getOfficeCarApplyByUnitIdStatePage(unitId, startTime, endTime, String.valueOf(Constants.APPLY_STATE_PASS), driverId1);
				for (OfficeCarApply officeCarApply2 : officeCarApplys1) {
					if(StringUtils.isNotBlank(officeCarApply2.getDeptId())){
						if(deptMap.containsKey(officeCarApply2.getDeptId())){
							officeCarApply2.setDeptName(deptMap.get(officeCarApply2.getDeptId()).getDeptname());
						}else{
							officeCarApply2.setDeptName("部门已删除");
						}
					}
					if(StringUtils.isNotBlank(officeCarApply2.getLinkUserId())){
						if(userMap.containsKey(officeCarApply2.getLinkUserId())){
							officeCarApply2.setLinkUserName(userMap.get(officeCarApply2.getLinkUserId()).getRealname());
						}else{
							officeCarApply2.setLinkUserName("用户已删除");
						}
					}
					if(officeCarApply2.getUseTime() != null){
						try {
							officeCarApply2.setXinqi(OfficeUtils.dayForWeek(officeCarApply2.getUseTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					StringBuffer str = new StringBuffer();
					String[] carIds = officeCarApply2.getCarId().split(",");
					int i = 0;
					for(String carId:carIds){
						if(i == 0){
							str.append(officeCarInfoMap.get(carId).getCarNumber());
						}else{
							str.append(","+officeCarInfoMap.get(carId).getCarNumber());
						}
						i++;
					}
					officeCarApply2.setCarNumber(str.toString());
					overTime+=officeCarApply2.getOvertimeNumber();
				}
				applyDto.setDriverId(driverId1);
				OfficeCarDriver driver = officeCarDriverMap.get(driverId1);
				if(driver != null){
					if(userMap.get(driver.getDriverId()) != null){
						driver.setDriverName(userMap.get(driver.getDriverId()).getRealname());
					}else{
						driver.setDriverName(driver.getName() + "(已删除)");
					}
					applyDto.setDriverName(officeCarDriverMap.get(driverId1).getDriverName());
				}else{
					applyDto.setDriverName("驾驶员已删除");
				}
				applyDto.setOfficeCarApplyList(officeCarApplys1);
				applyDto.setCount(String.valueOf(officeCarApplys1.size()));
				applyDto.setOvertimeNumber(overTime);
				overTime=0;
				officeCarApplyDtos.add(applyDto);
			}
		return officeCarApplyDtos;
}
	public List<UseCarInfoDto> getOfficeCarUseInfoList(String unitId, String startTime, String endTime, String querySumType){
		List<OfficeCarApply> officeCarApplys = officeCarApplyDao.getOfficeCarApplyByUnitIdPage(unitId, startTime, endTime, String.valueOf(Constants.APPLY_STATE_PASS), null, null);
		setDetailInfo(unitId, officeCarApplys);
		List<UseCarInfoDto> useCarInfoDtos = new ArrayList<UseCarInfoDto>();
		if("1".equals(querySumType)){
			setCarUseInfo(unitId, useCarInfoDtos, officeCarApplys);
		}else if("2".equals(querySumType)){
			setDriverUserInfo(unitId, useCarInfoDtos, officeCarApplys);
		}
		
		
		
		return useCarInfoDtos;
	}
	@Override
	public List<OfficeCarApply> getOfficeCarApplyListByArea(String unitId,
			String driverId) {
		return officeCarApplyDao.getOfficeCarApplyListByArea( unitId,
				 driverId);
	}
	@Override
	//TODO
	public List<UseCarInfoDto> getOfficeCarPassSum(String unitId,
			String startTime, String endTime) {
		List<OfficeCarApply> officeCarApplys = officeCarApplyDao.getOfficeCarApplyByUnitIdStatePage(unitId, startTime, endTime, String.valueOf(Constants.APPLY_STATE_PASS), null);
		List<UseCarInfoDto> useCarInfoDtos = new ArrayList<UseCarInfoDto>();
		List<OfficeCarDriver> officeCarDriverList = officeCarDriverService.getOfficeCarDriverWithDelByUnitIdList(unitId);

		for(OfficeCarDriver officeCarDriver:officeCarDriverList){
			List<OfficeCarApply> officeCarApplies = new ArrayList<OfficeCarApply>();
			for(OfficeCarApply officeCarApply:officeCarApplys){
				if(officeCarApply.getDriverId().contains(officeCarDriver.getId())){
					OfficeCarApply officeCarApplyTemp = new OfficeCarApply();
					if(!officeCarApply.getIsOvertime())
						officeCarApply.setOvertimeNumber(0f);
					setToTemp(officeCarApply,officeCarApplyTemp);
					if(officeCarApply.getIsOvertime()){
						officeCarApplyTemp.setSubsidy(officeCarApply.getOvertimeNumber() * 100);
					}
					officeCarApplies.add(officeCarApplyTemp);
				}
			}
			if(officeCarApplies.size() > 0){
				UseCarInfoDto useCarInfoDto = new UseCarInfoDto();
				useCarInfoDto.setDriverName(officeCarDriver.getName());
				useCarInfoDto.setOfficeCarApplyList(officeCarApplies);
				useCarInfoDtos.add(useCarInfoDto);
			}
		}
		
		
		return useCarInfoDtos;
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
	
	public void setToTemp(OfficeCarApply officeCarApply,OfficeCarApply officeCarApplyTemp){
		officeCarApplyTemp.setId(officeCarApply.getId());
		officeCarApplyTemp.setUnitId(officeCarApply.getUnitId());
		officeCarApplyTemp.setApplyUserId(officeCarApply.getApplyUserId());
		officeCarApplyTemp.setApplyTime(officeCarApply.getApplyTime());
		officeCarApplyTemp.setDeptId(officeCarApply.getDeptId());
		officeCarApplyTemp.setLinkUserId(officeCarApply.getLinkUserId());
		officeCarApplyTemp.setMobilePhone(officeCarApply.getMobilePhone());
		officeCarApplyTemp.setPersonNumber(officeCarApply.getPersonNumber());
		officeCarApplyTemp.setReason(officeCarApply.getReason());
		officeCarApplyTemp.setCarLocation(officeCarApply.getCarLocation());
		officeCarApplyTemp.setIsGoback(officeCarApply.getIsGoback());
		officeCarApplyTemp.setIsNeedWaiting(officeCarApply.getIsNeedWaiting());
		officeCarApplyTemp.setWaitingTime(officeCarApply.getWaitingTime());
		officeCarApplyTemp.setUseTime(officeCarApply.getUseTime());
		officeCarApplyTemp.setIsDeleted(officeCarApply.getIsDeleted());
		officeCarApplyTemp.setAuditUserId(officeCarApply.getAuditUserId());
		officeCarApplyTemp.setState(officeCarApply.getState());
		officeCarApplyTemp.setAuditTime(officeCarApply.getAuditTime());
		officeCarApplyTemp.setArea(officeCarApply.getArea());
		officeCarApplyTemp.setCarId(officeCarApply.getCarId());
		officeCarApplyTemp.setDriverId(officeCarApply.getDriverId());
		officeCarApplyTemp.setRemark(officeCarApply.getRemark());
		officeCarApplyTemp.setOvertimeNumber(officeCarApply.getOvertimeNumber());
		officeCarApplyTemp.setApplyUserName(officeCarApply.getApplyUserName());
		officeCarApplyTemp.setDeptName(officeCarApply.getDeptName());
		officeCarApplyTemp.setLinkUserName(officeCarApply.getLinkUserName());
		officeCarApplyTemp.setAuditUserName(officeCarApply.getAuditUserName());
		officeCarApplyTemp.setCarNumber(officeCarApply.getCarNumber());
		officeCarApplyTemp.setDriverName(officeCarApply.getDriverName());
		officeCarApplyTemp.setXinqi(officeCarApply.getXinqi());
	}
	
	public void setCarUseInfo(String unitId,List<UseCarInfoDto> useCarInfoDtos,List<OfficeCarApply> officeCarApplys){
		List<OfficeCarInfo> officeCarInfoList = officeCarInfoService.getOfficeCarInfoByUnitIdList(unitId);
		for(OfficeCarInfo officeCarInfo:officeCarInfoList){
			List<OfficeCarApply> officeCarApplies = new ArrayList<OfficeCarApply>();
			for(OfficeCarApply officeCarApply:officeCarApplys){
				if(officeCarApply.getCarId().contains(officeCarInfo.getId())){
					OfficeCarApply officeCarApplyTemp = new OfficeCarApply();
					setToTemp(officeCarApply,officeCarApplyTemp);
					int i = getNumber(officeCarApplyTemp.getCarId().split(","), officeCarInfo.getId());
					if(org.apache.commons.lang3.StringUtils.isNotBlank(officeCarApplyTemp.getDriverName())){
						officeCarApplyTemp.setDriverName(officeCarApplyTemp.getDriverName().split(",")[i]);
					}
					officeCarApplies.add(officeCarApplyTemp);
				}
			}
			if(officeCarApplies.size() > 0){
				UseCarInfoDto useCarInfoDto = new UseCarInfoDto();
				useCarInfoDto.setCarNumber(officeCarInfo.getCarNumber());
				useCarInfoDto.setOfficeCarApplyList(officeCarApplies);
				useCarInfoDtos.add(useCarInfoDto);
			}
		}
	}
	
	public void setDriverUserInfo(String unitId,List<UseCarInfoDto> useCarInfoDtos,List<OfficeCarApply> officeCarApplys){
		List<OfficeCarDriver> officeCarDriverList = officeCarDriverService.getOfficeCarDriverByUnitIdList(unitId);
		float sumSubsidy = 0;
		for(OfficeCarDriver officeCarDriver:officeCarDriverList){
			List<OfficeCarApply> officeCarApplies = new ArrayList<OfficeCarApply>();
			for(OfficeCarApply officeCarApply:officeCarApplys){
				if(org.apache.commons.lang3.StringUtils.isNotBlank(officeCarApply.getDriverId())&&officeCarApply.getDriverId().contains(officeCarDriver.getId())){
					OfficeCarApply officeCarApplyTemp = new OfficeCarApply();
					setToTemp(officeCarApply,officeCarApplyTemp);
					int i = getNumber(officeCarApplyTemp.getDriverId().split(","), officeCarDriver.getId());
					if(org.apache.commons.lang3.StringUtils.isNotBlank(officeCarApplyTemp.getCarNumber())){
						officeCarApplyTemp.setCarNumber(officeCarApplyTemp.getCarNumber().split(",")[i]);
					}
					OfficeCarSubsidy subsidy = officeCarSubsidyService.getOfficeCarSubsidyById(officeCarApply.getArea());
					if(subsidy != null){
						officeCarApplyTemp.setSubsidy(subsidy.getSubsidy() );
					}
					sumSubsidy = officeCarApplyTemp.getSubsidy() + sumSubsidy;
					officeCarApplies.add(officeCarApplyTemp);
				}
			}
			if(officeCarApplies.size() > 0){
				UseCarInfoDto useCarInfoDto = new UseCarInfoDto();
				useCarInfoDto.setDriverName(officeCarDriver.getName());
				useCarInfoDto.setOfficeCarApplyList(officeCarApplies);
				useCarInfoDto.setSumSubsidy(sumSubsidy);
				useCarInfoDtos.add(useCarInfoDto);
			}
			sumSubsidy = 0;
		}
	}
	
	public void setDetailInfo(String unitId,List<OfficeCarApply> officeCarApplys){
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
		Map<String, OfficeCarInfo> officeCarInfoMap = officeCarInfoService.getOfficeCarInfoMapByUnitId(unitId);
		Map<String, OfficeCarDriver> officeCarDriverMap = officeCarDriverService.getOfficeCarDriverMapByUnitId(unitId);
		
		for(OfficeCarApply officeCarApply:officeCarApplys){
			if(StringUtils.isNotBlank(officeCarApply.getDeptId())){
				if(deptMap.containsKey(officeCarApply.getDeptId())){
					officeCarApply.setDeptName(deptMap.get(officeCarApply.getDeptId()).getDeptname());
				}else{
					officeCarApply.setDeptName("部门已删除");
				}
			}
			if(StringUtils.isNotBlank(officeCarApply.getApplyUserId())){
				if(userMap.containsKey(officeCarApply.getApplyUserId())){
					officeCarApply.setApplyUserName(userMap.get(officeCarApply.getApplyUserId()).getRealname());
				}else{
					officeCarApply.setApplyUserName("用户已删除");
				}
			}
			if(StringUtils.isNotBlank(officeCarApply.getLinkUserId())){
				if(userMap.containsKey(officeCarApply.getLinkUserId())){
					officeCarApply.setLinkUserName(userMap.get(officeCarApply.getLinkUserId()).getRealname());
				}else{
					officeCarApply.setLinkUserName("用户已删除");
				}
			}
			if(StringUtils.isNotBlank(officeCarApply.getAuditUserId())){
				if(StringUtils.isNotBlank(officeCarApply.getAuditUserId())){
					if(userMap.containsKey(officeCarApply.getAuditUserId())){
						officeCarApply.setAuditUserName(userMap.get(officeCarApply.getAuditUserId()).getRealname());
					}else{
						officeCarApply.setAuditUserName("用户已删除");
					}
				}
			}
			if(officeCarApply.getState() == Constants.APPLY_STATE_PASS || officeCarApply.getState() == Constants.APPLY_STATE_CANCEL_NEED_AUDIT || officeCarApply.getState() == Constants.APPLY_STATE_CANCEL_PASS){
				setDetail(officeCarInfoMap, officeCarDriverMap, userMap, officeCarApply);
			}
			if(officeCarApply.getUseTime() != null){
				try {
					officeCarApply.setXinqi(OfficeUtils.dayForWeek(officeCarApply.getUseTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 设置驾驶员和车辆信息
	 */
	public void setDetail(Map<String, OfficeCarInfo> officeCarInfoMap, Map<String, OfficeCarDriver> officeCarDriverMap, Map<String, User> userMap, OfficeCarApply officeCarApply){
		StringBuffer str = new StringBuffer();
		if(StringUtils.isNotBlank(officeCarApply.getCarId())){
			String[] carIds = officeCarApply.getCarId().split(",");
			int i = 0;
			for(String carId:carIds){
				if(i == 0){
					str.append(officeCarInfoMap.get(carId).getCarNumber());
				}else{
					str.append(","+officeCarInfoMap.get(carId).getCarNumber());
				}
				i++;
			}
			officeCarApply.setCarNumber(str.toString());
		}
		if(StringUtils.isNotBlank(officeCarApply.getDriverId())){
			String[] driverIds = officeCarApply.getDriverId().split(",");
			str = new StringBuffer();
			int i = 0;
			for(String driverId:driverIds){
				String driverName = "";
				OfficeCarDriver driver = officeCarDriverMap.get(driverId);
				if(driver != null){
					if(userMap.get(driver.getDriverId()) != null){
						driver.setDriverName(userMap.get(driver.getDriverId()).getRealname());
					}else{
						driver.setDriverName(driver.getName() + "(已删除)");
					}
					driverName = officeCarDriverMap.get(driverId).getDriverName();
				}else{
					driverName = "驾驶员已删除";
				}
				if(i == 0){
					str.append(driverName);
				}else{
					str.append(","+driverName);
				}
				i++;
			}
			officeCarApply.setDriverName(str.toString());
		}
	}
	
	@Override
	public void updateApplyState(OfficeCarApply officeCarApply){//TODO
		if(officeCarApply.getState() == Constants.APPLY_STATE_CANCEL_PASS){
			OfficeCarApply officeCarApplyDetail = officeCarApplyDao.getOfficeCarApplyById(officeCarApply.getId());
			String[] carIds=ArrayUtils.EMPTY_STRING_ARRAY;
			String[] driverIds=ArrayUtils.EMPTY_STRING_ARRAY;
			if(org.apache.commons.lang3.StringUtils.isNotBlank(officeCarApply.getCarId())){
				carIds = officeCarApply.getCarId().split(",");
			}
			if(org.apache.commons.lang3.StringUtils.isNotBlank(officeCarApply.getDriverId())){
				driverIds = officeCarApply.getDriverId().split(",");
			}
			Map<String, OfficeCarInfo> officeCarInfoMap = officeCarInfoService.getOfficeCarInfoMapByUnitId(officeCarApplyDetail.getUnitId());
			Map<String, OfficeCarDriver> officeCarDriverMap = officeCarDriverService.getOfficeCarDriverMapByUnitId(officeCarApplyDetail.getUnitId());
			Map<String, User> userMap = userService.getUserWithDelMap(officeCarApplyDetail.getUnitId());
			Unit unit = unitService.getUnit(officeCarApplyDetail.getUnitId());
			DateFormat df = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分");
			
			//组织消息内容
			StringBuffer sbf = new StringBuffer();
			sbf.append("您好，以下出车任务已通过撤销审核：");
			
			sbf.append(" 用车联系人：").append(userMap.get(officeCarApplyDetail.getLinkUserId()).getRealname()).append(" 电话：").append(officeCarApplyDetail.getMobilePhone());
			sbf.append(" 用车时间：").append(df.format(officeCarApplyDetail.getUseTime())).append(" 地点：").append(officeCarApplyDetail.getCarLocation());
			if(officeCarApplyDetail.getWaitingTime()!=null){
				sbf.append(" 接返时间：").append(df.format(officeCarApplyDetail.getWaitingTime())).append("；");
			}else{
				sbf.append(" 接返时间： 不需接返 ；");
			}
			for(int i=0;i<carIds.length;i++){
				if(ArrayUtils.isNotEmpty(driverIds)&&org.apache.commons.lang3.StringUtils.isNotBlank(driverIds[i])&&officeCarDriverMap.containsKey(driverIds[i])){
					sbf.append("驾驶员").append(i+1).append("：");
					sbf.append(officeCarDriverMap.get(driverIds[i]).getName());
				}
				sbf.append(" 车牌号：").append(officeCarInfoMap.get(carIds[i]).getCarNumber());
				if(ArrayUtils.isNotEmpty(driverIds)&&org.apache.commons.lang3.StringUtils.isNotBlank(driverIds[i])&&officeCarDriverMap.containsKey(driverIds[i])){
					sbf.append(" 联系电话：").append(officeCarDriverMap.get(driverIds[i]).getMobilePhone()).append("；");
				}
			}
			//TODO---------------------组织消息内容------------------------
			OfficeMsgSending officeMsgSending = new OfficeMsgSending();
			officeMsgSending.setCreateUserId(officeCarApply.getAuditUserId());
			officeMsgSending.setTitle("出车任务撤销审核通过信息提醒");
			officeMsgSending.setContent(sbf.toString());
			officeMsgSending.setSimpleContent(sbf.toString());
			officeMsgSending.setUserIds(officeCarApplyDetail.getLinkUserId());
			officeMsgSending.setSendUserName(userMap.get(officeCarApply.getAuditUserId()).getRealname());
			officeMsgSending.setState(Constants.MSG_STATE_SEND);
			officeMsgSending.setIsNeedsms(false);
			officeMsgSending.setMsgType(BaseConstant.MSG_TYPE_CAR);
			officeMsgSendingService.save(officeMsgSending, null, null);
			//组织短信内容
			StringBuffer sbf1 = new StringBuffer();
			sbf1.append("您好，您的出车任务已被取消，任务如下：");
			
			sbf1.append(" 用车联系人：").append(userMap.get(officeCarApplyDetail.getLinkUserId()).getRealname()).append(" 电话：").append(officeCarApplyDetail.getMobilePhone());
			sbf1.append(" 用车时间：").append(df.format(officeCarApplyDetail.getUseTime())).append(" 地点：").append(officeCarApplyDetail.getCarLocation());
			if(officeCarApplyDetail.getWaitingTime()!=null){
				sbf1.append(" 接返时间：").append(df.format(officeCarApplyDetail.getWaitingTime())).append("；");
			}else{
				sbf1.append(" 接返时间： 不需接返 ；");
			}
			//TODO-------------------组织短信内容并发送----------------------
			MsgDto msgDto = new MsgDto();
			msgDto.setUserId(officeCarApply.getAuditUserId());
			msgDto.setUnitName(unit.getName());
			msgDto.setUserName(userMap.get(officeCarApply.getAuditUserId()).getRealname());
			msgDto.setContent(sbf1.toString());
			msgDto.setTiming(false);
			List<SendDetailDto> sendDetailDtos = new ArrayList<SendDetailDto>();
			for(String driverId:driverIds){
				OfficeCarDriver officeCarDriver = officeCarDriverMap.get(driverId);
				SendDetailDto sendDetailDto = new SendDetailDto();
				sendDetailDto.setReceiverId(officeCarDriver.getDriverId());
				sendDetailDto.setReceiverName(officeCarDriver.getName());
				sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
				sendDetailDto.setMobile(officeCarDriver.getMobilePhone());// 短信接收人手机号码
				sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
				sendDetailDto.setUnitId(officeCarApplyDetail.getUnitId());// 短信接收用户单位id
				sendDetailDtos.add(sendDetailDto);
			}
			SmsThread smsThread = new SmsThread(msgDto, sendDetailDtos);
			smsThread.start();
			//-----------------------发短信结束------------------------
		}
		officeCarApplyDao.updateApplyState(officeCarApply);
	}

	public void setOfficeCarApplyDao(OfficeCarApplyDao officeCarApplyDao){
		this.officeCarApplyDao = officeCarApplyDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setOfficeCarInfoService(OfficeCarInfoService officeCarInfoService) {
		this.officeCarInfoService = officeCarInfoService;
	}

	public void setOfficeCarDriverService(
			OfficeCarDriverService officeCarDriverService) {
		this.officeCarDriverService = officeCarDriverService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}
	
	public void setOfficeCarSubsidyService(
			OfficeCarSubsidyService officeCarSubsidyService) {
		this.officeCarSubsidyService = officeCarSubsidyService;
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
}