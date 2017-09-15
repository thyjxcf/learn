package net.zdsoft.office.secretaryMail.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.secretaryMail.entity.OfficeJzmail;
import net.zdsoft.office.secretaryMail.entity.OfficeJzmailInfo;
import net.zdsoft.office.secretaryMail.service.OfficeJzmailInfoService;
import net.zdsoft.office.secretaryMail.service.OfficeJzmailService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
* @Package net.zdsoft.office.secretaryMail.action 
* @author songxq  
* @date 2016-11-23 下午4:34:13 
* @version V1.0
 */
@SuppressWarnings("serial")
public class secretaryMailAction extends PageSemesterAction{
	
	private OfficeJzmailService officeJzmailService;
	private OfficeJzmailInfoService officeJzmailInfoService;
	private UserService userService;
	private UnitService unitService;
	private AttachmentService attachmentService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private String beginTimes;
	private String endTimes;
	private String title;
	private String jzmailId;
	private String url;
	private String createUserName;
	private String unitName;
	private String state;
	private String topUnitName;
	
	public static final String OFFICE_MAIL_MANAGE="office_mail_manage";
	
	//附件
	private String attachmentId;
	//tup
	private String removeAttachmentId;
	
	private boolean mailManager=false;
	
	private boolean topEdu=false;
	private String fileSize;
	
	private OfficeJzmail officeJzmail=new OfficeJzmail();
	private List<OfficeJzmail> officeJzmails=new ArrayList<OfficeJzmail>();
	private OfficeJzmailInfo officeJzmailInfo=new OfficeJzmailInfo();
	public String execute(){
		List<OfficeJzmailInfo> officeJzmailInfos=officeJzmailInfoService.getOfficeJzmailInfoList();
		if(CollectionUtils.isNotEmpty(officeJzmailInfos)){
			officeJzmailInfo=officeJzmailInfos.get(0);
		}
		return SUCCESS;
	}
	public String myMailAdmin(){
		if(StringUtils.isBlank(beginTimes) && StringUtils.isBlank(endTimes)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			//c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			endTimes = df.format(c.getTime());
			c.add(Calendar.DAY_OF_MONTH, -6);
			beginTimes = df.format(c.getTime());
		}
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//Date date=new Date();
		//beginTimes=sdf.format(date);
		//endTimes=sdf.format(date);
		return SUCCESS;
	}
	public String myMailList(){
		officeJzmails=officeJzmailService.getOfficeJzmailByUnitIdAndTitle(getUnitId(), getLoginUser().getUserId(), beginTimes, endTimes, title, getPage());
		return SUCCESS;
	}
	public String myMailEdit(){
		if(officeJzmail==null){
			officeJzmail=new OfficeJzmail();
		}
		User user=userService.getUser(getLoginUser().getUserId());
		officeJzmail.setAnonymous(true);
		Unit unit=unitService.getUnit(getUnitId());
		if(user!=null){
			officeJzmail.setCreateUserName(user.getRealname());
			officeJzmail.setPhone(user.getMobilePhone());
			officeJzmail.setMail(user.getEmail());
			officeJzmail.setId(UUIDGenerator.getUUID());
		}
		if(unit!=null){
			officeJzmail.setUnitName(unit.getName());
		}
		return SUCCESS;
	}
	public String myMailSave(){
		try {
			List<UploadFile> uploadFileList  = StorageFileUtils.handleFiles(new String[] {}, 50*1024, 100*1024);
			if(officeJzmail==null){
				officeJzmail=new OfficeJzmail();
			}
			officeJzmail.setCreateTime(new Date());
			officeJzmail.setCreateUserId(getLoginUser().getUserId());
			officeJzmail.setIsDeleted(false);
			officeJzmail.setState(Constants.APPLY_STATE_SAVE);
			officeJzmail.setUnitId(getUnitId());
			if(StringUtils.isBlank(officeJzmail.getId())){
				officeJzmailService.add(officeJzmail, uploadFileList);
			}else{
				officeJzmailService.save(officeJzmail);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作失败！");
		}
		return SUCCESS;
	}
	public String myMailDelete(){
		try {
			if(StringUtils.isNotBlank(jzmailId)){
				officeJzmail=officeJzmailService.getOfficeJzmailById(jzmailId);
				officeJzmail.setIsDeleted(true);
				officeJzmailService.update(officeJzmail);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败！");
		}
		return SUCCESS;
	}
	public String myMailView(){
		officeJzmail=officeJzmailService.getOfficeJzmailById(jzmailId);
		return SUCCESS;
	}
	public String myPictureView(){
		return SUCCESS;
	}
	public String mailAdmin(){
		if(StringUtils.isBlank(beginTimes) && StringUtils.isBlank(endTimes)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			//c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			endTimes = df.format(c.getTime());
			c.add(Calendar.DAY_OF_MONTH, -6);
			beginTimes = df.format(c.getTime());
		}
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//Date date=new Date();
		//beginTimes=sdf.format(date);
		//endTimes=sdf.format(date);
		state="1";
		return SUCCESS;
	}
	public String mailList(){
		officeJzmails=officeJzmailService.getOfficeJzmailByUnitIdAndTitleAndOther(beginTimes, endTimes, title, createUserName, unitName, state, getPage());
		return SUCCESS;
	}
	public String mailSave(){
		try {
			officeJzmail=officeJzmailService.getOfficeJzmailById(jzmailId);
			officeJzmail.setDealTime(new Date());
			officeJzmail.setDealUserId(getLoginUser().getUserId());
			officeJzmail.setState(2);
			officeJzmailService.update(officeJzmail);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作失败！");
		}
		return SUCCESS;
	}
	public String mailView(){
		officeJzmail=officeJzmailService.getOfficeJzmailById(jzmailId);
		return SUCCESS;
	}
	public String mailEdit(){//TODO
		List<OfficeJzmailInfo> officeJzmailInfos=officeJzmailInfoService.getOfficeJzmailInfoByUnitIdList(getUnitId());
		if(CollectionUtils.isNotEmpty(officeJzmailInfos)){
			officeJzmailInfo=officeJzmailInfos.get(0);
		}
		return SUCCESS;
	}
	public String mailInfoSave(){
		try {
			officeJzmailInfo.setCreateUserId(getLoginUser().getUserId());
			officeJzmailInfo.setUnitId(getUnitId());
			officeJzmailInfoService.save(officeJzmailInfo);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("保存失败！");
		}
		return SUCCESS;
	}
	
	//附件保存
	public String saveAttachment(){
		try {
			List<UploadFile> uploadFileList  = StorageFileUtils.handleFiles(new String[] {},0);//js已限制
			if(StringUtils.isNotBlank(removeAttachmentId)){
				attachmentService.deleteAttachments(new String[]{removeAttachmentId});
			}
			Attachment attachment = new Attachment();
			if(!CollectionUtils.isEmpty(uploadFileList)){
				for (UploadFile uploadFile : uploadFileList) {
					attachment.setFileName(uploadFile.getFileName());
					attachment.setContentType(uploadFile.getContentType());
					attachment.setFileSize(uploadFile.getFileSize());
					attachment.setUnitId(getUnitId());
					attachment.setObjectId(jzmailId);
					attachment.setObjectType(Constants.OFFICE_JZMAIL_AIT);
					attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
					attachmentService.saveAttachment(attachment, uploadFile, false);
				}
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("文件保存成功");
			promptMessageDto.setBusinessValue(attachment.getId()+"*"+attachment.getDownloadPath());
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件保存出现问题");
		}
		return SUCCESS;
	}
	
	public String showAttachment(){
		Attachment attachment=null;
		if(StringUtils.isNotBlank(attachmentId)){
			attachment = attachmentService.getAttachment(attachmentId);
		}
		if(attachment==null){
			attachment=new Attachment();
		}
		url=attachment.getDownloadPath();
		return SUCCESS;
	}
	
	public String deleteAttachment(){
		try {
			if(StringUtils.isNotBlank(removeAttachmentId)){
				attachmentService.deleteAttachments(new String[]{removeAttachmentId});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("文件删除成功");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("文件删除出现问题,文件未找到或者已删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件删除出现问题,文件未找到或者已删除");
		}
		return SUCCESS;
	}
	
	/**
	 * 判断当前用户是否指定角色
	 * @param roleCode
	 */
	public boolean isMailManager() {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), OFFICE_MAIL_MANAGE);
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
	public boolean isTopEdu() {
		Unit unit=unitService.getTopEdu();
		boolean flag;
		if(unit == null){
			flag = false;
			return flag;
		}
		String unitId=getUnitId();
		if(StringUtils.equals(unit.getId(), unitId)){
			flag=true;
			return flag;
		}
		flag = false;
		return flag;
	}
	public String getRemoveAttachmentId() {
		return removeAttachmentId;
	}
	public void setRemoveAttachmentId(String removeAttachmentId) {
		this.removeAttachmentId = removeAttachmentId;
	}
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	public String getBeginTimes() {
		return beginTimes;
	}
	public void setBeginTimes(String beginTimes) {
		this.beginTimes = beginTimes;
	}
	public String getEndTimes() {
		return endTimes;
	}
	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public OfficeJzmail getOfficeJzmail() {
		return officeJzmail;
	}
	public void setOfficeJzmail(OfficeJzmail officeJzmail) {
		this.officeJzmail = officeJzmail;
	}
	public List<OfficeJzmail> getOfficeJzmails() {
		return officeJzmails;
	}
	public void setOfficeJzmails(List<OfficeJzmail> officeJzmails) {
		this.officeJzmails = officeJzmails;
	}
	public void setOfficeJzmailService(OfficeJzmailService officeJzmailService) {
		this.officeJzmailService = officeJzmailService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public String getJzmailId() {
		return jzmailId;
	}
	public void setJzmailId(String jzmailId) {
		this.jzmailId = jzmailId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public OfficeJzmailInfo getOfficeJzmailInfo() {
		return officeJzmailInfo;
	}
	public void setOfficeJzmailInfo(OfficeJzmailInfo officeJzmailInfo) {
		this.officeJzmailInfo = officeJzmailInfo;
	}
	public void setOfficeJzmailInfoService(
			OfficeJzmailInfoService officeJzmailInfoService) {
		this.officeJzmailInfoService = officeJzmailInfoService;
	}
	public String getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public void setMailManager(boolean mailManager) {
		this.mailManager = mailManager;
	}
	public void setTopEdu(boolean topEdu) {
		this.topEdu = topEdu;
	}
	public String getFileSize() {
		return systemIniService.getValue(Constants.FILE_INIID);
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getTopUnitName() {
		//平度定制
		if (BaseConstant.SYS_DEPLOY_SCHOOL_PINGDU.equals(getSystemDeploySchool())){
			topUnitName="留言";
		}else{
			topUnitName="局长";
			Unit unit=unitService.getTopEdu();
			if(unit != null && unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL){
				topUnitName="校长";
			}
		}
		return topUnitName;
	}
	public void setTopUnitName(String topUnitName) {
		this.topUnitName = topUnitName;
	}
	
}
