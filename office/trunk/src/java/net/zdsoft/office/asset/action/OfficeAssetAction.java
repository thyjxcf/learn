package net.zdsoft.office.asset.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowApplyService;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowAuditService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.keel.util.RandomUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.asset.constant.OfficeAssetConstants;
import net.zdsoft.office.asset.entity.OfficeAssetApply;
import net.zdsoft.office.asset.entity.OfficeAssetCategory;
import net.zdsoft.office.asset.service.OfficeAssetApplyService;
import net.zdsoft.office.asset.service.OfficeAssetBusinessApplyAuditService;
import net.zdsoft.office.asset.service.OfficeAssetCategoryService;
import net.zdsoft.office.util.Constants;

public class OfficeAssetAction extends OfficeAssetCommonAction implements ModelDriven<OfficeAssetApply>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeAssetApplyService officeAssetApplyService;
	
	private OfficeAssetCategoryService officeAssetCategoryService;
	
	private SchFlowApplyService schFlowApplyService;
	
	private SchFlowAuditService schFlowAuditService;
	
	private OfficeAssetBusinessApplyAuditService officeAssetBusinessApplyAuditService;
	
	private UserService userService;
	
	private TeacherService teacherService;
	
	private DeptService deptService;
	
	private List<OfficeAssetApply> applyList;
	
	private List<OfficeAssetApply> auditList;
	
	private OfficeAssetApply assetApply = new OfficeAssetApply();
	
	private OfficeAssetCategory officeAssetCategory=new OfficeAssetCategory();
	
	private List<CustomRole> roleList = new ArrayList<CustomRole>();
	
	private String roleCode;
	
	private String stateQuery;
	
	private String applyid;
	
	private String auditid;
	
	private boolean auditModel=false;
	
	private List<Dept> deptList=new ArrayList<Dept>();
	private String searchName;
	private String currentDeptId;
	
	private boolean assetManage=false;
	
	
	public String execute() throws Exception {
		queryRoles();
		if(CollectionUtils.isEmpty(roleList)){
			
		}
		return SUCCESS;
	}
	
	public String applyList(){
		assetApply.setUnitId(getUnitId());
		assetApply.setApplyUserId(getLoginInfo().getUser().getId());
		if(StringUtils.isNotBlank(stateQuery)){
			assetApply.setApplyStatus(stateQuery);
		}
		applyList = officeAssetApplyService.getAssetApplyList(assetApply, getPage());
		return SUCCESS;
	}
	
	public String addApply(){
		
		return SUCCESS;
	} 
	
	public String editApply(){
		assetApply = officeAssetApplyService.getOfficeAssetApplyById(assetApply.getId());
		FlowApply aent = schFlowApplyService.getFlowApply(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), applyid);
		assetApply.setApplyId(aent.getId());
		assetApply.setReason(aent.getReason());
		Map<String, OfficeAssetCategory> cateMap = officeAssetCategoryService.getOfficeAssetCategoryMap(getUnitId());
		if(cateMap.containsKey(assetApply.getCategoryId())){
			assetApply.setCategoryName(cateMap.get(assetApply.getCategoryId()).getAssetName());
		}else{
			assetApply.setCategoryName("类别已删除");
		}
		return SUCCESS;
	}
	
	public String saveApply(){
		UploadFile file = null;
		if(StringUtils.isBlank(assetApply.getId())){
			User user = getLoginInfo().getUser();
			assetApply.setUnitId(getUnitId());
			assetApply.setApplyCode(returnAssetApplyCode());
			assetApply.setApplyUserId(user.getId()); 
			assetApply.setApplyUserName(user.getRealname());
			assetApply.setPurchaseUserid2(user.getId());
			try {
				file = StorageFileUtils.handleFile(new String[] {"gif",
						"bmp", "jpg", "jpeg", "png"},Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
				officeAssetApplyService.save(assetApply,file);
				promptMessageDto.setOperateSuccess(true);
			} catch (Exception e) {
				promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
				promptMessageDto.setOperateSuccess(false);
			}
		}else{
			//重新提交
			OfficeAssetApply updateEnt = officeAssetApplyService.getOfficeAssetApplyById(assetApply.getId());
			updateEnt.setAssetFormat(assetApply.getAssetFormat());
			updateEnt.setAssetNumber(assetApply.getAssetNumber());
			updateEnt.setUnitPrice(assetApply.getUnitPrice());
			updateEnt.setAssetUnit(assetApply.getAssetUnit());
			updateEnt.setApplyId(assetApply.getApplyId());
			//原实际采购单价置空
			updateEnt.setPurchaseUserid1(null);
			updateEnt.setPurchasePrice(null);
			updateEnt.setPurchaseTotalPrice(null);
			try {
				file = StorageFileUtils.handleFile(new String[] {"gif",
						"bmp", "jpg", "jpeg", "png"}, Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
				officeAssetApplyService.saveSubmitFlowApply(updateEnt,file);
				promptMessageDto.setOperateSuccess(true);
			} catch (Exception e) {
				promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
				promptMessageDto.setOperateSuccess(false);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 返回一个请购单号
	 * @return
	 */
	private String returnAssetApplyCode(){
		String code = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		code = df.format(new Date())+String.valueOf(RandomUtils.getRandomInt(0, 999999));
		return code;
	}
	
	/**
	 * 资产查询
	 */
	public String applyQuery(){//TODO
		deptList=deptService.getDepts(getUnitId());
		return SUCCESS;
	}
	public String applyQueryList(){
		applyList=officeAssetApplyService.getAssetApplyQueryList(getUnitId(), stateQuery, searchName, currentDeptId, getPage());
		return SUCCESS;
	}
	public String applyQueryView(){
		assetApply = officeAssetApplyService.getOfficeAssetApplyById(assetApply.getId());
		
		officeAssetCategory=officeAssetCategoryService.getOfficeAssetCategoryById(assetApply.getCategoryId());
		
		FlowApply aent = schFlowApplyService.getFlowApply(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), applyid);
		User user = userService.getUserWithDel(aent.getApplyUserId());
		assetApply.setReason(aent.getReason());
		if(user != null){
			assetApply.setApplyUserName(user.getRealname());
			
			Teacher t = teacherService.getTeacher(user.getTeacherid());
			if(t != null){
				Dept d = deptService.getDept(t.getDeptid());
				if(d != null){
					assetApply.setDeptName(d.getDeptname());
				}else{
					assetApply.setDeptName("部门已删除");
				}
			}
		}else{
			assetApply.setApplyUserName("用户已删除");
		}
		
		Map<String, OfficeAssetCategory> cMap = officeAssetCategoryService.getOfficeAssetCategoryMap(getUnitId());
		if(cMap.containsKey(assetApply.getCategoryId())){
			assetApply.setCategoryName(cMap.get(assetApply.getCategoryId()).getAssetName());
		}else{
			assetApply.setCategoryName("类别已删除");
		}
		
//		FlowAudit adent = schFlowAuditService.getFlowAudit(auditid);
//		assetApply.setAuditId(adent.getId());
//		assetApply.setAuditStatus(String.valueOf(adent.getStatus()));
//		assetApply.setOpinion(adent.getOpinion());
		
		Map<String, List<FlowAudit>> auditMap = schFlowAuditService.getFlowAuditsMap(new String[]{applyid});
		if(auditMap.containsKey(applyid)){
			List<FlowAudit> alist = auditMap.get(applyid);
			int m = alist.size();
			if(m > 0){
				assetApply.setDeptState(String.valueOf(alist.get(0).getStatus()));
				assetApply.setDeptOpinion(alist.get(0).getOpinion());
			}
			if(m > 1){
				assetApply.setAssetLeaderState(String.valueOf(alist.get(1).getStatus()));
				assetApply.setAssetLeaderOpinion(alist.get(1).getOpinion());
			}
			if(m > 2){
				assetApply.setSchoolmasterState(String.valueOf(alist.get(2).getStatus()));
				assetApply.setSchoolmasterOpinion(alist.get(2).getOpinion());
			}
			if(m > 3){
				assetApply.setMeetingleaderState(String.valueOf(alist.get(3).getStatus()));
				assetApply.setMeetingleaderOpinion(alist.get(3).getOpinion());
			}
		}
		
		return SUCCESS;
	}
	/**
	 * 审核
	 * @param officeAssetApplyService
	 */
	public String auditAdmin(){
		queryRoles();
		if(CollectionUtils.isEmpty(roleList)){
			promptMessageDto.setPromptMessage("没有权限");
			return PROMPTMSG;
		}
		return SUCCESS;
	}
	
	public String auditList(){
		assetApply.setUnitId(getUnitId());
		assetApply.setAuditStatus(stateQuery);
		auditList = officeAssetApplyService.getAssetAuditList(assetApply, roleCode, getLoginInfo().getUser(), getPage());
		return SUCCESS;
	}
	
	public String auditEdit(){
		assetApply = officeAssetApplyService.getOfficeAssetApplyById(assetApply.getId());
		FlowApply aent = schFlowApplyService.getFlowApply(officeAssetBusinessApplyAuditService.getApplyBusinessService(OfficeAssetConstants.BUSINESS_TYPE), applyid);
		User user = userService.getUserWithDel(aent.getApplyUserId());
		assetApply.setReason(aent.getReason());
		if(user != null){
			assetApply.setApplyUserName(user.getRealname());
			
			Teacher t = teacherService.getTeacher(user.getTeacherid());
			if(t != null){
				Dept d = deptService.getDept(t.getDeptid());
				if(d != null){
					assetApply.setDeptName(d.getDeptname());
				}else{
					assetApply.setDeptName("部门已删除");
				}
			}
		}else{
			assetApply.setApplyUserName("用户已删除");
		}
		
		officeAssetCategory=officeAssetCategoryService.getOfficeAssetCategoryById(assetApply.getCategoryId());
		
		Map<String, OfficeAssetCategory> cMap = officeAssetCategoryService.getOfficeAssetCategoryMap(getUnitId());
		if(cMap.containsKey(assetApply.getCategoryId())){
			assetApply.setCategoryName(cMap.get(assetApply.getCategoryId()).getAssetName());
		}else{
			assetApply.setCategoryName("类别已删除");
		}
		
		FlowAudit adent = schFlowAuditService.getFlowAudit(auditid);
		assetApply.setAuditId(adent.getId());
		assetApply.setAuditStatus(String.valueOf(adent.getStatus()));
		assetApply.setOpinion(adent.getOpinion());
		
		Map<String, List<FlowAudit>> auditMap = schFlowAuditService.getFlowAuditsMap(new String[]{applyid});
		if(auditMap.containsKey(applyid)){
			List<FlowAudit> alist = auditMap.get(applyid);
			int m = alist.size();
			if(m > 0){
				assetApply.setDeptState(String.valueOf(alist.get(0).getStatus()));
				assetApply.setDeptOpinion(alist.get(0).getOpinion());
			}
			if(m > 1){
				assetApply.setAssetLeaderState(String.valueOf(alist.get(1).getStatus()));
				assetApply.setAssetLeaderOpinion(alist.get(1).getOpinion());
			}
			if(m > 2){
				assetApply.setSchoolmasterState(String.valueOf(alist.get(2).getStatus()));
				assetApply.setSchoolmasterOpinion(alist.get(2).getOpinion());
			}
			if(m > 3){
				assetApply.setMeetingleaderState(String.valueOf(alist.get(3).getStatus()));
				assetApply.setMeetingleaderOpinion(alist.get(3).getOpinion());
			}
		}
		
		return SUCCESS;
	}
	
	public String saveAudit(){
		User user = getLoginInfo().getUser(); 
		FlowAudit audit = new FlowAudit();
		audit.setAuditUnitId(user.getUnitid());
		audit.setAuditUserId(user.getId());
		audit.setAuditUsername(user.getRealname());
		audit.setStatus(Integer.valueOf(assetApply.getAuditStatus()));
		audit.setOpinion(assetApply.getOpinion());
		audit.setAuditDate(new Date());
		try {
			officeAssetApplyService.saveOfficeAssetApplyAudit(audit, assetApply.getId(),assetApply.getApplyId(),user.getId(), assetApply.getAuditId());
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("审核异常！"+e.getMessage());
			return SUCCESS;
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.setPromptMessage("操作成功");
		return SUCCESS;
	}
	
	private void queryRoles(){
		CustomRole role1 = customRoleService.getCustomRoleByRoleCode(getUnitId(), OfficeAssetConstants.OFFICE_MEETINGLEADER);
		CustomRole role2 = customRoleService.getCustomRoleByRoleCode(getUnitId(), OfficeAssetConstants.OFFICE_SCHOOLMASTER);
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		List<CustomRole> roles = new ArrayList<CustomRole>(); 
		if(CollectionUtils.isNotEmpty(roleUs)){
			int i = 0;
			for(CustomRoleUser ru : roleUs){
				if(StringUtils.equals(ru.getRoleId(), role1.getId())){
					roles.add(role1);
					i++;
					continue;
				}
				if(StringUtils.equals(ru.getRoleId(), role2.getId())){
					roles.add(role2);
					i++;
					continue;
				}
				if(i==2){
					break;
				}
			}
		}
		
		String userId = getLoginInfo().getUser().getId();
		
		List<OfficeAssetCategory> catelist1 = officeAssetCategoryService.getOfficeAssetCategoryListByDeptLeaderId(getUnitId(), userId);
		List<OfficeAssetCategory> catelist2 = officeAssetCategoryService.getOfficeAssetCategoryListByLeaderId(getUnitId(), userId);
		if(CollectionUtils.isNotEmpty(catelist1)){
			CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(),  OfficeAssetConstants.OFFCIE_DEPT_LEADER);
			roleList.add(role);
		}
		if(CollectionUtils.isNotEmpty(catelist2)){
			CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(),  OfficeAssetConstants.OFFICE_ASSET_LEADER);
			roleList.add(role);
		}
		roleList.addAll(roles);
	}
	
	public void setOfficeAssetApplyService(
			OfficeAssetApplyService officeAssetApplyService) {
		this.officeAssetApplyService = officeAssetApplyService;
	}
	
	public List<OfficeAssetCategory> getAssetCategoryList(){
		return officeAssetCategoryService.getOfficeAssetCategoryList(getUnitId());
	}

	public OfficeAssetApply getModel() {
		return assetApply;
	}

	public OfficeAssetApply getAssetApply() {
		return assetApply;
	}

	public void setAssetApply(OfficeAssetApply assetApply) {
		this.assetApply = assetApply;
	}

	public List<OfficeAssetApply> getApplyList() {
		return applyList;
	}
	
	public void setOfficeAssetCategoryService(
			OfficeAssetCategoryService officeAssetCategoryService) {
		this.officeAssetCategoryService = officeAssetCategoryService;
	}
	
	public List<OfficeAssetApply> getAuditList() {
		return auditList;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getStateQuery() {
		return stateQuery;
	}

	public void setStateQuery(String stateQuery) {
		this.stateQuery = stateQuery;
	}

	public List<CustomRole> getRoleList() {
		return roleList;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getApplyid() {
		return applyid;
	}

	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}

	public String getAuditid() {
		return auditid;
	}

	public void setAuditid(String auditid) {
		this.auditid = auditid;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSchFlowApplyService(SchFlowApplyService schFlowApplyService) {
		this.schFlowApplyService = schFlowApplyService;
	}

	public void setSchFlowAuditService(SchFlowAuditService schFlowAuditService) {
		this.schFlowAuditService = schFlowAuditService;
	}
	public void setOfficeAssetBusinessApplyAuditService(
			OfficeAssetBusinessApplyAuditService officeAssetBusinessApplyAuditService) {
		this.officeAssetBusinessApplyAuditService = officeAssetBusinessApplyAuditService;
	}
	
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public boolean isAuditModel() {
		String standardValue = systemIniService
				.getValue("ASSET.AUDIT.MODEL");
		if(StringUtils.isNotBlank(standardValue) && "1".equals(standardValue)){
			return true;
		}
		return false;
	}

	public void setAuditModel(boolean auditModel) {
		this.auditModel = auditModel;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getCurrentDeptId() {
		return currentDeptId;
	}

	public void setCurrentDeptId(String currentDeptId) {
		this.currentDeptId = currentDeptId;
	}

	public boolean isAssetManage() {
		CustomRole customRole=customRoleService.getCustomRoleByRoleCode(getUnitId(), "asset_category_auth");
		if(customRole==null){
			return false;
		}
		List<CustomRoleUser> customRoleUsers=customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		for (CustomRoleUser customRoleUser : customRoleUsers) {
			if(org.apache.commons.lang.StringUtils.equals(customRole.getId(), customRoleUser.getRoleId())){
				return true;
			}
		}
		return false;
	}

	public void setAssetManage(boolean assetManage) {
		this.assetManage = assetManage;
	}

	public OfficeAssetCategory getOfficeAssetCategory() {
		return officeAssetCategory;
	}

	public void setOfficeAssetCategory(OfficeAssetCategory officeAssetCategory) {
		this.officeAssetCategory = officeAssetCategory;
	}
	
}
