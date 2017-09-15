package net.zdsoft.office.dailyoffice.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.dataimport.exception.DataValidException;
import net.zdsoft.office.dailyoffice.dao.OfficeWorkReportDao;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_work_report
 * @author 
 * 
 */
public class OfficeWorkReportServiceImpl implements OfficeWorkReportService{
	private OfficeWorkReportDao officeWorkReportDao;
	private UserService userService;
	private UnitService unitService;
	private UserSetService userSetService;
	private AttachmentService attachmentService;
	private OfficeSubsystemService officeSubsystemService;

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setOfficeSubsystemService(
			OfficeSubsystemService officeSubsystemService) {
		this.officeSubsystemService = officeSubsystemService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}
	
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	@Override
	public OfficeWorkReport save(OfficeWorkReport officeWorkReport){
		return officeWorkReportDao.save(officeWorkReport);
	}
	public void save(OfficeWorkReport officeWorkReport,UploadFile file){
		save(officeWorkReport);
		if(file!=null){
			Attachment attachment = new Attachment();
			attachment.setFileName(file.getFileName());
			attachment.setContentType(file.getContentType());
			attachment.setFileSize(file.getFileSize());
			attachment.setUnitId(officeWorkReport.getUnitId());
			attachment.setObjectId(officeWorkReport.getId());
			attachment.setObjectType(Constants.OFFICE_WORK_REPORT_AIT);
			attachmentService.saveAttachment(attachment, file);
		}
		//推送消息
		try{
			User user = userService.getUser(officeWorkReport.getCreateUserId());
			Unit unit = unitService.getUnit(officeWorkReport.getUnitId());
			String[] userIds = {};
			if(StringUtils.isNotBlank(officeWorkReport.getReceiveUserId())){
				userIds = officeWorkReport.getReceiveUserId().split(",");
			}
			String content = "您好，您收到新的工作汇报。";
			if(officeSubsystemService != null){
				officeSubsystemService.sendMsgDetail(user, unit, "工作汇报提醒",content, content,
						false, BaseConstant.MSG_TYPE_WORK_REPORT, userIds,null);
			}else{
				throw new DataValidException("推送信息失败！原因：信息未找到！");
			}
		}catch(Exception e){
			System.out.println("工作汇报：发送消息失败=============");
			e.printStackTrace();
		}
	}

	@Override
	public Integer delete(String[] ids){
		return officeWorkReportDao.delete(ids);
	}

	@Override
	public Integer update(OfficeWorkReport officeWorkReport){
		return officeWorkReportDao.update(officeWorkReport);
	}

	@Override
	public OfficeWorkReport getOfficeWorkReportById(String id){
		return officeWorkReportDao.getOfficeWorkReportById(id);
	}

	@Override
	public Map<String, OfficeWorkReport> getOfficeWorkReportMapByIds(String[] ids){
		return officeWorkReportDao.getOfficeWorkReportMapByIds(ids);
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportList(){
		return officeWorkReportDao.getOfficeWorkReportList();
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportPage(Pagination page){
		return officeWorkReportDao.getOfficeWorkReportPage(page);
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdList(String unitId){
		return officeWorkReportDao.getOfficeWorkReportByUnitIdList(unitId);
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdPage(String unitId, Pagination page){
		return officeWorkReportDao.getOfficeWorkReportByUnitIdPage(unitId, page);
	}

	public void setOfficeWorkReportDao(OfficeWorkReportDao officeWorkReportDao){
		this.officeWorkReportDao = officeWorkReportDao;
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdPageContent(String unit,Pagination page,String userId,
			String content,String beginTime,String endTime,String reportType,String state) {
		return officeWorkReportDao.getOfficeWorkReportByUnitIdPageContent(unit, page, userId, content, beginTime, endTime, reportType, state);
	}

	@Override
	public List<OfficeWorkReport> getOfficeWorkReportByUnitIdPageContentCreateUserName(
			String userId, Pagination page, String content, String beginTime,
			String endTime, String reportType, String createUserName) {
		return officeWorkReportDao.getOfficeWorkReportByUnitIdPageContentCreateUserName(userId, page, content, beginTime, endTime, reportType, createUserName);
	}
	
	public List<OfficeWorkReport> getOfficeWorkReportList(String receiveUserId, String userId, String createUserName, String content, Pagination page){
//		String searchUserIds = "";
//		if(StringUtils.isNotBlank(createUserName)){
//			List<User> userlist = userService.getUsersBySearchName(createUserName, null);
//			
//		}
		List<OfficeWorkReport> list = officeWorkReportDao.getOfficeWorkReportList(receiveUserId, userId, createUserName, content, page);
		Set<String> userIds = new HashSet<String>();
		for(OfficeWorkReport ent : list){
			userIds.add(ent.getCreateUserId());
			if(StringUtils.isNotBlank(ent.getReceiveUserId())){
				String[] ids = ent.getReceiveUserId().split(",");
				for(String id : ids){
					userIds.add(id);
				}
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		for(OfficeWorkReport ent : list){
			if(userMap.containsKey(ent.getCreateUserId())){
				User user = userMap.get(ent.getCreateUserId());
				if(user != null){
					ent.setCreateUserName(user.getRealname());
				}
			}
			if(StringUtils.isNotBlank(ent.getReceiveUserId())){
				String[] ids = ent.getReceiveUserId().split(",");
				StringBuffer names = new StringBuffer();
				int m = 0;
				for(String id : ids){
					if(userMap.containsKey(id)){
						User user = userMap.get(id);
						if(user != null){
							if(m == 0){
								names.append(user.getRealname());
							}else{
								if(m > 1){//最多显示两个接收人，多余两个则显示...
									names.append("...");
									break;
								}else{
									names.append(",").append(user.getRealname());
								}
							}
							m++;
						}
					}
				}
				ent.setReceiveUserNameStr(names.toString());
			}
		}
		return list;
	}
	
	public List<OfficeWorkReport> getOfficeWorkReportList(String receiveUserId, String userId, String createUserName, String content,String type, String createTime,Pagination page){
		List<OfficeWorkReport> list = officeWorkReportDao.getOfficeWorkReportList(receiveUserId, userId, createUserName, content,type,createTime, page);
		Set<String> userIds = new HashSet<String>();
		for(OfficeWorkReport ent : list){
			userIds.add(ent.getCreateUserId());
			if(StringUtils.isNotBlank(ent.getReceiveUserId())){
				String[] ids = ent.getReceiveUserId().split(",");
				for(String id : ids){
					userIds.add(id);
				}
				ent.setSendNum(ids.length);
			}
			if(StringUtils.isNotBlank(ent.getReadUserId())){
				String[] ids = ent.getReadUserId().split(",");
				ent.setReadNum(ids.length);
				if(StringUtils.isNotBlank(receiveUserId)){
					if(Arrays.asList(ids).contains(receiveUserId)){
						ent.setRead("1");
					}else{
						ent.setRead("0");
					}
				}
			}else{
				ent.setReadNum(0);
				if(StringUtils.isNotBlank(receiveUserId)){
					ent.setRead("0");
				}
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		Map<String, String> userPhotoMap = userSetService.getUserPhotoMap(userIds.toArray(new String[0]));
		for(OfficeWorkReport ent : list){
			if(userMap.containsKey(ent.getCreateUserId())){
				User user = userMap.get(ent.getCreateUserId());
				String path = userPhotoMap.get(ent.getCreateUserId());
				if(StringUtils.isBlank(path)){
					path="";
				}
				if(user != null){
					ent.setCreateUserName(user.getRealname());
				}
				if(StringUtils.isNotBlank(path)){
					ent.setUserPhoto(path);
				}
			}
		}
		return list;
	}
	
	@Override
	public OfficeWorkReport getOfficeWorkReportDetailsById(String id) {
		OfficeWorkReport officeWorkReport = getOfficeWorkReportById(id);
		Set<String> userIds = new HashSet<String>();
		List<String> userIdList = new ArrayList<String>();
		userIds.add(officeWorkReport.getCreateUserId());
		if(StringUtils.isNotBlank(officeWorkReport.getReceiveUserId())){
			String[] ids = officeWorkReport.getReceiveUserId().split(",");
			for(String reid:ids){
				userIds.add(reid);
				userIdList.add(reid);
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIds.toArray(new String[0]));
		Map<String, String> userPhotoMap = userSetService.getUserPhotoMap(userIds.toArray(new String[0]));
		if(StringUtils.isNotBlank(officeWorkReport.getReadUserId())){
			String[] readIds = officeWorkReport.getReadUserId().split(",");
			StringBuffer readNames = new StringBuffer();
			for(String readId : readIds){
				if(userMap.containsKey(readId)){
					User user = userMap.get(readId);
					String path = userPhotoMap.get(readId);
					if(StringUtils.isBlank(path)){
						path="";
					}
					if(user != null){
						if(StringUtils.isNotBlank(readNames.toString())){
							readNames.append(","+user.getRealname()+"=="+path);
						}else{
							readNames.append(user.getRealname()+"=="+path);
						}
					}
				}
				userIds.remove(readId);
			}
			officeWorkReport.setReadUserNameStr(readNames.toString());
		}
		if(userMap.containsKey(officeWorkReport.getCreateUserId())){
			User user = userMap.get(officeWorkReport.getCreateUserId());
			String path = userPhotoMap.get(officeWorkReport.getCreateUserId());
			if(StringUtils.isBlank(path)){
				path="";
			}
			if(user != null){
				officeWorkReport.setCreateUserName(user.getRealname());
			}
			if(StringUtils.isNotBlank(path)){
				officeWorkReport.setUserPhoto(path);;
			}
		}
		if(!userIdList.contains(officeWorkReport.getCreateUserId())){
			userIds.remove(officeWorkReport.getCreateUserId());
		}
		if(CollectionUtils.isNotEmpty(userIds)){
			StringBuffer unreadNames = new StringBuffer();
			for(String unreadId : userIds){
				if(userMap.containsKey(unreadId)){
					User user = userMap.get(unreadId);
					String path = userPhotoMap.get(unreadId);
					if(StringUtils.isBlank(path)){
						path="";
					}
					if(user != null){
						if(StringUtils.isNotBlank(unreadNames.toString())){
							unreadNames.append(","+user.getRealname()+"=="+path);
						}else{
							unreadNames.append(user.getRealname()+"=="+path);
						}
					}
				}
			}
			officeWorkReport.setUnreadUserNameStr(unreadNames.toString());
		}
		officeWorkReport.setAttachments(attachmentService.getAttachments(officeWorkReport.getId(), Constants.OFFICE_WORK_REPORT_AIT));
		return officeWorkReport;
	}
	@Override
	public List<OfficeWorkReport> getOfficeWorkReportListICanRead(
			String receiveUserId, String type, Pagination page) {
		return officeWorkReportDao.getOfficeWorkReportListICanRead(receiveUserId, type, page);
	}
	@Override
	public Integer updateReadUserId(String readUserId, String id) {
		OfficeWorkReport officeWorkReport = getOfficeWorkReportById(id);
		if(StringUtils.isNotBlank(officeWorkReport.getReadUserId())){
			String[] ids = officeWorkReport.getReadUserId().split(",");
			if(Arrays.asList(ids).contains(readUserId)){
				return 0;
			}else{
				readUserId = officeWorkReport.getReadUserId() +","+readUserId;
			}
		}
		return officeWorkReportDao.updateReadUserId(readUserId, id);
	}

}