package net.zdsoft.office.survey.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.office.survey.constant.OfficeSurveyConstants;
import net.zdsoft.office.survey.entity.OfficeSurveyApply;
import net.zdsoft.office.survey.entity.OfficeSurveyAudit;
import net.zdsoft.office.survey.service.OfficeSurveyApplyService;
import net.zdsoft.office.survey.service.OfficeSurveyAuditService;

import com.opensymphony.xwork2.ModelDriven;

public class OfficeSurveyManageAction extends PageAction implements ModelDriven<OfficeSurveyApply> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 55631062287722968L;
	
	private OfficeSurveyApply officeSurveyApply = new OfficeSurveyApply();
	private OfficeSurveyAudit officeSurveyAudit = new OfficeSurveyAudit();
	
	private boolean surveyAuditAuth;//调研审核权限
	private boolean surveyQueryAuth;//调研查看权限
	private String searchType;
	private String searchName;
	private String searchPlace;
	private Date searchStartTime;
	private Date searchEndTime;
	
	private String[] checkid;
	private boolean placeByCode;//调研地点是否使用微代码
	
	private List<OfficeSurveyApply> officeSurveyApplyList = new ArrayList<OfficeSurveyApply>();
	
	private OfficeSurveyApplyService officeSurveyApplyService;
	private OfficeSurveyAuditService officeSurveyAuditService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private UserService userService;

	@Override
	public OfficeSurveyApply getModel() {
		return officeSurveyApply;
	}

	/**
	 * 判断当前用户是否指定角色
	 * @param roleCode
	 */
	public boolean isCustomRole(String roleCode){
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), roleCode);
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
	
	public String execute() {
		return SUCCESS;
	}
	
	/**
	 * 我的调研
	 */
	public String mySurvey() {
		return SUCCESS;
	}
	
	public String mySurveyList() {
		officeSurveyApplyList = officeSurveyApplyService.getSurveyApplyListByConditions(getLoginInfo().getUnitID(), 
			searchType,	null, getLoginUser().getUserId(), null, null, null, getPage());
		return SUCCESS;
	}
	
	public String mySurveyAdd() {
		officeSurveyApply = new OfficeSurveyApply();
		officeSurveyApply.setApplyUserId(getLoginUser().getUserId());
		officeSurveyApply.setApplyUserName(getLoginInfo().getUser().getRealname());
		return SUCCESS;
	}
	
	public String mySurveyEdit() {
		officeSurveyApply = officeSurveyApplyService.getOfficeSurveyApplyById(officeSurveyApply.getId());
		return SUCCESS;
	}
	
	public String mySurveyView() {
		officeSurveyApply = officeSurveyApplyService.getOfficeSurveyApplyById(officeSurveyApply.getId());
		officeSurveyAudit = officeSurveyAuditService.getOfficeSurveyAuditByUnitId(getLoginInfo().getUnitID(), officeSurveyApply.getId());
		if(officeSurveyAudit != null){
			officeSurveyApply.setOpinion(officeSurveyAudit.getOpinion());
		}
		return SUCCESS;
	}
	
	public String mySurveySave() {
		try{
			officeSurveyApply.setApplyDate(new Date());
			if(StringUtils.isBlank(officeSurveyApply.getId())){
				officeSurveyApply.setUnitId(getLoginInfo().getUnitID());
				officeSurveyApplyService.save(officeSurveyApply);
			}else{
				officeSurveyApplyService.update(officeSurveyApply);
			}
			promptMessageDto.setOperateSuccess(true);
			if(officeSurveyApply.getState() == OfficeSurveyConstants.OFFCIE_SURVEY_NOT_SUBMIT){
				promptMessageDto.setPromptMessage("保存成功！");
			}else{
				promptMessageDto.setPromptMessage("提交成功！");
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败！");
		}
		return SUCCESS;
	}
	
	public String mySurveyDelete() {
		try{
			officeSurveyApplyService.delete(checkid);
			
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 调研审核
	 */
	public String surveyAudit() {//TODO
		return SUCCESS;
	}
	public String surveyAuditList() {//TODO
		officeSurveyApplyList=officeSurveyApplyService.getSurveyApplyList(null, getLoginUser().getUnitId(), searchType, searchPlace, getPage());
		return SUCCESS;
	}
	public String surveyAuditEdit(){
		officeSurveyApply = officeSurveyApplyService.getOfficeSurveyApplyById(officeSurveyApply.getId());
		return SUCCESS;
	}
	public String surveyAuditSave() {
		try{
			if(StringUtils.isBlank(officeSurveyAudit.getId())){
				officeSurveyAudit.setAuditDate(new Date());
				officeSurveyAudit.setAuditUserId(getLoginUser().getUserId());
				officeSurveyAudit.setApplyId(officeSurveyApply.getId());
				officeSurveyAudit.setState(officeSurveyApply.getState());
				officeSurveyAudit.setUnitId(getLoginUser().getUnitId());
				officeSurveyApply.setAuditDate(new Date());
				officeSurveyAuditService.save(officeSurveyAudit,officeSurveyApply);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功！");
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	public String surveyAuditView() {
		officeSurveyApply = officeSurveyApplyService.getOfficeSurveyApplyById(officeSurveyApply.getId());
		officeSurveyAudit=officeSurveyAuditService.getOfficeSurveyAuditByUnitId(getLoginUser().getUnitId(), officeSurveyApply.getId());
		return SUCCESS;
	}
	/**
	 * 调研查询
	 */
	public String surveyQuery() {
		searchType = OfficeSurveyConstants.OFFCIE_SURVEY_ALL + "";
		return SUCCESS;
	}
	
	public String surveyQueryList() {
		officeSurveyApplyList = officeSurveyApplyService.getSurveyApplyListByConditions(getLoginInfo().getUnitID(), 
				searchType,	searchPlace, null, searchName, searchStartTime, searchEndTime, getPage());
		Map<String, OfficeSurveyAudit> surveyAuditMap = officeSurveyAuditService.getOfficeSurveyAuditByUnitIdMap(getLoginInfo().getUnitID());
		for(OfficeSurveyApply apply : officeSurveyApplyList){
			apply.setOpinion("");
			for(Map.Entry<String, OfficeSurveyAudit> entry : surveyAuditMap.entrySet()){
				OfficeSurveyAudit audit = entry.getValue();
				if(StringUtils.equals(audit.getApplyId(), apply.getId())){
					apply.setOpinion(audit.getOpinion());
				}
			}
		}
		
		return SUCCESS;
	}

	public String getPlaceMessage() {
		jsonError = "success";
		List<OfficeSurveyApply> applylist = officeSurveyApplyService.getSurveyApplyList(null, getUnitId(), null, searchPlace, null);
		Date nowDate = new Date();
		Set<String> userSet = new HashSet<String>();
		for(OfficeSurveyApply apply : applylist){
			Date auditDate = apply.getAuditDate();
			if(apply.getState() == OfficeSurveyConstants.OFFCIE_SURVEY_PASS && auditDate != null){
				long days = (nowDate.getTime() - auditDate.getTime()) /1000/60/60/24;
				if(days <= 30){
					userSet.add(apply.getApplyUserId());
				}
			}
		}
		if(userSet.size() > 0){
			Map<String, User> userMap = userService.getUsersMap(userSet.toArray(new String[0]));
			String userNames = ""; 
			for(Map.Entry<String, User> entry : userMap.entrySet()){
				User user = entry.getValue();
				if(StringUtils.isNotBlank(userNames)){
					userNames += "、" + user.getRealname();
				}else{
					userNames = user.getRealname();
				}
			}
			if(StringUtils.isNotBlank(userNames)){
				jsonError = "该调研地点已经被" + userNames+ "申请通过";
			}
		}
		return SUCCESS;
	}
	
	public OfficeSurveyApply getOfficeSurveyApply() {
		return officeSurveyApply;
	}

	public void setOfficeSurveyApply(OfficeSurveyApply officeSurveyApply) {
		this.officeSurveyApply = officeSurveyApply;
	}

	public OfficeSurveyAudit getOfficeSurveyAudit() {
		return officeSurveyAudit;
	}

	public void setOfficeSurveyAudit(OfficeSurveyAudit officeSurveyAudit) {
		this.officeSurveyAudit = officeSurveyAudit;
	}

	public boolean isSurveyAuditAuth() {
		surveyAuditAuth = isCustomRole(OfficeSurveyConstants.OFFCIE_SURVEY_AUDIT);
		return surveyAuditAuth;
	}

	public void setSurveyAuditAuth(boolean surveyAuditAuth) {
		this.surveyAuditAuth = surveyAuditAuth;
	}

	public List<OfficeSurveyApply> getOfficeSurveyApplyList() {
		return officeSurveyApplyList;
	}

	public void setOfficeSurveyApplyService(
			OfficeSurveyApplyService officeSurveyApplyService) {
		this.officeSurveyApplyService = officeSurveyApplyService;
	}

	public void setOfficeSurveyAuditService(
			OfficeSurveyAuditService officeSurveyAuditService) {
		this.officeSurveyAuditService = officeSurveyAuditService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Date getSearchStartTime() {
		return searchStartTime;
	}

	public void setSearchStartTime(Date searchStartTime) {
		this.searchStartTime = searchStartTime;
	}

	public Date getSearchEndTime() {
		return searchEndTime;
	}

	public void setSearchEndTime(Date searchEndTime) {
		this.searchEndTime = searchEndTime;
	}

	public String[] getCheckid() {
		return checkid;
	}

	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}

	public boolean isSurveyQueryAuth() {
		surveyQueryAuth = isCustomRole(OfficeSurveyConstants.OFFCIE_SURVEY_QUERY);
		return surveyQueryAuth;
	}

	public void setSurveyQueryAuth(boolean surveyQueryAuth) {
		this.surveyQueryAuth = surveyQueryAuth;
	}

	public String getSearchPlace() {
		return searchPlace;
	}

	public void setSearchPlace(String searchPlace) {
		this.searchPlace = searchPlace;
	}
	
	public boolean isPlaceByCode() {
		SystemIni systemIni = systemIniService.getSystemIni("SURVEY_PLACE_BY_CODE");
		if(systemIni != null && "1".equals(systemIni.getNowValue()))//使用微代码
			placeByCode = true;
		else
			placeByCode = false;
		return placeByCode;
	}

	public void setPlaceByCode(boolean placeByCode) {
		this.placeByCode = placeByCode;
	}
}
