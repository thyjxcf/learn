package net.zdsoft.office.secretaryMail.service.impl;

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
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.office.secretaryMail.dao.OfficeJzmailDao;
import net.zdsoft.office.secretaryMail.entity.OfficeJzmail;
import net.zdsoft.office.secretaryMail.service.OfficeJzmailService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_jzmail
 * @author 
 * 
 */
public class OfficeJzmailServiceImpl implements OfficeJzmailService{
	private OfficeJzmailDao officeJzmailDao;
	private AttachmentService attachmentService;
	private UserService userService;
	private UnitService unitService;

	@Override
	public OfficeJzmail save(OfficeJzmail officeJzmail){
		return officeJzmailDao.save(officeJzmail);
	}

	@Override
	public Integer delete(String[] ids){
		return officeJzmailDao.delete(ids);
	}

	@Override
	public Integer update(OfficeJzmail officeJzmail){
		return officeJzmailDao.update(officeJzmail);
	}

	@Override
	public OfficeJzmail getOfficeJzmailById(String id){
		List<Attachment> attachments=attachmentService.getAttachments(id, Constants.OFFICE_JZMAIL_AIT);
		OfficeJzmail officeJzmail=officeJzmailDao.getOfficeJzmailById(id);
		for (Attachment attachment : attachments) {
			//storageFileService.setDirPath(attachment);
			//attachment.setFilePath(attachment.getDirPath()+ File.separator +attachment.getFilePath());
			if(StringUtils.equals(attachment.getExtName(),"png")||StringUtils.equals(attachment.getExtName(), "jpg")
					||StringUtils.equals(attachment.getExtName(), "jpeg")||StringUtils.equals(attachment.getExtName(), "gif")){
				officeJzmail.setDisplay(true);
			}
		}
		if(StringUtils.isNotBlank(officeJzmail.getCreateUserId())){
			User user=userService.getUser(officeJzmail.getCreateUserId());
			if(user!=null){
				officeJzmail.setCreateUserName(user.getRealname());
			}else{
				officeJzmail.setCreateUserName("用户已删除");
			}
		}
		if(StringUtils.isNotBlank(officeJzmail.getUnitId())){
			Unit unit=unitService.getUnit(officeJzmail.getUnitId());
			if(unit!=null){
				officeJzmail.setUnitName(unit.getName());
			}
		}
		if(StringUtils.isNotBlank(officeJzmail.getDealUserId())){
			User user2=userService.getUser(officeJzmail.getDealUserId());
			if(user2!=null){
				officeJzmail.setDealUserName(user2.getRealname());
			}else{
				officeJzmail.setDealUserName("用户已删除");
			}
		}
		if(CollectionUtils.isNotEmpty(attachments)){
			officeJzmail.setAttachments(attachments);
		}
		return officeJzmail;
	}

	@Override
	public Map<String, OfficeJzmail> getOfficeJzmailMapByIds(String[] ids){
		return officeJzmailDao.getOfficeJzmailMapByIds(ids);
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailList(){
		return officeJzmailDao.getOfficeJzmailList();
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailPage(Pagination page){
		return officeJzmailDao.getOfficeJzmailPage(page);
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailByUnitIdList(String unitId){
		return officeJzmailDao.getOfficeJzmailByUnitIdList(unitId);
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailByUnitIdPage(String unitId, Pagination page){
		return officeJzmailDao.getOfficeJzmailByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailByUnitIdAndTitle(String unitId,String userId,
			String startTime, String endTime, String title, Pagination page) {
		return officeJzmailDao.getOfficeJzmailByUnitIdAndTitle(unitId,userId, startTime, endTime, title, page);
	}

	@Override
	public void add(OfficeJzmail officeJzmail, List<UploadFile> uploadFileList) {
		officeJzmailDao.save(officeJzmail);
		if(!CollectionUtils.isEmpty(uploadFileList)){
			for (UploadFile uploadFile : uploadFileList) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeJzmail.getUnitId());
				attachment.setObjectId(officeJzmail.getId());
				attachment.setObjectType(Constants.OFFICE_JZMAIL_AIT);
				attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
				attachmentService.saveAttachment(attachment, uploadFile, false);
			}
		}
	}

	@Override
	public List<OfficeJzmail> getOfficeJzmailByUnitIdAndTitleAndOther(
			String startTime, String endTime, String title,
			String createUserName, String unitName, String state,
			Pagination page) {
		boolean anonymous=false;
		if(org.apache.commons.lang3.StringUtils.isNotBlank(createUserName)&&org.apache.commons.lang3.StringUtils.equals(createUserName, "匿名")){
			createUserName="";
			anonymous=true;
		}
		List<OfficeJzmail> officeJzmails=officeJzmailDao.getOfficeJzmailByUnitIdAndTitleAndOther(startTime, endTime, title, createUserName, unitName, state, page,anonymous);
		Set<String> userIdSet=new HashSet<String>();
		if(CollectionUtils.isNotEmpty(officeJzmails)){
			for (OfficeJzmail officeJzmail : officeJzmails) {
				userIdSet.add(officeJzmail.getCreateUserId());
			}
		}
		Map<String,User> userMap=userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		for (OfficeJzmail officeJzmail : officeJzmails) {
			User user=userMap.get(officeJzmail.getCreateUserId());
			if(user!=null){
				officeJzmail.setCreateUserName(user.getRealname());
			}else{
				officeJzmail.setCreateUserName("用户已删除");
			}
		}
		return officeJzmails;
	}

	public void setOfficeJzmailDao(OfficeJzmailDao officeJzmailDao){
		this.officeJzmailDao = officeJzmailDao;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
}
