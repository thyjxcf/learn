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
			try {
				officeAssetCategoryService.update(c);
				promptMessageDto.setOperateSuccess(true);
			} catch (Exception e) {
				promptMessageDto.setErrorMessage("操作异常："+e.getMessage());
			}
		}
		return SUCCESS;
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
}
