package net.zdsoft.office.asset.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.auditflow.manager.FlowInvoke;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.impl.AbstractApplyBusinessServiceImpl;
import net.zdsoft.eis.base.common.dao.TeacherDao;
import net.zdsoft.eis.base.common.dao.UserDao;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.converter.service.ConverterFileTypeService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.office.asset.constant.OfficeAssetConstants;
import net.zdsoft.office.asset.dao.OfficeAssetApplyDao;
import net.zdsoft.office.asset.entity.OfficeAssetApply;
import net.zdsoft.office.asset.entity.OfficeAssetCategory;
import net.zdsoft.office.asset.service.OfficeAssetApplyService;
import net.zdsoft.office.asset.service.OfficeAssetBusinessApplyAuditService;
import net.zdsoft.office.asset.service.OfficeAssetCategoryService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_asset_apply
 * @author 
 * 
 */
public class OfficeAssetApplyServiceImpl extends
		AbstractApplyBusinessServiceImpl<OfficeAssetApply> implements OfficeAssetApplyService{
	private OfficeAssetApplyDao officeAssetApplyDao;
	private OfficeAssetBusinessApplyAuditService officeAssetBusinessApplyAuditService;
	private UserDao userDao;
	private CustomRoleService customRoleService;
	private TeacherDao teacherDao;
	private OfficeAssetCategoryService officeAssetCategoryService;
	private DeptService deptService;
	private UserService userService;
	private AttachmentService attachmentService;
	private SystemIniService systemIniService;
	private ConverterFileTypeService converterFileTypeService;
	

	private Map<String, String> businessMap = new HashMap<String, String>();
	
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public void setOfficeAssetCategoryService(
			OfficeAssetCategoryService officeAssetCategoryService) {
		this.officeAssetCategoryService = officeAssetCategoryService;
	}
	public void setOfficeAssetBusinessApplyAuditService(
			OfficeAssetBusinessApplyAuditService officeAssetBusinessApplyAuditService) {
		this.officeAssetBusinessApplyAuditService = officeAssetBusinessApplyAuditService;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}
	
	public List<OfficeAssetApply> getAssetApplyList(OfficeAssetApply ent, Pagination page){
		List<OfficeAssetApply> list = officeAssetApplyDao.getAssetApplyList(ent, page);
		Map<String, OfficeAssetCategory> cateMap = officeAssetCategoryService.getOfficeAssetCategoryMap(ent.getUnitId());
		Set<String> businessIds = new HashSet<String>();
		for(OfficeAssetApply a : list){
			businessIds.add(a.getId());
		}
		
		Map<String,FlowApply> flowapplyMap = schFlowApplyService.getFlowApplyMapByBusinessIds(businessIds.toArray(new String[0]));
		Set<String> applyIds = new HashSet<String>();
		for(Map.Entry<String, FlowApply> entry : flowapplyMap.entrySet()){
			applyIds.add(entry.getValue().getId());
		}
		Map<String, List<FlowAudit>> auditMap = schFlowAuditService.getFlowAuditsMap(applyIds.toArray(new String[0]));
		for(OfficeAssetApply c : list){
			if(cateMap.containsKey(c.getCategoryId())){
				c.setCategoryName(cateMap.get(c.getCategoryId()).getAssetName());
			}else{
				c.setCategoryName("类别已删除");
			}
			if(auditMap.containsKey(c.getApplyId())){
				List<FlowAudit> alist = auditMap.get(c.getApplyId());
				int m = alist.size();
				if(m > 0){
					c.setDeptState(String.valueOf(alist.get(0).getStatus()));
					c.setDeptOpinion(alist.get(0).getOpinion());
				}
				if(m > 1){
					c.setAssetLeaderState(String.valueOf(alist.get(1).getStatus()));
					c.setAssetLeaderOpinion(alist.get(1).getOpinion());
				}
				if(m > 2){
					c.setSchoolmasterState(String.valueOf(alist.get(2).getStatus()));
					c.setSchoolmasterOpinion(alist.get(2).getOpinion());
				}
				if(m > 3){
					c.setMeetingleaderState(String.valueOf(alist.get(3).getStatus()));
					c.setMeetingleaderOpinion(alist.get(3).getOpinion());
				}
			}
			//判断是否是采购总价大于所申请的最大金额值
			if(c.getPurchaseTotalPrice() != null){
				double m = returnApplyMaxNum(c.getTotalUnitPrice());
				if((m <= 2000 && c.getPurchaseTotalPrice() > m) || (m >2000 && c.getPurchaseTotalPrice() >= m)){
					c.setIsOverMaxNum(true);
				}
			}
		}
		
		
		return list;
	}
	
	@Override
	public List<OfficeAssetApply> getAssetApplyQueryList(String unitId,
			String state, String name, String deptId, Pagination page) {//TODO
		List<OfficeAssetApply> list = officeAssetApplyDao.getAssetApplyQueryList(unitId, state, name, deptId, page);
		Map<String, OfficeAssetCategory> cateMap = officeAssetCategoryService.getOfficeAssetCategoryMap(unitId);
		Set<String> businessIds = new HashSet<String>();
		Set<String> userIds=new HashSet<String>();
		for(OfficeAssetApply a : list){
			businessIds.add(a.getId());
			userIds.add(a.getApplyUserId());
		}
		
		Map<String,User> userMap=userService.getUserWithDelMap(userIds.toArray(new String[0]));
		Map<String,Teacher> teacherMap=teacherDao.getTeacherMap(unitId);
		Map<String,Dept> deptMap=deptService.getDeptMap(unitId);
		for (OfficeAssetApply a : list) {
			User user=userMap.get(a.getApplyUserId());
			if(user!=null){
				a.setApplyUserName(user.getRealname());
				Teacher teacher=teacherMap.get(user.getTeacherid());
				if(teacher!=null){
					Dept dept=deptMap.get(teacher.getDeptid());
					if(dept!=null){
						a.setDeptName(dept.getDeptname());
					}
				}
			}else{
				a.setApplyUserName("用户已删除");
			}
		}
		
		Map<String,FlowApply> flowapplyMap = schFlowApplyService.getFlowApplyMapByBusinessIds(businessIds.toArray(new String[0]));
		Set<String> applyIds = new HashSet<String>();
		for(Map.Entry<String, FlowApply> entry : flowapplyMap.entrySet()){
			applyIds.add(entry.getValue().getId());
		}
		Map<String, List<FlowAudit>> auditMap = schFlowAuditService.getFlowAuditsMap(applyIds.toArray(new String[0]));
		for(OfficeAssetApply c : list){
			if(cateMap.containsKey(c.getCategoryId())){
				c.setCategoryName(cateMap.get(c.getCategoryId()).getAssetName());
			}else{
				c.setCategoryName("类别已删除");
			}
			if(auditMap.containsKey(c.getApplyId())){
				List<FlowAudit> alist = auditMap.get(c.getApplyId());
				int m = alist.size();
				if(m > 0){
					c.setDeptState(String.valueOf(alist.get(0).getStatus()));
					c.setDeptOpinion(alist.get(0).getOpinion());
				}
				if(m > 1){
					c.setAssetLeaderState(String.valueOf(alist.get(1).getStatus()));
					c.setAssetLeaderOpinion(alist.get(1).getOpinion());
				}
				if(m > 2){
					c.setSchoolmasterState(String.valueOf(alist.get(2).getStatus()));
					c.setSchoolmasterOpinion(alist.get(2).getOpinion());
				}
				if(m > 3){
					c.setMeetingleaderState(String.valueOf(alist.get(3).getStatus()));
					c.setMeetingleaderOpinion(alist.get(3).getOpinion());
				}
			}
			//判断是否是采购总价大于所申请的最大金额值
			if(c.getPurchaseTotalPrice() != null){
				double m = returnApplyMaxNum(c.getTotalUnitPrice());
				if((m <= 2000 && c.getPurchaseTotalPrice() > m) || (m >2000 && c.getPurchaseTotalPrice() >= m)){
					c.setIsOverMaxNum(true);
				}
			}
		}
		
		
		return list;
	}

	public List<OfficeAssetApply> getAssetAuditList(OfficeAssetApply ent, String roleCode, User user, Pagination page){
		
		Set<String> arrangeIds = new HashSet<String>();
		CustomRole role = customRoleService.getCustomRoleByRoleCode(ent.getUnitId(), roleCode);
		if(StringUtils.equals(roleCode, OfficeAssetConstants.OFFCIE_DEPT_LEADER)){
			List<OfficeAssetCategory> catelist = officeAssetCategoryService.getOfficeAssetCategoryListByDeptLeaderId(ent.getUnitId(), user.getId());
			if(CollectionUtils.isNotEmpty(catelist)){
				for(OfficeAssetCategory cate : catelist){
					arrangeIds.add(cate.getId());
				}
			}
		}else if(StringUtils.equals(roleCode, OfficeAssetConstants.OFFICE_ASSET_LEADER)){
			List<OfficeAssetCategory> catelist = officeAssetCategoryService.getOfficeAssetCategoryListByLeaderId(ent.getUnitId(), user.getId());
			if(CollectionUtils.isNotEmpty(catelist)){
				for(OfficeAssetCategory cate : catelist){
					arrangeIds.add(cate.getId());
				}
			}
		}else{
			arrangeIds.add(getSchoolId(user.getId(), null, null));
		}
		
		List<OfficeAssetApply> auditlist = officeAssetApplyDao.getAssetApplyAuditList(ent, role.getId(), FlowApply.OPERATE_TYPE_NO, arrangeIds.toArray(new String[0]), OfficeAssetConstants.BUSINESS_TYPE, page);
		Map<String, OfficeAssetCategory> cateMap = officeAssetCategoryService.getOfficeAssetCategoryMap(ent.getUnitId());
//		Map<String, User> userMap = userDao.getUserMap(ent.getUnitId(), User.TEACHER_LOGIN);
		Map<String, User> userMap = userService.getUserWithDelMap(ent.getUnitId());
		Map<String, Teacher> teacherMap = teacherDao.getTeacherMap(ent.getUnitId());
		Map<String, Dept> deptMap = deptService.getDeptMap(ent.getUnitId());
		for(OfficeAssetApply audit : auditlist){
			if(cateMap.containsKey(audit.getCategoryId())){
				audit.setCategoryName(cateMap.get(audit.getCategoryId()).getAssetName());
			}else{
				audit.setCategoryName("类别已删除");
			}
			if(userMap.containsKey(audit.getApplyUserId())){
				User u = userMap.get(audit.getApplyUserId());
				if(u != null){
					audit.setApplyUserName(u.getRealname());
					if(teacherMap.containsKey(u.getTeacherid())){
						Teacher t = teacherMap.get(u.getTeacherid());
						if(t != null){
							if(deptMap.containsKey(t.getDeptid())){
								audit.setDeptName(deptMap.get(t.getDeptid()).getDeptname());
							}else{
								audit.setDeptName("部门已删除");
							}
						}
					}
				}else{
					audit.setApplyUserName("用户已删除");
				}
			}
			
		}
		
		return auditlist;
	}
	

	public OfficeAssetApply save(OfficeAssetApply officeAssetApply, UploadFile uploadFile){
		OfficeAssetApply apply = officeAssetApplyDao.save(officeAssetApply);
		businessMap.put(apply.getId(), apply.getCategoryId());
		FlowApply flowApply = new FlowApply(
				officeAssetBusinessApplyAuditService
						.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE));
		flowApply.setBusinessId(apply.getId());
		flowApply.setBusinessType(OfficeAssetConstants.BUSINESS_TYPE);
		flowApply.setOperateType(FlowApply.OPERATE_TYPE_NO);
		flowApply.setReason(apply.getReason());// 用途 申请原因
		flowApply.setApplyUserId(apply.getApplyUserId());
		flowApply.setApplyUsername(apply.getApplyUserName());
		flowApply.setApplyUnitId(apply.getUnitId());
		flowApply.setApplyDate(new Date());
		flowApply.setStatus(FlowApply.STATUS_IN_AUDIT);
		schFlowApplyService.addFlowApply(flowApply);
		
		//TODO加流程
		if(!this.isAuditModel()){
			OfficeAssetCategory officeAssetCategory=officeAssetCategoryService.getOfficeAssetCategoryById(apply.getCategoryId());
			if(officeAssetCategory.isIs_DeptLeader()){
				List<FlowAudit> flowAudits=flowAuditService.getFlowAudits(flowApply.getId());
				FlowAudit flowAudit=flowAudits.get(0);
				flowAudit.setOpinion("通过");
				flowAudit.setAuditUserId(apply.getApplyUserId());
				flowAudit.setStatus(2);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit, new String[]{flowAudit.getId()});
			}
			if(officeAssetCategory.isIs_DeptLeader()&&officeAssetCategory.isIs_Leader()){
				List<FlowAudit> flowAudits=flowAuditService.getFlowAudits(flowApply.getId());
				FlowAudit flowAudit=flowAudits.get(1);
				flowAudit.setOpinion("通过");
				flowAudit.setAuditUserId(apply.getApplyUserId());
				flowAudit.setStatus(2);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit, new String[]{flowAudit.getId()});
			}
			if(officeAssetCategory.isIs_DeptLeader()&&officeAssetCategory.isIs_Leader()&&officeAssetCategory.isIs_master()){
				List<FlowAudit> flowAudits=flowAuditService.getFlowAudits(flowApply.getId());
				FlowAudit flowAudit=flowAudits.get(2);
				flowAudit.setOpinion("通过");
				flowAudit.setAuditUserId(apply.getApplyUserId());
				flowAudit.setStatus(2);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit, new String[]{flowAudit.getId()});
			}
			if(officeAssetCategory.isIs_DeptLeader()&&officeAssetCategory.isIs_Leader()&&officeAssetCategory.isIs_master()&&officeAssetCategory.isIs_meeting()){
				List<FlowAudit> flowAudits=flowAuditService.getFlowAudits(flowApply.getId());
				FlowAudit flowAudit=flowAudits.get(3);
				flowAudit.setOpinion("通过");
				flowAudit.setAuditUserId(apply.getApplyUserId());
				flowAudit.setStatus(4);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit, new String[]{flowAudit.getId()});
			}
		}
				
		if(uploadFile!=null){
			Attachment attachment = new Attachment();
			attachment.setFileName(uploadFile.getFileName());
			attachment.setContentType(uploadFile.getContentType());
			attachment.setFileSize(uploadFile.getFileSize());
			attachment.setUnitId(apply.getUnitId());
			attachment.setObjectId(apply.getId());
			attachment.setObjectType(Constants.OFFICE_ASSET_AIT);
			String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(attachment.getFileName());
			if(converterFileTypeService.isVideo(fileExt)||converterFileTypeService.isDocument(fileExt)){
				attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
			}
			if(converterFileTypeService.isPicture(fileExt)||converterFileTypeService.isAudio(fileExt)){
				attachment.setConStatus(BusinessTask.TASK_STATUS_SUCCESS);
			}
			attachmentService.saveAttachment(attachment, uploadFile);
		}
		return apply;
	}
	
	private boolean isAuditModel() {
		String standardValue = systemIniService
				.getValue("ASSET.AUDIT.MODEL");
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}
	
	public void saveSubmitFlowApply(OfficeAssetApply ent, UploadFile uploadFile){
		officeAssetApplyDao.update(ent);
		schFlowApplyService.saveSubmitFlowApply(ent.getApplyId());
		if(uploadFile!=null){
			List<Attachment> attachments = attachmentService.getAttachments(ent.getId(), Constants.OFFICE_ASSET_AIT);
			if(CollectionUtils.isNotEmpty(attachments)){
				//Attachment attachment = attachments.get(0);
				//attachment.setFileName(uploadFile.getFileName());
				//attachment.setContentType(uploadFile.getContentType());
				//attachment.setFileSize(uploadFile.getFileSize());
				//attachmentService.updateAttachment(attachment, uploadFile, true);
				String[] attachmentIds=new String[attachments.size()];
				for(int i=0;i<attachments.size();i++){
					attachmentIds[i]=attachments.get(i).getId();
				}
				attachmentService.deleteAttachments(attachmentIds);
			}
			Attachment attachment = new Attachment();
			attachment.setFileName(uploadFile.getFileName());
			attachment.setContentType(uploadFile.getContentType());
			attachment.setFileSize(uploadFile.getFileSize());
			attachment.setUnitId(ent.getUnitId());
			attachment.setObjectId(ent.getId());
			attachment.setObjectType(Constants.OFFICE_ASSET_AIT);
			String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(attachment.getFileName());
			if(converterFileTypeService.isVideo(fileExt)||converterFileTypeService.isDocument(fileExt)){
				attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
			}
			if(converterFileTypeService.isPicture(fileExt)||converterFileTypeService.isAudio(fileExt)){
				attachment.setConStatus(BusinessTask.TASK_STATUS_SUCCESS);
			}
			attachmentService.saveAttachment(attachment, uploadFile);
		}
	}
	
	public void saveOfficeAssetApplyAudit(FlowAudit audit, String businessId,String applyid,String userId, String... auditIds){
		OfficeAssetApply apply = officeAssetApplyDao.getOfficeAssetApplyById(businessId);
		businessMap.put(apply.getId(), apply.getCategoryId());
		schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), audit, auditIds);
		
		if(!this.isAuditModel()&&!StringUtils.equals("3", audit.getStatus()+"")){
			OfficeAssetCategory officeAssetCategory=officeAssetCategoryService.getOfficeAssetCategoryById(apply.getCategoryId());
			FlowAudit flowAudit=flowAuditService.getFlowAudit(auditIds[0]);
			if(officeAssetCategory.isIs_Leader()&&flowAudit.getAuditOrder()==0){
				List<FlowAudit> flowAudits=flowAuditService.getFlowAudits(applyid);
				FlowAudit flowAudit1=flowAudits.get(1);
				flowAudit1.setOpinion("通过");
				flowAudit1.setAuditUserId(userId);
				flowAudit1.setStatus(2);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit1, new String[]{flowAudit1.getId()});
			}
			if(officeAssetCategory.isIs_master()&&flowAudit.getAuditOrder()==1){
				List<FlowAudit> flowAudits=flowAuditService.getFlowAudits(applyid);
				FlowAudit flowAudit1=flowAudits.get(2);
				flowAudit1.setOpinion("通过");
				flowAudit1.setAuditUserId(userId);
				flowAudit1.setStatus(2);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit1, new String[]{flowAudit1.getId()});
			}
			if(officeAssetCategory.isIs_meeting()&&flowAudit.getAuditOrder()==2){
				List<FlowAudit> flowAudits=flowAuditService.getFlowAudits(applyid);
				FlowAudit flowAudit1=flowAudits.get(3);
				flowAudit1.setOpinion("通过");
				flowAudit1.setAuditUserId(userId);
				flowAudit1.setStatus(4);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit1, new String[]{flowAudit1.getId()});
			}
			if(officeAssetCategory.isIs_Leader()&&officeAssetCategory.isIs_master()&&flowAudit.getAuditOrder()==0){
				List<FlowAudit> flowAuditss=flowAuditService.getFlowAudits(applyid);
				FlowAudit flowAudit1=flowAuditss.get(2);
				flowAudit1.setOpinion("通过");
				flowAudit1.setAuditUserId(userId);
				flowAudit1.setStatus(2);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit1, new String[]{flowAudit1.getId()});
			}
			if(officeAssetCategory.isIs_master()&&officeAssetCategory.isIs_meeting()&&flowAudit.getAuditOrder()==1){
				List<FlowAudit> flowAuditss=flowAuditService.getFlowAudits(applyid);
				FlowAudit flowAudit1=flowAuditss.get(3);
				flowAudit1.setOpinion("通过");
				flowAudit1.setAuditUserId(userId);
				flowAudit1.setStatus(4);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit1, new String[]{flowAudit1.getId()});
			}
			if(officeAssetCategory.isIs_master()&&officeAssetCategory.isIs_meeting()&&officeAssetCategory.isIs_Leader()&&flowAudit.getAuditOrder()==0){
				List<FlowAudit> flowAuditss=flowAuditService.getFlowAudits(applyid);
				FlowAudit flowAudit1=flowAuditss.get(3);
				flowAudit1.setOpinion("通过");
				flowAudit1.setAuditUserId(userId);
				flowAudit1.setStatus(4);
				//flowAuditService.saveUpOneStep(flowAudit);
				schFlowApplyService.saveAudits(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), flowAudit1, new String[]{flowAudit1.getId()});
			}
		}
		
	}

	@Override
	public Integer update(OfficeAssetApply officeAssetApply){
		return officeAssetApplyDao.update(officeAssetApply);
	}
	
	public String savePurchaseInfo(OfficeAssetApply ent){
		String returnMsg = "";
		//判断是否是采购总价大于所申请的最大金额值
		if(ent.getPurchaseTotalPrice() != null){
			double m = returnApplyMaxNum(ent.getTotalUnitPrice());
			if((m <= 2000 && ent.getPurchaseTotalPrice() > m) || (m >2000 && ent.getPurchaseTotalPrice() >= m)){
				try {
					ent.setIsPassApply(false);
					ent.setPurchaseState(String.valueOf(FlowAudit.STATUS_PREPARING));
				} catch (Exception e) {
					e.printStackTrace();
				}
				returnMsg = "实际采购总价大于请购时所需要审核的临界值，已反馈给请购人重新申请采购！";
			}else{
				ent.setPurchaseState(String.valueOf(FlowAudit.STATUS_CHECKING));
			}
		}
		officeAssetApplyDao.update(ent);
		
		return returnMsg;
	}

	@Override
	public OfficeAssetApply getOfficeAssetApplyById(String id){
		OfficeAssetApply ent = officeAssetApplyDao.getOfficeAssetApplyById(id);
		if(ent.getPurchaseTotalPrice() != null){
			double m = returnApplyMaxNum(ent.getTotalUnitPrice());
			if((m <= 2000 && ent.getPurchaseTotalPrice() > m) || (m >2000 && ent.getPurchaseTotalPrice() >= m)){
				ent.setIsOverMaxNum(true);
			}
		}
		ent.setAttachments(attachmentService.getAttachments(ent.getId(), Constants.OFFICE_ASSET_AIT));
		return ent;
	}

	public void setOfficeAssetApplyDao(OfficeAssetApplyDao officeAssetApplyDao){
		this.officeAssetApplyDao = officeAssetApplyDao;
	}
	
	public String getArrangeId(String userId, String method, String type,String businessId) {
		String[] params = new String[] { userId, type ,this.businessMap.get(businessId)};
		try {
			return FlowInvoke.getArrangeId2(new OfficeAssetApplyServiceImpl(),
					method, params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getDeptId(String userId, String type, String businessId) {
		return businessId;
	}
	
	public String getAssetLeaderId(String userId, String type, String businessId) {
		return businessId;
	}
	
	public String getSchoolId(String userId, String type, String businessId) {
		if (null == userDao) {
			userDao = (UserDao) ContainerManager.getComponent("userDao");
		}
		return userDao.getUser(userId).getUnitid();
	}

	public OfficeAssetApply getEntity() {
		OfficeAssetApply ent = new OfficeAssetApply();
		ent.setFlowTypeValue(OfficeAssetConstants.BUSINESS_TYPE);
		return ent;
	}
	
	public void saveDisposeBusiness(FlowApply apply) {
		OfficeAssetApply ent = officeAssetApplyDao.getOfficeAssetApplyById(apply.getBusinessId());
		ent.setIsPassApply(true);
		ent.setPurchaseState(String.valueOf(FlowAudit.STATUS_PREPARING));
		ent.setCreationTime(new Date());
		officeAssetApplyDao.update(ent);
	}
	
	public List<OfficeAssetApply> getOfficeAssetApplyPurchaseList(String unitId, String applyUserId, String state, boolean isAudit, Pagination page){
		List<OfficeAssetApply> list = officeAssetApplyDao.getOfficeAssetApplyPurchaseList(unitId, applyUserId, state, isAudit, page);
		Map<String, OfficeAssetCategory> cateMap = officeAssetCategoryService.getOfficeAssetCategoryMap(unitId);
		Set<String> ids = new HashSet<String>();
		for(OfficeAssetApply ent : list){
			ids.add(ent.getId());
		}
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		for(OfficeAssetApply ent : list){
			if(cateMap.containsKey(ent.getCategoryId())){
				ent.setCategoryName(cateMap.get(ent.getCategoryId()).getAssetName());
			}else{
				ent.setCategoryName("类别已删除");
			}
			if(StringUtils.isNotBlank(ent.getPurchaseUserid1())){
				if(userMap.containsKey(ent.getPurchaseUserid1())){
					ent.setPurchaseUserName1(userMap.get(ent.getPurchaseUserid1()).getRealname());
				}else{
					ent.setPurchaseUserName1("用户已删除");
				}
			}
			if(StringUtils.isNotBlank(ent.getPurchaseUserid2())){
				if(userMap.containsKey(ent.getPurchaseUserid2())){
					ent.setPurchaseUserName2(userMap.get(ent.getPurchaseUserid2()).getRealname());
				}else{
					ent.setPurchaseUserName2("用户已删除");
				}
			}
		}
		return list;
	}
	
	private Double returnApplyMaxNum(double applyPrice){
		double m = 0;
		if(applyPrice<= 200){
			m = 200;
		}else if(applyPrice <= 2000){
			m = 2000;
		}else if(applyPrice < 10000){
			m = 10000;
		}else{
			m = 50000;
		}
		return m;
	}
	
	public List<OfficeAssetApply> getAssetData(OfficeAssetApply ent, Date queryBeginDate, Date queryEndDate, Pagination page){
		List<OfficeAssetApply> list = officeAssetApplyDao.getAssetData(ent, queryBeginDate, queryEndDate, page);
		Map<String, OfficeAssetCategory> cateMap = officeAssetCategoryService.getOfficeAssetCategoryMap(ent.getUnitId());
		for(OfficeAssetApply it : list){
			User user = userService.getUserWithDel(it.getApplyUserId());
			if(user != null){
				it.setApplyUserName(user.getRealname());
			}else{
				it.setApplyUserName("用户已删除");
			}
			if(cateMap.containsKey(it.getCategoryId())){
				it.setCategoryName(cateMap.get(it.getCategoryId()).getAssetName());
			}else{
				it.setCategoryName("类别已删除");
			}
		}
		return list;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setConverterFileTypeService(
			ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
	}
	
}