package net.zdsoft.office.seal.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.converter.service.ConverterFileTypeService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.seal.entity.OfficeSeal;
import net.zdsoft.office.seal.entity.OfficeSealType;
import net.zdsoft.office.seal.service.OfficeSealService;
import net.zdsoft.office.seal.service.OfficeSealTypeService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
* @Package net.zdsoft.office.seal.action 
* @author songxq  
* @date 2016-9-28 上午11:05:02 
* @version V1.0
 */
@SuppressWarnings("serial")
public class SealAction extends PageSemesterAction{
	
	private OfficeSealService officeSealService;
	private OfficeSealTypeService officeSealTypeService;
	private DeptService deptService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private ConverterFileTypeService converterFileTypeService;
	private AttachmentService attachmentService;
	
	private OfficeSeal officeSeal=new OfficeSeal();
	private List<OfficeSeal> officeSealList=new ArrayList<OfficeSeal>();
	private OfficeSealType officeSealType;
	private List<OfficeSealType> officeSealTypeList;
	private List<Dept> deptList; 
	private String years;
	private String semesters;
	private String officeSealId;
	private String deptId;
	private String sealType;
	
	public static final String OFFICE_SEAL_MANAGE="office_seal_manage";
	
	private String removeAttachmentId;
	
	//类别选中
	private String[] checkid;
	//是否是分管校长
	private boolean deputyHead=false;
	private boolean sealManager=false;
	
	public String execute() throws Exception{
		return SUCCESS;
	}
	public String mySealAdmin(){
		return SUCCESS;
	}
	public String mySealList(){
		officeSealList=officeSealService.getOfficeSealByOthers(years, semesters, getLoginUser().getUserId(), getUnitId(), getPage());
		return SUCCESS;
	}
	public String editSeal(){
		if(StringUtils.isNotBlank(officeSealId)){
			officeSeal=officeSealService.getOfficeSealById(officeSealId);
		}
		if(org.apache.commons.lang3.StringUtils.isBlank(officeSeal.getId())){
			officeSeal.setId(UUIDGenerator.getUUID());
		}
		officeSealTypeList = officeSealTypeService.getOfficeSealTypeByUnitIdList(getUnitId());
		return SUCCESS;
	}
	public String viewSeal(){
		if(StringUtils.isNotBlank(officeSealId)){
			officeSeal=officeSealService.getOfficeSealById(officeSealId);
		}
		if(officeSeal==null){
			officeSeal=new OfficeSeal();
		}
		return SUCCESS;
	}
	public String deleteSeal(){
		try {
			if(StringUtils.isNotBlank(officeSealId)){
				officeSealService.delete(new String[]{officeSealId});
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功!");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败："+e.getMessage());
		}
		return SUCCESS;
	}
	public String saveSeal(){
		UploadFile file = null;
		try {
			//file = StorageFileUtils.handleFile(new String[] {},Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			if(StringUtils.isNotBlank(officeSeal.getId())){
				OfficeSeal office=officeSealService.getOfficeSealById(officeSeal.getId());
				if(office==null){
					officeSeal.setAcadyear(this.getCurrentSemester().getAcadyear());
					officeSeal.setSemester(Integer.valueOf(this.getCurrentSemester().getSemester()));
					officeSeal.setCreateUserId(getLoginUser().getUserId());
					officeSeal.setUnitId(getUnitId());
					officeSeal.setCreateTime(new Date());
					officeSeal.setDeptId(getLoginInfo().getUser().getDeptid());
					officeSealService.save(officeSeal);
				}else{
					OfficeSeal officeSeals=officeSealService.getOfficeSealById(officeSeal.getId());
					officeSeals.setSealType(officeSeal.getSealType());
					officeSeals.setApplyOpinion(officeSeal.getApplyOpinion());
					officeSeals.setState(officeSeal.getState());
					officeSeals.setUploadContentFileInput(officeSeal.getUploadContentFileInput());
					officeSealService.update(officeSeals);
				}
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("用印申请成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("用印申请失败！");
		}
		return SUCCESS;
	}
	public String submitSeal(){
		UploadFile file = null;
		try {
			//file = StorageFileUtils.handleFile(new String[] {},Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			officeSeal=officeSealService.getOfficeSealById(officeSealId);
			officeSeal.setState("2");
			officeSeal.setDelete(true);
			officeSealService.update(officeSeal);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功!");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("提交失败:"+e.getMessage());
		}
		return SUCCESS;
	}
	/**
	 * 用印管理
	 * @return
	 */
	public String sealManageAdmin(){
		officeSealTypeList = officeSealTypeService.getOfficeSealTypeByUnitIdList(getUnitId());
		deptList=deptService.getDepts(getUnitId());
		return SUCCESS;
	}
	public String sealManageList(){//TODO
		List<Dept> depts=new ArrayList<Dept>();
		if(isDeputyHead()){
			depts=deptService.DeputyHead(getLoginInfo().getUnitID(), getLoginUser().getUserId());
		}
		officeSealList=officeSealService.getOfficeSealManageByOthers(depts,getUnitId(), deptId, sealType, getPage());
		Collections.sort(officeSealList, new Comparator<OfficeSeal>() {
			@Override
			public int compare(OfficeSeal o1, OfficeSeal o2) {
				String compare1=StringUtils.isEmpty(o1.getManageUserId())?"":o1.getManageUserId();
				String compare2=StringUtils.isEmpty(o2.getManageUserId())?"":o2.getManageUserId();
				return compare1.compareTo(compare2);
			}
		});
		return SUCCESS;
	}
	public String sealManageAudit(){
		if(StringUtils.isNotBlank(officeSealId)){
			officeSeal=officeSealService.getOfficeSealById(officeSealId);
		}
		if(officeSeal==null){
			officeSeal=new OfficeSeal();
		}
		return SUCCESS;
	}
	public String sealManageView(){
		if(StringUtils.isNotBlank(officeSealId)){
			officeSeal=officeSealService.getOfficeSealById(officeSealId);
		}
		if(officeSeal==null){
			officeSeal=new OfficeSeal();
		}
		return SUCCESS;
	}
	public String sealManageSave(){
		try {
			OfficeSeal seal=null;
			if(StringUtils.isNotBlank(officeSealId)){
				seal=officeSealService.getOfficeSealById(officeSealId);
			}
			if(seal==null){
				seal=new OfficeSeal();
			}
			seal.setAuditUserId(getLoginUser().getUserId());
			seal.setAuditOpinion(officeSeal.getAuditOpinion());
			seal.setState(officeSeal.getState());
			officeSealService.update(seal);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	public String sealManageConfirmSave(){
		try {
			OfficeSeal seal=null;
			if(StringUtils.isNotBlank(officeSealId)){
				seal=officeSealService.getOfficeSealById(officeSealId);
			}
			if(seal==null){
				seal=new OfficeSeal();
			}
			seal.setManageUserId(getLoginUser().getUserId());
			seal.setUseSeal(officeSeal.getUseSeal());
			officeSealService.update(seal);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	/**
	 * 印章类型
	 * @return
	 */
	public String sealType() {//TODO
		officeSealTypeList = officeSealTypeService.getOfficeSealTypeByUnitIdList(getUnitId());
		return SUCCESS;
	}

	public String sealTypeAdd() {
		try{
			int num = 1;
			officeSealTypeList = officeSealTypeService.getOfficeSealTypeByUnitIdList(getUnitId());
			for(OfficeSealType type : officeSealTypeList){
				if(StringUtils.equals(officeSealType.getTypeName(), type.getTypeName())){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("该印章类型已存在！");
					return SUCCESS;
				}
				if(num <= Integer.parseInt(type.getTypeId())){
					num = Integer.parseInt(type.getTypeId()) + 1;
				}
			}
			officeSealType.setUnitId(getUnitId());
			officeSealType.setTypeId(num + "");
			officeSealTypeService.save(officeSealType);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("印章类型添加成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("印章类型添加失败！");
		}
		return SUCCESS;
	}
	
	public String sealTypeEdit() {
		try{
			String typeName = officeSealType.getTypeName();
			officeSealType = officeSealTypeService.getOfficeSealTypeById(officeSealType.getId());
			officeSealTypeList = officeSealTypeService.getOfficeSealTypeByUnitIdList(getUnitId());
			for(OfficeSealType type : officeSealTypeList){
				if(!StringUtils.equals(officeSealType.getId(), type.getId())
						&& StringUtils.equals(typeName, type.getTypeName())){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("该印章类型已存在！");
					return SUCCESS;
				}
			}
			officeSealType.setTypeName(typeName);
			officeSealTypeService.update(officeSealType);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("印章类型修改成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("印章类型修改失败！");
		}
		return SUCCESS;
	}
	
	public String sealTypeDelete() {
		try{
			Map<String,OfficeSealType> map=officeSealTypeService.getOfficeSealTypeMap(checkid);
			List<String> typeId=new ArrayList<String>();
			for (Entry<String, OfficeSealType> ele : map.entrySet()) {
				OfficeSealType officeSealT=ele.getValue();
				typeId.add(officeSealT.getTypeId());
			}
			List<OfficeSeal> list=officeSealService.getOfficeSealByUnitIdTypeId(getUnitId(), typeId.toArray(new String[0]));
			if(CollectionUtils.isNotEmpty(list)&&list.size()>0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败，要删除的印章类型存在用印记录!");
				return SUCCESS;
			}
			officeSealTypeService.delete(checkid);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("印章类型删除成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("印章类型删除失败！");
		}
		return SUCCESS;
	}
	/**
	 * 判断当前用户是否指定角色
	 * @param roleCode
	 */
	public boolean isSealManager() {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), OFFICE_SEAL_MANAGE);
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
	public List<String> getYearList() {
		List<String> acadyears = new ArrayList<String>();
		String currentYear = DateUtils.date2String(new Date(), "yyyy");
		acadyears.add(Integer.parseInt(currentYear)+3+"-"+(Integer.parseInt(currentYear)+4));
		acadyears.add(Integer.parseInt(currentYear)+2+"-"+(Integer.parseInt(currentYear)+3));
		acadyears.add(Integer.parseInt(currentYear)+1+"-"+(Integer.parseInt(currentYear)+2));
		acadyears.add(Integer.parseInt(currentYear)+"-"+(Integer.parseInt(currentYear)+1));
		acadyears.add(Integer.parseInt(currentYear)-1+"-"+Integer.parseInt(currentYear));
		acadyears.add(Integer.parseInt(currentYear)-2+"-"+(Integer.parseInt(currentYear)-1));
		acadyears.add(Integer.parseInt(currentYear)-3+"-"+(Integer.parseInt(currentYear)-2));
		return acadyears;
	}
	public String getYear() {
		if (StringUtils.isBlank(years)) {
			return this.getCurrentSemester().getAcadyear();
		}
		return years;
	}
	public String getSemester() {
		if (StringUtils.isBlank(semesters)) {
			return this.getCurrentSemester().getSemester();
		}
		return semesters;
	}
	
	public String deleteFileAttach(){
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
	public String saveFileAttach(){
		try{
			List<UploadFile> files=StorageFileUtils.handleFiles(new String[] {}, 0);//js已限制
			Attachment attachment=null;
			if(!CollectionUtils.isEmpty(files)){
				for (UploadFile uploadFile : files) {
					attachment=new Attachment();
					attachment.setFileName(uploadFile.getFileName());
					attachment.setContentType(uploadFile.getContentType());
					attachment.setFileSize(uploadFile.getFileSize());
					attachment.setUnitId(getUnitId());
					attachment.setObjectId(officeSealId);
					attachment.setObjectType(Constants.OFFICE_SEAL_AIT);
					String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(attachment.getFileName());
					if(converterFileTypeService.isVideo(fileExt)||converterFileTypeService.isDocument(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
					}
					if(converterFileTypeService.isPicture(fileExt)||converterFileTypeService.isAudio(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_SUCCESS);
					}
					attachmentService.saveAttachment(attachment, uploadFile);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("文件保存成功");
				promptMessageDto.setBusinessValue(attachment.getId()+"*"+attachment.getDownloadPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件保存出现问题");
		}
	
		return SUCCESS;
	}
	
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getSemesters() {
		return semesters;
	}
	public void setSemesters(String semesters) {
		this.semesters = semesters;
	}
	public void setOfficeSealService(OfficeSealService officeSealService) {
		this.officeSealService = officeSealService;
	}
	public void setOfficeSealTypeService(OfficeSealTypeService officeSealTypeService) {
		this.officeSealTypeService = officeSealTypeService;
	}
	public OfficeSeal getOfficeSeal() {
		return officeSeal;
	}
	public void setOfficeSeal(OfficeSeal officeSeal) {
		this.officeSeal = officeSeal;
	}
	public List<OfficeSeal> getOfficeSealList() {
		return officeSealList;
	}
	public void setOfficeSealList(List<OfficeSeal> officeSealList) {
		this.officeSealList = officeSealList;
	}
	public String getOfficeSealId() {
		return officeSealId;
	}
	public void setOfficeSealId(String officeSealId) {
		this.officeSealId = officeSealId;
	}
	public OfficeSealType getOfficeSealType() {
		return officeSealType;
	}
	public void setOfficeSealType(OfficeSealType officeSealType) {
		this.officeSealType = officeSealType;
	}
	public List<OfficeSealType> getOfficeSealTypeList() {
		return officeSealTypeList;
	}
	public void setOfficeSealTypeList(List<OfficeSealType> officeSealTypeList) {
		this.officeSealTypeList = officeSealTypeList;
	}
	public String[] getCheckid() {
		return checkid;
	}
	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}
	public List<Dept> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getSealType() {
		return sealType;
	}
	public void setSealType(String sealType) {
		this.sealType = sealType;
	}
	public boolean isDeputyHead() {
		return deptService.isDeputyHead(getUnitId(), getLoginUser().getUserId());
	}
	public void setDeputyHead(boolean deputyHead) {
		this.deputyHead = deputyHead;
	}
	public void setSealManager(boolean sealManager) {
		this.sealManager = sealManager;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public String getExtNames(){
		String[] extNames = converterFileTypeService.getAllExtNames();
		StringBuffer sb = new StringBuffer();
		if(extNames!=null){//rtf,doc,docx,xls,xlsx,csv,ppt,pptx,pdf,bmp,jpg,jpeg,png,gif
			for (String extName : extNames) {
				boolean contain=extName.contains("bmp")||extName.contains("jpg")||extName.contains("jpeg")||extName.contains("png")||extName.contains("gif");
				boolean contain2=extName.contains("doc")||extName.contains("docx")||extName.contains("xls")||extName.contains("xlsx")||extName.contains("pdf");
				boolean contain3=extName.contains("ppt")||extName.contains("pptx");
				if(contain||contain2||contain3){
					sb.append(extName+",");
				}
			}
		}
		return sb.toString();
	}
	public void setConverterFileTypeService(
			ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
	}
	public String getFileSize() {
		return systemIniService.getValue(Constants.FILE_INIID);
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
	
}
