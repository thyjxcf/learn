package net.zdsoft.office.msgcenter.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import net.zdsoft.office.msgcenter.dao.OfficeMsgReceivingDao;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.dto.ReadInfoDto;
import net.zdsoft.office.msgcenter.entity.OfficeMsgFolderDetail;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
import net.zdsoft.office.msgcenter.entity.OfficeMsgRecycle;
import net.zdsoft.office.msgcenter.service.OfficeMsgFolderDetailService;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.msgcenter.service.OfficeMsgRecycleService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * 信息接受信息表
 * @author 
 * 
 */
public class OfficeMsgReceivingServiceImpl implements OfficeMsgReceivingService{
	
	private DeptService deptService;
	private UnitService unitService;
	private UserService userService;
	private TeacherService teacherService;
	private SmsClientService smsClientService;
	private OfficeMsgRecycleService officeMsgRecycleService;
	private OfficeMsgFolderDetailService officeMsgFolderDetailService;
	private OfficeMsgReceivingDao officeMsgReceivingDao;
	
	@Override
	public OfficeMsgReceiving save(OfficeMsgReceiving officeMsgReceiving){
		return officeMsgReceivingDao.save(officeMsgReceiving);
	}
	
	@Override
	public void batchSave(List<OfficeMsgReceiving> officeMsgReceivings) {
		officeMsgReceivingDao.batchSave(officeMsgReceivings);
		
	}

	@Override
	public Integer delete(String[] ids){
		return officeMsgReceivingDao.delete(ids);
	}
	
	@Override
	public void turnToFolder(String receiveUserId, String[] replyMsgIds, String folderId, boolean isCopy) {
		//获取要根据会话删除的数据
		List<OfficeMsgReceiving> officeMsgReceivings = officeMsgReceivingDao.getOfficeMsgReceivingList(receiveUserId,replyMsgIds);
		List<OfficeMsgFolderDetail> officeMsgFolderDetails = new ArrayList<OfficeMsgFolderDetail>();
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			OfficeMsgFolderDetail officeMsgFolderDetail = new OfficeMsgFolderDetail();
			officeMsgFolderDetail.setCreationTime(new Date());
			officeMsgFolderDetail.setFolderId(folderId);
			officeMsgFolderDetail.setIsEmergency(officeMsgReceiving.getIsEmergency());
			officeMsgFolderDetail.setReferenceId(officeMsgReceiving.getId());
			officeMsgFolderDetail.setSendTime(officeMsgReceiving.getSendTime());
			officeMsgFolderDetail.setTitle(officeMsgReceiving.getTitle());
			officeMsgFolderDetail.setUserId(officeMsgReceiving.getReceiveUserId());
			officeMsgFolderDetail.setMsgType(officeMsgReceiving.getMsgType());
			officeMsgFolderDetail.setReferenceState(officeMsgReceiving.getState());
			officeMsgFolderDetail.setIsDeleted(false);
			if(isCopy){
				officeMsgFolderDetail.setIsCopy(Constants.COPY);
			}else{
				officeMsgFolderDetail.setIsCopy(Constants.MOVE);
			}
			officeMsgFolderDetails.add(officeMsgFolderDetail);
		}
		officeMsgFolderDetailService.batchSave(officeMsgFolderDetails);
		if(!isCopy){
			officeMsgReceivingDao.deleteByReplyMsgIds(receiveUserId, replyMsgIds);
		}
	}
	
	@Override
	public void turnToFolderFromDraft(String[] ids, String folderId) {
		//获取要根据会话删除的数据
		List<OfficeMsgReceiving> officeMsgReceivings = officeMsgReceivingDao.getOfficeMsgReceivingList(ids);
		List<OfficeMsgFolderDetail> officeMsgFolderDetails = new ArrayList<OfficeMsgFolderDetail>();
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			OfficeMsgFolderDetail officeMsgFolderDetail = new OfficeMsgFolderDetail();
			officeMsgFolderDetail.setCreationTime(new Date());
			officeMsgFolderDetail.setFolderId(folderId);
			officeMsgFolderDetail.setIsEmergency(officeMsgReceiving.getIsEmergency());
			officeMsgFolderDetail.setReferenceId(officeMsgReceiving.getId());
			officeMsgFolderDetail.setSendTime(officeMsgReceiving.getSendTime());
			officeMsgFolderDetail.setTitle(officeMsgReceiving.getTitle());
			officeMsgFolderDetail.setUserId(officeMsgReceiving.getReceiveUserId());
			officeMsgFolderDetail.setMsgType(officeMsgReceiving.getMsgType());
			officeMsgFolderDetail.setReferenceState(Constants.MSG_STATE_RECEIVE);
			officeMsgFolderDetail.setIsDeleted(false);
			officeMsgFolderDetails.add(officeMsgFolderDetail);
		}
		officeMsgFolderDetailService.batchSave(officeMsgFolderDetails);
	}
	
	@Override
	public void turnSingleToFolder(String id, String folderId, Integer msgState, boolean isCopy) {
		OfficeMsgReceiving officeMsgReceiving = officeMsgReceivingDao.getOfficeMsgReceivingById(id);
		OfficeMsgFolderDetail officeMsgFolderDetail = new OfficeMsgFolderDetail();
		officeMsgFolderDetail.setCreationTime(new Date());
		officeMsgFolderDetail.setFolderId(folderId);
		officeMsgFolderDetail.setIsEmergency(officeMsgReceiving.getIsEmergency());
		officeMsgFolderDetail.setReferenceId(officeMsgReceiving.getId());
		officeMsgFolderDetail.setSendTime(officeMsgReceiving.getSendTime());
		officeMsgFolderDetail.setTitle(officeMsgReceiving.getTitle());
		officeMsgFolderDetail.setUserId(officeMsgReceiving.getReceiveUserId());
		officeMsgFolderDetail.setMsgType(officeMsgReceiving.getMsgType());
		officeMsgFolderDetail.setReferenceState(msgState);
		officeMsgFolderDetail.setIsDeleted(false);
		if(isCopy){
			officeMsgFolderDetail.setIsCopy(Constants.COPY);
		}else{
			officeMsgFolderDetail.setIsCopy(Constants.MOVE);
		}
		officeMsgFolderDetailService.save(officeMsgFolderDetail);
		if(!isCopy){
			officeMsgReceivingDao.delete(new String[]{id});
		}
	}
	
	@Override
	public void revertById(String id) {
		officeMsgReceivingDao.updateRevertById(id);
	}
	
	@Override
	public void deleteById(String id) {
		//获取要根据会话删除的数据
		OfficeMsgReceiving officeMsgReceiving = officeMsgReceivingDao.getOfficeMsgReceivingById(id);
		OfficeMsgRecycle officeMsgRecycle = new OfficeMsgRecycle();
		officeMsgRecycle.setDeleteTime(new Date());
		officeMsgRecycle.setIsEmergency(officeMsgReceiving.getIsEmergency());
		officeMsgRecycle.setMsgtype(officeMsgReceiving.getMsgType());
		officeMsgRecycle.setReferenceId(officeMsgReceiving.getId());
		officeMsgRecycle.setSendTime(officeMsgReceiving.getSendTime());
		officeMsgRecycle.setState(Constants.MSG_STATE_RECEIVE);
		officeMsgRecycle.setTitle(officeMsgReceiving.getTitle());
		officeMsgRecycle.setUserId(officeMsgReceiving.getReceiveUserId());
		officeMsgRecycleService.save(officeMsgRecycle);
		officeMsgReceivingDao.delete(new String[]{id});
		
	}
	
	@Override
	public void removeByReplyMsgIds(String receiveUserId, String[] replyMsgIds) {
		//获取要根据会话删除的数据
		List<OfficeMsgReceiving> officeMsgReceivings = officeMsgReceivingDao.getOfficeMsgReceivingList(receiveUserId,replyMsgIds);
		List<OfficeMsgRecycle> officeMsgRecycles = new ArrayList<OfficeMsgRecycle>();
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			OfficeMsgRecycle officeMsgRecycle = new OfficeMsgRecycle();
			officeMsgRecycle.setDeleteTime(new Date());
			officeMsgRecycle.setIsEmergency(officeMsgReceiving.getIsEmergency());
			officeMsgRecycle.setMsgtype(officeMsgReceiving.getMsgType());
			officeMsgRecycle.setReferenceId(officeMsgReceiving.getId());
			officeMsgRecycle.setSendTime(officeMsgReceiving.getSendTime());
			officeMsgRecycle.setState(Constants.MSG_STATE_RECEIVE);
			officeMsgRecycle.setTitle(officeMsgReceiving.getTitle());
			officeMsgRecycle.setUserId(receiveUserId);
			officeMsgRecycles.add(officeMsgRecycle);
		}
		officeMsgRecycleService.batchSave(officeMsgRecycles);
		officeMsgReceivingDao.deleteByReplyMsgIds(receiveUserId, replyMsgIds);
	}

	@Override
	public Integer update(OfficeMsgReceiving officeMsgReceiving){
		return officeMsgReceivingDao.update(officeMsgReceiving);
	}
	
	@Override
	public void readAll(String receiveUserId) {
		officeMsgReceivingDao.updateReadAll(receiveUserId);
	}
	
	@Override
	public void updateRead(String id) {
		officeMsgReceivingDao.updateRead(id);
	}
	
	@Override
	public void updateIsWithdraw(String messageId) {
		officeMsgReceivingDao.updateIsWithdraw(messageId);
	}
	
	public Integer getNumber(String userId, String readType) {
		return officeMsgReceivingDao.getNumber(userId, readType);
	};

	@Override
	public OfficeMsgReceiving getOfficeMsgReceivingById(String id){
		return officeMsgReceivingDao.getOfficeMsgReceivingById(id);
	}
	
	@Override
	public List<ReadInfoDto> getIsReadInfo(String messageId, Integer isRead) {
		List<ReadInfoDto> readInfoDtos = new ArrayList<ReadInfoDto>();
		List<String> receiveIds = officeMsgReceivingDao.getIsReadInfo(messageId, isRead);
		List<User> users = userService.getUsersWithDel(receiveIds.toArray(new String[0]));
		List<Dept> depts = deptService.getDeptList(receiveIds.toArray(new String[0]));
		List<Unit> units = unitService.getUnits(receiveIds.toArray(new String[0]));
		Set<String> teacherIds = new HashSet<String>();
		Set<String> unitIds = new HashSet<String>();
		Set<String> deptIds = new HashSet<String>();
		for(User user:users){
			teacherIds.add(user.getTeacherid());
			unitIds.add(user.getUnitid());
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherWithDeletedMap(teacherIds.toArray(new String[0]));
		for(Teacher teacher:teacherMap.values()){
			deptIds.add(teacher.getDeptid());
		}
		for(Dept dept:depts){
			deptIds.add(dept.getId());
			unitIds.add(dept.getUnitId());
		}
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		for(Unit unit:units){
			unitIds.add(unit.getId());
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));
		
		for(Unit unit:unitMap.values()){
			ReadInfoDto readInfoDto = new ReadInfoDto();
			List<String> userDeptNames = new ArrayList<String>();
			//人员
			for(User user:users){
				if(unit.getId().equals(user.getUnitid())){
					Teacher teacher = teacherMap.get(user.getTeacherid());
					if(teacher!=null){
						userDeptNames.add(deptMap.get(teacher.getDeptid()).getDeptname()+"<span>"+teacher.getName()+"</span>");
					}
				}
			}
			//部门
			for(Dept dept:depts){
				if(unit.getId().equals(dept.getUnitId())){
					userDeptNames.add("<span>"+dept.getDeptname()+"</span>");
				}
			}
			//单位
			for(Unit unit1:units){
				if(unit.getId().equals(unit1.getId())){
					userDeptNames.add("<span>"+unit1.getName()+"</span>");
				}
			}
			readInfoDto.setUnitName(unit.getName());
			readInfoDto.setUserDeptNameList(userDeptNames);
			readInfoDtos.add(readInfoDto);
		}
		return readInfoDtos;
	}
	
	@Override
	public void remindSms(String messageId, MsgDto msgDto) {
		List<String> receiveUserIds = officeMsgReceivingDao.getIsReadInfo(messageId, Constants.UNREAD);
		List<User> users = userService.getUsersWithDel(receiveUserIds.toArray(new String[0]));
		Set<String> teacherIds = new HashSet<String>();
		for(User user:users){
			teacherIds.add(user.getTeacherid());
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherWithDeletedMap(teacherIds.toArray(new String[0]));
		
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
			}
		}
		
		if(CollectionUtils.isNotEmpty(sendDetailDtos)){
			SmsThread smsThread = new SmsThread(msgDto, sendDetailDtos);
			smsThread.start();
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

	@Override
	public Map<String, OfficeMsgReceiving> getOfficeMsgReceivingMapByIds(String[] ids){
		return officeMsgReceivingDao.getOfficeMsgReceivingMapByIds(ids);
	}

	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingList(MessageSearch messageSearch, Pagination page){
//		System.out.println("----列表查询开始----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		List<OfficeMsgReceiving> officeMsgReceivings = officeMsgReceivingDao.getOfficeMsgReceivingList(messageSearch,page);
//		System.out.println("----列表查询结束----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		if(CollectionUtils.isEmpty(officeMsgReceivings)){
			return officeMsgReceivings;
		}
		String[] replyMsgIds = new String[officeMsgReceivings.size()];
		int i = 0;
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			replyMsgIds[i] = officeMsgReceiving.getReplyMsgId();
			i++;
		}
//		System.out.println("----加星map开始----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
//		获取整个会话是否有加星的，如果有一个加星，那么外面显示加星
		Map<String, String> starMap = officeMsgReceivingDao.getStarMapByreplyMsgIds(messageSearch.getReceiveUserId(),replyMsgIds);
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			if(StringUtils.isNotBlank(starMap.get(officeMsgReceiving.getReplyMsgId()))){
				officeMsgReceiving.setHasStar(Constants.HASSTAR);
			}
		}
//		System.out.println("----加星map结束，待办map开始----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
//		获取整个会话是否有待办的，如果有一个待办，那么外面显示待办
		Map<String, String> needTodoMap = officeMsgReceivingDao.getNeedTodoMapByreplyMsgIds(messageSearch.getReceiveUserId(),replyMsgIds);
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			if(StringUtils.isNotBlank(needTodoMap.get(officeMsgReceiving.getReplyMsgId()))){
				officeMsgReceiving.setNeedTodo(Constants.NEEDTODO);
			}
		}
//		System.out.println("----待办map结束，已读未读map开始----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
//		获取整个会话是否有未读的，如果有一个未读，那么外面显示未读
		Map<String, String> receivingUnReadMap = officeMsgReceivingDao.getReceivingUnReadMapByreplyMsgIds(messageSearch.getReceiveUserId(),replyMsgIds);
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			if(StringUtils.isNotBlank(receivingUnReadMap.get(officeMsgReceiving.getReplyMsgId()))){
				officeMsgReceiving.setIsRead(Constants.UNREAD);
			}
		}
//		System.out.println("----已读未读map结束，会话条数开始----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		//设置会话条数
		Map<String, Integer> countMap = officeMsgReceivingDao.getOfficeMsgReceivingCountMap(replyMsgIds,messageSearch.getReceiveUserId());
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			officeMsgReceiving.setCountNum(countMap.get(officeMsgReceiving.getReplyMsgId()));
		}
//		System.out.println("----会话条数结束----->"+DateUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return officeMsgReceivings;
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingImportList(
			MessageSearch messageSearch, Pagination page) {
		List<OfficeMsgReceiving> officeMsgReceivings = officeMsgReceivingDao.getOfficeMsgReceivingImportList(messageSearch,page);
		String[] replyMsgIds = new String[officeMsgReceivings.size()];
		int i = 0;
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			replyMsgIds[i] = officeMsgReceiving.getReplyMsgId();
			i++;
		}
//		获取整个会话是否有待办的，如果有一个待办，那么外面显示待办
		Map<String, String> needTodoMap = officeMsgReceivingDao.getNeedTodoMapByreplyMsgIds(messageSearch.getReceiveUserId(),replyMsgIds);
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			if(StringUtils.isNotBlank(needTodoMap.get(officeMsgReceiving.getReplyMsgId()))){
				officeMsgReceiving.setNeedTodo(Constants.NEEDTODO);
			}
		}
//		获取整个会话是否有未读的，如果有一个未读，那么外面显示未读
		Map<String, String> receivingUnReadMap = officeMsgReceivingDao.getReceivingUnReadMapByreplyMsgIds(messageSearch.getReceiveUserId(),replyMsgIds);
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			if(StringUtils.isNotBlank(receivingUnReadMap.get(officeMsgReceiving.getReplyMsgId()))){
				officeMsgReceiving.setIsRead(Constants.UNREAD);
			}
		}
		//设置会话条数
		Map<String, Integer> countMap = officeMsgReceivingDao.getOfficeMsgReceivingCountMap(replyMsgIds,messageSearch.getReceiveUserId());
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			officeMsgReceiving.setCountNum(countMap.get(officeMsgReceiving.getReplyMsgId()));
		}
		return officeMsgReceivings;
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingTodoList(
			MessageSearch messageSearch, Pagination page) {
		List<OfficeMsgReceiving> officeMsgReceivings = officeMsgReceivingDao.getOfficeMsgReceivingTodoList(messageSearch,page);
		String[] replyMsgIds = new String[officeMsgReceivings.size()];
		int i = 0;
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			replyMsgIds[i] = officeMsgReceiving.getReplyMsgId();
			i++;
		}
//		获取整个会话是否有加星的，如果有一个加星，那么外面显示加星
		Map<String, String> starMap = officeMsgReceivingDao.getStarMapByreplyMsgIds(messageSearch.getReceiveUserId(),replyMsgIds);
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			if(StringUtils.isNotBlank(starMap.get(officeMsgReceiving.getReplyMsgId()))){
				officeMsgReceiving.setHasStar(Constants.HASSTAR);
			}
		}
//		获取整个会话是否有未读的，如果有一个未读，那么外面显示未读
		Map<String, String> receivingUnReadMap = officeMsgReceivingDao.getReceivingUnReadMapByreplyMsgIds(messageSearch.getReceiveUserId(),replyMsgIds);
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			if(StringUtils.isNotBlank(receivingUnReadMap.get(officeMsgReceiving.getReplyMsgId()))){
				officeMsgReceiving.setIsRead(Constants.UNREAD);
			}
		}
		//设置会话条数
		Map<String, Integer> countMap = officeMsgReceivingDao.getOfficeMsgReceivingCountMap(replyMsgIds,messageSearch.getReceiveUserId());
		for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
			officeMsgReceiving.setCountNum(countMap.get(officeMsgReceiving.getReplyMsgId()));
		}
		return officeMsgReceivings;
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByMessageId(
			String messageId) {
		return officeMsgReceivingDao.getOfficeMsgReceivingListByMessageId(messageId);
	}
	
	@Override
	public List<OfficeMsgReceiving> getOfficeMsgReceivingListByReplyMsgId(
			String replyMsgId, String userId) {
		//获取为收件人的数据
		List<OfficeMsgReceiving> officeMsgReceivings = officeMsgReceivingDao.getOfficeMsgReceivingListByReplyMsgId(replyMsgId, userId);
		//获取为发件人的数据
		List<OfficeMsgReceiving> officeMsgReceivingSend = officeMsgReceivingDao.getOfficeMsgReceivingListSend(replyMsgId, userId);
		//需要综合的数据
		List<OfficeMsgReceiving> officeMsgReceivingList = officeMsgReceivings;
		
		if(CollectionUtils.isNotEmpty(officeMsgReceivingSend)){
			flag:for(OfficeMsgReceiving officeMsgReceivingNew:officeMsgReceivingSend){
				for(OfficeMsgReceiving officeMsgReceiving:officeMsgReceivings){
					if(officeMsgReceivingNew.getMessageId().equals(officeMsgReceiving.getMessageId())){
						continue flag;
					}
				}
				officeMsgReceivingNew.setIsSendInfo(true);
				officeMsgReceivingList.add(officeMsgReceivingNew);
			}
		}
		Collections.sort(officeMsgReceivingList, new Comparator<OfficeMsgReceiving>() {
			public int compare(OfficeMsgReceiving o1, OfficeMsgReceiving o2) {
				Date date1 = o1.getSendTime();
				Date date2 = o2.getSendTime();
				return date2.compareTo(date1);
			}
		});
		return officeMsgReceivingList;
	}
	
	@Override
	public void changeAllStar(String receiveUserId, String replyMsgId,
			Integer hasStar) {
		officeMsgReceivingDao.updateAllStar(receiveUserId, replyMsgId,
				hasStar);
	}
	
	@Override
	public void changeAllNeedTodo(String receiveUserId, String replyMsgId,
			Integer needTodo) {
		officeMsgReceivingDao.updateAllNeedTodo(receiveUserId, replyMsgId,
				needTodo);
	}
	
	@Override
	public void changeNeedTodo(String id, Integer needTodo) {
		officeMsgReceivingDao.updateNeedTodo(id, needTodo);
		
	}
	
	@Override
	public void changeStar(String id, Integer hasStar) {
		officeMsgReceivingDao.updateStar(id, hasStar);
	}

	@Override
	public Map<String, String> findMessageReadStatus(String[] msgSendIds) {
		return officeMsgReceivingDao.findMessageReadStatus(msgSendIds);
	}
	
	@Override
	public List<User> findReceiveUsers(String sendUserId, int size) {
		List<String> userIds = officeMsgReceivingDao.findReceiveUserIds(sendUserId, size);
		List<User> users = new ArrayList<User>();
		if(userIds.size() > 0){
			users = userService.getUsersWithDel(userIds.toArray(new String[0]));
			setUserOtherInfos(users);
		}
		return users;
	}
	
	public void setUserOtherInfos(List<User> users){
		Set<String> teacherIds = new HashSet<String>();
		Set<String> unitIds = new HashSet<String>();
		Set<String> deptIds = new HashSet<String>();
		for(User user:users){
			teacherIds.add(user.getTeacherid());
			unitIds.add(user.getUnitid());
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIds.toArray(new String[0]));
		for(Teacher teacher:teacherMap.values()){
			deptIds.add(teacher.getDeptid());
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(deptIds.toArray(new String[0]));
		for(User user:users){
			Unit unit = unitMap.get(user.getUnitid());
			if(unit!=null){
				user.setUnitName(unit.getName());
			}
			Teacher teacher = teacherMap.get(user.getTeacherid());
			if(teacher!=null){
				user.setMobilePhone(teacher.getPersonTel());
				user.setOfficeTel(teacher.getOfficeTel());
				Dept dept = deptMap.get(teacher.getDeptid());
				if(dept!=null){
					user.setDeptName(dept.getDeptname());
				}
			}
		}
	}
	
	@Override
	public String[] getChangedReceivingUserIds(String time) {
		return officeMsgReceivingDao.getChangedReceivingUserIds(time);
	}
	
	@Override
	public Map<String, Integer> getMsgReceivingMap(String[] userIds) {
		return officeMsgReceivingDao.getMsgReceivingMap(userIds);
	}
	
	@Override
	public List<OfficeMsgReceiving> getMsgUnPushed(Pagination page) {
		return officeMsgReceivingDao.getMsgUnPushed(page);
	}
	
	@Override
	public void updatePushed(String[] ids) {
		officeMsgReceivingDao.updatePushed(ids);
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

	public void setOfficeMsgRecycleService(
			OfficeMsgRecycleService officeMsgRecycleService) {
		this.officeMsgRecycleService = officeMsgRecycleService;
	}

	public void setOfficeMsgFolderDetailService(
			OfficeMsgFolderDetailService officeMsgFolderDetailService) {
		this.officeMsgFolderDetailService = officeMsgFolderDetailService;
	}

	public void setOfficeMsgReceivingDao(OfficeMsgReceivingDao officeMsgReceivingDao){
		this.officeMsgReceivingDao = officeMsgReceivingDao;
	}
}