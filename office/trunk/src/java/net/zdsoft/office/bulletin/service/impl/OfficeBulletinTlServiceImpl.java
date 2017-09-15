package net.zdsoft.office.bulletin.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.eis.sms.dto.SendDetailDto;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.dao.OfficeBulletinReadDao;
import net.zdsoft.office.bulletin.dao.OfficeBulletinTlDao;
import net.zdsoft.office.bulletin.dao.OfficeBulletinTlUnitDao;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;
import net.zdsoft.office.bulletin.entity.OfficeBulletinTl;
import net.zdsoft.office.bulletin.entity.OfficeBulletinTlUnit;
import net.zdsoft.office.bulletin.service.OfficeBulletinTlService;
import net.zdsoft.office.bulletin.service.OfficeBulletinTlUnitService;
import net.zdsoft.office.util.EntityUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;

/**
 * office_bulletin_tl
 * 
 * @author
 * 
 */
public class OfficeBulletinTlServiceImpl implements OfficeBulletinTlService {

	private OfficeBulletinTlUnitService officeBulletinTlUnitService;
	private UserService userService;
	private DeptService deptService;
	private UnitService unitService;
	private TeacherService teacherService;
	private SmsClientService smsClientService;

	private OfficeBulletinTlDao officeBulletinTlDao;
	private OfficeBulletinReadDao officeBulletinReadDao;
	private OfficeBulletinTlUnitDao officeBulletinTlUnitDao;

	@Override
	public OfficeBulletinTl save(OfficeBulletinTl officeBulletinTl) {
		officeBulletinTl.setId(EntityUtils.generateUUID());
		String[] unitIds = officeBulletinTl.getUnitIds().split(",");
		List<OfficeBulletinTlUnit> list = new ArrayList<OfficeBulletinTlUnit>();
		for (String unitId : unitIds) {
			OfficeBulletinTlUnit tlUnit = new OfficeBulletinTlUnit();
			tlUnit.setBulletinId(officeBulletinTl.getId());
			tlUnit.setReceiveUnitId(unitId);
			list.add(tlUnit);
		}
		officeBulletinTlUnitService.batchSave(list);
		return officeBulletinTlDao.save(officeBulletinTl);
	}

	@Override
	public void delete(String[] ids, String userId) {
		officeBulletinTlDao.delete(ids, userId);
	}

	@Override
	public void publish(String[] ids, String userId) {
		officeBulletinTlDao.publish(ids,userId);
	}

	@Override
	public void qxPublish(String id, String userId) {
		officeBulletinTlDao.qxPublish(id, userId);

	}

	@Override
	public void top(String id, Integer topState) {
		officeBulletinTlDao.top(id, topState);
	}

	@Override
	public void saveOrder(String[] bulletinIds, String[] orderIds, String userId) {
		officeBulletinTlDao.saveOrder(bulletinIds,orderIds,userId);
	}

	@Override
	public void update(OfficeBulletinTl officeBulletinTl) {
		String[] unitIds = officeBulletinTl.getUnitIds().split(",");
		List<OfficeBulletinTlUnit> list = new ArrayList<OfficeBulletinTlUnit>();
		for (String unitId : unitIds) {
			OfficeBulletinTlUnit tlUnit = new OfficeBulletinTlUnit();
			tlUnit.setBulletinId(officeBulletinTl.getId());
			tlUnit.setReceiveUnitId(unitId);
			list.add(tlUnit);
		}
		officeBulletinTlUnitService
				.deleteByBulletinId(officeBulletinTl.getId());
		officeBulletinTlUnitService.batchSave(list);
		officeBulletinTlDao.update(officeBulletinTl);
	}

	@Override
	public OfficeBulletinTl getOfficeBulletinTlById(String id) {
		OfficeBulletinTl officeBulletinTl = officeBulletinTlDao.getOfficeBulletinTlById(id);
		List<OfficeBulletinTlUnit> list = officeBulletinTlUnitService.getOfficeBulletinTlUnitList(id);
		String[] unitIds = new String[list.size()];
		int i = 0;
		for(OfficeBulletinTlUnit tlUnit:list){
			unitIds[i] = tlUnit.getReceiveUnitId();
			i++;
		}
		String unitNames = unitService.getUnitDetailNamesStr(unitIds);
		officeBulletinTl.setUnitIds(StringUtils.join(unitIds, ","));
		officeBulletinTl.setUnitNames(unitNames);
		User user = userService.getUserWithDel(officeBulletinTl.getCreateUserId());
		if(user != null){
			officeBulletinTl.setCreateUserName(user.getRealname());
			if(!user.getIsdeleted()){
				Teacher teacher = teacherService.getTeacherContainDelete(user.getTeacherid());
				if(teacher != null){
					Dept dept = deptService.getDept(teacher.getDeptid());
					if(dept != null){
						officeBulletinTl.setDeptName(dept.getDeptname());
					}
				}
			}else{
				officeBulletinTl.setDeptName("部门已删除");
			}
		}else{
			officeBulletinTl.setCreateUserName("用户已删除");
		}
		Map<String, String> clickMap = officeBulletinReadDao.getClickMapByBulletinIds(new String[]{id});
		if(clickMap!=null && StringUtils.isNotBlank(clickMap.get(id))){
			officeBulletinTl.setClickNum(clickMap.get(id));
		}else{
			officeBulletinTl.setClickNum("0");
		}
		return officeBulletinTl;
	}

	@Override
	public List<OfficeBulletinTl> getOfficeBulletinTlListPage(String unitId,
			String isReadUserId, String startTime, String endTime, String searchName,
			String publishName, Pagination page) {
		Set<String> userIdsSet1 = new HashSet<String>();
		List<OfficeBulletinTl> list;
		if(StringUtils.isNotBlank(publishName)){
			List<User> usersFaintness = userService.getUsersFaintness(publishName, unitId);
			for (User user : usersFaintness) {
				userIdsSet1.add(user.getId());
			}
			list = officeBulletinTlDao.getOfficeBulletinTlListViewPage(unitId,startTime,endTime, searchName, userIdsSet1.toArray(new String[0]), page);
		}else{
			list = officeBulletinTlDao.getOfficeBulletinTlListViewPage(unitId,startTime,endTime, searchName, null, page);
		}
		String[] ids = new String[list.size()];
		int i=0;
		Set<String> userIdsSet = new HashSet<String>();
		Set<String> unitIdsSet = new HashSet<String>();
		for(OfficeBulletinTl officeBulletinTl:list){
			userIdsSet.add(officeBulletinTl.getCreateUserId());
			unitIdsSet.add(officeBulletinTl.getUnitId());
			ids[i] = officeBulletinTl.getId();
			i++;
		}
		Map<String, String> readMap = officeBulletinReadDao.getReadMapByBulletinIds(isReadUserId,ids);
		Map<String, String> clickMap = officeBulletinReadDao.getClickMapByBulletinIds(ids);
		Map<String, User> usersMap = userService.getUserWithDelMap(userIdsSet.toArray(new String[0]));
		String[] teacherIds = new String[usersMap.size()];
		int j = 0;
		for(User user:usersMap.values()){
			teacherIds[j] = user.getTeacherid();
			j++;
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds);
		String[] deptIds  = new String[teacherMap.size()];
		int k = 0;
		for(Teacher teacher:teacherMap.values()){
			deptIds[k] = teacher.getDeptid();
			k++;
		}
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds);
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIdsSet.toArray(new String[0]));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = dateFormat.format(date);
		for(OfficeBulletinTl officeBulletinTl:list){
			User user = usersMap.get(officeBulletinTl.getCreateUserId());
			if(user!=null){
				if(user.getIsdeleted()){
					officeBulletinTl.setCreateUserName(user.getRealname());
				}else{
					officeBulletinTl.setCreateUserName(user.getRealname());
					Teacher teacher = teacherMap.get(user.getTeacherid());
					if(teacher!=null){
						Dept dept = deptMap.get(teacher.getDeptid());
						if(dept!=null){
							officeBulletinTl.setDeptName(dept.getDeptname());
						}
					}
				}
				officeBulletinTl.setUnitName(unitMap.get(officeBulletinTl.getUnitId()).getName());
			}else{
				officeBulletinTl.setCreateUserName("用户已删除");
			}
			if(str.equals(dateFormat.format(officeBulletinTl.getCreateTime()))){
				officeBulletinTl.setIsNew(true);
			}
			if(StringUtils.isNotBlank(readMap.get(officeBulletinTl.getId()))){
				officeBulletinTl.setIsRead(true);
			}
			//点击量
			if(StringUtils.isNotBlank(clickMap.get(officeBulletinTl.getId()))){
				officeBulletinTl.setClickNum(clickMap.get(officeBulletinTl.getId()));
			}else{
				officeBulletinTl.setClickNum("0");
			}
		}
		return list;
	}

	@Override
	public List<OfficeBulletinTl> getOfficeBulletinManageListPage(
			String unitId, String state, String startTime, String endTime,
			String searchName, String publishName, Pagination page) {
		Set<String> userIdsSet = new HashSet<String>();
		List<OfficeBulletinTl> list;
		if(StringUtils.isNotBlank(publishName)){
			List<User> usersFaintness = userService.getUsersFaintness(publishName, unitId);
			for (User user : usersFaintness) {
				userIdsSet.add(user.getId());
			}
			list = officeBulletinTlDao
					.getOfficeBulletinManageListPage(unitId, state, startTime,
							endTime, searchName, userIdsSet.toArray(new String[0]), page);
		}else{
			list = officeBulletinTlDao
					.getOfficeBulletinManageListPage(unitId, state, startTime,
							endTime, searchName, null, page);
		}
		String[] ids = new String[list.size()];
		int i=0;
		Set<String> userIdsSet1 = new HashSet<String>();
		for(OfficeBulletinTl officeBulletinTl:list){
			userIdsSet1.add(officeBulletinTl.getCreateUserId());
			ids[i] = officeBulletinTl.getId();
			i++;
		}
		Map<String, String> clickMap = officeBulletinReadDao.getClickMapByBulletinIds(ids);
		Map<String, User> usersMap = userService.getUserWithDelMap(userIdsSet1.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(unitId);
		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(unitId);
		for(OfficeBulletinTl officeBulletinTl:list){
			User user = usersMap.get(officeBulletinTl.getCreateUserId());
			if(user!=null){
				if(user.getIsdeleted()){
					officeBulletinTl.setCreateUserName(user.getRealname());
				}else{
					officeBulletinTl.setCreateUserName(user.getRealname());
					Teacher teacher = teacherMap.get(user.getTeacherid());
					if(teacher!=null){
						Dept dept = deptMap.get(teacher.getDeptid());
						if(dept!=null){
							officeBulletinTl.setDeptName(dept.getDeptname());
						}
					}
				}
			}else{
				officeBulletinTl.setCreateUserName("用户已删除");
			}
			//点击量
			if(StringUtils.isNotBlank(clickMap.get(officeBulletinTl.getId()))){
				officeBulletinTl.setClickNum(clickMap.get(officeBulletinTl.getId()));
			}else{
				officeBulletinTl.setClickNum("0");
			}
		}
		return list;
	}

	@Override
	public void sendMsg(OfficeBulletinTl officeBulletinTl, MsgDto msgDto){
		if(msgDto != null && StringUtils.isNotBlank(officeBulletinTl.getUnitIds())){
			List<User> users = userService.getUsersByUnitIds(officeBulletinTl.getUnitIds().split(","));
			Set<String> teacherIds = new HashSet<String>();
			for(User user:users){
				teacherIds.add(user.getTeacherid());
			}
			Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds.toArray(new String[0]));
			
			Map<String, Boolean> smsMap = new HashMap<String, Boolean>();//是否维护手机号码
			List<SendDetailDto> sendDetailDtos = new ArrayList<SendDetailDto>();//需要发送短信的对象list
			for(User user:users){
				Teacher teacher = teacherMap.get(user.getTeacherid());
				if(teacher!=null && StringUtils.isNotBlank(teacher.getPersonTel())){
					SendDetailDto sendDetailDto = new SendDetailDto();
					sendDetailDto.setReceiverId(user.getId());// 短信接收人userId,这里实现的是自由短信,无对应用户id
					sendDetailDto.setReceiverName(user.getRealname());// 短信接收人用户名,这里实现的是自由短信,用手机号码替代用户名
					sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
					sendDetailDto.setMobile(teacher.getPersonTel());// 短信接收人手机号码
					sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
					sendDetailDto.setUnitId(user.getUnitid());// 短信接收用户单位id
					sendDetailDtos.add(sendDetailDto);
					smsMap.put(user.getId(), true);
				}else{
					smsMap.put(user.getId(), false);
				}
			}
			if(CollectionUtils.isNotEmpty(sendDetailDtos)){
				SmsThread smsThread = new SmsThread(msgDto, sendDetailDtos);
				smsThread.start();
			}
		}
	}
	
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
	
	public void remind(String[] ids, String userId){//TODO
		//获取要提醒的通知
		List<OfficeBulletinTl> bulletinTlList = officeBulletinTlDao.getBulletinTlByIds(ids);
		//获取接收单位
		Map<String, List<OfficeBulletinTlUnit>> bulletintlUnitMap = officeBulletinTlUnitDao.getOfficeBulletinTlUnitMap(ids);
		//获取已读用户
		Map<String, List<OfficeBulletinRead>> bulletinReadMap = officeBulletinReadDao.getOfficeBulletinReadMap(ids);
		
		Set<String> unitIds = new HashSet<String>();
		for(Map.Entry<String, List<OfficeBulletinTlUnit>> entry : bulletintlUnitMap.entrySet()) {
			List<OfficeBulletinTlUnit> unitlist = entry.getValue();
			for(OfficeBulletinTlUnit bulletintlUnit : unitlist){
				unitIds.add(bulletintlUnit.getReceiveUnitId());
			}
		}
		Map<String, List<User>> userMap = userService.getUserMapByUnitIds(unitIds.toArray(new String[0]));
		
		Set<String> unReadUserIds = new HashSet<String>();
		Set<String> unReadTeacherIds = new HashSet<String>();
		for(OfficeBulletinTl bulletintl : bulletinTlList){
			if("3".equals(bulletintl.getState())){//发布的通知
				String bulletinId = bulletintl.getId();
				
				List<OfficeBulletinTlUnit> receiveUnitList = bulletintlUnitMap.get(bulletinId);
				List<OfficeBulletinRead> readUserList = bulletinReadMap.get(bulletinId);
				
				List<User> receiveUsers = new ArrayList<User>();
				for(OfficeBulletinTlUnit receiveUnit : receiveUnitList){
					List<User> userlist = userMap.get(receiveUnit.getReceiveUnitId());
					if(userlist != null && userlist.size() > 0){
						receiveUsers.addAll(userlist);
					}
				}
				boolean flag;
				for(User user : receiveUsers){
					flag = false;
					if(readUserList != null && readUserList.size() > 0){
						for(OfficeBulletinRead readUser : readUserList){
							if(user.getId().equals(readUser.getUserId())){
								flag = true;
								break;
							}
						}
					}
					if(!flag){
						unReadUserIds.add(user.getId());
						unReadTeacherIds.add(user.getTeacherid());
					}
				}
			}
		}
		Map<String, User> unReadUserMap = userService.getUsersMap(unReadUserIds.toArray(new String[0]));
		Map<String, Teacher> unReadTeacherMap = teacherService.getTeacherMap(unReadTeacherIds.toArray(new String[0]));
		
		User sendUser = userService.getUser(userId);
		Unit sendUnit = unitService.getUnit(sendUser.getUnitid());
		MsgDto msgDto = new MsgDto();
		msgDto.setUserId(userId);
		msgDto.setUnitName(sendUnit.getName());
		msgDto.setUserName(sendUser.getRealname());
		msgDto.setContent("您有通知未读，请尽快查看！【"+sendUser.getRealname()+"-"+ sendUnit.getName() +"】【 OA短信提醒】");
		msgDto.setTiming(false);
		
		Map<String, Boolean> smsMap = new HashMap<String, Boolean>();//是否维护手机号码
		List<SendDetailDto> sendDetailDtos = new ArrayList<SendDetailDto>();
		for(Map.Entry<String, User> entry : unReadUserMap.entrySet()){
			User unReadUser = entry.getValue();
			Teacher unReadTeacher = unReadTeacherMap.get(unReadUser.getTeacherid());
			if(unReadTeacher != null && StringUtils.isNotBlank(unReadTeacher.getPersonTel())){
				SendDetailDto sendDetailDto = new SendDetailDto();
				sendDetailDto.setReceiverId(unReadUser.getId());// 短信接收人userId,这里实现的是自由短信,无对应用户id
				sendDetailDto.setReceiverName(unReadUser.getRealname());// 短信接收人用户名,这里实现的是自由短信,用手机号码替代用户名
				sendDetailDto.setBusinessType(SmsConstant.SMS_BUSINESS_PAY);// Sdk收费短信
				sendDetailDto.setMobile(unReadTeacher.getPersonTel());// 短信接收人手机号码
				sendDetailDto.setReceiverType(User.TEACHER_LOGIN);// 短信接收用户类型:教师用户,学生用户,家长用户
				sendDetailDto.setUnitId(unReadUser.getUnitid());// 短信接收用户单位id
				sendDetailDtos.add(sendDetailDto);
				smsMap.put(unReadUser.getId(), true);
			}else{
				smsMap.put(unReadUser.getId(), false);
			}
		}
		if(CollectionUtils.isNotEmpty(sendDetailDtos)){
			SmsThread smsThread = new SmsThread(msgDto, sendDetailDtos);
			smsThread.start();
		}
	}
	
	public void setOfficeBulletinTlDao(OfficeBulletinTlDao officeBulletinTlDao) {
		this.officeBulletinTlDao = officeBulletinTlDao;
	}

	public void setOfficeBulletinTlUnitService(
			OfficeBulletinTlUnitService officeBulletinTlUnitService) {
		this.officeBulletinTlUnitService = officeBulletinTlUnitService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}

	public void setOfficeBulletinReadDao(OfficeBulletinReadDao officeBulletinReadDao) {
		this.officeBulletinReadDao = officeBulletinReadDao;
	}

	public void setOfficeBulletinTlUnitDao(
			OfficeBulletinTlUnitDao officeBulletinTlUnitDao) {
		this.officeBulletinTlUnitDao = officeBulletinTlUnitDao;
	}

}