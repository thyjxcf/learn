package net.zdsoft.office.meeting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.office.meeting.dao.OfficeExecutiveIssueDao;
import net.zdsoft.office.meeting.entity.OfficeDeptLeader;
import net.zdsoft.office.meeting.entity.OfficeExecutiveFixedDept;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssue;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssueAttend;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeet;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeetAttend;
import net.zdsoft.office.meeting.service.OfficeDeptLeaderService;
import net.zdsoft.office.meeting.service.OfficeExecutiveFixedDeptService;
import net.zdsoft.office.meeting.service.OfficeExecutiveIssueAttendService;
import net.zdsoft.office.meeting.service.OfficeExecutiveIssueService;
import net.zdsoft.office.meeting.service.OfficeExecutiveMeetAttendService;
import net.zdsoft.office.meeting.service.OfficeExecutiveMeetService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * office_executive_issue
 * @author 
 * 
 */
public class OfficeExecutiveIssueServiceImpl implements OfficeExecutiveIssueService{
	
	private AttachmentService attachmentService;
	private OfficeDeptLeaderService officeDeptLeaderService;
	private OfficeExecutiveMeetService officeExecutiveMeetService;
	private OfficeExecutiveFixedDeptService officeExecutiveFixedDeptService;
	private OfficeExecutiveMeetAttendService officeExecutiveMeetAttendService;
	private OfficeExecutiveIssueAttendService officeExecutiveIssueAttendService;
	
	private OfficeExecutiveIssueDao officeExecutiveIssueDao;
	private SystemIniService systemIniService;
	
	@Override
	public OfficeExecutiveIssue save(OfficeExecutiveIssue officeExecutiveIssue){
		return officeExecutiveIssueDao.save(officeExecutiveIssue);
	}

	@Override
	public Integer delete(String[] ids){
		return officeExecutiveIssueDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExecutiveIssue officeExecutiveIssue){
		return officeExecutiveIssueDao.update(officeExecutiveIssue);
	}

	@Override
	public OfficeExecutiveIssue getOfficeExecutiveIssueById(String id){//TODO
		OfficeExecutiveIssue issue = officeExecutiveIssueDao.getOfficeExecutiveIssueById(id);
		if(issue != null){
			List<OfficeExecutiveIssueAttend> issueAttendList = officeExecutiveIssueAttendService.getOfficeExecutiveIssueAttendList(new String[]{id});
			for(OfficeExecutiveIssueAttend issueAttend : issueAttendList){
				String objectId = issueAttend.getObjectId();
				String objectName = issueAttend.getObjectName();
				if(StringUtils.isNotBlank(objectName)){
					if(issueAttend.getType() == 1){
						if(StringUtils.isBlank(issue.getHostDeptId())){
							issue.setHostDeptId(objectId);
							issue.setHostDeptNameStr(objectName);
						}
						else{
							issue.setHostDeptId(issue.getHostDeptId() + "," + objectId);
							issue.setHostDeptNameStr(issue.getHostDeptNameStr() + "," + objectName);
						}
					}
					if(issueAttend.getType() == 2){
						if(StringUtils.isBlank(issue.getAttendDeptId())){
							issue.setAttendDeptId(objectId);
							issue.setAttendDeptNameStr(objectName);
						}
						else{
							issue.setAttendDeptId(issue.getAttendDeptId() + "," + objectId);
							issue.setAttendDeptNameStr(issue.getAttendDeptNameStr() + "," + objectName);
						}
					}
					if(issueAttend.getType() == 3){
						if(StringUtils.isBlank(issue.getLeaderId())){
							issue.setLeaderId(objectId);
							issue.setLeaderNameStr(objectName);
						}
						else{
							issue.setLeaderId(issue.getLeaderId() + "," + objectId);
							issue.setLeaderNameStr(issue.getLeaderNameStr() + "," + objectName);
						}
					}
					if(issueAttend.getType() == 4){
						if(StringUtils.isBlank(issue.getOpinionDeptId())){
							issue.setOpinionDeptId(objectId);
							issue.setOpinionDeptNameStr(objectName);
						}
						else{
							issue.setOpinionDeptId(issue.getOpinionDeptId() + "," + objectId);
							issue.setOpinionDeptNameStr(issue.getOpinionDeptNameStr() + "," + objectName);
						}
					}
				}
			}
			List<Attachment> issueAttachments = attachmentService.getAttachments(id, Constants.OFFICE_EXECUTIVE_ISSUE);
			if(issueAttachments.size()>0)
				issue.setAttachments(issueAttachments);
			
			if(StringUtils.isNotBlank(issue.getMeetingId())){
				OfficeExecutiveMeet meet = officeExecutiveMeetService.getOfficeExecutiveMeetById(issue.getMeetingId());
				if(meet != null){
					issue.setMeetingName(meet.getName());
				}
			}
		}
		return issue;
	}

	@Override
	public Map<String, OfficeExecutiveIssue> getOfficeExecutiveIssueMapByIds(String[] ids){
		return officeExecutiveIssueDao.getOfficeExecutiveIssueMapByIds(ids);
	}

	public List<OfficeExecutiveIssue> setIssueListImformations(List<OfficeExecutiveIssue> list){
		Set<String> issueIdSet = new HashSet<String>();
		for(OfficeExecutiveIssue issue:list){
			issueIdSet.add(issue.getId());
		}
		List<OfficeExecutiveIssueAttend> attends = officeExecutiveIssueAttendService.getOfficeExecutiveIssueAttendList(issueIdSet.toArray(new String[0]));
		Map<String, List<Attachment>> attachmentMap = attachmentService.getAttachmentsMap(issueIdSet.toArray(new String[0]));
		for(OfficeExecutiveIssue issue:list){
			for(OfficeExecutiveIssueAttend attend : attends){
				if(issue.getId().equals(attend.getIssueId())){
					if(attend.getType() == 1){
						if(StringUtils.isBlank(issue.getHostDeptNameStr()))
							issue.setHostDeptNameStr(attend.getObjectName());
						else
							issue.setHostDeptNameStr(issue.getHostDeptNameStr() + "," + attend.getObjectName());
					}
					if(attend.getType() == 2){
						if(StringUtils.isBlank(issue.getAttendDeptNameStr()))
							issue.setAttendDeptNameStr(attend.getObjectName());
						else
							issue.setAttendDeptNameStr(issue.getAttendDeptNameStr() + "," + attend.getObjectName());
					}
					if(attend.getType() == 3){
						if(StringUtils.isBlank(issue.getLeaderNameStr()))
							issue.setLeaderNameStr(attend.getObjectName());
						else
							issue.setLeaderNameStr(issue.getLeaderNameStr() + "," + attend.getObjectName());
					}
				}
			}
			List<Attachment> attachments = attachmentMap.get(issue.getId());
			if(attachments != null && attachments.size() > 0){
				issue.setAttachments(attachments);
			}
		}
		return list;
	}
	
	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueList(String meetId, Pagination page){
		//TODO
		List<OfficeExecutiveIssue> list = officeExecutiveIssueDao.getOfficeExecutiveIssueList(meetId, page);
		if(CollectionUtils.isNotEmpty(list)){
			list = this.setIssueListImformations(list);
		}
		return list;
	}

	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssuePage(Pagination page){
		return officeExecutiveIssueDao.getOfficeExecutiveIssuePage(page);
	}

	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByUnitIdList(String unitId){
		return officeExecutiveIssueDao.getOfficeExecutiveIssueByUnitIdList(unitId);
	}

	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByConditions(String unitId, String userId, String queryName, String[] queryStates, Pagination page){
		List<OfficeDeptLeader> leaderList = officeDeptLeaderService.getOfficeDeptLeaderByUnitIdList(unitId);
		//当为某一部门领导人，office_executive_issue_attend的object_id可以是deptId或userId
		String deptId = null;
		if(StringUtils.isNotBlank(userId)){
			for(OfficeDeptLeader leader : leaderList){
				if(userId.equals(leader.getUserId())){
					deptId = leader.getDeptId();
				}
			}
		}
		//查询条件有单位ID，议题名称，以及提报人或提报领导，或主办科室、列席科室、意见征集科室为userID的
		List<OfficeExecutiveIssue> issueList = officeExecutiveIssueDao.getOfficeExecutiveIssueByConditions(unitId, userId, deptId, queryName, queryStates, page);
		Set<String> issueIds = new HashSet<String>();
		for(OfficeExecutiveIssue issue : issueList){
			issueIds.add(issue.getId());
		}
		//TODO
		List<OfficeExecutiveIssueAttend> issueAttendList = officeExecutiveIssueAttendService.getOfficeExecutiveIssueAttendList(issueIds.toArray(new String[0]));
		Map<String, String> hostDeptNameMap = new HashMap<String, String>();
		Map<String, String> attendDeptNameMap = new HashMap<String, String>();
		Map<String, String> leaderNameMap = new HashMap<String, String>();
		Map<String, String> opinionDeptNameMap = new HashMap<String, String>();
		Map<String, List<Attachment>> attachmentMap = attachmentService.getAttachmentsMap(issueIds.toArray(new String[0]));
		//key为issue的ID，value为issue_attend
		Map<String, OfficeExecutiveIssueAttend> reviseOpinionMap = new HashMap<String, OfficeExecutiveIssueAttend>();
		for(OfficeExecutiveIssueAttend issueAttend : issueAttendList){
			String objectId = issueAttend.getObjectId();
			String objectName = issueAttend.getObjectName();
			if(StringUtils.isNotBlank(objectName)){
				
			}
			//设置主办科室
			if(issueAttend.getType() == 1){
				String hostDeptName = hostDeptNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(hostDeptName))
					hostDeptName = objectName;
				else
					hostDeptName += "," + objectName;
				hostDeptNameMap.put(issueAttend.getIssueId(), hostDeptName);
			}
			//设置列席科室
			if(issueAttend.getType() == 2){
				String attendDeptName = attendDeptNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(attendDeptName))
					attendDeptName = objectName;
				else
					attendDeptName += "," + objectName;
				attendDeptNameMap.put(issueAttend.getIssueId(), attendDeptName);
			}
			//设置提报领导
			if(issueAttend.getType() == 3){
				String leaderName = leaderNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(leaderName))
					leaderName = objectName;
				else	
					leaderName += "," + objectName;
				leaderNameMap.put(issueAttend.getIssueId(), leaderName);
			}
			//设置意见征集科室
			if(issueAttend.getType() == 4){
				String opinionDeptName = opinionDeptNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(opinionDeptName))
					opinionDeptName = objectName;
				else
					opinionDeptName += "," + objectName;
					
				opinionDeptNameMap.put(issueAttend.getIssueId(), opinionDeptName);
				
				if(objectId.equals(deptId))
					reviseOpinionMap.put(issueAttend.getIssueId(), issueAttend);
			}
		}
		for(OfficeExecutiveIssue issue : issueList){
			for(Map.Entry<String, String> entry : hostDeptNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setHostDeptNameStr(entry.getValue());
				}
			}
			for(Map.Entry<String, String> entry : attendDeptNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setAttendDeptNameStr(entry.getValue());
				}
			}
			for(Map.Entry<String, String> entry : leaderNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setLeaderNameStr(entry.getValue());
				}
			}
			for(Map.Entry<String, String> entry : opinionDeptNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setOpinionDeptNameStr(entry.getValue());
				}
			}
			if(issue.getCreateUserId().equals(userId))
				issue.setCreateUser(true);
			else
				issue.setCreateUser(false);
			
			issue.setReviseOpinionType(0);
			for(Map.Entry<String, OfficeExecutiveIssueAttend> entry : reviseOpinionMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					if(entry.getValue().getIsReplyed())
						issue.setReviseOpinionType(2);
					else
						issue.setReviseOpinionType(1);
				}
			}
			
			List<Attachment> attachments = attachmentMap.get(issue.getId());
			if(attachments != null && attachments.size() > 0){
				issue.setAttachments(attachments);
			}
		}
		
		return issueList;
	}
	
	@Override
	public void submitIssue(String issueId){
		officeExecutiveIssueDao.submitIssue(issueId);
	}
	
	public void deleteFile(String objectId, String objectType){
		List<Attachment> attachments = attachmentService
				.getAttachments(objectId, objectType);
		String[] attachmentIds = new String[attachments.size()];
		for(int i = 0; i < attachments.size(); i++){
			attachmentIds[i] = attachments.get(i).getId();
		}
		attachmentService.deleteAttachments(attachmentIds);
	}
	
	public void saveAttachment(OfficeExecutiveIssue issue, List<UploadFile> uploadFileList){
		//保存附件
		if(!CollectionUtils.isEmpty(uploadFileList)){
			for (UploadFile uploadFile : uploadFileList) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(issue.getUnitId());
				attachment.setObjectId(issue.getId());
				attachment.setObjectType(Constants.OFFICE_EXECUTIVE_ISSUE);
				attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
				attachmentService.saveAttachment(attachment, uploadFile, false);
			}
		}
	}
	
	@Override
	public void saveIssue(OfficeExecutiveIssue issue){//TODO
		//获取附件
		List<UploadFile> uploadFileList  = StorageFileUtils.handleFiles(new String[] {},Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
		if(StringUtils.isNotBlank(issue.getId())){
			officeExecutiveIssueDao.update(issue);
			if(ArrayUtils.isNotEmpty(issue.getRemoveAttachment())){
				attachmentService.deleteAttachments(issue.getRemoveAttachment());
			}
		}else{
			officeExecutiveIssueDao.save(issue);
		}
		saveAttachment(issue, uploadFileList);
		
		//将信息更新进attend表之前，先删除老数据
		officeExecutiveIssueAttendService.deleteByIssueIds(new String[]{issue.getId()});
		List<OfficeExecutiveIssueAttend> attendlist = new ArrayList<OfficeExecutiveIssueAttend>();
		
		if(StringUtils.isNotBlank(issue.getHostDeptId())){
			String[] hostDeptIds = issue.getHostDeptId().split(",");
			for(String hostDeptId : hostDeptIds){
				OfficeExecutiveIssueAttend attend = new OfficeExecutiveIssueAttend();
				attend.setIssueId(issue.getId());
				attend.setType(1);
				attend.setObjectId(hostDeptId);
				attend.setIsReplyed(false);
				attend.setUnitId(issue.getUnitId());
				attendlist.add(attend);
			}
		}
		if(StringUtils.isNotBlank(issue.getAttendDeptId())){
			String[] attendDeptIds = issue.getAttendDeptId().split(",");
			for(String attendDeptId : attendDeptIds){
				OfficeExecutiveIssueAttend attend = new OfficeExecutiveIssueAttend();
				attend.setIssueId(issue.getId());
				attend.setType(2);
				attend.setObjectId(attendDeptId);
				attend.setIsReplyed(false);
				attend.setUnitId(issue.getUnitId());
				attendlist.add(attend);
			}
		}
		if(StringUtils.isNotBlank(issue.getLeaderId())){
			String[] leaderIds = issue.getLeaderId().split(",");
			for(String leaderId : leaderIds){
				OfficeExecutiveIssueAttend attend = new OfficeExecutiveIssueAttend();
				attend.setIssueId(issue.getId());
				attend.setType(3);
				attend.setObjectId(leaderId);
				attend.setIsReplyed(false);
				attend.setUnitId(issue.getUnitId());
				attendlist.add(attend);
			}
		}
		if(StringUtils.isNotBlank(issue.getOpinionDeptId())){
			String[] opinionDeptIds = issue.getOpinionDeptId().split(",");
			for(String opinionDeptId : opinionDeptIds){
				OfficeExecutiveIssueAttend attend = new OfficeExecutiveIssueAttend();
				attend.setIssueId(issue.getId());
				attend.setType(4);
				attend.setObjectId(opinionDeptId);
				attend.setIsReplyed(false);
				attend.setUnitId(issue.getUnitId());
				attendlist.add(attend);
			}
		}
		officeExecutiveIssueAttendService.batchSave(attendlist);
	}
	
	@Override
	public void deleteIssue(String issueId){
		deleteFile(issueId, Constants.OFFICE_EXECUTIVE_ISSUE);
		officeExecutiveIssueAttendService.deleteByIssueIds(new String[]{issueId});
		this.delete(new String[]{issueId});
	}
	
	@Override
	public void removeIssue(String unitId, String issueId, String meetId) {
		List<String> issueIds = officeExecutiveIssueDao.getIdsByMeetId(meetId);
		officeExecutiveIssueDao.removeIssue(issueId);
		issueIds.remove(issueId);
		addToMeetAttend(unitId, meetId, issueIds.toArray(new String[0]));
	}
	
	@Override
	public void saveIssueAudit(OfficeExecutiveIssue issue){
		if(StringUtils.isNotBlank(issue.getId())){
			//原来的attend信息
			List<OfficeExecutiveIssueAttend> issueAttendList = officeExecutiveIssueAttendService.getOfficeExecutiveIssueAttendList(new String[]{issue.getId()});
			List<OfficeExecutiveIssueAttend> newAttendlist = new ArrayList<OfficeExecutiveIssueAttend>();
			List<String> deleteAttendIds = new ArrayList<String>();
			List<String> newDeptIds = new ArrayList<String>();
			Map<String, String> oldIdsMap = new HashMap<String, String>();
			Map<String, String> newIdsMap = new HashMap<String, String>();
			String[] newOpinionDeptIds = issue.getOpinionDeptId().split(",");
			for(String newId : newOpinionDeptIds)
				newIdsMap.put(newId, newId);
			
			//删除所有type=2（列席科室）的attend信息
			for(OfficeExecutiveIssueAttend attend : issueAttendList){
				if(attend.getType() == 2){
					deleteAttendIds.add(attend.getId());
				}
				if(attend.getType() == 4){
					oldIdsMap.put(attend.getId(), attend.getObjectId());
				}
			}
			//删除原来有的而现在没的意见征集科室
			for(Map.Entry<String, String> entry : oldIdsMap.entrySet())
				if(!newIdsMap.containsKey(entry.getValue()))
					deleteAttendIds.add(entry.getKey());
			//新增原来没有而现在有的意见征集科室
			for(String newId : newOpinionDeptIds)
				if(!oldIdsMap.containsValue(newId))
					newDeptIds.add(newId);
			
			officeExecutiveIssueAttendService.delete(deleteAttendIds.toArray(new String[0]));
			
			//type=2全新增
			if(StringUtils.isNotBlank(issue.getAttendDeptId())){
				String[] attendDeptIds = issue.getAttendDeptId().split(",");
				for(String attendDeptId : attendDeptIds){
					OfficeExecutiveIssueAttend attend = new OfficeExecutiveIssueAttend();
					attend.setIssueId(issue.getId());
					attend.setType(2);
					attend.setObjectId(attendDeptId);
					attend.setIsReplyed(false);
					attend.setUnitId(issue.getUnitId());
					newAttendlist.add(attend);
				}
			}
			//type=4新增newOpinionList
			for(String opinionDeptId : newDeptIds){
				OfficeExecutiveIssueAttend attend = new OfficeExecutiveIssueAttend();
				attend.setIssueId(issue.getId());
				attend.setType(4);
				attend.setObjectId(opinionDeptId);
				attend.setIsReplyed(false);
				attend.setUnitId(issue.getUnitId());
				newAttendlist.add(attend);
			}
			officeExecutiveIssueAttendService.batchSave(newAttendlist);
			officeExecutiveIssueDao.saveIssueAudit(issue);
		}
	}
	
	@Override
	public Map<String, List<OfficeExecutiveIssue>> getOfficeExecutiveIssueMapByMeetIds(
			String[] meetIds) {
		Map<String, List<OfficeExecutiveIssue>> map = new HashMap<String, List<OfficeExecutiveIssue>>();
		List<OfficeExecutiveIssue> issueList = officeExecutiveIssueDao.getOfficeExecutiveIssueByMeetIds(meetIds);
		Set<String> issueIds = new HashSet<String>();
		for(OfficeExecutiveIssue issue : issueList){
			issueIds.add(issue.getId());
		}
		//TODO
		List<OfficeExecutiveIssueAttend> issueAttendList = officeExecutiveIssueAttendService.getOfficeExecutiveIssueAttendList(issueIds.toArray(new String[0]));
		Map<String, String> hostDeptNameMap = new HashMap<String, String>();
		Map<String, String> attendDeptNameMap = new HashMap<String, String>();
		Map<String, String> leaderNameMap = new HashMap<String, String>();
		Map<String, List<Attachment>> attachmentMap = attachmentService.getAttachmentsMap(issueIds.toArray(new String[0]));
		//key为issue的ID，value为issue_attend
		for(OfficeExecutiveIssueAttend issueAttend : issueAttendList){
			String objectName = issueAttend.getObjectName();
			//设置主办科室
			if(issueAttend.getType() == 1){
				String hostDeptName = hostDeptNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(hostDeptName))
					hostDeptName = objectName;
				else
					hostDeptName += "," + objectName;
				hostDeptNameMap.put(issueAttend.getIssueId(), hostDeptName);
			}
			//设置列席科室
			if(issueAttend.getType() == 2){
				String attendDeptName = attendDeptNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(attendDeptName))
					attendDeptName = objectName;
				else
					attendDeptName += "," + objectName;
				attendDeptNameMap.put(issueAttend.getIssueId(), attendDeptName);
			}
			//设置提报领导
			if(issueAttend.getType() == 3){
				String leaderName = leaderNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(leaderName))
					leaderName = objectName;
				else	
					leaderName += "," + objectName;
				leaderNameMap.put(issueAttend.getIssueId(), leaderName);
			}
		}
		for(OfficeExecutiveIssue issue : issueList){
			for(Map.Entry<String, String> entry : hostDeptNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setHostDeptNameStr(entry.getValue());
				}
			}
			for(Map.Entry<String, String> entry : attendDeptNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setAttendDeptNameStr(entry.getValue());
				}
			}
			for(Map.Entry<String, String> entry : leaderNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setLeaderNameStr(entry.getValue());
				}
			}
			
			List<Attachment> attachments = attachmentMap.get(issue.getId());
			if(attachments != null && attachments.size() > 0){
				issue.setAttachments(attachments);
			}
		}
		for(String meetId:meetIds){
			List<OfficeExecutiveIssue> officeExecutiveIssues = new ArrayList<OfficeExecutiveIssue>();
			for(OfficeExecutiveIssue issue : issueList){
				if(meetId.equals(issue.getMeetingId())){
					officeExecutiveIssues.add(issue);
				}
			}
			map.put(meetId, officeExecutiveIssues);
		}
		return map;
	}
	
	@Override
	public Map<String, List<OfficeExecutiveIssue>> getOfficeExecutiveIssueMapByMeetIds(
			String[] meetIds, String userId, String deptId) {
		Map<String, List<OfficeExecutiveIssue>> map = new HashMap<String, List<OfficeExecutiveIssue>>();
		List<OfficeExecutiveIssue> issueList = officeExecutiveIssueDao.getOfficeExecutiveIssueByMeetIds(meetIds);
		Set<String> issueIds = new HashSet<String>();
		for(OfficeExecutiveIssue issue : issueList){
			issueIds.add(issue.getId());
		}
		//获取与当前登录人员相关的议题Map
		Map<String, String> issueMap = officeExecutiveIssueAttendService.getOfficeExecutiveIssueAttendMap(issueIds.toArray(new String[0]), userId, deptId);
		//重新过滤议题，减少搜索及数据组装
		issueIds = new HashSet<String>();
		for(OfficeExecutiveIssue issue : issueList){
			if(issueMap.containsKey(issue.getId())){
				issueIds.add(issue.getId());
			}
		}
		List<OfficeExecutiveIssueAttend> issueAttendList = officeExecutiveIssueAttendService.getOfficeExecutiveIssueAttendList(issueIds.toArray(new String[0]));
		Map<String, String> hostDeptNameMap = new HashMap<String, String>();
		Map<String, String> attendDeptNameMap = new HashMap<String, String>();
		Map<String, String> leaderNameMap = new HashMap<String, String>();
		Map<String, List<Attachment>> attachmentMap = attachmentService.getAttachmentsMap(issueIds.toArray(new String[0]));
		//key为issue的ID，value为issue_attend
		for(OfficeExecutiveIssueAttend issueAttend : issueAttendList){
			String objectName = issueAttend.getObjectName();
			//设置主办科室
			if(issueAttend.getType() == 1){
				String hostDeptName = hostDeptNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(hostDeptName))
					hostDeptName = objectName;
				else
					hostDeptName += "," + objectName;
				hostDeptNameMap.put(issueAttend.getIssueId(), hostDeptName);
			}
			//设置列席科室
			if(issueAttend.getType() == 2){
				String attendDeptName = attendDeptNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(attendDeptName))
					attendDeptName = objectName;
				else
					attendDeptName += "," + objectName;
				attendDeptNameMap.put(issueAttend.getIssueId(), attendDeptName);
			}
			//设置提报领导
			if(issueAttend.getType() == 3){
				String leaderName = leaderNameMap.get(issueAttend.getIssueId());
				if(StringUtils.isBlank(leaderName))
					leaderName = objectName;
				else	
					leaderName += "," + objectName;
				leaderNameMap.put(issueAttend.getIssueId(), leaderName);
			}
		}
		for(OfficeExecutiveIssue issue : issueList){
			if(!issueMap.containsKey(issue.getId())){
				continue;
			}
			for(Map.Entry<String, String> entry : hostDeptNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setHostDeptNameStr(entry.getValue());
				}
			}
			for(Map.Entry<String, String> entry : attendDeptNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setAttendDeptNameStr(entry.getValue());
				}
			}
			for(Map.Entry<String, String> entry : leaderNameMap.entrySet()){
				if(issue.getId().equals(entry.getKey())){
					issue.setLeaderNameStr(entry.getValue());
				}
			}
			
			List<Attachment> attachments = attachmentMap.get(issue.getId());
			if(attachments != null && attachments.size() > 0){
				issue.setAttachments(attachments);
			}
		}
		for(String meetId:meetIds){
			List<OfficeExecutiveIssue> officeExecutiveIssues = new ArrayList<OfficeExecutiveIssue>();
			for(OfficeExecutiveIssue issue : issueList){
				if(!issueMap.containsKey(issue.getId())){
					continue;
				}
				if(meetId.equals(issue.getMeetingId())){
					officeExecutiveIssues.add(issue);
				}
			}
			map.put(meetId, officeExecutiveIssues);
		}
		return map;
	}
	
	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssues(String unitId, Pagination page){
		List<OfficeExecutiveIssue> list = officeExecutiveIssueDao.getOfficeExecutiveIssues(unitId, page);
		if(CollectionUtils.isNotEmpty(list)){
			list = this.setIssueListImformations(list);
		}
		return list;
	}
	
	@Override
	public void addToMeet(String unitId, String meetId, String[] ids, boolean needAddOne) {
		List<OfficeExecutiveIssue> officeExecutiveIssues = officeExecutiveIssueDao.getOfficeExecutiveIssueList(meetId, null);
		int firstNumber = officeExecutiveIssues.size();
		if(needAddOne){
			firstNumber = firstNumber+1;
		}
		officeExecutiveIssueDao.addToMeet(meetId,ids,firstNumber);
		String[] issueIdsAll = new String[officeExecutiveIssues.size()+ids.length];
		int i = 0;
		for(OfficeExecutiveIssue issue:officeExecutiveIssues){
			issueIdsAll[i] = issue.getId();
			i++;
		}
		System.arraycopy(ids, 0, issueIdsAll, i, ids.length);
		addToMeetAttend(unitId, meetId, issueIdsAll);
	}
	
	@Override
	public void sortIssue(List<OfficeExecutiveIssue> issueList){
		officeExecutiveIssueDao.sortIssue(issueList);
	}
	
	/**
	 * 添加会议参加表数据
	 * @param unitId
	 * @param meetId
	 * @param issueIdsAll
	 */
	private void addToMeetAttend(String unitId, String meetId, String[] issueIdsAll){
		List<OfficeExecutiveIssueAttend> issueAttends = officeExecutiveIssueAttendService.getOfficeExecutiveIssueAttendListSimple(issueIdsAll);
		List<OfficeExecutiveFixedDept> fixedDepts = officeExecutiveFixedDeptService.getOfficeExecutiveFixedDeptByUnitIdList(unitId);
		List<OfficeExecutiveMeetAttend> meetAttends = new ArrayList<OfficeExecutiveMeetAttend>();
		Set<String> attendDeptSet = new HashSet<String>();
		Set<String> attendLeaderSet = new HashSet<String>();
		// 组织数据
		for(OfficeExecutiveIssueAttend issueAttend:issueAttends){
			if(issueAttend.getType() == Constants.HOST_DEPT || issueAttend.getType() == Constants.ATTEND_DEPT){
				attendDeptSet.add(issueAttend.getObjectId());
			}
			if(issueAttend.getType() == Constants.ATTEND_LEADER){
				attendLeaderSet.add(issueAttend.getObjectId());
			}
		}
		if(org.apache.commons.lang.ArrayUtils.isNotEmpty(issueIdsAll)){//当没有议题附加到会议上时，不添加固定列席科室到meet_attend表
			for(OfficeExecutiveFixedDept fixedDept:fixedDepts){
				attendDeptSet.add(fixedDept.getDeptId());
			}
		}
		for(String deptId:attendDeptSet){
			OfficeExecutiveMeetAttend meetAttend = new OfficeExecutiveMeetAttend();
			meetAttend.setMeetingId(meetId);
			meetAttend.setUnitId(unitId);
			meetAttend.setType(Constants.ATTEND_DEPT);
			meetAttend.setObjectId(deptId);
			meetAttends.add(meetAttend);
		}
		for(String userId:attendLeaderSet){
			OfficeExecutiveMeetAttend meetAttend = new OfficeExecutiveMeetAttend();
			meetAttend.setMeetingId(meetId);
			meetAttend.setUnitId(unitId);
			meetAttend.setType(Constants.ATTEND_LEADER);
			meetAttend.setObjectId(userId);
			meetAttends.add(meetAttend);
		}
		officeExecutiveMeetAttendService.deleteByMeetId(meetId);
		officeExecutiveMeetAttendService.batchSave(meetAttends);
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setOfficeDeptLeaderService(
			OfficeDeptLeaderService officeDeptLeaderService) {
		this.officeDeptLeaderService = officeDeptLeaderService;
	}

	public void setOfficeExecutiveMeetService(
			OfficeExecutiveMeetService officeExecutiveMeetService) {
		this.officeExecutiveMeetService = officeExecutiveMeetService;
	}

	public void setOfficeExecutiveFixedDeptService(
			OfficeExecutiveFixedDeptService officeExecutiveFixedDeptService) {
		this.officeExecutiveFixedDeptService = officeExecutiveFixedDeptService;
	}

	public void setOfficeExecutiveMeetAttendService(
			OfficeExecutiveMeetAttendService officeExecutiveMeetAttendService) {
		this.officeExecutiveMeetAttendService = officeExecutiveMeetAttendService;
	}

	public void setOfficeExecutiveIssueAttendService(
			OfficeExecutiveIssueAttendService officeExecutiveIssueAttendService) {
		this.officeExecutiveIssueAttendService = officeExecutiveIssueAttendService;
	}

	public void setOfficeExecutiveIssueDao(
			OfficeExecutiveIssueDao officeExecutiveIssueDao) {
		this.officeExecutiveIssueDao = officeExecutiveIssueDao;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}
	
}	