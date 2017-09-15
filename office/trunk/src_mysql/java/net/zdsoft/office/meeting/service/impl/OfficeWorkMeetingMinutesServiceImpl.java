package net.zdsoft.office.meeting.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.office.meeting.dao.OfficeWorkMeetingMinutesDao;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingMinutes;
import net.zdsoft.office.meeting.service.OfficeWorkMeetingMinutesService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
/**
 * office_work_meeting_minutes
 * @author 
 * 
 */
public class OfficeWorkMeetingMinutesServiceImpl implements OfficeWorkMeetingMinutesService{
	private OfficeWorkMeetingMinutesDao officeWorkMeetingMinutesDao;
	private AttachmentService attachmentService; 
	
	
	@Override
	public void save(OfficeWorkMeetingMinutes officeWorkMeetingMinutes,List<UploadFile> uploadFileList){
		if(uploadFileList!=null&&uploadFileList.size()>0){
			officeWorkMeetingMinutes.setHasAttached(Constants.HASATTACHMENT);
		}else{
			officeWorkMeetingMinutes.setHasAttached(Constants.NOATTACHMENT);
		}
		officeWorkMeetingMinutesDao.save(officeWorkMeetingMinutes);
		saveAttachment(officeWorkMeetingMinutes,uploadFileList);
	}

	private void saveAttachment(OfficeWorkMeetingMinutes officeWorkMeetingMinutes,List<UploadFile> uploadFileList) {
		//保存附件
		if(!CollectionUtils.isEmpty(uploadFileList)){
			for(UploadFile file:uploadFileList){
				Attachment attachment=new Attachment();
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachment.setUnitId(officeWorkMeetingMinutes.getUnitId());
				attachment.setObjectId(officeWorkMeetingMinutes.getId());
				attachment.setObjectType(OfficeWorkMeetingMinutes.OFFICEWORKMEETINGMINUTES_ATTACHMENT);
				attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
				System.out.println(attachmentService);
				attachmentService.saveAttachment(attachment, file,false);
			}
		}
		
	}

	@Override
	public Integer delete(String[] ids){
		return officeWorkMeetingMinutesDao.delete(ids);
	}

	@Override
	public Integer update(OfficeWorkMeetingMinutes officeWorkMeetingMinutes,List<UploadFile> uploadFileList,String[] removeAttachment){
		List<Attachment> attachments=attachmentService.getAttachments(officeWorkMeetingMinutes.getId(), OfficeWorkMeetingMinutes.OFFICEWORKMEETINGMINUTES_ATTACHMENT);
		int removeLength=0;
		if(removeAttachment==null){
			removeLength=0;
		}else{
			removeLength=removeAttachment.length;
		}
		if((attachments.size()-removeLength+uploadFileList.size())>0){
			officeWorkMeetingMinutes.setHasAttached(Constants.HASATTACHMENT);
		}else{
			officeWorkMeetingMinutes.setHasAttached(Constants.NOATTACHMENT);
		}
		officeWorkMeetingMinutes.setCreateTime(new Date());
		officeWorkMeetingMinutesDao.update(officeWorkMeetingMinutes);
		if(ArrayUtils.isNotEmpty(removeAttachment)){
			attachmentService.deleteAttachments(removeAttachment);
		}
		saveAttachment(officeWorkMeetingMinutes, uploadFileList);
		return officeWorkMeetingMinutesDao.update(officeWorkMeetingMinutes);
	}
	
	@Override
	public OfficeWorkMeetingMinutes getOfficeWorkMeetingMinutesById(String id){
		//TODO
		OfficeWorkMeetingMinutes minutes =  officeWorkMeetingMinutesDao.getOfficeWorkMeetingMinutesById(id);
		List<Attachment> attachments = attachmentService
				.getAttachments(minutes.getId(), OfficeWorkMeetingMinutes.OFFICEWORKMEETINGMINUTES_ATTACHMENT);
		if(attachments!=null&&attachments.size()>0){
			minutes.setFileName(attachments.get(0).getFileName());
			minutes.setFileUrl(attachments.get(0).getDownloadPath());
		}
		return minutes;
	}

	@Override
	public Map<String, OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesMapByIds(String[] ids){
		return officeWorkMeetingMinutesDao.getOfficeWorkMeetingMinutesMapByIds(ids);
	}

	@Override
	public List<OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesList(){
		return officeWorkMeetingMinutesDao.getOfficeWorkMeetingMinutesList();
	}
	
	@Override
	public Map<String, OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesMap() {
		List<OfficeWorkMeetingMinutes> minlist = officeWorkMeetingMinutesDao.getOfficeWorkMeetingMinutesList();
		Map<String,OfficeWorkMeetingMinutes> minmap = new HashMap<String, OfficeWorkMeetingMinutes>();
		for(OfficeWorkMeetingMinutes min : minlist){
			minmap.put(min.getMeetingId(), min);
		}
		return minmap;
	}

	@Override
	public List<OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesPage(Pagination page){
		return officeWorkMeetingMinutesDao.getOfficeWorkMeetingMinutesPage(page);
	}

	public void setOfficeWorkMeetingMinutesDao(OfficeWorkMeetingMinutesDao officeWorkMeetingMinutesDao){
		this.officeWorkMeetingMinutesDao = officeWorkMeetingMinutesDao;
	}

	@Override
	public OfficeWorkMeetingMinutes getOfficeWorkMinutesByMeetId(
			String meetingId) {
		return officeWorkMeetingMinutesDao.getOfficeWorkMeetingsByMeetId(meetingId);
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@Override
	public OfficeWorkMeetingMinutes save(
			OfficeWorkMeetingMinutes officeworkmeetingminutes) {
		return officeWorkMeetingMinutesDao.save(officeworkmeetingminutes);
	}

	@Override
	public Integer update(OfficeWorkMeetingMinutes officeWorkMeetingMinutes) {
		
		return officeWorkMeetingMinutesDao.update(officeWorkMeetingMinutes);
	}

	
}
