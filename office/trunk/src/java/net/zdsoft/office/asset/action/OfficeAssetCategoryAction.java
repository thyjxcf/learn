package net.zdsoft.office.asset.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;

import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.office.asset.entity.OfficeAssetCategory;
import net.zdsoft.office.asset.service.OfficeAssetCategoryService;

public class OfficeAssetCategoryAction extends BaseAction implements ModelDriven<OfficeAssetCategory>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeAssetCategoryService officeAssetCategoryService;
	
	private List<OfficeAssetCategory> categorylist;
	
	private UserService userService;
	
	private OfficeAssetCategory category = new OfficeAssetCategory();
	
	private boolean auditModel=false;
	
	public String execute() throws Exception {
		categorylist = officeAssetCategoryService.getOfficeAssetCategoryList(getUnitId());
		dealNameStr(categorylist);
		return SUCCESS;
	}
	
	public String addCategory(){
			
		return SUCCESS;
	}
	
	public String updateCategoryUpdate(){
		category = officeAssetCategoryService.getOfficeAssetCategoryById(category.getId());
		List<OfficeAssetCategory> list = new ArrayList<OfficeAssetCategory>();
		list.add(category);
		dealNameStr(list);
		return SUCCESS;
	}
	
	public String saveCategory(){
		if(StringUtils.isBlank(category.getId())){
			List<OfficeAssetCategory> clist = officeAssetCategoryService.getOfficeAssetCategoryList(getUnitId(), category.getAssetName(), null);
			if(clist != null && clist.size() >0){
				promptMessageDto.setErrorMessage("系统中已存在此类别，请重新维护！");
				return SUCCESS;
			}
			category.setUnitId(getUnitId());
			if(dealCategory(category)){
				if(isAuditModel()){
					promptMessageDto.setErrorMessage("请设置审核人");
				}else{
					promptMessageDto.setErrorMessage("请设置审核人或勾选不审核步骤");
				}
				return SUCCESS;
			}
			officeAssetCategoryService.save(category);
			try {
				promptMessageDto.setOperateSuccess(true);
			} catch (Exception e) {
				promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
			}
		}else{
			List<OfficeAssetCategory> clist = officeAssetCategoryService.getOfficeAssetCategoryList(getUnitId(), category.getAssetName(), category.getId());
			if(clist != null && clist.size() >0){
				promptMessageDto.setErrorMessage("系统中已存在此类别，请重新维护！");
				return SUCCESS;
			}
			OfficeAssetCategory c = officeAssetCategoryService.getOfficeAssetCategoryById(category.getId());
			c.setAssetName(category.getAssetName());
			c.setLeaderId(category.getLeaderId());
			c.setDeptLeaderId(category.getDeptLeaderId());
			c.setIs_DeptLeader(category.isIs_DeptLeader());
			c.setIs_Leader(category.isIs_Leader());
			c.setIs_master(category.isIs_master());
			c.setIs_meeting(category.isIs_meeting());
			try {
				if(dealCategory(c)){
					if(isAuditModel()){
						promptMessageDto.setErrorMessage("请设置审核人");
					}else{
						promptMessageDto.setErrorMessage("请设置审核人或勾选不审核步骤");
					}
					return SUCCESS;
				}
				officeAssetCategoryService.update(c);
				promptMessageDto.setOperateSuccess(true);
			} catch (Exception e) {
				promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
			}
		}
		return SUCCESS;
	}

	private boolean dealCategory(OfficeAssetCategory c){
		if(!isAuditModel()){
			if((!c.isIs_DeptLeader()&&StringUtils.isBlank(c.getDeptLeaderId()))||(!c.isIs_Leader()&&StringUtils.isBlank(c.getLeaderId()))){
				return true;
			}
		}else{
			if(StringUtils.isBlank(c.getDeptLeaderId())||StringUtils.isBlank(c.getLeaderId())){
				return true;
			}
		}
		return false;
	}
	
	public String deleteCategory(){
		try {
			officeAssetCategoryService.delete(new String[]{category.getId()});
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
		}
		return SUCCESS;
	}
	
	private void dealNameStr(List<OfficeAssetCategory> list){
		Map<String, User> userMap = userService.getUserMap(getUnitId());
		for(OfficeAssetCategory ent : list){
			if(userMap.containsKey(ent.getLeaderId())){
				ent.setLeaderName(userMap.get(ent.getLeaderId()).getRealname());
			}
			StringBuffer nameStr = new StringBuffer();
			StringBuffer idStr = new StringBuffer();
			if(StringUtils.isNotBlank(ent.getDeptLeaderId())){
				String[] ids = ent.getDeptLeaderId().split(",");
				int m=0;
				for(int i = 0; i < ids.length; i++){
					if(userMap.containsKey(ids[i])){
						if(m==0){
							nameStr.append(userMap.get(ids[i]).getRealname());
							idStr.append(ids[i]);
						}else{
							nameStr.append(",").append(userMap.get(ids[i]).getRealname());
							idStr.append(",").append(ids[i]);
						}
						m++;
					}
				}
				ent.setDeptLeaderId(idStr.toString());
				ent.setDeptLeaderName(nameStr.toString());
			}
		}
	}
	
	public void setOfficeAssetCategoryService(
			OfficeAssetCategoryService officeAssetCategoryService) {
		this.officeAssetCategoryService = officeAssetCategoryService;
	}

	public OfficeAssetCategory getModel() {
		return category;
	}

	public OfficeAssetCategory getCategory() {
		return category;
	}

	public void setCategory(OfficeAssetCategory category) {
		this.category = category;
	}

	public List<OfficeAssetCategory> getCategorylist() {
		return categorylist;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
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
}
